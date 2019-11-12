package com.ssn.ssijs.workflow.server;

import java.rmi.RemoteException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ssn.ssijs.workflow.bl.Article;
import com.ssn.ssijs.workflow.bl.LoadUnit;
import com.ssn.ssijs.workflow.bl.PickingOrder;
import com.ssn.ssijs.workflow.bl.PickingOrderLine;
import com.ssn.ssijs.workflow.bl.PickingOrderStatus;
import com.ssn.ssijs.workflow.bl.StockItem;
import com.ssn.ssijs.workflow.flavours.web.ActionResponse;
import com.ssn.ssijs.workflow.flavours.web.Parameter;

public class ActionsImpl implements Actions {
	private Parameter param = Parameter.getInstance();

	@Override
	public ActionResponse checkUser(String badgeId) throws RemoteException {
		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {
				ActionResponse acr = null;
				try {
					String hql = "from Badge where badgeId= :badgeId";
					Query<?> query = session.createQuery(hql);
					query.setParameter("badgeId", badgeId);
					List<?> result = null;
					result = query.list();
					if (result.isEmpty()) {
						acr = new ActionResponse("ERROR");
						acr.addToMap("message", "Invalid badge");

					} else {
						acr = new ActionResponse("OK");
					}
				} catch (Exception e) {
					acr = new ActionResponse("ERROR");
					acr.addToMap("message", e.getMessage());
				}
				setReturnValue(acr);
			}
		};
		dbAction.run();

