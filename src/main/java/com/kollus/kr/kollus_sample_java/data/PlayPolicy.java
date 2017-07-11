package com.kollus.kr.kollus_sample_java.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlayPolicy {
	private int expirationDate;
	private List<PlaySection> playSectionList;
	private int expirationPlayTime;
	private boolean result;
	private String message;
	private boolean disableTvOut;
	private boolean vmCheck;
	private boolean contentExpired;

	public int getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(int expiration_date) {
		this.expirationDate = expiration_date;
	}

	public List<PlaySection> getPlaySectionLists() {
		return playSectionList;
	}

	public void setPlaySectionList(List<PlaySection> play_section) {
		this.playSectionList = play_section;
	}

	public void addPlaySection(PlaySection play_section) {
		if (this.playSectionList == null) {
			this.playSectionList = new ArrayList<PlaySection>();
		}
		this.playSectionList.add(play_section);
	}

	public int getExpirationPlayTime() {
		return expirationPlayTime;
	}

	public void setExpirationPlayTime(int expiration_playtime) {
		this.expirationPlayTime = expiration_playtime;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isDisableTvOut() {
		return disableTvOut;
	}

	public void setDisableTvOut(boolean disable_tvout) {
		this.disableTvOut = disable_tvout;
	}

	public boolean isVmCheck() {
		return vmCheck;
	}

	public void setVmCheck(boolean vmcheck) {
		this.vmCheck = vmcheck;
	}

	public boolean isContentExpired() {
		return this.contentExpired;
	}

	public void setContentExpired(boolean content_expired) {
		this.contentExpired = content_expired;
	}

	public static PlayPolicy getPlayPolicyFromMap(HashMap<String, Object> map) {
		PlayPolicy policy = new PlayPolicy();
		if (map.containsKey("expiration_date")) {
			policy.setExpirationDate(Integer.parseInt(map.get("expiration_date").toString()));
		}
		if (map.containsKey("expiration_playtime")) {
			policy.setExpirationPlayTime(Integer.parseInt(map.get("expiration_playtime").toString()));
		}
		if (map.containsKey("result")) {
			policy.setResult("1".equals(map.get("result").toString()));
		}
		if (map.containsKey("message")) {
			policy.setMessage(map.get("message").toString());
		}
		if (map.containsKey("disable_tvout")) {
			policy.setDisableTvOut("1".equals(map.get("disable_tvout").toString()));
		}
		if (map.containsKey("vmcheck")) {
			policy.setVmCheck("1".equals(map.get("vmcheck").toString()));
		}
		if (map.containsKey("content_expired")) {
			policy.setContentExpired("1".equals(map.get("content_expired").toString()));
		}
		if (map.containsKey("start_time") && map.containsKey("end_time")) {
			String start_time = map.get("start_time").toString();
			String end_time = map.get("end_time").toString();;
			policy.setPlaySectionList(policy.getPlaySectionListFromJson(start_time, end_time));
		}
		return policy;
	}

	private List<PlaySection> getPlaySectionListFromJson(String start_time, String end_time) {
		List<PlaySection> playSectionList = null;
		String[] sTimes = null, eTimes = null;
		if (start_time != null && !start_time.trim().isEmpty()) {
			sTimes = start_time.split(",");
		}
		if (end_time != null && !end_time.trim().isEmpty()) {
			eTimes = end_time.split(",");
		}
		if (sTimes != null && eTimes != null) {
			playSectionList = new ArrayList<PlaySection>();
			if (sTimes.length >= 1 && eTimes.length >= 1 && sTimes.length == eTimes.length) {
				int count = sTimes.length;
				for (int idx = 0; count > idx; idx++) {
					int start = Integer.parseInt(sTimes[idx]);
					int end = Integer.parseInt(eTimes[idx]);
					playSectionList.add(new PlaySection(start, end));
				}
			}
		}
		return playSectionList;
	}
	public int getExpirationDateFromNow(){
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.SECOND, this.getExpirationDate());
		return (int)(c.getTime().getTime() / 1000);
	}

}
