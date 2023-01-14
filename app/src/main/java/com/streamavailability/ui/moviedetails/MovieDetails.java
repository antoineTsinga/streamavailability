package com.streamavailability.ui.moviedetails;





import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.streamavailability.Adapter.movieDetails.CardCircleAdapter;
import com.streamavailability.Model.CardCircle;
import com.streamavailability.Model.Cast;
import com.streamavailability.Model.Credits;
import com.streamavailability.Model.Crew;
import com.streamavailability.Model.Movie;
import com.streamavailability.Model.Platforms;
import com.streamavailability.Model.WatchProvider;
import com.streamavailability.R;
import com.streamavailability.databinding.ActivityMovieDetailsBinding;
import com.streamavailability.databinding.CardeCircleBinding;
import com.streamavailability.service.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private RecyclerView castRecyclerView;




    private CardCircleAdapter castAdapter;
    private List<CardCircle> castList;



    public MovieDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("");


        ImageView poster = binding.posterMovieDetails;
        TextView title = binding.titleMovieDetails;
        TextView description = binding.descriptionMovieDetails;
        TextView rating = binding.ratingMovieDetails;
        TextView runtime = binding.runtime;
        TextView ageRating = binding.ageRating;
        TextView director = binding.director;
        TextView genre = binding.genre;

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        title.setText(movie.getTitle() != null ? movie.getTitle() : movie.getName());
        description.setText(movie.getOverview());

        Glide.with(poster.getContext()).load(movie.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);

        castRecyclerView = binding.casting;

        //platformRecyclerView = binding.platforms;


        LinearLayoutManager layoutManagerCast = new LinearLayoutManager(this);
        layoutManagerCast.setOrientation(RecyclerView.HORIZONTAL);
        layoutManagerCast.setReverseLayout(true);
        layoutManagerCast.setStackFromEnd(true);

        castRecyclerView.setLayoutManager(layoutManagerCast);


        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";

        // you can use the movieList data from API here.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        String appendToResponse = "watch/providers,genres,credits";
        Call<Movie> call = movieService.getMoviesDetails(movie.getId(), apiKey, appendToResponse);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movieResult = response.body();

                    if(movieResult !=null){

                        rating.setText(movieResult.getVote_average());
                        List<CharSequence> genresS = movieResult.getGenres().stream().map(g->g.getName()).collect(Collectors.toList());
                        genre.setText(String.join(", ",genresS));
                        Optional<Crew> directorCrew = movieResult.getCredits().getCrew().stream().filter(crew -> crew.getJob().equals("Director")).findFirst();
                        director.setText(directorCrew.isPresent() ? directorCrew.get().getName(): "-/-");
                        runtime.setText(movieResult.getRuntime());


                        Credits credits = movieResult.getCredits();

                        if(credits!=null) {

                            if(credits.getCast() !=null) {
                                List<CardCircle> casts = movieResult.getCredits().getCast().stream().filter(actor -> actor.getProfilePath() != null).map(actor -> new CardCircle(actor.getName(),
                                        "https://image.tmdb.org/t/p/w500" + actor.getProfilePath())).collect(Collectors.toList());

                                castAdapter.setCardCircles(casts);
                                castAdapter.notifyDataSetChanged();
                            }
                        }

                        WatchProvider watchProvider = movieResult.getWatchProvider();

                        if(watchProvider !=null){


                        List<Platforms> platformsListFlatrate = movieResult.getWatchProvider().getResults().get("US").getmFlatrate();
                        List<Platforms> platformsListBuy = movieResult.getWatchProvider().getResults().get("US").getBuy();
                        List<Platforms> platformsListRent = movieResult.getWatchProvider().getResults().get("US").getRent();





                        if(platformsListBuy!=null){
                            List<CardCircle> platformsBuy = platformsListBuy.stream().filter(platforms1 -> platforms1.getLogoPath() !=null).sorted((platform1, platform2)-> (platform1.getDisplayPriority() < platform2.getDisplayPriority()) ? -1 : ((platform1.getDisplayPriority() == platform2.getDisplayPriority()) ? 0 : 1)).map(platform -> new CardCircle(platform.getProviderName(),
                                    "https://image.tmdb.org/t/p/w500" + platform.getLogoPath())).collect(Collectors.toList());




                            LinearLayout linearLayout = binding.linearBuy;
                            for (CardCircle item : platformsBuy) {

                                CardeCircleBinding binding2 = CardeCircleBinding.inflate(getLayoutInflater());

                                Glide.with(binding2.cardCircleImage.getContext()).load(item.getImage())
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(binding2.cardCircleImage);
                                binding2.cardCircleTitle.setText(item.getName());
                                // set item content in view
                                linearLayout.addView(binding2.cardCircle);
                            }



                        }
                        if(platformsListRent!=null){
                            List<CardCircle> platformsRent =platformsListRent.stream().filter(platforms1 -> platforms1.getLogoPath() !=null).sorted((platform1, platform2)-> (platform1.getDisplayPriority() < platform2.getDisplayPriority()) ? -1 : ((platform1.getDisplayPriority() == platform2.getDisplayPriority()) ? 0 : 1)).map(platform -> new CardCircle(platform.getProviderName(),
                                    "https://image.tmdb.org/t/p/w500" + platform.getLogoPath())).collect(Collectors.toList());

                            LinearLayout linearLayout = binding.linearRent;
                            for (CardCircle item : platformsRent) {

                                CardeCircleBinding binding2 = CardeCircleBinding.inflate(getLayoutInflater());

                                Glide.with(binding2.cardCircleImage.getContext()).load(item.getImage())
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(binding2.cardCircleImage);
                                binding2.cardCircleTitle.setText(item.getName());
                                // set item content in view
                                linearLayout.addView(binding2.cardCircle);
                            }


                        }
                            if( platformsListFlatrate!=null){
                                List<CardCircle> platformsFlatrate = platformsListFlatrate.stream().filter(platforms1 -> platforms1.getLogoPath() !=null).sorted((platform1, platform2)-> (platform1.getDisplayPriority() < platform2.getDisplayPriority()) ? -1 : ((platform1.getDisplayPriority() == platform2.getDisplayPriority()) ? 0 : 1)).map(platform -> new CardCircle(platform.getProviderName(),
                                        "https://image.tmdb.org/t/p/w500" + platform.getLogoPath())).collect(Collectors.toList());


                                LinearLayout linearLayout = binding.linearFlatrate;
                                for (CardCircle item : platformsFlatrate) {
                                    CardeCircleBinding binding2 = CardeCircleBinding.inflate(getLayoutInflater());
                                    Glide.with(binding2.cardCircleImage.getContext()).load(item.getImage())
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(binding2.cardCircleImage);
                                    binding2.cardCircleTitle.setText(item.getName());
                                    // set item content in view
                                    linearLayout.addView(binding2.cardCircle);
                                }
                            }


                    }
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                // handle failure
                System.out.println("failure -----------------------------");
            }
        });

        castList = new ArrayList<>();
        castAdapter = new CardCircleAdapter(castList);
        castRecyclerView.setAdapter(castAdapter);
        //platformRecyclerView.setAdapter(platformAdapter);

    }

    private Movie fetchData(String id) {
        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";
        final Movie[] movie = {new Movie()};
        // you can use the movieList data from API here.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        String appendToResponse = "watch/providers,genres,credits";
        Call<Movie> call = movieService.getMoviesDetails(id, apiKey, appendToResponse);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movieResult = response.body();
                    movie[0] = movieResult;
                    System.out.println(movieResult);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                // handle failure
                System.out.println("failure -----------------------------");
            }
        });
        return movie[0];
    }

}

