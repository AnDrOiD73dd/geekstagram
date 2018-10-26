package ru.android73.geekstagram.mvp.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface GeekstagramDao {

    @Query("SELECT * FROM ImageListItem")
    Single<List<ImageListItem>> getAll();

    @Query("SELECT * FROM ImageListItem WHERE image_uri==:imagePath")
    ImageListItem get(String imagePath);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ImageListItem entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ImageListItem> entities);

    @Update
    void update(ImageListItem entity);

    @Delete
    void delete(ImageListItem entity);
}
