package com.kollus.kr.kollus_sample_java.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.kollus.kr.kollus_sample_java.config.UrlConf;
/**
 * JWT 유틸 클래스
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class JwtUtil {

	public String createJwt(final String headerJson, final String payloadJson, String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String header = Base64.encodeBase64URLSafeString(headerJson.getBytes(StandardCharsets.UTF_8));
		String payload = Base64.encodeBase64URLSafeString(payloadJson.getBytes(StandardCharsets.UTF_8));
		String content = String.format("%s.%s", header, payload);
		final Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
		byte[] signatureBytes = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));
		String signature = Base64.encodeBase64URLSafeString(signatureBytes);
		return String.format("%s.%s", content, signature);
	}
	public String createJwt(final String payloadJson, String secretKey)
			throws InvalidKeyException, NoSuchAlgorithmException {
		String headerJson = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";
		return createJwt(headerJson, payloadJson, secretKey);
	}
	public String createJwt(String cuid, int exptMinutes, String secretKey, String... mediaKeys)
			throws InvalidKeyException, NoSuchAlgorithmException {
		String fmt_payloadJson = "{\"cuid\": \"%s\",\"expt\": %d,\"mc\": [%s]}";
		StringBuilder sb = new StringBuilder();
		int nMediakeys = mediaKeys.length;
		for (int idx = 0; idx < nMediakeys; idx++) {
			sb.append("{\"mckey\":\"");
			sb.append(mediaKeys[idx]);
			sb.append("\"}");
			if (idx < nMediakeys - 1) {
				sb.append(",");
			}
		}

		String listMeidaKey = sb.toString();
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.MINUTE, exptMinutes);
		long expt = c.getTime().getTime();
		final String payloadJson = String.format(fmt_payloadJson, cuid, expt, listMeidaKey);
		return createJwt(payloadJson, secretKey);

	}

	public String getPlayUrl(String host, String userKey, String cuid, String secretKey, int exptMinutes,
			String... mediaKeys) throws InvalidKeyException, NoSuchAlgorithmException {

		String token = createJwt(cuid, exptMinutes, secretKey, mediaKeys);
		return getPlayUrl(host, token, userKey);
	}
	public String getPlayUrl(String host, String token, String userKey){
		return String.format("%s/s?jwt=%s&custom_key=%s", host, token, userKey);
	}

	public String getPlayUrlByMediaToken(String mediaToken){
		return String.format(UrlConf.KOLLUS_URL + "/s?token=%s", mediaToken);
	}
	public String[] splitJwt(String jwt) throws Exception{
		String[] parts = jwt.split("\\.");
		if (parts.length == 2 && jwt.endsWith(".")) {
			parts = new String[] { parts[0], parts[1], "" };
		}
		if (parts.length != 3) {
			throw new Exception(String.format("The token was expected to have 3 parts, but got %s.", parts.length));
		}
		return parts;
	}
	public String[] decodeJwt(String jwt) throws Exception {

		String[] parts = splitJwt(jwt);
		String headerJson = StringUtils.newStringUtf8(Base64.decodeBase64(parts[0]));
		String payloadJson = StringUtils.newStringUtf8(Base64.decodeBase64(parts[1]));
		String signature = parts[2];
		return new String[]{headerJson, payloadJson, signature};
	}
	public boolean verify(String secretKey, String jwt) throws Exception{
		String[] parts = splitJwt(jwt);
		byte[] contentBytes = String.format("%s.%s", parts[0], parts[1]).getBytes(StandardCharsets.UTF_8);
		byte[] signatureBytes = Base64.decodeBase64(parts[2]);
		
		final Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
		byte[] newSignatureBytes = mac.doFinal(contentBytes);
		
		
		return MessageDigest.isEqual(newSignatureBytes, signatureBytes);
		
	}
}
