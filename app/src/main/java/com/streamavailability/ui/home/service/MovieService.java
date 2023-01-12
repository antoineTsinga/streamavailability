package com.streamavailability.ui.home.service;

import com.streamavailability.ui.home.data.Movie;
import com.streamavailability.ui.home.data.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
   @GET("trending/all/day")
   Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey);

   @GET("movie/{movie_id}/recommendations")
   Call<MovieResponse> getRecommendations(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

   @GET("movie/popular?")
   Call<MovieResponse> getMostPopulars(@Query("api_key") String apiKey);

   @GET("movie/{movie_id}/similar")
   Call<MovieResponse> getSimilarMovies(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

   List<Movie> getInWatchlist();
}