package com.example.appstronomyv2.api;

public class ApodResponse {
    private String date;
    private String explanation;
    private String title;
    private String url;
    private String media_type;
    private String hdurl;

    // Getters y setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaType() {
        return media_type;
    }

    public void setMediaType(String media_type) {
        this.media_type = media_type;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getHdurl() {
        return hdurl;
    }

}

