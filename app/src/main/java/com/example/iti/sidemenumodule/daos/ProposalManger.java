package com.example.iti.sidemenumodule.daos;

import android.content.Context;
import android.util.Log;

import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.model.Proposal;
import com.example.iti.sidemenumodule.network_manager.AfterAsynchronous;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.network_manager.HttpClientConn;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ahmed_telnet on 6/10/2016.
 */
public class ProposalManger implements AfterAsynchronous{
    private static ProposalManger proposalManger;
    private static Context context;
    private AfterPraseResult view;
    public  static ProposalManger getInstance(Context c){
        if(proposalManger==null)
        {
            context=c;
            proposalManger=new ProposalManger();
        }
        return proposalManger;
    }



    @Override
    public void afterExecute(String message, int code) {
        try {
            JSONObject object = new JSONObject(message);
            String myMessage = object.getString("output");
            view.afterParesResult(myMessage,code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void errorInExecute(String errorMessage) {
        view.errorParesResult(errorMessage, 0);
    }


    public void postProposal(Proposal proposal, AfterPraseResult view) {
        this.view = view;
        String postProjectURL = URLManager.postProposalURL;
        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.put("price", proposal.getPrice());
        requestParam.put("startDate", proposal.getStartDatePor());
        requestParam.put("deadLine", proposal.getDeadLinePor());
        requestParam.put("pId", proposal.getProjectsforusers());
        requestParam.put("uId", proposal.getUsers());
        loginConnection.RequestService(postProjectURL, requestParam, 2, null, 0);
    }
}
