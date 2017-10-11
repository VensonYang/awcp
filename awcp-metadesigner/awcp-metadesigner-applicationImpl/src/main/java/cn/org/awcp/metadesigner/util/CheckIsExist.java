package cn.org.awcp.metadesigner.util;

import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.metadesigner.core.domain.MetaModel;
import cn.org.awcp.metadesigner.vo.MetaModelVO;
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
