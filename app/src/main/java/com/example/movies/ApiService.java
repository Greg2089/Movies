package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ApiService {
    @GET("movie?rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=40")
    @Headers("X-API-KEY:8JMF16Q-PXC4P0S-JMNGCXV-M6CXYKD")
    Single<MovieResponse> loadMovies(@Query("page") int page);
}
