package ru.android73.geekstagram.mvp.presentation.view;

import ru.android73.geekstagram.mvp.model.entity.ImageListItem;
import ru.android73.geekstagram.mvp.model.cache.ImageCache;


public interface PhotoView {
    void setPhoto(ImageListItem filePath, ImageCache imageCache);

    void setFavorite(boolean flag);

    void setDeleteIcon();
}
