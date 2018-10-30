package ru.android73.geekstagram.di.modules;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.repo.CombinedImageRepository;
import ru.android73.geekstagram.mvp.model.repo.FavoritesOnlyImageRepository;
import ru.android73.geekstagram.mvp.model.repo.ImageRepository;
import ru.android73.geekstagram.mvp.model.repo.NetworkImageRepository;
import ru.android73.geekstagram.mvp.model.repo.SimpleImageRepository;
import ru.android73.geekstagram.mvp.model.repo.ThemeRepository;
import ru.android73.geekstagram.mvp.model.repo.ThemeRepositoryImpl;
import ru.android73.geekstagram.mvp.model.repo.cache.ImageCache;
import ru.android73.geekstagram.mvp.model.repo.network.PhotoLoader;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumString;


@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule {

    @Named("Common")
    @Provides
    public ImageRepository commonImageRepository(FileManager fileManager) {
        return new SimpleImageRepository(fileManager);
    }

    @Named("Favorites")
    @Provides
    public ImageRepository favoritesImageRepository(FileManager fileManager) {
        return new FavoritesOnlyImageRepository(fileManager);
    }

    @Named("Network")
    @Provides
    public ImageRepository networkImageRepository(PhotoLoader photoLoader, @Named("Realm") ImageCache imageCache) {
        return new NetworkImageRepository(photoLoader, imageCache);
    }

    @Named("Combined")
    @Provides
    public ImageRepository combinedImageRepository(@Named("Network") ImageRepository networkImageRepository, @Named("Favorites") ImageRepository favoritesOnlyImageRepository) {
        return new CombinedImageRepository(networkImageRepository, favoritesOnlyImageRepository);
    }

    @Provides
    public ThemeRepository themeRepository(Context context, ThemeMapperEnumString themeMapperEnumString) {
        return new ThemeRepositoryImpl(context, themeMapperEnumString);
    }
}
