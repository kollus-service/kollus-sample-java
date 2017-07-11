package com.kollus.kr.kollus_sample_java.controller.callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.CallbackConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.controller.Websocket;
import com.kollus.kr.kollus_sample_java.data.PlaySection;
import com.kollus.kr.kollus_sample_java.data.WebSocketMessage;
import com.kollus.kr.kollus_sample_java.types.CallbackMethod;
import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.KollusResEnc;
import com.kollus.kr.kollus_sample_java.util.StringUtil;

/**
 * 콜백 서블릿 LMS 
 *  
 * @author Yang Hyeon Deok
 * @since 2017. 7. 11.
 */
@WebServlet("/callback/lms")
public class LmsCallbackServlet extends CallbackServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LmsCallbackServlet.class);

	public LmsCallbackServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HashMap<String, Object> lmsRequestMap = new HashMap<String, Object>();
		HashMap<String, String[]> parameterMap = (HashMap<String, String[]>) request.getParameterMap();

		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			lmsRequestMap.put(entry.getKey(), entry.getValue()[0]);
		}
		HashMap<String, Object> lmsResponseMap = new HashMap<String, Object>();
		lmsResponseMap.put("user", lmsRequestMap);
		lmsResponseMap.put("json_data", request.getParameter("json_data"));
		requestBody = mapper.writeValueAsString(lmsResponseMap);

		WebSocketMessage websocketMessage = new WebSocketMessage(0, "LMS", null, responseBody, responseJson);
		this.writeResponse(response);
		this.sendWebSocketMessage(websocketMessage);
	}
}
