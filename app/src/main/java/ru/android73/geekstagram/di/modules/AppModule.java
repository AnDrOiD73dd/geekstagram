package ru.android73.geekstagram.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.GeekstagramApp;

@Module
public class AppModule {
    private GeekstagramApp app;

    public AppModule(GeekstagramApp app) {
        this.app = app;
    }

    @Provides
    public GeekstagramApp app() {
        return app;
    }
}
