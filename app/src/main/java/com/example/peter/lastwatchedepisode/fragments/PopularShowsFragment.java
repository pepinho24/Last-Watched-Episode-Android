package com.example.peter.lastwatchedepisode.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.net.ConnectivityManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.AsyncResponse;
import com.example.peter.lastwatchedepisode.DownloadAsync;
import com.example.peter.lastwatchedepisode.InterestingEvent;
import com.example.peter.lastwatchedepisode.MyAsyncTask;
import com.example.peter.lastwatchedepisode.R;
import com.example.peter.lastwatchedepisode.Show;
import com.example.peter.lastwatchedepisode.ShowAdapter;
import com.example.peter.lastwatchedepisode.ShowsDataSource;

import java.util.ArrayList;
import java.util.List;

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
            task.execute("");
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
//                String description = ((TextView) rel.getChildAt(2)).getText().toString();
//                String airweekday = ((TextView) rel.getChildAt(3)).getText().toString();
//                final Show show = new Show(title,description,airweekday);
//                show.setId(new Integer(String.valueOf(showId)));


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
    public void processFinish(ArrayList<String> output) {
        if (output == null){
            Toast.makeText(this.getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, output);
        setListAdapter(adapter);
    }
}
