package ru.android73.geekstagram.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private final TabFragmentFactory fragmentFactory;

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager, TabFragmentFactory fragmentFactory) {
        super(fragmentManager);
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return fragmentFactory.getFragmentsCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentFactory.getFragmentTitle(position);
    }

}
