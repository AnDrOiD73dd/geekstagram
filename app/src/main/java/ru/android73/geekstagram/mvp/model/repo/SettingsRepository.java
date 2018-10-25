package ru.android73.geekstagram.mvp.model.repo;

import ru.android73.geekstagram.mvp.model.theme.AppTheme;

public interface SettingsRepository {
    void saveTheme(AppTheme appTheme);

    AppTheme getTheme();
}
