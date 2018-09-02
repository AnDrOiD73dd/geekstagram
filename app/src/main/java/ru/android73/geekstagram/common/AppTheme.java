package ru.android73.geekstagram.common;

public enum AppTheme {
    BLUE,
    GRAY;

    public static AppTheme toEnum (String appThemeName) {
        try {
            return valueOf(appThemeName);
        } catch (Exception ex) {
            return BLUE;
        }
    }
}
