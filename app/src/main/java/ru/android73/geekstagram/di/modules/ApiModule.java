package ru.android73.geekstagram.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.FileManagerImpl;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumResource;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumString;

@Module(includes = ContextModule.class)
public class ApiModule {

    @Provides
    public FileManager fileManager(Context context) {
        return new FileManagerImpl(context);
    }

    @Provides
    public ThemeMapperEnumString themeMapperEnumString() {
        return new ThemeMapperEnumString();
    }

    @Provides
    public ThemeMapperEnumResource themeMapperEnumResource() {
        return new ThemeMapperEnumResource();
    }
}
