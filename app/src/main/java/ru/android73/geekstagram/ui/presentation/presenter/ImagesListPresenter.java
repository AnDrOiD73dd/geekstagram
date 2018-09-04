package ru.android73.geekstagram.ui.presentation.presenter;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.model.db.ImageListItem;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> {

    public void onTakePhotoSuccess(String lastPhotoPath) {
        getViewState().addItemToList(new ImageListItem(lastPhotoPath, false));
        getViewState().showInfo(R.string.notification_image_added_text);
    }

    public void onImageClick(int adapterPosition) {
    }

    public void onLikeClick(int adapterPosition) {
        getViewState().revertItemLike(adapterPosition);
    }

    public void onImageLongClick(int adapterPosition) {
        getViewState().showDeleteConfirmationDialog(adapterPosition);
    }

    public void onDeleteConfirmed(int adapterPosition) {
        getViewState().removeItem(adapterPosition);
        getViewState().showInfo(R.string.notification_image_deleted_text);
    }

    public void onDeleteCanceled() {

    }

    public void onDeleteClick(int adapterPosition) {
        getViewState().showDeleteConfirmationDialog(adapterPosition);
    }

    public void onAddPhotoClick() {
        getViewState().createImageFile();
    }

    public void onFileCreatedFail() {
        getViewState().showInfo(R.string.notification_can_not_create_file);
    }

    public void onFileCreatedSuccess(File imageFile) {
        getViewState().openCamera(imageFile);
    }
}
