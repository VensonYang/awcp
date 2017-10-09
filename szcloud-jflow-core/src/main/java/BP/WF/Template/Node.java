package BP.WF.Template;

import javax.servlet.http.HttpServletRequest;

import BP.DA.DBAccess;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.DA.Depositary;
import BP.DA.Log;
import BP.En.Entity;
import BP.En.EntityOIDNameAttr;
import BP.En.FieldTypeS;
import BP.En.Map;
import BP.En.UAC;
import BP.En.UIContralType;
import BP.Port.WebUser;
import BP.Sys.SystemConfig;
import BP.Sys.Frm.GroupFieldAttr;
import BP.Sys.Frm.GroupFields;
import BP.Sys.Frm.MapAttr;
import BP.Sys.Frm.MapAttrAttr;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDtls;
import BP.Sys.Frm.MapExts;
import BP.Sys.Frm.MapFrames;
import BP.Sys.Frm.MapM2Ms;
import BP.Tools.StringHelper;
import BP.WF.Data.BillTemplate;
import BP.WF.Data.BillTemplates;
import BP.WF.Entity.FrmWorkCheckAttr;
import BP.WF.Entity.FrmWorkCheckSta;
import BP.WF.Template.AccepterRole.NodeDept;
import BP.WF.Template.AccepterRole.NodeDepts;
import BP.WF.Template.AccepterRole.NodeEmp;
import BP.WF.Template.AccepterRole.NodeEmps;
import BP.WF.Template.CC.CC;
import BP.WF.Template.Ext.OutTimeDeal;
import BP.WF.Template.Form.FrmNode;
import BP.WF.Template.Form.FrmNodes;
import BP.WF.Template.Form.Frms;
import BP.WF.Template.PubLib.BatchRole;
import BP.WF.Template.PubLib.BlockModel;
import BP.WF.Template.PubLib.BtnAttr;
import BP.WF.Template.PubLib.CCRole;
import BP.WF.Template.PubLib.CCWriteTo;
import BP.WF.Template.PubLib.CancelRole;
import BP.WF.Template.PubLib.DataStoreModel;
import BP.WF.Template.PubLib.DelWorkFlowRole;
import BP.WF.Template.PubLib.DeliveryWay;
import BP.WF.Template.PubLib.FlowAppType;
import BP.WF.Template.PubLib.NodeAttr;
import BP.WF.Template.PubLib.NodeFormType;
import BP.WF.Template.PubLib.NodePosType;
import BP.WF.Template.PubLib.NodeWorkType;
import BP.WF.Template.PubLib.PrintDocEnable;
import BP.WF.Template.PubLib.ReadReceipts;
import BP.WF.Template.PubLib.ReturnRole;
import BP.WF.Template.PubLib.RunModel;
import BP.WF.Template.PubLib.SaveModel;
import BP.WF.Template.PubLib.SubFlowStartWay;
import BP.WF.Template.PubLib.SubThreadType;
import BP.WF.Template.PubLib.ThreadKillRole;
import BP.WF.Template.PubLib.TodolistModel;
import BP.WF.Template.PubLib.TurnToDeal;
import BP.WF.Template.PubLib.WhenNoWorker;
import BP.WF.Template.WorkBase.GEStartWork;
import BP.WF.Template.WorkBase.GEWork;
import BP.WF.Template.WorkBase.StartWorkAttr;
import BP.WF.Template.WorkBase.Work;
import BP.WF.Template.WorkBase.WorkAttr;
import BP.WF.Template.WorkBase.Works;
import TL.ContextHolderUtils;

/**
 * 这里存放每个节点的信息.
 * 
 */
public class Node extends Entity {

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 执行节点事件.
	/**
	 * 执行运动事件
	 * 
	 * @param doType
	 *            事件类型
	 * @param en
	 *            实体参数 //public string DoNodeEventEntity(string doType,Node
	 *            currNode, Entity en, string atPara) //{ // if
	 *            (this.NDEventEntity != null) // return
	 *            NDEventEntity.DoIt(doType,currNode, en, atPara);
	 */

	// return this.MapData.FrmEvents.DoEventNode(doType, en, atPara);
	// }
	// private BP.WF.NodeEventBase _NDEventEntity = null;
	///// <summary>
	///// 节点实体类，没有就返回为空.
	///// </summary>
	// private BP.WF.NodeEventBase NDEventEntity11
	// {
	// get
	// {
	// if (_NDEventEntity == null && this.NodeMark!="" &&
	// this.NodeEventEntity!="" )
	// _NDEventEntity = BP.WF.Glo.GetNodeEventEntityByEnName(
	// this.NodeEventEntity);

	// return _NDEventEntity;
	// }
	// }
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 执行节点事件.

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 参数属性
	/**
	 * 方向条件控制规则
	 * 
	 */
	public final CondModel getCondModel() {
		return CondModel.forValue(this.GetValIntByKey(NodeAttr.CondModel));
	}

	/**
	 * 超时处理方式
	 * 
	 */
	public final OutTimeDeal getHisOutTimeDeal() {
		return OutTimeDeal.forValue(this.GetValIntByKey(NodeAttr.OutTimeDeal));
	}

	public final void setHisOutTimeDeal(OutTimeDeal value) {
		this.SetValByKey(NodeAttr.OutTimeDeal, value.getValue());
	}

	/**
	 * 子线程类型
	 * 
	 */
	public final SubThreadType getHisSubThreadType() {
		return SubThreadType.forValue(this.GetValIntByKey(NodeAttr.SubThreadType));
	}

