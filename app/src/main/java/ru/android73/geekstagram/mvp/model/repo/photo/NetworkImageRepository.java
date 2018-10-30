package ru.android73.geekstagram.mvp.model.repo.photo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.mvp.model.NetworkStatus;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;
import ru.android73.geekstagram.mvp.model.entity.DataType;
import ru.android73.geekstagram.mvp.model.cache.ImageCache;
import ru.android73.geekstagram.mvp.model.entity.PhotoEntity;
import ru.android73.geekstagram.mvp.model.photoloader.PhotoLoader;


public class NetworkImageRepository implements ImageRepository {

    public static final String ACCESS_KEY = "a92470b1cabfcb5f8b025017a71a9176a9e7046a2af19b5c38b205702aa3172b";
    public static final String SECRET_KEY = "275899fadbdd227a56c67cab75a774b8f577b257ab263d6cfd2e8b5fb5ed74c0";

    private final transient PhotoLoader photoLoader;
    private final transient ImageCache cache;

    public NetworkImageRepository(PhotoLoader photoLoader, ImageCache imageCache) {
        this.photoLoader = photoLoader;
        this.cache = imageCache;
    }

    @Override
    public Single<List<ImageListItem>> getPhotos() {
        if (NetworkStatus.isOnline()) {
            return Single.create(emitter -> photoLoader.getPhotos(ACCESS_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(photoEntities -> {
                        List<ImageListItem> imagesList = new ArrayList<>();
                        for (PhotoEntity photoEntity : photoEntities) {
                            ImageListItem item = new ImageListItem(photoEntity.getUrls().getRegular(), false, DataType.REMOTE);
                            imagesList.add(item);
                        }
                        cache.putImageList(imagesList);
                        emitter.onSuccess(imagesList);
                    }, emitter::onError));
        } else {
            return Single.create(emitter -> emitter.onSuccess(cache.getImageList()));
        }
    }

    @Override
    public Single<ImageListItem> add(ImageListItem item) {
        return Single.just(null);
    }

    @Override
    public Completable remove(ImageListItem item) {
        return Completable.complete();
    }

    @Override
    public Completable update(ImageListItem item) {
        return Completable.complete();
    }
}
