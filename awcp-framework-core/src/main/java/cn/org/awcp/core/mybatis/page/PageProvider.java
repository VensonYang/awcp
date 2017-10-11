package cn.org.awcp.core.mybatis.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import cn.org.awcp.core.mybatis.mapper.EntityHelper;
import cn.org.awcp.core.mybatis.mapper.MapperHelper;
import cn.org.awcp.core.mybatis.mapper.MapperTemplate;
import cn.org.awcp.core.mybatis.sqlnode.OrderBySqlNode;

/**
 * 
 * @author hleecs
 *
 */
public class PageProvider extends MapperTemplate {
    public PageProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 分页查询
     * @param ms
     * @return
     */
    public SqlNode selectPage(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);

        List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
        //静态的sql部分:select column ... from table
        sqlNodes.add(new StaticTextSqlNode("SELECT "
                + EntityHelper.getSelectColumns(entityClass)
                + " FROM "
                + tableName(entityClass)));
        //获取全部列
        List<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List<SqlNode> ifNodes = new ArrayList<SqlNode>();
        boolean first = true;
        //对所有列循环，生成<if test="property!=null">[AND] column = #{property}</if>
        for (EntityHelper.EntityColumn column : columnList) {
            StaticTextSqlNode columnNode
                    = new StaticTextSqlNode((first ? "" : " AND ") + column.getColumn() + " = #{entity." + column.getProperty() + "} ");
            if (column.getJavaType().equals(String.class)) {
                ifNodes.add(new IfSqlNode(columnNode, "entity."+column.getProperty() + " != null and " + "entity."+column.getProperty() + " != '' "));
            } else {
                ifNodes.add(new IfSqlNode(columnNode, "entity."+column.getProperty() + " != null "));
            }
            first = false;
        }
        //增加entity判断
        IfSqlNode ifSqlNode = new IfSqlNode(new MixedSqlNode(ifNodes),"entity!=null");
        //将if添加到<where>
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), ifSqlNode));
        //add order by
        sqlNodes.add(new OrderBySqlNode(ms.getConfiguration(), getOrderByColumnNode(entityClass)));
        //处理分页
        sqlNodes.add(new IfSqlNode(new StaticTextSqlNode(" LIMIT #{limit}"),"offset==0"));
        sqlNodes.add(new IfSqlNode(new StaticTextSqlNode(" LIMIT #{limit} OFFSET #{offset} "),"offset>0"));
        return new MixedSqlNode(sqlNodes);
    }
}
