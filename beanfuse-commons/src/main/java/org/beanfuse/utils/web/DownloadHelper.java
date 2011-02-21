//$Id: DownloadUtil.java,v 1.1 2007-12-13 下午05:33:55 Administrator Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * huanghaijun          2007-12-13         Created
 *  
 ********************************************************************************/

package org.beanfuse.utils.web;

import static org.beanfuse.utils.web.RequestUtils.encodeAttachName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadHelper {

	private static Logger logger = LoggerFactory.getLogger(DownloadHelper.class);

	public static final Properties contentTypes = new Properties();

	static {
		InputStream im = DownloadHelper.class.getResourceAsStream("/content_types.properties");
		try {
			contentTypes.load(im);
			logger.info("content types loaded ");
		} catch (IOException e) {
			logger.error("load /content_type.properties error", e);
		}
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, File file) {
		download(request, response, file, file.getName());
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, URL url,
			String display) {
		try {
			download(request, response, url.openStream(), url.getFile(), display);
		} catch (Exception e) {
			logger.warn("download file error=" + display, e);
		}
	}

	public static void download(HttpServletRequest request, HttpServletResponse response,
			File file, String display) {
		try {
			download(request, response, new FileInputStream(file), file.getAbsolutePath(), display);
		} catch (Exception e) {
			logger.warn("download file error=" + display, e);
		}
	}

	public static void download(HttpServletRequest request, HttpServletResponse response,
			InputStream inStream, String name, String display) {
		String attch_name = "";
		byte[] b = new byte[1024];
		int len = 0;
		try {
			String ext = StringUtils.substringAfterLast(name, ".");
			if (StringUtils.isBlank(display)) {
				attch_name = getAttachName(name);
			} else {
				attch_name = display;
				if (!attch_name.endsWith("." + ext)) {
					attch_name += "." + ext;
				}
			}
			response.reset();
			String contentType = response.getContentType();
			if (null == contentType) {
				if (StringUtils.isEmpty(ext)) {
					contentType = "application/x-msdownload";
				} else {
					contentType = contentTypes.getProperty(ext, "application/x-msdownload");
				}
				response.setContentType(contentType);
				logger.debug("set content type {} for {}", contentType, attch_name);
			}
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ encodeAttachName(request, attch_name) + "\"");
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			inStream.close();
		} catch (Exception e) {
			logger.warn("download file error=" + attch_name, e);
		}
	}

	/**
	 * 根据路径得到真实的文件名.
	 * 
	 * @param file_name
	 * @return
	 */
	public static String getAttachName(String file_name) {
		if (file_name == null)
			return "";
		file_name = file_name.trim();
		int iPos = 0;
		iPos = file_name.lastIndexOf("\\");
		if (iPos > -1) {
			file_name = file_name.substring(iPos + 1);
		}
		iPos = file_name.lastIndexOf("/");
		if (iPos > -1) {
			file_name = file_name.substring(iPos + 1);
		}
		iPos = file_name.lastIndexOf(File.separator);
		if (iPos > -1) {
			file_name = file_name.substring(iPos + 1);
		}
		return file_name;
	}
}
