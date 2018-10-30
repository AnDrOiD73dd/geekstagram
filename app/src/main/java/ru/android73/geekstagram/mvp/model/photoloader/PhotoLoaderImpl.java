package ru.android73.geekstagram.mvp.model.photoloader;

import java.util.List;

import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.entity.PhotoEntity;


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
