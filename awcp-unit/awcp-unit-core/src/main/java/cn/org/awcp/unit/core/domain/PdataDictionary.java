package cn.org.awcp.unit.core.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;

/**
 * 数据字典实体类
 * @author Administrator
 *
 */
public class PdataDictionary extends BaseEntity{
	
	private static final long serialVersionUID = 6442609976185597249L;
	private Long id;
	private String code;
	private String dataKey;
	private String dataValue;
	private Integer dataOrder;
	private String dictRemark;
	private String dictStatus;
	private Long level;

	public PdataDictionary(){
		
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setDataKey(String value) {
		this.dataKey = value;
	}
	
	public String getDataKey() {
		return this.dataKey;
	}
	
	public void setDataValue(String value) {
		this.dataValue = value;
	}
	
	public String getDataValue() {
		return this.dataValue;
	}
	
	public void setDataOrder(Integer value) {
		this.dataOrder = value;
	}
	
	public Integer getDataOrder() {
		return this.dataOrder;
	}
	
	public void setDictRemark(String value) {
		this.dictRemark = value;
	}
	
	public String getDictRemark() {
		return this.dictRemark;
	}
	
	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getDictStatus() {
		return dictStatus;
	}

	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Code",getCode())
			.append("DataKey",getDataKey())
			.append("DataValue",getDataValue())
			.append("DataOrder",getDataOrder())
			.append("DictRemark",getDictRemark())
			.append("Level",getDictRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PdataDictionary == false) return false;
		if(this == obj) return true;
		PdataDictionary other = (PdataDictionary)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	public void save() {
		PdataDictionary.getRepository().save(this);
	}
	
	public void delete() throws MRTException{
		PdataDictionary.getRepository().remove(this);
	}
	
	public static List<PdataDictionary> findAll() throws MRTException{
		return PdataDictionary.getRepository().findAll(PdataDictionary.class);
	}

}

