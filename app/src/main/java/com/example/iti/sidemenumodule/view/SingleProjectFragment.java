package com.example.iti.sidemenumodule.view;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.helperclasses.ImageViewPagerAdapter;
import com.example.iti.sidemenumodule.model.Project;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleProjectFragment extends Fragment {

    View rootView;
    FragmentActivity myContext;
    Project project;

    public SingleProjectFragment() {
        // Required empty public constructor
    }

    public SingleProjectFragment(Project project) {
        this.project=project;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_single_project, container, false);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(myContext,project.getImageURL());
        viewPager.setAdapter(adapter);
        TextView titleTextView=(TextView) rootView.findViewById(R.id.project_name_in_details);
        titleTextView.setText(project.getProjectName());
        TextView priceTextView=(TextView) rootView.findViewById(R.id.project_price);
        priceTextView.setText(project.getBudget()+"");
        TextView deliverTextView=(TextView) rootView.findViewById(R.id.single_project_delver_textview);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        String timeFormat=sdf.format(project.getProjectDeadLine());
        deliverTextView.setText(timeFormat);
        TextView statusTextView=(TextView) rootView.findViewById(R.id.project_namde_intails);
        statusTextView.setText(project.getStatusOfProject());
        TextView discTextView=(TextView) rootView.findViewById(R.id.project_description);
        discTextView.setText(project.getProjectDescription());
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myContext=(FragmentActivity)activity;
    }
}
