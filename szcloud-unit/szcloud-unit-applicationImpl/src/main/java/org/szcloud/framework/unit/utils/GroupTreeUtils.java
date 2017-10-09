package org.szcloud.framework.unit.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.szcloud.framework.unit.vo.PunGroupVO;
import org.szcloud.framework.unit.vo.PunResourceTreeNode;

public class GroupTreeUtils {
	/*
	 * 存储需要遍历的资源结点；
	 */
	private HashMap<String, PunGroupVO> resourceNodes;

	/*
	 * 存储已遍历的资源结点；
	 */
	private HashMap<String, String> processedNodes;

	/**
	 * 初始化资源结点和已遍历的资源结点
	 * 
	 * @param resources:需要生产资源树的结点集合；
	 */
	private void initialize(List<PunGroupVO> resources) {
		resourceNodes = new HashMap<String, PunGroupVO>();
		processedNodes = new HashMap<String, String>();
		for (int i = 0; i < resources.size(); i++) {
			PunGroupVO temp = resources.get(i);
			if (temp.getParentGroupId() == null) {
				temp.setParentGroupId(new Long(0));
			}
			resourceNodes.put(temp.getGroupId().toString(), temp);
		}
	}

	/**
	 * 根据资源结点结合，生成多棵资源树，并返回树集合；
	 * 
	 * @param resources
	 * @return 资源树数组集合；
	 */
	public List<PunGroupVO[]> generateTreeView(List<PunGroupVO> resources) {
		initialize(resources);
		List<PunGroupVO[]> forest = new ArrayList<PunGroupVO[]>();
		List<PunGroupVO> roots = getRoots();
		for (int i = 0; i < roots.size(); i++) {
			PunGroupVO root = roots.get(i);
			String keysString = generateNodeIdString(root);
			PunGroupVO[] tree = getPunResourceArrayByKeys(keysString);
			if (tree != null) {
				forest.add(tree);
			}
		}
		return forest;
	}

	/**
	 * 根据某一资源结点，找到其所在那棵树的遍历结点Id列表，例如1;2;4;3等；
	 * 
	 * @param resource
	 * @return 返回结点ID列表，用”;"分隔并以“;"结尾;
	 */
	private String generateNodeIdString(PunGroupVO resource) {
		PunGroupVO parent = getParent(resource);
		List<PunGroupVO> children = getChildren(resource);
		if (parent != null && !processedNodes.containsKey(parent.getGroupId().toString())) {
			return generateNodeIdString(parent);
		}
		if (children.size() == 0) {
			processedNodes.put(resource.getGroupId().toString(), resource.getGroupId().toString());
			return resource.getGroupId().toString() + ";";
		}
		StringBuilder sb = new StringBuilder();
		if (children.size() > 0) {
			processedNodes.put(resource.getGroupId().toString(), resource.getGroupId().toString());
			sb.append(resource.getGroupId().toString() + ";");
			for (int i = 0; i < children.size(); i++) {
				PunGroupVO temp = children.get(i);
				sb.append(generateNodeIdString(temp));
			}
		}
		return sb.toString();
	}

	/**
	 * 在集合中查找所有的树根结点；
	 * 
	 * @return 树根结点集合;
	 */
	private List<PunGroupVO> getRoots() {
		List<PunGroupVO> roots = new ArrayList<PunGroupVO>();
		for (PunGroupVO source : resourceNodes.values()) {
			if (source.getParentGroupId() == null || !resourceNodes.containsKey(source.getParentGroupId().toString())) {
				roots.add(source);
			}
		}
		ComparatorPunResource comparator = new ComparatorPunResource();
		Collections.sort(roots, comparator);
		return roots;
	}

	/**
	 * 在集合中查某一结点的子节点并安装sequence排序;
	 * 
	 * @param resource
	 * @return 有序的子节点集合;
	 */
	private List<PunGroupVO> getChildren(PunGroupVO resource) {
		List<PunGroupVO> children = new ArrayList<PunGroupVO>();
		for (PunGroupVO temp : resourceNodes.values()) {
			if (temp.getParentGroupId() != null && temp.getParentGroupId().compareTo(resource.getGroupId()) == 0) {
				children.add(temp);
			}
		}
		ComparatorPunResource comparator = new ComparatorPunResource();
		Collections.sort(children, comparator);
		return children;
	}

	/**
	 * 在集合中查找某一结点的父节点
	 * 
	 * @param resource
	 * @return 父节点
	 */
	private PunGroupVO getParent(PunGroupVO resource) {
		if (resource.getParentGroupId() == null)
			return null;
		if (resourceNodes.containsKey(resource.getParentGroupId().toString())) {
			return (PunGroupVO) resourceNodes.get(resource.getParentGroupId().toString());
		}
		return null;
	}

	/**
	 * 实现PunResource排序的类；
	 * 
	 * @author Bobo
	 *
	 */
	public class ComparatorPunResource implements Comparator<PunGroupVO> {
		/**
		 * 比较两个PunResource的大小;
		 */
		public int compare(PunGroupVO a, PunGroupVO b) {
			return a.getGroupId().compareTo(b.getGroupId());
		}
	}

	/**
	 * 根据ID列表提取有序的资源结点数组;
	 * 
	 * @param keysString
	 * @return 有序的资源结点数组;
	 */
	private PunGroupVO[] getPunResourceArrayByKeys(String keysString) {
		if (keysString == null)
			return null;
		if (keysString.endsWith(";")) {
			keysString = keysString.substring(0, keysString.length() - 1);
		}
		if (keysString.length() == 0)
			return null;
		String[] keys = keysString.split(";");
		PunGroupVO[] rtnAry = new PunGroupVO[keys.length];
		for (int i = 0; i < keys.length; i++) {
			rtnAry[i] = (PunGroupVO) resourceNodes.get(keys[i]);
		}
		return rtnAry;
	}

	public static List<PunResourceTreeNode> getPlainZNodes(PunGroupVO[] resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunGroupVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getGroupId());
			zNode.setId(eachRes.getGroupId());
			// zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentGroupId());
			zNode.setName(eachRes.getGroupChName());
			zNode.setChecked(false);
			zNode.setGroupType(eachRes.getGroupType());
			if (eachRes.getGroupAddress() == null || eachRes.getGroupAddress().equalsIgnoreCase("NULL")) {
				// 如果address是为null，就把url设为空地址
				zNode.setUrl("");
			} else {
				zNode.setUrl(eachRes.getGroupAddress());
			}
			zNode.setTarget("main");
			zNode.setOpen(true);
			zNode.setisParent(false);
			zNode.setNumber(eachRes.getNumber());
			zNodes.add(zNode);
		}

		return zNodes;
	}

	/**
	 * 格式化输出树结点，去掉rul,target,设置菜单为展开（即open=true;
	 * 
	 * @param menuList
	 */
	public static List<PunResourceTreeNode> getPlainAccessZNodes(PunGroupVO[] resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunGroupVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getGroupId());
			zNode.setId(eachRes.getGroupId());
			// zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentGroupId());
			zNode.setName(eachRes.getGroupChName());
			zNode.setChecked(false);
			zNode.setUrl("");
			zNode.setTarget("");
			zNode.setOpen(true);
			zNode.setisParent(false);
			zNodes.add(zNode);
		}
		return zNodes;
	}

	public static String getLastPId(String PId) {
		return PId.split(",")[PId.split(",").length - 1];
	}

}
