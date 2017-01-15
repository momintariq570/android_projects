package com.example.momintariq.tourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by momintariq on 12/27/16.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    // Public constructor
    public CustomFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // Create a new fragment
    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new RestaurantsFragment();
        } else if(position == 1) {
            return new HotelsFragment();
        } else if(position == 2) {
            return new MuseumsFragment();
        } else {
            return new NatureFragment();
        }
    }

    // Get number of tabs / fragments
    @Override
    public int getCount() {
        return 4;
    }

    // Get tab headings
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return mContext.getString(R.string.restaurant);
        } else if(position == 1) {
            return mContext.getString(R.string.hotel);
        } else if(position == 2) {
            return mContext.getString(R.string.museum);
        } else {
            return mContext.getString(R.string.nature);
        }
    }
}
