package com.streamavailability.ui.moviesresult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.streamavailability.Adapter.moviesResult.SpinAdapter;
import com.streamavailability.Adapter.moviesResult.SpinnerAdapterGeneric;
import com.streamavailability.Model.AvailableRegion;
import com.streamavailability.Model.AvailableRegionResponse;
import com.streamavailability.Model.Genre;
import com.streamavailability.Model.GenreResponse;
import com.streamavailability.Model.Provider;
import com.streamavailability.Model.ProviderResponse;
import com.streamavailability.R;
import com.streamavailability.service.MovieService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesResult extends AppCompatActivity {

    private Spinner spinnerGenre;
    private Spinner spinnerProvider;
    private Spinner spinnerRegions;


    private SpinnerAdapterGeneric<Genre> genreAdapter;
    private SpinnerAdapterGeneric<Provider> providerAdapter;
    private SpinnerAdapterGeneric<AvailableRegion> regionAdapter;

    private ArrayList<Genre> genres;
    private ArrayList<Provider> providers;
    private ArrayList<AvailableRegion> regions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_result);

        setTitle("");

        genreAdapter = new SpinnerAdapterGeneric(this,
                android.R.layout.simple_spinner_item,genres);
        genreAdapter.setField("name");
        spinnerGenre = findViewById(R.id.genre_filter);


        providerAdapter = new SpinnerAdapterGeneric(this,
                android.R.layout.simple_spinner_item,providers);
        providerAdapter.setField("providerName");
        spinnerProvider = findViewById(R.id.platform_filter);


        regionAdapter = new SpinnerAdapterGeneric(this,
                android.R.layout.simple_spinner_item,regions);
        regionAdapter.setField("nativeName");
        spinnerRegions = findViewById(R.id.region_filter2);

        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fetchDataGenre(apiKey, retrofit);
        fetchDataProvider(apiKey, retrofit);
        fetchDataRegion(apiKey, retrofit);

        genres = new ArrayList<>();
        providers = new ArrayList<>();
        regions = new ArrayList<>();


        genreAdapter.setValues(genres);
        providerAdapter.setValues(providers);
        regionAdapter.setValues(regions);

        spinnerGenre.setAdapter(genreAdapter); // Set the custom adapter to the spinner
        spinnerProvider.setAdapter(providerAdapter);
        spinnerRegions.setAdapter(regionAdapter);
        // You can create an anonymous listener to handle the event when is selected an spinner item


        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Genre genre = genreAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(MoviesResult.this, "ID: " + genre.getId() + "\nName: " + genre.getName(),
                        Toast.LENGTH_SHORT).show(); //TODO : call api there to apply filter
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });


        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Provider provider = providerAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(MoviesResult.this, "ID: " + provider.getProviderId() + "\nName: " + provider.getProviderName(),
                        Toast.LENGTH_SHORT).show(); //TODO : call api there to apply filter
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                AvailableRegion provider = regionAdapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(MoviesResult.this, "ID: " + provider.getIso() + "\nName: " + provider.getNativeName(),
                        Toast.LENGTH_SHORT).show(); //TODO : call api there to apply filter
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });

    }

    private void fetchDataGenre(String apiKey,Retrofit retrofit) {
        MovieService movieService = retrofit.create(MovieService.class);
        Call<GenreResponse> call = movieService.getGenres(apiKey);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (response.isSuccessful()) {
                    GenreResponse genreResponse = response.body();
                    assert genreResponse != null;
                    ArrayList<Genre> genresList = genreResponse.getGenres();

                    genreAdapter.setValues(genresList);
                    genreAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });
    }

    private void fetchDataProvider(String apiKey,Retrofit retrofit) {
        System.out.println("----------------------------------------------------Launch------------------------");
        MovieService movieService = retrofit.create(MovieService.class);
        Call<ProviderResponse> call = movieService.getProviderMovie(apiKey);
        call.enqueue(new Callback<ProviderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProviderResponse> call, @NonNull Response<ProviderResponse> response) {
                if (response.isSuccessful()) {
                    ProviderResponse providerResponse = response.body();
                    assert providerResponse != null;
                    ArrayList<Provider> providersList = providerResponse.getResults();
                    providersList.sort(Comparator.comparing(Provider::getProviderName));


                    Log.w("Fetch provider data", providersList.stream().map(p -> p.getProviderName()).collect(Collectors.toList()).toString());
                    providerAdapter.setValues(providersList);
                    providerAdapter.notifyDataSetChanged();
                }else{

                }
            }
            @Override
            public void onFailure(@NonNull Call<ProviderResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });
    }


    private void fetchDataRegion(String apiKey,Retrofit retrofit) {
        MovieService movieService = retrofit.create(MovieService.class);
        Call<AvailableRegionResponse> call = movieService.getAvailableRegion(apiKey);
        call.enqueue(new Callback<AvailableRegionResponse>() {
            @Override
            public void onResponse(@NonNull Call<AvailableRegionResponse> call, @NonNull Response<AvailableRegionResponse> response) {
                if (response.isSuccessful()) {
                    AvailableRegionResponse regionResponse = response.body();
                    assert regionResponse != null;
                    ArrayList<AvailableRegion> regionsList = regionResponse.getResults();



                    regionAdapter.setValues(regionsList);
                    regionAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<AvailableRegionResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });
    }

}