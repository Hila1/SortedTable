package com.example.sortedtable;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BorderCountriesActivity extends AppCompatActivity {

    private static final String TAG = "BorderCountriesActivity";
    String borderCountriesUrl = "https://restcountries.eu/rest/v2/alpha?codes=";
    RecyclerView recyclerView;
    BorderCountriesAdaptor adaptor;
    ArrayList<Country> borderCountries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_border_countries);

        getIncomingIntent();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewBorders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new BorderCountriesAdaptor();
        recyclerView.setAdapter(adaptor);
        borderCountries = new ArrayList<>();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        // check if there are any extras so the app wont crush
        if (getIntent().hasExtra("country_name") && getIntent().hasExtra("country_borders")){
            Log.d(TAG, "getIncomingIntent: found incoming intents");
            String countryName = getIntent().getStringExtra("country_name");
            String countryBorders = getIntent().getStringExtra("country_borders");

            setCountryNameToTitle(countryName);
            if (countryBorders.length() > 2)
                getBordersData(countryBorders);
            else
                Toast.makeText(this, "this country has no borders with other countries", Toast.LENGTH_SHORT).show();
        }
        else{ Log.d(TAG, "getIncomingIntent: no incoming intents"); }
    }

    private void getBordersData(String countryBorders) {
        fixBordersUrl(countryBorders);
        initRecyclerView();
        getData();
    }

    private void fixBordersUrl(String countryBorders) {
        countryBorders = countryBorders.replaceAll("\",\"",";");
        borderCountriesUrl = borderCountriesUrl + countryBorders.substring(2, countryBorders.length()-2);

    }

    private void setCountryNameToTitle(String countryName) {
        TextView textView = findViewById(R.id.country_name_and_native_name);
        textView.setText(countryName);
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading the data...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(borderCountriesUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        Country country = new Country();
                        country.setNativeName(jsonObject.getString("nativeName"));
                        country.setEnglishName(jsonObject.getString("name"));
//                        country.setBorders(jsonObject.getString("borders"));
//                        country.setArea(jsonObject.getString("area"));
                        borderCountries.add(country);
                    }

                    adaptor.setData(borderCountries);
                    adaptor.notifyDataSetChanged();
                }
                catch (JSONException e){
                    Toast.makeText(BorderCountriesActivity.this, "Json is not valid", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(BorderCountriesActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
