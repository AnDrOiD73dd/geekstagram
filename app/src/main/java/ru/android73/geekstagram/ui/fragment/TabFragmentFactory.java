package ru.android73.geekstagram.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import ru.android73.geekstagram.mvp.model.FileManagerImpl;
import ru.android73.geekstagram.mvp.model.repo.SimpleImageRepository;

public class TabFragmentFactory {

    private final Context context;
    private String[] titles;

    public TabFragmentFactory(Context context, String[] titles) {
        this.context = context;
        this.titles = titles;
    }

    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ImagesListFragment.newInstance(new SimpleImageRepository(new FileManagerImpl(context)));
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
