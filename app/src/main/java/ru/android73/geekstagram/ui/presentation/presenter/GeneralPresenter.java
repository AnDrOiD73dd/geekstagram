package ru.android73.geekstagram.ui.presentation.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.model.ImageListItem;
import ru.android73.geekstagram.ui.presentation.view.GeneralView;

import static android.app.Activity.RESULT_OK;
import static ru.android73.geekstagram.ui.fragment.GeneralFragment.REQUEST_IMAGE_CAPTURE;

@InjectViewState
public class GeneralPresenter extends MvpPresenter<GeneralView> {

    private static final String IMAGE_SUFFIX = ".jpg";

    public void onFabClick() {
        getViewState().requestWriteExternalPermission();
    }

    public void onListItemClick(View view, int position) {

    }

    public void onLongItemClick(View view, int position) {
    }

    public void onRequestPermissionResult(String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            switch (permission) {
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getViewState().openCamera();
                    } else {
                        getViewState().showInfo(R.string.notification_can_not_open_camera);
                    }
                    break;
                default:
                    Logger.e("Unknown permission=" + permission);
                    break;
            }
        }
    }

    public void handleActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            // TODO check NPE
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // TODO check NPE
            getViewState().addItemToList(new ImageListItem(getImageUri(context, imageBitmap), false));
            getViewState().notifyDataChanged();
            getViewState().showInfo(R.string.notification_image_added_text);
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                getNewFileName(), null);
        return Uri.parse(path);
    }

    private String getNewFileName() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.getDefault()).format(new Date());
        return timeStamp + IMAGE_SUFFIX;
    }
}
