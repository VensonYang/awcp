package cn.org.awcp.formdesigner.core.utils;

import org.apache.commons.lang3.StringUtils;

public class HTMLUtils {
	/**
	 * 插入键值对
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static StringBuilder addProperty(String propertyName, String value){
		return new StringBuilder(" ").append(propertyName).append("=\"").append(value).append("\" ");
	}
	
	/**
	 * 插入键值对
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static StringBuilder addProperty(String propertyName, String value, boolean ingoreBlankValue){
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isBlank(value) && ingoreBlankValue)
			return sb;
		sb.append(propertyName).append("=\"").append(value).append("\" ");
		return sb;
	}
	
	/**
	 * 插入键值对
	 * @param propertyName
	 * @param value
	 * @param defValue:默认值
	 * @return
	 */
	public static StringBuilder addProperty(String propertyName, String value, String defValue){
		String val = value;
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isBlank(value)){
			 val = defValue;
		}
		sb.append(propertyName).append("=\"").append(val).append("\" ");
		return sb;
	}
	
	/**
	 * 插入属性值值占位符
	 * @param itemCode
	 * @return
	 */
	public static StringBuilder addPropertyValuePlaceHolder(String itemCode){
		return new StringBuilder(" ").append("value").append("=\"${").append(itemCode).append("}\" ");
	}
	

	/**
	 * 插入值值占位符
	 * @param itemCode
	 * @return
	 */
	public static StringBuilder addValuePlaceHolder(String itemCode){
		return new StringBuilder(" ").append("${").append(itemCode).append("} ");
	}
	
	public static StringBuilder addHiddenClass(StringBuilder sb, String keyString){
		return null;
	}
}
