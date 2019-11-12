package com.ssn.ssijs.workflow.scraps;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.ssijs.workflow.bl.Article;
import com.ssn.ssijs.workflow.bl.StockItem;
import com.ssn.ssijs.workflow.server.DbAction;

public class HibernateTest {
	public static void main(String[] args) {
		new DbAction() {

			@Override
			public void doAction(Session session) {
				Query<?> q = session.createQuery("from Article where barcode=:barcode");
				q.setParameter("barcode", "111");
				Article a = (Article) q.uniqueResult();

				q = session.createQuery("from StockItem");
				List<?> list = q.list();
				for (StockItem si : (List<StockItem>) list) {
					System.out.println(a.equals(si.getArticle()));
					System.out.println(si.getArticle());
				}

			}
		}.run();
	}
}
