package com.kollus.kr.kollus_sample_java.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayCallbackResponse {
	private int expirationDate = 0;
	private List<PlaySection> playSectionList = null;
	private int expirationPlayTime = 0;
	private boolean result = false;
	private String message = null;
	private boolean disableTvOut = true;
	private boolean vmCheck = true;
	private boolean contentExpired = false;

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

	public HashMap<String, Object> toMapFromObject() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", isResult() ? 1 : 0);
		if (getExpirationDate() > 0) {
			map.put("expiration_date", getExpirationDate());
		}
		if (getExpirationPlayTime() >= 0) {
			map.put("expiration_playtime", getExpirationPlayTime());
		}
		if (getMessage() != null) {
			map.put("message", getMessage());
		}

		if (getPlaySectionLists() != null) {
			map.put("play_section", getPlaySectionLists());
		}

		if (isContentExpired() == true) {
			map.put("content_expired", isContentExpired());
		}
		if (isDisableTvOut() == false) {
			map.put("disable_tvout", isDisableTvOut());
		}
		if (isVmCheck() == false) {
			map.put("vmcheck", isVmCheck());
		}
		return map.size() > 0 ? map : null;
	}

	public String toBody() throws JsonProcessingException {
		HashMap<String, Object> map = this.toMapFromObject();
		String result = null;
		if (map != null) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("data", map);
			result = (new ObjectMapper()).writeValueAsString(resultMap);
		}
		return result;
	}

	public static PlayCallbackResponse getPlayCallbackResponseFromPolicy(int kind, PlayPolicy policy) {
		PlayCallbackResponse playCallbackResponse = new PlayCallbackResponse();
		playCallbackResponse.setResult(policy.isResult());
		playCallbackResponse.setMessage(policy.getMessage());
		if (kind == 1) {
			playCallbackResponse.setDisableTvOut(policy.isDisableTvOut());
			playCallbackResponse.setExpirationDate(policy.getExpirationDateFromNow());
			playCallbackResponse.setExpirationPlayTime(policy.getExpirationPlayTime());
			playCallbackResponse.setPlaySectionList(policy.getPlaySectionLists());
			playCallbackResponse.setVmCheck(policy.isVmCheck());
		} else if (kind == 3) {
			playCallbackResponse.setContentExpired(policy.isContentExpired());
		}

		return playCallbackResponse;
	}

	public static PlayCallbackResponse getPlayCallbackResponseFromMap(int kind, HashMap<String, Object> map) {
		PlayPolicy policy = PlayPolicy.getPlayPolicyFromMap(map);
		return getPlayCallbackResponseFromPolicy(kind, policy);
	}

}
