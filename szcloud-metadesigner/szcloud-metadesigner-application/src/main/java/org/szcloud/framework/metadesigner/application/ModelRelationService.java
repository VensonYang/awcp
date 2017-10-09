package org.szcloud.framework.metadesigner.application;

import java.util.List;

import org.szcloud.framework.metadesigner.vo.ModelRelationVO;

/**
 * 对模型关系表进行CRUD操作
 * @author Administrator
 *
 */
public interface ModelRelationService {
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<ModelRelationVO> findAll();
	
	/**
	 * 根据itemId查询
	 * @param itemId
	 * @return
	 */
	public ModelRelationVO queryByItem(Long itemId);
	
	/**
	 * 根据modelId查询
	 * @param modelId
	 * @return
	 */
	public List<ModelRelationVO> queryByModelId(Long modelId);
	
	/**
	 * 增加
	 * @param vo
	 */
	public boolean save(ModelRelationVO vo);
	
	/**
	 * 修改
	 * @param vo
	 * @param queryStr
	 */
	public boolean executeUpdate(ModelRelationVO vo,String queryStr);
	
	/**
	 * 删除
	 * @param vo
	 */
	public boolean remove(ModelRelationVO vo);

}
