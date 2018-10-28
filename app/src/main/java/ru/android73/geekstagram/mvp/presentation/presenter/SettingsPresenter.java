package ru.android73.geekstagram.mvp.presentation.presenter;


import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
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
    public void onThemeSelected(AppTheme newTheme, AppTheme currentTheme) {
        if (currentTheme == newTheme) {
            return;
        }
        preferences.saveTheme(newTheme)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(() -> getViewState().applyTheme(newTheme), Logger::e);
    }
}
