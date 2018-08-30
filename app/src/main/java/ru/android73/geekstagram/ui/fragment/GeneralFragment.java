package ru.android73.geekstagram.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.model.ImageAdapter;
import ru.android73.geekstagram.model.ImageListItem;
import ru.android73.geekstagram.ui.presentation.presenter.GeneralPresenter;
import ru.android73.geekstagram.ui.presentation.view.GeneralView;

public class GeneralFragment extends MvpAppCompatFragment implements GeneralView,
        ImageAdapter.OnItemClickListener {

    private static final int COLUMN_COUNT = 2;
    public static final int REQUEST_IMAGE_CAPTURE = 1000;

    @InjectPresenter
    GeneralPresenter generalPresenter;

    protected FloatingActionButton floatingActionButton;
    protected CoordinatorLayout coordinatorLayout;
    protected RecyclerView recyclerView;
    protected ImageAdapter adapter;
    protected List<ImageListItem> dataSource;

    public static GeneralFragment newInstance() {
        GeneralFragment fragment = new GeneralFragment();
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
                generalPresenter.onFabClick(getActivity());
            }
        });

        dataSource = new ArrayList<>();
        adapter = new ImageAdapter(dataSource);
        adapter.setOnItemClickListener(this);

        recyclerView = layout.findViewById(R.id.rv_images_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClickListener(null);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onImageClick(View v, int adapterPosition) {
        generalPresenter.onImageClick(v, adapterPosition);
    }

    @Override
    public void onImageLongClick(View v, int adapterPosition) {
        generalPresenter.onImageLongClick(adapterPosition);
    }

    @Override
    public void onLikeClick(View v, int adapterPosition) {
        generalPresenter.onLikeClick(v, adapterPosition);
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
        dataSource.add(item);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDeleteConfirmationDialog(final int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_item_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                generalPresenter.onDeleteConfirmed(adapterPosition);
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                generalPresenter.onDeleteCanceled();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void removeItem(int adapterPosition) {
        dataSource.remove(adapterPosition);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        generalPresenter.handleActivityResult(getActivity(), requestCode, resultCode, data);
    }
}
