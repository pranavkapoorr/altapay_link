import 'dart:async';

import 'package:android_intent/android_intent.dart';

class PaxFunctions{

  Future<Null> makeRefund(){
    final AndroidIntent intent = new AndroidIntent(
        action: 'action_view',
        data: Uri.encodeFull('pax://payment?callerPackage=com.ips.altapaylink&paymentType=REFUND&amount=50&callerTrxId=hellotrans&confirmOperation=false&uri=demopax%3A%2F%2Fpaymentresult  flg=0x10000000'),
    );//package: 'RpcClient open rpc');
    return intent.launch().catchError((e)=>print(e));
  }
  void testExplicitIntentFallback() {
    final AndroidIntent intent = new AndroidIntent(
        action: 'action_view',
        data: Uri.encodeFull('https://flutter.io'),
        package: 'com.android.chrome.implicit.fallback');
    intent.launch();
  }
  Future<Null> makePayment(){
    final AndroidIntent intent = new AndroidIntent(
      action: 'action_view',
      data: Uri.encodeFull('pax://payment?callerPackage=pax.ips.com.pax_mpos&paymentType=PAYMENT&amount=50&callerTrxId=testx&confirmOperation=false&uri=demopax%3A%2F%2Fpaymentresult flg=0x10000000'),
    );//package: 'RpcClient open rpc');
    return intent.launch().catchError((e)=>print(e));
  }


  }