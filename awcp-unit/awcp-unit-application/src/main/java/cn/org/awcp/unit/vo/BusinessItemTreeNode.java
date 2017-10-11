package cn.org.awcp.unit.vo;

public class BusinessItemTreeNode {
	/**
	 * 对应前台zTree的树节点
	 * 
	 */
	private String id;
	private String parentId;
	private String pId;
	private String name;
	private Integer sequence;
	private Integer sysId;
	private Long dynamicPageId;
	private String status;
	private Integer level;
	private String comment;
	private boolean checked;
	private String target;
	private boolean open;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public Integer getSysId() {
		return sysId;
	}
	
	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}
	
	public Long getDynamicPageId() {
		return dynamicPageId;
	}
	
	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public String getpId() {
		return pId;
	}
	
	public void setpId(String pId) {
		this.pId = pId;
	}

	@Override
	public String toString() {
		return "BusinessItemTreeNode [id=" + id + ", parentId=" + parentId + ", pId=" + pId + ", name=" + name
				+ ", sequence=" + sequence + ", sysId=" + sysId + ", dynamicPageId=" + dynamicPageId + ", status="
				+ status + ", level=" + level + ", comment=" + comment + ", checked=" + checked + ", target=" + target
				+ ", open=" + open + "]";
	}
	
}
