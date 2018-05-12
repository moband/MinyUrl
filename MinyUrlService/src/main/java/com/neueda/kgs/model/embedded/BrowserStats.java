package com.neueda.kgs.model.embedded;

import java.io.Serializable;

public class BrowserStats implements Serializable {
    private Long ie=0L;
    private Long fireFox=0L;
    private Long chrome=0L;
    private Long opera=0L;
    private Long safari=0L;
    private Long others=0L;

    public Long getIe() {
        return ie;
    }

    public void incrementIe() {this.ie++;}
    public void incrementFireFox() {this.fireFox++;}
    public void incrementChrome() {this.chrome++;}
    public void incrementOpera() {this.opera++;}
    public void incrementSafari() {this.safari++;}
    public void incrementOthers() {this.others++;}

    public void setIe(Long ie) {
        this.ie = ie;
    }

    public Long getFireFox() {
        return fireFox;
    }

    public void setFireFox(Long fireFox) {
        this.fireFox = fireFox;
    }

    public Long getChrome() {
        return chrome;
    }

    public void setChrome(Long chrome) {
        this.chrome = chrome;
    }

    public Long getOpera() {
        return opera;
    }

    public void setOpera(Long opera) {
        this.opera = opera;
    }

    public Long getSafari() {
        return safari;
    }

    public void setSafari(Long safari) {
        this.safari = safari;
    }

    public Long getOthers() {
        return others;
    }

    public void setOthers(Long others) {
        this.others = others;
    }
}
