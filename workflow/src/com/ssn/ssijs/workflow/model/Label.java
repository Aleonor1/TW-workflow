package com.ssn.ssijs.workflow.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Label extends Control {
	private String source;
	private String value;

	@XmlAttribute
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	public String getValue() {
		return value;
	}
}
