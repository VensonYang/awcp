package org.szcloud.framework.metadesigner.util;

import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.metadesigner.core.domain.MetaModel;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;
/**
 * 检查元数据对应的表在数据库是否存在
 * 
 * @author Administrator
 *
 */
public class CheckIsExist {
	public static boolean checkTableIsExist(long id){
		//查询单个
		try {
			MetaModel model =  MetaModel.get(MetaModel.class, id);
			MetaModelVO vo=BeanUtils.getNewInstance(model, MetaModelVO.class);
			String sql="select * from "+vo.getTableName();
			MetaModel.getRepository().excuteSql(sql);//修改
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
