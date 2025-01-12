package com.example.appstronomyv2.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class SavedApod {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String explanation;
    private String url;
    private String hdurl;
    private String media_type;
    private String date;
    private String user_email;
    private String itemId;



    //getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "SavedApod{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", explanation='" + explanation + '\'' +
                ", url='" + url + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", media_type='" + media_type + '\'' +
                ", date='" + date + '\'' +
                ", user_email='" + user_email + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }



}
