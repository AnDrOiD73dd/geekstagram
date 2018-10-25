package ru.android73.geekstagram.mvp.model.theme;

public class ThemeMapperEnumString {

    private static final String DEFAULT_THEME = "DefaultTheme";
    private static final String GRAY_THEME = "GrayTheme";

    public AppTheme toEnum(String themeName) {
        switch (themeName) {
            case GRAY_THEME:
                return AppTheme.GRAY;
            case DEFAULT_THEME:
            default:
                return AppTheme.BLUE;
        }
    }

    public String toString(AppTheme themeName) {
        switch (themeName) {
            case GRAY:
                return GRAY_THEME;
            case BLUE:
            default:
                return DEFAULT_THEME;
        }
    }
}
