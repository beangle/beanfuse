package org.beanfuse.security;

public interface Authentication {

	public static String NAME = "name";

	public static String PASSWORD = "password";

	public static String USERID = "security.userId";

	public static String LOGINNAME = "security.loginName";

	public static String FULLNAME = "security.fullName";

	public static String USER_CATEGORYID = "security.categoryId";

	public static String ERROR_PASSWORD = "error.wrongPassword";

	public static String ERROR_NOTEXIST = "error.userNotExist";

	public static String ERROR_UNACTIVE = "error.userUnactive";

	public static String ERROR_OVERMAX = "error.overmax";

	public Object getPrincipal();

	public Object setPrincipal(Object principal);

	public Object getCredentials();

	public Object getDetails();

	public void setDetails(UserDetails userDetails);

}
