package org.szcloud.framework.metadesigner.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.metadesigner.application.MetaModelItemService;
import org.szcloud.framework.metadesigner.application.ModelRelationService;
import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;
import org.szcloud.framework.metadesigner.vo.ModelRelationVO;

@Service("checkIsNull")
public class CreateColumn {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(CreateColumn.class);
	
	@Autowired
	private ModelRelationService modelRelationServiceImpl;
	
	@Autowired
	private MetaModelItemService metaModelItemsServiceImpl;

	public String columnSql(MetaModelItemsVO vo) {
		StringBuffer st = new StringBuffer(vo.getItemCode());
		// String str=vo.getItemCode();
		if (vo.getItemType().equals("varchar") || vo.getItemType().equals("decimal")) {
			st.append(" ");
			st.append(vo.getItemType());
			st.append("(");
			st.append(vo.getItemLength());
			st.append(")");
			// str+=" "+vo.getItemType()+"("+vo.getItemLength()+")";
		} else if (vo.getItemType().equals("一对一") || vo.getItemType().equals("多对一")) {
			ModelRelationVO mr = this.modelRelationServiceImpl.queryByItem(vo.getId());
			List<MetaModelItemsVO> list = this.metaModelItemsServiceImpl.queryResult("queryResult", mr.getModelId());
			for (MetaModelItemsVO mmi : list) {
				if (mmi.getUsePrimaryKey() == 1) {
					st.append(" ");
					st.append(mmi.getItemType());
					// str+=" "+mmi.getItemType();
					logger.debug(mmi.getItemType());
				}
			}
		} else {
			st.append(" ");
			st.append(vo.getItemType());
			// str+=" "+vo.getItemType();
		}
	
		if (vo.getUsePrimaryKey() != null && vo.getUsePrimaryKey() == 1) {
			if (vo.getItemType().equals("int") || vo.getItemType().equals("bigInt")) {
				st.append(" primary key auto_increment");
				// str+=" primary key auto_increment";
			} else {
				st.append(" primary key");
			}
		} else {
			if (vo.getUsePrimaryKey() != null && vo.getUsePrimaryKey() == 0) {
				if (vo.getDefaultValue() != null && !vo.getDefaultValue().equals("")) {
					try {
						Integer.parseInt(vo.getDefaultValue());
						st.append(" default ");
						st.append(vo.getDefaultValue());
						// str+=" default "+vo.getDefaultValue();
					} catch (Exception e) {
						st.append(" default '");
						st.append(vo.getDefaultValue());
						st.append("'");
						// str+=" default '"+vo.getDefaultValue()+"'";
					}
				}
			}
		}
		st.append(",\n");
		// str+=",\n";
		return st.toString();
	}

}
