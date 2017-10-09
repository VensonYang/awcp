package org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.ValuedComponent;

public class FormValidator extends MyCustomValidator {
	/**
	 * 校验组
	 * 	默认为1
	 */
	private String validatorGroup;
	/**
	 * 是否允许为空
	 */
	private boolean empty;
	/**
	 * 为空时提示
	 */
	private String onEmpty;
	/**
	 * 提示消息
	 */
	private String onShow;
	/**
	 * 获得焦点时提示
	 */
	private String onFocus;
	/**
	 * 校验正确时提示
	 */
	private String onCorrect;
	/**
	 * 
	 */
	private String tipID;
	/**
	 * 
	 */
	private String relativeID;
	/**
	 * Json
	 * 	[{id,order},{id,orer}]
	 */
	private String validatorsJson;
	/**
	 * 校验对象
	 */
	Map<String, MyCustomValidator> validatorsMap = new HashMap<String, MyCustomValidator>();
	
	/**
	 * 所属组件
	 */
	private ValuedComponent component;
	
	public String getValidatorGroup() {
		return validatorGroup;
	}
	
	public void setValidatorGroup(String validatorGroup) {
		this.validatorGroup = validatorGroup;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public String getOnEmpty() {
		return onEmpty;
	}
	
	public void setOnEmpty(String onEmpty) {
		this.onEmpty = onEmpty;
	}
	
	public String getOnShow() {
		return onShow;
	}
	
	public void setOnShow(String onShow) {
		this.onShow = onShow;
	}
	
	public String getOnFocus() {
		return onFocus;
	}
	
	public void setOnFocus(String onFocus) {
		this.onFocus = onFocus;
	}
	
	public String getOnCorrect() {
		return onCorrect;
	}
	
	public void setOnCorrect(String onCorrect) {
		this.onCorrect = onCorrect;
	}
	
	public String getTipID() {
		return tipID;
	}
	
	public void setTipID(String tipID) {
		this.tipID = tipID;
	}
	
	public String getRelativeID() {
		return relativeID;
	}
	
	public void setRelativeID(String relativeID) {
		this.relativeID = relativeID;
	}
	
	public String getValidatorsJson() {
		return validatorsJson;
	}
	
	public void setValidatorsJson(String validatorsJson) {
		this.validatorsJson = validatorsJson;
	}
	
	public Map<String, MyCustomValidator> getValidatorsMap() {
		return validatorsMap;
	}
	
	public void setValidatorsMap(Map<String, MyCustomValidator> validatorsMap) {
		this.validatorsMap = validatorsMap;
	}
	
	public ValuedComponent getComponent() {
		return component;
	}
	
	public void setComponent(ValuedComponent component) {
		this.component = component;
	}
	
	@Override
	public String toClientScriptString() {
		StringBuilder sb = new StringBuilder();
		sb.append(".formValidator({");
		if(StringUtils.isNotBlank(this.validatorGroup)){
			sb.append("validatorgroup:\""+this.validatorGroup+"\",");
		}else{
			sb.append("validatorgroup:\""+1+"\",");
		}
		if(StringUtils.isNotBlank(this.tipID)){
			sb.append("tipID:\""+this.tipID+"\",");
		}
		if(StringUtils.isNotBlank(this.relativeID)){
			sb.append("relativeID:\""+this.relativeID+"\",");
		}
		if(StringUtils.isNotBlank(this.relativeID)){
			sb.append("relativeID:\""+this.relativeID+"\",");
		}
		sb.append("empty:"+empty+",");
		if(StringUtils.isNotBlank(this.onEmpty)){
			sb.append("onEmpty:\""+this.onEmpty+"\",");
		}
		if(StringUtils.isNotBlank(this.onCorrect)){
			sb.append("onCorrect:\""+this.onCorrect+"\",");
		}
		if(StringUtils.isNotBlank(this.onFocus)){
			sb.append("onFocus:\""+this.onFocus+"\",");
		}		
		if(StringUtils.isNotBlank(this.onShow)){
			sb.append("onShow:\""+this.onShow+"\",");
		}		
		sb.deleteCharAt(sb.length()-1);
		sb.append("})");	
		for(MyCustomValidator my:validatorsMap.values()){
			sb.append(my.toClientScriptString());
		}
		sb.append(";");
		return sb.toString();
	}
	
	@Override
	public String toServerScriptString() {
		return null;
	}
}
