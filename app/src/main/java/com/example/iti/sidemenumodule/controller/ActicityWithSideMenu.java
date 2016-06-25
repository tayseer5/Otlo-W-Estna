package com.example.iti.sidemenumodule.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.example.iti.sidemenumodule.view.JobsFragment;
import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.view.MyProjectListFragment;
import com.google.gson.Gson;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;
//import com.example.iti.sidemenumodule.view.WorkStreamFragment;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;


public class ActicityWithSideMenu extends NavigationLiveo implements OnItemClickListener {
    private HelpLiveo mHelpLiveo;
    private Toolbar toolbar;

    @Override
    public void onInt(Bundle bundle) {
        Log.e("in init", "yes");
        drawSideMenu();
        TypefaceHelper.typeface(this);
    }
    private void drawSideMenu() {
        HelpLiveo mHelpLiveo = new HelpLiveo();
        //is not login
        if(IsNotLogin())
        {
            //Menu Elements

            mHelpLiveo.add(getString(R.string.homepage), R.mipmap.home);
            // mHelpLiveo.addSeparator(); // Item separator
            mHelpLiveo.add(getString(R.string.show_profile), R.mipmap.briefcase);
            mHelpLiveo.add(getString(R.string.dash_board), R.mipmap.work_flow);
            mHelpLiveo.add(getString(R.string.post_project), R.mipmap.job_post);
            mHelpLiveo.add(getString(R.string.customer_project), R.drawable.ic_https_black_24dp);
            mHelpLiveo.add(getString(R.string.sigin_in), R.drawable.ic_https_black_24dp);
            this.userBackground.setImageResource(R.drawable.ic_user_background_first);
            this.userBackground.setColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);
        }
        else {
            //This is the Header of side menu
            MyApplication userObject = (MyApplication) getApplicationContext();
            Users user = userObject.getUser();
            if(userObject!=null) {
                this.userName.setText(user.getUserName());
                this.userEmail.setText(user.getUserEmail());
                //this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
                Picasso.with(this)
                        .load(URLManager.ip+"/itiProject"+user.getUserImageUrl())
                        .placeholder(R.drawable.ic_rudsonlive)
                        .into(userPhoto);
            }
            //Menu Elements
            mHelpLiveo.add(getString(R.string.homepage), R.mipmap.home);
            mHelpLiveo.add(getString(R.string.show_profile), R.mipmap.briefcase);
            if (user.getTypeOfBusiness().equals("both")||user.getTypeOfBusiness().equals("hire")) {
                mHelpLiveo.add(getString(R.string.post_project), R.mipmap.job_post);
                mHelpLiveo.add(getString(R.string.customer_project), R.drawable.ic_https_black_24dp);
            }
            if (user.getTypeOfBusiness().equals("both")||user.getTypeOfBusiness().equals("work")) {
                mHelpLiveo.add(getString(R.string.dash_board), R.mipmap.work_flow);
            }
            mHelpLiveo.add(getString(R.string.log_out), R.drawable.ic_android_black_24dp);
            this.userBackground.setImageResource(R.drawable.ic_user_background_first);
            this.userBackground.setColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);

        }
        mHelpLiveo.add(getString(R.string.settings), R.drawable.ic_android_black_24dp);
        mHelpLiveo.add(getString(R.string.about), R.drawable.ic_android_black_24dp);
        with(this,1) // default theme is dark ,R.color.nliveo_black
                .startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())
                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .removeFooter()
                .build();
        TypefaceHelper.typeface(this);
    }
    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("onClickPhoto","onClickPhoto");
            Intent profileIntent = new Intent(ActicityWithSideMenu.this,ProfileActivity.class);
            startActivity(profileIntent);
            closeDrawer();
        }
    };
    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
            Log.e("onPrepareOptionsMenu","onPrepareOptionsMenu");
        }
    };



    @Override
    public void onItemClick(int position) {
        Fragment mFragment=null;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        switch (position){
            case 1:
                Intent profileIntent = new Intent(ActicityWithSideMenu.this,ProfileActivity.class);
                startActivity(profileIntent);
                closeDrawer();
                break;
            case 2:
                mFragment=new MyProjectListFragment();
                break;
            case 3:
                Intent postIntent = new Intent(ActicityWithSideMenu.this,PostProjectMainActivity.class);
                startActivity(postIntent);
                closeDrawer();
                break;
            case 4:
                mFragment=new JobsFragment(true);
                break;
            case 5:
                Log.e("IsNotLogin","5");
                if (IsNotLogin())
                {
                    Intent intent = new Intent(this,RegistrationActivity.class);
                    startActivityForResult(intent, 0);
                    Log.e("in if","in if");
                }
                else
                {
                    Log.e("in else","in else");
                    //remove shared prefrence
                }
                break;
            default:
              //  mFragment = MainFragment.newInstance(0);
                mFragment =new SimpleTabsActivity();
                break;
        }

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
        toolbar=getToolbar();

        toolbar.setBackgroundColor(getResources().getColor(R.color.light_green));
      //  @TargetApi()
      //  toolbar.setElevation((float) 0.0);
        toolbar.setTitleTextColor(getResources().getColor(R.color.light_gray));
        toolbar.inflateMenu(R.menu.menu_protoflio);
//        TypefaceHelper.typeface(this);
//        TypefaceCollection typeface=new TypefaceCollection.Builder()
//                .set(Typeface.NORMAL,Typeface.createFromAsset(getAssets(),"fonts/DroidKufi-Regular.ttf"))
//                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf"))
//                .create();
//        TypefaceHelper.init(typeface);
    }
    private boolean IsNotLogin()
    {
        Log.e("IsNotLogin", "IsNotLogin");
        SharedPreferences sharedpreferences = getSharedPreferences("loginPrefrence", Context.MODE_PRIVATE);
        String sharedString = sharedpreferences.getString("user", "Not login");
        if(!sharedString.contentEquals("Not login")) {

            Gson gson = new Gson();
            Users user = gson.fromJson(sharedString,Users.class);
            if (user!=null)
            {Log.e("not","null");
                Log.e("is",user.getUserEmail());
                Log.e("type",user.getTypeOfBusiness());
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
    protected void onStop() {
        Log.e("onStop","onStop");
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        Log.e("onPostResume","onPostResume");
        super.onPostResume();
        toolbar=getToolbar();
       // toolbar.setElevation((float) 0.0);
    }

    @Override
    protected void onDestroy() {
        Log.e("onDestroy","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.e("onRestart","onRestart");
        if(!IsNotLogin())
        {
            Log.e("reload","on Int");

        }
        super.onRestart();
       // toolbar.setElevation((float) 0.0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        with(this,1).recreate();
     //   toolbar.setElevation((float) 0.0);
    }
}