package ru.android73.geekstagram.mvp.model.repo;

import java.util.List;

import ru.android73.geekstagram.mvp.model.db.ImageListItem;

public interface ImageRepository {

    void load();

    void add(ImageListItem item);

    void remove(ImageListItem item);

    void update(ImageListItem item);

    List<ImageListItem> getAll();

    List<ImageListItem> getFavorites();

    int getSize();

    void addListener(ImageRepositoryCallback listener);

    void removeListener(ImageRepositoryCallback listener);
}
