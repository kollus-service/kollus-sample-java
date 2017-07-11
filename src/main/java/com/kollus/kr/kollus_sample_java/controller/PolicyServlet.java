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
import com.kollus.kr.kollus_sample_java.util.HttpUtil;

/**
 * Play, Drm 콜백의 정책 설정 서블렛
 * \/policy : 기본 정책 설정
 * \/policy\/media_content_key : 해당 컨텐츠에 대한 정책 설정
 * POST : 정책 설정
 * GET : 정책 탐색
 * 모든 정책은 src/main/webapp/files/policy 폴더 아래에 저장이되며 기본값은 default.json 파일에 저장됨.
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/policy/*")
public class PolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpUtil httpUtil = new HttpUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolicyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] subPaths = null;
		if (request.getPathInfo() != null) {
			subPaths = request.getPathInfo().split("/");
		} else {
			return;
		}
		String fileName = this.getServletContext().getRealPath(String.format("/files/policy/%s.json", subPaths[1]));
		File file = new File(fileName);
		if(!file.exists()){
			file = new File(fileName.replace(subPaths[1], "default"));
		}
		@SuppressWarnings("resource")
		BufferedReader in  = new BufferedReader(new FileReader(file));
		StringBuilder fileContent = new StringBuilder();
		String line = null;
		while(( line = in.readLine())!=null){
			fileContent.append(line);
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.append(fileContent.toString());
		writer.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestBody = null;
		String[] subPaths = null;
		if (request.getPathInfo() != null) {
			subPaths = request.getPathInfo().split("/");
		} else {
			return;
		}
		try {
			requestBody = httpUtil.getBody(request);
			if(requestBody == null && requestBody.trim().isEmpty())
			{
				response.setStatus(500);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> policyMap = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmMap = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmK1Map = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmK2Map = new HashMap<String, Object>();
		HashMap<String, Object> policyDrmK3Map = new HashMap<String, Object>();
		HashMap<String, Object> policyPlayMap = new HashMap<String, Object>();
		HashMap<String, Object> policyPlayK1Map = new HashMap<String, Object>();
		HashMap<String, Object> policyPlayK3Map = new HashMap<String, Object>();
		
		HashMap<String, Object> src_drm_map = JsonPath.read(requestBody, "$.drm");
		for(Entry<String, Object> entry : src_drm_map.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			if(key.contains("tb_drm_k1_")){
				policyDrmK1Map.put(key.replace("tb_drm_k1_", ""), value);
			}
			if(key.contains("tb_drm_k2_")){
				policyDrmK2Map.put(key.replace("tb_drm_k2_", ""), value);
			}
			if(key.contains("tb_drm_k3_")){
				policyDrmK3Map.put(key.replace("tb_drm_k3_", ""), value);
			}
		}
		policyDrmMap.put("k1", policyDrmK1Map);
		policyDrmMap.put("k2", policyDrmK2Map);
		policyDrmMap.put("k3", policyDrmK3Map);
		HashMap<String, Object> src_play_map = JsonPath.read(requestBody, "$.play");
		for(Entry<String, Object> entry : src_play_map.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			if(key.contains("tb_play_k1_")){
				policyPlayK1Map.put(key.replace("tb_play_k1_", ""), value);
			}
			if(key.contains("tb_play_k3_")){
				policyPlayK3Map.put(key.replace("tb_play_k3_", ""), value);
			}
		}
		policyPlayMap.put("k1", policyPlayK1Map);
		policyPlayMap.put("k3", policyPlayK3Map);
		policyMap.put("drm", policyDrmMap);
		policyMap.put("play", policyPlayMap);
		
		ObjectMapper mapper = new ObjectMapper();
		String fileContent = mapper.writeValueAsString(policyMap);
		String fileName = this.getServletContext().getRealPath(String.format("/files/policy/%s.json", subPaths[1]));		
		File file = new File(fileName);
		FileWriter fw = new FileWriter(fileName, false);
		fw.write(fileContent);
		fw.flush();
		fw.close();
		response.setStatus(200);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.append(fileContent.toString());
		writer.flush();
	}

}
