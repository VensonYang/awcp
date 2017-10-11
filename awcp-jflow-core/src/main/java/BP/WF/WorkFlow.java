package BP.WF;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.DA.DBAccess;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Log;
import BP.DA.LogType;
import BP.DA.Paras;
import BP.En.Entity;
import BP.Port.Emp;
import BP.Port.Emps;
import BP.Port.WebUser;
import BP.Sys.GEDtl;
import BP.Sys.GEDtlAttr;
import BP.Sys.GEDtls;
import BP.Sys.SystemConfig;
import BP.Sys.Frm.EventListOfNode;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDtl;
import BP.Sys.Frm.MapDtls;
import BP.Tools.StringHelper;
import BP.WF.Data.GERpt;
import BP.WF.Data.GERptAttr;
import BP.WF.Entity.GenerFHAttr;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.GenerWorkFlowAttr;
import BP.WF.Entity.GenerWorkFlows;
import BP.WF.Entity.GenerWorkerList;
import BP.WF.Entity.GenerWorkerListAttr;
import BP.WF.Entity.GenerWorkerLists;
import BP.WF.Entity.TrackAttr;
import BP.WF.Entity.WFSta;
import BP.WF.Entity.WorkFlowDeleteLog;
import BP.WF.Port.SMSMsgType;
import BP.WF.Template.Flow;
import BP.WF.Template.HungUp;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;
import BP.WF.Template.Form.FrmNode;
import BP.WF.Template.Form.FrmNodeAttr;
import BP.WF.Template.Form.FrmNodes;
import BP.WF.Template.PubLib.ActionType;
import BP.WF.Template.PubLib.HungUpWay;
import BP.WF.Template.PubLib.WFState;
import BP.WF.Template.WorkBase.StartWork;
import BP.WF.Template.WorkBase.Work;

/**
 * WF 的摘要说明。 工作流 这里包含了两个方面 工作的信息． 流程的信息．
 * 
 */
public class WorkFlow {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(WorkFlow.class);

	/// #region 当前工作统计信息
	/**
	 * 正常范围的运行的个数。
	 * 
	 */
	public static int NumOfRuning(String FK_Emp) {
		String sql = "SELECT COUNT(*) FROM V_WF_CURRWROKS WHERE FK_Emp='" + FK_Emp + "' AND WorkTimeState=0";
		return DBAccess.RunSQLReturnValInt(sql);
	}

	/**
	 * 进入警告期限的个数
	 * 
	 */
	public static int NumOfAlert(String FK_Emp) {
		String sql = "SELECT COUNT(*) FROM V_WF_CURRWROKS WHERE FK_Emp='" + FK_Emp + "' AND WorkTimeState=1";
		return DBAccess.RunSQLReturnValInt(sql);
	}

	/**
	 * 逾期
	 * 
	 */
	public static int NumOfTimeout(String FK_Emp) {
		String sql = "SELECT COUNT(*) FROM V_WF_CURRWROKS WHERE FK_Emp='" + FK_Emp + "' AND WorkTimeState=2";
		return DBAccess.RunSQLReturnValInt(sql);
	}

	/// #region 权限管理
	/**
	 * 是不是能够作当前的工作。
	 * 
	 * @param empId
	 *            工作人员ID
	 * @return 是不是能够作当前的工作
	 */
	public final boolean IsCanDoCurrentWork(String empId) {
		WorkNode wn = this.GetCurrentWorkNode();
		// return
		// BP.WF.Dev2Interface.Flow_IsCanDoCurrentWork(wn.getHisNode().getNodeID(),
		// wn.getWorkID(), empId);
		/// #region 使用dev2InterFace 中的算法
		// return true;
		// 找到当前的工作节点
		if (wn.getHisNode().getIsStartNode()) {
			// 从物理上判断是不是有这个权限。
			// return WorkFlow.IsCanDoWorkCheckByEmpStation(wn.HisNode.NodeID,
			// empId);
			return true;
		}

		// 判断他的工作生成的工作者.
		GenerWorkerLists gwls = new GenerWorkerLists(this.getWorkID(), wn.getHisNode().getNodeID());
		if (gwls.size() == 0) {
			// return true;
			// throw new
			// Exception("@工作流程定义错误,没有找到能够执行此项工作的人员.相关信息:工作ID="+this.WorkID+",节点ID="+wn.HisNode.NodeID
			// );
			throw new RuntimeException("@工作流程定义错误,没有找到能够执行此项工作的人员.相关信息:WorkID=" + this.getWorkID() + ",NodeID="
					+ wn.getHisNode().getNodeID());
		}

		for (Entity obj : GenerWorkerLists.convertEntities(gwls)) {
			GenerWorkerList en = (GenerWorkerList) obj;
			if (en.getFK_Emp().equals(empId)) {
				return true;
			}
		}
		return false;
		/// #endregion
	}
	/// #endregion

	/// #region 流程公共方法
	/**
	 * 执行驳回 应用场景:子流程向分合点驳回时
	 * 
	 * @param fid
	 * @param fk_node
	 *            被驳回的节点
	 * @param msg
	 * @return
	 */
	public final String DoReject(long fid, int fk_node, String msg) {
		GenerWorkerList wl = new GenerWorkerList();
		int i = wl.Retrieve(GenerWorkerListAttr.FID, fid, GenerWorkerListAttr.WorkID, this.getWorkID(),
				GenerWorkerListAttr.FK_Node, fk_node);
		// if (i == 0)
		// throw new Exception("系统错误，没有找到应该找到的数据。");

		i = wl.Delete();
		// if (i == 0)
		// throw new Exception("系统错误，没有删除应该删除的数据。");

		wl = new GenerWorkerList();
		i = wl.Retrieve(GenerWorkerListAttr.FID, fid, GenerWorkerListAttr.WorkID, this.getWorkID(),
				GenerWorkerListAttr.IsPass, 3);

		// if (i == 0)
		// throw new Exception("系统错误，想找到退回的原始起点没有找到。");

		Node nd = new Node(fk_node);
		// 更新当前流程管理表的设置当前的节点。
		DBAccess.RunSQL("UPDATE WF_GenerWorkFlow SET FK_Node=" + fk_node + ", NodeName='" + nd.getName()
				+ "' WHERE WorkID=" + this.getWorkID());

		wl.setRDT(DataType.getCurrentDataTime());
		wl.setIsPass(false);
		wl.Update();

		return "工作已经驳回到(" + wl.getFK_Emp() + " , " + wl.getFK_EmpText() + ")";
		// wl.HisNode
	}

	/**
	 * 逻辑删除流程
	 * 
	 * @param msg
	 *            逻辑删除流程原因，可以为空。
	 */
	public final void DoDeleteWorkFlowByFlag(String msg) {
		try {
			// 设置产生的工作流程为.
			GenerWorkFlow gwf = new GenerWorkFlow(this.getWorkID());
			gwf.setWFState(WFState.Delete);
			gwf.Update();

			// 记录日志 感谢 itdos and 888 , 提出了这个bug.
			WorkNode wn = new WorkNode(getWorkID(), gwf.getFK_Node());
			wn.AddToTrack(ActionType.DeleteFlowByFlag, WebUser.getNo(), WebUser.getName(), wn.getHisNode().getNodeID(),
					wn.getHisNode().getName(), msg);

			String sql = "UPDATE  " + this.getHisFlow().getPTable() + " SET WFState=" + WFState.Delete.getValue()
					+ " WHERE OID=" + this.getWorkID();
			DBAccess.RunSQL(sql);
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLine(LogType.Error, "@逻辑删除出现错误:" + ex.getMessage());
			throw new RuntimeException("@逻辑删除出现错误:" + ex.getMessage());
		}
	}

	/**
	 * 恢复逻辑删除流程
	 * 
	 * @param msg
	 *            回复原因,可以为空.
	 */
	public final void DoUnDeleteWorkFlowByFlag(String msg) {
		try {
			DBAccess.RunSQL("UPDATE WF_GenerWorkFlow SET WFState=" + WFState.Runing.getValue() + " WHERE  WorkID="
					+ this.getWorkID());

			// 设置产生的工作流程为.
			GenerWorkFlow gwf = new GenerWorkFlow(this.getWorkID());
			gwf.setWFState(WFState.Runing);
			gwf.Update();

			WorkNode wn = new WorkNode(getWorkID(), gwf.getFK_Node());
			wn.AddToTrack(ActionType.UnDeleteFlowByFlag, WebUser.getNo(), WebUser.getName(),
					wn.getHisNode().getNodeID(), wn.getHisNode().getName(), msg);
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLine(LogType.Error, "@逻辑删除出现错误:" + ex.getMessage());
			throw new RuntimeException("@逻辑删除出现错误:" + ex.getMessage());
		}
	}

