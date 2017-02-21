package com.example.momintariq.news;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momintariq on 2/12/17.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    ArrayList<String> url = new ArrayList<>();

    public ArticleLoader(Context context, String url) {
        super(context);
        this.url.add(url);
    }

    @Override
    // Get the article information from the web api
    public List<Article> loadInBackground() {
        if(this.url.get(0) == null) {
            return null;
        }

        return QueryUtils.fetchArticleData(this.url.get(0));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
