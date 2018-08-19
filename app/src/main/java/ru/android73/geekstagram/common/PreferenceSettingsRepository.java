package ru.android73.geekstagram.common;

import android.content.Context;
import android.content.SharedPreferences;

import ru.android73.geekstagram.R;

public class PreferenceSettingsRepository implements SettingsRepository {

    public final String KEY_APP_THEME = "kat";

    @Override
    public void saveTheme(Context context, int themeId) {
        PrefUtils.getEditor(context)
                .putInt(KEY_APP_THEME, themeId)
                .apply();
    }

    @Override
    public int getCurrentTheme(Context context) {
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        return prefs.getInt(KEY_APP_THEME, R.style.DefaultTheme);
    }
}
