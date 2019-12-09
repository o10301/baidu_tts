import 'dart:async';

import 'package:flutter/services.dart';

class BaiduTts {
  static const MethodChannel methodChannel = const MethodChannel('baidu_tts');

  static Future<bool> init({String appId, String appKey, String secretKey, String sn}) async {
    Map<String, dynamic> params = {
      'appId': appId,
      'appKey': appKey,
      'secretKey': secretKey,
      'sn': sn,
    };
    return await methodChannel.invokeMethod('init', params);
  }

  static Future speak(String text) async {
    Map<String, dynamic> params = {
      'text': text,
    };
    return await methodChannel.invokeMethod('speak', params);
  }

}