	public final void setHisSubThreadType(SubThreadType value) {
		this.SetValByKey(NodeAttr.SubThreadType, value.getValue());
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 外键属性.
	public final CC getHisCC() {
		Object tempVar = this.GetRefObject("HisCC");
		CC obj = (CC) ((tempVar instanceof CC) ? tempVar : null);
		if (obj == null) {
			obj = new CC();
			obj.setNodeID(this.getNodeID());
			obj.Retrieve();
			this.SetRefObject("HisCC", obj);
		}
		return obj;
	}

	/**
	 * 他的将要转向的方向集合 如果他没有到转向方向,他就是结束节点. 没有生命周期的概念,全部的节点.
	 * 
	 */
	public final Nodes getHisToNodes() {
		Object tempVar = this.GetRefObject("HisToNodes");
		Nodes obj = (Nodes) ((tempVar instanceof Nodes) ? tempVar : null);
		if (obj == null) {
			obj = new Nodes();
			obj.AddEntities(this.getHisToNDs());
			this.SetRefObject("HisToNodes", obj);
		}
		return obj;
	}

	/**
	 * 他的工作
	 * 
	 */
	public final Work getHisWork() {
		Work obj = null;
		if (this.getIsStartNode()) {
			obj = new GEStartWork(this.getNodeID(), this.getNodeFrmID());
			obj.setHisNode(this);
			obj.setNodeID(this.getNodeID());
			return obj;

			// this.SetRefObject("HisWork", obj);
		} else {
			obj = new GEWork(this.getNodeID(), this.getNodeFrmID());
			obj.setHisNode(this);
			obj.setNodeID(this.getNodeID());
			return obj;
			// this.SetRefObject("HisWork", obj);
		}
		// return obj;
		// 放入缓存就没有办法执行数据的clone.
		// Work obj = this.GetRefObject("HisWork") as Work;
		// if (obj == null)
		// {
		// if (this.IsStartNode)
		// {
		// obj = new BP.WF.GEStartWork(this.NodeID);
		// obj.HisNode = this;
		// obj.NodeID = this.NodeID;
		// this.SetRefObject("HisWork", obj);
		// }
		// else
		// {
		// obj = new BP.WF.GEWork(this.NodeID);
		// obj.HisNode = this;
		// obj.NodeID = this.NodeID;
		// this.SetRefObject("HisWork", obj);
		// }
		// }
		//// obj.GetNewEntities.GetNewEntity;
		//// obj.Row = null;
		// return obj;
	}

	/**
	 * 他的工作s
	 * 
	 */
	public final Works getHisWorks() {
		Works obj = (Works) ((this.getHisWork().getGetNewEntities() instanceof Works)
				? this.getHisWork().getGetNewEntities() : null);
		return obj;
		//// Works obj = this.GetRefObject("HisWorks") as Works;
		//// if (obj == null)
		//// {
		// this.SetRefObject("HisWorks",obj);
		// }
		// return obj;
	}

	/**
	 * 流程
	 * 
	 */
	public final Flow getHisFlow() {
		Object tempVar = this.GetRefObject("Flow");
		Flow obj = (Flow) ((tempVar instanceof Flow) ? tempVar : null);
		if (obj == null) {
			obj = new Flow(this.getFK_Flow());
			this.SetRefObject("Flow", obj);
		}
		return obj;
	}

	/**
	 * HisFrms
	 * 
	 */
	public final Frms getHisFrms() {
		Frms frms = new Frms();
		FrmNodes fns = new FrmNodes(this.getFK_Flow(), this.getNodeID());
		for (FrmNode fn : FrmNodes.convertFrmNodes(fns)) {
			frms.AddEntity(fn.getHisFrm());
		}
		return frms;

		// this.SetRefObject("HisFrms", obj);
		// Frms obj = this.GetRefObject("HisFrms") as Frms;
		// if (obj == null)
		// {
		// obj = new Frms();
		// FrmNodes fns = new FrmNodes(this.NodeID);
		// foreach (FrmNode fn in fns)
		// obj.AddEntity(fn.HisFrm);
		// this.SetRefObject("HisFrms", obj);
		// }
		// return obj;
	}

	/**
	 * 他的将要来自的方向集合 如果他没有到来的方向,他就是开始节点.
	 * 
	 */
	public final Nodes getFromNodes() {
		Object tempVar = this.GetRefObject("HisFromNodes");
		Nodes obj = (Nodes) ((tempVar instanceof Nodes) ? tempVar : null);
		if (obj == null) {
			// 根据方向生成到达此节点的节点。
			Directions ens = new Directions();
			if (this.getIsStartNode()) {
				obj = new Nodes();
			} else {
				obj = ens.GetHisFromNodes(this.getNodeID());
			}
			this.SetRefObject("HisFromNodes", obj);
		}
		return obj;
	}

	public final BillTemplates getBillTemplates() {
		Object tempVar = this.GetRefObject("BillTemplates");
		BillTemplates obj = (BillTemplates) ((tempVar instanceof BillTemplates) ? tempVar : null);
		if (obj == null) {
			obj = new BillTemplates(this.getNodeID());
			this.SetRefObject("BillTemplates", obj);
		}
		return obj;
	}

	public final NodeStations getNodeStations() {
		Object tempVar = this.GetRefObject("NodeStations");
		NodeStations obj = (NodeStations) ((tempVar instanceof NodeStations) ? tempVar : null);
		if (obj == null) {
			obj = new NodeStations(this.getNodeID());
			this.SetRefObject("NodeStations", obj);
		}
		return obj;
	}

	public final NodeDepts getNodeDepts() {
		Object tempVar = this.GetRefObject("NodeDepts");
		NodeDepts obj = (NodeDepts) ((tempVar instanceof NodeDepts) ? tempVar : null);
		if (obj == null) {
			obj = new NodeDepts(this.getNodeID());
			this.SetRefObject("NodeDepts", obj);
		}
		return obj;
	}

	public final NodeEmps getNodeEmps() {
		Object tempVar = this.GetRefObject("NodeEmps");
		NodeEmps obj = (NodeEmps) ((tempVar instanceof NodeEmps) ? tempVar : null);
		if (obj == null) {
			obj = new NodeEmps(this.getNodeID());
			this.SetRefObject("NodeEmps", obj);
		}
		return obj;
	}

	public final FrmNodes getFrmNodes() {
		Object tempVar = this.GetRefObject("FrmNodes");
		FrmNodes obj = (FrmNodes) ((tempVar instanceof FrmNodes) ? tempVar : null);
		if (obj == null) {
			obj = new FrmNodes(this.getFK_Flow(), this.getNodeID());
			this.SetRefObject("FrmNodes", obj);
		}
		return obj;
	}

	public final MapData getMapData() {
		Object tempVar = this.GetRefObject("MapData");
		MapData obj = (MapData) ((tempVar instanceof MapData) ? tempVar : null);
		if (obj == null) {
			obj = new MapData("ND" + this.getNodeID());
			this.SetRefObject("MapData", obj);
		}
		return obj;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 初试化全局的 Node
	@Override
	public String getPK() {
		return "NodeID";
	}

	/**
	 * UI界面上的访问控制
	 * 
	 */
	@Override
	public UAC getHisUAC() {
		UAC uac = new UAC();
		if (WebUser.getNo().equals("admin")) {
			uac.IsUpdate = true;
		}
		return uac;
	}

	/**
	 * 初试化全局的
	 * 
	 * @return
	 */
	public final NodePosType GetHisNodePosType() {
		String nodeid = (new Integer(this.getNodeID())).toString();
		if (nodeid.substring(nodeid.length() - 2).equals("01")) {
			return NodePosType.Start;
		}

		if (this.getFromNodes().size() == 0) {
			return NodePosType.Mid;
		}

		if (this.getHisToNodes().size() == 0) {
			return NodePosType.End;
		}
		return NodePosType.Mid;
	}

	/**
	 * 检查流程，修复必要的计算字段信息.
	 * 
	 * @param fl
	 *            流程
	 * @return 返回检查信息
	 */
	public static String CheckFlow(Flow fl) {
		String sqls = "UPDATE WF_Node SET IsCCFlow=0";
		sqls += "@UPDATE WF_Node  SET IsCCFlow=1 WHERE NodeID IN (SELECT NodeID FROM WF_Cond a WHERE a.NodeID= NodeID AND CondType=1 )";
		BP.DA.DBAccess.RunSQLs(sqls);
		// 删除必要的数据.
		DBAccess.RunSQL("DELETE FROM WF_NodeEmp WHERE FK_Emp  not in (select No from Port_Emp)");
		DBAccess.RunSQL("DELETE FROM WF_Emp WHERE NO not in (select No from Port_Emp )");
		DBAccess.RunSQL(
				"UPDATE WF_Emp set Name=(SELECT Name From Port_Emp where Port_Emp.No=WF_Emp.No),FK_Dept=(select FK_Dept from Port_Emp where Port_Emp.No=WF_Emp.No)");

		Nodes nds = new Nodes();
		nds.Retrieve(NodeAttr.FK_Flow, fl.getNo());

		FlowSort fs = new FlowSort(fl.getFK_FlowSort());

		if (nds.size() == 0) {
			return "流程[" + fl.getNo() + fl.getName() + "]中没有节点数据，您需要注册一下这个流程。";
		}

		// 更新是否是有完成条件的节点。
		DBAccess.RunSQL("UPDATE WF_Node SET IsCCFlow=0  WHERE FK_Flow='" + fl.getNo() + "'");
		DBAccess.RunSQL("DELETE FROM WF_Direction WHERE Node=0 OR ToNode=0");
		DBAccess.RunSQL("DELETE FROM WF_Direction WHERE Node  NOT IN (SELECT NODEID FROM WF_Node )");
		DBAccess.RunSQL("DELETE FROM WF_Direction WHERE ToNode  NOT IN (SELECT NODEID FROM WF_Node) ");

		// 单据信息，岗位，节点信息。
		for (Node nd : Nodes.convertNodes(nds)) {
			DBAccess.RunSQL(
					"UPDATE WF_Node SET FK_FlowSort='" + fl.getFK_FlowSort() + "',FK_FlowSortT='" + fs.getName() + "'");

			MapData md = new MapData();
			md.setNo("ND" + nd.getNodeID());
			if (md.getIsExits() == false) {
				nd.CreateMap();
			}

			// 工作岗位。
			NodeStations stas = new NodeStations(nd.getNodeID());
			String strs = "";
			for (NodeStation sta : NodeStations.convertNodeStations(stas)) {
				strs += "@" + sta.getFK_Station();
			}
			nd.setHisStas(strs);

			// 工作部门。
			NodeDepts ndpts = new NodeDepts(nd.getNodeID());
			strs = "";
			for (NodeDept ndp : NodeDepts.convertNodeDepts(ndpts)) {
				strs += "@" + ndp.getFK_Dept();
			}

			nd.setHisDeptStrs(strs);

			// 可执行人员。
			NodeEmps ndemps = new NodeEmps(nd.getNodeID());
			strs = "";
			for (NodeEmp ndp : NodeEmps.convertNodeEmps(ndemps)) {
				strs += "@" + ndp.getFK_Emp();
			}
			// nd.HisEmps = strs;

			// 子流程。
			NodeFlows ndflows = new NodeFlows(nd.getNodeID());
			strs = "";
			for (NodeFlow ndp : NodeFlows.convertNodeFlows(ndflows)) {
				strs += "@" + ndp.getFK_Flow();
			}
			nd.setHisSubFlows(strs);

			// 节点方向.
			strs = "";
			Directions dirs = new Directions(nd.getNodeID(), 0);
			for (Direction dir : Directions.convertDirections(dirs)) {
				strs += "@" + dir.getToNode();
			}
			nd.setHisToNDs(strs);

			// 单据
			strs = "";
			BillTemplates temps = new BillTemplates(nd);
			for (BillTemplate temp : BillTemplates.convertBillTemplates(temps)) {
				strs += "@" + temp.getNo();
			}
			nd.setHisBillIDs(strs);

			// 检查节点的位置属性。
			nd.setHisNodePosType(nd.GetHisNodePosType());
			nd.DirectUpdate();
		}

		// 处理岗位分组.
		String sql = "SELECT HisStas, COUNT(*) as NUM FROM WF_Node WHERE FK_Flow='" + fl.getNo() + "' GROUP BY HisStas";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		for (DataRow dr : dt.Rows) {
			String stas = dr.getValue(0).toString();
			String nodes = "";
			for (Node nd : Nodes.convertNodes(nds)) {
				if (nd.getHisStas().equals(stas)) {
					nodes += "@" + nd.getNodeID();
				}
			}

			for (Node nd : Nodes.convertNodes(nds)) {
				if (nodes.contains("@" + (new Integer(nd.getNodeID())).toString()) == false) {
					continue;
				}

				nd.setGroupStaNDs(nodes);
				nd.DirectUpdate();
			}
		}

		// 判断流程的类型
		sql = "SELECT Name FROM WF_Node WHERE (NodeWorkType=" + NodeWorkType.StartWorkFL.getValue()
				+ " OR NodeWorkType=" + NodeWorkType.WorkFHL.getValue() + " OR NodeWorkType="
				+ NodeWorkType.WorkFL.getValue() + " OR NodeWorkType=" + NodeWorkType.WorkHL.getValue()
				+ ") AND (FK_Flow='" + fl.getNo() + "')";
		dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		// if (dt.Rows.Count == 0)
		// fl.HisFlowType = FlowType.Panel;
		// else
		// fl.HisFlowType = FlowType.FHL;

		fl.DirectUpdate();
		return null;
	}

	@Override
	protected boolean beforeUpdate() {
		if (this.getIsStartNode()) {
			this.SetValByKey(BtnAttr.ReturnRole, ReturnRole.CanNotReturn.getValue());
			this.SetValByKey(BtnAttr.ShiftEnable, 0);
			// this.SetValByKey(BtnAttr.CCRole, 0);
			this.SetValByKey(BtnAttr.EndFlowEnable, 0);
		}

		// 给icon设置默认值.
		if (this.GetValStrByKey(NodeAttr.ICON).equals("")) {
			this.setICON("/WF/Data/NodeIcon/审核.png");
		}

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 如果是数据合并模式，就要检查节点中是否有子线程，如果有子线程就需要单独的表.
		if (this.getHisRunModel() == RunModel.SubThread) {
			MapData md = new MapData("ND" + this.getNodeID());
			if (!md.getPTable().equals("ND" + this.getNodeID())) {
				md.setPTable("ND" + this.getNodeID());
				md.Update();
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 如果是数据合并模式，就要检查节点中是否有子线程，如果有子线程就需要单独的表.

		// 更新版本号.
		Flow.UpdateVer(this.getFK_Flow());

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region //获得 NEE 实体.
		// if (string.IsNullOrEmpty(this.NodeMark) == false)
		// {
		// object obj =
		// Glo.GetNodeEventEntityByNodeMark(fl.FlowMark,this.NodeMark);
		// if (obj == null)
		// throw new Exception("@节点标记错误：没有找到该节点标记(" + this.NodeMark +
		// ")的节点事件实体.");
		// this.NodeEventEntity = obj.ToString();
		// }
		// else
		// {
		// this.NodeEventEntity = "";
		// }
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 同步事件实体.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 更新流程判断条件的标记。
		DBAccess.RunSQL("UPDATE WF_Node SET IsCCFlow=0  WHERE FK_Flow='" + this.getFK_Flow() + "'");
		DBAccess.RunSQL(
				"UPDATE WF_Node SET IsCCFlow=1 WHERE NodeID IN (SELECT NodeID FROM WF_Cond WHERE CondType=1) AND FK_Flow='"
						+ this.getFK_Flow() + "'");
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion

		Flow fl = new Flow(this.getFK_Flow());

		Node.CheckFlow(fl);
		this.setFlowName(fl.getName());

		DBAccess.RunSQL("UPDATE Sys_MapData SET Name='" + this.getName() + "' WHERE No='ND" + this.getNodeID() + "'");
		switch (this.getHisRunModel()) {
		case Ordinary:
			if (this.getIsStartNode()) {
				this.setHisNodeWorkType(NodeWorkType.StartWork);
			} else {
				this.setHisNodeWorkType(NodeWorkType.Work);
			}
			break;
		case FL:
			if (this.getIsStartNode()) {
				this.setHisNodeWorkType(NodeWorkType.StartWorkFL);
			} else {
				this.setHisNodeWorkType(NodeWorkType.WorkFL);
			}
			break;
		case HL:
			// if (this.IsStartNode)
			// throw new Exception("@您不能设置开始节点为合流节点。");
			// else
			// this.HisNodeWorkType = NodeWorkType.WorkHL;
			break;
		case FHL:
			// if (this.IsStartNode)
			// throw new Exception("@您不能设置开始节点为分合流节点。");
			// else
			// this.HisNodeWorkType = NodeWorkType.WorkFHL;
			break;
		case SubThread:
			this.setHisNodeWorkType(NodeWorkType.SubThreadWork);
			break;
		default:
			throw new RuntimeException("eeeee");
		}
		return super.beforeUpdate();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 基本属性
	/**
	 * 审核组件
	 * 
	 */
	public final FrmWorkCheckSta getFrmWorkCheckSta() {
		return FrmWorkCheckSta.forValue(this.GetValIntByKey(NodeAttr.FWCSta));
	}

	/**
	 * 内部编号
	 * 
	 */
	public final String getNo() {
		try {
			return (new Integer(this.getNodeID())).toString()
					.substring((new Integer(this.getNodeID())).toString().length() - 2);
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLineInfo(ex.getMessage() + " - " + this.getNodeID());
			throw new RuntimeException("@没有获取到它的NodeID = " + this.getNodeID());
		}
	}

	/**
	 * 自动跳转规则0-处理人就是提交人
	 * 
	 */
	public final boolean getAutoJumpRole0() {
		return this.GetValBooleanByKey(NodeAttr.AutoJumpRole0);
	}

	public final void setAutoJumpRole0(boolean value) {
		this.SetValByKey(NodeAttr.AutoJumpRole0, value);
	}

	/**
	 * 自动跳转规则1-处理人已经出现过
	 * 
	 */
	public final boolean getAutoJumpRole1() {
		return this.GetValBooleanByKey(NodeAttr.AutoJumpRole1);
	}

	public final void setAutoJumpRole1(boolean value) {
		this.SetValByKey(NodeAttr.AutoJumpRole1, value);
	}

	/**
	 * 自动跳转规则2-处理人与上一步相同
	 * 
	 */
	public final boolean getAutoJumpRole2() {
		return this.GetValBooleanByKey(NodeAttr.AutoJumpRole2);
	}

	public final void setAutoJumpRole2(boolean value) {
		this.SetValByKey(NodeAttr.AutoJumpRole2, value);
	}

	/**
	 * 启动参数
	 * 
	 */
	public final String getSubFlowStartParas() {
		return this.GetValStringByKey(NodeAttr.SubFlowStartParas);
	}

	public final void setSubFlowStartParas(String value) {
		this.SetValByKey(NodeAttr.SubFlowStartParas, value);
	}

	/**
	 * 子线程启动方式
	 * 
	 */
	public final SubFlowStartWay getSubFlowStartWay() {
		return SubFlowStartWay.forValue(this.GetValIntByKey(NodeAttr.SubFlowStartWay));
	}

	public final void setSubFlowStartWay(SubFlowStartWay value) {
		this.SetValByKey(NodeAttr.SubFlowStartWay, value.getValue());
	}

	public final NodeFormType getHisFormType() {
		return NodeFormType.forValue(this.GetValIntByKey(NodeAttr.FormType));
	}

	public final void setHisFormType(NodeFormType value) {
		this.SetValByKey(NodeAttr.FormType, value.getValue());
	}

	/**
	 * OID
	 * 
	 */
	public final int getNodeID() {
		return this.GetValIntByKey(NodeAttr.NodeID);
	}

	public final void setNodeID(int value) {
		this.SetValByKey(NodeAttr.NodeID, value);
	}

	public final boolean getIsEnableTaskPool() {
		if (this.getTodolistModel() == TodolistModel.Sharing) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 节点头像
	 * 
	 */
	public final String getICON() {
		String s = this.GetValStrByKey(NodeAttr.ICON);
		if (StringHelper.isNullOrEmpty(s)) {
			if (this.getIsStartNode()) {
				return "/WF/Data/NodeIcon/审核.png";
			} else {
				return "/WF/Data/NodeIcon/前台.png";
			}
		}
		return s;
	}

	public final void setICON(String value) {
		this.SetValByKey(NodeAttr.ICON, value);
	}

	/**
	 * FormUrl
	 * 
	 */
	public final String getFormUrl() {
		String str = this.GetValStrByKey(NodeAttr.FormUrl);
		String sdkFromServHost = (String) BP.Sys.SystemConfig.getAppSettings().get("SDKFromServHost");
		if (sdkFromServHost == null) {
			HttpServletRequest request = ContextHolderUtils.getRequest();
			String path = request.getContextPath();
			sdkFromServHost = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path;
		}

		str = str.replace("@SDKFromServHost", sdkFromServHost);
		return str;
	}

	public final void setFormUrl(String value) {
		this.SetValByKey(NodeAttr.FormUrl, value);
	}

	public final NodeFormType getFormType() {
		return NodeFormType.forValue(this.GetValIntByKey(NodeAttr.FormType));
	}

	public final void setFormType(NodeFormType value) {
		this.SetValByKey(NodeAttr.FormType, value);
	}

	/**
	 * 名称
	 * 
	 */
	public final String getName() {
		return this.GetValStrByKey(EntityOIDNameAttr.Name);
	}

	public final void setName(String value) {
		this.SetValByKey(EntityOIDNameAttr.Name, value);
	}

	/**
	 * 需要天数（限期）
	 * 
	 */
	public final float getDeductDays() {
		float i = this.GetValFloatByKey(NodeAttr.DeductDays);
		if (i == 0) {
			return 1;
		}
		return i;
	}

	public final void setDeductDays(float value) {
		this.SetValByKey(NodeAttr.DeductDays, value);
	}

	/**
	 * 最高扣分
	 * 
	 */
	public final float getMaxDeductCent() {
		return this.GetValFloatByKey(NodeAttr.MaxDeductCent);
	}

	public final void setMaxDeductCent(float value) {
		this.SetValByKey(NodeAttr.MaxDeductCent, value);
	}

	/**
	 * 最高辛苦加分
	 * 
	 */
	public final float getSwinkCent() {
		return this.GetValFloatByKey(NodeAttr.SwinkCent);
	}

	public final void setSwinkCent(float value) {
		this.SetValByKey(NodeAttr.SwinkCent, value);
	}

	/**
	 * 保存方式 @0=仅节点表 @1=节点与NDxxxRtp表.
	 * 
	 */
	public final SaveModel getSaveModel() {
		return SaveModel.forValue(this.GetValIntByKey(NodeAttr.SaveModel));
	}

	public final void setSaveModel(SaveModel value) {
		this.SetValByKey(NodeAttr.SaveModel, value.getValue());
	}

	/**
	 * 流程步骤
	 * 
	 */
	public final int getStep() {
		return this.GetValIntByKey(NodeAttr.Step);
	}

	public final void setStep(int value) {
		this.SetValByKey(NodeAttr.Step, value);
	}

	/**
	 * 最终期限( 需要天数（限期）+警告天数)
	 * 
	 */
	public final float getNeedCompleteDays() {
		return this.getDeductDays();
	}

	/**
	 * 扣分率（分/天）
	 * 
	 */
	public final float getDeductCent() {
		return this.GetValFloatByKey(NodeAttr.DeductCent);
	}

	public final void setDeductCent(float value) {
		this.SetValByKey(NodeAttr.DeductCent, value);
	}

	/**
	 * 是否是客户执行节点？
	 * 
	 */
	public final boolean getIsGuestNode() {
		return this.GetValBooleanByKey(NodeAttr.IsGuestNode);
	}

	public final void setIsGuestNode(boolean value) {
		this.SetValByKey(NodeAttr.IsGuestNode, value);
	}

	/**
	 * 是否是开始节点
	 * 
	 */
	public final boolean getIsStartNode() {
		if (this.getNo().equals("01")) {
			return true;
		}
		return false;

		// if (this.HisNodePosType == NodePosType.Start)
		// return true;
		// else
		// return false;
	}

	/**
	 * x
	 * 
	 */
	public final int getX() {
		return this.GetValIntByKey(NodeAttr.X);
	}

	public final void setX(int value) {
		this.SetValByKey(NodeAttr.X, value);
	}

	public final Float getWarningDays() {
		if (this.GetValFloatByKey(NodeAttr.WarningDays) == 0) {
			return this.getDeductDays();
		} else {
			return this.getDeductDays() - this.GetValFloatByKey(NodeAttr.WarningDays);
		}
	}

	/**
	 * y
	 * 
	 */
	public final int getY() {
		return this.GetValIntByKey(NodeAttr.Y);
	}

	public final void setY(int value) {
		this.SetValByKey(NodeAttr.Y, value);
	}

	/**
	 * 水执行它？
	 * 
	 */
	public final int getWhoExeIt() {
		return this.GetValIntByKey(NodeAttr.WhoExeIt);
	}

	public final void setWhoExeIt(int value) {
		this.SetValByKey(NodeAttr.WhoExeIt, value);
	}

	/**
	 * 位置
	 * 
	 */
	public final NodePosType getNodePosType() {
		return NodePosType.forValue(this.GetValIntByKey(NodeAttr.NodePosType));
	}

	public final void setNodePosType(NodePosType value) {
		this.SetValByKey(NodeAttr.NodePosType, value.getValue());
	}

	/**
	 * 运行模式
	 * 
	 */
	public final RunModel getHisRunModel() {
		return RunModel.forValue(this.GetValIntByKey(NodeAttr.RunModel));
	}

	public final void setHisRunModel(RunModel value) {
		this.SetValByKey(NodeAttr.RunModel, value.getValue());
	}

	/**
	 * 操纵提示
	 * 
	 */
	public final String getTip() {
		return this.GetValStrByKey(NodeAttr.Tip);
	}

	public final void setTip(String value) {
		this.SetValByKey(NodeAttr.Tip, value);
	}

	/**
	 * 焦点字段
	 * 
	 */
	public final String getFocusField() {
		return this.GetValStrByKey(NodeAttr.FocusField);
	}

	public final void setFocusField(String value) {
		this.SetValByKey(NodeAttr.FocusField, value);
	}

	/**
	 * 退回信息字段.
	 * 
	 */
	public final String getReturnField_del() {
		return this.GetValStrByKey(BtnAttr.ReturnField);
	}

	/**
	 * 节点的事务编号
	 * 
	 */
	public final String getFK_Flow() {
		return this.GetValStrByKey(NodeAttr.FK_Flow);
	}

	public final void setFK_Flow(String value) {
		SetValByKey(NodeAttr.FK_Flow, value);
	}

	/**
	 * 获取它的上一步的分流点
	 * 
	 */
	private Node _GetHisPriFLNode(Nodes nds) {
		for (Node mynd : Nodes.convertNodes(nds)) {
			if (mynd.getIsFL()) {
				return mynd;
			} else {
				return _GetHisPriFLNode(mynd.getFromNodes());
			}
		}
		return null;
	}

	/**
	 * 它的上一步分流节点
	 * 
	 */
	public final Node getHisPriFLNode() {
		return _GetHisPriFLNode(this.getFromNodes());
	}

	public final String getTurnToDealDoc() {
		String s = this.GetValStrByKey(NodeAttr.TurnToDealDoc);
		if (this.getHisTurnToDeal() == TurnToDeal.SpecUrl) {
			if (s.contains("?")) {
				s += "&1=1";
			} else {
				s += "?1=1";
			}
		}
		return s;
	}

	public final void setTurnToDealDoc(String value) {
		SetValByKey(NodeAttr.TurnToDealDoc, value);
	}

	/**
	 * 可跳转的节点
	 * 
	 */
	public final String getJumpToNodes() {
		return this.GetValStrByKey(NodeAttr.JumpToNodes);
	}

	public final void setJumpToNodes(String value) {
		SetValByKey(NodeAttr.JumpToNodes, value);
	}

	/**
	 * 节点表单ID
	 * 
	 */
	public final String getNodeFrmID() {
		String str = this.GetValStrByKey(NodeAttr.NodeFrmID);
		if (StringHelper.isNullOrEmpty(str)) {
			return "ND" + this.getNodeID();
		}
		return str;
	}

	public final String getFlowName() {
		return this.GetValStrByKey(NodeAttr.FlowName);
	}

	public final void setFlowName(String value) {
		SetValByKey(NodeAttr.FlowName, value);
	}

	/**
	 * 打印方式
	 * 
	 */
	public final PrintDocEnable getHisPrintDocEnable() {
		return PrintDocEnable.forValue(this.GetValIntByKey(NodeAttr.PrintDocEnable));
	}

	public final void setHisPrintDocEnable(PrintDocEnable value) {
		this.SetValByKey(NodeAttr.PrintDocEnable, value.getValue());
	}

	/**
	 * 批处理规则
	 * 
	 */
	public final BatchRole getHisBatchRole() {
		return BatchRole.forValue(this.GetValIntByKey(NodeAttr.BatchRole));
	}

	public final void setHisBatchRole(BatchRole value) {
		this.SetValByKey(NodeAttr.BatchRole, value.getValue());
	}

	/**
	 * 批量处理规则 @显示的字段.
	 * 
	 */
	public final String getBatchParas() {
		String str = this.GetValStringByKey(NodeAttr.BatchParas);

		// 替换约定的URL.
		str = str.replace("@SDKFromServHost", BP.Sys.SystemConfig.getAppSettings().get("SDKFromServHost").toString());
		// if (str.Length <=3)
		// str="Title,RDT"
		return str;
	}

	public final void setBatchParas(String value) {
		this.SetValByKey(NodeAttr.BatchParas, value);
	}

	/**
	 * 是否是自定义的url,处理批处理.
	 * 
	 */
	public final boolean getBatchParas_IsSelfUrl() {
		if (this.getBatchParas().contains(".aspx") || this.getBatchParas().contains(".jsp")
				|| this.getBatchParas().contains(".htm") || this.getBatchParas().contains("http:")) {
			return true;
		}
		return false;
	}

	/**
	 * 批量审核数量
	 * 
	 */
	public final int getBatchListCount() {
		return this.GetValIntByKey(NodeAttr.BatchListCount);
	}

	public final void setBatchListCount(int value) {
		this.SetValByKey(NodeAttr.BatchListCount, value);
	}

	public final String getPTable() {

		return "ND" + this.getNodeID();
	}

	public final void setPTable(String value) {
		SetValByKey(NodeAttr.PTable, value);
	}

	/**
	 * 要显示在后面的表单
	 * 
	 */
	public final String getShowSheets() {
		String s = this.GetValStrByKey(NodeAttr.ShowSheets);
		if (s.equals("")) {
			return "@";
		}
		return s;
	}

	public final void setShowSheets(String value) {
		SetValByKey(NodeAttr.ShowSheets, value);
	}

	/**
	 * Doc
	 * 
	 */
	public final String getDoc() {
		return this.GetValStrByKey(NodeAttr.Doc);
	}

	public final void setDoc(String value) {
		SetValByKey(NodeAttr.Doc, value);
	}

	public final String getGroupStaNDs() {
		return this.GetValStrByKey(NodeAttr.GroupStaNDs);
	}

	public final void setGroupStaNDs(String value) {
		this.SetValByKey(NodeAttr.GroupStaNDs, value);
	}

	/**
	 * 到达的节点数量.
	 * 
	 */
	public final int getHisToNDNum() {
		String[] strs = this.getHisToNDs().split("[@]", -1);
		return strs.length - 1;
	}

	public final String getHisToNDs() {
		return this.GetValStrByKey(NodeAttr.HisToNDs);
	}

	public final void setHisToNDs(String value) {
		this.SetValByKey(NodeAttr.HisToNDs, value);
	}

	public final String getHisDeptStrs() {
		return this.GetValStrByKey(NodeAttr.HisDeptStrs);
	}

	public final void setHisDeptStrs(String value) {
		this.SetValByKey(NodeAttr.HisDeptStrs, value);
	}

	public final String getHisStas() {
		return this.GetValStrByKey(NodeAttr.HisStas);
	}

	public final void setHisStas(String value) {
		this.SetValByKey(NodeAttr.HisStas, value);
	}

	public final String getHisBillIDs() {
		return this.GetValStrByKey(NodeAttr.HisBillIDs);
	}

	public final void setHisBillIDs(String value) {
		this.SetValByKey(NodeAttr.HisBillIDs, value);
	}

	/**
	 * 公文左边词语
	 * 
	 */
	public final String getDocLeftWord() {
		return this.GetValStrByKey(NodeAttr.DocLeftWord);
	}

	public final void setDocLeftWord(String value) {
		this.SetValByKey(NodeAttr.DocLeftWord, value);
	}

	/**
	 * 公文右边词语
	 * 
	 */
	public final String getDocRightWord() {
		return this.GetValStrByKey(NodeAttr.DocRightWord);
	}

	public final void setDocRightWord(String value) {
		this.SetValByKey(NodeAttr.DocRightWord, value);
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 扩展属性
	/**
	 * 是不是多岗位工作节点.
	 * 
	 */
	public final boolean getIsMultiStations() {
		if (this.getNodeStations().size() > 1) {
			return true;
		}
		return false;
	}

	public final String getHisStationsStr() {
		String s = "";
		for (NodeStation ns : NodeStations.convertNodeStations(this.getNodeStations())) {
			s += ns.getFK_StationT() + ",";
		}
		return s;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 公共方法
	/**
	 * 得到一个工作data实体
	 * 
	 * @param workId
	 *            工作ID
	 * @return 如果没有就返回null
	 */
	public final Work GetWork(long workId) {
		Work wk = this.getHisWork();
		wk.SetValByKey("OID", workId);
		if (wk.RetrieveFromDBSources() == 0) {
			return null;
		} else {
			return wk;
		}
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 节点的工作类型
	/**
	 * 转向处理
	 * 
	 */
	public final TurnToDeal getHisTurnToDeal() {
		return TurnToDeal.forValue(this.GetValIntByKey(NodeAttr.TurnToDeal));
	}

	public final void setHisTurnToDeal(TurnToDeal value) {
		this.SetValByKey(NodeAttr.TurnToDeal, value.getValue());
	}

	/**
	 * 访问规则
	 * 
	 */
	public final DeliveryWay getHisDeliveryWay() {
		return DeliveryWay.forValue(this.GetValIntByKey(NodeAttr.DeliveryWay));
	}

	public final void setHisDeliveryWay(DeliveryWay value) {
		this.SetValByKey(NodeAttr.DeliveryWay, value.getValue());
	}

	/**
	 * 抄送规则
	 * 
	 */
	public final CCRole getHisCCRole() {
		return CCRole.forValue(this.GetValIntByKey(NodeAttr.CCRole));
	}

	/**
	 * 删除流程规则
	 * 
	 */
	public final DelWorkFlowRole getHisDelWorkFlowRole() {
		return DelWorkFlowRole.forValue(this.GetValIntByKey(BtnAttr.DelEnable));
	}

	/**
	 * 未找到处理人时的方式
	 * 
	 */
	public final WhenNoWorker getHisWhenNoWorker() {
		return WhenNoWorker.forValue(this.GetValIntByKey(NodeAttr.WhenNoWorker));
	}

	public final void setHisWhenNoWorker(WhenNoWorker value) {
		this.SetValByKey(NodeAttr.WhenNoWorker, value.getValue());
	}

	/**
	 * 撤销规则
	 * 
	 */
	public final CancelRole getHisCancelRole() {
		return CancelRole.forValue(this.GetValIntByKey(NodeAttr.CancelRole));
	}

	public final void setHisCancelRole(CancelRole value) {
		this.SetValByKey(NodeAttr.CancelRole, value.getValue());
	}

	/**
	 * 数据写入规则
	 * 
	 */
	public final CCWriteTo getCCWriteTo() {
		return CCWriteTo.forValue(this.GetValIntByKey(NodeAttr.CCWriteTo));
	}

	public final void setCCWriteTo(CCWriteTo value) {
		this.SetValByKey(NodeAttr.CCWriteTo, value.getValue());
	}

	/**
	 * Int type
	 * 
	 */
	public final NodeWorkType getHisNodeWorkType() {
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #warning 2012-01-24修订,没有自动计算出来属性。
		switch (this.getHisRunModel()) {
		case Ordinary:
			if (this.getIsStartNode()) {
				return NodeWorkType.StartWork;
			} else {
				return NodeWorkType.Work;
			}
		case FL:
			if (this.getIsStartNode()) {
				return NodeWorkType.StartWorkFL;
			} else {
				return NodeWorkType.WorkFL;
			}
		case HL:
			return NodeWorkType.WorkHL;
		case FHL:
			return NodeWorkType.WorkFHL;
		case SubThread:
			return NodeWorkType.SubThreadWork;
		default:
			throw new RuntimeException("@没有判断类型NodeWorkType.");
		}
	}

	public final void setHisNodeWorkType(NodeWorkType value) {
		this.SetValByKey(NodeAttr.NodeWorkType, value.getValue());
	}

	public final String getHisNodeWorkTypeT() {
		return this.getHisNodeWorkType().toString();

		// Sys.SysEnum se = new Sys.SysEnum(NodeAttr.NodeWorkType,
		// this.GetValIntByKey(NodeAttr.NodeWorkType));
		// return se.Lab;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 推算属性 (对于节点位置的判断)

	/**
	 * 类型
	 * 
	 */
	public final NodePosType getHisNodePosType() {
		if (SystemConfig.getIsDebug()) {
			this.SetValByKey(NodeAttr.NodePosType, this.GetHisNodePosType().getValue());
			return this.GetHisNodePosType();
		}
		return NodePosType.forValue(this.GetValIntByKey(NodeAttr.NodePosType));
	}

	public final void setHisNodePosType(NodePosType value) {
		if (value == NodePosType.Start) {
			if (!this.getNo().equals("01")) {
				value = NodePosType.Mid;
			}
		}

		this.SetValByKey(NodeAttr.NodePosType, value.getValue());
	}

	/**
	 * 是不是结束节点
	 * 
	 */
	public final boolean getIsEndNode() {
		if (this.getHisNodePosType() == NodePosType.End) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否允许子线程接受人员重复(对子线程点有效)?
	 * 
	 */
	public final boolean getIsAllowRepeatEmps() {
		return this.GetValBooleanByKey(NodeAttr.IsAllowRepeatEmps);
	}

	public final void setIsAllowRepeatEmps(boolean value) {
		this.SetValByKey(NodeAttr.IsAllowRepeatEmps, value);
	}

	/**
	 * 是否可以在退回后原路返回？
	 * 
	 */
	public final boolean getIsBackTracking() {
		return this.GetValBooleanByKey(NodeAttr.IsBackTracking);
	}

	/**
	 * 是否启用自动记忆功能
	 * 
	 */
	public final boolean getIsRememberMe() {
		return this.GetValBooleanByKey(NodeAttr.IsRM);
	}

	/**
	 * 是否可以删除
	 * 
	 */
	public final boolean getIsCanDelFlow() {
		return this.GetValBooleanByKey(NodeAttr.IsCanDelFlow);
	}

	/**
	 * 普通工作节点处理模式
	 * 
	 */
	public final TodolistModel getTodolistModel() {
		return TodolistModel.forValue(this.GetValIntByKey(NodeAttr.TodolistModel));
	}

	/**
	 * 阻塞模式
	 * 
	 */
	public final BlockModel getBlockModel() {
		return BlockModel.forValue(this.GetValIntByKey(NodeAttr.BlockModel));
	}

	/**
	 * 阻塞的表达式
	 * 
	 */
	public final String getBlockExp() {

		String str = this.GetValStringByKey(NodeAttr.BlockExp);

		if (StringHelper.isNullOrEmpty(str)) {
			if (this.getBlockModel() == BlockModel.CurrNodeAll) {
				return "还有子流程没有完成您不能提交,需要等到所有的子流程完成后您才能发送.";
			}

			if (this.getBlockModel() == BlockModel.SpecSubFlow) {
				return "还有子流程没有完成您不能提交,需要等到所有的子流程完成后您才能发送.";
			}
		}
		return str;
	}

	/**
	 * 被阻塞时提示信息
	 * 
	 */
	public final String getBlockAlert() {
		return this.GetValStringByKey(NodeAttr.BlockAlert);
	}

	/**
	 * 子线程删除规则
	 * 
	 */
	public final ThreadKillRole getThreadKillRole() {
		return ThreadKillRole.forValue(this.GetValIntByKey(NodeAttr.ThreadKillRole));
	}

	/**
	 * 是否保密步骤
	 * 
	 */
	public final boolean getIsSecret() {
		return this.GetValBooleanByKey(NodeAttr.IsSecret);
	}

	public final void setIsSecret(boolean value) {
		this.SetValByKey(NodeAttr.IsSecret, value);
	}

	/**
	 * 完成通过率
	 * 
	 */
	public final java.math.BigDecimal getPassRate() {
		return this.GetValDecimalByKey(NodeAttr.PassRate);
	}

	/**
	 * 是否允许分配工作
	 * 
	 */
	public final boolean getIsTask() {
		return this.GetValBooleanByKey(NodeAttr.IsTask);
	}

	public final void setIsTask(boolean value) {
		this.SetValByKey(NodeAttr.IsTask, value);
	}

	public final boolean getIsCanOver() {
		return this.GetValBooleanByKey(NodeAttr.IsCanOver);
	}

	public final void setIsCanOver(boolean value) {
		this.SetValByKey(NodeAttr.IsCanOver, value);
	}

	public final boolean getIsCanRpt() {
		return this.GetValBooleanByKey(NodeAttr.IsCanRpt);
	}

	public final void setIsCanRpt(boolean value) {
		this.SetValByKey(NodeAttr.IsCanRpt, value);
	}

	/**
	 * 是否可以移交
	 * 
	 */
	public final boolean getIsHandOver() {
		if (this.getIsStartNode()) {
			return false;
		}

		return this.GetValBooleanByKey(NodeAttr.IsHandOver);
	}

	public final void setIsHandOver(boolean value) {
		this.SetValByKey(NodeAttr.IsHandOver, value);
	}

	public final boolean getIsCanHidReturn_del() {
		return this.GetValBooleanByKey(NodeAttr.IsCanHidReturn);
	}

	public final void setIsCanHidReturn_del(boolean value) {
		this.SetValByKey(NodeAttr.IsCanHidReturn, value);
	}

	public final boolean getIsCanReturn() {
		if (this.getHisReturnRole() == ReturnRole.CanNotReturn) {
			return false;
		}
		return true;
	}

	/**
	 * 已读回执
	 * 
	 */
	public final ReadReceipts getReadReceipts() {
		return ReadReceipts.forValue(this.GetValIntByKey(NodeAttr.ReadReceipts));
	}

	public final void setReadReceipts(ReadReceipts value) {
		this.SetValByKey(NodeAttr.ReadReceipts, value.getValue());
	}

	/**
	 * 退回规则
	 * 
	 */
	public final ReturnRole getHisReturnRole() {
		return ReturnRole.forValue(this.GetValIntByKey(NodeAttr.ReturnRole));
	}

	public final void setHisReturnRole(ReturnRole value) {
		this.SetValByKey(NodeAttr.ReturnRole, value.getValue());
	}

	/**
	 * 是不是中间节点
	 * 
	 */
	public final boolean getIsMiddleNode() {
		if (this.getHisNodePosType() == NodePosType.Mid) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否是工作质量考核点
	 * 
	 */
	public final boolean getIsEval() {
		return this.GetValBooleanByKey(NodeAttr.IsEval);
	}

	public final void setIsEval(boolean value) {
		this.SetValByKey(NodeAttr.IsEval, value);
	}

	public final String getHisSubFlows() {
		return this.GetValStringByKey(NodeAttr.HisSubFlows);
	}

	public final void setHisSubFlows(String value) {
		this.SetValByKey(NodeAttr.HisSubFlows, value);
	}

	public final String getFrmAttr() {
		return this.GetValStringByKey(NodeAttr.FrmAttr);
	}

	public final void setFrmAttr(String value) {
		this.SetValByKey(NodeAttr.FrmAttr, value);
	}

	/**
	 * 就是否有子流程
	 * 
	 */
	public final boolean getIsHaveSubFlow() {
		if (this.getHisSubFlows().length() > 2) {
			return true;
		} else {
			return false;
		}
	}

	public final boolean getIsHL() {
		switch (this.getHisNodeWorkType()) {
		case WorkHL:
		case WorkFHL:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 是否是分流
	 * 
	 */
	public final boolean getIsFL() {
		switch (this.getHisNodeWorkType()) {
		case WorkFL:
		case WorkFHL:
		case StartWorkFL:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 是否分流合流
	 * 
	 */
	public final boolean getIsFLHL() {
		switch (this.getHisNodeWorkType()) {
		case WorkHL:
		case WorkFL:
		case WorkFHL:
		case StartWorkFL:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 是否有流程完成条件
	 * 
	 */
	public final boolean getIsCCFlow() {
		return this.GetValBooleanByKey(NodeAttr.IsCCFlow);
	}

	public final void setIsCCFlow(boolean value) {
		this.SetValByKey(NodeAttr.IsCCFlow, value);
	}

	/**
	 * 接受人sql
	 * 
	 */
	public final String getDeliveryParas() {
		String s = this.GetValStringByKey(NodeAttr.DeliveryParas);
		s = s.replace("~", "'");

		if (this.getHisDeliveryWay() == DeliveryWay.ByPreviousNodeFormEmpsField
				&& StringHelper.isNullOrEmpty(s) == true) {
			return "ToEmps";
		}
		return s;
	}

	public final void setDeliveryParas(String value) {
		this.SetValByKey(NodeAttr.DeliveryParas, value);
	}

	/**
	 * 是不是PC工作节点
	 * 
	 */
	public final boolean getIsPCNode() {
		return false;
	}

	/**
	 * 工作性质
	 * 
	 */
	public final String getNodeWorkTypeText() {
		return this.getHisNodeWorkType().toString();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 公共方法 (用户执行动作之后,所要做的工作)
	/**
	 * 用户执行动作之后,所要做的工作
	 * 
	 * @return 返回消息,运行的消息
	 */
	public final String AfterDoTask() {
		return "";
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造函数
	/**
	 * 节点
	 * 
	 */
	public Node() {
	}

	/**
	 * 节点
	 * 
	 * @param _oid
	 *            节点ID
	 */
	public Node(int _oid) {
		this.setNodeID(_oid);
		if (SystemConfig.getIsDebug()) {
			if (this.RetrieveFromDBSources() <= 0) {
				throw new RuntimeException("Node Retrieve 错误没有ID=" + _oid);
			}
		} else {
			// 去掉缓存.
			this.RetrieveFromDBSources();
			// if (this.Retrieve() <= 0)
			// throw new Exception("Node Retrieve 错误没有ID=" + _oid);
		}
	}

	public Node(String ndName) {
		ndName = ndName.replace("ND", "");
		this.setNodeID(Integer.parseInt(ndName));

		if (SystemConfig.getIsDebug()) {
			if (this.RetrieveFromDBSources() <= 0) {
				throw new RuntimeException("Node Retrieve 错误没有ID=" + ndName);
			}
		} else {
			if (this.Retrieve() <= 0) {
				throw new RuntimeException("Node Retrieve 错误没有ID=" + ndName);
			}
		}
	}

	public final String getEnName() {
		return "ND" + this.getNodeID();
	}

	public final String getEnsName() {
		return "ND" + this.getNodeID() + "s";
	}

	/**
	 * 节点意见名称，如果为空则取节点名称.
	 * 
	 */
	public final String getFWCNodeName() {
		String str = this.GetValStringByKey(FrmWorkCheckAttr.FWCNodeName);
		if (StringHelper.isNullOrEmpty(str)) {
			return this.getName();
		}
		return str;
	}

	/**
	 * 重写基类方法
	 * 
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map("WF_Node");
		map.setEnDesc("节点"); // "节点";

		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 基本属性.
		map.AddTBIntPK(NodeAttr.NodeID, 0, "节点ID", true, true);
		map.AddTBString(NodeAttr.Name, null, "名称", true, false, 0, 100, 10);
		map.AddTBString(NodeAttr.Tip, null, "操作提示", true, true, 0, 100, 10, false);

		map.AddTBInt(NodeAttr.Step, NodeWorkType.Work.getValue(), "流程步骤", true, false);

		// 头像. "/WF/Data/NodeIcon/审核.png" "/WF/Data/NodeIcon/前台.png"
		map.AddTBString(NodeAttr.ICON, null, "节点ICON图片路径", true, false, 0, 50, 10);

		map.AddTBInt(NodeAttr.NodeWorkType, 0, "节点类型", false, false);
		map.AddTBInt(NodeAttr.SubThreadType, 0, "子线程ID", false, false);

		map.AddTBString(NodeAttr.FK_Flow, null, "FK_Flow", false, false, 0, 3, 10);
		map.AddTBInt(NodeAttr.IsGuestNode, 0, "是否是客户执行节点", false, false);

		map.AddTBString(NodeAttr.FlowName, null, "流程名", false, true, 0, 100, 10);
		map.AddTBString(NodeAttr.FK_FlowSort, null, "FK_FlowSort", false, true, 0, 4, 10);
		map.AddTBString(NodeAttr.FK_FlowSortT, null, "FK_FlowSortT", false, true, 0, 100, 10);
		map.AddTBString(NodeAttr.FrmAttr, null, "FrmAttr", false, true, 0, 300, 10);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 基本属性.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 审核组件.
		map.AddTBInt(NodeAttr.FWCSta, 0, "审核组件", false, false);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 审核组件.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 考核属性.
		map.AddTBFloat(NodeAttr.WarningDays, 0, "警告期限(0不警告)", false, false); // "警告期限(0不警告)"
		map.AddTBFloat(NodeAttr.DeductDays, 5, "限期(天)", false, false); // "限期(天)"
		map.AddTBFloat(NodeAttr.DeductCent, 2, "扣分(每延期1天扣)", false, false); // "扣分(每延期1天扣)"
		map.AddTBFloat(NodeAttr.MaxDeductCent, 10, "最高扣分", false, false); // "最高扣分"
		map.AddTBFloat(NodeAttr.SwinkCent, Float.parseFloat("0.1"), "工作得分", false, false); // "工作得分"
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 考核属性.

		map.AddTBString(FrmWorkCheckAttr.FWCNodeName, null, "节点意见名称", true, false, 0, 100, 10);

		map.AddTBString(NodeAttr.Doc, null, "描述", true, false, 0, 100, 10);
		map.AddBoolean(NodeAttr.IsTask, true, "允许分配工作否?", true, true);

		map.AddTBInt(NodeAttr.ReturnRole, 2, "退回规则", true, true);
		map.AddTBInt(NodeAttr.DeliveryWay, 0, "访问规则", true, true);
		map.AddTBInt(NodeAttr.CancelRole, 0, "撤销规则", true, true);

		map.AddTBInt(NodeAttr.WhenNoWorker, 0, "未找到处理人时", true, true);
		map.AddTBString(NodeAttr.DeliveryParas, null, "访问规则设置", true, false, 0, 500, 10);
		map.AddTBString(NodeAttr.NodeFrmID, null, "节点表单ID", true, false, 0, 50, 10);

		map.AddTBInt(NodeAttr.CCRole, 0, "抄送规则", true, true);
		map.AddTBInt(NodeAttr.CCWriteTo, 0, "抄送数据写入规则", true, true);

		map.AddTBInt(BtnAttr.DelEnable, 0, "删除规则", true, true);
		map.AddTBInt(NodeAttr.IsEval, 0, "是否工作质量考核", true, true);
		map.AddTBInt(NodeAttr.SaveModel, 0, "保存模式", true, true);

		map.AddTBInt(NodeAttr.IsCanRpt, 1, "是否可以查看工作报告?", true, true);
		map.AddTBInt(NodeAttr.IsCanOver, 0, "是否可以终止流程", true, true);
		map.AddTBInt(NodeAttr.IsSecret, 0, "是否是保密步骤", true, true);
		map.AddTBInt(NodeAttr.IsCanDelFlow, 0, "是否可以删除流程", true, true);

		map.AddTBInt(NodeAttr.ThreadKillRole, 0, "子线程删除方式", true, true);
		map.AddTBInt(NodeAttr.TodolistModel, 0, "是否是队列节点", true, true);

		map.AddTBInt(NodeAttr.IsAllowRepeatEmps, 0, "是否允许子线程接受人员重复(对子线程点有效)?", true, true);
		map.AddTBInt(NodeAttr.IsBackTracking, 0, "是否可以在退回后原路返回(只有启用退回功能才有效)", true, true);
		map.AddTBInt(NodeAttr.IsRM, 1, "是否启用投递路径自动记忆功能?", true, true);
		map.AddBoolean(NodeAttr.IsHandOver, false, "是否可以移交", true, true);
		map.AddTBDecimal(NodeAttr.PassRate, 100, "通过率", true, true);
		map.AddTBInt(NodeAttr.RunModel, 0, "运行模式(对普通节点有效)", true, true);
		map.AddTBInt(NodeAttr.BlockModel, 0, "阻塞模式", true, true);
		map.AddTBString(NodeAttr.BlockExp, null, "阻塞表达式", true, false, 0, 700, 10);
		map.AddTBString(NodeAttr.BlockAlert, null, "被阻塞提示信息", true, false, 0, 700, 10);

		map.AddTBInt(NodeAttr.WhoExeIt, 0, "谁执行它", true, true);
		map.AddTBInt(NodeAttr.ReadReceipts, 0, "已读回执", true, true);
		map.AddTBInt(NodeAttr.CondModel, 0, "方向条件控制规则", true, true);

		// 自动跳转.
		map.AddTBInt(NodeAttr.AutoJumpRole0, 0, "处理人就是提交人0", false, false);
		map.AddTBInt(NodeAttr.AutoJumpRole1, 0, "处理人已经出现过1", false, false);
		map.AddTBInt(NodeAttr.AutoJumpRole2, 0, "处理人与上一步相同2", false, false);

		// 批处理.
		map.AddTBInt(NodeAttr.BatchRole, 0, "批处理", true, true);
		map.AddTBInt(NodeAttr.BatchListCount, 12, "批处理数量", true, true);
		map.AddTBString(NodeAttr.BatchParas, null, "参数", true, false, 0, 100, 10);
		map.AddTBInt(NodeAttr.PrintDocEnable, 0, "打印方式", true, true);

		// 考核相关.
		map.AddTBInt(NodeAttr.OutTimeDeal, 0, "超时处理方式", false, false);
		map.AddTBString(NodeAttr.DoOutTime, null, "超时处理内容", true, false, 0, 300, 10, true);

		map.AddTBInt(NodeAttr.FormType, 1, "表单类型", false, false);
		map.AddTBString(NodeAttr.FormUrl, "http://", "表单URL", true, false, 0, 2000, 10);
		map.AddTBString(NodeAttr.DeliveryParas, null, "接受人SQL", true, false, 0, 300, 10, true);
		map.AddTBInt(NodeAttr.TurnToDeal, 0, "转向处理", false, false);
		map.AddTBString(NodeAttr.TurnToDealDoc, null, "发送后提示信息", true, false, 0, 1000, 10, true);
		map.AddTBInt(NodeAttr.NodePosType, 0, "位置", false, false);
		map.AddTBInt(NodeAttr.IsCCFlow, 0, "是否有流程完成条件", false, false);
		map.AddTBString(NodeAttr.HisStas, null, "岗位", false, false, 0, 4000, 10);
		map.AddTBString(NodeAttr.HisDeptStrs, null, "部门", false, false, 0, 4000, 10);
		map.AddTBString(NodeAttr.HisToNDs, null, "转到的节点", false, false, 0, 100, 10);
		map.AddTBString(NodeAttr.HisBillIDs, null, "单据IDs", false, false, 0, 200, 10);
		// map.AddTBString(NodeAttr.HisEmps, null, "HisEmps", false, false, 0,
		// 3000, 10);
		map.AddTBString(NodeAttr.HisSubFlows, null, "HisSubFlows", false, false, 0, 50, 10);
		map.AddTBString(NodeAttr.PTable, null, "物理表", false, false, 0, 100, 10);

		map.AddTBString(NodeAttr.ShowSheets, null, "显示的表单", false, false, 0, 100, 10);
		map.AddTBString(NodeAttr.GroupStaNDs, null, "岗位分组节点", false, false, 0, 200, 10);
		map.AddTBInt(NodeAttr.X, 0, "X坐标", false, false);
		map.AddTBInt(NodeAttr.Y, 0, "Y坐标", false, false);

		map.AddTBString(NodeAttr.FocusField, null, "焦点字段", false, false, 0, 30, 10);
		map.AddTBString(NodeAttr.JumpToNodes, null, "可跳转的节点", true, false, 0, 200, 10, true);

		// 按钮控制部分.
		// map.AddTBString(BtnAttr.ReturnField, "", "退回信息填写字段", true, false, 0,
		// 50, 10, true);
		map.AddTBAtParas(500);

		// 启动子线程参数 2013-01-04
		map.AddTBInt(NodeAttr.SubFlowStartWay, 0, "子线程启动方式", true, false);
		map.AddTBString(NodeAttr.SubFlowStartParas, null, "启动参数", true, false, 0, 100, 10);

		map.AddTBString(NodeAttr.DocLeftWord, null, "公文左边词语(多个用@符合隔开)", true, false, 0, 200, 10);
		map.AddTBString(NodeAttr.DocRightWord, null, "公文右边词语(多个用@符合隔开)", true, false, 0, 200, 10);

		this.set_enMap(map);
		return this.get_enMap();
	}

	/**
	 * 我能处理当前的节点吗？
	 * 
	 * @return
	 */
	public final boolean CanIdoIt() {
		return false;
	}
	/// #endregion

	/**
	 * 删除前的逻辑处理.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean beforeDelete() {
		// 判断是否可以被删除.
		int num = DBAccess
				.RunSQLReturnValInt("SELECT COUNT(*) FROM WF_GenerWorkerlist WHERE FK_Node=" + this.getNodeID());
		if (num != 0) {
			throw new RuntimeException("@该节点[" + this.getNodeID() + "," + this.getName() + "]有待办工作存在，您不能删除它.");
		}

		// 删除它的节点。
		MapData md = new MapData();
		md.setNo("ND" + this.getNodeID());
		md.Delete();

		// 删除分组.
		GroupFields gfs = new GroupFields();
		gfs.Delete(GroupFieldAttr.EnName, md.getNo());

		try {
			// 删除它的明细。
			MapDtls dtls = new MapDtls(md.getNo());
			dtls.Delete();

			// 删除框架
			MapFrames frams = new MapFrames(md.getNo());
			frams.Delete();

			// 删除多选
			MapM2Ms m2ms = new MapM2Ms(md.getNo());
			m2ms.Delete();

			// 删除扩展
			MapExts exts = new MapExts(md.getNo());
			exts.Delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 删除节点与岗位的对应.
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_NodeStation WHERE FK_Node=" + this.getNodeID());
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_NodeEmp  WHERE FK_Node=" + this.getNodeID());
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_NodeDept WHERE FK_Node=" + this.getNodeID());
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_NodeFlow WHERE FK_Node=" + this.getNodeID());
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_FrmNode  WHERE FK_Node=" + this.getNodeID());
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_CCEmp  WHERE FK_Node=" + this.getNodeID());
		return super.beforeDelete();
	}

	/**
	 * 文书流程
	 * 
	 * @param md
	 */
	private void AddDocAttr(MapData md) {
		// 如果是单据流程？
		MapAttr attr = new MapAttr();

		// attr = new BP.Sys.MapAttr();
		// attr.FK_MapData = md.getNo();
		// attr.HisEditType = BP.En.EditType.UnDel;
		// attr.KeyOfEn = "Title";
		// attr.Name = "标题";
		// attr.MyDataType = BP.DA.DataType.AppString;
		// attr.UIContralType = UIContralType.TB;
		// attr.LGType = FieldTypeS.Normal;
		// attr.UIVisible = true;
		// attr.UIIsEnable = true;
		// attr.MinLen = 0;
		// attr.MaxLen = 300;
		// attr.Idx = 1;
		// attr.UIIsLine = true;
		// attr.Idx = -100;
		// attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("KeyWord");
		attr.setName("主题词");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setUIIsLine(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(-99);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("FZ");
		attr.setName("附注");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setUIIsLine(true);
		// attr.setIDX(1;
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setIDX(-98);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn("DW_SW");
		attr.setName("收文单位");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setUIIsLine(true);
		attr.setIDX(1);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("DW_FW");
		attr.setName("发文单位");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(1);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setUIIsLine(true);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("DW_BS");
		attr.setName("主报(送)单位");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(1);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setUIIsLine(true);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("DW_CS");
		attr.setName("抄报(送)单位");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(1);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setUIIsLine(true);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("NumPrint");
		attr.setName("印制份数");
		attr.setMyDataType(BP.DA.DataType.AppInt);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(10);
		attr.setIDX(1);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setUIIsLine(false);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("JMCD");
		attr.setName("机密程度");
		attr.setMyDataType(BP.DA.DataType.AppInt);
		attr.setUIContralType(UIContralType.DDL);
		attr.setLGType(FieldTypeS.Enum);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(1);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setUIIsLine(false);
		attr.setUIBindKey("JMCD");
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn("PRI");
		attr.setName("紧急程度");
		attr.setMyDataType(BP.DA.DataType.AppInt);
		attr.setUIContralType(UIContralType.DDL);
		attr.setLGType(FieldTypeS.Enum);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(1);
		attr.setUIIsLine(false);
		attr.setUIBindKey("PRI");
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("GWWH");
		attr.setName("公文文号");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(true);
		attr.setUIIsEnable(true);
		attr.setMinLen(0);
		attr.setMaxLen(300);
		attr.setIDX(1);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setUIIsLine(false);
		attr.Insert();
	}

	/**
	 * 修复map
	 * 
	 */
	public final String RepareMap() {
		MapData md = new MapData();
		md.setNo("ND" + this.getNodeID());
		if (md.RetrieveFromDBSources() == 0) {
			this.CreateMap();
			return "";
		}

		MapAttr attr = new MapAttr();
		if (attr.IsExit(MapAttrAttr.KeyOfEn, "OID", MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr.setFK_MapData(md.getNo());
			attr.setKeyOfEn("OID");
			attr.setName("WorkID");
			attr.setMyDataType(BP.DA.DataType.AppInt);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("0");
			attr.setHisEditType(BP.En.EditType.Readonly);
			attr.Insert();
		}

		if (attr.IsExit(MapAttrAttr.KeyOfEn, "FID", MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setKeyOfEn("FID");
			attr.setName("FID");
			attr.setMyDataType(BP.DA.DataType.AppInt);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setDefVal("0");
			attr.Insert();
		}

		if (attr.IsExit(MapAttrAttr.KeyOfEn, WorkAttr.RDT, MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn(WorkAttr.RDT);
			attr.setName("接受时间"); // "接受时间";
			attr.setMyDataType(BP.DA.DataType.AppDateTime);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setTag("1");
			attr.Insert();
		}

		if (attr.IsExit(MapAttrAttr.KeyOfEn, WorkAttr.CDT, MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn(WorkAttr.CDT);
			if (this.getIsStartNode()) {
				attr.setName("发起时间"); // "发起时间";
			} else {
				attr.setName("完成时间"); // "完成时间";
			}

			attr.setMyDataType(BP.DA.DataType.AppDateTime);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setDefVal("@RDT");
			attr.setTag("1");
			attr.Insert();
		}

		if (attr.IsExit(MapAttrAttr.KeyOfEn, WorkAttr.Rec, MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn(WorkAttr.Rec);
			if (this.getIsStartNode() == false) {
				attr.setName("记录人"); // "记录人";
			} else {
				attr.setName("发起人"); // "发起人";
			}

			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setMaxLen(20);
			attr.setMinLen(0);
			attr.setDefVal("@WebUser.No");
			attr.Insert();
		}

		if (attr.IsExit(MapAttrAttr.KeyOfEn, WorkAttr.Emps, MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn(WorkAttr.Emps);
			attr.setName(WorkAttr.Emps);
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setMaxLen(400);
			attr.setMinLen(0);
			attr.Insert();
		}

		if (attr.IsExit(MapAttrAttr.KeyOfEn, StartWorkAttr.FK_Dept, MapAttrAttr.FK_MapData, md.getNo()) == false) {
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn(StartWorkAttr.FK_Dept);
			attr.setName("操作员部门"); // "操作员部门";
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.DDL);
			attr.setLGType(FieldTypeS.FK);
			attr.setUIBindKey("BP.Port.Depts");
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setMinLen(0);
			attr.setMaxLen(20);
			attr.Insert();
		}

		Flow fl = new Flow(this.getFK_Flow());
		if (fl.getIsMD5()
				&& attr.IsExit(MapAttrAttr.KeyOfEn, WorkAttr.MD5, MapAttrAttr.FK_MapData, md.getNo()) == false) {
			// 如果是MD5加密流程.
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn(StartWorkAttr.MD5);
			attr.setUIBindKey(attr.getKeyOfEn());
			attr.setName("MD5");
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setUIVisible(false);
			attr.setMinLen(0);
			attr.setMaxLen(40);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (this.getNodePosType() == NodePosType.Start) {

			if (BP.WF.Glo.getIsEnablePRI() && this.getIsStartNode() && attr.IsExit(MapAttrAttr.KeyOfEn,
					StartWorkAttr.PRI, MapAttrAttr.FK_MapData, md.getNo()) == false) {
				// 如果有优先级
				attr = new MapAttr();
				attr.setFK_MapData(md.getNo());
				attr.setHisEditType(BP.En.EditType.UnDel);
				attr.setKeyOfEn(StartWorkAttr.PRI);
				attr.setUIBindKey(attr.getKeyOfEn());
				attr.setName("优先级");
				attr.setMyDataType(BP.DA.DataType.AppInt);
				attr.setUIContralType(UIContralType.DDL);
				attr.setLGType(FieldTypeS.Enum);
				attr.setUIIsEnable(true);
				attr.setUIIsLine(false);
				attr.setMinLen(0);
				attr.setMaxLen(200);
				attr.setIDX(-100);
				attr.setDefVal("0");
				attr.setX((float) 174.76);
				attr.setY((float) 56.19);
				attr.Insert();
			}

			if (attr.IsExit(MapAttrAttr.KeyOfEn, StartWorkAttr.Title, MapAttrAttr.FK_MapData, md.getNo()) == false) {
				attr = new MapAttr();
				attr.setFK_MapData(md.getNo());
				attr.setHisEditType(BP.En.EditType.UnDel);
				attr.setKeyOfEn(StartWorkAttr.Title);
				attr.setName("标题"); // "流程标题";
				attr.setMyDataType(BP.DA.DataType.AppString);
				attr.setUIContralType(UIContralType.TB);
				attr.setLGType(FieldTypeS.Normal);
				attr.setUIVisible(false);
				attr.setUIIsEnable(true);
				attr.setUIIsLine(true);
				attr.setUIWidth(251);

				attr.setMinLen(0);
				attr.setMaxLen(200);
				attr.setIDX(-100);
				attr.setX((float) 171.2);
				attr.setY((float) 68.4);
				attr.Insert();
			}

			// if (attr.IsExit(MapAttrAttr.KeyOfEn, "faqiren",
			// MapAttrAttr.FK_MapData, md.getNo()) == false)
			// {
			// attr = new MapAttr();
			// attr.FK_MapData = md.getNo();
			// attr.HisEditType = BP.En.EditType.Edit;
			// attr.KeyOfEn = "faqiren";
			// attr.Name = "发起人"; // "发起人";
			// attr.MyDataType = BP.DA.DataType.AppString;
			// attr.UIContralType = UIContralType.TB;
			// attr.LGType = FieldTypeS.Normal;
			// attr.UIVisible = true;
			// attr.UIIsEnable = false;
			// attr.UIIsLine = false;
			// attr.MinLen = 0;
			// attr.MaxLen = 200;
			// attr.Idx = -100;
			// attr.DefVal = "@WebUser.No";
			// attr.X = (float)159.2;
			// attr.Y = (float)102.8;
			// attr.Insert();
			// }

			// if (attr.IsExit(MapAttrAttr.KeyOfEn, "faqishijian",
			// MapAttrAttr.FK_MapData, md.getNo()) == false)
			// {
			// attr = new MapAttr();
			// attr.FK_MapData = md.getNo();
			// attr.HisEditType = BP.En.EditType.Edit;
			// attr.KeyOfEn = "faqishijian";
			// attr.Name = "发起时间"; //"发起时间";
			// attr.MyDataType = BP.DA.DataType.AppDateTime;
			// attr.UIContralType = UIContralType.TB;
			// attr.LGType = FieldTypeS.Normal;
			// attr.UIVisible = true;
			// attr.UIIsEnable = false;
			// attr.DefVal = "@RDT";
			// attr.Tag = "1";
			// attr.X = (float)324;
			// attr.Y = (float)102.8;
			// attr.Insert();
			// }

			if (attr.IsExit(MapAttrAttr.KeyOfEn, "FK_NY", MapAttrAttr.FK_MapData, md.getNo()) == false) {
				attr = new MapAttr();
				attr.setFK_MapData(md.getNo());
				attr.setHisEditType(BP.En.EditType.UnDel);
				attr.setKeyOfEn("FK_NY");
				attr.setName("年月"); // "年月";
				attr.setMyDataType(BP.DA.DataType.AppString);
				attr.setUIContralType(UIContralType.TB);
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.setLGType(FieldTypeS.Normal);
				// attr.setUIBindKey("BP.Pub.NYs";
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.setMinLen(0);
				attr.setMaxLen(7);
				attr.Insert();
			}

			if (attr.IsExit(MapAttrAttr.KeyOfEn, "MyNum", MapAttrAttr.FK_MapData, md.getNo()) == false) {
				attr = new MapAttr();
				attr.setFK_MapData(md.getNo());
				attr.setHisEditType(BP.En.EditType.UnDel);
				attr.setKeyOfEn("MyNum");
				attr.setName("个数"); // "个数";
				attr.setDefVal("1");
				attr.setMyDataType(BP.DA.DataType.AppInt);
				attr.setUIContralType(UIContralType.TB);
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.setLGType(FieldTypeS.Normal);
				attr.setUIVisible(false);
				attr.setUIIsEnable(false);
				attr.Insert();
			}
		}
		String msg = "";
		if (!this.getFocusField().equals("")) {
			if (attr.IsExit(MapAttrAttr.KeyOfEn, this.getFocusField(), MapAttrAttr.FK_MapData, md.getNo()) == false) {
				msg += "@焦点字段 " + this.getFocusField() + " 被非法删除了.";
				// this.FocusField = "";
				// this.DirectUpdate();
			}
		}
		return msg;
	}

	/**
	 * 建立map
	 * 
	 */
	public final void CreateMap() {
		// 创建节点表单.
		MapData md = new MapData();
		md.setNo("ND" + this.getNodeID());
		md.Delete();

		md.setName(this.getName());
		if (this.getHisFlow().getHisDataStoreModel() == DataStoreModel.SpecTable) {
			md.setPTable(this.getHisFlow().getPTable());
		}
		md.Insert();

		MapAttr attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("OID");
		attr.setName("WorkID");
		attr.setMyDataType(BP.DA.DataType.AppInt);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setDefVal("0");
		attr.setHisEditType(BP.En.EditType.Readonly);
		attr.Insert();

		if (this.getHisFlow().getFlowAppType() == FlowAppType.DocFlow) {
			this.AddDocAttr(md);
		}

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setKeyOfEn("FID");
		attr.setName("FID");
		attr.setMyDataType(BP.DA.DataType.AppInt);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setDefVal("0");
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn(WorkAttr.RDT);
		attr.setName("接受时间"); // "接受时间";
		attr.setMyDataType(BP.DA.DataType.AppDateTime);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setTag("1");
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn(WorkAttr.CDT);
		if (this.getIsStartNode()) {
			attr.setName("发起时间"); // "发起时间";
		} else {
			attr.setName("完成时间"); // "完成时间";
		}

		attr.setMyDataType(BP.DA.DataType.AppDateTime);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setDefVal("@RDT");
		attr.setTag("1");
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn(WorkAttr.Rec);
		if (this.getIsStartNode() == false) {
			attr.setName("记录人"); // "记录人";
		} else {
			attr.setName("发起人"); // "发起人";
		}

		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setMaxLen(20);
		attr.setMinLen(0);
		attr.setDefVal("@WebUser.No");
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn(WorkAttr.Emps);
		attr.setName("Emps");
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setMaxLen(400);
		attr.setMinLen(0);
		attr.Insert();

		attr = new MapAttr();
		attr.setFK_MapData(md.getNo());
		attr.setHisEditType(BP.En.EditType.UnDel);
		attr.setKeyOfEn(StartWorkAttr.FK_Dept);
		attr.setName("操作员部门"); // "操作员部门";
		attr.setMyDataType(BP.DA.DataType.AppString);
		attr.setUIContralType(UIContralType.TB);
		attr.setLGType(FieldTypeS.Normal);
		// attr.setUIBindKey("BP.Port.Depts";
		attr.setUIVisible(false);
		attr.setUIIsEnable(false);
		attr.setMinLen(0);
		attr.setMaxLen(32);
		attr.Insert();

		if (this.getNodePosType() == NodePosType.Start) {
			// 开始节点信息.
			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.Edit);
			// attr.setedit
			attr.setKeyOfEn("Title");
			attr.setName("标题"); // "流程标题";
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(true);
			attr.setUIWidth(251);

			attr.setMinLen(0);
			attr.setMaxLen(200);
			attr.setIDX(-100);
			attr.setX((float) 174.83);
			attr.setY((float) 54.4);
			attr.Insert();

			if (BP.WF.Glo.getIsEnablePRI() == true) {
				// 如果有优先级
				attr = new MapAttr();
				attr.setFK_MapData(md.getNo());
				attr.setHisEditType(BP.En.EditType.UnDel);
				attr.setKeyOfEn("PRI");
				attr.setUIBindKey(attr.getKeyOfEn());
				attr.setName("优先级");
				attr.setMyDataType(BP.DA.DataType.AppInt);
				attr.setUIContralType(UIContralType.DDL);
				attr.setLGType(FieldTypeS.Enum);
				attr.setUIIsEnable(true);
				attr.setUIIsLine(false);
				attr.setMinLen(0);
				attr.setMaxLen(200);
				attr.setIDX(-100);
				attr.setDefVal("2");
				attr.setX((float) 174.76);
				attr.setY((float) 56.19);
				attr.Insert();
			}

			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn("FK_NY");
			attr.setName("年月"); // "年月";
			attr.setMyDataType(BP.DA.DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setLGType(FieldTypeS.Normal);
			// attr.setUIBindKey("BP.Pub.NYs";
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setMinLen(0);
			attr.setMaxLen(7);
			attr.Insert();

			attr = new MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(BP.En.EditType.UnDel);
			attr.setKeyOfEn("MyNum");
			attr.setName("个数"); // "个数";
			attr.setDefVal("1");
			attr.setMyDataType(BP.DA.DataType.AppInt);
			attr.setUIContralType(UIContralType.TB);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.Insert();

		}
	}
}