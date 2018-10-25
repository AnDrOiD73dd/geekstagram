package ru.android73.geekstagram.ui.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.common.SettingsRepository;
import ru.android73.geekstagram.common.theme.AppTheme;
import ru.android73.geekstagram.common.theme.ThemeMapperEnumResource;
import ru.android73.geekstagram.common.theme.ThemeMapperEnumString;

public abstract class BaseActivity extends MvpAppCompatActivity {

    private final ThemeMapperEnumResource themeMapperEnumResource;
    private final ThemeMapperEnumString themeMapperEnumString;
    protected int currentThemeId;

    public BaseActivity() {
        themeMapperEnumResource = new ThemeMapperEnumResource();
        themeMapperEnumString = new ThemeMapperEnumString();
    }

    @Override
    public void setTheme(int resid) {
        currentThemeId = getUserTheme();
        super.setTheme(currentThemeId);
    }

    private int getUserTheme() {
        SettingsRepository preferences = new PreferenceSettingsRepository(getApplicationContext(),
                themeMapperEnumString);
        AppTheme themeName = preferences.getTheme();
        return themeMapperEnumResource.toResourceId(themeName);
    }
}
