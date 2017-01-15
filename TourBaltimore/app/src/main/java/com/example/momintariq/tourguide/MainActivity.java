package com.example.momintariq.tourguide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize view pager and custom fragment pager adapter
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        CustomFragmentPagerAdapter fragmentPagerAdapter = new CustomFragmentPagerAdapter(this,
                getSupportFragmentManager());

        // Attach fragment pager adapter to the view pager
        viewPager.setAdapter(fragmentPagerAdapter);

        // Initialize tabs and attach them with the view pager
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
