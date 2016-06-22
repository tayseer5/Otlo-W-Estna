package com.example.iti.sidemenumodule.daos;

import android.content.Context;
import android.util.Log;

import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Employee;
import com.example.iti.sidemenumodule.model.Portfolio;
import com.example.iti.sidemenumodule.model.Users;
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
 * Created by Ahmed_telnet on 6/6/2016.
 */
public class EmployeeManger implements AfterAsynchronous{
    private static ArrayList<Users> employeesList;
    private static EmployeeManger employeeManger;
    private AfterPraseResult view;
    private static Context context;
    public  static EmployeeManger getInstance(Context c){
        if(employeeManger==null)
        {
            context=c;
            employeeManger=new EmployeeManger();
        }
        return employeeManger;
    }

    public void getEmployeesList(AfterPraseResult view){
        this.view=view;
        if(employeesList==null){
            String employeeURL= URLManager.getEmployeesURL;
            HttpClientConn loginConnection = new HttpClientConn(this, context);
            RequestParams requestParam = new RequestParams();
            loginConnection.RequestService(employeeURL, requestParam, 1, null, 1);
        }else {
            this.view.afterParesResult(employeesList,0);
        }
    }

    @Override
    public void afterExecute(String message, int code) {
        if (code==1){
            try {
                JSONObject object=new JSONObject(message);
                String myData =object.getString("users");
                JSONArray jsonArray=new JSONArray(myData);
                employeesList=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++) {
                    String element=jsonArray.getString(i);
                    Users emoloyee = new Users();
                    JSONObject emoloyeeObject=new JSONObject(element);
                    emoloyee.setUserId(emoloyeeObject.getInt("userId"));
                    emoloyee.setUserEmail(emoloyeeObject.getString("userEmail"));
                    emoloyee.setUserImageUrl(URLManager.ip + "/itiProject" + emoloyeeObject.getString("userImageUrl"));
                  //  emoloyee.setPassword(emoloyeeObject.getString("password"));
                    emoloyee.setGender(emoloyeeObject.getString("gender").equals("1"));
                    emoloyee.setUserName(emoloyeeObject.getString("userName"));
                    emoloyee.setPed(emoloyeeObject.getInt("ped"));
                    emoloyee.setCountry(emoloyeeObject.getString("country"));
                    emoloyee.setGovernorate(emoloyeeObject.getString("governorate"));
                    emoloyee.setCity(emoloyeeObject.getString("city"));
                    emoloyee.setStreet(emoloyeeObject.getString("street"));
                    emoloyee.setSummery(emoloyeeObject.getString("summery"));
                    emoloyee.setProfessinalTiltle(emoloyeeObject.getString("professinalTiltle"));
                    emoloyee.setIdentefire(emoloyeeObject.getString("identefire"));
                    emoloyee.setToken(emoloyeeObject.getString("token"));
                    emoloyee.setRate(emoloyeeObject.getInt("rate"));
                    String portofolioJsonArrayString = emoloyeeObject.getString("portofolioforusers");
                    JSONArray portofolioJsonArray = new JSONArray(portofolioJsonArrayString);

//                    for(int j=0;i<portofolioJsonArray.length();i++) {
//                        String portofolioObjectString = portofolioJsonArray.getString(i);
//                        JSONObject portofolioObject = new JSONObject(portofolioObjectString);
//                        Portfolio
//                        project.setImageURL(portofolioObject.getString("imageUrl"));
//                    }
                    employeesList.add(emoloyee);
                }
                view.afterParesResult(employeesList,code);
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
