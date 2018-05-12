package com.neueda.kgs.model.embedded;

import org.springframework.data.annotation.Version;

public class AllocatedCounter {

    private Integer rangeNumber;
    private Long counter;
    private Boolean isExhausted = false;
    @Version
    private Integer version;

    public Integer getRangeNumber() {
        return rangeNumber;
    }

    public void setRangeNumber(Integer rangeNumber) {
        this.rangeNumber = rangeNumber;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Boolean getExhausted() {
        return isExhausted;
    }

    public void setExhausted(Boolean exhausted) {
        isExhausted = exhausted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void incrementCounter(){
        ++this.counter;
    }

}
