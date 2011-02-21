package org.beanfuse.webapp.doc.service;

import java.net.URL;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

public class ClasspathDocLoader implements StaticDocLoader {

	public URL getFile(String filename) {
		return ClassLoaderUtil.getResource(filename, getClass());
	}

}
