package com.example.momintariq.news;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by momintariq on 2/12/17.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    static class ViewHolderItem {
        TextView titleTextView;
        TextView sectionTextView;
    }

    ViewHolderItem viewHolder;

    public ArticleAdapter(Activity context, List<Article> articles) {
        super(context, 0, articles);
    }


    @NonNull
    @Override
    // Sets up the individual list view item
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.titleTextView = (TextView)listItemView.findViewById(R.id.list_title);
            viewHolder.sectionTextView = (TextView)listItemView.findViewById(R.id.list_section);
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem)listItemView.getTag();
        }

        final Article currentArticle = getItem(position);

        // Set title and section to the list item
        viewHolder.titleTextView.setText(currentArticle.getTitle());
        viewHolder.sectionTextView.setText(currentArticle.getSection());

        // Click on the list item opens the article url
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = currentArticle.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL));
                getContext().startActivity(i);
            }
        });

        return listItemView;
    }
}
