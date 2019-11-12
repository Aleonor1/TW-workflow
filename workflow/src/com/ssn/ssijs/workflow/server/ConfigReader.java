package com.ssn.ssijs.workflow.server;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ConfigReader {
	private static Config config;
	private static ConfigReader reader = null;
	private static final String FILE_NAME = "config.xml";

	public static ConfigReader getInstance() {
		if (reader == null)
			reader = new ConfigReader();

		return reader;
	}

	public Config getConfigData(String fileName) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(Config.class);
		Unmarshaller um = context.createUnmarshaller();
		config = (Config) um.unmarshal(new FileReader(fileName));
		return config;

	}

	public Config getConfig() {
		if (config == null) {
			try {
				config = getConfigData(FILE_NAME);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (JAXBException e) {
				System.out.println(e.getMessage());
			}
		}
		return config;
	}
}
