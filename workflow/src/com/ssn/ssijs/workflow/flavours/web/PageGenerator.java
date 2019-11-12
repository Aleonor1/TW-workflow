package com.ssn.ssijs.workflow.flavours.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import com.ssn.ssijs.workflow.model.Control;
import com.ssn.ssijs.workflow.model.Label;
import com.ssn.ssijs.workflow.model.Page;
import com.ssn.ssijs.workflow.model.Workflow;

public class PageGenerator {
	private Workflow flow = Workflow.getInstance();

	public String generatePage(String targetPageId, PrintWriter out, HttpServletRequest request,
			HttpServletResponse response) throws IOException, JAXBException, NotBoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException {
		ActionServlet as = new ActionServlet();
		response.setContentType("text/html");
		if (targetPageId == null || targetPageId.isEmpty()) {
			targetPageId = flow.getStartPage();
		}
		Page thisPage = getPage(targetPageId);
		String but = new String();
		String[] params = request.getParameterValues("param");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		but = request.getParameter("button");
		thisPage = getPage(targetPageId);
		if (but != null) {
			targetPageId = as.doAction(but, thisPage, params, out, request);
		}
		ActionResponse actionResponse = as.getActionResponse();
		thisPage = getPage(targetPageId);
		out.println(getHtmlCode(thisPage, actionResponse));
		return targetPageId;
	}

	private String getHtmlCode(Page startPage, ActionResponse actionResponse)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, InstantiationException {
		if (startPage == null) {
			startPage = getPage(flow.getStartPage());
			EditPage page = new EditPage(startPage);
			return page.getHtmlCodeForEdit();
		}
		switch (startPage.getType()) {
		case "edit_page":
			EditPage page = new EditPage(startPage);
//			if (page.getLabels().size() != 0) {
//				if (page.getLabels().get(0).getValue() == null) {
//					ErrorPage ep = new ErrorPage(startPage);
//					String type = getType(startPage);
//					String text = actionResponse.getTextForMsg(type);
//					return ep.getHtmlCodeForEdit(text);
//				}
//			} AFISARE PAGINA EROARE DACA NU E STOC SUFICIENT ??
			return page.getHtmlCodeForEdit();
		case "menu_page":
			MenuPage mp = new MenuPage(startPage);
			return mp.getHtmlCodeForMenu();
		case "view_page":
			ViewPage vp;
			try {
				vp = new ViewPage(startPage);
				return vp.getHtmlCode();
			} catch (FileNotFoundException | RemoteException | JAXBException | NotBoundException e) {
				e.printStackTrace();
			}
		default:
			ErrorPage ep = new ErrorPage(startPage);
			String type = getType(startPage);
			String text = actionResponse.getTextForMsg(type);
			return ep.getHtmlCodeForEdit(text);
		}
	}

	private String getType(Page startPage) {
		for (Control control : startPage.getControls()) {
			if (control instanceof Label) {
				if (control.getId().contains("error")) {
					return ((Label) control).getSource();
				}
			}
		}
		return null;
	}

	private Page getPage(String id) {
		List<Page> pages = flow.getPagesList();
		for (Page p : pages) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

}
