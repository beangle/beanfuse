//$Id: ZipUtils.java,v 1.1 2007-12-19 下午08:21:53 Administrator Exp $
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
 * huanghaijun              2007-12-19         Created
 *  
 ********************************************************************************/

package org.beanfuse.archiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipException;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public final class ZipUtils {
	/**
	 * Don't let anyone instantiate this class.
	 */
	private ZipUtils() {
	}

	public static List unzip(final File zipFile, final String destination) {
		List fileNames = new ArrayList();
		String dest = destination;
		if (!destination.endsWith(File.separator)) {
			dest = destination + File.separator;
		}

		ZipFile zf = null;
		try {
			zf = new ZipFile(zipFile);
			Enumeration enu = zf.getEntries();
			while (enu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) enu.nextElement();
				String name = entry.getName();
				String path = dest + name;
				File file = new File(path);
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					InputStream is = zf.getInputStream(entry);
					byte[] buf1 = new byte[1024];
					int len;
					if (!file.exists()) {
						file.getParentFile().mkdirs();
						file.createNewFile();
					}
					OutputStream out = new FileOutputStream(file);
					while ((len = is.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					out.flush();
					out.close();
					is.close();
					fileNames.add(file.getAbsolutePath());
				}
			}
			zf.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != zf) {
				try {
					zf.close();
				} catch (IOException e) {
				}
			}
		}
		return fileNames;
	}

	// 文件压缩
	public static File zip(List fileNames, String zipPath, String encoding) {
		try {
			FileOutputStream f = new FileOutputStream(zipPath);
			ZipOutputStream out = new ZipOutputStream(new DataOutputStream(f));
			out.setEncoding(encoding);
			for (int i = 0; i < fileNames.size(); i++) {
				DataInputStream in = new DataInputStream(new FileInputStream(
						fileNames.get(i).toString()));
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(StringUtils
						.substringAfterLast(fileNames.get(i).toString(),
								File.separator)));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
			}
			out.close();
			return new File(zipPath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isZipFile(File zipFile) {
		try {
			ZipFile zf = new ZipFile(zipFile);
			Enumeration a = zf.getEntries();
			boolean isZip = a.hasMoreElements();
			zf.close();
			return isZip;
		} catch (ZipException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
