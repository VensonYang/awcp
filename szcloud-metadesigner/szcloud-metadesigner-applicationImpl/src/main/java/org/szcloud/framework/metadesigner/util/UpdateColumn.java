package org.szcloud.framework.metadesigner.util;

import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;

public class UpdateColumn {

	public static String updateColumn(MetaModelItemsVO newMetaModelItem,String tableName){
		//alter table fw_mm_metamodelitems change itemCode itemCode varchar(20) not null default '123456'; 
			String str = "alter table " + tableName;
			if(!newMetaModelItem.getItemCode().equals("")){
				str += " change " + newMetaModelItem.getItemCode() + " " + newMetaModelItem.getItemCode();
			}
			if(!newMetaModelItem.getItemType().equals("")){
				if(newMetaModelItem.getItemType().equals("varchar") || newMetaModelItem.getItemType().equals("decimal")){
					str += " " + newMetaModelItem.getItemType() + "("+newMetaModelItem.getItemLength() + ")";
				}else{
					str += " " + newMetaModelItem.getItemType();
				}
			}
			if(newMetaModelItem.getUseNull()==1){
				str += " not null";
			}
			if(!newMetaModelItem.getDefaultValue().equals("")){
				if(newMetaModelItem.getItemCode().equals("varchar")){
					str += " default '" + newMetaModelItem.getDefaultValue() + "'";
				}
				else{
					str += " default " + newMetaModelItem.getDefaultValue();
				}
			}
			return str;
	}
	//添加列
	public static String newAddColumn(MetaModelItemsVO mmi,String tableName){
		//alter table user add COLUMN new1 VARCHAR(20) not null DEFAULT NULL
		String str = "alter table " + tableName;
		if(!mmi.getItemCode().equals("")){
			str += " add column " + mmi.getItemCode();
		}
		if(!mmi.getItemType().equals("")){
			if(mmi.getItemType().equals("varchar")){
				str += " " + mmi.getItemType() + "(" + mmi.getItemLength() + ")";
			}else{
				str += " " + mmi.getItemType();
			}
		}
		if(mmi.getUseNull()!=null && mmi.getUseNull()==1){
			str += " not null";
		}
		if(mmi.getDefaultValue()!=null && !mmi.getDefaultValue().equals("")){
			if(mmi.getItemCode().equals("varchar")){
				str += " default '" + mmi.getItemLength() + "'";
			}else{
				str += " default " + mmi.getItemLength();
			}
		}
		return str;
	}
	
}
