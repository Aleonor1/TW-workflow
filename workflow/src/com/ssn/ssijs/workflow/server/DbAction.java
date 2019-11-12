package com.ssn.ssijs.workflow.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class DbAction {
	private DatabaseSession dbSession = DatabaseSession.getInstance();
	private Object returnValue;
	private boolean withTransaction = true;

	public DbAction() {
//gdrgfsgr
	}

	public DbAction(boolean withTransaction) {
		this.withTransaction = withTransaction;
	}

	public void run() {
		Session session = dbSession.getSessionFactory().openSession();
		Transaction t = null;
		if (withTransaction) {
			t = session.beginTransaction();
		}

		doAction(session);

		if (withTransaction) {
			t.commit();
		}
		session.close();
	}

	public abstract void doAction(Session session);

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
}
