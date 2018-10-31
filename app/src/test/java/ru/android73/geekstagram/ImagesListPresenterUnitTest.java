package ru.android73.geekstagram;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import ru.android73.geekstagram.di.AppComponent;
import ru.android73.geekstagram.di.DaggerTestComponent;
import ru.android73.geekstagram.di.TestComponent;
import ru.android73.geekstagram.di.modules.TestApiModule;
import ru.android73.geekstagram.di.modules.TestCacheModule;
import ru.android73.geekstagram.di.modules.TestRepoModule;
import ru.android73.geekstagram.mvp.model.entity.DataType;
import ru.android73.geekstagram.mvp.model.entity.ImageListItem;
import ru.android73.geekstagram.mvp.model.repo.photo.ImageRepository;
import ru.android73.geekstagram.mvp.presentation.presenter.ImagesListPresenter;
import ru.android73.geekstagram.mvp.presentation.view.ImagesListView;

public class ImagesListPresenterUnitTest {

    private ImagesListPresenter presenter;
    private TestScheduler scheduler;
    @Named("Realm")
    @Inject
    ImageRepository imageRepository;
    @Mock
    private ImagesListView imagesListView;
    @Inject
    AppComponent appComponent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scheduler = new TestScheduler();
    }

    @Test
    public void attachView() {
        List<ImageListItem> imageListItems = new ArrayList<>();
        ImageListItem item = new ImageListItem("http://roga-i-kopita", false, DataType.LOCAL);
        imageListItems.add(item);

        TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ImageRepository realmImageRepository() {
                        ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
                        Mockito.when(imageRepository.getPhotos()).thenReturn(Single.just(imageListItems));
                        return imageRepository;
                    }
                })
                .testCacheModule(new TestCacheModule())
                .testApiModule(new TestApiModule())
                .build();

        testComponent.inject(this);
        presenter = Mockito.spy(new ImagesListPresenter(scheduler, imageRepository, appComponent));
        testComponent.inject(presenter);

        presenter.attachView(imagesListView);
        Mockito.verify(presenter).loadPhotos();
    }

    @Test
    public void loadPhotosSuccess() {
        List<ImageListItem> imageListItems = new ArrayList<>();
        ImageListItem item = new ImageListItem("http://roga-i-kopita", false, DataType.LOCAL);
        imageListItems.add(item);

        TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ImageRepository realmImageRepository() {
                        ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
                        Mockito.when(imageRepository.getPhotos()).thenReturn(Single.just(imageListItems));
                        return imageRepository;
                    }
                })
                .testCacheModule(new TestCacheModule())
                .testApiModule(new TestApiModule())
                .build();

        testComponent.inject(this);
        presenter = Mockito.spy(new ImagesListPresenter(scheduler, imageRepository, appComponent));
        testComponent.inject(presenter);

        presenter.attachView(imagesListView);
        Mockito.verify(presenter).loadPhotos();
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        Mockito.verify(imagesListView).updatePhotosList();
    }

    @Test
    public void loadPhotosFailure() {
        Throwable throwable = new Throwable("Error during load photo");

        TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ImageRepository realmImageRepository() {
                        ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
                        Mockito.when(imageRepository.getPhotos()).thenReturn(Single.error(throwable));
                        return imageRepository;
                    }
                })
                .testCacheModule(new TestCacheModule())
                .testApiModule(new TestApiModule())
                .build();

        testComponent.inject(this);
        presenter = Mockito.spy(new ImagesListPresenter(scheduler, imageRepository, appComponent));
        testComponent.inject(presenter);

        presenter.attachView(imagesListView);
        Mockito.verify(presenter).loadPhotos();
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        Mockito.verify(imagesListView).showErrorLoadPhoto();
    }

    @Test
    public void onDeleteConfirmedSuccess() {
        List<ImageListItem> imageListItems = new ArrayList<>();
        ImageListItem item = new ImageListItem("http://roga-i-kopita", false, DataType.LOCAL);
        imageListItems.add(item);
        TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ImageRepository realmImageRepository() {
                        ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
                        Mockito.when(imageRepository.getPhotos()).thenReturn(Single.just(imageListItems));
                        Mockito.when(imageRepository.remove(item)).thenReturn(Completable.complete());
                        return imageRepository;
                    }
                })
                .testCacheModule(new TestCacheModule())
                .testApiModule(new TestApiModule())
                .build();

        testComponent.inject(this);
        presenter = Mockito.spy(new ImagesListPresenter(scheduler, imageRepository, appComponent));
        testComponent.inject(presenter);

        presenter.attachView(imagesListView);
        int position = 100;
        presenter.onDeleteConfirmed(item, position);
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        Mockito.verify(imagesListView).showMessageImageDeleted();
        Mockito.verify(imagesListView).onItemDeleted(position);
    }

    @Test
    public void onDeleteConfirmedFailure() {
        List<ImageListItem> imageListItems = new ArrayList<>();
        ImageListItem item = new ImageListItem("http://roga-i-kopita", false, DataType.LOCAL);
        imageListItems.add(item);
        Throwable throwable = new Throwable("Error during load photo");
        TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public ImageRepository realmImageRepository() {
                        ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
                        Mockito.when(imageRepository.getPhotos()).thenReturn(Single.just(imageListItems));
                        Mockito.when(imageRepository.remove(item)).thenReturn(Completable.error(throwable));
                        return imageRepository;
                    }
                })
                .testCacheModule(new TestCacheModule())
                .testApiModule(new TestApiModule())
                .build();

        testComponent.inject(this);
        presenter = Mockito.spy(new ImagesListPresenter(scheduler, imageRepository, appComponent));
        testComponent.inject(presenter);

        presenter.attachView(imagesListView);
        int position = 100;
        presenter.onDeleteConfirmed(item, position);
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        Mockito.verify(imagesListView).showErrorImageDeleted();
    }
}
