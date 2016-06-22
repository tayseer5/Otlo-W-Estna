package com.example.iti.sidemenumodule.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.CategoryManger;
import com.example.iti.sidemenumodule.daos.PortfolioManger;
import com.example.iti.sidemenumodule.datamanger.DataManger;
import com.example.iti.sidemenumodule.helperclasses.MarginDecoration;
import com.example.iti.sidemenumodule.helperclasses.MyData;
import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Portfolio;
import com.example.iti.sidemenumodule.network_manager.AfterAsynchronous;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Ahmed_telnet on 5/21/2016.
 */
public class PortfoliosFragment extends Fragment implements AfterPraseResult{
    private static PortfolioCustomAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Portfolio> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    FragmentActivity myContext;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    int catId;
   static boolean flag=false;
    ProgressDialog progress;

    public static PortfoliosFragment newInstance(String text,int id){
        PortfoliosFragment mFragment = new PortfoliosFragment(id);
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        data=new ArrayList<>();
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);
        TypefaceHelper.typeface(rootView);
        myOnClickListener = new MyOnClickListener(myContext);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MarginDecoration(myContext));
        recyclerView.setLayoutManager(new GridLayoutManager(myContext, 2));
        progress = new ProgressDialog(myContext,R.style.MyTheme);
        progress.setCancelable(false);
        progress.show();
        PortfolioManger portfolioManger = PortfolioManger.getInstance(myContext);

        if(flag) {
            //put here code to get
        }else {
            portfolioManger.getCategoriesList(this,catId);
        }
        adapter = new PortfolioCustomAdapter(myContext, data);
        recyclerView.setAdapter(adapter);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
    }


    public PortfoliosFragment(int id)
    {
        catId=id;
    }

    public PortfoliosFragment()
    {
        flag=true;
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity)activity;
        super.onAttach(activity);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
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


    private  class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
//            Toast.makeText(
//                    myContext, selectedItemPosition, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }



    }
