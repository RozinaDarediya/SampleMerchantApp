package com.example.merchantapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.merchantapp.webservice.ApiFunctions;
import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is the sample app which will make use of the PG SDK. This activity will
 * show the usage of Paytm PG SDK API's.
 **/

public class MerchantActivity extends Activity implements OnApiCallListener {

    String checksum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchantapp);
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /*try {
            Cipher cip = Cipher.getInstance("DES","SunJCE");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }*/

        CheckSumServiceHelper checksumHelper = CheckSumServiceHelper.getCheckSumServiceHelper();
// key : Merchant Key; paramMap : TreeMap of request parameters
        TreeMap<String,String> parameters = new TreeMap<String,String>();
        String merchantKey = "bQfzzkKzeCbR7jOl"; //Key provided by Paytm
        parameters.put("MID", "amitgo59443067266036"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("ORDER_ID", "ORDER0000001"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("CUST_ID", "CUST00001"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("INDUSTRY_TYPE_ID", "Retail"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("CHANNEL_ID", "WAP"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("TXN_AMOUNT", "1.00"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("WEBSITE", "worldpressplg"); // Merchant ID (MID) sent by Paytm pg
        parameters.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"); // Merchant ID (MID) sent by Paytm pg

        ApiFunctions apiFunctions = new ApiFunctions(this, this);
        apiFunctions.checkSum(merchantKey, parameters);

        /*try {
            String checksum = checksumHelper.genrateCheckSumGAE(merchantKey, parameters);
            Log.e("checksum : " , checksum);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        }*/


    }

    //This is to refresh the order id: Only for the Sample App's purpose.
    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


	/*private void initOrderId() {
		Random r = new Random(System.currentTimeMillis());
		String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
				+ r.nextInt(10000);
		EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
		orderIdEditText.setText(orderId);
	}*/

    public void onStartTransaction(View view) {
        PaytmPGService Service = PaytmPGService.getStagingService();


        //Kindly create complete Map and checksum on your server side and then put it here in paramMap.

        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("MID", "amitgo59443067266036");
        paramMap.put("ORDER_ID", "ORDER0000001");
        paramMap.put("CUST_ID", "CUST00001");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", "1.00");
        paramMap.put("WEBSITE", "www.thetatechnolabs.com");
        paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
        paramMap.put("CHECKSUMHASH", checksum);
        PaytmOrder Order = new PaytmOrder(paramMap);


        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                        Log.d("LOG", "someUIErrorOccurred");
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction : " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
                        // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.

                        Log.d("LOG", "clientAuthenticationFailed");
                        Log.d("LOG", inErrorMessage);
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                        Log.d("LOG", "onErrorLoadingWebPage");
                        Log.d("LOG", String.valueOf(iniErrorCode));
						Log.d("LOG", inErrorMessage);
						Log.d("LOG", inFailingUrl);
                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }

    @Override
    public void onSuccess(int responseCode, String responseString, String requestType) {

        Log.e("msg", String.valueOf(responseCode));
        try {
            JSONObject jsonObjectResponse = new JSONObject(responseString);
             checksum = jsonObjectResponse.getString("CHECKSUMHASH");
             Log.e("checksum: ", checksum);
        } catch (JSONException e) {

        }
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.e("msg", String.valueOf(errorMessage));
    }
}
