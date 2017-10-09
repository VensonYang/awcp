package org.szcloud.framework.formdesigner.application.vo;

import java.util.List;

public class WorkflowVO {
	
	private Long pageId;
	
	private List<WorkflowNodeVO> nodes;//结点
	
	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	
	public List<WorkflowNodeVO> getNodes() {
		return nodes;
	}

	public void setNodes(List<WorkflowNodeVO> nodes) {
		this.nodes = nodes;
	}
	
}
