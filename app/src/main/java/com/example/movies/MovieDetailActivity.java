package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity";
    private MovieDetailViewModel viewModel;


    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailers;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewReviews;
    private ReviewAdapter reviewsAdapter;
    private ImageView imageViewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        reviewsAdapter = new ReviewAdapter();
        recyclerViewReviews.setAdapter(reviewsAdapter);
        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);
        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.onTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewList) {
                reviewsAdapter.setReviews(reviewList);
            }
        });
        viewModel.loadReviews(movie.getId());

        Drawable starOff = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_on);

        viewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDB) {
                if (movieFromDB == null) {
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertMovie(movie);
                        }
                    });
                } else {
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });

    }

    /**
     * Фабричный метод по созданию интентов *
     */
    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }


    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        imageViewStar = findViewById(R.id.imageViewStar);
    }
}