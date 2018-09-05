package ru.android73.geekstagram.ui.presentation.presenter;


import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.AppTheme;
import ru.android73.geekstagram.common.AppThemeMapper;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    public void onThemeSelected(Context context, int newThemeId, int currentThemeId) {
        if (currentThemeId == newThemeId) {
            return;
        }
        PreferenceSettingsRepository preferences = new PreferenceSettingsRepository(new AppThemeMapper());
        if (newThemeId == R.style.DefaultTheme) {
            preferences.saveTheme(context, AppTheme.BLUE);
        }
        else if (newThemeId == R.style.DarkTheme) {
            preferences.saveTheme(context, AppTheme.GRAY);
        }
        else {
            Logger.w("Unknown theme id=", newThemeId);
            return;
        }
        getViewState().applyTheme(newThemeId);
    }
}
