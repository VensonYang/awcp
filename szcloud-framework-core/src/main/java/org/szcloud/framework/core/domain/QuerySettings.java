package org.szcloud.framework.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class QuerySettings<T>{
	private Class<T> entityClass;
	private String rootAlias;
	private int firstResult;
	private int maxResults;
	private Map<String, String> aliases = new LinkedHashMap<String, String>();
	private Set<QueryCriterion> criterions = new HashSet<QueryCriterion>();
	private List<OrderSetting> orderSettings = new ArrayList<OrderSetting>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Entity> QuerySettings<T> create(Class<T> entityClass) {
		return new QuerySettings(entityClass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Entity> QuerySettings<T> create(Class<T> entityClass, String alias) {
		return new QuerySettings(entityClass, alias);
	}

	private QuerySettings(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	public QuerySettings(Class<T> entityClass, String alias) {
		this.entityClass = entityClass;
		this.rootAlias = alias;
	}

	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	public String getRootAlias() {
		return this.rootAlias;
	}

	public Map<String, String> getAliases() {
		return this.aliases;
	}

	public Set<QueryCriterion> getCriterions() {
		return this.criterions;
	}

	public int getFirstResult() {
		return this.firstResult;
	}

	public int getMaxResults() {
		return this.maxResults;
	}

	public List<OrderSetting> getOrderSettings() {
		return this.orderSettings;
	}

	public QuerySettings<T> alias(String propName, String aliasName) {
		this.aliases.put(propName, aliasName);
		return this;
	}

	public QuerySettings<T> eq(String propName, Object value) {
		addCriterion(Criterions.eq(propName, value));
		return this;
	}

	public QuerySettings<T> notEq(String propName, Object value) {
		addCriterion(Criterions.notEq(propName, value));
		return this;
	}

	public QuerySettings<T> ge(String propName, Comparable<?> value) {
		addCriterion(Criterions.ge(propName, value));
		return this;
	}

	public QuerySettings<T> gt(String propName, Comparable<?> value) {
		addCriterion(Criterions.gt(propName, value));
		return this;
	}

	public QuerySettings<T> le(String propName, Comparable<?> value) {
		addCriterion(Criterions.le(propName, value));
		return this;
	}

	public QuerySettings<T> lt(String propName, Comparable<?> value) {
		addCriterion(Criterions.lt(propName, value));
		return this;
	}

	public QuerySettings<T> eqProp(String propName, String otherProp) {
		addCriterion(Criterions.eqProp(propName, otherProp));
		return this;
	}

	public QuerySettings<T> notEqProp(String propName, String otherProp) {
		addCriterion(Criterions.notEqProp(propName, otherProp));
		return this;
	}

	public QuerySettings<T> gtProp(String propName, String otherProp) {
		addCriterion(Criterions.gtProp(propName, otherProp));
		return this;
	}

	public QuerySettings<T> geProp(String propName, String otherProp) {
		addCriterion(Criterions.geProp(propName, otherProp));
		return this;
	}

	public QuerySettings<T> ltProp(String propName, String otherProp) {
		addCriterion(Criterions.ltProp(propName, otherProp));
		return this;
	}

	public QuerySettings<T> leProp(String propName, String otherProp) {
		addCriterion(Criterions.leProp(propName, otherProp));
		return this;
	}

	public QuerySettings<T> sizeEq(String propName, int size) {
		addCriterion(Criterions.sizeEq(propName, size));
		return this;
	}

	public QuerySettings<T> sizeNotEq(String propName, int size) {
		addCriterion(Criterions.sizeNotEq(propName, size));
		return this;
	}

	public QuerySettings<T> sizeGt(String propName, int size) {
		addCriterion(Criterions.sizeGt(propName, size));
		return this;
	}

	public QuerySettings<T> sizeGe(String propName, int size) {
		addCriterion(Criterions.sizeGe(propName, size));
		return this;
	}

	public QuerySettings<T> sizeLt(String propName, int size) {
		addCriterion(Criterions.sizeLt(propName, size));
		return this;
	}

	public QuerySettings<T> sizeLe(String propName, int size) {
		addCriterion(Criterions.sizeLe(propName, size));
		return this;
	}

	public QuerySettings<T> containsText(String propName, String value) {
		addCriterion(Criterions.containsText(propName, value));
		return this;
	}

	public QuerySettings<T> startsWithText(String propName, String value) {
		addCriterion(Criterions.startsWithText(propName, value));
		return this;
	}

	public QuerySettings<T> in(String propName, Collection<? extends Object> value) {
		addCriterion(Criterions.in(propName, value));
		return this;
	}

	public QuerySettings<T> in(String propName, Object[] value) {
		addCriterion(Criterions.in(propName, value));
		return this;
	}

	public QuerySettings<T> notIn(String propName, Collection<? extends Object> value) {
		addCriterion(Criterions.notIn(propName, value));
		return this;
	}

	public QuerySettings<T> notIn(String propName, Object[] value) {
		addCriterion(Criterions.notIn(propName, value));
		return this;
	}

	public <E> QuerySettings<T> between(String propName, Comparable<E> from, Comparable<E> to) {
		addCriterion(Criterions.between(propName, from, to));
		return this;
	}

	public QuerySettings<T> isNull(String propName) {
		addCriterion(Criterions.isNull(propName));
		return this;
	}

	public QuerySettings<T> notNull(String propName) {
		addCriterion(Criterions.notNull(propName));
		return this;
	}

	public QuerySettings<T> isEmpty(String propName) {
		addCriterion(Criterions.isEmpty(propName));
		return this;
	}

	public QuerySettings<T> notEmpty(String propName) {
		addCriterion(Criterions.notEmpty(propName));
		return this;
	}

	public QuerySettings<T> not(QueryCriterion criterion) {
		addCriterion(Criterions.not(criterion));
		return this;
	}

	public QuerySettings<T> and(QueryCriterion[] criterions) {
		addCriterion(Criterions.and(criterions));
		return this;
	}

	public QuerySettings<T> or(QueryCriterion[] criterions) {
		addCriterion(Criterions.or(criterions));
		return this;
	}

	private void addCriterion(QueryCriterion criterion) {
		this.criterions.add(criterion);
	}

	public QuerySettings<T> setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public QuerySettings<T> setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	public QuerySettings<T> asc(String propName) {
		this.orderSettings.add(OrderSetting.asc(propName));
		return this;
	}

	public QuerySettings<T> desc(String propName) {
		this.orderSettings.add(OrderSetting.desc(propName));
		return this;
	}

	@SuppressWarnings("rawtypes")
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof QuerySettings))
			return false;
		QuerySettings castOther = (QuerySettings)other;
		return new EqualsBuilder().append(this.entityClass, castOther.entityClass).append(this.criterions, castOther.criterions).append(this.firstResult, castOther.firstResult).append(this.maxResults, castOther.maxResults).append(this.orderSettings, castOther.orderSettings).isEquals();
	}

	public int hashCode(){
		return new HashCodeBuilder(17, 37).append(this.entityClass).append(this.criterions).append(this.firstResult).append(this.maxResults).append(this.orderSettings).toHashCode();
	}

	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append("Class:").append(this.entityClass.getSimpleName()).append(SystemUtils.LINE_SEPARATOR);
		result.append("criterions: [");
		for (QueryCriterion criteron : this.criterions) {
			result.append(criteron);
		}
		result.append("]").append(SystemUtils.LINE_SEPARATOR);
		result.append(new StringBuilder().append("firstResult:").append(this.firstResult).toString()).append(SystemUtils.LINE_SEPARATOR);
		result.append(new StringBuilder().append("maxResults").append(this.maxResults).toString()).append(SystemUtils.LINE_SEPARATOR);
		result.append("orderSettings: [");
		for (OrderSetting orderSetting : this.orderSettings) {
			result.append(orderSetting);
		}
		result.append("]");
		return result.toString();
	}
}

