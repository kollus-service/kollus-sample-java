package com.kollus.kr.kollus_sample_java.test;

import java.util.HashMap;

import com.google.gson.Gson;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;

public class HttpUtilTest {

	public static void main(String[] args) {

		HttpUtil httpUtil = new HttpUtil();
		String _url = "http://api.kr.kollus.com/0/media/category/index?access_token=7ge80tfvz51x2606";
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/xml; charset=utf-8");
		headers.put("Content-Type", "application/xml; charset=utf-8");
		try {
			String result = httpUtil.get(_url, headers);
			System.out.println(result);
			Gson gson = new Gson();
			HashMap<String, Object> resultMap = gson.fromJson(result, HashMap.class);
			System.out.println(resultMap.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
