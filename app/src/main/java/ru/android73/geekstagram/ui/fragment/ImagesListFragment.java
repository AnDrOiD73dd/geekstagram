package ru.android73.geekstagram.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.File;
import java.util.List;

import ru.android73.geekstagram.AppApi;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.model.ImageAdapter;
import ru.android73.geekstagram.model.db.ImageListItem;
import ru.android73.geekstagram.ui.presentation.presenter.ImagesListPresenter;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

import static android.app.Activity.RESULT_OK;

public class ImagesListFragment extends MvpAppCompatFragment implements ImagesListView,
        ImageAdapter.OnItemClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1000;
    private static final int IMAGE_WIDTH = 180;

    @InjectPresenter
    ImagesListPresenter imagesListPresenter;

    protected FloatingActionButton floatingActionButton;
    protected CoordinatorLayout coordinatorLayout;
    protected RecyclerView recyclerView;
    protected ImageAdapter adapter;
    protected List<ImageListItem> dataSource;

    public static ImagesListFragment newInstance() {
        ImagesListFragment fragment = new ImagesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        dataSource = AppApi.getInstance().getDatabase().geekstagramDao().getAll();
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
        return (int) (dpWidth / IMAGE_WIDTH);
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
    public void onImageLongClick(View v, int adapterPosition) {
        imagesListPresenter.onImageLongClick(adapterPosition);
    }

    @Override
    public void onLikeClick(View v, int adapterPosition) {
        imagesListPresenter.onLikeClick(adapterPosition);
    }

    @Override
    public void onDeleteClick(int adapterPosition) {
        imagesListPresenter.onDeleteClick(adapterPosition);
    }

    @Override
    public void showInfo(int resourceId) {
        Snackbar.make(coordinatorLayout, resourceId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openCamera(Uri imageUri) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // TODO check NPE
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void addItemToList(ImageListItem item) {
        if (dataSource.add(item)) {
            // TODO move work with DB
            AppApi.getInstance().getDatabase().geekstagramDao().insert(item);
            int position = dataSource.indexOf(item);
            adapter.notifyItemInserted(position);
        }
    }

    @Override
    public void showDeleteConfirmationDialog(final int adapterPosition) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.dialog_delete_item_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                imagesListPresenter.onDeleteConfirmed(adapterPosition);
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
    public void removeItem(int adapterPosition) {
        ImageListItem item = dataSource.get(adapterPosition);
        //TODO move work with DB and files
        AppApi.getInstance().getDatabase().geekstagramDao().delete(item);
        File file = new File(item.getImageUri());
        // TODO handle result
        file.delete();
        dataSource.remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
    }

    @Override
    public void revertItemLike(int adapterPosition) {
        ImageListItem item = dataSource.get(adapterPosition);
        item.setFavorite(!item.isFavorite());
        // TODO move work with DB
        AppApi.getInstance().getDatabase().geekstagramDao().update(item);
        adapter.notifyItemChanged(adapterPosition);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagesListPresenter.onTakePhotoSuccess();
        }
    }
}
