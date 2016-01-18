package com.example.peter.lastwatchedepisode.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.MainActivity;
import com.example.peter.lastwatchedepisode.R;
import com.example.peter.lastwatchedepisode.Show;
import com.example.peter.lastwatchedepisode.ShowAdapter;
import com.example.peter.lastwatchedepisode.ShowsDataSource;

public class LastWatchedEpisodesListFragment extends ListFragment implements ListView.OnClickListener {

    private ShowsDataSource datasource;
    public ShowAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.getListView().setDivider(new ColorDrawable(Color.LTGRAY));
        this.getListView().setDividerHeight(3);

        AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                RelativeLayout rel = (RelativeLayout) view;
                final long showId = new Integer(((TextView) rel.getChildAt(0)).getText().toString());
                String title = ((TextView) rel.getChildAt(1)).getText().toString();
//                String description = ((TextView) rel.getChildAt(2)).getText().toString();
//                String airweekday = ((TextView) rel.getChildAt(3)).getText().toString();
//                final Show show = new Show(title,description,airweekday);
//                show.setId(new Integer(String.valueOf(showId)));


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to delete '"+title +"' ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                datasource.deleteShow(showId);
                                adapter.remove(adapter.getItem(position));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(view.getContext() , "Show Deleted", Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_last_watched_episodes_list, container, false);

        datasource = new ShowsDataSource(this.getActivity());
        datasource.open();

        List<Show> values = datasource.getAllShows();

        adapter = new ShowAdapter(this.getActivity(), values);
        setListAdapter(adapter);

        Button btnAdd = (Button) rootView.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        Button btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        RelativeLayout rel = (RelativeLayout) v;
        int childCount = rel.getChildCount();
        String[] properties = new String[childCount];

        for (int i = 0; i < childCount; i++) {
            TextView view = (TextView) rel.getChildAt(i);
            properties[i] = (String) view.getText();
        }

        Show show = new Show(properties[1], properties[2], properties[3]);
        show.setId(new Integer(properties[0]));
        // Toast.makeText(this.getActivity(), show.getTitle() + ": " + show.getDescription() + " airs every " + show.getAirWeekDay(), Toast.LENGTH_SHORT).show();

        MainActivity activity = (MainActivity) getActivity();
        activity.GoToDetails(show);

    }

    @Override
    public void onClick(View v) {

        Show show;
        switch (v.getId()) {
            case R.id.btn_add:
                Show[] shows = new Show[]{new Show("Show title 1 ", "Description 1", "Sunday"), new Show("Show title 2 ", "Description 2", "Friday"), new Show("Show title 3 ", "Description 3", "Monday")};
                int nextInt = new Random().nextInt(3);
                show = datasource.createShow(shows[nextInt].getTitle(), shows[nextInt].getDescription(), shows[nextInt].getAirWeekDay());
                adapter.add(show);
                Toast.makeText(v.getContext(), "Show Added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                if (getListAdapter().getCount() > 0) {
                    show = adapter.getItem(0);
//                    datasource.deleteShow(show);
//                    adapter.remove(show);
                    Toast.makeText(v.getContext(), "To delete a show hold on it!", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        adapter.notifyDataSetChanged();
    }
}
