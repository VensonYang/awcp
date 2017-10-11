package BP.WF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import BP.DA.AtPara;
import BP.DA.DBAccess;
import BP.DA.DataColumn;
import BP.DA.DataRow;
import BP.DA.DataRowCollection;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Paras;
import BP.En.QueryObject;
import BP.Port.Emp;
import BP.Port.WebUser;
import BP.Sys.GEDtl;
import BP.Sys.GEDtlAttr;
import BP.Sys.GEDtls;
import BP.Sys.GEEntity;
import BP.Sys.PubClass;
import BP.Sys.SystemConfig;
import BP.Sys.Frm.EventListOfNode;
import BP.Sys.Frm.FrmEventList;
import BP.Sys.Frm.FrmEvents;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDtl;
import BP.Sys.Frm.MapDtls;
import BP.Sys.Frm.MapExt;
import BP.Sys.Frm.MapExtAttr;
import BP.Tools.DateUtils;
import BP.Tools.StringHelper;
import BP.WF.Data.GERpt;
import BP.WF.Data.GERptAttr;
import BP.WF.Data.NDXRptBaseAttr;
import BP.WF.Entity.FrmWorkCheck;
import BP.WF.Entity.GenerFHAttr;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.GenerWorkFlowAttr;
import BP.WF.Entity.GenerWorkFlows;
import BP.WF.Entity.GenerWorkerList;
import BP.WF.Entity.GenerWorkerListAttr;
import BP.WF.Entity.GenerWorkerLists;
import BP.WF.Entity.ShiftWork;
import BP.WF.Entity.TaskSta;
import BP.WF.Entity.Track;
import BP.WF.Entity.TrackAttr;
import BP.WF.Entity.WFSta;
import BP.WF.Port.MsgSta;
import BP.WF.Port.SMS;
import BP.WF.Port.SMSAttr;
import BP.WF.Port.SMSMsgType;
import BP.WF.Port.WFEmp;
import BP.WF.Template.ButtonState;
import BP.WF.Template.Direction;
import BP.WF.Template.DirectionAttr;
import BP.WF.Template.Directions;
import BP.WF.Template.Flow;
import BP.WF.Template.Flows;
import BP.WF.Template.Node;
import BP.WF.Template.NodeReturnAttr;
import BP.WF.Template.NodeReturns;
import BP.WF.Template.NodeStations;
import BP.WF.Template.Nodes;
import BP.WF.Template.TransferCustom;
import BP.WF.Template.AccepterRole.SelectAccper;
import BP.WF.Template.AccepterRole.SelectAccperAttr;
import BP.WF.Template.AccepterRole.SelectAccpers;
import BP.WF.Template.CC.CC;
import BP.WF.Template.CC.CCList;
import BP.WF.Template.CC.CCListAttr;
import BP.WF.Template.CC.CCLists;
import BP.WF.Template.CC.CCSta;
import BP.WF.Template.Condition.Cond;
import BP.WF.Template.Condition.CondAttr;
import BP.WF.Template.Ext.Selector;
import BP.WF.Template.PubLib.ActionType;
import BP.WF.Template.PubLib.AskforHelpSta;
import BP.WF.Template.PubLib.CCRole;
import BP.WF.Template.PubLib.CCWriteTo;
import BP.WF.Template.PubLib.DataStoreModel;
import BP.WF.Template.PubLib.DraftRole;
import BP.WF.Template.PubLib.FlowAttr;
import BP.WF.Template.PubLib.HungUpWay;
import BP.WF.Template.PubLib.NodeAttr;
import BP.WF.Template.PubLib.ReadReceipts;
import BP.WF.Template.PubLib.RunModel;
import BP.WF.Template.PubLib.SaveModel;
import BP.WF.Template.PubLib.SubThreadType;
import BP.WF.Template.PubLib.TodolistModel;
import BP.WF.Template.PubLib.WFState;
import BP.WF.Template.WorkBase.StartWorkAttr;
import BP.WF.Template.WorkBase.Work;
import BP.WF.Template.WorkBase.WorkSysFieldAttr;
import TL.ContextHolderUtils;

/**
 * 此接口为程序员二次开发使用,在阅读代码前请注意如下事项. 1, CCFlow的对外的接口都是以静态方法来实现的. 2, 以 DB_
 * 开头的是需要返回结果集合的接口. 3, 以 Flow_ 是流程接口. 4, 以 Node_ 是节点接口. 5, 以 Port_ 是组织架构接口. 6, 以
 * DTS_ 是调度. data tranr system. 7, 以 UI_ 是流程的功能窗口 8, 以 WorkOpt_ 用工作处理器相关的接口。
 * 
 */
public class Dev2Interface {

	/**
	 * 写入消息 用途可以处理提醒.
	 * 
	 * @param sendToUserNo
	 *            发送给的操作员ID
	 * @param sendDT
	 *            发送时间，如果null 则表示立刻发送。
	 * @param title
	 *            标题
	 * @param doc
	 *            内容
	 * @param msgFlag
	 *            消息标记
	 * @return 写入成功或者失败.
	 */
	public static boolean WriteToSMS(String sendToUserNo, String sendDT, String title, String doc, String msgFlag) {
		SMS.SendMsg(sendToUserNo, title, doc, msgFlag, "Info");
		return true;
	}

	/// <summary>
	/// 检查指定节点上的所有子流程是否完成？
	/// </summary>
	/// <param name="nodeID">节点ID</param>
	/// <param name="workID">工作ID</param>
	/// <returns>返回该节点上的子流程是否完成？</returns>
	public static boolean Flow_CheckAllSubFlowIsOver(int nodeID, int workID) {
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "SELECT COUNT(WorkID) FROM WF_GenerWorkFlow WHERE  PNodeID=" + dbstr + "PNodeID AND PWorkID=" + dbstr
				+ "PWorkID AND WFState!=" + dbstr + "WFState ";
		ps.Add(GenerWorkFlowAttr.PNodeID, nodeID);
		ps.Add(GenerWorkFlowAttr.PWorkID, workID);
		ps.Add(GenerWorkFlowAttr.WFState, WFState.Complete.getValue());

		if (BP.DA.DBAccess.RunSQLReturnValInt(ps) == 0)
			return true;
		else
			return false;
	}

	/**
	 * 待办工作数量
	 * 
	 */
	public static int getTodolist_EmpWorks() {
		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String wfSql = "  WFState=" + WFState.Askfor.getValue() + " OR WFState=" + WFState.Runing.getValue()
				+ "  OR WFState=" + WFState.AskForReplay.getValue() + " OR WFState=" + WFState.Shift.getValue()
				+ " OR WFState=" + WFState.ReturnSta.getValue() + " OR WFState=" + WFState.Fix.getValue();
		String sql;

		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			if (BP.WF.Glo.getIsEnableTaskPool() == true) {
				ps.SQL = "SELECT count(WorkID) as Num FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND TaskSta!=1 ";
			} else {
				ps.SQL = "SELECT count(WorkID) as Num FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp ";
			}

			ps.Add("FK_Emp", WebUser.getNo());
			// throw new Exception(ps.SQL);

			// BP.DA.Log.DebugWriteInfo(ps.SQL);
			return BP.DA.DBAccess.RunSQLReturnValInt(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			if (BP.WF.Glo.getIsEnableTaskPool() == true) {
				ps.SQL = "SELECT count(WorkID) as Num FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND TaskSta!=0  ";
			} else {
				ps.SQL = "SELECT count(WorkID) as Num FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp ";
			}
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case SpecFlows:
			if (BP.WF.Glo.getIsEnableTaskPool() == true) {
				ps.SQL = "SELECT count(WorkID) as Num FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows() + " AND TaskSta!=0   ";
			} else {
				ps.SQL = "SELECT count(WorkID) as Num FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows();
			}

			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnValInt(ps);
	}

	/**
	 * 抄送数量
	 */
	public static int getTodolist_CCWorks() {
		Paras ps = new Paras();
		ps.SQL = "SELECT count(MyPK) as Num FROM WF_CCList WHERE CCTo=" + SystemConfig.getAppCenterDBVarStr()
				+ "FK_Emp AND Sta=0";
		ps.Add("FK_Emp", WebUser.getNo());
		return DBAccess.RunSQLReturnValInt(ps, 0);
	}

	/**
	 * 获取抄送人员的
	 * 
	 * @param node
	 * @return
	 */
	public static String GetNode_CCList(WorkNode node) {
		String ccers = null;

		if (node.getHisNode().getHisCCRole() == CCRole.AutoCC
				|| node.getHisNode().getHisCCRole() == CCRole.HandAndAuto) {
			try {
				// 如果是自动抄送
				CC cc = node.getHisNode().getHisCC();

				DataTable table = cc.GenerCCers(node.rptGe);
				if (table.Rows.size() > 0) {
					String ccMsg = "@消息自动抄送给";

					for (DataRow dr : table.Rows) {
						ccers += dr.get(0) + ",";
					}
				}
			} catch (RuntimeException ex) {
				throw new RuntimeException("@处理操送时出现错误:" + ex.getMessage());
			}
		}

		/// 执行抄送 BySysCCEmps
		if (node.getHisNode().getHisCCRole() == CCRole.BySysCCEmps) {
			CC cc = node.getHisNode().getHisCC();

			// 取出抄送人列表
			String temps = node.rptGe.GetValStrByKey("SysCCEmps");
			if (!StringHelper.isNullOrEmpty(temps)) {
				String[] cclist = temps.split("[|]", -1);
				java.util.Hashtable ht = new java.util.Hashtable();
				for (String item : cclist) {
					String[] tmp = item.split("[,]", -1);
					ccers += tmp[0] + ",";
				}
			}
		}
		return ccers;
	}

	/**
	 * 返回挂起流程数量
	 */
	public static int getTodolist_HungUpNum() {
		String sql = "select  COUNT(WorkID) AS Num from WF_GenerWorkFlow where WFState=4 and  WorkID in (SELECT distinct WorkID FROM WF_HungUp WHERE Rec='"
				+ WebUser.getNo() + "')";
		return BP.DA.DBAccess.RunSQLReturnValInt(sql);
	}

	/**
	 * 在途的工作数量
	 */
	public static int getTodolist_Runing() {
		String sql;
		int state = WFState.Runing.getValue();
		if (WebUser.getIsAuthorize()) {
			// 如果是授权状态.
			WFEmp emp = new WFEmp(WebUser.getNo());
			sql = "SELECT count( distinct a.WorkID ) as Num FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
					+ WebUser.getNo() + "' AND B.IsEnable=1 AND (B.IsPass=1 OR B.IsPass<0) AND A.FK_Flow IN "
					+ emp.getAuthorFlows();
			return BP.DA.DBAccess.RunSQLReturnValInt(sql);
		} else {
			Paras ps = new Paras();
			ps.SQL = "SELECT count( distinct a.WorkID ) as Num FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp="
					+ SystemConfig.getAppCenterDBVarStr() + "FK_Emp AND B.IsEnable=1 AND (B.IsPass=1 OR B.IsPass<0) ";
			ps.Add("FK_Emp", WebUser.getNo());
			return BP.DA.DBAccess.RunSQLReturnValInt(ps);
		}
	}

	/**
	 * 获取已经完成流程数量
	 * 
	 * @return
	 */
	public static int getTodolist_Complete() {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT count(WorkID) Num FROM WF_GenerWorkFlow WHERE Emps LIKE '%@" + WebUser.getNo()
					+ "@%' AND FID=0 AND WFState=" + WFState.Complete.getValue();
			return BP.DA.DBAccess.RunSQLReturnValInt(ps, 0);
		} else {
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT count(*) Num FROM V_FlowData WHERE FlowEmps LIKE '%@" + WebUser.getNo()
					+ "%' AND FID=0 AND WFState=" + WFState.Complete.getValue();
			return BP.DA.DBAccess.RunSQLReturnValInt(ps, 0);
		}
	}

	/**
	 * 共享任务个数
	 */
	public static int getTodolist_Sharing() {

		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String wfSql = "  (WFState=" + WFState.Askfor.getValue() + " OR WFState=" + WFState.Runing.getValue()
				+ " OR WFState=" + WFState.Shift.getValue() + " OR WFState=" + WFState.ReturnSta.getValue()
				+ ") AND TaskSta=" + TaskSta.Sharing.getValue();
		String sql;
		String realSql = null;
		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			ps.SQL = "SELECT COUNT(WorkID) FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp ";
			ps.Add("FK_Emp", WebUser.getNo());
			return BP.DA.DBAccess.RunSQLReturnValInt(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			ps.SQL = "SELECT COUNT(WorkID) FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
					+ "FK_Emp AND TaskSta=0";
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case SpecFlows:
			ps.SQL = "SELECT COUNT(WorkID) FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
					+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows() + " ";
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnValInt(ps);
	}

	/**
	 * 处理延期的任务.根据节点属性的设置
	 * 
	 * @return 返回处理的消息
	 */
	public static String DTS_DealDeferredWork() {
		// string sql = "SELECT * FROM WF_EmpWorks WHERE FK_Node IN (SELECT
		// NodeID FROM WF_Node WHERE OutTimeDeal >0 ) AND SDT <='" +
		// DataType.CurrentData + "' ORDER BY FK_Emp";
		// 改成小于号SDT <'" + DataType.CurrentData
		String sql = "SELECT * FROM WF_EmpWorks WHERE FK_Node IN (SELECT NodeID FROM WF_Node WHERE OutTimeDeal >0 ) AND SDT <'"
				+ DataType.getCurrentData() + "' ORDER BY FK_Emp";
		// string sql = "SELECT * FROM WF_EmpWorks WHERE FK_Node IN (SELECT
		// NodeID FROM WF_Node WHERE OutTimeDeal >0 ) AND SDT <='2013-12-30'
		// ORDER BY FK_Emp";
		DataTable dt = DBAccess.RunSQLReturnTable(sql);
		String msg = "";
		String dealWorkIDs = "";
		for (DataRow dr : dt.Rows) {
			String FK_Emp = dr.getValue("FK_Emp").toString();
			String fk_flow = dr.getValue("FK_Flow").toString();
			int fk_node = Integer.parseInt(dr.getValue("FK_Node").toString());
			long workid = Long.parseLong(dr.getValue("WorkID").toString());
			long fid = Long.parseLong(dr.getValue("FID").toString());

			// 方式两个人同时处理一件工作, 一个人处理后，另外一个人还可以处理的情况.
			if (dealWorkIDs.contains("," + workid + ",")) {
				continue;
			}
			dealWorkIDs += "," + workid + ",";

			if (!FK_Emp.equals(WebUser.getNo())) {
				Emp emp = new Emp(FK_Emp);
				WebUser.SignInOfGener(emp);
			}

			BP.WF.Template.NodeSheet nd = new BP.WF.Template.NodeSheet();
			nd.setNodeID(fk_node);
			nd.Retrieve();

			// 首先判断是否有启动的表达式, 它是是否自动执行的总阀门。
			if (StringHelper.isNullOrEmpty(nd.getDoOutTimeCond()) == false) {
				Node nodeN = new Node(nd.getNodeID());
				Work wk = nodeN.getHisWork();
				wk.setOID(workid);
				wk.Retrieve();
				Object tempVar = nd.getDoOutTimeCond();
				String exp = (String) ((tempVar instanceof String) ? tempVar : null);
				if (Glo.ExeExp(exp, wk) == false) {
					continue; // 不能通过条件的设置.
				}
			}

			switch (nd.getHisOutTimeDeal()) {
			case None:
				break;
			case AutoTurntoNextStep: // 自动转到下一步骤.
				if (StringHelper.isNullOrEmpty(nd.getDoOutTime())) {
					// 如果是空的,没有特定的点允许，就让其它向下执行。
					msg += BP.WF.Dev2Interface.Node_SendWork(fk_flow, workid).ToMsgOfText();
				} else {
					int nextNode = Dev2Interface.Node_GetNextStepNode(fk_flow, workid);
					if (nd.getDoOutTime().contains((new Integer(nextNode)).toString())) // 如果包含了当前点的ID,就让它执行下去.
					{
						msg += BP.WF.Dev2Interface.Node_SendWork(fk_flow, workid).ToMsgOfText();
					}
				}
				break;
			case AutoJumpToSpecNode: // 自动的跳转下一个节点.
				if (StringHelper.isNullOrEmpty(nd.getDoOutTime())) {
					throw new RuntimeException("@设置错误,没有设置要跳转的下一步节点.");
				}
				int nextNodeID = Integer.parseInt(nd.getDoOutTime());
				msg += BP.WF.Dev2Interface.Node_SendWork(fk_flow, workid, null, null, nextNodeID, null).ToMsgOfText();
				break;
			case AutoShiftToSpecUser: // 移交给指定的人员.
				msg += BP.WF.Dev2Interface.Node_Shift(fk_flow, fk_node, workid, fid, nd.getDoOutTime(),
						"来自ccflow的自动消息:(" + WebUser.getName() + ")工作未按时处理(" + nd.getName() + "),现在移交给您。");
				break;
			case SendMsgToSpecUser: // 向指定的人员发消息.
				BP.WF.Dev2Interface.Port_SendMsg(nd.getDoOutTime(),
						"来自ccflow的自动消息:(" + WebUser.getName() + ")工作未按时处理(" + nd.getName() + ")", "感谢您选择ccflow.",
						"SpecEmp" + workid);
				break;
			case DeleteFlow: // 删除流程.
				msg += BP.WF.Dev2Interface.Flow_DoDeleteFlowByReal(fk_flow, workid, true);
				break;
			case RunSQL:
				msg += BP.DA.DBAccess.RunSQL(nd.getDoOutTime());
				break;
			default:
				throw new RuntimeException("@错误没有判断的超时处理方式." + nd.getHisOutTimeDeal());
			}
		}
		Emp emp1 = new Emp(SystemConfig.getAppSettings().get("Admin").toString());
		WebUser.SignInOfGener(emp1);
		return msg;
	}

	/**
	 * 自动执行开始节点数据 说明:根据自动执行的流程设置，自动启动发起的流程。
	 * 比如：您根据ccflow的自动启动流程的设置，自动启动该流程，不使用ccflow的提供的服务程序，您需要按如下步骤去做。 1, 写一个自动调度的程序。
	 * 2，根据自己的时间需要调用这个接口。
	 * 
	 * @param fl
	 *            流程实体,您可以 new Flow(flowNo); 来传入.
	 */
	public static void DTS_AutoStarterFlow(Flow fl) {
		MapExt me = new MapExt();
		int i = me.Retrieve(MapExtAttr.FK_MapData, "ND" + Integer.parseInt(fl.getNo()) + "01", MapExtAttr.ExtType,
				"PageLoadFull");
		if (i == 0) {
			BP.DA.Log.DefaultLogWriteLineError("没有为流程(" + fl.getName() + ")的开始节点设置发起数据,请参考说明书解决.");
			return;
		}

		// 获取从表数据.
		DataSet ds = new DataSet();
		String[] dtlSQLs = me.getTag1().split("[*]", -1);
		for (String sql : dtlSQLs) {
			if (StringHelper.isNullOrEmpty(sql)) {
				continue;
			}

			String[] tempStrs = sql.split("[=]", -1);
			String dtlName = tempStrs[0];
			DataTable dtlTable = BP.DA.DBAccess.RunSQLReturnTable(sql.replace(dtlName + "=", ""));
			dtlTable.TableName = dtlName;
			ds.Tables.add(dtlTable);
		}

		/// #region 检查数据源是否正确.
		String errMsg = "";
		// 获取主表数据.
		DataTable dtMain = BP.DA.DBAccess.RunSQLReturnTable(me.getTag());
		if (dtMain.Columns.contains("Starter") == false) {
			errMsg += "@配值的主表中没有Starter列.";
		}

		if (dtMain.Columns.contains("MainPK") == false) {
			errMsg += "@配值的主表中没有MainPK列.";
		}

		if (errMsg.length() > 2) {
			BP.DA.Log.DefaultLogWriteLineError("流程(" + fl.getName() + ")的开始节点设置发起数据,不完整." + errMsg);
			return;
		}

		String nodeTable = "ND" + Integer.parseInt(fl.getNo()) + "01";
		MapData md = new MapData(nodeTable);

		for (DataRow dr : dtMain.Rows) {
			String mainPK = dr.getValue("MainPK").toString();
			String sql = "SELECT OID FROM " + md.getPTable() + " WHERE MainPK='" + mainPK + "'";
			if (DBAccess.RunSQLReturnTable(sql).Rows.size() != 0) {
				continue; // 说明已经调度过了
			}

			String starter = dr.getValue("Starter").toString();
			if (!starter.equals(WebUser.getNo())) {
				WebUser.Exit();
				BP.Port.Emp emp = new BP.Port.Emp();
				emp.setNo(starter);
				if (emp.RetrieveFromDBSources() == 0) {
					BP.DA.Log.DefaultLogWriteLineInfo(
							"@数据驱动方式发起流程(" + fl.getName() + ")设置的发起人员:" + emp.getNo() + "不存在。");
					continue;
				}

				WebUser.SignInOfGener(emp);
			}

			/// #region 给值.
			Work wk = fl.NewWork();
			for (DataColumn dc : dtMain.Columns) {
				wk.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName).toString());
			}

			if (ds.Tables.size() != 0) {
				String refPK = dr.getValue("MainPK").toString();
				MapDtls dtls = wk.getHisNode().getMapData().getMapDtls(); // new
																			// MapDtls(nodeTable);
				for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
					for (DataTable dt : ds.Tables) {
						if (dt.TableName != dtl.getNo()) {
							continue;
						}

						// 删除原来的数据。
						GEDtl dtlEn = dtl.getHisGEDtl();
						dtlEn.Delete(GEDtlAttr.RefPK, (new Long(wk.getOID())).toString());

						// 执行数据插入。
						for (DataRow drDtl : dt.Rows) {
							if (!drDtl.getValue("RefMainPK").toString().equals(refPK)) {
								continue;
							}

							dtlEn = dtl.getHisGEDtl();

							for (DataColumn dc : dt.Columns) {
								dtlEn.SetValByKey(dc.ColumnName, drDtl.getValue(dc.ColumnName).toString());
							}

							dtlEn.setRefPK((new Long(wk.getOID())).toString());
							dtlEn.Insert();
						}
					}
				}
			}
			/// #endregion 给值.

