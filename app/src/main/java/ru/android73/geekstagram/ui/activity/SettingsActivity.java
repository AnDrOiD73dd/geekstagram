package ru.android73.geekstagram.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.TypedValue;
import android.widget.CompoundButton;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.android73.geekstagram.R;
import ru.android73.geekstagram.mvp.model.repo.ThemeRepository;
import ru.android73.geekstagram.mvp.model.repo.ThemeRepositoryImpl;
import ru.android73.geekstagram.mvp.model.theme.AppTheme;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumResource;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumString;
import ru.android73.geekstagram.mvp.presentation.presenter.SettingsPresenter;
import ru.android73.geekstagram.mvp.presentation.view.SettingsView;

public class SettingsActivity extends BaseActivity implements SettingsView {

    @InjectPresenter
    SettingsPresenter settingsPresenter;
    private AppCompatRadioButton themeDarkRadioButton;
    private AppCompatRadioButton themeStandardRadioButton;
    private CompoundButton.OnCheckedChangeListener themeChooserListener;
    private ThemeMapperEnumResource themeMapperEnumResource;

    public static Intent getIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @ProvidePresenter
    public SettingsPresenter provideSettingsPresenter() {
        ThemeRepository preferences = new ThemeRepositoryImpl(getApplicationContext(),
                new ThemeMapperEnumString());
        return new SettingsPresenter(AndroidSchedulers.mainThread(), preferences);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        themeMapperEnumResource = new ThemeMapperEnumResource();
        themeStandardRadioButton = findViewById(R.id.rb_theme_standard);
        themeDarkRadioButton = findViewById(R.id.rb_theme_dark);
        themeChooserListener = (buttonView, isChecked) -> {
            switch (buttonView.getId()) {
                case R.id.rb_theme_standard:
                    if (isChecked) {
                        AppTheme currentTheme = themeMapperEnumResource.toEnum(currentThemeId);
                        AppTheme newTheme = themeMapperEnumResource.toEnum(R.style.DefaultTheme);
                        settingsPresenter.onThemeSelected(newTheme, currentTheme);
                    }
                    break;
                case R.id.rb_theme_dark:
                    if (isChecked) {
                        AppTheme currentTheme = themeMapperEnumResource.toEnum(currentThemeId);
                        AppTheme newTheme = themeMapperEnumResource.toEnum(R.style.DarkTheme);
                        settingsPresenter.onThemeSelected(newTheme, currentTheme);
                    }
                    break;
                default:
                    break;
            }
        };
        themeStandardRadioButton.setOnCheckedChangeListener(themeChooserListener);
        themeDarkRadioButton.setOnCheckedChangeListener(themeChooserListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckedCurrentTheme();
    }

    @Override
    public void setCheckedStandardTheme() {
        themeStandardRadioButton.setChecked(true);
    }

    @Override
    public void setCheckedDarkTheme() {
        themeDarkRadioButton.setChecked(true);
    }

    @Override
    public void applyTheme(AppTheme theme) {
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
        } else if (themeAttributeValue.string.equals(getString(R.string.theme_name_dark))) {
            setCheckedDarkTheme();
        }
    }
}
