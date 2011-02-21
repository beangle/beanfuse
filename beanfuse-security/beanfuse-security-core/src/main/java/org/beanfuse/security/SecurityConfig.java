package org.beanfuse.security;

import java.util.HashMap;
import java.util.Map;

import org.beanfuse.model.pojo.LongIdObject;

public class SecurityConfig extends LongIdObject {

	public static final String USER_DEFAULT_PASSWORD = "user.default.password";

	public static final String USER_LOGIN_ENABLERANDOMCODE = "user.login.enableRandomcode";

	private Map properties = new HashMap();

}
