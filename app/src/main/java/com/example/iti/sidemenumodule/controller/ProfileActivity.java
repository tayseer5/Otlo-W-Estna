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
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.view.ProfileFragment;
import com.google.gson.Gson;
import com.norbsoft.typefacehelper.TypefaceHelper;

public class ProfileActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TypefaceHelper.typeface(this);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MedicineActivity.this, "Alarm....", Toast.LENGTH_LONG).show();
                NavUtils.navigateUpFromSameTask(ProfileActivity.this);
            }
        });
        Fragment fragment=new ProfileFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.fragment, fragment, "postProject");
        transaction.commit();
        if(IsNotLogin()){

            Toast.makeText(getApplicationContext(), getString(R.string.you_must_login), Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("back press","back press");
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
}
