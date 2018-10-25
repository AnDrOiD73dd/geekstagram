package ru.android73.geekstagram.mvp.model.repo;

import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public interface ImageRepositoryCallback {

    void onLoadComplete();

    void onAdded(ImageListItem item);

    void onUpdated(int i, ImageListItem item);

    void onDeleted(int index, ImageListItem item);
}
