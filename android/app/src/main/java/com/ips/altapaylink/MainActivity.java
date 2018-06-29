package com.ips.altapaylink;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.ActivityLifecycleListener;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;


public class MainActivity extends FlutterActivity {
  private String sharedText;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

          new MethodChannel(getFlutterView(), "serviceresult.data")
                  .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
                      @Override
                      public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                          if (methodCall.method.matches("paymentresultX")) {
                              System.out.println("in hereeeeeee");
                              try {
                                  TimeUnit.SECONDS.sleep(20);
                              } catch (InterruptedException e) {
                                  e.printStackTrace();
                              }
                              processIntent();
                              result.success(sharedText);
                              sharedText = null;
                          }
                      }
                  });

  }
  void processIntent(){
      if (getIntent() != null && getIntent().getData()!=null && (getIntent().getData().getHost().equals("serviceresult")||getIntent().getData().getHost().equals("paymentresult"))){
          System.out.println(getIntent());
          Uri data = getIntent().getData();
              handleSendText(getIntent());
      }
  }

  void handleSendText(Intent intent) {
    sharedText = intent.getData().getQueryParameter("message");
  }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.print("ending......");
    }
}
