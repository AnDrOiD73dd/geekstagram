package ru.android73.geekstagram.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.mvp.model.repo.cache.ImageCache;
import ru.android73.geekstagram.mvp.model.repo.cache.PaperCache;

@Module
public class CacheModule {

    @Named("paper")
    @Provides
    public ImageCache paperCache(){
        return new PaperCache();
    }
}
