//$Id: AbstractImporter.java,v 1.1 2007-3-24 下午12:46:57 chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-24         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.importer;

import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.TransferListener;
import org.beanfuse.transfer.TransferResult;
import org.beanfuse.transfer.importer.reader.Reader;

/**
 * 导入的抽象和缺省实现
 * 
 * @author chaostone
 * 
 */
public abstract class AbstractImporter implements Importer {
	/**
	 * Commons Logging instance.
	 */
	protected static Logger logger = LoggerFactory.getLogger(AbstractImporter.class);

	/**
	 * 数据读取对象
	 */
	protected Reader reader;

	/**
	 * 转换结果
	 */
	protected TransferResult transferResult;

	/**
	 * 监听器列表
	 */
	protected Vector listeners = new Vector();

	/**
	 * 成功记录数
	 */
	protected int success = 0;

	/**
	 * 失败记录数
	 */
	protected int fail = 0;

	/**
	 * 下一个要读取的位置
	 */
	protected int index = 0;

	/**
	 * 进行转换
	 */
	public void transfer(TransferResult tr) {
		this.transferResult = tr;
		this.transferResult.setTransfer(this);
		long transferStartAt = System.currentTimeMillis();
		try {
			beforeImport();
		} catch (Exception e) {
			// 预导入发生位置错误，错误信息已经记录在tr了
			return;
		}

		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			TransferListener listener = (TransferListener) iter.next();
			listener.startTransfer(tr);
		}
		while (read()) {
			long transferItemStart = System.currentTimeMillis();
			index++;
			beforeImportItem();
			if (!isDataValid())
				continue;
			int errors = tr.errors();
			// 实体转换开始
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				TransferListener listener = (TransferListener) iter.next();
				listener.startTransferItem(tr);
			}
			// 如果转换前已经存在错误,则不进行转换
			if (tr.errors() > errors) {
				continue;
			}
			// 进行转换
			transferItem();
			// 实体转换结束
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				TransferListener listener = (TransferListener) iter.next();
				listener.endTransferItem(tr);
			}
			// 如果导入过程中没有错误，将成功记录数增一
			if (tr.errors() == errors) {
				this.success++;
			} else {
				this.fail++;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("importer item:" + getTranferIndex() + " take time:"
						+ (System.currentTimeMillis() - transferItemStart));
			}
		}
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			TransferListener listener = (TransferListener) iter.next();
			listener.endTransfer(tr);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("importer elapse:" + (System.currentTimeMillis() - transferStartAt));
		}
	}

	public int getFail() {
		return fail;
	}

	public int getSuccess() {
		return success;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public boolean ignoreNull() {
		return true;
	}

	public Locale getLocale() {
		return Locale.getDefault();
	}

	public String getFormat() {
		return reader.getFormat();
	}

	public int getTranferIndex() {
		return index;
	}

	public Transfer addListener(TransferListener listener) {
		listeners.add(listener);
		listener.setTransfer(this);
		return this;
	}

}
