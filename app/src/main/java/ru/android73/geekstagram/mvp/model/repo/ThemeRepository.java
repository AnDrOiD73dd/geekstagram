package ru.android73.geekstagram.mvp.model.repo;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.theme.AppTheme;

public interface ThemeRepository {
    Completable saveTheme(AppTheme appTheme);

    AppTheme getTheme();

    Single<AppTheme> getThemeAsync();
}
