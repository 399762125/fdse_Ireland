package cn.edu.fudan.software.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过反射机制获取servlet中的指定方法以供外部调用
 * 
 * @author zyf
 * 
 */
public class ServletMethodInvoke {
	private Object object; // 传入用于请求的servlet
	private String methodName; // 请求的servlet中的方法名

	/**
	 * 
	 * @param object
	 *            传入用于请求的servlet
	 * @param methodName
	 *            请求的servlet中的方法名
	 */
	public ServletMethodInvoke(Object object, String methodName) {
		this.object = object;
		this.methodName = methodName;
	}

	/**
	 * 调用指定的方法
	 * 
	 * @param request
	 * @param response
	 */
	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		try {
			Class servletClass = object.getClass();
			Method m = servletClass.getMethod(methodName, new Class[] {
					Class.forName("javax.servlet.http.HttpServletRequest"),
					Class.forName("javax.servlet.http.HttpServletResponse") });
			m.invoke(object, new Object[] { request, response });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

	}

	// ////////get set 方法///////////////
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
