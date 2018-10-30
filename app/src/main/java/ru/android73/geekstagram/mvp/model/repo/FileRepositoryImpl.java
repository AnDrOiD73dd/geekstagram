package ru.android73.geekstagram.mvp.model.repo;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.FileManager;

public class FileRepositoryImpl implements FileRepository {

    private FileManager fileManager;

    public FileRepositoryImpl(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public Single<List<String>> getPhotos() {
        return fileManager.getStorageFilesList();
    }

    @Override
    public Single<File> createFile() {
        return fileManager.createPhotoFile(null, null);
    }

    @Override
    public Completable deleteFile(String filePath) {
        return fileManager.delete(filePath);
    }
}
