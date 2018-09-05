package ru.android73.geekstagram.common;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceSettingsRepository implements SettingsRepository {

    protected static final String KEY_APP_THEME = "db2414d0-7131-4bb2-bcb0-032ec8d7ae40";

    private final AppThemeMapper themeMapper;

    public PreferenceSettingsRepository(AppThemeMapper themeMapper) {
        this.themeMapper = themeMapper;
    }

    @Override
    public void saveTheme(Context context, AppTheme appTheme) {
        PrefUtils.getEditor(context)
                .putString(KEY_APP_THEME, appTheme.toString())
                .apply();
    }

    @Override
    public AppTheme getTheme(Context context) {
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        String themeName = prefs.getString(KEY_APP_THEME, AppTheme.BLUE.name());
        return themeMapper.toEnumValue(themeName);
    }
}
