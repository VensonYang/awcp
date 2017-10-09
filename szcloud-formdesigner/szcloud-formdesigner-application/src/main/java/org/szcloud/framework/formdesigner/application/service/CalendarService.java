package org.szcloud.framework.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.formdesigner.application.vo.CalendarVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface CalendarService {
	
	/**
	 * 
	 * @Title: save
	 * @Description: 保存或更新
	 * @param vo
	 * @return Long 数据库id
	 * @throws
	 */
	public String save(CalendarVO vo);

	/**
	 * @Title: delete
	 * @Description: 物理删除
	 * @param vo
	 * @return boolean
	 * @throws
	 */
	public boolean delete(CalendarVO vo);

	/**
	 * @Title: logicDeleteLikeCode
	 * @Description: 按照id查找
	 * @param code
	 * @return CalendarVO
	 * @throws
	 */	
	public CalendarVO findById(String id);
	
	/**
	 * @Title: findByUserId
	 * @Description: 按照userId查找
	 * @param id
	 * @return List<CalendarVO>
	 * @throws
	 */
	public List<CalendarVO> findByUserId(Long userId);
	
	/**
	 * @Title: findByIds
	 * @Description: 按照id数组查找
	 * @param id
	 * @return List<CalendarVO>
	 * @throws
	 */
	public List<CalendarVO> findByIds(String[]ids);
		
	/**
	 * @Title: queryPagedResult 
	 * @Description: 分页查找 可以按照指定字段排序
	 * @param params       参数
	 * @param currentPage     当前页
	 * @param pageSize      每页数
	 * @param sortString     排序 
	 * @return PageList<CalendarVO>
	 * @throws
	 */
	public PageList<CalendarVO> queryPagedResult(Map<String, Object> params,
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
	public PageList<CalendarVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	boolean delete(String[] ids);
	
	List<CalendarVO> findByIds(List<String> ids);

	boolean delete(List<String> ids);

}
