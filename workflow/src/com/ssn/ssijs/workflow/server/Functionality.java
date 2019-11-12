package com.ssn.ssijs.workflow.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.ssn.ssijs.workflow.flavours.web.ActionResponse;
import com.ssn.ssijs.workflow.server.sql.ArticleSQL;

public interface Functionality extends Remote {
	public ActionResponse sendContactMessage() throws RemoteException;

	public ActionResponse addArticle(String barcode, String name) throws RemoteException;

	public ActionResponse removeArticle(String barcode) throws RemoteException;

	public List<ArticleSQL> show() throws RemoteException;

	public ActionResponse checkUser(String badge) throws RemoteException;
}
