package com.streamavailability.ui.home;




import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.streamavailability.MainActivity;
import com.streamavailability.MainViewModel;
import com.streamavailability.Model.User;
import com.streamavailability.R;
import com.streamavailability.auth.LoginActivity;
import com.streamavailability.databinding.FragmentHomeBinding;
import com.streamavailability.Adapter.home.MovieSliderAdapter;
import com.streamavailability.Adapter.home.SectionMovieAdapter;
import com.streamavailability.Model.Movie;
import com.streamavailability.Model.MovieResponse;
import com.streamavailability.service.MovieService;
import com.streamavailability.ui.moviedetails.MovieDetails;
import com.streamavailability.ui.moviesresult.MoviesResult;
import com.streamavailability.ui.util.DepthPageTransformer;
import com.streamavailability.ui.util.FixedSpeedScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MovieSliderAdapter movieSliderAdapter;
    private SectionMovieAdapter
            sectionMovieAdapterMostPopular,
            sectionMovieAdapterSimilar,
            sectionMovieAdapterWatchlist,
            sectionMovieAdapterRecommendation;


    private RecyclerView movieRecommendationsRecyclerView,
            movieMostPopularListRecyclerView,
            movieInYourWatchlistListRecyclerView,
            movieSimilarToListRecyclerView;

    private List<Movie> movieSliderList,movieRecommendationsList,movieMostPopularList,
            movieInYourWatchlistList, movieSimilarToList;

    private User user = new User();

    private ViewPager viewPager;

    private int currentPage = 0;
    private final int delayTime = 15000; // delay time between page changes, in milliseconds
    private final int pageCount = 20; // number of pages in the ViewPager
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (currentPage == pageCount) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
            handler.postDelayed(runnable, delayTime);
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewPager = binding.viewPager;



        Bundle bundle = getArguments();
      //  String textViewText = bundle.getString("textViewText");




        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";
        movieSliderList = new ArrayList<>();
        // you can use the movieList data from API here.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);


        Call<MovieResponse> call = movieService.getTrendingMovies(apiKey);
        call.enqueue(new Callback<MovieResponse>() {
             @Override
             public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                 if (response.isSuccessful()) {
                     MovieResponse movieResponse = response.body();
                     assert movieResponse != null;
                     List<Movie> movieList = movieResponse.getResults();
                     for (Movie movie : movieList) {
                         movie.setPoster_path("https://image.tmdb.org/t/p/original" + movie.getPoster_path());
                     }
                     movieSliderAdapter.setMovieList(movieList);
                     movieSliderAdapter.notifyDataSetChanged();
                 }

             }

             @Override
             public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                 // handle failure
             }
        });



        movieSliderAdapter = new MovieSliderAdapter(movieSliderList, getContext());
        viewPager.setAdapter(movieSliderAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = null;
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), new LinearInterpolator());
            // scroller.setFixedDuration(5000);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }


        //pageSwitcher(5);
        timer();
        //bind recycler view

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (currentUser != null) {

            // User is signed in
            String uid = currentUser.getUid();
            // Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {


                            binding.searchViewSearchViewHome.setOnClickListener(view -> {

                                Intent moviesResultIntent = new Intent(getContext(), MoviesResult.class);
                                moviesResultIntent.putExtra("user", user);
                                getContext().startActivity(moviesResultIntent);
                            });


                            user = task.getResult().toObject(User.class);
                        movieMostPopularListRecyclerView = binding.sectionMostPopular;
                        movieRecommendationsRecyclerView = binding.sectionRecommendation;
                        movieInYourWatchlistListRecyclerView = binding.sectionInWatchlist;
                        movieSimilarToListRecyclerView = binding.sectionSimilar;

                        // initiate data
                        movieMostPopularList = new ArrayList<>();
                        movieRecommendationsList = new ArrayList<>();
                        movieSimilarToList = new ArrayList<>();
                        movieInYourWatchlistList = new ArrayList<>();


                        // initiate adapter
                        sectionMovieAdapterMostPopular = new SectionMovieAdapter(movieMostPopularList, getContext());
                        sectionMovieAdapterMostPopular.setUser(user);
                        sectionMovieAdapterSimilar = new SectionMovieAdapter(movieSimilarToList, getContext());
                        sectionMovieAdapterSimilar.setUser(user);
                        sectionMovieAdapterWatchlist = new SectionMovieAdapter(movieInYourWatchlistList, getContext());
                        sectionMovieAdapterWatchlist.setUser(user);
                        sectionMovieAdapterRecommendation = new SectionMovieAdapter(movieRecommendationsList, getContext());
                        sectionMovieAdapterRecommendation.setUser(user);

                        // initiate layout manager
                        LinearLayoutManager layoutManagerMostPopular = new LinearLayoutManager(getContext());
                        layoutManagerMostPopular.setOrientation(RecyclerView.HORIZONTAL);
                        layoutManagerMostPopular.setReverseLayout(true);
                        layoutManagerMostPopular.setStackFromEnd(true);

                        LinearLayoutManager layoutManagerRecommendation = new LinearLayoutManager(binding.sectionMostPopular.getContext());
                        layoutManagerRecommendation.setOrientation(RecyclerView.HORIZONTAL);
                        layoutManagerRecommendation.setReverseLayout(true);
                        layoutManagerRecommendation.setStackFromEnd(true);

                        LinearLayoutManager layoutManagerSimilar = new LinearLayoutManager(binding.sectionMostPopular.getContext());
                        layoutManagerSimilar.setOrientation(RecyclerView.HORIZONTAL);
                        layoutManagerSimilar.setReverseLayout(true);
                        layoutManagerSimilar.setStackFromEnd(true);

                        LinearLayoutManager layoutManagerWatchlist = new LinearLayoutManager(binding.sectionMostPopular.getContext());
                        layoutManagerWatchlist.setOrientation(RecyclerView.HORIZONTAL);
                        layoutManagerWatchlist.setReverseLayout(true);
                        layoutManagerWatchlist.setStackFromEnd(true);

                        // set Layout manager for the recycler views
                        movieRecommendationsRecyclerView.setLayoutManager(layoutManagerRecommendation);
                        movieMostPopularListRecyclerView.setLayoutManager(layoutManagerMostPopular);
                        movieInYourWatchlistListRecyclerView.setLayoutManager(layoutManagerWatchlist);
                        movieSimilarToListRecyclerView.setLayoutManager(layoutManagerSimilar);


                        // set adapter for recycler view
                        movieRecommendationsRecyclerView.setAdapter(sectionMovieAdapterRecommendation);
                        movieMostPopularListRecyclerView.setAdapter(sectionMovieAdapterMostPopular);
                        movieInYourWatchlistListRecyclerView.setAdapter(sectionMovieAdapterWatchlist);
                        movieSimilarToListRecyclerView.setAdapter(sectionMovieAdapterSimilar);


                        // fetch data

                        fetchData(movieService.getMostPopulars(apiKey), sectionMovieAdapterMostPopular);

                        fetchData(movieService.getRecommendations("505642", apiKey), sectionMovieAdapterRecommendation);

                        fetchData(movieService.getSimilarMovies("505642", apiKey), sectionMovieAdapterSimilar);

                        fetchData(movieService.getTrendingMovies(apiKey), sectionMovieAdapterWatchlist);


                          //  System.out.println("-----------ici----------"+user.getCountry());
                        } else {
                            System.out.println("---- Document doesn't exist-----");

                        }
                    } else {
                        // Handle error
                    }
                }
            });
        }



        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.hide();


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static void fetchData( Call<MovieResponse> call, SectionMovieAdapter adapter) {
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
                    adapter.setMovieList(movieList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                // handle failure
            }
        });
    }



    Timer timer;
    int page = 1;

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...


    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 4) { // In my case the number of pages are 5
                        timer.cancel();
                        // Showing a toast for just testing purpose
                        Toast.makeText(getContext(), "Timer stoped",
                                Toast.LENGTH_LONG).show();
                    } else {
                        binding.viewPager.setCurrentItem(page++);
                    }
                }
            });

        }
    }


    private void timer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(getActivity() ==null) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (page == 20 - 1) {
                            page = 0;
                        }
                        viewPager.setCurrentItem(page++, true);
                    }
                });
            }
        }, 500, 10000);
    }

}