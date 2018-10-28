package ru.android73.geekstagram.ui.fragment;

import android.support.v4.app.Fragment;

public class TabFragmentFactory {

    private String[] titles;

    public TabFragmentFactory(String[] titles) {
        this.titles = titles;
    }

    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ImagesListFragment.newInstance();
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
