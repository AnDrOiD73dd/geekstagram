package ru.android73.geekstagram.common.theme;

import ru.android73.geekstagram.R;

public class ThemeMapperEnumResource {

    public int toResourceId(AppTheme appTheme) {
        switch (appTheme) {
            case BLUE:
                return R.style.DefaultTheme;
            case GRAY:
                return R.style.DarkTheme;
            default:
                return R.style.DefaultTheme;
        }
    }
}
