package com.example.peter.lastwatchedepisode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.peter.lastwatchedepisode.MainActivity;
import com.example.peter.lastwatchedepisode.R;
import com.example.peter.lastwatchedepisode.Show;
import com.example.peter.lastwatchedepisode.ShowAdapter;
import com.example.peter.lastwatchedepisode.ShowsDataSource;

import java.util.List;
import java.util.Random;


public class AddShowFragment extends Fragment implements View.OnClickListener {
    private ShowsDataSource datasource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_show, container, false);

        Button btnAdd = (Button) rootView.findViewById(R.id.btn_add_show);
        btnAdd.setOnClickListener(this);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_airweekday);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.days_of_week_array, R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.abc_simple_dropdown_hint);
        spinner.setAdapter(adapter);

        datasource = new ShowsDataSource(this.getActivity());
        datasource.open();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        String title = ((EditText) this.getActivity().findViewById(R.id.input_title)).getText().toString();
        String description = ((EditText) this.getActivity().findViewById(R.id.input_description)).getText().toString();
        String airweekday = ((Spinner) this.getActivity().findViewById(R.id.spinner_airweekday)).getSelectedItem().toString();

        if (!IsNotNullNotEmptyNotWhiteSpace(title) || !IsNotNullNotEmptyNotWhiteSpace(description) || !IsNotNullNotEmptyNotWhiteSpace(airweekday)) {
            Toast.makeText(v.getContext(), "All fields are required and cannot be empty!", Toast.LENGTH_SHORT).show();
        } else {
            Show show = datasource.createShow(title, description, airweekday);
            Toast.makeText(v.getContext(), "Show Added", Toast.LENGTH_SHORT).show();

            MainActivity activity = (MainActivity) getActivity();
            activity.GoToDetails(show);
        }
    }

    public static boolean IsNotNullNotEmptyNotWhiteSpace(final String str) {
        return str != null && !str.isEmpty() && !str.trim().isEmpty();
    }
}
