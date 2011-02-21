//$Id: AbstractExporter.java,v 1.1 2007-3-24 下午08:51:38 chaostone Exp $
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

package org.beanfuse.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.TransferListener;
import org.beanfuse.transfer.TransferResult;
import org.beanfuse.transfer.exporter.writer.ItemWriter;
import org.beanfuse.transfer.exporter.writer.Writer;

public class ItemExporter implements Exporter {
	/** Commons Logging instance. */
	protected static Logger logger = LoggerFactory.getLogger(ItemExporter.class);

	/** 数据读取对象 */
	protected ItemWriter writer;

	/** 转换结果 */
	protected TransferResult transferResult;

	/** 监听器列表 */
	protected Vector listeners = new Vector();

	/** 成功记录数 */
	protected int success = 0;

	/** 失败记录数 */
	protected int fail = 0;

	/** 下一个要输出的位置 */
	protected int index = -1;

	/** 导入属性对应的标题 */
	protected String[] titles;

	/** 转换的数据集 */
	protected Collection datas;

	protected Iterator iter;

	private Object current;

	/** 转换上下文 */
	protected Context context;

	public Transfer addListener(TransferListener listener) {
		listeners.add(listener);
		listener.setTransfer(this);
		return this;
	}

	public Object getCurrent() {
		return current;
	}

	public int getFail() {
		return fail;
	}

	public Locale getLocale() {
		return Locale.getDefault();
	}

	public int getSuccess() {
		return success;
	}

	public int getTranferIndex() {
		return index;
	}

	public void transfer(TransferResult tr) {
		if (null == titles || titles.length == 0)
			return;

		this.transferResult = tr;
		tr.setTransfer(this);
		beforeExport();
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			TransferListener listener = (TransferListener) iter.next();
			listener.startTransfer(tr);
		}
		while (hasNext()) {
			next();
			int errors = tr.errors();
			// 实体转换开始
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				TransferListener listener = (TransferListener) iter.next();
				listener.startTransferItem(tr);
			}
			long transferItemStart = System.currentTimeMillis();
			// 进行转换
			transferItem();
			// 如果导出过程中没有错误，将成功记录数增一
			if (tr.errors() == errors) {
				this.success++;
			} else {
				this.fail++;
			}
			logger.debug("tranfer item:{}  take time:{}", String.valueOf(getTranferIndex()), String
					.valueOf(System.currentTimeMillis() - transferItemStart));
			// 实体转换结束
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				TransferListener listener = (TransferListener) iter.next();
				listener.endTransferItem(tr);
			}
		}
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			TransferListener listener = (TransferListener) iter.next();
			listener.endTransfer(tr);
		}
		// 告诉输出者,输出完成
		writer.close();
	}

	protected void beforeExport() {
		writer.writeTitle(titles);
	}

	public void transferItem() {
		if (null == getCurrent())
			return;
		writer.write(getCurrent());
	}

	protected void next() {
		index++;
		current = iter.next();
	}

	public boolean hasNext() {
		return iter.hasNext();
	}

	public String getFormat() {
		return writer.getFormat();
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public void setContext(Context context) {
		Collection items = (Collection) context.getDatas().get("items");
		if (null != items) {
			datas = items;
			iter = datas.iterator();
		}
		this.context = context;
	}

	public String getDataName() {
		return null;
	}

	public void setWriter(Writer writer) {
		if (writer instanceof ItemWriter) {
			this.writer = (ItemWriter) writer;
		}
	}

}
