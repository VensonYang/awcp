package org.szcloud.framework.metadesigner.core.generator.util.typemapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述java类型和as类型对应关系
 * 
 * @author caoyong
 */
public class ActionScriptDataTypesUtils {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(ActionScriptDataTypesUtils.class);
	private final static Map<String, String> preferredAsTypeForJavaType = new HashMap<String, String>();

	public static String getPreferredAsType(String javaType) {
		String result = preferredAsTypeForJavaType.get(javaType);
		if (result == null) {
			result = javaType;
		}
		return result;
	}

	static {

		preferredAsTypeForJavaType.put("Short", "Number");
		preferredAsTypeForJavaType.put("java.lang.Short", "Number");
		preferredAsTypeForJavaType.put("short", "Number");

		preferredAsTypeForJavaType.put("Integer", "Number");
		preferredAsTypeForJavaType.put("java.lang.Integer", "Number");
		preferredAsTypeForJavaType.put("int", "Number");

		preferredAsTypeForJavaType.put("Long", "Number");
		preferredAsTypeForJavaType.put("java.lang.Long", "Number");
		preferredAsTypeForJavaType.put("long", "Number");

		preferredAsTypeForJavaType.put("Float", "Number");
		preferredAsTypeForJavaType.put("java.lang.Float", "Number");
		preferredAsTypeForJavaType.put("float", "Number");

		preferredAsTypeForJavaType.put("Double", "Number");
		preferredAsTypeForJavaType.put("java.lang.Double", "Number");
		preferredAsTypeForJavaType.put("double", "Number");

		preferredAsTypeForJavaType.put("Byte", "Number");
		preferredAsTypeForJavaType.put("java.lang.Byte", "Number");
		preferredAsTypeForJavaType.put("byte", "Number");

		preferredAsTypeForJavaType.put("java.math.BigDecimal", "Number");

		preferredAsTypeForJavaType.put("Boolean", "Boolean");
		preferredAsTypeForJavaType.put("java.lang.Boolean", "Boolean");
		preferredAsTypeForJavaType.put("boolen", "Boolean");

		preferredAsTypeForJavaType.put("char", "String");
		preferredAsTypeForJavaType.put("char[]", "String");
		preferredAsTypeForJavaType.put("java.lang.String", "String");
		preferredAsTypeForJavaType.put("java.sql.Clob", "String");

		preferredAsTypeForJavaType.put("byte[]", "flash.utils.ByteArray");
		preferredAsTypeForJavaType.put("java.sql.Blob", "flash.utils.ByteArray");
		preferredAsTypeForJavaType.put("java.sql.Array", "Array");
		preferredAsTypeForJavaType.put("java.lang.reflect.Array", "Array");
		preferredAsTypeForJavaType.put("java.util.Collection", "mx.collections.ArrayCollection");
		preferredAsTypeForJavaType.put("java.util.List", "mx.collections.ArrayCollection");
		preferredAsTypeForJavaType.put("java.util.ArrayList", "mx.collections.ArrayCollection");
		preferredAsTypeForJavaType.put("java.util.ArrayList", "mx.collections.ArrayCollection");

		preferredAsTypeForJavaType.put("java.util.Set", "Object");
		preferredAsTypeForJavaType.put("java.util.HashSet", "Object");
		preferredAsTypeForJavaType.put("java.util.Map", "Object");
		preferredAsTypeForJavaType.put("java.util.HashMap", "Object");

		preferredAsTypeForJavaType.put("java.sql.Date", "Date");
		preferredAsTypeForJavaType.put("java.sql.Time", "Date");
		preferredAsTypeForJavaType.put("java.util.Date", "Date");
		preferredAsTypeForJavaType.put("java.sql.Timestamp", "Date");

	}

	public static void main(String[] args) {
		String bb = ActionScriptDataTypesUtils.getPreferredAsType(List.class.getName());
		logger.debug(bb);
	}

}
