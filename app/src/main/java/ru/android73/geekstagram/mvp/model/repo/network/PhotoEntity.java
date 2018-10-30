package ru.android73.geekstagram.mvp.model.repo.network;

import com.google.gson.annotations.SerializedName;

public class PhotoEntity {

    @SerializedName("id")
    private final String id;

    @SerializedName("urls")
    private final PhotoUrlsEntity urls;

    public PhotoEntity(String id, PhotoUrlsEntity urls) {
        this.id = id;
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public PhotoUrlsEntity getUrls() {
        return urls;
    }
}
