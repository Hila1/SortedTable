package com.example.sortedtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountriesAdaptor extends RecyclerView.Adapter<CountryViewHolder> {

    ArrayList<Country> countries;

    public CountriesAdaptor() {
        countries = new ArrayList<>();
    }

    public void setData(ArrayList<Country> countries){
        this.countries = countries;
    }
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View countryView = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new CountryViewHolder(countryView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.nativeName.setText(country.nativeName);
        holder.englishName.setText(country.englishName);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
