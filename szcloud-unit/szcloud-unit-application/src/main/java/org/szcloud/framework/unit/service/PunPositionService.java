package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.core.domain.PunPosition;
import org.szcloud.framework.unit.vo.PunPositionVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PunPositionService {
	public List<PunPositionVO> findAll();
	
	public void remove(PunPositionVO vo);
	
	/**
	 * 删除岗位与其它表的关联关系；
	 * auth:huangqr
	 * @param user
	 * @throws MRTException
	 */
	public void removeRelations(PunPosition position) throws MRTException;
	
	public void update(PunPositionVO vo,String queryStr);
	
	public void save(PunPositionVO vo);
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunPositionVO> selectPagedByExample(String queryStr, Map<String, Object> params, 
			int currentPage, int pageSize,String sortString);
	
	public PunPositionVO get(Long id);
	
	public List<PunPositionVO> selectByExample(BaseExample example);
	
	/**
	 * 查询
	 * @param queryStr 查询id
	 * @param params 参数
	 * @return
	 */
	public List<PunPositionVO> queryResult(String queryStr,Map<String, Object> params);
}