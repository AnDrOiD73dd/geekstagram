package ru.android73.geekstagram.mvp.model.theme;

import ru.android73.geekstagram.R;

public class ThemeMapperEnumResource {

    public int toResourceId(AppTheme appTheme) {
        switch (appTheme) {
            case GRAY:
                return R.style.DarkTheme;
            case BLUE:
            default:
                return R.style.DefaultTheme;
        }
    }

    public AppTheme toEnum(int themeId) {
        switch (themeId) {
            default:
            case R.style.DefaultTheme:
                return AppTheme.BLUE;
            case R.style.DarkTheme:
                return AppTheme.GRAY;
        }
    }
}
