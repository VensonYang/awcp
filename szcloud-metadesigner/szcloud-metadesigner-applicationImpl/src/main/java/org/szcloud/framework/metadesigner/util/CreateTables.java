package org.szcloud.framework.metadesigner.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.metadesigner.application.MetaModelItemService;
import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;

@Service("createTables")
public class CreateTables implements ICreateTables {

	@Autowired
	private MetaModelItemService metaModelItemsServiceImpl;

	@Autowired
	private CreateColumn checkIsNull;

	public String getSql(MetaModelVO mm, List<MetaModelItemsVO> mmi) {
		StringBuffer sq = new StringBuffer("create table ").append(mm.getTableName()).append("(\n");
		// String sql="create table "+mm.getTableName()+"(\n";
		if (new CheckColumn().isPri(mmi)) {
			for (MetaModelItemsVO mmo : mmi) {
				sq.append(checkIsNull.columnSql(mmo));
			}
		} else {
			sq.append("ID VARCHAR primary key auto_increment,");
			// sql+="id bigInt primary key auto_increment,";
			MetaModelItemsVO vosd = new MetaModelItemsVO();
			vosd.setItemCode("ID");
			vosd.setItemName("编号");
			vosd.setItemType("varchar");
			vosd.setUsePrimaryKey(1);
			vosd.setModelId(mm.getId());
			vosd.setUseNull(0);
			vosd.setItemValid(1);
			this.metaModelItemsServiceImpl.save(vosd);
			for (MetaModelItemsVO mmo : mmi) {
				sq.append(checkIsNull.columnSql(mmo));
			}
		}
		String s = sq.toString().substring(0, sq.toString().length() - 2);
		// String sqls=sql.substring(0,sql.length()-2);
		// logger.debug(sqls);
		// sqls+="\n);";
		s += "\n);";
		return s;
	}

}
