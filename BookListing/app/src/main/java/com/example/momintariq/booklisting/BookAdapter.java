package com.example.momintariq.booklisting;

import android.content.Context;
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
 * Created by momintariq on 2/5/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    static class ViewHolderItem {
        TextView titleTextView;
        TextView authorTextView;
    }

    ViewHolderItem viewHolder;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    // Setting up the list item
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.titleTextView = (TextView)listItemView.findViewById(R.id.list_title);
            viewHolder.authorTextView = (TextView)listItemView.findViewById(R.id.list_author);
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem)listItemView.getTag();
        }

        final Book currentBook = getItem(position);

        // Set title and author to the list item
        viewHolder.titleTextView.setText(currentBook.getTitle());
        viewHolder.authorTextView.setText(currentBook.getAuthor());

        // Click on the list item opens the book url
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = currentBook.getPreviewLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(URL));
                getContext().startActivity(i);
            }
        });

        return listItemView;
    }
}
