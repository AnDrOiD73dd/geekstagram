package ru.android73.geekstagram.mvp.model.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class CachedImageList extends RealmObject {

    RealmList<RealmImageListItem> imageListItems;

    public RealmList<RealmImageListItem> getImageListItems() {
        return imageListItems;
    }

    public void setImageListItems(List<RealmImageListItem> imageListItems) {
        this.imageListItems = new RealmList<>();
        this.imageListItems.addAll(imageListItems);
    }
}
