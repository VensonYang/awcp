package cn.org.awcp.metadesigner.application;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.metadesigner.vo.DataSourceManageVO;

public interface DataSourceManageService {
	
	/**
	 * 增加
	 * @param vo
	 * @return
	 */
	public long save(DataSourceManageVO vo);
	
	/**
	 * 删除
	 * @param vo
	 * @return
	 */
	public boolean delete(DataSourceManageVO vo);
	
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	public boolean update(DataSourceManageVO vo);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<DataSourceManageVO> findAll();
	
	/**
	 * 查询一条数据
	 */
	public DataSourceManageVO get(long id);
	
	/**
	 * 根据数据源的名称查询
	 * @param name
	 * @return
	 */
	public DataSourceManageVO queryDataSourceByName(String name);
	
	/**
	 * 模糊查询
	 * @param baseExample
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<DataSourceManageVO> selectPagedByExample(BaseExample baseExample, 
			int currentPage, int pageSize,String sortString);
	
	public DataSourceManageVO queryDataSourceByNameAndSystemId(String name,Long systemId);
}
