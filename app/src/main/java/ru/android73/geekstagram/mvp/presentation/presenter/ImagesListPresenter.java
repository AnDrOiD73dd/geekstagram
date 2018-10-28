package ru.android73.geekstagram.mvp.presentation.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;
import ru.android73.geekstagram.mvp.model.repo.ImageRepository;
import ru.android73.geekstagram.mvp.model.repo.ImageRepositoryCallback;
import ru.android73.geekstagram.mvp.model.repo.ImageRepositoryImpl;
import ru.android73.geekstagram.mvp.presentation.view.ImagesListView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> {

    private final FileManager fileManager;
    private final Scheduler scheduler;
    private String lastPhotoPath;
    private ImageRepository imageRepository;
    private ImageRepositoryCallback imageRepositoryCallback;

    public ImagesListPresenter(FileManager fileManager, Scheduler scheduler) {
        this.fileManager = fileManager;
        this.scheduler = scheduler;
        imageRepository = new ImageRepositoryImpl(fileManager);
        imageRepositoryCallback = new ImageRepositoryCallback() {
            @Override
            public void onLoadComplete() {
                getViewState().setData(imageRepository.getAll());
            }

            @Override
            public void onAdded(ImageListItem item) {
                getViewState().showInfo(R.string.notification_image_added_text);
                getViewState().onItemAdded(item);
            }

            @Override
            public void onUpdated(int index, ImageListItem item) {
                getViewState().updateItem(item);
            }

            @Override
            public void onDeleted(int index, ImageListItem item) {
                getViewState().showInfo(R.string.notification_image_deleted_text);
                getViewState().onItemDeleted(index, item);
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

    public void onImageClick(int adapterPosition) {
        getViewState().showImageViewer(adapterPosition);
    }

    public void onLikeClick(ImageListItem item, int adapterPosition) {
        item.setFavorite(!item.isFavorite());
        imageRepository.update(item);
    }

    public void onImageLongClick(ImageListItem item, int adapterPosition) {
        getViewState().showDeleteConfirmationDialog(item, adapterPosition);
    }

    public void onDeleteConfirmed(ImageListItem item, int adapterPosition) {
        imageRepository.remove(item);
    }

    public void onDeleteCanceled() {

    }

    public void onDeleteClick(ImageListItem item, int adapterPosition) {
        getViewState().showDeleteConfirmationDialog(item, adapterPosition);
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
                                .subscribe(uri -> getViewState().openCamera(uri.toString()), this::onAddPhotoError);
                    }
                }, this::onAddPhotoError);
    }

    private void onAddPhotoError(Throwable throwable) {
        Logger.e(throwable);
        getViewState().showInfo(R.string.notification_can_not_create_file);
    }
}
