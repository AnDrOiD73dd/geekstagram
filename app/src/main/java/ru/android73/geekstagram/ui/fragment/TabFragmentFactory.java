package ru.android73.geekstagram.ui.fragment;

import android.support.v4.app.Fragment;

import javax.inject.Inject;
import javax.inject.Named;

import ru.android73.geekstagram.GeekstagramApp;
import ru.android73.geekstagram.mvp.model.repo.ImageRepository;

public class TabFragmentFactory {

    @Named("Common")
    @Inject
    ImageRepository imageRepository;
    private String[] titles;

    public TabFragmentFactory(String[] titles) {
        this.titles = titles;
        GeekstagramApp.getInstance().getAppComponent().inject(this);
    }

    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ImagesListFragment.newInstance(imageRepository);
            case 1:
                return FavoriteFragment.newInstance();
            default:
                throw new IllegalArgumentException("Could not create fragment for position " + position);
        }
    }

    public int getFragmentsCount() {
        return titles.length;
    }

    public CharSequence getFragmentTitle(int position) {
        return titles[position];
    }
}
