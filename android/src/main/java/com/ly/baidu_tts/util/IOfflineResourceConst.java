package com.ly.baidu_tts.util;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

public interface IOfflineResourceConst {
    String VOICE_FEMALE = "F";

    String VOICE_MALE = "M";

    String VOICE_DUYY = "Y";

    String VOICE_DUXY = "X";

    String TEXT_MODEL = "bd_etts_text.dat";

    String VOICE_MALE_MODEL = "bd_etts_common_speech_m15_mand_eng_high_am-mgc_v3.6.0_20190117.dat";

    String VOICE_FEMALE_MODEL = "bd_etts_common_speech_f7_mand_eng_high_am-mgc_v3.6.0_20190117.dat";

    String VOICE_DUXY_MODEL = "bd_etts_common_speech_yyjw_mand_eng_high_am-mgc_v3.6.0_20190117.dat";

    String VOICE_DUYY_MODEL = "bd_etts_common_speech_as_mand_eng_high_am-mgc_v3.6.0_20190117.dat";

    TtsMode DEFAULT_OFFLINE_TTS_MODE = TtsMode.OFFLINE;

    String PARAM_SN_NAME = SpeechSynthesizer.PARAM_AUTH_SN;
}
