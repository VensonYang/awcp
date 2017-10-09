package BP.Sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




import BP.DA.DBAccess;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.RefMethod;

/** 
 数据源
 
*/
public class SFDBSrc extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		///#region 属性
	/** 
	 是否是树形实体?
	 
	*/
	public final String getUserID()
	{
		return this.GetValStringByKey(SFDBSrcAttr.UserID);
	}
	public final void setUserID(String value)
	{
		this.SetValByKey(SFDBSrcAttr.UserID, value);
	}
	/** 
	 密码
	 
	*/
	public final String getPassword()
	{
		return this.GetValStringByKey(SFDBSrcAttr.Password);
	}
	public final void setPassword(String value)
	{
		this.SetValByKey(SFDBSrcAttr.Password, value);
	}
	/** 
	 数据库名称
	 
	*/
	public final String getDBName()
	{
		return this.GetValStringByKey(SFDBSrcAttr.DBName);
	}
	public final void setDBName(String value)
	{
		this.SetValByKey(SFDBSrcAttr.DBName, value);
	}
	/** 
	 数据库类型
	 
	*/
	public final DBSrcType getDBSrcType()
	{
		return DBSrcType.forValue(this.GetValIntByKey(SFDBSrcAttr.DBSrcType));
	}
	public final void setDBSrcType(DBSrcType value)
	{
		this.SetValByKey(SFDBSrcAttr.DBSrcType, value.getValue());
	}
	public final String getIP()
	{
		return this.GetValStringByKey(SFDBSrcAttr.IP);
	}
	public final void setIP(String value)
	{
		this.SetValByKey(SFDBSrcAttr.IP, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 数据源
	 
	*/
	public SFDBSrc()
	{
	}
	public SFDBSrc(String mypk)
	{
		this.setNo(mypk);
		this.Retrieve();
	}
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_SFDBSrc");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("数据源");
		map.setEnType(EnType.Sys);

		map.AddTBStringPK(SFDBSrcAttr.No, null, "数据源编号(必须是英文)", true, false, 1, 20, 20);
		map.AddTBString(SFDBSrcAttr.Name, null, "数据源名称", true, false, 0, 30, 20);

		map.AddTBString(SFDBSrcAttr.UserID, null, "数据库登录用户ID", true, false, 0, 30, 20);
		map.AddTBString(SFDBSrcAttr.Password, null, "数据库登录用户密码", true, false, 0, 30, 20);
		map.AddTBString(SFDBSrcAttr.IP, null, "IP地址", true, false, 0, 50, 20);
		map.AddTBString(SFDBSrcAttr.DBName, null, "数据库名称", true, false, 0, 30, 20);

		map.AddDDLSysEnum(SFDBSrcAttr.DBSrcType, 0, "数据源类型", true, true, SFDBSrcAttr.DBSrcType, "@0=应用系统主数据库@1=SQLServer@2=Oracle@3=MySQL@4=Infomix");

		RefMethod rm = new RefMethod();
		rm = new RefMethod();
		rm.Title = "测试连接";
		rm.ClassMethodName = this.toString() + ".DoConn";
		map.AddRefMethod(rm);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
	/** 
	 执行连接
	 
	 @return 
	*/
	public final String DoConn()
	{
		if (this.getNo().equals("local"))
		{
			return "本地连接不需要测试是否连接成功.";
		}

		if (this.getDBSrcType() == BP.Sys.DBSrcType.Localhost)
		{
			throw new RuntimeException("@在该系统中只能有一个本地连接.");
		}

		String dsn = "";
		if (this.getDBSrcType() == BP.Sys.DBSrcType.SQLServer)
		{
			try
			{
//				dsn = "Password=" + this.getPassword() + ";Persist Security Info=True;User ID=" + this.getUserID() + ";Initial Catalog=" + this.getDBName() + ";Data Source=" + this.getIP() + ";Timeout=999;MultipleActiveResultSets=true";
//				System.Data.SqlClient.SqlConnection conn = new System.Data.SqlClient.SqlConnection();
//				conn.ConnectionString = dsn;
//				conn.Open();
//				conn.Close();

				//删除应用.
				try
				{
					BP.DA.DBAccess.RunSQL("Exec sp_droplinkedsrvlogin " + this.getNo() + ",Null ");
					BP.DA.DBAccess.RunSQL("Exec sp_dropserver " + this.getNo());
				}
				catch (java.lang.Exception e)
				{
				}

				//创建应用.
				String sql = "";
				sql += "sp_addlinkedserver @server='" + this.getNo() + "', @srvproduct='', @provider='SQLOLEDB', @datasrc='" + this.getIP() + "'";
				BP.DA.DBAccess.RunSQL(sql);

				//执行登录.
				sql = "";
				sql += " EXEC sp_addlinkedsrvlogin '" + this.getNo() + "','false', NULL, '" + this.getUserID() + "', '" + this.getPassword() + "'";
				BP.DA.DBAccess.RunSQL(sql);

				return "恭喜您，该(" + this.getName() + ")连接配置成功。";
			}
			catch (RuntimeException ex)
			{
				return ex.getMessage();
			}
		}

		if (this.getDBSrcType() == BP.Sys.DBSrcType.Oracle)
		{
			try
			{
				dsn = "user id=" + this.getUserID() + ";data source=" + this.getDBName() + ";password=" + this.getPassword() + ";Max Pool Size=200";
//				System.Data.OracleClient.OracleConnection conn = new System.Data.OracleClient.OracleConnection();
//				conn.ConnectionString = dsn;
//				conn.Open();
//				conn.Close();
				return "恭喜您，该(" + this.getName() + ")连接配置成功。";
			}
			catch (RuntimeException ex)
			{
				return ex.getMessage();
			}
		}

		if (this.getDBSrcType() == BP.Sys.DBSrcType.MySQL)
		{
			try
			{
				dsn = "Data Source=" + this.getIP() + ";Persist Security info=True;Initial Catalog=" + this.getDBName() + ";User ID=" + this.getUserID() + ";Password=" + this.getPassword() + ";";
//				MySql.Data.MySqlClient.MySqlConnection conn = new MySql.Data.MySqlClient.MySqlConnection();
//				conn.ConnectionString = dsn;
//				conn.Open();
//				conn.Close();
				return "恭喜您，该(" + this.getName() + ")连接配置成功。";
			}
			catch (RuntimeException ex)
			{
				return ex.getMessage();
			}
		}

		//  if (this.DBSrcType == Sys.DBSrcType.Infomax)
		//{
		//    try
		//    {
		////Host=10.0.2.36;Service=8001;Server=niosserver; Database=ccflow; UId=npmuser; Password=npmoptr2012;Database locale=en_US.819;Client Locale=en_US.CP1252
		//        dsn = "Data Source="+this.IP+";Persist Security info=True;Initial Catalog="+this.DBName+";User ID="+this.UserID+";Password="+this.Password+";";
		//        MySql.Data.MySqlClient.MySqlConnection conn = new MySql.Data.MySqlClient.MySqlConnection();
		//        conn.ConnectionString = dsn;
		//        conn.Open();
		//        conn.Close();
		//        return "恭喜您，该(" + this.Name + ")连接配置成功。";
		//    }
		//    catch (Exception ex)
		//    {
		//        return ex.Message;
		//    }
		//}
		return "没有涉及到的连接测试类型...";
	}
	/** 
	 获得数据列表.
	 
	 @return 
	*/
	public final DataTable GetTables()
	{
		 StringBuilder sql = new StringBuilder();
			sql.append(String.format("SELECT ss.SrcTable FROM Sys_SFTable ss WHERE ss.FK_SFDBSrc = '%1$s'", this.getNo()));

			DataTable allTablesExist = DBAccess.RunSQLReturnTable(sql.toString());

			sql = new StringBuilder();

			DBSrcType dbType = this.getDBSrcType();
			if (dbType == DBSrcType.Localhost)
			{
				switch (SystemConfig.getAppCenterDBType())
				{
					case MSSQL:
						dbType =DBSrcType.SQLServer;
						break;
					case Oracle:
						dbType = DBSrcType.Oracle;
						break;
					case MySQL:
						dbType = DBSrcType.MySQL;
						break;
					case Informix:
						dbType = DBSrcType.Infomax;
						break;
					default:
						throw new RuntimeException("没有涉及到的连接测试类型...");
				}
			}

			switch (dbType)
			{
				case SQLServer:
					sql.append("SELECT NAME AS No,");
					sql.append("       [Name] = '[' + (CASE xtype WHEN 'U' THEN '表' ELSE '视图' END) + '] ' + ");
					sql.append("       NAME,");
					sql.append("       xtype");
					sql.append(" FROM   sysobjects");
					sql.append(" WHERE  (xtype = 'U' OR xtype = 'V')");
					//   sql.append("       AND (NAME NOT LIKE 'ND%')");
					sql.append("       AND (NAME NOT LIKE 'Demo_%')");
					sql.append("       AND (NAME NOT LIKE 'Sys_%')");
					sql.append("       AND (NAME NOT LIKE 'WF_%')");
					sql.append("       AND (NAME NOT LIKE 'GPM_%')");
					sql.append(" ORDER BY");
					sql.append("       xtype,");
					sql.append("       NAME");
					break;
				case Oracle:
					sql.append("SELECT uo.OBJECT_NAME AS No,");
					sql.append("       '[' || (CASE uo.OBJECT_TYPE");
					sql.append("         WHEN 'TABLE' THEN");
					sql.append("          '表'");
					sql.append("         ELSE");
					sql.append("          '视图'");
					sql.append("       END) || '] ' || uo.OBJECT_NAME AS Name,");
					sql.append("       CASE uo.OBJECT_TYPE");
					sql.append("         WHEN 'TABLE' THEN");
					sql.append("          'U'");
					sql.append("         ELSE");
					sql.append("          'V'");
					sql.append("       END AS xtype");
					sql.append("  FROM user_objects uo");
					sql.append(" WHERE (uo.OBJECT_TYPE = 'TABLE' OR uo.OBJECT_TYPE = 'VIEW')");
					//sql.append("   AND uo.OBJECT_NAME NOT LIKE 'ND%'");
					sql.append("   AND uo.OBJECT_NAME NOT LIKE 'Demo_%'");
					sql.append("   AND uo.OBJECT_NAME NOT LIKE 'Sys_%'");
					sql.append("   AND uo.OBJECT_NAME NOT LIKE 'WF_%'");
					sql.append("   AND uo.OBJECT_NAME NOT LIKE 'GPM_%'");
					sql.append(" ORDER BY uo.OBJECT_TYPE, uo.OBJECT_NAME");
					break;
				case MySQL:
					sql.append("SELECT ");
					sql.append("    table_name AS No,");
					sql.append("    CONCAT('[',");
					sql.append("            CASE table_type");
					sql.append("                WHEN 'BASE TABLE' THEN '表'");
					sql.append("                ELSE '视图'");
					sql.append("            END,");
					sql.append("            '] ',");
					sql.append("            table_name) AS Name,");
					sql.append("    CASE table_type");
					sql.append("        WHEN 'BASE TABLE' THEN 'U'");
					sql.append("        ELSE 'V'");
					sql.append("    END AS xtype");
					sql.append(" FROM");
					sql.append("    information_schema.tables");
					sql.append(" WHERE");
					sql.append(String.format("    table_schema = '%1$s'", SystemConfig.getAppCenterDBDatabase()));
					sql.append("        AND (table_type = 'BASE TABLE'");
					sql.append("        OR table_type = 'VIEW')");
					//   sql.append("       AND (table_name NOT LIKE 'ND%'");
					sql.append("        AND table_name NOT LIKE 'Demo_%'");
					sql.append("        AND table_name NOT LIKE 'Sys_%'");
					sql.append("        AND table_name NOT LIKE 'WF_%'");
					sql.append("        AND table_name NOT LIKE 'GPM_%'");
					sql.append(" ORDER BY table_type , table_name;");
					break;
				case  Infomax:
					sql.append("");
					break;
				default:
					break;
			}

			DataTable allTables = null;
			if (this.getNo().equals("local")) {
				allTables = DBAccess.RunSQLReturnTable(sql.toString());
			} else {
				try
				{
					allTables = DBAccess.RunSQLReturnTable(sql.toString());
				}
				catch (RuntimeException ex)
				{
					throw new RuntimeException("@失败:" + ex.getMessage());
				}
			}

			//去除已经使用的表
			DataTable allTablesTemp = allTables.clone();
			for (DataRow dr : allTablesExist.Rows) {
				for (DataRow alldr : allTables.Rows) {
					if(!alldr.getValue("No").equals(dr.getValue(0))){
						allTablesTemp.Rows.remove(alldr);
					}
					
				}
				
			}
			return allTablesTemp;
	}

	/** 
	 获取表的字段信息
	 
	 @param tableName 表/视图名称
	 @return 
	*/
	public final DataTable GetColumns(String tableName)
	{
		//SqlServer数据库
		StringBuilder sql = new StringBuilder();

		DBSrcType dbType = this.getDBSrcType();
		if (dbType == DBSrcType.Localhost)
		{
			switch (SystemConfig.getAppCenterDBType())
			{
				case MSSQL:
					dbType = DBSrcType.SQLServer;
					break;
				case Oracle:
					dbType = DBSrcType.Oracle;
					break;
				case MySQL:
					dbType = DBSrcType.MySQL;
					break;
				case Informix:
					dbType = DBSrcType.Infomax;
					break;
				default:
					throw new RuntimeException("没有涉及到的连接测试类型...");
			}
		}

		switch (dbType)
		{
			case SQLServer:
				sql.append("SELECT sc.name,");
				sql.append("       st.name AS [type],");
				sql.append("       (");
				sql.append("           CASE ");
				sql.append("                WHEN st.name = 'nchar' OR st.name = 'nvarchar' THEN sc.length / 2");
				sql.append("                ELSE sc.length");
				sql.append("           END");
				sql.append("       ) AS length,");
				sql.append("       sc.colid,");
				sql.append("       ISNULL(ep.[value], '') AS [Desc]");
				sql.append("FROM   dbo.syscolumns sc");
				sql.append("       INNER JOIN dbo.systypes st");
				sql.append("            ON  sc.xtype = st.xusertype");
				sql.append("       LEFT OUTER JOIN sys.extended_properties ep");
				sql.append("            ON  sc.id = ep.major_id");
				sql.append("            AND sc.colid = ep.minor_id");
				sql.append("            AND ep.name = 'MS_Description'");
				sql.append(String.format("WHERE  sc.id = OBJECT_ID('dbo.%1$s')", tableName));
				break;
			case Oracle:
				sql.append("SELECT utc.COLUMN_NAME AS Name,");
				sql.append("       utc.DATA_TYPE   AS type,");
				sql.append("       utc.CHAR_LENGTH AS length,");
				sql.append("       utc.COLUMN_ID   AS colid,");
				sql.append("       ucc.comments    AS Desc");
				sql.append("  FROM user_tab_cols utc");
				sql.append("  LEFT JOIN user_col_comments ucc");
				sql.append("    ON ucc.table_name = utc.TABLE_NAME");
				sql.append("   AND ucc.column_name = utc.COLUMN_NAME");
				sql.append(String.format(" WHERE utc.TABLE_NAME = '%1$s'", tableName.toUpperCase()));
				sql.append(" ORDER BY colid ASC");

				break;
			case MySQL:
				sql.append("SELECT ");
				sql.append("    column_name AS 'name',");
				sql.append("    data_type AS 'type',");
				sql.append("    IFNULL(character_maximum_length,");
				sql.append("            numeric_precision) AS length,");
				sql.append("    ordinal_position AS colid,");
				sql.append("    column_comment AS 'Desc'");
				sql.append("FROM");
				sql.append("    information_schema.columns");
				sql.append(" WHERE");
				sql.append(String.format("    table_schema = '%1$s'", SystemConfig.getAppCenterDBDatabase()));
				sql.append(String.format("        AND table_name = '%1$s';", tableName));
				break;
			case Infomax:
				break;
			default:
				throw new RuntimeException("没有涉及到的连接测试类型...");
		}


		if (this.getNo().equals("local"))
		{
			return DBAccess.RunSQLReturnTable(sql.toString());
		}

		try
		{
			if (this.getNo().equals("local"))
			{
				DataTable table=DBAccess.RunSQLReturnTable(sql.toString());
				return table;
			}
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException("@失败:" + ex.getMessage());
		}

		return null;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		if (this.getNo().equals("local"))
		{
			throw new RuntimeException("@默认连接(local)不允许删除、更新.");
		}
		return super.beforeDelete();
	}
	@Override
	protected boolean beforeUpdate()
	{
		if (this.getNo().equals("local"))
		{
			throw new RuntimeException("@默认连接(local)不允许删除、更新.");
		}
		return super.beforeUpdate();
	}

}