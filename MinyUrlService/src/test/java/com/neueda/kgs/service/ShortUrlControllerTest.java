package com.neueda.kgs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.kgs.controller.ShortUrlController;
import com.neueda.kgs.controller.dto.BaseResponse;
import com.neueda.kgs.controller.dto.NewLinkDto;
import com.neueda.kgs.controller.dto.ResolveLinkDto;
import com.neueda.kgs.controller.dto.VisitStateDto;
import com.neueda.kgs.model.ShortUrl;
import com.neueda.kgs.model.embedded.BrowserStats;
import com.neueda.kgs.model.embedded.DateStat;
import com.neueda.kgs.model.embedded.OsStat;
import com.neueda.kgs.model.embedded.Stats;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = ShortUrlController.class, secure = false)
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShortUrlService shortUrlService;

    private JacksonTester<NewLinkDto> shorteningRequest;
    private JacksonTester<VisitStateDto> getStatRequest;
    private JacksonTester<ShortUrl> getShortUrl;


    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }


    @Test
    public void should_returnShortedUrl_whenUrlIsValid() throws Exception {
        //Given
        NewLinkDto dto = new NewLinkDto();
        dto.setLongUrl("www.google.com");
        final String linkDTOJson = shorteningRequest.write(dto).getJson();
        given(shortUrlService.shorten(any(NewLinkDto.class))).willReturn("b");

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/api/v1/shortify").accept(MediaType.APPLICATION_JSON).content(linkDTOJson)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("b"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(BaseResponse.SUCCESSFUL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andReturn();
    }

    @Test
    public void should_returnError_whenUrlIsNotValid() throws Exception {
        //Given
        NewLinkDto dto = new NewLinkDto();
        dto.setLongUrl("badURL");
        final String linkDTOJson = shorteningRequest.write(dto).getJson();
        given(shortUrlService.shorten(any(NewLinkDto.class))).willThrow(new MalformedURLException());

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/api/v1/shortify").accept(MediaType.APPLICATION_JSON).content(linkDTOJson)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Url format."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(BaseResponse.BAD_REQUEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andReturn();
    }

    @Test
    public void should_returnError_whenHostNameIsNotValid() throws Exception {
        //Given
        NewLinkDto dto = new NewLinkDto();
        dto.setLongUrl("badURL");
        final String linkDTOJson = shorteningRequest.write(dto).getJson();
        given(shortUrlService.shorten(any(NewLinkDto.class))).willThrow(new UnknownHostException());

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/api/v1/shortify").accept(MediaType.APPLICATION_JSON).content(linkDTOJson)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Worker identifier not found."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(BaseResponse.BAD_REQUEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andReturn();
    }

    @Test
    public void should_returnStats_whenUrlIsValid() throws Exception {
        //Given

        VisitStateDto dto = this.initVisitStateDto();

        final String linkDTOJson = getStatRequest.write(dto).getJson();
        given(shortUrlService.getVisitStateByKey(any(String.class))).willReturn(dto);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v1/stat/YqrE3").accept(MediaType.APPLICATION_JSON).content(linkDTOJson)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(dto.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(dto.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(dto.isSuccess()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastAccessDate").value("2018-04-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dailyAverage").value(dto.getDailyAverage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.max").value(dto.getMax()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(dto.getMin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(dto.getMin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(dto.getMin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(dto.getMin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPerYear").value(dto.getTotalPerYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.June").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.December").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.October").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.May").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.September").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.March").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.July").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.January").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.February").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.April").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.August").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perMonth.November").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byBrowsers.ie").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byBrowsers.fireFox").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byBrowsers.chrome").value(dto.getByBrowsers().getChrome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byBrowsers.opera").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byBrowsers.safari").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byBrowsers.others").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byOs.windows").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byOs.macOs").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byOs.linux").value(dto.getByOs().getLinux()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byOs.android").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byOs.ios").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byOs.others").value(0))
                .andReturn();
    }

    @Test
    public void should_redirectUrl_whenUrlIsReturned() throws Exception {
        //Given
        ShortUrl shortUrl = initShortUrl();
        final String linkDTOJson = getShortUrl.write(shortUrl).getJson();
        given(shortUrlService.resolve(any(ResolveLinkDto.class))).willReturn(shortUrl);

        //When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/Y3dwP");

        //Then
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(shortUrl.getLongUrl()))
                .andReturn();
    }

    private VisitStateDto initVisitStateDto() {
        VisitStateDto dto = new VisitStateDto();
        dto.setLastAccessDate(LocalDate.now().minusDays(12));
        dto.setTotalPerYear(12L);
        dto.setDailyAverage(3.0);
        dto.setMessage("analytics");
        dto.setCode(BaseResponse.SUCCESSFUL);
        dto.setMin(10L);
        dto.setMax(29L);

        BrowserStats browserStats = new BrowserStats();
        browserStats.setChrome(19L);
        dto.setByBrowsers(browserStats);

        OsStat osStat = new OsStat();
        osStat.setLinux(12L);
        dto.setByOs(osStat);

        Map<String, Long> permonth = new HashMap<>();
        permonth.put("June", 0L);
        permonth.put("December", 0L);
        permonth.put("May", 0L);
        permonth.put("September", 0L);
        permonth.put("March", 0L);
        permonth.put("July", 0L);
        permonth.put("January", 0L);
        permonth.put("February", 0L);
        permonth.put("April", 0L);
        permonth.put("October", 0L);
        permonth.put("August", 0L);
        permonth.put("November", 0L);


        dto.setPerMonth(permonth);
        return dto;
    }

    private ShortUrl initShortUrl() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl("http://www.google.com");
        shortUrl.setCreatedDate(LocalDate.now());
        Stats stats = new Stats();

        BrowserStats browserStats = new BrowserStats();
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

        stats.getDateStats().addAll(Arrays.asList(dateStat1, dateStat2));

        shortUrl.setKey(1L);
        shortUrl.setStats(stats);

        return shortUrl;
    }
}
