package org.szcloud.framework.formdesigner.application.service;

import java.util.List;

import org.szcloud.framework.formdesigner.application.vo.CommondWordsVO;

public interface CommondWordsService {

	/**
	 * 仅保存或更新日常用语
	 * 
	 * @param vo
	 * @return
	 */
	String save(CommondWordsVO vo);

	/**
	 * 删除日常用语
	 * 
	 * @param vo
	 * @return
	 */
	Boolean delete(String id,Long user_id);

	/**
	 * 
	 * 根据ID在数据库中查找出来
	 * 
	 * @param id
	 * @return
	 */
	CommondWordsVO findById(String id);

	/**
	 * 条件 查询 CommondWordsVO
	 * 
	 * @param baseExample
	 * @param sortString
	 * @return
	 */
	List<CommondWordsVO> findByUserId(String userId);
	
	/**
	 * 根据类型查找
	 * 
	 * @param typeId
	 * @return
	 */
	public List<CommondWordsVO> findByTypeId(String typeId);
}
