package com.kollus.kr.kollus_sample_java.types;

/**
 * Kollus 콜백 및 Token 생성 유형 Enum
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public enum KollusCallbackEnc {
	JWT("JWT"),
	KOLLUS("KOLLUS");
	private String enc;
	KollusCallbackEnc(String _enc){
		this.enc = _enc;
	}
	public String getEnc(){
		return this.enc;
	}
}
