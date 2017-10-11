package cn.org.awcp.metadesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.core.utils.Tools;
import cn.org.awcp.core.utils.constants.SessionContants;
import cn.org.awcp.metadesigner.application.MetaModelService;
import cn.org.awcp.metadesigner.core.domain.MetaModel;
import cn.org.awcp.metadesigner.vo.MetaModelVO;
import cn.org.awcp.unit.vo.PunSystemVO;

@Service(value = "metaModelServiceImpl")
public class MetaModelServiceImpl implements MetaModelService {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(MetaModelServiceImpl.class);
	
	@Resource(name = "queryChannel")
	private QueryChannelService queryChannel;

	public Long save(MetaModelVO vo) {
		try {
			MetaModel model = BeanUtils.getNewInstance(vo, MetaModel.class);
			model.save();
			vo.setId(model.getId());
			return model.getId();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean remove(MetaModelVO vo) {
		try {
			MetaModel model = BeanUtils.getNewInstance(vo, MetaModel.class);
			model.remove();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public <T extends MetaModelVO> void remove(Set<T> entities) {
		try {
			for (MetaModelVO entityVo : entities) {
				MetaModel model = BeanUtils.getNewInstance(entityVo, MetaModel.class);
				model.save();
			}
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}

	public List<MetaModelVO> selectByExample(BaseExample example) {
		List<MetaModel> result = MetaModel.selectByExample(MetaModel.class, example);
		List<MetaModelVO> resultVo = new ArrayList<MetaModelVO>();
		for (MetaModel mm : result) {
			resultVo.add(BeanUtils.getNewInstance(mm, MetaModelVO.class));
		}
		result.clear();
		return resultVo;
	}

	public PageList<MetaModelVO> selectPagedByExample(BaseExample baseExample, int currentPage, int pageSize,String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			if (baseExample.getOredCriteria().size() > 0) {
				baseExample.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			} else {
				baseExample.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
			}
		}
		PageList<MetaModel> result = queryChannel.selectPagedByExample(MetaModel.class, baseExample, 
				currentPage,pageSize, sortString);
		List<MetaModelVO> resultVO = new ArrayList<MetaModelVO>();
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, MetaModelVO.class));
		}
		PageList<MetaModelVO> pl = new PageList<MetaModelVO>(resultVO, result.getPaginator());
		result.clear();
		return pl;
	}

	public List<MetaModelVO> findAll() {
		List<MetaModel> result = MetaModel.findAll();
		List<MetaModelVO> resultVo = new ArrayList<MetaModelVO>();
		for (MetaModel mm : result) {
			resultVo.add(BeanUtils.getNewInstance(mm, MetaModelVO.class));
		}
		result.clear();
		return resultVo;
	}

	public PageList<MetaModelVO> eqQueryPagedResult(MetaModelVO vo, int currentPage, int pageSize, String sortString) {
		MetaModel sm = BeanUtils.getNewInstance(vo, MetaModel.class);
		PageList<MetaModel> result = queryChannel.eqQueryPagedResult(MetaModel.class, sm, currentPage, pageSize, sortString);
		List<MetaModelVO> resultvo = new ArrayList<MetaModelVO>();
		for (MetaModel smm : result) {
			resultvo.add(BeanUtils.getNewInstance(smm, MetaModelVO.class));
		}
		result.clear();
		return new PageList<MetaModelVO>(resultvo, result.getPaginator());
	}

	public MetaModelVO get(Long id) {
		try {
			MetaModel model = MetaModel.get(MetaModel.class, id);
			return BeanUtils.getNewInstance(model, MetaModelVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}

	public PageList<MetaModelVO> queryResult(String queryStr, Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			params.put("systemId", system.getSysId());
		}
		PageList<MetaModel> pl = queryChannel.queryPagedResult(MetaModel.class, queryStr, params, 
				currentPage, pageSize,sortString);
		PageList<MetaModelVO> list = new PageList<MetaModelVO>(pl.getPaginator());
		for (MetaModel m : pl) {
			MetaModelVO vo = BeanUtils.getNewInstance(m, MetaModelVO.class);
			list.add(vo);
		}
		return list;
	}

	public boolean tableIsExist(String tableName) {
		try {
			String sql = "select * from " + tableName;
			return excuteSql(sql);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean excuteSql(String sql) {
		try {
			MetaModel.getRepository().excuteSql(sql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public MetaModelVO load(long id) {
		try {
			MetaModel mm = MetaModel.load(MetaModel.class, id);
			MetaModelVO mmo = BeanUtils.getNewInstance(mm, MetaModelVO.class);
			logger.debug(mmo.getModelName());
			return mmo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<MetaModelVO> queryMetaModel(String queryStr, String modelCode, String tableName, String projectName) {
		List<MetaModelVO> ls = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Object obj = Tools.getObjectFromSession(SessionContants.CURRENT_SYSTEM);
			if (obj instanceof PunSystemVO) {
				PunSystemVO system = (PunSystemVO) obj;
				map.put("systemId", system.getSysId());
			}
			map.put("tableName", tableName);
			map.put("modelCode", modelCode);
			map.put("projectName", projectName);
			List<MetaModel> list = queryChannel.queryResult(MetaModel.class, queryStr, map);
			ls = new ArrayList<MetaModelVO>();
			for (MetaModel mm : list) {
				ls.add(BeanUtils.getNewInstance(mm, MetaModelVO.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;

	}

	public boolean update(String queryStr, MetaModelVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		map.put("modelClassId", vo.getModelClassId());
		map.put("modelName", vo.getModelName());
		map.put("modelCode", vo.getModelCode());
		map.put("modelDesc", vo.getModelDesc());
		map.put("tableName", vo.getTableName());
		map.put("projectName", vo.getProjectName());
		map.put("modelType", vo.getModelType());
		map.put("modelSynchronization", vo.getModelSynchronization());
		map.put("modelValid", vo.getModelValid());
		map.put("systemId", vo.getSystemId());
		map.put("dataSourceId", vo.getDataSourceId());
		try {
			MetaModel.getRepository().executeUpdate(queryStr, map, MetaModel.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public MetaModelVO queryByModelCode(String modelCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelCode", modelCode);
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			map.put("systemId", system.getSysId());
		} else {
			obj = Tools.getObjectFromSession(SessionContants.CURRENT_SYSTEM);
			if (obj instanceof PunSystemVO) {
				PunSystemVO system = (PunSystemVO) obj;
				map.put("systemId", system.getSysId());
			}
		}
		MetaModel mm = queryChannel.querySingleResult(MetaModel.class, "queryByModelCode", map);
		MetaModelVO vo = BeanUtils.getNewInstance(mm, MetaModelVO.class);
		return vo;
	}

	public MetaModelVO queryByModelCode(String modelCode, Long systemId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelCode", modelCode);
		map.put("systemId", systemId);
		MetaModel mm = queryChannel.querySingleResult(MetaModel.class, "queryByModelCode", map);
		MetaModelVO vo = BeanUtils.getNewInstance(mm, MetaModelVO.class);
		return vo;
	}

	public PageList<MetaModelVO> queryPagedResult(Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.CURRENT_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			params.put("systemId", system.getSysId());
		}
		PageList<MetaModel> result = queryChannel.queryPagedResult(MetaModel.class, "eqQueryList", params, 
				currentPage,pageSize, sortString);
		List<MetaModelVO> tmp = new ArrayList<MetaModelVO>();
		for (MetaModel model : result) {
			tmp.add(BeanUtils.getNewInstance(model, MetaModelVO.class));
		}
		PageList<MetaModelVO> resultVO = new PageList<MetaModelVO>(tmp, result.getPaginator());
		result.clear();
		return resultVO;
	}

}
