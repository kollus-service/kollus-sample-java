package com.kollus.kr.kollus_sample_java.controller;

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
import com.kollus.kr.kollus_sample_java.types.CallbackMethod;
import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
import com.kollus.kr.kollus_sample_java.types.PlaySection;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.KollusResEnc;
import com.kollus.kr.kollus_sample_java.util.StringUtil;
/**
 * 콜백 서블릿
 * DRM, PLAY, LMS 등 분기로 처리함
 * kollus - 콜백 등록시
 * http://localhost/callbak/[lms|mdrm|pdrm|play|upload|transcoding|update|add|delete]
 * 콜백 유형에 대한 정보는 CallbackMethod 를 참조함
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/callback/*")
public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CallbackServlet.class);

	private Websocket ws = new Websocket();
	private HttpUtil httpUtil = new HttpUtil();
	private ObjectMapper mapper = new ObjectMapper();

	public CallbackServlet() {
		super();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);
		mapper.configure(SerializationFeature.CLOSE_CLOSEABLE, true);
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
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

		HashMap<String, Object> responseWsMap = new HashMap<String, Object>();
		HashMap<String, Object> resItem = new HashMap<String, Object>();
		HashMap<String, Object> policyMap = null;
		String requestBody = null;
		String responseBody = null;
		String responseJson = null;
		int kind = 0;
		CallbackMethod callbackMethod = null;
		if (request.getPathInfo() != null) {
			String[] splitPaths = request.getPathInfo().split("/");
			if (splitPaths.length <= 1) {
				return;
			}
			String path = splitPaths[1];
			callbackMethod = CallbackMethod.getMethod(path);
			logger.debug(String.format("CallbackMethod : %s", callbackMethod));
		} else {
			return;
		}

		switch (callbackMethod) {
		case LMS:
			requestBody = request.getParameter("json_data");
			break;
		case UPLOAD:
		case TRANSCODING:
		case UPDATE:
		case CHANNEL_ADD:
		case CHANNEL_DELETE:
			try {
				requestBody = mapper.writeValueAsString(
						httpUtil.getMapFromFormUrlEncoded(URLDecoder.decode(httpUtil.getBody(request), "UTF-8")));

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case MOBILE_DRM:
		case PC_DRM:
			try {
				requestBody = httpUtil.getBody(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<HashMap<String, Object>> resListDrmMap = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> reqItems = JsonPath.read(requestBody.replace("items=", ""), "$[*]");

			String uservalue_drm = null;
			for (HashMap<String, Object> item : reqItems) {
				resItem.clear();
				String mck = item.get("media_content_key").toString();
				kind = Integer.parseInt(item.get("kind").toString());
				if (item.containsKey("uservalues")) {
					uservalue_drm = ((HashMap) item.get("uservalues")).get("uservalue0").toString();
				}
				if ("test".equals(uservalue_drm)) {
					String policy = mapper.writeValueAsString(CallbackConf.DRMCALLBACK_POLICY);
					policyMap = getTestPolicyMap(policy, kind);
				} else {
					policyMap = getPolicyMap(mck, callbackMethod, kind);
				}
				long startat = 0L;
				resItem.put("kind", kind);
				resItem.put("media_content_key", mck);

				for (Entry<String, Object> drmPolicy : policyMap.entrySet()) {
					String key = drmPolicy.getKey();
					Object value = drmPolicy.getValue();
					if ("expiration_date".equals(key)) {
						Date now = new Date();
						Calendar c = Calendar.getInstance();
						c.setTime(now);
						c.add(Calendar.SECOND, Integer.parseInt(value.toString()));
						long expt = c.getTime().getTime();
						resItem.put("expiration_date", expt);
					} else {
						String strValue = value.toString();
						if (!strValue.isEmpty()) {
							if (StringUtil.isNumeric(value)) {
								resItem.put(key, Integer.parseInt(value.toString()));
							} else {
								resItem.put(key, value);
							}
						}
					}
				}
				switch (kind) {
				case 1:
				case 2:
					break;
				case 3:
					startat = Long.parseLong(item.get("start_at").toString());
					resItem.put("start_at", startat);
					break;
				}
				resListDrmMap.add(resItem);
			}
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put("data", resListDrmMap);
			responseJson = mapper.writeValueAsString(temp);
			responseBody = KollusResEnc.encResponse(responseJson, KollusCallbackEnc.JWT,
					UserConf.getInstance().getKOLLUS_SECRET_KEY(), UserConf.getInstance().getKOLLUS_ACCESS_TOKEN());
			break;
		case PLAY:
			resItem.clear();
			try {
				requestBody = httpUtil.getBody(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			HashMap<String, Object> requestBodyMap = httpUtil.getMapFromFormUrlEncoded(requestBody);
			kind = Integer.parseInt(requestBodyMap.get("kind").toString());
			String play_mck = requestBodyMap.get("media_content_key").toString();
			String uservalue = null;
			if (requestBodyMap.containsKey("uservalues")) {
				uservalue = JsonPath
						.read(URLDecoder.decode(requestBodyMap.get("uservalues").toString(), "UTF-8"), "$.uservalue0")
						.toString();
			}
			if ("test".equals(uservalue)) {
				String policy = mapper.writeValueAsString(CallbackConf.PLAYCALLBACK_POLICY);
				policyMap = getTestPolicyMap(policy, kind);
			} else {
				policyMap = getPolicyMap(play_mck, callbackMethod, kind);
			}
			resItem.put("media_content_key", play_mck);
			String start_time = null, end_time = null;
			String[] sTimes = null, eTimes = null;
			if (policyMap.get("start_time") != null) {
				start_time = policyMap.get("start_time").toString();
				if (!start_time.trim().isEmpty()) {
					sTimes = start_time.split(",");
				}
			}
			if (policyMap.get("end_time") != null) {
				end_time = policyMap.get("end_time").toString();
				if (!end_time.trim().isEmpty()) {
					eTimes = end_time.split(",");
				}
			}
			if (sTimes != null && eTimes != null) {
				List<PlaySection> list_play_section = new ArrayList<PlaySection>();
				if (sTimes.length >= 1 && eTimes.length >= 1 && sTimes.length == eTimes.length) {
					int count = sTimes.length;
					for (int idx = 0; count > idx; idx++) {
						resItem.put("play_section",
								new PlaySection(Integer.parseInt(sTimes[idx]), Integer.parseInt(eTimes[idx])));
					}
				}
			}
			policyMap.remove("start_time");
			policyMap.remove("end_time");

			policyMap.remove("vmcheck");
			policyMap.remove("expiration_playtime");
			policyMap.remove("disable_tvout");

			for (Entry<String, Object> drmPolicy : policyMap.entrySet()) {
				String key = drmPolicy.getKey();
				Object value = drmPolicy.getValue();
				if ("expiration_date".equals(key)) {
					Date now = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(now);
					c.add(Calendar.SECOND, Integer.parseInt(value.toString()));
					long expt = c.getTime().getTime() / 1000;
					resItem.put("expiration_date", expt);
				} else {
					String strValue = value.toString();
					if (!strValue.isEmpty()) {
						if (StringUtil.isNumeric(value)) {
							resItem.put(key, Integer.parseInt(value.toString()));
						} else {
							resItem.put(key, value);
						}
					}
				}
			}
			HashMap<String, Object> temp2 = new HashMap<String, Object>();
			temp2.put("data", resItem);
			responseJson = mapper.writeValueAsString(temp2);
			responseBody = KollusResEnc.encResponse(responseJson, KollusCallbackEnc.JWT,
					UserConf.getInstance().getKOLLUS_SECRET_KEY(), UserConf.getInstance().getKOLLUS_ACCESS_TOKEN());
			break;
		default:
			logger.debug(request.getPathInfo());
			return;
		}

		responseWsMap.put("kind", kind);
		responseWsMap.put("type", callbackMethod.toString());
		responseWsMap.put("time", new Date().getTime());
		responseWsMap.put("request", requestBody);
		responseWsMap.put("response", responseBody);
		responseWsMap.put("responseJson", responseJson);

		response.setStatus(200);
		response.reset();
		if (callbackMethod == CallbackMethod.PC_DRM || callbackMethod == CallbackMethod.MOBILE_DRM
				|| callbackMethod == CallbackMethod.PLAY) {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/json");
			response.setHeader("X-Kollus-UserKey", UserConf.getInstance().getKOLLUS_USER_KEY());
			if (callbackMethod == CallbackMethod.PC_DRM || callbackMethod == CallbackMethod.MOBILE_DRM) {
			}
			response.getWriter().print(responseBody.trim().replaceAll("[\r\n]", ""));
		}
		ws.handleMessage(mapper.writeValueAsString(responseWsMap));
	}

	private HashMap<String, Object> getPolicyMap(String mck, CallbackMethod method, int kind) throws IOException {
		String fileName = this.getServletContext().getRealPath(String.format("/files/policy/%s.json", mck));
		File file = new File(fileName);
		if (!file.exists()) {
			file = new File(fileName.replace(mck, "default"));
		}
		BufferedReader in = new BufferedReader(new FileReader(file));
		StringBuilder fileContent = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			fileContent.append(line);
		}
		HashMap<String, Object> result = null;
		switch (method) {
		case PC_DRM:
		case MOBILE_DRM:
			result = JsonPath.read(fileContent.toString(), String.format("$.drm.k%d", kind));
			break;
		case PLAY:
			result = JsonPath.read(fileContent.toString(), String.format("$.play.k%d", kind));
			break;
		default:
			break;
		}
		return result;
	}
	
	private HashMap<String, Object> getTestPolicyMap(String policy, int kind) throws IOException {
		HashMap<String, Object> result = JsonPath.read(policy, String.format("$.k%d", kind));
		return result;
	}
}
