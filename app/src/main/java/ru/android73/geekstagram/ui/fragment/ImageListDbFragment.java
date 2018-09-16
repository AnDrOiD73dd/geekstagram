package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.ImageListDbPresenter;
import ru.android73.geekstagram.ui.presentation.view.ImageListDbView;

public class ImageListDbFragment extends MvpAppCompatFragment implements ImageListDbView {

    public static final String TAG = "ImageListDbFragment";

    @InjectPresenter
    ImageListDbPresenter imageListDbPresenter;

    public static ImageListDbFragment newInstance() {
        ImageListDbFragment fragment = new ImageListDbFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_list_db, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
