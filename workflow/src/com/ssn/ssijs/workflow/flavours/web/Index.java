package com.ssn.ssijs.workflow.flavours.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String targetPageId = new String();

	// Method to handle POST method request.
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PageGenerator pageGenerator = new PageGenerator();

		try {
			targetPageId = pageGenerator.generatePage(targetPageId, out, request, response);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException | ClassNotFoundException | JAXBException
				| NotBoundException e) {
			System.out.println(e.getMessage());
		}

	}
}