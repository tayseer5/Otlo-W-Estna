package com.example.iti.sidemenumodule.helperclasses;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.ProjectData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ITI on 01/06/2016.
 */
public class MyProjectCustomerAdapter extends ArrayAdapter {
    //to Cash my layout element
    private static class ViewHolderItem {
        PieChart pieChart;
        TextView myProjectName;
        TextView myProjectStartDate;
        TextView myProjectEndDate;
        TextView myProjectSalary;
        TextView myProjectSate;
    }

    private final Activity context;
    ArrayList<Project> myDate;
    public MyProjectCustomerAdapter(Activity context, ArrayList<Project> data) {
        super(context, R.layout.my_project_row);
        this.context = context;
        myDate=data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.my_project_row, parent, false);
            TypefaceHelper.typeface(convertView);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.pieChart = (PieChart) convertView.findViewById(R.id.pie_chart);
            viewHolder.myProjectName = (TextView) convertView.findViewById(R.id.my_project_name);
            viewHolder.myProjectSalary = (TextView) convertView.findViewById(R.id.my_project_price);
            viewHolder.myProjectSate = (TextView) convertView.findViewById(R.id.my_project_state);
            viewHolder.myProjectEndDate = (TextView) convertView.findViewById(R.id.my_project_end_date);
            viewHolder.myProjectStartDate = (TextView) convertView.findViewById(R.id.my_project_start_date);
            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        // object item based on the position
        Project objectItem =myDate.get(position);
        Log.e("length of data adaptour", myDate.size() + "");
        // assign values if the object is not null
        if(objectItem != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

            Date todayWithZeroTime=null;
            int persent=0;
            try
            {
                int all=get_count_of_days(objectItem.getStartDate(),objectItem.getProjectDeadLine());
                Date today = new Date();
                todayWithZeroTime =dateFormat.parse(dateFormat.format(today));
                int remain=get_count_of_days(todayWithZeroTime,objectItem.getProjectDeadLine());
                persent=(remain/all)*100;
            } catch (ParseException e)
            {
                e.printStackTrace();
            }

           PieData data =DrawMyPieCart(persent);
            //  get the pieChart from the ViewHolder and then set data into chart
            viewHolder.pieChart.setData(data);
            viewHolder.pieChart.setCenterText(persent+" %");  // set the description
            viewHolder.pieChart.setUsePercentValues(true);
            viewHolder.pieChart.setDrawSliceText(true);
            viewHolder.pieChart.setTransparentCircleRadius(9f);
            viewHolder.pieChart.setDescription("");  // set the description
            viewHolder.pieChart.setClickable(false);
            viewHolder.myProjectName.setText(objectItem.getProjectName());
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            String timeFormat=sdf.format(objectItem.getStartDate());
            viewHolder.myProjectStartDate.setText(timeFormat);
            timeFormat=sdf.format(objectItem.getProjectDeadLine());
            viewHolder.myProjectEndDate.setText(timeFormat);
            viewHolder.myProjectSate.setText(objectItem.getStatusOfProject());
           // viewHolder.myProjectSalary.setText(objectItem.getBudget());
        }

        return convertView;

    }

    private PieData DrawMyPieCart(float progress)
    {
        //data of chart see if you move it to outer function
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(progress, 0));
        entries.add(new Entry(100-progress, 1));
        //pass argument to pie dara set
        PieDataSet dataSet = new PieDataSet(entries,null);
//            // creating labels
        ArrayList<String> labels = new ArrayList<>();
        labels.add("");
        labels.add("");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // set the color
        dataSet.setSliceSpace(3f);
        // initialize Piedata
        PieData data = new PieData(labels,dataSet);
        data.removeXValue(0);
        data.removeXValue(0);
        return data;
    }
    @Override
    public int getCount() {
        return myDate.size();
    }



    public int get_count_of_days(Date Created_convertedDate,Date Expire_CovertedDate)
    {

        int c_year=0,c_month=0,c_day=0;

            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(Created_convertedDate);

            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);



            /*Calendar today_cal = Calendar.getInstance();
            int today_year = today_cal.get(Calendar.YEAR);
            int today = today_cal.get(Calendar.MONTH);
            int today_day = today_cal.get(Calendar.DAY_OF_MONTH);
            */





        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(Expire_CovertedDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);


        return (int) dayCount;
    }

    public List<Project> getData() {
        return myDate;
    }
}
