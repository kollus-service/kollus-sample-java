package com.kollus.kr.kollus_sample_java.data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


import com.jayway.jsonpath.JsonPath;

public class PlayCallbackRequest {
	private int kind;
	private String clientUserId;
	private String playerId;
	private String deviceName;
	private String hardwareId;
	private String mediaContentKey;
	private HashMap<String, String> uservalues;

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

	public String getUservalue(String key) {
		return uservalues.get(key);
	}

	public String getUservalue(int key) {
		return uservalues.get("uservalue" + key);
	}

	public PlayCallbackRequest() {

	}

	public PlayCallbackRequest(int kind, String client_user_id, String player_id, String device_name,
			String hardware_id, String media_content_key, HashMap<String, String> uservalues) {
		super();
		this.kind = kind;
		this.clientUserId = client_user_id;
		this.playerId = player_id;
		this.deviceName = device_name;
		this.hardwareId = hardware_id;
		this.mediaContentKey = media_content_key;
		this.uservalues = uservalues;
	}

	public static PlayCallbackRequest getPlayCallbackRequestFromMap(HashMap<String, Object> map) throws UnsupportedEncodingException {
		int kind = map.containsKey("kind") ? Integer.parseInt(map.get("kind").toString()) : 0;
		String client_user_id = map.containsKey("client_user_id") ? map.get("client_user_id").toString() : "";
		String player_id = map.containsKey("player_id") ? map.get("player_id").toString() : "";
		String device_name = map.containsKey("device_name") ? map.get("device_name").toString() : "";
		String hardware_id = map.containsKey("hardware_id") ? map.get("hardware_id").toString() : "";
		String media_content_key = map.containsKey("media_content_key") ? map.get("media_content_key").toString() : "";
		HashMap<String, String> uservalues = map.containsKey("uservalues") ? (HashMap<String, String>) JsonPath.read(URLDecoder.decode(map.get("uservalues").toString(), "UTF-8"),
				"$") : null;
		return new PlayCallbackRequest(kind, client_user_id, player_id, device_name, hardware_id, media_content_key, uservalues);
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
