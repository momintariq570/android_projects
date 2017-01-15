package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by momintariq on 12/19/16.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int backgroundColor;

    public WordAdapter(Activity context, ArrayList<Word> words, int color) {
        super(context, 0, words);
        backgroundColor = color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView defaultTranslationTextView = (TextView)listItemView.findViewById(R.id.default_word);
        defaultTranslationTextView.setText(currentWord.getDefaultTranslation());

        TextView miwokTranslationTextView = (TextView)listItemView.findViewById(R.id.miwok_word);
        miwokTranslationTextView.setText(currentWord.getMiwokTranslation());

        ImageView miwokImageView = (ImageView)listItemView.findViewById(R.id.miwok_image);

        if(!currentWord.getIsImageSet()) {
            miwokImageView.setVisibility(View.GONE);
        } else {
            miwokImageView.setImageResource(currentWord.getImageResourceID());
            miwokImageView.setVisibility(View.VISIBLE);
        }

        LinearLayout linearLayout = (LinearLayout)listItemView.findViewById(R.id.list_item_outer);
        linearLayout.setBackgroundResource(backgroundColor);

        return listItemView;
    }
}
