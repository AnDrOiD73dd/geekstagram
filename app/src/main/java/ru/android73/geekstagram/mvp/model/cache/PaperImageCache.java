package ru.android73.geekstagram.mvp.model.cache;

import android.graphics.Bitmap;

import java.util.List;

import ru.android73.geekstagram.mvp.model.entity.ImageListItem;


public class PaperImageCache implements ImageCache {
    @Override
    public void putImageList(List<ImageListItem> imageListItems) {

    }

    @Override
    public List<ImageListItem> getImageList() {
        return null;
    }

    @Override
    public void putImage(String imagePath, Bitmap bitmap) {

    }

    @Override
    public String getImage(String imagePath) {
        return null;
    }
}
