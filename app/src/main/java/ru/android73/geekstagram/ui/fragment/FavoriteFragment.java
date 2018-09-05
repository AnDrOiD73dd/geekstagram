package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

    FragmentManager fragmentManager;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_favorite, container, false);

        fragmentManager = getChildFragmentManager();

        if (fragmentManager.findFragmentByTag(ImageListDbFragment.TAG) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container_frame, ImageListDbFragment.newInstance(), ImageListDbFragment.TAG)
                    .commit();
        }

        BottomNavigationView bottomNavigationView = layout.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_database:
                                Fragment fragment = fragmentManager.findFragmentByTag(ImageListDbFragment.TAG);
                                if (fragment == null) {
                                    fragment = ImageListDbFragment.newInstance();
                                }
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container_frame, fragment, ImageListDbFragment.TAG)
                                        .commit();
                                return true;
                            case R.id.action_network:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container_frame, ImageListNetworkFragment.newInstance(), ImageListNetworkFragment.TAG)
                                        .commit();
                                return true;
                            case R.id.action_aggregate:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container_frame, ImagesListFragment.newInstance(), ImagesListFragment.TAG)
                                        .commit();
                                return true;
                        }
                        return false;
                    }
                });
        return layout;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
