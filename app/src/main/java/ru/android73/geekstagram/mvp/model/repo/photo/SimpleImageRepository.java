package ru.android73.geekstagram.mvp.model.repo.photo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;
import ru.android73.geekstagram.mvp.model.entity.DataType;
import ru.android73.geekstagram.mvp.model.repo.FileRepository;
import ru.android73.geekstagram.mvp.model.repo.FileRepositoryImpl;


public class SimpleImageRepository implements ImageRepository {

    protected transient FileRepository fileRepository;
    protected transient ImageRepository favoritesRepository;

    public SimpleImageRepository(FileManager fileManager) {
        fileRepository = new FileRepositoryImpl(fileManager);
        favoritesRepository = new FavoritesRepository();
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
                            // Add images from file system to result list
                            for (String filePath : storageFilesList) {
                                // Simulate favorite item
                                ImageListItem imageListItem = new ImageListItem(filePath, true, DataType.LOCAL);
                                if (!simpleImagesList.contains(imageListItem)) {
                                    simpleImagesList.add(new ImageListItem(filePath, false, DataType.LOCAL));
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

    @Override
    public Single<ImageListItem> add(ImageListItem item) {
        return Single.create(emitter -> {
            fileRepository.createFile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(file -> {
                        emitter.onSuccess(new ImageListItem(file.getAbsolutePath(), false, DataType.LOCAL));
                    }, throwable -> {
                        if (!emitter.isDisposed()) {
                            emitter.onError(throwable);
                        }
                    });
        });
    }

    @Override
    public Completable remove(ImageListItem item) {
        return Completable.create(emitter -> {
            favoritesRepository.remove(item).subscribe();
            fileRepository.deleteFile(item.getImagePath()).subscribe();
            emitter.onComplete();
        });
    }

    @Override
    public Completable update(ImageListItem item) {
        return Completable.create(emitter -> {
            if (item.isFavorite()) {
                favoritesRepository.add(item).subscribe();  // TODO check result
            } else {
                favoritesRepository.remove(item).subscribe();  // TODO check result
            }
            emitter.onComplete();
        });
    }
}
