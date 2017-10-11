package cn.org.awcp.unit.vo;

import java.util.Arrays;

public class PunGroupUserVO {
	private Long groupId;
	private Long[] userIds;
	
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	@Override
	public String toString() {
		return "PunGroupUserVO [groupId=" + groupId + ", userIds=" + Arrays.toString(userIds) + "]";
	}
	
}
