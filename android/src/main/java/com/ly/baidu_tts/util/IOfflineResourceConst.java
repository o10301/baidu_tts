package com.ly.baidu_tts.util;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

public interface IOfflineResourceConst {
    String TEXT_MODEL = "bd_etts_text.dat";
    String VOICE_FEMALE_MODEL = "bd_etts_common_speech_f7_mand_eng_high_am-mgc_v3.6.0_20190117.dat";
    String PARAM_SN_NAME = SpeechSynthesizer.PARAM_AUTH_SN;
}
