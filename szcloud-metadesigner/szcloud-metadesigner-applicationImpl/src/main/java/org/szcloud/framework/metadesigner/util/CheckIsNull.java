package org.szcloud.framework.metadesigner.util;

import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;

public class CheckIsNull {
	
	public static String checkElement(MetaModelItemsVO vo){
		StringBuffer s=new StringBuffer(vo.getItemCode());
		//String str=vo.getItemCode();
		if(vo.getItemType().equals("varchar") || vo.getItemType().equals("decimal")){
			s.append(" ").append(vo.getItemType()).append("(").append(vo.getItemLength()).append(")");
			//str+=" "+vo.getItemType()+"("+vo.getItemLength()+")";
		}
		else if(vo.getItemType().equals("一对一") || vo.getItemType().equals("多对一")){
			//查询出所对表的主键
			s.append(" bigInt");
			//str+=" bigInt";
		}
		else{
			s.append(" ").append(vo.getItemType());
			//str+=" "+vo.getItemType();
		}
		if(vo.getUseNull()==0){
			s.append(" not null");
			//str+=" not null";
		}
		if(vo.getUsePrimaryKey()!=null && vo.getUsePrimaryKey()==1){
			s.append(" primary key auto_increment");
			//str+=" primary key auto_increment";
		}
		else{
			if(vo.getUsePrimaryKey()==0){
				if(vo.getDefaultValue()!=null && !vo.getDefaultValue().equals("")){
					try {
						Integer.parseInt(vo.getDefaultValue());
						s.append(" default ").append(vo.getDefaultValue());
						//str+=" default "+vo.getDefaultValue();
					} catch (Exception e) {
						s.append(" default '").append(vo.getDefaultValue()).append("'");
						//str+=" default '"+vo.getDefaultValue()+"'";
					}
				}
			}
		}
		s.append(",\n");
		//str+=",\n";
		return s.toString();
	}
}
