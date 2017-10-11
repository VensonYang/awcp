package cn.org.awcp.metadesigner.util;

import java.util.List;
import java.util.Map;

public interface RelationQuery {
	
	/**
	 * 级联的顶级查询
	 * @param modelCode
	 * @return
	 */
	public List<Map<String,Object>> queryRoot(String modelCode);
	
	/**
	 * 一对多的关系查询
	 * @param modelCode1	主表
	 * @param modelCode2	从表
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> queryChild(String modelCode1,String modelCode2,Object id);
	
	/**
	 * 一对一的关系查询
	 * @param modelCode1	主表
	 * @param modelCode2	从表
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryOne(String modelCode1,String modelCode2,Object id);
	
	/**
	 * 根据多对一的关系 ，通过多的一方查询一的一方
	 * @param modelCode1	主表
	 * @param modelCode2	从表
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryManyToOne(String modelCode1,String modelCode2,Object id);
}
