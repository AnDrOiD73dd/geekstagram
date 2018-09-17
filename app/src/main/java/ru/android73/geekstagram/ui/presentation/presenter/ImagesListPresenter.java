package ru.android73.geekstagram.ui.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.FileManager;
import ru.android73.geekstagram.common.ImageRepository;
import ru.android73.geekstagram.common.ImageRepositoryCallback;
import ru.android73.geekstagram.common.ImageRepositoryImpl;
import ru.android73.geekstagram.model.db.ImageListItem;
import ru.android73.geekstagram.ui.fragment.ImagesListFragment;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

@InjectViewState
public class ImagesListPresenter extends MvpPresenter<ImagesListView> {

    private final FileManager fileManager;
    private final int mode;
    private String lastPhotoPath;
    private ImageRepository imageRepository;
    private ImageRepositoryCallback imageRepositoryCallback;

    public ImagesListPresenter(@ImagesListFragment.ImageListMode final int mode, FileManager fileManager) {
        this.fileManager = fileManager;
        this.mode = mode;
        imageRepository = new ImageRepositoryImpl(fileManager);
        imageRepositoryCallback = new ImageRepositoryCallback() {
            @Override
            public void onLoadComplete() {
                if (mode == ImagesListFragment.MODE_ALL) {
                    getViewState().setData(imageRepository.getAll());
                } else if (mode == ImagesListFragment.MODE_FAVORITE) {
                    getViewState().setData(imageRepository.getFavorites());
                }
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

    @Override
    public void attachView(ImagesListView view) {
        super.attachView(view);
        imageRepository.addListener(imageRepositoryCallback);
        getViewState().loadData(imageRepository);
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

    public void onAddPhotoClick() {
        File imageFile = fileManager.createPhotoFile(null, null);
        if (imageFile == null) {
            getViewState().showInfo(R.string.notification_can_not_create_file);
        } else {
            lastPhotoPath = imageFile.getAbsolutePath();
            String imageUri = fileManager.getPhotoImageUri(imageFile).toString();
            getViewState().openCamera(imageUri);
        }
    }
}
