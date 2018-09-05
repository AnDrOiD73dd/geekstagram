package ru.android73.geekstagram.common;

import android.content.Context;

public interface SettingsRepository {
    void saveTheme(Context context, AppTheme appTheme);
    AppTheme getTheme(Context context);
}
