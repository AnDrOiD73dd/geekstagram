package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.squareup.picasso.Picasso;

import java.io.File;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.log.Logger;
import ru.android73.geekstagram.mvp.presentation.presenter.ViewerPresenter;
import ru.android73.geekstagram.mvp.presentation.view.ViewerView;


public class ViewerFragment extends MvpAppCompatFragment implements ViewerView {

    public static final String TAG = "ViewerFragment";
    public static final String KEY_IMAGE_URI = "c0725e31-cf64-43cf-b84e-1e79252fdce1";

    protected String imageUri;
    protected ImageView imageView;

    @InjectPresenter
    ViewerPresenter viewerPresenter;

    public static ViewerFragment newInstance(String imageUri) {
        ViewerFragment fragment = new ViewerFragment();
        Bundle args = new Bundle();
        args.putString(KEY_IMAGE_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_viewer, container, false);
        imageView = layout.findViewById(R.id.iv_photo);
        return layout;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_IMAGE_URI)) {
            imageUri = bundle.getString(KEY_IMAGE_URI);
        }
        if (imageUri == null) {
            Logger.w("imageUri is NULL");
            return;
        }
        File imageFile = new File(imageUri);
        Picasso.get()
                .load(imageFile)
                .into(imageView);
    }
}
