package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.mvp.model.FileManagerImpl;
import ru.android73.geekstagram.mvp.model.repo.FavoritesOnlyRepository;
import ru.android73.geekstagram.mvp.model.repo.SimpleImageRepository;
import ru.android73.geekstagram.mvp.presentation.presenter.FavoritePresenter;
import ru.android73.geekstagram.mvp.presentation.view.FavoriteView;

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
            replaceChildFragment(ImageListDbFragment.newInstance(), ImageListDbFragment.TAG);
        }

        BottomNavigationView bottomNavigationView = layout.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_database:
//                            Fragment fragment = fragmentManager.findFragmentByTag(ImageListDbFragment.TAG);
//                            if (fragment == null) {
//                                fragment = ImageListDbFragment.newInstance();
//                            }
                            Fragment fragment = ImagesListFragment.newInstance(new FavoritesOnlyRepository(new FileManagerImpl(getContext())));
                            replaceChildFragment(fragment, ImagesListFragment.TAG);
                            return true;
                        case R.id.action_network:
                            replaceChildFragment(ImageListNetworkFragment.newInstance(),
                                    ImageListNetworkFragment.TAG);
                            return true;
                        case R.id.action_aggregate:
                            fragment = ImagesListFragment.newInstance(new SimpleImageRepository(new FileManagerImpl(getContext())));
                            replaceChildFragment(fragment, ImagesListFragment.TAG);
                            return true;
                    }
                    return false;
                });
        return layout;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void replaceChildFragment(Fragment fragment, String fragmentTag) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_frame, fragment, fragmentTag)
                .commit();
    }
}
