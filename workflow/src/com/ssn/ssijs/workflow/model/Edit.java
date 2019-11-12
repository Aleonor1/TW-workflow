package com.ssn.ssijs.workflow.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Edit extends Control {
	private String label;
	private String field;
	private String password;

	@XmlAttribute
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlAttribute
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlAttribute
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "Edit [label=" + label + ", field=" + field + ", id=" + id + "]";
	}

}
