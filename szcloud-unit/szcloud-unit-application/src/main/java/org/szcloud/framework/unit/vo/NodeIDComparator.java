package org.szcloud.framework.unit.vo;

import java.util.Comparator;

public class NodeIDComparator implements Comparator<Object>{
	
	public int compare(Object o1, Object o2) {
		// 按照节点编号比较
		Long id1 = ((PunResourceTreeNode)o1).getId();
		Long id2 = ((PunResourceTreeNode)o2).getId();
		return (id1 < id2 ? -1 : (id1 == id2 ? 0 : 1));  
	}

}