	/**
	 * 删除已经完成的流程
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param isDelSubFlow
	 *            是否要删除子流程
	 * @param note
	 *            删除原因
	 * @return 删除信息
	 */
	public static String DoDeleteWorkFlowAlreadyComplete(String flowNo, long workID, boolean isDelSubFlow,
			String note) {
		Log.DebugWriteInfo(
				"开始删除流程:流程编号:" + flowNo + "-WorkID:" + workID + "-" + ". 是否要删除子流程:" + isDelSubFlow + ";删除原因:" + note);
		Flow fl = new Flow(flowNo);
		/// #region 记录流程删除日志
		GERpt rpt = new GERpt("ND" + Integer.parseInt(flowNo) + "Rpt");
		rpt.SetValByKey(GERptAttr.OID, workID);
		rpt.Retrieve();
		WorkFlowDeleteLog log = new WorkFlowDeleteLog();
		log.setOID(workID);
		try {
			log.Copy(rpt);
			log.setDeleteDT(DataType.getCurrentDataTime());
			log.setOperDept(WebUser.getFK_Dept());
			log.setOperDeptName(WebUser.getFK_DeptName());
			log.setOper(WebUser.getNo());
			log.setDeleteNote(note);
			log.setOID(workID);
			log.setFK_Flow(flowNo);
			log.setFK_FlowSort(fl.getFK_FlowSort());
			try {
				log.InsertAsOID(log.getOID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RuntimeException ex) {
			log.CheckPhysicsTable();
			log.Delete();
			return ex.getStackTrace().toString();
		}
		/// #endregion 记录流程删除日志

		DBAccess.RunSQL("DELETE FROM ND" + Integer.parseInt(flowNo) + "Track WHERE WorkID=" + workID);
		DBAccess.RunSQL("DELETE FROM " + fl.getPTable() + " WHERE OID=" + workID);
		DBAccess.RunSQL("DELETE FROM WF_CHEval WHERE  WorkID=" + workID); // 删除质量考核数据。

		String info = "";

		/// #region 正常的删除信息.
		String msg = "";
		try {
			// 删除单据信息.
			DBAccess.RunSQL("DELETE FROM WF_CCList WHERE WorkID=" + workID);

			// 删除单据信息.
			DBAccess.RunSQL("DELETE FROM WF_Bill WHERE WorkID=" + workID);
			// 删除退回.
			DBAccess.RunSQL("DELETE FROM WF_ReturnWork WHERE WorkID=" + workID);
			// 删除移交.
			// DBAccess.RunSQL("DELETE FROM WF_ForwardWork WHERE WorkID=" +
			// workID);

			// 删除它的工作.
			DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE  FID=" + workID);
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE (WorkID=" + workID + " OR FID=" + workID
					+ " ) AND FK_Flow='" + flowNo + "'");
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkerList WHERE (WorkID=" + workID + " OR FID=" + workID
					+ " ) AND FK_Flow='" + flowNo + "'");

			// 删除所有节点上的数据.
			Nodes nds = fl.getHisNodes();
			for (Node nd : Nodes.convertNodes(nds)) {
				try {
					DBAccess.RunSQL("DELETE FROM ND" + nd.getNodeID() + " WHERE OID=" + workID + " OR FID=" + workID);
				} catch (RuntimeException ex) {
					msg += "@ delete data error " + ex.getMessage();
				}
			}
			if (!msg.equals("")) {
				Log.DebugWriteInfo(msg);
			}
		} catch (RuntimeException ex) {
			String err = "@删除工作流程 Err " + ex.getMessage();
			Log.DefaultLogWriteLine(LogType.Error, err);
			throw new RuntimeException(err);
		}
		info = "@删除流程删除成功";
		/// #endregion 正常的删除信息.

		/// #region 删除该流程下面的子流程.
		if (isDelSubFlow) {
			GenerWorkFlows gwfs = new GenerWorkFlows();
			gwfs.Retrieve(GenerWorkFlowAttr.PWorkID, workID);
			for (Entity obj : GenerWorkFlows.convertEntities(gwfs)) {
				GenerWorkFlow item = (GenerWorkFlow) obj;
				BP.WF.Dev2Interface.Flow_DoDeleteFlowByReal(item.getFK_Flow(), item.getWorkID(), true);
			}
		}
		/// #endregion 删除该流程下面的子流程.

		BP.DA.Log.DefaultLogWriteLineInfo(
				"@[" + fl.getName() + "]流程被[" + WebUser.getNo() + WebUser.getName() + "]删除，WorkID[" + workID + "]。");
		return "已经完成的流程被您删除成功.";
	}

	/**
	 * 删除子线程
	 * 
	 * @return 返回删除结果.
	 */
	private String DoDeleteSubThread() {
		WorkNode wn = this.GetCurrentWorkNode();
		Emp empOfWorker = wn.getHisWork().getRecOfEmp();

		/// #region 正常的删除信息.
		String msg = "";
		try {
			long workId = this.getWorkID();
			String flowNo = this.getHisFlow().getNo();
		} catch (RuntimeException ex) {
			throw new RuntimeException("获取流程的 ID 与流程编号 出现错误。" + ex.getMessage());
		}

		try {
			// 删除质量考核信息.
			DBAccess.RunSQL("DELETE FROM WF_CHEval WHERE WorkID=" + this.getWorkID()); // 删除质量考核数据。

			// 删除抄送信息.
			DBAccess.RunSQL("DELETE FROM WF_CCList WHERE WorkID=" + this.getWorkID());

			// 删除单据信息.
			DBAccess.RunSQL("DELETE FROM WF_Bill WHERE WorkID=" + this.getWorkID());
			// 删除退回.
			DBAccess.RunSQL("DELETE FROM WF_ReturnWork WHERE WorkID=" + this.getWorkID());
			// 删除移交.
			// DBAccess.RunSQL("DELETE FROM WF_ForwardWork WHERE WorkID=" +
			// this.WorkID);

			// 删除它的工作.
			// DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE FID=" + this.WorkID
			// + " AND FK_Flow='" + this.HisFlow.No + "'");
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE (WorkID=" + this.getWorkID() + " ) AND FK_Flow='"
					+ this.getHisFlow().getNo() + "'");
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkerList WHERE (WorkID=" + this.getWorkID() + " ) AND FK_Flow='"
					+ this.getHisFlow().getNo() + "'");

			if (!msg.equals("")) {
				Log.DebugWriteInfo(msg);
			}
		} catch (RuntimeException ex) {
			String err = "@删除工作流程[" + this.getHisStartWork().getOID() + "," + this.getHisStartWork().getTitle()
					+ "] Err " + ex.getMessage();
			Log.DefaultLogWriteLine(LogType.Error, err);
			throw new RuntimeException(err);
		}
		String info = "@删除流程删除成功";
		/// #endregion 正常的删除信息.

		/// #region 处理分流程删除的问题完成率的问题。
		if (1 == 2) {
			// 目前还没有必要，因为在分流点,才有计算完成率的需求.
			String sql = "";
			//
			// * 取出来获取停留点,没有获取到说明没有任何子线程到达合流点的位置.
			//
			sql = "SELECT FK_Node FROM WF_GenerWorkerList WHERE WorkID=" + this.getFID() + " AND IsPass=3";
			int fk_node = DBAccess.RunSQLReturnValInt(sql, 0);
			if (fk_node != 0) {
				// 说明它是待命的状态
				Node nextNode = new Node(fk_node);
				if (nextNode.getPassRate().compareTo(new BigDecimal(0)) > 0) {
					// 找到等待处理节点的上一个点
					Nodes priNodes = nextNode.getFromNodes();
					if (priNodes.size() != 1) {
						throw new RuntimeException("@没有实现子流程不同线程的需求。");
					}

					Node priNode = (Node) priNodes.get(0);

					/// #region 处理完成率
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + priNode.getNodeID()
							+ " AND FID=" + this.getFID() + " AND IsPass=1";
					// java.math.BigDecimal ok =
					// (java.math.BigDecimal)DBAccess.RunSQLReturnValInt(sql);
					BigDecimal ok = new BigDecimal(DBAccess.RunSQLReturnValInt(sql));
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + priNode.getNodeID()
							+ " AND FID=" + this.getFID();
					BigDecimal all = new BigDecimal(DBAccess.RunSQLReturnValInt(sql));
					if (all.equals(0)) {
						// 说明:所有的子线程都被杀掉了, 就应该整个流程结束。
						WorkFlow wf = new WorkFlow(this.getHisFlow(), this.getFID());
						info += "@所有的子线程已经结束。";
						info += "@结束主流程信息。";
						info += "@" + wf.DoFlowOver(ActionType.FlowOver, "合流点流程结束", null, null);
					}

					// java.math.BigDecimal passRate = ok.divide(all) * 100;
					java.math.BigDecimal passRate = ok.divide(all).multiply(new BigDecimal(100));
					if (nextNode.getPassRate().compareTo(passRate) <= 0) {
						// 说明全部的人员都完成了，就让合流点显示它。
						DBAccess.RunSQL("UPDATE WF_GenerWorkerList SET IsPass=0  WHERE IsPass=3  AND WorkID="
								+ this.getFID() + " AND FK_Node=" + fk_node);
					}
					/// #endregion 处理完成率
				}
			} // 结束有待命的状态判断。

			if (fk_node == 0) {
				// 说明:没有找到等待启动工作的合流节点.
				GenerWorkFlow gwf = new GenerWorkFlow(this.getFID());
				Node fND = new Node(gwf.getFK_Node());
				switch (fND.getHisNodeWorkType()) {
				case WorkHL: // 主流程运行到合流点上了
					break;
				default:
					/*** 解决删除最后一个子流程时要把干流程也要删除。 */
					// sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList
					// WHERE FK_Node=" +this.HisGenerWorkFlow +" AND FID=" +
					// this.FID;
					// int num = DBAccess.RunSQLReturnValInt(sql);
					// if (num == 0)
					// {
					// /*说明没有子进程，就要把这个流程执行完成。*/
					// WorkFlow wf = new WorkFlow(this.HisFlow, this.FID);
					// info += "@所有的子线程已经结束。";
					// info += "@结束主流程信息。";
					// info += "@" + wf.DoFlowOver(ActionType.FlowOver,
					// "主流程结束");
					// }

					break;
				}
			}
		}
		/// #endregion

		/// #region 写入删除日志.
		wn.AddToTrack(ActionType.DeleteSubThread, empOfWorker.getNo(), empOfWorker.getName(),
				wn.getHisNode().getNodeID(), wn.getHisNode().getName(), "子线程被:" + WebUser.getName() + "删除.");
		/// #endregion 写入删除日志.

		return "子线程被删除成功.";
	}

	/**
	 * 删除已经完成的流程
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param isDelSubFlow
	 *            是否删除子流程
	 * @return 删除错误会抛出异常
	 */
	public static void DeleteFlowByReal(String flowNo, long workid, boolean isDelSubFlow) {
		Flow fl = new Flow(flowNo);
		// 检查流程是否完成，如果没有完成就调用workflow流程删除.
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workid);
		if (gwf.RetrieveFromDBSources() != 0) {
			if (gwf.getWFState() != WFState.Complete) {
				WorkFlow wf = new WorkFlow(flowNo, workid);
				wf.DoDeleteWorkFlowByReal(isDelSubFlow);
				return;
			}
		}

		/// #region 删除流程表单的数据.
		FrmNodes fns = new FrmNodes();
		fns.Retrieve(FrmNodeAttr.FK_Flow, flowNo);
		String strs = "";
		for (Entity obj : FrmNodes.convertEntities(fns)) {
			FrmNode nd = (FrmNode) obj;
			if (strs.contains("@" + nd.getFK_Frm()) == true) {
				continue;
			}

			strs += "@" + nd.getFK_Frm() + "@";
			try {
				MapData md = new MapData(nd.getFK_Frm());
				DBAccess.RunSQL("DELETE FROM " + md.getPTable() + " WHERE OID=" + workid);
			} catch (java.lang.Exception e) {
			}
		}
		/// #endregion 删除流程表单的数据.

		// 删除流程数据.
		DBAccess.RunSQL("DELETE FROM ND" + Integer.parseInt(flowNo) + "Track WHERE WorkID=" + workid);
		DBAccess.RunSQL("DELETE FROM " + fl.getPTable() + " WHERE OID=" + workid);
		DBAccess.RunSQL("DELETE FROM WF_CHEval WHERE  WorkID=" + workid); // 删除质量考核数据。

		/// #region 正常的删除信息.
		BP.DA.Log.DefaultLogWriteLineInfo(
				"@[" + fl.getName() + "]流程被[" + WebUser.getNo() + WebUser.getName() + "]删除，WorkID[" + workid + "]。");
		String msg = "";

		// 删除单据信息.
		DBAccess.RunSQL("DELETE FROM WF_CCList WHERE WorkID=" + workid);
		// 删除单据信息.
		DBAccess.RunSQL("DELETE FROM WF_Bill WHERE WorkID=" + workid);
		// 删除退回.
		DBAccess.RunSQL("DELETE FROM WF_ReturnWork WHERE WorkID=" + workid);

