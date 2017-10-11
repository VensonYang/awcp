package cn.org.awcp.unit.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;
import cn.org.awcp.core.domain.BaseExample;

/**
 * 系统实体类
 * @author Administrator
 *
 */
public class PunSystem extends BaseEntity{
	private static final long serialVersionUID = -140072828961558627L;
	private Long sysId;
	private String sysAddress;
	private String sysName;
	private String sysShortName;
	private String sysCreater;
	private String sysCreateGroup;
	private String sysStatus;
	private Date sysCreateTime;	
	private Long groupId;
	private Set<PunGroupSys> punGroupSyss = new HashSet<PunGroupSys>();
	private Set<PunResource> punResources = new HashSet<PunResource>();
	private Set<PunRoleInfo> punRoleInfos = new HashSet<PunRoleInfo>();
	
	public PunSystem(){
		
	}

	public PunSystem(Long sysId){
		this.sysId = sysId;
	}

	public void setSysId(Long value) {
		this.sysId = value;
	}
	
	public Long getSysId() {
		return this.sysId;
	}
	
	public void setSysAddress(String value) {
		this.sysAddress = value;
	}
	
	public String getSysAddress() {
		return this.sysAddress;
	}
	
	public void setSysName(String value) {
		this.sysName = value;
	}
	
	public String getSysName() {
		return this.sysName;
	}
	
	public void setSysShortName(String value) {
		this.sysShortName = value;
	}
	
	public String getSysShortName() {
		return this.sysShortName;
	}
	
	public void setSysCreater(String value) {
		this.sysCreater = value;
	}
	
	public String getSysCreater() {
		return this.sysCreater;
	}

	public void setSysCreateTime(Date value) {
		this.sysCreateTime = value;
	}
	
	public Date getSysCreateTime() {
		return this.sysCreateTime;
	}
	
	public void setPunGroupSyss(Set<PunGroupSys> punGroupSys){
		this.punGroupSyss = punGroupSys;
	}
	
	public Set<PunGroupSys> getPunGroupSyss() {
		return punGroupSyss;
	}
	
	public void setPunResources(Set<PunResource> punResource){
		this.punResources = punResource;
	}
	
	public Set<PunResource> getPunResources() {
		return punResources;
	}
	
	public void setPunRoleInfos(Set<PunRoleInfo> punRoleInfo){
		this.punRoleInfos = punRoleInfo;
	}
	
	public Set<PunRoleInfo> getPunRoleInfos() {
		return punRoleInfos;
	}

	public String getSysCreateGroup() {
		return sysCreateGroup;
	}

	public void setSysCreateGroup(String sysCreateGroup) {
		this.sysCreateGroup = sysCreateGroup;
	}
	
	public String getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("SysId",getSysId())
			.append("SysAddress",getSysAddress())
			.append("SysName",getSysName())
			.append("SysShortName",getSysShortName())
			.append("SysCreater",getSysCreater())
			.append("SysCreateTime",getSysCreateTime())
			.append("groupId",getGroupId())
			.append("sysCreateGroup",getSysCreateGroup())
			.append("sysStatus",getSysStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSysId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunSystem == false) return false;
		if(this == obj) return true;
		PunSystem other = (PunSystem)obj;
		return new EqualsBuilder()
			.append(getSysId(),other.getSysId())
			.isEquals();
	}
	
	public static List<PunSystem> findAll() throws MRTException{
		return PunSystem.getRepository().findAll(PunSystem.class);
	}
	
	public void save() throws MRTException{
		PunSystem.getRepository().save(this);
	}
	
	public void delete() throws MRTException{
		PunSystem.getRepository().remove(this);
	}

	public List<PunSystem> findByIdCard(BaseExample example) throws MRTException{
		return PunSystem.getRepository().selectByExample(PunSystem.class, example);
	}

}

