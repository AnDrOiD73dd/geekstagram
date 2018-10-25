package ru.android73.geekstagram.mvp.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface SettingsView extends MvpView {

    void setCheckedStandardTheme();

    void setCheckedDarkTheme();

    void applyTheme(int themeId);
}
