package ru.android73.geekstagram.mvp.model.repo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.android73.geekstagram.AppApi;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;

class FavoritesRepository implements ImageRepository {

    private final AppApi api;

    public FavoritesRepository() {
        api = AppApi.getInstance();
    }

    @Override
    public Single<List<ImageListItem>> getPhotos() {
        return api.getDatabase().geekstagramDao().getAll();
    }

    @Override
    public Single<ImageListItem> add(ImageListItem item) {
        return Single.create(emitter -> {
            api.getDatabase().geekstagramDao().insert(item);
            emitter.onSuccess(item);
        });
    }

    @Override
    public Completable remove(ImageListItem item) {
        return Completable.create(emitter -> {
            api.getDatabase().geekstagramDao().delete(item);
            emitter.onComplete();
        });
    }

    @Override
    public Completable update(ImageListItem item) {
        return Completable.create(emitter -> {
            api.getDatabase().geekstagramDao().update(item);
            emitter.onComplete();
        });
    }
}
