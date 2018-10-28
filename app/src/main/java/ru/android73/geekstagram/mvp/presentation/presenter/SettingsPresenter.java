package ru.android73.geekstagram.mvp.presentation.presenter;


import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.model.repo.ThemeRepository;
import ru.android73.geekstagram.mvp.model.theme.AppTheme;
import ru.android73.geekstagram.mvp.presentation.view.SettingsView;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    private final ThemeRepository preferences;
    private Scheduler scheduler;

    public SettingsPresenter(Scheduler scheduler, ThemeRepository preferences) {
        this.scheduler = scheduler;
        this.preferences = preferences;
    }

    @SuppressLint("CheckResult")
    public void onThemeSelected(int newThemeId, int currentThemeId) {
        if (currentThemeId == 0 || currentThemeId == newThemeId) {
            return;
        }
        if (newThemeId == R.style.DefaultTheme) {
            preferences.saveTheme(AppTheme.BLUE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(scheduler)
                    .subscribe(() -> onThemeSaved(newThemeId), Logger::e);
        } else if (newThemeId == R.style.DarkTheme) {
            preferences.saveTheme(AppTheme.GRAY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(scheduler)
                    .subscribe(() -> onThemeSaved(newThemeId), Logger::e);
        } else {
            Logger.w("Unknown theme id=", newThemeId);
        }
    }

    private void onThemeSaved(int newThemeId) {
        Logger.v("%d", newThemeId);
        getViewState().applyTheme(newThemeId);
    }
}
