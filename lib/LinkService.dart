import 'dart:io';

import 'package:flutter/material.dart';

class LinkService extends StatefulWidget {
  @override
  _LinkServiceState createState() => _LinkServiceState();

}

class _LinkServiceState extends State<LinkService>{
  ServerSocket _server;
  String _buttonText = "Start";

  void bindServer() async{
    _server = await ServerSocket.bind("0.0.0.0", 40001).whenComplete((){
      print("server bound at  40001");
    }).catchError((e)=>print(e));
    _server.forEach((server) {
      print("Incoming socket $server");
      server.listen((List<int> data){
        String result = new String.fromCharCodes(data);
        print(result.substring(0, result.length - 1));
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return new Center(
      child: new Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          new FlatButton(
              color: Colors.grey,
              onPressed: bindServer,
              child: new Text(
                _buttonText,
                style: new TextStyle(fontSize: 20.0),)
          ),
          new Padding(padding: new EdgeInsetsDirectional.only(top: 10.0)),
          new Text(
            "Press Button to Start Service",
            style: new TextStyle(fontSize: 20.0),
          ),
        ],
      ),
    );
  }

}


