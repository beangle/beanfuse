//$Id: SessionRecordPersister.java,v 1.1 2007-7-21 下午03:10:24 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-7-21         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.online;

/**
 * 登录持久化接口
 * 
 * @author chaostone
 * 
 */
public interface OnlineActivityService {

	/**
	 * 保存会话记录
	 * 
	 * @param record
	 */
	public void save(OnlineActivity info);
}
