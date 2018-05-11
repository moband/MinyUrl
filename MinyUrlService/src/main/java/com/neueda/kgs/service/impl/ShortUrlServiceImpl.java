package com.neueda.kgs.service.impl;

import com.neueda.kgs.controller.dto.BaseResponse;
import com.neueda.kgs.controller.dto.NewLinkDto;
import com.neueda.kgs.controller.dto.ResolveLinkDto;
import com.neueda.kgs.controller.dto.VisitStateDto;
import com.neueda.kgs.exception.InvalidAddressException;
import com.neueda.kgs.exception.KeyNotFoundException;
import com.neueda.kgs.model.ShortUrl;
import com.neueda.kgs.model.embedded.BrowserStats;
import com.neueda.kgs.model.embedded.DateStat;
import com.neueda.kgs.model.embedded.OsStat;
import com.neueda.kgs.model.embedded.Stats;
import com.neueda.kgs.repository.ShortUrlRepository;
import com.neueda.kgs.service.ShortUrlService;
import com.neueda.kgs.service.WorkerStatusService;
import com.neueda.kgs.util.Base58;
import com.neueda.kgs.util.Utility;
import org.apache.commons.validator.UrlValidator;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private ShortUrlRepository shortUrlRepository;
    private WorkerStatusService workerStatusService;


    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, WorkerStatusService workerStatusService) {
        this.shortUrlRepository = shortUrlRepository;
        this.workerStatusService = workerStatusService;
    }

    @Override
    public String shorten(NewLinkDto linkDto) throws UnknownHostException, MalformedURLException {
        String workerID = Utility.getHostname();
        linkDto.setLongUrl(Utility.urlNormalization(linkDto.getLongUrl()));

        if (!Utility.isUrlValid(linkDto.getLongUrl())) throw new MalformedURLException();
        Optional<ShortUrl> existingShortUrl = Optional.ofNullable(shortUrlRepository.findByLongUrl(linkDto.getLongUrl()));
        if (existingShortUrl.isPresent()) return Base58.fromBase10(existingShortUrl.get().getKey());

        Long newKey = workerStatusService.getNewKey(workerID);
        ShortUrl newShortUrl = new ShortUrl();
        newShortUrl.setKey(newKey);
        newShortUrl.setLongUrl(linkDto.getLongUrl());
        newShortUrl.setStats(this.initState());
        newShortUrl.setCreatedDate(LocalDate.now());

        shortUrlRepository.save(newShortUrl);
        return Base58.fromBase10(newKey);

    }

    @Override
    public ShortUrl resolve(ResolveLinkDto dto) throws KeyNotFoundException, InvalidAddressException {
        if (dto.getShortUrl() == null || "".equals(dto.getShortUrl())) throw new InvalidAddressException();
        ShortUrl shortUrl = Optional.ofNullable(shortUrlRepository.findByKey(Base58.toBase10(dto.getShortUrl())))
                .map(c -> c)
                .orElseThrow(KeyNotFoundException::new);

        this.updateStats(dto, shortUrl);
        shortUrl.setLastAccessDate(LocalDate.now());
        shortUrlRepository.save(shortUrl);
        return shortUrl;
    }


    public VisitStateDto getVisitStateByKey(String key) throws KeyNotFoundException {
        VisitStateDto dto = new VisitStateDto();
        ShortUrl shortUrl = Optional.ofNullable(shortUrlRepository.findByKey(Base58.toBase10(key)))
                .map(c -> c)
                .orElseThrow(KeyNotFoundException::new);


        LongSummaryStatistics longSummaryStatistics = shortUrl.getStats().getDateStats().stream().mapToLong(d -> d.getVisits()).summaryStatistics();
        dto.setDailyAverage(longSummaryStatistics.getAverage());
        dto.setMax(longSummaryStatistics.getMax());
        dto.setMin(longSummaryStatistics.getMin());
        dto.setTotalPerYear(longSummaryStatistics.getSum());
        dto.setPerMonth(getMonthlyVisitReport(shortUrl));
        dto.setByOs(shortUrl.getStats().getOsStat());
        dto.setByBrowsers(shortUrl.getStats().getBrowserStats());
        dto.setLastAccessDate(shortUrl.getLastAccessDate());
        dto.setCode(BaseResponse.SUCCESSFUL);
        dto.setSuccess(true);
        dto.setMessage("analytics");

        return dto;
    }

    private ShortUrl updateStats(ResolveLinkDto dto, ShortUrl shortUrl) {
        switch (dto.getBrowser().toLowerCase()) {
            case "internet explorer":
                shortUrl.getStats().getBrowserStats().incrementIe();
                break;
            case "safari":
                shortUrl.getStats().getBrowserStats().incrementSafari();
                break;
            case "chrome":
                shortUrl.getStats().getBrowserStats().incrementChrome();
                break;
            case "firefox":
                shortUrl.getStats().getBrowserStats().incrementFireFox();
                break;
            case "opera":
                shortUrl.getStats().getBrowserStats().incrementOpera();
                break;

            default:
                shortUrl.getStats().getBrowserStats().incrementOthers();
                break;
        }

        switch (dto.getOs().toLowerCase()) {
            case "windows":
                shortUrl.getStats().getOsStat().incrementWindows();
                break;
            case "mac_os":
                shortUrl.getStats().getOsStat().incrementMacOs();
                break;
            case "linux":
                shortUrl.getStats().getOsStat().incrementLinux();
                break;
            case "android":
                shortUrl.getStats().getOsStat().incrementAndroid();
                break;
            case "iphone":
                shortUrl.getStats().getOsStat().incrementIos();
                break;

            default:
                shortUrl.getStats().getOsStat().incrementOthers();
                break;
        }

        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);


        shortUrl.getStats().getDateStats().stream().filter((d) -> d.getDayOfYear() == dayOfYear).findFirst().map(d -> {
            d.incrementVisit();
            return d;
        }).orElseGet(() -> {
            DateStat newDateStat = new DateStat(dayOfYear, 1);
            shortUrl.getStats().getDateStats().add(newDateStat);
            return newDateStat;
        });

        return shortUrl;
    }

    private Stats initState() {
        Stats state = new Stats();
        state.setBrowserStats(new BrowserStats());
        state.setOsStat(new OsStat());
        return state;
    }

    private Map<String, Long> getMonthlyVisitReport(ShortUrl shortUrl) {
        int year = LocalDate.now().getYear();
        Map<String, Long> monthlyVisitsReport = new HashMap<>();
        for (LocalDate date = LocalDate.of(year, 1, 1); date.isBefore(LocalDate.of(year + 1, 1, 1)); date = date.plusMonths(1)) {
            String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            Long totalVisits = this.getTotalVisitForAMonth(shortUrl, date.getDayOfYear(), date.plusMonths(1).getDayOfYear());
            monthlyVisitsReport.put(month, totalVisits);
        }

        return monthlyVisitsReport;
    }

    private Long getTotalVisitForAMonth(ShortUrl shortUrl, Integer startDay, Integer lastDay) {
        return shortUrl.getStats().getDateStats().stream().filter(d -> d.getDayOfYear() >= startDay && d.getDayOfYear() < lastDay).mapToLong(d -> d.getVisits()).sum();
    }

}
