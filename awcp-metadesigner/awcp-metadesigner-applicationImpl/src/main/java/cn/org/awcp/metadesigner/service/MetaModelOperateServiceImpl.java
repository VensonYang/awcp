package cn.org.awcp.metadesigner.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.utils.Tools;
import cn.org.awcp.core.utils.constants.SessionContants;
import cn.org.awcp.metadesigner.application.MetaModelItemService;
import cn.org.awcp.metadesigner.application.MetaModelOperateService;
import cn.org.awcp.metadesigner.application.MetaModelService;
import cn.org.awcp.metadesigner.application.ModelRelationService;
import cn.org.awcp.metadesigner.util.DataConvert;
import cn.org.awcp.metadesigner.vo.MetaModelItemsVO;
import cn.org.awcp.metadesigner.vo.MetaModelVO;
import cn.org.awcp.metadesigner.vo.ModelRelationVO;
import cn.org.awcp.unit.service.SysSourceRelationService;
import cn.org.awcp.unit.vo.PunSystemVO;
import cn.org.awcp.unit.vo.SysDataSourceVO;

@Service(value = "metaModelOperateServiceImpl")
public class MetaModelOperateServiceImpl implements MetaModelOperateService {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MetaModelItemService metaModelItemsServiceImpl;

	@Autowired
	private MetaModelService metaModelServiceImpl;

	@Autowired
	private DataConvert dataConvertImpl;

	@Autowired
	private ModelRelationService modelRelationServiceImpl;

	@Autowired
	@Qualifier("sysSourceRelationServiceImpl")
	SysSourceRelationService sysSourceRelationService;

