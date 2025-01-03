package com.example.appstronomyv2.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class UserPreference {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String itemId;
    private boolean liked;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
