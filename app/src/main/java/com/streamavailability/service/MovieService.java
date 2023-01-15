package com.streamavailability.service;

import com.streamavailability.Model.AvailableRegionResponse;
import com.streamavailability.Model.Genre;
import com.streamavailability.Model.GenreResponse;
import com.streamavailability.Model.Movie;
import com.streamavailability.Model.MovieResponse;
import com.streamavailability.Model.ProviderResponse;

import java.util.ArrayList;
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


   @GET("movie/{movie_id}")
   Call<Movie> getMoviesDetails(@Path("movie_id") String movieId,
                                        @Query("api_key") String apiKey,
                                        @Query("append_to_response") String appendToResponse
                                        );

   @GET("genre/movie/list")
   Call<GenreResponse> getGenres(@Query("api_key") String apiKey);

   @GET("watch/providers/regions")
   Call<AvailableRegionResponse> getAvailableRegion(@Query("api_key") String apiKey);

   @GET("/watch/providers/movie")
   Call<ProviderResponse> getProviderMovie(@Query("api_key") String apiKey);

}