		return (ActionResponse) dbAction.getReturnValue();
	}

	@Override
	public ActionResponse selectObject(String orderNo) throws RemoteException {
		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {
				ActionResponse acr = null;
				try {
					String hql = "from PickingOrder where orderNr= :orderNo";
					Query<?> query = session.createQuery(hql);
					query.setParameter("orderNo", orderNo);
					List<?> result = query.list();
					if (result.isEmpty()) {
						acr = new ActionResponse("ERROR");
						acr.addToMap("message", "Order number does not exist");
						acr.setRez(null);
					} else {
						acr = new ActionResponse("OK");
						PickingOrder po = (PickingOrder) result.get(0);
						po.setStatus(PickingOrderStatus.ACTIVE);
						acr.setRez(po);
						param.addParam("orderNo", orderNo);
					}
				} catch (Exception e) {
					acr = new ActionResponse("ERROR");
					acr.addToMap("message", e.getMessage());
					acr.setRez(null);
				}
				setReturnValue(acr);
			}
		};
		dbAction.run();

		return (ActionResponse) dbAction.getReturnValue();

	}

	@Override
	public ActionResponse selectLu(String loadUnit) throws RemoteException {
		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {

				ActionResponse acr = null;
				if (loadUnit == null || loadUnit.isEmpty()) {
					acr = new ActionResponse("ERROR");
					acr.addToMap("message", "Load Unit is null");
				} else {
					try {
						String hql = "from LoadUnit where barcode= :loadUnit";
						Query<?> query = session.createQuery(hql);
						query.setParameter("loadUnit", loadUnit);
						List<?> result = query.list();
						if (result.isEmpty()) {
							acr = new ActionResponse("OK");
							LoadUnit lu = new LoadUnit(loadUnit);
							acr.setRez(lu);
							session.save(lu);
						} else {
							acr = new ActionResponse("OK");
							LoadUnit lu = (LoadUnit) result.get(0);
							acr.setRez(lu);
						}
						param.addParam("loadUnit", loadUnit);

					} catch (Exception e) {
						e.printStackTrace();
						acr = new ActionResponse("ERROR");
						acr.addToMap("message", e.getMessage());
					}
				}
				setReturnValue(acr);
			}
		};
		dbAction.run();
		return (ActionResponse) dbAction.getReturnValue();
	}

	@Override
	public ActionResponse getItems(String articleBarcode) throws RemoteException {
		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {
				ActionResponse acr = null;
				try {
					if (!param.getParameters().get("article").equals(articleBarcode)) {
						acr = new ActionResponse("PICKING ERROR");
						acr.addToMap("message", "The barcode is not correct");
					} else {
						String aux = param.getParameters().get("orderNo");
						Query<?> q = session.createQuery("from Article where barcode= :barcode");
						q.setParameter("barcode", articleBarcode);
						Article article = (Article) q.uniqueResult();
						System.out.println(article);
						aux = param.getParameters().get("orderNo");
						Query<?> quer = session.createQuery("from PickingOrder where orderNr= :orderNo");
						quer.setParameter("orderNo", aux);
						PickingOrder po = (PickingOrder) quer.uniqueResult();

						Query<?> query = session.createQuery(
								"from PickingOrderLine where article_id= :article and pickingorder_id= :orderNo");
						query.setParameter("article", article.getId());
						query.setParameter("orderNo", po.getId());
						PickingOrderLine pOrder = (PickingOrderLine) query.uniqueResult();

						query = session.createQuery("from StockItem where article_id= :article");
						query.setParameter("article", article.getId());
						List<?> stockItems = query.list();

						StockItem si = (StockItem) stockItems.get(0);
						System.out.println(pOrder);
						if (pOrder.getQuantity() <= si.getPieces()) {
							System.err.println("?");
							pOrder.setQuantityPick(pOrder.getQuantity());
							pOrder.setStatus(PickingOrderStatus.FINISHED);
							session.saveOrUpdate(pOrder);
							si.setPieces(si.getPieces() - pOrder.getQuantity());
							if (si.getPieces() == 0) {
								Query<?> q3 = session.createQuery(
										"delete from StockItem where article_id= :articleId and pieces= :pieces");
								q3.setParameter("articleId", si.getArticle().getId());
								q3.setParameter("pieces", si.getPieces());
								q3.executeUpdate();
							}
						} else {
							pOrder.setQuantity((pOrder.getQuantity() - si.getPieces()));
							pOrder.setQuantityPick(si.getPieces());
							pOrder.setStatus(PickingOrderStatus.ACTIVE);
							session.saveOrUpdate(pOrder);
							Query<?> q3 = session.createQuery(
									"delete from StockItem where article_id= :articleId and pieces= :pieces");
							q3.setParameter("articleId", si.getArticle().getId());
							q3.setParameter("pieces", si.getPieces());
							q3.executeUpdate();

						}

						boolean bool = true;
						for (PickingOrderLine pol : po.getLines()) {
							if (pol.getStatus() != PickingOrderStatus.FINISHED) {
								bool = false;
							}
						}
						if (bool) {
							acr = new ActionResponse("FINISH");
							po.setStatus(PickingOrderStatus.FINISHED);
							session.saveOrUpdate(po);
						} else {
							acr = new ActionResponse("OK");
						}
						aux = param.getParameters().get("loadUnit");
						query = session.createQuery("from LoadUnit where barcode= :loadUnit");
						query.setParameter("loadUnit", aux);
						LoadUnit lUnit = (LoadUnit) query.uniqueResult();
					}
				} catch (Exception e) {
					e.printStackTrace();
					acr = new ActionResponse("ERROR");
					acr.addToMap("message", e.getMessage());
					e.printStackTrace();
				}
				setReturnValue(acr);
			}

		};
		dbAction.run();

		return (ActionResponse) dbAction.getReturnValue();
	}

	public ActionResponse getPickMessage() throws RemoteException {
		DbAction dbAction = new DbAction() {
			@Override
			public void doAction(Session session) {
				ActionResponse acr = null;
				try {
					String aux = param.getParameters().get("orderNo");
					Query<?> query = session.createQuery("from PickingOrder where orderNr= :orderNo");
					query.setParameter("orderNo", aux);
					List<?> pOrder = query.list();

					PickingOrder po = ((List<PickingOrder>) pOrder).get(0);
					if (!checkStock(session, po)) {
						acr = new ActionResponse("ERROR");
						acr.addToMap("message", "Stoc insuficient!");
						setReturnValue(acr);
						return;
					}
					Article article = null;
					int quantity = 0;
					for (PickingOrderLine pol : po.getLines()) {
						System.out.println(pol);
						if (pol.getStatus() != PickingOrderStatus.FINISHED) {
							article = pol.getArticle();
							quantity = pol.getQuantity();
							break;
						}
					}

					String mesaj = "";
					if (article != null) {
						query = session.createQuery("from StockItem where article_id= :article");
						query.setParameter("article", article.getId());
						List<?> stockItems = query.list();

						StockItem si = (StockItem) stockItems.get(0);

						if (quantity > si.getPieces()) {
							quantity = si.getPieces();
						}
						mesaj += "Pick " + quantity + " pieces of Article " + article.getName() + " from location "
								+ si.getLocation().getName();
					}
					param.addParam("article", article.getBarcode());
					acr = new ActionResponse("OK");
					acr.setRez(mesaj);
				} catch (Exception e) {
					acr = new ActionResponse("ERROR");
					acr.addToMap("message", e.getMessage());
				}
				setReturnValue(acr);
			}

			private boolean checkStock(Session session, PickingOrder po) {
				Article article = null;
				int quantity = 0;
				for (PickingOrderLine pol : po.getLines()) {

					if (pol.getStatus() != PickingOrderStatus.FINISHED) {
						article = pol.getArticle();
						quantity = pol.getQuantity();
						if (!checkValue(session, article, quantity)) {
							return false;
						}
					}
				}
				return true;
			}

			private boolean checkValue(Session session, Article article, int quantity) {
				Query<?> query = session.createQuery("select SUM(pieces) from StockItem where article_id= :article");
				query.setParameter("article", article.getId());
				List<?> stockItems = query.list();

				long si = (long) stockItems.get(0);

//				int stockItems = (int) query.uniqueResult();
				if (si < quantity) {
					return false;
				}
				return true;
			}
		};
		dbAction.run();

		return (ActionResponse) dbAction.getReturnValue();
	}

}
