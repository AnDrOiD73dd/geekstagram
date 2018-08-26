package ru.android73.geekstagram.ui.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.android73.geekstagram.common.PreferenceSettingsRepository;

public class BaseActivity extends MvpAppCompatActivity {

    protected int currentThemeId;

    @Override
    public void setTheme(int resid) {
        currentThemeId = getUserTheme();
        super.setTheme(currentThemeId);
    }

    private int getUserTheme() {
        PreferenceSettingsRepository preferences = new PreferenceSettingsRepository();
        return preferences.getCurrentTheme(this);
    }
}
