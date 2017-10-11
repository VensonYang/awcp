package BP.WF.DTS;

import javax.sound.sampled.Port;

import BP.DA.*;
import BP.Port.*;
import BP.En.*;
import BP.Sys.*;
import BP.WF.Data.*;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.WFSta;
import BP.WF.Template.Flow;
import BP.WF.Template.Flows;
import BP.WF.Template.PubLib.WFState;

/** 
 装载已经完成的流程数据到WF_GenerWorkflow
 
*/
public class LoadNDxxRpt2GernerWorkFlow extends Method
{
	/** 
	 装载已经完成的流程数据到WF_GenerWorkflow
	 
	*/
	public LoadNDxxRpt2GernerWorkFlow()
	{
		this.Title = "装载已经完成的流程数据到WF_GenerWorkflow（升级扩展流程数据完成模式下的旧数据查询不到的问题）";
		this.Help = "升级扩展流程数据完成模式下的旧数据查询不到的问题。";
	}
	/** 
	 设置执行变量
	 
	 @return 
	*/
	@Override
	public void Init()
	{

	}
	/** 
	 当前的操纵员是否可以执行这个方法
	 
	*/
	@Override
	public boolean getIsCanDo()
	{
		if (WebUser.getNo().equals("admin"))
		{
			return true;
		}
		return false;
	}
	/** 
	 执行
	 
	 @return 返回执行结果
	*/
	@Override
	public Object Do()
	{
		Flows ens = new Flows();
		for (Flow en :Flows.convertFlows(ens) )
		{
			String sql = "SELECT * FROM "+en.getPTable()+" WHERE OID NOT IN (SELECT WorkID FROM WF_GenerWorkFlow WHERE FK_Flow='"+en.getNo()+"')";
			DataTable dt = DBAccess.RunSQLReturnTable(sql);

			for (DataRow dr : dt.Rows)
			{
				GenerWorkFlow gwf = new GenerWorkFlow();
				gwf.setWorkID(Long.parseLong(dr.getValue(NDXRptBaseAttr.OID).toString()));
				gwf.setFID(Long.parseLong(dr.getValue(NDXRptBaseAttr.FID).toString()));

				gwf.setFK_FlowSort(en.getFK_FlowSort());
				gwf.setFK_Flow(en.getNo());
				gwf.setFlowName(en.getName());
				gwf.setTitle(dr.getValue(NDXRptBaseAttr.Title).toString());
				gwf.setWFState(WFState.forValue(Integer.parseInt(dr.getValue(NDXRptBaseAttr.WFState).toString())));
				gwf.setWFSta(WFSta.Complete);

				gwf.setStarter(dr.getValue(NDXRptBaseAttr.FK_Dept).toString());
				gwf.setStarterName(dr.getValue(NDXRptBaseAttr.FK_Dept).toString());
				gwf.setRDT(dr.getValue(NDXRptBaseAttr.FK_Dept).toString());
				gwf.setFK_Node(Integer.parseInt(dr.getValue(NDXRptBaseAttr.FK_Dept).toString()));
				gwf.setFK_Dept(dr.getValue(NDXRptBaseAttr.FK_Dept).toString());

				Dept dept=null;
				try
				{
					dept = new Dept(gwf.getFK_Dept());
					gwf.setDeptName(dept.getName());
				}
				catch (java.lang.Exception e)
				{
					gwf.setDeptName(gwf.getFK_Dept());
				}

				gwf.setPRI(Integer.parseInt(dr.getValue(NDXRptBaseAttr.FK_Dept).toString()));

				//  gwf.SDTOfNode = dr[NDXRptBaseAttr.FK_Dept].ToString();
				// gwf.SDTOfFlow = dr[NDXRptBaseAttr.FK_Dept].ToString();

				gwf.setPFlowNo(dr.getValue(NDXRptBaseAttr.FK_Dept).toString());
				gwf.setPWorkID(Long.parseLong(dr.getValue(NDXRptBaseAttr.FK_Dept).toString()));
				gwf.setPNodeID(Integer.parseInt(dr.getValue(NDXRptBaseAttr.FK_Dept).toString()));
				gwf.setPEmp(dr.getValue(NDXRptBaseAttr.FK_Dept).toString());

				gwf.setCFlowNo(dr.getValue(NDXRptBaseAttr.CFlowNo).toString());
				gwf.setCWorkID(Long.parseLong(dr.getValue(NDXRptBaseAttr.CWorkID).toString()));

				gwf.setGuestNo(dr.getValue(NDXRptBaseAttr.GuestNo).toString());
				gwf.setGuestName(dr.getValue(NDXRptBaseAttr.GuestName).toString());
				gwf.setBillNo(dr.getValue(NDXRptBaseAttr.BillNo).toString());
				//gwf.FlowNote = dr[NDXRptBaseAttr.flowno].ToString();

				gwf.SetValByKey("Emps", dr.getValue(NDXRptBaseAttr.FlowEmps).toString());
				//   gwf.AtPara = dr[NDXRptBaseAttr.FK_Dept].ToString();
				//  gwf.GUID = dr[NDXRptBaseAttr.gu].ToString();
				gwf.Insert();
			}

		}
		return "执行成功...";
	}
}