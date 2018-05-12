package com.neueda.kgs.model.embedded;

import java.io.Serializable;

public class OsStat implements Serializable {
    private Long windows=0L;
    private Long macOs=0L;
    private Long linux=0L;
    private Long android=0L;
    private Long ios=0L;
    private Long others=0L;


    public void incrementWindows() {this.windows++;}
    public void incrementMacOs() {this.macOs++;}
    public void incrementLinux() {this.linux++;}
    public void incrementAndroid() {this.android++;}
    public void incrementIos() {this.ios++;}
    public void incrementOthers() {this.others++;}


    public Long getWindows() {
        return windows;
    }

    public void setWindows(Long windows) {
        this.windows = windows;
    }

    public Long getMacOs() {
        return macOs;
    }

    public void setMacOs(Long macOs) {
        this.macOs = macOs;
    }

    public Long getLinux() {
        return linux;
    }

    public void setLinux(Long linux) {
        this.linux = linux;
    }

    public Long getAndroid() {
        return android;
    }

    public void setAndroid(Long android) {
        this.android = android;
    }

    public Long getIos() {
        return ios;
    }

    public void setIos(Long ios) {
        this.ios = ios;
    }

    public Long getOthers() {
        return others;
    }

    public void setOthers(Long others) {
        this.others = others;
    }
}
