package ru.android73.geekstagram.mvp.model.entity;


import io.realm.RealmObject;


public class CachedImage extends RealmObject {
    String url;
    String path;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
