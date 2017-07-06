package com.kollus.kr.kollus_sample_java.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;

/**
 * 사용자 정보 처리 서블릿
 * 참조 클래스는 {@see UserConf}
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserServlet.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Properties prop = new Properties();
		InputStream input = null;
		String result = null;
		try {
			String file = this.getServletContext().getRealPath("/files/user.properties");
			input = new FileInputStream(file);
			prop.load(input);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(prop);
		}catch(IOException e){
			result = String.format("{\"error\":\"%s\"}", e.getMessage());
		}
		finally {
			input.close();
			response.getWriter().print(result);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpUtil httpUtil = new HttpUtil();
		Properties prop = new Properties();
		OutputStream out = null;
		String body = null;
		String result = null;
		try {
			body = httpUtil.getBody(request);
			if (body != null){
				ObjectMapper mapper = new ObjectMapper();
				String file = this.getServletContext().getRealPath("/files/user.properties");
				out = new FileOutputStream(file);
				Map<String, String> value = mapper.readValue(body, new TypeReference<Map<String, String>>() {});
				prop.putAll(value);
				prop.store(out, null);
				result = "{\"result\":\"ok\"}";
			}
		} catch (Exception e) {
			result = String.format("{\"error\":\"%s\"}", e.getMessage());
		}
		finally {
			out.close();
			response.getWriter().print(result);
		}
		
	}

}
