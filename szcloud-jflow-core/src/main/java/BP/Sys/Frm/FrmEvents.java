package BP.Sys.Frm;

import java.util.Set;

import org.omg.IOP.Encoding;

import BP.DA.AtPara;
import BP.DA.DBAccess;
import BP.DA.DataType;
import BP.DA.Log;
import BP.En.Attr;
import BP.En.Attrs;
import BP.En.ClassFactory;
import BP.En.EntitiesOID;
import BP.En.Entity;
import BP.En.QueryObject;
import BP.En.Row;
import BP.Port.WebUser;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;

/** 
 事件
 
*/
public class FrmEvents extends EntitiesOID
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 执行事件
	 
	 @param dotype 执行类型
	 @param en 数据实体
	 @return null 没有事件，其他为执行了事件。
	*/
	public final String DoEventNode(String dotype, Entity en)
	{
	   return DoEventNode(dotype, en, null);
	}
	/** 
	 执行事件
	 
	 @param dotype 执行类型
	 @param en 数据实体
	 @param atPara 参数
	 @return null 没有事件，其他为执行了事件。
	*/
	public final String DoEventNode(String dotype, Entity en, String atPara)
	{
		if (this.size() == 0)
		{
			return null;
		}
		String val= _DoEventNode(dotype, en, atPara);
		if (val != null)
		{
			val = val.trim();
		}

		if (StringHelper.isNullOrEmpty(val))
		{
			return ""; // 说明有事件，执行成功了。
		}
		else
		{
			return val; // 没有事件.
		}
	}

	/** 
	 执行事件，事件标记是 EventList.
	 
	 @param dotype 执行类型
	 @param en 数据实体
	 @param atPara 特殊的参数格式@key=value 方式.
	 @return 
	*/
	private String _DoEventNode(String dotype, Entity en, String atPara)
	{
		if (this.size() == 0)
		{
			return null;
		}

		Entity tempVar = this.GetEntityByKey(FrmEventAttr.FK_Event, dotype);
		FrmEvent nev = (FrmEvent)((tempVar instanceof FrmEvent) ? tempVar : null);


		if (nev == null || nev.getHisDoType() == EventDoType.Disable)
		{
			return null;
		}

		String doc = nev.getDoDoc().trim();
		if (doc == null || doc.equals(""))
		{
			return null;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region 处理执行内容
		Attrs attrs = en.getEnMap().getAttrs();
		String MsgOK = "";
		String MsgErr = "";
		for (Attr attr : attrs)
		{
			if (!doc.contains("@" + attr.getKey()) )
			{
				continue;
			}
			if (attr.getMyDataType() == DataType.AppString || attr.getMyDataType() == DataType.AppDateTime || attr.getMyDataType() == DataType.AppDate)
			{
				doc = doc.replace("@" + attr.getKey(), "'" + en.GetValStrByKey(attr.getKey()) + "'");
			}
			else
			{
				doc = doc.replace("@" + attr.getKey(), en.GetValStrByKey(attr.getKey()));
			}
		}

		doc = doc.replace("~", "'");
		doc = doc.replace("@WebUser.No", WebUser.getNo());
		doc = doc.replace("@WebUser.Name", WebUser.getName());
		doc = doc.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());
		doc = doc.replace("@FK_Node", nev.getFK_MapData().replace("ND", ""));
		doc = doc.replace("@FK_MapData", nev.getFK_MapData());
		doc = doc.replace("@WorkID", en.GetValStrByKey("OID", "@WorkID"));

		//if (System.Web.HttpContext.Current != null)
		//{
			//如果是 bs 系统, 有可能参数来自于url ,就用url的参数替换它们 .
			String url = BP.Sys.Glo.getRequest().getRemoteAddr();
			if (url.indexOf('?') != -1)
			{
				url = url.substring(url.indexOf('?'));
			}

			String[] paras = url.split("[&]", -1);
			for (String s : paras)
			{
				if (!doc.contains("@" + s) )
				{
					continue;
				}

				String[] mys = s.split("[=]", -1);
				if (!doc.contains("@" + mys[0]) )
				{
					continue;
				}
				doc = doc.replace("@" + mys[0], mys[1]);
			}
		//}

		if (nev.getHisDoType() == EventDoType.URLOfSelf)
		{
			if (!doc.contains("?") )
			{
				doc += "?1=2";
			}

			doc += "&UserNo=" + WebUser.getNo();
			doc += "&SID=" + WebUser.getSID();
			doc += "&FK_Dept=" + WebUser.getFK_Dept();
			// doc += "&FK_Unit=" + WebUser.FK_Unit;
			doc += "&OID=" + en.getPKVal();

			if (SystemConfig.getIsBSsystem())
			{
				//是bs系统，并且是url参数执行类型.
				url = BP.Sys.Glo.getRequest().getRemoteAddr();
				if (url.indexOf('?') != -1)
				{
					url = url.substring(url.indexOf('?'));
				}
				paras = url.split("[&]", -1);
				for (String s : paras)
				{
					if (doc.contains(s))
					{
						continue;
					}
					doc += "&" + s;
				}
				doc = doc.replace("&?", "&");
			}

			if (!SystemConfig.getIsBSsystem() )
			{
				//非bs模式下调用,比如在cs模式下调用它,它就取不到参数. 
			}

//			if (!doc.startsWith("http") )
//			{
//				//如果没有绝对路径 
//				if (SystemConfig.getIsBSsystem())
//				{
//					//在cs模式下自动获取
//					String host = BP.Sys.Glo.getRequest().getRemoteHost();
//					if (doc.contains("@AppPath"))
//					{
//						doc = doc.replace("@AppPath", "http://" + host + BP.Sys.Glo.getRequest().ApplicationPath);
//					}
//					else
//					{
//						doc = "http://" + BP.Sys.Glo.getRequest().Url.Authority + doc;
//					}
//				}
//
//				if (!SystemConfig.getIsBSsystem() )
//				{
//					//在cs模式下它的baseurl 从web.config中获取.
//					String cfgBaseUrl = SystemConfig.getAppSettings()
//							.get("BaseUrl").toString();
//					/*
//					 * warning String cfgBaseUrl = SystemConfig.getAppSettings()["BaseUrl"];*/
//					if (StringHelper.isNullOrEmpty(cfgBaseUrl))
//					{
//						String err = "调用url失败:没有在web.config中配置BaseUrl,导致url事件不能被执行.";
//						Log.DefaultLogWriteLineError(err);
//						throw new RuntimeException(err);
//					}
//					doc = cfgBaseUrl + doc;
//				}
//			}

			//增加上系统约定的参数.
			doc += "&EntityName=" + en.toString() + "&EntityPK=" + en.getPK() + "&EntityPKVal=" + en.getPKVal() + "&FK_Event=" + nev.getMyPK();
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion 处理执行内容

		if (atPara != null && doc.contains("@"))
		{
			AtPara ap = new AtPara(atPara);
			
			/*
			 * warning for (String s : ap.getHisHT().keySet())*/
			Set<String> a=ap.getHisHT().keySet();
			for(String s : a)
			{
				doc = doc.replace("@" + s, ap.GetValStrByKey(s));
			}
		}

		if (FrmEventList.FrmLoadBefore.equals(dotype))
		{
			en.Retrieve(); //如果不执行，就会造成实体的数据与查询的数据不一致.
		}

		switch (nev.getHisDoType())
		{
			//case EventDoType.SP:
			//    try
			//    {
			//        Paras ps = new Paras();
			//        DBAccess.RunSP(doc, ps);
			//        return nev.MsgOK(en);
			//    }
			//    catch (Exception ex)
			//    {
			//        throw new Exception(nev.MsgError(en) + " Error:" + ex.Message);
			//    }
			//    break;
			case SP:
			case SQL:
				try
				{
					// 允许执行带有GO的sql.
					DBAccess.RunSQLs(doc);
					return nev.MsgOK(en);
				}
				catch (RuntimeException ex)
				{
					throw new RuntimeException(nev.MsgError(en) + " Error:" + ex.getMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case URLOfSelf:
				Object tempVar2 = new String(doc);
				String myURL = (String)((tempVar2 instanceof String) ? tempVar2 : null);
				if (!myURL.contains("http") )
				{
					if (SystemConfig.getIsBSsystem())
					{
//						String host = BP.Sys.Glo.getRequest().Url.Host;
//						if (myURL.contains("@AppPath"))
//						{
//							myURL = myURL.replace("@AppPath", "http://" + host + BP.Sys.Glo.getRequest().ApplicationPath);
//						}
//						else
//						{
//							myURL = "http://" + BP.Sys.Glo.getRequest().Url.Authority + myURL;
//						}
					}
					else
					{
						String cfgBaseUrl = SystemConfig.getAppSettings()
								.get("BaseUrl").toString();
						/*
						 * warning String cfgBaseUrl = SystemConfig.getAppSettings()["BaseUrl"];*/
						if (StringHelper.isNullOrEmpty(cfgBaseUrl))
						{
							String err = "调用url失败:没有在web.config中配置BaseUrl,导致url事件不能被执行.";
							Log.DefaultLogWriteLineError(err);
							throw new RuntimeException(err);
						}
						myURL = cfgBaseUrl + myURL;
					}
				}

				try
				{
					//Encoding encode = System.Text.Encoding.GetEncoding("gb2312");
					String text = DataType.ReadURLContext(myURL, 600000);
					if (text == null)
					{
						throw new RuntimeException("@流程设计错误，执行的url错误:" + myURL + ", 返回为null, 请检查url设置是否正确。提示：您可以copy出来此url放到浏览器里看看是否被正确执行。");
					}

					if (text != null && text.length() > 7 && text.substring(0, 7).toLowerCase().contains("err"))
					{
						throw new RuntimeException(text);
					}

					if (text == null || text.trim().equals(""))
					{
						return null;
					}
					return text;
				}
				catch (RuntimeException ex)
				{
					throw new RuntimeException("@" + nev.MsgError(en) + " Error:" + ex.getMessage());
				}
				//break;
			//case EventDoType.URLOfSystem:
			//    string hos1t = BP.Sys.Glo.Request.Url.Host;
			//    string url = "http://" + hos1t + BP.Sys.Glo.Request.ApplicationPath + "/DataUser/AppCoder/FrmEventHandle.jsp";
			//    url += "?FK_MapData=" + en.ClassID + "&WebUseNo=" + WebUser.No + "&EventType=" + nev.FK_Event;
			//    foreach (Attr attr in attrs)
			//    {
			//        if (attr.UIIsDoc || attr.IsRefAttr || attr.UIIsReadonly)
			//            continue;
			//        url += "&" + attr.Key + "=" + en.GetValStrByKey(attr.Key);
			//    }

			//    try
			//    {
			//        string text = DataType.ReadURLContext(url, 800, System.Text.Encoding.UTF8);
			//        if (text != null && text.Substring(0, 7).Contains("Err"))
			//            throw new Exception(text);

			//        if (text == null || text.Trim() == "")
			//            return null; // 如果是Null 没有事件配置。
			//        return text;
			//    }
			//    catch (Exception ex)
			//    {
			//        throw new Exception("@" + nev.MsgError(en) + " Error:" + ex.Message);
			//    }
			//    break;
			case EventBase: //执行事件类.

				// 获取事件类.
				Object tempVar3 = new String(doc);
				String evName = (String)((tempVar3 instanceof String) ? tempVar3 : null);
				BP.Sys.EventBase ev = null;
				try
				{
					ev = ClassFactory.GetEventBase(evName);
				}
				catch (RuntimeException ex)
				{
					throw new RuntimeException("@事件名称:" + evName + "拼写错误，或者系统不存在。说明：事件所在的类库必须是以BP.开头，并且类库的BP.xxx.dll。");
				}

				//开始执行.
				try
				{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#region 处理整理参数.
					Row r = en.getRow();
					try
					{
						//系统参数.
						r.put("FK_MapData", en.getClassID());
						/*
						 * warning r.Add("FK_MapData", en.getClassID());*/
					}
					catch (java.lang.Exception e)
					{
						r.put("FK_MapData", en.getClassID());
						/*
						 * warning r["FK_MapData"] = en.getClassID();*/
					}

					try
					{
						r.put("EventType", nev.getFK_Event());
						/*
						 * warning r.Add("EventType", nev.getFK_Event());*/
					}
					catch (java.lang.Exception e2)
					{
						r.put("EventType", nev.getFK_Event());
						/*
						 * warning r["EventType"] = nev.getFK_Event();*/
					}

					if (atPara != null)
					{
						AtPara ap = new AtPara(atPara);
						/*
						 * warning for (String s : ap.getHisHT().keySet())*/
						
						Set<String> a=ap.getHisHT().keySet();
						for (String s : a)
						{
							try
							{
								r.put(s, ap.GetValStrByKey(s));
								/*
								 * warning r.Add(s, ap.GetValStrByKey(s));*/
							}
							catch (java.lang.Exception e3)
							{
								r.put(s, ap.GetValStrByKey(s));
								/*
								 * warning r[s] = ap.GetValStrByKey(s);*/
							}
						}
					}

					if (SystemConfig.getIsBSsystem())
					{
						//如果是bs系统, 就加入外部url的变量.
						while (BP.Sys.Glo.getRequest().getParameterNames().hasMoreElements())
						{
							String key = (String) BP.Sys.Glo.getRequest().getParameterNames().nextElement();
							String val = BP.Sys.Glo.getRequest().getParameter(key);
							try
							{
								r.put(key, val);
								/*
								 * warning r.Add(key, val);*/
							}
							catch (java.lang.Exception e4)
							{
								r.put(key, val);
								/*
								 * warning r[key] = val;*/
							}
						}
					}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#endregion 处理整理参数.

					ev.setSysPara(r);
					ev.HisEn = en;
					ev.Do();
					return ev.SucessInfo;
				}
				catch (RuntimeException ex)
				{
					throw new RuntimeException("@执行事件(" + ev.getTitle() + ")期间出现错误:" + ex.getMessage());
				}
				//break;
			default:
				throw new RuntimeException("@no such way." + nev.getHisDoType().toString());
		}
		return "";
	}
	/** 
	 事件
	 
	*/
	public FrmEvents()
	{
	}
	/** 
	 事件
	 
	 @param FK_MapData FK_MapData
	*/
	public FrmEvents(String fk_MapData)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FrmEventAttr.FK_MapData, fk_MapData);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmEvent();
	}
}