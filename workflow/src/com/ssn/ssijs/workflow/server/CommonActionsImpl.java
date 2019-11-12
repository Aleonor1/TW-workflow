package com.ssn.ssijs.workflow.server;

import java.rmi.RemoteException;
import java.util.List;

import org.hibernate.Session;

import com.ssn.ssijs.workflow.flavours.web.ActionResponse;

public class CommonActionsImpl implements CommonActions {

	@Override
	public <T> List<T> getAll(Class<T> t, String whereClause) throws RemoteException {

		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {
				ActionResponse acr = null;
				int index = t.getName().lastIndexOf(".");
				String name = t.getName().substring(index + 1);
				List<T> result = session.createQuery("from " + name + whereClause).list();
				setReturnValue(result);
			}
		};
		dbAction.run();
		return (List<T>) dbAction.getReturnValue();

	}

}
