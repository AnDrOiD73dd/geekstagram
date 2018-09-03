package ru.android73.geekstagram.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.android73.geekstagram.AppApi;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.model.ImageAdapter;
import ru.android73.geekstagram.model.db.ImageListItem;
import ru.android73.geekstagram.ui.presentation.presenter.ImagesListPresenter;
import ru.android73.geekstagram.ui.presentation.view.ImagesListView;

import static android.app.Activity.RESULT_OK;

public class ImagesListFragment extends MvpAppCompatFragment implements ImagesListView,
        ImageAdapter.OnItemClickListener {

    public static final String TAG = "ImagesListFragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1000;
    private static final int IMAGE_WIDTH = 180;
    private static final String IMAGE_SUFFIX = ".jpg";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    private static final String KEY_PHOTO_PATH = "97c75611-71b0-49a7-9be3-aa0ad1adc655";

    @InjectPresenter
    ImagesListPresenter imagesListPresenter;

    protected FloatingActionButton floatingActionButton;
    protected CoordinatorLayout coordinatorLayout;
    protected RecyclerView recyclerView;
    protected ImageAdapter adapter;
    protected List<ImageListItem> dataSource;
    protected String lastPhotoPath;
    private OnFragmentInteractionListener listener;

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
                File imageFile = createImageFile();
                if (imageFile == null) {
                    imagesListPresenter.onCreateFileError();
                    return;
                }
                Uri imageUri = getImageUri(imageFile);
                lastPhotoPath = imageFile.getAbsolutePath();
                imagesListPresenter.onCreateFileSuccess(imageUri);
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

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat(DEFAULT_DATE_FORMAT,
                Locale.getDefault()).format(new Date());
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(timeStamp, IMAGE_SUFFIX, storageDir);
        } catch (IOException e) {
            Logger.e(e);
        }
        return image;
    }

    private Uri getImageUri(File image) {
        return FileProvider.getUriForFile(getContext(), getString(R.string.file_provider_authority),
                image);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PHOTO_PATH, lastPhotoPath);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_PHOTO_PATH)) {
            lastPhotoPath = savedInstanceState.getString(KEY_PHOTO_PATH);
        }
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
    public void showImageViewer(int adapterPosition) {
        listener.onItemClicked(dataSource.get(adapterPosition).getImageUri());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagesListPresenter.onTakePhotoSuccess(lastPhotoPath);
        }
    }

    public interface OnFragmentInteractionListener {
        void onItemClicked(String imageUri);
    }
}
