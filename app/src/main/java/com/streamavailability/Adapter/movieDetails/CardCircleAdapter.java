package com.streamavailability.Adapter.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.streamavailability.Adapter.home.SectionMovieAdapter;
import com.streamavailability.Model.CardCircle;
import com.streamavailability.R;

import java.util.List;


public class CardCircleAdapter extends RecyclerView.Adapter<CardCircleAdapter.ViewHolder>{




    private List<CardCircle> cardCircles;

    @NonNull
    @Override
    public CardCircleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carde_circle,parent,false);

        return new CardCircleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardCircleAdapter.ViewHolder holder, int position) {
        CardCircle cardCircle = cardCircles.get(position);
        holder.name.setText(cardCircle.getName());

        if(holder.image != null){
            Glide.with(holder.image.getContext()).load(cardCircle.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
        }


    }


    @Override
    public int getItemCount() {
        return cardCircles.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cardView;
        private TextView name;
        private ImageView image;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_circle_title);
            image = itemView.findViewById(R.id.card_circle_image);

        }
    }

    public CardCircleAdapter(List<CardCircle> cardCircles) {
        this.cardCircles = cardCircles;
    }

    public void setCardCircles(List<CardCircle> cardCircles) {
        this.cardCircles = cardCircles;
    }

}
