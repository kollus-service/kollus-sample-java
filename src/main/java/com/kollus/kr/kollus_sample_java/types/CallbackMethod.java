package com.kollus.kr.kollus_sample_java.types;
/**
 * 콜백 유형 정의 Enum
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public enum CallbackMethod {
	LMS("lms"), MOBILE_DRM("mdrm"), PC_DRM("pdrm"), PLAY("play"),
	UPLOAD("upload"), TRANSCODING("transcoding"), UPDATE("update"), CHANNEL_ADD("add"), CHANNEL_DELETE("delete");
	// UPLOAD("add"),
	// TRANSCODE("add"),
	// DELETE("delete");

	private String method;

	CallbackMethod(String _method) {
		this.method = _method;
	}

	public String getMethod() {
		return this.method;
	}

	public static CallbackMethod getMethod(String value) {
		for (CallbackMethod m : values()) {
			if (m.getMethod().equals(value))
				return m;
		}
		return null;
	}
}