	/**
	 * 保存
	 */
	public boolean save(Map<String, String> map, String modelCode) throws Exception {
		// 查询
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode.trim());
		StringBuffer sb = new StringBuffer("insert into ").append(mm.getTableName()).append(" (");
		StringBuilder values = new StringBuilder();
		List<MetaModelItemsVO> mmi = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		Map<String, MetaModelItemsVO> map2 = new HashMap<String, MetaModelItemsVO>();
		Map<String, Object> maps = new HashMap<String, Object>();
		for (MetaModelItemsVO tmp : mmi) {
			map2.put(tmp.getItemCode(), tmp);
		}
		for (String s : map.keySet()) {
			MetaModelItemsVO tmp = map2.get(s);
			if (tmp != null) {
				Object o = this.dataConvertImpl.stringToObject(map.get(s), tmp.getItemType());
				maps.put(s, o);
				sb.append(s);
				sb.append(",");
				values.append(":");
				values.append(s);
				values.append(",");
			}
		}
		try {
			if (values.length() > 0) {
				/**
				 * 去除groupId modify by venson 20170320
				 * 
				 */
				sb.delete(sb.length() - 1, sb.length());
				sb.append(") value(");
				sb.append(values.delete(values.length() - 1, values.length())).append(");");
				NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				namedParameterJdbcTemplate.update(sb.toString(), maps);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据modelCode删除
	 */
	public boolean delete(Object id, String modelCode) {
		MetaModelVO mm = this.metaModelServiceImpl.queryByModelCode(modelCode);
		StringBuffer sb = new StringBuffer("delete from ").append(mm.getTableName()).append(" where ");
		List<MetaModelItemsVO> lss = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		for (MetaModelItemsVO vo : lss) {
			if (vo.getUsePrimaryKey() != null && (vo.getUsePrimaryKey() == 1)) {
				sb.append(vo.getItemCode());
			}
		}
		sb.append("=?");
		// 查询是否有外键
		List<ModelRelationVO> list = modelRelationServiceImpl.queryByModelId(mm.getId());
		if (list.size() > 0) {
			for (ModelRelationVO ls : list) {
				// 查询外键列
				MetaModelItemsVO mmi = metaModelItemsServiceImpl.get(ls.getItemId());
				// 查询对应的外键表
				MetaModelVO mmv = metaModelServiceImpl.get(mmi.getModelId());
				StringBuffer sbb = new StringBuffer("delete from ");
				sbb.append(mmv.getTableName());
				sbb.append(" where ");
				sbb.append(mmi.getItemCode());
				sbb.append("=?");
				try {
					jdbcTemplate.update(sbb.toString(), id);
				} catch (Exception e) {
					return false;
				}
			}
		}
		try {
			jdbcTemplate.update(sb.toString(), id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据条件删除
	 * 
	 * @params:map 条件：map中的键（key）需要有前缀 “=”--"et_" ">"--"gt_" "<"--"lt_"
	 *             "!="--"nt_" "like"--"lt_"
	 * @param :modelCode
	 */
	public boolean deleteByParams(Map<String, String> map, String modelCode) throws ParseException {
		StringBuffer sb = new StringBuffer("delete from ");
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		sb.append(mm.getTableName());
		sb.append(" where 1=1");
		if (map != null && map.size() > 0) {
			for (String s : map.keySet()) {
				sb.append(" and ");
				sb.append(s);
				sb.append(this.markCheck(s));
				sb.append(":");
				sb.append(s);
			}
		}
		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			namedParameterJdbcTemplate.update(sb.toString(), map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据条件删修改 map为将要更新的数据
	 * 
	 * @params:map map中的key为 modelCode中的字段名
	 * @param :modelCode
	 */
	public boolean update(Map<String, String> map, String modelCode) throws ParseException {
		// 根据modelcode查出模型
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode.trim());
		StringBuffer sb = new StringBuffer("update ").append(mm.getTableName()).append(" set ");
		StringBuilder where = new StringBuilder(" where ");
		// 根据modelid找到所有的属性
		List<MetaModelItemsVO> lss = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		Map<String, MetaModelItemsVO> map2 = new HashMap<String, MetaModelItemsVO>();
		// 将查出的list转换成map，key为属性code
		for (MetaModelItemsVO tmp : lss) {
			map2.put(tmp.getItemCode(), tmp);
		}
		// 遍历数据，拼装SQL语句
		Map<String, Object> maps = new HashMap<String, Object>();
		int size = 0;// 更新的字段数，如果为空则不更新
		for (String s : map.keySet()) {
			// 根据数据的key（也就是数据字段的名字），找到对应的模型属性
			MetaModelItemsVO tmp = map2.get(s);
			if (tmp != null) {
				// 将数据进行转化，因为提交的数据都是字符串类型，而数据库中有多种
				Object o = this.dataConvertImpl.stringToObject(map.get(s), tmp.getItemType());
				maps.put(s, o);
				// 如果模型属性是主键，则特殊处理，否则写成itemcode=:itemcode的形式
				if (tmp.getUsePrimaryKey() != null && (tmp.getUsePrimaryKey() == 1)) {
					if (tmp.getItemType().equalsIgnoreCase("varchar") || tmp.getItemType().equalsIgnoreCase("char")) {
						where.append(tmp.getItemCode()).append("=").append("'").append(map.get(tmp.getItemCode())).append("'");
					} else {
						where.append(tmp.getItemCode());
						where.append("=");
						where.append(map.get(tmp.getItemCode()));
					}
				} else {
					sb.append(tmp.getItemCode());
					sb.append("=");
					sb.append(":");
					sb.append(tmp.getItemCode());
					sb.append(",");
					size++;
				}
			}
		}
		sb.deleteCharAt(sb.length() - 1).append(where);
		try {
			if (size > 0) {// 如果要更新的字段数大于0，则更新
				NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				namedParameterJdbcTemplate.update(sb.toString(), maps);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询
	 * 
	 * @param :modelCode
	 */
	public List<Map<String, String>> findAll(String modelCode) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		StringBuffer sb = new StringBuffer("select ");
		List<MetaModelItemsVO> lss = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		for (MetaModelItemsVO vo : lss) {
			sb.append(vo.getItemCode());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		for (Map<String, Object> m : list) {
			Map<String, String> maps = new HashMap<String, String>();
			for (String s : m.keySet()) {
				maps.put(s, String.valueOf(m.get(s)));
			}
			ls.add(maps);
		}
		return ls;
	}

	/**
	 * 单个查询
	 * 
	 * @param :modelCode
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> get(Object id, String modelCode) {
		MetaModelVO mms = metaModelServiceImpl.queryByModelCode(modelCode);
		StringBuffer sb = new StringBuffer("select ");
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mms.getId());
		String name = "";
		for (MetaModelItemsVO mm : ls) {
			String itemCode = mm.getItemCode();
			if (itemCode.equalsIgnoreCase("CREATOR")) {
				sb.append("(SELECT NAME FROM p_un_user_base_info WHERE user_id=creator) AS CREATOR");
				sb.append(",");
			} else {
				sb.append(itemCode);
				sb.append(",");
			}
			if ((mm.getUsePrimaryKey() != null) && (mm.getUsePrimaryKey() == 1)) {
				name = mm.getItemCode();
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		MetaModelVO mmv = metaModelServiceImpl.get(mms.getId());
		sb.append(mmv.getTableName());
		sb.append(" where ");
		sb.append(name);
		sb.append("=?");
		List<Map<String, Object>> map = jdbcTemplate.queryForList(sb.toString(), id);
		if (map.size() == 1) {
			Map<String, Object> data = map.get(0);
			return data;
		}
		return Collections.EMPTY_MAP;
	}

	public String markCheck(String str) {
		String s = str.substring(0, 3);
		if (s.equals("lk_")) {
			return " like ";
		}
		if (s.equals("gt_")) {
			return ">=";
		}
		if (s.equals("lt_")) {
			return "<=";
		}
		if (s.equals("nt_")) {
			return "!=";
		}
		if (s.equals("et_")) {
			return "=";
		}
		if (s.equals("in_")) {
			return " in ";
		}
		return "=";
	}

	public List<Map<String, Object>> search(String sql, Object... obj) {
		List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql, obj);
		return ls;
	}

	public int queryOne(String sql, Object... obj) {
		int count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
		return count;
	}

	/**
	 * 
	 * update
	 */
	public int updateBySql(String sql, Object... obj) {
		int count = jdbcTemplate.update(sql, obj);
		return count;
	}

	/**
	 * 根据元数据code获取到数据源ID，如果数据源没配置，则获取系统默认数据源Id
	 */
	public Long getDataSourceIdByModelCode(String modelCode) {
		MetaModelVO mmv = metaModelServiceImpl.queryByModelCode(modelCode);
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
			PageList<SysDataSourceVO> dataVos = sysSourceRelationService.selectPagedByExample(base, 1,Integer.MAX_VALUE, null);
			if (dataVos != null && dataVos.size() > 0) {
				return dataVos.get(0).getDataSourceId();
			}
		}
		return null;
	}

	/**
	 * 数据库类型
	 */
	public static final String DATABSE_TYPE_MYSQL = "mysql";
	public static final String DATABSE_TYPE_POSTGRE = "postgresql";
	public static final String DATABSE_TYPE_ORACLE = "oracle";
	public static final String DATABSE_TYPE_SQLSERVER = "sqlserver";
	/**
	 * 分页SQL
	 */
	public static final String MYSQL_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}"; // mysql
	public static final String POSTGRE_SQL = "select * from ( {0}) sel_tab00 limit {2} offset {1}";// postgresql
	public static final String ORACLE_SQL = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}"; // oracle
	public static final String SQLSERVER_SQL = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}"; // sqlserver

	@Override
	public Object queryObject(String sql, Object... obj) {
		List<Map<String, Object>> data = this.search(sql, obj);
		if (data == null || data.isEmpty()) {
			return 0;
		} else {
			return data.get(0).values().iterator().next();
		}
	}

}
