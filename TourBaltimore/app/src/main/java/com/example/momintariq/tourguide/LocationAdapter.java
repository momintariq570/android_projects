package com.example.momintariq.tourguide;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by momintariq on 12/27/16.
 */

public class LocationAdapter extends ArrayAdapter<Location> {

    static class ViewHolderItem {
        ImageView imageView;
        TextView textViewName;
        TextView textViewAddress;
        TextView textViewPhone;
        TextView textViewWebsite;
    }

    // Constructor
    public LocationAdapter(Activity context, ArrayList<Location> locations) {
        super(context, 0, locations);
    }

    // Readies and returns the location item in the adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.imageView = (ImageView)listItemView.findViewById(R.id.card_list_item_image);
            viewHolder.textViewName = (TextView)listItemView.findViewById(
                    R.id.card_list_item_location_name);
            viewHolder.textViewAddress = (TextView)listItemView.findViewById(
                    R.id.card_list_item_location_address);
            viewHolder.textViewPhone = (TextView)listItemView.findViewById(
                    R.id.card_list_item_location_phone);
            viewHolder.textViewWebsite = (TextView)listItemView.findViewById(
                    R.id.card_list_item_location_website);

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem)listItemView.getTag();
        }

        final Location currentLocation = getItem(position);

        // Location image resource(s) are attached to the view
        viewHolder.imageView.setImageResource(currentLocation.getImageResourceID());
        viewHolder.imageView.setVisibility(View.VISIBLE);

        // Location name resource(s) are attached to the view
        viewHolder.textViewName.setText(currentLocation.getName());

        // Location address resource(s) are attached to the view
        viewHolder.textViewAddress.setText(R.string.directions);
        viewHolder.textViewAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_drive_eta_black_18dp,
                0, 0, 0);
        viewHolder.textViewAddress.setCompoundDrawablePadding(25);
        viewHolder.textViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = currentLocation.getAddress();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("google.navigation:q=" + address));
                getContext().startActivity(i);
            }
        });

        // Location phone resource(s) are attached to the view
        viewHolder.textViewPhone.setText(R.string.call);
        viewHolder.textViewPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_call_black_18dp, 0, 0,
                0);
        viewHolder.textViewPhone.setCompoundDrawablePadding(25);
        viewHolder.textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = currentLocation.getPhoneNumber();
                String phoneNumber = "tel:" + str.trim();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(phoneNumber));
                getContext().startActivity(i);
            }
        });

        // Location website resource(s) are attached to the view
        viewHolder.textViewWebsite.setText(R.string.website);
        viewHolder.textViewWebsite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_web_black_18dp, 0, 0,
                0);
        viewHolder.textViewWebsite.setCompoundDrawablePadding(25);
        viewHolder.textViewWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = currentLocation.getWebsite();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getContext().startActivity(i);
            }
        });

        return listItemView;
    }
}
