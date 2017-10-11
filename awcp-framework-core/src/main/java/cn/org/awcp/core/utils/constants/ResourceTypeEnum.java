package cn.org.awcp.core.utils.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * 资源类型枚举
 * @author ljw
 *
 */
public enum ResourceTypeEnum {
	/**
	 * 链接
	 */
	MENU_LINK("1","menuAddress"),
	
	/**
	 * 动态表单
	 */
	MENU_DYNAMICPAGE("2","dynamicpage"),
	
	/**
	 * 按钮：3
	 */
	RESO_BUTTON("3","按钮")
	;
		
	private String key = "";
	private String value = "";
	
	private ResourceTypeEnum(String key , String value){
		this.key = key;
		this.value = value;
	}
	
	public String getkey() {
		return key;
	}
	
	public String getvalue() {
		return value;
	}
	
	/**
	 * 根据key获取枚举
	 * @param key
	 * @return
	 */
	public static ResourceTypeEnum getResourceType(String key){
		for (ResourceTypeEnum operChartType : values()) {
			if(StringUtils.equals(operChartType.getkey(), key)){
				return operChartType;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getResourceTypeValue(String key) {
		for (ResourceTypeEnum menutype : values()) {
			if(StringUtils.equals(menutype.getkey(), key)){
				return menutype.getvalue();
			}
		}
		return null;
	}
}
