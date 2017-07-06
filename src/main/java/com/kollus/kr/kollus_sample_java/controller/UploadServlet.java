package com.kollus.kr.kollus_sample_java.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.UrlConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
/**
 * HTTP Endpoint Upload 서블릿
 * 
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/upload/*")
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(UploadServlet.class);
	private HttpUtil httpUtil = new HttpUtil();

	public UploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String responseBody = null;
		String[] subPaths = null;
		if (request.getPathInfo() != null) {
			subPaths = request.getPathInfo().split("/");
		} else {
			return;
		}

		if ("url".equals(subPaths[1])) {
			String _url = String.format(UrlConf.KOLLUS_UPLOAD_ENDPOINT + "?access_token=%s",
					UserConf.getInstance().getKOLLUS_ACCESS_TOKEN());
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			try {

				//String body = httpUtil.getBody(request);
				String body = request.getQueryString();
				String json = httpUtil.post(_url, headers, body);
				responseBody = new ObjectMapper().writeValueAsString(JsonPath.read(json, "$.result"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("upload".equals(subPaths[2])) {

		} else {

		}

		response.setStatus(200);
		response.setContentType("application/json;charset=utf-8");
		ServletOutputStream outputStream = response.getOutputStream();
		byte[] bodybytes = responseBody.trim().getBytes("UTF-8");
		outputStream.write(bodybytes, 0, bodybytes.length);
		outputStream.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

	}
}
