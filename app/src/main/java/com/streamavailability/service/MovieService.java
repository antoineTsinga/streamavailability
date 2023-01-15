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

   @GET("watch/providers/movie")
   Call<ProviderResponse> getProviderMovie(@Query("api_key") String apiKey);

   @GET("search/movie")
   Call<MovieResponse> searchMovie(@Query("api_key") String apiKey,
                                   @Query("language") String language,
                                   @Query("query") String query,
                                   @Query("page") int page,
                                   @Query("region") String region
                                   );


   @GET("discover/movie")
   Call<MovieResponse> getFilterMovie(@Query("api_key") String apiKey,
                                   @Query("language") String language,
                                   @Query("region") String region,
                                   @Query("page") int page,
                                   @Query("watch_region") String watchRegion,
                                   @Query("with_watch_providers") String watchProvider,
                                   @Query("with_genres") String genre
   );


   /*
   * https://api.themoviedb.org/3/search/movie?api_key=895d65ebbdd5b9379ad195b07e0ed023
   * &language=en-US
   * &query=%3C%3Csearchvalue%3E%3E
   * &page=1
   * &include_adult=false
   * &region=FR
   * */
}





