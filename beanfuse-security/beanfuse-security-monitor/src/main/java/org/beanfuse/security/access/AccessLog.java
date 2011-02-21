package org.beanfuse.security.access;

import java.util.Date;

public interface AccessLog {
	public long getBeginAt();

	public void setBeginAt(long start);

	public long getEndAt();

	public void setEndAt(long end);

	public long getDuration();

	public Date getBeignTime();

	public Date getEndTime();
}
