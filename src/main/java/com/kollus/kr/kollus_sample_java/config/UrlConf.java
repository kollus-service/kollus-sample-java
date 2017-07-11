package com.kollus.kr.kollus_sample_java.config;

/**
 * 콜러스 접속 및 API 사용을 위한 URL 상수 클래스
 * @author Yang Hyeon Deok (hdyang@catenoid.net)
 * @since 2017. 7. 6.
 */
public class UrlConf {
	public static final String KOLLUS_URL = "http://v.kr.kollus.com";
	public static final String KOLLUS_UPLOAD_ENDPOINT="https://api.kr.kollus.com/0/media_auth/upload/create_url.json";
	public static final String KOLLUS_ENCRYPT_URL="http://api.kr.kollus.com/0/media_auth/media_token/get_kollus_encrypt.json";
	public static final String KOLLUS_CATEGORY_URL="http://api.kr.kollus.com/0/media/category/index";
	public static final String KOLLUS_CHANNEL_URL="http://api.kr.kollus.com/0/media/channel/index";
	public static final String KOLLUS_LIBRARY_ALL_CONTENTS_URL="http://api.kr.kollus.com/0/media/library/media_content?access_token=%s&per_page=%s";
	public static final String KOLLUS_CHANNEL_ALL_CONTENTS_URL="http://api.kr.kollus.com/0/media/channel/media_content?access_token=%s&per_page=%s";
	public static final String KOLLUS_LIBRARY_CONTENTS_URL="http://api.kr.kollus.com/0/media/library/media_content?access_token=%s&category_key=%s&per_page=%s";
	public static final String KOLLUS_CHANNEL_CONTENTS_URL="http://api.kr.kollus.com/0/media/channel/media_content?access_token=%s&channel_key=%s&per_page=%s";
}
