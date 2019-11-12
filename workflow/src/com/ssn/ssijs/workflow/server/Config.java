package com.ssn.ssijs.workflow.server;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class Config {
	private String workflowfile;
	private int serverport;
	private String ip;
	private String actionClass;

	public String getActionClass() {
		return actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	public String getWorkflowfile() {
		return workflowfile;
	}

	public void setWorkflowfile(String workflowfile) {
		this.workflowfile = workflowfile;
	}

	public int getServerport() {
		return serverport;
	}

	public void setServerport(int serverport) {
		this.serverport = serverport;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Config [workflowfile=" + workflowfile + ", serverport=" + serverport + ", ip=" + ip + ", actionClass="
				+ actionClass + "]";
	}

}
