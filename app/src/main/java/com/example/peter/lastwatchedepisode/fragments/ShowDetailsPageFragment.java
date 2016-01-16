package com.example.peter.lastwatchedepisode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peter.lastwatchedepisode.R;

public class ShowDetailsPageFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_show_details_page, container, false);
        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
