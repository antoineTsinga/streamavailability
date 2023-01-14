package com.streamavailability.service;

import com.streamavailability.Model.Movie;
import com.streamavailability.Model.MovieResponse;

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
}

/*https://api.themoviedb.org/3/movie/558?
api_key=895d65ebbdd5b9379ad195b07e0ed023
&append_to_response=watch%2Fproviders,genres,credits
* */