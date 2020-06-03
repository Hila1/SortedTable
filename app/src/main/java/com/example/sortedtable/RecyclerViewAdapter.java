package com.example.sortedtable;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Country> countries = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Country> countries) {
        this.countries = countries;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_all_countries, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder called.");

        holder.nativeName.setText(countries.get(position).nativeName);
        holder.englishName.setText(countries.get(position).getEnglishName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + countries.get(position).getEnglishName());
                Toast.makeText(mContext, countries.get(position).getEnglishName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, BorderCountriesActivity.class);
                intent.putExtra("country_name",countries.get(position).getEnglishName());
                intent.putExtra("country_borders",countries.get(position).getBorders());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nativeName;
        TextView englishName;
        androidx.constraintlayout.widget.ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nativeName = itemView.findViewById(R.id.native_name);
            englishName = itemView.findViewById(R.id.english_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
