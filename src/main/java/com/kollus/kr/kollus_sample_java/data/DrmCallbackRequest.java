package com.kollus.kr.kollus_sample_java.data;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;


public class DrmCallbackRequest {

	// 공통 부분
	private int kind;
	private String clientUserId;
	private String playerId;
	private String deviceName;
	private String hardwareId;
	private String mediaContentKey;
	private HashMap<String, String> uservalues;

	private int startAt;
	private int contentExpired;
	private int resetReq;
	private String sessionKey;

	public int getKind() {
		return kind;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public String getMediaContentKey() {
		return mediaContentKey;
	}

	public HashMap<String, String> getUservalues() {
		return uservalues;
	}
	public String getUservalues(int key) {
		return uservalues.get("uservalue" + key);
	}

	public int getStartAt() {
		return startAt;
	}

	public int getContentExpired() {
		return contentExpired;
	}

	public int getResetReq() {
		return resetReq;
	}
	public String getSessionKey(){
		return sessionKey;
	}
	public DrmCallbackRequest(int kind, String clientUserId, String playerId, String deviceName, String hardwareId,
			String mediaContentKey, HashMap<String, String> uservalues, int startAt, int contentExpired,
			int resetReq, String sessionKey) {
		super();
		this.kind = kind;
		this.clientUserId = clientUserId;
		this.playerId = playerId;
		this.deviceName = deviceName;
		this.hardwareId = hardwareId;
		this.mediaContentKey = mediaContentKey;
		this.uservalues = uservalues;
		this.startAt = startAt;
		this.contentExpired = contentExpired;
		this.resetReq = resetReq;
		this.sessionKey = sessionKey;
	}

	public static DrmCallbackRequest getDrmCallbackRequestFromMap(HashMap<String, Object> map) throws UnsupportedEncodingException {

		int kind = map.containsKey("kind") ? Integer.parseInt(map.get("kind").toString()) : 0;
		String clientUserId = map.containsKey("client_user_id") ? map.get("client_user_id").toString() : null;
		String playerId = map.containsKey("player_id") ? map.get("player_id").toString() : null;
		String deviceName = map.containsKey("device_name") ? map.get("device_name").toString() : null;
		String hardwareId = map.containsKey("hardware_id") ? map.get("hardware_id").toString() : null;
		String mediaContentKey = map.containsKey("media_content_key") ? map.get("media_content_key").toString()
				: null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> uservalues = map.containsKey("uservalues")
				? (HashMap<String, String>) map.get("uservalues") : null;

		int startAt = map.containsKey("start_at") ? Integer.parseInt(map.get("start_at").toString()) : 0;;
		int contentExpired = map.containsKey("content_expired") ? Integer.parseInt(map.get("content_expired").toString()) : 0;
		int resetReq = map.containsKey("reset_req") ? Integer.parseInt(map.get("reset_req").toString()) : 0;
		String sessionKey = map.containsKey("session_key") ?  map.get("session_key").toString() : null;
		return new DrmCallbackRequest(kind, clientUserId, playerId, deviceName, hardwareId, mediaContentKey, uservalues,
				startAt, contentExpired, resetReq, sessionKey);
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("kind:");
		builder.append(kind);
		builder.append("\n");
		
		builder.append("client_user_id:");
		builder.append(clientUserId);
		builder.append("\n");
		
		builder.append("player_id:");
		builder.append(playerId);
		builder.append("\n");
		
		builder.append("device_name:");
		builder.append(deviceName);
		builder.append("\n");
		
		builder.append("hardware_id:");
		builder.append(hardwareId);
		builder.append("\n");
		
		builder.append("media_content_key:");
		builder.append(mediaContentKey);
		builder.append("\n");
		
		if(kind == 3){
			builder.append("startAt:");
			builder.append(startAt);
			builder.append("\n");
			builder.append("contentExpired:");
			builder.append(contentExpired);
			builder.append("\n");
			builder.append(resetReq);
			builder.append(resetReq);
			builder.append("\n");
			builder.append("sessionKey:");
			builder.append(sessionKey);
			builder.append("\n");
		}
		
		
		if(uservalues != null){
			Set<Entry<String, String>> entries = uservalues.entrySet();
			for(Entry<String, String> entry : entries){
				builder.append(String.format("%s:", entry.getKey()));
				builder.append(entry.getValue());
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	

}
