package ru.android73.geekstagram.ui.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.android73.geekstagram.common.ImageRepository;
import ru.android73.geekstagram.model.db.ImageListItem;

public interface ImagesListView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void loadData(ImageRepository imageRepository);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setData(List<ImageListItem> data);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInfo(@StringRes int resId);

    @StateStrategyType(SkipStrategy.class)
    void openCamera(String imagePath);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onItemAdded(ImageListItem item);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onItemDeleted(int index, ImageListItem item);

    @StateStrategyType(SkipStrategy.class)
    void showDeleteConfirmationDialog(ImageListItem item, int adapterPosition);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateItem(ImageListItem item);

    @StateStrategyType(SkipStrategy.class)
    void showImageViewer(int adapterPosition);
}
