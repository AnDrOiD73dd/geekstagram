package ru.android73.geekstagram.model;

import android.net.Uri;

public class ImageListItem {

    private Uri imageUri;
    private boolean favorite;

    public ImageListItem(Uri imageUri, boolean favorite) {
        this.imageUri = imageUri;
        this.favorite = favorite;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
