package ru.android73.geekstagram.ui.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.AppTheme;
import ru.android73.geekstagram.common.SettingsRepository;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    private final SettingsRepository preferences;

    public SettingsPresenter(SettingsRepository preferences) {
        this.preferences = preferences;
    }

    public void onThemeSelected(int newThemeId, int currentThemeId) {
        if (currentThemeId == newThemeId) {
            return;
        }
        if (newThemeId == R.style.DefaultTheme) {
            preferences.saveTheme(AppTheme.BLUE);
        } else if (newThemeId == R.style.DarkTheme) {
            preferences.saveTheme(AppTheme.GRAY);
        } else {
            Logger.w("Unknown theme id=", newThemeId);
            return;
        }
        getViewState().applyTheme(newThemeId);
    }
}
