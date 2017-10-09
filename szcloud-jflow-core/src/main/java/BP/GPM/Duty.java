package BP.GPM;

import BP.DA.Depositary;
import BP.En.EntityNoName;
import BP.En.Map;

/** 
职务

*/
public class Duty extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 职务
	 
	*/
	public Duty()
	{
	}
	/** 
	 职务
	 
	 @param _No
	*/
	public Duty(String _No)
	{
		super(_No);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 职务Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Port_Duty");
		map.setEnDesc("职务");
		map.setCodeStruct("2");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);

		map.AddTBStringPK(DutyAttr.No, null, "编号", true, true, 2, 2, 2);
		map.AddTBString(DutyAttr.Name, null, "名称", true, false, 1, 50, 20);
		this.set_enMap(map);
		return this.get_enMap();
	}
}