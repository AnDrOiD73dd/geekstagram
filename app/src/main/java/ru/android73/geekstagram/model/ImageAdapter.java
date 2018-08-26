package ru.android73.geekstagram.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.android73.geekstagram.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

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
        void onItemClick(View view , int position);
        void onLongClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImageContainer;
        private ImageView ivFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onLongClick(v, getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
            ivImageContainer = itemView.findViewById(R.id.iv_image_container);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);
        }

        public void bind(int position) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                ImageListItem item = dataSource.get(position);
                setupViewData(item);
            }
        }

        private void setupViewData(ImageListItem item) {
            ivImageContainer.setImageURI(item.getImageUri());
            if (item.isFavorite()) {
                ivFavorite.setImageDrawable(ivFavorite.getContext().getResources()
                        .getDrawable(R.drawable.ic_favorite_24dp_vector));
            }
            else {
                ivFavorite.setImageDrawable(ivFavorite.getContext().getResources()
                        .getDrawable(R.drawable.ic_favorite_border_24dp_vector));
            }
        }
    }
}
