package org.beanfuse.webapp.avatar.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AvatarUtil {
	private static final Logger logger = LoggerFactory.getLogger(AvatarUtil.class);

	private AvatarUtil() {
		super();
	}

	public static void copyTo(File file, HttpServletResponse response) throws IOException {
		if (null == file) {
			return;
		}
		byte[] oBuff = new byte[1024];
		int iSize = 0;
		String ext = StringUtils.substringAfterLast(file.getAbsolutePath(), ".");
		response.setContentType("image/" + ext);
		InputStream inStream = null;
		OutputStream output = null;
		try {
			inStream = new FileInputStream(file);
			output = response.getOutputStream();
			while ((iSize = inStream.read(oBuff)) > 0) {
				output.write(oBuff, 0, iSize);
			}
		} catch (Exception e) {
			logger.error("copy input to output error", e);
		} finally {
			if (null != inStream) {
				inStream.close();
			}
			if (null != output) {
				output.close();
			}
		}
	}
}
