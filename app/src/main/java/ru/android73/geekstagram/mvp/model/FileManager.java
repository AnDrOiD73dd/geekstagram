package ru.android73.geekstagram.mvp.model;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FileManager {
    Single<File> createPhotoFile(@Nullable String dateFormat, @Nullable String fileSuffix);

    Single<Uri> getPhotoImageUri(String filePath);

    Single<List<String>> getStorageFilesList();

    Completable delete(String filePath);
}
