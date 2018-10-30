package ru.android73.geekstagram.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class has some methods to communicate with shared preference
 */

public class PrefUtils {

    private static final String PREF_NAME = "geek-weather";

    /**
     * Get SharedPreferences instance
     * @return The single SharedPreferences instance that can be used to retrieve and modify
     * the preference values.
     */
    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Get SharedPreferences editor
     * @return Returns a new instance of the SharedPreferences.Editor interface,
     * allowing you to modify the values in this SharedPreferences object.
     */
    public static SharedPreferences.Editor getEditor(Context context) {
        return getPrefs(context).edit();
    }
}

