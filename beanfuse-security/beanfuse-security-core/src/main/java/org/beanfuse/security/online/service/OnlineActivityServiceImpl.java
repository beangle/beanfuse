package org.beanfuse.security.online.service;

import java.sql.Timestamp;

import org.beanfuse.entity.Model;
import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.security.online.OnlineActivity;
import org.beanfuse.security.online.OnlineActivityService;
import org.beanfuse.security.online.SessionActivity;

public class OnlineActivityServiceImpl extends BaseServiceImpl implements OnlineActivityService {

	public void save(OnlineActivity info) {
		SessionActivity record = (SessionActivity) Model.newInstance(SessionActivity.class);
		record.setSessionid(info.getSessionid());
		record.setName((String) info.getPrincipal());
		record.setFullname(info.getFullname());
		record.setCategory(info.getCategory());
		record.setLoginAt(info.getLoginAt());
		record.setHost(info.getHost());
		record.setLastAccessAt(info.getLastAccessAt());
		record.setLogoutAt(new Timestamp(System.currentTimeMillis()));
		record.setRemark(info.getRemark());
		record.calcOnlineTime();
		entityDao.saveOrUpdate(record);
	}

}
