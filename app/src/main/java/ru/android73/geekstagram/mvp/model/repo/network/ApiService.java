package ru.android73.geekstagram.mvp.model.repo.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("photos/")
    Single<List<PhotoEntity>> getPhotos(@Query("client_id") String clientId);
}
