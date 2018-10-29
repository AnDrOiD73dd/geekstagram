package ru.android73.geekstagram.mvp.model.repo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public class CombinedImageRepository implements ImageRepository {

    public CombinedImageRepository(FileManager fileManager) {
    }

    @Override
    public Single<List<ImageListItem>> getPhotos() {
        return null;
    }

    @Override
    public Single<ImageListItem> add(ImageListItem item) {
        return null;
    }

    @Override
    public Completable remove(ImageListItem item) {
        return null;
    }

    @Override
    public Completable update(ImageListItem item) {
        return null;
    }
}
