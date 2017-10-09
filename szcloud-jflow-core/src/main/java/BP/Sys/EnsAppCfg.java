package BP.Sys;

import BP.DA.Depositary;
import BP.En.Attr;
import BP.En.Attrs;
import BP.En.EnType;
import BP.En.Entity;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
应用配置

*/
public class EnsAppCfg extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	/** 
	 配置标签
	 
	*/
	public final String getCfgVal()
	{
		String val= this.GetValStrByKey(EnsAppCfgAttr.CfgVal);
		if (val == null || val.equals(""))
		{
			return null;
		}
		return val;
	}
	public final void setCfgVal(String value)
	{
		this.SetValByKey(EnsAppCfgAttr.CfgVal, value);
	}
	/** 
	 Int 值
	 
	*/
	public final int getCfgValOfInt()
	{
		return this.GetValIntByKey(EnsAppCfgAttr.CfgVal);
	}
	/** 
	 Boolen 值
	 
	*/
	public final boolean getCfgValOfBoolen()
	{
		return this.GetValBooleanByKey(EnsAppCfgAttr.CfgVal);
	}
	/** 
	 数据源
	 
	*/
	public final String getEnsName()
	{
		return this.GetValStringByKey(EnsAppCfgAttr.EnsName);
	}
	public final void setEnsName(String value)
	{
		this.SetValByKey(EnsAppCfgAttr.EnsName, value);
	}
	/** 
	 附件路径
	 
	*/
	public final String getCfgKey()
	{
		return this.GetValStringByKey(EnsAppCfgAttr.CfgKey);
	}
	public final void setCfgKey(String value)
	{
		this.SetValByKey(EnsAppCfgAttr.CfgKey, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 系统实体
	 
	*/
	public EnsAppCfg()
	{
	}
	/** 
	 系统实体
	 
	 @param no
	*/
	public EnsAppCfg(String pk)
	{
		this.setMyPK(pk);
		int i = this.RetrieveFromDBSources();
		if (i == 0)
		{
			//BP.Sys.Xml.EnsAppXml xml = new BP.Sys.Xml.EnsAppXml();
		}
	}
	public EnsAppCfg(String ensName, String cfgkey)
	{
		this.setMyPK(ensName + "@" + cfgkey);
		try
		{
			this.Retrieve();
		}
		catch (java.lang.Exception e)
		{
			EnsAppXmls xmls = new BP.Sys.EnsAppXmls();
			int i = xmls.Retrieve(BP.Sys.EnsAppXmlEnsName.EnsName, ensName, "No", cfgkey);
			if (i == 0)
			{
				Attrs attrs = this.getEnMap().getHisCfgAttrs();
				for (Attr attr : attrs)
				{
					if (attr.getKey().equals(cfgkey))
					{
						this.setEnsName(ensName);
						this.setCfgKey(cfgkey);
						if (attr.getKey().equals("FocusField"))
						{
							Entity en = BP.En.ClassFactory.GetEns(ensName).getGetNewEntity();
							if (en.getEnMap().getAttrs().Contains("Name"))
							{
								this.setCfgVal("Name");
							}
							if (en.getEnMap().getAttrs().Contains("Title"))
							{
								this.setCfgVal("Title");
							}
						}
						else
						{
							this.setCfgVal(attr.getDefaultVal().toString());
						}
						this.Insert();
						return;
					}
				}
			}
			EnsAppXml xml = null;
			 if (xmls.size() == 0)
                 xml = new EnsAppXml();
             else
                 xml = (EnsAppXml) xmls.get(0);
			this.setEnsName(ensName);
			this.setCfgKey(cfgkey);
			this.setCfgVal(xml.getDefVal());
			this.Insert();
		}
	}
	/** 
	 map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_EnsAppCfg");
		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("实体集合配置");
		map.setEnType(EnType.Sys);

		map.AddMyPK();
		map.AddTBString(EnsAppCfgAttr.EnsName, null, "实体集合", true, false, 0, 100, 60);
		map.AddTBString(EnsAppCfgAttr.CfgKey, null, "键", true, false, 0, 100, 60);
		map.AddTBString(EnsAppCfgAttr.CfgVal, null, "值", true, false, 0, 200, 60);


		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}