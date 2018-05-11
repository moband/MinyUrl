package com.neueda.kgs.controller.dto;

import java.util.HashMap;
import java.util.Map;

public class MonthlyVisitsReport {
    private Map<String, Long> perMonth = new HashMap<>();

    public Map<String, Long> getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(Map<String, Long> perMonth) {
        this.perMonth = perMonth;
    }
}
