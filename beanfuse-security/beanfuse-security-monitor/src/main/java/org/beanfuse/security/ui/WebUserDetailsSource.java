package org.beanfuse.security.ui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class WebUserDetailsSource implements UserDetailsSource {

	private Class clazz = WebUserDetails.class;

	public UserDetails buildDetails(Object context) {
		Assert.isInstanceOf(HttpServletRequest.class, context);
		try {
			Constructor constructor = clazz
					.getConstructor(new Class[] { HttpServletRequest.class });
			return (UserDetails) constructor.newInstance(new Object[] { context });
		} catch (NoSuchMethodException ex) {
			ReflectionUtils.handleReflectionException(ex);
		} catch (InvocationTargetException ex) {
			ReflectionUtils.handleReflectionException(ex);
		} catch (InstantiationException ex) {
			ReflectionUtils.handleReflectionException(ex);
		} catch (IllegalAccessException ex) {
			ReflectionUtils.handleReflectionException(ex);
		}
		return null;
	}

	public void setClazz(Class clazz) {
		Assert.notNull(clazz, "Class required");
		this.clazz = clazz;
	}
}
