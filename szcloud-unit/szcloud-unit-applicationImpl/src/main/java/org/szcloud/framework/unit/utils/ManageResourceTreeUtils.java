package org.szcloud.framework.unit.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.szcloud.framework.unit.vo.PunManageMenuVO;
import org.szcloud.framework.unit.vo.PunResourceTreeNode;

/**
* @ClassName: ResourceTreeUtils 
* @Description: 资源树的操作工具工具
 * @author wwt
 *
 */
public class ManageResourceTreeUtils {
	/*
	 * 存储需要遍历的资源结点；
	 */
	private HashMap<String, PunManageMenuVO> resourceNodes ;
	
	/*
	 * 存储已遍历的资源结点；
	 */
	private HashMap<String, String> processedNodes;	
	
	/**
	 * 初始化资源结点和已遍历的资源结点
	 * @param resources:需要生产资源树的结点集合；
	 */
	private void initialize(List<PunManageMenuVO> resources){
		resourceNodes = new HashMap<String, PunManageMenuVO>();
		processedNodes = new HashMap<String, String>();
		for(int i = 0; i < resources.size(); i++){
			PunManageMenuVO temp  = resources.get(i);
			if(temp.getParentMenuId() == null){
				temp.setParentMenuId(new Long(0));
			}
			resourceNodes.put(temp.getMenuId().toString(), temp);
		}
	}
	
	/**
	 * 根据资源结点结合，生成多棵资源树，并返回树集合；
	 * @param resources
	 * @return 资源树数组集合；
	 */
	public List<PunManageMenuVO[]> generateTreeView(List<PunManageMenuVO> resources){
		initialize(resources);
		List<PunManageMenuVO[]> forest = new ArrayList<PunManageMenuVO[]>();
		List<PunManageMenuVO> roots = getRoots();
		for(int i = 0; i < roots.size();i++){
			PunManageMenuVO root = roots.get(i);
			String keysString = generateNodeIdString(root);
			PunManageMenuVO[] tree = getPunResourceArrayByKeys(keysString);
			if(tree != null){
				forest.add(tree);
			}
		}		
		return forest;
	}
	
	/**
	 * 根据某一资源结点，找到其所在那棵树的遍历结点Id列表，例如1;2;4;3等；
	 * @param resource
	 * @return 返回结点ID列表，用”;"分隔并以“;"结尾;
	 */
	private String generateNodeIdString(PunManageMenuVO resource){
		PunManageMenuVO parent = getParent(resource);
        List<PunManageMenuVO> children = getChildren(resource);
        if (parent != null && !processedNodes.containsKey(parent.getMenuId().toString()))
        {
            return generateNodeIdString(parent);
        }
        if (children.size() == 0)
        {
            processedNodes.put(resource.getMenuId().toString(), resource.getMenuId().toString());
            return resource.getMenuId().toString() + ";";
        }
        StringBuilder sb = new StringBuilder();
        if (children.size() > 0)
        {
            processedNodes.put(resource.getMenuId().toString(), resource.getMenuId().toString());
            sb.append(resource.getMenuId().toString() + ";");
            for (int i = 0; i < children.size(); i++)
            {
            	PunManageMenuVO temp = children.get(i);
                sb.append(generateNodeIdString(temp));
            } 
        }
        return sb.toString();
	}
	
	/**
	 * 在集合中查找所有的树根结点；
	 * @return 树根结点集合;
	 */
	private List<PunManageMenuVO> getRoots(){		
		List<PunManageMenuVO> roots = new ArrayList<PunManageMenuVO>();
		for(PunManageMenuVO source:resourceNodes.values()){
			if(source.getParentMenuId() == null || !resourceNodes.containsKey(source.getParentMenuId().toString())){
				roots.add(source);
			}
		}
		ComparatorPunResource comparator=new ComparatorPunResource();
		Collections.sort(roots, comparator);
        return roots;
	}
	
	/**
	 * 在集合中查某一结点的子节点并安装sequence排序;
	 * @param resource
	 * @return 有序的子节点集合;
	 */
	private List<PunManageMenuVO> getChildren(PunManageMenuVO resource){		
		List<PunManageMenuVO> children = new ArrayList<PunManageMenuVO>();
		for(PunManageMenuVO temp:resourceNodes.values()){
			if(temp.getParentMenuId() != null && temp.getParentMenuId().compareTo(resource.getMenuId()) == 0){
				children.add(temp);
			}
		}
		ComparatorPunResource comparator=new ComparatorPunResource();
		Collections.sort(children, comparator);
        return children;
	}
	
	/**
	 * 在集合中查找某一结点的父节点
	 * @param resource
	 * @return 父节点
	 */
	private PunManageMenuVO getParent(PunManageMenuVO resource){
		if(resource.getParentMenuId() == null)
			return null;
		if(resourceNodes.containsKey(resource.getParentMenuId().toString())){
			return (PunManageMenuVO) resourceNodes.get(resource.getParentMenuId().toString()); 
		}
        return null;
	}
	
