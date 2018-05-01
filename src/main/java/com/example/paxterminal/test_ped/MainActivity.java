package com.example.paxterminal.test_ped;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {
private Button getConfigButton;
private Button paymentButton;
private Button refundButton;
private TextView logField;
private EditText amountField;
private EditText transactionReferenceField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getConfigButton = findViewById(R.id.getConfigButton);
        getConfigButton.setOnClickListener(configButtonListener());
        paymentButton = findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(paymentButtonListener());
        refundButton = findViewById(R.id.refundButton);
        refundButton.setOnClickListener(refundButtonListener());
        logField = findViewById(R.id.logField);
        amountField = findViewById(R.id.amountField);
        transactionReferenceField = findViewById(R.id.transactionReference);
    }
    private View.OnClickListener configButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getConfiguration();
            }
        };
    }
    private View.OnClickListener paymentButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction("PAYMENT");
            }
        };
    }
    private View.OnClickListener refundButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction("REFUND");
            }
        };
    }
    private void getConfiguration(){
        Intent mRequestFileIntent = new Intent(Intent.ACTION_PICK);
        mRequestFileIntent.setType("application/json");
        mRequestFileIntent.setPackage("com.paxitalia.cb2.xxx");
        if (isIntentAvailable(getApplicationContext(),mRequestFileIntent)){
            startActivityForResult(mRequestFileIntent, 0);
        }else{
            logField.setText("Intent Not Available Dude.. !");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        do{
            if(resultCode != RESULT_OK){
                break;
            }
            Uri returnUri = data.getData();
            if(returnUri == null){
                break;
            }

            ParcelFileDescriptor pfd = null;
            try{
                pfd = getContentResolver().openFileDescriptor(returnUri,"r");
            }catch(FileNotFoundException e){
                logField.setText("app2app -> File not found." + e.getMessage());
                break;
            }
            FileDescriptor fd = pfd.getFileDescriptor();
            if(fd == null){
                try{
                    pfd.close();
                }catch(IOException e){
                    logField.setText("A2A "+e.getMessage());
                }
                break;
            }
            try{
                FileInputStream fileInputStream = new FileInputStream(fd);
                long size = fileInputStream.available();
                if(size<0){
                    break;
                }
                byte[] datax = new byte[(int)size];
                fileInputStream.read(datax);

                String jsonData = new String(datax);
                JSONObject jObject = new JSONObject(jsonData);
                if(jObject == null){
                    break;
                }
                String model = jObject.getString("model");
                logField.setText("a2a-> terminal model " + model);
                String serialno = jObject.getString("serialNo");
                logField.append("\n a2a-> serail Number" + serialno);
                String packageVersion = jObject.getString("packageVersion");
                logField.append("\n a2a-> Package version" + packageVersion);
                String cb2Version = jObject.getString("cb2Version");
                logField.append("\n a2a-> cb2 version " + cb2Version);

                JSONArray tidList = jObject.getJSONArray("terminalIdList");
                if(tidList == null){
                    break;
                }
                if((tidList != null) && (tidList.length() > 0 )){
                    for(int i = 0; i<tidList.length();i++){
                        JSONObject tidInfo = tidList.getJSONObject(i);
                        if(tidInfo != null){
                            String tid = tidInfo.getString("terminalID");
                            String status = tidInfo.getString("status");
                            boolean onlineOpDone = tidInfo.getBoolean("onlineOperationAfterFirstDll");
                            if(tid != null){
                                logField.append("a2a-> Tid "+tid + " , status: "+ status + ", onlineOperationAfterFirstDll: "+onlineOpDone);
                            }
                        }
                    }
                }

            }catch(IOException e){
                logField.setText("app2app -> " + e.getMessage());
            }catch(JSONException e){
                logField.setText("app2app -> " + e.getMessage());
            }
        } while(false);
    }

    private void Transaction(String transactionType){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("demopax").authority("payment");
        String uriResponse = builder.build().toString();
        builder = new Uri.Builder();
        builder.scheme("pax")
                .authority("payment")
                .appendQueryParameter("callerPackage", BuildConfig.APPLICATION_ID)
                .appendQueryParameter("paymentType", transactionType)
                .appendQueryParameter("amount", amountField.getText().toString())
                .appendQueryParameter("callerTrxId", transactionReferenceField.getText().toString())
                .appendQueryParameter("confirmOperation","true")
                .appendQueryParameter("uri", uriResponse);
        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(getApplicationContext(),intent)){
                startActivity(intent);
                if (getIntent() != null && getIntent().getData() != null && getIntent().getData().getHost() != null /**&& getIntent().getData().getHost().equals("PAYMENT_HOST")**/) {
                    Uri data = getIntent().getData();
                    String callTrx = data.getQueryParameter("callerTrxId");
                    String result = data.getQueryParameter("result");
                    String message = data.getQueryParameter("message");
                    String optMessage = data.getQueryParameter("optMessage");
                    if (!TextUtils.isEmpty(result)) {
                        logField.setText("Result " + result + " , message: " + message + " , opt.message: " + optMessage + " , callerTrxId = " + callTrx);
                    }
                }
        }else{
            logField.setText("Intent Not Available Dude.. !");
        }
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
         PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
