package org.beanfuse.struts2.view.freemarker;

import java.net.URL;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

import freemarker.cache.URLTemplateLoader;

/**
 * 搜索带有固定前缀下的资源，排除/org/和/com/
 * 
 * @author chaostone
 * 
 */
public class PrefixClassTemplateLoader extends URLTemplateLoader {

	String prefix = null;

	public PrefixClassTemplateLoader(String prefix) {
		super();
		setPrefix(prefix);
	}

	protected URL getURL(String name) {
		return ClassLoaderUtil.getResource(prefix + name, getClass());
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String pre) {
		this.prefix = pre;
		if (null != prefix) {
			if (prefix.equals("/")) {
				prefix = null;
			} else {
				if (!prefix.endsWith("/")) {
					prefix += "/";
				}
				if (prefix.startsWith("/")) {
					prefix = prefix.substring(1);
				}
			}
		}
	}
}
