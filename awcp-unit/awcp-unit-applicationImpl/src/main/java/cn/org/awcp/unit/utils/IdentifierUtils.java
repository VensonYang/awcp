package cn.org.awcp.unit.utils;

import java.util.List;

public  class IdentifierUtils {
	
	public static String getLongIdStringForSql(List<Long> ids){
		if(ids == null || ids.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < ids.size(); i++){
			sb.append(ids.get(i).toString() + ",");
		}
		String destIds = sb.toString();
		if(destIds.endsWith(",")){
			destIds = destIds.substring(0, destIds.length()-1);
		}
		return destIds;
	}
	
	public static String getStringIdStringForSql(List<String> ids){
		if(ids == null || ids.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < ids.size(); i++){
			sb.append("'" + ids.get(i) + "',");
		}
		String destIds = sb.toString();
		if(destIds.endsWith(",")){
			destIds = destIds.substring(0, destIds.length()-1);
		}
		return destIds;
	}

}
