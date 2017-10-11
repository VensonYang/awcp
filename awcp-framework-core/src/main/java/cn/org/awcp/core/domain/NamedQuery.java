package cn.org.awcp.core.domain;

import cn.org.awcp.core.utils.Assert;

/**
 * 基于命名查询的查询对象。DDDLib支持的四种查询形式之一。
 * 可以指定定位查询参数或命名查询参数，也可以针对查询结果取子集。
 * @author yyang
 */
public class NamedQuery extends BaseQuery<NamedQuery> {

    private final String queryName;

    /**
     * 使用仓储和命名查询名字创建命名查询
     * @param repository 仓储
     * @param queryName 命名查询的名称
     */
    public NamedQuery(EntityRepository repository, String queryName) {
        super(repository);
        Assert.notBlank(queryName);
        this.queryName = queryName;
    }

    /**
     * 获取命名查询的名称
     * @return 命名查询名称
     */
    public String getQueryName() {
        return queryName;
    }

}
