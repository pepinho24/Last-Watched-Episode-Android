package com.example.peter.lastwatchedepisode;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask<String, String, Void> {

    public AsyncResponse delegate = null;

    //private ProgressDialog progressDialog = new ProgressDialog(MainActivity);
    InputStream inputStream = null;
    String result = "";

    protected void onPreExecute() {
//        progressDialog.setMessage("Downloading your data...");
//        progressDialog.show();
//        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            public void onCancel(DialogInterface arg0) {
//                MyAsyncTask.this.cancel(true);
//            }
//        });
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
            urlConnection.setRequestProperty("trakt-api-key", "d9c02c3be534c34703caad455d257a49");
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

        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();




        return null;
    }

    // protected Void doInBackground(String... params)
    protected void onPostExecute(Void v) {
        //parse JSON data
        ArrayList<String> titles = null;
        try {
            JSONArray jArray = new JSONArray(result);
            titles = new ArrayList<String>();
            for(int i=0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                String name = jObject.getString("title");
                titles.add(name);
//                String tab1_text = jObject.getString("tab1_text");
//                int active = jObject.getInt("active");

                System.out.println(name );
            } // End Loop
//            this.progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)

        delegate.processFinish(titles);
    } // protected void onPostExecute(Void v)
} //class MyAsyncTask extends AsyncTask<String, String, Void>