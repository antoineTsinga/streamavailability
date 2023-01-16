package com.streamavailability.ui.moviesresult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.streamavailability.Adapter.home.SectionMovieAdapter;
import com.streamavailability.Adapter.moviesResult.SpinAdapter;
import com.streamavailability.Adapter.moviesResult.SpinnerAdapterGeneric;
import com.streamavailability.Model.AvailableRegion;
import com.streamavailability.Model.AvailableRegionResponse;
import com.streamavailability.Model.Genre;
import com.streamavailability.Model.GenreResponse;
import com.streamavailability.Model.Movie;
import com.streamavailability.Model.MovieResponse;
import com.streamavailability.Model.Provider;
import com.streamavailability.Model.ProviderResponse;
import com.streamavailability.Model.User;
import com.streamavailability.R;
import com.streamavailability.service.MovieService;
import com.streamavailability.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private RecyclerView resultMovieRecyclerView;


    private SpinnerAdapterGeneric<Genre> genreAdapter;
    private SpinnerAdapterGeneric<Provider> providerAdapter;
    private SpinnerAdapterGeneric<AvailableRegion> regionAdapter;
    private SectionMovieAdapter resultsMovieAdapter;

    private ArrayList<Genre> genres;
    private ArrayList<Provider> providers;
    private ArrayList<AvailableRegion> regions;
    private List<Movie> movieResults;

    private String incomingGenre;
    private String selectedRegion;
    private String selectedProvider;
    private String selectedGenre;
    private int page = 1;
    private String query;
    private Boolean startRegions = true;
    private Boolean startGenre = true;
    private Boolean startProvider = true;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_result);




        setTitle("");

        user = (User) getIntent().getSerializableExtra("user");

        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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


        resultMovieRecyclerView = findViewById(R.id.recycler_view_movie_result);

        movieResults = new ArrayList<>();

        resultsMovieAdapter = new SectionMovieAdapter(movieResults, this );
        resultsMovieAdapter.setUser(user);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);



        resultMovieRecyclerView.setLayoutManager(gridLayoutManager);
        resultMovieRecyclerView.setAdapter(resultsMovieAdapter);

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieResponse> call = movieService.getTrendingMovies(apiKey);
        //

        String genreArg = getIntent().getStringExtra("genre");

        if(genreArg !=null) {

            System.out.println("------ici----------" + genreArg);
            incomingGenre = genreArg;
            getIntent().putExtra("genre", (String) null);
        }else{
            HomeFragment.fetchData(movieService.getMostPopulars(apiKey), resultsMovieAdapter);
        }

        fetchDataGenre(apiKey, retrofit);
        fetchDataProvider(apiKey, retrofit);
        fetchDataRegion(apiKey, retrofit, regionAdapter);

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
                selectedGenre = String.valueOf(genre.getId());
                page=1;

                if(startGenre){
                    startGenre = false;
                    fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, incomingGenre,page, false);
                }else {
                    fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, selectedGenre,page, false);
                }

                // Here you can do the action you want to...
                Toast.makeText(MoviesResult.this, "ID: " + genre.getId() + "\nName: " + genre.getName(),
                        Toast.LENGTH_SHORT).show();
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
                selectedProvider = String.valueOf(provider.getProviderId());
                page=1;
                if(startProvider){
                    startProvider = false;
                    fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, incomingGenre,page, false);
                }else {
                    fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, selectedGenre,page, false);
                }
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
                AvailableRegion region = regionAdapter.getItem(position);
                selectedRegion = region.getIso();
                page=1;
                if(startRegions){
                    startRegions = false;
                    fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, incomingGenre,page, false);
                }else {
                    fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, selectedGenre,page, false);
                }
                // Here you can do the action you want to...
                Toast.makeText(MoviesResult.this, "ID: " + region.getIso() + "\nName: " + region.getNativeName(),
                        Toast.LENGTH_SHORT).show(); //TODO : call api there to apply filter
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });



        SearchView searchView = findViewById(R.id.search_view_search_view_result);

        Button cancel = findViewById(R.id.cancel_result);
        cancel.setOnClickListener(v ->{
            HomeFragment.fetchData(movieService.getMostPopulars(apiKey), resultsMovieAdapter);
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 1;
                searchMovie(apiKey, retrofit, query,page, false);
                selectedGenre=null;
                selectedProvider=null;
                selectedRegion=null;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        TextView textView = findViewById(R.id.see_more_result);

        textView.setOnClickListener(v->{
            page++;
            if(selectedGenre==null && selectedProvider==null&& selectedRegion==null){
                searchMovie(apiKey, retrofit, query, page, true);
            }else{
                //fetchFilterMovie(apiKey,retrofit, selectedProvider, selectedRegion, selectedGenre,page, true);
            }
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

                    Genre genre = new Genre();
                    genre.setName("genre");
                    genresList.add(0, genre);
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

                    Provider provider = new Provider();
                    provider.setProviderName("provider");
                    providersList.add(0, provider);
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


    public static void fetchDataRegion(String apiKey,Retrofit retrofit, SpinnerAdapterGeneric<AvailableRegion> regionAdapter) {
        MovieService movieService = retrofit.create(MovieService.class);
        Call<AvailableRegionResponse> call = movieService.getAvailableRegion(apiKey);
        call.enqueue(new Callback<AvailableRegionResponse>() {
            @Override
            public void onResponse(@NonNull Call<AvailableRegionResponse> call, @NonNull Response<AvailableRegionResponse> response) {
                if (response.isSuccessful()) {
                    AvailableRegionResponse regionResponse = response.body();
                    assert regionResponse != null;
                    ArrayList<AvailableRegion> regionsList = regionResponse.getResults();

                    AvailableRegion region = new AvailableRegion();
                    region.setNativeName("country");
                    regionsList.add(0, region);

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

    private void fetchFilterMovie(String apiKey,Retrofit retrofit, String provider, String region, String genre,int page, boolean seeMore){
        System.out.println("-----ici-------fetch" );
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieResponse> call = movieService.getFilterMovie(apiKey, "en-US", region, page, region, provider, genre);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    assert movieResponse != null;
                    List<Movie> movieList = movieResponse.getResults().stream().filter(movie -> movie.getPoster_path() != null).collect(Collectors.toList());

                    for (Movie movie : movieList) {
                        movie.setPoster_path("https://image.tmdb.org/t/p/original" + movie.getPoster_path());
                    }

                    if(seeMore){
                        movieResults.addAll(movieList);
                        resultsMovieAdapter.setMovieList(movieResults);
                        resultsMovieAdapter.notifyDataSetChanged();
                    }else {
                        resultsMovieAdapter.setMovieList(movieList);
                        resultsMovieAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });
    }

    private void searchMovie(String apiKey,Retrofit retrofit, String query,int page, boolean seeMore){
        System.out.println("-----ici-------search" );
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieResponse> call = movieService.searchMovie(apiKey, "en-US", query, page,"US");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    assert movieResponse != null;
                    List<Movie> movieList = movieResponse.getResults().stream().filter(movie -> movie.getPoster_path() != null).collect(Collectors.toList());

                    for (Movie movie : movieList) {
                        movie.setPoster_path("https://image.tmdb.org/t/p/original" + movie.getPoster_path());
                    }

                    if(seeMore){
                        movieResults.addAll(movieList);
                        resultsMovieAdapter.setMovieList(movieResults);
                        resultsMovieAdapter.notifyDataSetChanged();
                    }else{
                        resultsMovieAdapter.setMovieList(movieList);
                        resultsMovieAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });
    }


}