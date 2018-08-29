package ru.android73.geekstagram.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.fragment.GeneralFragment;
import ru.android73.geekstagram.ui.presentation.presenter.MainPresenter;
import ru.android73.geekstagram.ui.presentation.view.MainView;

public class MainActivity extends BaseActivity implements MainView,
        NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainPresenter mainPresenter;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.activity_main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MvpAppCompatFragment fragment = GeneralFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
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
}
