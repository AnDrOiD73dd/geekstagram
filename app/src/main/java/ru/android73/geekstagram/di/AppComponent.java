package ru.android73.geekstagram.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.android73.geekstagram.di.modules.AppModule;
import ru.android73.geekstagram.di.modules.RepoModule;
import ru.android73.geekstagram.mvp.presentation.presenter.ImagesListPresenter;
import ru.android73.geekstagram.ui.fragment.FavoriteFragment;
import ru.android73.geekstagram.ui.fragment.TabFragmentFactory;


@Singleton
@Component(modules = {
        AppModule.class,
        RepoModule.class
})
public interface AppComponent {
    void inject(TabFragmentFactory tabFragmentFactory);

    void inject(ImagesListPresenter imagesListPresenter);

    void inject(FavoriteFragment favoriteFragment);
}
