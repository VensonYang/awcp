package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.unit.vo.PunGroupSysVO;

public interface PunGroupSysService {
	
	public void addOrUpdate(PunGroupSysVO vo);
	
	public PunGroupSysVO findById(Long id);
	
	public List<PunGroupSysVO> findAll();
	
	public void delete(Long id);
	
	public List<PunGroupSysVO> queryResult(String queryStr,Map<String, Object> params);
	
	public PageList<PunGroupSysVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,
			String sortString);
	
	void batchDeleteByGroupId(Long groupId);
	
	/**
	 * 删除组与系统关联信息，根据系统Id和组ID
	 * @param sysId系统ID
	 * @param groupId组ID
	 */
	public void deleteBySysAndGroup(Long sysId,Long groupId);
}
