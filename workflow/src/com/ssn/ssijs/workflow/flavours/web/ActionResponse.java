package com.ssn.ssijs.workflow.flavours.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ActionResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private Map<String, String> params = new HashMap<>();
	private Object rez;

	public ActionResponse() {

	}

	public ActionResponse(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void addToMap(String type, String text) {
		params.put(type, text);
	}

	public String getTextForMsg(String type) {
		return params.get(type);
	}

	public Object getRez() {
		return rez;
	}

	public void setRez(Object rez) {
		this.rez = rez;
	}

}
