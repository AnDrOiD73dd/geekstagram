package ru.android73.geekstagram.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.fragment.CustomFragmentPagerAdapter;
import ru.android73.geekstagram.ui.fragment.ImagesListFragment;
import ru.android73.geekstagram.ui.fragment.TabFragmentFactory;
import ru.android73.geekstagram.ui.fragment.ViewerFragment;
import ru.android73.geekstagram.mvp.presentation.presenter.MainPresenter;
import ru.android73.geekstagram.mvp.presentation.view.MainView;

public class MainActivity extends BaseActivity implements MainView,
        NavigationView.OnNavigationItemSelectedListener, ImagesListFragment.OnFragmentInteractionListener {

    @InjectPresenter
    MainPresenter mainPresenter;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.activity_main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabFragmentFactory tabFragmentFactory = new TabFragmentFactory(getTabTitles());
        CustomFragmentPagerAdapter customFragmentPagerAdapter
                = new CustomFragmentPagerAdapter(getSupportFragmentManager(), tabFragmentFactory);
        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.view_pager_container);
        viewPager.setAdapter(customFragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showSettingsActivity() {
        startActivity(SettingsActivity.getIntent(this));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about_developer:
//                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                ((BaseFragment) fragment).showAboutDeveloper();
                break;
            case R.id.nav_settings:
                mainPresenter.onMenuClick();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClicked(String imageUri) {
        showViewerFragment(imageUri);
    }

    private void showViewerFragment(String imageUri) {
        MvpAppCompatFragment fragment = ViewerFragment.newInstance(imageUri);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, ViewerFragment.TAG)
                .addToBackStack(ViewerFragment.TAG)
                .commit();
    }

    private String[] getTabTitles() {
        String homeTab = getResources().getString(R.string.tab_name_home);
        String favoriteTab = getResources().getString(R.string.tab_name_favorite);
        return new String[] {homeTab, favoriteTab};
    }
}
