package ru.android73.geekstagram.ui.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.android73.geekstagram.common.AppTheme;
import ru.android73.geekstagram.common.AppThemeMapper;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.common.SettingsRepository;

public class BaseActivity extends MvpAppCompatActivity {

    private final AppThemeMapper themeMapper;
    protected int currentThemeId;

    public BaseActivity() {
        themeMapper = new AppThemeMapper();
    }

    @Override
    public void setTheme(int resid) {
        currentThemeId = getUserTheme();
        super.setTheme(currentThemeId);
    }

    private int getUserTheme() {
        SettingsRepository preferences = new PreferenceSettingsRepository(new AppThemeMapper());
        AppTheme themeName = preferences.getTheme(this);
        return themeMapper.toResourceId(themeName);
    }
}
