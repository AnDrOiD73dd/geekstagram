package ru.android73.geekstagram.di.modules;

import org.mockito.Mockito;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.mvp.model.repo.photo.ImageRepository;

@Module
public class TestRepoModule {

    @Named("Realm")
    @Provides
    public ImageRepository realmImageRepository() {
        return Mockito.mock(ImageRepository.class);
    }
}
