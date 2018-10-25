package ru.android73.geekstagram.common;

import ru.android73.geekstagram.common.theme.AppTheme;

public interface SettingsRepository {
    void saveTheme(AppTheme appTheme);

    AppTheme getTheme();
}
