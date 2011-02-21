package org.beanfuse.security.providers.encoding;

import org.beanfuse.security.codec.EncryptUtil;

public class DigestPasswordEncoder implements PasswordEncoder {

	private String algorithm = "MD5";

	public boolean isPasswordValid(String encPass, String rawPass) {
		return encPass.equals(EncryptUtil.encode(rawPass, algorithm));
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

}
