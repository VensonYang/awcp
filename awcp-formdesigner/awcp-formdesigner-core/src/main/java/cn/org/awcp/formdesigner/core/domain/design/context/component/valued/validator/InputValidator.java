package cn.org.awcp.formdesigner.core.domain.design.context.component.valued.validator;

import org.apache.commons.lang3.StringUtils;

public class InputValidator  extends MyCustomValidator{
	private String type;
	private String min;
	private String max;
	private String onError;
	private String onErrorMin;
	private String onErrorMax;
	private String empty;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMin() {
		return min;
	}
	
	public void setMin(String min) {
		this.min = min;
	}
	
	public String getMax() {
		return max;
	}
	
	public void setMax(String max) {
		this.max = max;
	}
	
	public String getOnError() {
		return onError;
	}
	
	public void setOnError(String onError) {
		this.onError = onError;
	}
	
	public String getOnErrorMin() {
		return onErrorMin;
	}
	
	public void setOnErrorMin(String onErrorMin) {
		this.onErrorMin = onErrorMin;
	}
	
	public String getOnErrorMax() {
		return onErrorMax;
	}
	
	public void setOnErrorMax(String onErrorMax) {
		this.onErrorMax = onErrorMax;
	}
	
	public String getEmpty() {
		return empty;
	}
	
	public void setEmpty(String empty) {
		this.empty = empty;
	}
	
	@Override
	public String toClientScriptString() {
		StringBuilder sb = new StringBuilder();
		sb.append(".inputValidator({ ");
		if(StringUtils.isNotBlank(this.type)){
			sb.append("type:\""+this.type+"\",");
		}
		if(StringUtils.isNotBlank(this.min)){
			sb.append("min:"+this.min+",");
		}
		if(StringUtils.isNotBlank(this.max)){
			sb.append("max:"+this.max+",");
		}
		if(StringUtils.isNotBlank(this.onError)){
			sb.append("onError:\""+this.onError+"\",");
		}
		if(StringUtils.isNotBlank(this.onErrorMin)){
			sb.append("onErrorMin:\""+this.onErrorMin+"\",");
		}
		if(StringUtils.isNotBlank(this.onErrorMax)){
			sb.append("onErrorMax:\""+this.onErrorMax+"\",");
		}
		if(StringUtils.isNotBlank(this.empty)){
			sb.append("empty:\""+this.empty+"\",");
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
