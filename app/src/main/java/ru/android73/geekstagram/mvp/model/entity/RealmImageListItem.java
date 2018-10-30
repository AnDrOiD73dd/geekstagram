package ru.android73.geekstagram.mvp.model.entity;

import io.realm.RealmObject;


public class RealmImageListItem extends RealmObject {

    protected String imagePath;
    protected boolean favorite;
    protected int dataType;

    public RealmImageListItem() {
    }

    public RealmImageListItem(String imagePath, boolean favorite, int dataType) {
        this.imagePath = imagePath;
        this.favorite = favorite;
        this.dataType = dataType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public DataType getDataType() {
        return DataTypeConverter.toDataType(this.dataType);
    }

    public void setDataType(DataType dataType) {
        this.dataType = DataTypeConverter.toInteger(dataType);
    }

    public ImageListItem map() {
        return new ImageListItem(imagePath, favorite, getDataType());
    }

    public static RealmImageListItem map(ImageListItem imageListItem) {
        return new RealmImageListItem(imageListItem.getImagePath(), imageListItem.isFavorite(),
                DataTypeConverter.toInteger(imageListItem.getDataType()));
    }
}
