package cn.org.awcp.metadesigner.vo;

import java.util.List;
import java.util.Map;

public class ModelVO {
	private String code;
	private List<Map<String, String>> datas;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<Map<String, String>> getDatas() {
		return datas;
	}
	
	public void setDatas(List<Map<String, String>> datas) {
		this.datas = datas;
	}
}
