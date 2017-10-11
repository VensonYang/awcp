package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.unit.vo.PunSystemVO;
import cn.org.awcp.unit.vo.SysDataSourceVO;

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
