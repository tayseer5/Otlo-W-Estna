package com.example.iti.sidemenumodule.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.EmployeeManger;
import com.example.iti.sidemenumodule.helperclasses.EmployeeCustomAdapter;
import com.example.iti.sidemenumodule.helperclasses.ProposalCustomAdapter;
import com.example.iti.sidemenumodule.model.Proposal;
import com.example.iti.sidemenumodule.model.Users;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposalListFragment extends Fragment {

    ListView listView;
    View rootView;
    FragmentActivity myContext;
    ArrayList<Proposal> data;
    ProposalCustomAdapter adapter;

    public ProposalListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data=new ArrayList<>();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_proposal_list, container, false);
        TypefaceHelper.typeface(rootView);
        listView = (ListView) rootView.findViewById(R.id.proposal_listview);
        adapter = new ProposalCustomAdapter(myContext, data);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity)activity;
        super.onAttach(activity);
    }

}
