import 'package:altapay_link/PaxFunctions.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class PaxApi extends StatefulWidget {
  @override
  _PaxApiState createState() => _PaxApiState();

}

class _PaxApiState extends State<PaxApi>{
  static const platform = const MethodChannel('serviceresult.data');
  String dataShared = "No data";

  @override
  void initState() {
    super.initState();
    getSharedText();
  }

  getSharedText() async {
    var sharedData = await platform.invokeMethod("paymentresultX");
    if (sharedData != null) {
      setState(() {
        dataShared = sharedData;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
     return new Scaffold(
       body: new SingleChildScrollView(
         child: new Padding(
           padding: const EdgeInsets.all(20.0),
           child: new Column(
             children: <Widget>[
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new TextFormField(decoration: new InputDecoration(hintText: "Amount in cents"),),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new TextFormField(decoration: new InputDecoration(hintText: "Transaction ID"),),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child: new SizedBox(child: new FlatButton(onPressed: (){new PaxFunctions().testExplicitIntentFallback();}, child: new Text("PAYMENT")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){new PaxFunctions().makePayment().whenComplete(getSharedText);}, child: new Text("REFUND")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){}, child: new Text("REVERSAL")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){}, child: new Text("PRE-AUTH")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){}, child: new Text("PRE-AUTH COMPLETION")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){}, child: new Text("CLOSE SESSION")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){}, child: new Text("ONLINE TOTALS")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new FlatButton(onPressed: (){}, child: new Text("TID SELECTION")))),
               ),
               new Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: new Container(width: 300.0,decoration: new BoxDecoration(borderRadius: new BorderRadius.all(new Radius.circular(10.0)),color: Colors.grey) ,child:new SizedBox( child: new Text(dataShared))),
               ),

             ],
           ),
         ),
       ),
     );
  }

}