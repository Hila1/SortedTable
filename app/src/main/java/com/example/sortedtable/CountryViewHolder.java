package com.example.sortedtable;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryViewHolder extends RecyclerView.ViewHolder {
    TextView nativeName;
    TextView englishName;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);
        nativeName = itemView.findViewById(R.id.border_native_name);
        englishName = itemView.findViewById(R.id.border_english_name);

    }
}
