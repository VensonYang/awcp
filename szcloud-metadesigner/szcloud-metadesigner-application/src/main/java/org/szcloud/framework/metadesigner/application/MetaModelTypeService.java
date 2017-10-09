package org.szcloud.framework.metadesigner.application;

import java.util.List;

import org.szcloud.framework.metadesigner.vo.MetaModelTypeVO;

/**
 * 模型分类
 * 接口
 * @author Administrator
 *
 */
public interface MetaModelTypeService {
	
	/**
	 * 增加
	 * @param vo
	 * @return
	 */
	public long save(MetaModelTypeVO vo);
	
	/**
	 * 删除
	 * @param vo
	 * @return
	 */
	public boolean remove(MetaModelTypeVO vo);
	
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	public boolean update(MetaModelTypeVO vo);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<MetaModelTypeVO> findAll();
	
}
