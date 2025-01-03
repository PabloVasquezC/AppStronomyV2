package com.example.appstronomyv2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appstronomyv2.data.model.UserPreference;

import java.util.List;

@Dao
public interface UserPreferenceDao {
    @Insert
    void insert(UserPreference userPreference);

    @Query("SELECT * FROM UserPreference")
    List<UserPreference> getAllPreferences();
}
