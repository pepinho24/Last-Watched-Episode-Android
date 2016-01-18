package com.example.peter.lastwatchedepisode;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAsyncTask extends AsyncTask<String, String, Void> {

    public AsyncResponse delegate = null;
    String result = "";

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection;
        InputStream in;

        try {
            url= new URL("https://api-v2launch.trakt.tv/shows/popular");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("trakt-api-key", "16d47c0248ab45d23f38d864f0a4d999a557b80058a76fe78e5b16e0a1f0e23e");
            urlConnection.setRequestProperty("trakt-api-version", "2");

            in = new BufferedInputStream(urlConnection.getInputStream());

// Convert response to string using String Builder
            try {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
                StringBuilder responseStringBuilder = new StringBuilder();

                String line;
                while ((line = streamReader.readLine()) != null) {
                    responseStringBuilder.append(line);
                }


                result = responseStringBuilder.toString();

            } catch (Exception e) {
                System.out.println(e);
            }

            in.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Void v) {
        delegate.processFinish(result);
    }
}