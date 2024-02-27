package com.example.movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM favourite_movies")
    LiveData<List<Movie>> getAllFavoriteMovie();

    @Query("SELECT * FROM favourite_movies WHERE id = :movieTd")
    LiveData<Movie> getFavouriteMovie(int movieTd);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    Completable removeMovie(int movieId);


}
