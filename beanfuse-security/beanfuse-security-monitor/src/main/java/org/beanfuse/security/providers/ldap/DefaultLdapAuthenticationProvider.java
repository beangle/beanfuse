//$Id: LdapMoniteeServiceImpl.java May 16, 2008 1:41:37 PM chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      May 16, 2008  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.providers.ldap;

import java.security.NoSuchAlgorithmException;

import org.beanfuse.security.Authentication;
import org.beanfuse.security.AuthenticationException;
import org.beanfuse.security.codec.PasswordHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.EntryNotFoundException;
import org.springframework.ldap.LdapTemplate;
import org.springframework.ldap.support.DirContextAdapter;
import org.springframework.ldap.support.DistinguishedName;

/**
 * 读取ldap的用户信息<br>
 * 
 * @author chaostone
 * 
 */
public class DefaultLdapAuthenticationProvider extends AbstractLdapAuthenticationProvider {
	protected static Logger logger = LoggerFactory.getLogger(DefaultLdapAuthenticationProvider.class);
	protected LdapTemplate ldapTemplate;

	protected String nameAttrName = "uid";

	protected String passwordAttrName = "userPassword";

	protected boolean doVerify(Authentication auth) {
		String userName = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();
		DistinguishedName dn = new DistinguishedName(nameAttrName + "=" + userName);
		String ldapPassword = null;
		try {
			ldapPassword = (String) ldapTemplate.lookup(dn, new String[] { "userPassword" },
					new ContextMapper() {
						public Object mapFromContext(Object ctx) {
							DirContextAdapter context = (DirContextAdapter) ctx;
							return new String((byte[]) context.getObjectAttribute("userPassword"));
						}
					});
		} catch (EntryNotFoundException e) {
			logger.info("cannot found {} in ldap", userName);
			throw new AuthenticationException("ldap user " + userName + " not found");
		}
		try {
			return (PasswordHandler.getInstance().verify(ldapPassword, password));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String getNameAttrName() {
		return nameAttrName;
	}

	public void setNameAttrName(String nameAttrName) {
		this.nameAttrName = nameAttrName;
	}

	public String getPasswordAttrName() {
		return passwordAttrName;
	}

	public void setPasswordAttrName(String passwordAttrName) {
		this.passwordAttrName = passwordAttrName;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

}
