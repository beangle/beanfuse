//$Id:ModelDrivenAction.java 2009-1-20 下午06:43:56 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.action;

import static org.beanfuse.utils.web.RequestUtils.encodeAttachName;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.beanfuse.collection.Order;
import org.beanfuse.entity.Model;
import org.beanfuse.entity.types.EntityType;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.Entity;
import org.beanfuse.model.EntityUtils;
import org.beanfuse.model.LongIdTimeEntity;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.TransferListener;
import org.beanfuse.transfer.TransferResult;
import org.beanfuse.transfer.exporter.Context;
import org.beanfuse.transfer.exporter.DefaultEntityExporter;
import org.beanfuse.transfer.exporter.DefaultPropertyExtractor;
import org.beanfuse.transfer.exporter.Exporter;
import org.beanfuse.transfer.exporter.ItemExporter;
import org.beanfuse.transfer.exporter.PropertyExtractor;
import org.beanfuse.transfer.exporter.TemplateExporter;
import org.beanfuse.transfer.exporter.writer.ExcelItemWriter;
import org.beanfuse.transfer.exporter.writer.ExcelTemplateWriter;
import org.beanfuse.transfer.exporter.writer.TemplateWriter;
import org.beanfuse.transfer.importer.DefaultEntityImporter;
import org.beanfuse.transfer.importer.EntityImporter;
import org.beanfuse.transfer.importer.listener.ImporterForeignerListener;
import org.beanfuse.transfer.importer.reader.CSVReader;
import org.beanfuse.transfer.importer.reader.ExcelItemReader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ClassLoaderUtil;

public class EntityDrivenAction extends BaseAction {

	protected String entityName;

	/**
	 * 主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		indexSetting();
		return forward();
	}

	/**
	 * 查找标准
	 * 
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception {
		put(getShortName() + "s", search(buildQuery()));
		return forward();
	}

	protected Collection getExportDatas() {
		EntityQuery query = buildQuery();
		query.setLimit(null);
		return search(query);
	}

	/**
	 * 修改标准
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		Long entityId = getEntityId(getShortName());
		Entity entity = null;
		if (null == entityId) {
			entity = populateEntity();
		} else {
			entity = getModel(getEntityName(), entityId);
		}
		put(getShortName(), entity);
		editSetting(entity);
		return forward();
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception {
		Long entityId = getLong(getShortName() + "Id");
		Collection entities = null;
		if (null == entityId) {
			String entityIdSeq = get(getShortName() + "Ids");
			entities = getModels(getEntityName(), SeqStringUtil.transformToLong(entityIdSeq));
		} else {
			Entity entity = getModel(getEntityName(), entityId);
			entities = Collections.singletonList(entity);
		}
		return removeAndForward(entities);
	}

	/**
	 * 保存修改后的标准
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		return saveAndForward(populateEntity());
	}

	protected Entity populateEntity() {
		return populateEntity(getEntityName(), getShortName());
	}

	protected Long getEntityId(String shortName) {
		Long entityId = getLong("id");
		if (null == entityId) {
			entityId = getLong(shortName + ".id");
		}
		if (null == entityId) {
			entityId = getLong(shortName + "Id");
		}
		return entityId;
	}

	protected Entity populateEntity(String entityName, String shortName) {
		Long entityId = getEntityId(shortName);
		Entity entity = null;
		if (null == entityId) {
			entity = (Entity) populate(entityName, shortName);
		} else {
			Map params = getParams(shortName);
			entity = getModel(entityName, entityId);
			populate(params, entity, entityName);
		}
		return entity;
	}

	protected Entity populateEntity(Class entityClass, String shortName) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		return populateEntity(type.getEntityName(), shortName);
	}

	protected Entity getEntity() {
		return getEntity(getEntityName(), getShortName());
	}

	protected Entity getEntity(String entityName, String name) {
		Long entityId = getEntityId(name);
		Entity entity = null;
		try {
			EntityType type = Model.getEntityType(entityName);
			if (null == entityId) {
				entity = (Entity) populate(type.newInstance(), type.getEntityName(), name);
			} else {
				entity = getModel(entityName, entityId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return entity;
	}

	protected Entity getEntity(Class entityClass, String shortName) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		return getEntity(type.getEntityName(), shortName);
	}

	/**
	 * 查看信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String info() throws Exception {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		Entity entity = getModel(getEntityName(), entityId);
		put(getShortName(), entity);
		return forward();
	}

	protected void indexSetting() {

	}

	protected void editSetting(Entity entity) {

	}

	/**
	 * 保存对象
	 * 
	 * @param entity
	 * @return
	 */
	protected String saveAndForward(Entity entity) {
		try {
			if (entity instanceof LongIdTimeEntity) {
				LongIdTimeEntity timeEntity = (LongIdTimeEntity) entity;
				if (timeEntity.isVO()) {
					timeEntity.setCreatedAt(new Date());
				}
				timeEntity.setUpdatedAt(new Date());
			}
			saveOrUpdate(Collections.singletonList(entity));
			return redirect("search", "info.save.success");
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}

	}

