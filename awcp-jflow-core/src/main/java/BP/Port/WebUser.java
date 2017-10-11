package BP.Port;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.DA.DBAccess;
import BP.DA.DataType;
import BP.DA.Paras;
import BP.En.QueryObject;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;
import TL.ContextHolderUtils;
import cn.org.awcp.core.utils.SessionUtils;

/**
 * User 的摘要说明。
 */
public class WebUser {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(WebUser.class);

	public static Depts getHisDepts() {
		Object obj = null;
		obj = GetSessionByKey("HisDepts", obj);
		if (obj == null) {
			Depts sts = WebUser.getHisEmp().getHisDepts();
			SetSessionByKey("HisDepts", sts);
			return sts;
		}
		return (Depts) ((obj instanceof Depts) ? obj : null);
	}

	/**
	 * 登陆
	 * 
	 * @param em
	 */
	public static void SignInOfGener(Emp em) {
		SignInOfGener(em, getSysLang(), null, true, false);
	}

	/**
	 * 登陆
	 * 
	 * @param em
	 * @param isRememberMe
	 */
	public static void SignInOfGener(Emp em, boolean isRememberMe) {
		SignInOfGener(em, getSysLang(), null, isRememberMe, false);
	}

	/**
	 * 登陆
	 * 
	 * @param em
	 * @param auth
	 */
	public static void SignInOfGenerAuth(Emp em, String auth) {
		SignInOfGener(em, getSysLang(), auth, true, false);
	}

	/**
	 * 登陆
	 * 
	 * @param em
	 * @param lang
	 */
	public static void SignInOfGenerLang(Emp em, String lang, boolean isRememberMe) {
		SignInOfGener(em, lang, null, isRememberMe, false);
	}

	/**
	 * 登陆
	 * 
	 * @param em
	 * @param lang
	 */
	public static void SignInOfGenerLang(Emp em, String lang) {
		SignInOfGener(em, lang, null, true, false);
	}

	public static void SignInOfGener(Emp em, String lang) {
		SignInOfGener(em, lang, em.getNo(), true, false);
	}

	/**
	 * 登录
	 * 
	 * @param em
	 *            登录人
	 * @param lang
	 *            语言
	 * @param auth
	 *            被授权登录人
	 * @param isRememberMe
	 *            是否记忆我
	 */
	public static void SignInOfGener(Emp em, String lang, String auth, boolean isRememberMe) {
		SignInOfGener(em, lang, auth, isRememberMe, false);
	}

