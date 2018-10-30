package ru.android73.geekstagram.di.modules;

import org.mockito.Mockito;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.mvp.model.cache.ImageCache;

@Module
public class TestCacheModule {

    @Named("Realm")
    @Provides
    public ImageCache imageCache() {
        return Mockito.mock(ImageCache.class);
    }
}
