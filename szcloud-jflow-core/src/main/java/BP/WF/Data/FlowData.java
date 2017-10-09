package BP.WF.Data;

import javax.sound.sampled.Port;

import BP.En.*;
import BP.Port.Depts;
import BP.Sys.*;
import BP.WF.Template.Flows;

/** 
 报表
 
*/
public class FlowData extends BP.En.EntityOID
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region attrs
	/** 
	 流程发起人
	 
	*/
	public final String getFlowStarter()
	{
		return this.GetValStringByKey(FlowDataAttr.FlowStarter);
	}
	public final void setFlowStarter(String value)
	{
		this.SetValByKey(FlowDataAttr.FlowStarter, value);
	}
	public final String getFlowStartRDT()
	{
		return this.GetValStringByKey(FlowDataAttr.FlowStartRDT);
	}
	public final void setFlowStartRDT(String value)
	{
		this.SetValByKey(FlowDataAttr.FlowStartRDT, value);
	}
	/** 
	 流程结束者
	 
	*/
	public final String getFlowEnder()
	{
		return this.GetValStringByKey(FlowDataAttr.FlowEnder);
	}
	public final void setFlowEnder(String value)
	{
		this.SetValByKey(FlowDataAttr.FlowEnder, value);
	}
	/** 
	 流程结束时间
	 
	*/
	public final String getFlowEnderRDT()
	{
		return this.GetValStringByKey(FlowDataAttr.FlowEnderRDT);
	}
	public final void setFlowEnderRDT(String value)
	{
		this.SetValByKey(FlowDataAttr.FlowEnderRDT, value);
	}
	public final String getFK_Dept()
	{
		return this.GetValStringByKey(FlowDataAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		this.SetValByKey(FlowDataAttr.FK_Dept, value);
	}
	public final int getWFState()
	{
		return this.GetValIntByKey(FlowDataAttr.WFState);
	}
	public final void setWFState(int value)
	{
		this.SetValByKey(FlowDataAttr.WFState, value);
	}
	public final String getFK_Flow()
	{
		return this.GetValStringByKey(FlowDataAttr.FK_Flow);
	}
	public final void setFK_Flow(String value)
	{
		this.SetValByKey(FlowDataAttr.FK_Flow, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion attrs

	/** 
	 UI界面上的访问控制
	 
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region attrs - attrs
	public String RptName = null;
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("V_FlowData");
		map.setEnDesc( "流程数据");
		map.setEnType (EnType.View);

		map.AddTBIntPKOID(FlowDataAttr.OID, "WorkID");
		map.AddTBInt(FlowDataAttr.FID, 0, "FID", false, false);

		map.AddDDLEntities(FlowDataAttr.FK_Dept, null, "部门", new Depts(), false);
		map.AddTBString(FlowDataAttr.Title, null, "标题", true, true, 0, 100, 100,true);
		map.AddTBString(FlowDataAttr.FlowStarter, null, "发起人", true, true, 0, 100, 100);
		map.AddTBDateTime(FlowDataAttr.FlowStartRDT, null, "发起日期", true, true);
		map.AddDDLSysEnum(FlowDataAttr.WFState, 0, "流程状态", true, true, "WFStateApp");
		map.AddDDLEntities(FlowDataAttr.FK_NY, null, "年月", new BP.Pub.NYs(), false);
		map.AddDDLEntities(FlowDataAttr.FK_Flow, null, "流程", new Flows(), false);
		map.AddTBDateTime(FlowDataAttr.FlowEnderRDT, null, "结束日期", true, true);
		map.AddTBInt(FlowDataAttr.FlowEndNode, 0, "结束节点", true, true);
		map.AddTBInt(FlowDataAttr.FlowDaySpan, 0, "跨度(天)", true, true);
		map.AddTBInt(FlowDataAttr.MyNum, 1, "个数", true, true);
		map.AddTBString(FlowDataAttr.FlowEmps, null, "参与人", false, false, 0, 100, 100);

		map.AddSearchAttr(FlowDataAttr.FK_NY);
		map.AddSearchAttr(FlowDataAttr.WFState);
		map.AddSearchAttr(FlowDataAttr.FK_Flow);

		map.AddHidden(FlowDataAttr.FlowEmps, " LIKE ", "'%@@WebUser.No%'");

		RefMethod rm = new RefMethod();
		rm.Title = "流程运行轨迹";
		rm.ClassMethodName = this.toString() + ".DoOpen";
		map.AddRefMethod(rm);

		this.set_enMap( map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion attrs

	public final String DoOpen()
	{
		return BP.WF.Glo.getCCFlowAppPath()+"WF/WorkOpt/OneWork/Track.jsp?WorkID=" + this.getOID() + "&FK_Flow=" + this.getFK_Flow();
		
	}
}