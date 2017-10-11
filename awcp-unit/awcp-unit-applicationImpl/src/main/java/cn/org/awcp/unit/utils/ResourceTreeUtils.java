package cn.org.awcp.unit.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import cn.org.awcp.unit.vo.PunManageMenuVO;
import cn.org.awcp.unit.vo.PunMenuVO;
import cn.org.awcp.unit.vo.PunResourceTreeNode;

/**
 * @ClassName: ResourceTreeUtils
 * @Description: 资源树的操作工具工具
 * @author Bobo
 *
 */
public class ResourceTreeUtils {
	/*
	 * 存储需要遍历的资源结点；
	 */
	private HashMap<String, PunMenuVO> resourceNodes;

	/*
	 * 存储已遍历的资源结点；
	 */
	private HashMap<String, String> processedNodes;

	/**
	 * 初始化资源结点和已遍历的资源结点
	 * 
	 * @param resources:需要生产资源树的结点集合；
	 */
	private void initialize(List<PunMenuVO> resources) {
		resourceNodes = new HashMap<String, PunMenuVO>();
		processedNodes = new HashMap<String, String>();
		for (int i = 0; i < resources.size(); i++) {
			PunMenuVO temp = resources.get(i);
			if (temp.getParentMenuId() == null) {
				temp.setParentMenuId(new Long(0));
			}
			resourceNodes.put(temp.getMenuId().toString(), temp);
		}
	}