	/**
	 * 实现PunResource排序的类；
	 * @author wwt
	 *
	 */
	public class ComparatorPunResource implements Comparator<PunManageMenuVO>{
		/**
		 * 比较两个PunResource的大小;
		 */
		 public int compare(PunManageMenuVO a, PunManageMenuVO b) {
			 if(a.getMenuSeq() == null)
				 return -1;
			 if(b.getMenuSeq() == null)
				 return 1;
		  return a.getMenuSeq().compareTo(b.getMenuSeq());		 
		}
	}
	
	/**
	 * 根据ID列表提取有序的资源结点数组;
	 * @param keysString
	 * @return 有序的资源结点数组;
	 */
	private PunManageMenuVO[] getPunResourceArrayByKeys(String keysString){
		if(keysString == null)
			return null;
		if(keysString.endsWith(";")){
			keysString = keysString.substring(0, keysString.length()-1);
		}
		if(keysString.length() == 0)
			return null;
		String[] keys = keysString.split(";");
		PunManageMenuVO[] rtnAry = new PunManageMenuVO[keys.length];
		for(int i = 0 ; i < keys.length; i++){
			rtnAry[i] = (PunManageMenuVO) resourceNodes.get(keys[i]);
		}
		return rtnAry;
	}
	
	public static List<PunResourceTreeNode> getPlainZNodes(PunManageMenuVO[] resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunManageMenuVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
			zNode.setId(eachRes.getMenuId());
			//zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
			zNode.setpId(eachRes.getParentMenuId());
			zNode.setName(eachRes.getMenuName());
			zNode.setChecked(eachRes.isChecked());
			if (eachRes.getMenuAddress() == null || 
					eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
				//如果address是为null，就把url设为空地址
				zNode.setUrl("");
			} else {
				zNode.setUrl(eachRes.getMenuAddress());
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
	 * @param menuList
	 */
	public static List<PunResourceTreeNode> getPlainAccessZNodes(PunManageMenuVO[] resource) {
		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
		for (PunManageMenuVO eachRes : resource) {
			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
			zNode.setId(eachRes.getMenuId());
			//zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
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
	
	public static String getLastPId(String PId) {
		return PId.split(",")[PId.split(",").length-1];
	}
	
	/*public static List<PunManageMenuVO> getManageMenuVOListFromDev(List<PunDevMenuVO> devManageMenuVOList) {
		List<PunManageMenuVO> menuVOList = new ArrayList<PunManageMenuVO>();
		for(PunDevMenuVO devManageMenuVO : devManageMenuVOList) {
			PunManageMenuVO menuVO = new PunManageMenuVO();
			menuVO.setChecked(devManageMenuVO.isChecked());
			menuVO.setMenuAddress(devManageMenuVO.getMenuAddress());
			menuVO.setMenuId(devManageMenuVO.getMenuId());;
			menuVO.setMenuName(devManageMenuVO.getMenuName());
			menuVO.setMenuSeq(devManageMenuVO.getMenuSeq());
			menuVO.setMenuType(devManageMenuVO.getMenuType());
			menuVO.setOperateType(devManageMenuVO.getOperateType());
			menuVO.setParentMenuId(devManageMenuVO.getParentMenuId());
			menuVO.setPid(devManageMenuVO.getPid());
			menuVO.setSysId(devManageMenuVO.getSysId());
			
			menuVOList.add(menuVO);
		}
		return menuVOList;
	}*/
	
	public static List<PunManageMenuVO> getManageMenuVOListFromManage(List<PunManageMenuVO> devManageMenuVOList) {
		List<PunManageMenuVO> menuVOList = new ArrayList<PunManageMenuVO>();
		for(PunManageMenuVO devManageMenuVO : devManageMenuVOList) {
			PunManageMenuVO menuVO = new PunManageMenuVO();
			menuVO.setChecked(devManageMenuVO.isChecked());
			menuVO.setMenuAddress(devManageMenuVO.getMenuAddress());
			menuVO.setMenuId(devManageMenuVO.getMenuId());;
			menuVO.setMenuName(devManageMenuVO.getMenuName());
			menuVO.setMenuSeq(devManageMenuVO.getMenuSeq());
			menuVO.setMenuType(devManageMenuVO.getMenuType());
			menuVO.setOperateType(devManageMenuVO.getOperateType());
			menuVO.setParentMenuId(devManageMenuVO.getParentMenuId());
			menuVO.setPid(devManageMenuVO.getPid());
			menuVO.setSysId(devManageMenuVO.getSysId());
			
			menuVOList.add(menuVO);
		}
		return menuVOList;
	}
}
