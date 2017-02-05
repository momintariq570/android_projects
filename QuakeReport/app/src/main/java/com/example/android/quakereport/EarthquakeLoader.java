package com.example.android.quakereport;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momintariq on 2/2/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    ArrayList<String> url = new ArrayList<String>();

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url.add(url);
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(url.get(0) == null) {
            return null;
        }
        Log.i("Log", "loadInBackground");
        return QueryUtils.fetchEarthquakeData(url.get(0));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i("Log", "onStartLoading");
    }
}
