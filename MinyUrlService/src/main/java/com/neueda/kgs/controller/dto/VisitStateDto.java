package com.neueda.kgs.controller.dto;

import com.neueda.kgs.model.embedded.BrowserStats;
import com.neueda.kgs.model.embedded.OsStat;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VisitStateDto extends BaseResponse {

    private LocalDate lastAccessDate;
    private Double dailyAverage;
    private Long max;
    private Long min;
    private Long totalPerYear;
    private Map<String, Long> perMonth;
    private BrowserStats byBrowsers;
    private OsStat byOs;


    public Map<String, Long> getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(Map<String, Long> perMonth) {
        this.perMonth = perMonth;
    }


    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Double getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(Double dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public Long getTotalPerYear() {
        return totalPerYear;
    }

    public void setTotalPerYear(Long totalPerYear) {
        this.totalPerYear = totalPerYear;
    }

    public BrowserStats getByBrowsers() {
        return byBrowsers;
    }

    public void setByBrowsers(BrowserStats byBrowsers) {
        this.byBrowsers = byBrowsers;
    }

    public OsStat getByOs() {
        return byOs;
    }

    public void setByOs(OsStat byOs) {
        this.byOs = byOs;
    }

    public LocalDate getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDate lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }
}
