package com.example.peter.lastwatchedepisode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.lastwatchedepisode.R;

import java.util.ArrayList;

public class ShowDetailsPageFragment extends Fragment{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvId = (TextView) this.getActivity().findViewById(R.id.tv_id);
        TextView tvTitle = (TextView) this.getActivity().findViewById(R.id.tv_title);
        TextView tvDescription = (TextView) this.getActivity().findViewById(R.id.tv_description);
        TextView tvAirweekday = (TextView) this.getActivity().findViewById(R.id.tv_airweekday);

        Bundle args = this.getArguments();
        ArrayList<String> obj = args.getStringArrayList("ShowDetails");

        if (!obj.isEmpty()) {
            tvId.setText(obj.get(0));
            tvTitle.setText(obj.get(1));
            tvDescription.setText(obj.get(2));
            tvAirweekday.setText(obj.get(3));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_show_details_page, container, false);


        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
