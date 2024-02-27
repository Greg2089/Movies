package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewFavouriteMovies);
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMovies.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.onMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(
                        FavouriteMoviesActivity.this,
                        movie);
                startActivity(intent);
            }
        });

        FavouriteMoviesViewModel favouriteMoviesViewModel = new ViewModelProvider(this).get(
                FavouriteMoviesViewModel.class);
        favouriteMoviesViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}