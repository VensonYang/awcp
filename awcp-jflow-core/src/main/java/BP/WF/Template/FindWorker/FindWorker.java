package BP.WF.Template.FindWorker;

import BP.DA.DBAccess;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Paras;
import BP.Port.WebUser;
import BP.Tools.StringHelper;
import BP.WF.OSModel;
import BP.WF.WorkNode;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.TrackAttr;
import BP.WF.Template.Flow;
import BP.WF.Template.Node;
import BP.WF.Template.NodeStations;
import BP.WF.Template.AccepterRole.SelectAccper;
import BP.WF.Template.AccepterRole.SelectAccpers;
import BP.WF.Template.PubLib.ActionType;
import BP.WF.Template.PubLib.DeliveryWay;
import BP.WF.Template.PubLib.FlowAppType;
import BP.WF.Template.PubLib.RunModel;
import BP.WF.Template.PubLib.WhenNoWorker;

/**
 * 找人规则
 */
public class FindWorker {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 变量
	public WorkNode town = null;
	public WorkNode currWn = null;
	public Flow fl = null;
	private String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
	public Paras ps = null;
	private String JumpToEmp = null;
	private int JumpToNode = 0;
	private long WorkID = 0;

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion 变量

	/**
	 * 找人
	 * 
	 * @param fl
	 * @param currWn
	 * @param toWn
	 */
	public FindWorker() {
	}

	private DataTable FindByWorkFlowModel() {
		this.town = town;

		DataTable dt = new DataTable();
		dt.Columns.Add("No", String.class);
		String sql;
		String FK_Emp;

		// 如果执行了两次发送，那前一次的轨迹就需要被删除,这里是为了避免错误。
		ps = new Paras();
		ps.Add("WorkID", this.WorkID);
		ps.Add("FK_Node", town.getHisNode().getNodeID());
		ps.SQL = "DELETE FROM WF_GenerWorkerlist WHERE WorkID=" + dbStr
				+ "WorkID AND FK_Node =" + dbStr + "FK_Node";
		DBAccess.RunSQL(ps);

		// 如果指定特定的人员处理。
		if (StringHelper.isNullOrEmpty(JumpToEmp) == false) {
			String[] emps = JumpToEmp.split("[,]", -1);
			for (String emp : emps) {
				if (StringHelper.isNullOrEmpty(emp)) {
					continue;
				}
				DataRow dr = dt.NewRow();
				dr.setValue(0, emp);
				dt.Rows.add(dr);
			}
			return dt;
		}

		// 按上一节点发送人处理。
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByPreviousNodeEmp) {
			DataRow dr = dt.NewRow();
			dr.setValue(0, BP.Port.WebUser.getNo());
			dt.Rows.add(dr);
			return dt;
		}

