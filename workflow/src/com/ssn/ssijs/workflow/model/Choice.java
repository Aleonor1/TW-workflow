package com.ssn.ssijs.workflow.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Choice extends Control {
	private String value;
	private String target;

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "Choice [value=" + value + ", target=" + target + ", id=" + id + "]";
	}

}
