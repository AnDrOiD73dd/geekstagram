package ru.android73.geekstagram;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class GeekstagramApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AppApi.getInstance().initDb(getApplicationContext());
    }
}
