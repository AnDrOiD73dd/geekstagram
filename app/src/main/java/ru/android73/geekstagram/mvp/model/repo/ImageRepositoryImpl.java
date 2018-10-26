package ru.android73.geekstagram.mvp.model.repo;

import android.annotation.SuppressLint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.AppApi;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public class ImageRepositoryImpl implements ImageRepository {

    private final AppApi api;
    private List<ImageRepositoryCallback> callbackList;
    private List<ImageListItem> imagesList;
    private FileManager fileManager;

    public ImageRepositoryImpl(FileManager fileManager) {
        this.fileManager = fileManager;
        api = AppApi.getInstance();
        imagesList = new ArrayList<>();
        callbackList = new ArrayList<>();
    }

    @Override
    public Completable load() {
        return syncData();
    }

    @Override
    public void add(ImageListItem item) {
        imagesList.add(item);  //TODO check result
        for (ImageRepositoryCallback callback : callbackList) {
            callback.onAdded(item);
        }
    }

    @Override
    public void remove(ImageListItem item) {
        int index = imagesList.indexOf(item);
        if (imagesList.remove(item)) {
            if (item.isFavorite()) {
                api.getDatabase().geekstagramDao().delete(item);
            }
            File file = new File(item.getImagePath());
            // TODO handle result
            file.delete();
            for (ImageRepositoryCallback callback : callbackList) {
                callback.onDeleted(index, item);
            }
        }
    }

    @Override
    public void update(ImageListItem item) {
        for (int i = 0; i < imagesList.size(); i++) {
            if (item.getImagePath().equals(imagesList.get(i).getImagePath())) {
                imagesList.remove(i);
                imagesList.add(i, item);
                if (item.isFavorite()) {
                    api.getDatabase().geekstagramDao().insert(item);
                } else {
                    api.getDatabase().geekstagramDao().delete(item);
                }
                for (ImageRepositoryCallback callback : callbackList) {
                    callback.onUpdated(i, item);
                }
                return;
            }
        }
    }

    @Override
    public List<ImageListItem> getAll() {
        return imagesList;
    }

    @Override
    public List<ImageListItem> getFavorites() {
        List<ImageListItem> favoritesList = new ArrayList<>();
        for (ImageListItem item : imagesList) {
            if (item.isFavorite()) {
                favoritesList.add(item);
            }
        }
        return favoritesList;
    }

    @Override
    public int getSize() {
        return imagesList.size();
    }

    @SuppressLint("CheckResult")
    private Completable syncData() {
        return Completable.create(emitter -> {
            List<String> filesPaths = fileManager.getStorageFilesList();
            api.getDatabase().geekstagramDao().getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(imageListItems -> {
                        imagesList.clear();
                        for (ImageListItem item : imageListItems) {
                            if (filesPaths.contains(item.getImagePath())) {
                                imagesList.add(item);
                            } else {
                                api.getDatabase().geekstagramDao().delete(item);
                            }
                        }
                        for (String filePath : filesPaths) {
                            ImageListItem imageListItem = api.getDatabase().geekstagramDao().get(filePath);
                            if (!imagesList.contains(imageListItem)) {
                                imagesList.add(new ImageListItem(filePath, false));
                            }
                        }
                        emitter.onComplete();
                    }, throwable -> Logger.e("%s", throwable));
        });
    }

    @Override
    public void addListener(ImageRepositoryCallback listener) {
        if (!callbackList.contains(listener)) {
            callbackList.add(listener);
        }
    }

    @Override
    public void removeListener(ImageRepositoryCallback listener) {
        callbackList.remove(listener);
    }
}
