package org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.validator;

import org.apache.commons.lang3.StringUtils;

public class CompareValidator extends MyCustomValidator{
	private String desID;
	private String operateor;
	private String dataType;
	private String onError;
	
	public String getDesID() {
		return desID;
	}
	
	public void setDesID(String desID) {
		this.desID = desID;
	}
	
	public String getOperateor() {
		return operateor;
	}
	
	public void setOperateor(String operateor) {
		this.operateor = operateor;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getOnError() {
		return onError;
	}
	
	public void setOnError(String onError) {
		this.onError = onError;
	}
	
	@Override
	public String toClientScriptString() {
		StringBuilder sb= new StringBuilder();
		sb.append(".compareValidator({");
		if(StringUtils.isNotBlank(this.desID)){
			sb.append("desID:\""+this.desID+"\",");
		}
		if(StringUtils.isNotBlank(this.operateor)){
			sb.append("operateor:\""+this.operateor+"\",");
		}
		if(StringUtils.isNotBlank(this.dataType)){
			sb.append("dataType:\""+this.dataType+"\",");
		}
		if(StringUtils.isNotBlank(this.onError)){
			sb.append("onError:\""+this.onError+"\",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("})");
		return sb.toString();
	}
	
	@Override
	public String toServerScriptString() {
		return null;
	}
}
