package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.ImageListNetworkPresenter;
import ru.android73.geekstagram.ui.presentation.view.ImageListNetworkView;

public class ImageListNetworkFragment extends MvpAppCompatFragment implements ImageListNetworkView {

    public static final String TAG = "ImageListNetworkFragment";

    @InjectPresenter
    ImageListNetworkPresenter imageListNetworkPresenter;

    public static ImageListNetworkFragment newInstance() {
        ImageListNetworkFragment fragment = new ImageListNetworkFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_list_network, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
