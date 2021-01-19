package com.deka.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    public EarthquakeLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(USGS_REQUEST_URL);
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
