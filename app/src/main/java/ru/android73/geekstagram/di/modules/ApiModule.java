package ru.android73.geekstagram.di.modules;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.android73.geekstagram.mvp.model.FileManager;
import ru.android73.geekstagram.mvp.model.FileManagerImpl;
import ru.android73.geekstagram.mvp.model.repo.network.ApiService;
import ru.android73.geekstagram.mvp.model.repo.network.PhotoLoader;
import ru.android73.geekstagram.mvp.model.repo.network.PhotoLoaderImpl;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumResource;
import ru.android73.geekstagram.mvp.model.theme.ThemeMapperEnumString;

@Module(includes = ContextModule.class)
public class ApiModule {

    @Provides
    public FileManager fileManager(Context context) {
        return new FileManagerImpl(context);
    }

    @Provides
    public ThemeMapperEnumString themeMapperEnumString() {
        return new ThemeMapperEnumString();
    }

    @Provides
    public ThemeMapperEnumResource themeMapperEnumResource() {
        return new ThemeMapperEnumResource();
    }

    @Singleton
    @Provides
    public PhotoLoader providePhotoLoader(ApiService api) {
        return new PhotoLoaderImpl(api);
    }

    @Named("baseUrl")
    @Provides
    public String getBaseUrl() {
        return "https://api.unsplash.com/";
    }

    @Singleton
    @Provides
    public ApiService apiService(OkHttpClient client, Gson gson, @Named("baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

    @Singleton
    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    public Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
