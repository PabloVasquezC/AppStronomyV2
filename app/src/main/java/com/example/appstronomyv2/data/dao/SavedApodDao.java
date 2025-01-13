package com.example.appstronomyv2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.appstronomyv2.data.model.SavedApod;

import java.util.List;

@Dao
public interface SavedApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedApod savedApod);

    @Query("SELECT * FROM SavedApod")
    List<SavedApod> getAllPreferences();

    @Query("SELECT * FROM SavedApod WHERE user_email = :userEmail")
    List<SavedApod> getApodsByUser(String userEmail);
}
