package com.kollus.kr.kollus_sample_java.data;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DrmCallbackResponse {

	private int kind;
	private long expirationDate;
	private int expirationCount;
	private int expirationPlayTime;
	private boolean result;
	private String message;
	private boolean expirationRefreshPopup;
	private String mediaContentKey;
	private boolean vmCheck;
	private boolean checkAbuse;

	private boolean contentDelete;

	private String sessionKey;
	private boolean contentExpired;
	private boolean contentExpireReset;
	private int startAt;

	public int getKind() {
		return kind;
	}

	public long getExpirationDate() {
		return expirationDate;
	}

	public int getExpirationCount() {
		return expirationCount;
	}

	public int getExpirationPlayTime() {
		return expirationPlayTime;
	}

	public boolean isResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public boolean isExpirationRefreshPopup() {
		return expirationRefreshPopup;
	}

	public String getMediaContentKey() {
		return mediaContentKey;
	}

	public boolean isVmCheck() {
		return vmCheck;
	}

	public boolean isCheckAbuse() {
		return checkAbuse;
	}

	public boolean isContentDelete() {
		return contentDelete;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public boolean isContentExpired() {
		return contentExpired;
	}

	public boolean isContentExpireReset() {
		return contentExpireReset;
	}

	public long getStartAt() {
		return startAt;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setExpirationCount(int expirationCount) {
		this.expirationCount = expirationCount;
	}

	public void setExpirationPlayTime(int expirationPlayTime) {
		this.expirationPlayTime = expirationPlayTime;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setExpirationRefreshPopup(boolean expirationRefreshPopup) {
		this.expirationRefreshPopup = expirationRefreshPopup;
	}

	public void setMediaContentKey(String mediaContentKey) {
		this.mediaContentKey = mediaContentKey;
	}

	public void setVmCheck(boolean vmCheck) {
		this.vmCheck = vmCheck;
	}

	public void setCheckAbuse(boolean checkAbuse) {
		this.checkAbuse = checkAbuse;
	}

	public void setContentDelete(boolean contentDelete) {
		this.contentDelete = contentDelete;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public void setContentExpired(boolean contentExpired) {
		this.contentExpired = contentExpired;
	}

	public void setContentExpireReset(boolean contentExpireReset) {
		this.contentExpireReset = contentExpireReset;
	}

	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	public String toBody() throws JsonProcessingException {
		HashMap<String, Object> map = this.toMapFromObject();
		String result = null;
		if (map != null) {
			result = (new ObjectMapper()).writeValueAsString(map);
		}
		return result;
	}

	public HashMap<String, Object> toMapFromObject() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("kind", kind);
		map.put("result", isResult() ? 1 : 0);
		map.put("media_content_key", getMediaContentKey());

		if (kind == 1) {
			if (getExpirationDate() > 0) {
				map.put("expiration_date", getExpirationDate());
			}
			if (getExpirationCount() > 0) {
				map.put("expiration_count", getExpirationCount());
			}
			if (getExpirationPlayTime() >= 0) {
				map.put("expiration_playtime", getExpirationPlayTime());
			}
			if (getMessage() != null) {
				map.put("message", getMessage());
			}
			map.put("expiration_refresh_popup", isExpirationRefreshPopup() ? 1 : 0);
			map.put("vmcheck", isVmCheck() ? 1 : 0);
			map.put("check_abuse", isCheckAbuse() ? 1 : 0);
		} else if (kind == 2) {
			map.put("content_delete", isContentDelete()?1:0);
			if (getMessage() != null) {
				map.put("message", getMessage());
			}
		} else if (kind == 3) {
			if(getSessionKey() != null){
			map.put("session_key", getSessionKey());
			}
			map.put("start_at", getStartAt());
			map.put("content_expired", isContentExpired()? 1: 0);
			map.put("content_delete", isContentDelete()? 1: 0);
			map.put("content_expired_reset", isContentExpireReset()? 1: 0);
			if (getExpirationDate() > 0) {
				map.put("expiration_date", getExpirationDate());
			}
			if (getExpirationCount() > 0) {
				map.put("expiration_count", getExpirationCount());
			}
			if (getExpirationPlayTime() >= 0) {
				map.put("expiration_playtime", getExpirationPlayTime());
			}
			if (getMessage() != null) {
				map.put("message", getMessage());
			}
			map.put("check_abuse", isCheckAbuse() ? 1 : 0);
		}
		return map;
	}

	public static DrmCallbackResponse getDrmCallbackResponseFromMap(int kind, String mediaContentKey, String sessionKey,
			int startAt, HashMap<String, Object> map) {
		DrmPolicy policy = DrmPolicy.getDrmPolicy(map);
		return getDrmCallbackResponseFromPolicy(kind, mediaContentKey, sessionKey, startAt, policy);
	}

	public static DrmCallbackResponse getDrmCallbackResponseFromPolicy(int kind, String mediaContentKey,
			String sessionKey, int startAt, DrmPolicy policy) {
		DrmCallbackResponse drmCallbackResponse = new DrmCallbackResponse();
		drmCallbackResponse.setKind(kind);
		drmCallbackResponse.setResult(policy.isResult());
		drmCallbackResponse.setMediaContentKey(mediaContentKey);
		if (kind == 1) {
			drmCallbackResponse.setExpirationDate(policy.getExpirationDateFromNow());
			drmCallbackResponse.setExpirationCount(policy.getExpirationCount());
			drmCallbackResponse.setExpirationPlayTime(policy.getExpirationPlayTime());
			drmCallbackResponse.setMessage(policy.getMessage());
			drmCallbackResponse.setExpirationRefreshPopup(policy.isExpirationRefreshPopup());
			drmCallbackResponse.setVmCheck(policy.isVmCheck());
			drmCallbackResponse.setCheckAbuse(policy.isCheckAbuse());

		} else if (kind == 2) {
			drmCallbackResponse.setContentDelete(policy.isContentDelete());
			drmCallbackResponse.setMessage(policy.getMessage());
		} else if (kind == 3) {
			drmCallbackResponse.setSessionKey(sessionKey);
			drmCallbackResponse.setStartAt(startAt);
			drmCallbackResponse.setContentExpired(policy.isContentExpired());
			drmCallbackResponse.setContentDelete(policy.isContentDelete());
			drmCallbackResponse.setContentExpireReset(policy.isContentExpireReset());
			drmCallbackResponse.setExpirationDate(policy.getExpirationDateFromNow());
			drmCallbackResponse.setExpirationCount(policy.getExpirationCount());
			drmCallbackResponse.setExpirationPlayTime(policy.getExpirationPlayTime());
			drmCallbackResponse.setMessage(policy.getMessage());
			drmCallbackResponse.setCheckAbuse(policy.isCheckAbuse());
		}
		return drmCallbackResponse;
	}

}
