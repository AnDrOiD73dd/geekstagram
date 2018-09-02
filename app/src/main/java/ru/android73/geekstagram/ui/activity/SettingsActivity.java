package ru.android73.geekstagram.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.TypedValue;
import android.widget.CompoundButton;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.SettingsPresenter;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

public class SettingsActivity extends BaseActivity implements SettingsView {

    @InjectPresenter
    SettingsPresenter settingsPresenter;
    private AppCompatRadioButton rbThemeDark;
    private AppCompatRadioButton rbThemeStandard;
    private CompoundButton.OnCheckedChangeListener themeChooserListener;

    public static Intent getIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rbThemeStandard = findViewById(R.id.rb_theme_standard);
        rbThemeDark = findViewById(R.id.rb_theme_dark);
        themeChooserListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.rb_theme_standard:
                        if (isChecked) {
                            settingsPresenter.onThemeSelected(SettingsActivity.this,
                                    R.style.DefaultTheme, currentThemeId);
                        }
                        break;
                    case R.id.rb_theme_dark:
                        if (isChecked) {
                            settingsPresenter.onThemeSelected(SettingsActivity.this,
                                    R.style.DarkTheme, currentThemeId);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        rbThemeStandard.setOnCheckedChangeListener(themeChooserListener);
        rbThemeDark.setOnCheckedChangeListener(themeChooserListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckedCurrentTheme();
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

    private void setCheckedCurrentTheme() {
        TypedValue themeAttributeValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.themeName, themeAttributeValue, true);
        if (themeAttributeValue.string.equals(getString(R.string.theme_name_standard))) {
            setCheckedStandardTheme();
        }
        else if (themeAttributeValue.string.equals(getString(R.string.theme_name_dark))) {
            setCheckedDarkTheme();
        }
    }
}
