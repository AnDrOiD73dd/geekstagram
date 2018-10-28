package ru.android73.geekstagram.mvp.presentation.view;

import com.arellomobile.mvp.MvpView;

import ru.android73.geekstagram.mvp.model.theme.AppTheme;

public interface SettingsView extends MvpView {

    void setCheckedStandardTheme();

    void setCheckedDarkTheme();

    void applyTheme(AppTheme themeId);
}
