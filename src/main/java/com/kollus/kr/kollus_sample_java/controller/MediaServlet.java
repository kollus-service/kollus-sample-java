package com.kollus.kr.kollus_sample_java.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.UrlConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
import com.kollus.kr.kollus_sample_java.util.JwtUtil;

/**
 * 컨텐츠 탐색 서블렛
 * \/media\/category\/categorykey : 카테고리내 컨텐츠 탐색
 * \/media\/channel\/channelkey : 채널내 컨텐츠 탐색
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/media/*")
public class MediaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(MediaServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MediaServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] subPaths = null;
		if (request.getPathInfo() != null) {
			subPaths = request.getPathInfo().split("/");
		} else {
			return;
		}
		HttpUtil httpUtil = new HttpUtil();
		
		String _url = null;
		HashMap<String, String> headers = null;
		String accessToken = UserConf.getInstance().getKOLLUS_ACCESS_TOKEN();
		String page = request.getParameter("page");
		String accountKey = UserConf.getInstance().getKOLLUS_ACCOUNT_KEY();
		String secretKey = UserConf.getInstance().getKOLLUS_SECRET_KEY();
		String userKey = UserConf.getInstance().getKOLLUS_USER_KEY();
		if (subPaths.length < 2) {
			/* TODO /media/ */
		} else if (subPaths.length == 2) {
			if ("category".equals(subPaths[1])){
				_url = String.format(UrlConf.KOLLUS_LIBRARY_ALL_CONTENTS_URL, accessToken, page);
			}
			else if( "channel".equals(subPaths[1])) {
				_url = String.format(UrlConf.KOLLUS_CHANNEL_ALL_CONTENTS_URL, accessToken, page);
			} else {
			}

		}
		else if (subPaths.length == 3) {
			if ("category".equals(subPaths[1])){
				_url = String.format(UrlConf.KOLLUS_LIBRARY_CONTENTS_URL, accessToken, subPaths[2],page);
			}
			else if( "channel".equals(subPaths[1])) {
				_url = String.format(UrlConf.KOLLUS_CHANNEL_CONTENTS_URL, accessToken, subPaths[2],page);
			} else {
			}

		}
		String result = null;
		List<HashMap<String, Object>> resultMap = null;
		List<HashMap<String, Object>> sendMap = null;
		try {
			logger.debug(_url);
			result = httpUtil.get(_url, headers);
			resultMap = JsonPath.read(result, "$.result.items.item[*]");
			sendMap = new ArrayList<HashMap<String, Object>>();
			if ("category".equals(subPaths[1])) {
				for (HashMap<String, Object> item : resultMap) {
					/*
					 * kind title upload_file_key category_name category_key
					 * poster_url snapshot_url channels channel_name channel_key
					 * media_content_key status status
					 */

					HashMap<String, Object> categoryItem = new HashMap<String, Object>();
					categoryItem.put("kind", item.get("kind"));
					categoryItem.put("title", item.get("title"));
					categoryItem.put("upload_file_key", item.get("upload_file_key"));
					categoryItem.put("category_name", item.get("category_name"));
					categoryItem.put("category_key", item.get("category_key"));
					categoryItem.put("poster_url", item.get("poster_url"));
					categoryItem.put("snapshot_url", item.get("snapshot_url"));
					@SuppressWarnings("unchecked")
					List<HashMap<String, Object>> channels = (List<HashMap<String, Object>>) item.get("channels");
					if (channels != null && channels.size() > 0) {
						categoryItem.put("channels", item.get("channels"));
					}
					categoryItem.put("status", item.get("status"));
					sendMap.add(categoryItem);
				}
			} else if ("channel".equals(subPaths[1])) {
				JwtUtil jwtUtil = new JwtUtil();
				for (HashMap<String, Object> item : resultMap) {
					/*
					 * kind title upload_file_key category_name category_key
					 * poster_url snapshot_url media_content_key status
					 */
					HashMap<String, Object> channelItem = new HashMap<String, Object>();
					channelItem.put("kind", item.get("kind"));
					channelItem.put("title", item.get("title"));
					channelItem.put("upload_file_key", item.get("upload_file_key"));
					channelItem.put("category_name", item.get("category_name"));
					channelItem.put("category_key", item.get("category_key"));
					channelItem.put("poster_url", item.get("poster_url"));
					channelItem.put("snapshot_url", item.get("snapshot_url"));
					channelItem.put("media_content_key", item.get("media_content_key"));
					channelItem.put("status", item.get("status"));
					String host = "http://v.kr.kollus.com";
					String mediaKeys = item.get("media_content_key").toString();
					channelItem.put("jwt_play_url",
							jwtUtil.getPlayUrl(host, userKey, accountKey, secretKey, 3600, mediaKeys));
					_url = String.format(
							"http://api.kr.kollus.com/0/media_auth/media_token/get_media_link_by_userkey?access_token=%s",
							accessToken);
					headers = new HashMap<String, String>();
					headers.put("ContentType", "application/xxx-www-form-urlencoded");

					String body = String.format("media_content_key=%s&security_key=%s&user_key=%s",
							mediaKeys, secretKey, userKey);
					String resultMediaToken = httpUtil.post(_url, headers, body);
					String mediaToken = JsonPath.read(resultMediaToken, "$.result.media_token");
					channelItem.put("key_play_url", jwtUtil.getPlayUrlByMediaToken(mediaToken));
					sendMap.add(channelItem);
				}
			}
		} catch (Exception e) {
			/* TODO /media ����ó�� �ڵ� �Է� */
			e.printStackTrace();
		}
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(sendMap));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
