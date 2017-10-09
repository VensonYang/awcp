package org.szcloud.framework.unit.core.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseEntity;

/**
 * 角色访问
 * @author Administrator
 *
 */
public class PunRoleAccess extends BaseEntity{
	
	private static final long serialVersionUID = 5847966723236793625L;
	private Long roleAccId;
	private Long roleId;
	private Long resourceId;
	private Long operType;
	private PunRoleInfo punRoleInfo;
	
	public PunRoleAccess(){
		
	}

	public PunRoleAccess(Long roleAccId,Long roleId,Long resourceId,Long operType){
		this.roleAccId = roleAccId;
		this.roleId = roleId;
		this.resourceId = resourceId;
		this.operType = operType;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	
	public void setResourceId(Long value) {
		this.resourceId = value;
	}
	
	public Long getResourceId() {
		return this.resourceId;
	}
	
	public Long getRoleAccId() {
		return roleAccId;
	}

	public void setRoleAccId(Long roleAccId) {
		this.roleAccId = roleAccId;
	}

	public Long getOperType() {
		return operType;
	}

	public void setOperType(Long operType) {
		this.operType = operType;
	}
	
	public void setPunRoleInfo(PunRoleInfo punRoleInfo){
		this.punRoleInfo = punRoleInfo;
	}
	
	public PunRoleInfo getPunRoleInfo() {
		return punRoleInfo;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("RoleId",getRoleId())
			.append("ResourceId",getResourceId())
			.append("OperType", getOperType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRoleId())
			.append(getResourceId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunRoleAccess == false) return false;
		if(this == obj) return true;
		PunRoleAccess other = (PunRoleAccess)obj;
		return new EqualsBuilder()
			.append(getRoleId(),other.getRoleId())
			.append(getResourceId(),other.getResourceId())
			.isEquals();
	}
	
	public void save(){
		PunRoleAccess.getRepository().save(this);
	}
	
	public void remove() throws MRTException{
		PunRoleAccess.getRepository().remove(this);
	}
	
	public static List<PunRoleAccess> findAll() throws MRTException{
		return PunRoleAccess.getRepository().findAll(PunRoleAccess.class);
	}
	
}

