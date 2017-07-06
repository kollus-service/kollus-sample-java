package com.kollus.kr.kollus_sample_java.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.ActionConf;
import com.kollus.kr.kollus_sample_java.config.UrlConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
/**
 * Kollus Token 생성 유틸 클래스
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class KollusResEnc {
	public static String encResponse(String plain, KollusCallbackEnc enc, String secretKey, String accessToken) {
		String responseBody = null;
		HttpUtil httpUtil = new HttpUtil();
		switch (enc) {
		case JWT:
			JwtUtil jwtUtil = new JwtUtil();
			try {
				responseBody = jwtUtil.createJwt(plain, secretKey);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case KOLLUS:
			String cryptUrl = String.format(UrlConf.KOLLUS_ENCRYPT_URL + "?access_token=%s", accessToken);
			HashMap<String, String> cryptHeaders = new HashMap<String, String>();
			cryptHeaders.put("Content-Type", "application/x-www-form-urlencoded");
			String cryptBody = String.format("source_string=%s", plain);
			try {
				String crypt = httpUtil.post(cryptUrl, cryptHeaders, cryptBody);
				responseBody = JsonPath.read(crypt, "$.result.encrypt_string").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		return responseBody;
	}

	public static String encResponse(String plain) throws IOException {
		return KollusResEnc.encResponse(plain, ActionConf.CALLBACK_ENC, UserConf.getInstance().getKOLLUS_USER_KEY(),
				UserConf.getInstance().getKOLLUS_ACCESS_TOKEN());
	}
}
