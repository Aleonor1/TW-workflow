package com.ssn.ssijs.workflow.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Column extends Control {
	private String name;
	private String field;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", id=" + id + "]";
	}

	@XmlAttribute
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
