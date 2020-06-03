package com.example.sortedtable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    String url = "https://restcountries.eu/rest/v2/all";
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Country> countries;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "OnCreate: MainActivity");
        setProgressDialog();
        getData();
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading the data...");
        progressDialog.show();
    }

    private void getData() {
        countries = new ArrayList<>();
        // make an json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonToData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void parseJsonToData(JSONArray response) {
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);
                Country country = new Country();
                country.setNativeName(jsonObject.getString("nativeName"));
                country.setEnglishName(jsonObject.getString("name"));
                country.setArea(result(jsonObject));
                country.setBorders(jsonObject.getString("borders"));
                countries.add(country);
            }
            addItemsToRecyclerView();
        }
        catch (JSONException e){
            Toast.makeText(MainActivity.this, "Json is not valid", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    private double result(JSONObject jsonObject) {
        try {
            return jsonObject.getDouble("area");
        } catch (Exception e){ return 0.0; }
    }

    private void addItemsToRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_all_countries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, countries);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void onClickBtnSortByName(View v)
    {
        Collections.sort(countries, new SortByName());
        addItemsToRecyclerView();
    }

    public void onClickBtnSortByArea(View v)
    {
        Collections.sort(countries, new SortByArea());
        addItemsToRecyclerView();
    }

    public void onClickBtnSortByAreaDescending(View view) {
        Collections.sort(countries, new SortByArea());
        Collections.reverse(countries);
        addItemsToRecyclerView();
    }

    public void onClickBtnSortByNameDescending(View view) {
        Collections.sort(countries, new SortByName());
        Collections.reverse(countries);
        addItemsToRecyclerView();
    }

    class SortByArea implements Comparator<Country>
    {
        // Used for sorting by area size
        public int compare(Country a, Country b)
        {
            return a.getArea().compareTo(b.getArea());
        }
    }

    class SortByName implements Comparator<Country>
    {
        // Used for sorting by name
        public int compare(Country a, Country b)
        {
            return a.getEnglishName().compareTo(b.getEnglishName());
        }
    }
}