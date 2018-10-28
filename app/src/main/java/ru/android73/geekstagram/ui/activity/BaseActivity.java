package ru.android73.geekstagram.ui.activity;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.android73.geekstagram.mvp.model.repo.ThemeRepository;
import ru.android73.geekstagram.mvp.model.repo.ThemeRepositoryImpl;
import ru.android73.geekstagram.mvp.model.theme.AppTheme;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumResource;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumString;

public abstract class BaseActivity extends MvpAppCompatActivity {

    private final ThemeMapperEnumResource themeMapperEnumResource;
    private final ThemeMapperEnumString themeMapperEnumString;
    protected int currentThemeId;

    public BaseActivity() {
        themeMapperEnumResource = new ThemeMapperEnumResource();
        themeMapperEnumString = new ThemeMapperEnumString();
    }

    @SuppressLint("CheckResult")
    @Override
    public void setTheme(int resid) {
        currentThemeId = themeMapperEnumResource.toResourceId(getUserTheme());
        super.setTheme(currentThemeId);
    }

    private AppTheme getUserTheme() {
        ThemeRepository preferences = new ThemeRepositoryImpl(getApplicationContext(), themeMapperEnumString);
        return preferences.getTheme();
    }
}
