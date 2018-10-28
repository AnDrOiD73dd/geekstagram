package ru.android73.geekstagram.mvp.model.repo;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FileRepository {

    Single<List<String>> getPhotos();

    Single<File> createFile();

    Completable deleteFile(String filePath);
}
