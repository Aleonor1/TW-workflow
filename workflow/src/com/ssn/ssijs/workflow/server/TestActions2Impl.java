package com.ssn.ssijs.workflow.server;

import java.rmi.Remote;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.ssijs.workflow.flavours.web.ActionResponse;

public class TestActions2Impl extends CommonActionsImpl implements TestActions2 {
	private static final String CODE_ERROR = "ERROR";
	private static final String CODE_VALID = "OK";
	private DatabaseSession dbSession = DatabaseSession.getInstance();
	public static Remote stub;

	@Override
	public ActionResponse login(String badgeId) {
		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {
				ActionResponse acr = null;
				String hql = "from Badge where badgeId= :badgeId ";
				Query query = session.createQuery(hql);
				query.setParameter("badgeId", badgeId);
				List result = query.list();
				if (result.isEmpty()) {
					acr = new ActionResponse(CODE_ERROR);
					acr.addToMap("message", "Invalid badge : " + badgeId);

				} else {
					acr = new ActionResponse(CODE_VALID);
				}
				setReturnValue(acr);
			}
		};
		dbAction.run();

		return (ActionResponse) dbAction.getReturnValue();
	}

}
