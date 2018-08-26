package ru.android73.geekstagram.ui.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.AppTheme;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.common.SettingsRepository;

public class BaseActivity extends MvpAppCompatActivity {

    protected int currentThemeId;

    @Override
    public void setTheme(int resid) {
        currentThemeId = getUserTheme();
        super.setTheme(currentThemeId);
    }

    private int getUserTheme() {
        SettingsRepository preferences = new PreferenceSettingsRepository();
        String themeName = preferences.getTheme(this);
        if (themeName.equals(AppTheme.BLUE.name())) {
            return R.style.DefaultTheme;
        }
        else if (themeName.equals(AppTheme.GRAY.name())) {
            return R.style.DarkTheme;
        }
        else {
            return -1;
        }
    }
}
