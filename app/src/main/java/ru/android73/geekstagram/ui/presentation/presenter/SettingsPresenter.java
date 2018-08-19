package ru.android73.geekstagram.ui.presentation.presenter;


import android.content.Context;
import android.util.TypedValue;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.common.PreferenceSettingsRepository;
import ru.android73.geekstagram.ui.activity.SettingsActivity;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    public void onThemeSelected(Context context, int themeId) {
        PreferenceSettingsRepository preferences = new PreferenceSettingsRepository();
        preferences.saveTheme(context, themeId);
    }

    public void onResume(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeName, outValue, true);
        if (outValue.string.equals(context.getString(R.string.theme_name_standard))) {
            getViewState().setStandardThemeChecked();
        }
        else if (outValue.string.equals(context.getString(R.string.theme_name_dark))) {
            getViewState().setDarkThemeChecked();
        }
    }
}
