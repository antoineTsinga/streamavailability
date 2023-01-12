package com.streamavailability.Adapter.category;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamavailability.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView nameCategorView;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        nameCategorView = itemView.findViewById(R.id.btnAny);
    }
}
