package org.szcloud.framework.metadesigner.util;

import java.util.List;
import java.util.Map;

public interface TreeUtils {
	
	/**
	 * 查询所有节点生成树
	 * @return
	 */
	public List<Map<String,Object>> findAll(String modelCode);
	
	/**
	 * 查询所有的根节点
	 * @return
	 */
	public List<Map<String,Object>> queryRootNodes(String modelCode);
	
	/**
	 * 根据根节点的Id查询所有子节点
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> queryChildNodesByRoot(String modelCode,long id);
	
	/**
	 * 查询某一个节点下面所有的子节点
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> queryChildNodes(String modelCode,long id);
	
	/**
	 * 查询某一个节点的父节点
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryParentNodes(String modelCode,long id);

	/**
	 * 查询某一节点的根节点
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryRootByNode(String modelCode,long id);
	
	/**
	 * 根据某一层级，查询该节点下面所有的节点
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> queryNodesByRank(String modelCode,int id);

}
