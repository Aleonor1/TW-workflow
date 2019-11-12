package com.ssn.ssijs.workflow.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.ssn.ssijs.workflow.flavours.web.ActionResponse;

public interface Actions extends Remote {

	public ActionResponse checkUser(String badgeId) throws RemoteException;

	public ActionResponse selectObject(String orderNo) throws RemoteException;

	public ActionResponse selectLu(String loadUnit) throws RemoteException;

	public ActionResponse getItems(String articleBarcode) throws RemoteException;
}
