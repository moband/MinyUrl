package com.neueda.kgs.model;

import com.neueda.kgs.model.embedded.Stats;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Document(collection = "shorturl")
public class ShortUrl implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    private String id;
    @Indexed
    private Long keyCode;
    private LocalDate createdDate;
    private LocalDate lastAccessDate;
    @Indexed
    private String longUrl;
    private Stats stats;


    public ShortUrl() {
    }

    public ShortUrl(Long keyCode) {
        this.keyCode = keyCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Long getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(Long keyCode) {
        this.keyCode = keyCode;
    }

    public LocalDate getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDate lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }
}
