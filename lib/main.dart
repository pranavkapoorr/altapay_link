import 'package:altapay_link/LinkService.dart';
import 'package:altapay_link/PaxApi.dart';
import 'package:flutter/material.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'AltaPay-Link Pax',
      debugShowCheckedModeBanner: false,
      debugShowMaterialGrid: false,
      theme: new ThemeData(
        primaryColor: new Color(0xffF4F4F4),
    accentColor: new Color(0xffA1A9A9)
      ),
      home: new MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {


  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> with TickerProviderStateMixin{
  TabController _tabController;

  @override
  void initState(){
    super.initState();
    _tabController = new TabController(length: 2, vsync: this);
  }



  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("ALTAPAY-LINK",style: new TextStyle(color: Theme.of(context).accentColor),),
        bottom: new TabBar(
            controller: _tabController,
            tabs:[
              new Tab(icon:new Icon(Icons.phonelink)),
              new Tab(icon:new Icon(Icons.phonelink_setup))
            ]
        ),
      ),
      body: new TabBarView(
          controller: _tabController,
          children: [
            new LinkService(),
            new PaxApi()
          ]
      )
    );
  }
}
