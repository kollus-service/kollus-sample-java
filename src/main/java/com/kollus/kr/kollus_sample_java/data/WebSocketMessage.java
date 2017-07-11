package com.kollus.kr.kollus_sample_java.data;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketMessage {
	private int kind;
	private String type;
	private long time;
	private String request;
	private String response;
	private String responseJson;
	public int getKind() {
		return kind;
	}
	public String getType() {
		return type;
	}
	public long getTime() {
		return time;
	}
	public String getRequest() {
		return request;
	}
	public String getResponse() {
		return response;
	}
	public String getResponseJson() {
		return responseJson;
	}
	public WebSocketMessage(int kind, String type, String request, String response, String responseJson) {
		super();
		this.kind = kind;
		this.type = type;
		this.time = new Date().getTime();
		this.request = request;
		this.response = response;
		this.responseJson = responseJson;
	}
	public String toMessage() throws JsonProcessingException{
		HashMap<String, Object> map = new HashMap<>();
		map.put("kind", kind);
		map.put("type", type);
		map.put("time", time);
		map.put("request", request);
		map.put("response", response);
		map.put("responseJson", responseJson);
		return (new ObjectMapper()).writeValueAsString(map);
	}
	
}
