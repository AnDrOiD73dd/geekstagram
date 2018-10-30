package ru.android73.geekstagram.mvp.model.photoloader;

import java.util.List;

import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.entity.PhotoEntity;


public interface PhotoLoader {
    Single<List<PhotoEntity>> getPhotos(String accessKey);
}
