package com.kollus.kr.kollus_sample_java;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import com.kollus.kr.kollus_sample_java.filter.CorsFilter;
import com.kollus.kr.kollus_sample_java.util.PathUtil;
/***
 * 샘플코드 실행 시작점.
 * embbed-tomcat 설정 및 실행
 * @author Yang Hyeon Deok (hdyang@catenoid.net)
 * @since 2017. 7. 6.
 */
public class Main {

	public static void main(String[] args) throws Exception {
		File root = PathUtil.getRootFolder();
		System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
		Tomcat tomcat = new Tomcat();
		Path tempPath = Files.createTempDirectory("tomcat-base-dir");
		tomcat.setBaseDir(tempPath.toString());

		// The port that we should run on can be set into an environment
		// variable
		// Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8089";
		}

		tomcat.setPort(Integer.valueOf(webPort));
		File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
		if (!webContentFolder.exists()) {
			webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
		}
		StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
		// Set execution independent of current thread context classloader
		// (compatibility with exec:java mojo)
		ctx.setParentClassLoader(Main.class.getClassLoader());

		System.out.println("configuring app with basedir: " + webContentFolder.getAbsolutePath());

		// Declare an alternative location for your "WEB-INF/classes" dir
		// Servlet 3.0 annotation will work
		File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
		WebResourceRoot resources = new StandardRoot(ctx);

		WebResourceSet resourceSet;
		if (additionWebInfClassesFolder.exists()) {
			resourceSet = new DirResourceSet(resources, "/WEB-INF/classes",
					additionWebInfClassesFolder.getAbsolutePath(), "/");
			System.out.println(
					"loading WEB-INF resources from as '" + additionWebInfClassesFolder.getAbsolutePath() + "'");
		} else {
			resourceSet = new EmptyResourceSet(resources);
		}
		resources.addPreResources(resourceSet);
		ctx.setResources(resources);
		String filterName = CorsFilter.class.getSimpleName();
		FilterDef filterDef = new FilterDef();
		filterDef.setFilterName(filterName);
		filterDef.setFilterClass(CorsFilter.class.getName());
		ctx.addFilterDef(filterDef);
		FilterMap filterMap = new FilterMap();
		filterMap.setFilterName(filterName);
		filterMap.addURLPattern("/**");
		ctx.addFilterMap(filterMap);
		tomcat.start();
		tomcat.getServer().await();
	}

}
