package BP.Sys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import BP.DA.DataRow;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.Tools.StringHelper;

/**
 * 系统配置
 * 
 * @author thinkpad
 * 
 */
public class SystemConfig {
	/**
	 * 读取配置文件
	 * 
	 * @param cfgFile
	 * @throws Exception
	 */
	public static void ReadConfigFile(InputStream fis) throws Exception {

		if (SystemConfig.getCS_AppSettings() != null) {
			SystemConfig.getCS_AppSettings().clear();
		}

		Properties properties = new Properties();
		try {
			properties.load(fis);
			for (Object s : properties.keySet()) {
				getCS_AppSettings().put(s.toString(),
						new String(String.valueOf(properties.get(s)).getBytes("ISO8859-1"), "UTF-8"));
			}
			fis.close();
		} catch (IOException e) {
			throw new Exception("读取配置文件失败", e);
		}
	}

	/**
	 * 获取xml中的配置信息 GroupTitle, ShowTextLen, DefaultSelectedAttrs, TimeSpan
	 * 
	 * @param key
	 * @param ensName
	 * @return
	 */
	public static String GetConfigXmlEns(String key, String ensName) {
		try {
			Object tempVar = BP.DA.Cash.GetObj("TConfigEns", BP.DA.Depositary.Application);
			DataTable dt = (DataTable) ((tempVar instanceof DataTable) ? tempVar : null);
			if (dt == null) {
				DataSet ds = new DataSet("dss");
				ds.readXml(
						BP.Sys.SystemConfig.getPathOfXML() + File.separator + "Ens" + File.separator + "ConfigEns.xml");
				dt = ds.Tables.get(0);
				BP.DA.Cash.AddObj("TConfigEns", BP.DA.Depositary.Application, dt);
			}

			for (DataRow dr : dt.Rows) {
				if (dr.getValue("Key").equals(key) && dr.getValue("For").equals(ensName)) {
					return dr.getValue("Val") == null ? "" : dr.getValue("Val").toString();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			;
		}
		return null;
	}

	/**
	 * 关于开发商的信息
	 * 
	 * @return
	 */
	public static String getVer() {
		try {
			return getAppSettings().get("Ver").toString();
		} catch (java.lang.Exception e) {
			return "1.0.0";
		}
	}

	public static String getTouchWay() {
		try {
			return getAppSettings().get("TouchWay").toString();
		} catch (java.lang.Exception e) {
			return SystemConfig.getCustomerTel() + " 地址:" + SystemConfig.getCustomerAddr();
		}
	}

	public static String getCopyRight() {
		try {
			return getAppSettings().get("CopyRight").toString();
		} catch (java.lang.Exception e) {
			return "版权所有@" + getCustomerName();
		}
	}

	public static String getCompanyID() {
		String s = getAppSettings().get("CompanyID").toString();
		if (StringHelper.isNullOrEmpty(s)) {
			return "CCFlow";
		}
		return s;
	}

	/**
	 * 开发商全称
	 * 
	 * @return
	 */
	public static String getDeveloperName() {
		return getAppSettings().get("DeveloperName").toString();
	}

	/**
	 * 开发商简称
	 * 
	 * @return
	 */
	public static String getDeveloperShortName() {
		return getAppSettings().get("DeveloperShortName").toString();
	}

	/**
	 * 开发商电话
	 * 
	 * @return
	 */
	public static String getDeveloperTel() {
		return getAppSettings().get("DeveloperTel").toString();
	}

	/**
	 * 开发商的地址
	 * 
	 * @return
	 */
	public static String getDeveloperAddr() {
		return (String) getAppSettings().get("DeveloperAddr");

	}

	/**
	 * 系统语言 对多语言的系统有效。
	 * 
	 * @return
	 */
	public static String getSysLanguage() {
		return "CH";
	}

	/**
	 * 封装了AppSettings, 负责存放 配置的基本信息
	 */
	private static Hashtable<String, Object> _CS_AppSettings = null;

	public static Hashtable<String, Object> getCS_AppSettings() {

		if (_CS_AppSettings == null) {
			try {
				_CS_AppSettings = new java.util.Hashtable<String, Object>();
				Map<String, String> properties = CustomPropertyConfigurer.getProperties();
				for (String s : properties.keySet()) {
					_CS_AppSettings.put(s,
							new String(String.valueOf(properties.get(s)).getBytes("ISO8859-1"), "UTF-8"));
				}
			} catch (IOException e) {
				throw new RuntimeException("读取配置文件失败", e);
			}
		}
		return _CS_AppSettings;
	}

	/**
	 * 封装了AppSettings
	 * 
	 * @return
	 */
	public static Hashtable<String, Object> getAppSettings() {
		return getCS_AppSettings();
	}

	static {
		CS_DBConnctionDic = new Hashtable<String, Object>();
	}

	/**
	 * 应用程序路径
	 * 
	 * @return
	 */
	public static String getPhysicalApplicationPath() {
		return "D:" + File.separator + "JJFlow" + File.separator + "trunk" + File.separator + "JJFlow" + File.separator;
	}

	/**
	 * 文件放置的路径
	 * 
	 * @return
	 */
	public static String getPathOfUsersFiles() {
		return "/Data/Files/";
	}

	/**
	 * 临时文件路径
	 * 
	 * @return
	 */
	public static String getPathOfTemp() {
		return getPathOfDataUser() + "Temp";
	}

	public static String getPathOfWorkDir() {
		return "D:" + File.separator + "JJFlow" + File.separator + "trunk" + File.separator;
	}

	public static String getPathOfFDB() {
		return getPathOfWebApp() + File.separator + "DataUser" + File.separator + "FDB" + File.separator;
	}

	/**
	 * 数据文件
	 * 
	 * @return
	 */
	public static String getPathOfData() {
		return getPathOfWebApp() + SystemConfig.getAppSettings().get("DataDirPath").toString() + File.separator + "Data"
				+ File.separator;
	}

	public static String getPathOfDataUser() {
		return getPathOfWebApp() + SystemConfig.getAppSettings().get("DataUserDirPath").toString() + "DataUser"
				+ File.separator;
	}

	/**
	 * XmlFilePath
	 * 
	 * @return
	 */
	public static String getPathOfXML() {
		return getPathOfWebApp() + SystemConfig.getAppSettings().get("DataDirPath").toString() + File.separator + "Data"
				+ File.separator + "XML" + File.separator;
	}

	public static String getPathOfAppUpdate() {
		return getPathOfWebApp() + SystemConfig.getAppSettings().get("DataDirPath").toString() + File.separator + "Data"
				+ File.separator + "AppUpdate" + File.separator;
	}

	public static String getPathOfCyclostyleFile() {
		return getPathOfWebApp() + File.separator + "DataUser" + File.separator + "CyclostyleFile" + File.separator;
	}

	/**
	 * 应用程序名称
	 * 
	 * @return
	 */
	public static String getAppName() {
		return "JJFlow";
	}

	/**
	 * ccflow物理目录
	 * 
	 * @return
	 */
	public static String getCCFlowAppPath() {
		// if (!StringHelper.isNullOrEmpty(SystemConfig
		// .getAppSettings().get("DataUserDirPath").toString())) {
		// return getPathOfWebApp()
		// + SystemConfig.getAppSettings().get("DataUserDirPath")
		// .toString();
		// }
		if (SystemConfig.getAppSettings().get("DataUserDirPath") != null) {
			return getPathOfWebApp() + SystemConfig.getAppSettings().get("DataUserDirPath").toString();
		}
		return getPathOfWebApp();
	}

	/**
	 * ccflow网站目录
	 * 
	 * @return
	 */
	public static String getCCFlowWebPath() {
		return System.getProperty("file.separator");
	}

	/**
	 * WebApp Path
	 * 
	 * @return
	 */
	public static String getPathOfWebApp() {
		// return "D:\\JJFlow\\trunk\\JJFlow";
		if (SystemConfig.getIsBSsystem()) {
			return Glo.getRequest().getSession().getServletContext().getRealPath("") + File.separator;
		} else {
			return "";
		}
	}

	public static boolean getIsBSsystem() {
		return SystemConfig.GetValByKeyBoolen("IsBSsystem", true);
	}

	public static boolean getIsCSsystem() {
		return !SystemConfig.getIsBSsystem();
	}

	// 统配置信息
	/**
	 * 系统编号
	 * 
	 * @return
	 */
	public static String getSysNo() {
		return getAppSettings().get("SysNo").toString();
	}

	/**
	 * 系统名称
	 * 
	 * @return
	 */
	public static String getSysName() {
		String s = getAppSettings().get("SysName").toString();
		if (s == null) {
			s = "请在web.propertoes中配置SysName名称";
		}
		return s;
	}

	public static String getOrderWay() {
		return getAppSettings().get("OrderWay").toString();
	}

	public static int getPageSize() {
		try {
			return Integer.parseInt(getAppSettings().get("PageSize").toString());
		} catch (java.lang.Exception e) {
			return 99999;
		}
	}

	public static int getMaxDDLNum() {
		try {
			return Integer.parseInt(getAppSettings().get("MaxDDLNum").toString());
		} catch (java.lang.Exception e) {
			return 50;
		}
	}

	public static int getPageSpan() {
		try {
			return Integer.parseInt(getAppSettings().get("PageSpan").toString());
		} catch (java.lang.Exception e) {
			return 20;
		}
	}

	/**
	 * 到的路径.PageOfAfterAuthorizeLogin
	 * 
	 * @return
	 */
	public static String getPageOfAfterAuthorizeLogin() {
		/*
		 * warning return BP.Glo.getHttpContextCurrent().Request.ApplicationPath
		 * + "" + getAppSettings().get("PageOfAfterAuthorizeLogin").toString();
		 */
		return Glo.class.getClass().getResource("/").getPath() + ""
				+ getAppSettings().get("PageOfAfterAuthorizeLogin").toString();
	}

	/**
	 * 丢失session 到的路径
	 * 
	 * @return
	 */
	public static String getPageOfLostSession() {
		/*
		 * warning return BP.Glo.getHttpContextCurrent().Request.ApplicationPath
		 * + "" + getAppSettings().get("PageOfLostSession").toString();
		 */
		return Glo.class.getClass().getResource("/").getPath() + ""
				+ getAppSettings().get("PageOfLostSession").toString();
	}

	/**
	 * 日志路径
	 * 
	 * @return
	 */
	public static String getPathOfLog() {
		return getPathOfWebApp() + File.separator + "DataUser" + File.separator + "Log" + File.separator;
	}

	/**
	 * 系统名称
	 * 
	 * @return
	 */
	public static int getTopNum() {
		try {
			return Integer.parseInt(getAppSettings().get("TopNum").toString());
		} catch (java.lang.Exception e) {
			return 99999;
		}
	}

	/**
	 * 服务电话
	 * 
	 * @return
	 */
	public static String getServiceTel() {
		return getAppSettings().get("ServiceTel").toString();
	}

	/**
	 * 服务E-mail
	 * 
	 * @return
	 */
	public static String getServiceMail() {
		return getAppSettings().get("ServiceMail").toString();
	}

	/**
	 * 第3方软件
	 * 
	 * @return
	 */
	public static String getThirdPartySoftWareKey() {
		return getAppSettings().get("ThirdPartySoftWareKey").toString();
	}

	public static boolean getIsEnableNull() {
		if (getAppSettings().get("IsEnableNull").toString().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否 debug 状态
	 * 
	 * @return
	 */
	public static boolean getIsDebug() {
		if (getAppSettings().get("IsDebug").toString().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean getIsOpenSQLCheck() {
		if (getAppSettings().get("IsOpenSQLCheck").toString().equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是不是多系统工作
	 * 
	 * @return
	 */
	public static boolean getIsMultiSys() {
		if (getAppSettings().get("IsMultiSys").toString().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是不是多线程工作
	 * 
	 * @return
	 */
	public static boolean getIsMultiThread_del() {
		if (getAppSettings().get("IsMultiThread").toString().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是不是多语言版本
	 * 
	 * @return
	 */
	public static boolean getIsMultiLanguageSys() {
		if (getAppSettings().get("IsMultiLanguageSys").toString().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	// 处理临时缓存
	/**
	 * 放在 Temp 中的cash 多少时间失效。0, 表示永久不失效
	 * 
	 * @return
	 */
	private static int getCashFail() {
		try {
			return Integer.parseInt(getAppSettings().get("CashFail").toString());
		} catch (java.lang.Exception e) {
			return 0;
		}
	}

	/**
	 * 当前的 TempCash 是否失效了
	 * 
	 * @return
	 */
	public static boolean getIsTempCashFail() {
		/*
		 * warning if (SystemConfig.getCashFail() == 0){ return false; } if
		 * (_CashFailDateTime == null){ _CashFailDateTime = new Date(); return
		 * true; }else{ TimeSpan ts = new Date() - _CashFailDateTime; if
		 * (ts.Minutes >= SystemConfig.getCashFail()) { _CashFailDateTime = new
		 * Date(); return true; } return false; }
		 */
		return true;
	}

	public static Date _CashFailDateTime = new Date(0);

	// 客户配置信息
	/**
	 * 客户编号
	 * 
	 * @return
	 */
	public static String getCustomerNo() {
		return getAppSettings().get("CustomerNo").toString();
	}

	/**
	 * 客户名称
	 * 
	 * @return
	 */
	public static String getCustomerName() {
		return getAppSettings().get("CustomerName").toString();
	}

	public static String getCustomerURL() {
		return getAppSettings().get("CustomerURL").toString();
	}

	/**
	 * 客户简称
	 * 
	 * @return
	 */
	public static String getCustomerShortName() {
		return getAppSettings().get("CustomerShortName").toString();
	}

	/**
	 * 客户地址
	 * 
	 * @return
	 */
	public static String getCustomerAddr() {
		return getAppSettings().get("CustomerAddr").toString();
	}

	/**
	 * 客户电话
	 * 
	 * @return
	 */
	public static String getCustomerTel() {
		return getAppSettings().get("CustomerTel").toString();
	}

	public static String GetValByKey(String key, String isNullas) {
		Object s = getAppSettings().get(key);
		if (s == null) {
			s = isNullas;
		}
		return s.toString();
	}

	public static boolean GetValByKeyBoolen(String key, boolean isNullas) {
		String s = getAppSettings().get(key).toString();
		if (s == null) {
			return isNullas;
		}
		if (s.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static int GetValByKeyInt(String key, int isNullas) {
		Object s = getAppSettings().get(key);
		if (s == null) {
			return isNullas;
		}
		return Integer.parseInt(s.toString());
	}

	public static String getAppCenterDSN() {
		String dsn = getAppSettings().get("AppCenterDSN").toString();
		return dsn;
	}

	public static String getUser() {
		String user = getAppSettings().get("User").toString();
		return user;
	}

	public static String getPassword() {
		String password = getAppSettings().get("Password").toString();
		return password;
	}

	public static void setAppCenterDSN(String value) {
		/*
		 * warning getAppSettings().get("AppCenterDSN").toString() = value;
		 */
		getAppSettings().put("AppCenterDSN", value);
	}

	/**
	 * 获取主应用程序的数据库类型
	 * 
	 * @return
	 */
	public static BP.DA.DBType getAppCenterDBType() {
		if (getAppSettings().get("AppCenterDBType").toString().equalsIgnoreCase("MSMSSQL")
				|| getAppSettings().get("AppCenterDBType").toString().equalsIgnoreCase("MSSQL")) {
			return BP.DA.DBType.MSSQL;
		} else if (getAppSettings().get("AppCenterDBType").toString().equalsIgnoreCase("Oracle")) {
			return BP.DA.DBType.Oracle;
		} else if (getAppSettings().get("AppCenterDBType").toString().equalsIgnoreCase("MySQL")) {
			return BP.DA.DBType.MySQL;
		} else {
			return BP.DA.DBType.Oracle;
		}
	}

	/**
	 * 获取不同类型的数据库变量标记
	 * 
	 * @return
	 */
	public static String getAppCenterDBVarStr() {
		switch (SystemConfig.getAppCenterDBType()) {
		case MSSQL:
			return ":";
		case Oracle:
			return ":";
		case Informix:
			return "?";
		case MySQL:
			return ":";
		default:
			return "";
		}
	}

	public static String getAppCenterDBLengthStr() {
		switch (SystemConfig.getAppCenterDBType()) {
		case Oracle:
			return "Length";
		case MSSQL:
			return "LEN";
		case Informix:
			return "Length";
		case Access:
			return "Length";
		default:
			return "Length";
		}
	}

	/**
	 * 获取不同类型的substring函数
	 * 
	 * @return
	 */
	public static String getAppCenterDBSubstringStr() {
		switch (SystemConfig.getAppCenterDBType()) {
		case Oracle:
			return "substr";
		case MSSQL:
			return "substring";
		case Informix:
			return "MySubString";
		case Access:
			return "Mid";
		default:
			return "substring";
		}
	}

	private static String _AppCenterDBDatabase = null;

	/**
	 * 数据库名称
	 * 
	 * @return
	 */
	public static String getAppCenterDBDatabase() {
		if (_AppCenterDBDatabase == null) {
			_AppCenterDBDatabase = getAppSettings().get("AppCenterDBDatabase").toString();
			/*
			 * warning switch (BP.DA.DBAccess.getAppCenterDBType()){ case MSSQL:
			 * SqlConnection connMSSQL = new
			 * SqlConnection(SystemConfig.getAppCenterDSN()); if
			 * (connMSSQL.State != ConnectionState.Open) { connMSSQL.Open(); }
			 * _AppCenterDBDatabase = connMSSQL.Database; break; case Oracle:
			 * OracleConnection connOra = new
			 * OracleConnection(SystemConfig.getAppCenterDSN()); if
			 * (connOra.State != ConnectionState.Open) { connOra.Open(); }
			 * _AppCenterDBDatabase = connOra.Database; break; case MySQL:
			 * MySqlConnection connMySQL = new
			 * MySqlConnection(SystemConfig.getAppCenterDSN()); if
			 * (connMySQL.State != ConnectionState.Open) { connMySQL.Open(); }
			 * _AppCenterDBDatabase = connMySQL.Database; break; //case
			 * DA.DBType.Informix: // IfxConnection connIFX = new
			 * IfxConnection(SystemConfig.AppCenterDSN); // if (connIFX.State !=
			 * ConnectionState.Open) // connIFX.Open(); // _AppCenterDBDatabase
			 * = connIFX.Database; // break; default: throw new
			 * RuntimeException("@没有判断的数据类型."); break; }
			 */

		}
		// 返回database.
		return _AppCenterDBDatabase;
	}

	public static String getAppCenterDBAddStringStr() {
		switch (SystemConfig.getAppCenterDBType()) {
		case Oracle:
		case MySQL:
		case Informix:
			return "||";
		default:
			return "+";
		}
	}

	public static String getAppSavePath() {
		String savePath = getAppSettings().get("SavaPath").toString();
		return savePath;
	}

	public static Hashtable<String, Object> CS_DBConnctionDic;

	public static void DoClearCash_del() {
		DoClearCash();
	}

	/**
	 * 执行清空
	 * 
	 */
	public static void DoClearCash() {
		// HttpRuntime.UnloadAppDomain();
		BP.DA.Cash.getMap_Cash().clear();
		BP.DA.Cash.getSQL_Cash().clear();
		BP.DA.Cash.getEnsData_Cash().clear();
		BP.DA.Cash.getEnsData_Cash_Ext().clear();
		BP.DA.Cash.getBS_Cash().clear();
		BP.DA.Cash.getBill_Cash().clear();
		BP.DA.CashEntity.getDCash().clear();

		try {
			// System.Web.HttpContext.Current.Session.Clear();
			// System.Web.HttpContext.Current.Application.Clear();
		} catch (java.lang.Exception e) {
		}
	}

}
