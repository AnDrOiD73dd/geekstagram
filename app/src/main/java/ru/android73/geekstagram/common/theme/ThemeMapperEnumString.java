package ru.android73.geekstagram.common.theme;

public class ThemeMapperEnumString {

    private static final String DEFAULT_THEME = "DefaultTheme";
    private static final String GRAY_THEME = "GrayTheme";

    public AppTheme toEnum(String themeName) {
        switch (themeName) {
            case DEFAULT_THEME:
                return AppTheme.BLUE;
            case GRAY_THEME:
                return AppTheme.GRAY;
            default:
                return AppTheme.BLUE;
        }
    }

    public String toString(AppTheme themeName) {
        switch (themeName) {
            case BLUE:
                return DEFAULT_THEME;
            case GRAY:
                return GRAY_THEME;
            default:
                return DEFAULT_THEME;
        }
    }
}
