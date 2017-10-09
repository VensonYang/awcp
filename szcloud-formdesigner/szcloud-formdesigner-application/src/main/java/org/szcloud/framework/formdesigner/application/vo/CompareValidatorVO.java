package org.szcloud.framework.formdesigner.application.vo;

public class CompareValidatorVO extends ValidatorVO{
	private String validatorType="3";
	private String desID;
	private String operateor;
	private String onError;
	private String dataType;
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getValidatorType() {
		return validatorType;
	}
	
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}
	
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
	
	public String getOnError() {
		return onError;
	}
	
	public void setOnError(String onError) {
		this.onError = onError;
	}
	
}
