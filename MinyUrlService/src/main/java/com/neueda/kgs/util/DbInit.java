package com.neueda.kgs.util;

import com.neueda.kgs.model.*;
import com.neueda.kgs.repository.ShortUrlRepository;
import com.neueda.kgs.repository.WorkerStatusRepository;
import com.neueda.kgs.service.ShortUrlService;
import com.neueda.kgs.service.WorkerStatusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DbInit implements CommandLineRunner {

    private ShortUrlRepository shortUrlRepository;
    private ShortUrlService shortUrlService;
    private WorkerStatusService workerStatusService;
    private WorkerStatusRepository workerStatusRepository;

    public DbInit(ShortUrlRepository shortUrlRepository, ShortUrlService shortUrlService, WorkerStatusService workerStatusService, WorkerStatusRepository workerStatusRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlService = shortUrlService;
        this.workerStatusService = workerStatusService;
        this.workerStatusRepository = workerStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
      /*  ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl("www.google.com");
        shortUrl.setCreatedDate(LocalDate.now());
        Stats stats = new Stats();

        BrowserStats  browserStats = new BrowserStats();
        browserStats.setChrome(19L);
        stats.setBrowserStats(browserStats);
        OsStat osStat = new OsStat();
        osStat.setIos(10L);
        stats.setOsStat(osStat);
        DateStat dateStat1 = new DateStat();
        dateStat1.setDayOfYear(29);
        dateStat1.setVisits(2);
        DateStat dateStat2 = new DateStat();
        dateStat2.setDayOfYear(30);
        dateStat2.setVisits(1);

        stats.getDateStats().addAll(Arrays.asList(dateStat1,dateStat2));

        shortUrl.setKey(1L);
        shortUrl.setStats(stats);


        WorkerStatus workerStatus = new WorkerStatus();
        workerStatus.setWorkerId(1);
        AllocatedCounter allocatedCounter= new AllocatedCounter();
        allocatedCounter.setCounter(1L);
        allocatedCounter.setExhausted(false);
        allocatedCounter.setRangeNumber(1);
        workerStatus.setAllocatedRanges(Arrays.asList(allocatedCounter));


        workerStatusRepository.deleteAll();*/
//        workerStatusRepository.save(workerStatus);
       /* ShortUrl shortUrl2 = new ShortUrl();
        shortUrl2.setLongUrl("wwww.google.com");
        shortUrl2.setKey(2L);*/


//        shortUrlRepository.saveAll(Arrays.asList(shortUrl));

//        workerStatusService.getNewKey(1);

        //shortUrlService.createNewRange(1L);


    }
}
