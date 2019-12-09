import 'package:flutter/material.dart';
import 'package:baidu_tts/baidu_tts.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController controller = TextEditingController()..text = 'A1001王五请到外科第一诊室就诊';
  bool speaking = false;

  @override
  void initState() {
    super.initState();
    BaiduTts.init('5dd0055d');
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