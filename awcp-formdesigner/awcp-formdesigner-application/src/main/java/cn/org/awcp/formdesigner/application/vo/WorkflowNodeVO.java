package cn.org.awcp.formdesigner.application.vo;

import java.util.List;

public class WorkflowNodeVO {
	
	private String id;//结点ID
	
	private String name;//结点名称
	
	private int priority;//优先级(顺序)
	
	private String workflowId;//流程ID
	
	private String workflowName;//流程名
	
	private List<WorkflowVariableVO> variables;//节点参数
	
	private String cyclostyleID;//公文模版ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public List<WorkflowVariableVO> getVariables() {
		return variables;
	}

	public void setVariables(List<WorkflowVariableVO> variables) {
		this.variables = variables;
	}

	public String getCyclostyleID() {
		return cyclostyleID;
	}

	public void setCyclostyleID(String cyclostyleID) {
		this.cyclostyleID = cyclostyleID;
	}
	
}
