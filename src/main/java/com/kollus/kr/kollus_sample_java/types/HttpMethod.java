package com.kollus.kr.kollus_sample_java.types;
/**
 * HTTP method 유형 Enum
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public enum HttpMethod {

	GET("GET"),
	POST("POST"),
	UPDATE("UPDATE"),
	HEAD("HEAD"),
	DELETE("DELETE"),
	PATCH("PATCH");
	
	private String method;
	HttpMethod(String _method){
		method = _method;
	}
	public String getMethod(){
		return method;
	}
}
