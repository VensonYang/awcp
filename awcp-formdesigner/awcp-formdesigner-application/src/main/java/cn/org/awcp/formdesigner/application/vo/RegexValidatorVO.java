package cn.org.awcp.formdesigner.application.vo;

public class RegexValidatorVO extends ValidatorVO{
	private String validatorType="2";
	private String regExp;
	private String onError;
	
	public String getValidatorType() {
		return validatorType;
	}
	
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}
	
	public String getRegExp() {
		return regExp;
	}
	
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}
	
	public String getOnError() {
		return onError;
	}
	
	public void setOnError(String onError) {
		this.onError = onError;
	}
		
}
