package org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.validator;

public class CustomValidator extends MyCustomValidator{

	private String clientScript;
	private String serverScript;
	
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

	@Override
	public String toClientScriptString() {
		return ".functionValidator({fun:"+this.clientScript+"})";
	}

	@Override
	public String toServerScriptString() {
		return null;
	}

}
