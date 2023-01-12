package com.streamavailability.ui.category;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.streamavailability.Adapter.category.CategoryViewHolder;
import com.streamavailability.Adapter.watchlist.MovieViewHolder;
import com.streamavailability.Model.Category;
import com.streamavailability.Model.MovieWatchlist;
import com.streamavailability.R;
import com.streamavailability.databinding.FragmentCategoryBinding;
import com.streamavailability.databinding.FragmentHomeBinding;
import com.streamavailability.databinding.FragmentWatchlistBinding;
import com.streamavailability.ui.home.HomeViewModel;
import com.streamavailability.ui.watchlist.WatchlistViewModel;


public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference movieRef = db.collection("genre");
    private Query query = movieRef;
    private FirestoreRecyclerOptions<Category> options;
    private FirestoreRecyclerAdapter<Category, CategoryViewHolder> adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoryViewModel categoryViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerViewCategory;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            public void onBindViewHolder(CategoryViewHolder holder, int position, Category category) {
                holder.nameCategorView.setText(category.getName());
            }

            @Override
            public CategoryViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.card_genre_movie, group, false);

                return new CategoryViewHolder(view);
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