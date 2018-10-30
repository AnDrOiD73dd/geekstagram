package ru.android73.geekstagram.mvp.model.repo.network;

import java.util.List;

import io.reactivex.Single;

public interface PhotoLoader {
    Single<List<PhotoEntity>> getPhotos(String accessKey);
}
