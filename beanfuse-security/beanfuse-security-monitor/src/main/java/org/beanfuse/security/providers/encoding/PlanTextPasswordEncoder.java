package org.beanfuse.security.providers.encoding;

public class PlanTextPasswordEncoder implements PasswordEncoder {

	public boolean isPasswordValid(String encPass, String rawPass) {
		return encPass.equals(rawPass);
	}

}
