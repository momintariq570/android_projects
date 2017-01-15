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
public class NatureFragment extends Fragment {


    public NatureFragment() {
        // Required empty public constructor
    }

    // Inflates fragment and populate it with the list adapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        ArrayList<Location> nature = new ArrayList<Location>();
        nature.add(new Location(
                getString(R.string.nature_one_name),
                getString(R.string.nature_one_address),
                getString(R.string.nature_one_phone_number),
                getString(R.string.nature_one_website),
                R.drawable.national_acquarium));
        nature.add(new Location(
                getString(R.string.nature_two_name),
                getString(R.string.nature_two_address),
                getString(R.string.nature_two_phone_number),
                getString(R.string.nature_two_website),
                R.drawable.fort_mchenry));
        nature.add(new Location(
                getString(R.string.nature_three_name),
                getString(R.string.nature_three_address),
                getString(R.string.nature_three_phone_number),
                getString(R.string.nature_three_website),
                R.drawable.maryland_zoo));
        nature.add(new Location(
                getString(R.string.nature_four_name),
                getString(R.string.nature_four_address),
                getString(R.string.nature_four_phone_number),
                getString(R.string.nature_four_website),
                R.drawable.sherwood_gardens));

        LocationAdapter locationAdapter = new LocationAdapter(getActivity(), nature);
        ListView listView = (ListView)rootView.findViewById(R.id.location_list);
        listView.setAdapter(locationAdapter);

        return rootView;
    }

}
