package com.ssn.ssijs.workflow.flavours.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.catalina.startup.Bootstrap;

public class WebApp {

	public static void main(String[] args) throws IOException {
		File file = new File(".");
		String mainPath = file.getAbsolutePath();
		mainPath += "\\tomcat";
		Path path = Paths.get(mainPath + "\\webapps\\home");
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.getMessage();
			}
		}
		System.setProperty("user.dir", mainPath);
		Bootstrap.main(new String[] { "start" });//
	}
}
