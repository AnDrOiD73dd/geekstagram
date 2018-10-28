package ru.android73.geekstagram.mvp.presentation.view;

public interface PhotoView {
    void setPhoto(String filePath);

    void setFavorite(boolean flag);

    void setDeleteIcon();
}
