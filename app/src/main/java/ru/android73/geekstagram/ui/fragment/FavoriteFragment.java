package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.FavoritePresenter;
import ru.android73.geekstagram.ui.presentation.view.FavoriteView;

public class FavoriteFragment extends MvpAppCompatFragment implements FavoriteView {

    public static final String TAG = "FavoriteFragment";

    @InjectPresenter
    FavoritePresenter favoritePresenter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
