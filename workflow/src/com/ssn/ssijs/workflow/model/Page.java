package com.ssn.ssijs.workflow.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "page")
public class Page {
	protected String id;
	protected String title;
	protected String type;
	@XmlElements(value = { @XmlElement(name = "edit", type = Edit.class),
			@XmlElement(name = "choice", type = Choice.class), @XmlElement(name = "button", type = Button.class),
			@XmlElement(name = "list", type = com.ssn.ssijs.workflow.model.List.class),
			@XmlElement(name = "label", type = com.ssn.ssijs.workflow.model.Label.class) })
	java.util.List<Control> controls;

	public java.util.List<Control> getControls() {
		return controls;
	}

	public void setControls(ArrayList<Control> controls) {
		this.controls = controls;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Page [id=" + id + ", title=" + title + ", type=" + type + ", controls=" + controls + "]";
	}

}