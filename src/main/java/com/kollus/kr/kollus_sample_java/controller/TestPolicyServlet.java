package com.kollus.kr.kollus_sample_java.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.CallbackConf;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;

/**
 * 임시적인 사용을 위한 콜백 정책 설정 서블릿
 * 콘텐츠별 설정은 불가능 하며 모든 정책은 메모리에 저장됨.
 * 어플리케이션 종료시 자료는 삭제됨
 *\/testpolicy/play : play 콜백 설정
 *\/testpolicy/drm : drm 콜백 설정
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/testpolicy/*")
public class TestPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TestPolicyServlet.class);
	private HttpUtil httpUtil = new HttpUtil();
	private ObjectMapper mapper = new ObjectMapper();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestPolicyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] subPaths = null;
		boolean success = false;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (request.getPathInfo() != null) {
			subPaths = request.getPathInfo().split("/");
		} else {
			return;
		}
		if ("drm".equals(subPaths[1])) {
			success = CallbackConf.DRMCALLBACK_POLICY != null;
			resultMap.put("success", success);
			resultMap.put("policy", CallbackConf.DRMCALLBACK_POLICY);
		} else if ("play".equals(subPaths[1])) {
			success = CallbackConf.PLAYCALLBACK_POLICY != null;
			resultMap.put("success", success);
			resultMap.put("policy", CallbackConf.PLAYCALLBACK_POLICY);
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(mapper.writeValueAsString(resultMap));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestBody = null;
		String responseBody = null;

		String[] subPaths = null;
		if (request.getPathInfo() != null) {
			subPaths = request.getPathInfo().split("/");
		} else {
			return;
		}
		try {
			requestBody = httpUtil.getBody(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (requestBody == null && requestBody.trim().isEmpty()) {
			response.setStatus(500);
			return;
		}
		HashMap<String, Object> policyMap = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmMap = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmK1Map = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmK2Map = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmK3Map = new HashMap<String, Object>();
		HashMap<String, Object> policyPlayMap = new HashMap<String, Object>();
		HashMap<String, Object> policyPlayK1Map = new HashMap<String, Object>();
		HashMap<String, Object> policyPlayK3Map = new HashMap<String, Object>();
		if ("drm".equals(subPaths[1])) {
			HashMap<String, Object> src_drm_map = JsonPath.read(requestBody, "$.drm");
			for (Entry<String, Object> entry : src_drm_map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.contains("tb_drm_k1_")) {
					policyDrmK1Map.put(key.replace("tb_drm_k1_", ""), value);
				}
				if (key.contains("tb_drm_k2_")) {
					policyDrmK2Map.put(key.replace("tb_drm_k2_", ""), value);
				}
				if (key.contains("tb_drm_k3_")) {
					policyDrmK3Map.put(key.replace("tb_drm_k3_", ""), value);
				}
			}
			policyDrmMap.put("k1", policyDrmK1Map);
			policyDrmMap.put("k2", policyDrmK2Map);
			policyDrmMap.put("k3", policyDrmK3Map);
			CallbackConf.DRMCALLBACK_POLICY = policyDrmMap;
		} else if ("play".equals(subPaths[1])) {

			HashMap<String, Object> src_play_map = JsonPath.read(requestBody, "$.play");
			for (Entry<String, Object> entry : src_play_map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.contains("tb_play_k1_")) {
					policyPlayK1Map.put(key.replace("tb_play_k1_", ""), value);
				}
				if (key.contains("tb_play_k3_")) {
					policyPlayK3Map.put(key.replace("tb_play_k3_", ""), value);
				}
			}
			policyPlayMap.put("k1", policyPlayK1Map);
			policyPlayMap.put("k3", policyPlayK3Map);
			CallbackConf.PLAYCALLBACK_POLICY = policyPlayMap;
		}

		response.setStatus(200);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print("{\"success\":\"ok\"}");
	}

}
