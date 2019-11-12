package com.ssn.ssijs.workflow.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Scraps {
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		String className = "com.ssn.ssijs.workflow.server.TestActions2Impl";

		Class<?> cls = Class.forName(className);
		Object newInstance = cls.newInstance();

		Class<?>[] interfaces = cls.getInterfaces();
		for (Class<?> i : interfaces) {
			System.out.println(i);
		}

		String method = "login";
		Method method2 = cls.getMethod("login", String.class);
		Object invoke = method2.invoke(newInstance, "test");

		System.out.println(invoke);
	}
}
