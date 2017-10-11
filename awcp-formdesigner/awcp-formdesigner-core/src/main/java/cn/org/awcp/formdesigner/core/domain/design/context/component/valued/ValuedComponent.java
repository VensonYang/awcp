package cn.org.awcp.formdesigner.core.domain.design.context.component.valued;

import cn.org.awcp.formdesigner.core.domain.design.context.component.SimpleComponent;
import cn.org.awcp.formdesigner.core.domain.design.context.component.valued.validator.FormValidator;

public abstract class ValuedComponent extends SimpleComponent{
	private static final long serialVersionUID = 1L;
	/**
	 * 值类型
	 */
	protected String valueType;
	/**
	 * 值格式
	 */
	protected String formatString;
	/**
	 * 默认值的值脚本 
	 */
	protected String defaultValueScript;
	/**
	 * 校验
	 */
	protected FormValidator formValidator;
	
	public String getValueType() {
		return valueType;
	}
	
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public String getFormatString() {
		return formatString;
	}
	
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}
	
	public String getDefaultValueScript() {
		return defaultValueScript;
	}
	
	public void setDefaultValueScript(String defaultValueScript) {
		this.defaultValueScript = defaultValueScript;
	}
	
	public FormValidator getFormValidator() {
		return formValidator;
	}
	
	public void setFormValidator(FormValidator formValidator) {
		this.formValidator = formValidator;
	}
}
