package com.streamavailability.Adapter.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.streamavailability.R;
import com.streamavailability.Model.Movie;
import com.streamavailability.ui.moviedetails.MovieDetails;

import java.util.List;

public class SectionMovieAdapter extends RecyclerView.Adapter<SectionMovieAdapter.ViewHolder>{

    private  List<Movie> movieList;
    private Context context;
    @NonNull
    @Override
    public SectionMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie_home,parent,false);

        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionMovieAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleMovie.setText(movie.getTitle() ==null? movie.getName(): movie.getTitle());
        // use a library such as Glide to load the image from the URL into the ImageView
        Glide.with(holder.poster.getContext()).load(movie.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.poster);
        /*
        Glide.with(context).load(movie.getPoster_path()).into(holder.);
        Glide.with(context).load(movie.getPoster_path()).into(imageView2);
        Glide.with(context).load(movie.getPoster_path()).into(imageView2);
*/

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cardView;
        private TextView titleMovie;
        private ImageView poster;
        private ImageView platform1;
        private ImageView platform2;
        private ImageView platform3;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleMovie = itemView.findViewById(R.id.card_home_title_movie);
            poster = itemView.findViewById(R.id.card_home_poster);
            /*
            platform1 = itemView.findViewById(R.id.card_home_platform1);
            platform2 = itemView.findViewById(R.id.card_home_platform2);
            platform3 = itemView.findViewById(R.id.card_home_platform3);
*/
            cardView = (ConstraintLayout) itemView.findViewById(R.id.card_movie_in_home);


            cardView.setOnClickListener(view -> {
                Movie movie = movieList.get(getAdapterPosition());

                MovieDetails fragment = new MovieDetails();

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                fragment.setArguments(bundle);


            });
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param movieList List<Movie> containing the data to populate views to be used
     * by RecyclerView
     * @param context
     */
    public SectionMovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
