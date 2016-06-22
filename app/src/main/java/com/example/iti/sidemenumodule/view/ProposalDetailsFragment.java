package com.example.iti.sidemenumodule.view;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.ProposalManger;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.Proposal;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.google.gson.Gson;
import com.norbsoft.typefacehelper.TypefaceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposalDetailsFragment extends Fragment implements AfterPraseResult {
    View rootView;
    FragmentActivity myContext;
    Project project;

    public ProposalDetailsFragment() {
        // Required empty public constructor
    }

    public ProposalDetailsFragment(Project project) {
        this.project=project;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView=inflater.inflate(R.layout.fragment_proposal_details, container, false);
        TypefaceHelper.typeface(rootView);
        final EditText budgetEditText=(EditText)rootView.findViewById(R.id.proposal_budget_edittext);
        Button sendButton=(Button) rootView.findViewById(R.id.post_proposal_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Proposal proposal=new Proposal();
                proposal.setDeadLinePor(project.getProjectDeadLine());
                proposal.setStartDatePor(project.getStartDate());
                proposal.setPrice(Integer.parseInt(budgetEditText.getText().toString()));
                proposal.setProjectsforusers(project.getProjectId());
                proposal.setUsers(1);
                ProposalManger proposalManger=ProposalManger.getInstance(myContext);
                proposalManger.postProposal(proposal,ProposalDetailsFragment.this);
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myContext=(FragmentActivity)activity;
    }


    @Override
    public void afterParesResult(Object data,int code) {
        String message=(String)data;
        if(message.equals("tureInsert")){
            getActivity().onBackPressed();
        }
        else {
            Toast.makeText(myContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errorParesResult(String errorMessage,int code) {
        Toast.makeText(myContext, errorMessage, Toast.LENGTH_LONG).show();
    }

}
