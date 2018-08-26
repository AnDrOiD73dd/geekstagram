package ru.android73.geekstagram.ui.presentation.presenter;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.view.GeneralView;

@InjectViewState
public class GeneralPresenter extends MvpPresenter<GeneralView> {

    public void onFabClick() {
        getViewState().showInfo(R.string.notification_image_added_text);
    }

    public void onListItemClick(View view, int position) {

    }

    public void onLongItemClick(View view, int position) {
    }
}
