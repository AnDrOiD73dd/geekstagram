package ru.android73.geekstagram.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(indices = {@Index(value = {"image_uri"}, unique = true)})
public class ImageListItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "image_uri")
    private String imagePath;
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public ImageListItem(@NonNull String  imagePath, boolean favorite) {
        this.imagePath = imagePath;
        this.favorite = favorite;
    }

    @NonNull
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@NonNull String imageUri) {
        this.imagePath = imageUri;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageListItem item = (ImageListItem) o;
        return favorite == item.favorite &&
                Objects.equals(imagePath, item.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imagePath, favorite);
    }
}
