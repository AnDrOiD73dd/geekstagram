package ru.android73.geekstagram.common;

import ru.android73.geekstagram.model.db.ImageListItem;

public interface ImageRepositoryCallback {

    void onLoadComplete();

    void onAdded(ImageListItem item);

    void onUpdated(ImageListItem item);

    void onDeleted(int index, ImageListItem item);
}
