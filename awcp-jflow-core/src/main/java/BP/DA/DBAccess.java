package BP.DA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import BP.Sys.SystemConfig;
import BP.Tools.CRC32Helper;
import BP.Tools.StringHelper;
import cn.org.awcp.core.utils.Springfactory;

//
//简介：负责存取数据的类
//创建时间：2002-10
//最后修改时间：2004-2-1 ccb
//
// 说明：
//  在次文件种处理了4种方式的连接。
//  1， sql server .
//  2， oracle.
//  3， ole.
//  4, odbc.
//  
//

/**
 * 数据库访问。 这个类负责处理了 实体信息
 */
public class DBAccess {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(DBAccess.class);

	public static Paras DealParasBySQL(String sql, Paras ps) {
		Paras myps = new Paras();
		for (Para p : ps) {
			if (!sql.contains(":" + p.ParaName)) {
				continue;
			}
			myps.Add(p);
		}
		return myps;
	}

	/**
	 * 文件复制
	 * 
	 * @param Src
	 *            源文件
	 * @param Dst
	 *            文件地址
	 */
	public static void copyDirectory(String Src, String Dst) {
		/*
		 * String[] Files; if (Dst.charAt(Dst.length() - 1) !=
		 * Path.DirectorySeparatorChar) { Dst += Path.DirectorySeparatorChar; }
		 * if (!Directory.Exists(Dst)) { Directory.CreateDirectory(Dst); } Files
		 * = Directory.GetFileSystemEntries(Src); for (String Element : Files) {
		 * // Sub directories if (Directory.Exists(Element)) {
		 * copyDirectory(Element, Dst + Path.GetFileName(Element)); } // Files
		 * in directory else { File.Copy(Element, Dst +
		 * Path.GetFileName(Element), true); } }
		 */
		copyFolder(new File(Src), new File(Dst));
	}

	public static int RunSP(String spName, String paraKey, Object paraVal) {
		Paras pas = new Paras();
		pas.Add(paraKey, paraVal);
		return DBAccess.RunSP(spName, pas);
	}

