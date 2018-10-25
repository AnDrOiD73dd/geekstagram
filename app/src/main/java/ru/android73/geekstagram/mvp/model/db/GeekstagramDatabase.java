package ru.android73.geekstagram.mvp.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ImageListItem.class}, version = 1, exportSchema = true)
public abstract class GeekstagramDatabase extends RoomDatabase {
    public abstract GeekstagramDao geekstagramDao();
}
