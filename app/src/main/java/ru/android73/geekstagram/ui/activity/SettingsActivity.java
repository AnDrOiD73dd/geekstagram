package ru.android73.geekstagram.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.SettingsPresenter;
import ru.android73.geekstagram.ui.presentation.view.SettingsView;

public class SettingsActivity extends MvpAppCompatActivity implements SettingsView {
    public static final String TAG = "SettingsActivity";
    @InjectPresenter
    SettingsPresenter mSettingsPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
