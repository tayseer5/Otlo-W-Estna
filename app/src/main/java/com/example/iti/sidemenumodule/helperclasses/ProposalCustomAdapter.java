package com.example.iti.sidemenumodule.helperclasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.datamanger.DataManger;
import com.example.iti.sidemenumodule.model.Employee;
import com.example.iti.sidemenumodule.model.Proposal;
import com.example.iti.sidemenumodule.model.Users;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ahmed_telnet on 6/9/2016.
 */
public class ProposalCustomAdapter extends ArrayAdapter {

    private final Activity context;
    List<Proposal> myDate;
    List<Employee>employeeDate;
    public ProposalCustomAdapter(Activity context, ArrayList<Proposal> data) {
        super(context, R.layout.proposal_row);
        this.context = context;
        myDate=data;
        employeeDate= DataManger.getEmployees();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.employee_single_row, parent, false);
        TextView nameTextView=(TextView)rowView.findViewById(R.id.employee_name_textview);
        TextView titleTextView=(TextView)rowView.findViewById(R.id.employee_title_textview);
        RatingBar ratingBar=(RatingBar)rowView.findViewById(R.id.rating);

        nameTextView.setText(employeeDate.get(position).getName());
        titleTextView.setText(employeeDate.get(position).getTitle());
        ratingBar.setRating((float) employeeDate.get(position).getRate());
        TextView priceTextView=(TextView) rowView.findViewById(R.id.project_price);
        priceTextView.setText(100*position+"");
        TextView deliverTextView=(TextView) rowView.findViewById(R.id.single_project_delver_textview);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        String timeFormat=sdf.format(new Date());
        deliverTextView.setText(timeFormat);
        TextView statusTextView=(TextView) rowView.findViewById(R.id.project_namde_intails);
        if(position%2==0) {
            statusTextView.setText(context.getString(R.string.pendding));
        }else{
            statusTextView.setText(context.getString(R.string.rejected));
        }
        return rowView;

    }

    @Override
    public int getCount() {
        return 5;
    }


    public List<Proposal> getData() {
        return myDate;
    }
}
