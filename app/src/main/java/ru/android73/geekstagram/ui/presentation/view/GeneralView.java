package ru.android73.geekstagram.ui.presentation.view;

import android.net.Uri;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.android73.geekstagram.model.ImageListItem;

public interface GeneralView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showInfo(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void openCamera(Uri imageUri);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addItemToList(ImageListItem item);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void notifyDataChanged();
}
