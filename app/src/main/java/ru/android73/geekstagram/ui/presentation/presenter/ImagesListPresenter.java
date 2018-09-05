package ru.android73.geekstagram.ui.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import ru.android73.geekstagram.AppApi;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.model.db.ImageListItem;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> {

    private String lastPhotoPath;

    public void onTakePhotoSuccess() {
        getViewState().addItemToList(new ImageListItem(lastPhotoPath, false));
        getViewState().showInfo(R.string.notification_image_added_text);
    }

    public void onImageClick(int adapterPosition) {
        getViewState().showImageViewer(adapterPosition);
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
        File imageFile = AppApi.getInstance().getFileManager().createPhotoFile(null, null);
        if (imageFile == null) {
            getViewState().showInfo(R.string.notification_can_not_create_file);
        }
        else {
            lastPhotoPath = imageFile.getAbsolutePath();
            String imageUri = AppApi.getInstance().getFileManager().getPhotoImageUri(imageFile).toString();
            getViewState().openCamera(imageUri);
        }
    }
}
