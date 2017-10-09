package org.szcloud.framework.core.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.szcloud.framework.core.utils.Springfactory;

/**
 * 
 * @author  caoyong
 *
 */
public class EntityRepositoryJDBC extends SimpleJdbcTemplate{
	
	/**
	 * 数据库类型
	 */
	public static final String DATABSE_TYPE_MYSQL ="mysql";
	public static final String DATABSE_TYPE_POSTGRE ="postgresql";
	public static final String DATABSE_TYPE_ORACLE ="oracle";
	public static final String DATABSE_TYPE_SQLSERVER ="sqlserver";
	
	/**
	 * 分页SQL
	 */
	public static final String MYSQL_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}";         //mysql
	public static final String POSTGRE_SQL = "select * from ( {0}) sel_tab00 limit {2} offset {1}";//postgresql
	public static final String ORACLE_SQL = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}"; //oracle
	public static final String SQLSERVER_SQL = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}"; //sqlserver

	
	public EntityRepositoryJDBC(javax.sql.DataSource dataSource) {
		super(dataSource);
	}
	
	/**
	 * 根据sql语句，返回对象集合
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param parameters参数集合(key为参数名，value为参数值)
	 * @return bean对象集合
	 */
	@SuppressWarnings("rawtypes")
	public List find(String sql,Class clazz,Map parameters){
		return super.find(sql,clazz,parameters);
	}
	
	/**
	 * 根据sql语句，返回对象
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param parameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	@SuppressWarnings("rawtypes")
	public Object findForObject(String sql,Class clazz,Map parameters){
		return super.findForObject(sql, clazz, parameters);
	}
	
	/**
	 * 根据sql语句，返回数值型返回结果
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	@SuppressWarnings("rawtypes")
	public long findForLong(String sql,Map parameters){
		return super.findForLong(sql, parameters);
	}
	
	/**
	 * 根据sql语句，返回Map对象,对于某些项目来说，没有准备Bean对象，则可以使用Map代替Key为字段名,value为值
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	@SuppressWarnings("rawtypes")
	public Map findForMap(String sql,Map parameters){
		return super.findForMap(sql, parameters);
	}
	
	/**
	 * 根据sql语句，返回Map对象集合
	 * @see findForMap
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param parameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> findForListMap(String sql,Map parameters){
		return super.findForListMap(sql, parameters);
	}
	
	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:loginName,:password)<br>
	 * 参数用冒号,参数为bean的属性名
	 * @param sql
	 * @param bean
	 */
	public int executeForObject(String sql,Object bean){
		return super.executeForObject(sql, bean);
	}

	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:login_name,:password)<br>
	 * 参数用冒号,参数为Map的key名
	 * @param sql
	 * @param parameters
	 */
	@SuppressWarnings("rawtypes")
	public int executeForMap(String sql,Map parameters){
		return super.executeForMap(sql, parameters);
	}
	
	/*
	 * 批量处理操作
	 * 例如：update t_actor set first_name = :firstName, last_name = :lastName where id = :id
	 * 参数用冒号
	 */
	public int[] batchUpdate(final String sql,List<Object[]> batch ){
        return super.batchUpdate(sql,batch);
	}
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 */
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> find(String sql, int page, int rows) {
		//封装分页SQL
		sql = jeecgCreatePageSql(sql,page,rows);
		return jdbcTemplate.queryForList(sql);
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> find(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql,objs);
	}
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public List<Map<String, Object>> findObj(String sql, int page, int rows,Class<T> clazz) {
		List<T> rsList = new ArrayList<T>();
		//封装分页SQL
		sql = jeecgCreatePageSql(sql,page,rows);
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("deprecation")
	public  List<Map<String, Object>>  find(String  sql,  int page, int rows,Object... objs){
		//封装分页SQL
		sql = jeecgCreatePageSql(sql,page,rows);
		return jdbcTemplate.queryForList(sql,objs);
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> findOne(String sql, Object... objs) {
		try{ 
			return this.jdbcTemplate.queryForMap(sql, objs);
		}catch (EmptyResultDataAccessException e) {   
		    return null;   
		}  
	}
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */
	@SuppressWarnings("deprecation")
	public Long getCount(String  sql) {
		return  jdbcTemplate.queryForLong(sql);
	}
	
	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 * 
	 */
	@SuppressWarnings("deprecation")
	public Long getCount(String  sql,Object... objs) {
		return  jdbcTemplate.queryForLong(sql, objs);
	}

	@SuppressWarnings("deprecation")
	public Integer executeSql2(String sql,List<Object> param) {
		return this.jdbcTemplate.update(sql,param);
	}

	@SuppressWarnings("deprecation")
	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql,param);
	}

	@SuppressWarnings("deprecation")
	public Integer queryCount(String sql, Object... param) {
		return this.jdbcTemplate.queryForInt(sql, param);
	}

	/**
	 * 按照数据库类型，封装SQL
	 */
	public static String jeecgCreatePageSql(String sql, int page, int rows){
		int beginNum = (page - 1) * rows;
		String[] sqlParam = new String[3];
		sqlParam[0] = sql;
		sqlParam[1] = beginNum+"";
		sqlParam[2] = rows+"";
		if(EntityRepositoryJDBC.getDbType().indexOf(DATABSE_TYPE_MYSQL)!=-1){
			sql = MessageFormat.format(MYSQL_SQL, sqlParam[0],sqlParam[1],sqlParam[2]);
		}else if(EntityRepositoryJDBC.getDbType().indexOf(DATABSE_TYPE_POSTGRE)!=-1){
			sql = MessageFormat.format(POSTGRE_SQL, sqlParam[0],sqlParam[1],sqlParam[2]);
		}else {
			int beginIndex = (page-1)*rows;
			int endIndex = beginIndex+rows;
			sqlParam[2] = Integer.toString(beginIndex);
			sqlParam[1] = Integer.toString(endIndex);
			if(EntityRepositoryJDBC.getDbType().indexOf(DATABSE_TYPE_ORACLE)!=-1) {
				sql = MessageFormat.format(ORACLE_SQL, sqlParam[0],sqlParam[1],sqlParam[2]);
			} else if(EntityRepositoryJDBC.getDbType().indexOf(DATABSE_TYPE_SQLSERVER)!=-1) {
				sqlParam[0] = sql.substring(getAfterSelectInsertPoint(sql));
				sql = MessageFormat.format(SQLSERVER_SQL, sqlParam[0],sqlParam[1],sqlParam[2]);
			}
		}
		return sql;
	}
	
	private static int getAfterSelectInsertPoint(String sql) {
	    int selectIndex = sql.toLowerCase().indexOf("select");
	    int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
	    return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }
	
	private static String  getDbType(){
		DataSource dataSource = Springfactory.getBean("dataSource");		
		String retStr = "";		
		String dbdialect="";
		Connection con = null;
		try {
			con = dataSource.getConnection();
			if(con != null) 
				dbdialect = con.getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (dbdialect.contains("mysql")) {
			retStr="mysql";
		}else if (dbdialect.contains("Oracle")) {//oracle有多个版本的方言
			retStr = "oracle";
		}else if (dbdialect.equals("org.hibernate.dialect.SQLServerDialect")) {
			retStr = "sqlserver";
		}else if (dbdialect.equals("org.hibernate.dialect.PostgreSQLDialect")) {
			retStr = "postgres";
		}
		return retStr;
	}
	
}

