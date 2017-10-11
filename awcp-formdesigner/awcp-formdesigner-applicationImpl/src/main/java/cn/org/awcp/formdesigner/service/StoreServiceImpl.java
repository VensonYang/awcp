package cn.org.awcp.formdesigner.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.Criteria;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.core.utils.SessionUtils;
import cn.org.awcp.core.utils.Tools;
import cn.org.awcp.core.utils.constants.SessionContants;
import cn.org.awcp.formdesigner.application.service.StoreService;
import cn.org.awcp.formdesigner.application.vo.DynamicPageVO;
import cn.org.awcp.formdesigner.application.vo.StoreVO;
import cn.org.awcp.formdesigner.core.domain.DynamicPage;
import cn.org.awcp.formdesigner.core.domain.Store;
import cn.org.awcp.unit.core.domain.PunResource;
import cn.org.awcp.unit.vo.PunResourceVO;
import cn.org.awcp.unit.vo.PunSystemVO;
import cn.org.awcp.unit.vo.PunUserBaseInfoVO;

/**
 * @ClassName: StoreServiceImpl
 * @Description: Store业务层实现类
 * @author zyg zyg166@163.com
 * @date 2014年11月6日 上午9:57:45
 */
@Service(value = "storeServiceImpl")
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	@Qualifier("queryChannel")
	private QueryChannelService queryChannel;

	// 0为保存初始状态，1为逻辑删除

	@Override
	public String save(StoreVO vo) throws MRTException {
		Store store = BeanUtils.getNewInstance(vo, Store.class);
		Object obj = SessionUtils.getObjectFromSession(SessionContants.CURRENT_USER);
		Date now = new Date();
		if (obj instanceof PunUserBaseInfoVO) {
			PunUserBaseInfoVO user = (PunUserBaseInfoVO) obj;
			if (store.getId() == null) { // 新增
				store.setCreatedUser(user.getName());
				store.setCreated(now);
				store.setIsCheckOut(1);
				store.setCheckOutUser(user.getName());
			}
			store.setUpdatedUser(user.getName());
		}
		store.setUpdated(now);
		String id = store.save();
		vo.setId(id);
		return id;
	}

	@Override
	public boolean delete(StoreVO vo) {
		try {
			Store store = BeanUtils.getNewInstance(vo, Store.class);
			store.remove();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String[] ids) {
		try {
			for (String id : ids) {
				Store store = BeanUtils.getNewInstance(Store.get(id), Store.class);
				store.remove();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(List<String> ids) {
		try {
			for (String id : ids) {
				Store store = BeanUtils.getNewInstance(Store.get(id), Store.class);
				store.remove();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public StoreVO findById(String id) throws MRTException {
		/*
		 * Map<String ,Object> params = new HashMap<String,Object>();
		 * params.put("id", id); Store store =
		 * queryChannel.querySingleResult(Store.class, "get", params);
		 */
		StoreVO vo = BeanUtils.getNewInstance(Store.get(id), StoreVO.class);
		return vo;
	}

	@Override
	public PageList<StoreVO> queryPagedResult(Map<String, Object> params, int currentPage, int pageSize,
			String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			params.put("systemId", system.getSysId());
		}
		PageList<Store> result = queryChannel.queryPagedResult(Store.class, "eqQueryList", params, currentPage,
				pageSize, sortString);
		List<StoreVO> tmp = new ArrayList<StoreVO>();
		for (Store store : result) {
			tmp.add(BeanUtils.getNewInstance(store, StoreVO.class));
		}
		PageList<StoreVO> resultVO = new PageList<StoreVO>(tmp, result.getPaginator());
		result.clear();
		return resultVO;
	}

	// 物理删除
	@Override
	public boolean deleteLikeCode(String code) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", code);
			Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
			if (obj instanceof PunSystemVO) {
				PunSystemVO system = (PunSystemVO) obj;
				params.put("systemId", system.getSysId());
			}
			queryChannel.excuteMethod(Store.class, "deleteLikeCode", params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PageList<StoreVO> selectPagedByExample(BaseExample baseExample, int currentPage, int pageSize,
			String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			if (baseExample.getOredCriteria().size() > 0) {
				baseExample.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			} else {
				baseExample.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
			}

		}
		PageList<Store> result = queryChannel.selectPagedByExample(Store.class, baseExample, currentPage, pageSize,
				sortString);
		PageList<StoreVO> resultVO = new PageList<StoreVO>(result.getPaginator());
		for (Store dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, StoreVO.class));
		}
		result.clear();
		return resultVO;
	}

	@Override
	public StoreVO findByCode(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			params.put("systemId", system.getSysId());

		}
		PageList<Store> result = queryChannel.queryPagedResult(Store.class, "eqQueryList", params, 1, Integer.MAX_VALUE,
				"code.desc");
		if (result.size() > 0)
			return BeanUtils.getNewInstance(result.get(0), StoreVO.class);
		return null;
	}

	@Override
	public List<StoreVO> findByIds(String[] ids) {
		List<StoreVO> ret = new ArrayList<StoreVO>();
		for (String id : ids) {
			Store store = Store.get(id);
			ret.add(BeanUtils.getNewInstance(store, StoreVO.class));
		}
		return ret;
	}

	@Override
	public List<StoreVO> findByIds(List<String> ids) {
		List<StoreVO> ret = new ArrayList<StoreVO>();
		for (String id : ids) {
			Store store = Store.get(id);
			ret.add(BeanUtils.getNewInstance(store, StoreVO.class));
		}
		return ret;
	}

	@Override
	public PageList<StoreVO> findByDyanamicPageId(Long dyanamicPageId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dynamicPageId", dyanamicPageId);
		BaseExample baseExample = new BaseExample();
		baseExample.createCriteria().andEqualTo("DYNAMICPAGE_ID", dyanamicPageId);

		PageList<StoreVO> list = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, null);
		return list;
	}

	@Override
	public PageList<JSONObject> findComponentByDyanamicPageId(Long dyanamicPageId) {
		BaseExample baseExample = new BaseExample();
		baseExample.createCriteria().andEqualTo("DYNAMICPAGE_ID", dyanamicPageId).andLike("CODE",
				StoreService.COMPONENT_CODE + "%");
		PageList<StoreVO> list = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, " T_ORDER ASC");
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			if (baseExample.getOredCriteria().size() > 0) {
				baseExample.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			} else {
				baseExample.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
			}
		}
		PageList<JSONObject> ret = new PageList<JSONObject>(list.getPaginator());
		for (StoreVO store : list) {
			JSONObject component = JSON.parseObject(store.getContent());
			ret.add(component);
		}
		return ret;
	}

	@Override
	public PageList<JSONObject> findParentComponentByDyanamicPageId(Long dyanamicPageId) {
		BaseExample baseExample = new BaseExample();
		baseExample.createCriteria().andLike("CODE", StoreService.COMPONENT_CODE + "%").andLike("CONTENT",
				"%\"componentType\":\"1012\",%\"relatePageId\":\"" + dyanamicPageId + "\"%");
		BaseExample baseExample1 = new BaseExample();
		baseExample1.createCriteria().andLike("CODE", StoreService.COMPONENT_CODE + "%").andLike("CONTENT",
				"%\"componentType\":\"1013\",%\"relatePageId\":\"" + dyanamicPageId + "\"%");
		PageList<StoreVO> list = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, " T_ORDER ASC");
		PageList<StoreVO> list1 = selectPagedByExample(baseExample1, 1, Integer.MAX_VALUE, " T_ORDER ASC");
		list.addAll(list1);
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			if (baseExample.getOredCriteria().size() > 0) {
				baseExample.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			} else {
				baseExample.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
			}

		}

		PageList<JSONObject> ret = new PageList<JSONObject>(list.getPaginator());
		for (StoreVO store : list) {
			JSONObject component = JSON.parseObject(store.getContent());
			Long pageId = component.getLong("dynamicPageId");
			if (!pageId.equals(dyanamicPageId)) {
				ret.addAll(findParentComponentByDyanamicPageId(component.getLong("dynamicPageId")));
			}
			ret.add(component);
		}

		return ret;
	}

	@Override
	public List<StoreVO> copyByDynamicPageId(Long sourceId, Long targetId) {
		DynamicPage source = DynamicPage.get(DynamicPage.class, sourceId);
		DynamicPage target = DynamicPage.get(DynamicPage.class, targetId);
		DynamicPageVO sourceVO = BeanUtils.getNewInstance(source, DynamicPageVO.class);
		DynamicPageVO targetVO = BeanUtils.getNewInstance(target, DynamicPageVO.class);
		return copyByDynamicPage(sourceVO, targetVO);
	}

	@Override
	public List<StoreVO> copyByDynamicPage(DynamicPageVO sourceVO, DynamicPageVO targetVO) {

		List<StoreVO> rtn = new ArrayList<StoreVO>();
		// 首先查出所有layout，然后保存，同时保存old和new的id映射，为后面的组件所属layout做准备
		BaseExample baseExample = new BaseExample();
		baseExample.createCriteria().andLike("code", StoreService.LAYOUT_CODE + "%").andEqualTo("DYNAMICPAGE_ID",
				sourceVO.getId());

		PageList<StoreVO> layouts = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, null);

		Map<String, String> layoutIds = new HashMap<String, String>();

		for (StoreVO vo : layouts) {
			String id = vo.getId();
			vo.setId(null);
			vo.setDynamicPageId(targetVO.getId());
			JSONObject object = JSONObject.parseObject(vo.getContent());
			object.put("dynamicPageId", targetVO.getId());
			object.put("dynamicPageName", targetVO.getName());
			object.put("systemId", targetVO.getSystemId());
			vo.setContent(object.toJSONString());
			vo.setSystemId(targetVO.getSystemId());
			save(vo);
			layoutIds.put(id, vo.getId());
			rtn.add(vo);
		}
		// 更新父ID数据
		for (StoreVO vo : rtn) {
			if (StringUtils.isNotBlank(vo.getDescription())) {
				String newId = layoutIds.get(vo.getDescription());
				vo.setDescription(newId);
				JSONObject object = JSONObject.parseObject(vo.getContent());
				object.put("parentId", newId);
				vo.setContent(object.toJSONString());
				save(vo);
			}
		}

		baseExample = new BaseExample();
		baseExample.createCriteria().andLike("code", StoreService.COMPONENT_CODE + "%").andEqualTo("DYNAMICPAGE_ID",
				sourceVO.getId());

		PageList<StoreVO> components = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, null);
		for (StoreVO vo : components) {
			vo.setId(null);
			vo.setDynamicPageId(targetVO.getId());
			vo.setSystemId(targetVO.getSystemId());
			JSONObject object = JSONObject.parseObject(vo.getContent());
			object.put("dynamicPageId", targetVO.getId());
			object.put("dynamicPageName", targetVO.getName());
			object.put("systemId", targetVO.getSystemId());
			String id = object.getString("layoutId");
			object.put("layoutId", layoutIds.get(id));
			vo.setContent(object.toJSONString());
			save(vo);
			rtn.add(vo);
		}

		baseExample = new BaseExample();
		baseExample.createCriteria().andLike("code", StoreService.PAGEACT_CODE + "%").andEqualTo("DYNAMICPAGE_ID",
				sourceVO.getId());

		PageList<StoreVO> acts = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, null);
		for (StoreVO vo : acts) {
			vo.setId(null);
			vo.setDynamicPageId(targetVO.getId());
			vo.setSystemId(targetVO.getSystemId());
			JSONObject object = JSONObject.parseObject(vo.getContent());
			object.put("dynamicPageId", targetVO.getId());
			object.put("dynamicPageName", targetVO.getName());
			object.put("systemId", targetVO.getSystemId());
			vo.setContent(object.toJSONString());
			save(vo);
			rtn.add(vo);
			PunResourceVO resource = new PunResourceVO();
			resource.setSysId(vo.getSystemId());
			resource.setResouType("3");// 3=按钮;
			resource.setResourceName(vo.getName());
			resource.setRelateResoId(vo.getId());
			PunResource punResource = BeanUtils.getNewInstance(resource, PunResource.class);
			punResource.save();

		}
		return rtn;
	}

	@Override
	public List<StoreVO> copy(String actIds, Long dynamicPageId, int order) {
		List<StoreVO> storeList = new ArrayList<StoreVO>();
		DynamicPage dynamicPage = DynamicPage.get(DynamicPage.class, dynamicPageId);
		if (dynamicPage != null && StringUtils.isNotBlank(actIds)) {
			String[] ids = actIds.split(",");
			BaseExample baseExample = new BaseExample();
			for (String id : ids) {
				Criteria c = baseExample.createCriteria().andEqualTo("id", id);
				baseExample.or(c);
			}
			PageList<Store> result = queryChannel.selectPagedByExample(Store.class, baseExample, 1, Integer.MAX_VALUE,
					null);
			for (Store s : result) {
				String name = s.getName() + "_copy";
				s.setId(null);
				s.setOrder(order);
				s.setDynamicPageId(dynamicPageId);
				s.setName(name);
				JSONObject object = JSONObject.parseObject(s.getContent());
				object.put("order", order++);
				object.put("dynamicPageId", dynamicPageId);
				object.put("dynamicPageName", dynamicPage.getName());
				object.put("name", name);
				s.setContent(object.toJSONString());
				String newId = s.save();
				s.setId(newId);
				PunResourceVO resource = new PunResourceVO();
				resource.setSysId(s.getSystemId());
				resource.setResouType("3");// 3=按钮;
				resource.setResourceName(name);
				resource.setRelateResoId(s.getId());
				PunResource punResource = BeanUtils.getNewInstance(resource, PunResource.class);
				punResource.save();
			}
			for (Store store : result) {
				storeList.add(BeanUtils.getNewInstance(store, StoreVO.class));
			}
		}
		return storeList;
	}

	@Override
	public List<StoreVO> queryComponentByLayoutId(String layoutID, Long systemId) {
		BaseExample baseExample = new BaseExample();
		String queryStr = "\"layoutId\":\"" + layoutID;
		baseExample.createCriteria().andLike("code", StoreService.COMPONENT_CODE + "%")
				.andLike("content", "%" + queryStr + "%").andEqualTo("SYSTEM_ID", systemId);

		List<Store> result = Store.selectByExample(baseExample);
		List<StoreVO> storeList = new ArrayList<StoreVO>();
		for (Store store : result) {
			storeList.add(BeanUtils.getNewInstance(store, StoreVO.class));
		}
		return storeList;

	}

	@Override
	public boolean freshComponentOrder(String dynamicPageId) {
		try {
			BaseExample baseExample = new BaseExample();
			baseExample.createCriteria().andEqualTo("DYNAMICPAGE_ID", dynamicPageId).andLike("CODE",
					StoreService.COMPONENT_CODE + "%");
			List<StoreVO> storeVOs = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, "T_ORDER ASC");
			for (StoreVO vo : storeVOs) {
				Integer oldOrder = vo.getOrder();
				if (oldOrder == null) {
					oldOrder = 0;
				}
				Integer newOrder = oldOrder * 10;
				vo.setOrder(newOrder);
				String content = vo.getContent();
				if (StringUtils.isNotBlank(content)) {
					JSONObject obj = JSON.parseObject(content);
					obj.put("order", newOrder);
					vo.setContent(obj.toJSONString());
				}
				this.save(vo);
			}
			return true;
		} catch (MRTException e) {

			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean freshLayoutOrder(String dynamicPageId) {
		try {
			BaseExample baseExample = new BaseExample();
			baseExample.createCriteria().andEqualTo("DYNAMICPAGE_ID", dynamicPageId).andLike("CODE",
					StoreService.LAYOUT_CODE + "%");
			List<StoreVO> storeVOs = this.selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, "T_ORDER ASC");
			for (StoreVO vo : storeVOs) {
				Integer oldOrder = vo.getOrder();
				if (oldOrder == null) {
					oldOrder = 0;
				}
				Integer newOrder = oldOrder * 10;
				vo.setOrder(newOrder);
				String content = vo.getContent();
				if (StringUtils.isNotBlank(content)) {
					JSONObject obj = JSON.parseObject(content);
					obj.put("order", newOrder);
					vo.setContent(obj.toJSONString());
				}
				this.save(vo);
			}
			return true;
		} catch (MRTException e) {
			e.printStackTrace();
			return false;
		}
	}

}
