package ru.android73.geekstagram.ui.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import ru.android73.geekstagram.model.ImageListItem;

public interface GeneralView extends MvpView {

    void showInfo(@StringRes int resId);

    void openCamera();

    void requestWriteExternalPermission();

    void addItemToList(ImageListItem item);

    void notifyDataChanged();
}
