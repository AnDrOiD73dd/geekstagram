package ru.android73.geekstagram.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.CompoundButton;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.SettingsPresenter;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

public class SettingsActivity extends BaseActivity implements SettingsView,
        CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    SettingsPresenter settingsPresenter;
    private AppCompatRadioButton rbThemeDark;
    private AppCompatRadioButton rbThemeStandard;

    public static Intent getIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
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
                    settingsPresenter.onThemeSelected(this, R.style.DefaultTheme, currentThemeId);
                }
                break;
            case R.id.rb_theme_dark:
                if (isChecked) {
                    settingsPresenter.onThemeSelected(this, R.style.DarkTheme, currentThemeId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setCheckedStandardTheme() {
        rbThemeStandard.setChecked(true);
    }

    @Override
    public void setCheckedDarkTheme() {
        rbThemeDark.setChecked(true);
    }

    @Override
    public void applyTheme(int themeId) {
        TaskStackBuilder.create(this)
                .addNextIntent(new Intent(this, MainActivity.class))
                .addNextIntent(this.getIntent())
                .startActivities();
    }
}
