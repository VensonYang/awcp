package BP.WF.Port;

import BP.DA.*;
import BP.En.*;

/** 
 岗位
 
*/
public class Station extends EntityNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 实现基本的方方法
	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	public final String getName()
	{
		return this.GetValStrByKey("Name");
	}
	public final int getGrade()
	{
		return this.getNo().length() / 2;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 岗位
	  
	*/
	public Station()
	{
	}
	/** 
	 岗位
	 
	 @param _No
	*/
	public Station(String _No)
	{
		super(_No);
	}
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Port_Station");
		map.setEnDesc("岗位"); // "岗位";
		map.setEnType( EnType.Admin);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity( Depositary.Application);
		map.setCodeStruct("2"); // 最大级别是7.

		map.AddTBStringPK(SimpleNoNameAttr.No, null, null, true, false, 2, 2, 2);
		map.AddTBString(SimpleNoNameAttr.Name, null, null, true, false, 2, 50, 250);
		map.AddDDLSysEnum(StationAttr.StaGrade, 0, "类型", true, false, StationAttr.StaGrade, "@1=高层岗@2=中层岗@3=执行岗");

	   //     map.AddDDLSysEnum("StaNWB", 0,"岗位标志", true, true);
		  //  map.AddDDLSysEnum("StaNWB", 0, "岗位标志", true, true, "StaNWB", "@1=内部岗@2=外部岗");


			//switch (BP.Sys.SystemConfig.SysNo)
			//{
			//    case BP.SysNoList.WF:
			//        map.AddDDLSysEnum(StationAttr.StaGrade, 0, "类型", true, false, StationAttr.StaGrade, "@1=总部@2=区域@3=中心");
			//        break;
			//    default:
			//        break;
			//}

			// map.AddTBInt(DeptAttr.Grade, 0, "级次", true, true);
			//map.AddBoolean(DeptAttr.IsDtl, true, "是否明细", true, true);
		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}