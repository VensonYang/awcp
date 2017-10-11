package cn.org.awcp.formdesigner.core.constants;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class FormDesignerGlobal {
	/**
	 * 组件类型
	 */
	public static Map<String, String> COMPONENT_TYPES = new HashMap<String, String>();
	
	/**
	 * 动态表单前台访问路径
	 */
	public static final String DOCUMENT_URL = "";
	/**
	 * 提示框 id 前缀
	 */
	public static final String PAGEACT_DIALOG_ID_PREFIX = "platform_artdialog_";
	
	static {
		//初始化数据字典中的组件类型
		COMPONENT_TYPES.put("1008","org.szcloud.framework.formdesigner.core.domain.design.context.component.grid.ColumnComponent");
		COMPONENT_TYPES.put("1006","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.SelectComponent");
		COMPONENT_TYPES.put("1001","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.InputTextComponent");
		COMPONENT_TYPES.put("1002","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.DateTimeComponent");
		COMPONENT_TYPES.put("1003","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.CheckBoxComponent");
		COMPONENT_TYPES.put("1004","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.RadioComponent");
		COMPONENT_TYPES.put("1005","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.TextAreaComponent");
		COMPONENT_TYPES.put("1007","org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.PasswordComponent");
		COMPONENT_TYPES.put("1009","org.szcloud.framework.formdesigner.core.domain.design.context.component.text.LabelComponent");
	}
	
	public static boolean isValueComponent(JSONObject o){
		int componentType = Integer.valueOf(o.getString("componentType"));
		if(componentType==1001 || componentType==1002 ||componentType==1003
				||componentType==1004 ||componentType==1005 || componentType==1006
				||componentType==1007||componentType==1012 || componentType==1010 
				|| componentType==1011 || componentType==1016 || componentType==1019 
				|| componentType==1020 || componentType==1029 || componentType==1030 || componentType==1031){
			return true;
		}
		return false;
	}
		
	public static boolean isValuedNoneContain(JSONObject o){
		int componentType = Integer.valueOf(o.getString("componentType"));
		if(componentType==1001 || componentType==1002 ||componentType==1003
				||componentType==1004 ||componentType==1005 ||componentType==1006
				||componentType==1007|| componentType==1010 || componentType==1011){
			return true;
		}
		return false;
	}
}
