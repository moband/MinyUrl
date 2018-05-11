package com.neueda.kgs.model.embedded;

import java.util.ArrayList;
import java.util.List;

public class Stats {

    private BrowserStats browserStats;
    private OsStat osStat;
    private List<DateStat> dateStats = new ArrayList<>();

    public BrowserStats getBrowserStats() {
        return browserStats;
    }

    public void setBrowserStats(BrowserStats browserStats) {
        this.browserStats = browserStats;
    }

    public OsStat getOsStat() {
        return osStat;
    }

    public void setOsStat(OsStat osStat) {
        this.osStat = osStat;
    }

    public List<DateStat> getDateStats() {
        return dateStats;
    }

    public void setDateStats(List<DateStat> dateStats) {
        this.dateStats = dateStats;
    }
}
