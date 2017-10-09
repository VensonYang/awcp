package org.szcloud.framework.metadesigner.application;

import java.util.List;

import org.szcloud.framework.metadesigner.vo.ProjectManageVO;

public interface ProjectManageService {
	
	/**
	 * 增加
	 * @param vo
	 * @return
	 */
	public long save(ProjectManageVO vo);
	
	/**
	 * 删除
	 * @param vo
	 * @return
	 */
	public boolean remove(ProjectManageVO vo);
	
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	public boolean update(ProjectManageVO vo);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<ProjectManageVO> findAll();
	
	/**
	 * 查询一条数据
	 * @return
	 */
	public ProjectManageVO get(long id);
	
}
