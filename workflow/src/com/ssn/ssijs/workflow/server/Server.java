package com.ssn.ssijs.workflow.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	private static Remote stub;

	public Server() {
	}

	public static void main(String args[]) {

		try {
			doServerStuff();
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
		}
	}

	public static void doServerStuff() throws RemoteException, AlreadyBoundException, AccessException {
		ConfigReader reader = ConfigReader.getInstance();
		Config config = reader.getConfig();
		LocateRegistry.createRegistry(config.getServerport());
		Class<?> cls = null;
		Object obj = null;

		try {
			cls = Class.forName(config.getActionClass());
			obj = cls.newInstance();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		DatabaseSession.getInstance();
		stub = UnicastRemoteObject.exportObject((Remote) obj, 0);
		Registry registry = LocateRegistry.getRegistry(config.getIp(), config.getServerport());
		registry.bind("Hello", stub);
		System.err.println("Server ready");
	}

	public static Remote getStub() {
		return stub;
	}

}
