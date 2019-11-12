package com.ssn.ssijs.workflow.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Button extends Control {
	private String label;
	private String target;
	private String selected;

	@XmlAttribute
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlAttribute
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
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
		return "Button [label=" + label + ", target=" + target + ", id=" + id + "]";
	}

}
