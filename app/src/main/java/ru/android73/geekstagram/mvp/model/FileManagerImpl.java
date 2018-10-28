package ru.android73.geekstagram.mvp.model;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;

public class FileManagerImpl implements FileManager {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    private static final String DEFAULT_IMAGE_SUFFIX = ".jpg";
    private final Context context;

    public FileManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<File> createPhotoFile(@Nullable String dateFormat, @Nullable String fileSuffix) {
        return Single.create(emitter -> {
            String datePattern = dateFormat == null ? DEFAULT_DATE_FORMAT : dateFormat;
            String imageSuffix = fileSuffix == null ? DEFAULT_IMAGE_SUFFIX : fileSuffix;
            String timeStamp = new SimpleDateFormat(datePattern, Locale.getDefault()).format(new Date());
            emitter.onSuccess(createTempFileInPicturesDirectory(timeStamp, imageSuffix));
        });
    }

    private File createTempFileInPicturesDirectory(@NonNull String filePrefix, @NonNull String fileSuffix) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return createTempFile(filePrefix, fileSuffix, storageDir);
    }

    private File createTempFile(@NonNull String filePrefix, @NonNull String fileSuffix,
                                @NonNull File directory) {
        File image = null;
        try {
            image = File.createTempFile(filePrefix, fileSuffix, directory);
        } catch (IOException e) {
            Logger.e(e);
        }
        return image;
    }

    @Override
    public Single<Uri> getPhotoImageUri(String filePath) {
        return Single.create(emitter -> {
            File imageFile = new File(filePath);
            Uri uri = FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority),
                    imageFile);
            emitter.onSuccess(uri);
        });
    }

    @Override
    public Single<List<String>> getStorageFilesList() {
        return Single.create(emitter -> {
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String[] filesName = storageDir.list();
            List<String> filesList = new ArrayList<>();
            for (String fileName : filesName) {
                File temp = new File(storageDir, fileName);
                filesList.add(temp.getAbsolutePath());
            }
            emitter.onSuccess(filesList);
        });
    }

    @Override
    public Completable delete(String filePath) {
        return Completable.create(emitter -> {
            File file = new File(filePath);
            if (file.delete()) {
                emitter.onComplete();
            } else {
                emitter.onError(new RuntimeException("Could not delete file"));
            }
        });
    }
}
