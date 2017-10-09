package org.szcloud.framework.unit.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应前台zTree的多叉树节点
 * 
 */
public class PunResourceManyTreeNode {
	
	//树节点
	private PunResourceTreeNode data;
	
	//子树集合
	private List<PunResourceManyTreeNode> childList;
	
	/**
	 * 构造函数
	 * @param treeNode
	 */
	public PunResourceManyTreeNode(PunResourceTreeNode treeNode) {
		this.data = treeNode;
		this.childList = new ArrayList<PunResourceManyTreeNode>();
	}
	
	public PunResourceTreeNode getData() {
		return data;
	}

	public void setData(PunResourceTreeNode data) {
		this.data = data;
	}

	public List<PunResourceManyTreeNode> getChildList() {
		return childList;
	}

	public void setChildList(List<PunResourceManyTreeNode> childList) {
		this.childList = childList;
	}

}
