package com.example.iti.sidemenumodule.helperclasses;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.Users;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ahmed_telnet on 6/6/2016.
 */
public class JobsCustomAdapter extends ArrayAdapter {
    private final Activity context;
    List<Project> myDate;
    public JobsCustomAdapter(Activity context, ArrayList<Project> data) {
        super(context, R.layout.job_custom_row);
        this.context = context;
        myDate=data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.job_custom_row, parent, false);
        TextView nameTextView=(TextView)rowView.findViewById(R.id.job_name_textview);
        TextView detTextView=(TextView)rowView.findViewById(R.id.job_detelis);
        TextView priceTextView=(TextView) rowView.findViewById(R.id.row_price_textview);
        TextView deliverTextView=(TextView) rowView.findViewById(R.id.deliver_on_textview);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        String timeFormat=sdf.format(myDate.get(position).getProjectDeadLine());
        deliverTextView.setText(timeFormat);
        nameTextView.setText(myDate.get(position).getProjectName());
        detTextView.setText(myDate.get(position).getProjectDescription());
        priceTextView.setText(String.valueOf(myDate.get(position).getBudget()));
        return rowView;

    }

    @Override
    public int getCount() {
        return myDate.size();
    }


    public List<Project> getData() {
        return myDate;
    }
}
