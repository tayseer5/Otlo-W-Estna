package com.example.iti.sidemenumodule.network_manager;

/**
 * Created by ITI on 04/06/2016.
 */
public class URLManager {
    public static String ip="http://192.168.1.4:8084";
    public static String getCategoryURL=ip+"/itiProject/rest/categoryURL/getCategories";
    public static String getEmployeesURL=ip+"/itiProject/rest/user/getMaxUser";
    public static String getJobsURL=ip+"/itiProject/rest/project/getLastProject?footer=0";
    public static String postProjectURL=ip+"/itiProject/rest/project/Project";
    public static String getMyJobsURL=ip+"/itiProject/rest/project/projectsOfUserHire";
    public static String getWorkJobsURL=ip+"/itiProject/rest/project/projectsOfUserWork";
    public static final int postConnectionType = 0;
    public static final int  fail = 0;
    public static final int success = 1;
    public static final int getConnectionType = 1;
    public static final String imageURL = "";
    public static  final  String loginURL = ip+"/itiProject/rest/authentication/login";
    public static final String registrationURL = ip+"/itiProject/rest/authentication/register";
    public static final String skillsURl = ip+"/itiProject/rest/authentication/getSkills";
    public static String postProposalURL=ip+"/itiProject/rest/porposa/insertPorposer";
}



