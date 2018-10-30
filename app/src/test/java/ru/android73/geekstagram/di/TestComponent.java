package ru.android73.geekstagram.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.android73.geekstagram.ImagesListPresenterUnitTest;
import ru.android73.geekstagram.di.modules.TestApiModule;
import ru.android73.geekstagram.di.modules.TestCacheModule;
import ru.android73.geekstagram.di.modules.TestRepoModule;
import ru.android73.geekstagram.mvp.presentation.presenter.ImagesListPresenter;

@Singleton
@Component(modules = {TestCacheModule.class, TestApiModule.class, TestRepoModule.class})
public interface TestComponent {
    void inject(ImagesListPresenter presenter);

    void inject(ImagesListPresenterUnitTest imagesListPresenterUnitTest);
}
