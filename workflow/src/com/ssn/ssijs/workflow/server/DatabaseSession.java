package com.ssn.ssijs.workflow.server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.ssn.ssijs.workflow.bl.Article;
import com.ssn.ssijs.workflow.bl.Location;
import com.ssn.ssijs.workflow.bl.PickingOrder;
import com.ssn.ssijs.workflow.bl.PickingOrderLine;
import com.ssn.ssijs.workflow.bl.PickingOrderStatus;
import com.ssn.ssijs.workflow.bl.StockItem;
import com.ssn.ssijs.workflow.server.sql.Badge;

public class DatabaseSession {
	private SessionFactory sessionFactory;
	private static DatabaseSession instance = null;

	private DatabaseSession() {
		final StandardServiceRegistry registry = //
				new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			init();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void init() {
		Session session = sessionFactory.openSession();
		Badge badge = new Badge("badge1");
		Article a1 = new Article("111", "banane");
		Article a2 = new Article("222", "prune");
		Article a3 = new Article("333", "struguri");
		Article a4 = new Article("444", "piersici");
		Article a5 = new Article("555", "afine");
		Article a6 = new Article("666", "capsuni");
		Article a7 = new Article("777", "coacaze");
		Article a8 = new Article("888", "pepene");

		Location l1 = new Location("L1");
		Location l2 = new Location("L2");
		Location l3 = new Location("L3");

		StockItem item1 = new StockItem(a1, l1, 50);
		StockItem item2 = new StockItem(a3, l2, 30);
		StockItem item3 = new StockItem(a2, l2, 30);
		StockItem item4 = new StockItem(a4, l3, 40);
		StockItem item5 = new StockItem(a5, l2, 50);
		StockItem item6 = new StockItem(a6, l3, 60);
		StockItem item7 = new StockItem(a1, l2, 20);
		StockItem item8 = new StockItem(a1, l3, 80);

		PickingOrder order1 = new PickingOrder("2", PickingOrderStatus.NEW);
		PickingOrder order2 = new PickingOrder("3", PickingOrderStatus.NEW);
		PickingOrder order3 = new PickingOrder("4", PickingOrderStatus.NEW);

		PickingOrderLine orderLine1 = new PickingOrderLine(160, PickingOrderStatus.NEW, a1);
		PickingOrderLine orderLine2 = new PickingOrderLine(30, PickingOrderStatus.NEW, a3);
		PickingOrderLine orderLine3 = new PickingOrderLine(20, PickingOrderStatus.NEW, a2);
		PickingOrderLine orderLine4 = new PickingOrderLine(40, PickingOrderStatus.NEW, a6);
		PickingOrderLine orderLine5 = new PickingOrderLine(30, PickingOrderStatus.NEW, a4);
		PickingOrderLine orderLine6 = new PickingOrderLine(45, PickingOrderStatus.NEW, a5);

		order1.addToList(orderLine1);
		order1.addToList(orderLine4);
		order1.addToList(orderLine2);
		order2.addToList(orderLine3);
		order3.addToList(orderLine5);
		order3.addToList(orderLine6);

		session.beginTransaction();
		session.save(badge);
		session.saveOrUpdate(a1);
		session.saveOrUpdate(a2);
		session.saveOrUpdate(a3);
		session.saveOrUpdate(a4);
		session.saveOrUpdate(a5);
		session.saveOrUpdate(a6);
		session.saveOrUpdate(a7);
		session.saveOrUpdate(a8);

		session.saveOrUpdate(l1);
		session.saveOrUpdate(l2);
		session.saveOrUpdate(l3);

		session.saveOrUpdate(item1);
		session.saveOrUpdate(item2);
		session.saveOrUpdate(item3);
		session.saveOrUpdate(item4);
		session.saveOrUpdate(item5);
		session.saveOrUpdate(item6);
		session.saveOrUpdate(item7);
		session.saveOrUpdate(item8);

		session.saveOrUpdate(order1);
		session.saveOrUpdate(order2);
		session.saveOrUpdate(order3);
		session.saveOrUpdate(orderLine4);
		session.saveOrUpdate(orderLine1);
		session.saveOrUpdate(orderLine2);
		session.saveOrUpdate(orderLine3);
		session.saveOrUpdate(orderLine5);
		session.saveOrUpdate(orderLine6);

		session.getTransaction().commit();
		session.close();
	}

	public static DatabaseSession getInstance() {
		if (instance == null) {
			instance = new DatabaseSession();
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
