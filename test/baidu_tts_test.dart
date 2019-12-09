import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:baidu_tts/baidu_tts.dart';

void main() {
  const MethodChannel channel = MethodChannel('baidu_tts');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await BaiduTts.platformVersion, '42');
  });
}
