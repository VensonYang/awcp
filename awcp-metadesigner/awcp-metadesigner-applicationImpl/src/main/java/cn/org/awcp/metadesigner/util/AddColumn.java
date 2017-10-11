package cn.org.awcp.metadesigner.util;

import java.util.List;

import cn.org.awcp.metadesigner.vo.MetaModelItemsVO;
/**
 * 添加列
 * @author Administrator
 *
 */
public class AddColumn {
	
	@SuppressWarnings("unused")
	public static String addListColumn(List<MetaModelItemsVO> vo,String tableName){
		String sql = "alter table ";
		//alter table t1 add column addr varchar(20) not null;
		for(MetaModelItemsVO mmo:vo){
			sql += tableName + " add column " + mmo.getItemCode() + " " + mmo.getItemType();
			if(mmo.getUseNull()==1){
				sql += " not null";
			}
			sql += ";";
			if(mmo.getItemType()!=null && !mmo.getItemType().equals("")){
				//alter　table　emp add　constraint　fk_emp_dept　foreign　key(dept)　references　dept(deptno)
				sql += "alter table " + tableName + " add constraint fk_" + mmo.getItemName() + 
					   " foreign key(" + mmo.getItemName() + ") references " + tableName + "(id);";
			}
		}
		return null;
	}
	public static String addOneColumn(MetaModelItemsVO vo,String tableName){
		String sql = "alter table " + tableName + " add column " + vo.getItemCode();
		if(vo.getItemType().equals("varchar")){
			sql += " varchar" + "(" + vo.getItemLength() + ")";
		}else{
			sql += " " + vo.getItemType();
		}
		if(vo.getUseNull()==1){
			sql += " not null;";
		}
		if(vo.getUsePrimaryKey()==1){
			//Alter table tb add primary key(id)；
			sql += "alter table " + tableName + " add primary key(" + vo.getItemName() + ")";
		}
		return sql;
	}
}
