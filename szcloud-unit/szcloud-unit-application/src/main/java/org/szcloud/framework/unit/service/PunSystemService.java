package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.vo.PunSystemVO;
import org.szcloud.framework.unit.vo.SysDataSourceVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PunSystemService {

	public void addOrUpdate(PunSystemVO vo);

	public PunSystemVO findById(Long id);

	public List<PunSystemVO> findAll();

	public String delete(Long id);

	public List<PunSystemVO> queryResult(String queryStr, Map<String, Object> params);

	public PageList<PunSystemVO> queryPagedResult(String queryStr, Map<String, Object> params, int currentPage,
			int pageSize, String sortString);

	public PageList<PunSystemVO> selectPagedByExample(BaseExample baseExample, int currentPage, int pageSize,
			String sortString);

	List<SysDataSourceVO> getSystemDataSource(Long long1);
}
