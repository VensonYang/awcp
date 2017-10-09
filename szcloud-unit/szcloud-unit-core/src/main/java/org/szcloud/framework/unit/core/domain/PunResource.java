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
 * 资源实体类
 * @author Administrator
 *
 */
public class PunResource extends BaseEntity{
	private static final long serialVersionUID = 5676772407916526067L;
	private Long resourceId;
	private Long sysId;
	private String resouType;
	private String relateResoId;
	private String whichEnd;
	private String resourceName;	
	private Long id;
	private Set<PunResource> punResources = new HashSet<PunResource>();
	private PunSystem punSystem;
	private PunResource punResource;
	
	public PunResource(){
		
	}

	public PunResource(Long resourceId){
		this.resourceId = resourceId;
	}
	
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public void setSysId(Long value) {
		this.sysId = value;
	}
	
	public Long getSysId() {
		return this.sysId;
	}
	
	public void setResouType(String value) {
		this.resouType = value;
	}
	
	public String getResouType() {
		return this.resouType;
	}
	
	public String getRelateResoId() {
		return relateResoId;
	}

	public void setRelateResoId(String relateResoId) {
		this.relateResoId = relateResoId;
	}
	
	public void setPunResources(Set<PunResource> punResource){
		this.punResources = punResource;
	}
	
	public Set<PunResource> getPunResources() {
		return punResources;
	}
	
	public void setPunSystem(PunSystem punSystem){
		this.punSystem = punSystem;
	}
	
	public PunSystem getPunSystem() {
		return punSystem;
	}
	
	public void setPunResource(PunResource punResource){
		this.punResource = punResource;
	}
	
	public PunResource getPunResource() {
		return punResource;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWhichEnd() {
		return whichEnd;
	}

	public void setWhichEnd(String whichEnd) {
		this.whichEnd = whichEnd;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("resourceId",getResourceId())
			.append("SysId",getSysId())
			.append("RelateResoId",getRelateResoId())
			.append("ResouType",getResouType())
			.append("WhichEnd",getWhichEnd())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getResourceId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunResource == false) return false;
		if(this == obj) return true;
		PunResource other = (PunResource)obj;
		return new EqualsBuilder()
			.append(getResourceId(),other.getResourceId())
			.isEquals();
	}
	
	public void save(){
		PunResource.getRepository().save(this);
	}
	
	public void remove() throws MRTException{
		PunResource.getRepository().remove(this);
	}
	
	public static List<PunResource> findAll() throws MRTException{
		return PunResource.getRepository().findAll(PunResource.class);
	}

}

