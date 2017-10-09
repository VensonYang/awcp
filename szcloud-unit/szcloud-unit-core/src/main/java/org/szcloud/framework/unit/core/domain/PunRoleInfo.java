package org.szcloud.framework.unit.core.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseEntity;

/**
 * 角色实体类
 * @author Administrator
 *
 */
public class PunRoleInfo extends BaseEntity{
	private static final long serialVersionUID = -3446349440907017146L;
	private Long roleId;
	private Long sysId;
	private String roleName;
	private String dictRemark;
	private PunSystem punSystem;
	private Set<PunRoleAccess> punRoleAccesss = new HashSet<PunRoleAccess>();
	
	public PunRoleInfo(){
		
	}

	public PunRoleInfo(Long roleId){
		this.roleId = roleId;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	
	public void setSysId(Long value) {
		this.sysId = value;
	}
	
	public Long getSysId() {
		return this.sysId;
	}
	
	public void setRoleName(String value) {
		this.roleName = value;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public void setDictRemark(String value) {
		this.dictRemark = value;
	}
	
	public String getDictRemark() {
		return this.dictRemark;
	}
	
	public void setPunRoleAccesss(Set<PunRoleAccess> punRoleAccess){
		this.punRoleAccesss = punRoleAccess;
	}
	
	public Set<PunRoleAccess> getPunRoleAccesss() {
		return punRoleAccesss;
	}
	
	public void setPunSystem(PunSystem punSystem){
		this.punSystem = punSystem;
	}
	
	public PunSystem getPunSystem() {
		return punSystem;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("RoleId",getRoleId())
			.append("SysId",getSysId())
			.append("RoleName",getRoleName())
			.append("DictRemark",getDictRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRoleId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunRoleInfo == false) return false;
		if(this == obj) return true;
		PunRoleInfo other = (PunRoleInfo)obj;
		return new EqualsBuilder()
			.append(getRoleId(),other.getRoleId())
			.isEquals();
	}
	
	public void save() throws MRTException{
		PunRoleInfo.getRepository().save(this);
	}
	
	public void delete() throws MRTException{
		PunRoleInfo.getRepository().remove(this);
	}
	
	public static List<PunRoleInfo> findAll() throws MRTException{
		return PunRoleInfo.getRepository().findAll(PunRoleInfo.class);
	}
}

