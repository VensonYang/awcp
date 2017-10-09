package BP.WF.Port;

import BP.DA.*;
import BP.En.*;

/** 
 人员岗位 的摘要说明。
 
*/
public class EmpStation extends Entity
{
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


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	/** 
	 工作人员ID
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStringByKey(EmpStationAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		SetValByKey(EmpStationAttr.FK_Emp, value);
	}
	public final String getFK_StationT()
	{
		return this.GetValRefTextByKey(EmpStationAttr.FK_Station);
	}
	/** 
	工作岗位
	 
	*/
	public final String getFK_Station()
	{
		return this.GetValStringByKey(EmpStationAttr.FK_Station);
	}
	public final void setFK_Station(String value)
	{
		SetValByKey(EmpStationAttr.FK_Station, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 扩展属性

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造函数
	/** 
	 工作人员岗位
	  
	*/
	public EmpStation()
	{
	}
	/** 
	 工作人员工作岗位对应
	 
	 @param _empoid 工作人员ID
	 @param wsNo 工作岗位编号 	
	*/
	public EmpStation(String _empoid, String wsNo)
	{
		this.setFK_Emp(_empoid);
		this.setFK_Station(wsNo);
		if (this.Retrieve() == 0)
		{
			this.Insert();
		}
	}
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Port_EmpStation");
		map.setEnDesc ("人员岗位");
		map.setEnType(EnType.Dot2Dot); //实体类型，admin 系统管理员表，PowerAble 权限管理表,也是用户表,你要想把它加入权限管理里面请在这里设置。。

		  //  map.AddDDLEntitiesPK(EmpStationAttr.FK_Emp, null, "操作员", new Emps(), true);

		map.AddTBStringPK(EmpDeptAttr.FK_Emp, null, "操作员", false, false, 1, 15, 1);

		map.AddDDLEntitiesPK(EmpStationAttr.FK_Station, null, "工作岗位", new Stations(), true);

		map.AddSearchAttr(EmpStationAttr.FK_Station);

		this.set_enMap( map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}