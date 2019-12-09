import 'dart:async';

import 'package:flutter/services.dart';

class BaiduTts {
  static const MethodChannel methodChannel = const MethodChannel('baidu_tts');

  static Future<bool> init(String appId) async {
    return await methodChannel.invokeMethod('init');
  }

  static Future speak(String text) async {
    Map<String, dynamic> params = {
      'text': text,
    };
    return await methodChannel.invokeMethod('speak', params);
  }

}
