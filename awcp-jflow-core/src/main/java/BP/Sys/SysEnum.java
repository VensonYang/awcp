package BP.Sys;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 SysEnum
 
*/
public class SysEnum extends EntityMyPK
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 得到一个String By LabKey.
	 
	 @param EnumKey
	 @param intKey
	 @return 
	*/
	public static String GetLabByPK(String EnumKey, int intKey)
	{
		SysEnum en = new SysEnum(EnumKey, intKey);
		return en.getLab();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 实现基本的方方法
	/** 
	 标签
	 
	*/
	public final String getLab()
	{
	  return this.GetValStringByKey(SysEnumAttr.Lab);
	}
	public final void setLab(String value)
	{
		this.SetValByKey(SysEnumAttr.Lab, value);
	}
	/** 
	 标签
	 
	*/
	public final String getLang()
	{
		return this.GetValStringByKey(SysEnumAttr.Lang);
	}
	public final void setLang(String value)
	{
		this.SetValByKey(SysEnumAttr.Lang, value);
	}
	/** 
	 Int val
	 
	*/
	public final int getIntKey()
	{
		return this.GetValIntByKey(SysEnumAttr.IntKey);
	}
	public final void setIntKey(int value)
	{
		this.SetValByKey(SysEnumAttr.IntKey, value);
	}
	/** 
	 EnumKey
	 
	*/
	public final String getEnumKey()
	{
		return this.GetValStringByKey(SysEnumAttr.EnumKey);
	}
	public final void setEnumKey(String value)
	{
		this.SetValByKey(SysEnumAttr.EnumKey, value);
	}
	///// <summary>
	///// 风格
	///// </summary>
	//public  string  Style
	//{
	//    get
	//    {
	//        return this.GetValStringByKey(SysEnumAttr.Style);
	//    }
	//    set
	//    {
	//        this.SetValByKey(SysEnumAttr.Style,value);
	//    }
	//}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 SysEnum
	 
	*/
	public SysEnum()
	{
	}
	/** 
	 税务编号
	 
	 @param _No 编号
	*/
	public SysEnum(String EnumKey, int val)
	{
		this.setEnumKey(EnumKey);
		this.setLang(BP.Port.WebUser.getSysLang());
		this.setIntKey(val);
		this.setMyPK(this.getEnumKey() + "_" + this.getLang() + "_" + this.getIntKey());
		int i = this.RetrieveFromDBSources();
		if (i == 0)
		{
			i = this.Retrieve(SysEnumAttr.EnumKey, EnumKey, SysEnumAttr.Lang, BP.Port.WebUser.getSysLang(), SysEnumAttr.IntKey, this.getIntKey());
			SysEnums ses = new SysEnums();
			ses.Full(EnumKey);
			if (i == 0)
			{
				throw new RuntimeException("@ EnumKey=" + EnumKey+ " Val=" + val + " Lang="+BP.Port.WebUser.getSysLang()+" ...Error");
			}
		}
	}
	public SysEnum(String enumKey, String Lang, int val)
	{
		this.setEnumKey(enumKey);
		this.setLang(Lang);
		this.setIntKey(val);
		this.setMyPK(this.getEnumKey() + "_" + this.getLang() + "_" + this.getIntKey());
		int i = this.RetrieveFromDBSources();
		if (i == 0)
		{
			i = this.Retrieve(SysEnumAttr.EnumKey, enumKey, SysEnumAttr.Lang, Lang, SysEnumAttr.IntKey, this.getIntKey());

			SysEnums ses = new SysEnums();
			ses.Full(enumKey);

			if (i == 0)
			{
				throw new RuntimeException("@ EnumKey=" + enumKey + " Val=" + val + " Lang=" + Lang + " Error");
			}
		}
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
		Map map = new Map("Sys_Enum");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("枚举");
		map.setEnType(EnType.Sys);
		map.AddMyPK();

		map.AddTBString(SysEnumAttr.Lab, null, "Lab", true, false, 1, 80, 8);
		map.AddTBString(SysEnumAttr.EnumKey, null, "EnumKey", true, false, 1, 40, 8);
		map.AddTBInt(SysEnumAttr.IntKey, 0, "Val", true, false);
		map.AddTBString(SysEnumAttr.Lang, "CH", "语言", true, false, 0, 10, 8);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


	@Override
	protected boolean beforeUpdateInsertAction()
	{
		if (this.getLang() == null && this.getLang().equals(""))
		{
			this.setLang(BP.Port.WebUser.getSysLang());
		}

		this.setMyPK(this.getEnumKey() + "_" + this.getLang() + "_" + this.getIntKey());
		return super.beforeUpdateInsertAction();
	}
}