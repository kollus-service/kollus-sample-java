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
import com.kollus.kr.kollus_sample_java.data.DrmCallbackRequest;
import com.kollus.kr.kollus_sample_java.data.DrmCallbackRequestList;
import com.kollus.kr.kollus_sample_java.data.DrmCallbackResponse;
import com.kollus.kr.kollus_sample_java.data.DrmCallbackResponseList;
import com.kollus.kr.kollus_sample_java.data.DrmPolicy;
import com.kollus.kr.kollus_sample_java.data.PlaySection;
import com.kollus.kr.kollus_sample_java.data.WebSocketMessage;
import com.kollus.kr.kollus_sample_java.types.CallbackMethod;
import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.KollusResEnc;
import com.kollus.kr.kollus_sample_java.util.StringUtil;

/**
 * 콜백 서블릿 DRM
 *  
 * @author Yang Hyeon Deok
 * @since 2017. 7. 11.
 */
@WebServlet("/callback/drm")
public class DrmCallbackServlet extends CallbackServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(DrmCallbackServlet.class);

	public DrmCallbackServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<HashMap<String, Object>> requestMapList = null;
		try {
			requestMapList = this.requestToMapList(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (requestMapList != null) {
			DrmCallbackResponseList drmCallbackResponseList = new DrmCallbackResponseList();
			DrmCallbackRequestList drmCallbackRequestList = DrmCallbackRequestList
					.getDrmCallbackRequestFromMap(requestMapList);
			for (DrmCallbackRequest drmCallbackRequest : drmCallbackRequestList) {
				int kind = drmCallbackRequest.getKind();
				String mediaContentKey = drmCallbackRequest.getMediaContentKey();
				String uservalue0 = drmCallbackRequest.getUservalues(0);
				int startAt = drmCallbackRequest.getStartAt();
				String seesionKey = drmCallbackRequest.getSessionKey();
				HashMap<String, Object> policyMap = "test".equals(uservalue0) ? getTestDrmPolicyMap(kind)
						: getDrmPolicyMap(mediaContentKey, kind);
				DrmPolicy policy = DrmPolicy.getDrmPolicy(policyMap);
				DrmCallbackResponse drmCallbackResponse = DrmCallbackResponse.getDrmCallbackResponseFromPolicy(kind,
						mediaContentKey, seesionKey, startAt, policy);
				drmCallbackResponseList.add(drmCallbackResponse);

			}
			responseJson = drmCallbackResponseList.toBody();
			responseBody = KollusResEnc.encResponse(responseJson, KollusCallbackEnc.JWT,
					UserConf.getInstance().getKOLLUS_SECRET_KEY(), UserConf.getInstance().getKOLLUS_ACCESS_TOKEN());
			response.setHeader("X-Kollus-UserKey", UserConf.getInstance().getKOLLUS_USER_KEY());
			this.writeResponse(response);
			WebSocketMessage websocketMessage = new WebSocketMessage(0, "DRM", drmCallbackRequestList.toString(),
					responseBody, responseJson);
			this.sendWebSocketMessage(websocketMessage);
		}
	}

	private HashMap<String, Object> getDrmPolicyMap(String mck, int kind) throws IOException {
		String policyJson = this.getPolicyJson(mck);
		return JsonPath.read(policyJson, String.format("$.drm.k%d", kind));
	}

	private HashMap<String, Object> getTestDrmPolicyMap(int kind) throws IOException {
		String policy = mapper.writeValueAsString(CallbackConf.DRMCALLBACK_POLICY);
		return getTestPolicyMap(policy, kind);
	}
}
