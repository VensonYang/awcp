package cn.org.awcp.metadesigner.application;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.metadesigner.vo.MetaModelVO;

public interface MetaModelService {
	
	/**
	 * 增加
	 * @param vo
	 */
	public Long save(MetaModelVO vo);
	
	/**
	 * 删除
	 * @param vo
	 */
	public boolean remove(MetaModelVO vo);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<MetaModelVO> findAll();	
	
	/**
	 * 查询一条数据
	 * @param id
	 * @return
	 */
	public MetaModelVO get(Long id);
	
	/**
	 * 分页查询
	 * @param queryStr
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<MetaModelVO> queryResult(String queryStr, Map<String, Object> params, int currentPage, int pageSize,String sortString);
		
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 */
	public boolean tableIsExist(String tableName);
	
	/**
	 * 动态Sql
	 * @param sql
	 * @return
	 */
	public boolean excuteSql(String sql);
	
	/**
	 * 查询一条
	 * @param id
	 * @return
	 */
	public MetaModelVO load(long id);
	
	/**
	 * 根据tableName查找
	 * @param queryStr
	 * @param tableName
	 * @return
	 */
	public List<MetaModelVO> queryMetaModel(String queryStr,String modelCode,String tableName,String projectName);
	
	/**
	 * 修改
	 * @param queryStr
	 * @param vo
	 */
	public boolean update(String queryStr,MetaModelVO vo);
	
	/**
	 * 根据模型名称查找
	 * @param modelCode
	 * @return
	 */
	public MetaModelVO queryByModelCode(String modelCode);
	
	/**
	 * 根据模型名称查找
	 * @param modelCode
	 * @return
	 */
	public MetaModelVO queryByModelCode(String modelCode, Long systemId);
	
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
	public PageList<MetaModelVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize, String sortString);
	
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
	public PageList<MetaModelVO> queryPagedResult(Map<String, Object> params,
			int currentPage, int pageSize, String sortString);
	
}
