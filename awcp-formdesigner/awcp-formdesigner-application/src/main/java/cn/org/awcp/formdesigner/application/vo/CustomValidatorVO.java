package cn.org.awcp.formdesigner.application.vo;

public class CustomValidatorVO extends ValidatorVO{
	private String validatorType="4";
	private String clientScript;
	private String serverScript;
	
	public String getValidatorType() {
		return validatorType;
	}
	
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}
	
	public String getClientScript() {
		return clientScript;
	}
	
	public void setClientScript(String clientScript) {
		this.clientScript = clientScript;
	}
	
	public String getServerScript() {
		return serverScript;
	}
	
	public void setServerScript(String serverScript) {
		this.serverScript = serverScript;
	}
	
}
