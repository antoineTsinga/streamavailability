package com.streamavailability.ui.home.service;

import com.streamavailability.ui.home.data.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
   @GET("trending/all/day")
   Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey);
}