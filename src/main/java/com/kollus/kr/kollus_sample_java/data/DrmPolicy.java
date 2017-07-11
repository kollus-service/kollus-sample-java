package com.kollus.kr.kollus_sample_java.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DrmPolicy {

	private int expirationDate;
	private int expirationCount;
	private int expirationPlayTime;
	private boolean result;
	private String message;
	private boolean expirationRefreshPopup;
	private boolean vmCheck;
	private boolean checkAbuse;

	private boolean contentDelete;

	private boolean contentExpired;
	private boolean contentExpireReset;

	public int getExpirationDate() {
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

	public boolean isVmCheck() {
		return vmCheck;
	}

	public boolean isCheckAbuse() {
		return checkAbuse;
	}

	public boolean isContentDelete() {
		return contentDelete;
	}

	public boolean isContentExpired() {
		return contentExpired;
	}

	public boolean isContentExpireReset() {
		return contentExpireReset;
	}

	public void setExpirationDate(int expirationDate) {
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

	public void setVmCheck(boolean vmCheck) {
		this.vmCheck = vmCheck;
	}

	public void setCheckAbuse(boolean checkAbuse) {
		this.checkAbuse = checkAbuse;
	}

	public void setContentDelete(boolean contentDelete) {
		this.contentDelete = contentDelete;
	}

	public void setContentExpired(boolean contentExpired) {
		this.contentExpired = contentExpired;
	}

	public void setContentExpireReset(boolean contentExpireReset) {
		this.contentExpireReset = contentExpireReset;
	}

	public int getExpirationDateFromNow() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.SECOND, this.getExpirationDate());
		return (int) c.getTime().getTime() / 1000;
	}

	public static DrmPolicy getDrmPolicy(HashMap<String, Object> map) {
		DrmPolicy policy = new DrmPolicy();
		if (map.containsKey("check_abuse")) {
			policy.setCheckAbuse("1".equals(map.get("check_abuse")));
		}
		if (map.containsKey("content_expire_reset")) {
			policy.setContentExpireReset("1".equals(map.get("content_expire_reset")));
		}
		if (map.containsKey("expiration_count")) {
			policy.setExpirationCount(Integer.parseInt(map.get("expiration_count").toString()));
		}
		if (map.containsKey("expiration_refresh_popup")) {
			policy.setExpirationRefreshPopup("1".equals(map.get("content_expire_reset")));
		}
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
		if (map.containsKey("vmcheck")) {
			policy.setVmCheck("1".equals(map.get("vmcheck").toString()));
		}
		if (map.containsKey("content_expired")) {
			policy.setContentExpired("1".equals(map.get("media_content_key").toString()));
		}
		return policy;
	}

}
