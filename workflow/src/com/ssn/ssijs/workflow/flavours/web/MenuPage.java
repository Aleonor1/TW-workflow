package com.ssn.ssijs.workflow.flavours.web;

import java.util.ArrayList;

import com.ssn.ssijs.workflow.model.Choice;
import com.ssn.ssijs.workflow.model.Control;
import com.ssn.ssijs.workflow.model.Page;

public class MenuPage {
	private String id;
	private String title;
	private String type;
	private java.util.List<Choice> choices = new ArrayList<>();

	public MenuPage(Page page) {
		this.id = page.getId();
		this.title = page.getTitle();
		this.type = page.getType();
		for (Control controls : page.getControls()) {
			if (controls instanceof Choice) {
				choices.add((Choice) controls);
			}
		}
	}

	public java.util.List<Choice> getChoices() {
		return choices;
	}

	public String getHtmlCodeForMenu() {
		String htmlCode = "<style>body {font-family: Arial, Helvetica, sans-serif;}\r\n"
				+ "* {box-sizing: border-box}\r\n" + "\r\n" + "input[type=text], input[type=password] {\r\n"
				+ "  width: 100%;\r\n" + "  padding: 15px;\r\n" + "  margin: 5px 0 22px 0;\r\n"
				+ "  display: inline-block;\r\n" + "  border: none;\r\n" + "  background: #f1f1f1;\r\n" + "}\r\n"
				+ "\r\n" + "input[type=text]:focus, input[type=password]:focus {\r\n" + "  background-color: #ddd;\r\n"
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
				+ "h1 {color:gry;text-align:centre;}\r\n" + "</style>"
				+ "<form name=\"loginForm\" method=\"get\"\">\r\n" + "<h1 align=\"center\">" + this.title + "</h1>"
				+ "<br/>\r\n";
		for (Choice c : choices) {
			htmlCode += "\r\n" + "<button name=\"button\"  value= \"" + c.getTarget() + "\">" + c.getValue()
					+ "</button><br>";
		}
		htmlCode += "</script>";

		return htmlCode;
	}

}