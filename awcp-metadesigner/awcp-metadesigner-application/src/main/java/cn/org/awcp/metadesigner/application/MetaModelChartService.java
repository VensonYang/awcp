package cn.org.awcp.metadesigner.application;

import java.util.List;

import cn.org.awcp.metadesigner.vo.MetaModelChartVO;

public interface MetaModelChartService {
	
	/**
	 * 增加
	 * @param vo
	 * @return
	 */
	public long save(MetaModelChartVO vo);
	
	/**
	 * 删除
	 * @param vo
	 * @return
	 */
	public boolean delete(MetaModelChartVO vo);
	
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	public boolean update(MetaModelChartVO vo);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<MetaModelChartVO> findAll();
	
	/**
	 * 根据条件查询
	 * @param classId
	 * @return
	 */
	public List<MetaModelChartVO> queryByClassId(long classId);
		
}
