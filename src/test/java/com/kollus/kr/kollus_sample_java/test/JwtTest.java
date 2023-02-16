package com.kollus.kr.kollus_sample_java.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import com.kollus.kr.kollus_sample_java.util.JwtUtil;

public class JwtTest {

	public static void main(String[] args) {

		JwtUtil jwtUtil = new JwtUtil();
		String headerJson = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";
		String payloadJson = "{\"cuid\": \"hdyang2\",\"expt\": 1462931880,\"mc\": [{\"mckey\": \"voY75pnF\"},{\"mckey\": \"p82K5pnH\"}]}";
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.MINUTE, 5);
		now = c.getTime();
		payloadJson = payloadJson.replace("1462931880",String.format("%d", now.getTime()));
		String result;
		try {
			result = jwtUtil.createJwt(headerJson, payloadJson);
			System.out.println(result);		
			System.out.println(String.format("https://v.kr.kollus.com/s?jwt=%s&custom_key=사용자키", result));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
