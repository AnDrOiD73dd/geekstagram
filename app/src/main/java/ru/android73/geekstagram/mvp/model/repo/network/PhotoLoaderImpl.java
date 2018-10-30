package ru.android73.geekstagram.mvp.model.repo.network;

import java.util.List;

import io.reactivex.Single;

public class PhotoLoaderImpl implements PhotoLoader {

    private final ApiService api;

    public PhotoLoaderImpl(ApiService api) {
        this.api = api;
    }

    @Override
    public Single<List<PhotoEntity>> getPhotos(String accessKey) {
        return api.getPhotos(accessKey);
    }
}
