package com.ly.baidu_tts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.ly.baidu_tts.control.InitConfig;
import com.ly.baidu_tts.listener.UiMessageListener;
import com.ly.baidu_tts.util.Auth;
import com.ly.baidu_tts.util.AutoCheck;
import com.ly.baidu_tts.util.IOfflineResourceConst;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;


public class  BaiduTtsPlugin implements MethodCallHandler, PluginRegistry.RequestPermissionsResultListener  {
    private Registrar registrar;
    private String appId;
    private String appKey;
    private String secretKey;
    private String sn;
    private TtsMode ttsMode = IOfflineResourceConst.DEFAULT_OFFLINE_TTS_MODE;
    private SpeechSynthesizer speechSynthesizer;
    private static final String TEMP_DIR = "/sdcard/baiduTTS";
    private static final String TEXT_FILENAME = TEMP_DIR + "/" + IOfflineResourceConst.TEXT_MODEL;
    private static final String MODEL_FILENAME = TEMP_DIR + "/" + IOfflineResourceConst.VOICE_FEMALE_MODEL;

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "baidu_tts");
        BaiduTtsPlugin plugin = new BaiduTtsPlugin(registrar);
        channel.setMethodCallHandler(plugin);
    }

    private BaiduTtsPlugin(Registrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("init")) {
            appId = call.argument("appId");
            appKey = call.argument("appKey");
            secretKey = call.argument("secretKey");
            sn = call.argument("sn");
            initPermission();
        } else if (call.method.equals("speak")) {
            String text = call.argument("text");
            speechSynthesizer.speak(text);
        } else {
            result.notImplemented();
        }
    }

    private void initTTs() {
        // 1. 获取实例
        speechSynthesizer = SpeechSynthesizer.getInstance();
        speechSynthesizer.setContext(registrar.activeContext());
        // 3. 设置appId，appKey.secretKey
        speechSynthesizer.setAppId(appId);
        speechSynthesizer.setApiKey(appKey, secretKey);
        // 4. 支持离线的话，需要设置离线模型
        // 文本模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
        // 声学模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        // 5. 以下setParam 参数选填。不填写则默认值生效
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-15 ，默认 5
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "15");
        // 设置合成的语速，0-15 ，默认 5
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "4");
        // 设置合成的语调，0-15 ，默认 5
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);

        if (sn != null) {
            // 纯离线sdk这个参数必填；离在线sdk没有此参数
            speechSynthesizer.setParam(IOfflineResourceConst.PARAM_SN_NAME, sn);
        }
        // x. 额外 ： 自动so文件是否复制正确及上面设置的参数
        Map<String, String> params = new HashMap<>();
        // 复制下上面的 speechSynthesizer.setParam参数
        // 上线时请删除AutoCheck的调用
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        // 6. 初始化
        speechSynthesizer.initTts(ttsMode);
    }

    private void initPermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(registrar.activeContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initTTs();
        } else {
            ActivityCompat.requestPermissions(registrar.activity(), permissions, 0);
        }
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] strings, int[] ints) {
        if (requestCode == 0 && ints.length > 0) {
            initTTs();
        }
        return true;
    }
}
