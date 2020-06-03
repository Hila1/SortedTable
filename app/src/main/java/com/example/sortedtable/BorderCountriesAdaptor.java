package com.example.sortedtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BorderCountriesAdaptor extends RecyclerView.Adapter<CountryViewHolder> {

    ArrayList<Country> countries;
    private OnItemClickedListener mListener;
    public interface OnItemClickedListener {
        void  onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener){
        mListener = listener;
    }
    public BorderCountriesAdaptor() {
        countries = new ArrayList<>();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View countryView = layoutInflater.inflate(R.layout.recycler_row_border_countries, parent, false);
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

    public void setData(ArrayList<Country> countries){
        this.countries = countries;
    }
}
