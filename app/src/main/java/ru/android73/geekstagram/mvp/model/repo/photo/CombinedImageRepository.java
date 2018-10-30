package ru.android73.geekstagram.mvp.model.repo.photo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;

public class CombinedImageRepository implements ImageRepository {

    private final ImageRepository networkImageRepository;
    private final ImageRepository favoritesOnlyImageRepository;

    public CombinedImageRepository(ImageRepository networkImageRepository, ImageRepository favoritesOnlyImageRepository) {
        this.networkImageRepository = networkImageRepository;
        this.favoritesOnlyImageRepository = favoritesOnlyImageRepository;
    }

    @Override
    public Single<List<ImageListItem>> getPhotos() {
        return Single.create(emitter -> networkImageRepository
                .getPhotos()
                .subscribe(networkImageListItems -> favoritesOnlyImageRepository
                        .getPhotos()
                        .subscribe(favoritesImageListItems -> {
                            List<ImageListItem> imageListItems = new ArrayList<>();
                            imageListItems.addAll(networkImageListItems);
                            imageListItems.addAll(favoritesImageListItems);
                            emitter.onSuccess(imageListItems);
                        }, emitter::onError), emitter::onError));
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
