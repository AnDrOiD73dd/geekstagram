package ru.android73.geekstagram.common;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

public interface FileManager {
    File createPhotoFile(@Nullable String dateFormat, @Nullable String fileSuffix);

    File createTempFileInPicturesDirectory(@NonNull String filePrefix, @NonNull String fileSuffix);

    File createTempFile(@NonNull String filePrefix, @NonNull String fileSuffix, @NonNull File directory);

    Uri getPhotoImageUri(File imageFile);

    List<String> getStorageFilesList();
}
