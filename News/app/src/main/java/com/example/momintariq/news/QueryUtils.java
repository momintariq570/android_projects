package com.example.momintariq.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.momintariq.news.MainActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return an ArrayList object to represent earthquakes.
     */
    public static ArrayList<Article> fetchArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Article> articles = extractEarthquakes(jsonResponse);

        // Return the {@link Event}
        Log.i("Log", "fetchEarthquakeData");
        return articles;
    }

    // Creates a URL
    private static URL createUrl(String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        // If url is null, then return early
        if(url == null) {
            return response;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            // Configuring the connection
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response was 200), then read the input stream and
            // parse the response
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while(line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<Article> extractEarthquakes(String earthquakesJSON) {

        if(TextUtils.isEmpty(earthquakesJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding articles to
        ArrayList<Article> articles = new ArrayList<>();

        try {

            // build up a list of Earthquake objects with the corresponding data.
            JSONObject rootJSON = new JSONObject(earthquakesJSON);
            JSONObject responseJSON = rootJSON.getJSONObject("response");
            JSONArray resultsArr = responseJSON.getJSONArray("results");

            for (int i = 0; i < resultsArr.length(); i++) {
                JSONObject currentArticle = resultsArr.getJSONObject(i);
                String title = currentArticle.getString("webTitle");
                String section = currentArticle.getString("sectionName");
                String date = currentArticle.getString("webPublicationDate");
                String url = currentArticle.getString("webUrl");

                articles.add(new Article(title, section, date, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return articles;
    }

}