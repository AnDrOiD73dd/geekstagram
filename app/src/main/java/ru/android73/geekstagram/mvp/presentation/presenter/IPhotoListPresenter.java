package ru.android73.geekstagram.mvp.presentation.presenter;

import ru.android73.geekstagram.mvp.model.ImageAdapter;
import ru.android73.geekstagram.mvp.presentation.view.PhotoView;

public interface IPhotoListPresenter {

    void bindPhoto(int pos, PhotoView view);

    int getPhotosCount();

    void onImageClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder);

    void onFavoriteClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder);

    void onDeleteClick(int adapterPosition, ImageAdapter.ViewHolder viewHolder);
}
