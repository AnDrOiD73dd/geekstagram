package ru.android73.geekstagram;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import io.paperdb.Paper;
import io.realm.Realm;
import ru.android73.geekstagram.di.AppComponent;
import ru.android73.geekstagram.di.DaggerAppComponent;
import ru.android73.geekstagram.di.modules.AppModule;
import ru.android73.geekstagram.di.modules.ContextModule;


public class GeekstagramApp extends Application {

    private static GeekstagramApp instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AppApi.getInstance().initDb(getApplicationContext());  // TODO -> Dagger
        Paper.init(this);
        Realm.init(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(this))
                .build();
    }

    public static GeekstagramApp getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
