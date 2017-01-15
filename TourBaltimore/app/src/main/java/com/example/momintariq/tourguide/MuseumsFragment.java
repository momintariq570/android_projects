package com.example.momintariq.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MuseumsFragment extends Fragment {


    public MuseumsFragment() {
        // Required empty public constructor
    }

    // Inflates fragment and populate it with the list adapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        ArrayList<Location> museums = new ArrayList<Location>();
        museums.add(new Location(
                getString(R.string.museum_one_name),
                getString(R.string.museum_one_address),
                getString(R.string.museum_one_phone_number),
                getString(R.string.museum_one_website),
                R.drawable.walters_art_museum));
        museums.add(new Location(
                getString(R.string.museum_two_name),
                getString(R.string.museum_two_address),
                getString(R.string.museum_two_phone_number),
                getString(R.string.museum_two_website),
                R.drawable.borail));
        museums.add(new Location(
                getString(R.string.museum_three_name),
                getString(R.string.museum_three_address),
                getString(R.string.museum_three_phone_number),
                getString(R.string.museum_three_website),
                R.drawable.babe_ruth));
        museums.add(new Location(
                getString(R.string.museum_four_name),
                getString(R.string.museum_four_address),
                getString(R.string.museum_four_phone_number),
                getString(R.string.museum_four_website),
                R.drawable.african_american_wax));

        LocationAdapter locationAdapter = new LocationAdapter(getActivity(), museums);
        ListView listView = (ListView)rootView.findViewById(R.id.location_list);
        listView.setAdapter(locationAdapter);

        return rootView;
    }

}
