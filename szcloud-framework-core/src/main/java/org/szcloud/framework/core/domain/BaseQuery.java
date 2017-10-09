/**
 * BaseQuery的抽象类
 * @author caoyong
 *
 */
package org.szcloud.framework.core.domain;

import java.util.List;
import java.util.Map;
import org.szcloud.framework.core.utils.Assert;

/**
 * 查询基类，为NamedQuery、JpqlQuery和SqlQuery提供共同行为。
 * @author yyang
 * @param <E> 查询的类型
 */
@SuppressWarnings("rawtypes")
public abstract class BaseQuery<E extends BaseQuery> {
    @SuppressWarnings("unused")
	private final EntityRepository repository;
    private QueryParameters parameters = PositionalParameters.create();
    private final NamedParameters mapParameters = NamedParameters.create();
    private int firstResult;
    private int maxResults;

    public BaseQuery(EntityRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

    /**
     * 获取查询参数
     * @return 查询参数
     */
    public QueryParameters getParameters() {
        return parameters;
    }

    /**
     * 设置定位命名参数（数组方式）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E setParameters(Object... parameters) {
        this.parameters = PositionalParameters.create(parameters);
        return (E)this;
    }

    /**
     * 设置定位参数（列表方式）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E setParameters(List<Object> parameters) {
        this.parameters = PositionalParameters.create(parameters);
        return (E) this;
    }

    /**
     * 设置命名参数（Map形式，Key是参数名称，Value是参数值）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E setParameters(Map<String, Object> parameters) {
        this.parameters = NamedParameters.create(parameters);
        return (E) this;
    }

    /**
     * 添加一个命名参数，Key是参数名称，Value是参数值。
     * @param key 命名参数名称
     * @param value 参数值
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E addParameter(String key, Object value) {
        mapParameters.add(key, value);
        this.parameters = mapParameters;
        return (E) this;
    }

    /**
     * 设置命名参数（Map形式，Key是参数名称，Value是参数值）
     * @param parameters 要设置的参数
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E setParameters(QueryParameters parameters) {
        this.parameters = parameters;
        return (E) this;
    }

    /**
     * 针对分页查询，获取firstResult。
     * firstResult代表从满足查询条件的记录的第firstResult + 1条开始获取数据子集。
     * @return firstResult的设置值，
     */
    public int getFirstResult() {
        return firstResult;
    }

    /**
     * 针对分页查询，设置firstResult。
     * firstResult代表从满足查询条件的记录的第firstResult + 1条开始获取数据子集。
     * @param firstResult 要设置的firstResult值。
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E setFirstResult(int firstResult) {
        Assert.isTrue(firstResult >= 0);
        this.firstResult = firstResult;
        return (E) this;
    }

    /**
     * 针对分页查询，获取maxResults设置值。
     * maxResults代表从满足查询条件的结果中最多获取的数据记录的数量。
     * @return maxResults的设置值。
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * 针对分页查询，设置maxResults的值。
     * maxResults代表从满足查询条件的结果中最多获取的数据记录的数量。
     * @param maxResults 要设置的maxResults值
     * @return 该对象本身
     */
    @SuppressWarnings("unchecked")
	public E setMaxResults(int maxResults) {
        Assert.isTrue(maxResults > 0);
        this.maxResults = maxResults;
        return (E) this;
    }
    
}
