package org.beanfuse.webapp.doc.action;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.beanfuse.struts2.action.BaseAction;
import org.beanfuse.utils.web.DownloadHelper;
import org.beanfuse.webapp.doc.service.StaticDocLoader;

/**
 * 
 * 静态资源下载
 * 
 * @author chaostone
 * 
 */
public class StaticDownloadAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private List<StaticDocLoader> loaders = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	public String index() throws Exception {
		String name = get("file");
		String displayName = get("display");
		URL url = null;
		if (StringUtils.isNotEmpty(name)) {
			for (StaticDocLoader loader : loaders) {
				url = loader.getFile(name);
				if (null != url) {
					break;
				}
			}
			if (null != url) {
				DownloadHelper.download(request, response, url, displayName);
			}
		}
		if (null == url) {
			return "nofound";
		} else {
			return null;
		}
	}

	public void setServletResponse(HttpServletResponse resp) {
		this.response = resp;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	public List<StaticDocLoader> getLoaders() {
		return loaders;
	}

	public void setLoaders(List<StaticDocLoader> loaders) {
		this.loaders = loaders;
	}

}
