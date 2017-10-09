package org.szcloud.framework.core.mybatis.mapper;

import java.util.Properties;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.szcloud.framework.core.mybatis.page.PageMapper;

/**
 * 通用Mapper和Spring集成
 * 
 * @author liuzh
 */
public class MapperSpring implements BeanPostProcessor {

	@Autowired
	private MapperHelper mapperHelper;

	/**
	 * 通过注入配置参数
	 * 
	 * @param properties
	 */
	public void setProperties(Properties properties) {
		mapperHelper.setProperties(properties);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// 对所有的SqlSessionTemplate进行处理，多数据源情况下仍然有效
		if (bean instanceof SqlSessionTemplate) {
			SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate) bean;
			initMapperHelper(mapperHelper);
			mapperHelper.processConfiguration(sqlSessionTemplate
					.getConfiguration());
		} else if (bean instanceof MapperFactoryBean) {
			MapperFactoryBean mapperFactoryBean = (MapperFactoryBean) bean;
			initMapperHelper(mapperHelper);
			mapperHelper.processConfiguration(mapperFactoryBean.getSqlSession()
					.getConfiguration());
		}
		return bean;
	}

	private void initMapperHelper(MapperHelper mapperHelper) {
		// 设置UUID生成策略
		// 配置UUID生成策略需要使用OGNL表达式
		// 默认值32位长度:@java.util.UUID@randomUUID().toString().replace("-", "")
		mapperHelper.setUUID("");
		// 主键自增回写方法,默认值MYSQL,详细说明请看文档
		mapperHelper.setIDENTITY("HSQLDB");
		// 序列的获取规则,使用{num}格式化参数，默认值为{0}.nextval，针对Oracle
		// 可选参数一共3个，对应0,1,2,分别为SequenceName，ColumnName, PropertyName
		mapperHelper.setSeqFormat("NEXT VALUE FOR {0}");
		// 设置全局的catalog,默认为空，如果设置了值，操作表时的sql会是catalog.tablename
		mapperHelper.setCatalog("");
		// 设置全局的schema,默认为空，如果设置了值，操作表时的sql会是schema.tablename
		// 如果同时设置了catalog,优先使用catalog.tablename
		mapperHelper.setSchema("");
		// 主键自增回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
		mapperHelper.setOrder("AFTER");
		// 注册通用Mapper接口
		mapperHelper.registerMapper(Mapper.class);
		mapperHelper.registerMapper(PageMapper.class);
	}
}