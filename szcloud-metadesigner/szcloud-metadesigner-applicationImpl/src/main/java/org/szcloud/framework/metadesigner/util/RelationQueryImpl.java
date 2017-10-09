package org.szcloud.framework.metadesigner.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.domain.EntityRepositoryJDBC;
import org.szcloud.framework.metadesigner.application.MetaModelItemService;
import org.szcloud.framework.metadesigner.application.MetaModelService;
import org.szcloud.framework.metadesigner.application.ModelRelationService;
import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;
import org.szcloud.framework.metadesigner.vo.ModelRelationVO;

@Service(value = "relationQueryImpl")
public class RelationQueryImpl implements RelationQuery {

	@Autowired
	private EntityRepositoryJDBC jdbcRepository;

	@Autowired
	private MetaModelItemService metaModelItemsServiceImpl;

	@Autowired
	private MetaModelService metaModelServiceImpl;

	@Autowired
	private ModelRelationService modelRelationServiceImpl;

	public List<Map<String, Object>> queryRoot(String modelCode) {
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
		List<Map<String, Object>> list = jdbcRepository.find(sb.toString());
		return list;
	}

	public List<Map<String, Object>> queryChild(String modelCode1, String modelCode2, Object id) {
		MetaModelVO m1 = metaModelServiceImpl.queryByModelCode(modelCode1);
		List<MetaModelItemsVO> lss = metaModelItemsServiceImpl.queryResult("queryResult", m1.getId());
		MetaModelVO m2 = metaModelServiceImpl.queryByModelCode(modelCode2);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", m2.getId());
		List<ModelRelationVO> mrls = modelRelationServiceImpl.queryByModelId(m1.getId());
		// 获取外键列的名称
		String fk = "";
		for (ModelRelationVO m : mrls) {
			for (MetaModelItemsVO mm : ls) {
				if (m.getItemId() == mm.getId()) {
					fk = mm.getItemCode();
				}
			}
		}

		// select ss.id,ss.pid,ss.name from s left join ss on s.id=ss.pid where
		// pid=1;
		StringBuffer sb = new StringBuffer("select ");
		for (MetaModelItemsVO mmi : ls) {
			sb.append("t1.");
			sb.append(mmi.getItemCode());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ");
		sb.append(m1.getTableName());
		sb.append(" t left join ");
		sb.append(m2.getTableName());
		sb.append(" t1 on t.");
		String pk = "";
		for (MetaModelItemsVO vo : lss) {
			if (vo.getUsePrimaryKey() == 1) {
				pk = vo.getItemCode();
			}
		}
		sb.append(pk);
		sb.append("=t1.");
		sb.append(fk);
		sb.append(" where t1.");
		sb.append(fk);
		sb.append("=?");
		List<Map<String, Object>> l = jdbcRepository.find(sb.toString(), new Object[] { id });
		return l;
	}

	public Map<String, Object> queryOne(String modelCode1, String modelCode2, Object id) {
		List<Map<String, Object>> l = queryChild(modelCode1, modelCode2, id);
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	public Map<String, Object> queryManyToOne(String modelCode1, String modelCode2, Object id) {
		MetaModelVO m1 = metaModelServiceImpl.queryByModelCode(modelCode1);
		List<MetaModelItemsVO> lss = metaModelItemsServiceImpl.queryResult("queryResult", m1.getId());
		MetaModelVO m2 = metaModelServiceImpl.queryByModelCode(modelCode2);
		List<MetaModelItemsVO> ls = metaModelItemsServiceImpl.queryResult("queryResult", m2.getId());
		List<ModelRelationVO> mrls = modelRelationServiceImpl.queryByModelId(m1.getId());
		// 获取外键列的名称
		String fk = "";
		for (ModelRelationVO m : mrls) {
			for (MetaModelItemsVO mm : ls) {
				if (m.getItemId() == mm.getId()) {
					fk = mm.getItemCode();
				}
			}
		}
		String pk2 = "";
		for (MetaModelItemsVO mm : ls) {
			if (mm.getUsePrimaryKey() == 1) {
				pk2 = mm.getItemCode();
			}
		}
		StringBuffer sb = new StringBuffer("select ");
		sb.append(fk);
		sb.append(" from ");
		sb.append(m2.getTableName());
		sb.append(" where ");
		sb.append(pk2);
		sb.append("=");
		sb.append(id);
		Map<String, Object> ma = jdbcRepository.findOne(sb.toString());
		StringBuffer s = new StringBuffer("select ");
		String pk1 = "";
		for (MetaModelItemsVO mm : lss) {
			s.append(mm.getItemCode());
			s.append(",");
			if (mm.getUsePrimaryKey() == 1) {
				pk1 = mm.getItemCode();
			}
		}
		s.deleteCharAt(s.length() - 1);
		s.append(" from ");
		s.append(m1.getTableName());
		s.append(" where ");
		s.append(pk1);
		s.append("=");
		if (ma != null) {
			s.append(ma.values().toArray()[0]);
			Map<String, Object> m = jdbcRepository.findOne(s.toString());
			return m;
		}
		return null;
	}

}
