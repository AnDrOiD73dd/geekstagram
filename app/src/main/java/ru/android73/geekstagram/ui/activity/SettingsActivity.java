package ru.android73.geekstagram.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.CompoundButton;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.ui.presentation.presenter.SettingsPresenter;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

public class SettingsActivity extends MvpAppCompatActivity implements SettingsView,
        CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    SettingsPresenter settingsPresenter;
    private AppCompatRadioButton rbThemeDark;
    private AppCompatRadioButton rbThemeStandard;
    private int currentThemeId;

    public static Intent getIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

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
        setContentView(R.layout.activity_settings);
        rbThemeStandard = findViewById(R.id.rb_theme_standard);
        rbThemeStandard.setOnCheckedChangeListener(this);
        rbThemeDark = findViewById(R.id.rb_theme_dark);
        rbThemeDark.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingsPresenter.onResume(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_theme_standard:
                if (isChecked) {
                    settingsPresenter.onThemeSelected(this, R.style.DefaultTheme);
                }
                break;
            case R.id.rb_theme_dark:
                if (isChecked) {
                    settingsPresenter.onThemeSelected(this, R.style.DarkTheme);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setStandardThemeChecked() {
        rbThemeStandard.setChecked(true);
    }

    @Override
    public void setDarkThemeChecked() {
        rbThemeDark.setChecked(true);
    }
}
