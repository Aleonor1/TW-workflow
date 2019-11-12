package com.ssn.ssijs.workflow.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "action")
public class Action {
	private String id;
	private String target;

	@XmlElement(name = "param", type = Param.class)
	java.util.List<Param> params;
	@XmlElement(name = "output", type = Output.class)
	java.util.List<Output> outputs;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public java.util.List<Param> getParamsList() {
		return params;
	}

	public void setParamsList(java.util.List<Param> paramsList) {
		this.params = paramsList;
	}

	public java.util.List<Output> getOutputsList() {
		return outputs;
	}

	public void setOutputsList(java.util.List<Output> outputsList) {
		this.outputs = outputsList;
	}

	public void addParameter(Param param) {
		try {
			if (params == null) {
				params = new ArrayList<>();
			}
			params.add(param);

		} catch (Exception e) {
		}
	}

	public void addOutput(Output output) {
		try {
			if (outputs == null) {
				outputs = new ArrayList<>();
			}
			outputs.add(output);

		} catch (Exception e) {
		}
	}

	@Override
	public String toString() {
		return "Action [id=" + id + ", target=" + target + ", paramsList=" + params + ", outputsList=" + outputs + "]";
	}

	public Output getActionById(String id) {
		for (Output output : outputs) {
			if (output.getValue().equals(id)) {
				return output;
			}
		}
		return null;
	}

}
