package com.ssn.ssijs.workflow.flavours.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ssn.ssijs.workflow.server.Config;
import com.ssn.ssijs.workflow.server.ConfigReader;

public class Reflection {

	public ActionResponse doStuffReflection(String functionName, String... param)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, InstantiationException {
		ConfigReader reader = ConfigReader.getInstance();
		Config config = reader.getConfig();
		Class<?> cls = Class.forName(config.getActionClass());
		Class<?>[] paramTypes = new Class<?>[param.length];
		for (int i = 0; i < param.length; i++) {
			paramTypes[i] = String.class;
		}

		Object testClass = cls.newInstance();
		Method setNameMethod = testClass.getClass().getMethod(functionName, paramTypes);

		return (ActionResponse) setNameMethod.invoke(testClass, param); // pass arg
	}

	public ActionResponse doStuffReflection(String functionName)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, InstantiationException {
		ConfigReader reader = ConfigReader.getInstance();
		Config config = reader.getConfig();
		Class<?> cls = Class.forName(config.getActionClass());
		Object testClass = cls.newInstance();
		Method setNameMethod = testClass.getClass().getMethod(functionName);

		return (ActionResponse) setNameMethod.invoke(testClass); // pass arg
	}

//	public ActionResponse doStuffReflectionSWT(String functionName, String... param)
//			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
//			InvocationTargetException, ClassNotFoundException, InstantiationException {
//		Class<?>[] paramTypes = new Class<?>[param.length];
//		for (int i = 0; i < param.length; i++) {
//			paramTypes[i] = String.class;
//		}
//		ApplicationSession a = ApplicationSession.getInstance();
//		Object testClass = a.getStub();
//
//		Method setNameMethod = testClass.getClass().getMethod(functionName, paramTypes);
//		return (ActionResponse) setNameMethod.invoke(testClass, param); // pass arg
//	}

}