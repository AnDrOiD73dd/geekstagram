package ru.android73.geekstagram.common;

public interface SettingsRepository {
    void saveTheme(AppTheme appTheme);

    AppTheme getTheme();
}
