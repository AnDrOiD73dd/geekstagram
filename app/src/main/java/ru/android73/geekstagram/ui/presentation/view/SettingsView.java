package ru.android73.geekstagram.ui.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface SettingsView extends MvpView {

    void setCheckedStandardTheme();

    void setCheckedDarkTheme();

    void applyTheme(int themeId);
}
