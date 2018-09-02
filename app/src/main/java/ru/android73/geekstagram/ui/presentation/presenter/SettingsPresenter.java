package ru.android73.geekstagram.ui.presentation.presenter;


import android.content.Context;
import android.util.TypedValue;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.AppTheme;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    public void onThemeSelected(Context context, int newThemeId, int currentThemeId) {
        if (currentThemeId == newThemeId) {
            return;
        }
        PreferenceSettingsRepository preferences = new PreferenceSettingsRepository();
        if (newThemeId == R.style.DefaultTheme) {
            preferences.saveTheme(context, AppTheme.BLUE);
        }
        else if (newThemeId == R.style.DarkTheme) {
            preferences.saveTheme(context, AppTheme.GRAY);
        }
        else {
            // TODO write warning to logcat
        }
        getViewState().applyTheme(newThemeId);
    }

    public void onResume(Context context) {
        setCheckedCurrentTheme(context);
    }

    private void setCheckedCurrentTheme(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeName, outValue, true);
        if (outValue.string.equals(context.getString(R.string.theme_name_standard))) {
            getViewState().setCheckedStandardTheme();
        }
        else if (outValue.string.equals(context.getString(R.string.theme_name_dark))) {
            getViewState().setCheckedDarkTheme();
        }
    }
}
