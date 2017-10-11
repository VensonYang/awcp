package cn.org.awcp.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.formdesigner.application.vo.AuthorityGroupVO;

public interface AuthorityGroupService {
	
	/**
	 * 
	 * @param dynamicPageId
	 * @return
	 */
	AuthorityGroupVO findById(String id);
	
	/**
	 * 根据动态页面ID，查询该页面下所有的权限组
	 * @param dynamicPageId
	 * @return
	 */
	List<AuthorityGroupVO> listByDynamicPageId(Long dynamicPageId);
	
	/**
	 * 
	 * 等值查询
	 * @param queryStr
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<AuthorityGroupVO> queryPagedResult(String queryStr,Map<String, Object> params, 
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
	public PageList<AuthorityGroupVO> selectPagedByExample(BaseExample example,
			int currentPage, int pageSize, String sortString);
	
	public String save(AuthorityGroupVO vo);
	
	public boolean delete(String[] ids);
	
	public List<AuthorityGroupVO> queryResult(String queryStr,Map<String, Object> params)throws MRTException;
	
}
