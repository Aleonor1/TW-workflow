package com.ssn.ssijs.workflow.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


import com.ssn.ssijs.workflow.server.Config;
import com.ssn.ssijs.workflow.server.ConfigReader;

/**
 * This statement means that class "Bookstore.java" is the root-element of our
 * example //@XmlRootElement(namespace = "com.mysofkey.jaxb.day1")
 * 
 * @author Ranjeet Jha
 *
 */
@XmlRootElement
public class Workflow {

	private static Workflow workflow = null;


	public static Workflow getInstance() {
		XMLReader reader = XMLReader.getInstance();
		if (workflow == null) {
			try {
				ConfigReader configReader = ConfigReader.getInstance();
				Config config = null;
				try {
					config = configReader.getConfigData("config.xml");
				} catch (FileNotFoundException | JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				workflow = reader.doConfig(config.getWorkflowfile());
				return workflow;
			} catch (JAXBException | IOException e) {
				e.printStackTrace();
			}
		}
		return workflow;
	}

	public String getPageTitleById(String ig) {
		for (Page p : pages) {
			if (p.getId().equals(ig)) {
				return p.getTitle();
			}
		}
		return null;
	}

	// XmLElementWrapper generates a wrapper element around XML representation
	@XmlElementWrapper(name = "pages")
	// XmlElement sets the name of the entities
	@XmlElement(name = "page")
	private java.util.List<Page> pages;
	@XmlElementWrapper(name = "actions")
	@XmlElement(name = "action")
	private java.util.List<Action> actions;
	private String startPage;

	@XmlAttribute
	public String getStartPage() {
		return startPage;
	}

	public void setStartPage(String sP) {
		this.startPage = sP;
	}

	public void setPageList(ArrayList<Page> pageList) {
		this.pages = pageList;
	}

	public java.util.List<Page> getPagesList() {
		return pages;
	}

	public Output getOutputById(String id) {
		for (Action a : actions) {
			if (a.getActionById(id) != null) {
				return a.getActionById(id);
			}
		}
		return null;
	}

	public void addPage(Page page) {
		try {
			if (pages == null) {
				pages = new ArrayList<Page>();
			}
			pages.add(page);

		} catch (Exception e) {
		}
	}

	public Action checkActionsContainID(String id) {
		for (Action act : actions) {
			if (act.getId().equals(id)) {
				return act;
			}
		}
		return null;
	}

	public Page checkPagesContainID(String id) {
		for (Page pg : pages) {
			if (pg.getId().equals(id)) {
				return pg;
			}
		}
		return null;
	}

	public void setActionsList(java.util.List<Action> actionList) {
		this.actions = actionList;
	}

	public java.util.List<Action> getActionsList() {
		return actions;
	}

//	public Object getObjectById(String id) {
//		for (Action action : actions) {
//			if (action.getId().equals(id)) {
//				return action;
//			}
//
//		}
//		for (Page page : pages) {
//			if (page.getId().equals(id)) {
//				return page;
//			}
//		}
//		System.out.println(actions);
//
//		return null;
//	}

	public void addAction(Action action) {
		try {
			if (actions == null) {
				actions = new ArrayList<Action>();
			}
			actions.add(action);

		} catch (Exception e) {
		}
	}

	public Page createStartPage() {
		for (Page page : this.pages) {
			if (page.getId().equals(this.startPage)) {
				return page;
			}
		}
		return null;
	}

}