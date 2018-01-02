package com.example.merchantapp.webservice;

import com.google.gson.annotations.SerializedName;

import java.util.TreeMap;


public class Request {

    @SerializedName("merchantKey")
    private String merchantKey;

    @SerializedName("parameters")
    private TreeMap<String, String> parameters;

    public Request(String merchantKey, TreeMap<String, String> parameters) {
        this.merchantKey = merchantKey;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Request{" +
                "merchantKey='" + merchantKey + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}