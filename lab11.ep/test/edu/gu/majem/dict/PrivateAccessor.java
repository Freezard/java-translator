package edu.gu.majem.dict;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * Helper class to test private methods 
 * (we don't change visibility in class under test)
 * 
 * Don't need to understand this right now
 * This is how to use in general;
 * 
 *       PrivateAccessor pa = new PrivateAccessor(theObject, nameOfMethod, 
 *                        listOfParamTypes);
 *		 pa.setParams(listOfParams);
 *       // Must know result type and cast
 *		 boolean b = (Boolean) pa.test();
 *       Assert.... (b == ...)
 * 
 * Example;
 * 
 *       Adder a = new Adder(); // Class with private sum-method
 *           
 *       PrivateAccessor pa = new PrivateAccessor( a, "sum", 
 *                        Interger.class, Interger.class);
 *		 pa.setParams(1, 3);
 *       // Will execute sum method 
 *		 Integer i = (Integer) pa.test();
 * 	     Assert.assertTrue( i == 3);	
 * 
 * @author hajo
 *
 */
public class PrivateAccessor {

	private Class<?>[] paramTypes;
	private Object[] params;
	private Object object;
	private String methodName;

	public PrivateAccessor(Object object, String methodName,
			Class<?>... paramTypes) {
		this.object = object;
		this.methodName = methodName;
		this.paramTypes = paramTypes;
	}

	public void setParams(Object... params) {
		this.params = params;
	}

	public Object test() {
		Object result = null;
		try {
			Method method = object.getClass().getDeclaredMethod(methodName,
					paramTypes);
			method.setAccessible(true); // So much for encapsulation...
			result = method.invoke(object, params);
			method.setAccessible(false);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
}
