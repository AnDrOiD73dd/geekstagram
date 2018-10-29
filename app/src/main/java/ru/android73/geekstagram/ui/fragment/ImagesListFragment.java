package ru.android73.geekstagram.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
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
import com.arellomobile.mvp.presenter.ProvidePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.android73.geekstagram.GeekstagramApp;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.mvp.model.FileManagerImpl;
import ru.android73.geekstagram.mvp.model.ImageAdapter;
import ru.android73.geekstagram.mvp.model.db.ImageListItem;
import ru.android73.geekstagram.mvp.model.repo.ImageRepository;
import ru.android73.geekstagram.mvp.presentation.presenter.ImagesListPresenter;
import ru.android73.geekstagram.mvp.presentation.view.ImagesListView;

import static android.app.Activity.RESULT_OK;

public class ImagesListFragment extends MvpAppCompatFragment implements ImagesListView {

    @InjectPresenter
    ImagesListPresenter imagesListPresenter;

    public static final String TAG = "ImagesListFragment";
    public static final String KEY_REPOSITORY = "key_repository";

    private static final int REQUEST_IMAGE_CAPTURE = 1000;
    private static final int IMAGE_WIDTH_DP = 180;

    protected FloatingActionButton floatingActionButton;
    protected CoordinatorLayout coordinatorLayout;
    protected RecyclerView recyclerView;
    protected ImageAdapter adapter;
    private OnFragmentInteractionListener listener;

    public static ImagesListFragment newInstance(ImageRepository imageRepository) {
        ImagesListFragment fragment = new ImagesListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_REPOSITORY, imageRepository);
        fragment.setArguments(bundle);
        return fragment;
    }

    @ProvidePresenter
    ImagesListPresenter provideImagesListPresenter() {
        ImageRepository imageRepository = (ImageRepository) getArguments().getSerializable(KEY_REPOSITORY);
        ImagesListPresenter imagesListPresenter = new ImagesListPresenter(AndroidSchedulers.mainThread(), imageRepository);
        return imagesListPresenter ;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_general, container, false);
        coordinatorLayout = layout.findViewById(R.id.fragment_general_root);

        floatingActionButton = layout.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(v -> imagesListPresenter.onAddPhotoClick());

        adapter = new ImageAdapter(imagesListPresenter);

        recyclerView = layout.findViewById(R.id.rv_images_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getColumnCount());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return layout;
    }

    private int getColumnCount() {
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
    public void updatePhotosList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemAdded(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override
    public void onItemUpdated(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onItemDeleted(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showMessageImageAdded() {
        showInfo(R.string.notification_image_added_text);
    }

    @Override
    public void showMessageImageDeleted() {
        showInfo(R.string.notification_image_deleted_text);
    }

    @Override
    public void showErrorImageDeleted() {
        showInfo(R.string.notification_image_deleted_error_text);
    }

    @Override
    public void showErrorLoadPhoto() {
        showInfo(R.string.notification_can_not_load_data_text);
    }

    @Override
    public void showErrorAddPhoto() {
        showInfo(R.string.notification_can_not_create_file);
    }

    @Override
    public void showErrorFavorite() {
        showInfo(R.string.notification_can_not_update_image_text);
    }

    private void showInfo(@StringRes int message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
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
    public void showDeleteConfirmationDialog(final ImageListItem item, final int adapterPosition) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.dialog_delete_item_message)
                .setPositiveButton(R.string.yes, (dialog, id) -> imagesListPresenter.onDeleteConfirmed(item, adapterPosition))
                .setNegativeButton(R.string.cancel, (dialog, id) -> imagesListPresenter.onDeleteCanceled());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showImageViewer(String imagePath) {
        listener.onItemClicked(imagePath);
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
}