		// 删除它的工作.
		DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE  FID=" + workid + " AND FK_Flow='" + flowNo + "'");
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE (WorkID=" + workid + " OR FID=" + workid
				+ " ) AND FK_Flow='" + flowNo + "'");
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkerList WHERE (WorkID=" + workid + " OR FID=" + workid
				+ " ) AND FK_Flow='" + flowNo + "'");

		// 删除所有节点上的数据.
		Nodes nds = new Nodes(flowNo); // this.HisFlow.HisNodes;
		for (Node nd : Nodes.convertNodes(nds)) {
			try {
				DBAccess.RunSQL("DELETE FROM ND" + nd.getNodeID() + " WHERE OID=" + workid + " OR FID=" + workid);
			} catch (RuntimeException ex) {
				msg += "@ delete data error " + ex.getMessage();
			}
		}
		if (!msg.equals("")) {
			Log.DebugWriteInfo(msg);
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 正常的删除信息.
	}

	/**
	 * 删除子线城
	 * 
	 * @return 删除的消息
	 */
	public final String DoDeleteSubThread2015() {
		if (this.getFID() == 0) {
			throw new RuntimeException("@该流程非子线城流程实例，不能执行该方法。");
		}

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 正常的删除信息.
		String msg = "";
		try {
			long workId = this.getWorkID();
			String flowNo = this.getHisFlow().getNo();
		} catch (RuntimeException ex) {
			throw new RuntimeException("获取流程的 ID 与流程编号 出现错误。" + ex.getMessage());
		}

		try {
			// 删除质量考核信息.
			DBAccess.RunSQL("DELETE FROM WF_CHEval WHERE WorkID=" + this.getWorkID()); // 删除质量考核数据。

			// 删除抄送信息.
			DBAccess.RunSQL("DELETE FROM WF_CCList WHERE WorkID=" + this.getWorkID());

			// 删除单据信息.
			DBAccess.RunSQL("DELETE FROM WF_Bill WHERE WorkID=" + this.getWorkID());
			// 删除退回.
			DBAccess.RunSQL("DELETE FROM WF_ReturnWork WHERE WorkID=" + this.getWorkID());
			// 删除移交.
			// DBAccess.RunSQL("DELETE FROM WF_ForwardWork WHERE WorkID=" +
			// this.WorkID);

			// 删除它的工作.
			// DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE FID=" + this.WorkID
			// + " AND FK_Flow='" + this.HisFlow.No + "'");
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE WorkID=" + this.getWorkID());
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkerList WHERE WorkID=" + this.getWorkID());

			if (!msg.equals("")) {
				Log.DebugWriteInfo(msg);
			}
		} catch (RuntimeException ex) {
			String err = "@删除工作流程[" + this.getHisStartWork().getOID() + "," + this.getHisStartWork().getTitle()
					+ "] Err " + ex.getMessage();
			Log.DefaultLogWriteLine(LogType.Error, err);
			throw new RuntimeException(err);
		}
		String info = "@删除流程删除成功";
		/// #endregion 正常的删除信息.

		/// #region 处理分流程删除的问题完成率的问题。
		if (1 == 2) {
			/// #warning
			/// 应该删除一个子线城后，就需要计算完成率的问题。但是目前应用到该场景极少,因为删除子线城的动作，1，合流点。2，分流点。能够看到河流点信息，说明已经到达了完成率了。

			// 目前还没有必要，因为在分流点,才有计算完成率的需求.
			String sql = "";
			//
			// * 取出来获取停留点,没有获取到说明没有任何子线程到达合流点的位置.
			//
			sql = "SELECT FK_Node FROM WF_GenerWorkerList WHERE WorkID=" + this.getFID() + " AND IsPass=3";
			int fk_node = DBAccess.RunSQLReturnValInt(sql, 0);
			if (fk_node != 0) {
				// 说明它是待命的状态
				Node nextNode = new Node(fk_node);
				if (nextNode.getPassRate().compareTo(new BigDecimal(0)) > 0) {
					// 找到等待处理节点的上一个点
					Nodes priNodes = nextNode.getFromNodes();
					if (priNodes.size() != 1) {
						throw new RuntimeException("@没有实现子流程不同线程的需求。");
					}

					// Node priNode = (Node)priNodes[0];
					Node priNode = (Node) priNodes.get(0);

					/// #region 处理完成率
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + priNode.getNodeID()
							+ " AND FID=" + this.getFID() + " AND IsPass=1";
					BigDecimal ok = new BigDecimal(DBAccess.RunSQLReturnValInt(sql));
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + priNode.getNodeID()
							+ " AND FID=" + this.getFID();
					BigDecimal all = new BigDecimal(DBAccess.RunSQLReturnValInt(sql));
					if (all.equals(0)) {
						// 说明:所有的子线程都被杀掉了, 就应该整个流程结束。
						WorkFlow wf = new WorkFlow(this.getHisFlow(), this.getFID());
						info += "@所有的子线程已经结束。";
						info += "@结束主流程信息。";
						info += "@" + wf.DoFlowOver(ActionType.FlowOver, "合流点流程结束", null, null);
					}

					BigDecimal passRate = ok.divide(all).multiply(new BigDecimal(100));
					if (nextNode.getPassRate().compareTo(passRate) <= 0) {
						// 说明: 全部的人员都完成了，就让合流点显示它。
						DBAccess.RunSQL("UPDATE WF_GenerWorkerList SET IsPass=0  WHERE IsPass=3  AND WorkID="
								+ this.getFID() + " AND FK_Node=" + fk_node);
					}
					/// #endregion 处理完成率
				}
			} // 结束有待命的状态判断。

			if (fk_node == 0) {
				// 说明:没有找到等待启动工作的合流节点.
				GenerWorkFlow gwf = new GenerWorkFlow(this.getFID());
				Node fND = new Node(gwf.getFK_Node());
				switch (fND.getHisNodeWorkType()) {
				case WorkHL: // 主流程运行到合流点上了
					break;
				default:
					/*** 解决删除最后一个子流程时要把干流程也要删除。 */
					// sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList
					// WHERE FK_Node=" +this.HisGenerWorkFlow +" AND FID=" +
					// this.FID;
					// int num = DBAccess.RunSQLReturnValInt(sql);
					// if (num == 0)
					// {
					// /*说明没有子进程，就要把这个流程执行完成。*/
					// WorkFlow wf = new WorkFlow(this.HisFlow, this.FID);
					// info += "@所有的子线程已经结束。";
					// info += "@结束主流程信息。";
					// info += "@" + wf.DoFlowOver(ActionType.FlowOver,
					// "主流程结束");
					// }

					break;
				}
			}
		}
		/// #endregion

		return "子线程被删除成功.";

	}

	/**
	 * 彻底的删除流程
	 * 
	 * @param isDelSubFlow
	 *            是否要删除子流程
	 * @return 删除的消息
	 */
	public final String DoDeleteWorkFlowByReal(boolean isDelSubFlow) {
		if (this.getFID() != 0) {
			return DoDeleteSubThread2015();
		}

		String info = "";
		WorkNode wn = null;
		try {
			wn = this.GetCurrentWorkNode();
		} catch (RuntimeException ex) {
		}
		// 处理删除前事件。
		if (wn != null) {
			wn.getHisFlow().DoFlowEventEntity(EventListOfNode.BeforeFlowDel, wn.getHisNode(), wn.getHisWork(), null);
		}

		/// #region 删除流程表单的数据.
		FrmNodes fns = new FrmNodes();
		fns.Retrieve(FrmNodeAttr.FK_Flow, this.getHisFlow().getNo());
		String strs = "";
		for (Entity obj : FrmNodes.convertEntities(fns)) {
			FrmNode nd = (FrmNode) obj;
			if (strs.contains("@" + nd.getFK_Frm()) == true) {
				continue;
			}

			strs += "@" + nd.getFK_Frm() + "@";
			try {
				MapData md = new MapData(nd.getFK_Frm());
				DBAccess.RunSQL("DELETE FROM " + md.getPTable() + " WHERE OID=" + this.getWorkID());
			} catch (java.lang.Exception e) {
			}
		}
		/// #endregion 删除流程表单的数据.

		// 删除流程数据.
		DBAccess.RunSQL("DELETE FROM ND" + Integer.parseInt(this.getHisFlow().getNo()) + "Track WHERE WorkID="
				+ this.getWorkID());
		DBAccess.RunSQL("DELETE FROM " + this.getHisFlow().getPTable() + " WHERE OID=" + this.getWorkID());
		DBAccess.RunSQL("DELETE FROM WF_CHEval WHERE  WorkID=" + this.getWorkID()); // 删除质量考核数据。

		/// #region 正常的删除信息.
		BP.DA.Log.DefaultLogWriteLineInfo("@[" + this.getHisFlow().getName() + "]流程被[" + WebUser.getNo()
				+ WebUser.getName() + "]删除，WorkID[" + this.getWorkID() + "]。");
		String msg = "";
		try {
			long workId = this.getWorkID();
			String flowNo = this.getHisFlow().getNo();
		} catch (RuntimeException ex) {
			throw new RuntimeException("获取流程的 ID 与流程编号 出现错误。" + ex.getMessage());
		}

		try {
			// 删除单据信息.
			DBAccess.RunSQL("DELETE FROM WF_CCList WHERE WorkID=" + this.getWorkID());
			// 删除单据信息.
			DBAccess.RunSQL("DELETE FROM WF_Bill WHERE WorkID=" + this.getWorkID());
			// 删除退回.
			DBAccess.RunSQL("DELETE FROM WF_ReturnWork WHERE WorkID=" + this.getWorkID());

			// 删除它的工作.
			DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE  FID=" + this.getWorkID() + " AND FK_Flow='"
					+ this.getHisFlow().getNo() + "'");
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE (WorkID=" + this.getWorkID() + " OR FID="
					+ this.getWorkID() + " ) AND FK_Flow='" + this.getHisFlow().getNo() + "'");
			DBAccess.RunSQL("DELETE FROM WF_GenerWorkerList WHERE (WorkID=" + this.getWorkID() + " OR FID="
					+ this.getWorkID() + " ) AND FK_Flow='" + this.getHisFlow().getNo() + "'");

			// 删除所有节点上的数据.
			Nodes nds = this.getHisFlow().getHisNodes();
			for (Node nd : Nodes.convertNodes(nds)) {
				try {
					DBAccess.RunSQL("DELETE FROM ND" + nd.getNodeID() + " WHERE OID=" + this.getWorkID() + " OR FID="
							+ this.getWorkID());
				} catch (RuntimeException ex) {
					msg += "@ delete data error " + ex.getMessage();
				}
			}
			if (!msg.equals("")) {
				Log.DebugWriteInfo(msg);
			}
		} catch (RuntimeException ex) {
			String err = "@删除工作流程[" + this.getHisStartWork().getOID() + "," + this.getHisStartWork().getTitle()
					+ "] Err " + ex.getMessage();
			Log.DefaultLogWriteLine(LogType.Error, err);
			throw new RuntimeException(err);
		}
		info = "@删除流程删除成功";
		/// #endregion 正常的删除信息.

