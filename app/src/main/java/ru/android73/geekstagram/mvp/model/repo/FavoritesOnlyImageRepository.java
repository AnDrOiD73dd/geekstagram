package ru.android73.geekstagram.mvp.model.repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public class FavoritesOnlyImageRepository extends SimpleImageRepository {

    public FavoritesOnlyImageRepository(FileManager fileManager) {
        super(fileManager);
    }

    @Override
    public Single<List<ImageListItem>> getPhotos() {
        return Single.create(emitter -> {
            fileRepository.getPhotos()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(storageFilesList -> {
                        favoritesRepository.getPhotos().subscribe(favoritesImageList -> {
                            List<ImageListItem> simpleImagesList = new ArrayList<>();
                            // Sync images from file system with images from favorite list
                            for (ImageListItem item : favoritesImageList) {
                                if (storageFilesList.contains(item.getImagePath())) {
                                    simpleImagesList.add(item);
                                } else {
                                    favoritesRepository.remove(item);
                                }
                            }
                            emitter.onSuccess(simpleImagesList);
                        }, throwable -> {
                            if (!emitter.isDisposed()) {
                                emitter.onError(throwable);
                            }
                        });
                    }, throwable -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(throwable);
                        }
                    });
        });
    }
}
