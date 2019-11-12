package com.ssn.ssijs.workflow.scraps;

import org.hibernate.Session;

import com.ssn.ssijs.workflow.server.DbAction;
import com.ssn.ssijs.workflow.server.sql.Badge;

public class HibernateTest2 {
	public static void main(String[] args) {
		new DbAction() {

			@Override
			public void doAction(Session session) {
				Badge b = new Badge("qwe");
				session.saveOrUpdate(b);
			}
		}.run();
	}
}
