package com.streamavailability.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.streamavailability.databinding.FragmentHomeBinding;
import com.streamavailability.ui.home.adapter.MovieSliderAdapter;
import com.streamavailability.ui.home.data.Movie;
import com.streamavailability.ui.home.data.MovieResponse;
import com.streamavailability.ui.home.service.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MovieSliderAdapter movieSliderAdapter;
    private List<Movie> movieList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        movieList = new ArrayList<>();
        // you can use the movieList data from API here.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Call<MovieResponse> call = movieService.getTrendingMovies("895d65ebbdd5b9379ad195b07e0ed023");
        call.enqueue(new Callback<MovieResponse>() {
             @Override
             public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                 if (response.isSuccessful()) {
                     MovieResponse movieResponse = response.body();
                     List<Movie> movieList = movieResponse.getResults();
                     for (Movie movie : movieList) {
                         movie.setPoster_path("https://image.tmdb.org/t/p/original" + movie.getPoster_path());
                     }
                     movieSliderAdapter.setMovieList(movieList);
                     movieSliderAdapter.notifyDataSetChanged();
                 } else {
            // handle error
                 }
             }

             @Override
             public void onFailure(Call<MovieResponse> call, Throwable t) {
                 // handle failure
             }
        });


            movieSliderAdapter = new MovieSliderAdapter(movieList, getContext());
        binding.viewPager.setAdapter(movieSliderAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}