package org.szcloud.framework.metadesigner.application;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface MetaModelItemService {
	
	/**
	 * 增加
	 * @param vo
	 */
	public Long save(MetaModelItemsVO vo);
	
	/**
	 * 删除
	 * @param vo
	 */
	public boolean remove(MetaModelItemsVO vo);
	

	/**
	 * 查询一条数据
	 * @param id
	 * @return
	 */
	public MetaModelItemsVO get(Long id);
	
	/**
	 * 分页查询
	 * @param queryStr
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<MetaModelItemsVO> queryResult(String queryStr, Map<String, Object> params, int currentPage, int pageSize,String sortString);
	
	/**
	 * 根据模型ID查询元数据模型属性
	 * @param queryStr
	 * @param modelId
	 * @return
	 */
	public List<MetaModelItemsVO> queryResult(String queryStr,long modelId);
	
	/**
	 * 修改
	 * @param queryStr
	 * @param vo
	 */
	public boolean update(String queryStr,MetaModelItemsVO vo);

	/**
	 * 判断列是否已经在数据存在
	 * @param queryStr
	 * @param tableName
	 * @param itemName
	 * @return
	 */
	public boolean columnIsExist(String queryStr,String tableName,String itemName);
	
	/**
	 * 根据属性的状态查找
	 * @param queryStr
	 * @param modelId
	 * @return
	 */
	public List<MetaModelItemsVO> queryByState(String queryStr,long modelId);

	/**
	 * 根据模型书名称查找
	 * @param queryStr
	 * @param tableName
	 * @return
	 */
	public List<MetaModelItemsVO> queryTableName(String queryStr,String tableName);
	
	/**
	 * 删除外键
	 * @param modelId
	 */
	public boolean removeByFk(Long modelId);
	
	/**
	 * @Title: selectPagedByExample
	 * @Description: 复杂条件的查询
	 * @param baseExample      条件
	 * @param currentPage      当前页
	 * @param pageSize       每页数
	 * @param sortString         排序字段
	 * @return PageList<MetaModelVO>
	 * @throws
	 */
	public PageList<MetaModelItemsVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString);
	
	/**
	 * 根据modelId和ItemCode查找
	 * @param queryStr
	 * @param modelId
	 * @param itemCode
	 * @return
	 */
	public List<MetaModelItemsVO> queryColumn(String queryStr,long modelId,String itemCode);
}
