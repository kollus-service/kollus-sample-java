package com.kollus.kr.kollus_sample_java.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollus.kr.kollus_sample_java.types.HttpMethod;
/**
 * HTTP 전송 Util
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private String send(String _url, HttpMethod method, HashMap<String, String> headers, String body) throws Exception {
		URL url = new URL(_url);

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(30 * 1000);
		} catch (Exception e) {
			Thread.sleep(5000L);
			connection = (HttpURLConnection) url.openConnection();
		}

		connection.setRequestMethod(method.getMethod());
		if (headers != null) {
			for (String key : headers.keySet()) {
				connection.setRequestProperty(key, headers.get(key));
			}
		}
		if (body != null && body.trim() != "") {
			if (method == HttpMethod.POST || method == HttpMethod.UPDATE) {
				connection.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				logger.debug(String.format("Body [%s] %s", _url, body));
				byte[] bodybytes = body.getBytes("UTF-8");
				logger.debug(String.format("String Len : %d, Byte Len : %d", body.length(), bodybytes.length));
				wr.write(bodybytes);
				wr.flush();
			}
		}
		int responseCode = 0;
		int retry = 0;
		try {
			responseCode = connection.getResponseCode();
		} catch (Exception e) {
			System.out.println("Raise ERR getResponseCode()");
			while (retry < 5 && responseCode == 0) {
				Thread.sleep(2000L);
				try {
					responseCode = connection.getResponseCode();
				} catch (Exception sub) {
					System.out.println("Raise Sub ERR getResponseCode()");
				}
				retry += 1;
			}
		}
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
		String inputLine;
		StringBuffer response = new StringBuffer();

		int inIdx = 0;
		while ((inputLine = in.readLine()) != null) {
			logger.debug(String.format("inputStrem [%s] [%d] %s", _url, inIdx, inputLine));
			response.append(inputLine);
			inIdx++;
		}
		in.close();
		return response.toString();
	}

	public String get(String _url, HashMap<String, String> headers) throws Exception {
		return send(_url, HttpMethod.GET, headers, null);
	}

	public String post(String _url, HashMap<String, String> headers, String body) throws Exception {
		return send(_url, HttpMethod.POST, headers, body);
	}

	public String getBody(HttpServletRequest request) throws Exception {
		String body = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream is = request.getInputStream();
			if (is != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(is));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					sb.append(charBuffer, 0, bytesRead);
				}
			} else {
				sb.append("");
			}
			body = sb.toString();

		} catch (Exception e) {
			throw new Exception("Raise Error in HttpUtil.getBody", e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					throw new Exception("Can't close buffer reader. Raise Error in HttpUtil.getBody", e);
				}
			}
		}
		return body;

	}

	public String getHeaderString(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		StringBuilder sb = new StringBuilder();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			sb.append(name);
			sb.append(":");
			sb.append(request.getHeader(name));
			sb.append(",");
		}
		return sb.toString();
	}

	public HashMap<String, Object> getMapFromFormUrlEncoded(String source) {
		String[] pairs = source.split("\\&");
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (String pair : pairs) {
			String key = pair.split("\\=")[0];
			String value = pair.split("\\=")[1];
			map.put(key, value);
		}
		return map;
	}

}
