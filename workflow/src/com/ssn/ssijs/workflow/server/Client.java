package com.ssn.ssijs.workflow.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public Client() {
	}

	public static void main(String[] args) {

		try {
			ConfigReader reader = ConfigReader.getInstance();
			Config config = reader.getConfigData("config.xml");
			Registry registry = LocateRegistry.getRegistry(config.getIp(), config.getServerport());
			Functionality stub = (Functionality) registry.lookup("Hello");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
