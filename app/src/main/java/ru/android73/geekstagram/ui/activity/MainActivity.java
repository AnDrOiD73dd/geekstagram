package ru.android73.geekstagram.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.ui.presentation.presenter.MainPresenter;
import ru.android73.geekstagram.ui.presentation.view.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainPresenter mainPresenter;

    private BottomNavigationView bottomNavigation;
    private int currentThemeId;

    @Override
    public void setTheme(int resid) {
        currentThemeId = getUserTheme();
        super.setTheme(currentThemeId);
    }

    private int getUserTheme() {
        PreferenceSettingsRepository preferences = new PreferenceSettingsRepository();
        return preferences.getCurrentTheme(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getUserTheme() != currentThemeId) {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
            case R.id.action_add:
            case R.id.action_chat:
            case R.id.action_profile:
                break;
            case R.id.action_menu:
                mainPresenter.onMenuClick();
                break;
        }
        return true;
    }

    @Override
    public void showSettingsActivity() {
        startActivity(SettingsActivity.getIntent(this));
    }
}
