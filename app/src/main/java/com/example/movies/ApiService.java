package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    @Headers("X-API-KEY:8JMF16Q-PXC4P0S-JMNGCXV-M6CXYKD")
    @GET("movie?rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=30")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @Headers("X-API-KEY:8JMF16Q-PXC4P0S-JMNGCXV-M6CXYKD")
    @GET("movie/{idFilms}")
    Single<TrailerResponse> loadTrailers(@Path("idFilms") int idFilms);

}
