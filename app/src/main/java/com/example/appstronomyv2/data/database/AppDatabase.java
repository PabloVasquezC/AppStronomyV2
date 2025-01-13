package com.example.appstronomyv2.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appstronomyv2.data.dao.SavedApodDao;
import com.example.appstronomyv2.data.model.SavedApod;

@Database(entities = {SavedApod.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedApodDao savedApodDao();
}
