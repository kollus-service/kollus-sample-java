package com.kollus.kr.kollus_sample_java.controller.callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
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
import com.kollus.kr.kollus_sample_java.controller.Websocket;
import com.kollus.kr.kollus_sample_java.data.WebSocketMessage;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;

/**
 * 콜백 서블릿 
 * 콜백 유형에 대한 정보는 CallbackMethod 를 참조함
 * 
 * @author Yang Hyeon Deok
 * @since 2017. 7. 11.
 */

public abstract class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CallbackServlet.class);

	protected Websocket webSocket = null; 
	protected HttpUtil httpUtil = null; 
	protected ObjectMapper mapper = null;
	protected String[] subPaths = null; 
	protected String requestBody = null;
	protected String responseBody = null;
	protected String responseJson = null;

	public CallbackServlet() {
		super();
		webSocket = new Websocket();
		httpUtil = new HttpUtil();
		mapper = new ObjectMapper();
		this.configureObjectMapper();
//		policyMap = new HashMap<String, Object>();
//		callbackReqBodyMap = new HashMap<String, Object>();
//		callbackRespItem = new HashMap<String, Object>();
//		websocketRespMap = new HashMap<String, Object>();
	}
	private void configureObjectMapper(){
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);
		mapper.configure(SerializationFeature.CLOSE_CLOSEABLE, true);
		mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, true);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			logger.debug("Get : " + names.nextElement());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	protected String requestToString(HttpServletRequest request) throws Exception{
		requestBody = httpUtil.getBody(request);
		return requestBody;
	}
	protected HashMap<String, Object> requestToMap(HttpServletRequest request) throws Exception{
		requestBody = httpUtil.getBody(request);
		return httpUtil.getMapFromFormUrlEncoded(requestBody);
		 
	}
	protected List<HashMap<String, Object>> requestToMapList(HttpServletRequest request) throws Exception{
		requestBody = httpUtil.getBody(request);
		return JsonPath.read(requestBody.replace("items=", ""), "$[*]");
	}
	protected void sendWebSocketMessage(String message) throws IOException{
		webSocket.handleMessage(message);
	}
	protected void sendWebSocketMessage(WebSocketMessage websocketMessage) throws IOException{
		webSocket.handleMessage(websocketMessage.toMessage());
	}
	protected void writeResponse(HttpServletResponse response) throws IOException{
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/json");
		response.getWriter().print(responseBody);
	}
	
	protected File getPolicyFile(String mck){
		String fileName = this.getServletContext().getRealPath(String.format("/files/policy/%s.json", mck));
		File file = new File(fileName);
		return file;
	}
	protected File getPolicyMckFileOrDefault(String mck){
		File file = getPolicyFile(mck);
		if (!file.exists()) {
			file = getPolicyFile("default");
		}
		return file;
	}
	protected String getPolicyJson(String mck) throws IOException{
		File file = getPolicyMckFileOrDefault(mck);
		BufferedReader in = new BufferedReader(new FileReader(file));
		StringBuilder fileContent = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			fileContent.append(line);
		}
		in.close();
		return fileContent.toString();
	}

	protected HashMap<String, Object> getTestPolicyMap(String policy, int kind) throws IOException {
		HashMap<String, Object> result = JsonPath.read(policy, String.format("$.k%d", kind));
		return result;
	}
}
