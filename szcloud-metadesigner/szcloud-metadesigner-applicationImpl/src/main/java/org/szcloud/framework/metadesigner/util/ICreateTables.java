package org.szcloud.framework.metadesigner.util;

import java.util.List;

import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;

public interface ICreateTables {
	public String getSql(MetaModelVO mm,List<MetaModelItemsVO> mmi);
}
