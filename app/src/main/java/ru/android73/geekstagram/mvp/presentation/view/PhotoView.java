package ru.android73.geekstagram.mvp.presentation.view;

import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public interface PhotoView {
    void setPhoto(ImageListItem filePath);

    void setFavorite(boolean flag);

    void setDeleteIcon();
}
