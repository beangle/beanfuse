package org.beanfuse.webapp.doc.service;

import java.net.URL;

/**
 * 静态文件加载器
 * @author chaostone
 *
 */
public interface StaticDocLoader {

	public URL getFile(String filename);
}
