package ru.android73.geekstagram.mvp.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;
import ru.android73.geekstagram.mvp.model.entity.DataType;
import ru.android73.geekstagram.mvp.model.cache.ImageCache;
import ru.android73.geekstagram.mvp.presentation.presenter.IPhotoListPresenter;
import ru.android73.geekstagram.mvp.presentation.view.PhotoView;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private static final int PREVIEW_SIZE = 300;
    private IPhotoListPresenter presenter;

    public ImageAdapter(IPhotoListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_general_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        presenter.bindPhoto(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getPhotosCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PhotoView {

        @BindView(R.id.iv_image_container)
        ImageView imageContainer;
        @BindView(R.id.iv_favorite)
        ImageView favoriteIcon;
        @BindView(R.id.iv_delete)
        ImageView deleteIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.iv_image_container, R.id.iv_favorite, R.id.iv_delete})
        public void onItemClick(View view) {
            switch (view.getId()) {
                case R.id.iv_image_container:
                    presenter.onImageClick(getAdapterPosition(), this);
                    break;
                case R.id.iv_favorite:
                    presenter.onFavoriteClick(getAdapterPosition(), this);
                    break;
                case R.id.iv_delete:
                    presenter.onDeleteClick(getAdapterPosition(), this);
                    break;
            }
        }

        @OnLongClick(R.id.iv_image_container)
        public boolean onItemLongClick(View view) {
            presenter.onDeleteClick(getAdapterPosition(), this);
            return true;
        }

        @Override
        public void setPhoto(ImageListItem item, ImageCache imageCache) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                if (item.getDataType().equals(DataType.LOCAL)) {
                    loadImageFromFile(item);

                } else if (item.getDataType().equals(DataType.REMOTE)) {
                    if (NetworkStatus.isOnline()) {
                        loadImageFromNetwork(item, imageCache);
                    } else {
                        String imagePath = imageCache.getImage(item.getImagePath());
                        item.setImagePath(imagePath);
                        loadImageFromFile(item);
                    }
                }
            }
        }

        private void loadImageFromNetwork(ImageListItem item, ImageCache imageCache) {
            Picasso.get()
                    .load(item.getImagePath())
                    .resize(PREVIEW_SIZE, PREVIEW_SIZE)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_24dp_vector)
                    .error(R.drawable.ic_report_problem_24dp_vector)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imageContainer.setImageBitmap(bitmap);
                            imageCache.putImage(item.getImagePath(), bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Logger.e("Error loading image: %s", e.getMessage());
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }

        private void loadImageFromFile(ImageListItem item) {
            File file = new File(item.getImagePath());
            Picasso.get()
                    .load(file)
                    .resize(PREVIEW_SIZE, PREVIEW_SIZE)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_24dp_vector)
                    .error(R.drawable.ic_report_problem_24dp_vector)
                    .into(imageContainer);

        }

        @Override
        public void setFavorite(boolean flag) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                if (flag) {
                    favoriteIcon.setImageDrawable(favoriteIcon.getContext().getResources()
                            .getDrawable(R.drawable.ic_favorite_red_24dp_vector));
                } else {
                    favoriteIcon.setImageDrawable(favoriteIcon.getContext().getResources()
                            .getDrawable(R.drawable.ic_favorite_white_24dp_vector));
                }
            }
        }

        @Override
        public void setDeleteIcon() {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                deleteIcon.setImageResource(R.drawable.ic_delete_filled_24dp_vector);
            }
        }
    }
}
