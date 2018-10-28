package ru.android73.geekstagram.mvp.model.repo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public interface ImageRepository {

    Single<List<ImageListItem>> getPhotos();

    Single<ImageListItem> add(ImageListItem item);

    Completable remove(ImageListItem item);

    Completable update(ImageListItem item);
}
