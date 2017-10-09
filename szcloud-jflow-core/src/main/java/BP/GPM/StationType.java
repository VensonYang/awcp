package BP.GPM;

import BP.DA.Depositary;
import BP.En.EntityNoName;
import BP.En.Map;

/** 
岗位类型

*/
public class StationType extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getFK_StationType()
	{
		return this.GetValStrByKey(StationAttr.FK_StationType);
	}
	public final void setFK_StationType(String value)
	{
		this.SetValByKey(StationAttr.FK_StationType, value);
	}

	public final String getFK_StationTypeText()
	{
		return this.GetValRefTextByKey(StationAttr.FK_StationType);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region  构造方法
	/** 
	 岗位类型
	 
	*/
	public StationType()
	{
	}
	/** 
	 岗位类型
	 
	 @param _No
	*/
	public StationType(String _No)
	{
		super(_No);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 岗位类型Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Port_StationType");
		map.setEnDesc("岗位类型");
		map.setCodeStruct("2");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);

		map.AddTBStringPK(StationTypeAttr.No, null, "编号", true, true, 2, 2, 2);
		map.AddTBString(StationTypeAttr.Name, null, "名称", true, false, 1, 50, 20);
		this.set_enMap(map);
		return this.get_enMap();
	}
}