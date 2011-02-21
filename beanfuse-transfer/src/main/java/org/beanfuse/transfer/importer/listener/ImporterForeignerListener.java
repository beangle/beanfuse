//$Id: ForeignerListener.java,v 1.1 2007-3-17 下午03:06:05 chaostone Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
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
 *chaostone      2007-3-17         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.importer.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.entity.Model;
import org.beanfuse.model.Entity;
import org.beanfuse.model.EntityUtils;
import org.beanfuse.persist.EntityDao;
import org.beanfuse.persist.EntityService;
import org.beanfuse.transfer.TransferResult;
import org.beanfuse.transfer.importer.MultiEntityImporter;

/**
 * 导入数据外键监听器<br>
 * 这里尽量使用entityDao，因为在使用entityService加载其他代码时，hibernate会保存还未修改外的"半成对象"<br>
 * 从而造成有些外键是空对象的错误<br>
 * 如果外键不存在，则目标中的外键会置成null；<br>
 * 如果外键是空的，那么目标的外键取决于importer.isIgnoreNull取值
 * 
 * @author chaostone
 * 
 */
public class ImporterForeignerListener extends ItemImporterListener {

	protected EntityDao entityDao;

	protected Map foreigersMap = new HashMap();

	private static final int CACHE_SIZE = 500;

	private String[] foreigerKeys = { "code" };

	private boolean multiEntity = false;

	public ImporterForeignerListener(EntityService entityService) {
		if (null != entityService) {
			entityDao = entityService.getEntityDao();
		}
	}

	public ImporterForeignerListener(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public void startTransfer(TransferResult tr) {
		if (importer.getClass().equals(MultiEntityImporter.class)) {
			multiEntity = true;
		}
		super.startTransfer(tr);
	}

	public void endTransferItem(TransferResult tr) {
		// 过滤所有外键
		for (int i = 0; i < importer.getAttrs().length; i++) { // getAttrs()得到属性,即表的第二行
			String attr = importer.getAttrs()[i];
			
			String processed = importer.processAttr(attr);
			int foreigerKeyIndex = 0;
			boolean isforeiger = false;
			for (; foreigerKeyIndex < foreigerKeys.length; foreigerKeyIndex++) {
				if (processed.endsWith("." + foreigerKeys[foreigerKeyIndex])) {// ?
					isforeiger = true;
					break;
				}
			}
			if (!isforeiger)
				continue;

			String codeValue = (String) importer.getCurData().get(attr);
			try {
				Object foreiger = null;
				// 外键的代码是空的
				if (StringUtils.isEmpty(codeValue))
					continue;
				Object entity = null;
				if (multiEntity) {
					entity = ((MultiEntityImporter) importer).getCurrent(attr);
				} else {
					entity = importer.getCurrent();
				}
				
				attr = importer.processAttr(attr);
				Object nestedForeigner = PropertyUtils.getProperty(entity, StringUtils.substring(
						attr, 0, attr.lastIndexOf(".")));

				if (nestedForeigner instanceof Entity) {
					String className = EntityUtils.getEntityClassName(nestedForeigner.getClass());
					Map foreignerMap = (Map) foreigersMap.get(className);
					if (null == foreignerMap) {
						foreignerMap = new HashMap();
						foreigersMap.put(className, foreignerMap);
					}
					if (foreignerMap.size() > CACHE_SIZE)
						foreignerMap.clear();
					foreiger = foreignerMap.get(codeValue);
					if (foreiger == null) {
						List foreigners = entityDao.load(Class.forName(className),
								foreigerKeys[foreigerKeyIndex], new Object[] { codeValue });
						if (!foreigners.isEmpty()) {
							foreiger = foreigners.iterator().next();
							foreignerMap.put(codeValue, foreiger);
						} else {
							tr.addFailure("error.model.notExist", codeValue);
						}
					}
				}
				String parentAttr = StringUtils.substring(attr, 0, attr.lastIndexOf("."));
				Model.getPopulator().populateValue(parentAttr, foreiger, entity);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void addForeigerKey(String key) {
		String[] foreigers = new String[foreigerKeys.length + 1];
		int i = 0;
		for (; i < foreigerKeys.length; i++) {
			foreigers[i] = foreigerKeys[i];
		}
		foreigers[i] = key;
		foreigerKeys = foreigers;
	}
}
