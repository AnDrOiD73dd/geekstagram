package ru.android73.geekstagram.common.theme;

public class ThemeMapperEnumString {

    private static final String DEFAULT_THEME = "default theme";
    private static final String GRAY_THEME = "gray theme";

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
        if (themeName.equals(AppTheme.GRAY)) {
            return GRAY_THEME;
        } else {
            return DEFAULT_THEME;
        }
    }
}
