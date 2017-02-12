package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chamod.smartplanner.R;

/**
 * Created by chamod on 2/12/17.
 */

public class ReportsListAdapter extends ArrayAdapter<String> {
    public ReportsListAdapter(Context context, String[] items) {
        super(context, R.layout.report_list_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.report_list_item,parent,false);

        TextView textViewTitle=(TextView)customView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(getItem(position));
        return customView;
    }
}
