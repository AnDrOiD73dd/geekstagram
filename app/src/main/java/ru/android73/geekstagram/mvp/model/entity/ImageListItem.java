package ru.android73.geekstagram.mvp.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Objects;


@Entity(indices = {@Index(value = {"image_uri"}, unique = true)})
public class ImageListItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "image_uri")
    protected String imagePath;
    @ColumnInfo(name = "favorite")
    protected boolean favorite;
    @ColumnInfo(name = "type")
    @TypeConverters(DataTypeConverter.class)
    protected DataType dataType;

    public ImageListItem(@NonNull String imagePath, boolean favorite, DataType dataType) {
        this.imagePath = imagePath;
        this.favorite = favorite;
        this.dataType = dataType;
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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageListItem item = (ImageListItem) o;
        return favorite == item.favorite &&
                Objects.equals(imagePath, item.imagePath) &&
                dataType == item.dataType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imagePath, favorite, dataType);
    }
}
