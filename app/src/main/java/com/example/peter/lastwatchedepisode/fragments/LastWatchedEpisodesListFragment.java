package com.example.peter.lastwatchedepisode.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.R;
import com.example.peter.lastwatchedepisode.Show;
import com.example.peter.lastwatchedepisode.ShowsDataSource;

public class LastWatchedEpisodesListFragment extends ListFragment implements View.OnClickListener {

    private ShowsDataSource datasource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.fragment_last_watched_episodes_list, container, false);

        datasource = new ShowsDataSource(this.getActivity());
        datasource.open();
       // Show show = datasource.createShow("Show title Test ","Description test","Sunday");
        List<Show> values = datasource.getAllShows();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Show> adapter = new ArrayAdapter<Show>(this.getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = null;
                @SuppressWarnings("unchecked")
                ArrayAdapter<Show> adapter = (ArrayAdapter<Show>) getListAdapter();
                Show show = null;
                switch (v.getId()) {
                    case R.id.btn_add:
                        Show[] shows = new Show[]{new Show("Show title 1 ", "Description 1", "Sunday"), new Show("Show title 2 ", "Description 2", "Friday"), new Show("Show title 3 ", "Description 3", "Monday")};
                        int nextInt = new Random().nextInt(3);
                        // save the new comment to the database
                        show = datasource.createShow(shows[nextInt].getTitle(), shows[nextInt].getDescription(), shows[nextInt].getAirWeekDay());
                        adapter.add(show);
                        Toast.makeText(v.getContext(), "Show Added", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.btn_delete:
                        if (getListAdapter().getCount() > 0) {
                            show = (Show) getListAdapter().getItem(0);
                            datasource.deleteShow(show);
                            adapter.remove(show);
                            Toast.makeText(v.getContext(), "Show Deleted", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        };

        Button btnAdd = (Button) rootView.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(listener);
        Button btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(listener);


        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        Toast.makeText(this.getActivity(), ((TextView)v).getText(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View v) {
        String str = null;
        @SuppressWarnings("unchecked")
        ArrayAdapter<Show> adapter = (ArrayAdapter<Show>) getListAdapter();
        Show show = null;
        switch (v.getId()) {
            case R.id.btn_add:
                Show[] shows = new Show[]{new Show("Show title 1 ", "Description 1", "Sunday"), new Show("Show title 2 ", "Description 2", "Friday"), new Show("Show title 3 ", "Description 3", "Monday")};
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                show = datasource.createShow(shows[nextInt].getTitle(), shows[nextInt].getDescription(), shows[nextInt].getAirWeekDay());
                adapter.add(show);
                break;
            case R.id.btn_delete:
                if (getListAdapter().getCount() > 0) {
                    show = (Show) getListAdapter().getItem(0);
                    datasource.deleteShow(show);
                    adapter.remove(show);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

}
