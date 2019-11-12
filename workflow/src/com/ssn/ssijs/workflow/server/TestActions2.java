package com.ssn.ssijs.workflow.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.ssn.ssijs.workflow.flavours.web.ActionResponse;

public interface TestActions2 extends Remote {
	public ActionResponse login(String badgeId) throws RemoteException;

}
