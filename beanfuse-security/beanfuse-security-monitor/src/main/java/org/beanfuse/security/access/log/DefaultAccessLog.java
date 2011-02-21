package org.beanfuse.security.access.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.beanfuse.security.access.AccessLog;

public class DefaultAccessLog implements AccessLog {
	String uri;
	String params;
	long beginAt;
	long endAt;

	public String toString() {
		StringBuilder sb=new StringBuilder(uri);
		if(null!=params){
			sb.append('?').append(params);
		}
		sb.append('(');
		DateFormat f= new SimpleDateFormat("HH:mm:ss");
		sb.append(f.format(new Date(beginAt)));
		sb.append('-');
		if(0!=endAt){
			sb.append(f.format(new Date(endAt)));
			sb.append(" duration ").append((endAt-beginAt)/1000).append(" s");
		}else{
			sb.append(" not ended");
		}
		sb.append(')');
		return sb.toString();
	}

	public long getDuration() {
		if (0 == endAt) {
			return System.currentTimeMillis() - beginAt;
		} else {
			return endAt - beginAt;
		}
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(long beginAt) {
		this.beginAt = beginAt;
	}

	public long getEndAt() {
		return endAt;
	}

	public void setEndAt(long endAt) {
		this.endAt = endAt;
	}

	public Date getBeignTime() {
		if (0 != beginAt) {
			return new Date(beginAt);
		} else
			return null;
	}

	public Date getEndTime() {
		if (0 != endAt) {
			return new Date(endAt);
		} else
			return null;
	}

}