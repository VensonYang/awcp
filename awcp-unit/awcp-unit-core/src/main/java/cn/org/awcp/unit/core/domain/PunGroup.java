package cn.org.awcp.unit.core.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;

/**
 * 组实体类
 * @author Administrator
 *
 */
public class PunGroup extends BaseEntity{

	private static final long serialVersionUID = 9200889246300170404L;
	private Long groupId;
	private Long parentGroupId;
	private String groupType;
	private String groupChName;
	private String groupShortName;
	private String groupEnName;
	private String orgCode;
	private String groupAddress;
	private String zipCode;
	private String contactNumber;
	private String fax;
	private String groupBusinessSphere;
	private java.util.Date createDate;
	private String pid;
	private String number;	
	private Set<PunGroup> punGroups = new HashSet<PunGroup>();
	private Set<PunGroupSys> punGroupSyss = new HashSet<PunGroupSys>();
	private List<PunSystem> systems;	
	private PunGroup punGroup;
	private Long id;

	public PunGroup(){
		
	}

	public PunGroup(Long groupId){
		this.groupId = groupId;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	
	public void setParentGroupId(Long value) {
		this.parentGroupId = value;
	}
	
	public Long getParentGroupId() {
		return this.parentGroupId;
	}
	
	public void setGroupType(String value) {
		this.groupType = value;
	}
	
	public String getGroupType() {
		return this.groupType;
	}
	
	public void setGroupChName(String value) {
		this.groupChName = value;
	}
	
	public String getGroupChName() {
		return this.groupChName;
	}
	
	public void setGroupShortName(String value) {
		this.groupShortName = value;
	}
	
	public String getGroupShortName() {
		return this.groupShortName;
	}
	
	public void setGroupEnName(String value) {
		this.groupEnName = value;
	}
	
	public String getGroupEnName() {
		return this.groupEnName;
	}
	
	public void setOrgCode(String value) {
		this.orgCode = value;
	}
	
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setGroupAddress(String value) {
		this.groupAddress = value;
	}
	
	public String getGroupAddress() {
		return this.groupAddress;
	}
	
	public void setZipCode(String value) {
		this.zipCode = value;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public void setContactNumber(String value) {
		this.contactNumber = value;
	}
	
	public String getContactNumber() {
		return this.contactNumber;
	}
	
	public void setFax(String value) {
		this.fax = value;
	}
	
	public String getFax() {
		return this.fax;
	}
	
	public void setGroupBusinessSphere(String value) {
		this.groupBusinessSphere = value;
	}
	
	public String getGroupBusinessSphere() {
		return this.groupBusinessSphere;
	}

	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setPunGroups(Set<PunGroup> punGroup){
		this.punGroups = punGroup;
	}
	
	public Set<PunGroup> getPunGroups() {
		return punGroups;
	}
	
	public void setPunGroupSyss(Set<PunGroupSys> punGroupSys){
		this.punGroupSyss = punGroupSys;
	}
	
	public Set<PunGroupSys> getPunGroupSyss() {
		return punGroupSyss;
	}
	
	public void setPunGroup(PunGroup punGroup){
		this.punGroup = punGroup;
	}
	
	public PunGroup getPunGroup() {
		return punGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = groupId;
	}

	public List<PunSystem> getSystems() {
		return systems;
	}

	public void setSystems(List<PunSystem> systems) {
		this.systems = systems;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("GroupId",getGroupId())
			.append("ParentGroupId",getParentGroupId())
			.append("GroupType",getGroupType())
			.append("GroupChName",getGroupChName())
			.append("GroupShortName",getGroupShortName())
			.append("GroupEnName",getGroupEnName())
			.append("OrgCode",getOrgCode())
			.append("GroupAddress",getGroupAddress())
			.append("ZipCode",getZipCode())
			.append("ContactNumber",getContactNumber())
			.append("Fax",getFax())
			.append("GroupBusinessSphere",getGroupBusinessSphere())
			.append("CreateDate",getCreateDate())
			.append("Pid",getPid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getGroupId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunGroup == false) return false;
		if(this == obj) return true;
		PunGroup other = (PunGroup)obj;
		return new EqualsBuilder().append(getGroupId(),other.getGroupId()).isEquals();
	}
	
	public void save(){
		PunGroup.getRepository().save(this);
	}
	
	public void delete() throws MRTException{
		PunGroup.getRepository().remove(this);
	}
	
	public static List<PunGroup> findAll() throws MRTException{
		return PunGroup.getRepository().findAll(PunGroup.class);
	}
}

