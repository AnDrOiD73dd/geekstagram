package ru.android73.geekstagram.ui.presentation.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.ui.presentation.view.GeneralView;

@InjectViewState
public class GeneralPresenter extends MvpPresenter<GeneralView> {

    public void onFabClick() {
//        getViewState().showInfo(R.string.notification_image_added_text);
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
}