		/// #region 处理分流程删除的问题完成率的问题。
		if (this.getFID() != 0) {
			String sql = "";
			//
			// * 取出来获取停留点,没有获取到说明没有任何子线程到达合流点的位置.
			//
			sql = "SELECT FK_Node FROM WF_GenerWorkerList WHERE WorkID=" + wn.getHisWork().getFID() + " AND IsPass=3";
			int fk_node = DBAccess.RunSQLReturnValInt(sql, 0);
			if (fk_node != 0) {
				// 说明它是待命的状态
				Node nextNode = new Node(fk_node);
				if (nextNode.getPassRate().compareTo(new BigDecimal(0)) > 0) {
					// 找到等待处理节点的上一个点
					Nodes priNodes = nextNode.getFromNodes();
					if (priNodes.size() != 1) {
						throw new RuntimeException("@没有实现子流程不同线程的需求。");
					}

					Node priNode = (Node) priNodes.get(0);

					/// #region 处理完成率
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + priNode.getNodeID()
							+ " AND FID=" + wn.getHisWork().getFID() + " AND IsPass=1";
					java.math.BigDecimal ok = new BigDecimal(DBAccess.RunSQLReturnValInt(sql));
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + priNode.getNodeID()
							+ " AND FID=" + wn.getHisWork().getFID();
					java.math.BigDecimal all = new BigDecimal(DBAccess.RunSQLReturnValInt(sql));
					if (all.equals(0)) {
						// 说明:所有的子线程都被杀掉了, 就应该整个流程结束。
						WorkFlow wf = new WorkFlow(this.getHisFlow(), this.getFID());
						info += "@所有的子线程已经结束。";
						info += "@结束主流程信息。";
						info += "@" + wf.DoFlowOver(ActionType.FlowOver, "合流点流程结束", null, null);
					}

					java.math.BigDecimal passRate = ok.divide(all).multiply(new BigDecimal(100));
					if (nextNode.getPassRate().compareTo(passRate) <= 0) {
						// 说明全部的人员都完成了，就让合流点显示它。
						DBAccess.RunSQL("UPDATE WF_GenerWorkerList SET IsPass=0  WHERE IsPass=3  AND WorkID="
								+ wn.getHisWork().getFID() + " AND FK_Node=" + fk_node);
					}
					/// #endregion 处理完成率
				}
			} // 结束有待命的状态判断。

			if (fk_node == 0) {
				// 说明:没有找到等待启动工作的合流节点.
				GenerWorkFlow gwf = new GenerWorkFlow(this.getFID());
				Node fND = new Node(gwf.getFK_Node());
				switch (fND.getHisNodeWorkType()) {
				case WorkHL: // 主流程运行到合流点上了
					break;
				default:
					// 解决删除最后一个子流程时要把干流程也要删除。
					sql = "SELECT COUNT(*) AS Num FROM WF_GenerWorkerList WHERE FK_Node=" + wn.getHisNode().getNodeID()
							+ " AND FID=" + wn.getHisWork().getFID();
					int num = DBAccess.RunSQLReturnValInt(sql);
					if (num == 0) {
						// 说明没有子进程，就要把这个流程执行完成。
						WorkFlow wf = new WorkFlow(this.getHisFlow(), this.getFID());
						info += "@所有的子线程已经结束。";
						info += "@结束主流程信息。";
						info += "@" + wf.DoFlowOver(ActionType.FlowOver, "主流程结束", null, null);
					}
					break;
				}
			}
		}
		/// #endregion

		/// #region 删除该流程下面的子流程.
		if (isDelSubFlow) {
			GenerWorkFlows gwfs = new GenerWorkFlows();
			gwfs.Retrieve(GenerWorkFlowAttr.PWorkID, this.getWorkID());

			for (Entity obj : GenerWorkFlows.convertEntities(gwfs)) {
				GenerWorkFlow item = (GenerWorkFlow) obj;
				BP.WF.Dev2Interface.Flow_DoDeleteFlowByReal(item.getFK_Flow(), item.getWorkID(), true);
			}
		}
		/// #endregion 删除该流程下面的子流程.

