package org.szcloud.framework.unit.vo;

import java.util.List;

public class PunResourceTreeVO {
	
	private String name;
	private List<PunResourceTreeNode> punResourceTreeNode;
	
	public List<PunResourceTreeNode> getPunResourceTreeNode() {
		return punResourceTreeNode;
	}
	
	public void setPunResourceTreeNode(List<PunResourceTreeNode> punResourceTreeNode) {
		this.punResourceTreeNode = punResourceTreeNode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
