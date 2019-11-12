package com.ssn.ssijs.workflow.flavours.web;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private static Parameter ai = null;
	private Map<String, String> parameters;

	private Parameter() {
		this.parameters = new HashMap<>();
	}

	public static Parameter getInstance() {
		if (ai == null) {
			ai = new Parameter();
		}
		return ai;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public void addParam(String key, String value) {
		parameters.put(key, value);
	}
}
