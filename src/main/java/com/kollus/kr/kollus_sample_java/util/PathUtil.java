package com.kollus.kr.kollus_sample_java.util;

import java.io.File;
import java.net.URISyntaxException;

import com.kollus.kr.kollus_sample_java.Main;
/**
 * 파일 경로 유틸 클래스
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class PathUtil {
	public static File getRootFolder() {
		try {
			File root;
			String runningJarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
					.replaceAll("\\\\", "/");
			int lastIndexOf = runningJarPath.lastIndexOf("/target/");
			if (lastIndexOf < 0) {
				root = new File("");
			} else {
				root = new File(runningJarPath.substring(0, lastIndexOf));
			}
			System.out.println("application resolved root folder: " + root.getAbsolutePath());
			return root;
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

}
