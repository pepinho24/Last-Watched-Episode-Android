package com.example.peter.lastwatchedepisode;

import android.content.Context;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends ArrayAdapter<Show> {
    List<Show> list;

    public ShowAdapter(Context context, List<Show> shows) {
        super(context,R.layout.show_item_layout, shows);
        list = shows;
    }

    static class DataHandler
    {
        TextView id;
        TextView title;
        TextView description;
        TextView airweekday;
    }
    public void add(Show object){
        //super.add(object);
        list.add(object);
    }
    public int getCount(){
        return this.list.size();
    }

//    @Override
//    public Object getItem(int position) {
//        return this.list.get(position);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        DataHandler handler;
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.show_item_layout,parent,false);
            handler=new DataHandler();
            handler.id = (TextView)row.findViewById(R.id.tv_id);
            handler.title=(TextView)row.findViewById(R.id.tv_title);
            handler.description=(TextView)row.findViewById(R.id.tv_description);
            handler.airweekday=(TextView)row.findViewById(R.id.tv_airweekday);
            row.setTag(handler);
        }
        else {
            handler=(DataHandler)row.getTag();
        }

        Show dataProvider;

        dataProvider=(Show)this.getItem(position);

        String id = String.valueOf(dataProvider.getId());
        handler.id.setText(id);
        handler.title.setText(dataProvider.getTitle());
        handler.description.setText(dataProvider.getDescription());
        handler.airweekday.setText(dataProvider.getAirWeekDay());

        return row;
    }
}
