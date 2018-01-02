package com.example.merchantapp.webservice;

/**
 * Created by ashish on 13/10/17.
 */

public class Api {

    //Dump Condition
    public static final int ConnectionTimeout = 30000; // = 30 seconds
    public static final int ConnectionSoTimeout = 60000; // = 60 seconds


    //UpdateUserProfile Codes
    public static final int ResponseOk = 200;
    public static final int ResponseCreated = 201;
    public static final int ResponsePageError = 400;
    public static final int ResponseUnauthorized = 401;
    public static final int ResponseServerError = 500;


    //UpdateUserProfile Params
    public static final String data = "data";
    public static final String error = "error";
    public static final String code = "code";
    public static final String message = "message";
    public static final String description = "description";
    public static final String response_data = "response_data";


    // App URL
    // Live API
    public static final String MainUrl = "http://192.168.1.14/zodimatch/webservices/";
    public static final String ActionRegistrationByPhone = "user/register";
    public static final String ActionRegistrationBySocial = "user/register-social";
    public static final String ActionValidateOPT = "user/validate-otp";
    public static final String ActionUpdateProfile = "user/update-profile";
    public static final String ActionUploadImage = "user/upload-image";
}
