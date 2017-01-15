package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by momintariq on 12/30/16.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitude = (TextView)listItemView.findViewById(R.id.list_item_magnitude);
        magnitude.setText(Double.toString(currentEarthquake.getMagnitude()));

        TextView city = (TextView)listItemView.findViewById(R.id.list_item_city);
        city.setText(currentEarthquake.getLocation());

        TextView date = (TextView)listItemView.findViewById(R.id.list_item_date);
        date.setText(currentEarthquake.getDate());

        return listItemView;
    }
}
