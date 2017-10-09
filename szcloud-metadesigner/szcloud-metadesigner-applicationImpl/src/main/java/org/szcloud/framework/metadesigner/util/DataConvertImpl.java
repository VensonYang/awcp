package org.szcloud.framework.metadesigner.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.utils.DateUtils;

@Service(value="dataConvertImpl")
public class DataConvertImpl implements DataConvert{
	
	public Object stringToObject(String s,String type) throws ParseException {		
		if(type.equalsIgnoreCase("int") || type.equalsIgnoreCase("smallint")){
			if(StringUtils.isNotBlank(s)) {
				try {
					return Integer.parseInt(s);
				} catch (Exception e) {
					try {
						return (int)Double.parseDouble(s);
					} catch (Exception e1) {
						return null;
					}
				}
			} else {
				return null;
			}
		}
		if(type.equalsIgnoreCase("bigInt")){
			if(StringUtils.isNotBlank(s)) {
				try{
					return Long.parseLong(s);
				} catch(Exception e){
					return null;
				}
			} else {
				return null;
			}
		}
		if(type.equalsIgnoreCase("double") || type.equalsIgnoreCase("DECIMAL")){
			if(StringUtils.isNotBlank(s)) {
				try{
					return Double.parseDouble(s);
				} catch(Exception e){
					return null;
				}
			} else {
				return null;
			}
		}
		if(type.equalsIgnoreCase("float")){
			if(StringUtils.isNotBlank(s)) {
				try{
					return Float.parseFloat(s);
				} catch(Exception e){
					return null;
				}
			} else {
				return null;
			}
		}
		if(type.equalsIgnoreCase("date") ||type.equalsIgnoreCase("DATETIME")){
			Date d = DateUtils.parseDate(s);
			return d;
		}
		return s;
	}

}
