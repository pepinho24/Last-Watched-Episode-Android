package com.example.peter.lastwatchedepisode.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.R;
import com.example.peter.lastwatchedepisode.Show;
import com.example.peter.lastwatchedepisode.ShowsDataSource;

import java.util.ArrayList;

public class ShowDetailsPageFragment extends Fragment {
    ShowsDataSource database;

    TextView tvId;
    TextView tvTitle;
    TextView tvDescription;
    TextView tvAirweekday;
    TextView tvLastWatchedEpisode;
    TextView tvDateLastEpisodeIsWatched;

    private void init() {
        database = new ShowsDataSource(this.getActivity());
        database.open();

        tvId = (TextView) this.getActivity().findViewById(R.id.tv_id);
        tvTitle = (TextView) this.getActivity().findViewById(R.id.tv_title);
        tvDescription = (TextView) this.getActivity().findViewById(R.id.tv_description);
        tvAirweekday = (TextView) this.getActivity().findViewById(R.id.tv_airweekday);
        tvLastWatchedEpisode = (TextView) this.getActivity().findViewById(R.id.tv_lastwatchedepisode);
        tvDateLastEpisodeIsWatched = (TextView) this.getActivity().findViewById(R.id.tv_datelastepisodeiswatched);
    }

    private void setTextToViews(long id) {
        Show show = database.getShowById(id);

        tvId.setText(String.valueOf(show.getId()));
        tvTitle.setText(show.getTitle());
        tvTitle.setTextAppearance(this.getActivity(), android.R.style.TextAppearance_DeviceDefault_Large);
        tvDescription.setText(show.getDescription());
        tvAirweekday.setText("Airs every " + show.getAirWeekDay());
        tvLastWatchedEpisode.setText("Last Watched episode is " + String.valueOf(show.getLastWatchedEpisode()));
        tvDateLastEpisodeIsWatched.setText("Watched on " + show.getDateLastEpisodeIsWatched());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        SetOnClickListener();
        Bundle args = this.getArguments();
        if (args != null) {
            ArrayList<String> obj = args.getStringArrayList("ShowDetails");

            if (!obj.isEmpty()) {
                long id = Long.parseLong(obj.get(0));
                setTextToViews(id);
            }
        }
    }

    private void watchShow(long showId){
        database.watchShow(showId);
        Toast.makeText(this.getActivity(), "Show Watched", Toast.LENGTH_SHORT).show();
        setTextToViews(showId);
    }

    private void SetOnClickListener(){

        View.OnLongClickListener listener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final long showId = new Integer(((TextView) view.findViewById(R.id.tv_id)).getText().toString());
                String title = ((TextView) view.findViewById(R.id.tv_title)).getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to watch the next episode of '" + title + "' ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                watchShow(showId);
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

        getView().setOnLongClickListener(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details_page, container, false);

        return rootView;
    }
}
