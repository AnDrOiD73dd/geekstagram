package ru.android73.geekstagram.common.theme;

import ru.android73.geekstagram.R;

public class ThemeMapperEnumResource {

    public int toResourceId(AppTheme appTheme) {
        if (appTheme.equals(AppTheme.GRAY)) {
            return R.style.DarkTheme;
        } else {
            return R.style.DefaultTheme;
        }
    }
}
