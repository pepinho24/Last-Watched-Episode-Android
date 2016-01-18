package com.example.peter.lastwatchedepisode.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.AsyncResponse;
import com.example.peter.lastwatchedepisode.MyAsyncTask;
import com.example.peter.lastwatchedepisode.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopularShowsFragment extends ListFragment implements AsyncResponse {
    public ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_popular_shows, container, false);
        ConnectivityManager cm = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            MyAsyncTask task = new MyAsyncTask();
            task.delegate = this;
            task.execute();
        }else  {
            Toast.makeText(this.getActivity(), "You need internet connection to get popular shows!", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
// TODO: should be double tap , not hold...
        AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {

                String title = ((TextView) view).getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want to add show to your favorites '"+title +"' ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Toast.makeText(view.getContext() , "Cool, soon you might be able to :P!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        };

        getListView().setOnItemLongClickListener(listener);
    }

    @Override
    public void processFinish(String output) {
        //parse JSON data
        ArrayList<String> titles = null;
        try {
            JSONArray jArray = new JSONArray(output);
            titles = new ArrayList<String>();
            for(int i=0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);

                String name = jObject.getString("title");
                titles.add(name);
                //System.out.println(name);
            }

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)

        if (output == null){
            Toast.makeText(this.getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, titles);
        setListAdapter(adapter);
    }
}
