package cn.org.awcp.metadesigner.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.awcp.core.domain.EntityRepositoryJDBC;
import cn.org.awcp.metadesigner.application.MetaModelItemService;
import cn.org.awcp.metadesigner.application.MetaModelService;
import cn.org.awcp.metadesigner.vo.MetaModelItemsVO;
import cn.org.awcp.metadesigner.vo.MetaModelVO;

@Service(value = "treeUtilsImpl")
public class TreeUtillsImpl implements TreeUtils {
	
	@Autowired
	private EntityRepositoryJDBC jdbcRepository;

	@Autowired
	private MetaModelItemService metaModelItemsServiceImpl;

	@Autowired
	private MetaModelService metaModelServiceImpl;

	public List<Map<String, Object>> findAll(String modelCode) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		StringBuffer sb = new StringBuffer("select ");
		for (MetaModelItemsVO mmi : ls) {
			sb.append(mmi.getItemCode());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		List<Map<String, Object>> list = jdbcRepository.findForListMap(sb.toString(), null);
		return list;
	}

	List<Map<String, Object>> ll = null;

	public List<Map<String, Object>> queryRootNodes(String modelCode) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		StringBuffer sb = new StringBuffer("select ");
		for (MetaModelItemsVO mmi : ls) {
			sb.append(mmi.getItemCode());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		sb.append(" where pid=0");
		List<Map<String, Object>> list = jdbcRepository.findForListMap(sb.toString(), null);
		return list;
	}

	public List<Map<String, Object>> queryChildNodesByRoot(String modelCode, long id) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		String type = "";

		StringBuffer sb = new StringBuffer("select ");
		for (MetaModelItemsVO m : ls) {
			sb.append("t1." + m.getItemCode());
			sb.append(",");
			if (m.getUsePrimaryKey() == 1) {
				type = m.getItemCode();
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		sb.append(" t left join ");
		sb.append(mm.getTableName());
		sb.append(" t1 on t.");
		sb.append(type);
		sb.append("=t1.pid where t1.pid=");
		sb.append(id);
		List<Map<String, Object>> list = jdbcRepository.findForListMap(sb.toString(), null);
		ll.addAll(list);
		return list;
	}

	public List<Map<String, Object>> queryChildNodes(String modelCode, long id) {
		MetaModelVO mm = this.metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		String type = "";

		StringBuffer sb = new StringBuffer("select ");
		for (MetaModelItemsVO m : ls) {
			sb.append("t1." + m.getItemCode());
			sb.append(",");
			if (m.getUsePrimaryKey() == 1) {
				type = m.getItemCode();
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		sb.append(" t left join ");
		sb.append(mm.getTableName());
		sb.append(" t1 on t.");
		sb.append(type);
		sb.append("=t1.pid where t1.pid=");
		sb.append(id);
		List<Map<String, Object>> list = jdbcRepository.findForListMap(sb.toString(), null);

		return list;
	}

	public Map<String, Object> queryParentNodes(String modelCode, long id) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		StringBuffer sb = new StringBuffer("select ");
		String type = "";
		for (MetaModelItemsVO m : ls) {
			sb.append(m.getItemCode());
			sb.append(",");
			if (m.getUsePrimaryKey() == 1) {
				type = m.getItemCode();
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		sb.append(" where ");
		sb.append(type);
		sb.append("=?");
		Map<String, Object> map = this.jdbcRepository.findOne(sb.toString(), new Object[] { id });
		Object o = map.get("pid");
		Map<String, Object> m = this.jdbcRepository.findOne(sb.toString(), new Object[] { o });
		return m;
	}

	public Map<String, Object> queryRootByNode(String modelCode, long id) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		StringBuffer sb = new StringBuffer("select ");
		String type = "";
		for (MetaModelItemsVO m : ls) {
			sb.append(m.getItemCode());
			sb.append(",");
			if (m.getUsePrimaryKey() == 1) {
				type = m.getItemCode();
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		sb.append(" where ");
		sb.append(type);
		sb.append("=?");
		Map<String, Object> map = jdbcRepository.findOne(sb.toString(), new Object[] { id });
		Object o = null;
		o = map.get("pid");
		Map<String, Object> map1 = null;
		// 逐层查找根节点
		while (isDataType(o)) {
			map1 = jdbcRepository.findOne(sb.toString(), new Object[] { o });
			o = map1.get("pid");
		}
		return map1;
	}

	public List<Map<String, Object>> queryNodesByRank(String modelCode, int id) {
		MetaModelVO mm = metaModelServiceImpl.queryByModelCode(modelCode);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", mm.getId());
		StringBuffer sb = new StringBuffer("select ");
		for (MetaModelItemsVO mmi : ls) {
			sb.append(mmi.getItemCode());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(mm.getTableName());
		sb.append(" where pid=?");
		// 查询出第一次
		List<Map<String, Object>> lss = jdbcRepository.find(sb.toString(), new Object[] { 0 });
		// 逐层查询
		if (id != 0) {

		} else {
			return lss;

		}
		return null;
	}

	// 判断数据类型
	public boolean isDataType(Object param) {
		if (param instanceof Integer) {
			if (Integer.parseInt(param.toString()) == 0) {
				return false;
			} else {
				return true;
			}
		} else if (param instanceof String) {
			if (param.equals("")) {
				return false;
			} else {
				return true;
			}
		} else if (param instanceof Long) {
			if (Long.parseLong(param.toString()) == 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
