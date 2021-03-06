package BP.WF.Data;

import java.io.IOException;

import TL.ContextHolderUtils;
import BP.En.AttrOfSearch;
import BP.En.EnType;
import BP.En.Entity;
import BP.En.Map;
import BP.En.QueryObject;
import BP.En.RefMethod;
import BP.En.UAC;
import BP.Port.WebUser;
import BP.Sys.PubClass;
import BP.WF.Glo;
import BP.WF.Entity.TaskSta;
import BP.WF.Entity.WFSta;
import BP.WF.Template.Flows;
import BP.WF.Template.PubLib.WFState;

/** 
我部门的待办

*/
public class MyDeptTodolist extends Entity
{

		///#region 基本属性
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}
	/** 
	 主键
	 
	*/
	@Override
	public String getPK()
	{
		return MyDeptTodolistAttr.WorkID;
	}
	/** 
	 工作流程编号
	 
	*/
	public final String getFK_Flow()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.FK_Flow);
	}
	public final void setFK_Flow(String value)
	{
		SetValByKey(MyDeptTodolistAttr.FK_Flow,value);
	}
	/** 
	 BillNo
	 
	*/
	public final String getBillNo()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.BillNo);
	}
	public final void setBillNo(String value)
	{
		SetValByKey(MyDeptTodolistAttr.BillNo, value);
	}
	/** 
	 流程名称
	 
	*/
	public final String getFlowName()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.FlowName);
	}
	public final void setFlowName(String value)
	{
		SetValByKey(MyDeptTodolistAttr.FlowName, value);
	}
	/** 
	 优先级
	 
	*/
	public final int getPRI()
	{
		return this.GetValIntByKey(MyDeptTodolistAttr.PRI);
	}
	public final void setPRI(int value)
	{
		SetValByKey(MyDeptTodolistAttr.PRI, value);
	}
	/** 
	 待办人员数量
	 
	*/
	public final int getTodoEmpsNum()
	{
		return this.GetValIntByKey(MyDeptTodolistAttr.TodoEmpsNum);
	}
	public final void setTodoEmpsNum(int value)
	{
		SetValByKey(MyDeptTodolistAttr.TodoEmpsNum, value);
	}
	/** 
	 待办人员列表
	 
	*/
	public final String getTodoEmps()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.TodoEmps);
	}
	public final void setTodoEmps(String value)
	{
		SetValByKey(MyDeptTodolistAttr.TodoEmps, value);
	}
	/** 
	 参与人
	 
	*/
	public final String getEmps()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.Emps);
	}
	public final void setEmps(String value)
	{
		SetValByKey(MyDeptTodolistAttr.Emps, value);
	}
	/** 
	 状态
	 
	*/
	public final TaskSta getTaskSta()
	{
		return TaskSta.forValue(this.GetValIntByKey(MyDeptTodolistAttr.TaskSta));
	}
	public final void setTaskSta(TaskSta value)
	{
		SetValByKey(MyDeptTodolistAttr.TaskSta, value.getValue());
	}
	/** 
	 类别编号
	 
	*/
	public final String getFK_Emp()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.FK_Emp);
	}
	public final void setFK_Emp(String value)
	{
		SetValByKey(MyDeptTodolistAttr.FK_Emp, value);
	}
	/** 
	 部门编号
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		SetValByKey(MyDeptTodolistAttr.FK_Dept,value);
	}
	/** 
	 标题
	 
	*/
	public final String getTitle()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.Title);
	}
	public final void setTitle(String value)
	{
		SetValByKey(MyDeptTodolistAttr.Title,value);
	}
	/** 
	 客户编号
	 
	*/
	public final String getGuestNo()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.GuestNo);
	}
	public final void setGuestNo(String value)
	{
		SetValByKey(MyDeptTodolistAttr.GuestNo, value);
	}
	/** 
	 客户名称
	 
	*/
	public final String getGuestName()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.GuestName);
	}
	public final void setGuestName(String value)
	{
		SetValByKey(MyDeptTodolistAttr.GuestName, value);
	}
	/** 
	 产生时间
	 
	*/
	public final String getRDT()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.RDT);
	}
	public final void setRDT(String value)
	{
		SetValByKey(MyDeptTodolistAttr.RDT,value);
	}
	/** 
	 节点应完成时间
	 
	*/
	public final String getSDTOfNode()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.SDTOfNode);
	}
	public final void setSDTOfNode(String value)
	{
		SetValByKey(MyDeptTodolistAttr.SDTOfNode, value);
	}
	/** 
	 流程应完成时间
	 
	*/
	public final String getSDTOfFlow()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.SDTOfFlow);
	}
	public final void setSDTOfFlow(String value)
	{
		SetValByKey(MyDeptTodolistAttr.SDTOfFlow, value);
	}
	/** 
	 流程ID
	 
	*/
	public final long getWorkID()
	{
		return this.GetValInt64ByKey(MyDeptTodolistAttr.WorkID);
	}
	public final void setWorkID(long value)
	{
		SetValByKey(MyDeptTodolistAttr.WorkID,value);
	}
	/** 
	 主线程ID
	 
	*/
	public final long getFID()
	{
		return this.GetValInt64ByKey(MyDeptTodolistAttr.FID);
	}
	public final void setFID(long value)
	{
		SetValByKey(MyDeptTodolistAttr.FID, value);
	}
	/** 
	 父节点ID 为或者-1.
	 
	*/
	public final long getCWorkID()
	{
		return this.GetValInt64ByKey(MyDeptTodolistAttr.CWorkID);
	}
	public final void setCWorkID(long value)
	{
		SetValByKey(MyDeptTodolistAttr.CWorkID, value);
	}
	/** 
	 PFlowNo
	 
	*/
	public final String getCFlowNo()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.CFlowNo);
	}
	public final void setCFlowNo(String value)
	{
		SetValByKey(MyDeptTodolistAttr.CFlowNo, value);
	}
	/** 
	 父节点流程编号.
	 
	*/
	public final long getPWorkID()
	{
		return this.GetValInt64ByKey(MyDeptTodolistAttr.PWorkID);
	}
	public final void setPWorkID(long value)
	{
		SetValByKey(MyDeptTodolistAttr.PWorkID, value);
	}
	/** 
	 父流程调用的节点
	 
	*/
	public final int getPNodeID()
	{
		return this.GetValIntByKey(MyDeptTodolistAttr.PNodeID);
	}
	public final void setPNodeID(int value)
	{
		SetValByKey(MyDeptTodolistAttr.PNodeID, value);
	}
	/** 
	 PFlowNo
	 
	*/
	public final String getPFlowNo()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.PFlowNo);
	}
	public final void setPFlowNo(String value)
	{
		SetValByKey(MyDeptTodolistAttr.PFlowNo, value);
	}
	/** 
	 吊起子流程的人员
	 
	*/
	public final String getPEmp()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.PEmp);
	}
	public final void setPEmp(String value)
	{
		SetValByKey(MyDeptTodolistAttr.PEmp, value);
	}
	/** 
	 发起人
	 
	*/
	public final String getStarter()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.Starter);
	}
	public final void setStarter(String value)
	{
		SetValByKey(MyDeptTodolistAttr.Starter, value);
	}
	/** 
	 发起人名称
	 
	*/
	public final String getStarterName()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.StarterName);
	}
	public final void setStarterName(String value)
	{
		this.SetValByKey(MyDeptTodolistAttr.StarterName, value);
	}
	/** 
	 发起人部门名称
	 
	*/
	public final String getDeptName()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.DeptName);
	}
	public final void setDeptName(String value)
	{
		this.SetValByKey(MyDeptTodolistAttr.DeptName, value);
	}
	/** 
	 当前节点名称
	 
	*/
	public final String getNodeName()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.NodeName);
	}
	public final void setNodeName(String value)
	{
		this.SetValByKey(MyDeptTodolistAttr.NodeName, value);
	}
	/** 
	 当前工作到的节点
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(MyDeptTodolistAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		SetValByKey(MyDeptTodolistAttr.FK_Node, value);
	}
	/** 
	 工作流程状态
	 
	*/
	public final WFState getWFState()
	{
		return WFState.forValue(this.GetValIntByKey(MyDeptTodolistAttr.WFState));
	}
	public final void setWFState(WFState value)
	{
		if (value == WFState.Complete)
		{
			SetValByKey(MyDeptTodolistAttr.WFSta, WFSta.Complete.getValue());
		}
		else if (value == WFState.Delete)
		{
			SetValByKey(MyDeptTodolistAttr.WFSta, WFSta.Delete.getValue());
		}
		else
		{
			SetValByKey(MyDeptTodolistAttr.WFSta, WFSta.Runing.getValue());
		}

		SetValByKey(MyDeptTodolistAttr.WFState, value.getValue());
	}
	/** 
	 状态(简单)
	 
	*/
	public final WFSta getWFSta()
	{
		return WFSta.forValue(this.GetValIntByKey(MyDeptTodolistAttr.WFSta));
	}
	public final void setWFSta(WFSta value)
	{
		SetValByKey(MyDeptTodolistAttr.WFSta, value.getValue());
	}
	public final String getWFStateText()
	{
	
		switch((WFState)this.getWFState())
		{
			case Complete:
				return "已完成";
			case Runing:
				return "在运行";
			case HungUp:
				return "挂起";
			case Askfor:
				return "加签";
			default:
				return "未判断";
		}
	}
	/** 
	 GUID
	 
	*/
	public final String getGUID()
	{
		return this.GetValStrByKey(MyDeptTodolistAttr.GUID);
	}
	public final void setGUID(String value)
	{
		SetValByKey(MyDeptTodolistAttr.GUID, value);
	}

		///#endregion


		///#region 参数属性.

		///#endregion 参数属性.


		///#region 构造函数
	/** 
	 产生的工作流程
	 
	*/
	public MyDeptTodolist()
	{
	}
	public MyDeptTodolist(long workId)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(MyDeptTodolistAttr.WorkID, workId);
		if (qo.DoQuery() == 0)
		{
			throw new RuntimeException("工作 MyDeptTodolist [" + workId + "]不存在。");
		}
	}
	/** 
	 执行修复
	 
	*/
	public final void DoRepair()
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

		Map map = new Map("WF_EmpWorks");
		map.setEnDesc("我部门的待办");
		map.setEnType(EnType.View);

		map.AddTBInt(MyDeptTodolistAttr.FID, 0, "FID", false, false);
		map.AddTBString(MyDeptTodolistAttr.Title, null, "流程标题", true, false, 0, 300, 10, true);
		map.AddDDLEntities(MyDeptTodolistAttr.FK_Flow, null, "流程", new Flows(), false);

		map.AddTBString(MyDeptTodolistAttr.Starter, null, "发起人编号", true, false, 0, 30, 10);
		map.AddTBString(MyDeptTodolistAttr.StarterName, null, "发起人名称", true, false, 0, 30, 10);
		map.AddTBString(MyDeptTodolistAttr.RDT, null, "发起时间", true, false, 0, 100, 10);

		map.AddTBString(MyDeptTodolistAttr.NodeName, null, "停留节点", true, false, 0, 100, 10);

		map.AddTBStringDoc(MyDeptTodolistAttr.FlowNote, null, "备注", true, false,true);

			//作为隐藏字段.
		map.AddTBString(MyDeptTodolistAttr.WorkerDept, null, "工作人员部门编号", false, false, 0, 30, 10);
		map.AddTBMyNum();
		map.AddDDLEntities(MyDeptTodolistAttr.FK_Emp, null, "当前处理人", new BP.WF.Data.MyDeptEmps(), false);
		map.AddTBIntPK(MyDeptTodolistAttr.WorkID, 0, "工作ID", true, true);

			//查询条件.
		map.AddSearchAttr(MyDeptTodolistAttr.FK_Flow);
		map.AddSearchAttr(MyDeptTodolistAttr.FK_Emp);


			//增加隐藏的查询条件.
		AttrOfSearch search = new AttrOfSearch(MyDeptTodolistAttr.WorkerDept, "部门", MyDeptTodolistAttr.WorkerDept, "=", WebUser.getFK_Dept(), 0, true);
		map.getAttrsOfSearch().Add(search);

		RefMethod rm = new RefMethod();
		rm.Title = "轨迹";
		rm.ClassMethodName = this.toString() + ".DoTrack";
		rm.Icon = Glo.getCCFlowAppPath()+"WF/Img/FileType/doc.gif";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/CC.gif";
		rm.Title = "移交";
		rm.ClassMethodName = this.toString() + ".DoShift";
		rm.getHisAttrs().AddDDLEntities("ToEmp", null, "移交给:", new Flows(), true);
		rm.getHisAttrs().AddTBString("Note", null, "移交原因", true, false, 0, 300, 100);
		map.AddRefMethod(rm);

			//rm = new RefMethod();
			//rm.Title = "跳转";
			//rm.ClassMethodName = this.ToString() + ".DoSkip";
			//rm.Icon = "/WF/Img/FileType/doc.gif";
			//map.AddRefMethod(rm);

		this.set_enMap(map);
		return this.get_enMap();
	}

		///#endregion


		///#region 执行诊断
	public final String DoTrack()
	{
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(), Glo.getCCFlowAppPath()+"/WF/WFRpt.jsp?WorkID=" + this.getWorkID() + "&FID="+this.getFID()+"&FK_Flow="+this.getFK_Flow(),900,800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/** 
	 执行移交
	 
	 @param ToEmp
	 @param Note
	 @return 
	*/
	public final String DoShift(String ToEmp, String Note)
	{
		try
		{
			BP.WF.Dev2Interface.Node_Shift(this.getFK_Flow(), this.getFK_Node(), this.getWorkID(), this.getFID(), ToEmp, Note);
			return "移交成功";
		}
		catch(RuntimeException ex)
		{
			return "移交失败@" + ex.getMessage();
		}
	}
	public final String DoSkip()
	{
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(), Glo.getCCFlowAppPath()+"WF/Admin/FlowDB/FlowSkip.aspx?WorkID=" + this.getWorkID() + "&FID=" + this.getFID() + "&FK_Flow=" + this.getFK_Flow() + "&FK_Node=" + this.getFK_Node(), 900, 800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

		///#endregion
}
