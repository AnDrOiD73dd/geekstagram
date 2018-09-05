package ru.android73.geekstagram.common;

import ru.android73.geekstagram.R;

public class AppThemeMapper {

    public int toResourceId(AppTheme appTheme) {
        if (appTheme.equals(AppTheme.BLUE)) {
            return R.style.DefaultTheme;
        }
        else if (appTheme.equals(AppTheme.GRAY)) {
            return R.style.DarkTheme;
        }
        else {
            return -1;
        }

    }

    public AppTheme toEnumValue(String appThemeName) {
        try {
            return AppTheme.valueOf(appThemeName);
        } catch (Exception ex) {
            return AppTheme.BLUE;
        }
    }
}
