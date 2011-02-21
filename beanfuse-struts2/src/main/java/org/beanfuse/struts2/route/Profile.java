package org.beanfuse.struts2.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路由调转配置
 * 
 * @author chaostone <br>
 *         /:controller:ext =>:method=index||get("method")
 *         /:controller/:method:ext
 */
public class Profile implements Comparable {
	private static final Logger logger = LoggerFactory.getLogger(Profile.class);
	// 配置名
	private String name;

	// action所在的包,匹配action的唯一条件
	private String packageName;

	private String[] packageSegs;

	// action前缀
	private String pattern = "";

	private String[] patternSegs = new String[0];

	// action类名后缀
	private String actionSuffix = "";

	// 路径前缀
	private String viewPath = "";

	// 路径后缀
	private String viewExtension = "";

	// 缺省的action中的方法
	private String defaultMethod = "index";

	private String uriStyle = "simple";

	private String pathStyle = "simple";

	/** URI的后缀 */
	private String uriExtension;

	// 匹配缓存[className,matchInfo]
	private Map<String, MatchInfo> cache = new HashMap();

	/**
	 * 得到控制器的起始位置
	 * 
	 * @param className
	 * @return
	 */
	public MatchInfo getCtlMatchInfo(final String className) {
		MatchInfo match = cache.get(className);
		if (null == match) {
			match = getMatchInfo(patternSegs, className);
			if (-1 != match.startIndex) {
				synchronized (cache) {
					cache.put(className, match);
				}
				logger.debug("get match info {}", match);
			}
		}
		return match;
	}

	/**
	 * 给定action是否符合该配置文件
	 * 
	 * @param className
	 * @return
	 */
	public boolean isMatch(final String className) {
		return -1 != getMatchInfo(packageSegs, className).startIndex;
	}

	public int matchedIndex(final String className) {
		return getMatchInfo(packageSegs, className).startIndex;
	}

	public MatchInfo getMatchInfo(final String[] pattens, final String className) {
		String sub = className;
		int index = 0;
		MatchInfo match = new MatchInfo(-1);
		for (int i = 0; i < pattens.length; i++) {
			int subIndex = sub.indexOf(pattens[i]);
			if (-1 == subIndex) {
				return match;
			}
			// 串接所有匹配项保留部分
			if (0 != subIndex) {
				if (match.reserved.length() > 0) {
					match.reserved.append('.');
				}
				match.reserved.append(sub.substring(0, subIndex));
			}
			index += (subIndex + pattens[i].length());
			if (i != pattens.length - 1) {
				sub = sub.substring(subIndex + pattens[i].length());
				if (StringUtils.isEmpty(sub)) {
					match.startIndex = className.length() - 1;
					return match;
				}
			}
		}

		match.startIndex = index - 1;
		return match;
	}

	/**
	 * 子包优先
	 */
	public int compareTo(Object object) {
		Profile myClass = (Profile) object;
		return new CompareToBuilder().append(myClass.packageName, this.packageName).toComparison();
	}

	public String getSimpleName(String className) {
		String postfix = getActionSuffix();
		String simpleName = className.substring(className.lastIndexOf('.') + 1);
		if (StringUtils.contains(simpleName, postfix)) {
			simpleName = StringUtils.uncapitalize(simpleName.substring(0, simpleName.length()
					- postfix.length()));
		} else {
			simpleName = StringUtils.uncapitalize(simpleName);
		}

		StringBuilder infix = new StringBuilder();
		infix.append(StringUtils.substringBeforeLast(className, "."));
		if (infix.length() == 0)
			return simpleName;
		infix.append('.');
		infix.append(simpleName);
		// 将.替换成/
		for (int i = 0; i < infix.length(); i++) {
			if (infix.charAt(i) == '.') {
				infix.setCharAt(i, '/');
			}
		}
		return infix.toString();
	}

	/**
	 * 将前后缀去除后，中间的.替换为/<br>
	 * 不以/开始。
	 * 
	 * @param clazz
	 * @param profile
	 * @return
	 */
	public String getInfix(String className) {
		String postfix = getActionSuffix();
		String simpleName = className.substring(className.lastIndexOf('.') + 1);
		if (StringUtils.contains(simpleName, postfix)) {
			simpleName = StringUtils.uncapitalize(simpleName.substring(0, simpleName.length()
					- postfix.length()));
		} else {
			simpleName = StringUtils.uncapitalize(simpleName);
		}

		MatchInfo match = getCtlMatchInfo(className);
		StringBuilder infix = new StringBuilder(match.getReserved().toString());
		if (infix.length() > 0) {
			infix.append('.');
		}
		String remainder = StringUtils.substring(StringUtils.substringBeforeLast(className, "."),
				match.getStartIndex() + 1);
		if (remainder.length() > 0) {
			infix.append(remainder).append('.');
		}
		if (infix.length() == 0)
			return simpleName;
		infix.append(simpleName);

		// 将.替换成/
		for (int i = 0; i < infix.length(); i++) {
			if (infix.charAt(i) == '.') {
				infix.setCharAt(i, '/');
			}
		}
		return infix.toString();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", this.name)
				.append("packageName", this.packageName).append("pattern", this.pattern).append(
						"actionSuffix", this.actionSuffix).append("viewPath", this.viewPath)
				.append("viewExtension", this.viewExtension).append("defaultMethod",
						this.defaultMethod).append("uriStyle", this.uriStyle).append("pathStyle",
						this.pathStyle).toString();
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		this.patternSegs = StringUtils.split(pattern, '*');
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String pagePath) {
		this.viewPath = pagePath;
	}

	public String getActionSuffix() {
		return actionSuffix;
	}

	public void setActionSuffix(String ctlPostfix) {
		this.actionSuffix = ctlPostfix;
	}

	public String getViewExtension() {
		return viewExtension;
	}

	public void setViewExtension(String pagePostfix) {
		this.viewExtension = pagePostfix;
	}

	public String getDefaultMethod() {
		return defaultMethod;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

	public String getUriStyle() {
		return uriStyle;
	}

	public void setUriStyle(String uriStyle) {
		this.uriStyle = uriStyle;
	}

	public String getPackageName() {
		return new String(packageName);
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
		this.packageSegs = StringUtils.split(packageName, '*');
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUriExtension() {
		return uriExtension;
	}

	public void setUriExtension(String uriExtension) {
		this.uriExtension = uriExtension;
	}

	public String getPathStyle() {
		return pathStyle;
	}

	public void setPathStyle(String pathStyle) {
		this.pathStyle = pathStyle;
	}

}
