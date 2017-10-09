package BP.Sys;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

import BP.DA.DBAccess;
import BP.DA.DataType;
import BP.En.Attr;
import BP.Tools.StringHelper;
import BP.WF.Template.WorkBase.StartWorkAttr;
import BP.WF.Template.WorkBase.Work;
import BP.WF.Template.WorkBase.WorkAttr;
import TL.ContextHolderUtils;

/** 
 公用的静态方法.
 
*/
public class Glo
{
		///#region 写入系统日志(写入的文件:\DataUser\Log\*.*)
	/** 
	 写入一条消息
	 
	 @param msg 消息
	*/
	public static void WriteLineInfo(String msg){
		BP.DA.Log.DefaultLogWriteLineInfo(msg);
	}
	/** 
	 写入一条警告
	 
	 @param msg 消息
	*/
	public static void WriteLineWarning(String msg)
	{
		BP.DA.Log.DefaultLogWriteLineWarning(msg);
	}
	/** 
	 写入一条错误
	 
	 @param msg 消息
	*/
	public static void WriteLineError(String msg)
	{
		BP.DA.Log.DefaultLogWriteLineError(msg);
	}
		///#endregion 写入系统日志

		///#region 写入用户日志(写入的用户表:Sys_UserLog).
	/** 
	 写入用户日志
	 
	 @param logType 类型
	 @param empNo 操作员编号
	 @param msg 信息
	 @param ip IP
	*/
	public static void WriteUserLog(String logType, String empNo, String msg, String ip)
	{
		UserLog ul = new UserLog();
		ul.setMyPK(DBAccess.GenerGUID());
		ul.setFK_Emp(empNo);
		ul.setLogFlag(logType);
		ul.setDocs(msg);
		ul.setIP(ip);
		ul.setRDT(DataType.getCurrentDataTime());
		ul.Insert();
	}
	
	

	/** 
	 写入用户日志
	 
	 @param logType 日志类型
	 @param empNo 操作员编号
	 @param msg 消息
	*/
	public static void WriteUserLog(String logType, String empNo, String msg)
	{
		UserLog ul = new UserLog();
		ul.setMyPK(DBAccess.GenerGUID());
		ul.setFK_Emp(empNo);
		ul.setLogFlag(logType);
		ul.setDocs(msg);
		ul.setRDT(DataType.getCurrentDataTime());
		try
		{
			if (BP.Sys.SystemConfig.getIsBSsystem())
			{
				ul.setIP(BP.Sys.Glo.getRequest().getRemoteHost());
			}
		}
		catch (java.lang.Exception e)
		{
		}
		ul.Insert();
	}
		///#endregion 写入用户日志.
	
	public static ArrayList<String> getQueryStringKeys(){
		// 处理传递过来的参数。
		ArrayList<String> requestKeys = new ArrayList<String>();

		Enumeration enu = getRequest().getParameterNames();
		while (enu.hasMoreElements()) {
			// 判断是否有内容，hasNext()
			String key = (String) enu.nextElement();
			requestKeys.add(key);
		}
		return requestKeys;
	}

	/** 
	 获得对象.
	 
	*/
	public static HttpServletRequest getRequest()
	{
		return ContextHolderUtils.getRequest();
	}
	/**
	 * 加密MD5
	 * 
	 * @param s
	 * @return
	 */
	public static String GenerMD5(Work wk) {
		String s = null;
		for (Attr attr : wk.getEnMap().getAttrs()) {
			if (attr.getKey() == WorkAttr.MD5) {

			} else if (attr.getKey() == BP.WF.Template.WorkBase.WorkAttr.RDT) {

			} else if (attr.getKey() == WorkAttr.CDT) {

			} else if (attr.getKey() == WorkAttr.Rec) {

			} else if (attr.getKey() == StartWorkAttr.Title) {

			} else if (attr.getKey() == StartWorkAttr.Emps) {

			} else if (attr.getKey() == StartWorkAttr.FK_Dept) {

			} else if (attr.getKey() == StartWorkAttr.PRI) {

			} else if (attr.getKey() == StartWorkAttr.FID) {
				continue;
			}

			/*
			 * warning for (Attr attr : wk.EnMap.Attrs) { switch (attr.getKey())
			 * { case WorkAttr.MD5: case WorkAttr.RDT: case WorkAttr.CDT: case
			 * WorkAttr.Rec: case StartWorkAttr.Title: case StartWorkAttr.Emps:
			 * case StartWorkAttr.FK_Dept: case StartWorkAttr.PRI: case
			 * StartWorkAttr.FID: continue; default: break; }
			 */

			String obj = (String) ((attr.getDefaultVal() instanceof String) ? attr
					.getDefaultVal() : null);
			/*
			 * warning String obj = (String) ((attr.DefaultVal instanceof
			 * String) ? attr.DefaultVal : null);
			 */
			// if (obj == null)
			// continue;
			if (obj != null && obj.contains("@")) {
				continue;
			}

			s += wk.GetValStrByKey(attr.getKey());
		}
		s += "ccflow";

		return DigestUtils.md5Hex(s).toLowerCase();
	}

}