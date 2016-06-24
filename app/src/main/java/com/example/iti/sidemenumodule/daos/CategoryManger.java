package com.example.iti.sidemenumodule.daos;

import android.content.Context;
import android.util.Log;

import com.example.iti.sidemenumodule.model.Attribute;
import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Message;
import com.example.iti.sidemenumodule.model.Type;
import com.example.iti.sidemenumodule.network_manager.AfterAsynchronous;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.network_manager.HttpClientConn;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.AnnotationFormatError;
import java.util.ArrayList;

/**
 * Created by Ahmed_telnet on 6/5/2016.
 */
public class CategoryManger implements AfterAsynchronous{
    private static ArrayList<Category> categoriesList;
    private static ArrayList<Type> typesList;
    private static ArrayList<Attribute> attributesList;
    private static CategoryManger categoryManger;
    private static Context context;
    private AfterPraseResult view;
    public  static CategoryManger getInstance(Context c){
        if(categoryManger==null)
        {
            context=c;
         categoryManger=new CategoryManger();
        }
            return categoryManger;
    }

    public void getCategoriesList(AfterPraseResult view){
        this.view=view;
        if(categoriesList==null){
            String categoryURL= URLManager.getCategoryURL;
            HttpClientConn loginConnection = new HttpClientConn(this, context);
            RequestParams requestParam = new RequestParams();
            loginConnection.RequestService(categoryURL, requestParam, 1, null, 1);
        }else {
            this.view.afterParesResult(categoriesList,0);
        }
    }

    public void getTypesList(AfterPraseResult view,int categoryId){
        this.view=view;
            String typesURL= URLManager.getTypesURL;
            HttpClientConn loginConnection = new HttpClientConn(this, context);
            RequestParams requestParam = new RequestParams();
        requestParam.put("catId",categoryId);
            loginConnection.RequestService(typesURL, requestParam, 2, null, 3);
    }

    public void getAttList(AfterPraseResult view,int typeId){
        this.view=view;
        String attrURL= URLManager.getAttrURL;
        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.put("tId",typeId);
        loginConnection.RequestService(attrURL, requestParam, 3, null, 3);
    }




    @Override
    public void afterExecute(String message, int code) {
        if (code==1){
            try {
                JSONObject object=new JSONObject(message);
                String myData =object.getString("categories");
                JSONArray jsonArray=new JSONArray(myData);
                categoriesList=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++) {
                    Gson gson = new Gson();
                    String element=jsonArray.getString(i);
                    Category category = gson.fromJson(element, Category.class);
                    Log.i("gsontest",category.getCategoryName());
                    String imageplace=category.getImageOfCategoryUrl();
                    Log.e("image from cat",category.getImageOfCategoryUrl());
                    Log.e("imageplace",imageplace);
                    category.setImageOfCategoryUrl(URLManager.ip+"/itiProject"+imageplace);
                    Log.e("image from cat 2",category.getImageOfCategoryUrl());
                    categoriesList.add(category);
                }
                view.afterParesResult(categoriesList,code);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(code==2)
        {
            JSONObject object= null;
            try {
                object = new JSONObject(message);
                String myData =object.getString("types");
                JSONArray jsonArray=new JSONArray(myData);
                typesList=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++) {
                    Gson gson = new Gson();
                    String element = jsonArray.getString(i);
                    Type type = gson.fromJson(element, Type.class);
                    typesList.add(type);
                }
                view.afterParesResult(typesList,code);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else if(code==3)
        {
            JSONObject object= null;
            try {
                object = new JSONObject(message);
                String myData =object.getString("Attribute");
                JSONArray jsonArray=new JSONArray(myData);
                attributesList=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++) {
                    Gson gson = new Gson();
                    String element = jsonArray.getString(i);
                    Attribute attribute = gson.fromJson(element, Attribute.class);

                    attributesList.add(attribute);
                }
                view.afterParesResult(attributesList,code);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void errorInExecute(String errorMessage) {
        view.errorParesResult("",0);
    }
}
