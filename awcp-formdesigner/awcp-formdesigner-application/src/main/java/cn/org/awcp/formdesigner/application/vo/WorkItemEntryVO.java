package cn.org.awcp.formdesigner.application.vo;

import java.io.Serializable;

public class WorkItemEntryVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 步骤ID */
	private int orginalID;
	/** 流程状态 */
	private String workFlowStatus;
	/** 处理步骤 */
	private String activityName;
	/** 处理人 */
	private String participant;
	/** 接收时间*/
	private String inceptDate;
	/** 处理时间 */
	private String handleDate;
	/** 状态 */
	private String handleStatus;
	/** 处理步意见*/
	private String handleView;
	/** 发送步骤 */
	private String sendStep;
	/** 发送人 */
	private String sender;
	
	public int getOrginalID() {
		return orginalID;
	}
	
	public void setOrginalID(int orginalID) {
		this.orginalID = orginalID;
	}
	
	public String getWorkFlowStatus() {
		return workFlowStatus;
	}
	
	public void setWorkFlowStatus(String workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}
	
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getParticipant() {
		return participant;
	}
	
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	
	public String getInceptDate() {
		return inceptDate;
	}
	
	public void setInceptDate(String inceptDate) {
		this.inceptDate = inceptDate;
	}
	
	public String getHandleDate() {
		return handleDate;
	}
	
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	
	public String getHandleStatus() {
		return handleStatus;
	}
	
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public String getHandleView() {
		return handleView;
	}
	
	public void setHandleView(String handleView) {
		this.handleView = handleView;
	}
	
	public String getSendStep() {
		return sendStep;
	}
	
	public void setSendStep(String sendStep) {
		this.sendStep = sendStep;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
}
