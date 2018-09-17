package ru.android73.geekstagram.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.android73.geekstagram.AppApi;
import ru.android73.geekstagram.model.db.ImageListItem;

public class ImageRepositoryImpl implements ImageRepository {

    private List<ImageRepositoryCallback> callbackList;
    private List<ImageListItem> imagesList;
    private FileManager fileManager;

    public ImageRepositoryImpl(FileManager fileManager) {
        this.fileManager = fileManager;
        imagesList = new ArrayList<>();
        callbackList = new ArrayList<>();
    }

    @Override
    public void load() {
        syncData();
        for (ImageRepositoryCallback callback : callbackList) {
            callback.onLoadComplete();
        }
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
                AppApi.getInstance().getDatabase().geekstagramDao().delete(item);
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
                    AppApi.getInstance().getDatabase().geekstagramDao().insert(item);
                } else {
                    AppApi.getInstance().getDatabase().geekstagramDao().delete(item);
                }
                for (ImageRepositoryCallback callback : callbackList) {
                    callback.onUpdated(item);
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

    private void syncData() {
        List<String> filesPaths = fileManager.getStorageFilesList();
        List<ImageListItem> favoritesFiles = AppApi.getInstance().getDatabase().geekstagramDao().getAll();
        imagesList.clear();
        for (ImageListItem item : favoritesFiles) {
            if (filesPaths.contains(item.getImagePath())) {
                imagesList.add(item);
            } else {
                AppApi.getInstance().getDatabase().geekstagramDao().delete(item);
            }
        }
        for (String filePath : filesPaths) {
            ImageListItem item = AppApi.getInstance().getDatabase().geekstagramDao().get(filePath);
            if (!imagesList.contains(item)) {
                imagesList.add(new ImageListItem(filePath, false));
            }
        }
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
