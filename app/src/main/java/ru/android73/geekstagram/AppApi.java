package ru.android73.geekstagram;

import android.arch.persistence.room.Room;
import android.content.Context;

import ru.android73.geekstagram.model.db.GeekstagramDatabase;

public class AppApi {
    private static final String DATABASE_NAME = "GeekstagramDatabase";

    private static volatile AppApi instance;
    private GeekstagramDatabase database;

    public static AppApi getInstance() {
        AppApi localInstance = instance;
        if (localInstance == null) {
            synchronized (AppApi.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AppApi();
                }
            }
        }
        return localInstance;
    }

    public void initDb(Context context) {
        database =  Room.databaseBuilder(context, GeekstagramDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()  //TODO delete it
                .build();
    }

    public GeekstagramDatabase getDatabase() {
        return database;
    }
}
