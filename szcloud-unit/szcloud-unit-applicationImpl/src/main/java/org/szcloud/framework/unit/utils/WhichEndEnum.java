package org.szcloud.framework.unit.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户端枚举
 * 
 * @author allen
 *
 */
public enum WhichEndEnum {
	/**
	 * 超级用户端
	 */
	SUPER_USER_END("1", "超级用户端"),

	/**
	 * 开发端
	 */
	DEVELOP_END("2", "开发端"),

	/**
	 * 前端
	 */
	FRONT_END("3", "前端");

	private String code = "";
	private String name = "";

	private WhichEndEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static WhichEndEnum getOperChartType(String code) {
		for (WhichEndEnum operChartType : values()) {
			if (StringUtils.equals(operChartType.getCode(), code)) {
				return operChartType;
			}
		}
		return null;
	}
}
