package ru.android73.geekstagram.mvp.presentation.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.ImageAdapter;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;
import ru.android73.geekstagram.mvp.model.repo.ImageRepository;
import ru.android73.geekstagram.mvp.model.repo.SimpleImageRepository;
import ru.android73.geekstagram.mvp.presentation.view.ImagesListView;
import ru.android73.geekstagram.mvp.presentation.view.PhotoView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> implements IPhotoListPresenter {

    private final Scheduler scheduler;
    private String lastPhotoPath;
    private List<ImageListItem> photosList;
    private ImageRepository imageRepository;
    private final FileManager fileManager;

    public ImagesListPresenter(FileManager fileManager, Scheduler scheduler, ImageRepository imageRepository) {
        this.scheduler = scheduler;
        this.fileManager = fileManager;
        this.imageRepository = imageRepository;
        imageRepository = new SimpleImageRepository(fileManager);
        photosList = new ArrayList<>();
    }

    @Override
    public void attachView(ImagesListView view) {
        super.attachView(view);
        loadPhotos();
    }

    @SuppressLint("CheckResult")
    private void loadPhotos() {
        imageRepository.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(imageListItems -> {
                    photosList = imageListItems;
                    getViewState().updatePhotosList();
                }, throwable -> {
                    Logger.e(throwable);
                    getViewState().showErrorLoadPhoto();
                });
    }

    public void onTakePhotoSuccess() {
        ImageListItem item = new ImageListItem(lastPhotoPath, false);
        if (photosList.add(item)) {
            int position = photosList.indexOf(item);
            getViewState().onItemAdded(position);
            getViewState().showMessageImageAdded();
        } else {
            getViewState().showErrorAddPhoto();
        }
    }

    @SuppressLint("CheckResult")
    public void onDeleteConfirmed(ImageListItem item, int adapterPosition) {
        imageRepository.remove(item)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(() -> {
                    photosList.remove(item);
                    getViewState().showMessageImageDeleted();
                    getViewState().onItemDeleted(adapterPosition);
                }, throwable -> {
                    Logger.e(throwable);
                    getViewState().showErrorImageDeleted();
                });
    }

    public void onDeleteCanceled() {

    }

    @SuppressLint("CheckResult")
    public void onAddPhotoClick() {
        imageRepository.add(null)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(imageListItem -> {
                    lastPhotoPath = imageListItem.getImagePath();
                    fileManager.getPhotoImageUri(lastPhotoPath)
                            .subscribeOn(Schedulers.io())
                            .observeOn(scheduler)
                            .subscribe(uri -> getViewState().openCamera(uri.toString()),
                                    this::onAddPhotoError);
                }, this::onAddPhotoError);
    }

    private void onAddPhotoError(Throwable throwable) {
        Logger.e(throwable);
        getViewState().showErrorAddPhoto();
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

    @SuppressLint("CheckResult")
    @Override
    public void onFavoriteClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder) {
        ImageListItem item = photosList.get(adapterPosition);
        item.setFavorite(!item.isFavorite());
        imageRepository.update(item).subscribeOn(Schedulers.io()).observeOn(scheduler)
                .subscribe(() -> {
                    photosList.remove(adapterPosition);
                    photosList.add(adapterPosition, item);
                    getViewState().onItemUpdated(adapterPosition);
                }, throwable -> {
                    Logger.e(throwable);
                    getViewState().showErrorFavorite();
                });
    }

    @Override
    public void onDeleteClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder) {
        ImageListItem item = photosList.get(adapterPosition);
        getViewState().showDeleteConfirmationDialog(item, adapterPosition);
    }
}
