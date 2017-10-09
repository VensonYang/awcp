package org.szcloud.framework.formdesigner.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.formdesigner.application.service.CommondWordsService;
import org.szcloud.framework.formdesigner.application.vo.CommondWordsVO;
import org.szcloud.framework.formdesigner.core.domain.CommondWords;

/**
 * @ClassName: CommondWordsServiceImpl
 * @Description: 日常用语 业务层实现类
 * @author xht
 * @date 2015年4月30日 上午15:31:45
 */
@Service(value = "commondWordsServiceImpl")
public class CommondWordsServiceImpl implements CommondWordsService{

	@Override
	public String save(CommondWordsVO vo) {
		CommondWords word = BeanUtils.getNewInstance(vo, CommondWords.class);
		String id = word.save();
		vo.setId(id);
		return id;
	}

	@Override
	public Boolean delete(String id,Long user_id) {
		try {
			CommondWords word = BeanUtils.getNewInstance(id, CommondWords.class);
			word.remove(id,user_id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CommondWordsVO findById(String id) {
		CommondWordsVO vo = BeanUtils.getNewInstance(CommondWords.get(id), CommondWordsVO.class);
		return vo;
	}

	@Override
	public List<CommondWordsVO> findByUserId(String userId) {
		return BeanUtils.getNewList(CommondWords.selectByUserId(userId),CommondWordsVO.class);
	}

	@Override
	public List<CommondWordsVO> findByTypeId(String typeId) {
		return BeanUtils.getNewList(CommondWords.selectByTypeId(typeId),CommondWordsVO.class);
	}
	
}
