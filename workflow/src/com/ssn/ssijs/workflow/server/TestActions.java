package com.ssn.ssijs.workflow.server;

import java.rmi.RemoteException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.ssijs.workflow.flavours.web.ActionResponse;
import com.ssn.ssijs.workflow.server.sql.ArticleSQL;

public class TestActions implements Functionality {
	private DatabaseSession dbSession = DatabaseSession.getInstance();
	public static Functionality stub;

	public TestActions() {
		super();
//		ConfigReader reader = ConfigReader.getInstance();
//		Config config = null;
//		try {
//			config = reader.getConfigData("config.xml");
//		} catch (FileNotFoundException | JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Registry registry = null;
//		try {
//			registry = LocateRegistry.getRegistry(config.getIp(), config.getServerport());
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			stub = (Functionality) registry.lookup("Hello");
//		} catch (RemoteException | NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	// @SuppressWarnings({ "unchecked" })
	public ActionResponse addArticle(String barcode, String name) throws RemoteException {
		try {
			ArticleSQL obj = new ArticleSQL(barcode, name);
			Session session = dbSession.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(obj);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			ActionResponse acr = new ActionResponse(e.getMessage());
			return acr;
		}
		ActionResponse acr = new ActionResponse("OK");
		return acr;
	}

	@Override
	// @SuppressWarnings({ "unchecked" })
	public ActionResponse removeArticle(String barcode) {
		try {
			Session session = dbSession.getSessionFactory().openSession();
			session.beginTransaction();
			String hql = "delete from ArticleSQL where barcode= :barcode";
			Query query = session.createQuery(hql);
			query.setParameter("barcode", barcode);
			query.executeUpdate();
			session.getTransaction().commit();
			session.close();
			ActionResponse acr = new ActionResponse("OK");
			return acr;
		} catch (Exception e) {
			ActionResponse acr = new ActionResponse(e.getMessage());
			return acr;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleSQL> show() throws RemoteException {
		List<ArticleSQL> result = null;
		try {
			Session session = dbSession.getSessionFactory().openSession();
			session.beginTransaction();
			result = session.createQuery("from ArticleSQL").list();
			for (ArticleSQL article : result) {
				System.out.println("Article (" + article.getName() + ") : " + article.getBarcode());
			}
			session.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActionResponse checkUser(String badge) throws RemoteException {
		String response;
		if (badge.equals("badge1")) {
			response = "OK";
		} else {
			response = "ERROR";
		}
		ActionResponse acr = new ActionResponse(response);
		if (response.equals("ERROR")) {
			acr.addToMap("message", "a aparut o eroare");
		}
		return acr;

	}

	@Override
	public ActionResponse sendContactMessage() {
		System.out.println("Someone is contacting the server...");
		ActionResponse acr = new ActionResponse("Connection established successfully!");
		return acr;
	}

//	public Functionality getStub() {
//		return stub;
//	}

}
