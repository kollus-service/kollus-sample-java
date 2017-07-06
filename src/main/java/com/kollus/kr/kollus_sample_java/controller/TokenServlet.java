package com.kollus.kr.kollus_sample_java.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.UrlConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.JwtUtil;
/**
 * Web Token (JWT) Servlet
 * 
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/token")
public class TokenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpUtil httpUtil;
	private JwtUtil jwtUtil;
	private ObjectMapper mapper;
	public TokenServlet(){
		super();
		httpUtil = new HttpUtil();
		jwtUtil = new JwtUtil();
		mapper = new ObjectMapper();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		try {
			String body = httpUtil.getBody(request);
			String headerJson = JsonPath.read(body, "$.header").toString();
			String payloadJson = mapper.writeValueAsString(JsonPath.read(body, "$.payload"));
			String token = jwtUtil.createJwt(headerJson, payloadJson, UserConf.getInstance().getKOLLUS_SECRET_KEY());
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("token", token);
			resultMap.put("custom_key", UserConf.getInstance().getKOLLUS_USER_KEY());
			resultMap.put("playUrl", jwtUtil.getPlayUrl(UrlConf.KOLLUS_URL, token, UserConf.getInstance().getKOLLUS_USER_KEY()));
			result = mapper.writeValueAsString(resultMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = String.format("{\"error\":\"%s\"}", e.getMessage());
		}
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}
}
