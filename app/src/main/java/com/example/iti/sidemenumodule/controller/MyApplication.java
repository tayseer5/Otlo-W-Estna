package com.example.iti.sidemenumodule.controller;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.example.iti.sidemenumodule.model.Users;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

/**
 * Created by Loma&M on 18/05/2016.
 */
public class MyApplication extends Application {
    Users user;
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceCollection typeface=new TypefaceCollection.Builder()
                .set(Typeface.NORMAL,Typeface.createFromAsset(getAssets(),"fonts/DroidKufi-Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf"))
                .create();
        TypefaceHelper.init(typeface);
        Log.i("ahmed","ahmed");

    }
}
