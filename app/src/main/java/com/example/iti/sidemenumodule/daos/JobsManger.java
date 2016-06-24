package com.example.iti.sidemenumodule.daos;

import android.content.Context;
import android.util.Log;

import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.network_manager.AfterAsynchronous;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.example.iti.sidemenumodule.network_manager.HttpClientConn;
import com.example.iti.sidemenumodule.network_manager.URLManager;
import com.example.iti.sidemenumodule.view.JobsFragment;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ahmed_telnet on 6/6/2016.
 */
public class JobsManger implements AfterAsynchronous {
    private static ArrayList<Project> jobsList;
    private static ArrayList<Project> workJobsList;
    private static ArrayList<Project> myJobsList;
    private static JobsManger jobsManger;
    private static Context context;
    private AfterPraseResult view;

    public static JobsManger getInstance(Context c) {
        if (jobsManger == null) {
            context = c;
            jobsManger = new JobsManger();
        }
        return jobsManger;
    }

    public void getJobsList(AfterPraseResult view) {
        this.view = view;
        if (jobsList == null) {
            String jobsURL = URLManager.getJobsURL;
            HttpClientConn loginConnection = new HttpClientConn(this, context);
            RequestParams requestParam = new RequestParams();
            loginConnection.RequestService(jobsURL, requestParam, 1, null, 1);
        } else {
            this.view.afterParesResult(jobsList,0);
        }
    }


    public void getMyJobsList(AfterPraseResult view, int userId) {
        this.view = view;
        String myJobsURL = URLManager.getMyJobsURL;
        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.put("uId", userId);
        loginConnection.RequestService(myJobsURL, requestParam, 4, null, 0);
    }


    public void getWorkJobsList(AfterPraseResult view, int userId) {
        this.view = view;
        String workJobsURL = URLManager.getWorkJobsURL;
        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.put("uId", userId);
        loginConnection.RequestService(workJobsURL, requestParam, 3, null, 0);
    }

