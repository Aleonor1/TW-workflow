package com.ssn.ssijs.workflow.flavours.web;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.ssn.ssijs.workflow.model.Button;
import com.ssn.ssijs.workflow.model.Column;
import com.ssn.ssijs.workflow.model.Control;
import com.ssn.ssijs.workflow.model.Page;
import com.ssn.ssijs.workflow.server.CommonActions;
import com.ssn.ssijs.workflow.server.CommonActionsImpl;
import com.ssn.ssijs.workflow.server.sql.ArticleSQL;

public class ViewPage {
	private String id;
	private String title;
	private String type;
	private java.util.List<Column> columns = new ArrayList<>();
	private java.util.List<Button> buttons = new ArrayList<>();
	private java.util.List<ArticleSQL> articles = new ArrayList<>();
	private com.ssn.ssijs.workflow.model.List list;

	public ViewPage(Page page) throws FileNotFoundException, JAXBException, RemoteException, NotBoundException {
		this.id = page.getId();
		this.title = page.getTitle();
		this.type = page.getType();
		for (Control c : page.getControls()) {
			if (c instanceof com.ssn.ssijs.workflow.model.List) {
				this.columns = ((com.ssn.ssijs.workflow.model.List) c).getColumn();
			} else if (c instanceof Button) {
				this.buttons.add((Button) c);
			}
			if (c instanceof com.ssn.ssijs.workflow.model.List) {
				list = (com.ssn.ssijs.workflow.model.List) c;
			}
		}
	}

	public String getHtmlCode() {
		String code = "<style>body {font-family: Arial, Helvetica, sans-serif;}\r\n" + "* {box-sizing: border-box}\r\n"
				+ "\r\n" + "input[type=text], input[type=password] {\r\n" + "  width: 100%;\r\n"
				+ "  padding: 15px;\r\n" + "  margin: 5px 0 22px 0;\r\n" + "  display: inline-block;\r\n"
				+ "  border: none;\r\n" + "  background: #f1f1f1;\r\n" + "}\r\n" + "\r\n"
				+ "input[type=text]:focus, input[type=password]:focus {\r\n" + "  background-color: #ddd;\r\n"
				+ "  outline: none;\r\n" + "}\r\n" + "\r\n" + "hr {\r\n" + "  border: 1px solid #f1f1f1;\r\n"
				+ "  margin-bottom: 25px;\r\n" + "}\r\n" + "\r\n" + "\r\n" + "button {\r\n"
				+ "  background-color: #4CAF50;\r\n" + "  color: white;\r\n" + "  padding: 14px 20px;\r\n"
				+ "  margin: 8px 0;\r\n" + "  border: none;\r\n" + "  cursor: pointer;\r\n" + "  width: 100%;\r\n"
				+ "  opacity: 0.9;\r\n" + "}\r\n" + "\r\n" + "button:hover {\r\n" + "  opacity:1;\r\n" + "}\r\n"
				+ "\r\n" + ".cancelbtn {\r\n" + "  padding: 14px 20px;\r\n" + "  background-color: #f44336;\r\n"
				+ "}\r\n" + "\r\n" + ".cancelbtn, .signupbtn {\r\n" + "  float: left;\r\n" + "  width: 50%;\r\n"
				+ "}\r\n" + "\r\n" + ".container {\r\n" + "  padding: 16px;\r\n" + "}\r\n" + "\r\n"
				+ ".clearfix::after {\r\n" + "  content: \"\";\r\n" + "  clear: both;\r\n" + "  display: table;\r\n"
				+ "}\r\n" + "\r\n" + "\r\n" + "@media screen and (max-width: 300px) {\r\n"
				+ "  .cancelbtn, .signupbtn {\r\n" + "     width: 100%;\r\n" + "  }\r\n</style>" + "<style>\r\n"
				+ "h1 {color:gry;text-align:centre;}\r\n" +

				"</style>" + "<form name=\"loginForm\" method=\"get\"\">\r\n" + "<h1 align=\"center\">" + this.title
				+ "</h1>" + "<br/>\r\n" + "<table border=\"1\" align=\"center\"><br/>\r\n<tr><br/>\r\n";
		code += "<td></td>";
		for (Column c : columns) {
			code += "<td>" + c.getName() + "</td>";
		}
		code += "</tr>";
		code += populateTable(list.getEntity());
		code += "</table>";
		for (Button but : buttons) {
			code += "    <button name=\"button\" value=" + but.getTarget() + ">" + but.getLabel();
		}
		code += "</form>";
		return code;
	}

	public <T> String populateTable(String className) {
		String code = "";
		CommonActions ca = new CommonActionsImpl();
		List<T> articles = null;
		Class<?> cls = null;
		try {
			cls = Class.forName(className);
			articles = (List<T>) ca.getAll(cls, list.getWhereClause());
		} catch (ClassNotFoundException | RemoteException e1) {
			e1.printStackTrace();
		}

		List<String> methods = getMethodsToBeCalled();
		for (int loopIndex = 0; loopIndex < articles.size(); loopIndex++) {
			Object testClass = articles.get(loopIndex);

			Method setNameMethod1 = null;
			try {
				setNameMethod1 = testClass.getClass().getMethod(methods.get(0), null);
				code += "<tr><td><input type=\"radio\" name=\"checked\" value="
						+ setNameMethod1.invoke(testClass).toString() + "><br></td>";
				for (String method : methods) {
					setNameMethod1 = testClass.getClass().getMethod(method, null);
					code += "<td>" + setNameMethod1.invoke(testClass).toString() + "</td>";
				}
				code += "</tr>";
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}

		}
		return code;
	}

	private List<String> getMethodsToBeCalled() {
		List<String> result = new ArrayList<>();
		for (Column c : list.getColumn()) {
			String obj = c.getField();
			obj = obj.substring(0, 1).toUpperCase() + obj.substring(1);
			result.add("get" + obj);
		}
		return result;
	}

}