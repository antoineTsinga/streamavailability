package com.streamavailability.Adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.streamavailability.R;
import com.streamavailability.Model.Movie;

import java.util.List;

public class MovieSliderAdapter extends PagerAdapter {

    private List<Movie> movieList;
    private Context context;

    public MovieSliderAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_slider, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        ImageView imageView2 = view.findViewById(R.id.imageView2);
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);

        Movie movie = movieList.get(position);
        titleTextView.setText(movie.getTitle() ==null? movie.getName(): movie.getTitle());
        descriptionTextView.setText(movie.getOverview());
        // use a library such as Glide to load the image from the URL into the ImageView
        Glide.with(context).load(movie.getPoster_path()).into(imageView);
        Glide.with(context).load(movie.getPoster_path()).into(imageView2);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
