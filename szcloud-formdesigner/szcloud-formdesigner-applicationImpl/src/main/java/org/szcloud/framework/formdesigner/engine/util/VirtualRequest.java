package org.szcloud.framework.formdesigner.engine.util;

import java.util.HashMap;
import java.util.Map;

public class VirtualRequest {
	
	Map<String,String> map ;
	
	public VirtualRequest(Map<String,String> map){
		this.map = map;
	}
	
	public VirtualRequest(){
		this.map = new HashMap<String,String>();
	}
	
	public String getParameter(String args){
		return map.get(args);
	}
	
	public void setParameter(String args,String value){
		this.map.put(args, value);
	} 
	
}
