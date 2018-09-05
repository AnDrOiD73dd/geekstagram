package ru.android73.geekstagram.common;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;

public class FileManager {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    private static final String DEFAULT_IMAGE_SUFFIX = ".jpg";
    private final Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    public File createPhotoFile(@Nullable String dateFormat, @Nullable String fileSuffix) {
        String datePattern = dateFormat == null ? DEFAULT_DATE_FORMAT : dateFormat;
        String imageSuffix = fileSuffix == null ? DEFAULT_IMAGE_SUFFIX : fileSuffix;
        String timeStamp = new SimpleDateFormat(datePattern, Locale.getDefault()).format(new Date());
        return createTempFileInPicturesDirectory(timeStamp, imageSuffix);
    }

    public File createTempFileInPicturesDirectory(@NonNull String filePrefix, @NonNull String fileSuffix) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return createTempFile(filePrefix, fileSuffix, storageDir);
    }

    public File createTempFile(@NonNull String filePrefix, @NonNull String fileSuffix,
                               @NonNull File directory) {
        File image = null;
        try {
            image = File.createTempFile(filePrefix, fileSuffix, directory);
        } catch (IOException e) {
            Logger.e(e);
        }
        return image;
    }

    public Uri getPhotoImageUri(File imageFile) {
        return FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority),
                imageFile);
    }
}
