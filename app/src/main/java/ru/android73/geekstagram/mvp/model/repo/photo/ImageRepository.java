package ru.android73.geekstagram.mvp.model.repo.photo;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;

public interface ImageRepository extends Serializable {

    Single<List<ImageListItem>> getPhotos();

    Single<ImageListItem> add(ImageListItem item);

    Completable remove(ImageListItem item);

    Completable update(ImageListItem item);
}
