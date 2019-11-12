package com.ssn.ssijs.workflow.flavours.web;

import java.util.ArrayList;

import com.ssn.ssijs.workflow.model.Button;
import com.ssn.ssijs.workflow.model.Control;
import com.ssn.ssijs.workflow.model.Page;

public class ErrorPage {
	private String id;
	private String title;
	private String type;
	java.util.List<Button> buttons = new ArrayList<>();

	public ErrorPage(Page page) {
		this.id = page.getId();
		this.title = page.getTitle();
		this.type = page.getType();
		for (Control controls : page.getControls()) {
			if (controls instanceof Button) {
				buttons.add((Button) controls);
			}
		}
	}

	public String getHtmlCodeForEdit(String text) {
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

				"</style>" + "<form name=\"loginForm\" method=\"get\"\">\r\n" + "<h1 align=\"center\">" + text + "</h1>"
				+ "<br/>\r\n";
		code += "<form action=\"${pageContext.request.contextPath}/myservlet\" method=\"post\">";
		for (Button but : buttons) {
			code += "    <button name=\"button\" value=" + but.getTarget() + ">" + but.getLabel();
		}
		code += "</form></form>";
		return code;
	}
}