	/**
	 * 通用的登陆
	 * 
	 * @param em
	 *            人员
	 * @param lang
	 *            语言
	 * @param auth
	 *            授权人
	 * @param isRememberMe
	 *            是否记录cookies
	 * @param IsRecSID
	 *            是否记录SID
	 */
	public static void SignInOfGener(Emp em, String lang, String auth, boolean isRememberMe, boolean IsRecSID) {
		/*
		 * warning if (BP.Glo.getHttpContextCurrent() == null)
		 */

		if (SystemConfig.getIsBSsystem()) {
			BP.Sys.Glo.WriteUserLog("SignIn", em.getNo(), "登录");
		}

		try {
			WebUser.setAuth(auth);
			WebUser.setNo(em.getNo());
			WebUser.setName(URLEncoder.encode(em.getName(), "UTF-8"));

			WebUser.setFK_Dept(em.getFK_Dept());
			WebUser.setFK_DeptName(em.getFK_DeptText());

			if (IsRecSID) {
				// 如果记录sid
				String sid1 = DataType.dateToStr(new Date(), "MMddHHmmss");
				/*
				 * warning String sid1 = new java.util.Date().ToString("MMddHHmmss");
				 */

				DBAccess.RunSQL("UPDATE Port_Emp SET SID='" + sid1 + "' WHERE No='" + WebUser.getNo() + "'");
				WebUser.setSID(sid1);
			}

			WebUser.setSysLang(lang);
			if (SystemConfig.getIsBSsystem()) {
				/*
				 * warning HttpCookie hc =
				 * BP.Glo.getHttpContextCurrent().Request.Cookies["CCS"]; if (hc != null) {
				 * BP.Glo.getHttpContextCurrent().Request.Cookies.Remove("CCS"); }
				 */

				int expiry = 60 * 60 * 24 * 2;
				ContextHolderUtils.addCookie("No", expiry, em.getNo());
				ContextHolderUtils.addCookie("Name", expiry, URLEncoder.encode(em.getName(), "UTF-8"));
				ContextHolderUtils.addCookie("IsRememberMe", expiry, isRememberMe ? "1" : "0");
				ContextHolderUtils.addCookie("FK_Dept", expiry, em.getFK_Dept());
				ContextHolderUtils.addCookie("FK_DeptName", expiry, URLEncoder.encode(em.getFK_DeptText(), "UTF-8"));
				/*
				 * warning HttpCookie cookie = new HttpCookie("CCS"); cookie.Expires = new
				 * java.util.Date().AddDays(2); cookie.Values.Add("No", em.getNo());
				 * cookie.Values.Add("Name", HttpUtility.UrlEncode(em.getName()));
				 * if(isRememberMe){ cookie.Values.Add("IsRememberMe", "1"); } else {
				 * cookie.Values.Add("IsRememberMe", "0"); } cookie.Values.Add("FK_Dept",
				 * em.getFK_Dept()); cookie.Values.Add("FK_DeptName",
				 * HttpUtility.UrlEncode(em.getFK_DeptText())); if
				 * (BP.Glo.getHttpContextCurrent().Session != null) { cookie.Values.Add("Token",
				 * BP.Glo.getHttpContextCurrent().Session.SessionID); cookie.Values.Add("SID",
				 * BP.Glo.getHttpContextCurrent().Session.SessionID); }
				 * cookie.Values.Add("Lang", lang);
				 */

				String isEnableStyle = SystemConfig.getAppSettings().get("IsEnableStyle").toString();
				if (isEnableStyle.equals("1")) {
					try {
						String sql = "SELECT Style FROM WF_Emp WHERE No='" + em.getNo() + "' ";
						int val = DBAccess.RunSQLReturnValInt(sql, 0);
						/*
						 * warning cookie.Values.Add("Style", (new Integer(val)).toString());
						 */
						ContextHolderUtils.addCookie("Style", expiry, String.valueOf(val));
						WebUser.setStyle(String.valueOf(val));
					} catch (java.lang.Exception e) {
					}
				}
				if (auth == null) {
					auth = "";
				}
				/*
				 * warning cookie.Values.Add("Auth", auth); // 授权人.
				 * BP.Glo.getHttpContextCurrent().Response.AppendCookie(cookie);
				 */
				ContextHolderUtils.addCookie("Auth", expiry, auth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 静态方法
	/**
	 * 通过key,取出session.
	 * 
	 * @param key
	 *            key
	 * @param isNullAsVal
	 *            如果是Null, 返回的值.
	 * @return
	 */
	public static String GetSessionByKey(String key, String isNullAsVal) {
		/*
		 * warning if (getIsBSMode() && BP.Glo.getHttpContextCurrent() != null &&
		 * BP.Glo.getHttpContextCurrent().Session != null) { String str = (String)
		 * ((BP.Glo.getHttpContextCurrent().Session[key] instanceof String) ? BP.Glo
		 * .getHttpContextCurrent().Session[key] : null);
		 */
		if (getIsBSMode() && null != ContextHolderUtils.getRequest() && null != ContextHolderUtils.getSession()) {
			Object value = ContextHolderUtils.getSession().getAttribute(key);
			String str = value == null ? "" : String.valueOf(value);
			if (StringHelper.isNullOrEmpty(str)) {
				str = isNullAsVal;
			}
			return str;
		} else {
			if ((Current.Session.get(key) == null || Current.Session.get(key).toString().equals(""))
					&& isNullAsVal != null) {
				Current.Session.put(key, isNullAsVal);
				return isNullAsVal;
			} else {
				return (String) Current.Session.get(key);
			}
		}
	}

	public static Object GetObjByKey(String key) {
		if (getIsBSMode()) {
			/*
			 * warning return BP.Glo.getHttpContextCurrent().Session[key];
			 */
			return ContextHolderUtils.getSession().getAttribute(key);
		} else {
			return Current.Session.get(key);
		}
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	/**
	 * 是不是b/s 工作模式。
	 */
	protected static boolean getIsBSMode() {
		/*
		 * warning if (BP.Glo.getHttpContextCurrent() == null) {
		 */
		if (ContextHolderUtils.getRequest() == null) {
			return false;
		} else {
			return true;
		}
	}

	public static Object GetSessionByKey(String key, Object defaultObjVal) {
		/*
		 * warning if (getIsBSMode() && BP.Glo.getHttpContextCurrent() != null &&
		 * BP.Glo.getHttpContextCurrent().Session != null) { if
		 * (BP.Glo.getHttpContextCurrent().Session[key] == null) { return defaultObjVal;
		 * } else { return BP.Glo.getHttpContextCurrent().Session[key]; }
		 */
		if (getIsBSMode() && null != ContextHolderUtils.getRequest() && null != ContextHolderUtils.getSession()) {
			Object value = ContextHolderUtils.getSession().getAttribute(key);
			if (null == value) {
				return defaultObjVal;
			} else {
				return value;
			}
		} else {
			if (Current.Session.get(key) == null) {
				return defaultObjVal;
			} else {
				return Current.Session.get(key);
			}
		}
	}

	/**
	 * 设置session
	 * 
	 * @param key
	 *            键
	 * @param val
	 *            值
	 */
	public static void SetSessionByKey(String key, Object val) {
		if (val == null) {
			return;
		}
		/*
		 * warning if (getIsBSMode() && BP.Glo.getHttpContextCurrent() != null &&
		 * BP.Glo.getHttpContextCurrent().Session != null) {
		 * BP.Glo.getHttpContextCurrent().Session[key] = val;
		 */
		if (getIsBSMode() && null != ContextHolderUtils.getRequest() && null != ContextHolderUtils.getSession()) {
			ContextHolderUtils.getSession().setAttribute(key, val);
		} else {
			Current.SetSession(key, val);
		}
	}

	/**
	 * 退回
	 */
	public static void Exit() {
		if (!getIsBSMode()) {
			try {
				String token = WebUser.getToken();
				/*
				 * warning BP.Glo.getHttpContextCurrent().Response.Cookies.Clear();
				 * BP.Glo.getHttpContextCurrent().Request.Cookies.Clear(); HttpCookie cookie =
				 * new HttpCookie("CCS", ""); cookie.Expires = new java.util.Date().AddDays(2);
				 * cookie.Values.Add("No", ""); cookie.Values.Add("Name", ""); // 2013.06.07 H
				 * cookie.Values.Add("Pass", ""); cookie.Values.Add("IsRememberMe", "0");
				 * BP.Glo.getHttpContextCurrent().Response.Cookies.Add(cookie);
				 */
				int expiry = 60 * 60 * 24 * 2;
				ContextHolderUtils.addCookie("No", expiry, "");
				ContextHolderUtils.addCookie("Name", expiry, "");
				ContextHolderUtils.addCookie("Pass", expiry, "");
				ContextHolderUtils.addCookie("IsRememberMe", expiry, "0");
				// ContextHolderUtils.getSession().invalidate();
				WebUser.setToken(token);
				Current.Session.clear();
			} catch (java.lang.Exception e) {
			}
		} else {
			try {
				String token = WebUser.getToken();
				/*
				 * BP.Glo.getHttpContextCurrent().Response.Cookies.Clear();
				 * BP.Glo.getHttpContextCurrent().Request.Cookies.Clear();
				 * BP.Glo.getHttpContextCurrent().Session.Clear();
				 * 
				 * HttpCookie cookie = new HttpCookie("CCS", ""); cookie.Expires = new
				 * java.util.Date().AddDays(2); cookie.Values.Add("No", "");
				 * cookie.Values.Add("Name", ""); // 2013.06.07 H cookie.Values.Add("Pass", "");
				 * cookie.Values.Add("IsRememberMe", "0"); cookie.Values.Add("Auth", ""); //
				 * 授权人. BP.Glo.getHttpContextCurrent().Response.Cookies.Add(cookie);
				 */
				ContextHolderUtils.clearCookie();
				// ContextHolderUtils.getSession().invalidate();
				int expiry = 60 * 60 * 24 * 2;
				ContextHolderUtils.addCookie("No", expiry, "");
				ContextHolderUtils.addCookie("Name", expiry, "");
				ContextHolderUtils.addCookie("Pass", expiry, "");
				ContextHolderUtils.addCookie("IsRememberMe", expiry, "0");
				ContextHolderUtils.addCookie("Auth", expiry, "");
				ContextHolderUtils.getSession().removeAttribute("Auth");
				logger.debug(ContextHolderUtils.getSession().getAttribute("Auth") + "");
				WebUser.setToken(token);
			} catch (java.lang.Exception e2) {
			}
		}
	}

	/**
	 * 授权人
	 */
	public static String getAuth() {
		String val = GetValFromCookie("Auth", null, false);
		if (val == null) {
			val = GetSessionByKey("Auth", null);
		}
		return val;
	}

	public static void setAuth(String value) {
		if (value == null || value.equals("")) {
			SetSessionByKey("Auth", null);
		} else {
			SetSessionByKey("Auth", value);
		}
	}

	public static String getFK_DeptName() {
		try {
			String val = GetValFromCookie("FK_DeptName", null, true);
			return val;
		} catch (java.lang.Exception e) {
			return "无";
		}
	}

	public static void setFK_DeptName(String value) {
		SetSessionByKey("FK_DeptName", value);
	}

	/**
	 * 部门全称
	 */
	public static String getFK_DeptNameOfFull() {
		String val = GetValFromCookie("FK_DeptNameOfFull", null, true);
		if (StringHelper.isNullOrEmpty(val)) {
			try {
				val = DBAccess.RunSQLReturnStringIsNull(
						"SELECT NameOfFull FROM Port_Dept WHERE No='" + WebUser.getFK_Dept() + "'", null);
				return val;
			} catch (java.lang.Exception e) {
				val = WebUser.getFK_DeptName();
			}

			// 给它赋值.
			setFK_DeptNameOfFull(val);
		}
		return val;
	}

	public static void setFK_DeptNameOfFull(String value) {
		SetSessionByKey("FK_DeptNameOfFull", value);
	}

	public static String getSysLang() {
		return "CH";
	}

	public static void setSysLang(String value) {
		SetSessionByKey("Lang", value);
	}

	/**
	 * sessionID
	 */
	public static String getNoOfSessionID() {
		String s = GetSessionByKey("No", null);
		if (s == null) {
			/*
			 * warning return BP.Glo.getHttpContextCurrent().Session.SessionID;
			 */
			return (String) SessionUtils.getCurrentSession().getId();
		}
		return s;
	}

	/**
	 * FK_Dept
	 */
	public static String getFK_Dept() {
		String val = GetValFromCookie("FK_Dept", null, false);
		if (val == null) {
			val = "587235";
			// throw new RuntimeException(
			// "@err-003 FK_Dept
			// 登陆信息丢失，请确认当前操作员的部门信息是否完整，检查表:Port_Emp字段FK_Dept。");
		}
		return val;
	}

	public static void setFK_Dept(String value) {
		SetSessionByKey("FK_Dept", value);
	}

	/**
	 * 当前登录人员的父节点编号
	 */
	public static String getDeptParentNo() {
		String val = GetValFromCookie("DeptParentNo", null, false);
		if (val == null) {
			throw new RuntimeException("@err-001 DeptParentNo 登陆信息丢失。");
		}
		return val;
	}

	public static void setDeptParentNo(String value) {
		SetSessionByKey("DeptParentNo", value);
	}

	/**
	 * 检查权限控制
	 * 
	 * @param sid
	 * @return
	 */
	public static boolean CheckSID(String userNo, String sid) {
		String mysid = null;
		try {
			mysid = DBAccess.RunSQLReturnStringIsNull("SELECT SID FROM Port_Emp WHERE No='" + userNo + "'", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sid.equals(mysid)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getNoOfRel() {
		return GetSessionByKey("No", null);
	}

	public static String GetValFromCookie(String valKey, String isNullAsVal, boolean isChinese) {
		if (!getIsBSMode()) {
			return Current.GetSessionStr(valKey, isNullAsVal);
		}

		try {
			// 先从session里面取.
			/*
			 * warning String v = (String) ((BP.Glo.getHttpContextCurrent().Session[valKey]
			 * instanceof String) ? BP.Glo .getHttpContextCurrent().Session[valKey] : null);
			 */
			Object value = ContextHolderUtils.getSession().getAttribute(valKey);
			String v = value == null ? "" : String.valueOf(value);
			if (!StringHelper.isNullOrEmpty(v)) {
				if (isChinese) {
					v = URLDecoder.decode(v, "UTF-8");
				}
				return v;
			}
		} catch (java.lang.Exception e) {
		}

		/*
		 * warning String key = "CCS"; HttpCookie hc =
		 * BP.Glo.getHttpContextCurrent().Request.Cookies[key]; if (hc == null) { return
		 * null; }
		 */
		try {
			String val = null;
			Cookie cookie = ContextHolderUtils.getCookie(valKey);
			if (isChinese) {
				/*
				 * warning val = HttpUtility.UrlDecode(hc[valKey]); if (val == null) { val =
				 * hc.Values[valKey]; }
				 */
				val = URLEncoder.encode(cookie.getValue(), "UTF-8");
			} else {
				/*
				 * warning val = hc.Values[valKey];
				 */
				val = cookie.getValue();
			}

			if (StringHelper.isNullOrEmpty(val)) {
				return isNullAsVal;
			}
			return val;
		} catch (java.lang.Exception e2) {
			return isNullAsVal;
		}
		// throw new RuntimeException("@err-001 登陆信息丢失。");
	}

	/**
	 * 编号
	 */
	public static String getNo() {
		// PunUserBaseInfoVO user = (PunUserBaseInfoVO) SessionUtils
		// .getObjectFromSession(SessionContants.CURRENT_USER);
		String val = GetValFromCookie("No", null, true);
		if (val == null) {
			val = SystemConfig.getAppSettings().get("Admin").toString();
			// throw new RuntimeException("@err-002 Name 登陆信息丢失。");
		}
		return val;

	}

	public static void setNo(String value) {
		SetSessionByKey("No", value);
	}

	/**
	 * 名称
	 */
	public static String getName() {
		String val = GetValFromCookie("Name", null, true);
		if (val == null) {
			val = SystemConfig.getAppSettings().get("Admin").toString();
			// throw new RuntimeException("@err-002 Name 登陆信息丢失。");
		}
		return val;
	}

	public static void setName(String value) {
		SetSessionByKey("Name", value);
	}

	/**
	 * 域
	 */
	public static String getDomain() {
		String val = GetValFromCookie("Domain", null, true);
		if (val == null) {
			throw new RuntimeException("@err-003 Domain 登陆信息丢失。");
		}
		return val;
	}

	public static void setDomain(String value) {
		SetSessionByKey("Domain", value);
	}

	/**
	 * 令牌
	 */
	public static String getToken() {

		return GetSessionByKey("token", "null");
	}

	public static void setToken(String value) {
		SetSessionByKey("token", value);
	}

	public static String getStyle() {
		return GetSessionByKey("Style", "0");
	}

	public static void setStyle(String value) {
		SetSessionByKey("Style", value);
	}

	/**
	 * 当前工作人员实体
	 */
	public static Emp getHisEmp() {
		return new Emp(WebUser.getNo());
	}

	/**
	 * SID
	 */
	public static String getSID() {
		String val = GetValFromCookie("SID", null, true);
		if (val == null) {
			return "";
		}
		return val;
	}

	public static void setSID(String value) {
		SetSessionByKey("SID", value);
	}

	/**
	 * 设置SID
	 * 
	 * @param sid
	 */
	public static void SetSID(String sid) {
		try {
			Paras ps = new Paras();
			ps.SQL = "UPDATE Port_Emp SET SID=" + SystemConfig.getAppCenterDBVarStr() + "SID WHERE No="
					+ SystemConfig.getAppCenterDBVarStr() + "No";
			ps.Add("SID", sid);
			ps.Add("No", WebUser.getNo());
			BP.DA.DBAccess.RunSQL(ps);
			WebUser.setSID(sid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 是否是授权状态
	 */
	public static boolean getIsAuthorize() {
		if (getAuth() == null || getAuth().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 使用授权人ID
	 */
	public static String getAuthorizerEmpID() {
		return GetSessionByKey("AuthorizerEmpID", null);

	}

	public static void setAuthorizerEmpID(String value) {
		SetSessionByKey("AuthorizerEmpID", value);
	}

	public static boolean getIsWap() {
		if (!SystemConfig.getIsBSsystem())
			return false;
		int s = Integer.parseInt(GetSessionByKey("IsWap", 9).toString());
		if (9 == s) {
			String userAgent = ContextHolderUtils.getRequest().getHeader("USER-AGENT").toLowerCase();
			boolean b = userAgent.contains("wap");
			setIsWap(b);
			if (b) {
				SetSessionByKey("IsWap", 1);
			} else {
				SetSessionByKey("IsWap", 0);
			}
			return b;
		}
		if (s == 1)
			return true;
		else
			return false;
	}

	public static void setIsWap(boolean value) {
		if (value) {
			SetSessionByKey("IsWap", 1);
		} else {
			SetSessionByKey("IsWap", 0);
		}
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 部门权限
	/**
	 * 部门权限
	 */
	public static Depts getHisDeptsOfPower() {
		EmpDepts eds = new EmpDepts();
		return eds.GetHisDepts(WebUser.getNo());
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion 部门权限

	// public static Stations getHisStations() {
	// EmpStations sts = new EmpStations();
	// return sts.GetHisStations(WebUser.getNo());
	// }
	public static Stations getHisStations() {
		Object obj = null;
		obj = GetSessionByKey("HisSts", obj);
		if (obj == null) {
			Stations sts = new Stations();
			QueryObject qo = new QueryObject(sts);
			qo.AddWhereInSQL("No", "SELECT FK_Station FROM Port_EmpStation WHERE FK_Emp='" + WebUser.getNo() + "'");
			qo.DoQuery();
			SetSessionByKey("HisSts", sts);
			return sts;
		}
		return (Stations) ((obj instanceof Stations) ? obj : null);
	}
}
