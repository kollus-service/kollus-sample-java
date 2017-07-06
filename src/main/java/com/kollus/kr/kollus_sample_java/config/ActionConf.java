package com.kollus.kr.kollus_sample_java.config;

import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
/**
 * 암호화 방식 및 만료시간등 상수 클래스
 * @author Yang Hyeon Deok (hdyang@catenoid.net)
 * @since 2017. 7. 6.
 */
public class ActionConf {
	public static final KollusCallbackEnc CALLBACK_ENC = KollusCallbackEnc.JWT ;
	public static final int EXP_SECONDS = 3600;
}
