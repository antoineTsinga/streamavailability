package com.streamavailability.ui.moviesresult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.protobuf.Internal;
import com.streamavailability.Adapter.moviesResult.SpinAdapter;
import com.streamavailability.Model.Genre;
import com.streamavailability.Model.GenreResponse;
import com.streamavailability.Model.Movie;
import com.streamavailability.Model.MovieResponse;
import com.streamavailability.R;
import com.streamavailability.service.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesResult extends AppCompatActivity {

    private Spinner spinnerGenre;
    private SpinAdapter adapter;

    private ArrayList<Genre> genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_result);




        adapter = new SpinAdapter(this,
                android.R.layout.simple_spinner_item,genres);
        spinnerGenre = findViewById(R.id.genre_filter);

        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);


        Call<GenreResponse> call = movieService.getGenres(apiKey);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (response.isSuccessful()) {
                    GenreResponse genreResponse = response.body();
                    assert genreResponse != null;
                    ArrayList<Genre> genresList = genreResponse.getGenres();

                    adapter.setValues(genresList);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });

        genres = new ArrayList<>();
        adapter.setValues(genres);

        spinnerGenre.setAdapter(adapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Genre genre = adapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(MoviesResult.this, "ID: " + genre.getId() + "\nName: " + genre.getName(),
                        Toast.LENGTH_SHORT).show(); //TODO : call api there to apply filter
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });

    }




}