		// 首先判断是否配置了获取下一步接受人员的sql.
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.BySQL
				|| town.getHisNode().getHisDeliveryWay() == DeliveryWay.BySQLAsSubThreadEmpsAndData) {
			if (town.getHisNode().getDeliveryParas().length() < 4) {
				throw new RuntimeException(
						"@您设置的当前节点按照SQL，决定下一步的接受人员，但是你没有设置sql.");
			}

			sql = town.getHisNode().getDeliveryParas();
			// sql = sql.clone().toString();
			sql = new String(sql);

			sql = BP.WF.Glo.DealExp(sql, this.currWn.rptGe, null);
			if (sql.contains("@")) {
				if(sql.contains("@WorkID")){
					
					sql = sql.replace("@WorkID"  , this.WorkID+"");
				}
				
				
				if (BP.WF.Glo.SendHTOfTemp != null) {
					for (Object key : BP.WF.Glo.SendHTOfTemp.keySet()) {
						sql = sql.replace("@" + key, BP.WF.Glo.SendHTOfTemp
								.get(key).toString());
					}
				}
			}

			dt = DBAccess.RunSQLReturnTable(sql);
			if (dt.Rows.size() == 0
					&& town.getHisNode().getHisWhenNoWorker() != WhenNoWorker.Skip) {
				throw new RuntimeException("@没有找到可接受的工作人员。@技术信息：执行的sql没有发现人员:"
						+ sql);
			}
			return dt;
		}

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按照明细表,作为子线程的接收人.
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByDtlAsSubThreadEmps) {
			if (this.town.getHisNode().getHisRunModel() != RunModel.SubThread) {
				throw new RuntimeException(
						"@您设置的节点接收人方式为：以分流点表单的明细表数据源确定子线程的接收人，但是当前节点非子线程节点。");
			}

			BP.Sys.Frm.MapDtls dtls = new BP.Sys.Frm.MapDtls(this.currWn
					.getHisNode().getNodeFrmID());
			String msg = null;
			for (BP.Sys.Frm.MapDtl dtl : BP.Sys.Frm.MapDtls
					.convertMapDtls(dtls)) {
				try {
					ps = new Paras();
					ps.SQL = "SELECT UserNo FROM " + dtl.getPTable()
							+ " WHERE RefPK=" + dbStr + "OID ORDER BY OID";
					ps.Add("OID", this.WorkID);
					dt = DBAccess.RunSQLReturnTable(ps);
					if (dt.Rows.size() == 0
							&& town.getHisNode().getHisWhenNoWorker() != WhenNoWorker.Skip) {
						throw new RuntimeException("@流程设计错误，到达的节点（"
								+ town.getHisNode().getName()
								+ "）在指定的节点中没有数据，无法找到子线程的工作人员。");
					}
					return dt;
				} catch (RuntimeException ex) {
					msg += ex.getMessage();
					// if (dtls.Count == 1)
					// throw new Exception("@估计是流程设计错误,没有在分流节点的明细表中设置");
				}
			}
			throw new RuntimeException(
					"@没有找到分流节点的明细表作为子线程的发起的数据源，流程设计错误，请确认分流节点表单中的明细表是否有UserNo约定的系统字段。"
							+ msg);
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按照明细表,作为子线程的接收人.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按节点绑定的人员处理.
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByBindEmp) {
			ps = new Paras();
			ps.Add("FK_Node", town.getHisNode().getNodeID());
			ps.SQL = "SELECT FK_Emp FROM WF_NodeEmp WHERE FK_Node=" + dbStr
					+ "FK_Node ORDER BY FK_Emp";
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() == 0) {
				throw new RuntimeException("@流程设计错误:下一个节点("
						+ town.getHisNode().getName() + ")没有绑定工作人员 . ");
			}
			return dt;
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按节点绑定的人员处理.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按照选择的人员处理。
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.BySelected
				|| town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByFEE) {
			ps = new Paras();
			ps.Add("FK_Node", this.town.getHisNode().getNodeID());
			ps.Add("WorkID", this.currWn.getHisWork().getOID());

			ps.SQL = "SELECT FK_Emp FROM WF_SelectAccper WHERE FK_Node="
					+ dbStr + "FK_Node AND WorkID=" + dbStr
					+ "WorkID AND AccType=0 ORDER BY IDX";
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() == 0) {
				// 从上次发送设置的地方查询.
				SelectAccpers sas = new SelectAccpers();
				int i = sas.QueryAccepterPriSetting(this.town.getHisNode()
						.getNodeID());
				if (i == 0) {
					if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.BySelected) {
						throw new RuntimeException("@请选择下一步骤工作("
								+ town.getHisNode().getName() + ")接受人员。");
					} else {
						throw new RuntimeException("@流程设计错误，请重写FEE，然后为节点("
								+ town.getHisNode().getName()
								+ ")设置接受人员，详细请参考cc流程设计手册。");
					}
				}

				// 插入里面.
				for (SelectAccper item : SelectAccpers
						.convertSelectAccpers(sas)) {
					DataRow dr = dt.NewRow();
					dr.setValue(0, item.getFK_Emp());
					dt.Rows.add(dr);
				}
				return dt;
			}
			return dt;
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按照选择的人员处理。

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按照指定节点的处理人计算。
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.BySpecNodeEmp
				|| town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByStarter) {
			// 按指定节点岗位上的人员计算
			String strs = town.getHisNode().getDeliveryParas();
			if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByStarter) {
				// 找开始节点的处理人员.
				strs = Integer.parseInt(this.fl.getNo()) + "01";
				ps = new Paras();
				ps.SQL = "SELECT FK_Emp FROM WF_GenerWorkerList WHERE WorkID="
						+ dbStr + "OID AND FK_Node=" + dbStr
						+ "FK_Node AND IsPass=1 AND IsEnable=1 ";
				ps.Add("FK_Node", Integer.parseInt(strs));
				ps.Add("OID", this.WorkID);
				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() == 1) {
					return dt;
				} else {
					// 有可能当前节点就是第一个节点，那个时间还没有初始化数据，就返回当前人.
					DataRow dr = dt.NewRow();
					dr.setValue(0, WebUser.getNo());
					dt.Rows.add(dr);
					return dt;
				}
			}

			// 首先从本流程里去找。
			strs = strs.replace(";", ",");
			String[] nds = strs.split("[,]", -1);
			for (String nd : nds) {
				if (StringHelper.isNullOrEmpty(nd)) {
					continue;
				}

				if (DataType.IsNumStr(nd) == false) {
					throw new RuntimeException("流程设计错误:您设置的节点("
							+ town.getHisNode().getName()
							+ ")的接收方式为按指定的节点岗位投递，但是您没有在访问规则设置中设置节点编号。");
				}

				ps = new Paras();
				ps.SQL = "SELECT FK_Emp FROM WF_GenerWorkerList WHERE WorkID="
						+ dbStr + "OID AND FK_Node=" + dbStr
						+ "FK_Node AND IsPass=1 AND IsEnable=1 ";
				ps.Add("FK_Node", Integer.parseInt(nd));
				if (this.currWn.getHisNode().getHisRunModel() == RunModel.SubThread) {
					ps.Add("OID", this.currWn.getHisWork().getFID());
				} else {
					ps.Add("OID", this.WorkID);
				}

				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() != 0) {
					return dt;
				}

				// 就要到轨迹表里查,因为有可能是跳过的节点.
				ps = new Paras();
				ps.SQL = "SELECT " + TrackAttr.EmpFrom + " FROM ND"
						+ Integer.parseInt(fl.getNo())
						+ "Track WHERE (ActionType=" + dbStr
						+ "ActionType1 OR ActionType=" + dbStr
						+ "ActionType2 OR ActionType=" + dbStr
						+ "ActionType3 OR ActionType=" + dbStr
						+ "ActionType4 OR ActionType=" + dbStr
						+ "ActionType5) AND NDFrom=" + dbStr
						+ "NDFrom AND WorkID=" + dbStr + "WorkID";
				ps.Add("ActionType1", ActionType.Skip.getValue());
				ps.Add("ActionType2", ActionType.Forward.getValue());
				ps.Add("ActionType3", ActionType.ForwardFL.getValue());
				ps.Add("ActionType4", ActionType.ForwardHL.getValue());
				ps.Add("ActionType5", ActionType.Start.getValue());

				ps.Add("NDFrom", Integer.parseInt(nd));
				ps.Add("WorkID", this.WorkID);
				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() != 0) {
					return dt;
				}
			}

			// 本流程里没有有可能该节点是配置的父流程节点,也就是说子流程的一个节点与父流程指定的节点的工作人员一致.
			GenerWorkFlow gwf = new GenerWorkFlow(this.WorkID);
			if (gwf.getPWorkID() != 0) {
				for (String pnodeiD : nds) {
					if (StringHelper.isNullOrEmpty(pnodeiD)) {
						continue;
					}

					Node nd = new Node(Integer.parseInt(pnodeiD));
					if (!nd.getFK_Flow().equals(gwf.getPFlowNo())) {
						continue; // 如果不是父流程的节点，就不执行.
					}

					ps = new Paras();
					ps.SQL = "SELECT FK_Emp FROM WF_GenerWorkerList WHERE WorkID="
							+ dbStr
							+ "OID AND FK_Node="
							+ dbStr
							+ "FK_Node AND IsPass=1 AND IsEnable=1 ";
					ps.Add("FK_Node", nd.getNodeID());
					if (this.currWn.getHisNode().getHisRunModel() == RunModel.SubThread) {
						ps.Add("OID", gwf.getPFID());
					} else {
						ps.Add("OID", gwf.getPWorkID());
					}

					dt = DBAccess.RunSQLReturnTable(ps);
					if (dt.Rows.size() == 1) {
						return dt;
					}

					// 就要到轨迹表里查,因为有可能是跳过的节点.
					ps = new Paras();
					ps.SQL = "SELECT " + TrackAttr.EmpFrom + " FROM ND"
							+ Integer.parseInt(fl.getNo())
							+ "Track WHERE (ActionType=" + dbStr
							+ "ActionType1 OR ActionType=" + dbStr
							+ "ActionType2 OR ActionType=" + dbStr
							+ "ActionType3 OR ActionType=" + dbStr
							+ "ActionType4 OR ActionType=" + dbStr
							+ "ActionType5) AND NDFrom=" + dbStr
							+ "NDFrom AND WorkID=" + dbStr + "WorkID";
					ps.Add("ActionType1", ActionType.Start.getValue());
					ps.Add("ActionType2", ActionType.Forward.getValue());
					ps.Add("ActionType3", ActionType.ForwardFL.getValue());
					ps.Add("ActionType4", ActionType.ForwardHL.getValue());
					ps.Add("ActionType5", ActionType.Skip.getValue());

					ps.Add("NDFrom", nd.getNodeID());

					if (this.currWn.getHisNode().getHisRunModel() == RunModel.SubThread) {
						ps.Add("OID", gwf.getPFID());
					} else {
						ps.Add("OID", gwf.getPWorkID());
					}

					dt = DBAccess.RunSQLReturnTable(ps);
					if (dt.Rows.size() != 0) {
						return dt;
					}
				}
			}

			throw new RuntimeException("@流程设计错误，到达的节点（"
					+ town.getHisNode().getName() + "）在指定的节点(" + strs
					+ ")中没有数据，无法找到工作的人员。 @技术信息如下: 投递方式:BySpecNodeEmp sql="
					+ ps.getSQLNoPara());
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按照节点绑定的人员处理。

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按照上一个节点表单指定字段的人员处理。
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByPreviousNodeFormEmpsField) {
			// 检查接受人员规则,是否符合设计要求.
			String specEmpFields = town.getHisNode().getDeliveryParas();
			if (StringHelper.isNullOrEmpty(specEmpFields)) {
				specEmpFields = "SysSendEmps";
			}

			if (this.currWn.getHisWork().getEnMap().getAttrs().Contains(specEmpFields) == false) {
				throw new RuntimeException(
						"@您设置的当前节点按照指定的人员，决定下一步的接受人员，但是你没有在节点表单中设置该表单"
								+ specEmpFields + "字段。");
			}

			// 获取接受人并格式化接受人,
			String emps = this.currWn.getHisWork().GetValStringByKey(
					specEmpFields);
			emps = emps.replace(" ", "");
			if (emps.contains(",") && emps.contains(";")) {
				// 如果包含,; 例如 zhangsan,张三;lisi,李四;
				String[] myemps1 = emps.split("[;]", -1);
				for (String str : myemps1) {
					if (StringHelper.isNullOrEmpty(str)) {
						continue;
					}

					String[] ss = str.split("[,]", -1);
					DataRow dr = dt.NewRow();
					dr.setValue(0, ss[0]);
					dt.Rows.add(dr);
				}
				if (dt.Rows.size() == 0) {
					throw new RuntimeException("@输入的接受人员信息错误;[" + emps + "]。");
				} else {
					return dt;
				}
			}

			emps = emps.replace(";", ",");
			emps = emps.replace("；", ",");
			emps = emps.replace("，", ",");
			emps = emps.replace("、", ",");
			emps = emps.replace("@", ",");

			if (StringHelper.isNullOrEmpty(emps)) {
				throw new RuntimeException(
						"@没有在字段["
								+ this.currWn.getHisWork().getEnMap().getAttrs()
										.GetAttrByKey(specEmpFields).getDesc()
								+ "]中指定接受人，工作无法向下发送。");
			}

			// 把它加入接受人员列表中.
			String[] myemps = emps.split("[,]", -1);
			for (String s : myemps) {
				if (StringHelper.isNullOrEmpty(s)) {
					continue;
				}

				// if
				// (BP.DA.DBAccess.RunSQLReturnValInt("SELECT COUNT(NO) AS NUM FROM Port_Emp WHERE NO='"
				// + s + "' or name='"+s+"'", 0) == 0)
				// continue;

				DataRow dr = dt.NewRow();
				// dr[0] = s;
				dr.setValue(0, s);
				dt.Rows.add(dr);
			}
			return dt;
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按照上一个节点表单指定字段的人员处理。

		String prjNo = "";
		FlowAppType flowAppType = this.currWn.getHisNode().getHisFlow()
				.getHisFlowAppType();
		sql = "";
		if (this.currWn.getHisNode().getHisFlow().getHisFlowAppType() == FlowAppType.PRJ) {
			prjNo = "";
			try {
				prjNo = this.currWn.getHisWork().GetValStrByKey("PrjNo");
			} catch (RuntimeException ex) {
				throw new RuntimeException(
						"@当前流程是工程类流程，但是在节点表单中没有PrjNo字段(注意区分大小写)，请确认。@异常信息:"
								+ ex.getMessage());
			}
		}

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按部门与岗位的交集计算.
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByDeptAndStation) {
			sql = "SELECT No FROM Port_Emp WHERE No IN ";
			sql += "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ";
			sql += "( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node=" + dbStr
					+ "FK_Node1)";
			sql += ")";
			sql += "AND No IN ";
			sql += "(";
			sql += "SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation()
					+ " WHERE FK_Station IN ";
			sql += "( SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
					+ dbStr + "FK_Node2 )";
			sql += ") ORDER BY No ";

			ps = new Paras();
			ps.Add("FK_Node1", town.getHisNode().getNodeID());
			ps.Add("FK_Node2", town.getHisNode().getNodeID());
			ps.SQL = sql;
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() > 0) {
				return dt;
			} else {
				throw new RuntimeException("@节点访问规则错误:节点("
						+ town.getHisNode().getNodeID() + ","
						+ town.getHisNode().getName()
						+ "), 按照岗位与部门的交集确定接受人的范围错误，没有找到人员:SQL=" + sql);
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按部门与岗位的交集计算.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 判断节点部门里面是否设置了部门，如果设置了，就按照它的部门处理。
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByDept) {
			ps = new Paras();
			ps.Add("FK_Node", this.town.getHisNode().getNodeID());
			ps.Add("WorkID", this.currWn.getHisWork().getOID());
			ps.SQL = "SELECT FK_Emp FROM WF_SelectAccper WHERE FK_Node="
					+ dbStr + "FK_Node AND WorkID=" + dbStr
					+ "WorkID AND AccType=0 ORDER BY IDX";
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() > 0) {
				return dt;
			}
			if (flowAppType == FlowAppType.Normal) {
				ps = new Paras();
				ps.SQL = "SELECT No,Name FROM Port_Emp WHERE FK_Dept IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
						+ dbStr + "FK_Node1)";
				ps.SQL += " OR ";
				ps.SQL += " No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
						+ dbStr + "FK_Node2 ) )";
				ps.SQL += " ORDER BY No";
				ps.Add("FK_Node1", town.getHisNode().getNodeID());
				ps.Add("FK_Node2", town.getHisNode().getNodeID());

				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() > 0
						&& town.getHisNode().getHisWhenNoWorker() != WhenNoWorker.Skip) {
					return dt;
				} else {
					// IsFindWorker = false;
					// ps.SQL =
					// "SELECT No,Name FROM Port_Emp WHERE FK_Dept IN ( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
					// + dbStr + "FK_Node )";
					throw new RuntimeException("@按部门确定接受人的范围,没有找到人员.");
				}
			}

			if (flowAppType == FlowAppType.PRJ) {
				sql = "SELECT No FROM Port_Emp WHERE No IN ";
				sql += "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ";
				sql += "( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
						+ dbStr + "FK_Node1)";
				sql += ")";
				sql += "AND NO IN ";
				sql += "(";
				sql += "SELECT FK_Emp FROM Prj_EmpPrjStation WHERE FK_Station IN ";
				sql += "( SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
						+ dbStr + "FK_Node2) AND FK_Prj=" + dbStr + "FK_Prj ";
				sql += ")";
				sql += " ORDER BY No";

				ps = new Paras();
				ps.Add("FK_Node1", town.getHisNode().getNodeID());
				ps.Add("FK_Node2", town.getHisNode().getNodeID());
				ps.Add("FK_Prj", prjNo);
				ps.SQL = sql;

				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() == 0) {
					// 如果项目组里没有工作人员就提交到公共部门里去找。
					sql = "SELECT NO FROM Port_Emp WHERE NO IN ";
					sql += "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ";
					sql += "( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
							+ dbStr + "FK_Node1)";
					sql += ")";
					sql += "AND NO IN ";
					sql += "(";
					sql += "SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation()
							+ " WHERE FK_Station IN ";
					sql += "( SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
							+ dbStr + "FK_Node2)";
					sql += ")";
					sql += " ORDER BY No";

					ps = new Paras();
					ps.Add("FK_Node1", town.getHisNode().getNodeID());
					ps.Add("FK_Node2", town.getHisNode().getNodeID());
					ps.SQL = sql;
				} else {
					return dt;
				}

				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() > 0) {
					return dt;
				}
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 判断节点部门里面是否设置了部门，如果设置了，就按照它的部门处理。

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 仅按岗位计算
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByStationOnly) {
			sql = "SELECT A.FK_Emp FROM "
					+ BP.WF.Glo.getEmpStation()
					+ " A, WF_NodeStation B WHERE A.FK_Station=B.FK_Station AND B.FK_Node="
					+ dbStr + "FK_Node ORDER BY A.FK_Emp";
			ps = new Paras();
			ps.Add("FK_Node", town.getHisNode().getNodeID());
			ps.SQL = sql;
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() > 0) {
				return dt;
			} else {
				throw new RuntimeException("@节点访问规则错误:节点("
						+ town.getHisNode().getNodeID() + ","
						+ town.getHisNode().getName()
						+ "), 按节点岗位与人员部门集合两个纬度计算，没有找到人员:SQL=" + sql);
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按岗位计算(以部门集合为纬度).
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.ByStationAndEmpDept) {
			sql = "SELECT No FROM Port_Emp WHERE NO IN "
					+ "(SELECT  FK_Emp  FROM "
					+ BP.WF.Glo.getEmpStation()
					+ " WHERE FK_Station IN (SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
					+ dbStr + "FK_Node) )" + " AND  FK_Dept IN "
					+ "(SELECT  FK_Dept  FROM Port_EmpDept WHERE FK_Emp ="
					+ dbStr + "FK_Emp)";

			sql += " ORDER BY No";

			ps = new Paras();
			ps.Add("FK_Node", town.getHisNode().getNodeID());
			ps.Add("FK_Emp", WebUser.getNo());
			ps.SQL = sql;
			// 2012.7.16李健修改
			// +" AND  NO IN "
			// + "(SELECT  FK_Emp  FROM Port_EmpDept WHERE FK_Emp = '" +
			// WebUser.No + "')";
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() > 0) {
				return dt;
			} else {
				throw new RuntimeException("@节点访问规则错误:节点("
						+ town.getHisNode().getNodeID() + ","
						+ town.getHisNode().getName()
						+ "), 按节点岗位与人员部门集合两个纬度计算，没有找到人员:SQL=" + sql);
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion

		String empNo = WebUser.getNo();
		String empDept = WebUser.getFK_Dept();

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 按指定的节点的人员岗位，做为下一步骤的流程接受人。
		if (town.getHisNode().getHisDeliveryWay() == DeliveryWay.BySpecNodeEmpStation) {
			// 按指定的节点的人员岗位
			String fk_node = town.getHisNode().getDeliveryParas();
			if (DataType.IsNumStr(fk_node) == false) {
				throw new RuntimeException("流程设计错误:您设置的节点("
						+ town.getHisNode().getName()
						+ ")的接收方式为按指定的节点人员岗位投递，但是您没有在访问规则设置中设置节点编号。");
			}

			ps = new Paras();
			ps.SQL = "SELECT Rec,FK_Dept FROM ND" + fk_node + " WHERE OID="
					+ dbStr + "OID";
			ps.Add("OID", this.WorkID);
			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() != 1) {
				throw new RuntimeException("@流程设计错误，到达的节点（"
						+ town.getHisNode().getName()
						+ "）在指定的节点中没有数据，无法找到工作的人员。");
			}

			empNo = dt.getValue(0, 0).toString();
			empDept = dt.getValue(0, 1).toString();
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按指定的节点人员，做为下一步骤的流程接受人。

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 最后判断 - 按照岗位来执行。
		if (this.currWn.getHisNode().getIsStartNode() == false) {
			ps = new Paras();
			if (flowAppType == FlowAppType.Normal
					|| flowAppType == FlowAppType.DocFlow) {
				// 如果当前的节点不是开始节点， 从轨迹里面查询。
				sql = "SELECT DISTINCT FK_Emp  FROM "
						+ BP.WF.Glo.getEmpStation()
						+ " WHERE FK_Station IN "
						+ "(SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
						+ town.getHisNode().getNodeID()
						+ ") "
						+ "AND FK_Emp IN (SELECT FK_Emp FROM WF_GenerWorkerlist WHERE WorkID="
						+ dbStr
						+ "WorkID AND FK_Node IN ("
						+ DataType.PraseAtToInSql(town.getHisNode()
								.getGroupStaNDs(), true) + ") )";

				sql += " ORDER BY FK_Emp ";

				ps.SQL = sql;
				ps.Add("WorkID", this.WorkID);
			}

			if (flowAppType == FlowAppType.PRJ) {
				// 如果当前的节点不是开始节点， 从轨迹里面查询。
				sql = "SELECT DISTINCT FK_Emp  FROM Prj_EmpPrjStation WHERE FK_Station IN "
						+ "(SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
						+ dbStr
						+ "FK_Node ) AND FK_Prj="
						+ dbStr
						+ "FK_Prj "
						+ "AND FK_Emp IN (SELECT FK_Emp FROM WF_GenerWorkerlist WHERE WorkID="
						+ dbStr
						+ "WorkID AND FK_Node IN ("
						+ DataType.PraseAtToInSql(town.getHisNode()
								.getGroupStaNDs(), true) + ") )";
				sql += " ORDER BY FK_Emp ";

				ps = new Paras();
				ps.SQL = sql;
				ps.Add("FK_Node", town.getHisNode().getNodeID());
				ps.Add("FK_Prj", prjNo);
				ps.Add("WorkID", this.WorkID);

				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() == 0) {
					// 如果项目组里没有工作人员就提交到公共部门里去找。
					sql = "SELECT DISTINCT FK_Emp  FROM "
							+ BP.WF.Glo.getEmpStation()
							+ " WHERE FK_Station IN "
							+ "(SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
							+ dbStr
							+ "FK_Node ) "
							+ "AND FK_Emp IN (SELECT FK_Emp FROM WF_GenerWorkerlist WHERE WorkID="
							+ dbStr
							+ "WorkID AND FK_Node IN ("
							+ DataType.PraseAtToInSql(town.getHisNode()
									.getGroupStaNDs(), true) + ") )";
					sql += " ORDER BY FK_Emp ";

					ps = new Paras();
					ps.SQL = sql;
					ps.Add("FK_Node", town.getHisNode().getNodeID());
					ps.Add("WorkID", this.WorkID);
				} else {
					return dt;
				}
			}

			dt = DBAccess.RunSQLReturnTable(ps);
			// 如果能够找到.
			if (dt.Rows.size() >= 1) {
				if (dt.Rows.size() == 1) {
					// 如果人员只有一个的情况，说明他可能要
				}
				return dt;
			}
		}

		// 如果执行节点 与 接受节点岗位集合一致
		if (this.currWn.getHisNode().getGroupStaNDs()
				.equals(town.getHisNode().getGroupStaNDs())) {
			// 说明，就把当前人员做为下一个节点处理人。
			DataRow dr = dt.NewRow();
			dr.setValue(0, WebUser.getNo());
			dt.Rows.add(dr);
			return dt;
		}

		// 如果执行节点 与 接受节点岗位集合不一致
		if (!this.currWn.getHisNode().getGroupStaNDs()
				.equals(town.getHisNode().getGroupStaNDs())) {
			// 没有查询到的情况下, 先按照本部门计算。
			if (flowAppType == FlowAppType.Normal) {
				if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
					sql = "SELECT FK_Emp as No FROM Port_DeptEmpStation A, WF_NodeStation B         WHERE A.FK_Station=B.FK_Station AND B.FK_Node="
							+ dbStr
							+ "FK_Node AND A.FK_Dept="
							+ dbStr
							+ "FK_Dept";
				} else {
					sql = "SELECT FK_Emp as No FROM Port_EmpStation A, WF_NodeStation B, Port_Emp C WHERE A.FK_Station=B.FK_Station AND A.FK_Emp=C.No  AND B.FK_Node="
							+ dbStr
							+ "FK_Node AND C.FK_Dept="
							+ dbStr
							+ "FK_Dept";
				}
				ps = new Paras();
				ps.SQL = sql;
				ps.Add("FK_Node", town.getHisNode().getNodeID());
				ps.Add("FK_Dept", empDept);
			}

			if (flowAppType == FlowAppType.PRJ) {
				sql = "SELECT  FK_Emp  FROM Prj_EmpPrjStation WHERE FK_Prj="
						+ dbStr
						+ "FK_Prj1 AND FK_Station IN (SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
						+ dbStr + "FK_Node)" + " AND  FK_Prj=" + dbStr
						+ "FK_Prj2 ";
				sql += " ORDER BY FK_Emp ";

				ps = new Paras();
				ps.SQL = sql;
				ps.Add("FK_Prj1", prjNo);
				ps.Add("FK_Node", town.getHisNode().getNodeID());
				ps.Add("FK_Prj2", prjNo);
				dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() == 0) {
					// 如果项目组里没有工作人员就提交到公共部门里去找。
					sql = "SELECT No FROM Port_Emp WHERE NO IN "
							+ "(SELECT  FK_Emp  FROM "
							+ BP.WF.Glo.getEmpStation()
							+ " WHERE FK_Station IN (SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
							+ dbStr
							+ "FK_Node))"
							+ " AND  NO IN "
							+ "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ="
							+ dbStr + "FK_Dept)";

					sql += " ORDER BY No ";

					ps = new Paras();
					ps.SQL = sql;
					ps.Add("FK_Node", town.getHisNode().getNodeID());
					ps.Add("FK_Dept", empDept);
					// dt = DBAccess.RunSQLReturnTable(ps);
				} else {
					return dt;
				}
			}

			dt = DBAccess.RunSQLReturnTable(ps);
			if (dt.Rows.size() == 0) {
				NodeStations nextStations = town.getHisNode().getNodeStations();
				if (nextStations.size() == 0) {
					throw new RuntimeException("节点没有岗位:"
							+ town.getHisNode().getNodeID() + "  "
							+ town.getHisNode().getName());
				}
			} else {
				boolean isInit = false;
				for (DataRow dr : dt.Rows) {
					if (dr.getValue(0).toString().equals(WebUser.getNo())) {
						// 如果岗位分组不一样，并且结果集合里还有当前的人员，就说明了出现了当前操作员，拥有本节点上的岗位也拥有下一个节点的工作岗位
						// 导致：节点的分组不同，传递到同一个人身上。
						isInit = true;
					}
				}

				// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in
				// Java:
				// /#warning edit by peng, 用来确定不同岗位集合的传递包含同一个人的处理方式。

				// if (isInit == false || isInit == true)
				return dt;
			}
		}

		// 这里去掉了向下级别寻找的算法.

		// 没有查询到的情况下, 按照最大匹配数 提高一个级别计算，递归算法未完成。
		// * 因为:以上已经做的岗位的判断，就没有必要在判断其它类型的节点处理了。
		// *
		// Object tempVar = empDept.clone();
		Object tempVar = new String(empDept);
		String nowDeptID = (String) ((tempVar instanceof String) ? tempVar
				: null);
		while (true) {
			BP.Port.Dept myDept = new BP.Port.Dept(nowDeptID);
			nowDeptID = myDept.getParentNo();
			if (nowDeptID.equals("-1") || nowDeptID.toString().equals("0")) {
				break; // 一直找到了最高级仍然没有发现，就跳出来循环从当前操作员人部门向下找。
				// throw new RuntimeException("@按岗位计算没有找到("
				// + town.getHisNode().getName() + ")接受人.");
			}

			// 检查指定的部门下面是否有该人员.
			DataTable mydtTemp = this.Func_GenerWorkerList_DiGui(nowDeptID,
					empNo);
			if (mydtTemp == null) {
				// 如果父亲级没有，就找父级的平级.
				BP.Port.Depts myDepts = new BP.Port.Depts();
				myDepts.Retrieve(BP.Port.DeptAttr.ParentNo,
						myDept.getParentNo());
				for (BP.Port.Dept item : BP.Port.Depts.convertDepts(myDepts)) {
					if (nowDeptID.equals(item.getNo())) {
						continue;
					}
					mydtTemp = this.Func_GenerWorkerList_DiGui(item.getNo(),
							empNo);
					if (mydtTemp == null) {
						continue;
					} else {
						return mydtTemp;
					}
				}

				continue; // 如果平级也没有，就continue.
			} else {
				return mydtTemp;
			}
		}

		// 如果向上找没有找到，就考虑从本级部门上向下找。
		// Object tempVar2 = empDept.clone();
		Object tempVar2 = new String(empDept);
		nowDeptID = (String) ((tempVar2 instanceof String) ? tempVar2 : null);
		BP.Port.Depts subDepts = new BP.Port.Depts(nowDeptID);

		// 递归出来子部门下有该岗位的人员
		DataTable mydt = Func_GenerWorkerList_DiGui_ByDepts(subDepts, empNo);
		if (mydt == null) {
			throw new RuntimeException("@按岗位计算没有找到("
					+ town.getHisNode().getName() + ")接受人.");
		}
		return mydt;
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 按照岗位来执行。
	}

	/**
	 * 递归出来子部门下有该岗位的人员
	 * 
	 * @param subDepts
	 * @param empNo
	 * @return
	 */
	public final DataTable Func_GenerWorkerList_DiGui_ByDepts(
			BP.Port.Depts subDepts, String empNo) {
		for (BP.Port.Dept item : BP.Port.Depts.convertDepts(subDepts)) {
			DataTable dt = Func_GenerWorkerList_DiGui(item.getNo(), empNo);
			if (dt != null) {
				return dt;
			}

			dt = Func_GenerWorkerList_DiGui_ByDepts(item.getHisSubDepts(),
					empNo);
			if (dt != null) {
				return dt;
			}
		}
		return null;
	}

	/**
	 * 根据部门获取下一步的操作员
	 * 
	 * @param deptNo
	 * @param emp1
	 * @return
	 */
	public final DataTable Func_GenerWorkerList_DiGui(String deptNo,
			String empNo) {
		String sql;

		if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
			sql = "SELECT FK_Emp as No FROM Port_DeptEmpStation A, WF_NodeStation B WHERE A.FK_Station=B.FK_Station AND B.FK_Node="
					+ dbStr
					+ "FK_Node AND A.FK_Dept="
					+ dbStr
					+ "FK_Dept AND A.FK_Emp!=" + dbStr + "FK_Emp";
		} else {
			sql = "SELECT FK_Emp as No FROM Port_EmpStation A, WF_NodeStation B, Port_Emp C WHERE A.FK_Station=B.FK_Station AND B.FK_Node="
					+ dbStr
					+ "FK_Node AND C.FK_Dept="
					+ dbStr
					+ "FK_Dept AND A.FK_Emp!=" + dbStr + "FK_Emp";
		}
		Paras ps = new Paras();
		ps.SQL = sql;
		ps.Add("FK_Node", town.getHisNode().getNodeID());
		ps.Add("FK_Dept", deptNo);
		ps.Add("FK_Emp", empNo);

		DataTable dt = DBAccess.RunSQLReturnTable(ps);
		if (dt.Rows.size() == 0) {
			NodeStations nextStations = town.getHisNode().getNodeStations();
			if (nextStations.size() == 0) {
				throw new RuntimeException("@节点没有岗位:"
						+ town.getHisNode().getNodeID() + "  "
						+ town.getHisNode().getName());
			}

			sql = "SELECT No FROM Port_Emp WHERE No IN ";
			sql += "(SELECT  FK_Emp  FROM "
					+ BP.WF.Glo.getEmpStation()
					+ " WHERE FK_Station IN (SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
					+ dbStr + "FK_Node ) )";
			sql += " AND No IN ";

			if (deptNo.equals("1")) {
				sql += "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Emp!="
						+ dbStr + "FK_Emp ) ";
			} else {
				BP.Port.Dept deptP = new BP.Port.Dept(deptNo);
				sql += "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Emp!="
						+ dbStr + "FK_Emp AND FK_Dept = '"
						+ deptP.getParentNo() + "')";
			}

			ps = new Paras();
			ps.SQL = sql;
			ps.Add("FK_Node", town.getHisNode().getNodeID());
			ps.Add("FK_Emp", empNo);
			dt = DBAccess.RunSQLReturnTable(ps);

			if (dt.Rows.size() == 0) {
				return null;
			}
			return dt;
		} else {
			return dt;
		}
	}

	/**
	 * 执行找人
	 * 
	 * @return
	 */
	public final DataTable DoIt(Flow fl, WorkNode currWn, WorkNode toWn) {
		// 给变量赋值.
		this.fl = fl;
		this.currWn = currWn;
		this.town = toWn;
		this.WorkID = currWn.getWorkID();

		// 如果到达的节点是按照workflow的模式。
		if (toWn.getHisNode().getHisDeliveryWay() != DeliveryWay.ByCCFlowBPM) {
			DataTable re_dt = this.FindByWorkFlowModel();
			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			// /#region 根据配置追加接收人 by dgq 2015.5.18
			if (!StringHelper.isNullOrEmpty(this.town.getHisNode()
					.getDeliveryParas())
					&& this.town.getHisNode().getDeliveryParas()
							.contains("@Spec")) {
				// 如果返回null ,则创建表
				if (re_dt == null) {
					re_dt = new DataTable();
					re_dt.Columns.Add("No", String.class);
				}

				// 获取配置规则
				String[] reWays = this.town.getHisNode().getDeliveryParas()
						.split("[@]", -1);
				for (String reWay : reWays) {
					if (StringHelper.isNullOrEmpty(reWay)) {
						continue;
					}
					String[] specItems = reWay.split("[=]", -1);
					// 配置规则错误
					if (specItems.length != 2) {
						continue;
					}
					// 规则名称，SpecStations、SpecEmps
					String specName = specItems[0];
					// 规则内容
					String specContent = specItems[1];
					// C# TO JAVA CONVERTER NOTE: The following 'switch'
					// operated on a string member and was converted to Java
					// 'if-else' logic:
					// switch (specName)
					// ORIGINAL LINE: case "SpecStations":
					if (specName.equals("SpecStations")) // 按岗位
					{
						String[] stations = specContent.split("[,]", -1);
						for (String station : stations) {
							if (StringHelper.isNullOrEmpty(station)) {
								continue;
							}

							// 获取岗位下的人员
							DataTable dt_Emps = DBAccess
									.RunSQLReturnTable("SELECT FK_Emp FROM "
											+ BP.WF.Glo.getEmpStation()
											+ " WHERE FK_Station='" + station
											+ "'");
							for (DataRow empRow : dt_Emps.Rows) {
								// 排除为空编号
								if (empRow.getValue(0) == null
										|| StringHelper.isNullOrEmpty(empRow.getValue(0)
												.toString())) {
									continue;
								}

								DataRow dr = re_dt.NewRow();
								//dr[0] = empRow[0];
								dr.setValue(0, empRow.getValue(0));
								re_dt.Rows.add(dr);
							}
						}
					}
					// ORIGINAL LINE: case "SpecEmps":
					else if (specName.equals("SpecEmps")) // 按人员编号
					{
						String[] emps = specContent.split("[,]", -1);
						for (String emp : emps) {
							// 排除为空编号
							if (StringHelper.isNullOrEmpty(emp)) {
								continue;
							}

							DataRow dr = re_dt.NewRow();
							dr.setValue(0, emp);
							re_dt.Rows.add(dr);
						}
					}
				}
			}
			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			// /#endregion
			return re_dt;
		}

		// 规则集合.
		FindWorkerRoles ens = new FindWorkerRoles(town.getHisNode().getNodeID());
		for (FindWorkerRole en : FindWorkerRoles.convertFindWorkerRoles(ens)) {
			en.fl = this.fl;
			en.town = toWn;
			en.currWn = currWn;
			en.HisNode = currWn.getHisNode();
			en.WorkID = this.WorkID;

			DataTable dt = en.GenerWorkerOfDataTable();
			if (dt == null || dt.Rows.size() == 0) {
				continue;
			}
			return dt;
		}

		// 没有找到人的情况，就返回空.
		return null;
	}
}