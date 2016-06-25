package com.example.iti.sidemenumodule.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.controller.MyApplication;
import com.example.iti.sidemenumodule.daos.JobsManger;
import com.example.iti.sidemenumodule.datamanger.DataManger;

import com.example.iti.sidemenumodule.helperclasses.MyProjectCustomerAdapter;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.ProjectData;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.norbsoft.typefacehelper.TypefaceHelper;


import java.util.ArrayList;

/**
 * Created by ITI on 01/06/2016.
 */
public class MyProjectListFragment extends Fragment implements AfterPraseResult{

    ListView listView;
    View rootView;
    MyProjectCustomerAdapter adapter;
    FragmentActivity myContext;
    ProgressDialog progress;
    ArrayList<Project> data;
    public MyProjectListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        data=new ArrayList<>();
        rootView = inflater.inflate(R.layout.my_project_list_fragment, container, false);
        TypefaceHelper.typeface(rootView);
        JobsManger jobsManger=JobsManger.getInstance(myContext);
        MyApplication userObject = (MyApplication) myContext.getApplicationContext();
        if(userObject!=null) {
            Users user = userObject.getUser();
            jobsManger.getMyJobsList(this, user.getUserId());
        }else {
            jobsManger.getMyJobsList(this, 4);
        }

        progress = new ProgressDialog(myContext,R.style.MyTheme);
        progress.setCancelable(false);
        progress.show();
        listView=(ListView)rootView.findViewById(R.id.my_project_listview);
        // Getting adapter by passing xml data ArrayList
        adapter=new MyProjectCustomerAdapter(myContext,data);
        listView.setAdapter(adapter);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity)activity;
        super.onAttach(activity);
    }

    @Override
    public void afterParesResult(Object list,int code) {
        data=(ArrayList)list;
        adapter.getData().clear();
        adapter.getData().addAll(data);
        // fire the event
        adapter.notifyDataSetChanged();
        progress.dismiss();
    }

    @Override
    public void errorParesResult(String errorMessage,int code) {
        AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
        alertDialog.setTitle(getString(R.string.alert));
        alertDialog.setMessage(getString(R.string.error_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }



}
