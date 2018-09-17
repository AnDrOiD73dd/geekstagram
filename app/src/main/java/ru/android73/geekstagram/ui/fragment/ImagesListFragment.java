package ru.android73.geekstagram.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.List;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.FileManagerImpl;
import ru.android73.geekstagram.common.ImageRepository;
import ru.android73.geekstagram.model.ImageAdapter;
import ru.android73.geekstagram.model.db.ImageListItem;
import ru.android73.geekstagram.ui.presentation.presenter.ImagesListPresenter;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

import static android.app.Activity.RESULT_OK;

public class ImagesListFragment extends MvpAppCompatFragment implements ImagesListView,
        ImageAdapter.OnItemClickListener {

    public static final String TAG = "ImagesListFragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1000;
    private static final int IMAGE_WIDTH_DP = 180;

    @InjectPresenter
    ImagesListPresenter imagesListPresenter;

    protected FloatingActionButton floatingActionButton;
    protected CoordinatorLayout coordinatorLayout;
    protected RecyclerView recyclerView;
    protected ImageAdapter adapter;
    private OnFragmentInteractionListener listener;
    private Handler handler;

    public static ImagesListFragment newInstance() {
        ImagesListFragment fragment = new ImagesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ImagesListPresenter provideImagesListPresenter() {
        return new ImagesListPresenter(new FileManagerImpl(getContext().getApplicationContext()));
    }

    public ImagesListFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_general, container, false);
        coordinatorLayout = layout.findViewById(R.id.fragment_general_root);

        floatingActionButton = layout.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesListPresenter.onAddPhotoClick();
            }
        });

        List<ImageListItem> dataSource = new ArrayList<>();
        adapter = new ImageAdapter(dataSource);
        adapter.setOnItemClickListener(this);

        recyclerView = layout.findViewById(R.id.rv_images_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), calculateColumnsCount());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return layout;
    }

    private int calculateColumnsCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / IMAGE_WIDTH_DP);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    @Override
    public void onImageClick(View v, int adapterPosition) {
        imagesListPresenter.onImageClick(adapterPosition);
    }

    @Override
    public void onImageLongClick(View v, ImageListItem item, int adapterPosition) {
        imagesListPresenter.onImageLongClick(item, adapterPosition);
    }

    @Override
    public void onLikeClick(View v, ImageListItem item, int adapterPosition) {
        imagesListPresenter.onLikeClick(item, adapterPosition);
    }

    @Override
    public void onDeleteClick(ImageListItem item, int adapterPosition) {
        imagesListPresenter.onDeleteClick(item, adapterPosition);
    }

    @Override
    public void loadData(final ImageRepository imageRepository) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageRepository.load();
            }
        });
    }

    @Override
    public void setData(final List<ImageListItem> data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showInfo(int resourceId) {
        Snackbar.make(coordinatorLayout, resourceId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openCamera(String imagePath) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // TODO check NPE
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Uri imageUri = Uri.parse(imagePath);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onItemAdded(ImageListItem item) {
        List<ImageListItem> dataSource = adapter.getData();
        if (dataSource.contains(item)) {
            return;
        } else {
            if (dataSource.add(item)) {
                int position = dataSource.indexOf(item);
                adapter.notifyItemInserted(position);
            }
        }
    }

    @Override
    public void onItemDeleted(int index, ImageListItem item) {
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void showDeleteConfirmationDialog(final ImageListItem item, final int adapterPosition) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.dialog_delete_item_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        imagesListPresenter.onDeleteConfirmed(item, adapterPosition);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        imagesListPresenter.onDeleteCanceled();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void updateItem(ImageListItem item) {
        List<ImageListItem> dataSource = adapter.getData();
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i).getImagePath().equals(item.getImagePath())) {
                dataSource.remove(i);
                dataSource.add(i, item);
                adapter.notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    public void showImageViewer(int adapterPosition) {
        listener.onItemClicked(adapter.getData().get(adapterPosition).getImagePath());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagesListPresenter.onTakePhotoSuccess();
        }
    }

    public interface OnFragmentInteractionListener {
        void onItemClicked(String imageUri);
    }

    private class ImageDiffUtilCallback extends DiffUtil.Callback {

        private final List<ImageListItem> oldList;
        private final List<ImageListItem> newList;

        public ImageDiffUtilCallback(List<ImageListItem> oldList, List<ImageListItem> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            ImageListItem oldItem = oldList.get(oldItemPosition);
            ImageListItem newItem = newList.get(newItemPosition);
            return oldItem.getImagePath().equals(newItem.getImagePath());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            ImageListItem oldItem = oldList.get(oldItemPosition);
            ImageListItem newItem = newList.get(newItemPosition);
            return oldItem.equals(newItem);
        }
    }
}
