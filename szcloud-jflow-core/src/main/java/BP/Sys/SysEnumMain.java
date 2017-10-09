package BP.Sys;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;

/** 
 SysEnumMain
 
*/
public class SysEnumMain extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 实现基本的方方法
	public final String getCfgVal()
	{
		return this.GetValStrByKey(SysEnumMainAttr.CfgVal);
	}
	public final void setCfgVal(String value)
	{
		this.SetValByKey(SysEnumMainAttr.CfgVal, value);
	}
	public final String getLang()
	{
		return this.GetValStrByKey(SysEnumMainAttr.Lang);
	}
	public final void setLang(String value)
	{
		this.SetValByKey(SysEnumMainAttr.Lang, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 SysEnumMain
	 
	*/
	public SysEnumMain()
	{
	}
	/** 
	 SysEnumMain
	 
	 @param no
	*/
	public SysEnumMain(String no)
	{
		try
		{
			this.setNo(no);
			this.Retrieve();
		}
		catch (RuntimeException ex)
		{
			SysEnums ses = new SysEnums(no);
			if (ses.size() == 0)
			{
				throw ex;
			}

			this.setNo(no);
			this.setName("未命名");
			String cfgVal = "";
			for (Object item : ses)
			{
				cfgVal += "@" + ((SysEnum)item).getIntKey() + "=" + ((SysEnum)item).getLab();
			}
			this.setCfgVal(cfgVal);
			this.Insert();
		}
	}
	private void InitUnRegEnum()
	{
		//   DataTable dt = BP.DA.DBAccess.RunSQL("SELECT DISTINCT EnumKey FROM SYS_Enum WHERE EnumKey Not IN (SELECT No FROM SYS_EnumMain )");
		//stringSELECT DISTINCT EnumKey FROM SYS_ENUM
	}
	/** 
	 Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_EnumMain");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("枚举");
		map.setEnType(EnType.Sys);

		map.AddTBStringPK(SysEnumMainAttr.No, null, "编号", true, false, 1, 40, 8);
		map.AddTBString(SysEnumMainAttr.Name, null, "名称", true, false, 0, 40, 8);
		map.AddTBString(SysEnumMainAttr.CfgVal, null, "配置信息", true, false, 0, 1500, 8);
		map.AddTBString(SysEnumMainAttr.Lang, "CH", "语言", true, false, 0, 10, 8);
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}