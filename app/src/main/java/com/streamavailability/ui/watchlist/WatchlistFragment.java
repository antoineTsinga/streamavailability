package com.streamavailability.ui.watchlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.streamavailability.Adapter.watchlist.MovieViewHolder;
import com.streamavailability.Model.MovieWatchlist;
import com.streamavailability.R;
import com.streamavailability.databinding.FragmentWatchlistBinding;


public class WatchlistFragment extends Fragment {

    private FragmentWatchlistBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference movieRef = db.collection("movies_watchlist");
    private Query query = movieRef;
    private FirestoreRecyclerOptions<MovieWatchlist> options;
    private FirestoreRecyclerAdapter<MovieWatchlist, MovieViewHolder> adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WatchlistViewModel watchlistViewModel =
                new ViewModelProvider(this).get(WatchlistViewModel.class);

        binding = FragmentWatchlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerMovieWatchlist;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        options = new FirestoreRecyclerOptions.Builder<MovieWatchlist>()
                .setQuery(query, MovieWatchlist.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<MovieWatchlist, MovieViewHolder>(options) {
            @Override
            public void onBindViewHolder(MovieViewHolder holder, int position, MovieWatchlist movie) {
                holder.nameView.setText(movie.getTitle());
                holder.descriptionView.setText(movie.getDescription());
               // holder.typePlatformView.setText(movie.getPlatforms().toString());
                Glide.with(holder.imageView.getContext()).load(movie.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imageView);

            }

            @Override
            public MovieViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.card_movie_watchlist, group, false);

                return new MovieViewHolder(view);
            }
        };
                recyclerView.setAdapter(adapter);



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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