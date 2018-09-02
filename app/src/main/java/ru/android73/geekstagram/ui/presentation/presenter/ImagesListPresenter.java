package ru.android73.geekstagram.ui.presentation.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.model.ImageListItem;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> {

    private static final String IMAGE_SUFFIX = ".jpg";
    protected String lastPhotoPath;

    public void onFabClick(Context context) {
        File imageFile = createImageFile(context);
        if (imageFile == null) {
            getViewState().showInfo(R.string.notification_can_not_create_file);
        }
        else {
            Uri imageUri = getImageUri(context, imageFile);
            lastPhotoPath = imageFile.getAbsolutePath();
            getViewState().openCamera(imageUri);
        }
    }

    private Uri getImageUri(Context context, File image) {
        return FileProvider.getUriForFile(context,
                context.getPackageName() + ".photoprovider", image);
    }

    public void onTakePhotoSuccess() {
        getViewState().addItemToList(new ImageListItem(Uri.parse(lastPhotoPath), false));
        getViewState().showInfo(R.string.notification_image_added_text);
    }

    private File createImageFile(Context context) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.getDefault()).format(new Date());
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(timeStamp, IMAGE_SUFFIX, storageDir);
        } catch (IOException e) {
            Logger.e(e);
        }
        return image;
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
    }

    public void onDeleteCanceled() {

    }

    public void onDeleteClick(int adapterPosition) {
        getViewState().showDeleteConfirmationDialog(adapterPosition);
    }
}