		// 处理删除hou事件。
		if (wn != null) {
			wn.getHisFlow().DoFlowEventEntity(EventListOfNode.AfterFlowDel, wn.getHisNode(), wn.getHisWork(), null);
		}
		return info;
	}

	/**
	 * 删除工作流程记录日志，并保留运动轨迹.
	 * 
	 * @param isDelSubFlow
	 *            是否要删除子流程
	 * @return
	 */
	public final String DoDeleteWorkFlowByWriteLog(String info, boolean isDelSubFlow) {
		GERpt rpt = new GERpt("ND" + Integer.parseInt(this.getHisFlow().getNo()) + "Rpt", this.getWorkID());
		WorkFlowDeleteLog log = new WorkFlowDeleteLog();
		log.setOID(this.getWorkID());
		try {
			log.Copy(rpt);
			log.setDeleteDT(DataType.getCurrentDataTime());
			log.setOperDept(WebUser.getFK_Dept());
			log.setOperDeptName(WebUser.getFK_DeptName());
			log.setOper(WebUser.getNo());
			log.setDeleteNote(info);
			log.setOID(this.getWorkID());
			log.setFK_Flow(this.getHisFlow().getNo());
			try {
				log.InsertAsOID(log.getOID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return DoDeleteWorkFlowByReal(isDelSubFlow);
		} catch (RuntimeException ex) {
			log.CheckPhysicsTable();
			log.Delete();
			return ex.getStackTrace().toString();
		}
	}

	/// #region 流程的强制终止\删除 或者恢复使用流程,
	/**
	 * 恢复流程.
	 * 
	 * @param msg
	 *            回复流程的原因
	 */
	public final void DoComeBackWorkFlow(String msg) {
		try {
			// 设置产生的工作流程为
			GenerWorkFlow gwf = new GenerWorkFlow(this.getWorkID());
			gwf.setWFState(WFState.Runing);
			gwf.DirectUpdate();

			// 增加消息
			WorkNode wn = this.GetCurrentWorkNode();
			GenerWorkerLists wls = new GenerWorkerLists(wn.getHisWork().getOID(), wn.getHisNode().getNodeID());
			if (wls.size() == 0) {
				throw new RuntimeException("@恢复流程出现错误,产生的工作者列表");
			}
			BP.WF.MsgsManager.AddMsgs(wls, "恢复的流程", wn.getHisNode().getName(), "回复的流程");
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLine(LogType.Error, "@恢复流程出现错误." + ex.getMessage());
			throw new RuntimeException("@恢复流程出现错误." + ex.getMessage());
		}
	}
	/// #endregion

	/**
	 * 得到当前的进行中的工作。
	 * 
	 * @return
	 */
	public final WorkNode GetCurrentWorkNode() {
		int currNodeID = 0;
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(this.getWorkID());
		if (gwf.RetrieveFromDBSources() == 0) {
			this.DoFlowOver(ActionType.FlowOver, "非正常结束，没有找到当前的流程记录。", null, null);
			throw new RuntimeException("@" + String.format("工作流程%1$s已经完成。", this.getHisStartWork().getTitle()));
		}

		Node nd = new Node(gwf.getFK_Node());
		Work work = nd.getHisWork();
		work.setOID(this.getWorkID());
		work.setNodeID(nd.getNodeID());
		work.SetValByKey("FK_Dept", WebUser.getFK_Dept());
		if (work.RetrieveFromDBSources() == 0) {
			Log.DefaultLogWriteLineError(
					"@WorkID=" + this.getWorkID() + ",FK_Node=" + gwf.getFK_Node() + ".不应该出现查询不出来工作."); // 没有找到当前的工作节点的数据，流程出现未知的异常。
			work.setRec(WebUser.getNo());
			try {
				work.Insert();
			} catch (RuntimeException ex) {
				Log.DefaultLogWriteLineError("@没有找到当前的工作节点的数据，流程出现未知的异常" + ex.getMessage() + ",不应该出现"); // 没有找到当前的工作节点的数据
			}
		}
		work.setFID(gwf.getFID());

		WorkNode wn = new WorkNode(work, nd);
		return wn;
	}

	/**
	 * 结束分流的节点
	 * 
	 * @param fid
	 * @return
	 */
	public final String DoFlowOverFeiLiu(GenerWorkFlow gwf) {
		// 查询出来有少没有完成的流程。
		int i = BP.DA.DBAccess.RunSQLReturnValInt(
				"SELECT COUNT(*) FROM WF_GenerWorkFlow WHERE FID=" + gwf.getFID() + " AND WFState!=1");
		switch (i) {
		case 0:
			throw new RuntimeException("@不应该的错误。");
		case 1:
			BP.DA.DBAccess
					.RunSQL("DELETE FROM WF_GenerWorkFlow  WHERE FID=" + gwf.getFID() + " OR WorkID=" + gwf.getFID());
			BP.DA.DBAccess
					.RunSQL("DELETE FROM WF_GenerWorkerlist WHERE FID=" + gwf.getFID() + " OR WorkID=" + gwf.getFID());
			BP.DA.DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE FID=" + gwf.getFID());

			Work tempVar = this.getHisFlow().getHisStartNode().getHisWork();
			StartWork wk = (StartWork) ((tempVar instanceof StartWork) ? tempVar : null);
			wk.setOID(gwf.getFID());
			wk.Update();

			return "@当前的工作已经完成，该流程上所有的工作都已经完成。";
		default:
			BP.DA.DBAccess.RunSQL("UPDATE WF_GenerWorkFlow SET WFState=1 WHERE WorkID=" + this.getWorkID());
			BP.DA.DBAccess.RunSQL("UPDATE WF_GenerWorkerlist SET IsPass=1 WHERE WorkID=" + this.getWorkID());
			return "@当前的工作已经完成。";
		}
	}

	/**
	 * 处理子流程完成.
	 * 
	 * @return
	 */
	public final String DoFlowSubOver() {
		GenerWorkFlow gwf = new GenerWorkFlow(this.getWorkID());
		Node nd = new Node(gwf.getFK_Node());

		DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow   WHERE WorkID=" + this.getWorkID());
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkerlist WHERE WorkID=" + this.getWorkID());

		String sql = "SELECT count(*) FROM WF_GenerWorkFlow WHERE  FID=" + this.getFID();
		int num = DBAccess.RunSQLReturnValInt(sql);
		if (DBAccess.RunSQLReturnValInt(sql) == 0) {
			// 说明这是最后一个
			WorkFlow wf = new WorkFlow(gwf.getFK_Flow(), this.getFID());
			wf.DoFlowOver(ActionType.FlowOver, "子流程结束", null, null);
			return "@当前子流程已完成，主流程已完成。";
		} else {
			return "@当前子流程已完成，主流程还有(" + num + ")个子流程未完成。";
		}
	}

	/**
	 * 让父亲流程自动发送到下一步骤上去.
	 * 
	 */
	public final String LetParentFlowAutoSendNextSetp() {
		if (this.getHisGenerWorkFlow().getPWorkID() == 0) {
			return "";
		}

		if (this.getHisFlow().getIsAutoSendSubFlowOver() == false) {
			return "";
		}

		// 检查是否是最后的一个.
		int num = BP.WF.Dev2Interface.Flow_NumOfSubFlowRuning(this.getHisGenerWorkFlow().getPWorkID(),
				this.getHisGenerWorkFlow().getWorkID());
		if (num != 0) {
			return "";
		}

		// 检查父流程是否存在?
		GenerWorkFlow pGWF = new GenerWorkFlow();
		pGWF.setWorkID(this.getHisGenerWorkFlow().getPWorkID());
		if (pGWF.RetrieveFromDBSources() == 0) {
			return ""; // 父流程被删除了也不能执行。
		}

		if (pGWF.getWFState() == WFState.Complete) {
			return ""; // 父流程已经完成也不能执行.
		}

		// 检查父流程的当前停留的节点是否还是发起子流程的节点？
		if (this.getHisGenerWorkFlow().getPNodeID() != pGWF.getFK_Node()) {
			return "";
		}

		// 找到调用该流程的人，这里判断不严禁，如果有多个人处理该节点，就只能找到当前人处理了。
		// string pEmp = DBAccess.RunSQLReturnStringIsNull("SELECT FK_Emp FROM
		// WF_GenerWorkerList WHERE WorkID=" + this.HisGenerWorkFlow.PWorkID + "
		// AND FK_Node=" + this.HisGenerWorkFlow.FK_Node + " AND IsPass=0",
		// null);
		// NDXRptBaseAttr

		// 因为前面已经对他进行个直接更新所以这里需要进行查询之后在执行更新.
		this.getHisGenerWorkFlow().RetrieveFromDBSources();

		try {
			// 取得调起子流程的人员.
			String pEmp = this.getHisGenerWorkFlow().getPEmp();
			if (StringHelper.isNullOrEmpty(pEmp) == true) {
				throw new RuntimeException("@没有找到调起子流程的工作人员.");
			}
			Emp emp = new Emp();
			emp.setNo(pEmp);
			if (emp.RetrieveFromDBSources() == 0) {
				throw new RuntimeException("@吊起子流程上的人员编号(" + pEmp + ")已不存在,无法启动父流程.");
			}

			// 改变当前节点的状态，不然父流程如果做了让所有的子流程发送完成后才能运行的设置后，不能不能让其发送了.
			this.getHisGenerWorkFlow().setWFState(WFState.Complete);
			this.getHisGenerWorkFlow().DirectUpdate();

			GERpt rpt = new GERpt("ND" + Integer.parseInt(this.getHisFlow().getNo()) + "Rpt", this.getWorkID());

			// 让当前人员向下发送，但是这种发送一定不要检查发送权限，否则的话就出错误，不能发送下去.
			SendReturnObjs objs = BP.WF.Dev2Interface.Node_SendWork(this.getHisGenerWorkFlow().getPFlowNo(),
					pGWF.getWorkID(), rpt.getRow(), null, 0, null, emp.getNo(), emp.getName(), emp.getFK_Dept(),
					emp.getFK_DeptText(), null);

			this.getHisGenerWorkFlow().setWFState(WFState.Complete);
			this.getHisGenerWorkFlow().DirectUpdate();

			return "@当前节点是子流程的最后一个流程, 成功让父流程运行到下一个节点." + objs.ToMsgOfHtml();
		} catch (RuntimeException ex) {
			this.getHisGenerWorkFlow().setWFState(WFState.Complete);
			this.getHisGenerWorkFlow().DirectUpdate();
			return "@在最后一个子流程完成后，让父流程的节点自动发送时，出现错误:" + ex.getMessage();
		}
	}

	/**
	 * 执行流程完成
	 * 
	 * @param at
	 * @param stopMsg
	 * @return
	 */
	public final String DoFlowOver(ActionType at, String stopMsg, Node currNode, GERpt rpt) {
		if (null == currNode) {
			return null;
		}
		// 调用结束前事件.
		this.getHisFlow().DoFlowEventEntity(EventListOfNode.FlowOverBefore, currNode, rpt, null);

		if (StringHelper.isNullOrEmpty(stopMsg)) {
			stopMsg += "流程结束";
		}

		String exp = currNode.getFocusField();
		if (StringHelper.isNullOrEmpty(exp) == false && exp.length() > 1) {
			if (rpt != null) {
				stopMsg += Glo.DealExp(exp, rpt, null);
			}
		}

		String msg = "";
		if (this.getIsMainFlow() == false) {
			// 处理子流程完成
			return this.DoFlowSubOver();
		}

		/// #region 处理明细表的汇总.
		Node currND = new Node(this.getHisGenerWorkFlow().getFK_Node());

		// 处理明细数据的copy问题。 首先检查：当前节点（最后节点）是否有明细表。
		MapDtls dtls = currND.getMapData().getMapDtls(); // new MapDtls("ND" +
															// nd.NodeID);
		int i = 0;
		for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
			i++;
			// 查询出该明细表中的数据。
			GEDtls dtlDatas = new GEDtls(dtl.getNo());
			dtlDatas.Retrieve(GEDtlAttr.RefPK, this.getWorkID());

			GEDtl geDtl = null;
			try {
				// 创建一个Rpt对象。
				geDtl = new GEDtl(
						"ND" + Integer.parseInt(this.getHisFlow().getNo()) + "RptDtl" + (new Integer(i)).toString());
				geDtl.ResetDefaultVal();
			} catch (java.lang.Exception e) {
				/// #warning 此处需要修复。
				continue;
			}
		}
		this._IsComplete = 1;
		/// #endregion 处理明细表的汇总.

		/// #region 处理后续的业务.

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "DELETE FROM WF_GenerFH WHERE FID=" + dbstr + "FID";
		ps.Add(GenerFHAttr.FID, this.getWorkID());
		DBAccess.RunSQL(ps);

		if (Glo.getIsDeleteGenerWorkFlow() == true) {
			// 是否删除流程注册表的数据？
			ps = new Paras();
			ps.SQL = "DELETE FROM WF_GenerWorkFlow WHERE WorkID=" + dbstr + "WorkID1 OR FID=" + dbstr + "WorkID2 ";
			ps.Add("WorkID1", this.getWorkID());
			ps.Add("WorkID2", this.getWorkID());
			DBAccess.RunSQL(ps);
		} else {
			// 求出参与人,以方便已经完成的工作查询.
			ps = new Paras();
			ps.SQL = "SELECT EmpFrom FROM ND" + Integer.parseInt(this._HisFlow.getNo()) + "Track WHERE WorkID=" + dbstr
					+ "WorkID OR FID=" + dbstr + "FID ";
			ps.Add("WorkID", this.getWorkID());
			ps.Add("FID", this.getWorkID());
			DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(ps);
			String emps = "";
			for (DataRow dr : dt.Rows) {
				if (emps.contains("@" + dr.getValue(0).toString()) == true) {
					continue;
				}
				emps += "@" + dr.getValue(0).toString();
			}
			emps = emps + "@";

			// 更新流程注册信息.
			ps = new Paras();
			ps.SQL = "UPDATE WF_GenerWorkFlow SET WFState=" + dbstr + "WFState,WFSta=" + dbstr + "WFSta,Emps=" + dbstr
					+ "Emps,MyNum=1 WHERE WorkID=" + dbstr + "WorkID ";
			ps.Add("WFState", WFState.Complete.getValue());
			ps.Add("WFSta", WFSta.Complete.getValue());
			ps.Add("Emps", emps);
			ps.Add("WorkID", this.getWorkID());
			DBAccess.RunSQL(ps);
		}

		// 清除工作者.
		ps = new Paras();
		ps.SQL = "DELETE FROM WF_GenerWorkerlist WHERE WorkID=" + dbstr + "WorkID1 OR FID=" + dbstr + "WorkID2 ";
		ps.Add("WorkID1", this.getWorkID());
		ps.Add("WorkID2", this.getWorkID());
		DBAccess.RunSQL(ps);

		// 设置流程完成状态.
		ps = new Paras();
		ps.SQL = "UPDATE " + this.getHisFlow().getPTable() + " SET WFState=" + dbstr + "WFState WHERE OID=" + dbstr
				+ "OID";
		ps.Add("WFState", WFState.Complete.getValue());
		ps.Add("OID", this.getWorkID());
		DBAccess.RunSQL(ps);

		// 加入轨迹.
		WorkNode wn = new WorkNode(getWorkID(), this.getHisGenerWorkFlow().getFK_Node());
		wn.AddToTrack(at, WebUser.getNo(), WebUser.getName(), wn.getHisNode().getNodeID(), wn.getHisNode().getName(),
				stopMsg);

		// 调用结束后事件.
		this.getHisFlow().DoFlowEventEntity(EventListOfNode.FlowOverAfter, currNode, rpt, null);
		/// #endregion 处理后续的业务.

		// 执行最后一个子流程发送后的检查，不管是否成功，都要结束该流程。
		msg += this.LetParentFlowAutoSendNextSetp();

		// string dbstr = BP.Sys.SystemConfig.AppCenterDBVarStr;

		/// #region 处理审核问题,更新审核组件插入的审核意见中的 到节点，到人员。
		ps = new Paras();
		ps.SQL = "UPDATE ND" + Integer.parseInt(currNode.getFK_Flow()) + "Track SET NDTo=" + dbstr + "NDTo,NDToT="
				+ dbstr + "NDToT,EmpTo=" + dbstr + "EmpTo,EmpToT=" + dbstr + "EmpToT WHERE NDFrom=" + dbstr
				+ "NDFrom AND EmpFrom=" + dbstr + "EmpFrom AND WorkID=" + dbstr + "WorkID AND ActionType="
				+ ActionType.WorkCheck.getValue();
		ps.Add(TrackAttr.NDTo, currNode.getNodeID());
		ps.Add(TrackAttr.NDToT, "");
		ps.Add(TrackAttr.EmpTo, "");
		ps.Add(TrackAttr.EmpToT, "");

		ps.Add(TrackAttr.NDFrom, currNode.getNodeID());
		ps.Add(TrackAttr.EmpFrom, WebUser.getNo());
		ps.Add(TrackAttr.WorkID, this.getWorkID());
		BP.DA.DBAccess.RunSQL(ps);
		/// #endregion 处理审核问题.

		// if (string.IsNullOrEmpty(msg) == true)
		// msg = "流程成功结束.";
		return msg;
	}

	public final String GenerFHStartWorkInfo() {
		String msg = "";
		DataTable dt = DBAccess.RunSQLReturnTable(
				"SELECT Title,RDT,Rec,OID FROM ND" + this.getStartNodeID() + " WHERE FID=" + this.getFID());
		switch (dt.Rows.size()) {
		case 0:
			Node nd = new Node(this.getStartNodeID());
			throw new RuntimeException(
					"@没有找到他们开始节点的数据，流程异常。FID=" + this.getFID() + "，节点：" + nd.getName() + "节点ID：" + nd.getNodeID());
		case 1:
			msg = String.format("@发起人： %1$s  日期：%2$s 发起的流程 标题：%3$s ，已经成功完成。", dt.Rows.get(0).get("Rec").toString(),
					dt.Rows.get(0).get("RDT").toString(), dt.Rows.get(0).get("Title").toString());
			break;
		default:
			msg = "@下列(" + dt.Rows.size() + ")位人员发起的流程已经完成。";
			for (DataRow dr : dt.Rows) {
				msg += "<br>发起人：" + dr.get("Rec") + " 发起日期：" + dr.get("RDT") + " 标题：" + dr.get("Title")
						+ "<a href='./../../WF/WFRpt.jsp?WorkID=" + dr.get("OID") + "&FK_Flow="
						+ this.getHisFlow().getNo() + "' target=_blank>详细...</a>";
			}
			break;
		}
		return msg;
	}

	public final int getStartNodeID() {
		return Integer.parseInt(this.getHisFlow().getNo() + "01");
	}

	/**
	 * 抄送到
	 * 
	 * @param dt
	 */
	public final String CCTo(DataTable dt) {
		if (dt.Rows.size() == 0) {
			return "";
		}

		String emps = "";
		String empsExt = "";

		String ip = "127.0.0.1";
		// System.Net.IPAddress[] addressList =
		// System.Net.Dns.GetHostByName(System.Net.Dns.GetHostName()).AddressList;
		String[] addressList = getAllLocalIP();
		if (addressList.length > 1) {
			ip = addressList[1].toString();
		} else {
			ip = addressList[0].toString();
		}

		for (DataRow dr : dt.Rows) {
			String no = dr.getValue(0).toString();
			String name = dr.getValue(1).toString();

			emps += BP.WF.Glo.DealUserInfoShowModel(no, name);
		}

		Paras pss = new Paras();
		pss.Add("Sender", WebUser.getNo());
		pss.Add("Receivers", emps);
		pss.Add("Title", "工作流抄送：工作名称:" + this.getHisFlow().getName() + "，最后处理人：" + WebUser.getName());
		pss.Add("Context", "工作轨迹 http://" + ip + "/WF/WFRpt.jsp?WorkID=" + this.getWorkID() + "&FID=0");

		try {
			DBAccess.RunSP("CCstaff", pss);
			return "@" + empsExt;
		} catch (RuntimeException ex) {
			return "@抄送出现错误，没有把该流程的信息抄送到(" + empsExt + ")请联系管理员检查系统异常" + ex.getMessage();
		}
	}

	/**
	 * 执行冻结
	 * 
	 * @param msg
	 *            冻结原因
	 */
	public final String DoFix(String fixMsg) {
		if (this.getHisGenerWorkFlow().getWFState() == WFState.Fix) {
			throw new RuntimeException("@当前已经是冻结的状态您不能执行再冻结.");
		}

		if (StringHelper.isNullOrEmpty(fixMsg)) {
			fixMsg = "无";
		}

		/*** 获取它的工作者，向他们发送消息。 */
		// GenerWorkerLists wls = new GenerWorkerLists(this.WorkID,
		// this.HisFlow.No);

		// string url = Glo.ServerIP + "/" + this.VirPath + this.AppType +
		// "/WorkOpt/OneWork/Track.jsp?FK_Flow=" + this.HisFlow.No + "&WorkID="
		// + this.WorkID + "&FID=" + this.HisGenerWorkFlow.FID + "&FK_Node=" +
		// this.HisGenerWorkFlow.FK_Node;
		// string mailDoc = "详细信息:<A href='" + url + "'>打开流程轨迹</A>.";
		// string title = "工作:" + this.HisGenerWorkFlow.Title + " 被" +
		// WebUser.Name + "冻结" + fixMsg;
		// string emps = "";
		// foreach (GenerWorkerList wl in wls)
		// {
		// if (wl.IsEnable == false)
		// continue; //不发送给禁用的人。

		// emps += wl.FK_Emp + "," + wl.FK_EmpText + ";";

		// //写入消息。
		// BP.WF.Dev2Interface.Port_SendMsg(wl.FK_Emp, title, mailDoc, "Fix" +
		// wl.WorkID, BP.Sys.SMSMsgType.Etc, wl.FK_Flow, wl.FK_Node, wl.WorkID,
		// wl.FID);
		// }

		// 执行 WF_GenerWorkFlow 冻结.
		int sta = WFState.Fix.getValue();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET WFState=" + dbstr + "WFState WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.WFState, sta);
		ps.Add(GenerWorkFlowAttr.WorkID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新流程报表的状态。
		ps = new Paras();
		ps.SQL = "UPDATE " + this.getHisFlow().getPTable() + " SET WFState=" + dbstr + "WFState WHERE OID=" + dbstr
				+ "OID";
		ps.Add(GERptAttr.WFState, sta);
		ps.Add(GERptAttr.OID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 记录日志..
		WorkNode wn = new WorkNode(this.getWorkID(), this.getHisGenerWorkFlow().getFK_Node());

		// wn.AddToTrack(ActionType.Info, WebUser.No, WebUser.Name,
		// wn.HisNode.NodeID, wn.HisNode.Name, fixMsg,);

		return "已经成功执行冻结";
	}

	/**
	 * 执行解除冻结
	 * 
	 * @param msg
	 *            冻结原因
	 */
	public final String DoUnFix(String unFixMsg) {
		if (this.getHisGenerWorkFlow().getWFState() != WFState.Fix) {
			throw new RuntimeException("@当前非冻结的状态您不能执行解除冻结.");
		}

		if (StringHelper.isNullOrEmpty(unFixMsg)) {
			unFixMsg = "无";
		}

		/*** 获取它的工作者，向他们发送消息。 */
		// GenerWorkerLists wls = new GenerWorkerLists(this.WorkID,
		// this.HisFlow.No);

		// string url = Glo.ServerIP + "/" + this.VirPath + this.AppType +
		// "/WorkOpt/OneWork/Track.jsp?FK_Flow=" + this.HisFlow.No + "&WorkID="
		// + this.WorkID + "&FID=" + this.HisGenerWorkFlow.FID + "&FK_Node=" +
		// this.HisGenerWorkFlow.FK_Node;
		// string mailDoc = "详细信息:<A href='" + url + "'>打开流程轨迹</A>.";
		// string title = "工作:" + this.HisGenerWorkFlow.Title + " 被" +
		// WebUser.Name + "冻结" + unFixMsg;
		// string emps = "";
		// foreach (GenerWorkerList wl in wls)
		// {
		// if (wl.IsEnable == false)
		// continue; //不发送给禁用的人。

		// emps += wl.FK_Emp + "," + wl.FK_EmpText + ";";

		// //写入消息。
		// BP.WF.Dev2Interface.Port_SendMsg(wl.FK_Emp, title, mailDoc, "Fix" +
		// wl.WorkID, BP.Sys.SMSMsgType.Self, wl.FK_Flow, wl.FK_Node, wl.WorkID,
		// wl.FID);
		// }

		// 执行 WF_GenerWorkFlow 冻结.
		int sta = WFState.Runing.getValue();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET WFState=" + dbstr + "WFState WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.WFState, sta);
		ps.Add(GenerWorkFlowAttr.WorkID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新流程报表的状态。
		ps = new Paras();
		ps.SQL = "UPDATE " + this.getHisFlow().getPTable() + " SET WFState=" + dbstr + "WFState WHERE OID=" + dbstr
				+ "OID";
		ps.Add(GERptAttr.WFState, sta);
		ps.Add(GERptAttr.OID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 记录日志..
		WorkNode wn = new WorkNode(this.getWorkID(), this.getHisGenerWorkFlow().getFK_Node());
		// wn.AddToTrack(ActionType.Info, WebUser.No, WebUser.Name,
		// wn.HisNode.NodeID, wn.HisNode.Name, unFixMsg);

		return "已经成功执行解除冻结:";
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 基本属性
	/**
	 * 他的节点
	 * 
	 */
	private Nodes _HisNodes = null;

	/**
	 * 节点s
	 * 
	 */
	public final Nodes getHisNodes() {
		if (this._HisNodes == null) {
			this._HisNodes = this.getHisFlow().getHisNodes();
		}
		return this._HisNodes;
	}

	/**
	 * 工作节点s(普通的工作节点)
	 * 
	 */
	private WorkNodes _HisWorkNodesOfWorkID = null;

	/**
	 * 工作节点s
	 * 
	 */
	public final WorkNodes getHisWorkNodesOfWorkID() {
		if (this._HisWorkNodesOfWorkID == null) {
			this._HisWorkNodesOfWorkID = new WorkNodes();
			this._HisWorkNodesOfWorkID.GenerByWorkID(this.getHisFlow(), this.getWorkID());
		}
		return this._HisWorkNodesOfWorkID;
	}

	/**
	 * 工作节点s
	 * 
	 */
	private WorkNodes _HisWorkNodesOfFID = null;

	/**
	 * 工作节点s
	 * 
	 */
	public final WorkNodes getHisWorkNodesOfFID() {
		if (this._HisWorkNodesOfFID == null) {
			this._HisWorkNodesOfFID = new WorkNodes();
			this._HisWorkNodesOfFID.GenerByFID(this.getHisFlow(), this.getFID());
		}
		return this._HisWorkNodesOfFID;
	}

	/**
	 * 工作流程
	 * 
	 */
	private Flow _HisFlow = null;

	/**
	 * 工作流程
	 * 
	 */
	public final Flow getHisFlow() {
		return this._HisFlow;
	}

	private GenerWorkFlow _HisGenerWorkFlow = null;

	public final GenerWorkFlow getHisGenerWorkFlow() {
		if (_HisGenerWorkFlow == null) {
			_HisGenerWorkFlow = new GenerWorkFlow(this.getWorkID());
		}
		return _HisGenerWorkFlow;
	}

	public final void setHisGenerWorkFlow(GenerWorkFlow value) {
		_HisGenerWorkFlow = value;
	}

	/**
	 * 工作ID
	 * 
	 */
	private long _WorkID = 0;

	/**
	 * 工作ID
	 * 
	 */
	public final long getWorkID() {
		return this._WorkID;
	}

	/**
	 * 工作ID
	 * 
	 */
	private long _FID = 0;

	/**
	 * 工作ID
	 * 
	 */
	public final long getFID() {
		return this._FID;
	}

	public final void setFID(long value) {
		this._FID = value;
	}

	/**
	 * 是否是干流
	 * 
	 */
	public final boolean getIsMainFlow() {
		if (this.getFID() != 0 && this.getFID() != this.getWorkID()) {
			return false;
		} else {
			return true;
		}
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造方法
	public WorkFlow(String fk_flow, long wkid) {
		this.setHisGenerWorkFlow(new GenerWorkFlow());
		this.getHisGenerWorkFlow().RetrieveByAttr(GenerWorkerListAttr.WorkID, wkid);
		this._FID = this.getHisGenerWorkFlow().getFID();
		if (wkid == 0) {
			throw new RuntimeException("@没有指定工作ID, 不能创建工作流程.");
		}
		Flow flow = new Flow(fk_flow);
		this._HisFlow = flow;
		this._WorkID = wkid;

	}

	public WorkFlow(Flow flow, long wkid) {
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(wkid);
		gwf.RetrieveFromDBSources();

		this._FID = gwf.getFID();
		if (wkid == 0) {
			throw new RuntimeException("@没有指定工作ID, 不能创建工作流程.");
		}
		// Flow flow= new Flow(FlowNo);
		this._HisFlow = flow;
		this._WorkID = wkid;
	}

	/**
	 * 建立一个工作流事例
	 * 
	 * @param flow
	 *            流程No
	 * @param wkid
	 *            工作ID
	 */
	public WorkFlow(Flow flow, long wkid, long fid) {
		this._FID = fid;
		if (wkid == 0) {
			throw new RuntimeException("@没有指定工作ID, 不能创建工作流程.");
		}
		// Flow flow= new Flow(FlowNo);
		this._HisFlow = flow;
		this._WorkID = wkid;
	}

	public WorkFlow(String FK_flow, long wkid, long fid) {
		this._FID = fid;

		Flow flow = new Flow(FK_flow);
		if (wkid == 0) {
			throw new RuntimeException("@没有指定工作ID, 不能创建工作流程.");
		}
		// Flow flow= new Flow(FlowNo);
		this._HisFlow = flow;
		this._WorkID = wkid;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 公共属性

	/**
	 * 开始工作
	 * 
	 */
	private StartWork _HisStartWork = null;

	/**
	 * 他开始的工作.
	 * 
	 */
	public final StartWork getHisStartWork() {
		if (_HisStartWork == null) {
			StartWork en = (StartWork) this.getHisFlow().getHisStartNode().getHisWork();
			en.setOID(this.getWorkID());
			en.setFID(this.getFID());
			if (en.RetrieveFromDBSources() == 0) {
				en.RetrieveFID();
			}
			_HisStartWork = en;
		}
		return _HisStartWork;
	}

	/**
	 * 开始工作节点
	 * 
	 */
	private WorkNode _HisStartWorkNode = null;

	/**
	 * 他开始的工作.
	 * 
	 */
	public final WorkNode getHisStartWorkNode() {
		if (_HisStartWorkNode == null) {
			Node nd = this.getHisFlow().getHisStartNode();
			StartWork en = (StartWork) nd.getHisWork();
			en.setOID(this.getWorkID());
			en.Retrieve();

			WorkNode wn = new WorkNode(en, nd);
			_HisStartWorkNode = wn;

		}
		return _HisStartWorkNode;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 运算属性
	public int _IsComplete = -1;

	/**
	 * 是不是完成
	 * 
	 */
	public final boolean getIsComplete() {

		// bool s = !DBAccess.IsExits("select workid from WF_GenerWorkFlow WHERE
		// WorkID=" + this.WorkID + " AND FK_Flow='" + this.HisFlow.No + "'");

		GenerWorkFlow generWorkFlow = new GenerWorkFlow(this.getWorkID());
		if (generWorkFlow.getWFState() == WFState.Complete) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 是不是完成
	 * 
	 */
	public final String getIsCompleteStr() {
		if (this.getIsComplete()) {
			return "已";
		} else {
			return "未";
		}
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 静态方法

	/**
	 * 是否这个工作人员能执行这个工作
	 * 
	 * @param nodeId
	 *            节点
	 * @param empId
	 *            工作人员
	 * @return 能不能执行
	 */
	public static boolean IsCanDoWorkCheckByEmpStation(int nodeId, String empId) {
		boolean isCan = false;
		// 判断岗位对应关系是不是能够执行.
		String sql = "SELECT a.FK_Node FROM WF_NodeStation a,  " + BP.WF.Glo.getEmpStation()
				+ " b WHERE (a.FK_Station=b.FK_Station) AND (a.FK_Node=" + nodeId + " AND b.FK_Emp='" + empId + "' )";
		isCan = DBAccess.IsExits(sql);
		if (isCan) {
			return true;
		}
		// 判断他的主要工作岗位能不能执行它.
		sql = "select FK_Node from WF_NodeStation WHERE FK_Node=" + nodeId
				+ " AND ( FK_Station in (select FK_Station from " + BP.WF.Glo.getEmpStation() + " WHERE FK_Emp='"
				+ empId + "') ) ";
		return DBAccess.IsExits(sql);
	}

	/**
	 * 是否这个工作人员能执行这个工作
	 * 
	 * @param nodeId
	 *            节点
	 * @param dutyNo
	 *            工作人员
	 * @return 能不能执行
	 */
	public static boolean IsCanDoWorkCheckByEmpDuty(int nodeId, String dutyNo) {
		String sql = "SELECT a.FK_Node FROM WF_NodeDuty  a,  Port_EmpDuty b WHERE (a.FK_Duty=b.FK_Duty) AND (a.FK_Node="
				+ nodeId + " AND b.FK_Duty=" + dutyNo + ")";
		if (DBAccess.RunSQLReturnTable(sql).Rows.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否这个工作人员能执行这个工作
	 * 
	 * @param nodeId
	 *            节点
	 * @param DeptNo
	 *            工作人员
	 * @return 能不能执行
	 */
	public static boolean IsCanDoWorkCheckByEmpDept(int nodeId, String DeptNo) {
		String sql = "SELECT a.FK_Node FROM WF_NodeDept  a,  Port_EmpDept b WHERE (a.FK_Dept=b.FK_Dept) AND (a.FK_Node="
				+ nodeId + " AND b.FK_Dept=" + DeptNo + ")";
		if (DBAccess.RunSQLReturnTable(sql).Rows.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 在物理上能构作这项工作的人员。
	 * 
	 * @param nodeId
	 *            节点ID
	 * @return
	 */
	public static DataTable CanDoWorkEmps(int nodeId) {
		String sql = "select a.FK_Node, b.EmpID from WF_NodeStation  a,  " + BP.WF.Glo.getEmpStation()
				+ " b WHERE (a.FK_Station=b.FK_Station) AND (a.FK_Node=" + nodeId + " )";
		return DBAccess.RunSQLReturnTable(sql);
	}

	/**
	 * GetEmpsBy
	 * 
	 * @param dt
	 * @return
	 */
	public final Emps GetEmpsBy(DataTable dt) {
		// 形成能够处理这件事情的用户几何。
		Emps emps = new Emps();
		for (DataRow dr : dt.Rows) {
			emps.AddEntity(new Emp(dr.get("EmpID").toString()));
		}
		return emps;
	}
	/// #endregion

	/// #region 流程方法
	private String _AppType = null;

	/**
	 * 虚拟目录的路径
	 */
	public final String getAppType() {
		if (_AppType == null) {
			if (BP.Sys.SystemConfig.getIsBSsystem() == false) {
				_AppType = "WF";
			} else {
				if (WebUser.getIsWap()) {
					_AppType = "WF/WAP";
				} else {
					boolean b = BP.Sys.Glo.getRequest().getRequestURI().toLowerCase().contains("oneflow");
					if (b) {
						_AppType = "WF/OneFlow";
					} else {
						_AppType = "WF";
					}
				}
			}
		}
		return _AppType;
	}

	private String _VirPath = null;

	/**
	 * 虚拟目录的路径
	 * 
	 */
	public final String getVirPath() {
		if (_VirPath == null) {
			if (BP.Sys.SystemConfig.getIsBSsystem()) {
				_VirPath = BP.Sys.Glo.getRequest().getRealPath("/");
			} else {
				_VirPath = "";
			}
		}
		return _VirPath;
	}

	/**
	 * 执行挂起
	 * 
	 * @param way
	 *            挂起方式
	 * @param relData
	 *            释放日期
	 * @param hungNote
	 *            挂起原因
	 * @return
	 */
	public final String DoHungUp(HungUpWay way, String relData, String hungNote) {
		if (this.getHisGenerWorkFlow().getWFState().equals(WFState.HungUp)) {
			throw new RuntimeException("@当前已经是挂起的状态您不能执行在挂起.");
		}

		if (StringHelper.isNullOrEmpty(hungNote)) {
			hungNote = "无";
		}

		if (way == HungUpWay.SpecDataRel) {
			if (relData.length() < 10) {
				throw new RuntimeException("@解除挂起的日期不正确(" + relData + ")");
			}
		}
		if (relData == null) {
			relData = "";
		}

		HungUp hu = new HungUp();
		hu.setFK_Node(this.getHisGenerWorkFlow().getFK_Node());
		hu.setWorkID(this.getWorkID());
		hu.setMyPK(hu.getFK_Node() + "_" + hu.getWorkID());
		hu.setHungUpWay(way); // 挂起方式.
		hu.setDTOfHungUp(DataType.getCurrentDataTime()); // 挂起时间
		hu.setRec(WebUser.getNo()); // 挂起人
		hu.setDTOfUnHungUp(relData); // 解除挂起时间。
		hu.setNote(hungNote);
		hu.Insert();

		// 获取它的工作者，向他们发送消息。
		GenerWorkerLists wls = new GenerWorkerLists(this.getWorkID(), this.getHisFlow().getNo());
		String url = Glo.getServerIP() + "/" + this.getVirPath() + this.getAppType()
				+ "/WorkOpt/OneWork/Track.jsp?FK_Flow=" + this.getHisFlow().getNo() + "&WorkID=" + this.getWorkID()
				+ "&FID=" + this.getHisGenerWorkFlow().getFID() + "&FK_Node=" + this.getHisGenerWorkFlow().getFK_Node();
		String mailDoc = "详细信息:<A href='" + url + "'>打开流程轨迹</A>.";
		String title = "工作:" + this.getHisGenerWorkFlow().getTitle() + " 被" + WebUser.getName() + "挂起" + hungNote;
		String emps = "";
		for (Entity obj : GenerWorkerLists.convertEntities(wls)) {
			GenerWorkerList wl = (GenerWorkerList) obj;
			if (wl.getIsEnable() == false) {
				continue; // 不发送给禁用的人。
			}

			// BP.WF.Port.WFEmp emp = new Port.WFEmp(wl.FK_Emp);
			emps += wl.getFK_Emp() + "," + wl.getFK_EmpText() + ";";

			// 写入消息。
			BP.WF.Dev2Interface.Port_SendMsg(wl.getFK_Emp(), title, mailDoc, "HungUp" + wl.getWorkID(), SMSMsgType.Self,
					wl.getFK_Flow(), wl.getFK_Node(), wl.getWorkID(), wl.getFID());
		}

		// 执行 WF_GenerWorkFlow 挂起.
		int hungSta = WFState.HungUp.getValue();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET WFState=" + dbstr + "WFState WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.WFState, hungSta);
		ps.Add(GenerWorkFlowAttr.WorkID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新流程报表的状态。
		ps = new Paras();
		ps.SQL = "UPDATE " + this.getHisFlow().getPTable() + " SET WFState=" + dbstr + "WFState WHERE OID=" + dbstr
				+ "OID";
		ps.Add(GERptAttr.WFState, hungSta);
		ps.Add(GERptAttr.OID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新工作者的挂起时间。
		ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkerlist SET DTOfHungUp=" + dbstr + "DTOfHungUp,DTOfUnHungUp=" + dbstr
				+ "DTOfUnHungUp, HungUpTimes=HungUpTimes+1 WHERE FK_Node=" + dbstr + "FK_Node AND WorkID=" + dbstr
				+ "WorkID";
		ps.Add(GenerWorkerListAttr.DTOfHungUp, DataType.getCurrentDataTime());
		ps.Add(GenerWorkerListAttr.DTOfUnHungUp, relData);

		ps.Add(GenerWorkerListAttr.FK_Node, this.getHisGenerWorkFlow().getFK_Node());
		ps.Add(GenerWorkFlowAttr.WorkID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 记录日志..
		WorkNode wn = new WorkNode(this.getWorkID(), this.getHisGenerWorkFlow().getFK_Node());
		wn.AddToTrack(ActionType.HungUp, WebUser.getNo(), WebUser.getName(), wn.getHisNode().getNodeID(),
				wn.getHisNode().getName(), hungNote);
		return "已经成功执行挂起,并且已经通知给:" + emps;
	}

	/**
	 * 取消挂起
	 * 
	 * @return
	 */
	public final String DoUnHungUp() {
		if (this.getHisGenerWorkFlow().getWFState() != WFState.HungUp) {
			throw new RuntimeException("@非挂起状态,您不能解除挂起.");
		}

		// 执行解除挂起.
		int sta = WFState.Runing.getValue();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET WFState=" + dbstr + "WFState WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.WFState, sta);
		ps.Add(GenerWorkFlowAttr.WorkID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新流程报表的状态。
		ps = new Paras();
		ps.SQL = "UPDATE " + this.getHisFlow().getPTable() + " SET WFState=" + dbstr + "WFState WHERE OID=" + dbstr
				+ "OID";
		ps.Add(GERptAttr.WFState, sta);
		ps.Add(GERptAttr.OID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新工作者的挂起时间。
		ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkerlist SET  DTOfUnHungUp=" + dbstr + "DTOfUnHungUp WHERE FK_Node=" + dbstr
				+ "FK_Node AND WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkerListAttr.DTOfUnHungUp, DataType.getCurrentDataTime());
		ps.Add(GenerWorkerListAttr.FK_Node, this.getHisGenerWorkFlow().getFK_Node());
		ps.Add(GenerWorkFlowAttr.WorkID, this.getWorkID());
		DBAccess.RunSQL(ps);

		// 更新 HungUp
		HungUp hu = new HungUp();
		hu.setFK_Node(this.getHisGenerWorkFlow().getFK_Node());
		hu.setWorkID(this.getHisGenerWorkFlow().getWorkID());
		hu.setMyPK(hu.getFK_Node() + "_" + hu.getWorkID());
		if (hu.RetrieveFromDBSources() == 0) {
			throw new RuntimeException("@系统错误，没有找到挂起点");
		}

		hu.setDTOfUnHungUp(DataType.getCurrentDataTime()); // 挂起时间
		hu.Update();

		// 更新他的主键。
		ps = new Paras();
		ps.SQL = "UPDATE WF_HungUp SET MyPK=" + SystemConfig.getAppCenterDBVarStr() + "MyPK WHERE MyPK=" + dbstr
				+ "MyPK1";
		ps.Add("MyPK", BP.DA.DBAccess.GenerGUID());
		ps.Add("MyPK1", hu.getMyPK());
		DBAccess.RunSQL(ps);

		// 获取它的工作者，向他们发送消息。
		GenerWorkerLists wls = new GenerWorkerLists(this.getWorkID(), this.getHisFlow().getNo());
		String url = Glo.getServerIP() + "/" + this.getVirPath() + this.getAppType()
				+ "/WorkOpt/OneWork/Track.jsp?FK_Flow=" + this.getHisFlow().getNo() + "&WorkID=" + this.getWorkID()
				+ "&FID=" + this.getHisGenerWorkFlow().getFID() + "&FK_Node=" + this.getHisGenerWorkFlow().getFK_Node();
		String mailDoc = "详细信息:<A href='" + url + "'>打开流程轨迹</A>.";
		String title = "工作:" + this.getHisGenerWorkFlow().getTitle() + " 被" + WebUser.getName() + "解除挂起.";
		String emps = "";
		for (Entity obj : GenerWorkerLists.convertEntities(wls)) {
			GenerWorkerList wl = (GenerWorkerList) obj;
			if (wl.getIsEnable() == false) {
				continue; // 不发送给禁用的人。
			}

			emps += wl.getFK_Emp() + "," + wl.getFK_EmpText() + ";";

			// 写入消息。
			BP.WF.Dev2Interface.Port_SendMsg(wl.getFK_Emp(), title, mailDoc,
					"HungUp" + wl.getFK_Node() + this.getWorkID(), SMSMsgType.Self, getHisGenerWorkFlow().getFK_Flow(),
					getHisGenerWorkFlow().getFK_Node(), this.getWorkID(), this.getFID());

			// 写入消息。
			// Glo.SendMsg(wl.FK_Emp, title, mailDoc);
		}

		// 记录日志..
		WorkNode wn = new WorkNode(this.getWorkID(), this.getHisGenerWorkFlow().getFK_Node());
		wn.AddToTrack(ActionType.UnHungUp, WebUser.getNo(), WebUser.getName(), wn.getHisNode().getNodeID(),
				wn.getHisNode().getName(), "解除挂起,已经通知给:" + emps);
		return null;
	}

	/**
	 * 撤消移交
	 * 
	 * @return
	 */
	public final String DoUnShift() {
		GenerWorkFlow gwf = new GenerWorkFlow(this.getWorkID());
		GenerWorkerLists wls = new GenerWorkerLists();
		wls.Retrieve(GenerWorkerListAttr.WorkID, this.getWorkID(), GenerWorkerListAttr.FK_Node, gwf.getFK_Node());
		if (wls.size() == 0) {
			return "移交失败没有当前的工作。";
		}

		Node nd = new Node(gwf.getFK_Node());
		Work wk1 = nd.getHisWork();
		wk1.setOID(this.getWorkID());
		wk1.Retrieve();

		// 记录日志.
		WorkNode wn = new WorkNode(wk1, nd);
		wn.AddToTrack(ActionType.UnShift, WebUser.getNo(), WebUser.getName(), nd.getNodeID(), nd.getName(), "撤消移交");

		if (wls.size() == 1) {
			GenerWorkerList wl = (GenerWorkerList) wls.get(0);
			wl.setFK_Emp(WebUser.getNo());
			wl.setFK_EmpText(WebUser.getName());
			wl.setIsEnable(true);
			wl.setIsPass(false);
			wl.Update();
			return "@撤消移交成功，<a href='" + Glo.getCCFlowAppPath() + "WF/MyFlow.jsp?FK_Flow=" + this.getHisFlow().getNo()
					+ "&FK_Node=" + wl.getFK_Node() + "&FID=" + wl.getFID() + "&WorkID=" + this.getWorkID()
					+ "'><img src='" + Glo.getCCFlowAppPath() + "WF/Img/Btn/Do.gif' border=0/>执行工作</A>";
		}

		GenerWorkerList mywl = null;
		for (Entity obj : GenerWorkerLists.convertEntities(wls)) {
			GenerWorkerList wl = (GenerWorkerList) obj;
			if (wl.getFK_Emp().equals(WebUser.getNo())) {
				wl.setFK_Emp(WebUser.getNo());
				wl.setFK_EmpText(WebUser.getName());
				wl.setIsEnable(true);
				wl.setIsPass(false);
				wl.Update();
				mywl = wl;
			} else {
				wl.Delete();
			}
		}
		if (mywl != null) {
			return "@撤消移交成功，<a href='" + Glo.getCCFlowAppPath() + "WF/MyFlow.jsp?FK_Flow=" + this.getHisFlow().getNo()
					+ "&FK_Node=" + mywl.getFK_Node() + "&FID=" + mywl.getFID() + "&WorkID=" + this.getWorkID()
					+ "'><img src='" + Glo.getCCFlowAppPath() + "WF/Img/Btn/Do.gif' border=0/>执行工作</A>";
		}

		GenerWorkerList wk = (GenerWorkerList) wls.get(0);
		GenerWorkerList wkNew = new GenerWorkerList();
		wkNew.Copy(wk);
		wkNew.setFK_Emp(WebUser.getNo());
		wkNew.setFK_EmpText(WebUser.getName());
		wkNew.setIsEnable(true);
		wkNew.setIsPass(false);
		wkNew.Insert();

		// 删除撤销信息.
		BP.DA.DBAccess.RunSQL(
				"DELETE FROM WF_ShiftWork WHERE WorkID=" + this.getWorkID() + " AND FK_Node=" + wk.getFK_Node());

		return "@撤消移交成功，<a href='" + Glo.getCCFlowAppPath() + "WF/MyFlow.jsp?FK_Flow=" + this.getHisFlow().getNo()
				+ "&FK_Node=" + wk.getFK_Node() + "&FID=" + wk.getFID() + "&WorkID=" + this.getWorkID() + "'><img src='"
				+ Glo.getCCFlowAppPath() + "WF/Img/Btn/Do.gif' border=0/>执行工作</A>";
	}

	public static String[] getAllLocalIP() {
		ArrayList ar = new ArrayList();
		Enumeration netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
			InetAddress ip = (InetAddress) ni.getInetAddresses().nextElement();
			if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
				logger.debug("Interface " + ni.getName() + " seems to be InternetInterface. I'll take it...");
				;
			} else {
				ar.add(ip.getHostAddress());
			}
		}
		return (String[]) ar.toArray();
	}
	/// #endregion
}