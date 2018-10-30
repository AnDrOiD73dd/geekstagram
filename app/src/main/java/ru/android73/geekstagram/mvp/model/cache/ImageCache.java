package ru.android73.geekstagram.mvp.model.cache;

import android.graphics.Bitmap;

import java.util.List;

import ru.android73.geekstagram.mvp.model.entity.ImageListItem;


public interface ImageCache {

    void putImageList(List<ImageListItem> imageListItems);

    List<ImageListItem> getImageList();

    void putImage(final String imagePath, Bitmap bitmap);

    String getImage(String imagePath);
}
