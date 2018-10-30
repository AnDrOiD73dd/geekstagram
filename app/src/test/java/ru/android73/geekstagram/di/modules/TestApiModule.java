package ru.android73.geekstagram.di.modules;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.di.AppComponent;
import ru.android73.geekstagram.mvp.model.FileManager;

@Module
public class TestApiModule {

    @Provides
    public FileManager fileManager() {
        return Mockito.mock(FileManager.class);
    }

    @Provides
    public AppComponent appComponent() {
        return Mockito.mock(AppComponent.class);
    }
}
