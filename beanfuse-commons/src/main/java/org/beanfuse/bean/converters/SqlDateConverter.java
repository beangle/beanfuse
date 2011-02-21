package org.beanfuse.bean.converters;

import java.sql.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

/**
 * This class is converts a java.util.Date to a String and a String to a
 * java.util.Date.
 */
public class SqlDateConverter implements Converter {

	public Object convert(final Class type, final Object value) {
		if (value == null) {
			return null;
		} else if (type == Date.class) {
			return convertToDate(value);
		} else if (type == String.class) {
			return convertToString(value);
		}

		throw new ConversionException("Could not convert "
				+ value.getClass().getName() + " to " + type.getName());
	}

	/**
	 * 将日期字符串转换成日期<br>
	 * format 1: yyyy-MM-dd<br>
	 * format 2: yyyyMMdd
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	protected Object convertToDate(final Object value) {
		if (StringUtils.isEmpty((String) value)) {
			return null;
		} else {
			String dateStr = (String) value;
			if (!StringUtils.contains(dateStr, "-")) {
				StringBuilder dateBuf = new StringBuilder(dateStr);
				dateBuf.insert("yyyyMM".length(), '-');
				dateBuf.insert("yyyy".length(), '-');
				dateStr = dateBuf.toString();
			}
			return Date.valueOf(dateStr);
		}
	}

	protected Object convertToString(Object value) {
		return value.toString();
	}
}
