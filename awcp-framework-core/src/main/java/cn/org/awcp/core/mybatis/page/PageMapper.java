package cn.org.awcp.core.mybatis.page;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 扩展例子
 *
 * @author liuzh
 */
public interface PageMapper<T> {
    /**
     * 单表分页查询
     *
     * @param object
     * @param offset
     * @param limit
     * @return
     */
    @SelectProvider(type=PageProvider.class,method = "dynamicSQL")
    List<T> selectPage(@Param("entity") T object, @Param("offset") int offset, @Param("limit") int limit);
}