	/**
	 * 运行存储过程
	 * 
	 * @param spName
	 *            名称
	 * @return 返回影响的行数
	 */
	public static int RunSP(String spName) {
		// int i = 0;
		switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
		case MSSQL:
		case Access:
			throw new RuntimeException("@没有实现...");
		case Oracle:
			throw new RuntimeException("@没有实现...");
		case Informix:
			throw new RuntimeException("@没有实现...");
		default:
			throw new RuntimeException("Error: " + BP.Sys.SystemConfig.getAppCenterDBType());
		}
	}

	public static int RunSPReturnInt(String spName) {
		switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
		case MSSQL:
			throw new RuntimeException("@没有实现...");
		case MySQL:
			throw new RuntimeException("@没有实现...");
		case Informix:
			throw new RuntimeException("@没有实现...");
		case Access:
		case Oracle:
			throw new RuntimeException("@没有实现...");
		default:
			throw new RuntimeException("Error: " + BP.Sys.SystemConfig.getAppCenterDBType());
		}
	}

	/**
	 * 运行存储过程
	 * 
	 * @param spName
	 *            名称
	 * @param paras
	 *            参数
	 * @return 返回影响的行数
	 */
	public static int RunSP(String spName, Paras paras) {
		// int i = 0;
		switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
		case MSSQL:
			throw new RuntimeException("@没有实现...");
		case MySQL:
			throw new RuntimeException("@没有实现...");
		case Oracle:
			throw new RuntimeException("@没有实现...");
		case Informix:
			throw new RuntimeException("@没有实现...");
		default:
			throw new RuntimeException("Error " + BP.Sys.SystemConfig.getAppCenterDBType());
		}
	}

	/**
	 * 运行存储过程
	 * 
	 * @param spName
	 *            名称
	 * @return DataTable
	 */
	public static DataTable RunSPReTable(String spName) {
		switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
		case MSSQL:
		case Access:
			throw new RuntimeException("@没有实现...");
		case Oracle:
			throw new RuntimeException("@没有实现...");
		case Informix:
			throw new RuntimeException("@没有实现...");
		default:
			throw new RuntimeException("Error " + BP.Sys.SystemConfig.getAppCenterDBType());

		}
	}

	/**
	 * 运行存储过程
	 * 
	 * @param spName
	 *            名称
	 * @param paras
	 *            参数
	 * @return DataTable
	 */
	public static DataTable RunSPReTable(String spName, Paras paras) {
		switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
		case MSSQL:
			throw new RuntimeException("@没有实现...");
		case Oracle:
			throw new RuntimeException("@没有实现...");
		case Informix:
			throw new RuntimeException("@没有实现...");
		case Access:
		default:
			throw new RuntimeException("Error " + BP.Sys.SystemConfig.getAppCenterDBType());
		}
	}

	private static void copyFolder(File src, File dest) {
		try {
			if (src.isDirectory()) {
				if (!dest.exists()) {
					dest.mkdirs();
				}
				String files[] = src.list();
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// 递归复制
					copyFolder(srcFile, destFile);
				}
			} else {
				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dest);

				byte[] buffer = new byte[1024];

				int length;

				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// 构造函数
	static {
		CurrentSys_Serial = new Hashtable<String, Object>();
		KeyLockState = new Hashtable<String, Object>();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 运行中定义的变量
	public static Hashtable<String, Object> CurrentSys_Serial;
	private static int readCount = -1;
	private static Hashtable<String, Object> KeyLockState;

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 产生序列号码方法
	/**
	 * 根据标识产生的序列号
	 * 
	 * @param type
	 *            OID
	 * @return
	 * @throws Exception
	 */
	public static int GenerSequenceNumber(String type) throws Exception {
		if (readCount == -1) // 系统第一次运行时
		{
			DataTable tb = DBAccess.RunSQLReturnTable("SELECT CfgKey, IntVal FROM Sys_Serial ");
			for (DataRow row : tb.Rows) {
				String str = row.getValue(0).toString().trim();
				int id = Integer.parseInt(str);
				try {
					CurrentSys_Serial.put(str, id);
					KeyLockState.put(str, false);
				} catch (java.lang.Exception e) {
				}
			}
			readCount++;
		}
		if (!CurrentSys_Serial.containsKey(type)) {
			DBAccess.RunSQL("insert into Sys_Serial values('" + type + "',1 )");
			return 1;
		}

		while (true) {
			while (!Boolean.parseBoolean(KeyLockState.get(type).toString())) {
				KeyLockState.put(type, true);
				int cur = Integer.parseInt(CurrentSys_Serial.get(type).toString());
				if (readCount++ % 10 == 0) {
					readCount = 1;
					int n = Integer.parseInt(CurrentSys_Serial.get(type).toString()) + 10;

					Paras ps = new Paras();
					ps.Add("intVal", n);
					ps.Add("CfgKey", type);

					String upd = "update Sys_Serial set intVal=" + SystemConfig.getAppCenterDBVarStr()
							+ "intVal WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
					DBAccess.RunSQL(upd, ps);
				}

				cur++;
				CurrentSys_Serial.put(type, cur);
				KeyLockState.put(type, false);
				return cur;
			}
		}
	}

	/**
	 * 生成 GenerOIDByGUID.
	 * 
	 * @return
	 */
	public static int GenerOIDByGUID() {
		/*
		 * warning int i = CRC32Helper.GetCRC32(Guid.NewGuid().toString());
		 */
		int i = CRC32Helper.GetCRC32(UUID.randomUUID().toString());
		if (i <= 0) {
			i = -i;
		}
		return i;
	}

	/**
	 * 生成 GenerOIDByGUID.
	 * 
	 * @return
	 */
	public static int GenerOIDByGUID(String strs) {
		int i = CRC32Helper.GetCRC32(strs);
		if (i <= 0) {
			i = -i;
		}
		return i;
	}

	/**
	 * 生成 GenerGUID
	 * 
	 * @return
	 */
	public static String GenerGUID() {

		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 锁定OID
	 */
	private static boolean lock_OID = false;

	/**
	 * 产生一个OID
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int GenerOID() {
		while (lock_OID) {
		}

		lock_OID = true;
		if (DBAccess.RunSQL("UPDATE Sys_Serial SET IntVal=IntVal+1 WHERE CfgKey='OID'") == 0) {
			DBAccess.RunSQL("INSERT INTO Sys_Serial (CfgKey,IntVal) VALUES ('OID',100)");
		}
		int oid = DBAccess.RunSQLReturnValInt("SELECT  IntVal FROM Sys_Serial WHERE CfgKey='OID'");
		lock_OID = false;
		return oid;
	}

	/**
	 * 锁
	 */
	private static boolean lock_HT_CfgKey = false;
	private static Hashtable<String, Object> lock_HT = new Hashtable<String, Object>();

	/**
	 * 生成唯一的序列号
	 * 
	 * @param cfgKey
	 *            配置信息
	 * @return 唯一的序列号
	 * @throws Exception
	 */
	public static long GenerOID(String cfgKey) {
		while (lock_HT_CfgKey) {
		}
		lock_HT_CfgKey = true;

		Paras ps = new Paras();
		ps.Add("CfgKey", cfgKey);
		String sql = "UPDATE Sys_Serial SET IntVal=IntVal+1 WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr()
				+ "CfgKey";
		int num = DBAccess.RunSQL(sql, ps);
		if (num == 0) {
			sql = "INSERT INTO Sys_Serial (CFGKEY,INTVAL) VALUES ('" + cfgKey + "',100)";
			DBAccess.RunSQL(sql);
			lock_HT_CfgKey = false;

			lock_HT.put(cfgKey, 200);
			return 100;
		}
		sql = "SELECT IntVal FROM Sys_Serial WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		num = DBAccess.RunSQLReturnValInt(sql, ps);
		lock_HT_CfgKey = false;
		return num;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion 第二版本的生成 OID。

	/**
	 * 获取一个从OID, 更新到OID. 用例: 我已经明确知道要用到260个OID, 但是为了避免多次取出造成效率浪费，就可以一次性取出
	 * 260个OID.
	 * 
	 * @param cfgKey
	 * @param getOIDNum
	 *            要获取的OID数量.
	 * @return 从OID
	 * @throws Exception
	 */
	public static long GenerOID(String cfgKey, int getOIDNum) throws Exception {
		Paras ps = new Paras();
		ps.Add("CfgKey", cfgKey);
		String sql = "UPDATE Sys_Serial SET IntVal=IntVal+" + getOIDNum + " WHERE CfgKey="
				+ SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		int num = DBAccess.RunSQL(sql, ps);
		if (num == 0) {
			getOIDNum = getOIDNum + 100;
			sql = "INSERT INTO Sys_Serial (CFGKEY,INTVAL) VALUES (" + SystemConfig.getAppCenterDBVarStr() + "CfgKey,"
					+ getOIDNum + ")";
			DBAccess.RunSQL(sql, ps);
			return 100;
		}
		sql = "SELECT  IntVal FROM Sys_Serial WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		return DBAccess.RunSQLReturnValInt(sql, ps) - getOIDNum;
	}

	public static int GenerOIDByKey32(String intKey) throws Exception {
		Paras ps = new Paras();
		ps.Add("CfgKey", intKey);

		String sql = "";
		sql = "UPDATE Sys_Serial SET IntVal=IntVal+1 WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		int num = DBAccess.RunSQL(sql, ps);
		if (num == 0) {
			sql = "INSERT INTO Sys_Serial (CFGKEY,INTVAL) VALUES (" + SystemConfig.getAppCenterDBVarStr()
					+ "CfgKey,'100')";
			DBAccess.RunSQL(sql, ps);
			return Integer.parseInt(intKey + "100");
		}
		sql = "SELECT IntVal FROM Sys_Serial WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		int val = DBAccess.RunSQLReturnValInt(sql, ps);
		return Integer.parseInt(intKey + (new Integer(val)).toString());
	}

	public static long GenerOID(String table, String intKey) throws Exception {
		Paras ps = new Paras();
		ps.Add("CfgKey", intKey);

		String sql = "";
		sql = "UPDATE " + table + " SET IntVal=IntVal+1 WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		int num = DBAccess.RunSQL(sql, ps);
		if (num == 0) {
			sql = "INSERT INTO " + table + " (CFGKEY,INTVAL) VALUES (" + SystemConfig.getAppCenterDBVarStr()
					+ "CfgKey,100)";
			DBAccess.RunSQL(sql, ps);
			return Integer.parseInt(intKey + "100");
		}
		sql = "SELECT  IntVal FROM " + table + " WHERE CfgKey=" + SystemConfig.getAppCenterDBVarStr() + "CfgKey";
		int val = DBAccess.RunSQLReturnValInt(sql, ps);

		return Long.parseLong(intKey + (new Integer(val)).toString());
	}

	public static Connection getGetAppCenterDBConn() throws Exception {

		JdbcTemplate jdbcTemplate = Springfactory.getBean("jdbcTemplate");

		Connection conn = jdbcTemplate.getDataSource().getConnection();

		// Class.forName("com.mysql.jdbc.Driver");
		// Connection conn = DriverManager.getConnection(
		// SystemConfig.getAppCenterDSN(), SystemConfig.getUser(),
		// SystemConfig.getPassword());

		return conn;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion 取得连接对象 ，CS、BS共用属性

	/**
	 * AppCenterDBType
	 */
	public static DBType getAppCenterDBType() {
		return SystemConfig.getAppCenterDBType();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 运行 SQL

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 通过主应用程序在其他库上运行sql
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region pk
	/**
	 * 建立主键
	 * 
	 * @param tab
	 *            物理表
	 * @param pk
	 *            主键
	 * @throws Exception
	 */
	public static void CreatePK(String tab, String pk, DBType db) {
		String sql;
		switch (db) {
		// case DBType.Informix:
		// sql = "ALTER TABLE " + tab.ToUpper() +
		// " ADD CONSTRAINT PRIMARY KEY(" + pk + ") CONSTRAINT " + tab + "pk ";
		// break;
		default:
			sql = "ALTER TABLE " + tab.toUpperCase() + " ADD CONSTRAINT " + tab + "pk PRIMARY KEY(" + pk.toUpperCase()
					+ ")";
			break;
		}
		DBAccess.RunSQL(sql);
	}

	public static void CreatePK(String tab, String pk1, String pk2, DBType db) throws Exception {
		String sql;
		switch (db) {
		// case DBType.Informix:
		// sql = "ALTER TABLE " + tab.ToUpper() +
		// " ADD CONSTRAINT PRIMARY KEY(" + pk1.ToUpper() + "," + pk2.ToUpper()
		// + ") CONSTRAINT " + tab + "pk ";
		// break;
		default:
			sql = "ALTER TABLE " + tab.toUpperCase() + " ADD CONSTRAINT " + tab + "pk  PRIMARY KEY(" + pk1.toUpperCase()
					+ "," + pk2.toUpperCase() + ")";
			break;
		}
		DBAccess.RunSQL(sql);
	}

	public static void CreatePK(String tab, String pk1, String pk2, String pk3, DBType db) throws Exception {
		String sql;
		switch (db) {
		// case DBType.Informix:
		// sql = "ALTER TABLE " + tab.ToUpper() +
		// " ADD CONSTRAINT PRIMARY KEY(" + pk1.ToUpper() + "," + pk2.ToUpper()
		// + ","+pk3.ToUpper()+") CONSTRAINT " + tab + "pk ";
		// break;
		default:
			sql = "ALTER TABLE " + tab.toUpperCase() + " ADD CONSTRAINT " + tab + "pk PRIMARY KEY(" + pk1.toUpperCase()
					+ "," + pk2.toUpperCase() + "," + pk3.toUpperCase() + ")";
			break;
		}
		DBAccess.RunSQL(sql);
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region index
	public static void CreatIndex(String table, String pk) {
		String sql = "";
		try {
			sql = "DROP INDEX " + table + "ID ON " + table;
			DBAccess.RunSQL(sql);
		} catch (java.lang.Exception e) {
		}

		try {
			sql = "CREATE INDEX " + table + "ID ON " + table + " (" + pk + ")";
			DBAccess.RunSQL(sql);
		} catch (java.lang.Exception e2) {
		}
	}

	public static void CreatIndex(String table, String pk1, String pk2) {
		try {
			DBAccess.RunSQL("CREATE INDEX " + table + "ID ON " + table + " (" + pk1 + "," + pk2 + ")");
		} catch (java.lang.Exception e) {
		}
	}

	public static void CreatIndex(String table, String pk1, String pk2, String pk3) throws Exception {
		DBAccess.RunSQL("CREATE INDEX " + table + "ID ON " + table + " (" + pk1 + "," + pk2 + "," + pk3 + ")");
	}

	public static void CreatIndex(String table, String pk1, String pk2, String pk3, String pk4) throws Exception {
		DBAccess.RunSQL(
				"CREATE INDEX " + table + "ID ON " + table + " (" + pk1 + "," + pk2 + "," + pk3 + "," + pk4 + ")");
	}

	public static int RunSQL(String sql, Object obj, String dsn, Object... pars) {
		return 0;
		// object oconn = GetAppCenterDBConn;
		// if (oconn is SqlConnection)
		// return RunSQL(sql, (SqlConnection)oconn, sqlType, dsn);
		// else if (oconn is OracleConnection)
		// return RunSQL(sql, (OracleConnection)oconn, sqlType, dsn);
		// else
		// throw new Exception("获取数据库连接[GetAppCenterDBConn]失败！");
	}

	public static DataTable ReadProText(String proName) throws Exception {
		String sql = "";
		switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
		case Oracle:
			sql = "SELECT text FROM user_source WHERE name=UPPER('" + proName + "') ORDER BY LINE ";
			break;
		default:
			sql = "SP_Help  " + proName;
			break;
		}

		try {
			return BP.DA.DBAccess.RunSQLReturnTable(sql);
		} catch (java.lang.Exception e) {
			sql = "select * from Port_Emp WHERE 1=2";
			return BP.DA.DBAccess.RunSQLReturnTable(sql);
		}
	}

	public static void RunSQLScript(String sqlOfScriptFilePath) {
		String str = DataType.ReadTextFile(sqlOfScriptFilePath);
		String[] strs = str.split("[;]", -1);
		for (String s : strs) {
			/*
			 * warning if (StringHelper.isNullOrEmpty(s) ||
			 * String.IsNullOrWhiteSpace(s)) { continue; }
			 */
			if (StringHelper.isNullOrEmpty(s)) {
				continue;
			}

			if (s.contains("--")) {
				continue;
			}

			if (s.contains("/*")) {
				continue;
			}

			DBAccess.RunSQL(s);
		}
	}

	/**
	 * 执行具有Go的sql 文本。
	 * 
	 * @param sqlOfScriptFilePath
	 * @throws IOException
	 * @throws Exception
	 */
	public static void RunSQLScriptGo(String sqlOfScriptFilePath) throws IOException, Exception {
		String str = BP.DA.DataType.ReadTextFile(sqlOfScriptFilePath);
		/*
		 * warning String[] strs = str.split(new String[] { "--GO--" },
		 * StringSplitOptions.RemoveEmptyEntries);
		 */
		String[] strs = str.split("--GO--");
		for (String s : strs) {
			/*
			 * warning if (StringHelper.isNullOrEmpty(s) ||
			 * String.IsNullOrWhiteSpace(s)) { continue; }
			 */
			if (StringHelper.isNullOrEmpty(s)) {
				continue;
			}

			// if (s.Contains("--"))
			// continue;

			if (s.contains("/**")) {
				continue;
			}

			String mysql = s.replace("--GO--", "");
			if (StringHelper.isNullOrEmpty(mysql.trim())) {
				continue;
			}

			BP.DA.DBAccess.RunSQL(mysql);
		}
	}

	public static void RunSQLs(String sql) {
		if (StringHelper.isNullOrEmpty(sql)) {
			return;
		}

		sql = sql.replace("@GO", "~");
		sql = sql.replace("@", "~");
		String[] strs = sql.split("[~]", -1);
		for (String str : strs) {
			if (StringHelper.isNullOrEmpty(str)) {
				continue;
			}

			if (str.contains("--") || str.contains("/*")) {
				continue;
			}

			RunSQL(str);
		}
	}

	/**
	 * 运行带有参数的sql
	 * 
	 * @param ps
	 * @return
	 * @throws Exception
	 */
	public static int RunSQL(Paras ps) {
		return RunSQL(ps.SQL, ps);
	}

	/**
	 * 运行sql
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int RunSQL(String sql) {
		if (sql == null || sql.trim().equals("")) {
			return 1;
		}
		Paras ps = new Paras();
		ps.SQL = sql;
		return RunSQL(ps);
	}

	public static int RunSQL(String sql, String paraKey, Object val) {
		Paras ens = new Paras();
		ens.Add(paraKey, val);
		return RunSQL(sql, ens);
	}

	public static int RunSQL(String sql, String paraKey1, Object val1, String paraKey2, Object val2) {
		Paras ens = new Paras();
		ens.Add(paraKey1, val1);
		ens.Add(paraKey2, val2);
		return RunSQL(sql, ens);
	}

	public static int RunSQL(String sql, String paraKey1, Object val1, String paraKey2, Object val2, String k3,
			Object v3) {
		Paras ens = new Paras();
		ens.Add(paraKey1, val1);
		ens.Add(paraKey2, val2);
		ens.Add(k3, v3);
		return RunSQL(sql, ens);
	}

	/** 
	 
	 
	*/
	private static boolean lockRunSQL = false;

	/**
	 * 执行sql
	 * 
	 * @param sql
	 * @param paras
	 * @return
	 * @throws Exception
	 */

	public static int RunSQL(String sql, Paras paras) {
		if (sql == null || sql.trim().equals("")) {
			return 1;
		}

		// while (lockRunSQL) {
		// }
		lockRunSQL = true;

		int result = 0;
		try {
			switch (getAppCenterDBType()) {
			case MSSQL:
				result = RunSQL_200705_SQL(sql, paras);
				break;
			case Oracle:
				result = RunSQL_200705_Ora(sql.replace("]", "").replace("[", ""), paras);
				break;
			case MySQL:
				result = RunSQL_200705_MySQL(sql, paras);
				break;
			// case DBType.Informix:
			// result = RunSQL_201205_Informix(sql, paras);
			// break;
			// case DBType.Access:
			// result = RunSQL_200705_OLE(sql, paras);
			// break;
			default:
				throw new RuntimeException("发现未知的数据库连接类型！");
			}
			lockRunSQL = false;
			return result;
		} catch (RuntimeException ex) {
			lockRunSQL = false;
			String msg = "";
			/*
			 * warning Object tempVar = sql.clone();
			 */
			Object tempVar = sql;
			String mysql = (String) ((tempVar instanceof String) ? tempVar : null);
			for (Para p : paras) {
				msg += "@" + p.ParaName + "=" + p.val + "," + p.DAType.toString();
				mysql = mysql.replace(":" + p.ParaName + ",", "'" + p.val + "',");
			}
			throw new RuntimeException(
					"执行sql错误:" + ex.getMessage() + " Paras(" + paras.size() + ")=" + msg + "<hr>" + mysql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 运行sql返回结果
	 * 
	 * @param sql
	 *            sql
	 * @param paras
	 *            参数
	 * @return 执行的结果
	 * @throws Exception
	 */
	private static int RunSQL_200705_SQL(String sql, Paras paras) throws Exception {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		NamedParameterStatement pstmt = null;
		try {
			conn = DBAccess.getGetAppCenterDBConn();
			int i = 0;
			if (null != paras && paras.size() > 0) {
				pstmt = new NamedParameterStatement(conn, sql);
				PrepareCommand(pstmt, paras);
				i = pstmt.executeUpdate();
			} else {
				stmt = conn.createStatement();// 创建用于执行静态sql语句的Statement对象，st属局部变量
				i = stmt.executeUpdate(sql);

			}
			return i;
		} catch (Exception ex) {
			paras.SQL = sql;
			String msg = "";
			if (paras.size() == 0) {
				msg = "SQL=" + sql + ",异常信息:" + ex.getMessage();
			} else {
				msg = "SQL=" + paras.getSQLNoPara() + ",异常信息:" + ex.getMessage();
			}

			Log.DefaultLogWriteLineInfo(msg);
			throw new Exception(msg);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 运行sql
	 * 
	 * @param sql
	 *            sql
	 * @return 执行结果
	 * @throws Exception
	 */
	private static int RunSQL_200705_MySQL(String sql) throws Exception {
		return RunSQL_200705_MySQL(sql, new Paras());
	}

	/**
	 * RunSQL_200705_MySQL
	 * 
	 * @param sql
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	private static int RunSQL_200705_MySQL(String sql, Paras paras) throws Exception {
		logger.debug("流程SQL：" + sql);
		JdbcTemplate jdbcTemplate = Springfactory.getBean("jdbcTemplate");
		try {

			Map<String, Object> ps = parasToMap(paras);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
			namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			SqlParameterSource paramSource = new MapSqlParameterSource(ps);
			return namedParameterJdbcTemplate.update(sql, paramSource);
		} catch (Exception ex) {
			logger.debug(sql);
			ex.printStackTrace();
			String msg = "";
			if (paras.size() == 0) {
				msg = "SQL=" + sql + ",异常信息:" + ex.getMessage();
			} else {
				msg = "SQL=" + paras.getSQLNoPara() + ",异常信息:" + ex.getMessage();
			}
			Log.DefaultLogWriteLineInfo(msg);
			throw new Exception(msg);
		}
	}

	private static Map<String, Object> parasToMap(Paras paras) {
		Map<String, Object> ps = new HashMap<String, Object>();
		for (Para para : paras) {
			if (para.DAType == String.class) {
				try {
					if (para.val != null && !para.val.equals("")) {
						ps.put(para.ParaName, String.valueOf(para.val));
					} else {
						ps.put(para.ParaName, "");
					}
				} catch (Exception e) {
					ps.put(para.ParaName, null);
				}
			} else if (para.DAType == Long.class) {
				ps.put(para.ParaName, Long.parseLong(String.valueOf(para.val)));
			} else if (para.DAType == Integer.class) {
				if (para.val == "")
					para.val = "0";
				ps.put(para.ParaName, Integer.parseInt(String.valueOf(para.val)));
			} else if (para.DAType == Float.class) {
				ps.put(para.ParaName, Float.parseFloat(String.valueOf(para.val)));
			} else if (para.DAType == Double.class) {
				ps.put(para.ParaName, Double.parseDouble(String.valueOf(para.val)));
			} else if (para.DAType == BigDecimal.class) {
				ps.put(para.ParaName, new BigDecimal(String.valueOf(para.val)));
			} else if (para.DAType == Date.class) {
				Date date = (Date) para.val;
				ps.put(para.ParaName, new java.sql.Date(date.getTime()));
			} else if (para.DAType == Boolean.class) {
				ps.put(para.ParaName, Boolean.parseBoolean(String.valueOf(para.val)));
			}

		}
		return ps;
	}

	private static int RunSQL_200705_Ora(String sql, Paras paras) throws Exception {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		NamedParameterStatement pstmt = null;
		try {
			conn = DBAccess.getGetAppCenterDBConn();
			/*
			 * warning OracleConnection conn = DBAccess.getGetAppCenterDBConn();
			 * OracleCommand cmd = new OracleCommand(sql, conn); cmd.CommandType
			 * = CommandType.getText();
			 * 
			 * for (Para para : paras) { OracleParameter oraP = new
			 * OracleParameter(para.ParaName, para.getDATypeOfOra()); oraP.Size
			 * = para.Size; oraP.setValue(para.val); cmd.Parameters.Add(oraP); }
			 * int i = cmd.ExecuteNonQuery(); cmd.dispose(); return i;
			 */
			int i = 0;
			if (null != paras && paras.size() > 0) {
				pstmt = new NamedParameterStatement(conn, sql);
				PrepareCommand(pstmt, paras);
				i = pstmt.executeUpdate();
			} else {
				stmt = conn.createStatement();// 创建用于执行静态sql语句的Statement对象，st属局部变量
				i = stmt.executeUpdate(sql);
			}

			return i;
		} catch (Exception ex) {
			String msg = "";
			if (paras.size() == 0) {
				msg = "SQL=" + sql + ",异常信息:" + ex.getMessage();
			} else {
				msg = "SQL=" + paras.getSQLNoPara() + ",异常信息:" + ex.getMessage();
			}
			Log.DefaultLogWriteLineInfo(msg);
			throw new Exception(msg);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static DataTable RunSQLReturnTable_200705_Ora_UL(String selectSQL, Paras paras) {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		NamedParameterStatement pstmt = null;
		try {
			conn = DBAccess.getGetAppCenterDBConn();
			/*
			 * warning OracleDataAdapter ada = new OracleDataAdapter(selectSQL,
			 * conn); ada.SelectCommand.setCommandType(CommandType.getText());
			 * 
			 * // 加入参数 for (Para para : paras) { OracleParameter myParameter =
			 * new OracleParameter( para.ParaName, para.getDATypeOfOra());
			 * myParameter.Size = para.Size; myParameter.setValue(para.val);
			 * ada.SelectCommand.getParameters().Add(myParameter); }
			 * 
			 * DataTable oratb = new DataTable("otb"); ada.Fill(oratb);
			 * ada.dispose(); return oratb;
			 */
			DataTable oratb = new DataTable("otb");
			if (null != paras && paras.size() > 0) {
				pstmt = new NamedParameterStatement(conn, selectSQL);
				PrepareCommand(pstmt, paras);
				rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					oratb.Columns.Add_UL(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
				}
				while (rs.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < size; i++) {
						Object val = rs.getObject(i + 1);
						dr.setValue(i, val);// DataRow一欄一欄填入資料
						// dr.setDataType(i, Para.getDAType(val));
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			} else {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stmt.executeQuery(selectSQL);
				ResultSetMetaData rsmd = rs.getMetaData();
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					oratb.Columns.Add_UL(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
				}
				while (rs.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < size; i++) {
						Object val = rs.getObject(i + 1);
						dr.setValue(i, val);// DataRow一欄一欄填入資料
						// dr.setDataType(i, Para.getDAType(val));
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			}
			return oratb;

		} catch (Exception ex) {
			String msg = "@运行查询在(RunSQLReturnTable_200705_Ora with paras)出错 sql=" + selectSQL + " @异常信息："
					+ ex.getMessage();
			msg += "@Para Num= " + paras.size();
			for (Para pa : paras) {
				msg += "@" + pa.ParaName + "=" + pa.val;
			}
			Log.DebugWriteError(msg);
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static DataTable RunSQLReturnTable_200705_Ora(String selectSQL, Paras paras) {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		NamedParameterStatement pstmt = null;
		try {
			conn = DBAccess.getGetAppCenterDBConn();
			/*
			 * warning OracleDataAdapter ada = new OracleDataAdapter(selectSQL,
			 * conn); ada.SelectCommand.setCommandType(CommandType.getText());
			 * 
			 * // 加入参数 for (Para para : paras) { OracleParameter myParameter =
			 * new OracleParameter( para.ParaName, para.getDATypeOfOra());
			 * myParameter.Size = para.Size; myParameter.setValue(para.val);
			 * ada.SelectCommand.getParameters().Add(myParameter); }
			 * 
			 * DataTable oratb = new DataTable("otb"); ada.Fill(oratb);
			 * ada.dispose(); return oratb;
			 */
			DataTable oratb = new DataTable("otb");
			if (null != paras && paras.size() > 0) {
				pstmt = new NamedParameterStatement(conn, selectSQL);
				PrepareCommand(pstmt, paras);
				rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					oratb.Columns.Add(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
				}
				while (rs.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < size; i++) {
						Object val = rs.getObject(i + 1);
						dr.setValue(i, val);// DataRow一欄一欄填入資料
						// dr.setDataType(i, Para.getDAType(val));
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			} else {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stmt.executeQuery(selectSQL);
				ResultSetMetaData rsmd = rs.getMetaData();
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					oratb.Columns.Add(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
				}
				while (rs.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < size; i++) {
						Object val = rs.getObject(i + 1);
						dr.setValue(i, val);// DataRow一欄一欄填入資料
						// dr.setDataType(i, Para.getDAType(val));
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			}
			return oratb;

		} catch (Exception ex) {
			String msg = "@运行查询在(RunSQLReturnTable_200705_Ora with paras)出错 sql=" + selectSQL + " @异常信息："
					+ ex.getMessage();
			msg += "@Para Num= " + paras.size();
			for (Para pa : paras) {
				msg += "@" + pa.ParaName + "=" + pa.val;
			}
			Log.DebugWriteError(msg);
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static DataTable RunSQLReturnTable_200705_SQL(String selectSQL) throws Exception {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBAccess.getGetAppCenterDBConn();
			/*
			 * warning SqlConnection conn = DBAccess.getGetAppCenterDBConn();
			 * SqlDataAdapter ada = new SqlDataAdapter(selectSQL, conn);
			 * ada.SelectCommand.setCommandType(CommandType.getText());
			 * DataTable oratb = new DataTable("otb"); ada.Fill(oratb);
			 * ada.dispose(); return oratb;
			 */

			DataTable oratb = new DataTable("otb");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(selectSQL);
			ResultSetMetaData rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				// logger.debug(rsmd.getColumnType(i + 1));
				oratb.Columns.Add(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
			}
			while (rs.next()) {
				DataRow dr = oratb.NewRow();// 產生一列DataRow
				for (int i = 0; i < size; i++) {
					Object val = rs.getObject(i + 1);
					dr.setValue(i, val);// DataRow一欄一欄填入資料
					// dr.setDataType(i, Para.getDAType(val));
				}
				oratb.Rows.add(dr);// DataTable加入此DataRow
			}
			return oratb;
		} catch (Exception ex) {
			String msgErr = ex.getMessage();
			String msg = "@运行查询在(RunSQLReturnTable_200705_SQL)出错 sql=" + selectSQL + " @异常信息：" + msgErr;
			Log.DebugWriteError(msg);
			throw new Exception(msg);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static DataTable RunSQLReturnTable_200705_SQL_UL(String sql, Paras paras) {
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		NamedParameterStatement pstmt = null;
		try {
			conn = DBAccess.getGetAppCenterDBConn();
			/*
			 * warning SqlConnection conn = new
			 * SqlConnection(SystemConfig.getAppCenterDSN()); if (conn.State !=
			 * ConnectionState.Open) { conn.Open(); }
			 * 
			 * SqlDataAdapter ada = new SqlDataAdapter(sql, conn);
			 * ada.SelectCommand.setCommandType(CommandType.getText());
			 * 
			 * // 加入参数 for (Para para : paras) { SqlParameter myParameter = new
			 * SqlParameter(para.ParaName, para.val); myParameter.Size =
			 * para.Size; ada.SelectCommand.getParameters().Add(myParameter);
			 * }DataTable oratb = new DataTable("otb"); ada.Fill(oratb);
			 * ada.dispose(); conn.Close(); return oratb;
			 */
			DataTable oratb = new DataTable("otb");
			if (null != paras && paras.size() > 0) {
				pstmt = new NamedParameterStatement(conn, sql);
				PrepareCommand(pstmt, paras);
				rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					oratb.Columns.Add_UL(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
				}
				while (rs.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < size; i++) {
						Object val = rs.getObject(i + 1);
						dr.setValue(i, val);// DataRow一欄一欄填入資料
						// dr.setDataType(i, Para.getDAType(val));
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			} else {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stmt.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int size = rsmd.getColumnCount();
				for (int i = 0; i < size; i++) {
					oratb.Columns.Add_UL(rsmd.getColumnName(i + 1), Para.getDAType(rsmd.getColumnType(i + 1)));
				}
				while (rs.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < size; i++) {
						Object val = rs.getObject(i + 1);
						dr.setValue(i, val);// DataRow一欄一欄填入資料
						// dr.setDataType(i, Para.getDAType(val));
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			}
			return oratb;
		} catch (Exception ex) {
			String msg = "@运行查询在(RunSQLReturnTable_200705_SQL with paras)出错 sql=" + sql + " @异常信息：" + ex.getMessage();
			msg += "@Para Num= " + paras.size();
			for (Para pa : paras) {
				msg += "@" + pa.ParaName + "=" + pa.val;
			}
			Log.DebugWriteError(msg);
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static DataTable RunSQLReturnTable_200705_SQL(String sql, Paras paras) {
		try {
			JdbcTemplate jdbcTemplate = Springfactory.getBean("jdbcTemplate");
			DataTable oratb = new DataTable("otb");
			if (null != paras && paras.size() > 0) {

				Map<String, Object> ps = parasToMap(paras);
				NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
				namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				SqlParameterSource paramSource = new MapSqlParameterSource(ps);

				SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, paramSource);
				SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
				int columnCount = sqlRsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					Map<String, String> fieldMap = new HashMap<String, String>();
					oratb.Columns.Add(sqlRsmd.getColumnName(i));
					// dr.setDataType(i,
					// Para.getDAType(String.valueOf(sqlRsmd.getColumnType(i))));
				}

				while (sqlRowSet.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < sqlRowSet.getRow(); i++) {
						Object val = sqlRowSet.getObject(i + 1);
						if (dr.columns.get(i).getDataType().toString().toLowerCase().contains("int")) {
							if (val == null) {
								dr.setValue(i, 0);
							} else {
								dr.setValue(i, val);// DataRow一欄一欄填入資料
							}
						} else {
							dr.setValue(i, val);
						}
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			} else {

				NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
				namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

				SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<String, Object>());
				SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
				int columnCount = sqlRsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					Map<String, String> fieldMap = new HashMap<String, String>();
					oratb.Columns.Add(sqlRsmd.getColumnName(i));
					Para.getDAType(String.valueOf(sqlRsmd.getColumnType(i)));
				}

				while (sqlRowSet.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < sqlRowSet.getRow(); i++) {
						Object val = sqlRowSet.getObject(i + 1);
						if (dr.columns.get(i).getDataType().toString().toLowerCase().contains("int")) {
							if (val == null) {
								dr.setValue(i, 0);
							} else {
								dr.setValue(i, val);// DataRow一欄一欄填入資料
							}
						} else if (dr.columns.get(i).getDataType().toString().toLowerCase().contains("float")
								|| dr.columns.get(i).getDataType().toString().toLowerCase().contains("double")) {
							if (val == null) {
								dr.setValue(i, 0);
							} else {
								dr.setValue(i, val);// DataRow一欄一欄填入資料
							}
						} else {
							dr.setValue(i, val);
						}
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			}
			return oratb;
		} catch (Exception ex) {
			String msg = "@运行查询在(RunSQLReturnTable_200705_SQL with paras)出错 sql=" + sql + " @异常信息：" + ex.getMessage();
			msg += "@Para Num= " + paras.size();
			for (Para pa : paras) {
				msg += "@" + pa.ParaName + "=" + pa.val;
			}
			Log.DebugWriteError(msg);
			return null;
		} finally {

		}
	}

	/**
	 * RunSQLReturnTable_200705_SQL
	 * 
	 * @param selectSQL
	 *            要执行的sql
	 * @return 返回table
	 * @throws Exception
	 */
	private static DataTable RunSQLReturnTable_200705_MySQL(String selectSQL) throws Exception {
		return RunSQLReturnTable_200705_MySQL(selectSQL, new Paras());
	}

	private static DataTable RunSQLReturnTable_200705_MySQL_UL(String sql, Paras paras) {

		logger.debug("流程SQL：" + sql);
		try {
			JdbcTemplate jdbcTemplate = Springfactory.getBean("jdbcTemplate");
			DataTable oratb = new DataTable("otb");
			if (null != paras && paras.size() > 0) {

				Map<String, Object> ps = parasToMap(paras);
				NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
				namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				SqlParameterSource paramSource = new MapSqlParameterSource(ps);

				SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, paramSource);
				SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
				int columnCount = sqlRsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					Map<String, String> fieldMap = new HashMap<String, String>();
					oratb.Columns.Add(sqlRsmd.getColumnName(i), Para.getDAType(sqlRsmd.getColumnType(i)));
				}

				while (sqlRowSet.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < sqlRowSet.getRow(); i++) {
						Object val = sqlRowSet.getObject(i + 1);
						if (dr.columns.get(i).getDataType().toString().toLowerCase().contains("int")) {
							if (val == null) {
								dr.setValue(i, 0);
							} else {
								dr.setValue(i, val);// DataRow一欄一欄填入資料
							}
						} else {
							dr.setValue(i, val);
						}
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			} else {

				NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
				namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

				SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, new HashMap<String, Object>());
				SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();

				int columnCount = sqlRsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					Map<String, String> fieldMap = new HashMap<String, String>();
					oratb.Columns.Add(sqlRsmd.getColumnName(i), Para.getDAType(sqlRsmd.getColumnType(i)));
				}

				while (sqlRowSet.next()) {
					DataRow dr = oratb.NewRow();// 產生一列DataRow
					for (int i = 0; i < columnCount; i++) {
						Object val = sqlRowSet.getObject(i + 1);
						if (dr.columns.get(i).getDataType().toString().toLowerCase().contains("int")) {
							if (val == null) {
								dr.setValue(i, 0);
							} else {
								dr.setValue(i, val);// DataRow一欄一欄填入資料
							}
						} else if (dr.columns.get(i).getDataType().toString().toLowerCase().contains("float")
								|| dr.columns.get(i).getDataType().toString().toLowerCase().contains("double")) {
							if (val == null) {
								dr.setValue(i, 0);
							} else {
								dr.setValue(i, val);// DataRow一欄一欄填入資料
							}
						} else {
							dr.setValue(i, val);
						}
					}
					oratb.Rows.add(dr);// DataTable加入此DataRow
				}
			}

			return oratb;
		} catch (Exception ex) {
			String msg = "@运行查询在(RunSQLReturnTable_200705_MySQL with paras)出错 sql=" + sql + " @异常信息：" + ex.getMessage();
			msg += "@Para Num= " + paras.size();
			for (Para pa : paras) {
				msg += "@" + pa.ParaName + "=" + pa.val;
			}
			// Log.DebugWriteError(msg);
			return null;
		} finally {

		}
	}

	private static DataTable genTableByMap(List<Map<String, Object>> result) {
		DataRow dr = null;
		DataTable oratb = new DataTable("otb");
		for (int i = 0; i < result.size(); i++) {
			dr = oratb.NewRow();// 產生一列DataRow
			Map<String, Object> val = result.get(i);
			for (String key : val.keySet()) {
				if (i == 0) {
					oratb.Columns.Add(key, Para.getDAType(val.get(key)));
				}
				dr.setValue(key, val.get(key));// DataRow一欄一欄填入資料
			}
			oratb.Rows.add(dr);// DataTable加入此DataRow
		}
		return oratb;
	}

	/**
	 * RunSQLReturnTable_200705_SQL
	 * 
	 * @param selectSQL
	 *            要执行的sql
	 * @return 返回table
	 * @throws Exception
	 */
	private static DataTable RunSQLReturnTable_200705_MySQL(String sql, Paras paras) {

		logger.debug("流程SQL：" + sql);
		JdbcTemplate jdbcTemplate = null;
		List<Map<String, Object>> result = null;
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
		try {
			jdbcTemplate = Springfactory.getBean("jdbcTemplate");
			if (null != paras && paras.size() > 0) {
				Map<String, Object> ps = parasToMap(paras);
				namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
				result = namedParameterJdbcTemplate.queryForList(sql, ps);
				return genTableByMap(result);
			} else {
				result = jdbcTemplate.queryForList(sql);

				return genTableByMap(result);
			}
		} catch (Exception ex) {
			String msg = "@运行查询在(RunSQLReturnTable_200705_MySQL with paras)出错 sql=" + sql + " @异常信息：" + ex.getMessage();
			msg += "@Para Num= " + paras.size();
			for (Para pa : paras) {
				msg += "@" + pa.ParaName + "=" + pa.val;
			}
			// Log.DebugWriteError(msg);
			return null;
		} finally {
			namedParameterJdbcTemplate = null;
		}
	}

	public static DataTable RunSQLReturnTable(Paras ps) {
		return RunSQLReturnTable(ps.SQL, ps);
	}

	public static int RunSQLReturnTableCount = 0;

	/**
	 * 传递一个select 语句返回一个查询结果集合。
	 * 
	 * @param sql
	 *            select sql
	 * @return 查询结果集合DataTable
	 * @throws Exception
	 */
	public static DataTable RunSQLReturnTable(String sql) {
		Paras ps = new Paras();
		return RunSQLReturnTable(sql, ps);
	}

	/**
	 * 执行sql 返回数据区分大小写
	 * 
	 * @param sql
	 * @return
	 */
	public static DataTable RunSQLReturnTable_UL(String sql) {
		Paras ps = new Paras();
		return RunSQLReturnTable_UL(sql, ps);

	}

	/**
	 * 根据传入的键和值的方式查询返回DataTable
	 * 
	 * @param sql
	 *            语句
	 * @param key1
	 *            键1
	 * @param v1
	 *            值1
	 * @param key2
	 *            键2
	 * @param v2
	 *            值2
	 * @return
	 * @throws Exception
	 */
	public static DataTable RunSQLReturnTable(String sql, String key1, Object v1, String key2, Object v2)
			throws Exception {
		Paras ens = new Paras();
		ens.Add(key1, v1);
		ens.Add(key2, v2);
		return RunSQLReturnTable(sql, ens);
	}

	/**
	 * 根据传入的键和值的方式查询返回DataTable
	 * 
	 * @param sql
	 *            语句
	 * @param key
	 *            键
	 * @param val
	 *            值
	 * @return
	 * @throws Exception
	 */
	public static DataTable RunSQLReturnTable(String sql, String key, Object val) {
		Paras ens = new Paras();
		ens.Add(key, val);
		return RunSQLReturnTable(sql, ens);
	}

	private static boolean lockRunSQLReTable = false;

	/**
	 * 根据传入的键和值的方式查询返回DataSet集合 sqls语句用@符号区分
	 * 
	 * @param sqls
	 *            语句
	 * @return
	 * @throws Exception
	 */
	public static DataSet RunSQLReturnDataSet(String sqls) {
		DataSet ds = new DataSet();
		String[] str = sqls.split(";");
		for (String s : str) {
			DataTable dt = DBAccess.RunSQLReturnTable(s);
			ds.Tables.add(dt);
		}
		return ds;
	}

	public static DataSet RunSQLReturnDataSet_UL(String sqls) {
		DataSet ds = new DataSet();
		String[] str = sqls.split(";");
		for (String s : str) {
			DataTable dt = DBAccess.RunSQLReturnTable_UL(s);
			ds.Tables.add(dt);
		}
		return ds;
	}

	public static DataTable RunSQLReturnTable_UL(String sql, Paras paras) {
		if (StringHelper.isNullOrEmpty(sql)) {
			throw new RuntimeException("要执行的 sql =null ");
		}
		try {
			DataTable dt = null;
			switch (getAppCenterDBType()) {
			case MSSQL:
				dt = RunSQLReturnTable_200705_SQL_UL(sql, paras);
				break;
			case Oracle:
				dt = RunSQLReturnTable_200705_Ora_UL(sql, paras);
				break;
			case MySQL:
				dt = RunSQLReturnTable_200705_MySQL_UL(sql, paras);
				break;
			default:
				throw new RuntimeException("@发现未知的数据库连接类型！");
			}
			return dt;
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLineError(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static DataTable RunSQLReturnTable(String sql, Paras paras) {
		if (StringHelper.isNullOrEmpty(sql)) {
			throw new RuntimeException("要执行的 sql =null ");
		}
		try {
			DataTable dt = null;
			switch (getAppCenterDBType()) {
			case MSSQL:
				dt = RunSQLReturnTable_200705_SQL(sql, paras);
				break;
			case Oracle:
				dt = RunSQLReturnTable_200705_Ora(sql, paras);
				break;
			case MySQL:
				dt = RunSQLReturnTable_200705_MySQL(sql, paras);
				break;
			default:
				throw new RuntimeException("@发现未知的数据库连接类型！");
			}
			return dt;
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLineError(ex.getMessage());
			throw ex;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static float RunSQLReturnValFloat(Paras ps) throws Exception {
		return RunSQLReturnValFloat(ps.SQL, ps, 0);
	}

	public static float RunSQLReturnValFloat(String sql, Paras ps, float val) throws Exception {
		ps.SQL = sql;
		Object obj = DBAccess.RunSQLReturnVal(ps);

		try {
			if (obj == null || obj.toString().equals("")) {
				return val;
			} else {
				return Float.parseFloat(obj.toString());
			}
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage() + sql + " @OBJ=" + obj);
		}
	}

	/**
	 * 运行sql返回float
	 * 
	 * @param sql
	 *            要执行的sql,返回一行一列.
	 * @param isNullAsVal
	 *            如果是空值就返回的默认值
	 * @return float的返回值
	 * @throws Exception
	 */
	public static float RunSQLReturnValFloat(String sql, float isNullAsVal) throws Exception {
		return RunSQLReturnValFloat(sql, new Paras(), isNullAsVal);
	}

	/**
	 * sdfsd
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static float RunSQLReturnValFloat(String sql) throws Exception {
		try {
			return Float.parseFloat(DBAccess.RunSQLReturnVal(sql).toString());
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage() + sql);
		}
	}

	public static int RunSQLReturnValInt(Paras ps, int IsNullReturnVal) {
		return RunSQLReturnValInt(ps.SQL, ps, IsNullReturnVal);
	}

	/**
	 * sdfsd
	 * 
	 * @param sql
	 * @param IsNullReturnVal
	 * @return
	 * @throws Exception
	 */
	public static int RunSQLReturnValInt(String sql, int IsNullReturnVal) {
		Object obj = "";
		obj = DBAccess.RunSQLReturnVal(sql);

		if (obj == null || obj.toString().equals("")) {
			return IsNullReturnVal;
		} else {
			if (obj.toString().indexOf(".") != -1) {
				return Integer.parseInt(obj.toString().substring(0, obj.toString().indexOf(".")));
			} else {
				return Integer.parseInt(obj.toString());
			}

		}
	}

	public static int RunSQLReturnValInt(String sql, int IsNullReturnVal, Paras paras) throws Exception {
		Object obj = "";

		obj = DBAccess.RunSQLReturnVal(sql, paras);
		if (obj == null || obj.toString().equals("")) {
			return IsNullReturnVal;
		} else {
			return Integer.parseInt(obj.toString());
		}
	}

	public static BigDecimal RunSQLReturnValDecimal(String sql, BigDecimal IsNullReturnVal, int blws) {
		Paras ps = new Paras();
		ps.SQL = sql;
		return RunSQLReturnValDecimal(ps, IsNullReturnVal, blws);
	}

	public static BigDecimal RunSQLReturnValDecimal(Paras ps, BigDecimal IsNullReturnVal, int blws) {
		try {
			Object obj = DBAccess.RunSQLReturnVal(ps);
			if (obj == null || obj.toString().equals("")) {
				return IsNullReturnVal;
			} else {
				BigDecimal d = (BigDecimal) obj;

				return d.setScale(blws, BigDecimal.ROUND_HALF_UP);
			}
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage() + ps.SQL);
		}
	}

	public static int RunSQLReturnCOUNT(String sql) {
		return RunSQLReturnTable(sql).Rows.size();

	}

	public static int RunSQLReturnValInt(Paras ps) {
		String str = DBAccess.RunSQLReturnString(ps.SQL, ps);
		if (str.contains(".")) {
			str = str.substring(0, str.indexOf("."));
		}
		try {
			return Integer.parseInt(str);
		} catch (RuntimeException ex) {
			throw new RuntimeException("@" + ps.SQL + "   Val=" + str + ex.getMessage());
		}
	}

	public static int RunSQLReturnValInt(String sql) {

		logger.debug("流程SQL：" + sql);
		Object obj = DBAccess.RunSQLReturnVal(sql);

		if (obj == null) {
			throw new RuntimeException("@没有获取您要查询的数据,请检查SQL:" + sql + " @关于查询出来的详细信息已经记录日志文件，请处理。");
		}
		String s = obj.toString();
		if (s.contains(".")) {
			s = s.substring(0, s.indexOf("."));
		}
		return Integer.parseInt(s);
	}

	public static int RunSQLReturnValInt(String sql, Paras paras) {
		return Integer.parseInt(DBAccess.RunSQLReturnVal(sql, paras).toString());
	}

	public static int RunSQLReturnValInt(String sql, Paras paras, int isNullAsVal) {
		try {
			return Integer.parseInt(DBAccess.RunSQLReturnVal(sql, paras).toString());
		} catch (java.lang.Exception e) {
			return isNullAsVal;
		}
	}

	public static String RunSQLReturnString(String sql, Paras ps) {
		if (ps == null) {
			ps = new Paras();
		}
		Object obj = DBAccess.RunSQLReturnVal(sql, ps);

		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 执行查询返回结果,如果为dbNull 返回 null.
	 * 
	 * @param sql
	 *            will run sql.
	 * @return ,如果为dbNull 返回 null.
	 * @throws Exception
	 */
	public static String RunSQLReturnString(String sql) {
		return RunSQLReturnString(sql, new Paras());

	}

	public static String RunSQLReturnStringIsNull(Paras ps, String isNullAsVal) throws Exception {
		String v = RunSQLReturnString(ps);
		if (v == null) {
			return isNullAsVal;
		} else {
			return v;
		}
	}

	/**
	 * 运行sql返回一个值
	 * 
	 * @param sql
	 * @param isNullAsVal
	 * @return
	 * @throws Exception
	 */
	public static String RunSQLReturnStringIsNull(String sql, String isNullAsVal) {
		String s = RunSQLReturnString(sql, new Paras());
		if (s == null) {
			return isNullAsVal;
		}
		return s;
	}

	public static String RunSQLReturnString(Paras ps) {
		return RunSQLReturnString(ps.SQL, ps);
	}

	public static Object RunSQLReturnVal(String sql, String pkey, Object val) throws Exception {
		Paras ps = new Paras();
		ps.Add(pkey, val);
		return RunSQLReturnVal(sql, ps);
	}

	public static Object RunSQLReturnVal(String sql, Paras paras) {
		RunSQLReturnTableCount++;
		DataTable dt = null;
		try {
			switch (SystemConfig.getAppCenterDBType()) {
			case Oracle:
				dt = DBAccess.RunSQLReturnTable_200705_Ora(sql, paras);
				break;
			case MSSQL:
				dt = DBAccess.RunSQLReturnTable_200705_SQL(sql, paras);
				break;
			case MySQL:
				dt = DBAccess.RunSQLReturnTable_200705_MySQL(sql, paras);
				break;
			default:
				throw new RuntimeException("@没有判断的数据库类型");
			}
		} catch (Exception e) {
			return null;
		}

		if (dt.Rows.size() == 0) {
			return null;
		}
		return dt.Rows.get(0).getValue(0);
	}

	public static Object RunSQLReturnVal(Paras ps) {
		return RunSQLReturnVal(ps.SQL, ps);
	}

	public static Object RunSQLReturnVal(String sql) {
		RunSQLReturnTableCount++;
		DataTable dt = null;
		// log.debug(SystemConfig.getAppCenterDBType());
		switch (SystemConfig.getAppCenterDBType()) {
		case Oracle:
			dt = DBAccess.RunSQLReturnTable_200705_Ora(sql, new Paras());
			break;
		case MSSQL:
			dt = DBAccess.RunSQLReturnTable_200705_SQL(sql, new Paras());
			break;
		case MySQL:
			dt = DBAccess.RunSQLReturnTable_200705_MySQL(sql, new Paras());
			break;
		default:
			throw new RuntimeException("@没有判断的数据库类型");
		}
		if (dt.Rows.size() == 0) {

			String cols = "";
			for (DataColumn dc : dt.Columns) {
				cols += " , " + dc.ColumnName;
			}

			BP.DA.Log.DebugWriteInfo("@SQL=" + sql + " . 列=" + cols);
			return null;
		}
		return dt.Rows.get(0).getValue(0);
	}

	/**
	 * 检查是不是存在
	 * 
	 * @param sql
	 *            sql
	 * @return 检查是不是存在
	 * @throws Exception
	 */
	public static boolean IsExits(String sql) {
		if (RunSQLReturnVal(sql) == null) {
			return false;
		}
		return true;
	}

	public static boolean IsExits(String sql, Paras ps) {
		if (RunSQLReturnVal(sql, ps) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否存在主键pk .
	 * 
	 * @param tab
	 *            物理表
	 * @return 是否存在
	 * @throws Exception
	 */
	public static boolean IsExitsTabPK(String tab) {
		BP.DA.Paras ps = new Paras();
		ps.Add("Tab", tab);
		String sql = "";
		switch (getAppCenterDBType()) {
		case MSSQL:
			sql = "SELECT column_name, table_name,CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE table_name =:Tab ";
			break;
		case Oracle:
			sql = "SELECT constraint_name, constraint_type,search_condition, r_constraint_name  from user_constraints WHERE table_name = upper(:Tab) AND constraint_type = 'P'";
			break;
		case MySQL:
			sql = "SELECT column_name, table_name, CONSTRAINT_NAME from INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE table_name =:Tab and table_schema='"
					+ SystemConfig.getAppCenterDBDatabase() + "' ";
			break;
		default:
			throw new RuntimeException("数据库连接配置失败！ ");
		}
		DataTable dt = DBAccess.RunSQLReturnTable(sql, ps);
		if (dt.Rows.size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断系统中是否存在对象.
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public static boolean IsExitsObject(String obj) {
		Paras ps = new Paras();
		ps.Add("obj", obj);

		switch (getAppCenterDBType()) {
		case Oracle:
			if (obj.indexOf(".") != -1) {
				obj = obj.split("[.]", -1)[1];
			}
			return IsExits("select tname from tab WHERE  tname = upper(:obj) ", ps);
		case MSSQL:
			return IsExits("SELECT name  FROM sysobjects  WHERE  name = '" + obj + "'");
		case MySQL:
			if (obj.indexOf(".") != -1) {
				obj = obj.split("[.]", -1)[1];
			}
			return IsExits("SELECT table_name, table_type FROM information_schema.tables  WHERE table_name = '" + obj
					+ "' AND   TABLE_SCHEMA='" + BP.Sys.SystemConfig.getAppCenterDBDatabase() + "' ");
		default:
			throw new RuntimeException("没有识别的数据库编号");
		}
	}

	/**
	 * 表中是否存在指定的列
	 * 
	 * @param table
	 *            表名
	 * @param col
	 *            列名
	 * @return 是否存在
	 * @throws Exception
	 */
	public static boolean IsExitsTableCol(String table, String col) throws Exception {
		Paras ps = new Paras();
		ps.Add("tab", table);
		ps.Add("col", col);

		int i = 0;
		switch (DBAccess.getAppCenterDBType()) {
		// case DBType.Access:
		// return false;
		// break;
		case MSSQL:
			i = DBAccess.RunSQLReturnValInt("SELECT  COUNT(*) FROM information_schema.COLUMNS  WHERE TABLE_NAME='"
					+ table + "' AND COLUMN_NAME='" + col + "'", 0);
			break;
		case MySQL:
			String sql = "select count(*) FROM information_schema.columns WHERE TABLE_SCHEMA='"
					+ BP.Sys.SystemConfig.getAppCenterDBDatabase() + "' AND table_name ='" + table
					+ "' and column_Name='" + col + "'";
			i = DBAccess.RunSQLReturnValInt(sql);
			break;
		case Oracle:
			if (table.indexOf(".") != -1) {
				table = table.split("[.]", -1)[1];
			}
			i = DBAccess.RunSQLReturnValInt(
					"SELECT COUNT(*) from user_tab_columns  WHERE table_name= upper(:tab) AND column_name= upper(:col) ",
					ps);
			break;
		default:
			throw new RuntimeException("error");
		}
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static void PrepareCommand(NamedParameterStatement ps, Paras params) {
		if (null == params || params.size() <= 0) {
			return;
		}
		try {
			int i = 0;
			for (Para para : params) {
				if (para.DAType == String.class) {
					try {
						if (para.val != null && !para.val.equals("")) {
							ps.setString(para.ParaName, String.valueOf(para.val));
						} else {
							ps.setString(para.ParaName, null);
						}
					} catch (Exception e) {
						ps.setString(para.ParaName, null);
					}
				} else if (para.DAType == Long.class) {
					ps.setLong(para.ParaName, Long.parseLong(String.valueOf(para.val)));
				} else if (para.DAType == Integer.class) {
					if (para.val == "")
						para.val = "0";
					ps.setInt(para.ParaName, Integer.parseInt(String.valueOf(para.val)));
				} else if (para.DAType == Float.class) {
					ps.setFloat(para.ParaName, Float.parseFloat(String.valueOf(para.val)));
				} else if (para.DAType == Double.class) {
					ps.setDouble(para.ParaName, Double.parseDouble(String.valueOf(para.val)));
				} else if (para.DAType == BigDecimal.class) {
					ps.setBigDecimal(para.ParaName, new BigDecimal(String.valueOf(para.val)));
				} else if (para.DAType == Date.class) {
					Date date = (Date) para.val;
					ps.setDate(para.ParaName, new java.sql.Date(date.getTime()));
				} else if (para.DAType == Boolean.class) {
					ps.setBoolean(para.ParaName, Boolean.parseBoolean(String.valueOf(para.val)));
				}
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 开启事物
	public static void DoTransactionBegin() {
		// try {
		// Connection conn = null;
		// switch (SystemConfig.getAppCenterDBType()) {
		// case MSSQL:
		// conn = DBAccess.getGetAppCenterDBConn();
		// conn.setAutoCommit(true);
		// break;
		// case Oracle:
		// conn = DBAccess.getGetAppCenterDBConn();
		// conn.setAutoCommit(true);
		// break;
		// case MySQL:
		// conn = DBAccess.getGetAppCenterDBConn()();
		// conn.setAutoCommit(true);
		// break;
		// default:
		// break;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	// 提交事物
	public static void DoTransactionCommit() {
		// try {
		// Connection conn = null;
		// for (DBType g : DBType.values()) {
		// switch (g) {
		// case MSSQL:
		// conn = DBAccess.getGetAppCenterDBConn();
		// conn.commit();
		// break;
		// case Oracle:
		// conn = DBAccess.getGetAppCenterDBConn();
		// conn.commit();
		// break;
		// case MySQL:
		// conn = DBAccess.getGetAppCenterDBConn()();
		// conn.commit();
		// break;
		// default:
		// break;
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	// 回滚事物
	public static void DoTransactionRollback() {
		// try {
		// Connection conn = null;
		// //for (DBType g : DBType.values()) {
		// switch (SystemConfig.getAppCenterDBType()) {
		// case MSSQL:
		// conn = DBAccess.getGetAppCenterDBConn();
		// conn.rollback();
		// break;
		// case Oracle:
		// conn = DBAccess.getGetAppCenterDBConn();
		// conn.rollback();
		// break;
		// case MySQL:
		// conn = DBAccess.getGetAppCenterDBConn()();
		// conn.rollback();
		// break;
		// default:
		// break;
		// }
		// //}
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) throws Exception {
		// Paras ps = new Paras();
		// // Para p = new Para("visit_date",Date.class,
		// // DataType.stringToDate("2014-09-05"));
		// // ps.Add(p);
		// DataTable table = RunSQLReturnTable_200705_MySQL(
		// "select * from media_control where ip = '192.168.0.2'", ps);
		// logger.debug(Json.ToJson(table));

		DataSet ds = new DataSet();
		ds.readXmls("D:/android workspace/jflow/jflow-web/src/main/webapp/DataUser/XML/TempleteSheetOfStartNode.xml");
	}
}
