package ru.android73.geekstagram.ui.presentation.view;

import android.net.Uri;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

import ru.android73.geekstagram.model.db.ImageListItem;

public interface ImagesListView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInfo(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void openCamera(File imageFile);

    @StateStrategyType(SkipStrategy.class)
    void openCamera(Uri imageUri);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void addItemToList(ImageListItem item);

    @StateStrategyType(SkipStrategy.class)
    void showDeleteConfirmationDialog(int adapterPosition);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void removeItem(int adapterPosition);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void revertItemLike(int adapterPosition);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void createImageFile();
}
