package com.example.appstronomyv2.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appstronomyv2.data.dao.UserPreferenceDao;
import com.example.appstronomyv2.data.model.UserPreference;

@Database(entities = {UserPreference.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserPreferenceDao userPreferenceDao();
}
