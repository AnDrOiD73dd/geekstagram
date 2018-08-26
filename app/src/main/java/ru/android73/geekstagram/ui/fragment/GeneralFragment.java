package ru.android73.geekstagram.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73.geekstagram.R;
import ru.android73.geekstagram.ui.presentation.presenter.GeneralPresenter;
import ru.android73.geekstagram.ui.presentation.view.GeneralView;

public class GeneralFragment extends MvpAppCompatFragment implements GeneralView {

    @InjectPresenter
    GeneralPresenter generalPresenter;

    protected FloatingActionButton floatingActionButton;
    private CoordinatorLayout coordinatorLayout;

    public static GeneralFragment newInstance() {
        GeneralFragment fragment = new GeneralFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_general, container, false);
        coordinatorLayout = layout.findViewById(R.id.fragment_general_root);
        floatingActionButton = layout.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, R.string.notification_image_added_text,
                        Snackbar.LENGTH_LONG).show();
            }
        });
        return layout;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
