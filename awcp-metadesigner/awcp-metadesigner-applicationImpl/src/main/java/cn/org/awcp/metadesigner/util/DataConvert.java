package cn.org.awcp.metadesigner.util;

import java.text.ParseException;

public interface DataConvert {
	
	public Object stringToObject(String s,String type) throws ParseException;
	
}
