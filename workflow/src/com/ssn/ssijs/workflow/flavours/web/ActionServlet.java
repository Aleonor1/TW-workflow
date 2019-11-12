package com.ssn.ssijs.workflow.flavours.web;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ssn.ssijs.workflow.model.Action;
import com.ssn.ssijs.workflow.model.Button;
import com.ssn.ssijs.workflow.model.Choice;
import com.ssn.ssijs.workflow.model.Control;
import com.ssn.ssijs.workflow.model.Output;
import com.ssn.ssijs.workflow.model.Page;
import com.ssn.ssijs.workflow.model.Workflow;

public class ActionServlet {
	private Workflow flow = Workflow.getInstance();
	private ActionResponse actionResponse;

	public String doAction(String buttonId, Page page, String[] params, PrintWriter out, HttpServletRequest request)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, InstantiationException {
		Button button = getButton(buttonId, page);
		Reflection ref = new Reflection();
		if (button == null) {
			List<Choice> choices = getChoice(page);
			for (Choice choice : choices) {
				if (choice.getTarget().equals(buttonId)) {

					return choice.getTarget();
				}
			}

			return page.getId();
		}
		if (isPageId(button.getTarget())) {
			return button.getTarget();
		}
		if (params != null) {
			actionResponse = ref.doStuffReflection("checkUser", params);
		}
		if (isActionId(button.getTarget())) {
			Action action = getAction(button.getTarget());
			String checkbox = request.getParameter("checked");
			if (params != null || checkbox != null) {
				if (params == null && checkbox != null) {
					params = new String[1];
					params[0] = checkbox;
				}
				actionResponse = ref.doStuffReflection(action.getTarget(), params);
				System.out.println(actionResponse.getCode());
				Output output = action.getActionById(actionResponse.getCode());
				return output.getTarget();
			}
		}

		return page.getId();
	}

	public String getErrorPage() {
		List<Page> pages = flow.getPagesList();
		for (Page page : pages) {
			if (page.getType().equals("error_page")) {
				return page.getId();
			}
		}
		return null;
	}

	public Action getAction(String target) {
		List<Action> actions = flow.getActionsList();
		for (Action action : actions) {
			if (action.getId().equals(target)) {
				return action;
			}
		}
		return null;
	}

	public boolean isPageId(String target) {
		List<Page> pages = flow.getPagesList();
		for (Page page : pages) {
			if (page.getId().equals(target)) {
				return true;
			}
		}
		return false;
	}

	public boolean isActionId(String target) {
		List<Action> actions = flow.getActionsList();
		for (Action action : actions) {
			if (action.getId().equals(target)) {
				return true;
			}
		}
		return false;
	}

	public List<Button> getButtons(Page page) {
		List<Button> buttons = new ArrayList<>();
		List<Control> controls = page.getControls();
		for (Control control : controls) {
			if (control instanceof Button) {
				buttons.add((Button) control);
			}
		}
		return buttons;
	}

	private Button getButton(String buttonId, Page page) {
		List<Button> buttons = getButtons(page);
		for (Button button : buttons) {
			if (button.getTarget().equals(buttonId)) {
				return button;
			}
		}
		return null;
	}

	public List<Choice> getChoice(Page page) {
		List<Choice> choices = new ArrayList<>();
		List<Control> controls = page.getControls();
		for (Control control : controls) {
			if (control instanceof Choice) {
				choices.add((Choice) control);
			}
		}
		return choices;
	}

	public ActionResponse getActionResponse() {
		return actionResponse;
	}
}