			// 处理发送信息.
			Node nd = fl.getHisStartNode();
			try {
				WorkNode wn = new WorkNode(wk, nd);
				String msg = wn.NodeSend().ToMsgOfHtml();
				// BP.DA.Log.DefaultLogWriteLineInfo(msg);
			} catch (RuntimeException ex) {
				BP.DA.Log.DefaultLogWriteLineWarning(ex.getMessage());
			}
		}
		/// #endregion 处理流程发起.

	}
	/// #endregion

	/// #region 数据集合接口(如果您想获取一个结果集合的接口，都是以DB_开头的.)
	/**
	 * 获取能发起流程的人员
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @return
	 */
	public static String GetFlowStarters(String fk_flow) {
		Node nd = new Node(Integer.parseInt(fk_flow + "01"));
		String sql = "";
		switch (nd.getHisDeliveryWay()) {
		case ByBindEmp: // 按人员
			sql = "SELECT * FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM WF_NodeEmp WHERE FK_Node=" + nd.getNodeID()
					+ ")";
			break;
		case ByDept: // 按部门
			sql = "SELECT * FROM Port_Emp WHERE FK_Dept IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
					+ nd.getNodeID() + ")";
			break;
		case ByStation: // 按岗位
			sql = "SELECT * FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation()
					+ " WHERE FK_Station IN ( SELECT FK_Station from WF_nodeStation where FK_Node=" + nd.getNodeID()
					+ ")) ";
			break;
		default:
			throw new RuntimeException("@开始节点的人员访问规则错误,不允许在开始节点设置此访问类型:" + nd.getHisDeliveryWay());

		}
		return sql;
	}

	public static String GetFlowStarters(String fk_flow, String fk_dept) {
		Node nd = new Node(Integer.parseInt(fk_flow + "01"));
		String sql = "";
		switch (nd.getHisDeliveryWay()) {
		case ByBindEmp: // 按人员
			sql = "SELECT * FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM WF_NodeEmp WHERE FK_Node=" + nd.getNodeID()
					+ ") and fk_dept='" + fk_dept + "'";
			break;
		case ByDept: // 按部门
			sql = "SELECT * FROM Port_Emp WHERE FK_Dept IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
					+ nd.getNodeID() + ") and fk_dept='" + fk_dept + "' ";
			break;
		case ByStation: // 按岗位
			sql = "SELECT * FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation()
					+ " WHERE FK_Station IN ( SELECT FK_Station from WF_nodeStation where FK_Node=" + nd.getNodeID()
					+ ")) and fk_dept='" + fk_dept + "' ";
			break;
		default:
			throw new RuntimeException("@开始节点的人员访问规则错误,不允许在开始节点设置此访问类型:" + nd.getHisDeliveryWay());

		}
		return sql;
	}

	/// #region 与子流程相关.
	/**
	 * 获取流程事例的运行轨迹数据. 说明：使用这些数据可以生成流程的操作日志.
	 * 
	 * @param workid
	 *            工作ID
	 * @return GenerWorkFlows
	 */
	public static GenerWorkFlows DB_SubFlows(long workid) {
		GenerWorkFlows gwf = new GenerWorkFlows();
		gwf.Retrieve(GenerWorkFlowAttr.PWorkID, workid);
		return gwf;
	}

	/// #region 获取流程事例的轨迹图
	/**
	 * 获取流程事例的运行轨迹数据. 说明：使用这些数据可以生成流程的操作日志.
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 * @return 从临时表与轨迹表获取流程轨迹数据.
	 */
	public static DataSet DB_GenerTrack(String fk_flow, long workid, long fid) {
		// 定义变量，把数据都放入这个track里面.
		DataSet ds = new DataSet();

		/// #region 获取track数据.
		String sqlOfWhere2 = "";
		String sqlOfWhere1 = "";
		String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		if (fid == 0) {
			sqlOfWhere1 = " WHERE (FID=" + dbStr + "WorkID11 OR WorkID=" + dbStr + "WorkID12 )  ";
			ps.Add("WorkID11", workid);
			ps.Add("WorkID12", workid);
		} else {
			sqlOfWhere1 = " WHERE (FID=" + dbStr + "FID11 OR WorkID=" + dbStr + "FID12 ) ";
			ps.Add("FID11", fid);
			ps.Add("FID12", fid);
		}

		String sql = "";
		sql = "SELECT MyPK,ActionType,ActionTypeText,FID,WorkID,NDFrom,NDFromT,NDTo,NDToT,EmpFrom,EmpFromT,EmpTo,EmpToT,RDT,WorkTimeSpan,Msg,NodeData,Exer,Tag FROM ND"
				+ Integer.parseInt(fk_flow) + "Track " + sqlOfWhere1 + " ORDER BY RDT asc";
		ps.SQL = sql;
		DataTable dt = null;
		try {
			dt = DBAccess.RunSQLReturnTable(ps);
		} catch (java.lang.Exception e) {
			// 处理track表.
			Track.CreateOrRepairTrackTable(fk_flow);
			dt = DBAccess.RunSQLReturnTable(ps);
		}

		// 把track加入里面去.
		dt.TableName = "Track";
		ds.Tables.add(dt);
		/// #endregion 获取track数据.

		// 把抄送信息加入.
		CCLists ens = new CCLists(fk_flow, workid, fid);
		ds.Tables.add(ens.ToDataTableField("WF_CCList"));

		// 把未来节点选择人信息写入里面.
		long wfid = 0;
		if (fid != 0) {
			wfid = fid;
		}
		SelectAccpers accepts = new SelectAccpers(wfid);
		ds.Tables.add(ens.ToDataTableField("WF_SelectAccper"));

		// 把节点信息写入里面.
		sql = "SELECT * FROM WF_Node WHERE FK_Flow='" + fk_flow + "'";
		DataTable dtNode = BP.DA.DBAccess.RunSQLReturnTable(sql);
		dtNode.TableName = "WF_Node";
		ds.Tables.add(dtNode);

		// 把方向写入里面.
		sql = "SELECT * FROM WF_Direction WHERE FK_Flow='" + fk_flow + "'";
		DataTable dtDir = BP.DA.DBAccess.RunSQLReturnTable(sql);
		dtDir.TableName = "WF_Direction";
		ds.Tables.add(dtDir);

		return ds;
	}

	/**
	 * 获取一个流程
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param userNo
	 *            操作员编号
	 * @return
	 */
	public static DataTable DB_GenerNDxxxRpt(String fk_flow, String userNo) {
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		String dbstr = SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "SELECT * FROM ND" + Integer.parseInt(fk_flow) + "Rpt WHERE FlowStarter=" + dbstr
				+ "FlowStarter  ORDER BY RDT";
		ps.Add(GERptAttr.FlowStarter, userNo);
		return DBAccess.RunSQLReturnTable(ps);

	}

	/// #region 获取能够发送或者抄送人员的列表.
	/**
	 * 获取当前节点可抄送人员列表
	 * 
	 * @param fk_node
	 * @param workid
	 * @return
	 */
	public static DataTable DB_CanCCEmps(int fk_node, long workid) {
		DataTable table = new DataTable();
		String ccers = null;

		WorkNode node = new WorkNode(workid, fk_node);

		if (node.getHisNode().getHisCCRole() == CCRole.AutoCC
				|| node.getHisNode().getHisCCRole() == CCRole.HandAndAuto) {
			try {
				// 如果是自动抄送
				CC cc = node.getHisNode().getHisCC();

				table = cc.GenerCCers(node.rptGe);

			} catch (RuntimeException ex) {
				throw new RuntimeException("@处理操送时出现错误:" + ex.getMessage());
			}
		}

		/// #region 执行抄送 BySysCCEmps
		if (node.getHisNode().getHisCCRole() == CCRole.BySysCCEmps) {
			CC cc = node.getHisNode().getHisCC();

			// 取出抄送人列表
			String temps = node.rptGe.GetValStrByKey("SysCCEmps");
			if (!StringHelper.isNullOrEmpty(temps)) {
				String[] cclist = temps.split("[|]", -1);

				if (!table.Columns.contains("No")) {
					table.Columns.Add("No");
				}
				if (!table.Columns.contains("Name")) {
					table.Columns.Add("Name");
				}
				for (String item : cclist) {
					String[] tmp = item.split("[,]", -1);

					DataRow row = table.NewRow();

					// row.getValue("No") = tmp[0];
					// row["Name"] = tmp[1];
					// table.Rows.Add(row);
					row.put("No", tmp[0]);
					row.put("Name", tmp[1]);
					table.Rows.add(row);
				}
			}
		}

		return table;
	}

	/**
	 * 获取可以执行指定节点人的列表
	 * 
	 * @param fk_node
	 *            节点编号
	 * @param workid
	 *            工作ID
	 * @return
	 */
	public static DataSet DB_CanExecSpecNodeEmps(int fk_node, long workid) {
		DataSet ds = new DataSet();
		Paras ps = new Paras();
		ps.SQL = "SELECT No,Name,FK_Dept FROM Port_Emp ";
		DataTable dtEmp = DBAccess.RunSQLReturnTable(ps);
		dtEmp.TableName = "Emps";
		ds.Tables.add(dtEmp);

		ps = new Paras();
		ps.SQL = "SELECT No,Name FROM Port_Dept ";
		DataTable dtDept = DBAccess.RunSQLReturnTable(ps);
		dtDept.TableName = "Depts";
		ds.Tables.add(dtDept);

		return ds;
	}

	/**
	 * 获得可以抄送的人员列表
	 * 
	 * @param fk_node
	 *            节点编号
	 * @param workid
	 *            工作ID
	 * @return
	 */
	public static DataSet DB_CanCCSpecNodeEmps(int fk_node, long workid) {
		DataSet ds = new DataSet();
		Paras ps = new Paras();
		ps.SQL = "SELECT No,Name,FK_Dept FROM Port_Emp ";
		DataTable dtEmp = DBAccess.RunSQLReturnTable(ps);
		dtEmp.TableName = "Emps";
		ds.Tables.add(dtEmp);

		ps = new Paras();
		ps.SQL = "SELECT No,Name FROM Port_Dept ";
		DataTable dtDept = DBAccess.RunSQLReturnTable(ps);
		dtDept.TableName = "Depts";
		ds.Tables.add(dtDept);

		return ds;
	}

	/// #region 获取操送列表
	/**
	 * 获取指定人员的抄送列表 说明:可以根据这个列表生成指定用户的抄送数据.
	 * 
	 * @param FK_Emp
	 *            人员编号
	 * @return 返回该人员的所有抄送列表,结构同表WF_CCList.
	 */
	public static DataTable DB_CCList(String FK_Emp) {
		Paras ps = new Paras();
		ps.SQL = "SELECT * FROM WF_CCList WHERE CCTo=" + SystemConfig.getAppCenterDBVarStr() + "FK_Emp";
		ps.Add("FK_Emp", FK_Emp);
		return DBAccess.RunSQLReturnTable(ps);
	}

	public static DataTable DB_CCList(String FK_Emp, CCSta sta) {
		Paras ps = new Paras();
		ps.SQL = "SELECT * FROM WF_CCList WHERE CCTo=" + SystemConfig.getAppCenterDBVarStr() + "FK_Emp AND Sta="
				+ SystemConfig.getAppCenterDBVarStr() + "Sta";
		ps.Add("FK_Emp", FK_Emp);
		ps.Add("Sta", sta.getValue());
		return DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 获取指定人员的抄送列表(未读)
	 * 
	 * @param FK_Emp
	 *            人员编号
	 * @return 返回该人员的未读的抄送列表
	 */
	public static DataTable DB_CCList_UnRead(String FK_Emp) {
		return DB_CCList(FK_Emp, CCSta.UnRead);
	}

	/**
	 * 获取指定人员的抄送列表(已读)
	 * 
	 * @param FK_Emp
	 *            人员编号
	 * @return 返回该人员的已读的抄送列表
	 */
	public static DataTable DB_CCList_Read(String FK_Emp) {
		return DB_CCList(FK_Emp, CCSta.Read);
	}

	/**
	 * 获取指定人员的抄送列表(已删除)
	 * 
	 * @param FK_Emp
	 *            人员编号
	 * @return 返回该人员的已删除的抄送列表
	 */
	public static DataTable DB_CCList_Delete(String FK_Emp) {
		return DB_CCList(FK_Emp, CCSta.Del);
	}

	/**
	 * 获取指定人员的抄送列表(已回复)
	 * 
	 * @param FK_Emp
	 *            人员编号
	 * @return 返回该人员的已删除的抄送列表
	 */
	public static DataTable DB_CCList_CheckOver(String FK_Emp) {
		return DB_CCList(FK_Emp, CCSta.CheckOver);
	}

	/// #region 获取当前操作员可以发起的流程集合
	/**
	 * 获取指定人员能够发起流程的集合. 说明:利用此接口可以生成用户的发起的流程列表.
	 * 
	 * @param userNo
	 *            操作员编号
	 * @return BP.WF.Flows 可发起的流程对象集合,如何使用该方法形成发起工作列表,请参考:\WF\UC\Start.ascx
	 */
	public static Flows DB_GenerCanStartFlowsOfEntities(String userNo) {
		// 按岗位计算.
		String sql = "SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND ( WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeStation WHERE FK_Station IN (SELECT FK_Station FROM "
				+ BP.WF.Glo.getEmpStation() + " WHERE FK_Emp='" + userNo + "')) ";
		sql += " UNION  "; // 按指定的人员计算.
		sql += "  SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND ( WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeEmp WHERE FK_Emp='"
				+ userNo + "' ) ";
		sql += " UNION  "; // 按岗位计算.
		sql += " SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND ( WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeDept WHERE FK_Dept IN(SELECT FK_Dept FROM Port_Emp WHERE No='"
				+ userNo + "' UNION SELECT FK_DEPT FROM Port_EmpDept WHERE FK_Emp='" + userNo + "') ) ";
		// sql += " UNION "; //按岗位与部门的交际计算.
		// sql += " SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND (
		// WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM
		// WF_NodeDept WHERE FK_Dept IN(SELECT FK_Dept FROM Port_Emp WHERE No='"
		// + userNo + "' UNION SELECT FK_DEPT FROM Port_EmpDept WHERE FK_Emp='"
		// + userNo + "') ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeStation
		// WHERE FK_Station IN( SELECT FK_Station FROM Port_EmpStation WHERE
		// FK_Emp='" + userNo + "') ) ";
		sql = "SELECT FK_Emp FROM V_FlowStarter WHERE FK_Emp='" + userNo + "'";
		Flows fls = new Flows();
		BP.En.QueryObject qo = new BP.En.QueryObject(fls);
		qo.AddWhereInSQL("No", sql);
		qo.addAnd();
		qo.AddWhere(FlowAttr.IsCanStart, true);
		if (WebUser.getIsAuthorize()) {
			// 如果是授权状态
			qo.addAnd();
			WFEmp wfEmp = new WFEmp(userNo);
			qo.AddWhereIn("No", wfEmp.getAuthorFlows());
		}
		qo.addOrderBy(FlowAttr.FK_FlowSort, FlowAttr.Idx);
		qo.DoQuery();
		return fls;
	}

	/**
	 * 获取指定人员能够发起流程的集合 说明:利用此接口可以生成用户的发起的流程列表.
	 * 
	 * @param userNo
	 *            操作员编号
	 * @return Datatable类型的数据集合,数据结构与表WF_Flow大致相同.
	 *         如何使用该方法形成发起工作列表,请参考:\WF\UC\Start.ascx
	 */
	public static DataTable DB_GenerCanStartFlowsOfDataTable(String userNo) {
		String sql = "";
		// 按岗位计算.
		sql += "SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND ( WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeStation WHERE FK_Station IN (SELECT FK_Station FROM "
				+ BP.WF.Glo.getEmpStation() + " WHERE FK_Emp='" + userNo + "')) ";
		sql += " UNION  "; // 按指定的人员计算.
		sql += "SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND ( WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeEmp WHERE FK_Emp='"
				+ userNo + "' ) ";
		sql += " UNION  "; // 按部门计算.
		sql += "SELECT FK_Flow FROM WF_Node WHERE NodePosType=0 AND ( WhoExeIt=0 OR WhoExeIt=2 ) AND NodeID IN ( SELECT FK_Node FROM WF_NodeDept WHERE FK_Dept IN(SELECT FK_Dept FROM Port_Emp WHERE No='"
				+ userNo + "' UNION SELECT FK_DEPT FROM " + Glo.getEmpDept() + " WHERE FK_Emp='" + userNo + "') ) ";

		// 采用新算法.
		if (BP.WF.Glo.getOSModel() == OSModel.WorkFlow) {
			sql = "SELECT FK_Flow FROM V_FlowStarter WHERE FK_Emp='" + userNo + "'";
		} else {
			sql = "SELECT FK_Flow FROM V_FlowStarterBPM WHERE FK_Emp='" + userNo + "'";
		}

		Flows fls = new Flows();
		BP.En.QueryObject qo = new BP.En.QueryObject(fls);
		qo.AddWhereInSQL("No", sql);
		qo.addAnd();
		qo.AddWhere(FlowAttr.IsCanStart, true);
		if (WebUser.getIsAuthorize()) {
			// 如果是授权状态
			qo.addAnd();
			WFEmp wfEmp = new WFEmp(userNo);
			qo.AddWhereIn("No", wfEmp.getAuthorFlows());
		}
		qo.addOrderBy("FK_FlowSort", FlowAttr.Idx);
		return qo.DoQueryToTable();
	}

	public static DataTable DB_GenerCanStartFlowsTree(String userNo) {
		// 发起.
		DataTable table = DB_GenerCanStartFlowsOfDataTable(userNo);
		table.Columns.Add("ParentNo");
		table.Columns.Add("ICON");
		String flowSort = String.format("select No,Name,ParentNo from WF_FlowSort");

		DataTable sortTable = DBAccess.RunSQLReturnTable(flowSort);
		for (DataRow row : sortTable.Rows) {
			DataRow newRow = table.NewRow();
			newRow.setValue("No", row.getValue("No"));
			newRow.setValue("Name", row.getValue("Name"));

			if (!"0".equals(row.getValue("ParentNo") + "")) {
				newRow.setValue("_parentId", row.getValue("ParentNo"));
			}
			newRow.setValue("ICON", row.getValue("icon-tree_folder"));
			// newRow["No"] = row["No"];
			// newRow["Name"] = row["Name"];
			// newRow["ParentNo"] = row["ParentNo"];
			// newRow["ICON"] = "icon-tree_folder";
			table.Rows.add(newRow);
		}

		for (DataRow row : table.Rows) {
			if (StringHelper.isNullOrEmpty(row.getValue("_parentId") + "")
					&& !"0".equals(row.getValue("_parentId") + "")) {
				row.setValue("_parentId", row.getValue("FK_FlowSort"));
			}
		}
		return table;
	}

	/**
	 * 获取(同表单)合流点上的子线程 说明:如果您要想在合流点看到所有的子线程运行的状态.
	 * 
	 * @param nodeIDOfHL
	 *            合流点ID
	 * @param workid
	 *            工作ID
	 * @return 与表WF_GenerWorkerList结构类同的datatable.
	 */
	public static DataTable DB_GenerHLSubFlowDtl_TB(int nodeIDOfHL, long workid) {
		Node nd = new Node(nodeIDOfHL);
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		wk.Retrieve();

		GenerWorkerLists wls = new GenerWorkerLists();
		QueryObject qo = new QueryObject(wls);
		qo.AddWhere(GenerWorkerListAttr.FID, wk.getOID());
		qo.addAnd();
		qo.AddWhere(GenerWorkerListAttr.IsEnable, 1);
		qo.addAnd();
		qo.AddWhere(GenerWorkerListAttr.FK_Node, ((Node) (nd.getFromNodes().get(0))).GetValByKey(NodeAttr.NodeID));

		DataTable dt = qo.DoQueryToTable();
		if (dt.Rows.size() == 1) {
			qo.clear();
			qo.AddWhere(GenerWorkerListAttr.FID, wk.getOID());
			qo.addAnd();
			qo.AddWhere(GenerWorkerListAttr.IsEnable, 1);
			return qo.DoQueryToTable();
		}
		return dt;
	}

	/**
	 * 获取(异表单)合流点上的子线程
	 * 
	 * @param nodeIDOfHL
	 *            合流点ID
	 * @param workid
	 *            工作ID
	 * @return 与表WF_GenerWorkerList结构类同的datatable.
	 */
	public static DataTable DB_GenerHLSubFlowDtl_YB(int nodeIDOfHL, long workid) {
		Node nd = new Node(nodeIDOfHL);
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		wk.Retrieve();

		GenerWorkerLists wls = new GenerWorkerLists();
		QueryObject qo = new QueryObject(wls);
		qo.AddWhere(GenerWorkerListAttr.FID, wk.getOID());
		qo.addAnd();
		qo.AddWhere(GenerWorkerListAttr.IsEnable, 1);
		qo.addAnd();
		qo.AddWhere(GenerWorkerListAttr.IsPass, 0);
		return qo.DoQueryToTable();
	}

	/// #region 流程草稿
	/**
	 * 获取当前操作员的指定流程的流程草稿数据
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @return 返回草稿数据集合,列信息. OID=工作ID,Title=标题,RDT=记录日期,FK_Flow=流程编号,FID=流程ID,
	 *         FK_Node=节点ID
	 */
	public static DataTable DB_GenerDraftDataTable() {
		// 获取数据.
		String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		BP.DA.Paras ps = new BP.DA.Paras();
		ps.SQL = "SELECT WorkID,Title,FK_Flow,FlowName,RDT FROM WF_GenerWorkFlow A WHERE WFState=1 AND Starter=" + dbStr
				+ "Starter ORDER BY RDT";
		ps.Add(GenerWorkFlowAttr.Starter, WebUser.getNo());
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/// #region 获取当前操作员的共享工作
	/**
	 * 获取当前人员待处理的工作
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @return 共享工作列表
	 */
	public static DataTable DB_GenerEmpWorksOfDataTable(String userNo, String fk_flow) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String sql;
		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE FK_Emp=" + dbstr + "FK_Emp AND TaskSta=0 AND WFState!="
							+ WFState.Batch.getValue() + " ORDER BY FK_Flow,ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE FK_Emp=" + dbstr + "FK_Emp  AND WFState!="
							+ WFState.Batch.getValue() + " ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("FK_Emp", userNo);
			} else {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE FK_Emp=" + dbstr + "FK_Emp AND TaskSta=0 AND FK_Flow="
							+ dbstr + "FK_Flow  AND WFState!=" + WFState.Batch.getValue() + " ORDER BY  ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE FK_Emp=" + dbstr + "FK_Emp AND FK_Flow=" + dbstr
							+ "FK_Flow  AND WFState!=" + WFState.Batch.getValue() + " ORDER BY  ADT DESC ";
				}

				ps.Add("FK_Flow", fk_flow);
				ps.Add("FK_Emp", userNo);
			}
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE  FK_Emp=" + dbstr
							+ "FK_Emp  AND TaskSta=0 ORDER BY FK_Flow,ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE  FK_Emp=" + dbstr + "FK_Emp  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("FK_Emp", userNo);
			} else {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE  FK_Emp=" + dbstr + "FK_Emp AND FK_Flow" + dbstr
							+ "FK_Flow AND TaskSta=0 ORDER BY FK_Flow,ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE  FK_Emp=" + dbstr + "FK_Emp AND FK_Flow" + dbstr
							+ "FK_Flow ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("FK_Emp", userNo);
				ps.Add("FK_Flow", fk_flow);
			}
			break;
		case SpecFlows:
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					sql = "SELECT * FROM WF_EmpWorks WHERE FK_Emp=" + dbstr + "FK_Emp AND  FK_Flow IN "
							+ emp.getAuthorFlows() + " AND TaskSta=0 ORDER BY FK_Flow,ADT DESC ";
				} else {
					sql = "SELECT * FROM WF_EmpWorks WHERE FK_Emp=" + dbstr + "FK_Emp AND  FK_Flow IN "
							+ emp.getAuthorFlows() + "  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("FK_Emp", userNo);
			} else {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					sql = "SELECT * FROM WF_EmpWorks WHERE  FK_Emp=" + dbstr + "FK_Emp  AND FK_Flow" + dbstr
							+ "FK_Flow AND FK_Flow IN " + emp.getAuthorFlows()
							+ " AND TaskSta=0  ORDER BY FK_Flow,ADT DESC ";
				} else {
					sql = "SELECT * FROM WF_EmpWorks WHERE  FK_Emp=" + dbstr + "FK_Emp  AND FK_Flow" + dbstr
							+ "FK_Flow AND FK_Flow IN " + emp.getAuthorFlows() + "  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("FK_Emp", userNo);
				ps.Add("FK_Flow", fk_flow);
			}
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 根据状态获取当前操作员的共享工作
	 * 
	 * @param wfState
	 *            流程状态
	 * @param fk_flow
	 *            流程编号
	 * @return 表结构与视图WF_EmpWorks一致
	 */
	public static DataTable DB_GenerEmpWorksOfDataTable(String userNo, WFState wfState, String fk_flow) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String sql;
		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND TaskSta=0   ORDER BY FK_Flow,ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("WFState", wfState.getValue());
				ps.Add("FK_Emp", userNo);
			} else {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND FK_Flow=" + dbstr + "FK_Flow AND TaskSta=0  ORDER BY  ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND FK_Flow=" + dbstr + "FK_Flow ORDER BY  ADT DESC ";
				}

				ps.Add("WFState", wfState.getValue());
				ps.Add("FK_Flow", fk_flow);
				ps.Add("FK_Emp", userNo);
			}
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp  AND TaskSta=0  ORDER BY FK_Flow,ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("WFState", wfState.getValue());
				ps.Add("FK_Emp", WebUser.getNo());
			} else {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND FK_Flow" + dbstr + "FK_Flow AND TaskSta=0  ORDER BY FK_Flow,ADT DESC ";
				} else {
					ps.SQL = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND FK_Flow" + dbstr + "FK_Flow ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("WFState", wfState.getValue());
				ps.Add("FK_Emp", WebUser.getNo());
				ps.Add("FK_Flow", fk_flow);
			}
			break;
		case SpecFlows:
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					sql = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows()
							+ " AND TaskSta=0   ORDER BY FK_Flow,ADT DESC ";
				} else {
					sql = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows() + "  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("WFState", wfState.getValue());
				ps.Add("FK_Emp", WebUser.getNo());
			} else {
				if (BP.WF.Glo.getIsEnableTaskPool() == true) {
					sql = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp  AND FK_Flow" + dbstr + "FK_Flow AND FK_Flow IN " + emp.getAuthorFlows()
							+ " AND TaskSta=0   ORDER BY FK_Flow,ADT DESC ";
				} else {
					sql = "SELECT * FROM WF_EmpWorks WHERE WFState=" + dbstr + "WFState AND FK_Emp=" + dbstr
							+ "FK_Emp  AND FK_Flow" + dbstr + "FK_Flow AND FK_Flow IN " + emp.getAuthorFlows()
							+ "  ORDER BY FK_Flow,ADT DESC ";
				}

				ps.Add("WFState", wfState.getValue());
				ps.Add("FK_Emp", WebUser.getNo());
				ps.Add("FK_Flow", fk_flow);
			}
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 获取当前操作人员的待办信息 数据内容请参考图:WF_EmpWorks
	 * 
	 * @return 返回从视图WF_EmpWorks查询出来的数据.
	 */
	public static DataTable DB_GenerEmpWorksOfDataTable() {
		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String wfSql = "  WFState=" + WFState.Askfor.getValue() + " OR WFState=" + WFState.Runing.getValue()
				+ "  OR WFState=" + WFState.AskForReplay.getValue() + " OR WFState=" + WFState.Shift.getValue()
				+ " OR WFState=" + WFState.ReturnSta.getValue() + " OR WFState=" + WFState.Fix.getValue();
		String sql;

		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			if (BP.WF.Glo.getIsEnableTaskPool() == true) {
				ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND TaskSta!=1  ORDER BY ADT DESC";
			} else {
				ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp ORDER BY ADT DESC";
			}

			ps.Add("FK_Emp", WebUser.getNo());
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			if (BP.WF.Glo.getIsEnableTaskPool() == true) {
				ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND TaskSta!=0  ORDER BY ADT DESC";
			} else {
				ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp ORDER BY ADT DESC";
			}
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case SpecFlows:
			if (BP.WF.Glo.getIsEnableTaskPool() == true) {
				ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows() + " AND TaskSta!=0    ORDER BY ADT DESC";
			} else {
				ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr
						+ "FK_Emp AND  FK_Flow IN " + emp.getAuthorFlows() + "   ORDER BY ADT DESC";
			}

			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 获得已完成数据统计列表
	 * 
	 * @param userNo
	 *            操作员编号
	 * @return 具有FlowNo,FlowName,Num三个列的指定人员的待办列表
	 */
	public static DataTable DB_FlowCompleteGroup(String userNo) {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT FK_Flow as No,FlowName,COUNT(*) Num FROM WF_GenerWorkFlow WHERE Emps LIKE '%@" + userNo
					+ "@%' AND FID=0 AND WFState=" + WFState.Complete.getValue() + " GROUP BY FK_Flow,FlowName";
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		} else {
			throw new RuntimeException("未实现..");
			// Paras ps = new Paras();
			// string dbstr = BP.Sys.SystemConfig.AppCenterDBVarStr;
			// ps.SQL = "SELECT * FROM V_FlowData WHERE FlowEmps LIKE '%@" +
			// userNo + "%' AND FID=0 AND WFState=" + (int)WFState.Complete;
			// return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}
	}

	/**
	 * 获取指定页面已经完成流程
	 * 
	 * @param userNo
	 *            用户编号
	 * @param flowNo
	 *            流程编号
	 * @param pageSize
	 *            每页的数量
	 * @param pageIdx
	 *            第几页
	 * @return 用户编号
	 */
	public static DataTable DB_FlowComplete(String userNo, String flowNo, int pageSize, int pageIdx) {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			GenerWorkFlows ens = new GenerWorkFlows();
			QueryObject qo = new QueryObject(ens);
			if (flowNo != null) {
				qo.AddWhere(GenerWorkFlowAttr.FK_Flow, flowNo);
				qo.addAnd();
			}
			qo.AddWhere(GenerWorkFlowAttr.FID, 0);
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.WFState, WFState.Complete.getValue());
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.Emps, " LIKE ", " '%@" + userNo + "@%'");
			// *小周鹏修改-----------------------------START*
			// qo.DoQuery(GenerWorkFlowAttr.WorkID,pageSize, pageIdx);
			qo.DoQuery(GenerWorkFlowAttr.WorkID, pageSize, pageIdx, GenerWorkFlowAttr.RDT, true);
			// *小周鹏修改-----------------------------END*
			return ens.ToDataTableField();
		} else {
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT *,FlowEndNode FK_Node FROM V_FlowData WHERE FlowEmps LIKE '%@" + userNo
					+ "%' AND   FID=0 AND WFState=" + WFState.Complete.getValue();

			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}
	}

	/**
	 * 查询指定流程中已完成的流程
	 * 
	 * @param userNo
	 * @param pageCount
	 * @param pageSize
	 * @param pageIdx
	 * @param strFlow
	 * @return
	 */
	public static DataTable DB_FlowComplete(String userNo, int pageCount, int pageSize, int pageIdx, String strFlow) {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			GenerWorkFlows ens = new GenerWorkFlows();
			QueryObject qo = new QueryObject(ens);
			qo.AddWhere(GenerWorkFlowAttr.FID, 0);
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.WFState, WFState.Complete.getValue());
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.Emps, " LIKE ", " '%@" + userNo + "@%'");
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.FK_Flow, strFlow);
			qo.DoQuery(GenerWorkFlowAttr.WorkID, pageSize, pageIdx);
			return ens.ToDataTableField();
		} else {
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT *,FlowEndNode FK_Node FROM V_FlowData WHERE FK_Flow='" + strFlow
					+ "' AND FlowEmps LIKE '%@" + userNo + "%' AND FID=0 AND WFState=" + WFState.Complete.getValue();
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}
	}

	/**
	 * 查询指定流程中已完成的公告流程
	 * 
	 * @param pageCount
	 *            页数
	 * @param pageSize
	 *            每页条数
	 * @param pageIdx
	 *            页码
	 * @param strFlow
	 *            流程编号
	 * @return
	 */
	public static DataTable DB_FlowComplete(String strFlow, int pageSize, int pageIdx) {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			GenerWorkFlows ens = new GenerWorkFlows();
			QueryObject qo = new QueryObject(ens);
			qo.AddWhere(GenerWorkFlowAttr.FID, 0);
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.WFState, WFState.Complete.getValue());
			qo.addAnd();
			qo.AddWhere(GenerWorkFlowAttr.FK_Flow, strFlow);
			qo.DoQuery(GenerWorkFlowAttr.WorkID, pageSize, pageIdx);
			return ens.ToDataTableField();
		} else {
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT *,FlowEndNode FK_Node FROM V_FlowData WHERE FK_Flow='" + strFlow
					+ "' AND FID=0 AND WFState=" + WFState.Complete.getValue();
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}
	}

	/**
	 * 获取已经完成流程
	 * 
	 * @return
	 */
	public static DataTable DB_FlowComplete() {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT 'RUNNING' AS Type, T.* FROM WF_GenerWorkFlow T WHERE T.Emps LIKE '%@" + WebUser.getNo()
					+ "@%' AND T.FID=0 AND T.WFState=" + WFState.Complete.getValue() + " ORDER BY  RDT DESC";
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		} else {
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			ps.SQL = "SELECT 'RUNNING' AS Type, T.* FROM V_FlowData T WHERE T.FlowEmps LIKE '%@" + WebUser.getNo()
					+ "@%' AND T.FID=0 AND T.WFState=" + WFState.Complete.getValue() + " ORDER BY RDT DESC";
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}
	}

	/**
	 * 获取已经完成
	 * 
	 * @return
	 */
	public static DataTable DB_FlowCompleteAndCC() {
		DataTable dt = DB_FlowComplete();
		DataTable ccDT = DB_CCList_CheckOver(WebUser.getNo());

		try {
			dt.Columns.Add("MyPK");
			dt.Columns.Add("Sta");
		} catch (RuntimeException e) {

		}

		for (DataRow row : ccDT.Rows) {
			DataRow newRow = dt.NewRow();

			for (DataColumn column : ccDT.Columns) {
				for (DataColumn dtColumn : dt.Columns) {
					if (column.ColumnName == dtColumn.ColumnName) {
						newRow.setValue(column.ColumnName, row.getValue(dtColumn.ColumnName));
					}

				}

			}
			// newRow["Type"] = "CC";
			newRow.setValue("Type", "CC");
			dt.Rows.add(newRow);
		}
		// dt.DefaultView.Sort = "RDT DESC";
		return dt;// .DefaultView.ToTable();
	}

	public static DataTable DB_FlowComplete2(String fk_flow, String title) {
		if (Glo.getIsDeleteGenerWorkFlow() == false) {
			// 如果不是删除流程注册表.
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (StringHelper.isNullOrEmpty(title)) {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM WF_GenerWorkFlow WHERE Emps LIKE '%@" + WebUser.getNo()
							+ "@%' AND FID=0 AND WFState=" + WFState.Complete.getValue()
							+ " and FK_Flow!='010' order by RDT desc";
				} else {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM WF_GenerWorkFlow WHERE Emps LIKE '%@" + WebUser.getNo()
							+ "@%' and Title Like '%" + title + "%' AND FID=0 AND WFState="
							+ WFState.Complete.getValue() + " and FK_Flow!='010' order by RDT desc";
				}
			} else {
				if (StringHelper.isNullOrEmpty(title)) {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM WF_GenerWorkFlow WHERE Emps LIKE '%@" + WebUser.getNo()
							+ "@%' AND FID=0 AND WFState=" + WFState.Complete.getValue() + " and FK_Flow='" + fk_flow
							+ "' order by RDT desc";
				} else {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM WF_GenerWorkFlow WHERE Emps LIKE '%@" + WebUser.getNo()
							+ "@%' and Title Like '%" + title + "%' AND FID=0 AND WFState="
							+ WFState.Complete.getValue() + " and FK_Flow='" + fk_flow + "' order by RDT desc";
				}
			}
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		} else {
			Paras ps = new Paras();
			String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				if (StringHelper.isNullOrEmpty(title)) {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM V_FlowData WHERE FlowEmps LIKE '%@" + WebUser.getNo()
							+ "%' AND FID=0 AND WFState=" + WFState.Complete.getValue()
							+ " and FK_Flow!='010' order by RDT desc";
				} else {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM V_FlowData WHERE FlowEmps LIKE '%@" + WebUser.getNo()
							+ "%' and Title Like '%" + title + "%' AND FID=0 AND WFState=" + WFState.Complete.getValue()
							+ " and FK_Flow!='010' order by RDT desc";
				}
			} else {
				if (StringHelper.isNullOrEmpty(title)) {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM V_FlowData WHERE FlowEmps LIKE '%@" + WebUser.getNo()
							+ "%' AND FID=0 AND WFState=" + WFState.Complete.getValue() + " and FK_Flow='" + fk_flow
							+ "' order by RDT desc";
				} else {
					ps.SQL = "SELECT 'RUNNING' AS Type,* FROM V_FlowData WHERE FlowEmps LIKE '%@" + WebUser.getNo()
							+ "%' and Title Like '%" + title + "%' AND FID=0 AND WFState=" + WFState.Complete.getValue()
							+ " and FK_Flow='" + fk_flow + "' order by RDT desc";
				}
			}
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}
	}

	public static DataTable DB_FlowCompleteAndCC2(String fk_flow, String title) {
		DataTable dt = DB_FlowComplete2(fk_flow, title);
		DataTable ccDT = DB_CCList_CheckOver(WebUser.getNo());
		try {
			dt.Columns.Add("MyPK");
			dt.Columns.Add("Sta");
		} catch (RuntimeException e) {

		}

		for (DataRow row : ccDT.Rows) {
			DataRow newRow = dt.NewRow();

			for (DataColumn column : ccDT.Columns) {
				for (DataColumn dtColumn : dt.Columns) {
					if (column.ColumnName == dtColumn.ColumnName) {
						newRow.setValue(column.ColumnName, row.getValue(dtColumn.ColumnName));
					}

				}

			}
			newRow.setValue("Type", "CC");
			dt.Rows.add(newRow);
		}
		// dt.DefaultView.Sort = "RDT DESC";
		// return dt.DefaultView.ToTable();
		return dt;
	}

	// 获取我发起的流程
	public static DataTable DB_MyFlow(String fk_flow, String title) {
		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		if (StringHelper.isNullOrEmpty(fk_flow)) {
			if (StringHelper.isNullOrEmpty(title)) {
				ps.SQL = "SELECT * FROM WF_GenerWorkFlow WHERE Starter='" + WebUser.getNo() + "' AND FK_Flow!='010' ";
			} else {
				ps.SQL = "SELECT * FROM WF_GenerWorkFlow WHERE Starter='" + WebUser.getNo()
						+ "' AND FK_Flow!='010' and Title Like '%" + title + "%' ";
			}
		} else {
			if (StringHelper.isNullOrEmpty(title)) {
				ps.SQL = "SELECT * FROM WF_GenerWorkFlow WHERE Starter='" + WebUser.getNo() + "' AND FK_Flow='"
						+ fk_flow + "' ";
			} else {
				ps.SQL = "SELECT * FROM WF_GenerWorkFlow WHERE Starter='" + WebUser.getNo() + "' AND FK_Flow='"
						+ fk_flow + "' and Title Like '%" + title + "%'";
			}
		}
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 获得任务池的工作列表
	 * 
	 * @return 任务池的工作列表
	 */
	public static DataTable DB_TaskPool() {
		if (BP.WF.Glo.getIsEnableTaskPool() == false) {
			throw new RuntimeException("@你必须在Web.config中启用IsEnableTaskPool才可以执行此操作。");
		}

		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String wfSql = "  (WFState=" + WFState.Askfor.getValue() + " OR WFState=" + WFState.Runing.getValue()
				+ " OR WFState=" + WFState.Shift.getValue() + " OR WFState=" + WFState.ReturnSta.getValue()
				+ ") AND TaskSta=" + TaskSta.Sharing.getValue();
		String sql;
		String realSql = null;
		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp ";
			ps.Add("FK_Emp", WebUser.getNo());
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp AND TaskSta=0";
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case SpecFlows:
			ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp AND  FK_Flow IN "
					+ emp.getAuthorFlows() + " ";
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 获得我从任务池里申请下来的工作列表
	 * 
	 * @return
	 */
	public static DataTable DB_TaskPoolOfMyApply() {
		if (BP.WF.Glo.getIsEnableTaskPool() == false) {
			throw new RuntimeException("@你必须在Web.config中启用IsEnableTaskPool才可以执行此操作。");
		}

		Paras ps = new Paras();
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String wfSql = "  (WFState=" + WFState.Askfor.getValue() + " OR WFState=" + WFState.Runing.getValue()
				+ " OR WFState=" + WFState.Shift.getValue() + " OR WFState=" + WFState.ReturnSta.getValue()
				+ ") AND TaskSta=" + TaskSta.Takeback.getValue();
		String sql;
		String realSql;
		if (WebUser.getIsAuthorize() == false) {
			// 不是授权状态
			// ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND
			// FK_Emp=" + dbstr + "FK_Emp ORDER BY FK_Flow,ADT DESC ";
			// ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND
			// FK_Emp=" + dbstr + "FK_Emp ORDER BY ADT DESC ";
			ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp";

			// ps.SQL = "select v1.*,v2.name,v3.name as ParentName from (" +
			// realSql + ") as v1 left join JXW_Inc v2 on v1.WorkID=v2.OID left
			// join Jxw_Inc V3 on v1.PWorkID = v3.OID ORDER BY v1.ADT DESC";

			ps.Add("FK_Emp", WebUser.getNo());
			return BP.DA.DBAccess.RunSQLReturnTable(ps);
		}

		// 如果是授权状态, 获取当前委托人的信息.
		WFEmp emp = new WFEmp(WebUser.getNo());
		switch (emp.getHisAuthorWay()) {
		case All:
			ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp AND TaskSta=0";
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case SpecFlows:
			ps.SQL = "SELECT * FROM WF_EmpWorks WHERE (" + wfSql + ") AND FK_Emp=" + dbstr + "FK_Emp AND  FK_Flow IN "
					+ emp.getAuthorFlows() + "";
			ps.Add("FK_Emp", WebUser.getNo());
			break;
		case None:
			throw new RuntimeException("对方(" + WebUser.getNo() + ")已经取消了授权.");
		default:
			throw new RuntimeException("no such way...");
		}
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 获得所有的流程挂起工作列表
	 * 
	 * @return 返回从视图WF_EmpWorks查询出来的数据.
	 */
	public static DataTable DB_GenerHungUpList() {
		return DB_GenerHungUpList(null);
	}

	/**
	 * 获得指定流程挂起工作列表
	 * 
	 * @param fk_flow
	 *            流程编号,如果编号为空则返回所有的流程挂起工作列表.
	 * @return 返回从视图WF_EmpWorks查询出来的数据.
	 */
	public static DataTable DB_GenerHungUpList(String fk_flow) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		String sql;
		int state = WFState.HungUp.getValue();
		if (WebUser.getIsAuthorize()) {
			WFEmp emp = new WFEmp(WebUser.getNo());
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE  A.WFState=" + state
						+ " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND B.IsEnable=1 AND A.FK_Flow IN " + emp.getAuthorFlows();
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE  A.FK_Flow='" + fk_flow
						+ "' AND A.WFState=" + state + " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND  B.IsPass=1 AND A.FK_Flow IN " + emp.getAuthorFlows();
			}
		} else {
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE  A.WFState=" + state
						+ " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo() + "' AND B.IsEnable=1   ";
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WFState=" + state + " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND B.IsEnable=1 ";
			}
		}
		GenerWorkFlows gwfs = new GenerWorkFlows();
		gwfs.RetrieveInSQL(GenerWorkFlowAttr.WorkID, "(" + sql + ")");
		return gwfs.ToDataTableField();
	}

	/**
	 * 获得逻辑删除的流程
	 * 
	 * @return 返回从视图WF_EmpWorks查询出来的数据.
	 */
	public static DataTable DB_GenerDeleteWorkList() {
		return DB_GenerDeleteWorkList(WebUser.getNo(), null);
	}

	/**
	 * 获得逻辑删除的流程:根据流程编号
	 * 
	 * @param userNo
	 *            操作员编号
	 * @param fk_flow
	 *            流程编号(可以为空)
	 * @return WF_GenerWorkFlow数据结构的集合
	 */
	public static DataTable DB_GenerDeleteWorkList(String userNo, String fk_flow) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		String sql;
		int state = WFState.Delete.getValue();
		if (WebUser.getIsAuthorize()) {
			WFEmp emp = new WFEmp(WebUser.getNo());
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE  A.WFState=" + state
						+ " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND B.IsEnable=1 AND A.FK_Flow IN " + emp.getAuthorFlows();
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WFState=" + state + " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND  B.IsPass=1 AND A.FK_Flow IN " + emp.getAuthorFlows();
			}
		} else {
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE  A.WFState=" + state
						+ " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo() + "' AND B.IsEnable=1   ";
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WFState=" + state + " AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND B.IsEnable=1 ";
			}
		}
		GenerWorkFlows gwfs = new GenerWorkFlows();
		gwfs.RetrieveInSQL(GenerWorkFlowAttr.WorkID, "(" + sql + ")");
		return gwfs.ToDataTableField();
	}
	// C# TO JAVA CONVERTER
	/// #endregion 获取当前操作员的共享工作

	// C# TO JAVA CONVERTER
	/// #region 获取流程数据
	/**
	 * 根据流程状态获取指定流程数据
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param sta
	 *            流程状态
	 * @return 数据表OID,Title,RDT,FID
	 */
	public static DataTable DB_NDxxRpt(String fk_flow, WFState sta) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		Flow fl = new Flow(fk_flow);
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String sql = "SELECT OID,Title,RDT,FID FROM " + fl.getPTable() + " WHERE WFState=" + sta.getValue()
				+ " AND Rec=" + dbstr + "Rec";
		BP.DA.Paras ps = new BP.DA.Paras();
		ps.SQL = sql;
		ps.Add("Rec", WebUser.getNo());
		return DBAccess.RunSQLReturnTable(ps);
	}
	// C# TO JAVA CONVERTER
	/// #endregion

	// C# TO JAVA CONVERTER
	/// #region 获取当前可以退回的节点。
	/**
	 * 获取当前节点可以退回的节点
	 * 
	 * @param fk_node
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            FID
	 * @return No节点编号,Name节点名称,Rec记录人,RecName记录人名称
	 */
	public static DataTable DB_GenerWillReturnNodes(int fk_node, long workid, long fid) {
		DataTable dt = new DataTable("obt");
		dt.Columns.Add("No"); // 节点ID
		dt.Columns.Add("Name"); // 节点名称.
		dt.Columns.Add("Rec"); // 被退回节点上的操作员编号.
		dt.Columns.Add("RecName"); // 被退回节点上的操作员名称.

		Node nd = new Node(fk_node);
		if (nd.getHisRunModel() == RunModel.SubThread) {
			// 如果是子线程，它只能退回它的上一个节点，现在写死了，其它的设置不起作用了。
			Nodes nds = nd.getFromNodes();
			for (Node ndFrom : Nodes.convertNodes(nds)) {
				Work wk;
				switch (ndFrom.getHisRunModel()) {
				case FL:
				case FHL:
					wk = ndFrom.getHisWork();
					wk.setOID(fid);
					if (wk.RetrieveFromDBSources() == 0) {
						continue;
					}
					break;
				case SubThread:
					wk = ndFrom.getHisWork();
					wk.setOID(workid);
					if (wk.RetrieveFromDBSources() == 0) {
						continue;
					}
					break;
				case Ordinary:
				default:
					throw new RuntimeException("流程设计异常，子线程的上一个节点不能是普通节点。");
				}

				if (ndFrom.getNodeID() == fk_node) {
					continue;
				}

				DataRow dr = dt.NewRow();
				dr.setValue("No", String.valueOf(ndFrom.getNodeID()));
				dr.setValue("Name", ndFrom.getName());

				dr.setValue("Rec", wk.getRec());
				dr.setValue("RecName", wk.getRecText());

				dt.Rows.add(dr);
			}
			if (dt.Rows.size() == 0) {
				throw new RuntimeException("没有获取到应该退回的节点列表.");
			}
			return dt;
		}

		String sql = "";
		if (nd.getIsHL() || nd.getIsFLHL()) {
			// 如果当前点是分流，或者是分合流，就不按退回规则计算了。
			sql = "SELECT FK_Node AS No,FK_NodeText as Name, FK_Emp as Rec, FK_EmpText as RecName FROM WF_GenerWorkerlist WHERE FID="
					+ fid + " AND WorkID=" + workid + " AND FK_Node!=" + fk_node + "  ORDER BY RDT  ";
			return DBAccess.RunSQLReturnTable(sql);
		}

		WorkNode wn = new WorkNode(workid, fk_node);
		WorkNodes wns = new WorkNodes();
		switch (nd.getHisReturnRole()) {
		case CanNotReturn:
			return dt;
		case ReturnAnyNodes:
			if (nd.getTodolistModel() == TodolistModel.Order) {
				sql = "SELECT FK_Node as No,FK_NodeText as Name,FK_Emp as Rec,FK_EmpText as RecName FROM WF_GenerWorkerlist WHERE  (WorkID="
						+ workid + " AND IsEnable=1 AND IsPass=1 AND FK_Node!=" + fk_node + ") OR (FK_Node=" + fk_node
						+ " AND IsPass <0)  ORDER BY RDT";
			} else {
				sql = "SELECT FK_Node as No,FK_NodeText as Name,FK_Emp as Rec,FK_EmpText as RecName FROM WF_GenerWorkerlist WHERE  WorkID="
						+ workid + " AND IsEnable=1 AND IsPass=1 AND FK_Node!=" + fk_node + " ORDER BY RDT";
			}
			return DBAccess.RunSQLReturnTable(sql);
		case ReturnPreviousNode:
			WorkNode mywnP = wn.GetPreviousWorkNode();
			// turnTo = mywnP.HisWork.Rec + mywnP.HisWork.RecText;
			DataRow dr1 = dt.NewRow();
			dr1.setValue("No", String.valueOf(mywnP.getHisNode().getNodeID()));
			dr1.setValue("Name", mywnP.getHisNode().getName());

			dr1.setValue("Rec", mywnP.getHisWork().getRec());
			dr1.setValue("RecName", mywnP.getHisWork().getRecText());
			dt.Rows.add(dr1);
			break;
		case ReturnSpecifiedNodes: // 退回指定的节点。
			if (wns.size() == 0) {
				wns.GenerByWorkID(wn.getHisNode().getHisFlow(), workid);
			}

			NodeReturns rnds = new NodeReturns();
			rnds.Retrieve(NodeReturnAttr.FK_Node, fk_node);
			if (rnds.size() == 0) {
				throw new RuntimeException("@流程设计错误，您设置该节点可以退回指定的节点，但是指定的节点集合为空，请在节点属性设置它的制订节点。");
			}
			for (WorkNode mywn : WorkNodes.convertWorkNodes(wns)) {
				if (mywn.getHisNode().getNodeID() == fk_node) {
					continue;
				}

				if (rnds.Contains(NodeReturnAttr.ReturnTo, mywn.getHisNode().getNodeID()) == false) {
					continue;
				}

				DataRow dr = dt.NewRow();
				dr.setValue("No", String.valueOf(mywn.getHisNode().getNodeID()));
				dr.setValue("Name", mywn.getHisNode().getName());

				dr.setValue("Rec", mywn.getHisWork().getRec());
				dr.setValue("RecName", mywn.getHisWork().getRecText());
				dt.Rows.add(dr);
			}
			break;
		case ByReturnLine: // 按照流程图画的退回线执行退回.
			Directions dirs = new Directions();
			dirs.Retrieve(DirectionAttr.Node, fk_node, DirectionAttr.DirType, 1);
			if (dirs.size() == 0) {
				throw new RuntimeException("@流程设计错误:当前节点没有画向后退回的退回线,更多的信息请参考退回规则.");
			}
			for (Direction dir : Directions.convertDirections(dirs)) {
				Node toNode = new Node(dir.getToNode());
				sql = "SELECT FK_Emp,FK_EmpText FROM WF_GenerWorkerlist WHERE FK_Node=" + toNode.getNodeID()
						+ " AND WorkID=" + workid + " AND IsEnable=1 AND IsPass=1";
				DataTable dt1 = DBAccess.RunSQLReturnTable(sql);
				if (dt1.Rows.size() == 0) {
					continue;
				}

				DataRow dr = dt.NewRow();
				dr.setValue("No", String.valueOf(toNode.getNodeID()));
				dr.setValue("Name", toNode.getName());

				dr.setValue("Rec", dt1.Rows.get(0).getValue(0));
				dr.setValue("RecName", dt1.Rows.get(0).getValue(1));
				dt.Rows.add(dr);
			}
			break;
		default:
			throw new RuntimeException("@没有判断的退回类型。");
		}

		if (dt.Rows.size() == 0) {
			throw new RuntimeException("@没有计算出来要退回的节点，请管理员确认节点退回规则是否合理？");
		}
		return dt;
	}
	// C# TO JAVA CONVERTER
	/// #endregion 获取当前可以退回的节点

	// C# TO JAVA CONVERTER
	/// #region 获取当前操作员的在途工作
	/**
	 * 获取未完成的流程(也称为在途流程:我参与的但是此流程未完成)
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @return 返回从数据视图WF_GenerWorkflow查询出来的数据.
	 */
	public static DataTable DB_GenerRuning(String userNo, String fk_flow) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		String sql;
		int state = WFState.Runing.getValue();
		if (WebUser.getIsAuthorize()) {
			WFEmp emp = new WFEmp(userNo);
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
						+ userNo + "' AND B.IsEnable=1 AND  (B.IsPass=1 or B.IsPass < 0) AND A.FK_Flow IN "
						+ emp.getAuthorFlows();
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND B.IsEnable=1 AND  (B.IsPass=1 or B.IsPass < 0) AND A.FK_Flow IN "
						+ emp.getAuthorFlows();
			}
		} else {
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
						+ userNo + "' AND B.IsEnable=1 AND  (B.IsPass=1 or B.IsPass < 0) ";
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WorkID=B.WorkID AND B.FK_Emp='" + userNo
						+ "' AND B.IsEnable=1 AND (B.IsPass=1 or B.IsPass < 0 ) ";
			}
		}
		GenerWorkFlows gwfs = new GenerWorkFlows();
		gwfs.RetrieveInSQL(GenerWorkFlowAttr.WorkID, "(" + sql + ")");
		return gwfs.ToDataTableField();
	}

	public static DataTable DB_GenerRuning2(String userNo, String fk_flow, String title) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		String sql;
		int state = WFState.Runing.getValue();
		if (StringHelper.isNullOrEmpty(fk_flow)) {
			if (StringHelper.isNullOrEmpty(title)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
						+ userNo + "' AND B.IsEnable=1 AND  (B.IsPass=1 or B.IsPass < 0) and A.FK_Flow!='010'";
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
						+ userNo
						+ "' AND B.IsEnable=1 AND  (B.IsPass=1 or B.IsPass < 0) and A.FK_Flow!='010' and A.Title Like '%"
						+ title + "%'";
			}
		} else {
			if (StringHelper.isNullOrEmpty(title)) {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WorkID=B.WorkID AND B.FK_Emp='" + userNo
						+ "' AND B.IsEnable=1 AND (B.IsPass=1 or B.IsPass < 0 )";
			} else {
				sql = "SELECT a.WorkID FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WorkID=B.WorkID AND B.FK_Emp='" + userNo
						+ "' AND B.IsEnable=1 AND (B.IsPass=1 or B.IsPass < 0 ) and A.Title Like '%" + title + "%' ";
			}
		}
		GenerWorkFlows gwfs = new GenerWorkFlows();
		gwfs.RetrieveInSQL(GenerWorkFlowAttr.WorkID, "(" + sql + ")");
		return gwfs.ToDataTableField();
	}

	/**
	 * 在途工作
	 * 
	 * @return
	 */
	public static DataTable DB_GenerRuningV2() {
		String userNo = WebUser.getNo();
		String fk_flow = null;
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		String sql;
		int state = WFState.Runing.getValue();
		if (WebUser.getIsAuthorize()) {
			WFEmp emp = new WFEmp(userNo);
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.* FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
						+ userNo + "' AND B.IsEnable=1 AND B.IsPass=1 AND A.FK_Flow IN " + emp.getAuthorFlows();
			} else {
				sql = "SELECT a.* FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WorkID=B.WorkID AND B.FK_Emp='" + WebUser.getNo()
						+ "' AND B.IsEnable=1 AND B.IsPass=1 AND A.FK_Flow IN " + emp.getAuthorFlows();
			}
		} else {
			if (StringHelper.isNullOrEmpty(fk_flow)) {
				sql = "SELECT a.* FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.WorkID=B.WorkID AND B.FK_Emp='"
						+ userNo + "' AND B.IsEnable=1 AND B.IsPass=1 ";
			} else {
				sql = "SELECT a.* FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B WHERE A.FK_Flow='" + fk_flow
						+ "'  AND A.WorkID=B.WorkID AND B.FK_Emp='" + userNo + "' AND B.IsEnable=1 AND B.IsPass=1 ";
			}
		}
		return DBAccess.RunSQLReturnTable(sql);
	}

	/**
	 * 获取内部系统消息
	 * 
	 * @param myPK
	 * @return
	 */
	public static DataTable DB_GenerPopAlert(String type) {
		String sql = "";
		if (type.equals("unRead")) {
			sql = "SELECT LEFT(CONVERT(VARCHAR(20),RDT,120),10) AS SortRDT,Datepart(WEEKDAY, CONVERT(DATETIME,RDT)  + @@DateFirst - 1) AS WeekRDT,"
					+ "* FROM Sys_SMS WHERE SendTo ='" + WebUser.getNo()
					+ "' AND (IsRead = 0 OR IsRead IS NULL)  ORDER BY RDT DESC";
		} else {
			sql = "SELECT LEFT(CONVERT(VARCHAR(20),RDT,120),10) AS SortRDT,Datepart(WEEKDAY, CONVERT(DATETIME,RDT)  + @@DateFirst - 1) AS WeekRDT,"
					+ "* FROM Sys_SMS WHERE SendTo ='" + WebUser.getNo() + "'  ORDER BY RDT DESC";
		}
		return BP.DA.DBAccess.RunSQLReturnTable(sql);
	}

	/**
	 * 获取外部系统消息
	 * 
	 * @param type
	 * @param No
	 * @return
	 */
	public static DataTable DB_GenerPopAlert(String type, String No) {
		String sql = "";
		if (type.equals("unRead")) {
			sql = "SELECT LEFT(CONVERT(VARCHAR(20),RDT,120),10) AS SortRDT,Datepart(WEEKDAY, CONVERT(DATETIME,RDT)  + @@DateFirst - 1) AS WeekRDT,"
					+ "* FROM Sys_SMS WHERE SendTo ='" + No + "' AND (IsRead = 0 OR IsRead IS NULL)  ORDER BY RDT DESC";
		} else {
			sql = "SELECT LEFT(CONVERT(VARCHAR(20),RDT,120),10) AS SortRDT,Datepart(WEEKDAY, CONVERT(DATETIME,RDT)  + @@DateFirst - 1) AS WeekRDT,"
					+ "* FROM Sys_SMS WHERE SendTo ='" + No + "'  ORDER BY RDT DESC";
		}
		return BP.DA.DBAccess.RunSQLReturnTable(sql);
	}

	/**
	 * 
	 * 
	 * @param myPK
	 */
	public static DataTable DB_GenerUpdateMsgSta(String myPK) {
		String sql = "";
		sql = " UPDATE Sys_SMS SET IsRead=1 WHERE MyPK='" + myPK + "'";
		return BP.DA.DBAccess.RunSQLReturnTable(sql);
	}

	/**
	 * 获取未完成的流程(也称为在途流程:我参与的但是此流程未完成)
	 * 
	 * @return 返回从数据视图WF_GenerWorkflow查询出来的数据.
	 */
	public static DataTable DB_GenerRuning() {
		DataTable dt = DB_GenerRuning(WebUser.getNo(), null);
		dt.Columns.Add("Type");

		for (DataRow row : dt.Rows) {
			row.setValue("Type", "RUNNING");
		}

		// dt.DefaultView.Sort = "RDT DESC";
		//// return dt.DefaultView.ToTable();
		return dt;
	}

	/**
	 * 把抄送的信息也发送
	 * 
	 * @return
	 */
	public static DataTable DB_GenerRuningAndCC() {
		DataTable dt = DB_GenerRuning();
		DataTable ccDT = DB_CCList_CheckOver(WebUser.getNo());
		try {
			dt.Columns.Add("MyPK");
			dt.Columns.Add("Sta");
		} catch (RuntimeException e) {

		}

		for (DataRow row : ccDT.Rows) {
			DataRow newRow = dt.NewRow();

			for (DataColumn column : ccDT.Columns) {
				for (DataColumn dtColumn : dt.Columns) {
					if (column.ColumnName == dtColumn.ColumnName) {
						newRow.setValue(column.ColumnName, row.getValue(dtColumn.ColumnName));
					}

				}

			}
			newRow.setValue("Type", "CC");
			dt.Rows.add(newRow);
		}
		// dt.DefaultView.Sort = "RDT DESC";
		// return dt.DefaultView.ToTable();
		return dt;
	}

	public static DataTable DB_GenerRuning3(String name, String fk_flow, String title) {
		DataTable dt = DB_GenerRuning2(name, fk_flow, title);

		dt.Columns.Add("Type");

		for (DataRow row : dt.Rows) {
			row.setValue("Type", "RUNNING");
		}

		// dt.DefaultView.Sort = "RDT DESC";
		// return dt.DefaultView.ToTable();
		return dt;
	}

	public static DataTable DB_GenerRuningAndCC2(String name, String fk_flow, String title) {
		DataTable dt = DB_GenerRuning3(name, fk_flow, title);
		DataTable ccDT = DB_CCList_CheckOver(WebUser.getNo());
		try {
			dt.Columns.Add("MyPK");
			dt.Columns.Add("Sta");
		} catch (RuntimeException e) {

		}

		for (DataRow row : ccDT.Rows) {
			DataRow newRow = dt.NewRow();

			for (DataColumn column : ccDT.Columns) {
				for (DataColumn dtColumn : dt.Columns) {
					if (column.ColumnName == dtColumn.ColumnName) {
						newRow.setValue(column.ColumnName, row.getValue(dtColumn.ColumnName));
					}

				}

			}
			newRow.setValue("Type", "CC");
			dt.Rows.add(newRow);
		}
		// dt.DefaultView.Sort = "RDT DESC";
		// return dt.DefaultView.ToTable();
		return dt;
	}
	// C# TO JAVA CONVERTER
	/// #endregion 获取当前操作员的共享工作

	// C# TO JAVA CONVERTER
	/// #region 获取当前的批处理工作
	/**
	 * 获取当前节点的批处理工作
	 * 
	 * @param FK_Node
	 * @return
	 */
	public static DataTable GetBatch(int FK_Node) {

		Node nd = new Node(FK_Node);
		Flow fl = nd.getHisFlow();
		String fromTable = "";

		if (fl.getHisDataStoreModel() == DataStoreModel.ByCCFlow) {
			fromTable = nd.getPTable();
		} else {
			fromTable = fl.getPTable();
		}
		String sql = "SELECT a.*, b.Starter,b.Title as STitle,b.ADT,b.WorkID FROM " + fromTable
				+ " a , WF_EmpWorks b WHERE a.OID=B.WorkID AND b.WFState Not IN (7) AND b.FK_Node=" + nd.getNodeID()
				+ " AND b.FK_Emp='" + WebUser.getNo() + "'";
		// string sql = "SELECT Title,RDT,ADT,SDT,FID,WorkID,Starter FROM
		// WF_EmpWorks WHERE FK_Emp='" + WebUser.getNo() + "'";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		return dt;
	}

	// C# TO JAVA CONVERTER
	/// #endregion 获取当前的批处理工作
	// C# TO JAVA CONVERTER
	/// #endregion

	// C# TO JAVA CONVERTER
	/// #region 登陆接口
	/**
	 * 用户登陆,此方法是在开发者校验好用户名与密码后执行
	 * 
	 * @param userNo
	 *            用户名
	 * @param SID
	 *            安全ID,请参考流程设计器操作手册
	 */
	public static void Port_Login(String userNo, String sid) {
		if (userNo.equals(WebUser.getNo())) {
			return;
		}

		Paras ps = new Paras();
		ps.SQL = "SELECT SID FROM Port_Emp WHERE No=" + BP.Sys.SystemConfig.getAppCenterDBVarStr() + "No";
		ps.Add("No", userNo);
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(ps);
		if (dt.Rows.size() == 0) {
			throw new RuntimeException("用户不存在或者SID错误。");
		}

		if (!sid.equals(dt.Rows.get(0).getValue("SID").toString().equals(sid))) {
			throw new RuntimeException("用户不存在或者SID错误。");
		}

		BP.Port.Emp emp = new BP.Port.Emp(userNo);
		WebUser.SignInOfGener(emp, true);
		WebUser.setIsWap(false);
		return;
	}

	/**
	 * 用户登陆,此方法是在开发者校验好用户名与密码后执行
	 * 
	 * @param userNo
	 *            用户名
	 */
	public static String Port_Login(String userNo) {
		BP.Port.Emp emp = new BP.Port.Emp(userNo);
		WebUser.SignInOfGener(emp, true);
		WebUser.setIsWap(false);
		return Port_GetSID(userNo);
	}

	/**
	 * 注销当前登录
	 * 
	 */
	public static void Port_SigOut() {
		WebUser.Exit();
	}

	/**
	 * 获取未读的消息 用于消息提醒.
	 * 
	 * @param userNo
	 *            用户ID
	 */
	public static String Port_SMSInfo(String userNo) {
		Paras ps = new Paras();
		ps.SQL = "SELECT MyPK, EmailTitle  FROM sys_sms where SendTo=" + SystemConfig.getAppCenterDBVarStr()
				+ "SendTo AND IsAlert=0";
		ps.Add("SendTo", userNo);
		DataTable dt = DBAccess.RunSQLReturnTable(ps);
		String strs = "";
		for (DataRow dr : dt.Rows) {
			strs += "@" + dr.get(0) + "=" + dr.get(1).toString();
		}
		ps = new Paras();
		ps.SQL = "UPDATE  sys_sms SET IsAlert=1 WHERE  SendTo=" + SystemConfig.getAppCenterDBVarStr()
				+ "SendTo AND IsAlert=0";
		ps.Add("SendTo", userNo);
		DBAccess.RunSQL(ps);
		return strs;
	}

	/**
	 * 发送消息
	 * 
	 * @param userNo
	 *            信息接收人
	 * @param msgTitle
	 *            标题
	 * @param msgDoc
	 *            内容
	 */
	public static void Port_SendMsg(String userNo, String msgTitle, String msgDoc, String msgFlag) {
		Port_SendMsg(userNo, msgTitle, msgDoc, msgFlag, SMSMsgType.Self, null, 0, 0, 0);
	}

	/**
	 * 获取SID
	 * 
	 * @param userNo
	 *            用户编号
	 * @return SID
	 */
	public static String Port_GetSidName(String userNo) {
		String info = "";
		Paras ps = new Paras();
		ps.SQL = "SELECT SID, Name FROM Port_Emp WHERE No=" + BP.Sys.SystemConfig.getAppCenterDBVarStr() + "No";
		ps.Add("No", userNo);
		DataTable table = BP.DA.DBAccess.RunSQLReturnTable(ps);
		info = BP.Tools.FormatToJson.ToJson(table);

		return info;
	}

	/**
	 * 获取SID
	 * 
	 * @param userNo
	 *            用户编号
	 * @return SID
	 */
	public static String Port_GetSID(String userNo) {
		Paras ps = new Paras();
		ps.SQL = "SELECT SID FROM Port_Emp WHERE No=" + BP.Sys.SystemConfig.getAppCenterDBVarStr() + "No";
		ps.Add("No", userNo);
		String sid = BP.DA.DBAccess.RunSQLReturnString(ps);
		if (StringHelper.isNullOrEmpty(sid) == true) {
			try {
				sid = BP.DA.DBAccess.GenerGUID();
				ps.SQL = Glo.getUpdataSID();
				ps.Add("SID", sid);
				ps.Add("No", userNo);
				BP.DA.DBAccess.RunSQL(ps);
			} catch (java.lang.Exception e) {
				// throw new Exception("@可");
				// 这里有可能是更新失败，是因为用户连接的视图.
			}
		}
		return sid;
	}

	/**
	 * 设置SID
	 * 
	 * @param userNo
	 *            用户编号
	 * @param sid
	 *            SID信息
	 * @return SID
	 */
	public static boolean Port_SetSID(String userNo, String sid) {
		Paras ps = new Paras();
		ps.SQL = "UPDATE Port_Emp SET SID=" + BP.Sys.SystemConfig.getAppCenterDBVarStr() + "SID WHERE No="
				+ BP.Sys.SystemConfig.getAppCenterDBVarStr() + "No";
		ps.Add("SID", sid);
		ps.Add("No", userNo);
		if (BP.DA.DBAccess.RunSQL(ps) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发送邮件与消息(如果传入4大流程参数将会增加一个工作链接)
	 * 
	 * @param userNo
	 *            信息接收人
	 * @param title
	 *            标题
	 * @param msgDoc
	 *            内容
	 * @param msgFlag
	 *            消息标志
	 * @param flowNo
	 *            流程编号
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @param fid
	 *            FID
	 */
	public static void Port_SendMsg(String userNo, String title, String msgDoc, String msgFlag, String msgType,
			String flowNo, long nodeID, long workID, long fid) {
		if (workID != 0) {
			String url = Glo.getHostURL() + "WF/Do.jsp?SID=" + userNo + "_" + workID + "_" + nodeID;
			url = url.replace("//", "/");
			url = url.replace("//", "/");

			msgDoc += " <hr>打开工作: " + url;
		}
		SMS.SendMsg(userNo, title, msgDoc, msgFlag, msgType);
	}

	/**
	 * 发送消息
	 * 
	 * @param sendTo
	 *            发送给
	 * @param mailTitle
	 *            标题
	 * @param mailDoc
	 *            内容
	 * @param smsInfo
	 *            短信信息
	 * @param msgFlag
	 *            消息标记
	 * @param msgType
	 *            消息类型
	 * @param flowNo
	 *            流程编号
	 * @param nodeID
	 *            节点编号
	 * @param workID
	 *            工作ID
	 * @param fid
	 *            FID
	 * @param isEmail
	 *            是否发送邮件
	 * @param isSMS
	 *            是否发送短信
	 */
	public static void Port_SendMsg(String sendTo, String mailTitle, String mailDoc, String smsInfo, String msgFlag,
			String msgType, String flowNo, long nodeID, long workID, long fid, boolean isEmail, boolean isSMS) {
		SMS sms = new SMS();
		sms.setMyPK(DBAccess.GenerGUID());
		sms.setHisEmaiSta(MsgSta.UnRun);
		sms.setSender(WebUser.getNo());
		sms.setSendTo(sendTo);

		// 邮件标题.
		sms.setTitle(mailTitle);
		sms.setDocOfEmail(mailDoc);
		if (isEmail == true) {
			sms.setHisEmaiSta(MsgSta.UnRun);
		} else {
			sms.setHisEmaiSta(MsgSta.Disable);
		}

		// 短信属性.
		if (isSMS == true) {
			sms.setHisMobileSta(MsgSta.UnRun);
		} else {
			sms.setHisMobileSta(MsgSta.Disable);
		}

		sms.setMobileInfo(smsInfo); // 短信信息.

		// 其他属性.
		sms.setSender(WebUser.getNo());
		sms.setRDT(BP.DA.DataType.getCurrentDataTime());

		sms.setMsgFlag(msgFlag); // 消息标志.
		sms.setMsgType(msgType); // 消息类型.
		sms.Insert();
	}

	/**
	 * 发送消息
	 * 
	 * @param mobileNum
	 *            手机号吗
	 * @param mobileInfo
	 *            短信信息
	 * @param emailAddress
	 *            邮件
	 * @param emilTitle
	 *            标题
	 * @param emailBody
	 *            邮件内容
	 * @param msgFlag
	 *            消息标记，可以为空。
	 */
	public static void Port_SendMsg(String mobileNum, String mobileInfo, String emailAddress, String emilTitle,
			String emailBody, String msgFlag, String msgType) {
		SMS.SendMsg(mobileNum, mobileInfo, emailAddress, emilTitle, emailBody, msgFlag, msgType, null);
	}

	/**
	 * 发送消息
	 * 
	 * @param mobileNum
	 *            手机号吗
	 * @param mobileInfo
	 *            短信信息
	 * @param emailAddress
	 *            邮件
	 * @param emilTitle
	 *            标题
	 * @param emailBody
	 *            邮件内容
	 * @param msgFlag
	 *            消息标记，可以为空。
	 * @param msgType
	 *            消息类型，可以为空。
	 */
	public static void Port_SendMsg(String mobileNum, String mobileInfo, String emailAddress, String emilTitle,
			String emailBody, String msgFlag, String msgType, String guestNo) {
		SMS.SendMsg(mobileNum, mobileInfo, emailAddress, emilTitle, emailBody, msgFlag, msgType, guestNo);
	}

	/**
	 * 转化流程Code到流程编号
	 * 
	 * @param FlowMark
	 *            流程编号
	 * @return 返回编码
	 */
	public static String TurnFlowMarkToFlowNo(String FlowMark) {
		if (StringHelper.isNullOrEmpty(FlowMark)) {
			return "";
		}

		// 如果是编号，就不用转化.
		if (DataType.IsNumStr(FlowMark)) {
			return FlowMark;
		}

		String s = DBAccess.RunSQLReturnStringIsNull("SELECT No FROM WF_Flow WHERE FlowMark='" + FlowMark + "'", null);
		if (s == null) {
			throw new RuntimeException("@FlowMark错误:" + FlowMark + ",没有找到它的流程编号.");
		}
		return s;
	}

	/**
	 * 获取最新的消息
	 * 
	 * @param userNo
	 *            用户编号
	 * @param dateLastTime
	 *            上次获取的时间
	 * @return 返回消息：返回两个列的数据源MsgType,Num.
	 */
	public static DataTable Port_GetNewMsg(String userNo, String dateLastTime) {
		Paras ps = new Paras();
		ps.SQL = "SELECT MsgType , Count(*) as Num FROM Sys_SMS WHERE SendTo="
				+ BP.Sys.SystemConfig.getAppCenterDBVarStr() + "SendTo AND RDT >"
				+ BP.Sys.SystemConfig.getAppCenterDBVarStr() + "RDT Group By MsgType";
		ps.Add(SMSAttr.SendTo, userNo);
		ps.Add(SMSAttr.RDT, dateLastTime);
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(ps);
		return dt;
	}

	/**
	 * 获取最新的消息
	 * 
	 * @param userNo
	 *            用户编号
	 * @return
	 */
	public static DataTable Port_GetNewMsg(String userNo) {
		Paras ps = new Paras();
		ps.SQL = "SELECT MsgType , Count(*) as Num FROM Sys_SMS WHERE SendTo="
				+ BP.Sys.SystemConfig.getAppCenterDBVarStr() + "SendTo  Group By MsgType";
		ps.Add(SMSAttr.SendTo, userNo);
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(ps);
		return dt;
	}
	// C# TO JAVA CONVERTER
	/// #endregion 登陆接口

	// C# TO JAVA CONVERTER
	/// #region 与流程有关的接口
	/**
	 * 写入日志
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeFrom
	 *            节点从
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            FID
	 * @param msg
	 *            信息
	 * @param at
	 *            活动类型
	 * @param tag
	 *            参数:用@符号隔开比如, @PWorkID=101@PFlowNo=003
	 * @param cFlowInfo
	 *            子流程信息
	 */
	public static void WriteTrack(String flowNo, int nodeFrom, long workid, long fid, String msg, ActionType at,
			String tag, String cFlowInfo, String optionMsg) {
		if (at == ActionType.CallChildenFlow) {
			if (StringHelper.isNullOrEmpty(cFlowInfo) == true) {
				throw new RuntimeException("@必须输入信息cFlowInfo信息,在 CallChildenFlow 模式下.");
			}
		}

		if (StringHelper.isNullOrEmpty(optionMsg)) {
			optionMsg = Track.GetActionTypeT(at);
		}

		Track t = new Track();
		t.setWorkID(workid);
		t.setFID(fid);
		t.setRDT(DataType.getCurrentDataTimess());
		t.setHisActionType(at);
		t.setActionTypeText(optionMsg);

		Node nd = new Node(nodeFrom);
		t.setNDFrom(nodeFrom);
		t.setNDFromT(nd.getName());

		t.setEmpFrom(WebUser.getNo());
		t.setEmpFromT(WebUser.getName());
		t.FK_Flow = flowNo;

		t.setNDTo(nodeFrom);
		t.setNDToT(nd.getName());

		t.setEmpTo(WebUser.getNo());
		t.setEmpToT(WebUser.getName());
		t.setMsg(msg);

		if (tag != null) {
			t.setTag(tag);
		}

		try {
			t.Insert();
		} catch (java.lang.Exception e) {
			t.CheckPhysicsTable();
			t.Insert();
			t.DirectInsert();
		}

		// C# TO JAVA CONVERTER
		/// #region 特殊判断.
		if (at == ActionType.CallChildenFlow) {
			// 如果是吊起子流程，就要向它父流程信息里写数据，让父流程可以看到能够发起那些流程数据。
			AtPara ap = new AtPara(tag);
			GenerWorkFlow gwf = new GenerWorkFlow(ap.GetValInt64ByKey(GenerWorkFlowAttr.PWorkID));
			t.setWorkID(gwf.getWorkID());

			nd = new Node(gwf.getFK_Node());
			t.setNDFrom(gwf.getFK_Node());
			t.setNDFromT(nd.getName());

			t.setNDTo(t.getNDFrom());
			t.setNDToT(t.getNDFromT());

			t.FK_Flow = gwf.getFK_Flow();

			t.setHisActionType(ActionType.StartChildenFlow);
			t.setTag("@CWorkID=" + workid + "@CFlowNo=" + flowNo);
			t.setMsg(cFlowInfo);
			t.Insert();
		}
	}

	/**
	 * 写入日志
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeFrom
	 *            节点从
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            FID
	 * @param msg
	 *            信息
	 * @param at
	 *            活动类型
	 * @param tag
	 *            参数:用@符号隔开比如, @PWorkID=101@PFlowNo=003
	 * @param cFlowInfo
	 *            子流程信息
	 */
	public static void WriteTrack(String flowNo, int nodeFrom, long workid, long fid, String msg, ActionType at,
			String tag, String cFlowInfo, String optionMsg, String operateEmp, String operateEmpName) {
		if (at == ActionType.CallChildenFlow) {
			if (StringHelper.isNullOrEmpty(cFlowInfo) == true) {
				throw new RuntimeException("@必须输入信息cFlowInfo信息,在 CallChildenFlow 模式下.");
			}
		}

		if (StringHelper.isNullOrEmpty(optionMsg)) {
			optionMsg = Track.GetActionTypeT(at);
		}

		Track t = new Track();
		t.setWorkID(workid);
		t.setFID(fid);
		t.setRDT(DataType.getCurrentDataTimess());
		t.setHisActionType(at);
		t.setActionTypeText(optionMsg);

		Node nd = new Node(nodeFrom);
		t.setNDFrom(nodeFrom);
		t.setNDFromT(nd.getName());

		t.setEmpFrom(operateEmp);
		t.setEmpFromT(operateEmpName);
		t.FK_Flow = flowNo;

		t.setNDTo(nodeFrom);
		t.setNDToT(nd.getName());

		t.setEmpTo(operateEmp);
		t.setEmpToT(operateEmpName);
		t.setMsg(msg);

		if (tag != null) {
			t.setTag(tag);
		}

		try {
			t.Insert();
		} catch (java.lang.Exception e) {
			t.CheckPhysicsTable();
			t.Insert();
			t.DirectInsert();
		}

		// C# TO JAVA CONVERTER
		/// #region 特殊判断.
		if (at == ActionType.CallChildenFlow) {
			// 如果是吊起子流程，就要向它父流程信息里写数据，让父流程可以看到能够发起那些流程数据。
			AtPara ap = new AtPara(tag);
			GenerWorkFlow gwf = new GenerWorkFlow(ap.GetValInt64ByKey(GenerWorkFlowAttr.PWorkID));
			t.setWorkID(gwf.getWorkID());

			nd = new Node(gwf.getFK_Node());
			t.setNDFrom(gwf.getFK_Node());
			t.setNDFromT(nd.getName());

			t.setNDTo(t.getNDFrom());
			t.setNDToT(t.getNDFromT());

			t.FK_Flow = gwf.getFK_Flow();

			t.setHisActionType(ActionType.StartChildenFlow);
			t.setTag("@CWorkID=" + workid + "@CFlowNo=" + flowNo);
			t.setMsg(cFlowInfo);
			t.Insert();
		}
	}

	/**
	 * 写入日志
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeFrom
	 *            节点从
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            fID
	 * @param msg
	 *            信息
	 */
	public static void WriteTrackInfo(String flowNo, int nodeFrom, long workid, long fid, String msg,
			String optionMsg) {
		WriteTrack(flowNo, nodeFrom, workid, fid, msg, ActionType.Info, null, null, optionMsg);
	}

	/**
	 * 写入工作审核日志:
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeID
	 *            节点从
	 * @param workid
	 *            工作ID
	 * @param FID
	 *            FID
	 * @param msg
	 *            审核信息
	 * @param optionName
	 *            操作名称(比如:科长审核、部门经理审批),如果为空就是"审核".
	 */
	public static void WriteTrackWorkCheck(String flowNo, int nodeFrom, long workid, long fid, String msg,
			String optionName) {
		String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE  ND" + Integer.parseInt(flowNo) + "Track SET Msg=" + dbStr + "Msg WHERE  WorkID=" + dbStr
				+ "WorkID  AND NDFrom=" + dbStr + "NDFrom AND EmpFrom=" + dbStr + "EmpFrom AND ActionType="
				+ ActionType.WorkCheck.getValue();
		ps.Add(TrackAttr.Msg, msg);
		ps.Add(TrackAttr.WorkID, workid);
		ps.Add(TrackAttr.NDFrom, nodeFrom);
		ps.Add(TrackAttr.EmpFrom, WebUser.getNo());
		if (DBAccess.RunSQL(ps) == 0) {
			// 如果没有更新到，就写入.
			WriteTrack(flowNo, nodeFrom, workid, fid, msg, ActionType.WorkCheck, null, null, optionName);
		}
	}

	/**
	 * 写入日志组件
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeFrom
	 * @param workid
	 * @param fid
	 * @param msg
	 * @param optionName
	 */
	public static void WriteTrackDailyLog(String flowNo, int nodeFrom, long workid, long fid, String msg,
			String optionName) {
		String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		String today = BP.DA.DataType.getCurrentData();

		Paras ps = new Paras();
		ps.SQL = "UPDATE  ND" + Integer.parseInt(flowNo) + "Track SET Msg=" + dbStr + "Msg WHERE  RDT LIKE '" + today
				+ "%' AND WorkID=" + dbStr + "WorkID  AND NDFrom=" + dbStr + "NDFrom AND EmpFrom=" + dbStr
				+ "EmpFrom AND ActionType=" + ActionType.WorkCheck.getValue();
		ps.Add(TrackAttr.Msg, msg);
		ps.Add(TrackAttr.WorkID, workid);
		ps.Add(TrackAttr.NDFrom, nodeFrom);
		ps.Add(TrackAttr.EmpFrom, WebUser.getNo());
		if (DBAccess.RunSQL(ps) == 0) {
			// 如果没有更新到，就写入.
			WriteTrack(flowNo, nodeFrom, workid, fid, msg, ActionType.WorkCheck, null, null, optionName);
		}
	}

	/**
	 * 写入周报组件 一旦写入数据,只可更新 每周一次 qin
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeFrom
	 * @param workid
	 * @param fid
	 * @param msg
	 * @param optionName
	 */
	public static void WriteTrackWeekLog(String flowNo, int nodeFrom, long workid, long fid, String msg,
			String optionName) {
	}

	// {
	// String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
	//
	// java.util.Date dTime = new java.util.Date();
	// java.util.Date startWeek = dTime.AddDays(1 -
	// Integer.parseInt(String.format("%d", dTime.getDayOfWeek()))); //本周第一天
	//
	// java.util.Hashtable ht = new java.util.Hashtable(); //当前日期所属的week包含哪些日期
	// for (int i = 1; i < 7; i++)
	// {
	// ht.put(i + 1, startWeek.AddDays(i).ToString("yyyy-MM-dd"));
	// }
	// ht.put(1, startWeek.ToString("yyyy-MM-dd"));
	//
	// boolean isExitWeek = false; //本周是否已经有插入数据
	// String insertDate = null;
	// DataTable dt;
	// String sql = null;
	//
	// for (DictionaryEntry de : ht)
	// {
	// sql = "SELECT * FROM ND" + Integer.parseInt(flowNo) + "Track WHERE RDT
	// LIKE '" + de.getValue().toString() + "%' AND WorkID=" + workid + " AND
	// NDFrom='" + nodeFrom + "' AND EmpFrom='" + WebUser.getNo() + "' AND
	// ActionType=" + ActionType.WorkCheck.getValue();
	//
	// if (DBAccess.RunSQLReturnCOUNT(sql) != 0)
	// {
	// isExitWeek = true;
	// insertDate = de.getValue().toString();
	// break;
	// }
	// }
	//
	// //如果本周已经插入了记录，那么更新
	// if (isExitWeek)
	// {
	// Paras ps = new Paras();
	// ps.SQL = "UPDATE ND" + Integer.parseInt(flowNo) + "Track SET Msg=" +
	// dbStr + "Msg WHERE RDT LIKE '" + insertDate + "%' AND WorkID=" + dbStr +
	// "WorkID AND NDFrom=" + dbStr + "NDFrom AND EmpFrom=" + dbStr + "EmpFrom
	// AND ActionType=" + ActionType.WorkCheck.getValue();
	// ps.Add(TrackAttr.Msg, msg);
	// ps.Add(TrackAttr.WorkID, workid);
	// ps.Add(TrackAttr.NDFrom, nodeFrom);
	// ps.Add(TrackAttr.EmpFrom, WebUser.getNo());
	//
	// DBAccess.RunSQL(ps);
	// }
	// else
	// {
	// WriteTrack(flowNo, nodeFrom, workid, fid, msg, ActionType.WorkCheck,
	// null, null, optionName);
	// }
	// }
	/**
	 * 写入月报组件 同周报一样每月一条记录 qin
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeFrom
	 * @param workid
	 * @param fid
	 * @param msg
	 * @param optionName
	 */
	public static void WriteTrackMonthLog(String flowNo, int nodeFrom, long workid, long fid, String msg,
			String optionName) {
	}

	// {
	// String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
	// String today = BP.DA.DataType.getCurrentData();
	//
	// java.util.Date dTime = new java.util.Date();
	// java.util.Date startDay = dTime.addDays(1 - dTime.getDay()); //本月第一天
	//
	// int days = java.util.Date.DaysInMonth(dTime.Year, dTime.Month);
	// java.util.Hashtable ht = new java.util.Hashtable();
	//
	// for (int i = 1; i < days; i++)
	// {
	// ht.put(i + 1, startDay.AddDays(i).ToString("yyyy-MM-dd"));
	// }
	// ht.put(1, startDay.ToString("yyyy-MM-dd"));
	//
	// boolean isExitMonth = false; //本月是否已经有插入数据
	// String insertDate = null;
	// DataTable dt;
	// String sql = null;
	//
	// for (DictionaryEntry de : ht)
	// {
	// sql = "SELECT * FROM ND" + Integer.parseInt(flowNo) + "Track WHERE RDT
	// LIKE '" + de.getValue().toString() + "%' AND WorkID=" + workid + " AND
	// NDFrom='" + nodeFrom + "' AND EmpFrom='" + WebUser.getNo() + "' AND
	// ActionType=" + ActionType.WorkCheck.getValue();
	//
	// if (DBAccess.RunSQLReturnCOUNT(sql) != 0)
	// {
	// isExitMonth = true;
	// insertDate = de.getValue().toString();
	// break;
	// }
	// }
	//
	// if (isExitMonth)
	// {
	// Paras ps = new Paras();
	// ps.SQL = "UPDATE ND" + Integer.parseInt(flowNo) + "Track SET Msg=" +
	// dbStr + "Msg WHERE RDT LIKE '" + insertDate + "%' AND WorkID=" + dbStr +
	// "WorkID AND NDFrom=" + dbStr + "NDFrom AND EmpFrom=" + dbStr + "EmpFrom
	// AND ActionType=" + ActionType.WorkCheck.getValue();
	// ps.Add(TrackAttr.Msg, msg);
	// ps.Add(TrackAttr.WorkID, workid);
	// ps.Add(TrackAttr.NDFrom, nodeFrom);
	// ps.Add(TrackAttr.EmpFrom, WebUser.getNo());
	//
	// DBAccess.RunSQL(ps);
	// }
	// else
	// {
	// WriteTrack(flowNo, nodeFrom, workid, fid, msg, ActionType.WorkCheck,
	// null, null, optionName);
	// }
	// }
	/**
	 * 修改审核信息标识 比如：在默认的情况下是"审核"，现在要把ActionTypeText 修改成"组长审核。"。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param nodeFrom
	 *            节点ID
	 * @param label
	 *            要修改成的标签
	 * @return 是否成功
	 */
	public static boolean WriteTrackWorkCheckLabel(String flowNo, long workid, int nodeFrom, String label) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";
		String sql = "SELECT MyPK FROM " + table + " WHERE NDFrom=" + nodeFrom + " AND WorkID=" + workid
				+ " And NDTo='0' ORDER BY RDT DESC ";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			return false;
		}

		String pk = dt.Rows.get(0).getValue(0).toString();
		sql = "UPDATE " + table + " SET " + TrackAttr.ActionTypeText + "='" + label + "' WHERE MyPK=" + pk;
		BP.DA.DBAccess.RunSQL(sql);
		return true;
	}

	/**
	 * 前进,获取等标签 比如：在默认的情况下是"逻辑删除"，现在要把ActionTypeText 修改成"删除(清场)。"。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param nodeFrom
	 *            节点ID
	 * @param label
	 *            要修改成的标签
	 * @return 是否成功
	 */
	public static boolean WriteTrackLabel(String flowNo, long workid, int nodeFrom, String label) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";
		String sql = "SELECT MyPK FROM " + table + " WHERE NDFrom=" + nodeFrom + " AND WorkID=" + workid
				+ "  ORDER BY RDT DESC ";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			return false;
		}

		String pk = dt.Rows.get(0).getValue(0).toString();
		sql = "UPDATE " + table + " SET " + TrackAttr.ActionTypeText + "='" + label + "' WHERE MyPK=" + pk;
		BP.DA.DBAccess.RunSQL(sql);
		return true;
	}

	/**
	 * 获取Track 表中的审核的信息
	 * 
	 * @param flowNo
	 * @param workId
	 * @param nodeFrom
	 * @return
	 */
	public static String GetCheckInfo(String flowNo, long workId, int nodeFrom) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";
		String sql = "SELECT Msg FROM " + table + " WHERE NDFrom=" + nodeFrom + " AND ActionType="
				+ ActionType.WorkCheck.getValue() + " AND EmpFrom='" + WebUser.getNo() + "' AND WorkID=" + workId
				+ " ORDER BY RDT DESC ";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			// BP.Sys.FrmWorkCheck fwc = new FrmWorkCheck(nodeFrom);
			// return fwc.FWCDefInfo;
			return null;
		}
		String checkinfo = dt.Rows.get(0).getValue(0).toString();
		return checkinfo;
	}

	/**
	 * 获取队列节点Track 表中的审核的信息(队列节点中处理人 共享同一处理意见)
	 * 
	 * @param flowNo
	 * @param workId
	 * @param nodeFrom
	 * @return
	 */
	public static String GetOrderCheckInfo(String flowNo, long workId, int nodeFrom) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";
		String sql = "SELECT Msg FROM " + table + " WHERE NDFrom=" + nodeFrom + " AND ActionType="
				+ ActionType.WorkCheck.getValue() + " AND WorkID=" + workId + " ORDER BY RDT DESC ";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			// BP.Sys.FrmWorkCheck fwc = new FrmWorkCheck(nodeFrom);
			// return fwc.FWCDefInfo;
			return null;
		}
		String checkinfo = dt.Rows.get(0).getValue(0).toString();
		return checkinfo;
	}

	/**
	 * 删除审核信息,用于退回后.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workId
	 *            工作ID
	 * @param nodeFrom
	 *            节点从
	 * @return
	 */
	public static void DeleteCheckInfo(String flowNo, long workId, int nodeFrom) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";
		String sql = "DELETE FROM " + table + " WHERE NDFrom=" + nodeFrom + " AND ActionType="
				+ ActionType.WorkCheck.getValue() + " AND EmpFrom='" + WebUser.getNo() + "' AND WorkID=" + workId;
		BP.DA.DBAccess.RunSQL(sql);
	}

	public static String GetAskForHelpReInfo(String flowNo, long workId, int nodeFrom) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";
		String sql = "SELECT Msg FROM " + table + " WHERE NDFrom=" + nodeFrom + " AND ActionType="
				+ ActionType.AskforHelp.getValue() + " AND EmpFrom='" + WebUser.getNo() + "' AND WorkID=" + workId
				+ " ORDER BY RDT DESC ";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			return "";
		}
		String checkinfo = dt.Rows.get(0).getValue(0).toString();
		return checkinfo;
	}

	/**
	 * 更新Track信息
	 * 
	 * @param flowNo
	 * @param workId
	 * @param nodeFrom
	 * @param actionType
	 * @param strMsg
	 * @return
	 */
	public static void SetTrackInfo(String flowNo, long workId, int nodeFrom, int actionType, String strMsg) {
		String table = "ND" + Integer.parseInt(flowNo) + "Track";

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE " + table + " SET Msg=" + dbstr + "Msg  WHERE ActionType=" + dbstr + "ActionType and WorkID="
				+ dbstr + "WorkID and NDFrom=" + dbstr + "NDFrom";
		ps.Add("Msg", strMsg);
		ps.Add("ActionType", actionType);
		ps.Add("WorkID", workId);
		ps.Add("NDFrom", nodeFrom);
		BP.DA.DBAccess.RunSQL(ps);
	}

	/**
	 * 设置BillNo信息
	 * 
	 * @param flowNo
	 * @param workID
	 * @param newBillNo
	 */
	public static void SetBillNo(String flowNo, long workID, String newBillNo) {
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET BillNo=" + dbstr + "BillNo  WHERE WorkID=" + dbstr + "WorkID";
		ps.Add("BillNo", newBillNo);
		ps.Add("WorkID", workID);
		BP.DA.DBAccess.RunSQL(ps);

		Flow fl = new Flow(flowNo);
		ps = new Paras();
		ps.SQL = "UPDATE " + fl.getPTable() + " SET BillNo=" + dbstr + "BillNo WHERE OID=" + dbstr + "OID";
		ps.Add("BillNo", newBillNo);
		ps.Add("OID", workID);
		BP.DA.DBAccess.RunSQL(ps);
	}

	/**
	 * 设置父流程信息
	 * 
	 * @param subFlowNo
	 *            子流程编号
	 * @param subFlowWorkID
	 *            子流程workid
	 * @param parentFlowNo
	 *            父流程编号
	 * @param parentWorkID
	 *            父流程WorkID
	 * @param parentNodeID
	 *            调用子流程的节点ID
	 * @param parentEmpNo
	 *            调用人
	 */
	public static void SetParentInfo(String subFlowNo, long subFlowWorkID, String parentFlowNo, long parentWorkID,
			int parentNodeID, String parentEmpNo) {
		if (parentWorkID == 0) {
			throw new RuntimeException("@设置的父流程的流程编号为0，这是非法的。");
		}

		if (StringHelper.isNullOrEmpty(parentEmpNo)) {
			parentEmpNo = WebUser.getNo();
		}

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET PFlowNo=" + dbstr + "PFlowNo, PWorkID=" + dbstr + "PWorkID,PNodeID="
				+ dbstr + "PNodeID,PEmp=" + dbstr + "PEmp WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.PFlowNo, parentFlowNo);
		ps.Add(GenerWorkFlowAttr.PWorkID, parentWorkID);
		ps.Add(GenerWorkFlowAttr.PNodeID, parentNodeID);
		ps.Add(GenerWorkFlowAttr.PEmp, parentEmpNo);
		ps.Add(GenerWorkFlowAttr.WorkID, subFlowWorkID);

		BP.DA.DBAccess.RunSQL(ps);

		Flow fl = new Flow(subFlowNo);
		ps = new Paras();
		ps.SQL = "UPDATE " + fl.getPTable() + " SET PFlowNo=" + dbstr + "PFlowNo, PWorkID=" + dbstr + "PWorkID,PNodeID="
				+ dbstr + "PNodeID, PEmp=" + dbstr + "PEmp WHERE OID=" + dbstr + "OID";
		ps.Add(NDXRptBaseAttr.PFlowNo, parentFlowNo);
		ps.Add(NDXRptBaseAttr.PWorkID, parentWorkID);
		ps.Add(NDXRptBaseAttr.PNodeID, parentNodeID);
		ps.Add(NDXRptBaseAttr.PEmp, parentEmpNo);

		ps.Add(NDXRptBaseAttr.OID, subFlowWorkID);
		BP.DA.DBAccess.RunSQL(ps);
	}

	public static GERpt Flow_GenerGERpt(String flowNo, long workID) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		GERpt rpt = new GERpt("ND" + Integer.parseInt(flowNo) + "Rpt", workID);
		return rpt;
	}

	/**
	 * 产生一个新的工作ID
	 * 
	 * @param flowNo
	 *            流程编号
	 * @return 返回当前操作员创建的工作ID
	 */
	public static long Flow_GenerWorkID(String flowNo) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		Flow fl = new Flow(flowNo);
		return fl.NewWork().getOID();
	}

	/**
	 * 产生一个新的工作
	 * 
	 * @param flowNo
	 *            流程编号
	 * @return 返回当前操作员创建的工作
	 */
	public static Work Flow_GenerWork(String flowNo) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		Flow fl = new Flow(flowNo);
		Work wk = fl.NewWork();
		wk.ResetDefaultVal();
		return wk;
	}

	/**
	 * 把流程从非正常运行状态恢复到正常运行状态 比如现在的流程的状态是，删除，挂起，现在恢复成正常运行。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param msg
	 *            原因
	 * @return 执行信息
	 */
	public static void Flow_DoComeBackWorkFlow(String flowNo, long workID, String msg) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		WorkFlow wf = new WorkFlow(flowNo, workID);
		wf.DoComeBackWorkFlow(msg);
	}

	/**
	 * 恢复已完成的流程数据到指定的节点，如果节点为0就恢复到最后一个完成的节点上去. 恢复失败抛出异常
	 * 
	 * @param flowNo
	 *            要恢复的流程编号
	 * @param workid
	 *            要恢复的workid
	 * @param backToNodeID
	 *            恢复到的节点编号，如果是0，标示回复到流程最后一个节点上去.
	 * @param note
	 *            恢复的原因，此原因会记录到日志.
	 */
	public static String Flow_DoRebackWorkFlow(String flowNo, long workid, int backToNodeID, String note) {
		BP.WF.Template.FlowSheet fs = new BP.WF.Template.FlowSheet(flowNo);
		return fs.DoRebackFlowData(workid, backToNodeID, note);
	}

	/**
	 * 执行删除流程:彻底的删除流程. 清除的内容如下: 1, 流程引擎中的数据. 2, 节点数据,NDxxRpt数据. 3, 轨迹表数据.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param isDelSubFlow
	 *            是否要删除它的子流程
	 * @return 执行信息
	 */
	public static String Flow_DoDeleteFlowByReal(String flowNo, long workID, boolean isDelSubFlow) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);
		try {
			WorkFlow.DeleteFlowByReal(flowNo, workID, isDelSubFlow);
			// WorkFlow wf = new WorkFlow(flowNo, workID);
			// wf.DoDeleteWorkFlowByReal(isDelSubFlow);
		} catch (RuntimeException ex) {
			throw new RuntimeException("@删除前错误，" + ex.getStackTrace());
		}
		return "删除成功";
	}

	public static String Flow_DoDeleteDraft(String flowNo, long workID, boolean isDelSubFlow) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "DELETE WF_GenerWorkFlow WHERE WorkID=" + dbstr + "WorkID";
		ps.Add("WorkID", workID);
		BP.DA.DBAccess.RunSQL(ps);

		Flow fl = new Flow(flowNo);
		ps = new Paras();
		ps.SQL = "DELETE " + fl.getPTable() + " WHERE OID=" + dbstr + "OID";
		ps.Add("OID", workID);
		BP.DA.DBAccess.RunSQL(ps);

		// 删除开始节点数据.
		Node nd = fl.getHisStartNode();
		Work wk = nd.getHisWork();
		ps = new Paras();
		ps.SQL = "DELETE " + wk.getEnMap().getPhysicsTable() + " WHERE OID=" + dbstr + "OID";
		ps.Add("OID", workID);
		BP.DA.DBAccess.RunSQL(ps);

		BP.DA.Log.DefaultLogWriteLineInfo(WebUser.getName() + "删除了FlowNo 为'" + flowNo + "',workID为'" + workID + "'的数据");

		return "删除成功";
	}

	/**
	 * 删除已经完成的流程 注意:它不触发事件.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param isDelSubFlow
	 *            是否删除子流程
	 * @param note
	 *            删除原因
	 * @return 删除过程信息
	 */
	public static String Flow_DoDeleteWorkFlowAlreadyComplete(String flowNo, long workID, boolean isDelSubFlow,
			String note) {
		return WorkFlow.DoDeleteWorkFlowAlreadyComplete(flowNo, workID, isDelSubFlow, note);
	}

	/**
	 * 删除流程并写入日志 清除的内容如下: 1, 流程引擎中的数据. 2, 节点数据,NDxxRpt数据. 并作如下处理: 1, 保留track数据. 2,
	 * 写入流程删除记录表.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param deleteNote
	 *            删除原因
	 * @param isDelSubFlow
	 *            是否要删除它的子流程
	 * @return 执行信息
	 */
	public static String Flow_DoDeleteFlowByWriteLog(String flowNo, long workID, String deleteNote,
			boolean isDelSubFlow) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);
		WorkFlow wf = new WorkFlow(flowNo, workID);
		return wf.DoDeleteWorkFlowByWriteLog(deleteNote, isDelSubFlow);
	}

	/**
	 * 执行逻辑删除流程:此流程并非真正的删除仅做了流程删除标记 比如:逻辑删除工单.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param msg
	 *            逻辑删除的原因
	 * @param isDelSubFlow
	 *            逻辑删除的原因
	 * @return 执行信息,执行不成功抛出异常.
	 */
	public static String Flow_DoDeleteFlowByFlag(String flowNo, long workID, String msg, boolean isDelSubFlow) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		WorkFlow wf = new WorkFlow(flowNo, workID);
		wf.DoDeleteWorkFlowByFlag(msg);
		if (isDelSubFlow) {
			GenerWorkFlows gwfs = new GenerWorkFlows();
			gwfs.Retrieve(GenerWorkFlowAttr.PWorkID, workID);
			for (GenerWorkFlow item : GenerWorkFlows.convertGenerWorkFlows(gwfs)) {
				Flow_DoDeleteFlowByFlag(item.getFK_Flow(), item.getWorkID(), "删除子流程:" + msg, false);
			}
		}
		return "删除成功";
	}

	/**
	 * 撤销删除流程 说明:如果一个流程处于逻辑删除状态,要回复正常运行状态,就执行此接口.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作流程ID
	 * @param msg
	 *            撤销删除的原因
	 * @return 执行消息,如果撤销不成功则抛出异常.
	 */
	public static String Flow_DoUnDeleteFlowByFlag(String flowNo, long workID, String msg) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		WorkFlow wf = new WorkFlow(flowNo, workID);
		wf.DoUnDeleteWorkFlowByFlag(msg);
		return "撤销删除成功.";
	}

	/**
	 * 执行-撤销发送 说明:如果流程转入了下一个节点,就会执行失败,就会抛出异常.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @return 返回成功执行信息
	 */
	public static String Flow_DoUnSend(String flowNo, long workID) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		WorkUnSend unSend = new WorkUnSend(flowNo, workID);
		return unSend.DoUnSend();
	}

	/**
	 * 执行冻结
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            workid
	 * @param msg
	 *            冻结原因
	 */
	public static String Flow_DoFix(String flowNo, long workid, String msg) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		// 执行冻结.
		WorkFlow wf = new WorkFlow(flowNo, workid);
		return wf.DoFix(msg);
	}

	/**
	 * 执行解除冻结 于挂起的区别是,冻结需要有权限的人才可以执行解除冻结， 挂起是自己的工作可以挂起也可以解除挂起。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            workid
	 * @param msg
	 *            解除原因
	 */
	public static String Flow_DoUnFix(String flowNo, long workid, String msg) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		// 执行冻结.
		WorkFlow wf = new WorkFlow(flowNo, workid);
		return wf.DoUnFix(msg);
	}

	/**
	 * 执行流程结束 说明:正常的流程结束.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param msg
	 *            流程结束原因
	 * @return 返回成功执行信息
	 */
	public static String Flow_DoFlowOver(String flowNo, long workID, String msg) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		WorkFlow wf = new WorkFlow(flowNo, workID);
		return wf.DoFlowOver(ActionType.FlowOver, msg, null, null);
	}

	/**
	 * 执行流程结束:强制的流程结束.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param flowNo
	 *            当前节点编号
	 * @param workID
	 *            工作ID
	 * @param fid
	 *            工作ID
	 * @param msg
	 *            强制流程结束的原因
	 * @return 执行强制结束流程
	 */
	public static String Flow_DoFlowOverByCoercion(String flowNo, int nodeid, long workID, long fid, String msg) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);
		WorkFlow wf = new WorkFlow(flowNo, workID);

		Node currND = new Node(nodeid);

		Flow fl = new Flow(flowNo);
		GERpt rpt = fl.getHisGERpt();
		rpt.setOID(workID);
		rpt.RetrieveFromDBSources();

		String s = wf.DoFlowOver(ActionType.FlowOverByCoercion, msg, currND, rpt);
		if (StringHelper.isNullOrEmpty(s)) {
			s = "流程已经成功结束.";
		}
		return s;
	}

	/**
	 * 执行流程结束:强制的流程结束.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param flowNo
	 *            当前节点编号
	 * @param workID
	 *            工作ID
	 * @param fid
	 *            工作ID
	 * @param state
	 *            流程状态
	 * @return 执行强制结束流程
	 */
	public static void Flow_DoFlowOverByCoercion(String flowNo, int nodeid, long workID, long fid, WFState state) {
		// 转化成编号.
		WorkFlow wf = new WorkFlow(flowNo, workID);

		Node currNode = new Node(nodeid);

		// 处理明细数据的copy问题。 首先检查：当前节点（最后节点）是否有明细表。
		MapDtls dtls = currNode.getMapData().getMapDtls(); // new MapDtls("ND" +
		int i = 0;
		for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
			i++;
			// 查询出该明细表中的数据。
			GEDtls dtlDatas = new GEDtls(dtl.getNo());
			dtlDatas.Retrieve(GEDtlAttr.RefPK, wf.getWorkID());

			GEDtl geDtl = null;
			try {
				// 创建一个Rpt对象。
				geDtl = new GEDtl(
						"ND" + Integer.parseInt(wf.getHisFlow().getNo()) + "RptDtl" + (new Integer(i)).toString());
				geDtl.ResetDefaultVal();
			} catch (java.lang.Exception e) {
				/// #warning 此处需要修复。
				continue;
			}
		}
		wf._IsComplete = 1;
		/// #endregion 处理明细表的汇总.

		/// #region 处理后续的业务.

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "DELETE FROM WF_GenerFH WHERE FID=" + dbstr + "FID";
		ps.Add(GenerFHAttr.FID, wf.getWorkID());
		DBAccess.RunSQL(ps);

		// 求出参与人,以方便已经完成的工作查询.
		ps = new Paras();
		ps.SQL = "SELECT EmpFrom FROM ND" + Integer.parseInt(wf.getHisFlow().getNo()) + "Track WHERE WorkID=" + dbstr
				+ "WorkID OR FID=" + dbstr + "FID ";
		ps.Add("WorkID", wf.getWorkID());
		ps.Add("FID", wf.getWorkID());
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
		ps.Add("WFState", state.getValue());
		ps.Add("WFSta", WFSta.Complete.getValue());
		ps.Add("Emps", emps);
		ps.Add("WorkID", wf.getWorkID());
		DBAccess.RunSQL(ps);

		// 清除工作者.
		ps = new Paras();
		ps.SQL = "DELETE FROM WF_GenerWorkerlist WHERE WorkID=" + dbstr + "WorkID1 OR FID=" + dbstr + "WorkID2 ";
		ps.Add("WorkID1", wf.getWorkID());
		ps.Add("WorkID2", wf.getWorkID());
		DBAccess.RunSQL(ps);

		// 设置流程完成状态.
		ps = new Paras();
		ps.SQL = "UPDATE " + wf.getHisFlow().getPTable() + " SET WFState=" + dbstr + "WFState WHERE OID=" + dbstr
				+ "OID";
		ps.Add("WFState", WFState.Complete.getValue());
		ps.Add("OID", wf.getWorkID());
		DBAccess.RunSQL(ps);

		// 加入轨迹.
		WorkNode wn = new WorkNode(wf.getWorkID(), wf.getHisGenerWorkFlow().getFK_Node());
		wn.AddToTrack(ActionType.FlowOverByCoercion, WebUser.getNo(), WebUser.getName(), wn.getHisNode().getNodeID(),
				wn.getHisNode().getName(), "");

		/// #endregion 处理后续的业务.

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
		ps.Add(TrackAttr.WorkID, wf.getWorkID());
		BP.DA.DBAccess.RunSQL(ps);
	}

	/**
	 * 获得执行下一步骤的节点ID，这个功能是在流程未发送前可以预先知道 它就要到达那一个节点上去,以方便在当前节点发送前处理业务逻辑.
	 * 1,首先保证当前人员是可以执行当前节点的工作. 2,其次保证获取下一个节点只有一个.
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @return 下一步骤的所要到达的节点, 如果获取不到就会抛出异常.
	 */
	public static int Node_GetNextStepNode(String fk_flow, long workid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		//// 检查当前人员是否可以执行当前工作.
		// if (BP.WF.Dev2Interface.Flow_CheckIsCanDoCurrentWork( workid,
		//// WebUser.No) == false)
		// throw new Exception("@当前人员不能执行此节点上的工作.");

		// 获取当前nodeID.
		int currNodeID = BP.WF.Dev2Interface.Node_GetCurrentNodeID(fk_flow, workid);

		// 获取
		Node nd = new Node(currNodeID);
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		wk.Retrieve();

		WorkNode wn = new WorkNode(wk, nd);
		return wn.NodeSend_GenerNextStepNode().getNodeID();
	}

	/**
	 * 获取指定的workid 在运行到的节点编号
	 * 
	 * @param workID
	 *            需要找到的workid
	 * @return 返回节点编号. 如果没有找到，就会抛出异常.
	 */
	public static int Flow_GetCurrentNode(long workID) {
		Paras ps = new Paras();
		ps.SQL = "SELECT FK_Node FROM WF_GenerWorkFlow WHERE WorkID=" + SystemConfig.getAppCenterDBVarStr() + "WorkID";
		ps.Add("WorkID", workID);
		return BP.DA.DBAccess.RunSQLReturnValInt(ps);
	}

	/**
	 * 获取指定节点的Work
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @return 当前工作
	 */
	public static Work Flow_GetCurrentWork(int nodeID, long workID) {
		Node nd = new Node(nodeID);
		Work wk = nd.getHisWork();
		wk.setOID(workID);
		wk.Retrieve();
		return wk;
	}

	/**
	 * 获取当前工作节点的Work
	 * 
	 * @param workID
	 *            工作ID
	 * @return 当前工作节点的Work
	 */
	public static Work Flow_GetCurrentWork(long workID) {
		Node nd = new Node(Flow_GetCurrentNode(workID));
		Work wk = nd.getHisWork();
		wk.setOID(workID);
		wk.Retrieve();
		wk.ResetDefaultVal();
		return wk;
	}

	/**
	 * 指定 workid 当前节点由哪些人可以执行.
	 * 
	 * @param workID
	 *            需要找到的workid
	 * @return 返回当前处理人员列表,数据结构与WF_GenerWorkerList一致.
	 */
	public static DataTable Flow_GetWorkerList(long workID) {
		Paras ps = new Paras();
		ps.SQL = "SELECT * FROM WF_GenerWorkerList WHERE IsEnable=1 AND IsPass=0 AND WorkID="
				+ SystemConfig.getAppCenterDBVarStr() + "WorkID";
		ps.Add("WorkID", workID);
		return BP.DA.DBAccess.RunSQLReturnTable(ps);
	}

	/**
	 * 检查是否可以发起流程
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param userNo
	 *            用户编号
	 * @return 是否可以发起当前流程
	 */
	public static boolean Flow_IsCanStartThisFlow(String flowNo, String userNo) {
		Node nd = new Node(Integer.parseInt(flowNo + "01"));
		Paras ps = new Paras();
		String dbstr = SystemConfig.getAppCenterDBVarStr();
		int num = 0;
		switch (nd.getHisDeliveryWay()) {
		case ByStation:
			ps.SQL = "SELECT COUNT(A.FK_Node) as Num FROM WF_NodeStation A, " + BP.WF.Glo.getEmpStation()
					+ " B WHERE A.FK_Station= B.FK_Station AND  A.FK_Node=" + dbstr + "FK_Node AND B.FK_Emp=" + dbstr
					+ "FK_Emp";
			ps.Add("FK_Node", nd.getNodeID());
			ps.Add("FK_Emp", userNo);
			num = DBAccess.RunSQLReturnValInt(ps);
			break;
		case ByDept:
			ps.SQL = "SELECT COUNT(A.FK_Node) as Num FROM WF_NodeDept A, Port_EmpDept B WHERE A.FK_Dept= B.FK_Dept AND  A.FK_Node="
					+ dbstr + "FK_Node AND B.FK_Emp=" + dbstr + "FK_Emp";
			ps.Add("FK_Node", nd.getNodeID());
			ps.Add("FK_Emp", userNo);
			num = DBAccess.RunSQLReturnValInt(ps);
			break;
		case ByBindEmp:
			ps.SQL = "SELECT COUNT(*) AS Num FROM WF_NodeEmp WHERE FK_Emp=" + dbstr + "FK_Emp AND FK_Node=" + dbstr
					+ "FK_Node";
			ps.Add("FK_Emp", userNo);
			ps.Add("FK_Node", nd.getNodeID());
			num = DBAccess.RunSQLReturnValInt(ps);
			break;
		default:
			throw new RuntimeException("@开始节点不允许设置此访问规则：" + nd.getHisDeliveryWay());
		}
		if (num == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 获得正在运行中的子流程的数量
	 * 
	 * @param workID
	 *            父流程的workid
	 * @return 获得正在运行中的子流程的数量。如果是0，表示所有的流程的子流程都已经结束。
	 */
	public static int Flow_NumOfSubFlowRuning(long pWorkID) {
		String sql = "SELECT COUNT(*) AS num FROM WF_GenerWorkFlow WHERE WFState!=" + WFState.Complete.getValue()
				+ " AND PWorkID=" + pWorkID;
		return DBAccess.RunSQLReturnValInt(sql);
	}

	/**
	 * 获得正在运行中的子流程的数量
	 * 
	 * @param pWorkID
	 *            父流程的workid
	 * @param currWorkID
	 *            不包含当前的工作节点ID
	 * @param workID
	 *            父流程的workid
	 * @return 获得正在运行中的子流程的数量。如果是0，表示所有的流程的子流程都已经结束。
	 */
	public static int Flow_NumOfSubFlowRuning(long pWorkID, long currWorkID) {
		String sql = "SELECT COUNT(*) AS num FROM WF_GenerWorkFlow WHERE WFState!=" + WFState.Complete.getValue()
				+ " AND WorkID!=" + currWorkID + " AND PWorkID=" + pWorkID;
		return DBAccess.RunSQLReturnValInt(sql);
	}

	public static boolean Flow_IsInGenerWork(long workID) {

		if (workID == 0) {
			return false;
		}

		String sql = "select * from WF_Generworkflow where WorkID='" + workID + "'";

		return DBAccess.RunSQLReturnCOUNT(sql) > 0;
	}

	/**
	 * 是否可以处理当前工作
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param userNo
	 *            用户编号
	 * @return 是否可以处理当前工作
	 */
	public static boolean Flow_IsCanDoCurrentWork(String fk_flow, long workID, String userNo) {
		try {
			GenerWorkFlow gwf = new GenerWorkFlow(workID);
			return Flow_IsCanDoCurrentWork(fk_flow, gwf.getFK_Node(), workID, userNo);
		} catch (java.lang.Exception e) {
			return false;
		}
	}

	public static boolean Flow_IsCanDoCurrentWork(int nodeID, long workID, String userNo) {
		Node nd = new Node(nodeID);
		return Flow_IsCanDoCurrentWork(nd.getFK_Flow(), nodeID, workID, userNo);
	}

	/**
	 * 检查当前人员是否有权限处理当前的工作.
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @param userNo
	 *            要判断的操作人员
	 * @return 返回指定的人员是否有操作当前工作的权限
	 */
	public static boolean Flow_IsCanDoCurrentWork(String fk_flow, int nodeID, long workID, String userNo) {
		if (workID == 0) {
			return true;
		}

		if (userNo.equals(SystemConfig.getAppSettings().get("Admin").toString())) {
			return true;
		}

		// C# TO JAVA CONVERTER
		/// #region 判断是否是开始节点.
		// 判断是否是开始节点 .
		String str = (new Integer(nodeID)).toString();
		int len = str.length() - 2;
		if (str.substring(len, len + 2).equals("01")) {
			return true;
		}
		// C# TO JAVA CONVERTER
		/// #endregion 判断是否是开始节点.

		String sql = "SELECT c.RunModel, a.TaskSta, a.WFState, IsPass FROM WF_GenerWorkFlow a , WF_GenerWorkerlist b, WF_Node c WHERE a.FK_Node='"
				+ nodeID + "'  AND b.FK_Node=c.NodeID AND a.WorkID=b.WorkID AND a.FK_Node=b.FK_Node  AND b.FK_Emp='"
				+ userNo + "' AND b.IsEnable=1 AND a.workid='" + workID + "'";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			return false;
		}
		// 检查流程状态是否已经结束
		WFState wfsta = WFState.forValue(Integer.parseInt(dt.Rows.get(0).getValue(2).toString()));
		if (wfsta == WFState.Complete) {
			return false;
		}
		if (wfsta == WFState.Delete) {
			return false;
		}

		// 判断是否是待办.
		DataRowCollection rows = dt.Rows;
		boolean canDeal = false;
		for (DataRow row : rows) {
			int isPass = Integer.parseInt(row.getValue("IsPass").toString());
			// 如果当前环节有多个待办，只要有一个可以处理即可
			if (isPass == 0) {
				canDeal = true;
				break;
			}
		}
		if (!canDeal) {
			return false;
		}

		int i = Integer.parseInt(dt.Rows.get(0).getValue(0).toString());

		RunModel rm = RunModel.forValue(i);
		switch (rm) {
		case Ordinary:
			return true;
		case FL:
			return true;
		case HL:
			return true;
		case FHL:
			return true;
		case SubThread:
			return true;
		default:
			break;
		}
		return true;
	}

	/**
	 * 检查当前人员是否有权限处理当前的工作.
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @param userNo
	 *            要判断的操作人员
	 * @return 返回指定的人员是否有操作当前工作的权限
	 */
	public static boolean Flow_IsCanDoCurrentWorkGuest(int nodeID, long workID, String userNo) {
		if (workID == 0) {
			return true;
		}

		if (userNo.equals(SystemConfig.getAppSettings().get("Admin").toString())) {
			return true;
		}

		String dbstr = SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		// ps.SQL = "SELECT c.RunModel FROM WF_GenerWorkFlow a ,
		// WF_GenerWorkerlist b, WF_Node c WHERE a.FK_Node=" + dbstr + "FK_Node
		// AND b.FK_Node=c.NodeID AND a.WorkID=b.WorkID AND a.FK_Node=b.FK_Node
		// AND b.FK_Emp=" + dbstr + "FK_Emp AND b.IsEnable=1 AND a.workid=" +
		// dbstr + "WorkID";
		// ps.Add("FK_Node", nodeID);
		// ps.Add("FK_Emp", userNo);
		// ps.Add("WorkID", workID);
		String sql = "SELECT c.RunModel, a.TaskSta FROM WF_GenerWorkFlow a , WF_GenerWorkerlist b, WF_Node c WHERE a.FK_Node='"
				+ nodeID + "'  AND b.FK_Node=c.NodeID AND a.WorkID=b.WorkID AND a.FK_Node=b.FK_Node  AND b.GuestNo='"
				+ userNo + "' AND b.IsEnable=1 AND a.workid='" + workID + "'";

		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() == 0) {
			return false;
		}

		int i = Integer.parseInt(dt.Rows.get(0).getValue(0).toString());
		TaskSta TaskStai = TaskSta.forValue(Integer.parseInt(dt.Rows.get(0).getValue(1).toString()));
		if (TaskStai == TaskSta.Sharing) {
			return false;
		}

		RunModel rm = RunModel.forValue(i);
		switch (rm) {
		case Ordinary:
			return true;
		case FL:
			return true;
		case HL:
			return true;
		case FHL:
			return true;
		case SubThread:
			return true;
		default:
			break;
		}

		if (DBAccess.RunSQLReturnValInt(ps) == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 是否可以查看流程数据 用于判断是否可以查看流程轨迹图. edit: stone 2015-03-25
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            FID
	 * @return
	 */
	public static boolean Flow_IsCanViewTruck(String flowNo, long workid, long fid) {
		if (WebUser.getNo().equals(SystemConfig.getAppSettings().get("Admin").toString())) {
			return true;
		}

		// 先从轨迹里判断.
		String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "SELECT count(MyPK) as Num FROM ND" + Integer.parseInt(flowNo) + "Track WHERE (WorkID=" + dbStr
				+ "WorkID OR FID=" + dbStr + "FID) AND (EmpFrom=" + dbStr + "Emp1 OR EmpTo=" + dbStr + "Emp2)";
		ps.Add(TrackAttr.WorkID, workid);
		ps.Add(TrackAttr.FID, workid);
		ps.Add("Emp1", WebUser.getNo());
		ps.Add("Emp2", WebUser.getNo());

		if (BP.DA.DBAccess.RunSQLReturnValInt(ps) > 1) {
			return true;
		}

		// 在查看该流程的发起者，与当前人是否在同一个部门，如果是也返回true.
		ps = new Paras();
		ps.SQL = "SELECT FK_Dept FROM WF_GenerWorkFlow WHERE WorkID=" + dbStr + "WorkID OR WorkID=" + dbStr + "FID";
		ps.Add(TrackAttr.WorkID, workid);
		ps.Add(TrackAttr.FID, workid);

		String fk_dept = null;
		try {
			fk_dept = DBAccess.RunSQLReturnStringIsNull(ps, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (fk_dept == null) {
			Flow fl = new Flow(flowNo);
			ps.SQL = "SELECT FK_Dept FROM " + fl.getPTable() + " WHERE OID=" + dbStr + "WorkID OR OID=" + dbStr + "FID";
			try {
				fk_dept = DBAccess.RunSQLReturnStringIsNull(ps, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fk_dept == null) {
				throw new RuntimeException("@流程引擎数据被删除.");
			}
		}
		if (fk_dept.equals(WebUser.getFK_Dept())) {
			return true;
		}

		return false;
	}

	/**
	 * 删除子线程
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            子线程的工作ID
	 * @param info
	 *            删除信息
	 */
	public static void Flow_DeleteSubThread(String flowNo, long workid, String info) {
		GenerWorkFlow gwf = new GenerWorkFlow(workid);

		WorkFlow wf = new WorkFlow(flowNo, workid);
		wf.DoDeleteWorkFlowByReal(false);
		BP.WF.Dev2Interface.WriteTrackInfo(flowNo, gwf.getFK_Node(), gwf.getFID(), 0, info, "删除子线程");
	}

	/**
	 * 执行工作催办
	 * 
	 * @param workID
	 *            工作ID
	 * @param msg
	 *            催办消息
	 * @param isPressSubFlow
	 *            是否催办子流程？
	 * @return 返回执行结果
	 */
	public static String Flow_DoPress(long workID, String msg, boolean isPressSubFlow) {
		GenerWorkFlow gwf = new GenerWorkFlow(workID);

		// 找到当前待办的工作人员
		GenerWorkerLists wls = new GenerWorkerLists(workID, gwf.getFK_Node());
		String toEmp = "", toEmpName = "";
		String mailTitle = "催办:" + gwf.getTitle() + ", 发送人:" + WebUser.getName();
		// 如果子线程找不到流转日志并且父流程编号不为空，在父流程进行查找接收人
		if (wls.size() == 0 && gwf.getFID() != 0) {
			wls = new GenerWorkerLists(gwf.getFID(), gwf.getFK_Node());
		}

		for (GenerWorkerList wl : GenerWorkerLists.convertGenerWorkerLists(wls)) {
			if (wl.getIsEnable() == false) {
				continue;
			}

			toEmp += wl.getFK_Emp() + ",";
			toEmpName += wl.getFK_EmpText() + ",";

			// 发消息.
			BP.WF.Dev2Interface.Port_SendMsg(wl.getFK_Emp(), mailTitle, msg, null, SMSMsgType.Self, gwf.getFK_Flow(),
					gwf.getFK_Node(), gwf.getWorkID(), gwf.getFID());

			wl.setPressTimes(wl.getPressTimes() + 1);
			wl.Update();

			// wl.Update(GenerWorkerListAttr.PressTimes, wl.PressTimes + 1);
		}

		// 写入日志.
		WorkNode wn = new WorkNode(workID, gwf.getFK_Node());
		wn.AddToTrack(ActionType.Press, toEmp, toEmpName, gwf.getFK_Node(), gwf.getNodeName(), msg);

		// 如果催办子流程.
		if (isPressSubFlow) {
			String subMsg = "";
			GenerWorkFlows gwfs = gwf.getHisSubFlowGenerWorkFlows();
			for (GenerWorkFlow item : GenerWorkFlows.convertGenerWorkFlows(gwfs)) {
				subMsg += "@已经启动对子线程:" + item.getTitle() + "的催办,消息如下:";
				subMsg += Flow_DoPress(item.getWorkID(), msg, false);
			}
			return "系统已经把您的信息通知给:" + toEmpName + "" + subMsg;
		} else {
			return "系统已经把您的信息通知给:" + toEmpName;
		}
	}

	/**
	 * 重新设置流程标题 可以在节点的任何位置调用它,产生新的标题。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @return 是否设置成功
	 */
	public static boolean Flow_ReSetFlowTitle(String flowNo, int nodeID, long workid) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		Node nd = new Node(nodeID);
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		wk.RetrieveFromDBSources();
		Flow fl = nd.getHisFlow();
		String title = BP.WF.WorkNode.GenerTitle(fl, wk);
		return Flow_SetFlowTitle(flowNo, workid, title);
	}

	/**
	 * 设置流程参数
	 * 
	 * @param workid
	 *            工作ID
	 * @param paras
	 *            参数,格式：@GroupMark=xxxx@IsCC=1
	 * @return 是否设置成功
	 */
	public static boolean Flow_SetFlowParas(String flowNo, long workid, String paras) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workid);
		if (gwf.RetrieveFromDBSources() == 0) {
			throw new RuntimeException("创建流程ID不存在.");
		}

		String[] strs = paras.split("[@]", -1);
		for (String item : strs) {
			if (StringHelper.isNullOrEmpty(item)) {
				continue;
			}

			// GroupMark=xxxx
			String[] mystr = item.split("[=]", -1);
			gwf.SetPara(mystr[0], mystr[1]);
		}
		gwf.Update();
		return true;
	}

	/**
	 * 设置流程标题
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param title
	 *            标题
	 * @return 是否设置成功
	 */
	public static boolean Flow_SetFlowTitle(String flowNo, long workid, String title) {
		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		String dbstr = SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkFlow SET Title=" + dbstr + "Title WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.Title, title);
		ps.Add(GenerWorkFlowAttr.WorkID, workid);
		DBAccess.RunSQL(ps);

		Flow fl = new Flow(flowNo);
		ps = new Paras();
		ps.SQL = "UPDATE " + fl.getPTable() + " SET Title=" + dbstr + "Title WHERE OID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.Title, title);
		ps.Add(GenerWorkFlowAttr.WorkID, workid);
		int num = DBAccess.RunSQL(ps);

		if (fl.getHisDataStoreModel() == DataStoreModel.ByCCFlow) {
			ps = new Paras();
			ps.SQL = "UPDATE ND" + Integer.parseInt(flowNo + "01") + " SET Title=" + dbstr + "Title WHERE OID=" + dbstr
					+ "WorkID";
			ps.Add(GenerWorkFlowAttr.Title, title);
			ps.Add(GenerWorkFlowAttr.WorkID, workid);
			DBAccess.RunSQL(ps);
		}

		if (num == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 调度流程 说明： 1，通常是由admin执行的调度。 2，特殊情况下，需要从一个人的待办调度到另外指定的节点，制定的人员上。
	 * 
	 * @param workid
	 *            工作ID
	 * @param toNodeID
	 *            调度到节点
	 * @param toEmper
	 *            调度到人员
	 */
	public static String Flow_Schedule(long workid, int toNodeID, String toEmper) {
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();

		Node nd = new Node(toNodeID);
		Emp emp = new Emp(toEmper);

		// 找到GenerWorkFlow,并执行更新.
		GenerWorkFlow gwf = new GenerWorkFlow(workid);
		gwf.setWFState(WFState.Runing);
		gwf.setTaskSta(TaskSta.None);
		gwf.setTodoEmps(toEmper);
		gwf.setFK_Node(toNodeID);
		gwf.setNodeName(nd.getName());
		// gwf.StarterName =emp.Name;
		gwf.Update();

		// 让其都设置完成。
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkerList SET IsPass=1 WHERE WorkID=" + dbstr + "WorkID";
		ps.Add(GenerWorkFlowAttr.WorkID, workid);
		BP.DA.DBAccess.RunSQL(ps);

		// 更新流程数据信息。
		Flow fl = new Flow(gwf.getFK_Flow());
		ps = new Paras();
		ps.SQL = "UPDATE " + fl.getPTable() + " SET FlowEnder=" + dbstr + "FlowEnder,FlowEndNode=" + dbstr
				+ "FlowEndNode WHERE OID=" + dbstr + "OID";
		ps.Add(NDXRptBaseAttr.FlowEnder, toEmper);
		ps.Add(NDXRptBaseAttr.FlowEndNode, toNodeID);
		ps.Add(NDXRptBaseAttr.OID, workid);
		BP.DA.DBAccess.RunSQL(ps);

		// 执行更新.
		GenerWorkerLists gwls = new GenerWorkerLists(workid);
		GenerWorkerList gwl = (GenerWorkerList) ((gwls.get(gwls.size() - 1) instanceof GenerWorkerList)
				? gwls.get(gwls.size() - 1)
				: null); // 获得最后一个。
		gwl.setRDT(DataType.getCurrentDataTimess());
		gwl.setWorkID(workid);
		gwl.setFK_Node(toNodeID);
		gwl.setFK_NodeText(nd.getName());
		gwl.setFK_Emp(toEmper);
		gwl.setFK_EmpText(emp.getName());
		gwl.setIsPass(false);
		gwl.setIsEnable(true);
		gwl.setIsRead(false);
		gwl.setWhoExeIt(nd.getWhoExeIt());
		// gwl.Sender = WebUser.No;
		gwl.setHungUpTimes(0);
		gwl.setFID(gwf.getFID());
		gwl.setFK_Dept(emp.getFK_Dept());

		if (gwl.Update() == 0) {
			gwl.Insert();
		}

		String sql = "SELECT COUNT(*) FROM WF_EmpWorks where WorkID=" + workid + " and fk_emp='" + toEmper + "'";
		int i = BP.DA.DBAccess.RunSQLReturnValInt(sql);
		if (i == 0) {
			throw new RuntimeException("@调度错误");
		}

		return "该流程(" + gwf.getTitle() + ")，已经调度到(" + nd.getName() + "),分配给(" + emp.getName() + ")";
	}

	/**
	 * 设置流程运行模式 如果是自动流程. 负责人:liuxianchen. 调用地方/WorkOpt/TransferCustom.jsp
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param isAutoRun
	 *            是否自动运行？ 如果自动运行，就按照流程设置的规则运行。 非自动运行，就按照用户自己定义的运转顺序计算。
	 * @param paras
	 *            手工运行的参数格式为: @节点ID1,子流程No,接受人1,接受人n,@节点ID2,子流程No,接受人1,接受人n,
	 */
	public static void Flow_SetFlowTransferCustom(String flowNo, long workid, boolean isAutoRun, String paras) {
		// 删除以前存储的参数.
		BP.DA.DBAccess.RunSQL("DELETE FROM WF_TransferCustom WHERE WorkID=" + workid);

		// 保存参数.
		// 参数格式为 @104,SubFlow002,zhangsan,lisi@103,,zhaoliu,wangqi
		String[] strs = paras.split("[@]", -1);
		int idx = 0;
		for (String str : strs) {
			if (StringHelper.isNullOrEmpty(str)) {
				continue;
			}

			if (str.contains(",") == false) {
				continue;
			}

			// 处理字符串.
			String[] vals = str.split("[,]", -1);
			int nodeid = Integer.parseInt(vals[0]);

			String subFlow = vals[1];

			TransferCustom tc = new TransferCustom();
			tc.setIdx(idx);
			tc.setFK_Node(nodeid);
			tc.setWorkID(workid);
			tc.setWorker(str.replace(nodeid + "," + subFlow + ",", ""));
			tc.setSubFlowNo(subFlow);
			tc.setMyPK(tc.getFK_Node() + "_" + tc.getWorkID() + "_" + idx);
			tc.Save();
			idx++;
		}

		// 设置运行模式.
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workid);
		if (gwf.RetrieveFromDBSources() == 0) {
			gwf.setWFSta(WFSta.Runing);
			gwf.setWFState(WFState.Blank);

			gwf.setStarter(WebUser.getNo());
			gwf.setStarterName(WebUser.getName());

			gwf.setFK_Flow(flowNo);
			Flow fl = new Flow(flowNo);
			gwf.setFK_FlowSort(fl.getFK_FlowSort());
			gwf.setFK_Dept(WebUser.getFK_Dept());
			gwf.setIsAutoRun(isAutoRun);
			gwf.Insert();
			return;
		}
		gwf.Update();
	}

	/**
	 * 获得流程运行过程中的参数
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @return 如果没有就返回null,有就返回@参数名0=参数值0@参数名1=参数值1
	 */
	public static String GetFlowParas(int nodeID, long workid) {
		String str = "";
		try {
			String dbstr = SystemConfig.getAppCenterDBVarStr();
			Paras ps = new Paras();
			ps.SQL = "SELECT Paras FROM WF_GenerWorkerlist WHERE FK_Node=" + dbstr + "FK_Node AND WorkID=" + dbstr
					+ "WorkID";
			ps.Add(GenerWorkerListAttr.FK_Node, nodeID);
			ps.Add(GenerWorkerListAttr.WorkID, workid);
			str = DBAccess.RunSQLReturnStringIsNull(ps, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 发起流程
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param ht
	 *            节点表单:主表数据以Key Value 方式传递(可以为空)
	 * @param workDtls
	 *            节点表单:从表数据，从表名称与从表单的从表编号要对应(可以为空)
	 * @param nextNodeID
	 *            发起后要跳转到的节点(可以为空)
	 * @param nextWorker
	 *            发起后要跳转到的节点并指定的工作人员(可以为空)
	 * @return 发送到第二个节点的执行信息
	 */
	public static SendReturnObjs Node_StartWork(String flowNo, java.util.Hashtable ht, DataSet workDtls, int nextNodeID,
			String nextWorker) {
		return Node_StartWork(flowNo, ht, workDtls, nextNodeID, nextWorker, 0, null);
	}

	/**
	 * 发起流程
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param htWork
	 *            节点表单:主表数据以Key Value 方式传递(可以为空)
	 * @param workDtls
	 *            节点表单:从表数据，从表名称与从表单的从表编号要对应(可以为空)
	 * @param nextNodeID
	 *            发起后要跳转到的节点(可以为空)
	 * @param nextWorker
	 *            发起后要跳转到的节点并指定的工作人员(可以为空)
	 * @param parentWorkID
	 *            父流程的workid，如果没有可以为0
	 * @param parentFlowNo
	 *            父流程的编号，如果没有可以为空
	 * @return 发送到第二个节点的执行信息
	 */
	public static SendReturnObjs Node_StartWork(String flowNo, java.util.Hashtable<String, Object> htWork,
			DataSet workDtls, int nextNodeID, String nextWorker, long parentWorkID, String parentFlowNo) {
		// 给全局变量赋值.
		BP.WF.Glo.SendHTOfTemp = htWork;

		// 给全局变量赋值.
		BP.WF.Glo.SendHTOfTemp = htWork;

		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		//
		parentFlowNo = TurnFlowMarkToFlowNo(parentFlowNo);
		Flow fl = new Flow(flowNo);
		Work wk = fl.NewWork();
		long workID = wk.getOID();
		if (htWork != null) {
			for (String str : htWork.keySet()) {
				if (StartWorkAttr.OID.equals(str) || StartWorkAttr.CDT.equals(str) || StartWorkAttr.MD5.equals(str)
						|| StartWorkAttr.Emps.equals(str) || StartWorkAttr.FID.equals(str)
						|| StartWorkAttr.FK_Dept.equals(str) || StartWorkAttr.PRI.equals(str)
						|| StartWorkAttr.Rec.equals(str) || StartWorkAttr.Title.equals(str)) {
					continue;
				} else {
				}
				wk.SetValByKey(str, htWork.get(str));
			}
		}

		wk.setOID(workID);
		if (workDtls != null) {
			// 保存从表
			for (DataTable dt : workDtls.Tables) {
				for (MapDtl dtl : MapDtls.convertMapDtls(wk.getHisMapDtls())) {
					if (dt.TableName != dtl.getNo()) {
						continue;
					}
					// 获取dtls
					GEDtls daDtls = new GEDtls(dtl.getNo());
					daDtls.Delete(GEDtlAttr.RefPK, wk.getOID()); // 清除现有的数据.

					GEDtl daDtl = (GEDtl) ((daDtls.getGetNewEntity() instanceof GEDtl) ? daDtls.getGetNewEntity()
							: null);
					daDtl.setRefPK((new Long(wk.getOID())).toString());

					// 为从表复制数据.
					for (DataRow dr : dt.Rows) {
						daDtl.ResetDefaultVal();
						daDtl.setRefPK((new Long(wk.getOID())).toString());

						// 明细列.
						for (DataColumn dc : dt.Columns) {
							// 设置属性.
							daDtl.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName));
						}
						try {
							daDtl.InsertAsOID(DBAccess.GenerOID("Dtl"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 插入数据.
					}
				}
			}
		}

		WorkNode wn = new WorkNode(wk, fl.getHisStartNode());

		Node nextNoode = null;
		if (nextNodeID != 0) {
			nextNoode = new Node(nextNodeID);
		}

		SendReturnObjs objs = wn.NodeSend(nextNoode, nextWorker);
		if (parentWorkID != 0) {
			DBAccess.RunSQL("UPDATE WF_GenerWorkFlow SET PWorkID=" + parentWorkID + ",PFlowNo='" + parentFlowNo
					+ "' WHERE WorkID=" + objs.getVarWorkID());
		}

		if (htWork != null) {
			String paras = "";
			for (String key : htWork.keySet()) {
				paras += "@" + key + "=" + htWork.get(key).toString();
			}

			if (StringHelper.isNullOrEmpty(paras) == false) {
				String dbstr = SystemConfig.getAppCenterDBVarStr();
				Paras ps = new Paras();
				ps.SQL = "UPDATE WF_GenerWorkerlist set AtPara=" + dbstr + "Paras WHERE WorkID=" + dbstr
						+ "WorkID AND FK_Node=" + dbstr + "FK_Node";
				ps.Add(GenerWorkerListAttr.Paras, paras);
				ps.Add(GenerWorkerListAttr.WorkID, workID);
				ps.Add(GenerWorkerListAttr.FK_Node, Integer.parseInt(flowNo + "01"));
				try {
					DBAccess.RunSQL(ps);
				} catch (java.lang.Exception e) {
					GenerWorkerList gwl = new GenerWorkerList();
					gwl.CheckPhysicsTable();
					DBAccess.RunSQL(ps);
				}
			}
		}

		return objs;
	}

	/**
	 * 创建WorkID
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param ht
	 *            表单参数，可以为null。
	 * @param workDtls
	 *            明细表参数，可以为null。
	 * @param nextWorker
	 *            操作员，如果为null就是当前人员。
	 * @param title
	 *            创建工作时的标题，如果为null，就按设置的规则生成。
	 * @return 为开始节点创建工作后产生的WorkID.
	 */
	public static long Node_CreateBlankWork(String flowNo, java.util.Hashtable ht, DataSet workDtls, String starter,
			String title) {
		return Node_CreateBlankWork(flowNo, ht, workDtls, starter, title, 0, 0, null, 0, null, 0, null);
	}

	/**
	 * 创建WorkID
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param ht
	 *            表单参数，可以为null。
	 * @param workDtls
	 *            明细表参数，可以为null。
	 * @param starter
	 *            流程的发起人
	 * @param title
	 *            创建工作时的标题，如果为null，就按设置的规则生成。
	 * @param parentWorkID
	 *            父流程的WorkID,如果没有父流程就传入为0.
	 * @param parentFlowNo
	 *            父流程的流程编号,如果没有父流程就传入为null.
	 * @return 为开始节点创建工作后产生的WorkID.
	 */
	public static long Node_CreateBlankWork(String flowNo, java.util.Hashtable ht, DataSet workDtls, String starter,
			String title, long parentWorkID, String parentFlowNo, int parentNodeID, String parentEmp) {
		return Node_CreateBlankWork(flowNo, ht, workDtls, starter, title, 0, 0, null, 0, null, 0, null);
	}

	/**
	 * 创建WorkID
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param ht
	 *            表单参数，可以为null。
	 * @param workDtls
	 *            明细表参数，可以为null。
	 * @param starter
	 *            流程的发起人
	 * @param title
	 *            创建工作时的标题，如果为null，就按设置的规则生成。
	 * @param parentWorkID
	 *            父流程的WorkID,如果没有父流程就传入为0.
	 * @param parentFID
	 *            父流程的FID,如果没有父流程就传入为0.
	 * @param parentFlowNo
	 *            父流程的流程编号,如果没有父流程就传入为null.
	 * @param jumpToNode
	 *            要跳转到的节点,如果没有则为0.
	 * @param jumpToEmp
	 *            要跳转到的人员,如果没有则为null.
	 * @return 为开始节点创建工作后产生的WorkID.
	 */
	public static long Node_CreateBlankWork(String flowNo, java.util.Hashtable ht, DataSet workDtls, String starter,
			String title, long parentWorkID, long parentFID, String parentFlowNo, int parentNodeID, String parentEmp,
			int jumpToNode, String jumpToEmp) {
		// 把一些其他的参数也增加里面去,传递给ccflow.
		java.util.Hashtable htPara = new java.util.Hashtable();
		if (parentWorkID != 0) {
			htPara.put(StartFlowParaNameList.PWorkID, parentWorkID);
		}
		if (parentFID != 0) {
			htPara.put(StartFlowParaNameList.PFID, parentFID);
		}

		if (parentFlowNo != null) {
			htPara.put(StartFlowParaNameList.PFlowNo, parentFlowNo);
		}
		if (parentNodeID != 0) {
			htPara.put(StartFlowParaNameList.PNodeID, parentNodeID);
		}
		if (parentEmp != null) {
			htPara.put(StartFlowParaNameList.PEmp, parentEmp);
		}

		// 给全局变量赋值.
		BP.WF.Glo.SendHTOfTemp = ht;

		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		// 转化成编号
		parentFlowNo = TurnFlowMarkToFlowNo(parentFlowNo);

		if (parentFlowNo == null) {
			parentFlowNo = "";
		}

		String dbstr = SystemConfig.getAppCenterDBVarStr();

		if (StringHelper.isNullOrEmpty(starter)) {
			starter = WebUser.getNo();
		}

		Flow fl = new Flow(flowNo);
		Node nd = new Node(fl.getStartNodeID());

		// 下一个工作人员。
		Emp empStarter = new Emp(starter);
		Work wk = fl.NewWork(empStarter, htPara);
		long workID = wk.getOID();

		/// #region 给各个属性-赋值
		if (ht != null) {
			Set<String> keys = ht.keySet();
			for (String str : keys) {
				if (StartWorkAttr.OID.equals(str) || StartWorkAttr.CDT.equals(str) || StartWorkAttr.MD5.equals(str)
						|| StartWorkAttr.Emps.equals(str) || StartWorkAttr.FID.equals(str)
						|| StartWorkAttr.FK_Dept.equals(str) || StartWorkAttr.PRI.equals(str)
						|| StartWorkAttr.Rec.equals(str) || StartWorkAttr.Title.equals(str)) {
					continue;
				} else {
				}
				wk.SetValByKey(str, ht.get(str));
			}
		}
		wk.setOID(workID);
		if (workDtls != null) {
			// 保存从表
			for (DataTable dt : workDtls.Tables) {
				for (MapDtl dtl : MapDtls.convertMapDtls(wk.getHisMapDtls())) {
					if (dt.TableName != dtl.getNo()) {
						continue;
					}
					// 获取dtls
					GEDtls daDtls = new GEDtls(dtl.getNo());
					daDtls.Delete(GEDtlAttr.RefPK, wk.getOID()); // 清除现有的数据.

					GEDtl daDtl = (GEDtl) ((daDtls.getGetNewEntity() instanceof GEDtl) ? daDtls.getGetNewEntity()
							: null);
					daDtl.setRefPK((new Long(wk.getOID())).toString());

					// 为从表复制数据.
					for (DataRow dr : dt.Rows) {
						daDtl.ResetDefaultVal();
						daDtl.setRefPK((new Long(wk.getOID())).toString());

						// 明细列.
						for (DataColumn dc : dt.Columns) {
							// 设置属性.
							daDtl.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName));
						}
						try {
							daDtl.InsertAsOID(DBAccess.GenerOID("Dtl"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 插入数据.
					}
				}
			}
		}

		Paras ps = new Paras();
		// 执行对报表的数据表WFState状态的更新,让它为runing的状态.
		if (StringHelper.isNullOrEmpty(title) == false) {
			if (!fl.getTitleRole().equals("@OutPara")) {
				fl.setTitleRole("@OutPara");
				fl.Update();
			}

			ps = new Paras();
			ps.SQL = "UPDATE " + fl.getPTable() + " SET PFlowNo=" + dbstr + "PFlowNo,PWorkID=" + dbstr
					+ "PWorkID,WFState=" + dbstr + "WFState,Title=" + dbstr + "Title WHERE OID=" + dbstr + "OID";
			ps.Add(GERptAttr.PFlowNo, parentFlowNo);
			ps.Add(GERptAttr.PWorkID, parentWorkID);

			ps.Add(GERptAttr.WFState, WFState.Blank.getValue());
			ps.Add(GERptAttr.Title, title);
			ps.Add(GERptAttr.OID, wk.getOID());
			DBAccess.RunSQL(ps);
		} else {
			ps = new Paras();
			ps.SQL = "UPDATE " + fl.getPTable() + " SET PFlowNo=" + dbstr + "PFlowNo,PWorkID=" + dbstr
					+ "PWorkID,WFState=" + dbstr + "WFState,FK_Dept=" + dbstr + "FK_Dept,Title=" + dbstr
					+ "Title WHERE OID=" + dbstr + "OID";
			ps.Add(GERptAttr.PFlowNo, parentFlowNo);
			ps.Add(GERptAttr.PWorkID, parentWorkID);
			// ps.Add(GERptAttr.PFID, parentFID);

			ps.Add(GERptAttr.WFState, WFState.Blank.getValue());
			ps.Add(GERptAttr.FK_Dept, empStarter.getFK_Dept());
			ps.Add(GERptAttr.Title, WorkNode.GenerTitle(fl, wk));
			ps.Add(GERptAttr.OID, wk.getOID());
			DBAccess.RunSQL(ps);
		}

		// 设置父流程信息.
		if (parentWorkID != 0) {
			// 删除有可能产生的垃圾数据,比如上一次没有发送成功，导致数据没有清除.
			ps = new Paras();
			ps.SQL = "DELETE FROM WF_GenerWorkFlow  WHERE WorkID=" + dbstr + "WorkID1 OR FID=" + dbstr + "WorkID2";
			ps.Add("WorkID1", wk.getOID());
			ps.Add("WorkID2", wk.getOID());
			DBAccess.RunSQL(ps);

			GenerWorkFlow gwf = new GenerWorkFlow();
			gwf.setWorkID(wk.getOID());
			int i = gwf.RetrieveFromDBSources();
			if (i == 0) {
				// 将流程信息提前写入wf_GenerWorkFlow,避免查询不到
				gwf.setFlowName(fl.getName());
				gwf.setFK_Flow(flowNo);
				gwf.setFK_FlowSort(fl.getFK_FlowSort());
				gwf.setFK_Dept(WebUser.getFK_Dept());
				gwf.setDeptName(WebUser.getFK_DeptName());
				gwf.setFK_Node(fl.getStartNodeID());
				gwf.setNodeName(nd.getName());
				gwf.setWFState(WFState.Runing);
				if (StringHelper.isNullOrEmpty(title)) {
					gwf.setTitle(BP.WF.WorkNode.GenerTitle(fl, wk));
				} else {
					gwf.setTitle(title);
				}
				gwf.setStarter(WebUser.getNo());
				gwf.setStarterName(WebUser.getName());
				gwf.setRDT(DataType.getCurrentDataTime());
				gwf.setPWorkID(parentWorkID);
				gwf.setPFID(parentFID);

				gwf.setPFlowNo(parentFlowNo);
				gwf.setPNodeID(parentNodeID);
				gwf.Insert();

				GenerWorkerList gwl = new GenerWorkerList();
				gwl.setWorkID(wk.getOID());
				gwl.setFK_Emp(WebUser.getNo());
				gwl.setFK_EmpText(WebUser.getName());
				gwl.setFK_Node(nd.getNodeID());
				gwl.setFK_NodeText(nd.getName());
				gwl.setFID(0);
				gwl.setFK_Flow(fl.getNo());
				gwl.setFK_Dept(WebUser.getFK_Dept());
				gwl.setSDT(DataType.getCurrentDataTime());
				gwl.setDTOfWarning(DataType.getCurrentDataTime());
				gwl.setRDT(DataType.getCurrentDataTime());
				gwl.setIsEnable(true);
				gwl.setIsPass(false);

				gwl.setPRI(gwf.getPRI());
				gwl.Insert();
			}
			// 设置父流程信息
			BP.WF.Dev2Interface.SetParentInfo(flowNo, wk.getOID(), parentFlowNo, parentWorkID, parentNodeID, parentEmp);
		} else {
			// 删除有可能产生的垃圾数据,比如上一次没有发送成功，导致数据没有清除.
			ps = new Paras();
			ps.SQL = "DELETE FROM WF_GenerWorkFlow  WHERE WorkID=" + dbstr + "WorkID1 OR FID=" + dbstr + "WorkID2";
			ps.Add("WorkID1", wk.getOID());
			ps.Add("WorkID2", wk.getOID());
			DBAccess.RunSQL(ps);

		}
		ps = new Paras();
		ps.SQL = "DELETE FROM WF_GenerWorkerList  WHERE WorkID=" + dbstr + "WorkID1 OR FID=" + dbstr + "WorkID2";
		ps.Add("WorkID1", wk.getOID());
		ps.Add("WorkID2", wk.getOID());
		DBAccess.RunSQL(ps);

		// 如果有跳转.
		if (jumpToNode != 0) {
			BP.WF.Dev2Interface.Node_SendWork(flowNo, wk.getOID(), null, null, jumpToNode, jumpToEmp);
		}

		// if (jumpToNode != 0)
		// htPara.Add(StartFlowParaNameList.JumpToNode, jumpToNode);
		// if (jumpToEmp != null)
		// htPara.Add(StartFlowParaNameList.JumpToEmp, jumpToEmp);

		return wk.getOID();
	}

	/**
	 * 发起工作 创建后可以创办人形成一个待办.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param ht
	 *            表单参数，可以为null。
	 * @param workDtls
	 *            表单明细表参数，可以为null。
	 * @param flowStarter
	 *            流程发起人，如果为null就是当前人员。
	 * @param title
	 *            创建工作时的标题，如果为null，就按设置的规则生成。
	 * @return 为开始节点创建工作后产生的WorkID.
	 */
	public static long Node_CreateStartNodeWork(String flowNo, java.util.Hashtable ht, DataSet workDtls,
			String flowStarter, String title) {
		return Node_CreateStartNodeWork(flowNo, ht, workDtls, flowStarter, title, 0, null, 0);
	}

	/**
	 * 创建开始节点工作 创建后可以创办人形成一个待办.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param htWork
	 *            表单参数，可以为null。
	 * @param workDtls
	 *            明细表参数，可以为null。
	 * @param flowStarter
	 *            流程的发起人，如果为null就是当前人员。
	 * @param title
	 *            创建工作时的标题，如果为null，就按设置的规则生成。
	 * @param parentWorkID
	 *            父流程的WorkID,如果没有父流程就传入为0.
	 * @param parentFlowNo
	 *            父流程的流程编号,如果没有父流程就传入为null.
	 * @return 为开始节点创建工作后产生的WorkID.
	 */
	public static long Node_CreateStartNodeWork(String flowNo, java.util.Hashtable<String, Object> htWork,
			DataSet workDtls, String flowStarter, String title, long parentWorkID, String parentFlowNo,
			int parentNDFrom) {

		// 给全局变量赋值.
		BP.WF.Glo.SendHTOfTemp = htWork;

		// 转化成编号.
		flowNo = TurnFlowMarkToFlowNo(flowNo);

		// 转化成编号
		parentFlowNo = TurnFlowMarkToFlowNo(parentFlowNo);

		if (StringHelper.isNullOrEmpty(flowStarter)) {
			flowStarter = WebUser.getNo();
		}

		Flow fl = new Flow(flowNo);

		/// #region 处理流程标题.
		if (StringHelper.isNullOrEmpty(title) == false && !fl.getTitleRole().equals("@OutPara")) {
			// 如果标题不为空
			fl.setTitleRole("@OutPara"); // 特殊标记不为空.
			fl.Update();
		}
		if (StringHelper.isNullOrEmpty(title) == true && fl.getTitleRole().equals("@OutPara")) {
			// 如果标题为空
			fl.setTitleRole(""); // 特殊标记不为空.
			fl.Update();
		}
		/// #endregion 处理流程标题.

		Node nd = new Node(fl.getStartNodeID());

		// 下一个工作人员。
		Emp emp = new Emp(flowStarter);
		Work wk = fl.NewWork(flowStarter);
		long workID = wk.getOID();

		/// #region 为开始工作创建待办
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workID);
		int i = gwf.RetrieveFromDBSources();
		if (i == 0) {
			gwf.setFlowName(fl.getName());
			gwf.setFK_Flow(flowNo);
			gwf.setFK_FlowSort(fl.getFK_FlowSort());

			gwf.setFK_Dept(emp.getFK_Dept());
			gwf.setDeptName(emp.getFK_DeptText());
			gwf.setFK_Node(fl.getStartNodeID());

			gwf.setNodeName(nd.getName());
			gwf.setWFState(WFState.Runing);

			if (StringHelper.isNullOrEmpty(title)) {
				gwf.setTitle(BP.WF.WorkNode.GenerTitle(fl, wk));
			} else {
				gwf.setTitle(title);
			}

			gwf.setStarter(emp.getNo());
			gwf.setStarterName(emp.getName());
			gwf.setRDT(DataType.getCurrentDataTime());

			if (htWork != null && htWork.containsKey("PRI") == true) {
				gwf.setPRI(Integer.parseInt(htWork.get("PRI").toString()));
			}

			if (htWork != null && htWork.containsKey("SDTOfNode") == true)
			// 节点应完成时间
			{
				gwf.setSDTOfNode(htWork.get("SDTOfNode").toString());
			}

			if (htWork != null && htWork.containsKey("SDTOfFlow") == true)
			// 流程应完成时间
			{
				gwf.setSDTOfNode(htWork.get("SDTOfFlow").toString());
			}

			gwf.setPWorkID(parentWorkID);
			gwf.setPFlowNo(parentFlowNo);
			gwf.setPNodeID(parentNDFrom);
			gwf.Insert();

			// 产生工作列表.
			GenerWorkerList gwl = new GenerWorkerList();
			gwl.setWorkID(wk.getOID());
			gwl.setFK_Emp(emp.getNo());
			gwl.setFK_EmpText(emp.getName());

			gwl.setFK_Node(nd.getNodeID());
			gwl.setFK_NodeText(nd.getName());
			gwl.setFID(0);

			gwl.setFK_Flow(fl.getNo());
			gwl.setFK_Dept(emp.getFK_Dept());

			gwl.setSDT(DataType.getCurrentDataTime());
			gwl.setDTOfWarning(DataType.getCurrentDataTime());
			gwl.setRDT(DataType.getCurrentDataTime());
			gwl.setIsEnable(true);

			gwl.setIsPass(false);
			// gwl.Sender = WebUser.No;
			gwl.setPRI(gwf.getPRI());
			gwl.Insert();
		}

		/// #region 给各个属性-赋值
		if (htWork != null) {
			for (String str : htWork.keySet()) {

				if (StartWorkAttr.OID.equals(str) || StartWorkAttr.CDT.equals(str) || StartWorkAttr.MD5.equals(str)
						|| StartWorkAttr.Emps.equals(str) || StartWorkAttr.FID.equals(str)
						|| StartWorkAttr.FK_Dept.equals(str) || StartWorkAttr.PRI.equals(str)
						|| StartWorkAttr.Rec.equals(str) || StartWorkAttr.Title.equals(str)) {
					continue;
				} else {
				}

				wk.SetValByKey(str, htWork.get(str));
			}
		}
		wk.setOID(workID);
		if (workDtls != null) {
			// 保存从表
			for (DataTable dt : workDtls.Tables) {
				for (MapDtl dtl : MapDtls.convertMapDtls(wk.getHisMapDtls())) {
					if (dt.TableName != dtl.getNo()) {
						continue;
					}
					// 获取dtls
					GEDtls daDtls = new GEDtls(dtl.getNo());
					daDtls.Delete(GEDtlAttr.RefPK, wk.getOID()); // 清除现有的数据.

					GEDtl daDtl = (GEDtl) ((daDtls.getGetNewEntity() instanceof GEDtl) ? daDtls.getGetNewEntity()
							: null);
					daDtl.setRefPK((new Long(wk.getOID())).toString());

					// 为从表复制数据.
					for (DataRow dr : dt.Rows) {
						daDtl.ResetDefaultVal();
						daDtl.setRefPK((new Long(wk.getOID())).toString());

						// 明细列.
						for (DataColumn dc : dt.Columns) {
							// 设置属性.
							daDtl.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName));
						}
						try {
							daDtl.InsertAsOID(DBAccess.GenerOID("Dtl"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 插入数据.
					}
				}
			}
		}

		// 执行对报表的数据表WFState状态的更新,让它为runing的状态.
		String dbstr = SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE " + fl.getPTable() + " SET WFState=" + dbstr + "WFState,Title=" + dbstr + "Title,FK_Dept="
				+ dbstr + "FK_Dept,PFlowNo=" + dbstr + "PFlowNo,PWorkID=" + dbstr + "PWorkID WHERE OID=" + dbstr
				+ "OID";
		ps.Add("WFState", WFState.Runing.getValue());
		ps.Add("Title", gwf.getTitle());
		ps.Add("FK_Dept", gwf.getFK_Dept());

		ps.Add("PFlowNo", gwf.getPFlowNo());
		ps.Add("PWorkID", gwf.getPWorkID());

		ps.Add("OID", wk.getOID());
		DBAccess.RunSQL(ps);

		//// 写入日志.
		// WorkNode wn = new WorkNode(wk, nd);
		// wn.AddToTrack(ActionType.CallSubFlow, flowStarter, emp.Name,
		//// nd.NodeID, nd.Name, "来自" + WebUser.No + "," + WebUser.Name
		// + "工作发起.");

		if (htWork != null) {
			String paras = "";
			for (String key : htWork.keySet()) {
				paras += "@" + key + "=" + htWork.get(key).toString();
			}

			if (StringHelper.isNullOrEmpty(paras) == false) {
				ps = new Paras();
				ps.SQL = "UPDATE WF_GenerWorkerlist SET AtPara=" + dbstr + "Paras WHERE WorkID=" + dbstr
						+ "WorkID AND FK_Node=" + dbstr + "FK_Node";
				ps.Add(GenerWorkerListAttr.Paras, paras);
				ps.Add(GenerWorkerListAttr.WorkID, workID);
				ps.Add(GenerWorkerListAttr.FK_Node, nd.getNodeID());
				DBAccess.RunSQL(ps);
			}
		}

		return wk.getOID();
	}

	/**
	 * 执行工作发送
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @return 返回发送结果
	 */
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		return Node_SendWork(fk_flow, workID, null, null, 0, null);
	}

	/**
	 * 执行工作发送
	 * 
	 * @param fk_flow
	 *            工作编号
	 * @param workID
	 *            工作ID
	 * @param ht
	 *            节点表单数据
	 * @return 返回发送结果
	 */
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID, java.util.Hashtable ht) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		return Node_SendWork(fk_flow, workID, ht, null, 0, null);
	}

	/**
	 * 执行工作发送
	 * 
	 * @param fk_flow
	 *            工作编号
	 * @param workID
	 *            工作ID
	 * @param ht
	 *            节点表单数据
	 * @param dsDtl
	 *            节点表单从表数据
	 * @return 返回发送结果
	 */
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID, java.util.Hashtable ht, DataSet dsDtl) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		return Node_SendWork(fk_flow, workID, ht, dsDtl, 0, null);
	}

	/**
	 * 发送工作
	 * 
	 * @param nodeID
	 *            节点编号
	 * @param workID
	 *            工作ID
	 * @param toNodeID
	 *            发送到的节点编号，如果是0就让ccflow自动计算.
	 * @param toEmps
	 *            发送到的人员,多个人员用逗号分开比如：zhangsan,lisi. 如果是null则表示让ccflow自动计算.
	 * @return 返回执行信息
	 */
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID, int toNodeID, String toEmps) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		return Node_SendWork(fk_flow, workID, null, null, toNodeID, toEmps);
	}

	/**
	 * 发送工作
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param htWork
	 *            节点表单数据(Hashtable中的key与节点表单的字段名相同,value 就是字段值)
	 * @return 执行信息
	 */
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID, java.util.Hashtable htWork, int toNodeID,
			String nextWorkers) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		return Node_SendWork(fk_flow, workID, htWork, null, toNodeID, nextWorkers, WebUser.getNo(), WebUser.getName(),
				WebUser.getFK_Dept(), WebUser.getFK_DeptName(), null);
	}

	/**
	 * 发送工作
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param htWork
	 *            节点表单数据(Hashtable中的key与节点表单的字段名相同,value 就是字段值)
	 * @param workDtls
	 *            节点表单明从表数据(dataset可以包含多个table，每个table的名称与从表名称相同，列名与从表的字段相同,
	 *            OID,RefPK列需要为空或者null )
	 * @param toNodeID
	 *            到达的节点，如果是0表示让ccflow自动寻找，否则就按照该参数发送。
	 * @param nextWorkers
	 *            下一步的接受人，如果多个人员用逗号分开，比如:zhangsan,lisi, 如果为空，则标识让ccflow按照节点访问规则自动寻找。
	 * @return 执行信息
	 */
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID, java.util.Hashtable htWork,
			DataSet workDtls, int toNodeID, String nextWorkers) {
		return Node_SendWork(fk_flow, workID, htWork, workDtls, toNodeID, nextWorkers, WebUser.getNo(),
				WebUser.getName(), WebUser.getFK_Dept(), WebUser.getFK_DeptName(), null);
	}

	/**
	 * 发送工作
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param htWork
	 *            节点表单数据(Hashtable中的key与节点表单的字段名相同,value 就是字段值)
	 * @param workDtls
	 *            节点表单明从表数据(dataset可以包含多个table，每个table的名称与从表名称相同，列名与从表的字段相同,
	 *            OID,RefPK列需要为空或者null )
	 * @param toNodeID
	 *            到达的节点，如果是0表示让ccflow自动寻找，否则就按照该参数发送。
	 * @param nextWorkers
	 *            下一步的接受人，如果多个人员用逗号分开，比如:zhangsan,lisi, 如果为空，则标识让ccflow按照节点访问规则自动寻找。
	 * @param execUserNo
	 *            执行人编号
	 * @param execUserName
	 *            执行人名称
	 * @param execUserDeptNo
	 *            执行人部门名称
	 * @param execUserDeptName
	 *            执行人部门编号
	 * @return 发送的结果对象
	 */
	@Transactional
	public static SendReturnObjs Node_SendWork(String fk_flow, long workID, java.util.Hashtable<String, Object> htWork,
			DataSet workDtls, int toNodeID, String nextWorkers, String execUserNo, String execUserName,
			String execUserDeptNo, String execUserDeptName, String title) {
		// 给临时的发送变量赋值，解决带有参数的转向。
		Glo.SendHTOfTemp = htWork;

		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		int currNodeId = Dev2Interface.Node_GetCurrentNodeID(fk_flow, workID);

		BP.WF.Dev2Interface.Node_SaveWork(fk_flow, currNodeId, workID, htWork, workDtls);

		// 变量.
		Node nd = new Node(currNodeId);
		Work sw = nd.getHisWork();
		sw.setOID(workID);
		sw.RetrieveFromDBSources();

		// 补偿性修复.
		if (nd.getHisRunModel() != RunModel.SubThread) {
			if (sw.getFID() != 0) {
				sw.DirectUpdate();
			}
		}

		SendReturnObjs objs;
		// 执行流程发送.
		WorkNode wn = new WorkNode(sw, nd);
		wn.setExecer(execUserNo);
		wn.setExecerName(execUserName);
		wn.setExecer(execUserNo);
		wn.title = title; // 设置标题，有可能是从外部传递过来的标题.
		wn.SendHTOfTemp = htWork;

		if (toNodeID == 0) {

			objs = wn.NodeSend(null, nextWorkers);
		} else {
			objs = wn.NodeSend(new Node(toNodeID), nextWorkers);
		}

		if (htWork != null) {
			String dbstr = SystemConfig.getAppCenterDBVarStr();
			Paras ps = new Paras();

			String paras = "";
			for (String key : htWork.keySet()) {
				paras += "@" + key + "=" + htWork.get(key).toString();

				if (WorkSysFieldAttr.SysSDTOfFlow.equals(key)) {
					ps = new Paras();
					ps.SQL = "UPDATE WF_GenerWorkFlow SET SDTOfFlow=" + dbstr + "SDTOfFlow WHERE WorkID=" + dbstr
							+ "WorkID";
					ps.Add(GenerWorkFlowAttr.SDTOfFlow, htWork.get(key).toString());
					ps.Add(GenerWorkerListAttr.WorkID, workID);
					DBAccess.RunSQL(ps);

				}

				else if (WorkSysFieldAttr.SysSDTOfNode.equals(key)) {
					ps = new Paras();
					ps.SQL = "UPDATE WF_GenerWorkFlow SET SDTOfNode=" + dbstr + "SDTOfNode WHERE WorkID=" + dbstr
							+ "WorkID";
					ps.Add(GenerWorkFlowAttr.SDTOfNode, htWork.get(key).toString());
					ps.Add(GenerWorkerListAttr.WorkID, workID);
					DBAccess.RunSQL(ps);

					ps = new Paras();
					ps.SQL = "UPDATE WF_GenerWorkerlist SET SDT=" + dbstr + "SDT WHERE WorkID=" + dbstr
							+ "WorkID AND FK_Node=" + dbstr + "FK_Node";
					ps.Add(GenerWorkerListAttr.SDT, htWork.get(key).toString());
					ps.Add(GenerWorkerListAttr.WorkID, workID);
					ps.Add(GenerWorkerListAttr.FK_Node, objs.getVarToNodeID());
					DBAccess.RunSQL(ps);
				} else {
				}
			}

			if (StringHelper.isNullOrEmpty(paras) == false) {
				ps = new Paras();
				ps.SQL = "UPDATE WF_GenerWorkerlist SET AtPara=" + dbstr + "Paras WHERE WorkID=" + dbstr
						+ "WorkID AND FK_Node=" + dbstr + "FK_Node";
				ps.Add(GenerWorkerListAttr.Paras, paras);
				ps.Add(GenerWorkerListAttr.WorkID, workID);
				ps.Add(GenerWorkerListAttr.FK_Node, nd.getNodeID());
				DBAccess.RunSQL(ps);
			}
		}

		return objs;
	}

	/**
	 * 增加在队列工作中增加一个处理人. 这个处理顺序系统已经自动处理了.
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeID
	 *            工作ID
	 * @param workid
	 *            workid
	 * @param fid
	 *            fid
	 * @param empNo
	 *            要增加的处理人编号
	 * @param empName
	 *            要增加的处理人名称
	 */
	public static void Node_InsertOrderEmp(String flowNo, int nodeID, long workid, long fid, String empNo,
			String empName) {
		GenerWorkerList gwl = new GenerWorkerList();
		int i = gwl.Retrieve(GenerWorkerListAttr.WorkID, workid, GenerWorkerListAttr.FK_Node, nodeID);
		if (i == 0) {
			throw new RuntimeException("@没有找到当前工作人员的待办，请检查该流程是否已经运行到该节点上来了。");
		}

		gwl.setIsPassInt(100);
		gwl.setIsEnable(true);
		gwl.setFK_Emp(empNo);
		gwl.setFK_EmpText(empName);

		try {
			gwl.Insert();
		} catch (java.lang.Exception e) {
			throw new RuntimeException("@该人员已经存在处理队列中，您不能增加.");
		}

		// 开始更新他们的顺序, 首先从数据库里获取他们的顺序. lxl职位由小到大
		String sql = "SELECT No,Name FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM WF_GenerWorkerList WHERE WorkID="
				+ workid + " AND FK_Node=" + nodeID + " AND IsPass >=100 ) ORDER BY IDX desc";
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		int idx = 100;
		for (DataRow dr : dt.Rows) {
			idx++;
			String myEmpNo = dr.get(0).toString();
			sql = "UPDATE WF_GenerWorkerList SET IsPass=" + idx + " WHERE FK_Emp='" + myEmpNo + "' AND WorkID=" + workid
					+ " AND FK_Node=" + nodeID;
			BP.DA.DBAccess.RunSQL(sql);
		}
	}

	/**
	 * 把抄送写入待办列表
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @param ccToEmpNo
	 *            抄送给
	 * @param ccToEmpName
	 *            抄送给名称
	 * @return
	 */
	public static String Node_CC_WriteTo_Todolist(int ndFrom, int ndTo, long workID, String ccToEmpNo,
			String ccToEmpName) {
		// return Node_CC_WriteTo_CClist(ndFrom, ndTo, workID, ccToEmpNo,
		// ccToEmpName, "", ""));
		return Node_CC_WriteTo_CClist(ndFrom, ndTo, workID, ccToEmpNo, ccToEmpName, "", "");
		/*** 如果要写入待办 */
		// GenerWorkerList gwl =new GenerWorkerList();
		// int i=gwl.Retrieve(GenerWorkerListAttr.WorkID, workID,
		// GenerWorkerListAttr.FK_Node, nodeID);
		// if (i == 0)
		// throw new Exception("错误,没有找到它的待办.");
		// */

		// gwl.FK_Emp = ccToEmpNo;
		// gwl.FK_EmpText = ccToEmpName;
		// gwl.IsCC = true;
		// try
		// {
		// gwl.Insert();
		// }
		// catch
		// {
		// /*这里有可能*/
		// }
		// return "执行成功";
	}

	/**
	 * 执行抄送
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param toEmpNo
	 *            抄送人员编号
	 * @param toEmpName
	 *            抄送人员人员名称
	 * @param msgTitle
	 *            标题
	 * @param msgDoc
	 *            内容
	 * @return 执行信息
	 */
	public static String Node_CC_WriteTo_CClist(int ndFrom, int ndTo, long workID, String toEmpNo, String toEmpName,
			String msgTitle, String msgDoc) {
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workID);
		if (gwf.RetrieveFromDBSources() == 0) {
			Node nd = new Node(ndTo);
			gwf.setFK_Node(ndTo);
			gwf.setFK_Flow(nd.getFK_Flow());
			gwf.setFlowName(nd.getFlowName());
			gwf.setNodeName(nd.getName());
		}

		CCList list = new CCList();
		list.setMyPK(String.valueOf(DBAccess.GenerOIDByGUID()));// workID + "_"
																// + fk_node +
																// "_" + empNo);
		list.setFK_Flow(gwf.getFK_Flow());
		list.setFlowName(gwf.getFlowName());
		list.setFK_Node(ndTo);
		list.setNodeName(gwf.getNodeName());
		list.setTitle(msgTitle);
		list.setDoc(msgDoc);
		list.setCCTo(toEmpNo);
		list.setCCToName(toEmpName);

		// 增加抄送人部门.
		Emp emp = new Emp(toEmpNo);
		list.setCCToDept(emp.getFK_Dept());

		list.setRDT(DataType.getCurrentDataTime());
		list.setRec(WebUser.getNo());
		list.setWorkID(gwf.getWorkID());
		list.setFID(gwf.getFID());
		list.setPFlowNo(gwf.getPFlowNo());
		list.setPWorkID(gwf.getPWorkID());
		list.setNDFrom(ndFrom);
		try {
			list.Insert();
		} catch (java.lang.Exception e) {
			list.CheckPhysicsTable();
			list.Update();
		}

		// 记录日志.
		Glo.AddToTrack(ActionType.CC, gwf.getFK_Flow(), workID, gwf.getFID(), gwf.getFK_Node(), gwf.getNodeName(),
				WebUser.getNo(), WebUser.getName(), gwf.getFK_Node(), gwf.getNodeName(), toEmpNo, toEmpName, msgTitle,
				null);

		return "已经成功的把工作抄送给:" + toEmpNo + "," + toEmpName;
	}

	/**
	 * 执行删除
	 * 
	 * @param mypk
	 *            删除
	 */
	public static void Node_CC_DoDel(String mypk) {
		Paras ps = new Paras();
		ps.SQL = "DELETE WF_CCList WHERE MyPK=" + SystemConfig.getAppCenterDBVarStr() + "MyPK";
		ps.Add(CCListAttr.MyPK, mypk);
		BP.DA.DBAccess.RunSQL(ps);
	}

	/**
	 * 设置抄送状态
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @param empNo
	 *            人员编号
	 * @param sta
	 *            状态
	 */
	public static void Node_CC_SetSta(int nodeID, long workid, String empNo, CCSta sta) {
		String dbstr = SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_CCList   SET Sta=" + dbstr + "Sta WHERE WorkID=" + dbstr + "WorkID AND FK_Node=" + dbstr
				+ "FK_Node AND CCTo=" + dbstr + "CCTo";
		ps.Add(CCListAttr.Sta, sta.getValue());
		ps.Add(CCListAttr.WorkID, workid);
		ps.Add(CCListAttr.FK_Node, nodeID);
		ps.Add(CCListAttr.CCTo, empNo);
		BP.DA.DBAccess.RunSQL(ps);
	}

	/**
	 * 执行读取
	 * 
	 * @param mypk
	 */
	public static void Node_CC_SetRead(String mypk) {
		if (StringHelper.isNullOrEmpty(mypk)) {
			return;
		}

		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_CCList SET Sta=" + SystemConfig.getAppCenterDBVarStr() + "Sta  WHERE MyPK="
				+ SystemConfig.getAppCenterDBVarStr() + "MyPK";
		ps.Add(CCListAttr.Sta, CCSta.Read.getValue());
		ps.Add(CCListAttr.MyPK, mypk);
		BP.DA.DBAccess.RunSQL(ps);
	}

	/**
	 * 设置抄送读取
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @param empNo
	 *            读取人员编号
	 */
	public static void Node_CC_SetRead(int nodeID, long workid, String empNo) {
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_CCList SET Sta=" + SystemConfig.getAppCenterDBVarStr() + "Sta  WHERE WorkID="
				+ SystemConfig.getAppCenterDBVarStr() + "WorkID AND FK_Node=" + SystemConfig.getAppCenterDBVarStr()
				+ "FK_Node AND CCTo=" + SystemConfig.getAppCenterDBVarStr() + "CCTo";
		ps.Add(CCListAttr.Sta, CCSta.Read.getValue());
		ps.Add(CCListAttr.WorkID, workid);
		ps.Add(CCListAttr.FK_Node, nodeID);
		ps.Add(CCListAttr.CCTo, empNo);

		ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkerlist SET IsRead=1 WHERE WorkID=" + SystemConfig.getAppCenterDBVarStr()
				+ "WorkID AND FK_Node=" + SystemConfig.getAppCenterDBVarStr() + "FK_Node AND FK_Emp="
				+ SystemConfig.getAppCenterDBVarStr() + "FK_Emp";
		ps.Add(GenerWorkerListAttr.WorkID, workid);
		ps.Add(GenerWorkerListAttr.FK_Node, nodeID);
		ps.Add(GenerWorkerListAttr.FK_Emp, empNo);

		DBAccess.RunSQL(ps);
	}

	/**
	 * 执行抄送
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            节点编号
	 * @param workID
	 *            工作ID
	 * @param toEmpNo
	 *            抄送给人员编号
	 * @param toEmpName
	 *            抄送给人员名称
	 * @param msgTitle
	 *            消息标题
	 * @param msgDoc
	 *            消息内容
	 * @param pFlowNo
	 *            父流程编号(可以为null)
	 * @param pWorkID
	 *            父流程WorkID(可以为0)
	 * @return
	 */
	public static String Node_CC(String fk_flow, int fk_node, long workID, String toEmpNo, String toEmpName,
			String msgTitle, String msgDoc, String pFlowNo, long pWorkID) {
		Flow fl = new Flow(fk_flow);
		Node nd = new Node(fk_node);

		if (nd.getCCWriteTo() == CCWriteTo.Todolist) {
			// 如果要写入待办
			GenerWorkerList gwl = new GenerWorkerList();
			gwl.Retrieve(GenerWorkerListAttr.WorkID, workID, GenerWorkerListAttr.FK_Node, fk_node,
					GenerWorkerListAttr.IsPass, 0);

			gwl.setWorkID(workID);
			gwl.setFK_Flow(fk_flow);
			gwl.setFK_Node(fk_node);
			gwl.setFK_NodeText(nd.getName());
			gwl.setIsPassInt(0);

			gwl.setFK_Emp(toEmpNo);
			gwl.setFK_EmpText(toEmpName);
			gwl.setIsCC(true);
			try {
				gwl.Insert();

			} catch (RuntimeException ex) {
				// 这里有可能，抄送人员与处理人员重复.
				return "@执行抄送失败，有可能是人员(" + toEmpNo + "," + toEmpName + ")重复或者，该人员有待办。";
			}
		}

		if (nd.getCCWriteTo() == CCWriteTo.All || nd.getCCWriteTo() == CCWriteTo.CCList) {
			CCList list = new CCList();
			list.setMyPK(String.valueOf(DBAccess.GenerOIDByGUID())); // workID +
																		// "_" +
																		// fk_node
																		// + "_"
																		// +
																		// empNo;
			list.setFK_Flow(fk_flow);
			list.setFlowName(fl.getName());
			list.setFK_Node(fk_node);
			list.setNodeName(nd.getName());
			list.setTitle(msgTitle);
			list.setDoc(msgDoc);
			list.setCCTo(toEmpNo);
			list.setCCToName(toEmpName);

			// 增加抄送人部门.
			Emp emp = new Emp(toEmpNo);
			list.setCCToDept(emp.getFK_Dept());

			list.setRDT(DataType.getCurrentDataTime());
			list.setRec(WebUser.getNo());
			list.setWorkID(workID);
			list.setFID(0);
			list.setPFlowNo(pFlowNo);
			list.setPWorkID(pWorkID);

			try {
				list.Insert();
			} catch (java.lang.Exception e) {
				list.CheckPhysicsTable();
				list.Update();
			}
		}

		GenerWorkFlow gwf = new GenerWorkFlow(workID);

		// 记录日志.
		Glo.AddToTrack(ActionType.CC, fk_flow, workID, 0, nd.getNodeID(), nd.getName(), WebUser.getNo(),
				WebUser.getName(), nd.getNodeID(), nd.getName(), toEmpNo, toEmpName, msgTitle, null);

		// 发送邮件.
		BP.WF.Dev2Interface.Port_SendMsg(toEmpNo, WebUser.getName() + "把工作:" + gwf.getTitle(), "抄送:" + msgTitle,
				"CC" + nd.getNodeID() + "_" + workID + "_", SMSMsgType.CC, gwf.getFK_Flow(), gwf.getFK_Node(),
				gwf.getWorkID(), gwf.getFID());

		return "已经成功的把工作抄送给:" + toEmpNo + "," + toEmpName;

	}

	/**
	 * 删除草稿
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 */
	public static void Node_DeleteDraft(String fk_flow, long workID) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		// 设置引擎表.
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workID);
		if (gwf.RetrieveFromDBSources() == 1) {
			if (gwf.getFK_Node() != Integer.parseInt(fk_flow + "01")) {
				throw new RuntimeException("@该流程非草稿流程不能删除:" + gwf.getTitle());
			}

			if (gwf.getWFState() != WFState.Draft) {
				throw new RuntimeException("@非草稿状态不能删除");
			}

			gwf.Delete();
		}

		// 删除流程.
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Flow fl = new Flow(fk_flow);
		Paras ps = new Paras();
		ps.SQL = "DELETE FROM " + fl.getPTable() + " WHERE OID=" + dbstr + "OID ";
		ps.Add(GERptAttr.OID, workID);
		DBAccess.RunSQL(ps);

		// 删除开始节点数据.
		try {
			ps = new Paras();
			ps.SQL = "DELETE FROM ND" + Integer.parseInt(fk_flow + "01") + " WHERE OID=" + dbstr + "OID ";
			ps.Add(GERptAttr.OID, workID);
			DBAccess.RunSQL(ps);
		} catch (java.lang.Exception e) {
		}

	}

	/**
	 * 把草稿设置待办
	 * 
	 * @param fk_flow
	 * @param workID
	 */
	public static void Node_SetDraft2Todolist(String fk_flow, long workID) {

	}

	/**
	 * 设置当前工作状态为草稿,如果启用了草稿, 请在开始节点的表单保存按钮下增加上它. 注意:必须是在开始节点时调用.
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 */
	public static void Node_SetDraft(String fk_flow, long workID) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		Flow fl = new Flow(fk_flow);

		// 保存数据
		Node_SaveWork(fk_flow, fl.getStartNodeID(), workID);

		// 设置引擎表.
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workID);
		if (gwf.RetrieveFromDBSources() == 1) {
			if (gwf.getFK_Node() != Integer.parseInt(fk_flow + "01")) {
				throw new RuntimeException("@设置草稿错误，只有在开始节点时才能设置草稿，现在的节点是:" + gwf.getTitle());
			}

			if (gwf.getWFState() != WFState.Blank) {
				return;
			}
			// 规则设置为写入待办，将状态置为运行中，其他设置为草稿
			WFState wfState = fl.getDraftRole() == DraftRole.SaveToTodolist ? WFState.Runing : WFState.Draft;
			// 设置成草稿.
			gwf.Update(GenerWorkFlowAttr.WFState, wfState.getValue());
		}
	}

	/**
	 * 保存
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @return 返回保存的信息
	 */
	public static String Node_SaveWork(String fk_flow, int fk_node, long workID) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		return Node_SaveWork(fk_flow, fk_node, workID, new java.util.Hashtable(), null);
	}

	/**
	 * 保存
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            workid
	 * @param wk
	 *            节点表单参数
	 * @return
	 */
	public static String Node_SaveWork(String fk_flow, int fk_node, long workID, java.util.Hashtable wk) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		return Node_SaveWork(fk_flow, fk_node, workID, wk, null);
	}

	/**
	 * 保存
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workID
	 *            工作ID
	 * @param htWork
	 *            工作数据
	 * @return 返回执行信息
	 */
	public static String Node_SaveWork(String fk_flow, int fk_node, long workID,
			java.util.Hashtable<String, Object> htWork, DataSet dsDtls) {
		if (htWork == null) {
			return "参数错误，保存失败。";
		}

		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		try {
			Node nd = new Node(fk_node);
			Work wk = nd.getHisWork();
			if (workID != 0) {
				wk.setOID(workID);
				wk.RetrieveFromDBSources();
			}
			wk.ResetDefaultVal();

			if (htWork != null) {
				for (String str : htWork.keySet()) {
					// C# TO JAVA CONVERTER NOTE: The following 'switch'
					// operated on a string member and was converted to Java
					// 'if-else' logic:
					// switch (str)
					// ORIGINAL LINE: case StartWorkAttr.OID:
					if (StartWorkAttr.OID.equals(str) || StartWorkAttr.CDT.equals(str) || StartWorkAttr.MD5.equals(str)
							|| StartWorkAttr.Emps.equals(str) || StartWorkAttr.FID.equals(str)
							|| StartWorkAttr.FK_Dept.equals(str) || StartWorkAttr.PRI.equals(str)
							|| StartWorkAttr.Rec.equals(str) || StartWorkAttr.Title.equals(str)) {
						continue;
					} else {
					}

					if (wk.getRow().containsKey(str)) {
						wk.SetValByKey(str, htWork.get(str));
					} else {
						wk.getRow().put(str, htWork.get(str));
					}
				}
			}

			wk.setRec(WebUser.getNo());
			wk.setRecText(WebUser.getName());
			wk.SetValByKey(StartWorkAttr.FK_Dept, WebUser.getFK_Dept());
			wk.BeforeSave();
			wk.Save();

			// C# TO JAVA CONVERTER
			/// #region 保存从表
			if (dsDtls != null) {
				// 保存从表
				for (DataTable dt : dsDtls.Tables) {
					for (MapDtl dtl : MapDtls.convertMapDtls(wk.getHisMapDtls())) {
						if (dt.TableName != dtl.getNo()) {
							continue;
						}
						// 获取dtls
						GEDtls daDtls = new GEDtls(dtl.getNo());
						daDtls.Delete(GEDtlAttr.RefPK, workID); // 清除现有的数据.

						GEDtl daDtl = (GEDtl) ((daDtls.getGetNewEntity() instanceof GEDtl) ? daDtls.getGetNewEntity()
								: null);
						daDtl.setRefPK((new Long(workID)).toString());

						// 为从表复制数据.
						for (DataRow dr : dt.Rows) {
							// 明细列.
							for (DataColumn dc : dt.Columns) {
								// 设置属性.
								daDtl.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName));
							}

							daDtl.ResetDefaultVal();

							daDtl.setRefPK((new Long(workID)).toString());
							daDtl.setRDT(DataType.getCurrentDataTime());

							// 执行保存.
							try {
								if (daDtl.getOID() == 0) {
									daDtl.InsertAsOID(DBAccess.GenerOID("Dtl")); // 插入数据.
								} else {
									daDtl.InsertAsOID(daDtl.getOID()); // 插入数据.
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			// C# TO JAVA CONVERTER
			/// #endregion 保存从表结束

			// C# TO JAVA CONVERTER
			/// #region 更新发送参数.
			if (htWork != null) {
				String paras = "";
				for (String key : htWork.keySet()) {
					paras += "@" + key + "=" + htWork.get(key).toString();
				}

				if (StringHelper.isNullOrEmpty(paras) == false) {
					String dbstr = SystemConfig.getAppCenterDBVarStr();
					Paras ps = new Paras();
					ps.SQL = "UPDATE WF_GenerWorkerlist SET AtPara=" + dbstr + "Paras WHERE WorkID=" + dbstr
							+ "WorkID AND FK_Node=" + dbstr + "FK_Node";
					ps.Add(GenerWorkerListAttr.Paras, paras);
					ps.Add(GenerWorkerListAttr.WorkID, workID);
					ps.Add(GenerWorkerListAttr.FK_Node, nd.getNodeID());
					DBAccess.RunSQL(ps);
				}
			}
			// C# TO JAVA CONVERTER
			/// #endregion 更新发送参数.

			if (nd.getSaveModel() == SaveModel.NDAndRpt) {
				// 如果保存模式是节点表与Node与Rpt表.
				WorkNode wn = new WorkNode(wk, nd);
				GERpt rptGe = nd.getHisFlow().getHisGERpt();
				rptGe.SetValByKey("OID", workID);
				wn.rptGe = rptGe;
				if (rptGe.RetrieveFromDBSources() == 0) {
					rptGe.SetValByKey("OID", workID);
					wn.DoCopyRptWork(wk);

					if (Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
						rptGe.SetValByKey(GERptAttr.FlowEmps, "@" + WebUser.getNo() + "," + WebUser.getName());
					}

					if (Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDOnly) {
						rptGe.SetValByKey(GERptAttr.FlowEmps, "@" + WebUser.getNo());
					}

					if (Glo.getUserInfoShowModel() == UserInfoShowModel.UserNameOnly) {
						rptGe.SetValByKey(GERptAttr.FlowEmps, "@" + WebUser.getName());
					}

					rptGe.SetValByKey(GERptAttr.FlowStarter, WebUser.getNo());
					rptGe.SetValByKey(GERptAttr.FlowStartRDT, DataType.getCurrentDataTime());
					rptGe.SetValByKey(GERptAttr.WFState, 0);
					rptGe.SetValByKey(GERptAttr.FK_NY, DataType.getCurrentYearMonth());
					rptGe.SetValByKey(GERptAttr.FK_Dept, WebUser.getFK_Dept());
					rptGe.Insert();
				} else {
					wn.DoCopyRptWork(wk);
					rptGe.Update();
				}
			}
			// 获取表单树的数据
			BP.WF.WorkNode workNode = new WorkNode(workID, fk_node);
			Work treeWork = workNode.CopySheetTree();
			if (treeWork != null) {
				wk.Copy(treeWork);
			}

			// C# TO JAVA CONVERTER
			/// #region 为开始工作创建待办.
			if (nd.getIsStartNode() == true) {
				GenerWorkFlow gwf = new GenerWorkFlow();
				Flow fl = new Flow(fk_flow);
				if (fl.getDraftRole() == DraftRole.None) {
					return "保存成功";
				}

				// 规则设置为写入待办，将状态置为运行中，其他设置为草稿
				WFState wfState = fl.getDraftRole() == DraftRole.SaveToTodolist ? WFState.Runing : WFState.Draft;

				gwf.setWorkID(workID);
				int i = gwf.RetrieveFromDBSources();
				if (i == 0) {
					gwf.setFlowName(fl.getName());
					gwf.setFK_Flow(fk_flow);
					gwf.setFK_FlowSort(fl.getFK_FlowSort());

					gwf.setFK_Node(fk_node);
					gwf.setNodeName(nd.getName());
					gwf.setWFState(wfState);

					gwf.setFK_Dept(WebUser.getFK_Dept());
					gwf.setDeptName(WebUser.getFK_DeptName());
					gwf.setTitle(BP.WF.WorkNode.GenerTitle(fl, wk));
					gwf.setStarter(WebUser.getNo());
					gwf.setStarterName(WebUser.getName());
					gwf.setRDT(DataType.getCurrentDataTime());
					gwf.Insert();
					// 产生工作列表.
					GenerWorkerList gwl = new GenerWorkerList();
					gwl.setWorkID(workID);
					gwl.setFK_Emp(WebUser.getNo());
					gwl.setFK_EmpText(WebUser.getName());

					gwl.setFK_Node(fk_node);
					gwl.setFK_NodeText(nd.getName());
					gwl.setFID(0);

					gwl.setFK_Flow(fk_flow);
					gwl.setFK_Dept(WebUser.getFK_Dept());
					gwl.setSDT(DataType.getCurrentDataTime());
					gwl.setDTOfWarning(DataType.getCurrentDataTime());
					gwl.setRDT(DataType.getCurrentDataTime());
					gwl.setIsEnable(true);

					gwl.setIsPass(false);
					// gwl.Sender = WebUser.No;
					gwl.setPRI(gwf.getPRI());
					gwl.Insert();
				}
			}
			// C# TO JAVA CONVERTER
			/// #endregion 为开始工作创建待办

			return "保存成功.";
		} catch (RuntimeException ex) {
			return "保存失败:" + ex.getMessage();
		}
	}

	/**
	 * 保存流程表单
	 * 
	 * @param fk_mapdata
	 *            流程表单ID
	 * @param workID
	 *            工作ID
	 * @param htData
	 *            流程表单数据Key Value 格式存放.
	 * @return 返回执行信息
	 */
	public static void Node_SaveFlowSheet(String fk_mapdata, long workID, java.util.Hashtable htData) {
		Node_SaveFlowSheet(fk_mapdata, workID, htData, null);
	}

	/**
	 * 保存流程表单
	 * 
	 * @param fk_mapdata
	 *            流程表单ID
	 * @param workID
	 *            工作ID
	 * @param htData
	 *            流程表单数据Key Value 格式存放.
	 * @param workDtls
	 *            从表数据
	 * @return 返回执行信息
	 */
	public static void Node_SaveFlowSheet(String fk_mapdata, long workID, java.util.Hashtable<String, Object> htData,
			DataSet workDtls) {
		MapData md = new MapData(fk_mapdata);
		GEEntity en = md.getHisGEEn();
		en.SetValByKey("OID", workID);
		int i = en.RetrieveFromDBSources();

		for (String key : htData.keySet()) {
			en.SetValByKey(key, htData.get(key).toString());
		}

		en.SetValByKey("OID", workID);

		FrmEvents fes = md.getFrmEvents();
		fes.DoEventNode(FrmEventList.SaveBefore, en);
		if (i == 0) {
			en.Insert();
		} else {
			en.Update();
		}

		if (workDtls != null) {
			MapDtls dtls = new MapDtls(fk_mapdata);
			// 保存从表
			for (DataTable dt : workDtls.Tables) {
				for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
					if (dt.TableName != dtl.getNo()) {
						continue;
					}
					// 获取dtls
					GEDtls daDtls = new GEDtls(dtl.getNo());
					daDtls.Delete(GEDtlAttr.RefPK, workID); // 清除现有的数据.

					GEDtl daDtl = (GEDtl) ((daDtls.getGetNewEntity() instanceof GEDtl) ? daDtls.getGetNewEntity()
							: null);
					daDtl.setRefPK((new Long(workID)).toString());

					// 为从表复制数据.
					for (DataRow dr : dt.Rows) {
						daDtl.ResetDefaultVal();
						daDtl.setRefPK((new Long(workID)).toString());

						// 明细列.
						for (DataColumn dc : dt.Columns) {
							// 设置属性.
							daDtl.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName));
						}
						try {
							daDtl.InsertAsOID(DBAccess.GenerOID("Dtl"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 插入数据.
					}
				}
			}
		}
		fes.DoEventNode(FrmEventList.SaveAfter, en);
	}

	/**
	 * 从任务池里取出来一个子任务
	 * 
	 * @param nodeid
	 *            节点编号
	 * @param workid
	 *            工作ID
	 * @param empNo
	 *            取出来的人员编号
	 */
	public static boolean Node_TaskPoolTakebackOne(long workid) {
		if (Glo.getIsEnableTaskPool() == false) {
			throw new RuntimeException("@配置没有设置成共享任务池的状态。");
		}

		GenerWorkFlow gwf = new GenerWorkFlow(workid);
		if (gwf.getTaskSta() == TaskSta.None) {
			throw new RuntimeException("@该任务非共享任务。");
		}

		if (gwf.getTaskSta() == TaskSta.Takeback) {
			throw new RuntimeException("@该任务已经被其他人取走。");
		}

		// 更新状态。
		gwf.setTaskSta(TaskSta.Takeback);
		gwf.Update();

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		// 设置已经被取走的状态。
		ps.SQL = "UPDATE WF_GenerWorkerlist SET IsEnable=-1 WHERE IsEnable=1 AND WorkID=" + dbstr
				+ "WorkID AND FK_Node=" + dbstr + "FK_Node AND FK_Emp!=" + dbstr + "FK_Emp ";
		ps.Add(GenerWorkerListAttr.WorkID, workid);
		ps.Add(GenerWorkerListAttr.FK_Node, gwf.getFK_Node());
		ps.Add(GenerWorkerListAttr.FK_Emp, WebUser.getNo());
		int i = DBAccess.RunSQL(ps);

		BP.WF.Dev2Interface.WriteTrackInfo(gwf.getFK_Flow(), gwf.getFK_Node(), workid, 0,
				"任务被" + WebUser.getName() + "从任务池取走.", "获取");
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 放入一个任务
	 * 
	 * @param nodeid
	 *            节点编号
	 * @param workid
	 *            工作ID
	 * @param empNo
	 *            人员ID
	 */
	public static void Node_TaskPoolPutOne(long workid) {
		if (Glo.getIsEnableTaskPool() == false) {
			throw new RuntimeException("@配置没有设置成共享任务池的状态。");
		}

		GenerWorkFlow gwf = new GenerWorkFlow(workid);
		if (gwf.getTaskSta() == TaskSta.None) {
			throw new RuntimeException("@该任务非共享任务。");
		}

		if (gwf.getTaskSta() == TaskSta.Sharing) {
			throw new RuntimeException("@该任务已经是共享状态。");
		}

		// 更新 状态。
		gwf.setTaskSta(TaskSta.Sharing);
		gwf.Update();

		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		// 设置已经被取走的状态。
		ps.SQL = "UPDATE WF_GenerWorkerlist SET IsEnable=1 WHERE IsEnable=-1 AND WorkID=" + dbstr + "WorkID ";
		ps.Add(GenerWorkerListAttr.WorkID, workid);
		int i = DBAccess.RunSQL(ps);
		if (i == 0) {
			throw new RuntimeException("@流程数据错误,不应当更新不到数据。");
		}

		BP.WF.Dev2Interface.WriteTrackInfo(gwf.getFK_Flow(), gwf.getFK_Node(), workid, 0,
				"任务被" + WebUser.getName() + "放入了任务池.", "放入");
	}

	/**
	 * 增加下一步骤的接受人(用于当前步骤向下一步骤发送时增加接受人)
	 * 
	 * @param workID
	 *            工作ID
	 * @param formNodeID
	 *            节点ID
	 * @param emps
	 *            如果多个就用逗号分开
	 */
	public static void Node_AddNextStepAccepters(long workID, int formNodeID, String emps) {
		SelectAccper sa = new SelectAccper();
		sa.Delete(SelectAccperAttr.FK_Node, formNodeID, SelectAccperAttr.WorkID, workID);
		emps = emps.replace(" ", "");
		emps = emps.replace(";", ",");
		emps = emps.replace("@", ",");
		String[] strs = emps.split("[,]", -1);

		Emp empEn = new Emp();
		for (String emp : strs) {
			if (StringHelper.isNullOrEmpty(emp)) {
				continue;
			}
			sa.setMyPK(formNodeID + "_" + workID + "_" + emp);

			empEn.setNo(emp);
			int i = empEn.RetrieveFromDBSources();
			if (i == 0) {
				continue;
			}

			sa.setFK_Emp(emp);
			sa.setEmpName(empEn.getName());

			sa.setFK_Node(formNodeID);
			sa.setWorkID(workID);
			sa.Insert();
		}
	}

	/**
	 * 增加下一步骤的接受人(用于当前步骤向下一步骤发送时增加接受人)
	 * 
	 * @param workID
	 *            工作ID
	 * @param formNodeID
	 *            从节点ID
	 * @param emp
	 *            接收人
	 * @param tag
	 *            分组维度，可以为空.是为了分流节点向下发送时候，可能有一个工作人员两个或者两个以上的子线程的情况出现。 tag
	 *            是个维度，这个维度可能是一个类别，一个批次，一个标记，总之它是一个字符串。详细:
	 *            http://bbs.ccflow.org/showtopic-3065.jsp
	 */
	public static void Node_AddNextStepAccepter(long workID, int formNodeID, String emp, String tag) {
		SelectAccper sa = new SelectAccper();
		sa.Delete(SelectAccperAttr.FK_Node, formNodeID, SelectAccperAttr.WorkID, workID, SelectAccperAttr.FK_Emp, emp,
				SelectAccperAttr.Tag, tag);

		Emp empEn = new Emp();
		sa.setMyPK(formNodeID + "_" + workID + "_" + emp + "_" + tag);
		empEn.setNo(emp);
		sa.setTag(tag);
		sa.setFK_Emp(emp);
		sa.setEmpName(empEn.getName());
		sa.setFK_Node(formNodeID);
		sa.setWorkID(workID);
		sa.Insert();
	}

	/**
	 * 节点工作挂起
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param way
	 *            挂起方式
	 * @param reldata
	 *            解除挂起日期(可以为空)
	 * @param hungNote
	 *            挂起原因
	 * @return 返回执行信息
	 */
	public static String Node_HungUpWork(String fk_flow, long workid, int wayInt, String reldata, String hungNote) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		HungUpWay way = HungUpWay.forValue(wayInt);
		BP.WF.WorkFlow wf = new WorkFlow(fk_flow, workid);
		return wf.DoHungUp(way, reldata, hungNote);
	}

	/**
	 * 节点工作取消挂起
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param msg
	 *            取消挂起原因
	 * @return 执行信息
	 */
	public static void Node_UnHungUpWork(String fk_flow, long workid, String msg) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		BP.WF.WorkFlow wf = new WorkFlow(fk_flow, workid);
		wf.DoUnHungUp();
	}

	/**
	 * 获取该节点上的挂起时间
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @return 返回时间串，如果没有挂起的动作就抛出异常.
	 */
	public static Long Node_GetHungUpTimeSpan(String flowNo, int nodeID, long workid) {
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();

		String instr = ActionType.HungUp.getValue() + "," + ActionType.UnHungUp.getValue();
		Paras ps = new Paras();
		ps.SQL = "SELECT * FROM ND" + Integer.parseInt(flowNo) + "Track WHERE WorkID=" + dbstr + "WorkID AND "
				+ TrackAttr.ActionType + " in (" + instr + ")  and  NDFrom=" + dbstr + "NDFrom ";
		ps.Add(TrackAttr.WorkID, workid);
		ps.Add(TrackAttr.NDFrom, nodeID);
		DataTable dt = DBAccess.RunSQLReturnTable(ps);

		java.util.Date dtStart = new java.util.Date();
		java.util.Date dtEnd = new java.util.Date();
		for (DataRow item : dt.Rows) {
			ActionType at = (ActionType) item.getValue(TrackAttr.ActionType);

			// 挂起时间.
			if (at == ActionType.HungUp) {
				dtStart = DataType.ParseSysDateTime2DateTime(item.getValue(TrackAttr.RDT).toString());
			}

			// 解除挂起时间.
			if (at == ActionType.UnHungUp) {
				dtEnd = DataType.ParseSysDateTime2DateTime(item.getValue(TrackAttr.RDT).toString());
			}
		}

		Long ts = dtEnd.getTime() - dtStart.getTime();
		return ts;
	}

	/**
	 * 自动加签 可指定人员
	 * 
	 * @param workid
	 *            工作ID
	 * @param askfor
	 *            加签方式
	 * @param askForEmp
	 *            请求人员
	 * @param askForNote
	 *            内容
	 * @param operateEmp
	 *            操作人员
	 * @return
	 */
	public static String Node_Askfor(long workid, AskforHelpSta askforSta, String askForEmp, String askForNote,
			String operateEmp, String operateEmpName) {

		Emp emp = new Emp(askForEmp);
		GenerWorkFlow gwf = new GenerWorkFlow(workid);
		// throw new Exception("@该工作属于抢办工作，您不能执行加签。");

		if (Flow_IsCanDoCurrentWork(gwf.getFK_Node(), gwf.getWorkID(), operateEmp) == false) {
			throw new RuntimeException("@当前的工作已经被别人处理或者您没有处理该工作的权限.");
		}

		gwf.setWFState(WFState.Askfor); // 更新流程为加签状态.
		gwf.Update();

		// 设置当前状态为 2 表示加签状态.
		GenerWorkerLists gwls = new GenerWorkerLists(workid, gwf.getFK_Node());
		if (gwls.Contains(GenerWorkerListAttr.FK_Emp, askForEmp, GenerWorkerListAttr.IsEnable, 0) == true) {
			throw new RuntimeException("加签失败，您选择的加签人可以处理当前的工作。");
		}

		for (GenerWorkerList item : GenerWorkerLists.convertGenerWorkerLists(gwls)) {
			if (item.getIsEnable() == false) {
				continue;
			}

			if (item.getFK_Emp().equals(operateEmp)) {
				// GenerWorkerList gwl = gwls[0] as GenerWorkerList;
				item.setIsPassInt(askforSta.getValue());
				item.Update();

				// 更换主键后，执行insert ,让被加签人有代办工作.
				item.setIsPassInt(0);
				item.setFK_Emp(emp.getNo());
				item.setFK_EmpText(emp.getName());
				try {
					item.Insert();
				} catch (java.lang.Exception e) {
					item.Update();
				}
			} else {
				if (item.getIsCC() == true) // 如果是抄送的，就可以看到的.
				{
					item.setIsEnable(false); // = (int)askforSta;
				}
				item.Update();
			}
		}

		BP.WF.Dev2Interface.WriteTrack(gwf.getFK_Flow(), gwf.getFK_Node(), workid, gwf.getFID(), askForNote,
				ActionType.AskforHelp, "", null, null, operateEmp, operateEmpName);
		Flow fl = new Flow(gwf.getFK_Flow());
		// 更新状态.
		DBAccess.RunSQL(
				"UPDATE " + fl.getPTable() + " SET WFState=" + WFState.Askfor.getValue() + " WHERE OID=" + workid);

		// 设置成工作未读。
		BP.WF.Dev2Interface.Node_SetWorkUnRead(gwf.getFK_Node(), workid, operateEmp);

		String msg = "您的工作已经提交给(" + askForEmp + " " + emp.getName() + ") 加签了。";

		// 加签后事件
		Node hisNode = new Node(gwf.getFK_Node());
		Work currWK = hisNode.getHisWork();
		currWK.setOID(gwf.getWorkID());
		currWK.Retrieve();
		msg += fl.DoFlowEventEntity(EventListOfNode.AskerAfter, hisNode, currWK, null) == null ? ""
				: fl.DoFlowEventEntity(EventListOfNode.AskerAfter, hisNode, currWK, null);
		return msg;
	}

	/**
	 * 执行加签(手动插入待办) modify by venson 20170822
	 * 
	 * @param workid
	 *            工作ID
	 * @param askfor
	 *            加签方式
	 * @param askForEmp
	 *            请求人员
	 * @param askForNote
	 *            内容
	 * @return
	 */
	public static String Node_Askfor(long workid, AskforHelpSta askforSta, String askForEmp, String askForNote) {

		GenerWorkFlow gwf = new GenerWorkFlow(workid);
		int nd = gwf.getFK_Node();

		// 设置当前状态为 2 表示加签状态.
		String msg = null;
		String userNo = WebUser.getNo();
		GenerWorkerLists gwls = new GenerWorkerLists(workid, nd, userNo);
		ArrayList<GenerWorkerList> wls = GenerWorkerLists.convertGenerWorkerLists(gwls);

		StringBuilder mesg = new StringBuilder();
		if (wls != null && wls.size() > 0) {

			try {
				GenerWorkerList generWorkerList = wls.get(0);
				// 如果加签后由我直接发送
				if (askforSta == AskforHelpSta.AfterDealSend) {
					generWorkerList.setIsPassInt(1);
					generWorkerList.Update();
				}
				String[] users;
				if (askForEmp.contains(",")) {
					users = askForEmp.split(",");
				} else {
					users = new String[] { askForEmp };
				}
				Node ndNode = new Node(nd);
				// 判断处理模式是否属于队列模式
				boolean isOrder = TodolistModel.Order == ndNode.getTodolistModel() ? true : false;
				// 新插入的ispass值默认从52开始计数
				int ispass = 52;
				int len = users.length;
				// 所有待办
				ArrayList<GenerWorkerList> allls = GenerWorkerLists
						.convertGenerWorkerLists(new GenerWorkerLists(workid, nd));
				Date now = new Date();
				String date = DateUtils.format(now,
						DateUtils.YEAR_MONTH_DAY_PATTERN_MIDLINE + " " + DateUtils.HOUR_MINUTE_SECOND_PATTERN);
				String todoEmps = gwf.getTodoEmps();
				int empsNum = gwf.getTodoEmpsNum();
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < len; i++) {
					String user = users[i];
					if (StringUtils.isNotBlank(user)) {
						Emp emp = new Emp(user);
						boolean hasRecord = false;
						for (GenerWorkerList agwl : allls) {
							// 查找是否已经存在
							if (agwl.getFK_Emp().equals(user)) {
								agwl.setIsPassInt(0);
								agwl.setRDT(date);
								agwl.setCDT(date);
								agwl.Update();
								hasRecord = true;
								break;
							}
						}
						if (!hasRecord) {
							generWorkerList.setSender(WebUser.getNo() + "," + WebUser.getName());
							generWorkerList.setFK_Emp(emp.getNo());
							generWorkerList.setFK_Dept(emp.getFK_Dept());
							generWorkerList.setFK_EmpText(emp.getName());
							generWorkerList.setRDT(date);
							generWorkerList.setCDT(date);
							generWorkerList.setIsPassInt(0);
							// 如果是队列模式并且有多位处理人时，将从第二位处理人的ispass值设置为节点累加值
							if (isOrder && i >= 1) {
								generWorkerList.setIsPassInt(ispass);
								ispass++;
							}
							// 将当前处理人加入到处理记录中
							if (!todoEmps.contains(emp.getNo())) {
								buffer.append(emp.getNo() + "," + emp.getName() + ";");
								empsNum++;
							}
							generWorkerList.Insert();
						}

						mesg.append(emp.getNo() + " " + emp.getName() + ",");
					}

				}
				if (buffer.length() > 0) {
					gwf.setTodoEmps(todoEmps + buffer.toString());
					gwf.setTodoEmpsNum(empsNum);
					gwf.Update();
				}

			} catch (Exception e) {
				msg = "您加签的成员已经存在！！！！";
			}
		}
		if (mesg.length() > 3) {
			msg = "您的工作已经提交给(" + mesg.delete(mesg.length() - 1, mesg.length()) + ")加签了。";
		}

		return msg;
	}

	/**
	 * 答复加签信息
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            FID
	 * @param replyNote
	 *            答复信息
	 * @return
	 */
	public static String Node_AskforReply(String fk_flow, int fk_node, long workid, long fid, String replyNote) {
		// 把回复信息临时的写入 流程注册信息表以便让发送方法获取这个信息写入日志.
		GenerWorkFlow gwf = new GenerWorkFlow(workid);
		gwf.setParas_AskForReply(replyNote);
		gwf.Update();

		// 执行发送, 在发送的方法里面已经做了判断了,并且把 回复的信息写入了日志.
		String info = BP.WF.Dev2Interface.Node_SendWork(fk_flow, workid).ToMsgOfHtml();

		Node node = new Node(fk_node);
		// 恢复加签后执行事件
		info += node.getHisFlow().DoFlowEventEntity(EventListOfNode.AskerReAfter, node, node.getHisWork(), null);
		return info;
	}

	/**
	 * 工作移交
	 * 
	 * @param workid
	 *            工作ID
	 * @param toEmp
	 *            移交到人员(只给移交给一个人)
	 * @param msg
	 *            移交消息
	 */
	public static String Node_Shift(String flowNo, int nodeID, long workID, long fid, String toEmp, String msg) {
		// 人员.
		Emp emp = new Emp(toEmp);
		Node nd = new Node(nodeID);

		if (nd.getTodolistModel() == TodolistModel.Order || nd.getTodolistModel() == TodolistModel.Teamup) {
			// 如果是队列模式，或者是协作模式.
			try {
				String sql = "UPDATE WF_GenerWorkerlist SET FK_Emp='" + emp.getNo() + "', FK_EmpText='" + emp.getName()
						+ "' WHERE FK_Emp='" + WebUser.getNo() + "' AND FK_Node=" + nodeID + " AND WorkID=" + workID;
				BP.DA.DBAccess.RunSQL(sql);
			} catch (java.lang.Exception e) {
				return "@移交失败，您所移交的人员(" + emp.getNo() + " " + emp.getName() + ")已经在代办列表里.";
			}

			// 记录日志.
			Glo.AddToTrack(ActionType.Shift, nd.getFK_Flow(), workID, fid, nd.getNodeID(), nd.getName(),
					WebUser.getNo(), WebUser.getName(), nd.getNodeID(), nd.getName(), toEmp, emp.getName(), msg, null);

			String info = "@工作移交成功。@您已经成功的把工作移交给：" + emp.getNo() + " , " + emp.getName();

			// 移交后事件
			info += "@" + nd.getHisFlow().DoFlowEventEntity(EventListOfNode.ShitAfter, nd, nd.getHisWork(), null);

			info += "@<a href='" + Glo.getCCFlowAppPath() + "WF/MyFlowInfo.jsp?DoType=UnShift&FK_Flow="
					+ nd.getFK_Flow() + "&WorkID=" + workID + "&FK_Node=" + nodeID + "&FID=" + fid
					+ "' ><img src='WF/Img/UnDo.gif' border=0 />撤消工作移交</a>.";
			return info;

		}

		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workID);
		if (gwf.RetrieveFromDBSources() == 0) {
			// 说明开始节点数据表单移交.
			gwf.setWorkID(workID);
			gwf.setTitle("由" + WebUser.getNo() + " ; " + WebUser.getName() + ", 在(" + DataType.getCurrentDataCNOfShort()
					+ ")移交来的工作");
			gwf.setFK_Dept(WebUser.getFK_Dept());
			gwf.setFK_Flow(flowNo);

			Flow fl = new Flow(flowNo);
			gwf.setFK_FlowSort(fl.getFK_FlowSort());
			gwf.setFK_Node(nodeID);
			gwf.setStarter(emp.getNo());
			gwf.setStarterName(emp.getName());
			gwf.setWFState(WFState.Shift);
			gwf.setTodoEmps(toEmp);
			gwf.setTodoEmpsNum(1);
			gwf.setRDT(DataType.getCurrentDataTime());
			gwf.setNodeName("");
			gwf.setFlowName(fl.getName());
			gwf.setEmps(toEmp);
			gwf.setDeptName(WebUser.getFK_DeptName());
			gwf.Insert();

			GenerWorkerList gwl = new GenerWorkerList();
			gwl.setWorkID(workID);
			gwl.setFK_Dept(WebUser.getFK_Dept());

			// gwl.FK_DeptT = WebUser.FK_DeptName;
			gwl.setFK_Node(nodeID);
			gwl.setFK_NodeText(nd.getName());

			gwl.setFK_Emp(toEmp);
			gwl.setFK_EmpText(emp.getName());

			gwl.setFK_Flow(flowNo);

			gwl.setIsPass(false);
			gwl.setIsPassInt(0);
			gwl.setIsRead(false);
			gwl.setPressTimes(0);
			gwl.setRDT(gwf.getRDT());
			gwl.setSDT(gwf.getRDT());
			// gwl.Sender = WebUser.No;
			gwl.Insert();
		} else {
			if (gwf.getWFSta() == WFSta.Complete) {
				throw new RuntimeException("@流程已经完成，您不能执行移交了。");
			}

			// 删除当前非配的工作。
			// 已经非配或者自动分配的任务。
			// 设置所有的工作人员为不可处理.
			String dbStr = SystemConfig.getAppCenterDBVarStr();
			Paras ps = new Paras();
			ps.SQL = "UPDATE WF_GenerWorkerlist SET IsEnable=0  WHERE WorkID=" + dbStr + "WorkID AND FK_Node=" + dbStr
					+ "FK_Node";
			ps.Add(GenerWorkerListAttr.WorkID, workID);
			ps.Add(GenerWorkerListAttr.FK_Node, nodeID);
			DBAccess.RunSQL(ps);

			// 设置被移交人的FK_Emp 为当前处理人，（有可能被移交人不在工作列表里，就返回0.）
			ps = new Paras();
			ps.SQL = "UPDATE WF_GenerWorkerlist SET IsEnable=1  WHERE WorkID=" + dbStr + "WorkID AND FK_Node=" + dbStr
					+ "FK_Node AND FK_Emp=" + dbStr + "FK_Emp";
			ps.Add(GenerWorkerListAttr.WorkID, workID);
			ps.Add(GenerWorkerListAttr.FK_Node, nodeID);
			ps.Add(GenerWorkerListAttr.FK_Emp, toEmp);
			int i = DBAccess.RunSQL(ps);

			GenerWorkerLists wls = null;
			GenerWorkerList wl = null;
			if (i == 0) {
				// 说明: 用其它的岗位上的人来处理的，就给他增加共享工作。
				wls = new GenerWorkerLists(workID, nodeID);
				if (wls.size() == 0) {
					throw new RuntimeException("@流程引擎 GenerWorkerLists 数据丢失, workID=" + workID + ",nodeID=" + nodeID);
				} else {
					wl = (GenerWorkerList) ((wls.get(0) instanceof GenerWorkerList) ? wls.get(0) : null);
				}

				wl.setFK_Emp(toEmp.toString());
				wl.setFK_EmpText(emp.getName());
				wl.setIsEnable(true);
				wl.setIsRead(false);
				wl.Insert();

				// 清除工作者，为转发消息所用.
				wls.clear();
				wls.AddEntity(wl);
			}

			ps = new Paras();
			ps.SQL = "UPDATE WF_GenerWorkFlow SET WFState=" + WFState.Shift.getValue() + ", WFSta="
					+ WFSta.Runing.getValue() + "   WHERE WorkID=" + dbStr + "WorkID ";
			ps.Add(GenerWorkerListAttr.WorkID, workID);
			DBAccess.RunSQL(ps);
		}

		ShiftWork sf = new ShiftWork();
		sf.setWorkID(workID);
		sf.setFK_Node(nodeID);
		sf.setToEmp(toEmp);
		sf.setToEmpName(emp.getName());
		sf.setNote(msg);
		sf.setFK_Emp(WebUser.getNo());
		sf.setFK_EmpName(WebUser.getName());
		sf.Insert();
		// 记录日志.
		Glo.AddToTrack(ActionType.Shift, nd.getFK_Flow(), workID, gwf.getFID(), nd.getNodeID(), nd.getName(),
				WebUser.getNo(), WebUser.getName(), nd.getNodeID(), nd.getName(), toEmp, emp.getName(), msg, null);

		// 发送邮件.
		BP.WF.Dev2Interface.Port_SendMsg(emp.getNo(), WebUser.getName() + "向您移交了工作" + gwf.getTitle(), "移交信息:" + msg,
				"SF" + workID + "_" + sf.getFK_Node(), SMSMsgType.Self, gwf.getFK_Flow(), gwf.getFK_Node(),
				gwf.getWorkID(), gwf.getFID());

		String inf1o = "@工作移交成功。@您已经成功的把工作移交给：" + emp.getNo() + " , " + emp.getName();

		// 移交后事件
		inf1o += "@" + nd.getHisFlow().DoFlowEventEntity(EventListOfNode.ShitAfter, nd, nd.getHisWork(), null);

		inf1o += "@<a href='" + Glo.getCCFlowAppPath() + "WF/MyFlowInfo.jsp?DoType=UnShift&FK_Flow=" + nd.getFK_Flow()
				+ "&WorkID=" + workID + "&FK_Node=" + nodeID + "&FID=" + fid
				+ "' ><img src='Img/UnDo.gif' border=0 />撤消工作移交</a>.";
		return inf1o;
	}

	/**
	 * 撤销移交
	 * 
	 * @param flowNo
	 *            撤销编号
	 * @param workID
	 *            工作ID
	 * @return 返回撤销信息
	 */
	public static String Node_ShiftUn(String flowNo, long workID) {
		WorkFlow mwf = new WorkFlow(flowNo, workID);
		return mwf.DoUnShift();
	}

	/**
	 * 执行工作退回(退回指定的点)
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param fid
	 *            流程ID
	 * @param currentNodeID
	 *            当前节点ID
	 * @param returnToNodeID
	 *            退回到的工作ID
	 * @param returnToEmp
	 *            退回到人员
	 * @param msg
	 *            退回原因
	 * @param isBackToThisNode
	 *            退回后是否要原路返回？
	 * @return 执行结果，此结果要提示给用户。
	 * @throws IOException
	 */
	public static String Node_ReturnWork(String fk_flow, long workID, long fid, int currentNodeID, int returnToNodeID,
			String returnToEmp, String msg, boolean isBackToThisNode) throws IOException {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		WorkReturn wr = new WorkReturn(fk_flow, workID, fid, currentNodeID, returnToNodeID, returnToEmp,
				isBackToThisNode, msg);
		return wr.DoIt();
	}

	public static String Node_ReturnWork(String fk_flow, long workID, long fid, int currentNodeID, int returnToNodeID,
			String msg, boolean isBackToThisNode) throws IOException {
		return Node_ReturnWork(fk_flow, workID, fid, currentNodeID, returnToNodeID, null, msg, isBackToThisNode);
	}

	/**
	 * 获取当前工作的NodeID
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @return 指定工作的NodeID.
	 */
	public static int Node_GetCurrentNodeID(String fk_flow, long workid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		int nodeID = BP.DA.DBAccess.RunSQLReturnValInt(
				"SELECT FK_Node FROM WF_GenerWorkFlow WHERE WorkID=" + workid + " AND FK_Flow='" + fk_flow + "'", 0);
		if (nodeID == 0) {
			return Integer.parseInt(fk_flow + "01");
		}
		return nodeID;
	}

	/**
	 * 删除子线程
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fid
	 *            流程ID
	 * @param workid
	 *            工作ID
	 */
	public static void Node_FHL_KillSubFlow(String fk_flow, long fid, long workid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		WorkFlow wkf = new WorkFlow(fk_flow, workid);
		wkf.DoDeleteWorkFlowByReal(true);
	}

	/**
	 * 合流点驳回子线程
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fid
	 *            流程ID
	 * @param workid
	 *            子线程ID
	 * @param msg
	 *            驳回消息
	 */
	public static String Node_FHL_DoReject(String fk_flow, int NodeSheetfReject, long fid, long workid, String msg) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		WorkFlow wkf = new WorkFlow(fk_flow, workid);
		return wkf.DoReject(fid, NodeSheetfReject, msg);
	}

	/**
	 * 跳转审核取回
	 * 
	 * @param fromNodeID
	 *            从节点ID
	 * @param workid
	 *            工作ID
	 * @param tackToNodeID
	 *            取回到的节点ID
	 * @return
	 */
	public static String Node_Tackback(int fromNodeID, long workid, int tackToNodeID) {
		//
		// * 1,首先检查是否有此权限.
		// * 2, 执行工作跳转.
		// * 3, 执行写入日志.
		//
		Node nd = new Node(tackToNodeID);
		switch (nd.getHisDeliveryWay()) {
		case ByPreviousNodeFormEmpsField:
			break;
		}

		WorkNode wn = new WorkNode(workid, fromNodeID);
		String msg = wn.NodeSend(new Node(tackToNodeID), WebUser.getNo()).ToMsgOfHtml();
		wn.AddToTrack(ActionType.Tackback, WebUser.getNo(), WebUser.getName(), tackToNodeID, nd.getName(),
				"执行跳转审核的取回.");
		return msg;
	}

	/**
	 * 执行抄送已阅
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            流程节点
	 * @param workid
	 *            工作id
	 * @param fid
	 *            流程id
	 * @param checkNote
	 *            填写意见
	 */
	public static void Node_DoCCCheckNote(String fk_flow, int fk_node, long workid, long fid, String checkNote) {
		FrmWorkCheck fwc = new FrmWorkCheck(fk_node);

		BP.WF.Dev2Interface.WriteTrackWorkCheck(fk_flow, fk_node, workid, fid, checkNote, fwc.getFWCOpLabel());

		// 设置审核完成.
		BP.WF.Dev2Interface.Node_CC_SetSta(fk_node, workid, WebUser.getNo(), CCSta.CheckOver);

	}

	/**
	 * 设置是此工作为读取状态
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            WorkID
	 */
	public static void Node_SetWorkRead(int nodeID, long workid) {
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkerList SET IsRead=1 WHERE WorkID=" + dbstr + "WorkID AND FK_Node=" + dbstr
				+ "FK_Node AND FK_Emp=" + dbstr + "FK_Emp";
		ps.Add("WorkID", workid);
		ps.Add("FK_Node", nodeID);
		ps.Add("FK_Emp", WebUser.getNo());
		if (DBAccess.RunSQL(ps) == 0) {
			throw new RuntimeException("@设置的工作不存在，或者当前的登陆人员已经改变。");
		}

		// 判断当前节点的已读回执.
		Node nd = new Node(nodeID);
		if (nd.getReadReceipts() == ReadReceipts.None) {
			return;
		}

		boolean isSend = false;
		if (nd.getReadReceipts() == ReadReceipts.Auto) {
			isSend = true;
		}

		if (nd.getReadReceipts() == ReadReceipts.BySysField) {
			// 获取上一个节点ID
			Nodes fromNodes = nd.getFromNodes();
			int fromNodeID = 0;
			for (Node item : Nodes.convertNodes(fromNodes)) {
				ps = new Paras();
				ps.SQL = "SELECT FK_Node FROM WF_GenerWorkerlist  WHERE WorkID=" + dbstr + "WorkID AND FK_Node=" + dbstr
						+ "FK_Node ";
				ps.Add("WorkID", workid);
				ps.Add("FK_Node", item.getNodeID());
				DataTable dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() == 0) {
					continue;
				}
				fromNodeID = item.getNodeID();
				break;
			}
			if (fromNodeID == 0) {
				throw new RuntimeException("@没有找到它的上一步工作。");
			}

			try {
				ps = new Paras();
				ps.SQL = "SELECT " + WorkSysFieldAttr.SysIsReadReceipts + " FROM ND" + fromNodeID + "    WHERE OID="
						+ dbstr + "OID";
				ps.Add("OID", workid);
				DataTable dt1 = DBAccess.RunSQLReturnTable(ps);
				if (dt1.Rows.get(0).getValue(0).toString().equals("1")) {
					isSend = true;
				}
			} catch (RuntimeException ex) {
				throw new RuntimeException(
						"@流程设计错误:" + ex.getMessage() + " 在当前节点上个您设置了安上一步的表单字段决定是否回执，但是上一个节点表单中没有约定的字段。");
			}
		}

		if (nd.getReadReceipts() == ReadReceipts.BySDKPara) {
			// 如果是按开发者参数

			// 获取上一个节点ID
			Nodes fromNodes = nd.getFromNodes();
			int fromNodeID = 0;
			for (Node item : Nodes.convertNodes(fromNodes)) {
				ps = new Paras();
				ps.SQL = "SELECT FK_Node FROM WF_GenerWorkerlist  WHERE WorkID=" + dbstr + "WorkID AND FK_Node=" + dbstr
						+ "FK_Node ";
				ps.Add("WorkID", workid);
				ps.Add("FK_Node", item.getNodeID());
				DataTable dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() == 0) {
					continue;
				}

				fromNodeID = item.getNodeID();
				break;
			}
			if (fromNodeID == 0) {
				throw new RuntimeException("@没有找到它的上一步工作。");
			}

			String paras = BP.WF.Dev2Interface.GetFlowParas(fromNodeID, workid);
			if (StringHelper.isNullOrEmpty(paras)
					|| paras.contains("@" + WorkSysFieldAttr.SysIsReadReceipts + "=") == false) {
				throw new RuntimeException("@流程设计错误:在当前节点上个您设置了按开发者参数决定是否回执，但是没有找到该参数。");
			}

			// 开发者参数.
			if (paras.contains("@" + WorkSysFieldAttr.SysIsReadReceipts + "=1") == true) {
				isSend = true;
			}
		}

		if (isSend == true) {
			// 如果是自动的已读回执，就让它发送给当前节点的上一个发送人。

			// 获取流程标题.
			ps = new Paras();
			ps.SQL = "SELECT Title FROM WF_GenerWorkFlow WHERE WorkID=" + dbstr + "WorkID ";
			ps.Add("WorkID", workid);
			DataTable dt = DBAccess.RunSQLReturnTable(ps);
			String title = dt.Rows.get(0).getValue(0).toString();

			// 获取流程的发送人.
			ps = new Paras();
			ps.SQL = "SELECT " + GenerWorkerListAttr.Sender + " FROM WF_GenerWorkerlist WHERE WorkID=" + dbstr
					+ "WorkID AND FK_Node=" + dbstr + "FK_Node ";
			ps.Add("WorkID", workid);
			ps.Add("FK_Node", nodeID);
			dt = DBAccess.RunSQLReturnTable(ps);
			String sender = dt.Rows.get(0).getValue(0).toString();

			// 发送已读回执。
			BP.WF.Dev2Interface.Port_SendMsg(sender, "已读回执:" + title,
					"您发送的工作已经被" + WebUser.getName() + "在" + DataType.getCurrentDataTimeCNOfShort() + " 打开.",
					"RP" + workid + "_" + nodeID, SMSMsgType.Self, nd.getFK_Flow(), nd.getNodeID(), workid, 0);
		}
	}

	/**
	 * 设置工作未读取
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @param userNo
	 *            要设置的人
	 */
	public static void Node_SetWorkUnRead(int nodeID, long workid, String userNo) {
		String dbstr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
		Paras ps = new Paras();
		ps.SQL = "UPDATE WF_GenerWorkerList SET IsRead=0 WHERE WorkID=" + dbstr + "WorkID AND FK_Node=" + dbstr
				+ "FK_Node AND FK_Emp=" + dbstr + "FK_Emp";
		ps.Add("WorkID", workid);
		ps.Add("FK_Node", nodeID);
		ps.Add("FK_Emp", userNo);
		DBAccess.RunSQL(ps);
	}

	/**
	 * 设置工作未读取
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 */
	public static void Node_SetWorkUnRead(int nodeID, long workid) {
		Node_SetWorkUnRead(nodeID, workid, WebUser.getNo());
	}

	/**
	 * 更改流程属性
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param attr1
	 *            字段1
	 * @param v1
	 *            值1
	 * @param attr2
	 *            字段2(可为null)
	 * @param v2
	 *            值2(可为null)
	 * @return 执行结果
	 */
	public static String ChangeAttr_Flow(String fk_flow, String attr1, Object v1, String attr2, Object v2) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		Flow fl = new Flow(fk_flow);
		if (attr1 != null) {
			fl.SetValByKey(attr1, v1);
		}
		if (attr2 != null) {
			fl.SetValByKey(attr2, v2);
		}
		fl.Update();
		return "修改成功";
	}

	/**
	 * 获取按钮状态
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            流程ID
	 * @return 返回按钮状态
	 */
	public static ButtonState UI_GetButtonState(String fk_flow, int fk_node, long workid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		ButtonState bs = new ButtonState(fk_flow, fk_node, workid);
		return bs;
	}

	/**
	 * 打开退回窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static String UI_Window_Return(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		String url = Glo.getCCFlowAppPath() + "WF/WorkOpt/ReturnWork.jsp?FK_Flow=" + fk_flow + "&FK_Node=" + fk_node
				+ "&WorkID=" + workid + "&FID=" + fid;
		// System.Web.HttpContext.Current.Response.Redirect(url, true);
		return url;
	}

	/**
	 * 打开抄送窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_CC(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(), Glo.getCCFlowAppPath() + "WF/WorkOpt/CC.jsp?FK_Flow="
					+ fk_flow + "&FK_Node=" + fk_node + "&WorkID=" + workid + "&FID=" + fid, 800, 600);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 打开加签窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_AskForHelp(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		String tKey = DataType.dateToStr(new Date(), "MMddhhmmss");
		String urlr3 = Glo.getCCFlowAppPath() + "WF/WorkOpt/Askfor.jsp?FK_Node=" + fk_node + "&FID=" + fid + "&WorkID="
				+ workid + "&FK_Flow=" + fk_flow + "&s=" + tKey;
	}

	/**
	 * 打开挂起窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_HungUp(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(), Glo.getCCFlowAppPath() + "WF/WorkOpt/HungUp.jsp?FK_Flow="
					+ fk_flow + "&FK_Node=" + fk_node + "&WorkID=" + workid + "&FID=" + fid, 500, 400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 打开催办窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_Hurry(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(), Glo.getCCFlowAppPath() + "WF/Hurry.jsp?FK_Flow="
					+ fk_flow + "&FK_Node=" + fk_node + "&WorkID=" + workid + "&FID=" + fid, 500, 400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 打开跳转窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_JumpWay(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					Glo.getCCFlowAppPath() + "WF/JumpWaySmallSingle.jsp?FK_Flow=" + fk_flow + "&FK_Node=" + fk_node
							+ "&WorkID=" + workid + "&FID=" + fid,
					500, 400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 打开流程轨迹窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param nodeID
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_FlowChartTruck(String fk_flow, int nodeID, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					Glo.getCCFlowAppPath() + "WF/Chart.jsp?FK_Flow=" + fk_flow + "&WorkID=" + workid + "&FID=" + fid,
					500, 400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 下一步工作的接受人
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_Accepter(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);

		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					Glo.getCCFlowAppPath() + "WF/WorkOpt/Accepter.jsp?FK_Flow=" + fk_flow + "&FK_Node=" + fk_node
							+ "&WorkID=" + workid + "&FID=" + fid,
					500, 400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 打开流程图窗口
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param fk_node
	 *            当前节点编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_FlowChart(String fk_flow) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					Glo.getCCFlowAppPath() + "WF/Chart.jsp?FK_Flow=" + fk_flow, 500, 400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 打开OneWork
	 * 
	 * @param fk_flow
	 *            流程编号
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 */
	public static void UI_Window_OneWork(String fk_flow, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(), Glo.getCCFlowAppPath()
					+ "WF/WorkOpt/OneWork/Track.jsp?FK_Flow=" + fk_flow + "&WorkID=" + workid + "&FID=" + fid, 500,
					400);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 查看子线程信息
	 * 
	 * @param fk_flow
	 * @param fk_node
	 * @param workid
	 * @param fid
	 */
	public static void UI_Window_ThreadInfo(String fk_flow, int fk_node, long workid, long fid) {
		// 转化成编号.
		fk_flow = TurnFlowMarkToFlowNo(fk_flow);
		String key = DataType.dateToStr(new Date(), "yyyyMMddhhmmss");
		String url = Glo.getCCFlowAppPath() + "WF/ThreadDtl.jsp?FK_Node=" + fk_node + "&FID=" + fid + "&WorkID="
				+ workid + "&FK_Flow=" + fk_flow + "&s=" + key;
	}

	public static void UI_CCForm_XYSpan() {

	}

	/**
	 * 获得一个节点要转向的节点
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param ndFrom
	 *            节点从
	 * @param workid
	 *            工作ID
	 * @return 返回可以到达的节点
	 */
	public static Nodes WorkOpt_GetToNodes(String flowNo, int ndFrom, long workid, long FID) {
		Nodes nds = new Nodes();

		Node nd = new Node(ndFrom);
		Nodes toNDs = nd.getHisToNodes();

		Flow fl = nd.getHisFlow();
		GERpt rpt = fl.getHisGERpt();
		rpt.setOID(FID == 0 ? workid : FID);
		rpt.Retrieve();

		// 首先输出普通的节点
		for (Node mynd : Nodes.convertNodes(toNDs)) {
			if (mynd.getHisRunModel() == RunModel.SubThread) {
				continue; // 如果是子线程节点.
			}

			Cond cond = new Cond();
			int i = cond.Retrieve(CondAttr.FK_Node, nd.getNodeID(), CondAttr.ToNodeID, mynd.getNodeID());
			// 设置方向条件，就判断它。
			if (i > 0) {
				cond.setWorkID(workid);
				cond.en = rpt;
				if (cond.getIsPassed() == false) {
					continue;
				}
			}

			nds.AddEntity(mynd);
		}

		// 同表单子线程.
		for (Node mynd : Nodes.convertNodes(toNDs)) {
			if (mynd.getHisRunModel() != RunModel.SubThread) {
				continue; // 如果是子线程节点.
			}

			if (mynd.getHisSubThreadType() == SubThreadType.UnSameSheet) {
				continue; // 如果是异表单的分合流.
			}

			Cond cond = new Cond();
			int i = cond.Retrieve(CondAttr.FK_Node, nd.getNodeID(), CondAttr.ToNodeID, mynd.getNodeID());
			// 设置方向条件，就判断它。
			if (i > 0) {
				cond.setWorkID(workid);
				cond.en = rpt;
				if (cond.getIsPassed() == false) {
					continue;
				}
			}

			nds.AddEntity(mynd);
		}

		// 检查是否具有异表单的子线程.
		boolean isHave = false;
		for (Node mynd : Nodes.convertNodes(toNDs)) {
			if (mynd.getHisSubThreadType() == SubThreadType.UnSameSheet) {
				isHave = true;
			}
		}

		if (isHave) {
			Node myn1d = new Node();
			myn1d.setNodeID(0);
			myn1d.setName("可以分发启动的节点");
			nds.AddEntity(myn1d);

			// 增加异表单的子线程
			for (Node mynd : Nodes.convertNodes(toNDs)) {
				if (mynd.getHisSubThreadType() != SubThreadType.UnSameSheet) {
					continue;
				}

				Cond cond = new Cond();
				int i = cond.Retrieve(CondAttr.FK_Node, nd.getNodeID(), CondAttr.ToNodeID, mynd.getNodeID());
				// 设置方向条件，就判断它。
				if (i > 0) {
					cond.setWorkID(workid);
					cond.en = rpt;
					if (cond.getIsPassed() == false) {
						continue;
					}
				}

				nds.AddEntity(mynd);
			}
		}
		// 返回它.
		return nds;
	}

	/**
	 * 在节点选择转向功能界面，获得当前人员上一次选择的节点，在界面里让其自动选择。 以改善用户操作体验，就类似于默认记忆上一次的操作功能。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param nodeID
	 *            当前节点编号
	 * @return 返回上一次当前用户选择的节点,如果没有找到（当前用户第一次发送的情况下找不到）就返回0.
	 */
	public static int WorkOpt_ToNodes_GetLasterSelectNodeID(String flowNo, int nodeID) {
		String sql = "";
		switch (SystemConfig.getAppCenterDBType()) {
		case MSSQL:
		case Access:
			sql = "SELECT TOP 1 NDTo FROM ND" + Integer.parseInt(flowNo) + "Track WHERE EmpFrom='" + WebUser.getNo()
					+ "' AND NDFrom=" + nodeID + " AND (ActionType=" + ActionType.Forward.getValue() + " OR ActionType="
					+ ActionType.ForwardFL.getValue() + " OR ActionType=" + ActionType.SubFlowForward.getValue()
					+ ")  ORDER BY RDT DESC";
			break;
		case Oracle:
			sql = "SELECT NDTo FROM ND" + Integer.parseInt(flowNo) + "Track WHERE  RowNum=1 AND EmpFrom='"
					+ WebUser.getNo() + "' AND NDFrom=" + nodeID + " AND (ActionType=" + ActionType.Forward.getValue()
					+ " OR ActionType=" + ActionType.ForwardFL.getValue() + " OR ActionType="
					+ ActionType.SubFlowForward.getValue() + ")  ORDER BY RDT DESC";
			break;
		case MySQL:
			sql = "SELECT NDTo FROM ND" + Integer.parseInt(flowNo) + "Track WHERE  limit 0,1  AND EmpFrom='"
					+ WebUser.getNo() + "' AND NDFrom=" + nodeID + " AND (ActionType=" + ActionType.Forward.getValue()
					+ " OR ActionType=" + ActionType.ForwardFL.getValue() + " OR ActionType="
					+ ActionType.SubFlowForward.getValue() + ")  ORDER BY RDT DESC";
			break;
		case Informix:
			sql = "SELECT first 1 NDTo FROM ND" + Integer.parseInt(flowNo) + "Track WHERE EmpFrom='" + WebUser.getNo()
					+ "' AND NDFrom=" + nodeID + " AND (ActionType=" + ActionType.Forward.getValue() + " OR ActionType="
					+ ActionType.ForwardFL.getValue() + " OR ActionType=" + ActionType.SubFlowForward.getValue()
					+ ")  ORDER BY RDT DESC";
			break;
		default:
			throw new RuntimeException("@没有实现该类型的数据库支持.");
		}
		return BP.DA.DBAccess.RunSQLReturnValInt(sql, 0);
	}

	/**
	 * 发送到节点
	 * 
	 * @param flowNo
	 * @param node
	 * @param workid
	 * @param fid
	 * @param toNodes
	 */
	public static SendReturnObjs WorkOpt_SendToNodes(String flowNo, int nodeID, long workid, long fid, String toNodes) {
		// 把参数更新到数据库里面.
		GenerWorkFlow gwf = new GenerWorkFlow();
		gwf.setWorkID(workid);
		gwf.RetrieveFromDBSources();
		gwf.setParas_ToNodes(toNodes);
		gwf.Save();

		Node nd = new Node(nodeID);
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		wk.Retrieve();

		// 以下代码是从 MyFlow.jsp Send 方法copy 过来的，需要保持业务逻辑的一致性，所以代码需要保持一致.
		WorkNode firstwn = new WorkNode(wk, nd);
		String msg = "";
		SendReturnObjs objs = firstwn.NodeSend();
		return objs;
	}

	/**
	 * 获得接收人的数据源
	 * 
	 * @param FK_Flow
	 *            流程编号
	 * @param ToNode
	 *            到达节点ID
	 * @param WorkID
	 *            工作ID
	 * @param FID
	 *            流程ID
	 * @return
	 */
	public static DataSet WorkOpt_AccepterDB(String FK_Flow, int ToNode, long WorkID, long FID) {
		DataSet ds = new DataSet();
		Selector MySelector = new Selector(ToNode);
		switch (MySelector.getSelectorModel()) {
		case Station:
			DataTable dt = WorkOpt_Accepter_ByStation(ToNode);
			dt.TableName = "Port_Emp";
			ds.Tables.add(dt);
			// 部门表
			// string sql = "SELECT * FROM Port_Dept ";
			// DataTable dt1 = DBAccess.RunSQLReturnTable(sql);
			// dt1.TableName = "Port_Dept";
			// ds.Tables.Add(dt1);
			break;
		case SQL:
			ds = WorkOpt_Accepter_BySQL(ToNode);
			break;
		case Dept:
			ds = WorkOpt_Accepter_ByDept(ToNode);
			break;
		case Emp:
			ds = WorkOpt_Accepter_ByEmp(ToNode);
			break;
		case Url:
		default:
			break;
		}
		return ds;
	}

	/**
	 * 获取节点绑定岗位人员
	 * 
	 * @param ToNode
	 * @return
	 */
	public static DataTable WorkOpt_Accepter_ByStation(int ToNode) {
		if (ToNode == 0) {
			throw new RuntimeException(
					"@流程设计错误，没有转向的节点。举例说明: 当前是A节点。如果您在A点的属性里启用了[接受人]按钮，那么他的转向节点集合中(就是A可以转到的节点集合比如:A到B，A到C, 那么B,C节点就是转向节点集合)，必须有一个节点是的节点属性的[访问规则]设置为[由上一步发送人员选择]");
		}

		NodeStations stas = new NodeStations(ToNode);
		if (stas.size() == 0) {
			Node toNd = new Node(ToNode);
			throw new RuntimeException("@流程设计错误：设计员没有设计节点[" + toNd.getName() + "]，接受人的岗位范围。");
		}
		// 优先解决本部门的问题。
		String sql = "";
		if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
			sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
			sql += "SELECT FK_EMP FROM Port_DeptEmpStation WHERE FK_STATION ";
			sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" + ToNode + ") ";
			sql += ") AND a.No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ='" + WebUser.getFK_Dept() + "')";
			sql += " ORDER BY FK_DEPT ";
		} else {
			sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
			sql += "SELECT FK_EMP FROM " + BP.WF.Glo.getEmpStation() + " WHERE FK_STATION ";
			sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" + ToNode + ") ";
			sql += ") AND a.No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ='" + WebUser.getFK_Dept() + "')";
			sql += " ORDER BY FK_DEPT ";
		}
		DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
		if (dt.Rows.size() != 0) {
			return dt;
		}

		// 组织结构中所有岗位人员
		sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
		sql += "SELECT FK_EMP FROM " + BP.WF.Glo.getEmpStation() + " WHERE FK_STATION ";
		sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" + ToNode + ") ";
		sql += ") ORDER BY FK_DEPT ";
		return BP.DA.DBAccess.RunSQLReturnTable(sql);
	}

	/**
	 * 按sql方式
	 * 
	 */
	public static DataSet WorkOpt_Accepter_BySQL(int ToNode) {
		DataSet ds = new DataSet();
		Selector MySelector = new Selector(ToNode);
		String sqlGroup = MySelector.getSelectorP1();
		sqlGroup = sqlGroup.replace("@WebUser.No", WebUser.getNo());
		sqlGroup = sqlGroup.replace("@WebUser.Name", WebUser.getName());
		sqlGroup = sqlGroup.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());

		String sqlDB = MySelector.getSelectorP2();
		sqlDB = sqlDB.replace("@WebUser.No", WebUser.getNo());
		sqlDB = sqlDB.replace("@WebUser.Name", WebUser.getName());
		sqlDB = sqlDB.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());

		DataTable dtGroup = DBAccess.RunSQLReturnTable(sqlGroup);
		dtGroup.TableName = "Port_Dept";
		ds.Tables.add(dtGroup);
		DataTable dtDB = DBAccess.RunSQLReturnTable(sqlDB);
		dtDB.TableName = "Port_Emp";
		ds.Tables.add(dtDB);

		return ds;
	}

	/**
	 * 获取接收人选择器，按部门绑定
	 * 
	 * @param ToNode
	 * @return
	 */
	public static DataSet WorkOpt_Accepter_ByDept(int ToNode) {
		DataSet ds = new DataSet();
		String sqlGroup = "SELECT No,Name FROM Port_Dept WHERE No IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node='"
				+ ToNode + "')";
		String sqlDB = "SELECT No,Name, FK_Dept FROM Port_Emp WHERE FK_Dept IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node='"
				+ ToNode + "')";

		DataTable dtGroup = DBAccess.RunSQLReturnTable(sqlGroup);
		dtGroup.TableName = "Port_Dept";
		ds.Tables.add(dtGroup);

		DataTable dtDB = DBAccess.RunSQLReturnTable(sqlDB);
		dtDB.TableName = "Port_Emp";
		ds.Tables.add(dtDB);

		return ds;
	}

	/**
	 * 按BindByEmp 方式
	 * 
	 */
	public static DataSet WorkOpt_Accepter_ByEmp(int ToNode) {
		String sqlGroup = "SELECT No,Name FROM Port_Dept WHERE No IN (SELECT FK_Dept FROM Port_Emp WHERE No in(SELECT FK_EMP FROM WF_NodeEmp WHERE FK_Node='"
				+ ToNode + "'))";
		String sqlDB = "SELECT No,Name,FK_Dept FROM Port_Emp WHERE No in (SELECT FK_EMP FROM WF_NodeEmp WHERE FK_Node='"
				+ ToNode + "')";

		DataSet ds = new DataSet();
		DataTable dtGroup = DBAccess.RunSQLReturnTable(sqlGroup);
		dtGroup.TableName = "Port_Dept";
		ds.Tables.add(dtGroup);
		DataTable dtDB = DBAccess.RunSQLReturnTable(sqlDB);
		dtDB.TableName = "Port_Emp";
		ds.Tables.add(dtDB);

		return ds;
	}

	/**
	 * 设置指定的节点接受人
	 * 
	 * @param nodeID
	 *            节点ID
	 * @param workid
	 *            工作ID
	 * @param fid
	 *            流程ID
	 * @param emps
	 *            指定的人员集合zhangsan,lisi,wangwu
	 * @param isNextTime
	 *            是否下次自动设置
	 */
	public static void WorkOpt_SetAccepter(int toNode, long workid, long fid, String emps, boolean isNextTime) {
		SelectAccpers ens = new SelectAccpers();
		ens.Delete(SelectAccperAttr.FK_Node, toNode, SelectAccperAttr.WorkID, workid);

		// 下次是否记忆选择，清空掉。
		String sql = "UPDATE WF_SelectAccper SET " + SelectAccperAttr.IsRemember + " = 0 WHERE Rec='" + WebUser.getNo()
				+ "' AND IsRemember=1 AND FK_Node=" + toNode;
		BP.DA.DBAccess.RunSQL(sql);

		// 开始执行保存.
		String[] strs = emps.split("[,]", -1);
		for (String str : strs) {
			if (str == null || str.equals("")) {
				continue;
			}

			SelectAccper en = new SelectAccper();
			en.setMyPK(toNode + "_" + workid + "_" + str);
			en.setFK_Emp(str);
			en.setFK_Node(toNode);
			en.setWorkID(workid);
			en.setRec(WebUser.getNo());
			en.setIsRemember(isNextTime);
			en.Insert();
		}
	}

	/**
	 * 发送到节点
	 * 
	 * @param flowNo
	 * @param node
	 * @param workid
	 * @param fid
	 * @param toNodes
	 */
	public static SendReturnObjs WorkOpt_SendToEmps(String flowNo, int nodeID, long workid, long fid, int toNodeID,
			String toEmps, boolean isRememberMe) {
		WorkOpt_SetAccepter(toNodeID, workid, fid, toEmps, isRememberMe);

		Node nd = new Node(nodeID);
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		wk.Retrieve();

		// 以下代码是从 MyFlow.jsp Send 方法copy 过来的，需要保持业务逻辑的一致性，所以代码需要保持一致.
		WorkNode firstwn = new WorkNode(wk, nd);
		String msg = "";
		SendReturnObjs objs = firstwn.NodeSend();
		return objs;
	}

	public static String SaveImageAsFile(byte[] img, String pkval, String fk_Frm_Ele) {
		return "";
	}

	/**
	 * 上传文件.
	 * 
	 * @param FileByte
	 * @param fileName
	 * @return
	 */
	public static String UploadFile(byte[] FileByte, String fileName) {
		return null;
	}

}