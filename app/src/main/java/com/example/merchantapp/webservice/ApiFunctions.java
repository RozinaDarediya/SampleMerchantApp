package com.example.merchantapp.webservice;

import android.content.Context;
import android.util.Log;

import com.example.merchantapp.OnApiCallListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ashish on 13/10/17.
 */

public class ApiFunctions implements Serializable {

    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    private Context context;
    private OnApiCallListener acListener;
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private Gson gson;
    private MediaType CONTENT_TYPE = MediaType.parse("multipart/form-data; charset=utf-8");
    private String url = "http://192.168.1.101/Paytm_checksum/generateChecksum.php";

    // CONSTRUCTOR WITH 1 PARAMETER
    public ApiFunctions(Context context) {
        this.client = new OkHttpClient();
        this.context = context;
        this.acListener = (OnApiCallListener) context;
        this.gson = new Gson();

        try {
            builder.connectTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
            builder.writeTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
            builder.readTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e("Exception : ",e.toString());
        }

        File cacheDirectory = new File(context.getCacheDir(), "http");
        int cacheSize = 10 * 1024 * 1024;
        try {
            Cache cache = new Cache(cacheDirectory, cacheSize);
            builder.cache(cache);
            client = builder.build();
        } catch (Exception e) {
            Log.v("Exception " , e.getMessage());
        }
    }

    //  CONSTRUCTOR
    public ApiFunctions(Context context, OnApiCallListener acListener) {

        this.client = new OkHttpClient();
        this.context = context;
        this.acListener = acListener;
        this.gson = new Gson();

        try {
            builder.connectTimeout(Api.ConnectionTimeout, TimeUnit.MILLISECONDS);
            builder.writeTimeout(Api.ConnectionTimeout, TimeUnit.MILLISECONDS);
            builder.readTimeout(Api.ConnectionTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }

        File cacheDirectory = new File(context.getCacheDir(), "http");
        int cacheSize = 10 * 1024 * 1024;
        try {
            Cache cache = new Cache(cacheDirectory, cacheSize);
            builder.cache(cache);
            client = builder.build();
        } catch (Exception e) {
            Log.v("Exception " , e.getMessage());
        }
    }//ApiFunctions

    //executeRequest
    public void executeRequest(final String url, Request request) {

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                acListener.onFailure(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int responseCode = response.code();
                String responseString = response.body().string();
                acListener.onSuccess(responseCode, responseString, url);
            }
        });
    }//executeRequest

    public void checkSum(String merchantKey, TreeMap<String, String> parameters){
        com.example.merchantapp.webservice.Request request = new com.example.merchantapp.webservice.Request(merchantKey, parameters);
        String jsonData = gson.toJson(request);
        RequestBody postData = RequestBody.create(JSON, jsonData);
        Request requestFile = new Request.Builder().url(url).post(postData).build();
        executeRequest(url, requestFile);
    }

   /* // API Function For VALIDATE OTP FOR REGISTERING USING PHONE
    public void Request(String url, Request validateOTP){
        try{
            String jsonData = gson.toJson(validateOTP);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(e.toString());
        }
    }
*/






    /*// API Function to Register Device
    public void registerDevice(String deviceUDID, String deviceToken, String appName, String appVersion, String deviceSystemVersion, String deviceModel, String deviceName, String platformtype) {
            try {
                RequestBody postData = new FormBody.Builder().add("deviceUDID", deviceUDID).add("deviceToken", deviceToken).add("appName", appName).add("appVersion", appVersion).add("deviceSystemVersion", deviceSystemVersion).add("deviceModel", deviceModel).add("deviceName", deviceName).add("platformType", platformtype).build();
                Request request = new Request.Builder().url(MainUrl + ActionRegisterDevice).post(postData).build();
                executeRequest(MainUrl + ActionRegisterDevice, request);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }//registerDevice
    */
}
//https://github.com/Paytm-Payments/Paytm_Android_App_Kit/blob/master/SampleMerchantApp/app/src/main/java/com/example/merchantapp/MerchantActivity.java