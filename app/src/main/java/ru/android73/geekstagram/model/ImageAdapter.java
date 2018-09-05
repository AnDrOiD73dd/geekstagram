package ru.android73.geekstagram.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.model.db.ImageListItem;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private static final int PREVIEW_SIZE = 300;
    private final List<ImageListItem> dataSource;
    private OnItemClickListener itemClickListener;

    public ImageAdapter(List<ImageListItem> dataSource) {
        this.dataSource = dataSource;
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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnItemClickListener {
        void onImageClick(View v, int adapterPosition);
        void onImageLongClick(View v, int adapterPosition);
        void onLikeClick(View v, int adapterPosition);
        void onDeleteClick(int adapterPosition);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageContainer;
        private ImageView favoriteIcon;
        private ImageView deleteIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            imageContainer = itemView.findViewById(R.id.iv_image_container);
            imageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onImageClick(v, getAdapterPosition());
                    }
                }
            });
            imageContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onImageLongClick(v, getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });

            favoriteIcon = itemView.findViewById(R.id.iv_favorite);
            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onLikeClick(v, getAdapterPosition());
                    }
                }
            });
            deleteIcon = itemView.findViewById(R.id.iv_delete);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onDeleteClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(int position) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                ImageListItem item = dataSource.get(position);
                setupViewData(item);
            }
        }

        private void setupViewData(ImageListItem item) {
            File file = new File(item.getImageUri());
            Picasso.get()
                    .load(file)
                    .resize(PREVIEW_SIZE,PREVIEW_SIZE)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_24dp_vector)
                    .error(R.drawable.ic_report_problem_24dp_vector)
                    .into(imageContainer, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Logger.e(e.getLocalizedMessage());
                        }
                    });
            if (item.isFavorite()) {
                favoriteIcon.setImageDrawable(favoriteIcon.getContext().getResources()
                        .getDrawable(R.drawable.ic_favorite_24dp_vector));
            }
            else {
                favoriteIcon.setImageDrawable(favoriteIcon.getContext().getResources()
                        .getDrawable(R.drawable.ic_favorite_filled_with_border_vector));
            }
            deleteIcon.setImageResource(R.drawable.ic_delete_filled_with_border_24dp_vector);
        }
    }
}
