package com.streamavailability.Adapter.category;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.streamavailability.R;
import com.streamavailability.ui.moviesresult.MoviesResult;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public AppCompatButton nameCategoryView;



    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        nameCategoryView = itemView.findViewById(R.id.btnAny);

    }
}
