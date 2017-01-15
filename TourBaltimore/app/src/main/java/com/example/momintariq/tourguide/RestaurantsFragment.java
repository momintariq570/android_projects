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
public class RestaurantsFragment extends Fragment {


    public RestaurantsFragment() {
        // Required empty public constructor
    }

    // Inflates fragment and populate it with the list adapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        ArrayList<Location> restaurants = new ArrayList<Location>();
        restaurants.add(new Location(getString(R.string.restaurant_one_name),
                getString(R.string.restaurant_one_address),
                getString(R.string.restaurant_one_phone_number),
                getString(R.string.restaurant_one_website),
                R.drawable.ouzo_bay));
        restaurants.add(new Location(
                getString(R.string.restaurant_two_name),
                getString(R.string.restaurant_two_address),
                getString(R.string.restaurant_two_phone_number),
                getString(R.string.restaurant_two_website),
                R.drawable.cinghiale));
        restaurants.add(new Location(
                getString(R.string.restaurant_three_name),
                getString(R.string.restaurant_three_address),
                getString(R.string.restaurant_three_phone_number),
                getString(R.string.restaurant_three_website),
                R.drawable.woodberry_kitchen));
        restaurants.add(new Location(
                getString(R.string.restaurant_four_name),
                getString(R.string.restaurant_four_address),
                getString(R.string.restaurant_four_phone_number),
                getString(R.string.restaurant_four_website),
                R.drawable.wit_wisdom));

        LocationAdapter locationAdapter = new LocationAdapter(getActivity(), restaurants);
        ListView listView = (ListView)rootView.findViewById(R.id.location_list);
        listView.setAdapter(locationAdapter);

        return rootView;
    }

}
