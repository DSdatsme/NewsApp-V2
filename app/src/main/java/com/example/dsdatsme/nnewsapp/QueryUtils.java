package com.example.dsdatsme.nnewsapp;

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
import java.util.List;

import static com.example.dsdatsme.nnewsapp.MainActivity.LOG_TAG;

public final class QueryUtils {

    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;

    private QueryUtils() {}

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "URL failed ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {urlConnection.disconnect();}
            if (inputStream != null) {inputStream.close();}
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> newsList = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject mainResponse = baseJsonResponse.getJSONObject("response");
            JSONArray newsArray = mainResponse.getJSONArray("results");
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String author = "(unknown author)";
                if (currentNews.has("tags")) {
                    JSONArray contributorArray = currentNews.getJSONArray("tags");
                    JSONObject contributor = contributorArray.getJSONObject(0);
                    if (contributor.has("webTitle")) {
                        author = contributor.getString("webTitle");
                    }
                }
                String section = currentNews.getString("sectionName");
                String time = currentNews.getString("webPublicationDate");
                String url = currentNews.getString("webUrl");
                News news = new News(title, section,author, time, url);
                newsList.add(news);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "unable to parse", e);
        }

        return newsList;
    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "cant make http request", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }
}