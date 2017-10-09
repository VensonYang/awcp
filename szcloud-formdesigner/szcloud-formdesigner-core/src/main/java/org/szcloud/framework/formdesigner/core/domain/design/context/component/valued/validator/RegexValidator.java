package org.szcloud.framework.formdesigner.core.domain.design.context.component.valued.validator;

import org.apache.commons.lang3.StringUtils;


public class RegexValidator extends MyCustomValidator {
	private String regExp;
	private String param;
	private String compareType;
	private String dataType;
	private String onError;
	
	public String getRegExp() {
		return regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
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
		StringBuilder sb = new StringBuilder();
		sb.append(".regexValidator({");
		if(StringUtils.isNotBlank(this.dataType)){
			if("enum".equals(this.dataType)){
				sb.append("dataType:\""+this.dataType+"\",");
				sb.append("regExp:"+this.regExp+",");
			}else if("string".equals(this.dataType)){
				sb.append("dataType:\""+this.dataType+"\",");
				sb.append("regExp:\""+this.regExp+"\",");
			}
		}else{
			sb.append("regExp:\""+this.regExp+"\",");
		}
		if(StringUtils.isNotBlank(this.compareType)){
			sb.append("compareType:\""+this.compareType+"\",");
		}
		if(StringUtils.isNotBlank(this.param)){
			sb.append("param:\""+this.param+"\",");
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
