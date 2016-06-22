package com.example.iti.sidemenumodule.daos;

import android.content.Context;
import android.util.Log;

import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Portfolio;
import com.example.iti.sidemenumodule.network_manager.AfterAsynchronous;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.network_manager.HttpClientConn;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed_telnet on 6/10/2016.
 */
public class PortfolioManger implements AfterAsynchronous {
    private static ArrayList<Portfolio> portfoliosFromCategory;
    private static PortfolioManger portfolioManger;
    private static Context context;
    private AfterPraseResult view;
    public  static PortfolioManger getInstance(Context c){
        if(portfolioManger==null)
        {
            context=c;
            portfolioManger=new PortfolioManger();
        }
        return portfolioManger;
    }

    public void getCategoriesList(AfterPraseResult view,int categoryId){
        this.view=view;
            String categoryURL= URLManager.getPortfoliosFromCategoryURL;
            HttpClientConn loginConnection = new HttpClientConn(this, context);
            RequestParams requestParam = new RequestParams();
        requestParam.put("categoryId",categoryId);
        requestParam.put("footer",0);
            loginConnection.RequestService(categoryURL, requestParam, 1, null, 3);
    }

    @Override
    public void afterExecute(String message, int code) {
        if (code==1){
            try {
                JSONObject object=new JSONObject(message);
                String myData =object.getString("portofolios");
                JSONArray jsonArray=new JSONArray(myData);
                portfoliosFromCategory=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++) {
                    Gson gson = new Gson();
                    String element=jsonArray.getString(i);
                    JSONObject portofolioObject=new JSONObject(element);
                    Portfolio portfolio=new Portfolio();
                    portfolio.setPortofolioId(portofolioObject.getInt("portofolioId"));
                    portfolio.setPortDescription(portofolioObject.getString("portofolioDescription"));
                    String imagesJsonArrayString = portofolioObject.getString("portofolioimageses");
                    JSONArray imagesJsonArray = new JSONArray(imagesJsonArrayString);
                    if(imagesJsonArray.length()>0) {
                        String imageObjectString = imagesJsonArray.getString(0);
                        JSONObject imageObject = new JSONObject(imageObjectString);
                        portfolio.setImage(URLManager.ip+"/itiProject"+imageObject.getString("portfolioImageUrl"));
                    }
                    portfoliosFromCategory.add(portfolio);
                }
                view.afterParesResult(portfoliosFromCategory,code);

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