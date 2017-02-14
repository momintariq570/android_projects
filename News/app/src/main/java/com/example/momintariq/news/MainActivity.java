package com.example.momintariq.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String GUARDIAN_API_URL = "http://content.guardianapis.com/search?";
    private ArticleAdapter articleAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the list view in the layout
        ListView articleListView = (ListView)findViewById(R.id.list);

        // Set an empty view
        emptyView = (TextView)findViewById(R.id.empty_view);
        articleListView.setEmptyView(emptyView);

        // Create a new array adapter of articles
        articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the ListView so the list can be populated in the user interface
        articleListView.setAdapter(articleAdapter);

        // Check internet connection
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected()) {
            getSupportLoaderManager().initLoader(0, null, this);
            Log.i("Log", "initLoader");
        } else {
            progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String numArticles = sharedPreferences.getString(
                getString(R.string.settings_num_articles_key),
                getString(R.string.settings_num_articles_default)
        );

        String articlesTopic = sharedPreferences.getString(
                getString(R.string.settings_articles_topic_key),
                getString(R.string.settings_articles_topic_default)
        );

        Uri baseUri = Uri.parse(GUARDIAN_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", articlesTopic);
        uriBuilder.appendQueryParameter("page-size", numArticles);
        uriBuilder.appendQueryParameter("api-key", "test");
        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        progressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        emptyView.setText(R.string.no_articles);
        articleAdapter.clear();
        if(data != null && !data.isEmpty()) {
            articleAdapter.addAll(data);
        }
        Log.i("Log", "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        articleAdapter.clear();
        Log.i("Log", "onLoaderReset");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
