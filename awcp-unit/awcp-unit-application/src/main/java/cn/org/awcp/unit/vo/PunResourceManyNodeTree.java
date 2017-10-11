package cn.org.awcp.unit.vo;

import java.util.List;

public class PunResourceManyNodeTree {
	//树根
	private PunResourceManyTreeNode root;
	
	public PunResourceManyNodeTree() {
		this.root = new PunResourceManyTreeNode(new PunResourceTreeNode(0L)); 
	}
	
	/**
	 * 生成一颗树，根节点为root
	 * 
	 * @param plainZNodes
	 * @param root
	 * @return
	 */
	public PunResourceManyNodeTree createTree(List<PunResourceTreeNode> plainZNodes,PunResourceManyTreeNode root) {		
		if (plainZNodes == null || plainZNodes.size() == 0) 
			return null;		
		PunResourceManyNodeTree manyNodeTree = new PunResourceManyNodeTree();	
		//将所有节点添加到多叉树中  
		for (PunResourceTreeNode treeNode : plainZNodes) {
			if (treeNode.getpId() == null || treeNode.getpId() == 0L) {
				//向根添加一个节点  
				manyNodeTree.getRoot().getChildList().add(new PunResourceManyTreeNode(treeNode));
			}
			else {
				addChild(manyNodeTree.getRoot(), treeNode);
			}		
		}	
		return manyNodeTree;
	}
	
	/**
	 * 向多树节点添加子节点
	 * @param manyTreeNode
	 * @param child
	 */
	public void addChild(PunResourceManyTreeNode manyTreeNode,PunResourceTreeNode child) {
		for (PunResourceManyTreeNode item : manyTreeNode.getChildList()) {
			if (item.getData().getId() == child.getpId()) {
				//找到child对应的父节点
				item.getChildList().add(new PunResourceManyTreeNode(child));
				break;
			} else {
				if (item.getChildList() != null && item.getChildList().size() > 0) {
					addChild(item, child);
				}
			}
		}		
	}
	
	/**
	 * 遍历多叉树
	 * @param manyTreeNode
	 * @return
	 */
	public String traverseTree(PunResourceManyTreeNode manyTreeNode) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n");	
		if (manyTreeNode != null) {
			for (PunResourceManyTreeNode index : manyTreeNode.getChildList()) {
				buffer.append(index.getData().getId()+ ",");
				if (index.getChildList() != null && index.getChildList().size() > 0) {
					buffer.append(traverseTree(index));
				}
			}
		}		
		buffer.append("\n");		
		return buffer.toString();						
	}

	public PunResourceManyTreeNode getRoot() {
		return root;
	}

	public void setRoot(PunResourceManyTreeNode root) {
		this.root = root;
	}

}
