package ru.android73.geekstagram.ui.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.ui.presentation.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void onMenuClick() {
        getViewState().showSettingsActivity();
    }
}
