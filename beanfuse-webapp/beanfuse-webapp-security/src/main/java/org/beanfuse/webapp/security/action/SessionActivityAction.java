package org.beanfuse.webapp.security.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.Order;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.online.SessionActivity;

/**
 * 用户登陆退出的会话管理
 * 
 * @author chaostone
 * 
 */
public class SessionActivityAction extends SecurityAction {

	protected void indexSetting() {
		put("categories", entityService.loadAll(UserCategory.class));
	}

	/**
	 * 显示用户某时间段的登陆记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String search() {
		EntityQuery query = new EntityQuery(SessionActivity.class, "sessionActivity");
		populateConditions(query);
		addTimeCondition(query);
		query.setLimit(getPageLimit());
		query.addOrder(Order.parse(get("orderBy")));
		put("sessionActivitys", entityService.search(query));
		return forward();
	}

	private void addTimeCondition(EntityQuery query) {
		String stime = get("startTime");
		String etime = get("endTime");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date sdate = null, edate = null;
		if (StringUtils.isNotBlank(stime)) {
			try {
				sdate = df.parse(stime);
				// 截至日期增加一天
				if (StringUtils.isNotBlank(etime)) {
					edate = df.parse(etime);
					Calendar gc = new GregorianCalendar();
					gc.setTime(edate);
					gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) + 1);
					edate = gc.getTime();
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

		if (null != sdate && null == edate) {
			query.add(new Condition("sessionActivity.loginAt >=:sdate", sdate));
		} else if (null != sdate && null != edate) {
			query
					.add(new Condition(
							"sessionActivity.loginAt >=:sdate and sessionActivity.loginAt <:edate",
							sdate, edate));
		} else if (null == sdate && null != edate) {
			query.add(new Condition("sessionActivity.loginAt <:edate", edate));
		}
	}

	/**
	 * 显示用户组某时间段的登陆记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String loginCountStat() {
		EntityQuery query = new EntityQuery(SessionActivity.class, "sessionActivity");
		query.setSelect("sessionActivity.name,sessionActivity.fullname,count(sessionActivity.name)");
		populateConditions(query);
		String groupName = get("groupName");
		addTimeCondition(query);
		if (StringUtils.isNotEmpty(groupName)) {
			query.add(new Condition("exists( from User u  join u.groups as group "
					+ "where u.name=sessionActivity.name and group.name like :groupName )", "%"
					+ groupName + "%"));
		}
		query.groupBy("sessionActivity.name,sessionActivity.fullname");
		query.setLimit(getPageLimit());
		query.addOrder(Order.parse(get("orderBy")));
		put("loginCountStats", entityService.search(query));
		return forward();
	}

	/**
	 * 显示登陆次数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String timeIntervalStat() {
		EntityQuery query = new EntityQuery(SessionActivity.class, "sessionActivity");
		addTimeCondition(query);
		query.setSelect("hour(sessionActivity.loginAt),count(*)");
		query.groupBy("hour(sessionActivity.loginAt)");
		List datas = (List) entityService.search(query);
		put("logonStats", datas);
		return forward();
	}
}
