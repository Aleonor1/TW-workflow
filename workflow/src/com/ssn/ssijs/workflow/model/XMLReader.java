package com.ssn.ssijs.workflow.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * This example shows , how to map nested java object in jaxb 2.0
 * 
 * @author Ranjeet.Jha
 *
 */
public class XMLReader {
	private List<String> ids = new ArrayList<>();

	public Workflow doConfig(String fileNameXML) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Workflow.class);
		Unmarshaller um = context.createUnmarshaller();
		Workflow workflow = (Workflow) um.unmarshal(new FileReader(fileNameXML));
		for (Page p : workflow.getPagesList()) {
			checkPages(p);
		}
		for (Action a : workflow.getActionsList()) {
			checkActions(a);
		}
		return workflow;
	}

	private void checkPages(Page page) {
		if (ids.contains(page.getId())) {
			throw new IllegalArgumentException("Id-ul " + page.getId() + " este duplicat!");
		} else {
			ids.add(page.getId());
			for (Control c : page.getControls()) {
				checkControl(c);
			}
		}
	}

	private void checkActions(Action action) {
		if (ids.contains(action.getId())) {
			throw new IllegalArgumentException("Id-ul " + action.getId() + " este duplicat!");
		} else {
			ids.add(action.getId());
		}
	}

	private void checkControl(Control c) {
		if (ids.contains(c.getId())) {
			throw new IllegalArgumentException("Id-ul " + c.getId() + " este duplicat!");
		} else {
			ids.add(c.getId());
		}

	}

	private static XMLReader xmlReader = null;

	private XMLReader() {

	}

	public static XMLReader getInstance() {
		if (xmlReader == null)
			xmlReader = new XMLReader();

		return xmlReader;
	}

}