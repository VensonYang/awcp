package org.szcloud.framework.formdesigner.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.core.utils.SessionUtils;
import org.szcloud.framework.core.utils.Tools;
import org.szcloud.framework.core.utils.constants.SessionContants;
import org.szcloud.framework.formdesigner.application.service.FormdesignerService;
import org.szcloud.framework.formdesigner.application.service.StoreService;
import org.szcloud.framework.formdesigner.application.vo.DynamicPageVO;
import org.szcloud.framework.formdesigner.application.vo.StoreVO;
import org.szcloud.framework.formdesigner.core.domain.DynamicPage;
import org.szcloud.framework.formdesigner.core.domain.PfmTemplate;
import org.szcloud.framework.formdesigner.core.domain.Store;
import org.szcloud.framework.formdesigner.core.domain.design.context.act.PageAct;
import org.szcloud.framework.formdesigner.core.engine.FreeMarkers;
import org.szcloud.framework.formdesigner.core.parse.bean.PageActBeanWorker;
import org.szcloud.framework.unit.service.PunResourceService;
import org.szcloud.framework.unit.vo.PunSystemVO;
import org.szcloud.framework.unit.vo.PunUserBaseInfoVO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class FormdesignerServiceImpl implements FormdesignerService {
	private static final Logger logger = LoggerFactory.getLogger(FormdesignerService.class);

	@Autowired
	private QueryChannelService queryChannel;

	@Autowired
	@Qualifier("punResourceServiceImpl")
	private PunResourceService punResourceService;

	@Autowired
	@Qualifier("storeServiceImpl")
	private StoreService storeService;

	@Override
	public DynamicPageVO findById(long id) throws MRTException {
		DynamicPage dp = DynamicPage.get(DynamicPage.class, id);
		DynamicPageVO vo = BeanUtils.getNewInstance(dp, DynamicPageVO.class);
		return vo;
	}

	@Override
	public void saveOrUpdate(DynamicPageVO dpvo) throws MRTException {
		DynamicPage dp = BeanUtils.getNewInstance(dpvo, DynamicPage.class);
		Object obj = SessionUtils.getObjectFromSession(SessionContants.CURRENT_USER);
		Date now = new Date();
		if (obj instanceof PunUserBaseInfoVO) {
			PunUserBaseInfoVO user = (PunUserBaseInfoVO) obj;
			if (dpvo.getId() == null) { // 新增
				dp.setCreatedUser(user.getName());
				dp.setCreated(now);
			}
			dp.setUpdatedUser(user.getName());
		}
		dp.setUpdated(now);
		dp.save();
		dpvo.setId(dp.getId());
	}

	@Override
	public void updateWorkflowInfo(DynamicPageVO vo) throws MRTException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", vo.getId());
		params.put("workflowNodeInfo", vo.getWorkflowNodeInfo());

		// queryChannel.excuteMethod(DynamicPage.class, "updateWorkflowInfo",
		// params);
		DynamicPage.getRepository().executeUpdate("updateWorkflowInfo", params, DynamicPage.class);

	}

	@Override
	public void updateModelInfo(DynamicPageVO vo) throws MRTException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", vo.getId());
		params.put("dataJson", vo.getDataJson());
		DynamicPage.getRepository().executeUpdate("updateModelInfo", params, DynamicPage.class);
	}

	@Override
	public List<DynamicPageVO> findAll() {
		BaseExample example = new BaseExample();
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			example.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
		}
		PageList<DynamicPage> list = queryChannel.selectPagedByExample(DynamicPage.class, example, 1, Integer.MAX_VALUE,
				null);
		PageList<DynamicPageVO> resultVo = new PageList<DynamicPageVO>(list.getPaginator());
		for (DynamicPage mm : list) {
			resultVo.add(BeanUtils.getNewInstance(mm, DynamicPageVO.class));
		}
		return resultVo;
	}

	@Override
	public PageList<DynamicPageVO> queryPagedResult(String queryStr, Map<String, Object> params, int currentPage,
			int pageSize, String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			params.put("systemId", system.getSysId());
		}
		PageList<DynamicPage> groups = queryChannel.queryPagedResult(DynamicPage.class, queryStr, params, currentPage,
				pageSize, sortString);
		List<DynamicPageVO> tmp = new ArrayList<DynamicPageVO>();
		for (DynamicPage group : groups) {
			tmp.add(BeanUtils.getNewInstance(group, DynamicPageVO.class));
		}
		PageList<DynamicPageVO> vos = new PageList<DynamicPageVO>(tmp, groups.getPaginator());
		groups.clear();
		return vos;
	}

	@Override
	public PageList<DynamicPageVO> selectPagedByExample(BaseExample example, int currentPage, int pageSize,
			String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			if (example.getOredCriteria().size() > 0) {
				example.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			} else {
				example.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
			}
		}
		PageList<DynamicPage> list = queryChannel.selectPagedByExample(DynamicPage.class, example, currentPage,
				pageSize, sortString);
		PageList<DynamicPageVO> vos = new PageList<DynamicPageVO>(list.getPaginator());
		for (DynamicPage dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, DynamicPageVO.class));
		}
		list.clear();
		return vos;
	}

	public void delete(List<Long> ids) throws MRTException {
		for (Long id : ids) {
			DynamicPage dp = DynamicPage.get(DynamicPage.class, id);
			dp.remove();
		}
	}

	@Override
	public List<DynamicPageVO> queryResult(String queryStr, Map<String, Object> params) {
		List<DynamicPage> result = queryChannel.queryResult(DynamicPage.class, queryStr, params);
		List<DynamicPageVO> resultVO = new ArrayList<DynamicPageVO>();
		for (DynamicPage dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, DynamicPageVO.class));
		}
		result.clear();
		return resultVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.szcloud.framework.formdesigner.application.service.
	 * FormdesignerService#getWfByStartNode(java.lang.Long)
	 */
	@Override
	public String getWfByStartNode(Long pageId) {
		// [{workId,workName}]
		JSONArray rtn = new JSONArray();
		try {
			DynamicPage page = DynamicPage.get(DynamicPage.class, pageId);
			if (null != page) {
				String jsonStr = page.getWorkflowNodeInfo();
				if (!StringUtils.isEmpty(jsonStr)) {
					JSONObject nodes = JSON.parseObject(jsonStr);
					Collection<Object> objects = nodes.values();
					for (Object n : objects) {
						JSONObject tmp = (JSONObject) n;
						if (tmp.getIntValue("priority") == 1) {
							JSONObject workflow = new JSONObject();
							workflow.put("workId", tmp.getString("workflowId"));
							workflow.put("workName", tmp.getString("workflowName"));
							rtn.add(workflow);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn.toJSONString();
	}

	@Override
	public DynamicPageVO publish(DynamicPageVO vo) {
		String disStr = this.generateDisTemplateByPageId(vo);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateContext", disStr);
		params.put("id", vo.getId());
		queryChannel.excuteMethod(DynamicPage.class, "updateTemplateContext", params);
		return findById(vo.getId());
	}

	public String generateDisTemplateByPageId(DynamicPageVO vo) {
		logger.debug(" generateDisTemplateByPageId {} [id : {}]", vo.getName(), vo.getId());
		Map<String, Object> root = new HashMap<String, Object>();
		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("dynamicPage_id", vo.getId());
		List<Store> children = Store.selectByExample(example);
		List<JSONObject> mainLayouts = new ArrayList<JSONObject>();
		List<JSONObject> coms = new ArrayList<JSONObject>();
		List<JSONObject> tipsComs = new ArrayList<JSONObject>();
		Map<String, PageAct> pageActs = new HashMap<String, PageAct>();
		List<PageAct> pageActsList = new ArrayList<PageAct>();
		// 布局组件
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		Map<String, List<JSONObject>> components = new HashMap<String, List<JSONObject>>();
		// 隱藏框組件
		List<JSONObject> hiddenCom = new ArrayList<JSONObject>();
		// tab页主要页面包含所有子页面的所有的dataJson
		JSONArray dataJson = new JSONArray();

		Map<String, Long> pageActPermission = new HashMap<String, Long>();
		// key:componentId value:对应的校验list<JSONObject>
		Map<String, List<JSONObject>> valdatorsMap = new HashMap<String, List<JSONObject>>();
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Store o = children.get(i);
				if (o.getCode().indexOf(StoreService.LAYOUT_CODE) != -1) {
					// SimpleComponent c = JSON.parseObject(o.getContent(),
					// SimpleComponent.class);
					JSONObject c = JSON.parseObject(o.getContent());
					map.put(c.getString("pageId"), c);
				} else if (o.getCode().indexOf(StoreService.COMPONENT_CODE) != -1) {
					JSONObject c = JSONObject.parseObject(o.getContent()); // 取组件
					if (c != null) {
						logger.debug("start format component {} [id : {}] ", c.getString("name"),
								c.getString("pageId"));
						// 对于包含页面组件，特殊处理，根据包含的页面，解析后得到要发布的模板string，修改
						// 数据源dataItemCode 以及 组件id，组件name
						if ("1012".equals(c.getString("componentType"))
								|| "1013".equals(c.getString("componentType"))) {
							// c为包含组件
							Long relatePageId = c.getLong("relatePageId");
							logger.debug("start contain component {} [id : {}]  relatedPageId is {}",
									c.getString("name"), c.getString("pageId"), relatePageId);
							DynamicPageVO relaPageVO = findById(relatePageId);
							String relaPageStr = generateDisTemplateByPageId(relaPageVO);
							JSONArray relComArray = c.getJSONArray("configures");
							if (relComArray != null && relComArray.size() > 0) {
								for (int k = 0; k < relComArray.size(); k++) { // 遍历更改包含页面中组件的dataItemCode，pageId，name
									JSONObject relCom = (JSONObject) relComArray.get(k);
									String relComId = relCom.getString("pageId");
									Store relComStore = Store.get(relComId);
									if (relComStore != null) {
										JSONObject relComJson = JSON.parseObject(relComStore.getContent());
										String oraignDataCode = relComJson.getString("dataItemCode");
										String newDataCode = relCom.getString("dataItemCode");
										if (newDataCode == null || newDataCode == null || relaPageStr == null) {
											logger.debug(c.getString("pageId") + "name :  " + c.getString("name")
													+ "oraignDataCode   :" + oraignDataCode + "newDataCode:   "
													+ newDataCode + "componentId + " + relComStore.getId());
										}
										// 如果原片段页面未配置数据源，则执行添加操作

										// 如果原片段页面配置了数据源，则执行替换操作
										relaPageStr = relaPageStr.replace(oraignDataCode, newDataCode);

										relaPageStr = relaPageStr.replace(relCom.getString("pageId"),
												c.getString("name") + "_" + relCom.getString("pageId")); // 包含页面的组件pageId加前缀
										relaPageStr = relaPageStr.replace(relComJson.getString("name"),
												c.getString("name") + "_" + relComJson.getString("name")); // 包含页面的组件name加前缀
									}
								}
							}
							// logger.debug("relaPageStr" + relaPageStr);
							if (relaPageStr.indexOf("<--MYLayoutAndCom-->") == -1) {
								logger.debug(relaPageVO.getName() + " 模版没有选择正确");
							}
							String layoutAndCom = relaPageStr.substring(
									relaPageStr.indexOf("<--MYLayoutAndCom-->") + "<--MYLayoutAndCom-->".length(),
									relaPageStr.indexOf("<--MYLayoutAndCom/-->"));
							String pageJScript = relaPageStr.substring(
									relaPageStr.indexOf("<--MYpageJScript-->") + "<--MYpageJScript-->".length(),
									relaPageStr.indexOf("<--MYpageJScript/-->"));
							String pageActScript = relaPageStr.substring(
									relaPageStr.indexOf("<--MYpageActScript-->") + "<--MYpageActScript-->".length(),
									relaPageStr.indexOf("<--MYpageActScript/-->"));
							String pageValidate = relaPageStr.substring(
									relaPageStr.indexOf("<--MYvalidate-->") + "<--MYvalidate-->".length(),
									relaPageStr.indexOf("<--MYvalidate/-->"));

							c.put("layoutAndCom", layoutAndCom);
							c.put("pageJScript", pageJScript);
							c.put("pageActScript", pageActScript);
							c.put("pageValidate", pageValidate);

						}

						if ("1025".equals(c.getString("componentType"))) {
							JSONArray tags = c.getJSONArray("tags");
							String tabScript = "";
							if (tags != null && !tags.isEmpty()) {
								for (int j = 0; j < tags.size(); j++) {
									JSONObject tag = tags.getJSONObject(j);
									Integer relatePageId = tag.getInteger("relatePageId");
									DynamicPageVO relaPageVO = findById(relatePageId);
									String relaPageStr = generateDisTemplateByPageId(relaPageVO);
									String pageJScript = relaPageStr.substring(
											relaPageStr.indexOf("<--MYpageJScript-->") + "<--MYpageJScript-->".length(),
											relaPageStr.indexOf("<--MYpageJScript/-->"));
									String pageActScript = relaPageStr.substring(
											relaPageStr.indexOf("<--MYpageActScript-->")
													+ "<--MYpageActScript-->".length(),
											relaPageStr.indexOf("<--MYpageActScript/-->"));
									String pageValidate = relaPageStr.substring(
											relaPageStr.indexOf("<--MYvalidate-->") + "<--MYvalidate-->".length(),
											relaPageStr.indexOf("<--MYvalidate/-->"));
									tabScript = tabScript + "\n " + pageJScript + "\n " + pageActScript + "\n"
											+ pageValidate + "\n";
								}

							}
							c.put("tabScript", tabScript);
						}

						if (!"1015".equals(c.getString("componentType"))) { // 对于tips提示框,由于不放到布局中,所有这里不放到components中
							String layoutId = c.getString("layoutId");
							// 对tab组件进行特殊处理
							if ("1025".equals(c.getString("componentType"))) {

								JSONArray tags = c.getJSONArray("tags");
								JSONArray resultTags = new JSONArray();
								Map<String, JSONObject> parents = new HashMap<String, JSONObject>();
								if (tags != null) {
									for (int z = 0; z < tags.size(); z++) {
										JSONObject tag = tags.getJSONObject(z);
										String relatePageId = tag.getString("relatePageId");

										// 如果该标签下有引用页面，查找所有tab页包含的页面的隐藏标签和子页面的数据源，（父页面的数据源即为所有包含子页面的数据源）
										if (relatePageId != null && !relatePageId.equals("")) {

											DynamicPage relatePage = DynamicPage.getRepository().get(DynamicPage.class,
													relatePageId);
											JSONArray dataObject = JSONArray.parseArray(
													StringEscapeUtils.unescapeHtml4(relatePage.getDataJson()));
											if (dataObject != null && !dataObject.isEmpty()) {
												// 把所有数据源加载到dataJson中，再在后面处理
												dataJson.addAll(dataObject);
											}
											// 查找所有包含页面的隐藏框，加到主页面中
											BaseExample baseExample = new BaseExample();
											baseExample.createCriteria().andEqualTo("dynamicPage_id", relatePageId)
													.andLike("content", "%\"componentType\":\"1010\",%")
													.andLike("code", Store.COMPONENT_CODE + "%");
											PageList<StoreVO> hiddens = storeService.selectPagedByExample(baseExample,
													1, Integer.MAX_VALUE, null);
											for (StoreVO storeVo : hiddens) {
												JSONObject json = JSONObject.parseObject(storeVo.getContent());
												hiddenCom.add(json);
											}
										}

										// 这里处理tag标签的布局，处理组标签和普通标签包含关系
										if (tag.getString("tagsType").equals("2")) {
											parents.put(tag.getString("tagsId"), tag);
										} else if (tag.getString("parentTags") != null
												&& !tag.getString("parentTags").equalsIgnoreCase("")) {
											JSONObject pa = parents.get(tag.getString("parentTags"));
											if (pa.getJSONArray("childTags") == null) {
												pa.put("childTags", new JSONArray());
											}
											JSONArray childTags = pa.getJSONArray("childTags");
											childTags.add(tag);
										} else {
											resultTags.add(tag);
										}
									}

									// 更新tab父页面的数据源加入所有包含页面的数据源
									if (dataJson.toString() != null && !dataJson.toString().equalsIgnoreCase("[]")) {
										DynamicPage tabPage = DynamicPage.get(DynamicPage.class, vo.getId());
										JSONArray tabDataJson = JSONArray
												.parseArray(StringEscapeUtils.unescapeHtml4(tabPage.getDataJson()));
										// 遍历所有包含页面的数据源dataJson，去掉重复
										for (int j = 0; j < dataJson.size(); j++) {
											if (tabDataJson != null) {
												if (!tabDataJson.contains(dataJson.get(j))) {
													tabDataJson.add(dataJson.get(j));
												}
											} else {
												tabDataJson = dataJson;
											}

										}
										tabPage.setDataJson(StringEscapeUtils.escapeHtml4(tabDataJson.toString()));
										tabPage.save();
										vo.setDataJson(tabPage.getDataJson());
									}
									for (Iterator<String> it = parents.keySet().iterator(); it.hasNext();) {
										String key = it.next();
										resultTags.add(parents.get(key));
									}
									c.put("tags", resultTags);
								}
								// 对tab页处理完后，加入到components中
								if (components.get("layoutId") != null) {
									components.get(layoutId).add(c);
								} else {
									List<JSONObject> list = new ArrayList<JSONObject>();
									list.add(c);
									components.put(layoutId, list);
								}

							} else {
								// 存入components中
								if (components.get(layoutId) != null) {
									components.get(layoutId).add(c);
								} else {
									List<JSONObject> list = new ArrayList<JSONObject>();
									list.add(c);
									components.put(layoutId, list);
								}
							}

						} else {
							tipsComs.add(c);
						}

						coms.add(c);
						logger.debug("组件名称：" + c.getString("name") + "===组件layoutID" + c.getString("layoutId"));
						// 存组件对应的校验
						String validatJson = c.getString("validatJson");
						if (StringUtils.isNotBlank(validatJson)) {
							List<JSONObject> valdators = new ArrayList<JSONObject>();
							String[] validateArray = validatJson.split(",");
							for (int k = 0; k < validateArray.length; k++) {
								Store v = Store.get(validateArray[k]);
								if (v != null) {
									// String content = v.getContent();
									JSONObject tmp = JSONObject.parseObject(v.getContent());
									logger.debug(tmp.getString("clientScript"));
									tmp.put("clientScript",
											StringEscapeUtils.unescapeHtml4(tmp.getString("clientScript")));
									valdators.add(tmp);
								}
							}
							valdatorsMap.put(c.getString("pageId"), valdators);
						}
						logger.debug("end format component {} [id : {}] ", c.getString("name"), c.getString("pageId"));
					}

				} else if (o.getCode().indexOf(StoreService.PAGEACT_CODE) != -1) {
					PageAct c = PageActBeanWorker.convertConfToAct(o.getContent());
					if (c != null) {
						pageActs.put(c.getPageId(), c);
						pageActsList.add(c);

						Long resourceId = punResourceService.getResourceIdByRelateId(c.getPageId(), "3");
						pageActPermission.put(c.getPageId(), resourceId);
					}
				}
			}
		}
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			JSONObject v = map.get(key);
			if (!StringUtils.isEmpty((String) v.getString("parentId"))) {
				JSONObject p = map.get((String) v.getString("parentId"));
				if (p != null) {

					if (p.getJSONArray("childLayouts") == null) {
						List<JSONObject> tmp = new ArrayList<JSONObject>();
						p.put("childLayouts", tmp);
					}
					List<Object> list = p.getJSONArray("childLayouts");
					list.add(v);
					p.put("childLayouts", list);

				}
			} else {
				mainLayouts.add(v);
			}
		}
		// if(vo.getId() == 198) {
		// logger.debug(111);
		// }
		root.put("layouts", mainLayouts);
		if (vo.getPageType() == 1002) { // 表单页面
			root.put("components", components);
			root.put("tipsComs", tipsComs);
		} else {
			root.put("components", coms); // 列表页面
		}
		// 数据源
		String dataSource = StringEscapeUtils.unescapeHtml4(vo.getDataJson());

		JSONArray o = JSON.parseArray(dataSource);
		List<String> dataAlias = new ArrayList<String>();
		if (o != null && o.size() > 0) {
			for (int i = 0; i < o.size(); i++) {
				JSONObject jo = (JSONObject) o.get(i);
				dataAlias.add(jo.getString("name"));
			}
		}
		vo.setAfterLoadScript(StringEscapeUtils.unescapeHtml4(vo.getAfterLoadScript()));
		vo.setPreLoadScript(StringEscapeUtils.unescapeHtml4(vo.getPreLoadScript()));
		root.put("dataAlias", dataAlias);
		// root.put("pageActs", pageActs);
		root.put("pageActs", pageActsList);
		root.put("page", vo);
		root.put("valdatorsMap", valdatorsMap);
		root.put("pageActPermission", pageActPermission);
		root.put("hiddenCom", hiddenCom);
		String templateStr = PfmTemplate.get(PfmTemplate.class, vo.getTemplateId()).getContent();
		logger.debug("--------------此处超强分割线-------------------------------------页面组件-------------------------------");
		logger.debug("要解析的页面 ：　　" + vo.getName() + "\t页面ID为" + vo.getId());
		logger.debug("页面中组件有    ：　　" + vo.getName());
		for (int i = 0; i < coms.size(); i++) {
			JSONObject com = coms.get(i);
			logger.debug("组件ID: " + com.getString("pageId") + "\t组件名称： " + com.getString("name") + "\t组件数据源： "
					+ com.getString("dataItemCode") + "\t组件布局: " + com.getString("layoutName"));
		}
		logger.debug("--------------此处超强分割线-------------------------------------页面动作-------------------------------");
		for (int i = 0; i < pageActsList.size(); i++) {
			PageAct c = pageActsList.get(i);
			logger.debug("动作ID: " + c.getPageId() + "\t动作名称: " + c.getName());
			logger.debug("动作客户端脚本: " + c.getClientScript());
			logger.debug("动作服务端脚本: " + c.getServerScript());
		}
		logger.debug("--------------此处超强分割线-------------------------------------END-------------------------------");
		String disStr = FreeMarkers.renderString(templateStr, root);
		logger.debug("disStr : " + disStr);
		return disStr;
	}

	@Deprecated
	public String generateTemplateByPageId(JSONObject parent, DynamicPageVO vo) {
		// 根据需求 加上父组件的name 和 id
		Map<String, Object> root = new HashMap<String, Object>();
		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("dynamicPage_id", vo.getId());
		List<Store> children = Store.selectByExample(example);

		JSONArray array = parent.getJSONArray("configures");
		Map<String, JSONObject> configMap = new HashMap<String, JSONObject>();
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject o = array.getJSONObject(i);
				configMap.put(o.getString("id"), o);
			}
		}

		List<JSONObject> mainLayouts = new ArrayList<JSONObject>();
		List<JSONObject> coms = new ArrayList<JSONObject>();
		Map<String, PageAct> pageActs = new HashMap<String, PageAct>();
		List<PageAct> pageActsList = new ArrayList<PageAct>();

		// 布局组件
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();

		Map<String, List<JSONObject>> components = new HashMap<String, List<JSONObject>>();

		Map<String, Long> pageActPermission = new HashMap<String, Long>();
		// key:componentId value:对应的校验list<JSONObject>
		Map<String, List<JSONObject>> valdatorsMap = new HashMap<String, List<JSONObject>>();
		if (children != null && children.size() > 0) {
			for (int i = 0; i < children.size(); i++) {
				Store o = children.get(i);
				String name = o.getName();
				if (StringUtils.isNotBlank(parent.getString("name"))) {
					name = parent.getString("name") + "_" + name;
				}
				o.setName(name);
				if (o.getCode().indexOf(StoreService.LAYOUT_CODE) != -1) {
					// SimpleComponent c = JSON.parseObject(o.getContent(),
					// SimpleComponent.class);
					JSONObject c = JSON.parseObject(o.getContent());
					c.put("name", name);
					map.put(c.getString("pageId"), c);
				} else if (o.getCode().indexOf(StoreService.COMPONENT_CODE) != -1) {
					JSONObject c = JSONObject.parseObject(o.getContent()); // 取组件
					if (c != null) {
						c.put("name", name);
						if (configMap.containsKey(o.getId())) {
							c.put("dataItemCode", configMap.get(o.getId()).getString("dataItemCode"));
							// c.put("name", name);
						}
						// 对于包含页面组件，特殊处理，根据包含的页面，解析后得到要发布的模板string，修改
						// 数据源dataItemCode 以及 组件id，组件name
						if ("1012".equals(c.getString("componentType"))
								|| "1013".equals(c.getString("componentType"))) {
							// c为包含组件
							Long relatePageId = c.getLong("relatePageId");
							DynamicPageVO relaPageVO = findById(relatePageId);

							String relaPageStr = generateTemplateByPageId(c, relaPageVO);
							// JSONArray relComArray =
							// c.getJSONArray("configures");
							// if(relComArray != null && relComArray.size()>0){
							// for(int k =0; k<relComArray.size();k++){
							// //遍历更改包含页面中组件的dataItemCode，pageId，name
							// JSONObject relCom = (JSONObject)
							// relComArray.get(k);
							// String relComId = relCom.getString("pageId");
							// Store relComStore = Store.get(relComId);
							// if(relComStore != null){
							// JSONObject relComJson =
							// JSON.parseObject(relComStore.getContent());
							// String oraignDataCode =
							// relComJson.getString("dataItemCode");
							// String newDataCode =
							// relCom.getString("dataItemCode");
							// relaPageStr = relaPageStr.replace(oraignDataCode,
							// newDataCode);
							//
							// relaPageStr =
							// relaPageStr.replace(relCom.getString("pageId"),
							// c.getString("name")+"_"+relCom.getString("pageId"));
							// //包含页面的组件pageId加前缀
							// relaPageStr =
							// relaPageStr.replace(relComJson.getString("name"),
							// c.getString("name")+"_"+relComJson.getString("name"));
							// //包含页面的组件name加前缀
							// }
							// }
							// }
							// logger.debug("relaPageStr" + relaPageStr);
							String layoutAndCom = relaPageStr.substring(
									relaPageStr.indexOf("<--MYLayoutAndCom-->") + "<--MYLayoutAndCom-->".length(),
									relaPageStr.indexOf("<--MYLayoutAndCom/-->"));
							String pageJScript = relaPageStr.substring(
									relaPageStr.indexOf("<--MYpageJScript-->") + "<--MYpageJScript-->".length(),
									relaPageStr.indexOf("<--MYpageJScript/-->"));
							String pageActScript = relaPageStr.substring(
									relaPageStr.indexOf("<--MYpageActScript-->") + "<--MYpageActScript-->".length(),
									relaPageStr.indexOf("<--MYpageActScript/-->"));
							String pageValidate = relaPageStr.substring(
									relaPageStr.indexOf("<--MYvalidate-->") + "<--MYvalidate-->".length(),
									relaPageStr.indexOf("<--MYvalidate/-->"));

							c.put("layoutAndCom", layoutAndCom);
							c.put("pageJScript", pageJScript);
							c.put("pageActScript", pageActScript);
							c.put("pageValidate", pageValidate);

						}
						// 如果包含组件不能将name传递，则需要冒过包含组件后更新name
						// if(!configMap.isEmpty()) {
						// c.put("name", name);
						// }

						String layoutId = c.getString("layoutId"); // 存入components中
						if (components.get(layoutId) != null) {
							components.get(layoutId).add(c);
						} else {
							List<JSONObject> list = new ArrayList<JSONObject>();
							list.add(c);
							components.put(layoutId, list);
						}
						coms.add(c);

						// 存组件对应的校验
						String validatJson = c.getString("validatJson");
						if (StringUtils.isNotBlank(validatJson)) {
							List<JSONObject> valdators = new ArrayList<JSONObject>();
							String[] validateArray = validatJson.split(",");
							for (int k = 0; k < validateArray.length; k++) {
								Store v = Store.get(validateArray[k]);
								if (v != null) {
									valdators.add(JSONObject.parseObject(v.getContent()));
								}
							}
							valdatorsMap.put(c.getString("pageId"), valdators);
						}
					}

				} else if (o.getCode().indexOf(StoreService.PAGEACT_CODE) != -1) {
					PageAct c = PageActBeanWorker.convertConfToAct(o.getContent());
					if (c != null) {
						pageActs.put(c.getPageId(), c);
						pageActsList.add(c);

						Long resourceId = punResourceService.getResourceIdByRelateId(c.getPageId(), "3");
						pageActPermission.put(c.getPageId(), resourceId);
					}
				}
			}
		}
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			JSONObject v = map.get(key);
			if (!StringUtils.isEmpty((String) v.getString("parentId"))) {
				JSONObject p = map.get((String) v.getString("parentId"));
				if (p != null) {

					if (p.getJSONArray("childLayouts") == null) {
						List<JSONObject> tmp = new ArrayList<JSONObject>();
						p.put("childLayouts", tmp);
					}
					List<Object> list = p.getJSONArray("childLayouts");
					list.add(v);
					p.put("childLayouts", list);

				}
			} else {
				mainLayouts.add(v);
			}
		}
		// if(vo.getId() == 198) {
		// logger.debug(111);
		// }
		root.put("layouts", mainLayouts);
		if (vo.getPageType() == 1002) { // 表单页面
			root.put("components", components);
			root.put("allComs", coms);
		} else {
			root.put("components", coms); // 列表页面
		}
		// 数据源
		String dataSource = StringEscapeUtils.unescapeHtml4(vo.getDataJson());

		JSONArray o = JSON.parseArray(dataSource);
		List<String> dataAlias = new ArrayList<String>();
		if (o != null && o.size() > 0) {
			for (int i = 0; i < o.size(); i++) {
				JSONObject jo = (JSONObject) o.get(i);
				dataAlias.add(jo.getString("name"));
			}
		}
		vo.setAfterLoadScript(StringEscapeUtils.unescapeHtml4(vo.getAfterLoadScript()));
		vo.setPreLoadScript(StringEscapeUtils.unescapeHtml4(vo.getPreLoadScript()));
		root.put("dataAlias", dataAlias);
		// root.put("pageActs", pageActs);
		root.put("pageActs", pageActsList);
		root.put("page", vo);
		root.put("valdatorsMap", valdatorsMap);
		root.put("pageActPermission", pageActPermission);
		String templateStr = PfmTemplate.get(PfmTemplate.class, vo.getTemplateId()).getContent();
		// logger.debug(templateStr);
		String disStr = FreeMarkers.renderString(templateStr, root);
		logger.debug("disStr : " + disStr);
		return disStr;
	}

	@Override
	public DynamicPageVO publish(Long id) {
		DynamicPageVO vo = findById(id);
		return publish(vo);
	}

	/**
	 * 签出,将页面修改为checkOut状态，以及修改checkOutUser，同时修改store表中的页面元素
	 */
	@Override
	public DynamicPageVO checkOut(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		Object obj = SessionUtils.getObjectFromSession(SessionContants.CURRENT_USER);
		if (obj instanceof PunUserBaseInfoVO) {
			params.put("checkOutUser", ((PunUserBaseInfoVO) obj).getName());
		}
		params.put("id", id);
		queryChannel.excuteMethod(DynamicPage.class, "checkOutPage", params);
		queryChannel.excuteMethod(Store.class, "checkOutStore", params);
		return null;
	}

	/**
	 * 签入,将页面修改为checkIn状态，同时修改store表中的页面元素checkIn状态
	 */
	@Override
	public DynamicPageVO checkIn(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		queryChannel.excuteMethod(DynamicPage.class, "checkInPage", params);
		queryChannel.excuteMethod(Store.class, "checkInStore", params);
		return null;
	}

	@Override
	public DynamicPageVO copy(Long id) {
		DynamicPageVO vo = findById(id);

		vo.setName(vo.getName() + "_" + System.currentTimeMillis());
		vo.setId(null);
		vo.setWorkflowNodeInfo(null);
		saveOrUpdate(vo);

		DynamicPageVO sourceVO = new DynamicPageVO();
		sourceVO.setId(id);

		storeService.copyByDynamicPage(sourceVO, vo);
		return vo;
	}

	@Override
	public DynamicPageVO copyToSystem(Long id, String systemId) {
		DynamicPageVO vo = findById(id);

		vo.setName(vo.getName() + "_" + System.currentTimeMillis());
		vo.setId(null);
		vo.setWorkflowNodeInfo(null);
		vo.setSystemId(Long.parseLong(systemId));
		saveOrUpdate(vo);

		DynamicPageVO sourceVO = new DynamicPageVO();
		sourceVO.setId(id);

		storeService.copyByDynamicPage(sourceVO, vo);
		return vo;
	}

	/**
	 * 修改内容：修改查找按钮方式，由发送多条sql改为使用in(?,..,?)发送一条sql 修改人：wuhengguan 修改日期：2015.3.26
	 */
	@Override
	public Map<String, List<StoreVO>> getSystemActs(List<Long> dynamicPageIds) {
		BaseExample baseExample = new BaseExample();
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if (obj instanceof PunSystemVO) {
			PunSystemVO system = (PunSystemVO) obj;
			baseExample.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId());
		}
		List<DynamicPageVO> pageVos = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, null);
		Map<String, List<StoreVO>> ret = new HashMap<String, List<StoreVO>>();
		if (pageVos != null) {
			List<Long> ids = new ArrayList<Long>();
			for (DynamicPageVO pageVo : pageVos) {
				ids.add(pageVo.getId());
			}
			BaseExample example = new BaseExample();
			if (dynamicPageIds != null) {
				example.createCriteria().andInLong("DYNAMICPAGE_ID", ids).andInLong("DYNAMICPAGE_ID", dynamicPageIds)
						.andLike("code", StoreService.PAGEACT_CODE + "%");
			} else {
				example.createCriteria().andInLong("DYNAMICPAGE_ID", ids).andLike("code",
						StoreService.PAGEACT_CODE + "%");
			}
			List<StoreVO> stores = storeService.selectPagedByExample(example, 1, Integer.MAX_VALUE, null);
			for (StoreVO store : stores) {
				String pageName = null;
				for (DynamicPageVO pageVo : pageVos) {
					if (store.getDynamicPageId().equals(pageVo.getId())) {
						pageName = pageVo.getName();
					}
				}
				if (ret.containsKey(pageName)) {
					List<StoreVO> newStore = ret.get(pageName);
					newStore.add(store);
				} else {
					List<StoreVO> newStore = new ArrayList<StoreVO>();
					newStore.add(store);
					ret.put(pageName, newStore);
				}
			}
		}

		return ret;
	}

	@Override
	public List<JSONObject> getComponentByContainer(JSONObject container) {
		List<JSONObject> retVal = new ArrayList<JSONObject>();
		String name = container.getString("name");
		String dynamicPageId = container.getString("relatePageId");

		DynamicPageVO pageVO = findById(Long.valueOf(dynamicPageId));
		// 只查询表单和普通页面
		if (pageVO.getPageType() != 1003) {
			String componentType = container.getString("componentType");
			String configures = container.getString("configures");
			if (StringUtils.isNotBlank(configures)) {
				JSONArray array = JSON.parseArray(configures);
				for (int i = 0; i < array.size(); i++) {
					JSONObject tmp = array.getJSONObject(i);
					if (tmp != null) {
						StoreVO vo = storeService.findById(tmp.getString("pageId"));
						JSONObject o = JSON.parseObject(vo.getContent());
						if (o == null) {
							logger.debug(111 + "");
						}
						o.put("dataItemCode", tmp.getString("dataItemCode"));
						String nameTmp = StringUtils.isNotBlank(name) ? name + "_" + o.getString("name")
								: o.getString("name");
						o.put("name", nameTmp);
						retVal.add(o);
					}
				}
			}

			BaseExample example = new BaseExample();
			example.createCriteria().andEqualTo("dynamicPage_id", dynamicPageId).andLike("code",
					StoreService.COMPONENT_CODE + "%");
			List<StoreVO> stores = storeService.selectPagedByExample(example, 1, Integer.MAX_VALUE, null);
			for (StoreVO vo : stores) {
				JSONObject object = JSON.parseObject(vo.getContent());
				String type = object.getString("componentType");
				if (type.equalsIgnoreCase("1012")) {

					// String nameTmp = StringUtils.isNotBlank(name) ? name +
					// "_" + object.getString("name") :
					// object.getString("name");
					// object.put("name", nameTmp);

					retVal.addAll(getComponentByContainer(object));
				} else if (type.equalsIgnoreCase("1025")) {
					JSONObject content = JSONObject.parseObject(vo.getContent());
					JSONArray tags = content.getJSONArray("tags");
					for (int i = 0; i < tags.size(); i++) {

						JSONObject tag = tags.getJSONObject(i);
						Integer relatePageId = tag.getInteger("relatePageId");
						if (relatePageId != null) {
							JSONObject jcon = new JSONObject();
							jcon.put("relatePageId", relatePageId);
							jcon.put("componentType", "");
							retVal.addAll(getComponentByContainer(jcon));
						}
					}
				} else if (type.equalsIgnoreCase("1009") || type.equalsIgnoreCase("1014")) {

				} else {
					if (!componentType.equalsIgnoreCase("1012")) {
						String nameTmp = StringUtils.isNotBlank(name) ? name + "_" + object.getString("name")
								: object.getString("name");
						object.put("name", nameTmp);
						retVal.add(object);
					}
				}
			}
		}
		return retVal;
	}

	@Override
	public List<JSONObject> getComponentByContainerWithColumn(JSONObject container) {
		List<JSONObject> retVal = new ArrayList<JSONObject>();
		String name = container.getString("name");
		String dynamicPageId = container.getString("relatePageId");

		// DynamicPageVO pageVO = findById(Long.valueOf(dynamicPageId));

		String componentType = container.getString("componentType");
		String configures = container.getString("configures");
		if (StringUtils.isNotBlank(configures)) {
			JSONArray array = JSON.parseArray(configures);
			for (int i = 0; i < array.size(); i++) {
				JSONObject tmp = array.getJSONObject(i);
				if (tmp != null) {
					StoreVO vo = storeService.findById(tmp.getString("pageId"));
					JSONObject o = JSON.parseObject(vo.getContent());
					if (o == null) {
						String parentId = container.getString("dynamicPageId");
						if (StringUtils.isNotBlank(parentId)) {
							DynamicPageVO pageVO = findById(Long.valueOf(parentId));
							logger.debug(pageVO.getName() + " 动态页面中的[ " + name + " ]组件需要重新刷新下");
						} else {
							DynamicPageVO pageVO = findById(Long.valueOf(dynamicPageId));
							logger.debug(pageVO.getName() + " 动态页面中的[ " + name + " ]组件需要重新刷新下");
						}
					}
					o.put("dataItemCode", tmp.getString("dataItemCode"));
					String nameTmp = StringUtils.isNotBlank(name) ? name + "_" + o.getString("name")
							: o.getString("name");
					o.put("name", nameTmp);
					retVal.add(o);
				}
			}
		}

		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("dynamicPage_id", dynamicPageId).andLike("code",
				StoreService.COMPONENT_CODE + "%");
		List<StoreVO> stores = storeService.selectPagedByExample(example, 1, Integer.MAX_VALUE, null);
		for (StoreVO vo : stores) {
			JSONObject object = JSON.parseObject(vo.getContent());
			String type = object.getString("componentType");
			if (type.equalsIgnoreCase("1012") || type.equalsIgnoreCase("1013")) { // 包含组件和搜索组件，将包含页面下的其他组件也一并加上
				retVal.add(object);
				retVal.addAll(getComponentByContainerWithColumn(object));
			}
			// else if(type.equalsIgnoreCase("1009") ||
			// type.equalsIgnoreCase("1014")) {
			//
			// }
			else {
				if (!componentType.equalsIgnoreCase("1012")) {
					if (componentType.equalsIgnoreCase("1013")) { // 对于搜索组件，不加前缀，
																	// 包含组件，则需要加上前缀
						object.put("name", object.getString("name"));
						retVal.add(object);
					} else {
						String nameTmp = StringUtils.isNotBlank(name) ? name + "_" + object.getString("name")
								: object.getString("name");
						object.put("name", nameTmp);
						retVal.add(object);
					}

				}
			}
		}

		return retVal;
	}

	public List<Long> getChildListPages(Long dynamicPageId) {
		List<Long> list = new ArrayList<Long>();
		DynamicPageVO pageVO = findById(dynamicPageId);
		if (pageVO.getPageType() == 1003) {// 列表页面，则返回自己
			list.add(dynamicPageId);
		} else {// 表单页面，则查询包含的列表子页面
			BaseExample example = new BaseExample();
			example.createCriteria().andEqualTo("dynamicPage_id", dynamicPageId).andLike("code",
					StoreService.COMPONENT_CODE + "%");
			List<StoreVO> stores = storeService.selectPagedByExample(example, 1, Integer.MAX_VALUE, null);

			if (stores != null && stores.size() > 0) {
				for (int i = 0; i < stores.size(); i++) {
					JSONObject json = JSON.parseObject(((StoreVO) stores.get(i)).getContent());
					if ("1012".equals(json.getString("componentType"))) {
						Long relatePageId = json.getLong("relatePageId");
						DynamicPageVO relaPageVO = findById(relatePageId);
						if (relaPageVO.getPageType() == 1003) {
							list.add(relaPageVO.getId());
						} else {
							list.addAll(getChildListPages(relaPageVO.getId()));
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public String getTemplateContext(Long id) {
		DynamicPage page = DynamicPage.get(DynamicPage.class, id);
		if (page != null) {
			return page.getTemplateContext();
		}
		return null;
	}

	@Override
	public List<DynamicPageVO> listNameAndIdInSystem(Long systemId, Map<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();
		params.put("systemId", systemId);
		params.put("start", 0);
		params.put("limit", Integer.MAX_VALUE);
		List<DynamicPage> list = queryChannel.queryPagedResult(DynamicPage.class, "listNameAndIdInSystem", params);
		List<DynamicPageVO> resultVo = new ArrayList<DynamicPageVO>();
		for (DynamicPage mm : list) {
			resultVo.add(BeanUtils.getNewInstance(mm, DynamicPageVO.class));
		}
		list.clear();
		return resultVo;
	}

}