	/**
	 * 根据资源结点结合，生成多棵资源树，并返回树集合；
	 * 
	 * @param resources
	 * @return 资源树数组集合；
	 */
	public List<PunMenuVO[]> generateTreeView(List<PunMenuVO> resources) {
		initialize(resources);
		List<PunMenuVO[]> forest = new ArrayList<PunMenuVO[]>();
		List<PunMenuVO> roots = getRoots();
		for (int i = 0; i < roots.size(); i++) {
			PunMenuVO root = roots.get(i);
			String keysString = generateNodeIdString(root);
			PunMenuVO[] tree = getPunResourceArrayByKeys(keysString);
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
	private String generateNodeIdString(PunMenuVO resource) {
		PunMenuVO parent = getParent(resource);
		List<PunMenuVO> children = getChildren(resource);
		if (parent != null && !processedNodes.containsKey(parent.getMenuId().toString())) {
			return generateNodeIdString(parent);
		}
		if (children.size() == 0) {
			processedNodes.put(resource.getMenuId().toString(), resource.getMenuId().toString());
			return resource.getMenuId().toString() + ";";
		}
		StringBuilder sb = new StringBuilder();
		if (children.size() > 0) {
			processedNodes.put(resource.getMenuId().toString(), resource.getMenuId().toString());
			sb.append(resource.getMenuId().toString() + ";");
			for (int i = 0; i < children.size(); i++) {
				PunMenuVO temp = children.get(i);
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
	private List<PunMenuVO> getRoots() {
		List<PunMenuVO> roots = new ArrayList<PunMenuVO>();
		for (PunMenuVO source : resourceNodes.values()) {
			if (source.getParentMenuId() == null || !resourceNodes.containsKey(source.getParentMenuId().toString())) {
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
	private List<PunMenuVO> getChildren(PunMenuVO resource) {
		List<PunMenuVO> children = new ArrayList<PunMenuVO>();
		for (PunMenuVO temp : resourceNodes.values()) {
			if (temp.getParentMenuId() != null && temp.getParentMenuId().compareTo(resource.getMenuId()) == 0) {
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
	private PunMenuVO getParent(PunMenuVO resource) {
		if (resource.getParentMenuId() == null)
			return null;
		if (resourceNodes.containsKey(resource.getParentMenuId().toString())) {
			return (PunMenuVO) resourceNodes.get(resource.getParentMenuId().toString());
		}
		return null;
	}

	/**
	 * 实现PunResource排序的类；
	 * 
	 * @author Bobo
	 *
	 */
	public class ComparatorPunResource implements Comparator<PunMenuVO> {
		/**
		 * 比较两个PunResource的大小;
		 */
		public int compare(PunMenuVO a, PunMenuVO b) {
			if (a.getMenuSeq() == null)
				return -1;
			if (b.getMenuSeq() == null)
				return 1;
			return a.getMenuSeq().compareTo(b.getMenuSeq());
		}
	}

	/**
	 * 根据ID列表提取有序的资源结点数组;
	 * 
	 * @param keysString
	 * @return 有序的资源结点数组;
	 */
	private PunMenuVO[] getPunResourceArrayByKeys(String keysString) {
		if (keysString == null)
			return null;
		if (keysString.endsWith(";")) {
			keysString = keysString.substring(0, keysString.length() - 1);
		}
		if (keysString.length() == 0)
			return null;
		String[] keys = keysString.split(";");
		PunMenuVO[] rtnAry = new PunMenuVO[keys.length];
		for (int i = 0; i < keys.length; i++) {
			rtnAry[i] = (PunMenuVO) resourceNodes.get(keys[i]);
		}
		return rtnAry;
	}

	public static List<PunResourceTreeNode> getPlainZNodes(List<PunMenuVO> resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunMenuVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
			zNode.setId(eachRes.getMenuId());
			// zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentMenuId());
			zNode.setName(eachRes.getMenuName());
			zNode.setChecked(eachRes.isChecked());
			if (eachRes.getMenuAddress() == null || eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
				// 如果address是为null，就把url设为空地址
				zNode.setUrl("");
			} else {
				zNode.setUrl(eachRes.getMenuAddress());
			}
			if (eachRes.getMenuType() != null) {
				if (eachRes.getMenuType().equals("1")) {// 菜单地址
					if (eachRes.getMenuAddress() == null || eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
						// 如果address是为null，就把url设为空地址
						zNode.setUrl("");
					} else {
						zNode.setUrl(eachRes.getMenuAddress());
					}
				} else if (eachRes.getMenuType().equals("2")) {// 动态表单地址
					if (eachRes.getDynamicPageId() != null) {
						zNode.setUrl("document/view.do?id=&amp;dynamicPageId=" + eachRes.getDynamicPageId());
					} else {
						zNode.setUrl("");
					}
				}
			}
			zNode.setTarget("main");
			zNode.setOpen(false);
			zNode.setisParent(false);
			zNodes.add(zNode);
		}
		return zNodes;
	}

	public static List<PunResourceTreeNode> getPlainZNodes(PunMenuVO[] resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunMenuVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
			zNode.setId(eachRes.getMenuId());
			// zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentMenuId());
			zNode.setName(eachRes.getMenuName());
			zNode.setChecked(eachRes.isChecked());
			if (eachRes.getMenuAddress() == null || eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
				// 如果address是为null，就把url设为空地址
				zNode.setUrl("");
			} else {
				zNode.setUrl(eachRes.getMenuAddress());
			}
			if (eachRes.getMenuType() != null) {
				if (eachRes.getMenuType().equals("1")) {// 菜单地址
					if (eachRes.getMenuAddress() == null || eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
						// 如果address是为null，就把url设为空地址
						zNode.setUrl("");
					} else {
						zNode.setUrl(eachRes.getMenuAddress());
					}
				} else if (eachRes.getMenuType().equals("2")) {// 动态表单地址
					if (eachRes.getDynamicPageId() != null) {
						zNode.setUrl("document/view.do?id=&amp;dynamicPageId=" + eachRes.getDynamicPageId());
					} else {
						zNode.setUrl("");
					}
				}
			}
			zNode.setTarget("main");
			zNode.setOpen(false);
			zNode.setisParent(false);
			zNodes.add(zNode);
		}

		return zNodes;
	}

	/**
	 * 格式化输出树结点，去掉rul,target,设置菜单为展开（即open=true;
	 * 
	 * @param menuList
	 */
	public static List<PunResourceTreeNode> getPlainAccessZNodes(PunMenuVO[] resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunMenuVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
			zNode.setId(eachRes.getMenuId());
			// zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentMenuId());
			zNode.setName(eachRes.getMenuName());
			zNode.setChecked(eachRes.isChecked());
			zNode.setUrl("");
			zNode.setTarget("");
			zNode.setOpen(true);
			zNode.setisParent(false);
			zNodes.add(zNode);
		}

		return zNodes;
	}

	/**
	 * ljw 开发方资源授权专用
	 * 
	 * @param resource
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	public static List<PunResourceTreeNode> getPlainDevAccessZNodes(PunMenuVO[] resource, Long roleId) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunMenuVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
			zNode.setId(eachRes.getMenuId());
			// zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentMenuId());
			zNode.setName(eachRes.getMenuName());
			zNode.setChecked(eachRes.isChecked());
			if (eachRes.getDynamicPageId() != null) {
				zNode.setUrl("");
				// zNode.setUrl("unit/queryComponent.do?sysId="+eachRes.getSysId()+"&roleId="+roleId);
				zNode.setTarget("sysEditFrame");
			} else {
				zNode.setUrl("");
				zNode.setTarget("");
			}
			zNode.setOpen(true);
			zNode.setisParent(false);
			zNodes.add(zNode);
		}

		return zNodes;
	}

	public static String getLastPId(String PId) {
		return PId.split(",")[PId.split(",").length - 1];
	}

	public static List<PunMenuVO> getMenuVOListFromManage(List<PunManageMenuVO> devMenuVOList) {
		List<PunMenuVO> menuVOList = new ArrayList<PunMenuVO>();
		for (PunManageMenuVO devMenuVO : devMenuVOList) {
			PunMenuVO menuVO = new PunMenuVO();
			menuVO.setChecked(devMenuVO.isChecked());
			menuVO.setMenuAddress(devMenuVO.getMenuAddress());
			menuVO.setMenuId(devMenuVO.getMenuId());
			menuVO.setMenuName(devMenuVO.getMenuName());
			menuVO.setMenuSeq(devMenuVO.getMenuSeq());
			menuVO.setMenuType(devMenuVO.getMenuType());
			menuVO.setOperateType(devMenuVO.getOperateType());
			menuVO.setParentMenuId(devMenuVO.getParentMenuId());
			menuVO.setPid(devMenuVO.getPid());
			menuVO.setSysId(devMenuVO.getSysId());

			menuVOList.add(menuVO);
		}
		return menuVOList;
	}
}
