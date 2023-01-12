package com.streamavailability.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.streamavailability.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public ImageView imageView;
    public TextView descriptionView;
    public TextView typePlatformView;

    public MovieViewHolder(View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.textName);
        imageView = itemView.findViewById(R.id.imageview);
        descriptionView = itemView.findViewById(R.id.textdate);
        typePlatformView = itemView.findViewById(R.id.imageviewplatform1);
    }

}