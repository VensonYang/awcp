package org.szcloud.framework.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.formdesigner.application.vo.DynamicPageVO;
import org.szcloud.framework.formdesigner.application.vo.StoreVO;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface StoreService {
	/**
	 * 校验类型的code前缀
	 */
	public static final String VALIDATOR_CODE = "0.1.4.";
	/**
	 * 页面动作类型的code前缀
	 */
	public static final String PAGEACT_CODE = "0.1.5.";
	/**
	 * 样式类型的code前缀
	 */
	public static final String STYLE_CODE = "0.1.6.";
	/**
	 * 组件类型的code前缀
	 */
	public static final String COMPONENT_CODE = "0.1.7.";
	/**
	 * 布局类型的code前缀
	 */
	public static final String LAYOUT_CODE = "0.1.8.";
	
	/**
	 * 打印管理类型的code前缀
	 */
	public static final String PRINT_CODE = "0.1.9.";
	/**
	 * 函数库类型的code前缀
	 */
	public static final String FUNC_CODE = "0.1.10.";
	
	/**
	 * 
	 * @Title: save
	 * @Description: 保存或更新
	 * @param vo
	 * @return String 数据库id
	 * @throws
	 */
	public String save(StoreVO vo);

	/**
	 * @Title: delete
	 * @Description: 物理删除
	 * @param vo
	 * @return boolean
	 * @throws
	 */
	public boolean delete(StoreVO vo);
	
	/**
	 * 
	 * @Title: deleteLikeCode
	 * @Description: 按照code(code用于区分类型）删除
	 * @param code
	 * @return boolean
	 * @throws
	 */
	public boolean deleteLikeCode(String code);

	/**
	 * @Title: logicDeleteLikeCode
	 * @Description: 按照Code模糊删除
	 * @param code
	 * @return boolean
	 * @throws
	 */	
	public StoreVO findById(String id);
	
	/**
	 * @Title: findByCode
	 * @Description: 按照code查找
	 * @param id
	 * @return StoreVO
	 * @throws
	 */
	public StoreVO findByCode(String code);
	
	public List<StoreVO> findByIds(String[]ids);
	
	/**
	 * 根据动态表单ID查找动态表单下的所有组件（包括Layout、Component和PageAct）
	 * @param dyanamicPageId
	 * @return
	 */
	public PageList<StoreVO> findByDyanamicPageId(Long dyanamicPageId);
		
	/**
	 * 复制动态表单下的所有组件（包括Layout、Component和PageAct）到另外一个表单
	 * 
	 * @param sourceId
	 * @param targetId
	 * @return
	 */
	public List<StoreVO> copyByDynamicPageId(Long sourceId, Long targetId);
	
	/**
	 * 复制动态表单下的所有组件（包括Layout、Component和PageAct）到另外一个表单
	 * 
	 * @param sourceVO
	 * @param targetVO
	 * @return
	 */
	public List<StoreVO> copyByDynamicPage(DynamicPageVO sourceVO, DynamicPageVO targetVO);
	
	/**
	 * @Title: queryPagedResult
	 * @Description: 分页查找 可以按照指定字段排序
	 * @param params       参数
	 * @param currentPage     当前页
	 * @param pageSize      每页数
	 * @param sortString     排序
	 * @return PageList<StoreVO>
	 * @throws
	 */
	public PageList<StoreVO> queryPagedResult(Map<String, Object> params,
			int currentPage, int pageSize, String sortString);

	/**
	 * @Title: selectPagedByExample
	 * @Description: 复杂条件的查询
	 * @param baseExample      条件
	 * @param currentPage      当前页
	 * @param pageSize       每页数
	 * @param sortString         排序字段
	 * @return PageList<StoreVO>
	 * @throws
	 */
	public PageList<StoreVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString);
	
	/**
	 * 
	 * @Title: copy 
	 * @Description: 对数据进行拷贝 
	 * @param actIds
	 * @param dynamicPageId
	 * @param order
	 * @return List<StoreVO>    
	 * @throws
	 */
	public List<StoreVO> copy(String actIds, Long dynamicPageId, int order);

	boolean delete(String[] ids);
	
	public List<StoreVO> queryComponentByLayoutId(String layoutID,Long systemId);

	List<StoreVO> findByIds(List<String> ids);

	boolean delete(List<String> ids);
	
	/**
	 * 通过页面Id查找页面所有组件
	 * @param dyanamicPageId
	 * @return
	 */
	PageList<JSONObject> findComponentByDyanamicPageId(Long dyanamicPageId);

	PageList<JSONObject> findParentComponentByDyanamicPageId(Long dyanamicPageId);
	
	/**
	 * 重置组件order
	 * @param dynamicPageId
	 * @return
	 */
	boolean freshComponentOrder(String dynamicPageId);
	
	/**
	 * 重置布局Order
	 * @param dynamicPageId
	 * @return
	 */
	boolean freshLayoutOrder(String dynamicPageId);
}
