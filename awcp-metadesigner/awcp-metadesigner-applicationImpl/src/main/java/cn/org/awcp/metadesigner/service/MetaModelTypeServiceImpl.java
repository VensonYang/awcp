package cn.org.awcp.metadesigner.service;

import java.util.ArrayList;
import java.util.List;

import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.metadesigner.application.MetaModelTypeService;
import cn.org.awcp.metadesigner.core.domain.MetaModelType;
import cn.org.awcp.metadesigner.vo.MetaModelTypeVO;

public class MetaModelTypeServiceImpl implements MetaModelTypeService{

	public long save(MetaModelTypeVO vo) {
		try {
			MetaModelType mmt = BeanUtils.getNewInstance(vo,MetaModelType.class);
			mmt.save();
			vo.setId(mmt.getId());
			return mmt.getId();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean remove(MetaModelTypeVO vo) {
		try {
			MetaModelType mmt = BeanUtils.getNewInstance(vo,MetaModelType.class);
			mmt.remove();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean update(MetaModelTypeVO vo) {
		try {
			MetaModelType mmt = BeanUtils.getNewInstance(vo, MetaModelType.class);
			mmt.save();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<MetaModelTypeVO> findAll() {
		List<MetaModelType> ls = MetaModelType.findAll(MetaModelType.class);
		List<MetaModelTypeVO> list = new ArrayList<MetaModelTypeVO>();
		for(MetaModelType mm:ls){
			list.add(BeanUtils.getNewInstance(mm, MetaModelTypeVO.class));
		}
		return list;
	}

}
