package ru.android73.geekstagram.common;

import android.content.Context;
import android.content.SharedPreferences;

import ru.android73.geekstagram.R;

public class PreferenceSettingsRepository implements SettingsRepository {

    protected static final String KEY_APP_THEME = "kat";

    @Override
    public void saveTheme(Context context, String themeName) {
        // Check theme name is exists in AppName enum
        AppTheme.valueOf(themeName);
        PrefUtils.getEditor(context)
                .putString(KEY_APP_THEME, themeName)
                .apply();
    }

    @Override
    public String getTheme(Context context) {
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        return prefs.getString(KEY_APP_THEME, AppTheme.BLUE.name());
    }
}
