package ru.android73.geekstagram.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.mvp.model.cache.ImageCache;
import ru.android73.geekstagram.mvp.model.cache.PaperImageCache;
import ru.android73.geekstagram.mvp.model.cache.RealmImageCache;


@Module
public class CacheModule {

    @Named("Paper")
    @Provides
    public ImageCache paperImageCache() {
        return new PaperImageCache();
    }

    @Named("Realm")
    @Provides
    public ImageCache realmImageCache() {
        return new RealmImageCache();
    }
}
