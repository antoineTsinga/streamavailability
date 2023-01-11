package com.streamavailability.ui.watchlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.streamavailability.R;
import com.streamavailability.databinding.FragmentHomeBinding;
import com.streamavailability.databinding.FragmentWatchlistBinding;
import com.streamavailability.ui.home.HomeViewModel;


public class WatchlistFragment extends Fragment {

    private FragmentWatchlistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WatchlistViewModel watchlistViewModel =
                new ViewModelProvider(this).get(WatchlistViewModel.class);

        binding = FragmentWatchlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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