package org.szcloud.framework.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.formdesigner.application.vo.DynamicPageVO;
import org.szcloud.framework.formdesigner.application.vo.StoreVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lenovo
 *
 */
public interface FormdesignerService {
	
	/**
	 * 获取当前系统的每个表单的所有页面动作
	 * 
	 * @return
	 */
	public Map<String,List<StoreVO>> getSystemActs(List<Long> dynamicPageIds);
	
	public DynamicPageVO copy(Long id);
	
	public DynamicPageVO copyToSystem(Long id, String systemId);
	
	public DynamicPageVO publish(Long id);
	
	public DynamicPageVO checkOut(Long id);
	
	public DynamicPageVO checkIn(Long id);
	
	public DynamicPageVO publish(DynamicPageVO vo);
	
	
	public DynamicPageVO findById(long id);

	public void saveOrUpdate(DynamicPageVO dpvo);

	public List<DynamicPageVO> findAll();

	public PageList<DynamicPageVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString);

	/**
	 * 模糊查询分页显示
	 * 
	 * @param example
	 *            查询条件
	 * @param currentPage
	 *            当前页数
	 * @param pageSize
	 *            每页显示记录数
	 * @param sortString
	 *            排序
	 * @return
	 */
	public PageList<DynamicPageVO> selectPagedByExample(BaseExample example,
			int currentPage, int pageSize, String sortString);

	public void delete(List<Long> idx);
	
	/**
	 * 
	 * @param queryStr 对应mapper.xml的select中的id
	 * @param params 查询参数
	 * @return
	 */
	public List<DynamicPageVO> queryResult(String queryStr,Map<String, Object> params);
	
	/**
	 * 更新流程信息
	 */
	public void updateWorkflowInfo(DynamicPageVO vo);
	
	/**
	 * 查找流程信息
	 * @param pageId  动态表单Id
	 * @return [{workId,workName}]
	 */
	public String getWfByStartNode(Long pageId);
	
	/**
	 * 查找包含组件所  包含的所有组件(column除外)
	 * @param container 包含组件
	 * @return
	 */
	List<JSONObject> getComponentByContainer(JSONObject container);
	
	/**
	 * 更新数据源信息
	 * @param vo
	 * @throws MRTException
	 */
	void updateModelInfo(DynamicPageVO vo) throws MRTException;
	
	/**
	 * 查找包含组件所  包含的所有组件(包含column)
	 * @param container 包含组件
	 * @return
	 */
	List<JSONObject> getComponentByContainerWithColumn(JSONObject container);
	
	/**
	 * 根据页面查询子列表页面
	 * @param dynamicPageId
	 * @return
	 */
	public List<Long> getChildListPages(Long dynamicPageId);
		
	/**
	 * 查看动态页面发布后的模版源代码
	 * @param id
	 * @return null if page is null
	 */
	public String getTemplateContext(Long id);
	
	public List<DynamicPageVO> listNameAndIdInSystem(Long systemId, Map<String, Object> params);
	
}
