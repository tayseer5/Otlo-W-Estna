package com.example.iti.sidemenumodule.daos;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.iti.sidemenumodule.controller.MyApplication;
import com.example.iti.sidemenumodule.model.Users;
import com.example.iti.sidemenumodule.network_manager.AfterAsynchronous;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.network_manager.HttpClientConn;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;

/**
 * Created by ITI on 03/06/2016.
 */





public class UserManager implements AfterAsynchronous {


    AfterPraseResult afterPraseResult;
    Context context;
    int code;
    public UserManager(Context context,AfterPraseResult afterPraseResult) {
        this.context = context;
        this.afterPraseResult=afterPraseResult;
    }

    public void Login(String email, String password,int code) {
        this.code=code;
        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.add("email",email );
        requestParam.add("pass",password);
        loginConnection.RequestService(URLManager.loginURL, requestParam, 0, null, URLManager.postConnectionType);
    }

    public void registration(Users userData,int code) {
        this.code=code;
        HttpClientConn registrationConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.add("userEmail", userData.getUserEmail());
        requestParam.add("password", userData.getPassword());
        requestParam.add("gender", "");
        requestParam.add("userName",userData.getUserName());
        requestParam.add("userImage", "");
        requestParam.add("0", "");
        requestParam.add("country", "");
        requestParam.add("governorate", "");
        requestParam.add("ciry", "");
        requestParam.add("street", "");
        requestParam.add("summery", "");
        requestParam.add("Title", "");
        requestParam.add("identifire", "");
        requestParam.add("mobiles", " , ");
        requestParam.add("phones", " , ");
        String skills = "";
        for (int i=0;i<userData.getUserSkills().size();i++)
        {
            skills= skills.concat(userData.getUserSkills().get(i).getSkillId()+",");
        }
        requestParam.add("skill", skills);
        registrationConnection.RequestService(URLManager.registrationURL, requestParam, 1, null, URLManager.postConnectionType);
    }

    public void logOut() {

    }

    public void updateUser(Users userNewData) {

    }

    public void uploadPhoto(Context context, String encoded_image) {

        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.add("name","IMG_160743_20160608.jpg");
        requestParam.add("content",encoded_image);
        loginConnection.RequestService("http://10.0.3.2:8084/itiProject/rest/hassan/mona", requestParam, 5, null, URLManager.postConnectionType);
    }

    @Override
    public void afterExecute(String response, int code) {
        Log.e("code s", code + "");

        switch (code) {
            case 0:
                //call login process
                loginProcess(response,code);
                break;
            case 1:
                registrationProcess(response,code);
                Log.e("responce", response);
                break;
            case 2:
                //call update process
                break;
            case 3:
                //test upload image

                break;
            case 5 :
                Log.e("response",response);
                afterPraseResult.afterParesResult(response,code);
                break;
            default:
                break;
        }

    }

    @Override
    public void errorInExecute(String errorMessage) {
        Log.e("code e",code+"");
        switch (code) {
            case 0:
                //call login process
                loginProcess(errorMessage,code);
                break;
            case 1:
                registrationProcess(errorMessage,code);
                Log.e("errorMessage", errorMessage);
                break;
            case 2:
                //call update process
                break;
            case 3:
                //test upload image

                break;
            case 5 :
                Log.e("errorMessage",errorMessage);
                afterPraseResult.afterParesResult(errorMessage,code);
                break;
            default:
                break;

        }
    }
    private void registrationProcess (String response,int code)
    {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject myJsonObject = parser.parse(response).getAsJsonObject();
        JsonElement output =myJsonObject.get("output");
        afterPraseResult.afterParesResult(output.getAsString().trim(),code);
    }
    private void loginProcess (String response,int code)
    {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject myJsonObject = parser.parse(response).getAsJsonObject();
        JsonElement message =myJsonObject.get("message");
        if (message.getAsString().trim().contains("true")) {
            JsonObject user = myJsonObject.getAsJsonObject("user");
            if (user!=null)
            {
                SharedPreferences sharedpreferences =context.getSharedPreferences("loginPrefrence", Context.MODE_PRIVATE);
                SharedPreferences.Editor userPref = sharedpreferences.edit();
                String userJsonObj = gson.toJson(user);
                userPref.putString("user",userJsonObj);
                Users userObj2 = gson.fromJson(userJsonObj,Users.class);
                Log.e("json1",userJsonObj);
                Log.e("json",userObj2.getUserEmail());
                userPref.commit();
                MyApplication appState = (MyApplication)context.getApplicationContext();
                appState.setUser(userObj2);
                afterPraseResult.afterParesResult(null,code);

            }
            else {
                afterPraseResult.errorParesResult("some thing wrong happend", code);
            }
        }
        else
        {
            afterPraseResult.errorParesResult("your mail or password is wrong",code);
        }
    }

}

