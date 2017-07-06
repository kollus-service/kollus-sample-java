package com.kollus.kr.kollus_sample_java.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kollus.kr.kollus_sample_java.types.KollusCallbackEnc;
import com.kollus.kr.kollus_sample_java.util.PathUtil;
/**
 * 사용자 접근 정보를 위한 설정 클래스
 * 기본값은 user.properties파일에서 설정  
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class UserConf {

	private String KOLLUS_USER_KEY = null;
	private String KOLLUS_ACCOUNT_KEY = null;
	private String KOLLUS_SECRET_KEY = null;
	private String KOLLUS_ACCESS_TOKEN = null;

	private static UserConf instance = null;
	
	public String getKOLLUS_USER_KEY() {
		return KOLLUS_USER_KEY;
	}
	public String getKOLLUS_ACCOUNT_KEY() {
		return KOLLUS_ACCOUNT_KEY;
	}
	public String getKOLLUS_SECRET_KEY() {
		return KOLLUS_SECRET_KEY;
	}
	public String getKOLLUS_ACCESS_TOKEN() {
		return KOLLUS_ACCESS_TOKEN;
	}
	
	public static UserConf getInstance() throws IOException{
		if(instance == null){
			instance = new UserConf();
		}
		return instance;
	}
	private UserConf() throws IOException{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			File file = new File(PathUtil.getRootFolder().getAbsolutePath(), "src/main/webapp/files/user.properties");
			input = new FileInputStream(file);
			prop.load(input);
			this.KOLLUS_ACCESS_TOKEN = prop.getProperty("accessToken");
			this.KOLLUS_USER_KEY = prop.getProperty("userKey");
			this.KOLLUS_SECRET_KEY = prop.getProperty("secretKey");
			this.KOLLUS_ACCOUNT_KEY = prop.getProperty("accountKey");
		}catch(IOException e){
			throw e;
		}
		finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
