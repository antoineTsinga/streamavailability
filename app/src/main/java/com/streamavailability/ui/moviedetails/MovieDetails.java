package com.streamavailability.ui.moviedetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.streamavailability.Model.Movie;
import com.streamavailability.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment movieDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetails newInstance(String param1, String param2) {
        MovieDetails fragment = new MovieDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Movie movie = (Movie) getArguments().getSerializable("movie");
        TextView title = view.findViewById(R.id.title_movie_details);
        title.setText(movie.getTitle() !=null? movie.getTitle() : movie.getName());
        TextView description = view.findViewById(R.id.description_movie_details);
        description.setText(movie.getOverview());
        ImageView poster = view.findViewById(R.id.poster_movie_details);
        Glide.with(poster.getContext()).load(movie.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);
        return view;
    }
}