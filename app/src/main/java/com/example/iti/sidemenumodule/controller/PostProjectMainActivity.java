package com.example.iti.sidemenumodule.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.CategoryManger;
import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.view.PostProjectFragment;
import com.example.iti.sidemenumodule.view.PostProjectFragmentOne;
import com.example.iti.sidemenumodule.view.PostProjectFragmentThree;
import com.google.gson.Gson;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

public class PostProjectMainActivity extends ActionBarActivity implements AfterPraseResult {
    private Toolbar toolbar;
    Spinner categorySpinner;
    ArrayList<Category> categoryList;
    String[] categoryNames={"ahmed","mohamed","hussin"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_project_activity);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.post_project_toolbar_title);
//        toolbar.setElevation((float) 0.0);
        toolbar.setTitleTextColor(getResources().getColor(R.color.light_gray));
        TypefaceHelper.typeface(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MedicineActivity.this, "Alarm....", Toast.LENGTH_LONG).show();
                NavUtils.navigateUpFromSameTask(PostProjectMainActivity.this);
            }
        });

        CategoryManger categoryManger = CategoryManger.getInstance(this);
        categoryManger.getCategoriesList(this);
        categoryNames=getCategoryNames();
        categorySpinner = (Spinner) findViewById(R.id.post_project_category_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
        categorySpinner.setAdapter(adapter1);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Log.e("in selecte item",pos+"");
                Fragment fragment=null;
               switch (pos){
                   case 0:
                       fragment=new PostProjectFragmentOne(getCategoryIdByName(categoryNames[pos]));
                       break;
                   case 1:

                       fragment=new PostProjectFragmentThree(getCategoryIdByName(categoryNames[pos]));
                       break;
                   case 2:
                       fragment=new PostProjectFragmentOne(getCategoryIdByName(categoryNames[pos]));
                       break;

               }

                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.fragment, fragment, "postProject");
                transaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

//        Fragment fragment=new PostProjectFragment();
//        FragmentManager manager=getSupportFragmentManager();
//        FragmentTransaction transaction=manager.beginTransaction();
//        transaction.add(R.id.fragment, fragment, "postProject");
//        transaction.commit();

        if(IsNotLogin()){

            Toast.makeText(getApplicationContext(), getString(R.string.you_must_login), Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_project_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private boolean IsNotLogin()
    {
        Log.e("IsNotLogin", "IsNotLogin");
        SharedPreferences sharedpreferences = getSharedPreferences("loginPrefrence", Context.MODE_PRIVATE);
        String sharedString = sharedpreferences.getString("user", "Not login");
        if(!sharedString.contentEquals("Not login")) {
            Log.e("IsNotLogin if","IsNotLogin");
            Gson gson = new Gson();
            Users user = gson.fromJson(sharedString,Users.class);
            if (user!=null)
            {Log.e("not","null");
                Log.e("is",user.getUserEmail());
                Log.e("is",getApplicationContext().getClass()+"");
                MyApplication appState = (MyApplication)getApplicationContext();
                appState.setUser(user);}
            else
            {
                Log.e("is null",getApplicationContext().getClass()+"");
                MyApplication appState = (MyApplication)getApplicationContext();
                appState.setUser(null);
            }

        }
        return sharedString.contentEquals("Not login");
    }

    @Override
    public void afterParesResult(Object data, int code) {
        categoryList = (ArrayList) data;
    }

    @Override
    public void errorParesResult(String errorMessage, int code) {

    }

    private String[] getCategoryNames()
    {
        String[] names=new String[categoryList.size()];
        for(int i=0;i<categoryList.size();i++)
        {
            names[i]=categoryList.get(i).getCategoryName();
        }
        return names;
    }

    private int getCategoryIdByName(String name)
    {
        for(int i=0;i<categoryList.size();i++)
        {
            if(categoryList.get(i).getCategoryName().equals(name))
            {
                return categoryList.get(i).getCategoryId();
            }
        }
        return -1;
    }
}
