package BP.En;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.Map;

public abstract class SimpleNoNameFix extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}

	public SimpleNoNameFix()
	{
	}
	public SimpleNoNameFix(String _No)
	{
		super(_No);
	}
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap()!=null)
		{
			return this.get_enMap();
		}
		Map map = new Map(this.getPhysicsTable());
		map.setEnDesc(this.getDesc());
		map.setCodeStruct("2");

		map.setIsAutoGenerNo(true);

		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnType(EnType.App);

		map.setCodeStruct("3");
		map.setIsAutoGenerNo(true);

		 map.AddTBStringPK(SimpleNoNameFixAttr.No, null, null, true, true, 1, 30, 3);
		map.AddTBString(SimpleNoNameFixAttr.Name, null, null, true, false, 1, 60, 500);
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 需要子类重写的方法
	/** 
	 指定表
	 
	*/
	public abstract String getPhysicsTable();
	/** 
	 描述
	 
	*/
	public abstract String getDesc();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region  重写基类的方法。
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}