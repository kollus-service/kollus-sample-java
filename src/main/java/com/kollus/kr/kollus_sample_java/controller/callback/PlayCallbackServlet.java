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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.CallbackConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.data.PlayCallbackRequest;
import com.kollus.kr.kollus_sample_java.data.PlayCallbackResponse;
import com.kollus.kr.kollus_sample_java.data.PlayPolicy;
import com.kollus.kr.kollus_sample_java.data.PlaySection;
import com.kollus.kr.kollus_sample_java.data.WebSocketMessage;
import com.kollus.kr.kollus_sample_java.types.CallbackMethod;
import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.KollusResEnc;
import com.kollus.kr.kollus_sample_java.util.StringUtil;

/**
 * 콜백 서블릿 PLAY
 * 
 * @author Yang Hyeon Deok
 * @since 2017. 7. 11.
 */
@WebServlet("/callback/play")
public class PlayCallbackServlet extends CallbackServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(PlayCallbackServlet.class);

	public PlayCallbackServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HashMap<String, Object> requestMap = null;
		try {
			requestMap = this.requestToMap(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (requestMap != null) {
			PlayCallbackRequest playCallbackRequest = PlayCallbackRequest.getPlayCallbackRequestFromMap(requestMap);
			int kind = playCallbackRequest.getKind();
			String mediaContentKey = playCallbackRequest.getMediaContentKey();
			String uservalue0 = playCallbackRequest.getUservalue(0);
			HashMap<String, Object> policyMap = "test".equals(uservalue0) ? getTestPlayPolicyMap(kind)
					: getPlayPolicyMap(mediaContentKey, kind);
			PlayPolicy policy = PlayPolicy.getPlayPolicyFromMap(policyMap);
			PlayCallbackResponse playCallbackResponse = PlayCallbackResponse.getPlayCallbackResponseFromPolicy(kind,
					policy);
			responseJson = playCallbackResponse.toBody();
			responseBody = KollusResEnc.encResponse(responseJson, KollusCallbackEnc.JWT,
					UserConf.getInstance().getKOLLUS_SECRET_KEY(), UserConf.getInstance().getKOLLUS_ACCESS_TOKEN());
			response.setHeader("X-Kollus-UserKey", UserConf.getInstance().getKOLLUS_USER_KEY());
			this.writeResponse(response);
			WebSocketMessage websocketMessage = new WebSocketMessage(kind, "PLAY", playCallbackRequest.toString(),
					responseBody, responseJson);
			this.sendWebSocketMessage(websocketMessage);
		}

	}

	private HashMap<String, Object> getPlayPolicyMap(String mck, int kind) throws IOException {
		String policyJson = this.getPolicyJson(mck);
		return JsonPath.read(policyJson, String.format("$.play.k%d", kind));
	}

	private HashMap<String, Object> getTestPlayPolicyMap(int kind) throws IOException {
		String policy = mapper.writeValueAsString(CallbackConf.PLAYCALLBACK_POLICY);
		return getTestPolicyMap(policy, kind);
	}
}
