package org.szcloud.framework.unit.utils;

public class PunGroupUtils {
	
	public static Long getWorkflowGroupType(String punGroupType){
		Long wkGroupType = new Long(2);
		if("2".equals(punGroupType)){
			wkGroupType = new Long(3);//部门；
		} else if("3".equals(punGroupType)){
			wkGroupType = new Long(4);//小组
		}
		return wkGroupType;
	}

}
