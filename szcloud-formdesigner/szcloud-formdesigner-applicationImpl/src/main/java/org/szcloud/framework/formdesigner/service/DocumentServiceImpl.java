package org.szcloud.framework.formdesigner.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.core.utils.DateUtils;
import org.szcloud.framework.core.utils.Tools;
import org.szcloud.framework.core.utils.constants.SessionContants;
import org.szcloud.framework.formdesigner.application.service.DocumentService;
import org.szcloud.framework.formdesigner.application.service.FormdesignerService;
import org.szcloud.framework.formdesigner.application.vo.DocumentVO;
import org.szcloud.framework.formdesigner.application.vo.DynamicPageVO;
import org.szcloud.framework.formdesigner.core.constants.FormDesignerGlobal;
import org.szcloud.framework.formdesigner.core.domain.Document;
import org.szcloud.framework.formdesigner.core.domain.DynamicPage;
import org.szcloud.framework.formdesigner.core.domain.design.context.data.DataDefine;
import org.szcloud.framework.formdesigner.core.parse.bean.PageDataBeanWorker;
import org.szcloud.framework.metadesigner.application.MetaModelOperateService;
import org.szcloud.framework.metadesigner.application.MetaModelService;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;
import org.szcloud.framework.unit.service.SysSourceRelationService;
import org.szcloud.framework.unit.vo.PunSystemVO;
import org.szcloud.framework.unit.vo.SysDataSourceVO;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class DocumentServiceImpl implements DocumentService {
	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	public static final String CACHE_KEY_PREFIX = "cache_key_pre_";

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	@Qualifier("queryChannel")
	private QueryChannelService queryChannel;

	@Autowired
	private MetaModelOperateService metaModelOperateServiceImpl;

	@Autowired
	@Qualifier("sysSourceRelationServiceImpl")
	SysSourceRelationService sysSourceRelationService;

	@Autowired
	private FormdesignerService formdesignerServiceImpl;

	@Autowired
	private MetaModelService metaModelServiceImpl;

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	HttpServletRequest request; // 这里可以获取到request

	public String save(DocumentVO vo) {
		// 仅保存文档数据
		Document doc = BeanUtils.getNewInstance(vo, Document.class);
		String id = doc.save();
		vo.setId(id);
		return id;
	}

	/**
	 * 删除文档
	 */
	public Boolean delete(DocumentVO vo) {
		try {
			Document doc = BeanUtils.getNewInstance(vo, Document.class);
			doc.remove();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<DocumentVO> findPageByExample(BaseExample baseExample, int currentPage, int pageSize,String sortString) {
		PageList<Document> list = queryChannel.selectPagedByExample(Document.class, baseExample, currentPage, pageSize,sortString);
		PageList<DocumentVO> ret = new PageList<DocumentVO>(list.getPaginator());
		if (list != null && list.size() > 0) {
			for (Document vo : list) {
				ret.add(BeanUtils.getNewInstance(vo, DocumentVO.class));
			}
		}
		return ret;
	}

	@Override
	public DocumentVO findById(String id) {
		DocumentVO vo = BeanUtils.getNewInstance(Document.get(id), DocumentVO.class);
		return vo;
	}

	@Override
	public DocumentVO findDocByWorkItemId(String flowTemplateId, String workItemId) {
		DocumentVO vo = BeanUtils.getNewInstance(Document.findByWorkItemId(flowTemplateId, workItemId),
				DocumentVO.class);
		return vo;
	}

	@Override
	public Map<String, List<Map<String, String>>> initDocumentData(Integer currentPage, Integer pageSize,
			DocumentVO docVo, ScriptEngine engine, DynamicPageVO pageVo) {
		logger.debug("start initDocumentData ");
		Map<String, List<Map<String, String>>> listParams = null;
		listParams = new HashMap<String, List<Map<String, String>>>();
		Map<String, DataDefine> map = PageDataBeanWorker
						.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(pageVo.getDataJson()));
		List<DataDefine> datas = new ArrayList<DataDefine>(map.values());
		docVo.setListParams(listParams);
		String allowOrderBy = docVo.getAllowOrderBy();
		String orderByList = docVo.getOrderBy();
		logger.debug("start find data in datajson");
		for (DataDefine dd : datas) {
			try {
				if (!StringUtils.isNumeric(allowOrderBy) || Integer.parseInt(allowOrderBy) != 1) {
					orderByList = "";
				}
				PageList<Map<String, String>> pageList = getDataListByDataDefine(dd, engine, currentPage, pageSize,orderByList);
				if (pageList != null) {
					listParams.put(dd.getName() + "_list", pageList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("end find data in datajson");
		logger.debug("end initDocumentData with no cache");
		return listParams;
	}

	@Override
	public Map<String, List<Map<String, String>>> initDocumentDataFlow(Integer currentPage, Integer pageSize,
			DocumentVO docVo, ScriptEngine engine, DynamicPageVO pageVo) {
		logger.debug("start initDocumentData ");
		Map<String, List<Map<String, String>>> listParams = null;
		listParams = new HashMap<String, List<Map<String, String>>>();
		Map<String, DataDefine> map = PageDataBeanWorker
				.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(pageVo.getDataJson()));
		List<DataDefine> datas = new ArrayList<DataDefine>(map.values());
		docVo.setListParams(listParams);
		String allowOrderBy = docVo.getAllowOrderBy();
		String orderByList = docVo.getOrderBy();// ebaseinfo.id,desc;ebaseinfo.name,desc;
		logger.debug("start find data in datajson");
		for (DataDefine dd : datas) {
			try {
				StringBuilder orderBy = new StringBuilder();
				String alias = dd.getName();
				String keyAlias = alias + ".";
				if (StringUtils.isNotBlank(allowOrderBy) && allowOrderBy.equalsIgnoreCase("1")) {
					logger.debug("allow orderby");
					int begin = orderByList.indexOf(keyAlias);
					while (begin != -1) {
						orderBy.append(", ");
						int end = orderByList.indexOf(";", begin);
						if (end != -1) {
							String itemCode = orderByList.substring(begin + keyAlias.length(), end);
							logger.debug("itemcode is {}", itemCode);
							if (itemCode.indexOf(",") != -1) {
								orderBy.append(itemCode.replaceAll(",", " "));
							} else {
								orderBy.append(itemCode).append(" DESC");
							}
							begin = orderByList.indexOf(keyAlias, end);
						} else {
							begin = orderByList.indexOf(keyAlias, begin);
						}
					}
					if (orderBy.length() == 1) {
						orderBy.deleteCharAt(1);
					}
				} else {
					logger.debug("deny orderby");
				}
				PageList<Map<String, String>> pageList = getDataListByDataDefine(dd, engine, currentPage, pageSize,
						orderBy.toString());
				if (pageList != null) {
					listParams.put(dd.getName() + "_list", pageList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("end find data in datajson");
		logger.debug("end initDocumentData with no cache");
		return listParams;
	}

	/**
	 * 根据数据源和脚本engine获取数据列表
	 * 
	 * @param dd
	 *            数据源
	 * @param engine
	 *            脚本engine
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            页条数
	 * @return
	 * @throws ScriptException
	 */
	public PageList<Map<String, String>> getDataListByDataDefine(DataDefine dd, ScriptEngine engine,
			Integer currentPage, Integer pageSize, String orderBy) throws ScriptException {
		logger.debug("start find {} ", dd.getName());
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String script = dd.getSqlScript();
		String sql = null;
		if (StringUtils.isNotBlank(script)) {
			sql = (String) engine.eval(script);
		}
		logger.debug("datasource[{}] compute sql is {} ", dd.getName(), sql);
		if (StringUtils.isNotBlank(sql)) {
			// 去掉最后的";"，这样就可以对sql进行外包或者增加limit了
			if (sql.endsWith(";")) {
				sql = sql.substring(0, sql.length() - 1);
			}

			// 执行查询时，首先去掉order by 因为会很慢
			String sql2 = sql.toUpperCase();
			Integer totalCount = 1;// 默认为查询单条数据
			int index = sql2.lastIndexOf("ORDER BY");
			if (index != -1) {
				// 如果没有有排序条件，则将排序条件置为sql语句中的排序条件，也有可能sql语句中也没有排序条件，那么orderBy为空
				if (StringUtils.isBlank(orderBy)) {
					orderBy = sql.substring(index + 8);
					logger.debug("datasource[{}] : sql has order by \"{}\" ", dd.getName(), orderBy);
				} else {
					logger.debug("datasource[{}] : sql use param \"{}\" ", dd.getName(), orderBy);
				}
				sql = sql.substring(0, index);
			}
			// 增加
			sql = addSQLParam(sql, request);
			Map<String, Object> param = wrapMap(request.getParameterMap());
			Paginator paginator = null;
			if (dd.getIsSingle() == 0) {
				logger.debug("datasource[{}] : sql is single ", dd.getName());
				// 如果是单行数据，则无需查询数据条数
				paginator = new Paginator(currentPage, 1, totalCount);
			} else {
				//
				// 如果是多行数据，需分页，则需查询数据条数
				if (dd.getIsPage() > 0) {
					logger.debug("datasource[{}] : sql is mutiple and should be paged ", dd.getName());
					StringBuilder countSql = new StringBuilder();
					countSql.append("select count(*) from (").append(sql).append(") temp");
					// 修改成具名参数
					// totalCount
					// =jdbcTemplate.queryForInt(countSql.toString());
					totalCount = namedParameterJdbcTemplate.queryForObject(countSql.toString(), param, Integer.class);
					logger.debug("datasource[{}] : countSql is {} data count {}", dd.getName(), countSql, totalCount);
					paginator = new Paginator(currentPage, pageSize, totalCount);
				} else {
					if (dd.getLimitCount() <= 0) {
						// 多行数据，且不分页,且无固定记录数
						if (pageSize > 0) {
							dd.setLimitCount(pageSize);
						} else {
							dd.setLimitCount(10);
						}
					}
					logger.debug("datasource[{}] : sql data is mutiple and has no pager the total count is {}",
							dd.getName(), dd.getLimitCount());
					paginator = new Paginator(1, dd.getLimitCount(), dd.getLimitCount());
				}
			}
			if (StringUtils.isNotBlank(orderBy)) {
				sql += " ORDER BY " + orderBy;
			}
			sql += " limit " + paginator.getOffset() + "," + paginator.getLimit();
			logger.debug("datasource[{}] : actualSql is {} ", dd.getName(), sql);
			// 修改成具名参数
			// List<Map<String, Object>> retList =
			// jdbcTemplate.queryForList(sql, new Object[] {});
			List<Map<String, Object>> retList = namedParameterJdbcTemplate.queryForList(sql, param);
			logger.debug("datasource[{}] : start convert data. ", dd.getName());
			for (Map<String, Object> retMap : retList) {
				Map<String, String> temp = new HashMap<String, String>();
				for (Map.Entry<String, Object> entrySet : retMap.entrySet()) {
					String key = entrySet.getKey();
					Object obj = entrySet.getValue();
					String value = null;
					if (obj != null) {
						value = String.valueOf(obj);
					}
					temp.put(key, value);
				}
				list.add(temp);
			}
			PageList<Map<String, String>> pageList = new PageList<Map<String, String>>(list, paginator);
			logger.debug("datasource[{}] : end convert data. ", dd.getName());
			return pageList;
		}
		logger.debug("datasource[{}] : sql is blank. ", dd.getName());
		return null;
	}

	private Map<String, Object> wrapMap(Map<String, String[]> map, String... filters) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			List<String> filtersList = new ArrayList<String>();
			Collections.addAll(filtersList, "currentPage", "pageSize", "offset", "limit", "privilegesID");
			if (filters != null) {
				Collections.addAll(filtersList, filters);
			}
			if (!filtersList.contains(key)) {
				// 添加参数，取第一个value值
				param.put(entry.getKey(), StringUtils.join(entry.getValue(), ";"));
			}
		}
		return param;
	}

	private String addSQLParam(String sql, HttpServletRequest request) {
		StringBuilder builder = new StringBuilder(sql);
		StringBuilder where = new StringBuilder();
		int index = builder.indexOf("1=1") + 3;
		Enumeration<String> param = request.getParameterNames();
		for (; param.hasMoreElements();) {
			String key = param.nextElement();
			if (key.contains(".") && StringUtils.isNotBlank(request.getParameter(key))) {
				if (key.toLowerCase().contains("name") || key.toLowerCase().contains("title")) {
					where.append(" and " + key + " like concat ('%',:" + key + ",'%') ");
				} else {
					where.append(" and " + key + "=:" + key + " ");
				}
			}
		}
		builder.insert(index, where);
		return builder.toString();
	}

	@Override
	public String getTemplateString(DynamicPageVO pageVO) {
		DynamicPage page = DynamicPage.get(DynamicPage.class, pageVO.getId());
		return page.getTemplateContext();
	}

	@Override
	public String getStoreString(DynamicPageVO pageVO) {
		DynamicPage page = DynamicPage.get(DynamicPage.class, pageVO.getId());
		return page.getStores();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.szcloud.framework.formdesigner.application.service.DocumentService
	 * #processParams
	 * (org.szcloud.framework.formdesigner.application.vo.DocumentVO)
	 */
	public DocumentVO processParams(DocumentVO vo) {
		Map<String, String> map = vo.getRequestParams();
		String pageId = (String) map.get("dynamicPageId");
		JSONObject jcon = new JSONObject();
		jcon.put("relatePageId", pageId);
		jcon.put("componentType", "");
		// 需要把tab页包含的页面的组件都找出来
		List<JSONObject> components = formdesignerServiceImpl.getComponentByContainer(jcon);

		Map<String, String> tmpMap = new HashMap<String, String>();
		Map<String, List<Map<String, String>>> listParams = vo.getListParams();
		for (JSONObject c : components) {
			// 有值组件
			if (FormDesignerGlobal.isValueComponent(c)) {
				// ValuedComponent.name 为request里面的key
				String key = c.getString("name");
				// 由于checkbox当不选择的时候，会默认
				if (!map.containsKey(key)) {
					logger.debug("有值组件 {} 类型 : {} 没有提交数据", key, c.getIntValue("componentType"));
					continue;
				}
				String value = map.get(key);
				logger.debug("有值组件 {} 提交数据为：{}", key, value);

				// Added By George Zheng At 2015/11/19
				// 针对日期组件，物殊格式的日期处理一下
				int type = c.getIntValue("componentType");
				if (type == 1002) {
					String dateType = c.getString("dateType");
					if ("form-year-month".equals(dateType)) { // yyyy-MM
						value = DateUtils.format(DateUtils.parseDate(value, "MM/yyyy"));
					} else if ("form-year".equals(dateType)) {
						value = DateUtils.format(DateUtils.parseDate(value, "yyyy"));
					} else if ("form-english-date".equals(dateType)) {
						value = DateUtils.format(DateUtils.parseDate(value, "dd/MM/yyyy"));
					} else if ("form-english-date-long".equals(dateType)) {
						value = DateUtils.format(DateUtils.parseDate(value, "dd/MM/yyyy HH:mm"));
					}
				}
				if (type == 1032) {
					value = value.replaceAll(",", "");
				}

				String code = c.getString("dataItemCode");// "datadefine.name,modelitemcode"
				logger.debug("有值组件 {} 数据源为 ：{}", key, code);
				String[] codes = code.split("\\.");
				if (codes.length < 2)
					continue;
				String dataCode = codes[0];
				String itemCode = codes[1];
				if (itemCode.equals("FK_Flow"))
					logger.debug("5555555555555555555555555");

				List<Map<String, String>> list = listParams.get(dataCode);
				Map<String, String> data = null;
				if (list == null) {
					list = new ArrayList<Map<String, String>>();
					data = new HashMap<String, String>();
					data.put(itemCode, value);
					tmpMap.put(code, key);
				} else {
					data = list.get(0);
					if (data == null) {
						data = new HashMap<String, String>();
						data.put(itemCode, value);
						tmpMap.put(code, key);
					} else {
						data.put(itemCode, value);
					}
				}
				List<Map<String, String>> arr = new ArrayList<Map<String, String>>();
				arr.add(data);
				listParams.put(dataCode, arr);
			}

		}
		tmpMap.clear();
		vo.setListParams(listParams);
		return vo;
	}

	/**
	 * 根据元数据code获取到数据源ID，如果数据源没配置，则获取系统默认数据源Id
	 */
	public Long getDataSourceIdByModelCode(DataDefine dd) {
		MetaModelVO mmv = metaModelServiceImpl.queryByModelCode(dd.getModelCode());
		if (mmv != null && mmv.getDataSourceId() != null) { // 如果有配置数据源
			return mmv.getDataSourceId();
		} else {
			// 系统默认数据源
			Object obj = Tools.getObjectFromSession(SessionContants.CURRENT_SYSTEM);
			PunSystemVO system = null;
			if (obj instanceof PunSystemVO) {
				system = (PunSystemVO) obj;
			}
			BaseExample base = new BaseExample();
			base.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId()).andEqualTo("ISDEFAULT", true);
			PageList<SysDataSourceVO> dataVos = sysSourceRelationService.selectPagedByExample(base, 1,
					Integer.MAX_VALUE, null);
			if (dataVos != null && dataVos.size() > 0) {
				return dataVos.get(0).getDataSourceId();
			}
		}
		return null;
	}

	/**
	 * 根据元数据code获取到数据源ID，如果数据源没配置，则获取系统默认数据源Id
	 */
	public Long getDataSourceIdByModelCode(DataDefine dd, Long systemId) {
		MetaModelVO mmv = metaModelServiceImpl.queryByModelCode(dd.getModelCode(), systemId);
		if (mmv != null && mmv.getDataSourceId() != null) { // 如果有配置数据源
			return mmv.getDataSourceId();
		} else {
			BaseExample base = new BaseExample();
			base.createCriteria().andEqualTo("SYSTEM_ID", systemId).andEqualTo("ISDEFAULT", true);
			PageList<SysDataSourceVO> dataVos = sysSourceRelationService.selectPagedByExample(base, 1,
					Integer.MAX_VALUE, null);
			if (dataVos != null && dataVos.size() > 0) {
				return dataVos.get(0).getDataSourceId();
			}
		}
		return null;
	}

	/**
	 * 获取当前系统的默认数据源Id
	 */
	public Long getDataSourceIdByCurrentSystem() {
		Object obj = Tools.getObjectFromSession(SessionContants.CURRENT_SYSTEM);
		PunSystemVO system = null;
		if (obj instanceof PunSystemVO) {
			system = (PunSystemVO) obj;
		}
		BaseExample base = new BaseExample();
		base.createCriteria().andEqualTo("SYSTEM_ID", system.getSysId()).andEqualTo("ISDEFAULT", true);
		PageList<SysDataSourceVO> dataVos = sysSourceRelationService.selectPagedByExample(base, 1, Integer.MAX_VALUE,
				null);
		if (dataVos != null && dataVos.size() > 0) {
			return dataVos.get(0).getDataSourceId();
		}
		return null;
	}

	@Override
	public boolean updateModelDataFlow(DynamicPageVO pageVO, DocumentVO vo, String datadefineName) {
		DynamicPage page = BeanUtils.getNewInstance(pageVO, DynamicPage.class);
		DataDefine dd = PageDataBeanWorker.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(page.getDataJson()))
				.get(datadefineName);
		if (dd != null) {
			DataDefine md = (DataDefine) dd;
			try {
				if (vo != null && StringUtils.isNotBlank(vo.getId()) && pageVO != null && pageVO.getId() != null) {
				}
				metaModelOperateServiceImpl.update(vo.getListParams().get(datadefineName).get(0), md.getModelCode());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateModelData(DynamicPageVO pageVO, DocumentVO vo, String datadefineName) {
		Map<String, List<Map<String, String>>> listParams = null;
		DynamicPage page = BeanUtils.getNewInstance(pageVO, DynamicPage.class);
		logger.debug("start convert datadefines in {} [{}]", pageVO.getId(), pageVO.getName());
		DataDefine dd = PageDataBeanWorker.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(page.getDataJson()))
				.get(datadefineName);
		logger.debug("end convert datadefines in {} [{}]", pageVO.getId(), pageVO.getName());
		if (dd != null) {
			// get data from doc.requestMap, and put it into map with model
			// processParams(vo);executeAct 方法中已经做处理
			DataDefine md = (DataDefine) dd;
			try {
				boolean flag = true;
				Map<String, String> newData = null;
				if (vo != null && vo.getListParams() != null && vo.getListParams().get(datadefineName) != null
						&& !vo.getListParams().get(datadefineName).isEmpty()) {
					newData = vo.getListParams().get(datadefineName).get(0);
				}
				logger.debug("start compare data in {} [{}]", pageVO.getId(), pageVO.getName());
				// 比对数据
				// put data in cache
				if (pageVO != null && pageVO.getId() != null) {
					String cacheKey = CACHE_KEY_PREFIX + pageVO.getId();
					Cache documentCache = cacheManager.getCache("document");
					if (documentCache != null) {
						Element tmp = documentCache.get(cacheKey);
						if (tmp != null) {
							listParams = (Map<String, List<Map<String, String>>>) tmp;
							if (listParams.get(datadefineName) != null && !listParams.get(datadefineName).isEmpty()) {
								Map<String, String> memData = listParams.get(datadefineName).get(0);
								if (newData != null && !newData.isEmpty()) {
									boolean equal = true;
									for (Iterator<String> it = newData.keySet().iterator(); it.hasNext();) {
										String key = it.next();
										if (newData.get(key) == null) {
											if (memData.get(key) != null) {
												equal = false;
												memData.put(key, null);
											}
										} else if (!newData.get(key).equalsIgnoreCase(memData.get(key))) {
											equal = false;
											memData.put(key, newData.get(key));
										}
									}
									if (equal)
										flag = false;
								}
							}

						}
					}
				}
				logger.debug("end compare data in {} [{}]", pageVO.getId(), pageVO.getName());
				logger.debug("{} data in {} [{}] is be changed", datadefineName, pageVO.getId(), pageVO.getName());
				if (flag) {
					logger.debug("start update {} data in {} [{}] is be changed", datadefineName, pageVO.getId(),
							pageVO.getName());
					metaModelOperateServiceImpl.update(vo.getListParams().get(datadefineName).get(0),
							md.getModelCode());
					logger.debug("end update {} data in {} [{}] is be changed", datadefineName, pageVO.getId(),
							pageVO.getName());
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public String saveModelData(DynamicPageVO pageVO, DocumentVO vo, String datadefineName) {
		DynamicPage page = BeanUtils.getNewInstance(pageVO, DynamicPage.class);
		DataDefine dd = PageDataBeanWorker.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(page.getDataJson()))
				.get(datadefineName);
		if (dd != null) {
			// get data from doc.requestMap, and put it into map with model
			// processParams(vo);executeAct 方法中已经做处理
			DataDefine md = (DataDefine) dd;
			String id = null;
			try {
				Map<String, String> data = vo.getListParams().get(datadefineName).get(0);
				if (data != null && !data.isEmpty()) {
					if (data.containsKey("ID")) {
						id = data.get("ID");
						if (StringUtils.isBlank(id)) {
							id = UUID.randomUUID().toString();
							data.put("ID", id);
							vo.setRecordId(id);
						}
					}
				}

				metaModelOperateServiceImpl.save(data, md.getModelCode());
				vo.setTableName(metaModelServiceImpl.queryByModelCode(md.getModelCode()).getTableName());
				return id;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map saveModelDataFlow(DynamicPageVO pageVO, DocumentVO vo, String datadefineName, boolean masterDateSource)
			throws Exception {
		DynamicPage page = BeanUtils.getNewInstance(pageVO, DynamicPage.class);
		DataDefine dd = PageDataBeanWorker.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(page.getDataJson()))
				.get(datadefineName);
		Map resultMap = null;
		if (dd != null) {
			// get data from doc.requestMap, and put it into map with model
			// processParams(vo);executeAct 方法中已经做处理
			DataDefine md = (DataDefine) dd;
			String id = null;
			Map<String, String> data = vo.getListParams().get(datadefineName).get(0);
			if (data != null && !data.isEmpty()) {
				if (data.containsKey("ID") || data.containsKey("id")) {
					if (!StringUtils.isBlank(data.get("ID"))) {
						id = data.get("ID");
					} else if (!StringUtils.isBlank(data.get("id"))) {
						id = data.get("id");
					} else if (masterDateSource) {
						id = vo.getRecordId();
					}
					if (StringUtils.isBlank(id)) {
						id = UUID.randomUUID().toString();
						data.put("ID", id);
						vo.setRecordId(id);
					}
				}
			}

			Map<String, Object> map = metaModelOperateServiceImpl.get(id, md.getModelCode());
			resultMap = new HashMap();
			if (map != null && map.size() > 0) {
				if (metaModelOperateServiceImpl.update(data, md.getModelCode())) {
					resultMap.put("success", "true");
				} else {
					resultMap.put("success", "false");
					resultMap.put("message", "更新失败");
				}
			} else {

				if (metaModelOperateServiceImpl.save(data, md.getModelCode())) {
					resultMap.put("success", "true");
				} else {
					resultMap.put("success", "false");
					resultMap.put("message", "保存失败");
				}
			}
			vo.setTableName(metaModelServiceImpl.queryByModelCode(md.getModelCode()).getTableName());
			resultMap.put("id", id);
		}
		return resultMap;

	}

	@Override
	public void excuteUpdate(String sql) {
		// Long dataSourceId = getDataSourceIdByCurrentSystem();
		// JdbcTemplate jdbcTemplate =
		// DataSourceFactory.getJdbcTemplateById(dataSourceId);
		jdbcTemplate.execute(sql);
	}

	@Override
	public Map<String, Object> excuteQuery(String sql) {
		// Long dataSourceId = getDataSourceIdByCurrentSystem();
		// JdbcTemplate jdbcTemplate =
		// DataSourceFactory.getJdbcTemplateById(dataSourceId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = jdbcTemplate.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> excuteQueryForList(String sql) {
		// Long dataSourceId = getDataSourceIdByCurrentSystem();
		// JdbcTemplate jdbcTemplate =
		// DataSourceFactory.getJdbcTemplateById(dataSourceId);
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void excuteUpdate(String sql, String dsName) {

		jdbcTemplate.execute(sql);
	}

	@Override
	public Map<String,Object> excuteQuery(String sql, String dsName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = jdbcTemplate.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> excuteQueryForList(String sql, String dsName) {

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public String insertModelData(Map<String, String> map, String modelCode) {
		String id = null;
		try {
			if (map != null && !map.isEmpty()) {
				if (map.containsKey("ID")) {
					id = map.get("ID");
					if (!StringUtils.isNotBlank(id)) {
						id = UUID.randomUUID().toString();
						map.put("ID", id);
					}
				}
				metaModelOperateServiceImpl.save(map, modelCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public String updateModelData(Map<String, String> map, String modelCode) {
		String id = null;
		try {
			if (map != null && !map.isEmpty()) {
				metaModelOperateServiceImpl.update(map, modelCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public String print(String docId) {
		return null;
	}

	@Override
	public Boolean deleteModelData(DynamicPageVO pageVo, String recordId) {
		try {
			DynamicPage page = BeanUtils.getNewInstance(pageVo, DynamicPage.class);
			Map<String, DataDefine> dds = PageDataBeanWorker
					.convertConfToDataDefines(StringEscapeUtils.unescapeHtml4(page.getDataJson()));
			for (Entry<String, DataDefine> entry : dds.entrySet()) {
				DataDefine dd = entry.getValue();
				if (dd != null) {
					DataDefine md = (DataDefine) dd;
					metaModelOperateServiceImpl.delete(recordId, md.getModelCode());
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PageList<DocumentVO> selectPagedByExample(BaseExample baseExample, 
			int currentPage, int pageSize,String sortString) {
		PageList<Document> result = queryChannel.selectPagedByExample(Document.class, baseExample, 
				currentPage,pageSize, sortString);
		PageList<DocumentVO> resultVO = new PageList<DocumentVO>(result.getPaginator());
		for (Document doc : result) {
			resultVO.add(BeanUtils.getNewInstance(doc, DocumentVO.class));
		}
		result.clear();
		return resultVO;
	}

	@Override
	public boolean deleteByExample(BaseExample example) {
		try {
			List<DocumentVO> list = this.selectPagedByExample(example, 1, Integer.MAX_VALUE, null);
			for (DocumentVO doc : list) {
				delete(doc);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override // 计划废弃
	public Long findWorkId(String definationId) {
		return 0L;
	}

	@Override // 计划废弃
	public Long findNodeId(String taskDefinationKey, Long workId) {
		return 0L;
	}

}
