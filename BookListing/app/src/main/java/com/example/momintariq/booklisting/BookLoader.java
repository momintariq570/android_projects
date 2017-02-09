package com.example.momintariq.booklisting;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momintariq on 2/5/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    ArrayList<String> url = new ArrayList<>();

    public BookLoader(Context context, String url) {
        super(context);
        this.url.add(url);
    }

    @Override
    // Get the book information from the web api
    public List<Book> loadInBackground() {
        if(this.url.get(0) == null) {
            return null;
        }

        return QueryUtils.fetchBookData(this.url.get(0));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
