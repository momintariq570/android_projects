package com.example.momintariq.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private BookAdapter bookAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;
    private String googleApiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Extract the search parameter and build the url
        Intent intent = getIntent();
        String searchParameter = intent.getStringExtra("searchParameter");
        googleApiUrl = BookListingActivity.urlBuilder(searchParameter);

        // Set the empty view to the list view
        ListView bookListView = (ListView)findViewById(R.id.list);
        emptyView = (TextView)findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);

        // Create the book adapter and attach it to the list view
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);

        // Check internet connection and request data from the web api
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected()) {
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, googleApiUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        // Add the book data to the adapter
        progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        emptyView.setText(R.string.no_books);
        bookAdapter.clear();
        if(data != null && !data.isEmpty()) {
            bookAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }

    private static String urlBuilder(String search) {
        return "https://www.googleapis.com/books/v1/volumes?q=" + search + "&maxResults=10";
    }
}
