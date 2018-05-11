package com.neueda.kgs.model.embedded;

public class DateStat {
    private Integer dayOfYear;
    private Integer visits;

    public DateStat() {
    }

    public DateStat(Integer dayOfYear, Integer visits) {
        this.dayOfYear = dayOfYear;
        this.visits = visits;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void incrementVisit(){this.visits++;}
    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}
