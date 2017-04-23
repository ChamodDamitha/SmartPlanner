package com.example.chamod.smartplanner.ListItemModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chamod.smartplanner.R;

import java.util.ArrayList;

/**
 * Created by chamod on 2/12/17.
 */

public class ReportsListAdapter extends ArrayAdapter<ReportTask> {
    public ReportsListAdapter(Context context, ArrayList<ReportTask> items) {
        super(context, R.layout.report_list_item,items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.report_list_item,parent,false);

        if(getItem(position).isFirstOne()) {
            TextView textViewTitle = (TextView) customView.findViewById(R.id.textViewReportTaskTitle);
            textViewTitle.setVisibility(View.VISIBLE);
            if(getItem(position).isCompleted()) {
                textViewTitle.setText("COMPLETED TASKS");
            }
            else{
                textViewTitle.setText("INCOMPLETED TASKS");
            }
        }

        TextView textDesc=(TextView)customView.findViewById(R.id.textDesc);
        textDesc.setText(getItem(position).getDesc());

        ImageView imageView=(ImageView)customView.findViewById(R.id.imageView);
        if(getItem(position).isCompleted()){
            imageView.setImageDrawable(customView.getResources().getDrawable(R.drawable.tick_green));
        }
        else{
            imageView.setImageDrawable(customView.getResources().getDrawable(R.drawable.cross));
        }

        return customView;
    }
}
