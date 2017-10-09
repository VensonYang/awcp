
package org.szcloud.framework.core.domain;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 查询语言或命名查询的定位参数集。JPA、Hibernate和SQL等都支持定位
 * 参数(如"... where e.name = ?")和命名参数(如"... where name = :name")两种形式。<br>
 * 尽可能采用命名参数的形式，定位参数是落后的形式。
 * @author yyang
 */
public class PositionalParameters implements QueryParameters {
    
    private Object[] params;
    
    /**
     * 创建一个空查询参数集
     * @return 一个基于数组的查询参数集
     */
    public static PositionalParameters create() {
        return new PositionalParameters(new Object[]{});
    }
    
    /**
     * 创建一个查询参数集，用数组填充参数值
     * @param params 参数值数组
     * @return 一个基于数组的参数集
     */
    public static PositionalParameters create(Object... params) {
        return new PositionalParameters(params);
    }
    
    /**
     * 创建一个查询参数集，用列表填充参数值
     * @param params 参数值列表
     * @return 一个基于数组的参数集
     */
    public static PositionalParameters create(List<Object> params) {
        return new PositionalParameters(params.toArray());
    }

    private PositionalParameters(Object[] params) {
        if (params == null) {
            this.params = new Object[]{};
        } else {
            this.params = Arrays.copyOf(params, params.length);
        }
    }

    /**
     * 获得参数值数组
     * @return 参数数组
     */
    public Object[] getParams() {
        return Arrays.copyOf(params, params.length);
    }

    /**
     * 获得对象的哈希值
     * @return 对象的哈希值
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 43).append(params).toHashCode();
    }

    /**
     * 判断参数集对象的等价性。当且仅当两个PositionalParameters包含的参数数组相同时，两个对象才是等价的。
     * @param other 另一个对象
     * @return 如果当前对象等价于other则返回true，否则返回false。
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PositionalParameters)) {
            return false;
        }
        PositionalParameters that = (PositionalParameters) other;
        return new EqualsBuilder().append(this.getParams(), that.getParams()).isEquals();
    }

    /**
     * 获得参数集的字符串表示形式
     * @return 当前对象的字符串表示形式
     */
    @Override
    public String toString() {
        return Arrays.toString(params);
    }
}    