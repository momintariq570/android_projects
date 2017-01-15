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
public class HotelsFragment extends Fragment {


    public HotelsFragment() {
        // Required empty public constructor
    }

    // Inflates fragment and populate it with the list adapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        ArrayList<Location> hotels = new ArrayList<Location>();
        hotels.add(new Location(getString(R.string.hotel_one_name),
                getString(R.string.hotel_one_address),
                getString(R.string.hotel_one_phone_number),
                getString(R.string.hotel_one_website),
                R.drawable.kimpton_monaco));
        hotels.add(new Location(getString(R.string.hotel_two_name),
                getString(R.string.hotel_two_address),
                getString(R.string.hotel_two_phone_number),
                getString(R.string.hotel_two_website),
                R.drawable.royal_sonesta));
        hotels.add(new Location(getString(R.string.hotel_three_name),
                getString(R.string.hotel_three_address),
                getString(R.string.hotel_three_phone_number),
                getString(R.string.hotel_three_website),
                R.drawable.four_seasons));
        hotels.add(new Location(getString(R.string.hotel_four_name),
                getString(R.string.hotel_four_address),
                getString(R.string.hotel_four_phone_number),
                getString(R.string.hotel_four_website),
                R.drawable.ivy_hotel));

        LocationAdapter locationAdapter = new LocationAdapter(getActivity(), hotels);
        ListView listView = (ListView)rootView.findViewById(R.id.location_list);
        listView.setAdapter(locationAdapter);

        return rootView;
    }

}
