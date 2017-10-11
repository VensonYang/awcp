package cn.org.awcp.unit.utils;
//package org.szcloud.framework.unit.utils;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.szcloud.framework.unit.core.domain.PunResource;
//import org.szcloud.framework.unit.vo.PunMenuVO;
//import org.szcloud.framework.unit.vo.PunResourceManyTreeNode;
//import org.szcloud.framework.unit.vo.PunResourceTreeNode;
//import org.szcloud.framework.unit.vo.PunResourceVO;
//
//public class ResourceUtils {
//			
//	public static List<PunResourceTreeNode> getPlainZNodes(PunMenuVO[] resource) {
//		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
//		for (PunMenuVO eachRes : resource) {
//			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
//			zNode.setId(eachRes.getMenuId());
//			//zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
//			zNode.setpId(eachRes.getParentMenuId());
//			zNode.setName(eachRes.getMenuName());
//			zNode.setChecked(eachRes.isChecked());
//			if (eachRes.getMenuAddress() == null || 
//					eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
//				//如果address是为null，就把url设为空地址
//				zNode.setUrl("");
//			} else {
//				zNode.setUrl(eachRes.getMenuAddress());
//			}			
//			zNode.setTarget("main");
//			zNode.setOpen(false);
//			zNode.setisParent(false);
//			zNodes.add(zNode);
//		}
//		
//		return zNodes;
//	}
//	
//	/**
//	 * 格式化输出树结点，去掉rul,target,设置菜单为展开（即open=true;
//	 * @param menuList
//	 */
//	public static List<PunResourceTreeNode> getPlainAccessZNodes(PunMenuVO[] resource) {
//		List<PunResourceTreeNode> zNodes = new ArrayList<PunResourceTreeNode>();
//		for (PunMenuVO eachRes : resource) {
//			PunResourceTreeNode zNode = new PunResourceTreeNode(eachRes.getMenuId());
//			zNode.setId(eachRes.getMenuId());
//			//zNode.setPid(Long.parseLong(getLastPId(eachRes.getPid())));
//			zNode.setpId(eachRes.getParentMenuId());
//			zNode.setName(eachRes.getMenuName());
//			zNode.setChecked(eachRes.isChecked());
//			if (eachRes.getMenuAddress() == null || 
//					eachRes.getMenuAddress().equalsIgnoreCase("NULL")) {
//				//如果address是为null，就把url设为空地址
//				zNode.setUrl("");
//			} else {
//				zNode.setUrl(eachRes.getMenuAddress());
//			}			
//			zNode.setTarget("");
//			zNode.setOpen(true);
//			zNode.setisParent(false);
//			zNodes.add(zNode);
//		}
//		
//		return zNodes;
//	}
//	
//	public static String getLastPId(String PId) {
//		return PId.split(",")[PId.split(",").length-1];
//	}
//	
//	public static void main(String[] args) {
//		
//	}
//}
