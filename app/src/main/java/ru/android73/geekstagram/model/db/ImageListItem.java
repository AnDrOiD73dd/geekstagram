package ru.android73.geekstagram.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"image_uri"}, unique = true)})
public class ImageListItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "image_uri")
    private String imageUri;
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public ImageListItem(@NonNull String  imageUri, boolean favorite) {
        this.imageUri = imageUri;
        this.favorite = favorite;
    }

    @NonNull
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(@NonNull String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
