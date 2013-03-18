package ca.sfu.icat.util.cron;

/*
 * Created by IntelliJ IDEA.
 * User: robert
 * Date: 13-03-16
 * Time: 12:43 AM
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class CronThread extends Thread {

	private String className;
	private String methodName;

	private static Logger logger = Logger.getLogger(CronThread.class.getName());

	public CronThread(String className, String methodName) {
		super();
		this.className = className;
		this.methodName = methodName;
	}

	public void run(){
		try {
			Class c = Class.forName(className);
			if (c == null) {
				logger.info("Class \"" + className + "\" not found.");
				return;
			}
			Method theMethod = c.getMethod(methodName, null);
			if (theMethod == null) {
				logger.info("Method \"" + methodName + "\" for class \"" + className + "\" not found.");
				return;
			}
			theMethod.invoke(null, null);
		} catch (ClassNotFoundException e) {
			logger.info("Class \"" + className + "\" not found.");
		} catch (NoSuchMethodException e) {
			logger.info("Class \"" + className
						+ "\" does not have a static method \""
						+ methodName + "\".");
		} catch (IllegalAccessException e) {
            e.printStackTrace();
			logger.info("IllegalAccessException thrown running \""
						+ className + "."
						+ methodName + "()\": "
						+ e);
		} catch (InvocationTargetException e) {
            e.printStackTrace();
			logger.info("InvocationTargetException thrown running \""
						+ className + "."
						+ methodName + "()\": "
						+ e);
		}
	}
}
