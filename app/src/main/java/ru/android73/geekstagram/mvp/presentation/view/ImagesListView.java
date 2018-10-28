package ru.android73.geekstagram.mvp.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public interface ImagesListView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updatePhotosList();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onItemAdded(int position);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onItemUpdated(int position);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onItemDeleted(int position);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInfo(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void openCamera(String imagePath);

    @StateStrategyType(SkipStrategy.class)
    void showDeleteConfirmationDialog(ImageListItem item, int adapterPosition);

    @StateStrategyType(SkipStrategy.class)
    void showImageViewer(String imagePath);
}
