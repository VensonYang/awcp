package cn.org.awcp.unit.vo;

public class PunResourceTreeNode {
	/**
	 * 对应前台zTree的树节点
	 * 
	 */
	private Long id;
	private Long pId;
	private String name;
	private String url;
	private String target;
	private boolean open;
	private boolean isParent;
	private boolean checked;
	private String groupType;
	private String iconSkin;
	private String click;
	private String number;

	/**
	 * 构造函数
	 * 
	 * @param nodeId
	 *            节点ID
	 */
	public PunResourceTreeNode(Long nodeId) {
		this.id = nodeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pid) {
		this.pId = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public boolean getisParent() {
		return isParent;
	}

	public void setisParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PunResourceTreeNode [id=" + id + ", pId=" + pId + ", name=" + name + ", url=" + url + ", target="
				+ target + ", open=" + open + ", isParent=" + isParent + ", checked=" + checked + ", groupType="
				+ groupType + ", iconSkin=" + iconSkin + ", click=" + click + ", number=" + number + "]";
	}

}
