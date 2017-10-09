package org.szcloud.framework.formdesigner.application.vo;

public class InputValidatorVO extends ValidatorVO{
	private String min;
	private String onErrorMin;
	private String max;
	private String onErrorMax;
	private String validatorType="1";

	public String getValidatorType() {
		return validatorType;
	}
	
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}
	
	public String getMin() {
		return min;
	}
	
	public void setMin(String min) {
		this.min = min;
	}
	
	public String getOnErrorMin() {
		return onErrorMin;
	}
	
	public void setOnErrorMin(String onErrorMin) {
		this.onErrorMin = onErrorMin;
	}
	
	public String getMax() {
		return max;
	}
	
	public void setMax(String max) {
		this.max = max;
	}
	
	public String getOnErrorMax() {
		return onErrorMax;
	}
	
	public void setOnErrorMax(String onErrorMax) {
		this.onErrorMax = onErrorMax;
	}
	
}
