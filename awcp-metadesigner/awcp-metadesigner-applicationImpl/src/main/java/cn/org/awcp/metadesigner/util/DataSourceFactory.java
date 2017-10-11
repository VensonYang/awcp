package cn.org.awcp.metadesigner.util;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

import cn.org.awcp.core.domain.EntityRepositoryJDBC;
import cn.org.awcp.core.utils.Springfactory;
import cn.org.awcp.metadesigner.application.DataSourceManageService;
import cn.org.awcp.metadesigner.vo.DataSourceManageVO;

public class DataSourceFactory {
	private static Map<Long, DataSource> singletonObjects = new ConcurrentHashMap<Long, DataSource>(64);

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
	private static DataSourceManageService dataSourceManageService;

	public static DataSource getDataSource(String dsName, Long systemId) {
		if (dataSourceManageService == null) {
			dataSourceManageService = Springfactory.getBean("dataSourceManageServiceImpl");
		}
		// 查找系统关联id
		DataSourceManageVO dsm = dataSourceManageService.queryDataSourceByNameAndSystemId(dsName, systemId);
		if (dsm != null) {
			return getDataSourceById(dsm.getId());
		}
		return null;
	}

	/**
	 * 如果singletonObjects中有，则返回，否则初始化后返回 就怕并发性能问题
	 * 
	 * @param dsId
	 * @return
	 */
	public static DataSource getDataSourceById(Long dsId) {
		logger.debug("-------------------------是进这里？----------------------");
		DataSource ds = singletonObjects.get(dsId);
		if (ds == null) {
			synchronized (singletonObjects) {
				if (dataSourceManageService == null) {
					dataSourceManageService = Springfactory.getBean("dataSourceManageServiceImpl");
				}
				DataSourceManageVO vo = dataSourceManageService.get(dsId);
				// init DataSource
				DruidDataSource dds = new DruidDataSource();
				dds.setUrl(vo.getSourceUrl());
				dds.setUsername(vo.getUserName());
				dds.setPassword(vo.getUserPwd());
				// dds.setInitialSize(20);
				// dds.setMinIdle(1);
				// dds.setMaxActive(50); // 启用监控统计功能
				try {
					dds.init();
					ds = dds;
					singletonObjects.put(dsId, ds);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ds;
	}

	public static JdbcTemplate getJdbcTemplateById(Long dataSourceId) {
		DataSource ds = getDataSourceById(dataSourceId);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate;
	}

	public static EntityRepositoryJDBC getEntityRepositoryJDBCById(Long dataSourceId) {
		DataSource ds = getDataSourceById(dataSourceId);
		EntityRepositoryJDBC entityRepositoryJDBC = new EntityRepositoryJDBC(ds);
		return entityRepositoryJDBC;
	}

	public static JdbcTemplate getJdbcTemplateById(String dsName, Long systemId) {
		DataSource ds = getDataSource(dsName, systemId);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate;
	}

	public static EntityRepositoryJDBC getEntityRepositoryJDBCById(String dsName, Long systemId) {
		DataSource ds = getDataSource(dsName, systemId);
		EntityRepositoryJDBC entityRepositoryJDBC = new EntityRepositoryJDBC(ds);
		return entityRepositoryJDBC;
	}
}
