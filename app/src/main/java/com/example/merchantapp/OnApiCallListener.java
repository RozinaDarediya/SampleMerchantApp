package com.example.merchantapp;

/**
 * Created by ashish on 13/10/17.
 */

public interface OnApiCallListener {

    void onSuccess(int responseCode, String responseString, String requestType);

    void onFailure(String errorMessage);

}
