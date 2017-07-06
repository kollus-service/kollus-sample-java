package com.kollus.kr.kollus_sample_java.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kollus.kr.kollus_sample_java.config.UrlConf;
import com.kollus.kr.kollus_sample_java.config.UserConf;
import com.kollus.kr.kollus_sample_java.util.HttpUtil;
/**
 * Kollus 카테고리 정보처리 Servlet
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpUtil httpUtil = null;
	private ObjectMapper mapper = null;

	public CategoryServlet() {
		super();
		httpUtil = new HttpUtil();
		mapper = new ObjectMapper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String _url = UrlConf.KOLLUS_CATEGORY_URL + "?access_token=" + UserConf.getInstance().getKOLLUS_ACCESS_TOKEN();
		HashMap<String, String> headers = null;
		String body = null;
		String result = null;
		try {
			body = httpUtil.get(_url, headers);
			result = mapper.writeValueAsString(JsonPath.read(body, "$.result.items.item[*]"));
		} catch (Exception e) {
			String err = String.format("{\"error\":\"%s\"}", e.getMessage());
			response.getWriter().println(err);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

}