	protected String removeAndForward(Collection entities) {
		try {
			remove(entities);
		} catch (Exception e) {
			logger.info("removeAndForwad failure", e);
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.delete.success");
	}

	protected EntityQuery buildQuery() {
		EntityQuery query = new EntityQuery(getEntityName(), getShortName());
		populateConditions(query);
		query.addOrder(Order.parse(get("orderBy")));
		query.setLimit(getPageLimit());
		return query;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	protected String getEntityName() {
		if (null == entityName) {
			logger.error("entity name not setted for {}", getClass().getName());
		}
		return entityName;
	}

	protected String getShortName() {
		if (StringUtils.isNotEmpty(getEntityName())) {
			return EntityUtils.getCommandName(getEntityName());
		}
		return null;
	}

	protected Entity getModel(String entityName, Serializable id) {
		return entityService.get(entityName, id);
	}

	protected List getModels(String entityName, Long[] ids) {
		return entityService.load(entityName, "id", ids);
	}

	protected List getModels(Class modelClass, Long[] ids) {
		return entityService.load(modelClass, "id", ids);
	}

	/**
	 * 数据输出
	 * 
	 * @param mapping
	 * @param request
	 * @param forwardTag
	 * @param detailObject
	 * @return
	 */
	public String export() throws Exception {
		// 查找导出参数
		String format = get("format");
		String fileName = get("fileName");
		String template = get("template");
		if (StringUtils.isBlank(format)) {
			format = Transfer.EXCEL;
		}
		if (StringUtils.isEmpty(fileName)) {
			fileName = "exportResult";
		}

		// 配置导出上下文
		Context context = new Context();
		context.put("format", format);
		context.put("exportFile", fileName);

		if (StringUtils.isNotBlank(template)) {
			URL templateURL = loadTemplate(template);
			if (null != templateURL) {
				context.getDatas().put(TemplateWriter.TEMPLATE_PATH, templateURL);
			}
		}
		configExportContext(context);

		// 构造合适的输出器
		Collection datas = (Collection) context.get("items");
		boolean isArray = false;
		if (!CollectionUtils.isEmpty(datas)) {
			Object first = datas.iterator().next();
			if (first.getClass().isArray()) {
				isArray = true;
			}
		}
		Exporter exporter;
		if (isArray) {
			exporter = new ItemExporter();
		} else {
			if (StringUtils.isNotBlank(template)) {
				exporter = new TemplateExporter();
			} else {
				exporter = new DefaultEntityExporter();
				((DefaultEntityExporter) exporter)
						.setAttrs(StringUtils.split(getExportKeys(), ","));
				((DefaultEntityExporter) exporter).setPropertyExtractor(getPropertyExtractor());
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		// 设置下载项信息
		if (exporter instanceof ItemExporter) {
			((ItemExporter) exporter).setTitles(StringUtils.split(getExportTitles(), ","));
			exporter.setWriter(new ExcelItemWriter(response.getOutputStream()));
		} else {
			exporter.setWriter(new ExcelTemplateWriter(response.getOutputStream()));
		}

		if (format.equals(Transfer.EXCEL)) {
			response.setContentType("application/vnd.ms-excel;charset=GBK");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ encodeAttachName(request, context.getDatas().get("exportFile").toString()
							+ ".xls"));
		} else {
			throw new RuntimeException("Exporter is not supported for other format:"
					+ exporter.getFormat());
		}
		// 进行输出
		exporter.setContext(context);
		exporter.transfer(new TransferResult());
		return null;
	}

	protected URL loadTemplate(String template) {
		URL url = ClassLoaderUtil.getResource(template, getClass());
		if (url == null) {
			logger.error("Cannot load template {}", template);
		}
		return url;
	}

	protected void configExportContext(Context context) {
		Collection datas = getExportDatas();
		context.getDatas().put("items", datas);
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new DefaultPropertyExtractor(new ActionTextResource(this));
	}

	protected String getExportKeys() {
		return get("keys");
	}

	protected String getExportTitles() {
		return get("titles");
	}

	public String importForm() {
		return forward("/components/importData/form");
	}

	/**
	 * 构建实体导入者
	 * 
	 * @return
	 */
	protected EntityImporter buildEntityImporter() {
		if (null == getEntityName()) {
			return buildEntityImporter("importFile", null);
		} else {
			return buildEntityImporter("importFile", Model.getEntityType(getEntityName())
					.getEntityClass());
		}
	}

	protected EntityImporter buildEntityImporter(Class clazz) {
		return buildEntityImporter("importFile", clazz);
	}

	/**
	 * 构建实体导入者
	 * 
	 * @param upload
	 * @param clazz
	 * @return
	 */
	protected EntityImporter buildEntityImporter(String upload, Class clazz) {
		try {
			File[] files = (File[]) ActionContext.getContext().getParameters().get(upload);
			if (files == null || files.length < 1) {
				logger.error("cannot get {} file.", upload);
			}
			String fileName = get(upload + "FileName");
			InputStream is = new FileInputStream(files[0]);
			if (fileName.endsWith(".xls")) {
				HSSFWorkbook wb = new HSSFWorkbook(is);
				if (wb.getNumberOfSheets() < 1 || wb.getSheetAt(0).getLastRowNum() == 0) {
					return null;
				}
				EntityImporter importer = (clazz == null) ? new DefaultEntityImporter()
						: new DefaultEntityImporter(clazz);
				importer.setReader(new ExcelItemReader(wb, 1));
				put("importer", importer);
				return importer;
			} else {
				LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
				if (null == reader.readLine())
					return null;
				reader.reset();
				EntityImporter importer = (clazz == null) ? new DefaultEntityImporter()
						: new DefaultEntityImporter(clazz);
				importer.setReader(new CSVReader(reader));
				return importer;
			}
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
	}

	/**
	 * 导入班级信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return @
	 */
	public String importData() {
		TransferResult tr = new TransferResult();
		EntityImporter importer = buildEntityImporter();
		if (null == importer) {
			return forward("/components/importData/error");
		}
		configImporter(importer);
		importer.transfer(tr);
		put("importResult", tr);
		if (tr.hasErrors()) {
			return forward("/components/importData/error");
		} else {
			return forward("/components/importData/result");
		}
	}

	protected void configImporter(EntityImporter importer) {
		for (Iterator iter = getImporterListeners().iterator(); iter.hasNext();) {
			TransferListener il = (TransferListener) iter.next();
			importer.addListener(il);
		}
	}

	protected List getImporterListeners() {
		return Collections.singletonList(new ImporterForeignerListener(entityService));
	}
}
