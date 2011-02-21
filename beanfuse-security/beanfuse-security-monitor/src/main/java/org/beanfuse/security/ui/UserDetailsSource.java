package org.beanfuse.security.ui;

import org.beanfuse.security.UserDetails;

public interface UserDetailsSource {

	UserDetails buildDetails(Object context);
}
