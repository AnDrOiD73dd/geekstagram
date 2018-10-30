package ru.android73.geekstagram.mvp.model.repo;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.PrefUtils;
import ru.android73.geekstagram.mvp.model.theme.AppTheme;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumString;

public class ThemeRepositoryImpl implements ThemeRepository {

    private static final String KEY_APP_THEME = "db2414d0-7131-4bb2-bcb0-032ec8d7ae40";

    private final ThemeMapperEnumString themeMapper;
    private final Context context;

    public ThemeRepositoryImpl(Context context, ThemeMapperEnumString themeMapper) {
        this.context = context;
        this.themeMapper = themeMapper;
    }

    @Override
    public Completable saveTheme(AppTheme appTheme) {
        return Completable.create(emitter -> {
            PrefUtils.getEditor(context)
                    .putString(KEY_APP_THEME, themeMapper.toString(appTheme))
                    .apply();
            emitter.onComplete();
        });
    }

    @Override
    public AppTheme getTheme() {
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        String themeName = prefs.getString(KEY_APP_THEME, "");
        return themeMapper.toEnum(themeName);
    }

    @Override
    public Single<AppTheme> getThemeAsync() {
        return Single.create(emitter -> emitter.onSuccess(getTheme()));
    }
}
