package com.hangoclong.httpurlconnection.data.source.remote.config.service;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchDataFromGithub extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String data= "";
        try {
            data = getJSONStringFromURL(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String getJSONStringFromURL(String urlString) throws IOException,JSONException {
        HttpURLConnection urlConnection;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(15000);
        urlConnection.setReadTimeout(10000);
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        String jsonString = builder.toString();
        reader.close();
        urlConnection.disconnect();
        return jsonString;
    }
}
