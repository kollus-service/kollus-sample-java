package com.kollus.kr.kollus_sample_java.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.JwtUtil;
/**
 * Web Token (JWT) Servlet
 * 
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/decodetoken")
public class DecodeTokenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpUtil httpUtil;
	private JwtUtil jwtUtil;
	private ObjectMapper mapper;
	public DecodeTokenServlet(){
		super();
		httpUtil = new HttpUtil();
		jwtUtil = new JwtUtil();
		mapper = new ObjectMapper();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		try {
			StringBuilder err = new StringBuilder();
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			String body = httpUtil.getBody(request);
			String jwt = JsonPath.read(body, "$.jwt").toString();
			String[] decodedJwt = jwtUtil.decodeJwt(jwt);
			
			HashMap<String, Object> header = JsonPath.read(decodedJwt[0], "$");
			HashMap<String, Object> payload = JsonPath.read(decodedJwt[1], "$");
			resultMap.put("header", header);
			resultMap.put("payload", payload);
			resultMap.put("error", err.toString());
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
