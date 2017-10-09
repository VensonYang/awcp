package BP.WF.Template;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/** 
 流程岗位属性
 流程的人员有两部分组成.	 
 记录了从一个流程到其他的多个流程.
 也记录了到这个流程的其他的流程.
 
*/
public class FlowEmp extends EntityMM
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
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
	/** 
	流程
	 
	*/
	public final String getFK_Flow()
	{
		return this.GetValStringByKey(FlowEmpAttr.FK_Flow);
	}
	public final void setFK_Flow(String value)
	{
		this.SetValByKey(FlowEmpAttr.FK_Flow, value);
	}
	/** 
	 人员
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStringByKey(FlowEmpAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		this.SetValByKey(FlowEmpAttr.FK_Emp, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 流程岗位属性
	 
	*/
	public FlowEmp()
	{
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

		Map map = new Map("WF_FlowEmp");
		map.setEnDesc("流程岗位属性信息");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap (Depositary.Application);

		map.AddDDLEntitiesPK(FlowEmpAttr.FK_Flow, null, "FK_Flow", new Flows(), true);
		map.AddDDLEntitiesPK(FlowEmpAttr.FK_Emp, null, "人员", new Emps(), true);
		this.set_enMap(map);

		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}