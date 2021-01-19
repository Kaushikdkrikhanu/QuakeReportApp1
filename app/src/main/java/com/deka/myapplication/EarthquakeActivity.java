package com.deka.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private TextView emptyView;
    CustomArrayAdapter customArrayAdapter = null;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // Create a fake list of earthquake locations.




        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView =  findViewById(R.id.list);
        emptyView = findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyView);
        // Create a new {@link ArrayAdapter} of earthquakes
        customArrayAdapter = new CustomArrayAdapter(this,new ArrayList<Earthquake>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(customArrayAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = customArrayAdapter.getItem(position);

                Uri uri = Uri.parse(currentEarthquake.url);

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(intent);
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected()){
            LoaderManager.getInstance(this).initLoader(0,null,this);
        }
        else {
            View loadingProgressBar = findViewById(R.id.loading_spinner);
            loadingProgressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        }
        Log.v("LOADER","activity Created");


    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.v("LOADER","Created");
        return new EarthquakeLoader(EarthquakeActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.v("LOADER","finished");
        emptyView.setText(R.string.no_earthquakes);
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);
        customArrayAdapter.clear();
        if (data!=null&&!data.isEmpty()){
            customArrayAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        Log.v("LOADER","reset");
        customArrayAdapter.clear();
    }




}