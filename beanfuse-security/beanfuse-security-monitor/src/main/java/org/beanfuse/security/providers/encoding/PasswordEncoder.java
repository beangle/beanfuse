package org.beanfuse.security.providers.encoding;

public interface PasswordEncoder {

	public boolean isPasswordValid(String encPass, String rawPass);

}
