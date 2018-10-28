package ru.android73.geekstagram.mvp.presentation.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.ImageAdapter;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;
import ru.android73.geekstagram.mvp.model.repo.ImageRepository;
import ru.android73.geekstagram.mvp.model.repo.ImageRepositoryCallback;
import ru.android73.geekstagram.mvp.model.repo.ImageRepositoryImpl;
import ru.android73.geekstagram.mvp.presentation.view.ImagesListView;
import ru.android73.geekstagram.mvp.presentation.view.PhotoView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> implements IPhotoListPresenter {

    private final FileManager fileManager;
    private final Scheduler scheduler;
    private String lastPhotoPath;
    private ImageRepository imageRepository;
    private ImageRepositoryCallback imageRepositoryCallback;
    private List<ImageListItem> photosList;

    public ImagesListPresenter(FileManager fileManager, Scheduler scheduler) {
        this.fileManager = fileManager;
        this.scheduler = scheduler;
        photosList = new ArrayList<>();
        imageRepository = new ImageRepositoryImpl(fileManager);
        imageRepositoryCallback = new ImageRepositoryCallback() {
            @Override
            public void onLoadComplete() {
                photosList = imageRepository.getAll();
                getViewState().updatePhotosList();
            }

            @Override
            public void onAdded(ImageListItem item) {
                if (photosList.contains(item)) {
                    return;
                } else {
                    if (photosList.add(item)) {
                        int position = photosList.indexOf(item);
                        getViewState().onItemAdded(position);
                        getViewState().showInfo(R.string.notification_image_added_text);
                    }
                }
            }

            @Override
            public void onUpdated(int index, ImageListItem item) {
                for (int i = 0; i < photosList.size(); i++) {
                    if (photosList.get(i).getImagePath().equals(item.getImagePath())) {
                        photosList.remove(i);
                        photosList.add(i, item);
                        getViewState().onItemUpdated(i);
                        return;
                    }
                }
            }

            @Override
            public void onDeleted(int index, ImageListItem item) {
                getViewState().showInfo(R.string.notification_image_deleted_text);
                getViewState().onItemDeleted(index);
            }
        };
    }

    @SuppressLint("CheckResult")
    @Override
    public void attachView(ImagesListView view) {
        super.attachView(view);
        imageRepository.addListener(imageRepositoryCallback);
        imageRepository.load()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(() -> imageRepositoryCallback.onLoadComplete(),
                        throwable -> Logger.e("%s", throwable));
    }

    @Override
    public void destroyView(ImagesListView view) {
        imageRepository.removeListener(imageRepositoryCallback);
        super.destroyView(view);
    }

    public void onTakePhotoSuccess() {
        imageRepository.add(new ImageListItem(lastPhotoPath, false));
    }

    public void onDeleteConfirmed(ImageListItem item, int adapterPosition) {
        imageRepository.remove(item);
    }

    public void onDeleteCanceled() {

    }

    @SuppressLint("CheckResult")
    public void onAddPhotoClick() {
        fileManager.createPhotoFile(null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(imageFile -> {
                    if (imageFile == null) {
                        onAddPhotoError(null);
                    } else {
                        lastPhotoPath = imageFile.getAbsolutePath();
                        fileManager.getPhotoImageUri(imageFile)
                                .subscribeOn(Schedulers.io())
                                .observeOn(scheduler)
                                .subscribe(uri -> getViewState().openCamera(uri.toString()),
                                        this::onAddPhotoError);
                    }
                }, this::onAddPhotoError);
    }

    private void onAddPhotoError(Throwable throwable) {
        Logger.e(throwable);
        getViewState().showInfo(R.string.notification_can_not_create_file);
    }

    @Override
    public void bindPhoto(int pos, PhotoView view) {
        ImageListItem item = photosList.get(pos);
        view.setPhoto(item.getImagePath());
        view.setFavorite(item.isFavorite());
        view.setDeleteIcon();
    }

    @Override
    public int getPhotosCount() {
        return photosList.size();
    }

    @Override
    public void onImageClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder) {
        ImageListItem item = photosList.get(adapterPosition);
        getViewState().showImageViewer(item.getImagePath());
    }

    @Override
    public void onFavoriteClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder) {
        ImageListItem item = photosList.get(adapterPosition);
        item.setFavorite(!item.isFavorite());
        imageRepository.update(item);
    }

    @Override
    public void onDeleteClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder) {
        ImageListItem item = photosList.get(adapterPosition);
        getViewState().showDeleteConfirmationDialog(item, adapterPosition);
    }
}
