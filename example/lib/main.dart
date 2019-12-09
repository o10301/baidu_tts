import 'package:flutter/material.dart';
import 'package:baidu_tts/baidu_tts.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController controller = TextEditingController()..text = 'A1001，王五，请到外科第一诊室就诊';
  bool speaking = false;

  @override
  void initState() {
    super.initState();
    BaiduTts.init(appId: '17974304', appKey: 'EjzcwQ8eDoLVX4WMk0OLBQMC', secretKey: '88Ohk7GG8EnF6GX4wOfFpKHaTllXhbBE', sn: '94a8e631-74d582a8-01a2-00e7-2116f');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              TextField(
                controller: controller,
              ),
              RaisedButton(
                child: Text('Speak'),
                onPressed: () => BaiduTts.speak(controller.text),
              ),
              Text(speaking ? '正在播放' : '停止播放'),
            ],
          ),
        ),
      ),
    );
  }
}