    @Override
    public void afterExecute(String message, int code) {
        if (code == 1) {
            jobsList = parseJson(message);
            view.afterParesResult(jobsList, code);

        } else if (code == 2) {
            try {
                JSONObject object = new JSONObject(message);
                String myMessage = object.getString("output");
                view.afterParesResult(myMessage,code);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (code == 3) {
            workJobsList = parseJsonForWork(message);
            view.afterParesResult(workJobsList,code);
        } else if (code == 4) {
            myJobsList = parseJsonForMyWork(message);
            view.afterParesResult(myJobsList,code);
        }

    }


    private ArrayList<Project> parseJson(String message) {
        ArrayList<Project> returnArrayList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(message);
            String myData = object.getString("projects");
            JSONArray jsonArray = new JSONArray(myData);
            //  Log.i("opa", "opa");
            jobsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                Project project = new Project();
                String element = jsonArray.getString(i);
                JSONObject projectObject = new JSONObject(element);
                project.setProjectId(projectObject.getInt("projectId"));
                project.setProjectName(projectObject.getString("projectName"));
                project.setProjectDescription(projectObject.getString("projectDescription"));
                project.setBudget(projectObject.getInt("budget"));
                String date = projectObject.getString("startDate");
                String date2 = projectObject.getString("projectDeadLine");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                try {
                    Log.i("ahmedtest", "opalala");
                    Date d = dateFormat.parse(date);
                    Log.i("ahmedtest", "opa");
                    project.setStartDate(d);
                    d = dateFormat.parse(date2);
                    project.setProjectDeadLine(d);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String imagesJsonArrayString = projectObject.getString("projectsimageses");
                JSONArray imagesJsonArray = new JSONArray(imagesJsonArrayString);
                if(imagesJsonArray.length()>0) {
                    String imageObjectString = imagesJsonArray.getString(0);
                    JSONObject imageObject = new JSONObject(imageObjectString);
                    project.setImageURL(URLManager.ip+"/itiProject"+imageObject.getString("imageUrl"));
                }
                String detailsesJsonArrayString = projectObject.getString("detailses");
                JSONArray detailsesJsonArray = new JSONArray(detailsesJsonArrayString);
                if(detailsesJsonArray.length()>0) {
                    String detailsesObjectString = detailsesJsonArray.getString(0);
                    JSONObject detailsesObject = new JSONObject(detailsesObjectString);
                    project.setStatusOfProject(detailsesObject.getString("statusOfProjects"));
                }
                // Log.i("gsontest", project.getProjectName());
                returnArrayList.add(project);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnArrayList;
    }


    private ArrayList<Project> parseJsonForWork(String message) {
        ArrayList<Project> returnArrayList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(message);
            String myData = object.getString("projectsOfUserWork");
            JSONArray jsonArray = new JSONArray(myData);
            //  Log.i("opa", "opa");
            jobsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                Project project = new Project();
                String element = jsonArray.getString(i);
                JSONObject projectObject = new JSONObject(element);
                project.setProjectId(projectObject.getInt("projectId"));
                project.setProjectName(projectObject.getString("projectName"));
                project.setProjectDescription(projectObject.getString("projectDescription"));
                project.setBudget(projectObject.getInt("budget"));
                String date = projectObject.getString("startDate");
                String date2 = projectObject.getString("projectDeadLine");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                try {
                    Log.i("ahmedtest", "opalala");
                    Date d = dateFormat.parse(date);
                    Log.i("ahmedtest", "opa");
                    project.setStartDate(d);
                    d = dateFormat.parse(date2);
                    project.setProjectDeadLine(d);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String imagesJsonArrayString = projectObject.getString("projectsimageses");
                JSONArray imagesJsonArray = new JSONArray(imagesJsonArrayString);
                if(imagesJsonArray.length()>0) {
                    String imageObjectString = imagesJsonArray.getString(0);
                    JSONObject imageObject = new JSONObject(imageObjectString);
                    project.setImageURL(URLManager.ip+"/itiProject"+imageObject.getString("imageUrl"));
                }
                String detailsesJsonArrayString = projectObject.getString("detailses");
                JSONArray detailsesJsonArray = new JSONArray(detailsesJsonArrayString);
                if(detailsesJsonArray.length()>0) {
                    String detailsesObjectString = detailsesJsonArray.getString(0);
                    JSONObject detailsesObject = new JSONObject(detailsesObjectString);
                    project.setStatusOfProject(detailsesObject.getString("statusOfProjects"));
                }

                // Log.i("gsontest", project.getProjectName());
                returnArrayList.add(project);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnArrayList;
    }


    private ArrayList<Project> parseJsonForMyWork(String message) {
        ArrayList<Project> returnArrayList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(message);
            String myData = object.getString("projectsOfUserHire");
            JSONArray jsonArray = new JSONArray(myData);
            //  Log.i("opa", "opa");
            jobsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                Project project = new Project();
                String element = jsonArray.getString(i);
                JSONObject projectObject = new JSONObject(element);
                element = projectObject.getString("projectsforusers");
                projectObject = new JSONObject(element);
                project.setProjectId(projectObject.getInt("projectId"));
                project.setProjectName(projectObject.getString("projectName"));
                project.setProjectDescription(projectObject.getString("projectDescription"));
                project.setBudget(projectObject.getInt("budget"));
                String date = projectObject.getString("startDate");
                String date2 = projectObject.getString("projectDeadLine");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                try {
                    Log.i("ahmedtest", "opalala");
                    Date d = dateFormat.parse(date);
                    Log.i("ahmedtest", "opa");
                    project.setStartDate(d);
                    d = dateFormat.parse(date2);
                    project.setProjectDeadLine(d);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String imagesJsonArrayString = projectObject.getString("projectsimageses");
                JSONArray imagesJsonArray = new JSONArray(imagesJsonArrayString);
                if(imagesJsonArray.length()>0) {
                    String imageObjectString = imagesJsonArray.getString(0);
                    JSONObject imageObject = new JSONObject(imageObjectString);
                    project.setImageURL(URLManager.ip+"/itiProject"+imageObject.getString("imageUrl"));
                }
                String detailsesJsonArrayString = projectObject.getString("detailses");
                JSONArray detailsesJsonArray = new JSONArray(detailsesJsonArrayString);
                if(detailsesJsonArray.length()>0) {
                    String detailsesObjectString = detailsesJsonArray.getString(0);
                    JSONObject detailsesObject = new JSONObject(detailsesObjectString);
                    project.setStatusOfProject(detailsesObject.getString("statusOfProjects"));
                }

                // Log.i("gsontest", project.getProjectName());
                returnArrayList.add(project);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnArrayList;
    }


    @Override
    public void errorInExecute(String errorMessage) {
        view.errorParesResult(errorMessage,0);
    }

    public void postProject(Project project, AfterPraseResult view) {
        this.view = view;
        String postProjectURL = URLManager.postProjectURL;
        HttpClientConn loginConnection = new HttpClientConn(this, context);
        RequestParams requestParam = new RequestParams();
        requestParam.put("projectName", project.getProjectName());
        requestParam.put("categoryId", project.getCategory());
        requestParam.put("userId", project.getUsers());
        requestParam.put("skilltables", project.getProjectSkills());
        requestParam.put("projectDescription", project.getProjectDescription());
        requestParam.put("budget", project.getBudget());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String timeFormat = sdf.format(project.getStartDate());
        requestParam.put("startDate", timeFormat);
        timeFormat = sdf.format(project.getProjectDeadLine());
        requestParam.put("projectDeadLine", timeFormat);
        requestParam.put("content", project.getImageContent()==null?"":project.getImageContent());
        requestParam.put("name", project.getProjectName().trim());
        requestParam.put("tagsofprojectses", "java,php,mmm");
        requestParam.put("tId", project.getTypeId());
        requestParam.put("width", project.getWidth());
        requestParam.put("long", project.getProjectLong());
        requestParam.put("hight", project.getHight());
        requestParam.put("color", project.getColor());
        requestParam.put("material", project.getMaterial());
        requestParam.put("location", project.getLocation()==null?"":project.getLocation());

        loginConnection.RequestService(postProjectURL, requestParam, 2, null, 0);
    }


}
