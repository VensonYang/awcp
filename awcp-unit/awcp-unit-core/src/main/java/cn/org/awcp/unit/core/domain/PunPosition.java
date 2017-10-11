package cn.org.awcp.unit.core.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.domain.BaseEntity;

/**
 * 职位实体类
 * @author Administrator
 *
 */
public class PunPosition extends BaseEntity{

	private static final long serialVersionUID = -951060333032755905L;
	private Long positionId;
	private String name;
	private String shortName;
	private int grade;
	private Long groupId;

	public PunPosition(){
		
	}

	public PunPosition(Long positionId){
		this.positionId = positionId;
	}

	public void setPositionId(Long value) {
		this.positionId = value;
	}
	
	public Long getPositionId() {
		return this.positionId;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setShortName(String value) {
		this.shortName = value;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("PositionId",getPositionId())
			.append("Name",getName())
			.append("ShortName",getShortName())
			.append("GroupId",getGroupId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPositionId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunPosition == false) return false;
		if(this == obj) return true;
		PunPosition other = (PunPosition)obj;
		return new EqualsBuilder()
			.append(getPositionId(),other.getPositionId())
			.isEquals();
	}
}

