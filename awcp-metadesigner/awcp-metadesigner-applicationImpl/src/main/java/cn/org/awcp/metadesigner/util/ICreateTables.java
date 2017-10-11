package cn.org.awcp.metadesigner.util;

import java.util.List;

import cn.org.awcp.metadesigner.vo.MetaModelItemsVO;
import cn.org.awcp.metadesigner.vo.MetaModelVO;

public interface ICreateTables {
	public String getSql(MetaModelVO mm,List<MetaModelItemsVO> mmi);
}
