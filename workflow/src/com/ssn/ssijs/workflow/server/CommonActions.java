package com.ssn.ssijs.workflow.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CommonActions extends Remote {
	// parametru clasa Article,User..??
	public <T> List<T> getAll(Class<T> t, String whereClause) throws RemoteException;
}
