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
}
