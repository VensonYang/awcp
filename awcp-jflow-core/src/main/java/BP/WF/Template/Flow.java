package BP.WF.Template;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.DA.AtPara;
import BP.DA.DBAccess;
import BP.DA.DBType;
import BP.DA.DataColumn;
import BP.DA.DataRow;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Depositary;
import BP.DA.Log;
import BP.DA.Paras;
import BP.DA.XmlWriteMode;
import BP.En.Attr;
import BP.En.EditType;
import BP.En.Entity;
import BP.En.FieldTypeS;
import BP.En.Map;
import BP.En.RefMethod;
import BP.En.Row;
import BP.En.UAC;
import BP.En.UIContralType;
import BP.Port.Emp;
import BP.Port.GuestUser;
import BP.Port.WebUser;
import BP.Sys.GEDtl;
import BP.Sys.GEDtlAttr;
import BP.Sys.GEDtls;
import BP.Sys.PubClass;
import BP.Sys.SysEnum;
import BP.Sys.SysEnumMain;
import BP.Sys.SystemConfig;
import BP.Sys.Frm.AttachmentUploadType;
import BP.Sys.Frm.EventListOfNode;
import BP.Sys.Frm.FrmAttachment;
import BP.Sys.Frm.FrmAttachmentDB;
import BP.Sys.Frm.FrmAttachmentDBs;
import BP.Sys.Frm.FrmEle;
import BP.Sys.Frm.FrmEvent;
import BP.Sys.Frm.FrmEventAttr;
import BP.Sys.Frm.FrmEvents;
import BP.Sys.Frm.FrmImg;
import BP.Sys.Frm.FrmLab;
import BP.Sys.Frm.FrmLine;
import BP.Sys.Frm.FrmLink;
import BP.Sys.Frm.FrmRB;
import BP.Sys.Frm.GroupField;
import BP.Sys.Frm.GroupFieldAttr;
import BP.Sys.Frm.MapAttr;
import BP.Sys.Frm.MapAttrAttr;
import BP.Sys.Frm.MapAttrs;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDtl;
import BP.Sys.Frm.MapDtlAttr;
import BP.Sys.Frm.MapDtls;
import BP.Sys.Frm.MapExt;
import BP.Sys.Frm.MapExtXmlList;
import BP.Sys.Frm.MapM2M;
import BP.Tools.FileAccess;
import BP.Tools.StringHelper;
import BP.WF.OSModel;
import BP.WF.SendReturnObjs;
import BP.WF.StartFlowParaNameList;
import BP.WF.UserInfoShowModel;
import BP.WF.WorkFlow;
import BP.WF.WorkNode;
import BP.WF.Data.BillTemplate;
import BP.WF.Data.BillTemplates;
import BP.WF.Data.GERpt;
import BP.WF.Data.GERptAttr;
import BP.WF.Data.NDXRptBaseAttr;
import BP.WF.Entity.FrmWorkCheck;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.GenerWorkFlowAttr;
import BP.WF.Entity.GenerWorkFlows;
import BP.WF.Entity.Track;
import BP.WF.Entity.WFSta;
import BP.WF.EventBase.FlowEventBase;
import BP.WF.Template.AccepterRole.NodeDept;
import BP.WF.Template.AccepterRole.NodeEmp;
import BP.WF.Template.AccepterRole.NodeEmpAttr;
import BP.WF.Template.AccepterRole.NodeEmps;
import BP.WF.Template.AccepterRole.SelectAccper;
import BP.WF.Template.CC.CC;
import BP.WF.Template.CC.CCDept;
import BP.WF.Template.CC.CCEmp;
import BP.WF.Template.CC.CCStation;
import BP.WF.Template.Condition.Cond;
import BP.WF.Template.Condition.CondType;
import BP.WF.Template.Condition.Conds;
import BP.WF.Template.Condition.ConnDataFrom;
import BP.WF.Template.Condition.TurnTo;
import BP.WF.Template.Ext.NodeToolbar;
import BP.WF.Template.FindWorker.FindWorkerRole;
import BP.WF.Template.Form.FrmNode;
import BP.WF.Template.Form.FrmNodeAttr;
import BP.WF.Template.Form.FrmNodes;
import BP.WF.Template.Form.Sys.Sln.FrmField;
import BP.WF.Template.PubLib.CFlowWay;
import BP.WF.Template.PubLib.DTSField;
import BP.WF.Template.PubLib.DataStoreModel;
import BP.WF.Template.PubLib.DeliveryWay;
import BP.WF.Template.PubLib.DraftRole;
import BP.WF.Template.PubLib.FlowAppType;
import BP.WF.Template.PubLib.FlowAttr;
import BP.WF.Template.PubLib.FlowDTSTime;
import BP.WF.Template.PubLib.FlowDTSWay;
import BP.WF.Template.PubLib.FlowDeptDataRightCtrlType;
import BP.WF.Template.PubLib.FlowRunWay;
import BP.WF.Template.PubLib.ImpFlowTempleteModel;
import BP.WF.Template.PubLib.NodeAttr;
import BP.WF.Template.PubLib.NodePosType;
import BP.WF.Template.PubLib.NodeWorkType;
import BP.WF.Template.PubLib.StartGuideWay;
import BP.WF.Template.PubLib.StartLimitRole;
import BP.WF.Template.PubLib.StartLimitWhen;
import BP.WF.Template.PubLib.TimelineRole;
import BP.WF.Template.PubLib.WFState;
import BP.WF.Template.Rpt.MapRpt;
import BP.WF.Template.WorkBase.StartWork;
import BP.WF.Template.WorkBase.StartWorkAttr;
import BP.WF.Template.WorkBase.Work;
import BP.WF.Template.WorkBase.WorkAttr;
import TL.ContextHolderUtils;

/**
 * 流程 记录了流程的信息． 流程的编号，名称，建立时间．
 */
public class Flow extends BP.En.EntityNoName {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(Flow.class);

	// /#region 业务数据表同步属性.
	/**
	 * 同步方式
	 */
	public final FlowDTSWay getDTSWay() {
		// return (FlowDTSWay)this.GetValIntByKey(FlowAttr.DTSWay);
		return FlowDTSWay.forValue(this.GetValIntByKey(FlowAttr.DTSWay));
	}

	public final void setDTSWay(FlowDTSWay value) {
		this.SetValByKey(FlowAttr.DTSWay, value.getValue());
	}

	public final FlowDTSTime getDTSTime() {
		// return (FlowDTSTime)this.GetValIntByKey(FlowAttr.DTSTime);
		return FlowDTSTime.forValue(this.GetValIntByKey(FlowAttr.DTSTime));
	}

	public final void setDTSTime(FlowDTSTime value) {
		this.SetValByKey(FlowAttr.DTSTime, value.getValue());
	}

	public final DTSField getDTSField() {
		// return (DTSField)this.GetValIntByKey(FlowAttr.DTSField);
		return DTSField.forValue(this.GetValIntByKey(FlowAttr.DTSField));
	}

	public final void setDTSField(DTSField value) {
		this.SetValByKey(FlowAttr.DTSField, value.getValue());
	}

	/**
	 * 业务表
	 */
	public final String getDTSBTable() {
		return this.GetValStringByKey(FlowAttr.DTSBTable);
	}

	public final void setDTSBTable(String value) {
		this.SetValByKey(FlowAttr.DTSBTable, value);
	}

	public final String getDTSBTablePK() {
		return this.GetValStringByKey(FlowAttr.DTSBTablePK);
	}

	public final void setDTSBTablePK(String value) {
		this.SetValByKey(FlowAttr.DTSBTablePK, value);
	}

	/**
	 * 要同步的节点s
	 */
	public final String getDTSSpecNodes() {
		return this.GetValStringByKey(FlowAttr.DTSSpecNodes);
	}

	public final void setDTSSpecNodes(String value) {
		this.SetValByKey(FlowAttr.DTSSpecNodes, value);
	}

	/**
	 * 同步的字段对应关系.
	 */
	public final String getDTSFields() {
		return this.GetValStringByKey(FlowAttr.DTSFields);
	}

	public final void setDTSFields(String value) {
		this.SetValByKey(FlowAttr.DTSFields, value);
	}

	// /#endregion 业务数据表同步属性.

	// /#region 基础属性.
	/**
	 * 流程事件实体
	 */
	public final String getFlowEventEntity() {
		return this.GetValStringByKey(FlowAttr.FlowEventEntity);
	}

	public final void setFlowEventEntity(String value) {
		this.SetValByKey(FlowAttr.FlowEventEntity, value);
	}

	/**
	 * 流程标记
	 */
	public final String getFlowMark() {
		return this.GetValStringByKey(FlowAttr.FlowMark);
	}

	public final void setFlowMark(String value) {
		this.SetValByKey(FlowAttr.FlowMark, value);
	}

	/**
	 * 节点图形类型
	 */
	public final int getChartType() {
		return this.GetValIntByKey(FlowAttr.ChartType);
	}

	public final void setChartType(int value) {
		this.SetValByKey(FlowAttr.ChartType, value);
	}

	// /#endregion

	// /#region 发起限制.
	/**
	 * 发起限制.
	 */
	public final StartLimitRole getStartLimitRole() {
		// return (StartLimitRole) this.GetValIntByKey(FlowAttr.StartLimitRole);
		return StartLimitRole.forValue(this.GetValIntByKey(FlowAttr.StartLimitRole));
	}

	public final void setStartLimitRole(StartLimitRole value) {
		this.SetValByKey(FlowAttr.StartLimitRole, value.getValue());
	}

	/**
	 * 发起内容
	 */
	public final String getStartLimitPara() {
		return this.GetValStringByKey(FlowAttr.StartLimitPara);
	}

	public final void setStartLimitPara(String value) {
		this.SetValByKey(FlowAttr.StartLimitPara, value);
	}

	public final String getStartLimitAlert() {
		String s = this.GetValStringByKey(FlowAttr.StartLimitAlert);
		if (s.equals("")) {
			return "您已经启动过该流程，不能重复启动。";
		}
		return s;
	}

	public final void setStartLimitAlert(String value) {
		this.SetValByKey(FlowAttr.StartLimitAlert, value);
	}

	/**
	 * 限制触发时间
	 */
	public final StartLimitWhen getStartLimitWhen() {
		// return (StartLimitWhen)this.GetValIntByKey(FlowAttr.StartLimitWhen);
		return StartLimitWhen.forValue(this.GetValIntByKey(FlowAttr.StartLimitWhen));
	}

	public final void setStartLimitWhen(StartLimitWhen value) {
		this.SetValByKey(FlowAttr.StartLimitWhen, value.getValue());
	}

	// /#endregion 发起限制.

	// /#region 导航模式
	/**
	 * 发起导航方式
	 */
	public final StartGuideWay getStartGuideWay() {
		// return (StartGuideWay) this.GetValIntByKey(FlowAttr.StartGuideWay);
		return StartGuideWay.forValue(this.GetValIntByKey(FlowAttr.StartGuideWay));
	}

	public final void setStartGuideWay(StartGuideWay value) {
		this.SetValByKey(FlowAttr.StartGuideWay, value.getValue());
	}

	/**
	 * 流程发起参数1
	 */
	public final String getStartGuidePara1() {
		return this.GetValStrByKey(FlowAttr.StartGuidePara1);
	}

	public final void setStartGuidePara1(String value) {
		this.SetValByKey(FlowAttr.StartGuidePara1, value);
	}

	/**
	 * 流程发起参数2
	 */
	public final String getStartGuidePara2() {
		String s = this.GetValStrByKey(FlowAttr.StartGuidePara2);
		if (StringHelper.isNullOrEmpty(s)) {
			if (this.getStartGuideWay() == StartGuideWay.ByHistoryUrl) {

			}
		}
		return s;
	}

	public final void setStartGuidePara2(String value) {
		this.SetValByKey(FlowAttr.StartGuidePara2, value);
	}

	/**
	 * 流程发起参数3
	 */
	public final String getStartGuidePara3() {
		return this.GetValStrByKey(FlowAttr.StartGuidePara3);
	}

	public final void setStartGuidePara3(String value) {
		this.SetValByKey(FlowAttr.StartGuidePara3, value);
	}

	/**
	 * 是否启用数据重置按钮？
	 */
	public final boolean getIsResetData() {
		return this.GetValBooleanByKey(FlowAttr.IsResetData);
	}

	/**
	 * 是否启用导入历史数据按钮?
	 */
	public final boolean getIsImpHistory() {
		return this.GetValBooleanByKey(FlowAttr.IsImpHistory);
	}

	/**
	 * 是否自动装载上一笔数据?
	 */
	public final boolean getIsLoadPriData() {
		return this.GetValBooleanByKey(FlowAttr.IsLoadPriData);
	}

	// /#endregion

	// /#region 其他属性
	/**
	 * 草稿规则
	 */
	public final DraftRole getDraftRole() {
		// return (DraftRole) this.GetValIntByKey(FlowAttr.Draft);
		return DraftRole.forValue(this.GetValIntByKey(FlowAttr.Draft));
	}

	public final void setDraftRole(DraftRole value) {
		this.SetValByKey(FlowAttr.Draft, value.getValue());
	}

	public String Tag = null;

	/**
	 * 运行类型
	 */
	public final FlowRunWay getHisFlowRunWay() {
		// return (FlowRunWay) this.GetValIntByKey(FlowAttr.FlowRunWay);
		return FlowRunWay.forValue(this.GetValIntByKey(FlowAttr.FlowRunWay));
	}

	public final void setHisFlowRunWay(FlowRunWay value) {
		this.SetValByKey(FlowAttr.FlowRunWay, value.getValue());
	}

	/**
	 * 运行对象
	 */
	public final String getRunObj() {
		return this.GetValStrByKey(FlowAttr.RunObj);
	}

	public final void setRunObj(String value) {
		this.SetValByKey(FlowAttr.RunObj, value);
	}

	/**
	 * 时间点规则
	 */
	public final TimelineRole getHisTimelineRole() {
		return TimelineRole.forValue(this.GetValIntByKey(FlowAttr.TimelineRole));
	}

	/**
	 * 流程部门数据查询权限控制方式
	 */
	public final FlowDeptDataRightCtrlType getHisFlowDeptDataRightCtrlType() {
		// return (FlowDeptDataRightCtrlType) this
		// .GetValIntByKey(FlowAttr.DRCtrlType);
		return FlowDeptDataRightCtrlType.forValue(this.GetValIntByKey(FlowAttr.DRCtrlType));
	}

	public final void setHisFlowDeptDataRightCtrlType(FlowDeptDataRightCtrlType value) {
		this.SetValByKey(FlowAttr.DRCtrlType, value);
	}

	/**
	 * 流程应用类型
	 */
	public final FlowAppType getFlowAppType() {
		return FlowAppType.forValue(this.GetValIntByKey(FlowAttr.FlowAppType));
	}

	public final void setFlowAppType(FlowAppType value) {
		this.SetValByKey(FlowAttr.FlowAppType, value.getValue());
	}

	/**
	 * 延续流程方式
	 */
	public final CFlowWay getCFlowWay() {
		return CFlowWay.forValue(this.GetValIntByKey(FlowAttr.CFlowWay));
	}

	public final void setCFlowWay(CFlowWay value) {
		this.SetValByKey(FlowAttr.CFlowWay, value.getValue());
	}

	/**
	 * 延续流程参数。
	 */
	public final String getCFlowPara() {
		return this.GetValStrByKey(FlowAttr.CFlowPara);
	}

	public final void setCFlowPara(String value) {
		this.SetValByKey(FlowAttr.CFlowPara, value);
	}

	/**
	 * 流程备注的表达式
	 */
	public final String getFlowNoteExp() {
		return this.GetValStrByKey(FlowAttr.FlowNoteExp);
	}

	public final void setFlowNoteExp(String value) {
		this.SetValByKey(FlowAttr.FlowNoteExp, value);
	}

	// /#endregion 业务处理

	// /#region 创建新工作.
	/**
	 * 创建新工作web方式调用的
	 * 
	 * @return
	 */
	public final Work NewWork() {
		return NewWork(WebUser.getNo());
	}

	/**
	 * 创建新工作.web方式调用的
	 * 
	 * @param empNo
	 *            人员编号
	 * @return
	 */
	public final Work NewWork(String empNo) {
		Emp emp = new Emp(empNo);
		return NewWork(emp, null);
	}

	/**
	 * 产生一个开始节点的新工作
	 * 
	 * @param emp
	 *            发起人
	 * @param paras
	 *            参数集合,如果是CS调用，要发起子流程，要从其他table里copy数据,就不能从request里面取,可以传递为null.
	 * @return 返回的Work.
	 */
	public final Work NewWork(Emp emp, java.util.Hashtable<String, Object> paras) {
		// 如果是bs系统.
		if (paras == null) {
			paras = new java.util.Hashtable<String, Object>();
		}
		if (BP.Sys.SystemConfig.getIsBSsystem()) {
			Enumeration enu = BP.Sys.Glo.getRequest().getParameterNames();
			while (enu.hasMoreElements()) {
				// 判断是否有内容，hasNext()
				String key = (String) enu.nextElement();
				paras.put(key, BP.Sys.Glo.getRequest().getParameter(key));
			}
		}

		// 开始节点.
		Node nd = new Node(this.getStartNodeID());

		// 从草稿里看看是否有新工作？
		StartWork wk = (StartWork) nd.getHisWork();
		wk.ResetDefaultVal();

		String dbstr = SystemConfig.getAppCenterDBVarStr();

		Paras ps = new Paras();
		GERpt rpt = this.getHisGERpt();

		// 是否新创建的WorKID
		boolean IsNewWorkID = false;
		// 如果要启用草稿,就创建一个新的WorkID .
		if (this.getDraftRole() != DraftRole.None && nd.getIsStartNode()) {
			IsNewWorkID = true;
		}

		try {
			// 从报表里查询该数据是否存在？
			if (this.getIsGuestFlow() == true && StringHelper.isNullOrEmpty(GuestUser.getNo()) == false) {
				// 是客户参与的流程，并且具有客户登陆的信息。
				ps.SQL = "SELECT OID,FlowEndNode FROM " + this.getPTable() + " WHERE GuestNo=" + dbstr
						+ "GuestNo AND WFState=" + dbstr + "WFState ";
				ps.Add(GERptAttr.GuestNo, GuestUser.getNo());
				ps.Add(GERptAttr.WFState, WFState.Blank.getValue());
				DataTable dt = DBAccess.RunSQLReturnTable(ps);
				if (dt.Rows.size() > 0 && IsNewWorkID == false) {
					wk.setOID(Long.parseLong(dt.getValue(0, 0).toString()));
					int nodeID = Integer.parseInt(dt.getValue(0, 1).toString());
					if (nodeID != this.getStartNodeID()) {
						String error = "@这里出现了blank的状态下流程运行到其它的节点上去了的情况。";
						Log.DefaultLogWriteLineError(error);
						throw new RuntimeException(error);
					}
				}
			} else {
				ps.SQL = "SELECT OID,FlowEndNode FROM " + this.getPTable() + " WHERE FlowStarter=" + dbstr
						+ "FlowStarter AND WFState=" + dbstr + "WFState ";
				ps.Add(GERptAttr.FlowStarter, emp.getNo());
				ps.Add(GERptAttr.WFState, WFState.Blank.getValue());
				// throw new Exception(ps.SQL);
				DataTable dt = null;
				try {
					dt = DBAccess.RunSQLReturnTable(ps);
				} catch (Exception e) {
					this.CheckRpt();
					dt = DBAccess.RunSQLReturnTable(ps);
				}
				// 如果没有启用草稿，并且存在草稿就取第一条 by dgq 5.28
				if (dt.Rows.size() > 0 && IsNewWorkID == false) {
					// wk.setOID(Long.parseLong(dt.Rows[0][0].toString() ) );
					wk.setOID(Long.parseLong(dt.getValue(0, 0).toString()));
					wk.RetrieveFromDBSources();
					int nodeID = Integer.parseInt(dt.getValue(0, 0).toString());
					if (nodeID != this.getStartNodeID()) {
						String error = "@这里出现了blank的状态下流程运行到其它的节点上去了的情况，当前停留节点:" + nodeID;
						Log.DefaultLogWriteLineError(error);
						// throw new Exception(error);
					}
				}
			}

			// 启用草稿或空白就创建WorkID
			if (wk.getOID() == 0 || IsNewWorkID == true) {
				// 说明没有空白,就创建一个空白..
				wk.ResetDefaultVal();
				wk.setRec(WebUser.getNo());

				wk.SetValByKey(StartWorkAttr.RecText, emp.getName());
				wk.SetValByKey(StartWorkAttr.Emps, emp.getNo());

				wk.SetValByKey(WorkAttr.RDT, BP.DA.DataType.getCurrentDataTime());
				wk.SetValByKey(WorkAttr.CDT, BP.DA.DataType.getCurrentDataTime());
				wk.SetValByKey(GERptAttr.WFState, WFState.Blank.getValue());

				wk.setOID(DBAccess.GenerOID("WorkID")); // 这里产生WorkID
														// ,这是唯一产生WorkID的地方.

				// 把尽量可能的流程字段放入，否则会出现冲掉流程字段属性.
				wk.SetValByKey(GERptAttr.FK_NY, BP.DA.DataType.getCurrentYearMonth());
				wk.SetValByKey(GERptAttr.FK_Dept, emp.getFK_Dept());
				wk.setFID(0);
				wk.DirectInsert();

				// 初始化选择的人员.
				this.InitSelectAccper(nd, wk.getOID());

				// 设置参数.
				for (String k : paras.keySet()) {
					rpt.SetValByKey(k, paras.get(k));
				}

				if (this.getPTable().equals(wk.getEnMap().getPhysicsTable())) {
					// 如果开始节点表与流程报表相等.
					rpt.setOID(wk.getOID());
					rpt.RetrieveFromDBSources();
					rpt.setFID(0);
					rpt.setFlowStartRDT(BP.DA.DataType.getCurrentDataTime());
					rpt.setMyNum(0);
					rpt.setTitle(WorkNode.GenerTitle(this, wk));
					// WebUser.No + "," + BP.Web.WebUser.Name + "在" +
					// DataType.CurrentDataCNOfShort + "发起.";
					rpt.setWFState(WFState.Blank);
					rpt.setFlowStarter(emp.getNo());
					rpt.setFK_NY(DataType.getCurrentYearMonth());
					if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserNameOnly) {
						rpt.setFlowEmps("@" + emp.getName());
					}

					if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
						rpt.setFlowEmps("@" + emp.getNo());
					}

					if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
						rpt.setFlowEmps("@" + emp.getNo() + "," + emp.getName());
					}

					rpt.setFlowEnderRDT(BP.DA.DataType.getCurrentDataTime());
					rpt.setFK_Dept(emp.getFK_Dept());
					rpt.setFlowEnder(emp.getNo());
					rpt.setFlowEndNode(this.getStartNodeID());

					// 生成单据编号.
					// Object tempVar = this.getBillNoFormat().clone();
					Object tempVar = new String(this.getBillNoFormat());
					String billNoFormat = (String) ((tempVar instanceof String) ? tempVar : null);
					if (StringHelper.isNullOrEmpty(billNoFormat) == false) {
						rpt.setBillNo(BP.WF.Glo.GenerBillNo(billNoFormat, rpt.getOID(), rpt, this.getPTable()));
					}

					rpt.setFID(0);
					rpt.DirectUpdate();
				} else {
					rpt.setOID(wk.getOID());
					rpt.setFID(0);
					rpt.setFlowStartRDT(BP.DA.DataType.getCurrentDataTime());
					rpt.setFlowEnderRDT(BP.DA.DataType.getCurrentDataTime());
					rpt.setMyNum(0);

					rpt.setTitle(WorkNode.GenerTitle(this, wk));
					// rpt.Title = WebUser.No + "," + BP.Web.WebUser.Name + "在"
					// + DataType.CurrentDataCNOfShort + "发起.";

					rpt.setWFState(WFState.Blank);
					rpt.setFlowStarter(emp.getNo());

					rpt.setFlowEndNode(this.getStartNodeID());
					if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserNameOnly) {
						rpt.setFlowEmps("@" + emp.getName());
					}

					if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
						rpt.setFlowEmps("@" + emp.getNo());
					}

					if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
						rpt.setFlowEmps("@" + emp.getNo() + "," + emp.getName());
					}

					rpt.setFK_NY(DataType.getCurrentYearMonth());
					rpt.setFK_Dept(emp.getFK_Dept());
					rpt.setFlowEnder(emp.getNo());
					rpt.InsertAsOID(wk.getOID());
				}
			}
		} catch (RuntimeException ex) {
			wk.CheckPhysicsTable();
			ex.printStackTrace();
			String error = "@创建工作失败：有可能是您在设计表单时候，新增加的控件，没有预览导致的，请您刷新一次应该可以解决 ";
			if (!StringHelper.isNullOrEmpty(ex.getMessage())) {
				error += "@ 技术信息:" + ex.getMessage();
			}
			throw new RuntimeException(error);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// /#region copy数据.
		// 记录这个id ,不让其它在复制时间被修改。
		long newOID = wk.getOID();
		if (IsNewWorkID == true) {
			// 处理传递过来的参数。
			int i = 0;
			for (String k : paras.keySet()) {
				i++;
				wk.SetValByKey(k, paras.get(k).toString());
			}

			if (i >= 3) {
				wk.setOID(newOID);
				wk.DirectUpdate();
			}
		}

		// /#endregion copy数据.

		// /#region 处理删除草稿的需求。
		if (paras.containsKey(StartFlowParaNameList.IsDeleteDraft)
				&& paras.get(StartFlowParaNameList.IsDeleteDraft).toString().equals("1")) {
			// 是否要删除Draft
			long oid = wk.getOID();
			try {
				// wk.ResetDefaultValAllAttr();
				wk.DirectUpdate();
			} catch (RuntimeException ex) {
				wk.Update();
				BP.DA.Log.DebugWriteError("创建新工作错误，但是屏蔽了异常,请检查默认值的问题：" + ex.getMessage());
			}

			MapDtls dtls = wk.getHisMapDtls();
			for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
				DBAccess.RunSQL("DELETE FROM " + dtl.getPTable() + " WHERE RefPK=" + oid);
			}

			// 删除附件数据。
			DBAccess.RunSQL("DELETE FROM Sys_FrmAttachmentDB WHERE FK_MapData='ND" + wk.getNodeID() + "' AND RefPKVal='"
					+ wk.getOID() + "'");
			wk.setOID(newOID);
		}

		// /#endregion 处理删除草稿的需求。

		// /#region 处理开始节点, 如果传递过来 FromTableName 就是要从这个表里copy数据。
		if (paras.containsKey("FromTableName")) {
			String tableName = paras.get("FromTableName").toString();
			String tablePK = paras.get("FromTablePK").toString();
			String tablePKVal = paras.get("FromTablePKVal").toString();

			DataTable dt = DBAccess
					.RunSQLReturnTable("SELECT * FROM " + tableName + " WHERE " + tablePK + "='" + tablePKVal + "'");
			if (dt.Rows.size() == 0) {
				throw new RuntimeException("@利用table传递数据错误，没有找到指定的行数据，无法为用户填充数据。");
			}

			String innerKeys = ",OID,RDT,CDT,FID,WFState,";
			for (DataColumn dc : dt.Columns) {
				if (innerKeys.contains("," + dc.ColumnName + ",")) {
					continue;
				}

				wk.SetValByKey(dc.ColumnName, dt.getValue(0, dc.ColumnName).toString());
				rpt.SetValByKey(dc.ColumnName, dt.getValue(0, dc.ColumnName).toString());
			}
			rpt.Update();
		}

		// /#endregion 处理开始节点, 如果传递过来 FromTableName 就是要从这个表里copy数据。

		// /#region 获取特殊标记变量
		// 获取特殊标记变量.
		String PFlowNo = null;
		String PNodeIDStr = null;
		String PWorkIDStr = null;
		String PFIDStr = null;

		String CopyFormWorkID = null;
		if (paras.containsKey("CopyFormWorkID") == true) {
			CopyFormWorkID = paras.get("CopyFormWorkID").toString();
			PFlowNo = this.getNo();
			PNodeIDStr = paras.get("CopyFormNode").toString();
			PWorkIDStr = CopyFormWorkID;
			PFIDStr = "0";
		}

		if (paras.containsKey("PNodeID") == true) {
			PFlowNo = paras.get("PFlowNo").toString();
			PNodeIDStr = paras.get("PNodeID").toString();
			PWorkIDStr = paras.get("PWorkID").toString();
			PFIDStr = "0";
			if (paras.containsKey("PFID") == true) {
				PFIDStr = paras.get("PFID").toString(); // 父流程.
			}
		}

		// /#endregion 获取特殊标记变量

		// /#region 判断是否装载上一条数据.
		if (this.getIsLoadPriData() == true && this.getStartGuideWay() == StartGuideWay.None) {
			// 如果需要从上一个流程实例上copy数据.
			String sql = "SELECT OID FROM " + this.getPTable() + " WHERE FlowStarter='" + WebUser.getNo()
					+ "' AND OID!=" + wk.getOID() + " ORDER BY OID DESC";
			String workidPri = DBAccess.RunSQLReturnStringIsNull(sql, "0");
			if (workidPri.equals("0")) {
				// 说明没有第一笔数据.
			} else {
				PFlowNo = this.getNo();
				PNodeIDStr = Integer.parseInt(this.getNo()) + "01";
				PWorkIDStr = workidPri;
				PFIDStr = "0";
				CopyFormWorkID = workidPri;
			}
		}

		// /#endregion 判断是否装载上一条数据.

		// /#region 处理流程之间的数据传递1。
		if (StringHelper.isNullOrEmpty(PNodeIDStr) == false && StringHelper.isNullOrEmpty(PWorkIDStr) == false) {
			long PWorkID = Long.parseLong(PWorkIDStr);
			long PNodeID = 0;
			if (CopyFormWorkID != null) {
				PNodeID = Long.parseLong(PNodeIDStr);
			}

			// 如果是从另外的一个流程上传递过来的，就考虑另外的流程数据。

			// /#region copy 首先从父流程的NDxxxRpt copy.
			long pWorkIDReal = 0;
			Flow pFlow = new Flow(PFlowNo);
			String pOID = "";
			if (StringHelper.isNullOrEmpty(PFIDStr) == true || PFIDStr.equals("0")) {
				pOID = (new Long(PWorkID)).toString();
			} else {
				pOID = PFIDStr;
			}

			String sql = "SELECT * FROM " + pFlow.getPTable() + " WHERE OID=" + pOID;
			DataTable dt = DBAccess.RunSQLReturnTable(sql);
			if (dt.Rows.size() != 1) {
				throw new RuntimeException("@不应该查询不到父流程的数据, 可能的情况之一,请确认该父流程的调用节点是子线程，但是没有把子线程的FID参数传递进来。");
			}

			wk.Copy(dt.Rows.get(0));
			rpt.Copy(dt.Rows.get(0));

			// /#endregion copy 首先从父流程的NDxxxRpt copy.

			// /#region 从调用的节点上copy.
			BP.WF.Template.Node fromNd = new BP.WF.Template.Node(Integer.parseInt(PNodeIDStr));
			Work wkFrom = fromNd.getHisWork();
			wkFrom.setOID(PWorkID);
			if (wkFrom.RetrieveFromDBSources() == 0) {
				throw new RuntimeException("@父流程的工作ID不正确，没有查询到数据" + PWorkID);
			}
			// wk.Copy(wkFrom);
			// rpt.Copy(wkFrom);

			// /#endregion 从调用的节点上copy.

			// /#region 获取web变量.
			for (String k : paras.keySet()) {
				wk.SetValByKey(k, paras.get(k));
				rpt.SetValByKey(k, paras.get(k));
			}

			// /#endregion 获取web变量.

			// /#region 特殊赋值.
			wk.setOID(newOID);
			rpt.setOID(newOID);

			// 在执行copy后，有可能这两个字段会被冲掉。
			if (CopyFormWorkID != null) {
				// 如果不是执行的从已经完成的流程copy.

				wk.SetValByKey(StartWorkAttr.PFlowNo, PFlowNo);
				wk.SetValByKey(StartWorkAttr.PNodeID, PNodeID);
				wk.SetValByKey(StartWorkAttr.PWorkID, PWorkID);

				rpt.SetValByKey(GERptAttr.PFlowNo, PFlowNo);
				rpt.SetValByKey(GERptAttr.PNodeID, PNodeID);
				rpt.SetValByKey(GERptAttr.PWorkID, PWorkID);
				rpt.SetValByKey(GERptAttr.PEmp, WebUser.getNo());

				rpt.SetValByKey(GERptAttr.FID, 0);

				rpt.SetValByKey(GERptAttr.FlowStartRDT, BP.DA.DataType.getCurrentDataTime());
				rpt.SetValByKey(GERptAttr.FlowEnderRDT, BP.DA.DataType.getCurrentDataTime());
				rpt.SetValByKey(GERptAttr.MyNum, 0);
				rpt.SetValByKey(GERptAttr.WFState, WFState.Blank.getValue());
				rpt.SetValByKey(GERptAttr.FlowStarter, emp.getNo());
				rpt.SetValByKey(GERptAttr.FlowEnder, emp.getNo());
				rpt.SetValByKey(GERptAttr.FlowEndNode, this.getStartNodeID());
				rpt.SetValByKey(GERptAttr.FK_Dept, emp.getFK_Dept());
				rpt.SetValByKey(GERptAttr.FK_NY, DataType.getCurrentYearMonth());

				if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserNameOnly) {
					rpt.SetValByKey(GERptAttr.FlowEmps, "@" + emp.getName());
				}

				if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
					rpt.SetValByKey(GERptAttr.FlowEmps, "@" + emp.getNo());
				}

				if (BP.WF.Glo.getUserInfoShowModel() == UserInfoShowModel.UserIDUserName) {
					rpt.SetValByKey(GERptAttr.FlowEmps, "@" + emp.getNo() + "," + emp.getName());
				}
				// 生成单据编号.
				// Object tempVar2 = this.getBillNoFormat().clone();
				Object tempVar2 = new String(this.getBillNoFormat());
				String billNoFormat = (String) ((tempVar2 instanceof String) ? tempVar2 : null);
				if (StringHelper.isNullOrEmpty(billNoFormat) == false) {
					rpt.SetValByKey(GERptAttr.BillNo,
							BP.WF.Glo.GenerBillNo(billNoFormat, rpt.getOID(), rpt, this.getPTable()));
				}
			}

			if (rpt.getEnMap().getPhysicsTable() != wk.getEnMap().getPhysicsTable()) {
				wk.Update(); // 更新工作节点数据.
			}
			rpt.Update(); // 更新流程数据表.

			// /#endregion 特殊赋值.

			// /#region 复制表单其他数据.
			// 复制明细。
			MapDtls dtls = wk.getHisMapDtls();
			if (dtls.size() > 0) {
				MapDtls dtlsFrom = wkFrom.getHisMapDtls();
				int idx = 0;
				if (dtlsFrom.size() == dtls.size()) {
					for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
						if (dtl.getIsCopyNDData() == false) {
							continue;
						}

						// new 一个实例.
						GEDtl dtlData = new GEDtl(dtl.getNo());
						MapDtl dtlFrom = (MapDtl) ((dtlsFrom.get(idx) instanceof MapDtl) ? dtlsFrom.get(idx) : null);

						GEDtls dtlsFromData = new GEDtls(dtlFrom.getNo());
						dtlsFromData.Retrieve(GEDtlAttr.RefPK, PWorkID);
						for (GEDtl geDtlFromData : GEDtls.convertGEDtls(dtlsFromData)) {
							dtlData.Copy(geDtlFromData);
							dtlData.setRefPK((new Long(wk.getOID())).toString());
							if (PFlowNo.equals(this.getNo())) {
								dtlData.InsertAsNew();
							} else {
								dtlData.SaveAsOID((int) geDtlFromData.getOID());
							}
						}
					}
				}
			}

			// 复制附件数据。
			if (wk.getHisFrmAttachments().size() > 0) {
				if (wkFrom.getHisFrmAttachments().size() > 0) {
					int toNodeID = wk.getNodeID();

					// 删除数据。
					DBAccess.RunSQL("DELETE FROM Sys_FrmAttachmentDB WHERE FK_MapData='ND" + toNodeID
							+ "' AND RefPKVal='" + wk.getOID() + "'");
					FrmAttachmentDBs athDBs = new FrmAttachmentDBs("ND" + PNodeIDStr, (new Long(PWorkID)).toString());

					for (FrmAttachmentDB athDB : FrmAttachmentDBs.convertFrmAttachmentDBs(athDBs)) {
						FrmAttachmentDB athDB_N = new FrmAttachmentDB();
						athDB_N.Copy(athDB);
						athDB_N.setFK_MapData("ND" + toNodeID);
						athDB_N.setRefPKVal((new Long(wk.getOID())).toString());
						athDB_N.setFK_FrmAttachment(
								athDB_N.getFK_FrmAttachment().replace("ND" + PNodeIDStr, "ND" + toNodeID));

						if (athDB_N.getHisAttachmentUploadType() == AttachmentUploadType.Single) {
							// 如果是单附件.
							athDB_N.setMyPK(athDB_N.getFK_FrmAttachment() + "_" + wk.getOID());
							if (athDB_N.getIsExits() == true) {
								continue; // 说明上一个节点或者子线程已经copy过了,
											// 但是还有子线程向合流点传递数据的可能，所以不能用break.
							}
							athDB_N.Insert();
						} else {
							athDB_N.setMyPK(
									athDB_N.getUploadGUID() + "_" + athDB_N.getFK_MapData() + "_" + wk.getOID());
							athDB_N.Insert();
						}
					}
				}
			}

			// /#endregion 复制表单其他数据.

		}

		// /#endregion 处理流程之间的数据传递1。

		// /#region 处理单据编号.
		// 生成单据编号.
		if (this.getBillNoFormat().length() > 3) {
			// Object tempVar3 = this.getBillNoFormat().clone();
			Object tempVar3 = new String(this.getBillNoFormat());
			String billNoFormat = (String) ((tempVar3 instanceof String) ? tempVar3 : null);

			if (billNoFormat.contains("@")) {
				for (String str : paras.keySet()) {
					billNoFormat = billNoFormat.replace("@" + str, paras.get(str).toString());
				}
			}

			// 生成单据编号.
			rpt.setBillNo(BP.WF.Glo.GenerBillNo(billNoFormat, rpt.getOID(), rpt, this.getPTable()));
			// rpt.Update(GERptAttr.BillNo, rpt.BillNo);
			if (wk.getRow().containsKey(GERptAttr.BillNo) == true) {
				wk.SetValByKey(NDXRptBaseAttr.BillNo, rpt.getBillNo());
				// wk.Update(GERptAttr.BillNo, rpt.BillNo);
			}
		}

		// /#endregion 处理单据编号.

		// /#region 处理流程之间的数据传递2, 如果是直接要跳转到指定的节点上去.
		if (paras.containsKey("JumpToNode") == true) {
			wk.setRec(WebUser.getNo());
			wk.SetValByKey(StartWorkAttr.RDT, BP.DA.DataType.getCurrentDataTime());
			wk.SetValByKey(StartWorkAttr.CDT, BP.DA.DataType.getCurrentDataTime());
			wk.SetValByKey("FK_NY", DataType.getCurrentYearMonth());
			wk.setFK_Dept(emp.getFK_Dept());
			wk.SetValByKey("FK_DeptName", emp.getFK_DeptText());
			wk.SetValByKey("FK_DeptText", emp.getFK_DeptText());
			wk.setFID(0);
			wk.SetValByKey(StartWorkAttr.RecText, emp.getName());

			int jumpNodeID = Integer.parseInt(paras.get("JumpToNode").toString());
			Node jumpNode = new Node(jumpNodeID);

			String jumpToEmp = paras.get("JumpToEmp").toString();
			if (StringHelper.isNullOrEmpty(jumpToEmp)) {
				jumpToEmp = emp.getNo();
			}

			WorkNode wn = new WorkNode(wk, nd);
			wn.NodeSend(jumpNode, jumpToEmp);

			WorkFlow wf = new WorkFlow(this, wk.getOID(), wk.getFID());

			GenerWorkFlow gwf = new GenerWorkFlow(rpt.getOID());
			rpt.setWFState(WFState.Runing);
			rpt.Update();

			return wf.GetCurrentWorkNode().getHisWork();
		}

		// /#endregion 处理流程之间的数据传递。

		// /#region 最后整理wk数据.
		wk.setRec(emp.getNo());
		wk.SetValByKey(WorkAttr.RDT, BP.DA.DataType.getCurrentDataTime());
		wk.SetValByKey(WorkAttr.CDT, BP.DA.DataType.getCurrentDataTime());
		wk.SetValByKey("FK_NY", DataType.getCurrentYearMonth());
		wk.setFK_Dept(emp.getFK_Dept());
		wk.SetValByKey("FK_DeptName", emp.getFK_DeptText());
		wk.SetValByKey("FK_DeptText", emp.getFK_DeptText());

		wk.SetValByKey(NDXRptBaseAttr.BillNo, rpt.getBillNo());
		wk.setFID(0);
		wk.SetValByKey(StartWorkAttr.RecText, emp.getName());
		if (wk.getIsExits() == false) {
			wk.DirectInsert();
		}

		// #region 给generworkflow初始化数据. add 2015-08-06
		GenerWorkFlow mygwf = new GenerWorkFlow();
		mygwf.setWorkID(wk.getOID());
		if (mygwf.RetrieveFromDBSources() == 0) {
			mygwf.setStarter(WebUser.getNo());
			mygwf.setStarterName(WebUser.getName());
			mygwf.setFK_Dept(WebUser.getFK_Dept());
			mygwf.setDeptName(WebUser.getFK_DeptName());
			mygwf.setFK_Flow(this.getNo());
			mygwf.setFK_FlowSort(this.getFK_FlowSort());
			mygwf.setFK_Node(nd.getNodeID());
			mygwf.setWorkID(wk.getOID());
			mygwf.setWFState(WFState.Blank);
			mygwf.setFlowName(this.getName());
			mygwf.setRDT(BP.DA.DataType.getCurrentDataTime());
			if (StringHelper.isNullOrEmpty(PNodeIDStr) == false && StringHelper.isNullOrEmpty(PWorkIDStr) == false) {
				if (StringHelper.isNullOrEmpty(PFIDStr) == false)
					mygwf.setPFID(Integer.parseInt(PFIDStr));
				mygwf.setPEmp(rpt.getPEmp());
				mygwf.setPFlowNo(rpt.getPFlowNo());
				mygwf.setPNodeID(rpt.getPNodeID());
				mygwf.setPWorkID(rpt.getPWorkID());
			}
			mygwf.Insert();
		} else {
			if (StringHelper.isNullOrEmpty(PNodeIDStr) == false && StringHelper.isNullOrEmpty(PWorkIDStr) == false) {
				if (StringHelper.isNullOrEmpty(PFIDStr) == false)
					mygwf.setPFID(Integer.parseInt(PFIDStr));
				mygwf.setPEmp(rpt.getPEmp());
				mygwf.setPFlowNo(rpt.getPFlowNo());
				mygwf.setPNodeID(rpt.getPNodeID());
				mygwf.setPWorkID(rpt.getPWorkID());
				mygwf.DirectUpdate();
			}
		}
		// #endregion 给generworkflow初始化数据.

		return wk;
	}

	/**
	 * 系统发起是就处理各个节点的接受人员.
	 * 
	 * @param currND
	 * @param workid
	 */
	public final void InitSelectAccper(Node currND, long workid) {
		if (this.getIsFullSA() == false) {
			return;
		}

		// 查询出来所有的节点.
		Nodes nds = new Nodes(this.getNo());

		// 开始节点需要特殊处理》
		// 如果启用了要计算未来的处理人
		SelectAccper sa = new SelectAccper();

		sa.setFK_Emp(WebUser.getNo());
		sa.setFK_Node(currND.getNodeID());
		sa.setWorkID(workid);
		sa.ResetPK();
		if (sa.RetrieveFromDBSources() == 0) {
			sa.setAccType(0);
			sa.setEmpName(WebUser.getName());
			sa.Insert();
		} else {
			sa.setAccType(0);
			sa.setEmpName(WebUser.getName());
			sa.Update();
		}

		for (Node item : Nodes.convertNodes(nds)) {
			if (item.getIsStartNode() == true) {
				continue;
			}

			// 如果按照岗位计算（默认的第一个规则.）
			if (item.getHisDeliveryWay() == DeliveryWay.ByStation) {
				String sql = "SELECT No, Name FROM Port_Emp WHERE No IN (SELECT A.FK_Emp FROM "
						+ BP.WF.Glo.getEmpStation()
						+ " A, WF_NodeStation B WHERE A.FK_Station=B.FK_Station AND B.FK_Node=" + item.getNodeID()
						+ ")";
				DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
				if (dt.Rows.size() != 1) {
					continue;
				}

				String no = dt.getValue(0, 0).toString();
				String name = dt.getValue(0, 1).toString();

				// sa.Delete(SelectAccperAttr.FK_Node,item.NodeID,
				// SelectAccperAttr.WorkID, workid); //删除已经存在的数据.
				sa.setFK_Emp(no);
				sa.setEmpName(name);
				sa.setFK_Node(item.getNodeID());
				sa.setWorkID(workid);
				sa.setInfo("无");
				sa.setAccType(0);
				sa.ResetPK();
				if (sa.getIsExits()) {
					continue;
				}

				sa.Insert();
				continue;
			}

			// 处理与指定节点相同的人员.
			if (item.getHisDeliveryWay() == DeliveryWay.BySpecNodeEmp
					&& (new Integer(currND.getNodeID())).toString().equals(item.getDeliveryParas())) {

				sa.setFK_Emp(WebUser.getNo());
				sa.setFK_Node(item.getNodeID());
				sa.setWorkID(workid);
				sa.setInfo("无");
				sa.setAccType(0);
				sa.setEmpName(WebUser.getName());

				sa.ResetPK();
				if (sa.getIsExits()) {
					continue;
				}

				sa.Insert();
				continue;
			}

			// 处理绑定的节点人员..
			if (item.getHisDeliveryWay() == DeliveryWay.ByBindEmp) {
				NodeEmps nes = new NodeEmps();
				nes.Retrieve(NodeEmpAttr.FK_Node, item.getNodeID());
				for (NodeEmp ne : NodeEmps.convertNodeEmps(nes)) {
					sa.setFK_Emp(ne.getFK_Emp());
					sa.setFK_Node(item.getNodeID());
					sa.setWorkID(workid);
					sa.setInfo("无");
					sa.setAccType(0);
					sa.setEmpName(ne.getFK_EmpT());

					sa.ResetPK();
					if (sa.getIsExits()) {
						continue;
					}

					sa.Insert();
				}
			}

			// 按照节点的 岗位与部门的交集计算.

			// /#region 按部门与岗位的交集计算.
			if (item.getHisDeliveryWay() == DeliveryWay.ByDeptAndStation) {
				String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
				String sql = "SELECT No FROM Port_Emp WHERE No IN ";
				sql += "(SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ";
				sql += "( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node=" + dbStr + "FK_Node1)";
				sql += ")";
				sql += "AND No IN ";
				sql += "(";
				sql += "SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation() + " WHERE FK_Station IN ";
				sql += "( SELECT FK_Station FROM WF_NodeStation WHERE FK_Node=" + dbStr + "FK_Node1 )";
				sql += ") ORDER BY No ";

				Paras ps = new Paras();
				ps.Add("FK_Node1", item.getNodeID());
				ps.Add("FK_Node2", item.getNodeID());
				ps.SQL = sql;
				DataTable dt = DBAccess.RunSQLReturnTable(ps);
				for (DataRow dr : dt.Rows) {
					Emp emp = new Emp(dr.getValue(0).toString());
					sa.setFK_Emp(emp.getNo());
					sa.setFK_Node(item.getNodeID());
					sa.setWorkID(workid);
					sa.setInfo("无");
					sa.setAccType(0);
					sa.setEmpName(emp.getName());

					sa.ResetPK();
					if (sa.getIsExits()) {
						continue;
					}

					sa.Insert();
				}
			}

			// /#endregion 按部门与岗位的交集计算.
		}

		// 预制当前节点到达节点的数据。
		Nodes toNDs = currND.getHisToNodes();
		for (Node item : Nodes.convertNodes(toNDs)) {
			if (item.getHisDeliveryWay() == DeliveryWay.ByStation) {
				// 如果按照岗位访问

				// Java:
				// /#region 最后判断 - 按照岗位来执行。
				String dbStr = BP.Sys.SystemConfig.getAppCenterDBVarStr();
				String sql = "";
				Paras ps = new Paras();
				// 如果执行节点 与 接受节点岗位集合不一致
				// 没有查询到的情况下, 先按照本部门计算。
				if (this.getFlowAppType() == FlowAppType.Normal) {
					switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
					case MySQL:
					case MSSQL:
						sql = "select No from Port_Emp x inner join (select FK_Emp from " + BP.WF.Glo.getEmpStation()
								+ " a inner join WF_NodeStation b ";
						sql += " on a.FK_Station=b.FK_Station where FK_Node=" + dbStr
								+ "FK_Node) as y on x.No=y.FK_Emp inner join Port_EmpDept z on";
						sql += " x.No=z.FK_Emp where z.FK_Dept =" + dbStr + "FK_Dept order by x.No";
						break;
					default:
						sql = "SELECT No FROM Port_Emp WHERE NO IN " + "(SELECT  FK_Emp  FROM "
								+ BP.WF.Glo.getEmpStation()
								+ " WHERE FK_Station IN (SELECT FK_Station FROM WF_NodeStation WHERE FK_Node=" + dbStr
								+ "FK_Node) )" + " AND  NO IN " + "(SELECT  FK_Emp  FROM Port_EmpDept WHERE FK_Dept ="
								+ dbStr + "FK_Dept)";
						sql += " ORDER BY No ";
						break;
					}

					ps = new Paras();
					ps.SQL = sql;
					ps.Add("FK_Node", item.getNodeID());
					ps.Add("FK_Dept", WebUser.getFK_Dept());
				}

				DataTable dt = DBAccess.RunSQLReturnTable(ps);
				for (DataRow dr : dt.Rows) {
					Emp emp = new Emp(dr.getValue(0).toString());
					sa.setFK_Emp(emp.getNo());
					sa.setFK_Node(item.getNodeID());
					sa.setWorkID(workid);
					sa.setInfo("无");
					sa.setAccType(0);
					sa.setEmpName(emp.getName());

					sa.ResetPK();
					if (sa.getIsExits()) {
						continue;
					}

					sa.Insert();
				}

				// Java:
				// /#endregion 按照岗位来执行。
			}
		}
	}

	// /#endregion 创建新工作.

	// /#region 初始化一个工作.
	/**
	 * 初始化一个工作
	 * 
	 * @param workid
	 * @param fk_node
	 * @return
	 */
	public final Work GenerWork(long workid, Node nd, boolean isPostBack) {
		Work wk = nd.getHisWork();
		wk.setOID(workid);
		if (wk.RetrieveFromDBSources() == 0) {

			//
			// * 2012-10-15 偶然发现一次工作丢失情况, WF_GenerWorkerlist WF_GenerWorkFlow
			// 都有这笔数据，没有查明丢失原因。 stone.
			// * 用如下代码自动修复，但是会遇到数据copy不完全的问题。
			// *

			// /#warning 2011-10-15 偶然发现一次工作丢失情况.

			String fk_mapData = "ND" + Integer.parseInt(this.getNo()) + "Rpt";
			GERpt rpt = new GERpt(fk_mapData);
			rpt.setOID(Integer.parseInt((new Long(workid)).toString()));
			if (rpt.RetrieveFromDBSources() >= 1) {
				// 查询到报表数据.
				wk.Copy(rpt);
				wk.setRec(WebUser.getNo());
				try {
					wk.InsertAsOID(workid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 没有查询到报表数据.

				// Java:
				// /#warning 这里不应该出现的异常信息.

				String msg = "@不应该出现的异常.";
				msg += "@在为节点NodeID=" + nd.getNodeID() + " workid:" + workid + " 获取数据时.";
				msg += "@获取它的Rpt表数据时，不应该查询不到。";
				msg += "@GERpt 信息: table:" + rpt.getEnMap().getPhysicsTable() + "   OID=" + rpt.getOID();

				String sql = "SELECT count(*) FROM " + rpt.getEnMap().getPhysicsTable() + " WHERE OID=" + workid;
				int num = DBAccess.RunSQLReturnValInt(sql);

				msg += " @SQL:" + sql;
				msg += " ReturnNum:" + num;
				if (num == 0) {
					msg += "已经用sql可以查询出来，但是不应该用类查询不出来.";
				} else {
					// 如果可以用sql 查询出来.
					num = rpt.RetrieveFromDBSources();
					msg += "@从rpt.RetrieveFromDBSources = " + num;
				}

				Log.DefaultLogWriteLineError(msg);

				MapData md = new MapData("ND" + Integer.parseInt(nd.getFK_Flow()) + "01");
				sql = "SELECT * FROM " + md.getPTable() + " WHERE OID=" + workid;
				DataTable dt = DBAccess.RunSQLReturnTable(sql);
				if (dt.Rows.size() == 1) {
					rpt.Copy(dt.Rows.get(0));

					try {
						rpt.setFlowStarter(dt.getValue(0, StartWorkAttr.Rec).toString());
						rpt.setFlowStartRDT(dt.getValue(0, StartWorkAttr.RDT).toString());
						rpt.setFK_Dept(dt.getValue(0, StartWorkAttr.FK_Dept).toString());
					} catch (java.lang.Exception e) {
					}

					rpt.setOID(Integer.parseInt((new Long(workid)).toString()));
					try {
						rpt.InsertAsOID(rpt.getOID());
					} catch (Exception ex) {
						Log.DefaultLogWriteLineError(
								"@不应该出插入不进去 rpt:" + rpt.getEnMap().getPhysicsTable() + " workid=" + workid);
						rpt.RetrieveFromDBSources();
					}
				} else {
					Log.DefaultLogWriteLineError("@没有找到开始节点的数据, NodeID:" + nd.getNodeID() + " workid:" + workid);
					throw new RuntimeException(
							"@没有找到开始节点的数据, NodeID:" + nd.getNodeID() + " workid:" + workid + " SQL:" + sql);
				}

				// Java:
				// /#warning 不应该出现的工作丢失.
				Log.DefaultLogWriteLineError("@工作[" + nd.getNodeID() + " : " + wk.getEnDesc() + "], 报表数据WorkID="
						+ workid + " 丢失, 没有从NDxxxRpt里找到记录,请联系管理员。");

				wk.Copy(rpt);
				wk.setRec(WebUser.getNo());
				wk.ResetDefaultVal();
				wk.Insert();
			}
		}

		// /#region 判断是否有删除草稿的需求.
		if (SystemConfig.getIsBSsystem() == true && isPostBack == false && nd.getIsStartNode()
				&& BP.Sys.Glo.getRequest().getParameter("IsDeleteDraft").toString().equals("1")) {

			// 需要删除草稿.
			// 是否要删除Draft
			String title = wk.GetValStringByKey("Title");
			wk.ResetDefaultValAllAttr();
			wk.setOID(workid);
			wk.SetValByKey(GenerWorkFlowAttr.Title, title);
			wk.DirectUpdate();

			MapDtls dtls = wk.getHisMapDtls();
			for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
				DBAccess.RunSQL("DELETE FROM " + dtl.getPTable() + " WHERE RefPK=" + wk.getOID());
			}

			// 删除附件数据。
			DBAccess.RunSQL("DELETE FROM Sys_FrmAttachmentDB WHERE FK_MapData='ND" + wk.getNodeID() + "' AND RefPKVal='"
					+ wk.getOID() + "'");

		}

		// /#endregion

		// 设置当前的人员把记录人。
		wk.setRec(WebUser.getNo());
		wk.setRecText(WebUser.getName());
		wk.setRec(WebUser.getNo());
		wk.SetValByKey(WorkAttr.RDT, BP.DA.DataType.getCurrentDataTime());
		wk.SetValByKey(WorkAttr.CDT, BP.DA.DataType.getCurrentDataTime());
		wk.SetValByKey(GERptAttr.WFState, WFState.Runing);
		wk.SetValByKey("FK_Dept", WebUser.getFK_Dept());
		wk.SetValByKey("FK_DeptName", WebUser.getFK_DeptName());
		wk.SetValByKey("FK_DeptText", WebUser.getFK_DeptName());
		wk.setFID(0);
		wk.SetValByKey("RecText", WebUser.getName());

		// 处理单据编号.
		if (nd.getIsStartNode()) {
			try {
				String billNo = wk.GetValStringByKey(NDXRptBaseAttr.BillNo);
				if (StringHelper.isNullOrEmpty(billNo) && nd.getHisFlow().getBillNoFormat().length() > 2) {
					// 让他自动生成编号
					wk.SetValByKey(NDXRptBaseAttr.BillNo, BP.WF.Glo.GenerBillNo(nd.getHisFlow().getBillNoFormat(),
							wk.getOID(), wk, nd.getHisFlow().getPTable()));
				}
			} catch (java.lang.Exception e2) {
				// 可能是没有billNo这个字段,也不需要处理它.
			}
		}

		return wk;
	}

	// /#endregion 初始化一个工作

	// /#region 其他通用方法.
	public final String DoBTableDTS() {
		if (this.getDTSWay() == FlowDTSWay.None) {
			return "执行失败，您没有设置同步方式。";
		}

		String info = "";
		GenerWorkFlows gwfs = new GenerWorkFlows();
		gwfs.Retrieve(GenerWorkFlowAttr.FK_Flow, this.getNo());
		for (GenerWorkFlow gwf : GenerWorkFlows.convertGenerWorkFlows(gwfs)) {
			GERpt rpt = this.getHisGERpt();
			rpt.setOID(gwf.getWorkID());
			rpt.RetrieveFromDBSources();

			info += "@开始同步:" + gwf.getTitle() + ",WorkID=" + gwf.getWorkID();
			if (gwf.getWFSta() == WFSta.Complete) {
				info += this.DoBTableDTS(rpt, new Node(gwf.getFK_Node()), true);
			} else {
				info += this.DoBTableDTS(rpt, new Node(gwf.getFK_Node()), false);
			}
		}
		return info;
	}

	/**
	 * 同步当前的流程数据到业务数据表里.
	 * 
	 * @param rpt
	 *            流程报表
	 * @param currNode
	 *            当前节点ID
	 * @param isStopFlow
	 *            流程是否结束
	 * @return 返回同步结果.
	 */
	public final String DoBTableDTS(GERpt rpt, Node currNode, boolean isStopFlow) {
		boolean isActiveSave = false;
		// 判断是否符合流程数据同步条件.
		switch (this.getDTSTime()) {
		case AllNodeSend:
			isActiveSave = true;
			break;
		case SpecNodeSend:
			if (this.getDTSSpecNodes().contains((new Integer(currNode.getNodeID())).toString()) == true) {
				isActiveSave = true;
			}
			break;
		case WhenFlowOver:
			if (isStopFlow) {
				isActiveSave = true;
			}
			break;
		default:
			break;
		}
		if (isActiveSave == false) {
			return "";
		}

		// /#region qinfaliang, 编写同步的业务逻辑,执行错误就抛出异常.

		String[] dtsArray = this.getDTSFields().split("[@]", -1);

		String[] lcDTSFieldsArray = dtsArray[0].split("[,]", -1);
		String[] ywDTSFieldsArray = dtsArray[1].split("[,]", -1);

		String sql = "SELECT " + dtsArray[0] + " FROM " + this.getPTable().toUpperCase() + " WHERE OID=" + rpt.getOID();
		DataTable lcDt = DBAccess.RunSQLReturnTable(sql);
		if (lcDt.Rows.size() == 0) {
			return "";
		}

		sql = "SELECT " + dtsArray[1] + " FROM " + this.getDTSBTable().toUpperCase();
		DataTable ywDt = DBAccess.RunSQLReturnTable(sql);

		String values = "";

		for (int i = 0; i < lcDTSFieldsArray.length; i++) {
			switch (SystemConfig.getAppCenterDBType()) {
			case MSSQL:
				break;
			case MySQL:
				break;
			case Oracle:

				if (ywDt.Columns.get(ywDTSFieldsArray[i]).DataType == java.util.Date.class) {
					if (!StringHelper.isNullOrEmpty(lcDt.getValue(0, lcDTSFieldsArray[i].toString()).toString())) {
						values += "to_date('" + lcDt.getValue(0, lcDTSFieldsArray[i].toString()) + "','YYYY-MM-DD'),";
					} else {
						values += "'',";
					}
					continue;
				}
				values += "'" + lcDt.getValue(0, lcDTSFieldsArray[i].toString()) + "',";
				continue;
			default:
				throw new RuntimeException("暂时不支您所使用的数据库类型!");
			}

			values += "'" + lcDt.getValue(0, lcDTSFieldsArray[i].toString()) + "',";
		}

		values = values.substring(0, values.length() - 1);

		sql = "INSERT INTO " + this.getDTSBTable() + "(" + dtsArray[1] + ") VALUES(" + values + ")";
		try {
			DBAccess.RunSQL(sql);
		} catch (RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		// /#endregion qinfaliang, 编写同步的业务逻辑,执行错误就抛出异常.

		return "同步成功.";
	}

	/**
	 * 自动发起
	 * 
	 * @return
	 */
	public final String DoAutoStartIt() {
		switch (this.getHisFlowRunWay()) {
		case SpecEmp: // 指定人员按时运行。
			String RunObj = this.getRunObj();
			String FK_Emp = RunObj.substring(0, RunObj.indexOf('@'));
			BP.Port.Emp emp = new BP.Port.Emp();
			emp.setNo(FK_Emp);
			if (emp.RetrieveFromDBSources() == 0) {
				return "启动自动启动流程错误：发起人(" + FK_Emp + ")不存在。";
			}

			WebUser.SignInOfGener(emp);
			String info_send = BP.WF.Dev2Interface.Node_StartWork(this.getNo(), null, null, 0, null, 0, null)
					.ToMsgOfText();
			if (!WebUser.getNo().equals("admin")) {
				emp = new BP.Port.Emp();
				emp.setNo("admin");
				emp.Retrieve();
				WebUser.SignInOfGener(emp);
				return info_send;
			}
			return info_send;
		case DataModel: // 按数据集合驱动的模式执行。
			break;
		default:
			return "@该流程您没有设置为自动启动的流程类型。";
		}

		String msg = "";
		MapExt me = new MapExt();
		me.setMyPK("ND" + Integer.parseInt(this.getNo()) + "01_" + MapExtXmlList.StartFlow);
		int i = me.RetrieveFromDBSources();
		if (i == 0) {
			BP.DA.Log.DefaultLogWriteLineError("没有为流程(" + this.getName() + ")的开始节点设置发起数据,请参考说明书解决.");
			return "没有为流程(" + this.getName() + ")的开始节点设置发起数据,请参考说明书解决.";
		}
		if (StringHelper.isNullOrEmpty(me.getTag())) {
			BP.DA.Log.DefaultLogWriteLineError("没有为流程(" + this.getName() + ")的开始节点设置发起数据,请参考说明书解决.");
			return "没有为流程(" + this.getName() + ")的开始节点设置发起数据,请参考说明书解决.";
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

		// /#region 检查数据源是否正确.
		String errMsg = "";
		// 获取主表数据.
		DataTable dtMain = BP.DA.DBAccess.RunSQLReturnTable(me.getTag());
		if (dtMain.Rows.size() == 0) {
			return "流程(" + this.getName() + ")此时无任务,查询语句:" + me.getTag().replace("'", "”");
		}

		msg += "@查询到(" + dtMain.Rows.size() + ")条任务.";

		if (dtMain.Columns.contains("Starter") == false) {
			errMsg += "@配值的主表中没有Starter列.";
		}

		if (dtMain.Columns.contains("MainPK") == false) {
			errMsg += "@配值的主表中没有MainPK列.";
		}

		if (errMsg.length() > 2) {
			return "流程(" + this.getName() + ")的开始节点设置发起数据,不完整." + errMsg;
		}

		// /#endregion 检查数据源是否正确.

		// /#region 处理流程发起.

		String fk_mapdata = "ND" + Integer.parseInt(this.getNo()) + "01";

		MapData md = new MapData(fk_mapdata);
		int idx = 0;
		for (DataRow dr : dtMain.Rows) {
			idx++;

			String mainPK = dr.getValue("MainPK").toString();

			String sql = "SELECT OID FROM " + md.getPTable() + " WHERE MainPK='" + mainPK + "'";
			if (DBAccess.RunSQLReturnTable(sql).Rows.size() != 0) {
				msg += "@" + this.getName() + ",第" + idx + "条,此任务在之前已经完成。";
				continue; // 说明已经调度过了
			}

			String starter = dr.getValue("Starter").toString();
			if (!starter.equals(WebUser.getNo())) {
				WebUser.Exit();
				BP.Port.Emp emp = new BP.Port.Emp();
				emp.setNo(starter);
				if (emp.RetrieveFromDBSources() == 0) {
					msg += "@" + this.getName() + ",第" + idx + "条,设置的发起人员:" + emp.getNo() + "不存在.";
					msg += "@数据驱动方式发起流程(" + this.getName() + ")设置的发起人员:" + emp.getNo() + "不存在。";
					continue;
				}
				WebUser.SignInOfGener(emp);
			}

			// /#region 给值.
			Work wk = this.NewWork();
			for (DataColumn dc : dtMain.Columns) {
				wk.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName).toString());

			}

			if (ds.Tables.size() != 0) {
				// MapData md = new MapData(nodeTable);
				MapDtls dtls = md.getMapDtls(); // new MapDtls(nodeTable);
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
							if (!drDtl.getValue("RefMainPK").toString().equals(mainPK)) {
								continue;
							}

							dtlEn = dtl.getHisGEDtl();
							for (DataColumn dc : dt.Columns) {
								dtlEn.SetValByKey(dc.ColumnName, drDtl.getValue(dc.ColumnName).toString());
							}

							dtlEn.setRefPK((new Long(wk.getOID())).toString());
							dtlEn.setOID(0);
							dtlEn.Insert();
						}
					}
				}
			}

			// /#endregion 给值.

			// 处理发送信息.
			Node nd = this.getHisStartNode();
			try {
				WorkNode wn = new WorkNode(wk, nd);
				String infoSend = wn.NodeSend().ToMsgOfHtml();
				BP.DA.Log.DefaultLogWriteLineInfo(msg);
				msg += "@" + this.getName() + ",第" + idx + "条,发起人员:" + WebUser.getNo() + "-" + WebUser.getName()
						+ "已完成.\r\n" + infoSend;
				// this.SetText("@第（" + idx + "）条任务，" + WebUser.No + " - " +
				// WebUser.Name + "已经完成。\r\n" + msg);
			} catch (RuntimeException ex) {
				msg += "@" + this.getName() + ",第" + idx + "条,发起人员:" + WebUser.getNo() + "-" + WebUser.getName()
						+ "发起时出现错误.\r\n" + ex.getMessage();
			}
			msg += "<hr>";
		}
		return msg;

		// /#endregion 处理流程发起.
	}

	/**
	 * UI界面上的访问控制
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
	 * 修补流程数据视图
	 * 
	 * @return
	 */
	public static String RepareV_FlowData_View() {
		String err = "";
		Flows fls = new Flows();
		fls.RetrieveAllFromDBSource();

		if (fls.size() == 0) {
			return null;
		}

		String sql = "";
		sql = "CREATE VIEW V_FlowData (FK_FlowSort,FK_Flow,OID,FID,Title,WFState,CDT,FlowStarter,FlowStartRDT,FK_Dept,FK_NY,FlowDaySpan,FlowEmps,FlowEnder,FlowEnderRDT,FlowEndNode,MyNum, PWorkID,PFlowNo,BillNo,ProjNo) ";
		// sql += "\t\n /* WorkFlow Data " +
		// DateTime.Now.ToString("yyyy-MM-dd") + " */ ";
		sql += " AS ";
		for (Flow fl : Flows.convertFlows(fls)) {
			if (fl.getIsCanStart() == false) {
				continue;
			}

			String mysql = "\t\n SELECT '" + fl.getFK_FlowSort() + "' AS FK_FlowSort,'" + fl.getNo()
					+ "' AS FK_Flow,OID,FID,Title,WFState,CDT,FlowStarter,FlowStartRDT,FK_Dept,FK_NY,FlowDaySpan,FlowEmps,FlowEnder,FlowEnderRDT,FlowEndNode,1 as MyNum,PWorkID,PFlowNo,BillNo,ProjNo FROM "
					+ fl.getPTable() + " WHERE WFState >1 ";
			try {
				DBAccess.RunSQLReturnTable(mysql);
			} catch (RuntimeException ex) {
				continue;
				// try {
				// fl.DoCheck();
				// DBAccess.RunSQLReturnTable(mysql);
				// } catch (RuntimeException ex1) {
				// err += ex1.getMessage();
				// continue;
				// }
			}

			if (fls.size() == 1) {
				break;
			}

			sql += mysql;
			sql += "\t\n UNION ";
		}
		if (sql.contains("SELECT") == false) {
			return null;
		}

		if (fls.size() > 1) {
			sql = sql.substring(0, sql.length() - 6);
		}

		if (sql.length() > 20) {

			// /#region 删除 V_FlowData
			try {
				DBAccess.RunSQL("DROP VIEW V_FlowData");
			} catch (java.lang.Exception e) {
				try {
					DBAccess.RunSQL("DROP table V_FlowData");
				} catch (java.lang.Exception e2) {
				}
			}

			// /#endregion 删除 V_FlowData

			// /#region 创建视图.
			try {
				DBAccess.RunSQL(sql);
			} catch (java.lang.Exception e3) {
			}

			// /#endregion 创建视图.

		}
		return null;
	}

	/**
	 * 校验流程
	 * 
	 * @return
	 */
	public final String DoCheck() {

		// /#region 检查流程表单
		FrmNodes fns = new FrmNodes();
		fns.Retrieve(FrmNodeAttr.FK_Flow, this.getNo());
		String frms = "";
		String err = "";
		for (FrmNode item : FrmNodes.convertFrmNodes(fns)) {
			if (frms.contains(item.getFK_Frm() + ",")) {
				continue;
			}
			frms += item.getFK_Frm() + ",";
			try {
				MapData md = new MapData(item.getFK_Frm());
				md.RepairMap();
				Entity en = md.getHisEn();
				en.CheckPhysicsTable();
			} catch (RuntimeException ex) {
				err += "@节点绑定的表单:" + item.getFK_Frm() + ",已经被删除了.异常信息." + ex.getMessage();
			}
		}

		// /#endregion

		try {
			// 设置流程名称.
			DBAccess.RunSQL("UPDATE WF_Node SET FlowName = (SELECT Name FROM WF_Flow WHERE NO=WF_Node.FK_Flow)");

			// 删除垃圾,非法数据.
			String sqls = "DELETE FROM Sys_FrmSln where fk_mapdata not in (select no from sys_mapdata)";
			sqls += "@ DELETE FROM WF_Direction WHERE Node=ToNode";
			DBAccess.RunSQLs(sqls);

			// 更新计算数据.
			this.setNumOfBill(DBAccess.RunSQLReturnValInt(
					"SELECT count(*) FROM WF_BillTemplate WHERE NodeID IN (select NodeID from WF_Flow WHERE no='"
							+ this.getNo() + "')"));
			this.setNumOfDtl(DBAccess.RunSQLReturnValInt(
					"SELECT count(*) FROM Sys_MapDtl WHERE FK_MapData='ND" + Integer.parseInt(this.getNo()) + "Rpt'"));
			this.DirectUpdate();

			String msg = "@  =======  关于《" + this.getName() + " 》流程检查报告  ============";
			msg += "@信息输出分为三种: 信息  警告  错误. 如果遇到输出的错误，则必须要去修改或者设置.";
			msg += "@流程检查目前还不能覆盖100%的错误,需要手工的运行一次才能确保流程设计的正确性.";

			Nodes nds = new Nodes(this.getNo());

			// 单据模版.
			BillTemplates bks = new BillTemplates(this.getNo());

			// 条件集合.
			Conds conds = new Conds(this.getNo());

			// /#region 对节点进行检查
			for (Node nd : Nodes.convertNodes(nds)) {
				// 设置它的位置类型.
				nd.SetValByKey(NodeAttr.NodePosType, nd.GetHisNodePosType().getValue());

				msg += "@信息: -------- 开始检查节点ID:(" + nd.getNodeID() + ")名称:(" + nd.getName() + ")信息 -------------";

				// Java:
				// /#region 修复数节点表单数据库.
				msg += "@信息:开始补充&修复节点必要的字段";
				try {
					nd.RepareMap();
				} catch (RuntimeException ex) {
					throw new RuntimeException("@修复节点表必要字段时出现错误:" + nd.getName() + " - " + ex.getMessage());
				}

				msg += "@信息:开始修复节点物理表.";
				DBAccess.RunSQL(
						"UPDATE Sys_MapData SET Name='" + nd.getName() + "' WHERE No='ND" + nd.getNodeID() + "'");
				try {
					nd.getHisWork().CheckPhysicsTable();
				} catch (RuntimeException ex) {
					msg += "@检查节点表字段时出现错误:" + "NodeID" + nd.getNodeID() + " Table:"
							+ nd.getHisWork().getEnMap().getPhysicsTable() + " Name:" + nd.getName()
							+ " , 节点类型NodeWorkTypeText=" + nd.getNodeWorkTypeText() + "出现错误.@err=" + ex.getMessage();
				}

				// 从表检查。
				MapDtls dtls = new MapDtls("ND" + nd.getNodeID());
				for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
					msg += "@检查明细表:" + dtl.getName();
					try {
						dtl.getHisGEDtl().CheckPhysicsTable();
					} catch (RuntimeException ex) {
						msg += "@检查明细表时间出现错误" + ex.getMessage();
					}
				}

				// Java:
				// /#endregion 修复数节点表单数据库.

				MapAttrs mattrs = new MapAttrs("ND" + nd.getNodeID());

				// Java:
				// /#region 对节点的访问规则进行检查

				msg += "@信息:开始对节点的访问规则进行检查.";

				switch (nd.getHisDeliveryWay()) {
				case ByStation:
					if (nd.getNodeStations().size() == 0) {
						msg += "@错误:您设置了该节点的访问规则是按岗位，但是您没有为节点绑定岗位。";
					}
					break;
				case ByDept:
					if (nd.getNodeDepts().size() == 0) {
						msg += "@错误:您设置了该节点的访问规则是按部门，但是您没有为节点绑定部门。";
					}
					break;
				case ByBindEmp:
					if (nd.getNodeEmps().size() == 0) {
						msg += "@错误:您设置了该节点的访问规则是按人员，但是您没有为节点绑定人员。";
					}
					break;
				case BySpecNodeEmp: // 按指定的岗位计算.
				case BySpecNodeEmpStation: // 按指定的岗位计算.
					if (nd.getDeliveryParas().trim().length() == 0) {
						msg += "@错误:您设置了该节点的访问规则是按指定的岗位计算，但是您没有设置节点编号.</font>";
					} else {
						if (DataType.IsNumStr(nd.getDeliveryParas()) == false) {
							msg += "@错误:您没有设置指定岗位的节点编号，目前设置的为{" + nd.getDeliveryParas() + "}";
						}
					}
					break;
				case ByDeptAndStation: // 按部门与岗位的交集计算.
					String mysql = "SELECT No FROM Port_Emp WHERE No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept IN ( SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node="
							+ nd.getNodeID() + "))AND No IN (SELECT FK_Emp FROM " + BP.WF.Glo.getEmpStation()
							+ " WHERE FK_Station IN ( SELECT FK_Station FROM WF_NodeStation WHERE FK_Node="
							+ nd.getNodeID() + " )) ORDER BY No ";
					DataTable mydt = DBAccess.RunSQLReturnTable(mysql);
					if (mydt.Rows.size() == 0) {
						msg += "@错误:按照岗位与部门的交集计算错误，没有人员集合{" + mysql + "}";
					}
					break;
				case BySQL:
				case BySQLAsSubThreadEmpsAndData:
					if (nd.getDeliveryParas().trim().length() == 0) {
						msg += "@错误:您设置了该节点的访问规则是按SQL查询，但是您没有在节点属性里设置查询sql，此sql的要求是查询必须包含No,Name两个列，sql表达式里支持@+字段变量，详细参考开发手册.";
					} else {
						try {
							String sql = nd.getDeliveryParas();
							for (MapAttr item : MapAttrs.convertMapAttrs(mattrs)) {
								if (item.getIsNum()) {
									sql = sql.replace("@" + item.getKeyOfEn(), "0");
								} else {
									sql = sql.replace("@" + item.getKeyOfEn(), "'0'");
								}
							}

							sql = sql.replace("@WebUser.No", "'ss'");
							sql = sql.replace("@WebUser.Name", "'ss'");
							sql = sql.replace("@WebUser.FK_Dept", "'ss'");
							sql = sql.replace("@WebUser.FK_DeptName", "'ss'");

							if (sql.contains("@")) {
								throw new RuntimeException("您编写的sql变量填写不正确，实际执行中，没有被完全替换下来" + sql);
							}

							DataTable testDB = null;
							try {
								testDB = DBAccess.RunSQLReturnTable(sql);
							} catch (RuntimeException ex) {
								msg += "@错误:您设置了该节点的访问规则是按SQL查询,执行此语句错误." + ex.getMessage();
							}

							if (testDB.Columns.contains("No") == false || testDB.Columns.contains("Name") == false) {
								msg += "@错误:您设置了该节点的访问规则是按SQL查询，设置的sql不符合规则，此sql的要求是查询必须包含No,Name两个列，sql表达式里支持@+字段变量，详细参考开发手册.";
							}
						} catch (RuntimeException ex) {
							msg += ex.getMessage();
						}
					}
					break;
				case ByPreviousNodeFormEmpsField:
					if (mattrs.Contains(MapAttrAttr.KeyOfEn, nd.getDeliveryParas()) == false) {
						// 检查节点字段是否有FK_Emp字段
						msg += "@错误:您设置了该节点的访问规则是按指定节点表单人员，但是您没有在节点表单中增加FK_Emp字段，详细参考开发手册.";
					}
					if (mattrs.Contains(MapAttrAttr.KeyOfEn, "FK_Emp") == false) {
						// 检查节点字段是否有FK_Emp字段
						msg += "@错误:您设置了该节点的访问规则是按指定节点表单人员，但是您没有在节点表单中增加FK_Emp字段，详细参考开发手册 .";
					}
					break;
				case BySelected: // 由上一步发送人员选择
					if (nd.getIsStartNode()) {
						msg += "@错误:开始节点不能设置指定的选择人员访问规则。";
						break;
					}
					break;
				case ByPreviousNodeEmp: // 由上一步发送人员选择
					if (nd.getIsStartNode()) {
						msg += "@错误:节点访问规则设置错误:开始节点，不允许设置与上一节点的工作人员相同.";
						break;
					}
					break;
				default:
					break;
				}
				msg += "@对节点的访问规则进行检查完成....";

				// Java:
				// /#endregion

				// Java:
				// /#region 检查节点完成条件，方向条件的定义.
				// 设置它没有流程完成条件.
				nd.setIsCCFlow(false);

				if (conds.size() != 0) {
					msg += "@信息:开始检查(" + nd.getName() + ")方向条件:";
					for (Cond cond : Conds.convertConds(conds)) {
						if (cond.getFK_Node() == nd.getNodeID() && cond.getHisCondType() == CondType.Flow) {
							nd.setIsCCFlow(true);
							nd.Update();
						}

						Node ndOfCond = new Node();
						ndOfCond.setNodeID(ndOfCond.getNodeID());
						if (ndOfCond.RetrieveFromDBSources() == 0) {
							continue;
						}

						try {
							if (cond.getAttrKey().length() < 2) {
								continue;
							}
							if (ndOfCond.getHisWork().getEnMap().getAttrs().Contains(cond.getAttrKey()) == false) {
								throw new RuntimeException(
										"@错误:属性:" + cond.getAttrKey() + " , " + cond.getAttrName() + " 不存在。");
							}
						} catch (RuntimeException ex) {
							msg += "@错误:" + ex.getMessage();
							ndOfCond.Delete();
						}
						msg += cond.getAttrKey() + cond.getAttrName() + cond.getOperatorValue() + "、";
					}
					msg += "@(" + nd.getName() + ")方向条件检查完成.....";
				}

				// Java:
				// /#endregion 检查节点完成条件的定义.
			}

			// /#endregion

			msg += "@流程的基础信息: ------ ";
			msg += "@编号:  " + this.getNo() + " 名称:" + this.getName() + " , 存储表:" + this.getPTable();

			msg += "@信息:开始检查节点流程报表.";
			this.DoCheck_CheckRpt(this.getHisNodes());

			// /#region 检查焦点字段设置是否还有效
			msg += "@信息:开始检查节点的焦点字段";

			// 获得gerpt字段.
			GERpt rpt = this.getHisGERpt();
			for (Node nd : Nodes.convertNodes(nds)) {
				if (nd.getFocusField().trim().equals("")) {
					Work wk = nd.getHisWork();
					String attrKey = "";
					for (Attr attr : wk.getEnMap().getAttrs()) {
						if (attr.getUIVisible() == true && attr.getUIIsDoc() && attr.getUIIsReadonly() == false) {
							attrKey = attr.getDesc() + ":@" + attr.getKey();
						}
					}
					if (attrKey.equals("")) {
						msg += "@警告:节点ID:" + nd.getNodeID() + " 名称:" + nd.getName()
								+ "属性里没有设置焦点字段，会导致信息写入轨迹表空白，为了能够保证流程轨迹是可读的请设置焦点字段.";
					} else {
						msg += "@信息:节点ID:" + nd.getNodeID() + " 名称:" + nd.getName()
								+ "属性里没有设置焦点字段，会导致信息写入轨迹表空白，为了能够保证流程轨迹是可读的系统自动设置了焦点字段为" + attrKey + ".";
						nd.setFocusField(attrKey);
						nd.DirectUpdate();
					}
					continue;
				}

				// Object tempVar = nd.getFocusField().clone();
				Object tempVar = new String(nd.getFocusField());
				String strs = (String) ((tempVar instanceof String) ? tempVar : null);
				strs = BP.WF.Glo.DealExp(strs, rpt, "err");
				if (strs.contains("@") == true) {
					msg += "@错误:焦点字段（" + nd.getFocusField() + "）在节点(step:" + nd.getStep() + " 名称:" + nd.getName()
							+ ")属性里的设置已无效，表单里不存在该字段.";
				} else {
					msg += "@提示:节点的(" + nd.getNodeID() + "," + nd.getName() + ")焦点字段（" + nd.getFocusField()
							+ "）设置完整检查通过.";
				}

				if (this.getIsMD5()) {
					if (nd.getHisWork().getEnMap().getAttrs().Contains(WorkAttr.MD5) == false) {
						nd.RepareMap();
					}
				}
			}
			msg += "@信息:检查节点的焦点字段完成.";

			// /#endregion

			// /#region 检查质量考核点.
			msg += "@信息:开始检查质量考核点";
			for (Node nd : Nodes.convertNodes(nds)) {
				if (nd.getIsEval()) {
					// 如果是质量考核点，检查节点表单是否具别质量考核的特别字段？
					String sql = "SELECT COUNT(*) FROM Sys_MapAttr WHERE FK_MapData='ND" + nd.getNodeID()
							+ "' AND KeyOfEn IN ('EvalEmpNo','EvalEmpName','EvalEmpCent')";
					if (DBAccess.RunSQLReturnValInt(sql) != 3) {
						msg += "@信息:您设置了节点(" + nd.getNodeID() + "," + nd.getName()
								+ ")为质量考核节点，但是您没有在该节点表单中设置必要的节点考核字段.";
					}
				}
			}
			msg += "@检查质量考核点完成.";

			// /#endregion

			msg += "@流程报表检查完成...";

			// 检查流程.
			Node.CheckFlow(this);

			// 生成 V001 视图.
			CheckRptView(nds);
			return msg;
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("@检查流程错误:" + ex.getMessage() + " @" + ex.getStackTrace());
		}
	}

	// /#endregion 其他方法.

	// /#region 产生数据模板。
	private static String PathFlowDesc;
	static {
		PathFlowDesc = SystemConfig.getPathOfDataUser() + "FlowDesc" + File.separator;
	}

	/**
	 * 生成流程模板
	 * 
	 * @return
	 */
	public final String GenerFlowXmlTemplete() {

		// String name = this.getName();
		// name = StringExpressionCalculate.ReplaceBadCharOfFileName(name);
		//
		// String path = this.getNo() + "." + name;
		// path = PathFlowDesc + path + "\\";
		// this.DoExpFlowXmlTemplete(path);
		//
		// name = path + name + "." + this.getVer().replace(":", "_") + ".xml";
		// return name;
		String name = this.getNo() + "_" + this.getName(), path = PathFlowDesc + name + File.separator;
		this.DoExpFlowXmlTemplete(path);
		return path + this.getName() + "_" + this.getVer().replace(":", "_") + ".xml";
	}

	/**
	 * 生成流程模板
	 * 
	 * @param path
	 * @return
	 */
	public final DataSet DoExpFlowXmlTemplete(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.mkdirs();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		DataSet ds = GetFlow(path);
		if (ds != null) {
			String filePath = path + this.getName() + "_" + this.getVer().replace(":", "_") + ".xml";
			try {
				ds.WriteXml(filePath, XmlWriteMode.WriteSchema, ds);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return ds;
	}

	// xml文件是否正在操作中
	private static boolean isXmlLocked;

	public final void WriteToXml() {
		DataSet ds = null;
		String path = PathFlowDesc + this.getNo() + "." + this.getName() + File.separator;
		ds = GetFlow(path);
		WriteToXml(ds);
	}

	/**
	 * 备份当前流程到用户xml文件 用户每次保存时调用
	 */
	public final void WriteToXml(DataSet ds) {
		String path = PathFlowDesc + this.getNo() + "." + this.getName() + File.separator;
		String xmlName = path + "Flow" + ".xml";
		File file = new File(path);
		File filexmlName = new File(xmlName);
		if (!StringHelper.isNullOrEmpty(path)) {
			if (!file.exists()) {
				try {
					file.mkdirs();
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else if (filexmlName.exists()) {
				long modifiedTime = filexmlName.lastModified();
				Date date = new Date(modifiedTime);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String dd = sdf.format(date);
				String xmlNameOld = path + "Flow" + dd + ".xml";
				File filexmlNameOld = new File(xmlNameOld);
				if (filexmlNameOld.exists()) {
					filexmlNameOld.delete();
				}
				FileAccess.Move(filexmlName, xmlNameOld);
				// File.move(xmlName, xmlNameOld);
			}
		}

		try {
			if (!StringHelper.isNullOrEmpty(xmlName) && null != ds) {
				ds.WriteXml(xmlName, XmlWriteMode.WriteSchema, ds);
			}
		} catch (RuntimeException e) {
			isXmlLocked = false;
			BP.DA.Log.DefaultLogWriteLineError("流程模板文件备份错误:" + e.getMessage());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public final DataSet GetFlow(String path) {
		// 把所有的数据都存储在这里。
		DataSet ds = new DataSet();

		// 流程信息。
		String sql = "SELECT * FROM WF_Flow WHERE No='" + this.getNo() + "'";
		DataTable dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_Flow";
		ds.Tables.add(dt);

		// 节点信息
		sql = "SELECT * FROM WF_Node WHERE FK_Flow='" + this.getNo() + "'";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_Node";
		ds.Tables.add(dt);

		// 文书信息
		BillTemplates tmps = new BillTemplates(this.getNo());
		String pks = "";
		for (BillTemplate tmp : BillTemplates.convertBillTemplates(tmps)) {
			try {
				if (path != null) {
					// System.IO.File.Copy(SystemConfig.PathOfDataUser
					// + "\\CyclostyleFile\\" + tmp.getNo() + ".rtf", path
					// + "\\" + tmp.getNo() + ".rtf", true);
					FileAccess.Copy(SystemConfig.getPathOfDataUser() + File.separator + "CyclostyleFile"
							+ File.separator + tmp.getNo() + ".rtf", path + File.separator + tmp.getNo() + ".rtf");
				}
			} catch (java.lang.Exception e) {
				pks += "@" + tmp.getPKVal();
				tmp.Delete();
			}
		}
		tmps.Remove(pks);
		ds.Tables.add(tmps.ToDataTableField("WF_BillTemplate"));

		String sqlin = "SELECT NodeID FROM WF_Node WHERE fk_flow='" + this.getNo() + "'";

		// 流程表单树
		sql = "SELECT * FROM WF_FlowFormTree WHERE FK_Flow='" + this.getNo() + "'";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_FlowFormTree";
		ds.Tables.add(dt);

		// // 流程表单
		// sql = "SELECT * FROM WF_FlowForm WHERE FK_Flow='" + this.No + "'";
		// dt = DBAccess.RunSQLReturnTable(sql);
		// dt.TableName = "WF_FlowForm";
		// ds.Tables.Add(dt);

		// // 节点表单权限
		// sql = "SELECT * FROM WF_NodeForm WHERE FK_Node IN (" + sqlin + ")";
		// dt = DBAccess.RunSQLReturnTable(sql);
		// dt.TableName = "WF_NodeForm";
		// ds.Tables.Add(dt);

		// 条件信息
		sql = "SELECT * FROM WF_Cond WHERE FK_Flow='" + this.getNo() + "'";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_Cond";
		ds.Tables.add(dt);

		// 转向规则.
		sql = "SELECT * FROM WF_TurnTo WHERE FK_Flow='" + this.getNo() + "'";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_TurnTo";
		ds.Tables.add(dt);

		// 节点与表单绑定.
		sql = "SELECT * FROM WF_FrmNode WHERE FK_Flow='" + this.getNo() + "'";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_FrmNode";
		ds.Tables.add(dt);

		// 表单方案.
		sql = "SELECT * FROM Sys_FrmSln WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmSln";
		ds.Tables.add(dt);

		// 方向
		sql = "SELECT * from WF_Direction WHERE Node IN (" + sqlin + ") OR ToNode In (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_Direction";
		ds.Tables.add(dt);

		// // 应用设置 FAppSet
		// sql = "SELECT * FROM WF_FAppSet WHERE FK_Flow='" + this.No + "'";
		// dt = DBAccess.RunSQLReturnTable(sql);
		// dt.TableName = "WF_FAppSet";
		// ds.Tables.Add(dt);

		// 流程标签.
		LabNotes labs = new LabNotes(this.getNo());
		ds.Tables.add(labs.ToDataTableField("WF_LabNote"));

		// 消息监听.
		Listens lts = new Listens(this.getNo());
		ds.Tables.add(lts.ToDataTableField("WF_Listen"));

		// 可退回的节点。
		sql = "SELECT * FROM WF_NodeReturn WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_NodeReturn";
		ds.Tables.add(dt);

		// 工具栏。
		sql = "SELECT * FROM WF_NodeToolbar WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_NodeToolbar";
		ds.Tables.add(dt);

		// 节点与部门。
		sql = "SELECT * FROM WF_NodeDept WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_NodeDept";
		ds.Tables.add(dt);

		// 节点与岗位权限。
		sql = "SELECT * FROM WF_NodeStation WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_NodeStation";
		ds.Tables.add(dt);

		// 节点与人员。
		sql = "SELECT * FROM WF_NodeEmp WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_NodeEmp";
		ds.Tables.add(dt);

		// 抄送人员。
		sql = "SELECT * FROM WF_CCEmp WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_CCEmp";
		ds.Tables.add(dt);

		// 抄送部门。
		sql = "SELECT * FROM WF_CCDept WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_CCDept";
		ds.Tables.add(dt);

		// 抄送部门。
		sql = "SELECT * FROM WF_CCStation WHERE FK_Node IN (" + sqlin + ")";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "WF_CCStation";
		ds.Tables.add(dt);

		// // 流程报表。
		// WFRpts rpts = new WFRpts(this.No);
		// // rpts.SaveToXml(path + "WFRpts.xml");
		// ds.Tables.Add(rpts.ToDataTableField("WF_Rpt"));

		// // 流程报表属性
		// RptAttrs rptAttrs = new RptAttrs();
		// rptAttrs.RetrieveAll();
		// ds.Tables.Add(rptAttrs.ToDataTableField("RptAttrs"));

		// // 流程报表访问权限。
		// RptStations rptStations = new RptStations(this.No);
		// rptStations.RetrieveAll();
		// // rptStations.SaveToXml(path + "RptStations.xml");
		// ds.Tables.Add(rptStations.ToDataTableField("RptStations"));

		// // 流程报表人员访问权限。
		// RptEmps rptEmps = new RptEmps(this.No);
		// rptEmps.RetrieveAll();

		// rptEmps.SaveToXml(path + "RptEmps.xml");
		// ds.Tables.Add(rptEmps.ToDataTableField("RptEmps"));
		int flowID = Integer.parseInt(this.getNo());
		sql = "SELECT * FROM Sys_MapData WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "No");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_MapData";
		ds.Tables.add(dt);

		// Sys_MapAttr.
		sql = "SELECT * FROM Sys_MapAttr WHERE  FK_MapData LIKE 'ND" + flowID + "%' ORDER BY FK_MapData,Idx";
		// sql = "SELECT * FROM Sys_MapAttr WHERE " +
		// Glo.MapDataLikeKey(this.No, "FK_MapData") +
		// " ORDER BY FK_MapData,Idx";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_MapAttr";
		ds.Tables.add(dt);

		// Sys_EnumMain
		// sql =
		// "SELECT * FROM Sys_EnumMain WHERE No IN (SELECT KeyOfEn from
		// Sys_MapAttr WHERE "
		// + Glo.MapDataLikeKey(this.No, "FK_MapData") +")";
		sql = "SELECT * FROM Sys_EnumMain WHERE No IN (SELECT KeyOfEn from Sys_MapAttr WHERE FK_MapData LIKE 'ND"
				+ flowID + "%')";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_EnumMain";
		ds.Tables.add(dt);

		// Sys_Enum
		sql = "SELECT * FROM Sys_Enum WHERE EnumKey IN ( SELECT No FROM Sys_EnumMain WHERE No IN (SELECT KeyOfEn from Sys_MapAttr WHERE FK_MapData LIKE 'ND"
				+ flowID + "%' ) )";
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_Enum";
		ds.Tables.add(dt);

		// Sys_MapDtl
		sql = "SELECT * FROM Sys_MapDtl WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_MapDtl";
		ds.Tables.add(dt);

		// Sys_MapExt
		// sql = "SELECT * FROM Sys_MapExt WHERE " + Glo.MapDataLikeKey(this.No,
		// "FK_MapData");
		sql = "SELECT * FROM Sys_MapExt WHERE FK_MapData LIKE 'ND" + flowID + "%'"; // +Glo.MapDataLikeKey(this.No,
																					// "FK_MapData");

		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_MapExt";
		ds.Tables.add(dt);

		// Sys_GroupField
		sql = "SELECT * FROM Sys_GroupField WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "EnName");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_GroupField";
		ds.Tables.add(dt);

		// Sys_MapFrame
		sql = "SELECT * FROM Sys_MapFrame WHERE" + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_MapFrame";
		ds.Tables.add(dt);

		// Sys_MapM2M
		sql = "SELECT * FROM Sys_MapM2M WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_MapM2M";
		ds.Tables.add(dt);

		// Sys_FrmLine.
		sql = "SELECT * FROM Sys_FrmLine WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmLine";
		ds.Tables.add(dt);

		// Sys_FrmLab.
		sql = "SELECT * FROM Sys_FrmLab WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmLab";
		ds.Tables.add(dt);

		// Sys_FrmEle.
		sql = "SELECT * FROM Sys_FrmEle WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmEle";
		ds.Tables.add(dt);

		// Sys_FrmLink.
		sql = "SELECT * FROM Sys_FrmLink WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmLink";
		ds.Tables.add(dt);

		// Sys_FrmRB.
		sql = "SELECT * FROM Sys_FrmRB WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmRB";
		ds.Tables.add(dt);

		// Sys_FrmImgAth.
		sql = "SELECT * FROM Sys_FrmImgAth WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmImgAth";
		ds.Tables.add(dt);

		// Sys_FrmImg.
		sql = "SELECT * FROM Sys_FrmImg WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmImg";
		ds.Tables.add(dt);

		// Sys_FrmAttachment.
		sql = "SELECT * FROM Sys_FrmAttachment WHERE FK_Node=0 AND "
				+ BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmAttachment";
		ds.Tables.add(dt);

		// Sys_FrmEvent.
		sql = "SELECT * FROM Sys_FrmEvent WHERE " + BP.WF.Glo.MapDataLikeKey(this.getNo(), "FK_MapData");
		dt = DBAccess.RunSQLReturnTable(sql);
		dt.TableName = "Sys_FrmEvent";
		ds.Tables.add(dt);

		return ds;
	}

	// /#endregion 产生数据模板。

	// /#region 其他公用方法1
	/**
	 * 重新设置Rpt表
	 */
	public final void CheckRptOfReset() {
		String fk_mapData = "ND" + Integer.parseInt(this.getNo()) + "Rpt";
		String sql = "DELETE FROM Sys_MapAttr WHERE FK_MapData='" + fk_mapData + "'";
		DBAccess.RunSQL(sql);

		sql = "DELETE FROM Sys_MapData WHERE No='" + fk_mapData + "'";
		DBAccess.RunSQL(sql);
		this.DoCheck_CheckRpt(this.getHisNodes());
	}

	/**
	 * 重新装载
	 * 
	 * @return
	 */
	public final String DoReloadRptData() {
		this.DoCheck_CheckRpt(this.getHisNodes());

		// 检查报表数据是否丢失。

		if (this.getHisDataStoreModel() != DataStoreModel.ByCCFlow) {
			return "@流程" + this.getNo() + this.getName() + "的数据存储非轨迹模式不能重新生成.";
		}

		DBAccess.RunSQL("DELETE FROM " + this.getPTable());

		String sql = "SELECT OID FROM ND" + Integer.parseInt(this.getNo()) + "01 WHERE  OID NOT IN (SELECT OID FROM  "
				+ this.getPTable() + " ) ";
		DataTable dt = DBAccess.RunSQLReturnTable(sql);
		this.CheckRptData(this.getHisNodes(), dt);

		return "@共有:" + dt.Rows.size() + "条(" + this.getName() + ")数据被装载成功。";
	}

	/**
	 * 检查与修复报表数据
	 * 
	 * @param nds
	 * @param dt
	 */
	private String CheckRptData(Nodes nds, DataTable dt) {
		GERpt rpt = new GERpt("ND" + Integer.parseInt(this.getNo()) + "Rpt");
		String err = "";
		for (DataRow dr : dt.Rows) {
			rpt.ResetDefaultVal();
			int oid = Integer.parseInt(dr.getValue(0).toString());
			rpt.SetValByKey("OID", oid);
			Work startWork = null;
			Work endWK = null;
			String flowEmps = "";
			for (Node nd : Nodes.convertNodes(nds)) {
				try {
					Work wk = nd.getHisWork();
					wk.setOID(oid);
					if (wk.RetrieveFromDBSources() == 0) {
						continue;
					}

					rpt.Copy(wk);
					if (nd.getNodeID() == Integer.parseInt(this.getNo() + "01")) {
						startWork = wk;
					}

					try {
						if (flowEmps.contains("@" + wk.getRec() + ",")) {
							continue;
						}

						flowEmps += "@" + wk.getRec() + "," + wk.getRecOfEmp().getName();
					} catch (java.lang.Exception e) {
					}
					endWK = wk;
				} catch (RuntimeException ex) {
					err += ex.getMessage();
				}
			}

			if (startWork == null || endWK == null) {
				continue;
			}

			rpt.SetValByKey("OID", oid);
			rpt.setFK_NY(startWork.GetValStrByKey("RDT").substring(0, 7));
			rpt.setFK_Dept(startWork.GetValStrByKey("FK_Dept"));
			if (StringHelper.isNullOrEmpty(rpt.getFK_Dept())) {
				String fk_dept = DBAccess
						.RunSQLReturnString("SELECT FK_Dept FROM Port_Emp WHERE No='" + startWork.getRec() + "'");
				rpt.setFK_Dept(fk_dept);

				startWork.SetValByKey("FK_Dept", fk_dept);
				startWork.Update();
			}
			rpt.setTitle(startWork.GetValStrByKey("Title"));
			String wfState = DBAccess
					.RunSQLReturnStringIsNull("SELECT WFState FROM WF_GenerWorkFlow WHERE WorkID=" + oid, "1");
			rpt.setWFState(WFState.forValue(Integer.parseInt(wfState)));
			rpt.setFlowStarter(startWork.getRec());
			rpt.setFlowStartRDT(startWork.getRDT());
			rpt.setFID(startWork.GetValIntByKey("FID"));
			rpt.setFlowEmps(flowEmps);
			rpt.setFlowEnder(endWK.getRec());
			rpt.setFlowEnderRDT(endWK.getRDT());
			rpt.setFlowEndNode(endWK.getNodeID());
			rpt.setMyNum(1);

			// 修复标题字段。
			WorkNode wn = new WorkNode(startWork, this.getHisStartNode());
			rpt.setTitle(WorkNode.GenerTitle(this, startWork));
			try {
				int day = (int) ((endWK.getRDT_DateTime().getTime() - startWork.getRDT_DateTime().getTime())
						/ (1000 * 60 * 60 * 24));
				rpt.setFlowDaySpan(day);
			} catch (java.lang.Exception e2) {
			}
			try {
				rpt.InsertAsOID(rpt.getOID());
			} catch (Exception e) {

				e.printStackTrace();
			}
		} // 结束循环。
		return err;
	}

	/**
	 * 生成明细报表信息
	 * 
	 * @param nds
	 */
	private void CheckRptDtl(Nodes nds) {
		MapDtls dtlsDtl = new MapDtls();
		dtlsDtl.Retrieve(MapDtlAttr.FK_MapData, "ND" + Integer.parseInt(this.getNo()) + "Rpt");
		for (MapDtl dtl : MapDtls.convertMapDtls(dtlsDtl)) {
			dtl.Delete();
		}

		// dtlsDtl.Delete(MapDtlAttr.FK_MapData, "ND" + int.Parse(this.No) +
		// "Rpt");
		for (Node nd : Nodes.convertNodes(nds)) {
			if (nd.getIsEndNode() == false) {
				continue;
			}

			// 取出来从表.
			MapDtls dtls = new MapDtls("ND" + nd.getNodeID());
			if (dtls.size() == 0) {
				continue;
			}

			String rpt = "ND" + Integer.parseInt(this.getNo()) + "Rpt";
			int i = 0;
			for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
				i++;
				String rptDtlNo = "ND" + Integer.parseInt(this.getNo()) + "RptDtl" + (new Integer(i)).toString();
				MapDtl rtpDtl = new MapDtl();
				rtpDtl.setNo(rptDtlNo);
				if (rtpDtl.RetrieveFromDBSources() == 0) {
					rtpDtl.Copy(dtl);
					rtpDtl.setNo(rptDtlNo);
					rtpDtl.setFK_MapData(rpt);
					rtpDtl.setPTable(rptDtlNo);
					rtpDtl.setGroupID(-1);
					rtpDtl.Insert();
				}

				MapAttrs attrsRptDtl = new MapAttrs(rptDtlNo);
				MapAttrs attrs = new MapAttrs(dtl.getNo());
				for (MapAttr attr : MapAttrs.convertMapAttrs(attrs)) {
					if (attrsRptDtl.Contains(MapAttrAttr.KeyOfEn, attr.getKeyOfEn()) == true) {
						continue;
					}

					MapAttr attrN = new MapAttr();
					attrN.Copy(attr);
					attrN.setFK_MapData(rptDtlNo);
					// C# TO JAVA CONVERTER NOTE: The following 'switch'
					// operated on a string member and was converted to Java
					// 'if-else' logic:
					// switch (attr.KeyOfEn)
					// ORIGINAL LINE: case "FK_NY":
					if (attr.getKeyOfEn().equals("FK_NY")) {
						attrN.setUIVisible(true);
						attrN.setIDX(100);
						attrN.setUIWidth(60);
					}
					// ORIGINAL LINE: case "RDT":
					else if (attr.getKeyOfEn().equals("RDT")) {
						attrN.setUIVisible(true);
						attrN.setIDX(100);
						attrN.setUIWidth(60);
					}
					// ORIGINAL LINE: case "Rec":
					else if (attr.getKeyOfEn().equals("Rec")) {
						attrN.setUIVisible(true);
						attrN.setIDX(100);
						attrN.setUIWidth(60);
					} else {
					}

					attrN.Save();
				}

				GEDtl geDtl = new GEDtl(rptDtlNo);
				geDtl.CheckPhysicsTable();
			}
		}
	}

	/**
	 * 产生所有节点视图
	 * 
	 * @param nds
	 */
	private void CheckRptView(Nodes nds) {
		if (this.getHisDataStoreModel() == DataStoreModel.SpecTable) {
			return;
		}

		String viewName = "V" + this.getNo();
		String sql = "CREATE VIEW " + viewName + " ";
		sql += "\r\n (MyPK,FK_Node,OID,FID,RDT,FK_NY,CDT,Rec,Emps,FK_Dept,MyNum) AS ";
		boolean is1 = false;
		for (Node nd : Nodes.convertNodes(nds)) {
			// nd.HisWork.CheckPhysicsTable();
			if (is1 == false) {
				is1 = true;
			} else {
				sql += "\r\n UNION ";
			}

			switch (SystemConfig.getAppCenterDBType()) {
			case Oracle:
			case Informix:
				sql += "\r\n SELECT '" + nd.getNodeID() + "' || '_'|| OID||'_'|| FID  AS MyPK, '" + nd.getNodeID()
						+ "' AS FK_Node,OID,FID,RDT,SUBSTR(RDT,1,7) AS FK_NY,CDT,Rec,Emps,FK_Dept, 1 AS MyNum FROM ND"
						+ nd.getNodeID() + " ";
				break;
			case MySQL:
				sql += "\r\n SELECT '" + nd.getNodeID() + "'+'_'+CHAR(OID)  +'_'+CHAR(FID)  AS MyPK, '" + nd.getNodeID()
						+ "' AS FK_Node,OID,FID,RDT," + BP.Sys.SystemConfig.getAppCenterDBSubstringStr()
						+ "(RDT,1,7) AS FK_NY,CDT,Rec,Emps,FK_Dept, 1 AS MyNum FROM ND" + nd.getNodeID() + " ";
				break;
			default:
				sql += "\r\n SELECT '" + nd.getNodeID()
						+ "'+'_'+CAST(OID AS varchar(10)) +'_'+CAST(FID AS VARCHAR(10)) AS MyPK, '" + nd.getNodeID()
						+ "' AS FK_Node,OID,FID,RDT," + BP.Sys.SystemConfig.getAppCenterDBSubstringStr()
						+ "(RDT,1,7) AS FK_NY,CDT,Rec,Emps,FK_Dept, 1 AS MyNum FROM ND" + nd.getNodeID() + " ";
				break;
			}
		}
		if (SystemConfig.getAppCenterDBType() != DBType.Informix) {
			sql += "\r\n GO ";
		}

		try {
			if (DBAccess.IsExitsObject(viewName) == true) {
				DBAccess.RunSQL("DROP VIEW " + viewName);
			}
		} catch (java.lang.Exception e) {
		}

		try {
			DBAccess.RunSQL(sql);
		} catch (RuntimeException ex) {
			BP.DA.Log.DefaultLogWriteLineError(ex.getMessage());
		}
	}

	/**
	 * 检查数据报表.
	 * 
	 * @param nds
	 */
	/**
	 * 检查数据报表.
	 * 
	 * @param nds
	 * @throws Exception
	 */
	private void DoCheck_CheckRpt(Nodes nds) {
		String fk_mapData = "ND" + Integer.parseInt(this.getNo()) + "Rpt";
		String flowId = String.valueOf(Integer.parseInt(this.getNo()));

		// 处理track表.
		Track.CreateOrRepairTrackTable(flowId);

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 插入字段。
		String sql = "";
		switch (SystemConfig.getAppCenterDBType()) {
		case Oracle:
		case MSSQL:
			sql = "SELECT distinct  KeyOfEn FROM Sys_MapAttr WHERE FK_MapData IN ( SELECT 'ND' "
					+ SystemConfig.getAppCenterDBAddStringStr()
					+ " cast(NodeID as varchar(20)) FROM WF_Node WHERE FK_Flow='" + this.getNo() + "')";
			break;
		case Informix:
			sql = "SELECT distinct  KeyOfEn FROM Sys_MapAttr WHERE FK_MapData IN ( SELECT 'ND' "
					+ SystemConfig.getAppCenterDBAddStringStr()
					+ " cast(NodeID as varchar(20)) FROM WF_Node WHERE FK_Flow='" + this.getNo() + "')";
			break;
		case MySQL:
			sql = "SELECT DISTINCT KeyOfEn FROM Sys_MapAttr  WHERE FK_MapData IN (SELECT X.No FROM ( SELECT CONCAT('ND',NodeID) AS No FROM WF_Node WHERE FK_Flow='"
					+ this.getNo() + "') AS X )";
			break;
		default:
			sql = "SELECT distinct  KeyOfEn FROM Sys_MapAttr WHERE FK_MapData IN ( SELECT 'ND' "
					+ SystemConfig.getAppCenterDBAddStringStr()
					+ " cast(NodeID as varchar(20)) FROM WF_Node WHERE FK_Flow='" + this.getNo() + "')";
			break;
		}

		if (SystemConfig.getAppCenterDBType() == DBType.MySQL) {
			sql = "SELECT A.* FROM (" + sql + ") AS A ";
			String sql3 = "DELETE FROM Sys_MapAttr WHERE KeyOfEn NOT IN (" + sql + ") AND FK_MapData='" + fk_mapData
					+ "' ";
			DBAccess.RunSQL(sql3); // 删除不存在的字段.
		} else {
			String sql2 = "DELETE FROM Sys_MapAttr WHERE KeyOfEn NOT IN (" + sql + ") AND FK_MapData='" + fk_mapData
					+ "' ";
			DBAccess.RunSQL(sql2); // 删除不存在的字段.
		}

		// 补充上没有字段。
		switch (SystemConfig.getAppCenterDBType()) {
		case Oracle:
			sql = "SELECT MyPK, KeyOfEn FROM Sys_MapAttr WHERE FK_MapData IN ( SELECT 'ND' "
					+ SystemConfig.getAppCenterDBAddStringStr()
					+ " cast(NodeID as varchar(20)) FROM WF_Node WHERE FK_Flow='" + this.getNo() + "')";
			break;
		case MySQL:
			sql = "SELECT MyPK, KeyOfEn FROM Sys_MapAttr WHERE FK_MapData IN (SELECT X.No FROM ( SELECT CONCAT('ND',NodeID) AS No FROM WF_Node WHERE FK_Flow='"
					+ this.getNo() + "') AS X )";
			break;
		default:
			sql = "SELECT MyPK, KeyOfEn FROM Sys_MapAttr WHERE FK_MapData IN ( SELECT 'ND' "
					+ SystemConfig.getAppCenterDBAddStringStr()
					+ " cast(NodeID as varchar(20)) FROM WF_Node WHERE FK_Flow='" + this.getNo() + "')";
			break;
		}

		DataTable dt = DBAccess.RunSQLReturnTable(sql);
		sql = "SELECT KeyOfEn FROM Sys_MapAttr WHERE FK_MapData='ND" + flowId + "Rpt'";
		DataTable dtExits = DBAccess.RunSQLReturnTable(sql);
		String pks = "@";
		for (DataRow dr : dtExits.Rows) {
			pks += dr.getValue(0) + "@";
		}

		for (DataRow dr : dt.Rows) {
			String mypk = dr.getValue("MyPK").toString();
			if (pks.contains("@" + dr.getValue("KeyOfEn").toString() + "@")) {
				continue;
			}

			pks += dr.getValue("KeyOfEn").toString() + "@";

			BP.Sys.Frm.MapAttr ma = new BP.Sys.Frm.MapAttr(mypk);
			ma.setMyPK("ND" + flowId + "Rpt_" + ma.getKeyOfEn());
			ma.setFK_MapData("ND" + flowId + "Rpt");
			ma.setUIIsEnable(false);

			if (ma.getDefValReal().contains("@")) {
				// 如果是一个有变量的参数.
				ma.setDefVal("");
			}

			try {
				ma.Insert();
			} catch (java.lang.Exception e) {
			}
		}

		MapAttrs attrs = new MapAttrs(fk_mapData);

		// 创建mapData.
		BP.Sys.Frm.MapData md = new BP.Sys.Frm.MapData();
		md.setNo("ND" + flowId + "Rpt");
		if (md.RetrieveFromDBSources() == 0) {
			md.setName(this.getName());
			md.setPTable(this.getPTable());
			md.Insert();
		} else {
			md.setName(this.getName());
			md.setPTable(this.getPTable());
			md.Update();
		}
		// /#endregion 插入字段。

		// /#region 补充上流程字段。
		int groupID = 0;
		for (MapAttr attr : MapAttrs.convertMapAttrs(attrs)) {
			String sss = attr.getKeyOfEn();
			if (sss.equals(StartWorkAttr.FK_Dept)) {
				attr.setUIBindKey("BP.Port.Depts");
				attr.setUIContralType(UIContralType.DDL);
				attr.setLGType(FieldTypeS.FK);
				attr.setUIVisible(true);
				attr.setGroupID(groupID); // gfs[0].GetValIntByKey("OID");
				attr.setUIIsEnable(false);
				attr.setDefVal("");
				attr.setMaxLen(100);
				attr.Update();
			} else if (sss.equals("FK_NY")) {
				attr.setUIBindKey("BP.Pub.NYs");
				attr.setUIContralType(UIContralType.DDL);
				attr.setLGType(FieldTypeS.FK);
				attr.setUIVisible(true);
				attr.setUIIsEnable(false);
				attr.setGroupID(groupID);
				attr.Update();
			} else if (sss.equals("FK_Emp")) {

			}

		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.WFState)) {
			// 流程状态
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.WFState);
			attr.setName("流程状态");
			attr.setMyDataType(DataType.AppInt);
			attr.setUIBindKey(GERptAttr.WFState);
			attr.setUIContralType(UIContralType.DDL);
			attr.setLGType(FieldTypeS.Enum);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setMinLen(0);
			attr.setMaxLen(1000);
			attr.setIDX(-1);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.WFSta)) {
			// 流程状态Ext
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.WFSta);
			attr.setName("状态");
			attr.setMyDataType(DataType.AppInt);
			attr.setUIBindKey(GERptAttr.WFSta);
			attr.setUIContralType(UIContralType.DDL);
			attr.setLGType(FieldTypeS.Enum);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setMinLen(0);
			attr.setMaxLen(1000);
			attr.setIDX(-1);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowEmps)) {
			// 参与人
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowEmps); // "FlowEmps";
			attr.setName("参与人");
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(true);
			attr.setMinLen(0);
			attr.setMaxLen(1000);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowStarter)) {
			// 发起人
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowStarter);
			attr.setName("发起人");
			attr.setMyDataType(DataType.AppString);

			attr.setUIBindKey("BP.Port.Emps");
			attr.setUIContralType(UIContralType.DDL);
			attr.setLGType(FieldTypeS.FK);

			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setMinLen(0);
			attr.setMaxLen(20);
			attr.setIDX(-1);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowStartRDT)) {
			// MyNum
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowStartRDT); // "FlowStartRDT";
			attr.setName("发起时间");
			attr.setMyDataType(DataType.AppDateTime);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowEnder)) {
			// 发起人
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowEnder);
			attr.setName("结束人");
			attr.setMyDataType(DataType.AppString);
			attr.setUIBindKey("BP.Port.Emps");
			attr.setUIContralType(UIContralType.DDL);
			attr.setLGType(FieldTypeS.FK);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setMinLen(0);
			attr.setMaxLen(20);
			attr.setIDX(-1);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowEnderRDT)) {
			// MyNum
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowEnderRDT); // "FlowStartRDT";
			attr.setName("结束时间");
			attr.setMyDataType(DataType.AppDateTime);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowEndNode)) {
			// 结束节点
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowEndNode);
			attr.setName("结束节点");
			attr.setMyDataType(DataType.AppInt);
			attr.setDefVal("0");
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setHisEditType(EditType.UnDel);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.FlowDaySpan)) {
			// FlowDaySpan
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.FlowDaySpan); // "FlowStartRDT";
			attr.setName("跨度(天)");
			attr.setMyDataType(DataType.AppMoney);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(true);
			attr.setUIIsLine(false);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.PFlowNo)) {
			// 父流程 流程编号
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.PFlowNo);
			attr.setName("父流程流程编号"); // 父流程流程编号
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(true);
			attr.setMinLen(0);
			attr.setMaxLen(3);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.PNodeID)) {
			// 父流程WorkID
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.PNodeID);
			attr.setName("父流程启动的节点");
			attr.setMyDataType(DataType.AppInt);
			attr.setDefVal("0");
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setHisEditType(EditType.UnDel);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.PWorkID)) {
			// 父流程WorkID
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.PWorkID);
			attr.setName("父流程WorkID");
			attr.setMyDataType(DataType.AppInt);
			attr.setDefVal("0");
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setHisEditType(EditType.UnDel);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.PEmp)) {
			// 调起子流程的人员
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.PEmp);
			attr.setName("调起子流程的人员");
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(true);
			attr.setMinLen(0);
			attr.setMaxLen(32);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.CWorkID)) {
			// 延续流程WorkID
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.CWorkID);
			attr.setName("延续流程WorkID");
			attr.setMyDataType(DataType.AppInt);
			attr.setDefVal("0");
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setHisEditType(EditType.UnDel);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.CFlowNo)) {
			// 延续流程编号
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.CFlowNo);
			attr.setName("延续流程编号");
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(true);
			attr.setMinLen(0);
			attr.setMaxLen(3);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.BillNo)) {
			// 父流程 流程编号
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.BillNo);
			attr.setName("单据编号"); // 单据编号
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setMinLen(0);
			attr.setMaxLen(100);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_MyNum")) {
			// MyNum
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn("MyNum");
			attr.setName("条");
			attr.setMyDataType(DataType.AppInt);
			attr.setDefVal("1");
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(true);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setHisEditType(EditType.UnDel);
			attr.setIDX(-101);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.AtPara)) {
			// 父流程 流程编号
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.AtPara);
			attr.setName("参数"); // 单据编号
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setMinLen(0);
			attr.setMaxLen(4000);
			attr.setIDX(-100);
			attr.Insert();
		}

		if (!attrs.Contains(md.getNo() + "_" + GERptAttr.GUID)) {
			// 父流程 流程编号
			MapAttr attr = new BP.Sys.Frm.MapAttr();
			attr.setFK_MapData(md.getNo());
			attr.setHisEditType(EditType.UnDel);
			attr.setKeyOfEn(GERptAttr.GUID);
			attr.setName("GUID"); // 单据编号
			attr.setMyDataType(DataType.AppString);
			attr.setUIContralType(UIContralType.TB);
			attr.setLGType(FieldTypeS.Normal);
			attr.setUIVisible(false);
			attr.setUIIsEnable(false);
			attr.setUIIsLine(false);
			attr.setMinLen(0);
			attr.setMaxLen(32);
			attr.setIDX(-100);
			attr.Insert();
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 补充上流程字段。

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 为流程字段设置分组。
		try {
			String flowInfo = "流程信息";
			GroupField flowGF = new GroupField();
			int num = flowGF.Retrieve(GroupFieldAttr.EnName, fk_mapData, GroupFieldAttr.Lab, "流程信息");
			if (num == 0) {
				flowGF = new GroupField();
				flowGF.setLab(flowInfo);
				flowGF.setEnName(fk_mapData);
				flowGF.setIdx(-1);
				flowGF.Insert();
			}
			sql = "UPDATE Sys_MapAttr SET GroupID=" + flowGF.getOID() + " WHERE  FK_MapData='" + fk_mapData
					+ "'  AND KeyOfEn IN('" + GERptAttr.PFlowNo + "','" + GERptAttr.PWorkID + "','" + GERptAttr.MyNum
					+ "','" + GERptAttr.FK_Dept + "','" + GERptAttr.FK_NY + "','" + GERptAttr.FlowDaySpan + "','"
					+ GERptAttr.FlowEmps + "','" + GERptAttr.FlowEnder + "','" + GERptAttr.FlowEnderRDT + "','"
					+ GERptAttr.FlowEndNode + "','" + GERptAttr.FlowStarter + "','" + GERptAttr.FlowStartRDT + "','"
					+ GERptAttr.WFState + "')";
			DBAccess.RunSQL(sql);
		} catch (RuntimeException ex) {
			Log.DefaultLogWriteLineError(ex.getMessage());
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 为流程字段设置分组

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 尾后处理.
		GERpt sw = this.getHisGERpt();
		try {
			logger.debug("创建rpt表");
			sw.CheckPhysicsTable();
			logger.debug("创建成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 让报表重新生成.

		DBAccess.RunSQL("DELETE FROM Sys_GroupField WHERE EnName='" + fk_mapData
				+ "' AND OID NOT IN (SELECT GroupID FROM Sys_MapAttr WHERE FK_MapData = '" + fk_mapData + "')");
		DBAccess.RunSQL("UPDATE Sys_MapAttr SET Name='活动时间' WHERE FK_MapData='ND" + flowId + "Rpt' AND KeyOfEn='CDT'");
		DBAccess.RunSQL("UPDATE Sys_MapAttr SET Name='参与者' WHERE FK_MapData='ND" + flowId + "Rpt' AND KeyOfEn='Emps'");
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 尾后处理.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 处理报表.
		String mapRpt = "ND" + Integer.parseInt(this.getNo()) + "MyRpt";
		MapData mapData = new MapData();
		mapData.setNo(mapRpt);
		if (mapData.RetrieveFromDBSources() == 0) {
			mapData.setNo(mapRpt);
			mapData.setPTable(this.getPTable());
			mapData.setName(this.getName() + "报表");
			mapData.setNote("默认.");
			mapData.Insert();

			MapRpt rpt = new MapRpt();
			rpt.setNo(mapRpt);
			rpt.RetrieveFromDBSources();

			rpt.setFK_Flow(this.getNo());
			rpt.setParentMapData("ND" + Integer.parseInt(this.getNo()) + "Rpt");
			rpt.ResetIt();
			rpt.Update();
		}

		if (!this.getPTable().equals(mapData.getPTable())) {
			mapData.setPTable(this.getPTable());
			mapData.Update();
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 处理报表.
	}

	// /#endregion 其他公用方法1

	// /#region 执行流程事件.
	/**
	 * 执行运动事件
	 * 
	 * @param doType
	 *            事件类型
	 * @param currNode
	 *            当前节点
	 * @param en
	 *            实体
	 * @param atPara
	 *            参数
	 * @param objs
	 *            发送对象，可选
	 * @return 执行结果
	 */
	public final String DoFlowEventEntity(String doType, Node currNode, Entity en, String atPara, SendReturnObjs objs) {
		if (currNode == null) {
			return null;
		}

		String str = null;
		if (this.getFEventEntity() != null) {
			this.getFEventEntity().SendReturnObjs = objs;
			str = this.getFEventEntity().DoIt(doType, currNode, en, atPara);
		}

		FrmEvents fes = currNode.getMapData().getFrmEvents();
		if (StringHelper.isNullOrEmpty(str)) {
			str = fes.DoEventNode(doType, en, atPara);
		}

		// 一下是处理发送消息。
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (doType)
		// ORIGINAL LINE: case EventListOfNode.SendSuccess:
		if (doType.equals(EventListOfNode.SendSuccess) || doType.equals(EventListOfNode.ShitAfter)
				|| doType.equals(EventListOfNode.ReturnAfter) || doType.equals(EventListOfNode.UndoneAfter)
				|| doType.equals(EventListOfNode.AskerReAfter)) {
		} else {
			return str;
		}

		// 获得消息实体.
		Object tempVar = fes.GetEntityByKey(FrmEventAttr.FK_Event, doType);
		FrmEvent nev = (FrmEvent) ((tempVar instanceof FrmEvent) ? tempVar : null);
		if (nev == null) {
			nev = new FrmEvent();
			nev.setFK_Event(doType);
		}

		// 定义是否发送短信。
		boolean isSendEmail = false;
		boolean isSendSMS = false;

		// 处理参数.
		Row r = en.getRow();
		try {
			// 系统参数.
			// r.Add("FK_MapData", en.getClassID());
			r.SetValByKey("FK_MapData", en.getClassID());
		} catch (java.lang.Exception e) {
			// r["FK_MapData"] = en.ClassID;
			r.SetValByKey("FK_MapData", en.getClassID());
		}

		if (atPara != null) {
			AtPara ap = new AtPara(atPara);
			for (String s : ap.getHisHT().keySet()) {
				try {
					// r.Add(s, ap.GetValStrByKey(s));
					r.SetValByKey((String) s, ap.GetValStrByKey((String) s));
				} catch (java.lang.Exception e2) {
					// r[s] = ap.GetValStrByKey(s);
					r.SetValByKey((String) s, ap.GetValStrByKey((String) s));
				}
			}
		}

		// 分模式处理数据.
		switch (nev.getMsgCtrl()) {
		case BySet: // 按照设置计算.
			isSendEmail = nev.getMsgMailEnable();
			isSendSMS = nev.getSMSEnable();
			break;
		case BySDK: // 按照设置计算.
		case ByFrmIsSendMsg: // 按照设置计算.
			if (r.containsKey("IsSendEmail") == true) {
				isSendEmail = r.GetBoolenByKey("IsSendEmail");
			}
			if (r.containsKey("IsSendSMS") == true) {
				isSendSMS = r.GetBoolenByKey("IsSendSMS");
			}
			break;
		default:
			break;
		}

		// 判断是否发送消息？
		if (isSendSMS == false && isSendEmail == false) {
			return str;
		}

		long workid = Long.parseLong(en.getPKVal().toString());

		String title = "";
		try {
			title = en.GetValStrByKey("Title");
		} catch (java.lang.Exception e3) {
		}

		String hostUrl = BP.WF.Glo.getHostURL();
		String sid = "{EmpStr}_" + workid + "_" + currNode.getNodeID() + "_" + DataType.getCurrentDataTime();
		String openWorkURl = hostUrl + "WF/Do.jsp?DoType=OF&SID=" + sid;
		openWorkURl = openWorkURl.replace("//", "/");
		openWorkURl = openWorkURl.replace("//", "/");

		// 定义消息变量.
		String mailTitleTmp = "";
		String mailDocTmp = "";
		if (isSendEmail) {
			// 标题.
			mailTitleTmp = nev.getMailTitle();
			mailTitleTmp = mailTitleTmp.replace("{Title}", title);
			mailTitleTmp = mailTitleTmp.replace("@WebUser.No", WebUser.getNo());
			mailTitleTmp = mailTitleTmp.replace("@WebUser.Name", WebUser.getName());

			// 内容.
			mailDocTmp = nev.getMailDoc();
			mailDocTmp = mailDocTmp.replace("{Url}", openWorkURl);
			mailDocTmp = mailDocTmp.replace("{Title}", title);

			mailDocTmp = mailDocTmp.replace("@WebUser.No", WebUser.getNo());
			mailDocTmp = mailDocTmp.replace("@WebUser.Name", WebUser.getName());

			// 如果仍然有没有替换下来的变量.
			if (mailDocTmp.contains("@")) {
				mailDocTmp = BP.WF.Glo.DealExp(mailDocTmp, en, null);
			}
		}

		String smsDocTmp = "";
		if (isSendSMS) {
			// Object tempVar2 = nev.SMSDoc.clone();
			Object tempVar2 = new String(nev.getSMSDoc());
			smsDocTmp = (String) ((tempVar2 instanceof String) ? tempVar2 : null);

			smsDocTmp = smsDocTmp.replace("{Title}", title);
			smsDocTmp = smsDocTmp.replace("{Url}", openWorkURl);
			smsDocTmp = smsDocTmp.replace("@WebUser.No", WebUser.getNo());
			smsDocTmp = smsDocTmp.replace("@WebUser.Name", WebUser.getName());

			// 如果仍然有没有替换下来的变量.
			if (smsDocTmp.contains("@") == true) {
				smsDocTmp = BP.WF.Glo.DealExp(smsDocTmp, en, null);
			}
		}

		// 获得要发送人的ids.
		String toEmpIDs = "";
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (doType)
		// ORIGINAL LINE: case EventListOfNode.SendSuccess:
		if (doType.equals(EventListOfNode.SendSuccess)) {
			toEmpIDs = objs.getVarAcceptersID();
		}
		// ORIGINAL LINE: case EventListOfNode.ReturnAfter:
		else if (doType.equals(EventListOfNode.ReturnAfter)) // 如果是退回,就找到退回的当事人.
		{
			toEmpIDs = objs.getVarAcceptersID();
		} else {
		}

		// 执行发送消息.
		String[] emps = toEmpIDs.split("[,]", -1);
		for (String emp : emps) {
			if (StringHelper.isNullOrEmpty(emp)) {
				continue;
			}

			// Object tempVar3 = mailDocTmp.clone();
			Object tempVar3 = new String(mailDocTmp);
			String mailDocReal = (String) ((tempVar3 instanceof String) ? tempVar3 : null);
			mailDocReal = mailDocReal.replace("{EmpStr}", emp);

			// 发送消息.
			BP.WF.Dev2Interface.Port_SendMsg(emp, mailTitleTmp, mailDocReal, smsDocTmp, en.getPKVal().toString(),
					doType, this.getNo(), currNode.getNodeID(), Long.parseLong(en.getPKVal().toString()), 0,
					isSendEmail, isSendSMS);
		}
		return str;
	}

	/**
	 * 执行运动事件
	 * 
	 * @param doType
	 *            事件类型
	 * @param en
	 *            实体参数
	 */
	/**
	 * @param doType
	 * @param currNode
	 * @param en
	 * @param atPara
	 * @return
	 */
	public final String DoFlowEventEntity(String doType, Node currNode, Entity en, String atPara) {
		// return BP.DA.DataType.PraseGB2312_To_utf8(this.DoFlowEventEntity(
		// doType, currNode, en, atPara, null));
		return DoFlowEventEntity(doType, currNode, en, atPara, null);
	}

	private FlowEventBase _FDEventEntity = null;

	/**
	 * 节点实体类，没有就返回为空.
	 */
	private FlowEventBase getFEventEntity() {
		if (_FDEventEntity == null && !this.getFlowMark().equals("") && !this.getFlowEventEntity().equals("")) {
			_FDEventEntity = BP.WF.Glo.GetFlowEventEntityByEnName(this.getFlowEventEntity());
		}
		return _FDEventEntity;
	}

	// /#endregion 执行流程事件.

	// /#region 基本属性
	/**
	 * 是否是MD5加密流程
	 */
	public final boolean getIsMD5() {
		return this.GetValBooleanByKey(FlowAttr.IsMD5);
	}

	public final void setIsMD5(boolean value) {
		this.SetValByKey(FlowAttr.IsMD5, value);
	}

	/**
	 * 是否有单据
	 */
	public final int getNumOfBill() {
		return this.GetValIntByKey(FlowAttr.NumOfBill);
	}

	public final void setNumOfBill(int value) {
		this.SetValByKey(FlowAttr.NumOfBill, value);
	}

	/**
	 * 标题生成规则
	 */
	public final String getTitleRole() {
		return this.GetValStringByKey(FlowAttr.TitleRole);
	}

	public final void setTitleRole(String value) {
		this.SetValByKey(FlowAttr.TitleRole, value);
	}

	/**
	 * 明细表
	 */
	public final int getNumOfDtl() {
		return this.GetValIntByKey(FlowAttr.NumOfDtl);
	}

	public final void setNumOfDtl(int value) {
		this.SetValByKey(FlowAttr.NumOfDtl, value);
	}

	public final java.math.BigDecimal getAvgDay() {
		// return this.GetValIntByKey(FlowAttr.AvgDay);
		return new java.math.BigDecimal(this.GetValIntByKey(FlowAttr.AvgDay));
	}

	public final void setAvgDay(java.math.BigDecimal value) {
		this.SetValByKey(FlowAttr.AvgDay, value);
	}

	public final int getStartNodeID() {
		return Integer.parseInt(this.getNo() + "01");
		// return this.GetValIntByKey(FlowAttr.StartNodeID);
	}

	/**
	 * add 2013-01-01. 业务主表(默认为NDxxRpt)
	 */
	public final String getPTable() {
		String s = this.GetValStringByKey(FlowAttr.PTable);
		if (StringHelper.isNullOrEmpty(s)) {
			s = "ND" + Integer.parseInt(this.getNo()) + "Rpt";
		}
		return s;
	}

	public final void setPTable(String value) {
		this.SetValByKey(FlowAttr.PTable, value);
	}

	/**
	 * 历史记录显示字段.
	 */
	public final String getHistoryFields() {
		String strs = this.GetValStringByKey(FlowAttr.HistoryFields);
		if (StringHelper.isNullOrEmpty(strs)) {
			strs = "WFState,Title,FlowStartRDT,FlowEndNode";
		}

		return strs;
	}

	/**
	 * 是否启用？
	 */
	public final boolean getIsGuestFlow() {
		return this.GetValBooleanByKey(FlowAttr.IsGuestFlow);
	}

	public final void setIsGuestFlow(boolean value) {
		this.SetValByKey(FlowAttr.IsGuestFlow, value);
	}

	/**
	 * 是否可以独立启动
	 */
	public final boolean getIsCanStart() {
		return this.GetValBooleanByKey(FlowAttr.IsCanStart);
	}

	public final void setIsCanStart(boolean value) {
		this.SetValByKey(FlowAttr.IsCanStart, value);
	}

	/**
	 * 是否可以批量发起
	 */
	public final boolean getIsBatchStart() {
		return this.GetValBooleanByKey(FlowAttr.IsBatchStart);
	}

	public final void setIsBatchStart(boolean value) {
		this.SetValByKey(FlowAttr.IsBatchStart, value);
	}

	/**
	 * 是否自动计算未来的处理人
	 */
	public final boolean getIsFullSA() {
		return this.GetValBooleanByKey(FlowAttr.IsFullSA);
	}

	public final void setIsFullSA(boolean value) {
		this.SetValByKey(FlowAttr.IsFullSA, value);
	}

	/**
	 * 批量发起字段
	 */
	public final String getBatchStartFields() {
		return this.GetValStringByKey(FlowAttr.BatchStartFields);
	}

	public final void setBatchStartFields(String value) {
		this.SetValByKey(FlowAttr.BatchStartFields, value);
	}

	/**
	 * 单据格式
	 */
	public final String getBillNoFormat() {
		return this.GetValStringByKey(FlowAttr.BillNoFormat);
	}

	public final void setBillNoFormat(String value) {
		this.SetValByKey(FlowAttr.BillNoFormat, value);
	}

	/**
	 * 流程类别
	 */
	public final String getFK_FlowSort() {
		return this.GetValStringByKey(FlowAttr.FK_FlowSort);
	}

	public final void setFK_FlowSort(String value) {
		this.SetValByKey(FlowAttr.FK_FlowSort, value);
	}

	/**
	 * 参数
	 */
	public final String getParas() {
		return this.GetValStringByKey(FlowAttr.Paras);
	}

	public final void setParas(String value) {
		this.SetValByKey(FlowAttr.Paras, value);
	}

	/**
	 * 流程类别名称
	 */
	public final String getFK_FlowSortText() {
		FlowSort fs = new FlowSort(this.getFK_FlowSort());
		return fs.getName();
		// return this.GetValRefTextByKey(FlowAttr.FK_FlowSort);
	}

	/**
	 * 设计者编号
	 */
	public final String getDesignerNo1() {
		return this.GetValStringByKey(FlowAttr.DesignerNo);
	}

	public final void setDesignerNo1(String value) {
		this.SetValByKey(FlowAttr.DesignerNo, value);
	}

	/**
	 * 设计者名称
	 */
	public final String getDesignerName1() {
		return this.GetValStringByKey(FlowAttr.DesignerName);
	}

	public final void setDesignerName1(String value) {
		this.SetValByKey(FlowAttr.DesignerName, value);
	}

	/**
	 * 版本号
	 */
	public final String getVer() {
		return this.GetValStringByKey(FlowAttr.Ver);
	}

	public final void setVer(String value) {
		this.SetValByKey(FlowAttr.Ver, value);
	}

	// /#endregion

	// /#region 计算属性
	/**
	 * 流程类型(大的类型)
	 */
	public final int getFlowType_del() {
		return this.GetValIntByKey(FlowAttr.FlowType);
	}

	/**
	 * (当前节点为子流程时)是否检查所有子流程完成后父流程自动发送
	 */
	public final boolean getIsAutoSendSubFlowOver() {
		return this.GetValBooleanByKey(FlowAttr.IsAutoSendSubFlowOver);
	}

	public final String getNote() {
		String s = this.GetValStringByKey("Note");
		if (s.length() == 0) {
			return "无";
		}
		return s;
	}

	public final String getNoteHtml() {
		if (this.getNote().equals("无") || this.getNote().equals("")) {
			return "流程设计人员没有编写此流程的帮助信息，请打开设计器-》打开此流程-》设计画布上点击右键-》流程属性-》填写流程帮助信息。";
		} else {
			return this.getNote();
		}
	}

	/**
	 * 是否多线程自动流程
	 */
	public final boolean getIsMutiLineWorkFlow_del() {
		return false;
		//
		// if (this.FlowType==2 || this.FlowType==1 )
		// return true;
		// else
		// return false;
		//
	}

	// /#endregion

	// /#region 扩展属性
	/**
	 * 应用类型
	 */
	public final FlowAppType getHisFlowAppType() {
		// return (FlowAppType) this.GetValIntByKey(FlowAttr.FlowAppType);
		return FlowAppType.forValue(this.GetValIntByKey(FlowAttr.FlowAppType));
	}

	public final void setHisFlowAppType(FlowAppType value) {
		this.SetValByKey(FlowAttr.FlowAppType, value.getValue());
	}

	/**
	 * 数据存储模式
	 */
	public final DataStoreModel getHisDataStoreModel() {
		return DataStoreModel.forValue(this.GetValIntByKey(FlowAttr.DataStoreModel));
	}

	public final void setHisDataStoreModel(DataStoreModel value) {
		this.SetValByKey(FlowAttr.DataStoreModel, value.getValue());
	}

	/**
	 * 节点
	 */
	public Nodes _HisNodes = null;

	/**
	 * 他的节点集合.
	 */
	public final Nodes getHisNodes() {
		if (this._HisNodes == null) {
			_HisNodes = new Nodes(this.getNo());
		}
		return _HisNodes;
	}

	public final void setHisNodes(Nodes value) {
		_HisNodes = value;
	}

	/**
	 * 他的 Start 节点
	 */
	public final Node getHisStartNode() {

		// for (Node nd : this.getHisNodes()) {
		for (Node nd : Nodes.convertNodes(this.getHisNodes())) {
			if (nd.getIsStartNode()) {
				return nd;
			}
		}
		throw new RuntimeException("@没有找到他的开始节点,工作流程[" + this.getName() + "]定义错误.");
	}

	/**
	 * 他的事务类别
	 */
	public final FlowSort getHisFlowSort() {
		return new FlowSort(this.getFK_FlowSort());
	}

	/**
	 * flow data 数据
	 */
	public final BP.WF.Data.GERpt getHisGERpt() {
		try {
			BP.WF.Data.GERpt wk = new BP.WF.Data.GERpt("ND" + Integer.parseInt(this.getNo()) + "Rpt");
			return wk;
		} catch (java.lang.Exception e) {
			this.DoCheck();
			BP.WF.Data.GERpt wk1 = new BP.WF.Data.GERpt("ND" + Integer.parseInt(this.getNo()) + "Rpt");
			return wk1;
		}
	}

	// /#endregion

	// /#region 构造方法

	/**
	 * 流程
	 */
	public Flow() {
	}

	/**
	 * 流程
	 * 
	 * @param _No
	 *            编号
	 */
	public Flow(String _No) {
		this.setNo(_No);
		if (SystemConfig.getIsDebug()) {
			int i = this.RetrieveFromDBSources();
			if (i == 0) {
				throw new RuntimeException("流程编号不存在");
			}
		} else {
			this.Retrieve();
		}
	}

	@Override
	protected boolean beforeUpdateInsertAction() {
		try {
			if (StringHelper.isNullOrEmpty(this.getFlowMark()) == false) {
				this.setFlowEventEntity(BP.WF.Glo.GetFlowEventEntityByFlowMark(this.getFlowMark()).toString());
			} else {
				this.setFlowEventEntity("");
			}
		} catch (java.lang.Exception e) {
		}

		DBAccess.RunSQL("UPDATE WF_Node SET FlowName='" + this.getName() + "' WHERE FK_Flow='" + this.getNo() + "'");
		DBAccess.RunSQL("UPDATE Sys_MapData SET  Name='" + this.getName() + "' WHERE No='" + this.getPTable() + "'");
		return super.beforeUpdateInsertAction();
	}

	/**
	 * 重写基类方法
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map("WF_Flow");

		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("流程");
		map.setCodeStruct("3");
		map.AddTBStringPK(FlowAttr.No, null, "编号", true, true, 1, 10, 3);
		map.AddTBString(FlowAttr.Name, null, "名称", true, false, 0, 500, 10);
		map.AddDDLEntities(FlowAttr.FK_FlowSort, "01", "流程类别", new FlowSorts(), false);
		// map.AddTBString(FlowAttr.FK_FlowSort, null, "流程类别", true, false, 0,
		// 10, 10);
		map.AddTBInt(FlowAttr.FlowRunWay, 0, "运行方式", false, false);

		// map.AddDDLEntities(FlowAttr.FK_FlowSort, "01", "流程类别", new
		// FlowSorts(), false);
		// map.AddDDLSysEnum(FlowAttr.FlowRunWay, (int)FlowRunWay.HandWork,
		// "运行方式", false,
		// false, FlowAttr.FlowRunWay,
		// "@0=手工启动@1=指定人员按时启动@2=数据集按时启动@3=触发式启动");

		map.AddTBString(FlowAttr.RunObj, null, "运行内容", true, false, 0, 3000, 10);
		map.AddTBString(FlowAttr.Note, null, "备注", true, false, 0, 100, 10);
		map.AddTBString(FlowAttr.RunSQL, null, "流程结束执行后执行的SQL", true, false, 0, 2000, 10);

		map.AddTBInt(FlowAttr.NumOfBill, 0, "是否有单据", false, false);
		map.AddTBInt(FlowAttr.NumOfDtl, 0, "NumOfDtl", false, false);
		map.AddTBInt(FlowAttr.FlowAppType, 0, "流程类型", false, false);
		map.AddTBInt(FlowAttr.ChartType, 1, "节点图形类型", false, false);

		// map.AddBoolean(FlowAttr.IsOK, true, "是否启用", true, true);
		map.AddBoolean(FlowAttr.IsCanStart, true, "可以独立启动否？", true, true, true);
		map.AddTBDecimal(FlowAttr.AvgDay, 0, "平均运行用天", false, false);

		map.AddTBInt(FlowAttr.IsFullSA, 0, "是否自动计算未来的处理人？(启用后,ccflow就会为已知道的节点填充处理人到WF_SelectAccper)", false, false);
		map.AddTBInt(FlowAttr.IsMD5, 0, "IsMD5", false, false);
		map.AddTBInt(FlowAttr.Idx, 0, "显示顺序号(在发起列表中)", true, false);
		map.AddTBInt(FlowAttr.TimelineRole, 0, "时效性规则", true, false);
		map.AddTBString(FlowAttr.Paras, null, "参数", false, false, 0, 400, 10);

		// add 2013-01-01.
		map.AddTBString(FlowAttr.PTable, null, "流程数据存储主表", true, false, 0, 30, 10);

		// 草稿规则 "@0=无(不设草稿)@1=保存到待办@2=保存到草稿箱"
		map.AddTBInt(FlowAttr.Draft, 0, "草稿规则", true, false);

		// add 2013-01-01.
		map.AddTBInt(FlowAttr.DataStoreModel, 0, "数据存储模式", true, false);

		// add 2013-02-05.
		map.AddTBString(FlowAttr.TitleRole, null, "标题生成规则", true, false, 0, 150, 10, true);

		// add 2013-02-14
		map.AddTBString(FlowAttr.FlowMark, null, "流程标记", true, false, 0, 150, 10);
		map.AddTBString(FlowAttr.FlowEventEntity, null, "FlowEventEntity", true, false, 0, 100, 10, true);
		map.AddTBString(FlowAttr.HistoryFields, null, "历史查看字段", true, false, 0, 500, 10, true);
		map.AddTBInt(FlowAttr.IsGuestFlow, 0, "是否是客户参与流程？", true, false);
		map.AddTBString(FlowAttr.BillNoFormat, null, "单据编号格式", true, false, 0, 50, 10, true);
		map.AddTBString(FlowAttr.FlowNoteExp, null, "备注表达式", true, false, 0, 500, 10, true);

		// 部门权限控制类型,此属性在报表中控制的.
		map.AddTBInt(FlowAttr.DRCtrlType, 0, "部门查询权限控制方式", true, false);

		// /#region 流程启动限制
		map.AddTBInt(FlowAttr.StartLimitRole, 0, "启动限制规则", true, false);
		map.AddTBString(FlowAttr.StartLimitPara, null, "规则内容", true, false, 0, 500, 10, true);
		map.AddTBString(FlowAttr.StartLimitAlert, null, "限制提示", true, false, 0, 500, 10, false);
		map.AddTBInt(FlowAttr.StartLimitWhen, 0, "提示时间", true, false);

		// /#endregion 流程启动限制

		// /#region 导航方式。
		map.AddTBInt(FlowAttr.StartGuideWay, 0, "前置导航方式", false, false);

		map.AddTBString(FlowAttr.StartGuidePara1, null, "参数1", true, false, 0, 500, 10, true);
		map.AddTBString(FlowAttr.StartGuidePara2, null, "参数2", true, false, 0, 500, 10, true);
		map.AddTBString(FlowAttr.StartGuidePara3, null, "参数3", true, false, 0, 500, 10, true);
		map.AddTBInt(FlowAttr.IsResetData, 0, "是否启用数据重置按钮？", true, false);
		// map.AddTBInt(FlowAttr.IsImpHistory, 0, "是否启用导入历史数据按钮？", true, false);
		map.AddTBInt(FlowAttr.IsLoadPriData, 0, "是否导入上一个数据？", true, false);

		// /#endregion 导航方式。

		map.AddTBInt(FlowAttr.CFlowWay, 0, "延续流程方式", true, false);
		map.AddTBString(FlowAttr.CFlowPara, null, "延续流程参数", true, false, 0, 100, 10, true);

		// 批量发起 add 2013-12-27.
		map.AddTBInt(FlowAttr.IsBatchStart, 0, "是否可以批量发起", true, false);
		map.AddTBString(FlowAttr.BatchStartFields, null, "批量发起字段(用逗号分开)", true, false, 0, 500, 10, true);

		// map.AddTBInt(FlowAttr.IsEnableTaskPool, 0, "是否启用共享任务池", true, false);
		// map.AddDDLSysEnum(FlowAttr.TimelineRole, (int)TimelineRole.ByNodeSet,
		// "时效性规则",
		// true, true, FlowAttr.TimelineRole,
		// "@0=按节点(由节点属性来定义)@1=按发起人(开始节点SysSDTOfFlow字段计算)");

		map.AddTBInt(FlowAttr.IsAutoSendSubFlowOver, 0, "(当前节点为子流程时)是否检查所有子流程完成后父流程自动发送", true, true);
		map.AddTBString(FlowAttr.Ver, null, "版本号", true, true, 0, 20, 10);

		// /#region 数据同步方案
		// 数据同步方式.
		map.AddTBInt(FlowAttr.DTSWay, FlowDTSWay.None.getValue(), "同步方式", true, true);
		map.AddTBString(FlowAttr.DTSBTable, null, "业务表名", true, false, 0, 200, 100, false);
		map.AddTBString(FlowAttr.DTSBTablePK, null, "业务表主键", false, false, 0, 32, 10);

		map.AddTBInt(FlowAttr.DTSTime, FlowDTSTime.AllNodeSend.getValue(), "执行同步时间点", true, true);
		map.AddTBString(FlowAttr.DTSSpecNodes, null, "指定的节点ID", true, false, 0, 200, 100, false);
		map.AddTBInt(FlowAttr.DTSField, DTSField.SameNames.getValue(), "要同步的字段计算方式", true, true);
		map.AddTBString(FlowAttr.DTSFields, null, "要同步的字段s,中间用逗号分开.", false, false, 0, 2000, 100, false);

		// /#endregion 数据同步方案

		// map.AddSearchAttr(FlowAttr.FK_FlowSort);
		// map.AddSearchAttr(FlowAttr.FlowRunWay);

		RefMethod rm = new RefMethod();
		rm.Title = "设计检查报告"; // "设计检查报告";
		rm.ToolTip = "检查流程设计的问题。";
		rm.Icon = "/WF/Img/Btn/Confirm.gif";
		rm.ClassMethodName = this.toString() + ".DoCheck";
		map.AddRefMethod(rm);

		// rm = new RefMethod();
		// rm.Title = this.ToE("ViewDef", "视图定义"); //"视图定义";
		// rm.Icon = "/WF/Img/Btn/View.gif";
		// rm.ClassMethodName = this.ToString() + ".DoDRpt";
		// map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "报表运行"; // "报表运行";
		rm.Icon = "/WF/Img/Btn/View.gif";
		rm.ClassMethodName = this.toString() + ".DoOpenRpt()";
		// rm.Icon = "/WF/Img/Btn/Table.gif";
		map.AddRefMethod(rm);

		// rm = new RefMethod();
		// rm.Title = this.ToE("FlowDataOut", "数据转出定义"); //"数据转出定义";
		// // rm.Icon = "/WF/Img/Btn/Table.gif";
		// rm.ToolTip = "在流程完成时间，流程数据转储存到其它系统中应用。";
		// rm.ClassMethodName = this.ToString() + ".DoExp";
		// map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "删除数据";
		rm.Warning = "您确定要执行删除流程数据吗？";
		rm.ToolTip = "清除历史流程数据。";
		rm.ClassMethodName = this.toString() + ".DoExp";
		map.AddRefMethod(rm);

		// map.AttrsOfOneVSM.Add(new FlowStations(), new Stations(),
		// FlowStationAttr.FK_Flow,
		// FlowStationAttr.FK_Station, DeptAttr.Name, DeptAttr.No, "抄送岗位");

		this.set_enMap(map);
		return this.get_enMap();
	}

	// /#endregion

	// /#region 公共方法
	/**
	 * 设计数据转出
	 * 
	 * @return
	 */
	public final String DoExp() {
		this.DoCheck();

		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					BP.WF.Glo.getCCFlowAppPath() + "WF/Admin/Exp.aspx?CondType=0&FK_Flow=" + this.getNo(), "单据", "cdsn",
					800, 500, 210, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 定义报表
	 * 
	 * @return
	 */
	public final String DoDRpt() {
		this.DoCheck();
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					BP.WF.Glo.getCCFlowAppPath() + "WF/Admin/WFRpt.aspx?CondType=0&FK_Flow=" + this.getNo(), "单据",
					"cdsn", 800, 500, 210, 300);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 运行报表
	 * 
	 * @return
	 */
	public final String DoOpenRpt() {
		return null;
	}

	public final String DoDelData() {

		// /#region 删除流程表单的数据.
		String mysql = "SELECT OID FROM " + this.getPTable();
		FrmNodes fns = new FrmNodes();
		fns.Retrieve(FrmNodeAttr.FK_Flow, this.getNo());
		String strs = "";
		for (FrmNode nd : FrmNodes.convertFrmNodes(fns)) {
			if (strs.contains("@" + nd.getFK_Frm()) == true) {
				continue;
			}

			strs += "@" + nd.getFK_Frm() + "@";
			try {
				MapData md = new MapData(nd.getFK_Frm());
				DBAccess.RunSQL("DELETE FROM " + md.getPTable() + " WHERE OID in (" + mysql + ")");
			} catch (java.lang.Exception e) {
			}
		}

		// /#endregion 删除流程表单的数据.

		String sql = "  where FK_Node in (SELECT NodeID FROM WF_Node WHERE fk_flow='" + this.getNo() + "')";
		String sql1 = " where NodeID in (SELECT NodeID FROM WF_Node WHERE fk_flow='" + this.getNo() + "')";

		// DA.DBAccess.RunSQL("DELETE FROM WF_CHOfFlow WHERE FK_Flow='" +
		// this.No + "'");

		DBAccess.RunSQL("DELETE FROM WF_Bill WHERE FK_Flow='" + this.getNo() + "'");
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkerlist WHERE FK_Flow='" + this.getNo() + "'");
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE FK_Flow='" + this.getNo() + "'");

		DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow WHERE FK_Flow='" + this.getNo() + "'");

		String sqlIn = " WHERE ReturnNode IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo() + "')";
		DBAccess.RunSQL("DELETE FROM WF_ReturnWork " + sqlIn);
		DBAccess.RunSQL("DELETE FROM WF_GenerFH WHERE FK_Flow='" + this.getNo() + "'");
		DBAccess.RunSQL("DELETE FROM WF_SelectAccper " + sql);
		DBAccess.RunSQL("DELETE FROM WF_TransferCustom " + sql);
		// DA.DBAccess.RunSQL("DELETE FROM WF_FileManager " + sql);
		DBAccess.RunSQL("DELETE FROM WF_RememberMe " + sql);

		try {
			DBAccess.RunSQL("DELETE FROM ND" + Integer.parseInt(this.getNo()) + "Track ");
		} catch (java.lang.Exception e2) {
		}

		if (DBAccess.IsExitsObject(this.getPTable())) {
			DBAccess.RunSQL("DELETE FROM " + this.getPTable());
		}

		// DA.DBAccess.RunSQL("DELETE FROM WF_WorkList WHERE FK_Flow='" +
		// this.No + "'");
		// DA.DBAccess.RunSQL("DELETE FROM Sys_MapExt WHERE FK_MapData LIKE
		// 'ND"+int.Parse(this.No)+"%'"
		// );

		// 删除节点数据。
		Nodes nds = new Nodes(this.getNo());
		for (Node nd : Nodes.convertNodes(nds)) {
			try {
				Work wk = nd.getHisWork();
				DBAccess.RunSQL("DELETE FROM " + wk.getEnMap().getPhysicsTable());
			} catch (java.lang.Exception e3) {
			}

			MapDtls dtls = new MapDtls("ND" + nd.getNodeID());
			for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
				try {
					DBAccess.RunSQL("DELETE FROM " + dtl.getPTable());
				} catch (java.lang.Exception e4) {
				}
			}
		}
		MapDtls mydtls = new MapDtls("ND" + Integer.parseInt(this.getNo()) + "Rpt");
		for (MapDtl dtl : MapDtls.convertMapDtls(mydtls)) {
			try {
				DBAccess.RunSQL("DELETE FROM " + dtl.getPTable());
			} catch (java.lang.Exception e5) {
			}
		}
		return "删除成功...";
	}

	public static Flow DoLoadFlowTemplate(String fk_flowSort, String path, ImpFlowTempleteModel model) {
		return DoLoadFlowTemplate(fk_flowSort, path, model, -1);
	}

	/**
	 * 装载流程模板
	 * 
	 * @param fk_flowSort
	 *            流程类别
	 * @param path
	 *            流程名称
	 * @return
	 * @throws Exception
	 */
	// C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted
	// to Java:
	// ORIGINAL LINE: public static Flow DoLoadFlowTemplate(string fk_flowSort,
	// string path, ImpFlowTempleteModel model, int SpecialFlowNo = -1)
	public static Flow DoLoadFlowTemplate(String fk_flowSort, String path, ImpFlowTempleteModel model,
			int SpecialFlowNo) {
		File info = new File(path);
		DataSet ds = new DataSet();
		try {
			ds.readXmls(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ds.Tables.contains("WF_Flow") == false) {
			throw new RuntimeException("导入错误，非流程模版文件。");
		}

		DataTable dtFlow = ds.getHashTables().get("WF_Flow");
		Flow fl = new Flow();
		String oldFlowNo = dtFlow.getValue(0, "No").toString();
		String oldFlowName = dtFlow.getValue(0, "Name").toString();

		int oldFlowID = Integer.parseInt(oldFlowNo);
		// String timeKey = new java.util.Date().ToString("yyMMddhhmmss");
		SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
		String timeKey = format.format(new Date());
		// 判断流程标示.多个流程是否可以共用一个类，注释
		// if (dtFlow.Columns.Contains("FlowMark") == true)
		// {
		// string FlowMark = dtFlow.Rows[0]["FlowMark"].ToString();
		// if (string.IsNullOrEmpty(FlowMark) == false)
		// {
		// if (fl.IsExit(FlowAttr.FlowMark, FlowMark))
		// throw new Exception("@该流程标示:" + FlowMark + "已经存在于系统中,您不能导入.");
		// }
		// }

		switch (model) {
		case AsNewFlow: // 做为一个新流程.
			fl.setNo(fl.getGenerNewNo());
			fl.DoDelData();
			fl.DoDelete(); // 删除可能存在的垃圾.
			break;
		case AsTempleteFlowNo: // 用流程模版中的编号
			fl.setNo(oldFlowNo);
			if (fl.getIsExits()) {
				throw new RuntimeException("导入错误:流程模版(" + oldFlowName + ")中的编号(" + oldFlowNo + ")在系统中已经存在,流程名称为:"
				// + dtFlow.Rows[0]["Name"].toString());
						+ dtFlow.getValue(0, "Name").toString());
			} else {
				fl.setNo(oldFlowNo);
				fl.DoDelData();
				fl.DoDelete(); // 删除可能存在的垃圾.
			}
			break;
		case AsTempleteFlowNoOvrewaiteWhenExit: // 用流程模版中的编号，如果有并覆盖它.
			fl.setNo(oldFlowNo);
			fl.DoDelData();
			fl.DoDelete(); // 删除可能存在的垃圾.
			break;
		case AsSpecFlowNo:
			if (SpecialFlowNo <= 0) {
				throw new RuntimeException("@指定编号错误");
			}
			break;
		default:
			throw new RuntimeException("@没有判断");
		}

		// string timeKey = fl.No;
		int idx = 0;
		String infoErr = "";
		String infoTable = "";
		int flowID = Integer.parseInt(fl.getNo());

		// /#region 处理流程表数据
		for (DataColumn dc : dtFlow.Columns) {
			String val = (String) ((dtFlow.getValue(0, dc.ColumnName) instanceof String)
					? dtFlow.getValue(0, dc.ColumnName) : null);
			// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
			// string member and was converted to Java 'if-else' logic:
			// switch (dc.ColumnName.ToLower())
			// ORIGINAL LINE: case "no":
			if (dc.ColumnName.toLowerCase().equals("no") || dc.ColumnName.toLowerCase().equals("fk_flowsort")) {
				continue;
			}
			// ORIGINAL LINE: case "name":
			else if (dc.ColumnName.toLowerCase().equals("name")) {
				val = "复制:" + val + "_"
				// + new java.util.Date().ToString("MM月dd日HH时mm分");
						+ DataType.dateToStr(new Date(), "MM月dd日HH时mm分");
			} else {
			}
			fl.SetValByKey(dc.ColumnName, val);
		}
		fl.setFK_FlowSort(fk_flowSort);
		fl.Insert();

		// /#endregion 处理流程表数据

		// /#region 处理OID 插入重复的问题 Sys_GroupField, Sys_MapAttr.
		DataTable mydtGF = ds.getHashTables().get("Sys_GroupField");
		DataTable myDTAttr = ds.getHashTables().get("Sys_MapAttr");
		DataTable myDTAth = ds.getHashTables().get("Sys_FrmAttachment");
		DataTable myDTDtl = ds.getHashTables().get("Sys_MapDtl");
		DataTable myDFrm = ds.getHashTables().get("Sys_MapFrame");
		DataTable myDM2M = ds.getHashTables().get("Sys_MapM2M");
		if (mydtGF != null) {
			// throw new Exception("@" + fl.No + fl.Name +
			// ", 缺少：Sys_GroupField");
			for (DataRow dr : mydtGF.Rows) {
				// Sys.GroupField gf = new Sys.GroupField();
				BP.Sys.Frm.GroupField gf = new BP.Sys.Frm.GroupField();
				for (DataColumn dc : mydtGF.Columns) {
					String val = (String) ((dr.getValue(dc.ColumnName) instanceof String) ? dr.getValue(dc.ColumnName)
							: null);
					gf.SetValByKey(dc.ColumnName, val);
				}
				int oldID = (int) gf.getOID();
				gf.setOID(DBAccess.GenerOID());
				dr.setValue("OID", gf.getOID());

				// 属性。
				if (myDTAttr != null && myDTAttr.Columns.contains("GroupID")) {
					for (DataRow dr1 : myDTAttr.Rows) {
						if (dr1.getValue("GroupID") == null) {
							// dr1["GroupID"] = 0;
							dr1.setValue("GroupID", 0);
						}

						if (dr1.getValue("GroupID").toString().equals((new Integer(oldID)).toString())) {
							// dr1["GroupID"] = gf.OID;
							dr1.setValue("GroupID", gf.getOID());
						}
					}
				}

				if (myDTAth != null && myDTAth.Columns.contains("GroupID")) {
					// 附件。
					for (DataRow dr1 : myDTAth.Rows) {
						if (dr1.getValue("GroupID") == null) {
							dr1.setValue("GroupID", 0);
						}

						if (dr1.getValue("GroupID").toString().equals((new Integer(oldID)).toString())) {
							dr1.setValue("GroupID", gf.getOID());
						}
					}
				}

				if (myDTDtl != null && myDTDtl.Columns.contains("GroupID")) {
					// 从表。
					for (DataRow dr1 : myDTDtl.Rows) {
						if (dr1.getValue("GroupID") == null) {
							// dr1["GroupID"] = 0;
							dr1.setValue("GroupID", 0);
						}

						if (dr1.getValue("GroupID").toString().equals((new Integer(oldID)).toString())) {
							dr1.setValue("GroupID", gf.getOID());
						}
					}
				}

				if (myDFrm != null && myDFrm.Columns.contains("GroupID")) {
					// frm.
					for (DataRow dr1 : myDFrm.Rows) {
						if (dr1.getValue("GroupID") == null) {
							dr1.setValue("GroupID", 0);
						}

						if (dr1.getValue("GroupID").toString().equals((new Integer(oldID)).toString())) {
							dr1.setValue("GroupID", gf.getOID());
						}
					}
				}

				if (myDM2M != null && myDM2M.Columns.contains("GroupID")) {
					// m2m.
					for (DataRow dr1 : myDM2M.Rows) {
						if (dr1.getValue("GroupID") == null) {
							dr1.setValue("GroupID", 0);
						}

						if (dr1.getValue("GroupID").toString().equals((new Integer(oldID)).toString())) {
							dr1.setValue("GroupID", gf.getOID());
						}
					}
				}
			}
		}

		// /#endregion 处理OID 插入重复的问题。 Sys_GroupField ， Sys_MapAttr.

		int timeKeyIdx = 0;
		for (DataTable dt : ds.Tables) {
			timeKeyIdx++;
			timeKey = timeKey + (new Integer(timeKeyIdx)).toString();

			infoTable = "@导入:" + dt.TableName + " 出现异常。";
			// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
			// string member and was converted to Java 'if-else' logic:
			// switch (dt.TableName)
			// ORIGINAL LINE: case "WF_Flow":
			if (dt.TableName.equals("WF_Flow")) // 模版文件。
			{
				continue;
			}
			// ORIGINAL LINE: case "WF_FlowFormTree":
			else if (dt.TableName.equals("WF_FlowFormTree")) // 流程表单目录 add
																// 2013-12-03
			{
				// foreach (DataRow dr in dt.Rows)
				// {
				// FlowForm cd = new FlowForm();
				// foreach (DataColumn dc in dt.Columns)
				// {
				// string val = dr[dc.ColumnName] as string;
				// if (val == null)
				// continue;
				// switch (dc.ColumnName.ToLower())
				// {
				// case "fk_flow":
				// val = fl.No;
				// break;
				// default:
				// val = val.Replace("ND" + oldFlowID, "ND" + flowID);
				// break;
				// }
				// cd.SetValByKey(dc.ColumnName, val);
				// }
				// cd.Insert();
				// }
			}
			// ORIGINAL LINE: case "WF_FlowForm":
			else if (dt.TableName.equals("WF_FlowForm")) // 流程表单。 add 2013-12-03
			{
				// foreach (DataRow dr in dt.Rows)
				// {
				// FlowForm cd = new FlowForm();
				// foreach (DataColumn dc in dt.Columns)
				// {
				// string val = dr[dc.ColumnName] as string;
				// if (val == null)
				// continue;
				// switch (dc.ColumnName.ToLower())
				// {
				// case "fk_flow":
				// val = fl.No;
				// break;
				// default:
				// val = val.Replace("ND" + oldFlowID, "ND" + flowID);
				// break;
				// }
				// cd.SetValByKey(dc.ColumnName, val);
				// }
				// cd.Insert();
				// }
			}
			// ORIGINAL LINE: case "WF_NodeForm":
			else if (dt.TableName.equals("WF_NodeForm")) // 节点表单权限。 2013-12-03
			{
				for (DataRow dr : dt.Rows) {
					NodeToolbar cd = new NodeToolbar();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "tonodeid":
						if (dc.ColumnName.toLowerCase().equals("tonodeid")
								|| dc.ColumnName.toLowerCase().equals("fk_node")
								|| dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
							val = fl.getNo();
						} else {
							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						}
						cd.SetValByKey(dc.ColumnName, val);
					}
					cd.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmSln":
			else if (dt.TableName.equals("Sys_FrmSln")) // 表单字段权限。 2013-12-03
			{
				for (DataRow dr : dt.Rows) {
					FrmField cd = new FrmField();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "tonodeid":
						if (dc.ColumnName.toLowerCase().equals("tonodeid")
								|| dc.ColumnName.toLowerCase().equals("fk_node")
								|| dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
							val = fl.getNo();
						} else {
							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						}
						cd.SetValByKey(dc.ColumnName, val);
					}
					cd.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_NodeToolbar":
			else if (dt.TableName.equals("WF_NodeToolbar")) // 工具栏。
			{
				for (DataRow dr : dt.Rows) {
					NodeToolbar cd = new NodeToolbar();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "tonodeid":
						if (dc.ColumnName.toLowerCase().equals("tonodeid")
								|| dc.ColumnName.toLowerCase().equals("fk_node")
								|| dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
							val = fl.getNo();
						} else {
							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						}
						cd.SetValByKey(dc.ColumnName, val);
					}
					cd.setOID(DBAccess.GenerOID());
					cd.DirectInsert();
				}
			}
			// ORIGINAL LINE: case "WF_BillTemplate":
			else if (dt.TableName.equals("WF_BillTemplate")) {
				continue; // 因为省掉了 打印模板的处理。
				// for (DataRow dr : dt.Rows) {
				// BillTemplate bt = new BillTemplate();
				// for (DataColumn dc : dt.Columns) {
				// String val = (String) ((dr.getValue(dc.ColumnName) instanceof
				// String) ? (dr
				// .getValue(dc.ColumnName)) : null);
				// if (val == null) {
				// continue;
				// }
				// // C# TO JAVA CONVERTER NOTE: The following 'switch'
				// // operated on a string member and was converted to Java
				// // 'if-else' logic:
				// // switch (dc.ColumnName.ToLower())
				// // ORIGINAL LINE: case "fk_flow":
				// if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
				// val = (new Integer(flowID)).toString();
				// }
				// // ORIGINAL LINE: case "nodeid":
				// else if (dc.ColumnName.toLowerCase().equals("nodeid")
				// || dc.ColumnName.toLowerCase()
				// .equals("fk_node")) {
				// if (val.length() == 3) {
				// val = flowID + val.substring(1);
				// } else if (val.length() == 4) {
				// val = flowID + val.substring(2);
				// } else if (val.length() == 5) {
				// val = flowID + val.substring(3);
				// }
				// } else {
				// }
				// bt.SetValByKey(dc.ColumnName, val);
				// }
				// int i = 0;
				// String no = bt.getNo();
				// while (bt.getIsExits()) {
				// bt.setNo(no + (new Integer(i)).toString());
				// i++;
				// }
				//
				// try {
				// File.Copy(
				// // info.DirectoryName
				// info.getAbsolutePath() + "\\" + no + ".rtf",
				// BP.Sys.SystemConfig.getPathOfWebApp()
				// + "\\DataUser\\CyclostyleFile\\"
				// + bt.getNo() + ".rtf", true);
				// } catch (RuntimeException ex) {
				// // infoErr += "@恢复单据模板时出现错误：" + ex.Message +
				// // ",有可能是您在复制流程模板时没有复制同目录下的单据模板文件。";
				// }
				// bt.Insert();
				// }
			}
			// ORIGINAL LINE: case "WF_FrmNode":
			else if (dt.TableName.equals("WF_FrmNode")) // Conds.xml。
			{
				DBAccess.RunSQL("DELETE FROM WF_FrmNode WHERE FK_Flow='" + fl.getNo() + "'");
				for (DataRow dr : dt.Rows) {
					FrmNode fn = new FrmNode();
					fn.setFK_Flow(fl.getNo());
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? (dr.getValue(dc.ColumnName)) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
							val = fl.getNo();
						} else {
						}
						fn.SetValByKey(dc.ColumnName, val);
					}
					// 开始插入。
					fn.setMyPK(fn.getFK_Frm() + "_" + fn.getFK_Node());
					fn.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_FindWorkerRole":
			else if (dt.TableName.equals("WF_FindWorkerRole")) // 找人规则
			{
				for (DataRow dr : dt.Rows) {
					FindWorkerRole en = new FindWorkerRole();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? (dr.getValue(dc.ColumnName)) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")
								|| dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
							val = fl.getNo();
						} else {
							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						}
						en.SetValByKey(dc.ColumnName, val);
					}

					// 插入.
					en.DirectInsert();
				}
			}
			// ORIGINAL LINE: case "WF_Cond":
			else if (dt.TableName.equals("WF_Cond")) // Conds.xml。
			{
				for (DataRow dr : dt.Rows) {
					Cond cd = new Cond();
					cd.setFK_Flow(fl.getNo());
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? (dr.getValue(dc.ColumnName)) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "tonodeid":
						if (dc.ColumnName.toLowerCase().equals("tonodeid")
								|| dc.ColumnName.toLowerCase().equals("fk_node")
								|| dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")) {
							val = fl.getNo();
						} else {
							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						}
						cd.SetValByKey(dc.ColumnName, val);
					}

					cd.setFK_Flow(fl.getNo());

					// return this.FK_MainNode + "_" + this.ToNodeID + "_" +
					// this.HisCondType.ToString() + "_" +
					// ConnDataFrom.Stas.ToString();
					// ，开始插入。
					if (cd.getMyPK().contains("Stas")) {
						cd.setMyPK(cd.getFK_Node() + "_" + cd.getToNodeID() + "_" + cd.getHisCondType().toString() + "_"
								+ ConnDataFrom.Stas.toString());
					} else if (cd.getMyPK().contains("Dept")) {
						cd.setMyPK(cd.getFK_Node() + "_" + cd.getToNodeID() + "_" + cd.getHisCondType().toString() + "_"
								+ ConnDataFrom.Depts.toString());
					} else if (cd.getMyPK().contains("Paras")) {
						cd.setMyPK(cd.getFK_Node() + "_" + cd.getToNodeID() + "_" + cd.getHisCondType().toString() + "_"
								+ ConnDataFrom.Paras.toString());
					} else if (cd.getMyPK().contains("Url")) {
						cd.setMyPK(cd.getFK_Node() + "_" + cd.getToNodeID() + "_" + cd.getHisCondType().toString() + "_"
								+ ConnDataFrom.Url.toString());
					} else {
						// cd.setMyPK ( DBAccess.GenerOID().toString()
						// + new java.util.Date().ToString("yyMMddHHmmss"));
						cd.setMyPK(
								String.valueOf(DBAccess.GenerOID()) + DataType.dateToStr(new Date(), "yyMMddHHmmss"));
					}
					cd.DirectInsert();
				}
			}
			// ORIGINAL LINE: case "WF_CCDept":
			else if (dt.TableName.equals("WF_CCDept")) // 抄送到部门。
			{
				for (DataRow dr : dt.Rows) {
					CCDept cd = new CCDept();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						cd.SetValByKey(dc.ColumnName, val);
					}

					// 开始插入。
					try {
						cd.Insert();
					} catch (java.lang.Exception e) {
						cd.Update();
					}
				}
			}
			// ORIGINAL LINE: case "WF_NodeReturn":
			else if (dt.TableName.equals("WF_NodeReturn")) // 可退回的节点。
			{
				for (DataRow dr : dt.Rows) {
					NodeReturn cd = new NodeReturn();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")
								|| dc.ColumnName.toLowerCase().equals("returnn")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						cd.SetValByKey(dc.ColumnName, val);
					}

					// 开始插入。
					try {
						cd.Insert();
					} catch (java.lang.Exception e2) {
						cd.Update();
					}
				}
			}
			// ORIGINAL LINE: case "WF_Direction":
			else if (dt.TableName.equals("WF_Direction")) // 方向。
			{
				for (DataRow dr : dt.Rows) {
					Direction dir = new Direction();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "node":
						if (dc.ColumnName.toLowerCase().equals("node")
								|| dc.ColumnName.toLowerCase().equals("tonode")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						dir.SetValByKey(dc.ColumnName, val);
					}
					dir.setFK_Flow(fl.getNo());
					dir.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_TurnTo":
			else if (dt.TableName.equals("WF_TurnTo")) // 转向规则.
			{
				for (DataRow dr : dt.Rows) {
					TurnTo fs = new TurnTo();

					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						fs.SetValByKey(dc.ColumnName, val);
					}
					fs.setFK_Flow(fl.getNo());
					fs.Save();
				}
			}
			// ORIGINAL LINE: case "WF_FAppSet":
			else if (dt.TableName.equals("WF_FAppSet")) // FAppSets.xml。
			{
				continue;
			}
			// ORIGINAL LINE: case "WF_LabNote":
			else if (dt.TableName.equals("WF_LabNote")) // LabNotes.xml。
			{
				idx = 0;
				for (DataRow dr : dt.Rows) {
					LabNote ln = new LabNote();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						ln.SetValByKey(dc.ColumnName, val);
					}
					idx++;
					ln.setFK_Flow(fl.getNo());
					ln.setMyPK(ln.getFK_Flow() + "_" + ln.getX() + "_" + ln.getY() + "_" + idx);
					ln.DirectInsert();
				}
			}
			// ORIGINAL LINE: case "WF_NodeDept":
			else if (dt.TableName.equals("WF_NodeDept")) // FAppSets.xml。
			{
				for (DataRow dr : dt.Rows) {
					NodeDept dir = new NodeDept();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						dir.SetValByKey(dc.ColumnName, val);
					}
					dir.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_Node":
			else if (dt.TableName.equals("WF_Node")) // 导入节点信息.
			{
				for (DataRow dr : dt.Rows) {
					BP.WF.Template.NodeSheet nd = new BP.WF.Template.NodeSheet();

					CC cc = new CC(); // 抄送相关的信息.
					FrmWorkCheck fwc = new FrmWorkCheck();

					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// NodeAttr.NodeFrmID
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "nodefrmid":
						if (dc.ColumnName.toLowerCase().equals("nodefrmid")) {
							if (val.length() == 5) {
								val = "ND" + flowID + val.substring(3);
							} else if (val.length() == 6) {
								val = "ND" + flowID + val.substring(4);
							} else if (val.length() == 7) {
								val = "ND" + flowID + val.substring(5);
							}
						}
						// ORIGINAL LINE: case "nodeid":
						else if (dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")
								|| dc.ColumnName.toLowerCase().equals("fk_flowsort")) {
							continue;
						}
						// ORIGINAL LINE: case "showsheets":
						else if (dc.ColumnName.toLowerCase().equals("showsheets")
								|| dc.ColumnName.toLowerCase().equals("histonds")
								|| dc.ColumnName.toLowerCase().equals("groupstands")) {
							String key = "@" + flowID;
							val = val.replace(key, "");
						} else {
						}
						nd.SetValByKey(dc.ColumnName, val);
						cc.SetValByKey(dc.ColumnName, val);
						fwc.SetValByKey(dc.ColumnName, val);
					}

					nd.setFK_Flow(fl.getNo());
					nd.setFlowName(fl.getName());
					try {
						if (nd.GetValStringByKey("OfficePrintEnable").equals("打印")) {
							nd.SetValByKey("OfficePrintEnable", 0);
						}

						nd.DirectInsert();

						// 把抄送的信息也导入里面去.
						cc.DirectUpdate();
						fwc.DirectUpdate();
						DBAccess.RunSQL("DELETE FROM Sys_MapAttr WHERE FK_MapData='ND" + nd.getNodeID() + "'");
					} catch (RuntimeException ex) {
						throw new RuntimeException("@导入节点:FlowName:" + nd.getFlowName() + " nodeID: " + nd.getNodeID()
								+ " , " + nd.getName() + " 错误:" + ex.getMessage());
					}

					// 删除mapdata.
				}

				for (DataRow dr : dt.Rows) {
					Node nd = new Node();
					nd.setNodeID(Integer.parseInt(dr.getValue(NodeAttr.NodeID).toString()));
					nd.RetrieveFromDBSources();
					nd.setFK_Flow(fl.getNo());
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "nodefrmid":
						if (dc.ColumnName.toLowerCase().equals("nodefrmid")) {
							if (val.length() == 5) {
								val = "ND" + flowID + val.substring(3);
							} else if (val.length() == 6) {
								val = "ND" + flowID + val.substring(4);
							} else if (val.length() == 7) {
								val = "ND" + flowID + val.substring(5);
							}
						}
						// ORIGINAL LINE: case "nodeid":
						else if (dc.ColumnName.toLowerCase().equals("nodeid")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "fk_flow":
						else if (dc.ColumnName.toLowerCase().equals("fk_flow")
								|| dc.ColumnName.toLowerCase().equals("fk_flowsort")) {
							continue;
						}
						// ORIGINAL LINE: case "showsheets":
						else if (dc.ColumnName.toLowerCase().equals("showsheets")
								|| dc.ColumnName.toLowerCase().equals("histonds")
								|| dc.ColumnName.toLowerCase().equals("groupstands")) {
							String key = "@" + flowID;
							val = val.replace(key, "");
						} else {
						}
						nd.SetValByKey(dc.ColumnName, val);
					}
					nd.setFK_Flow(fl.getNo());
					nd.setFlowName(fl.getName());
					nd.DirectUpdate();
				}
			}
			// ORIGINAL LINE: case "WF_NodeStation":
			else if (dt.TableName.equals("WF_NodeStation")) // FAppSets.xml。
			{
				DBAccess.RunSQL(
						"DELETE FROM WF_NodeStation WHERE FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
								+ fl.getNo() + "')");
				for (DataRow dr : dt.Rows) {
					NodeStation ns = new NodeStation();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						ns.SetValByKey(dc.ColumnName, val);
					}
					ns.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_Listen":
			else if (dt.TableName.equals("WF_Listen")) // 信息侦听。
			{
				for (DataRow dr : dt.Rows) {
					Listen li = new Listen();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "oid":
						if (dc.ColumnName.toLowerCase().equals("oid")) {
							continue;
						}
						// ORIGINAL LINE: case "fk_node":
						else if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						}
						// ORIGINAL LINE: case "nodes":
						else if (dc.ColumnName.toLowerCase().equals("nodes")) {
							String[] nds = val.split("[@]", -1);
							String valExt = "";
							for (String nd : nds) {
								if (nd.equals("") || nd == null) {
									continue;
								}
								// Object tempVar = nd.clone();
								Object tempVar = new String(nd);
								String ndExt = (String) ((tempVar instanceof String) ? tempVar : null);
								if (ndExt.length() == 3) {
									ndExt = flowID + ndExt.substring(1);
								} else if (val.length() == 4) {
									ndExt = flowID + ndExt.substring(2);
								} else if (val.length() == 5) {
									ndExt = flowID + ndExt.substring(3);
								}
								ndExt = "@" + ndExt;
								valExt += ndExt;
							}
							val = valExt;
						} else {
						}
						li.SetValByKey(dc.ColumnName, val);
					}
					li.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_Enum":
			else if (dt.TableName.equals("Sys_Enum")) // RptEmps.xml。
			{
				for (DataRow dr : dt.Rows) {
					SysEnum se = new SysEnum();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
						} else {
						}
						se.SetValByKey(dc.ColumnName, val);
					}
					se.setMyPK(se.getEnumKey() + "_" + se.getLang() + "_" + se.getIntKey());
					if (se.getIsExits()) {
						continue;
					}
					se.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_EnumMain":
			else if (dt.TableName.equals("Sys_EnumMain")) // RptEmps.xml。
			{
				for (DataRow dr : dt.Rows) {
					SysEnumMain sem = new SysEnumMain();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						sem.SetValByKey(dc.ColumnName, val);
					}
					if (sem.getIsExits()) {
						continue;
					}
					sem.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_MapAttr":
			else if (dt.TableName.equals("Sys_MapAttr")) // RptEmps.xml。
			{
				for (DataRow dr : dt.Rows) {
					MapAttr ma = new MapAttr();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_mapdata":
						if (dc.ColumnName.toLowerCase().equals("fk_mapdata")
								|| dc.ColumnName.toLowerCase().equals("keyofen")
								|| dc.ColumnName.toLowerCase().equals("autofulldoc")) {
							if (val == null) {
								continue;
							}

							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						} else {
						}
						ma.SetValByKey(dc.ColumnName, val);
					}
					boolean b = ma.IsExit(MapAttrAttr.FK_MapData, ma.getFK_MapData(), MapAttrAttr.KeyOfEn,
							ma.getKeyOfEn());

					ma.setMyPK(ma.getFK_MapData() + "_" + ma.getKeyOfEn());
					if (b == true) {
						ma.DirectUpdate();
					} else {
						ma.DirectInsert();
					}
				}
			}
			// ORIGINAL LINE: case "Sys_MapData":
			else if (dt.TableName.equals("Sys_MapData")) // RptEmps.xml。
			{
				for (DataRow dr : dt.Rows) {
					MapData md = new MapData();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + Integer.parseInt(fl.getNo()));
						md.SetValByKey(dc.ColumnName, val);
					}
					md.Save();
				}
			}
			// ORIGINAL LINE: case "Sys_MapDtl":
			else if (dt.TableName.equals("Sys_MapDtl")) // RptEmps.xml。
			{
				for (DataRow dr : dt.Rows) {
					MapDtl md = new MapDtl();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						md.SetValByKey(dc.ColumnName, val);
					}
					md.Save();
				}
			}
			// ORIGINAL LINE: case "Sys_MapExt":
			else if (dt.TableName.equals("Sys_MapExt")) {
				for (DataRow dr : dt.Rows) {
					MapExt md = new MapExt();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						md.SetValByKey(dc.ColumnName, val);
					}
					md.Save();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmLine":
			else if (dt.TableName.equals("Sys_FrmLine")) {
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmLine en = new FrmLine();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}

					en.setMyPK(UUID.randomUUID().toString());
					// BP.DA.DBAccess.GenerOIDByGUID(); "LIE" + timeKey + "_" +
					// idx;
					// if (en.IsExitGenerPK())
					// continue;
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmEle":
			else if (dt.TableName.equals("Sys_FrmEle")) {
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmEle en = new FrmEle();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmImg":
			else if (dt.TableName.equals("Sys_FrmImg")) {
				idx = 0;
				// timeKey = new java.util.Date().ToString("yyyyMMddHHmmss");
				timeKey = DataType.dateToStr(new Date(), "yyyyMMddHHmmss");
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmImg en = new FrmImg();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}

					try {
						en.setMyPK("I" + timeKey + "_" + idx);
						en.Insert();
					} catch (java.lang.Exception e3) {
						en.setMyPK(UUID.randomUUID().toString());
						en.Insert();
					}
				}
			}
			// ORIGINAL LINE: case "Sys_FrmLab":
			else if (dt.TableName.equals("Sys_FrmLab")) {
				idx = 0;
				// timeKey = new java.util.Date().ToString("yyyyMMddHHmmss");
				timeKey = DataType.dateToStr(new Date(), "yyyyMMddHHmmss");
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmLab en = new FrmLab();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}

					// en.MyPK = Guid.NewGuid().ToString();
					// 出现重复的
					try {
						en.setMyPK("Lab" + timeKey + "_" + idx);
						en.Insert();
					} catch (java.lang.Exception e4) {
						en.setMyPK(UUID.randomUUID().toString());
						en.Insert();
					}
				}
			}
			// ORIGINAL LINE: case "Sys_FrmLink":
			else if (dt.TableName.equals("Sys_FrmLink")) {
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmLink en = new FrmLink();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						if (val == null) {
							continue;
						}

						en.SetValByKey(dc.ColumnName, val);
					}
					// en.setMyPK ( Guid.NewGuid().toString());
					en.setMyPK(UUID.randomUUID().toString());
					// en.MyPK = "LK" + timeKey + "_" + idx;
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmAttachment":
			else if (dt.TableName.equals("Sys_FrmAttachment")) {
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmAttachment en = new FrmAttachment();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}
					en.setMyPK(en.getFK_MapData() + "_" + en.getNoOfObj());
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmEvent":
			else if (dt.TableName.equals("Sys_FrmEvent")) // 事件.
			{
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmEvent en = new FrmEvent();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_MapM2M":
			else if (dt.TableName.equals("Sys_MapM2M")) // Sys_MapM2M.
			{
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					MapM2M en = new MapM2M();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_FrmRB":
			else if (dt.TableName.equals("Sys_FrmRB")) // Sys_FrmRB.
			{
				idx = 0;
				for (DataRow dr : dt.Rows) {
					idx++;
					FrmRB en = new FrmRB();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						val = val.replace("ND" + oldFlowID, "ND" + flowID);
						en.SetValByKey(dc.ColumnName, val);
					}
					en.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_NodeEmp":
			else if (dt.TableName.equals("WF_NodeEmp")) // FAppSets.xml。
			{
				for (DataRow dr : dt.Rows) {
					NodeEmp ne = new NodeEmp();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						ne.SetValByKey(dc.ColumnName, val);
					}
					ne.Insert();
				}
			}
			// ORIGINAL LINE: case "Sys_GroupField":
			else if (dt.TableName.equals("Sys_GroupField")) {
				for (DataRow dr : dt.Rows) {
					GroupField gf = new GroupField();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}
						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "enname":
						if (dc.ColumnName.toLowerCase().equals("enname")
								|| dc.ColumnName.toLowerCase().equals("keyofen")) {
							val = val.replace("ND" + oldFlowID, "ND" + flowID);
						} else {
						}
						gf.SetValByKey(dc.ColumnName, val);
					}
					// int oid = DBAccess.GenerOID();
					// DBAccess.RunSQL("UPDATE Sys_MapAttr SET GroupID=" +
					// gf.OID + " WHERE FK_MapData='" + gf.EnName +
					// "' AND GroupID=" + gf.OID);
					try {
						gf.InsertAsOID(gf.getOID());
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}
			// ORIGINAL LINE: case "WF_CCEmp":
			else if (dt.TableName.equals("WF_CCEmp")) // 抄送.
			{
				for (DataRow dr : dt.Rows) {
					CCEmp ne = new CCEmp();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						ne.SetValByKey(dc.ColumnName, val);
					}
					ne.Insert();
				}
			}
			// ORIGINAL LINE: case "WF_CCStation":
			else if (dt.TableName.equals("WF_CCStation")) // 抄送.
			{
				for (DataRow dr : dt.Rows) {
					CCStation ne = new CCStation();
					for (DataColumn dc : dt.Columns) {
						String val = (String) ((dr.getValue(dc.ColumnName) instanceof String)
								? dr.getValue(dc.ColumnName) : null);
						if (val == null) {
							continue;
						}

						// C# TO JAVA CONVERTER NOTE: The following 'switch'
						// operated on a string member and was converted to Java
						// 'if-else' logic:
						// switch (dc.ColumnName.ToLower())
						// ORIGINAL LINE: case "fk_node":
						if (dc.ColumnName.toLowerCase().equals("fk_node")) {
							if (val.length() == 3) {
								val = flowID + val.substring(1);
							} else if (val.length() == 4) {
								val = flowID + val.substring(2);
							} else if (val.length() == 5) {
								val = flowID + val.substring(3);
							}
						} else {
						}
						ne.SetValByKey(dc.ColumnName, val);
					}
					ne.Insert();
				}
			} else {
				// infoErr += "Error:" + dt.TableName;
				// throw new Exception("@unhandle named " + dt.TableName);
			}
		}

		// /#region 处理数据完整性。
		DBAccess.RunSQL("UPDATE WF_Cond SET FK_Node=NodeID WHERE FK_Node=0");
		DBAccess.RunSQL("UPDATE WF_Cond SET ToNodeID=NodeID WHERE ToNodeID=0");

		DBAccess.RunSQL("DELETE FROM WF_Cond WHERE NodeID NOT IN (SELECT NodeID FROM WF_Node)");
		DBAccess.RunSQL("DELETE FROM WF_Cond WHERE ToNodeID NOT IN (SELECT NodeID FROM WF_Node) ");
		DBAccess.RunSQL("DELETE FROM WF_Cond WHERE FK_Node NOT IN (SELECT NodeID FROM WF_Node) AND FK_Node > 0");

		// /#endregion

		if (infoErr.equals("")) {
			// // 删除空白行.
			// BP.DTS.DeleteBlankGroupField en = new
			// BP.DTS.DeleteBlankGroupField();
			// en.Do();

			infoTable = "";
			// fl.DoCheck();

			// 写入权限.
			// fl.WritToGPM(fl.FK_FlowSort);

			return fl; // "完全成功。";
		}

		infoErr = "@执行期间出现如下非致命的错误：\t\r" + infoErr + "@ " + infoTable;
		throw new RuntimeException(infoErr);
		// }
		// catch (Exception ex)
		// {
		// try
		// {
		// fl.DoDelete();
		// throw new Exception("@" + infoErr + " @table=" + infoTable + "@" +
		// ex.Message);
		// }
		// catch (Exception ex1)
		// {
		// throw new Exception("@删除已经产生的错误的流程数据期间错误:" + ex1.Message );
		// }
		// }
	}

	public final Node DoNewNode(int x, int y) {
		Node nd = new Node();
		int idx = this.getHisNodes().size();
		if (idx == 0) {
			idx++;
		}

		while (true) {
			String strID = this.getNo()
					// + (new Integer(idx)).toString().PadLeft(2, '0');
					+ StringUtils.leftPad(new Integer(idx).toString(), 2, '0');
			nd.setNodeID(Integer.parseInt(strID));
			if (!nd.getIsExits()) {
				break;
			}
			idx++;
		}

		nd.setHisNodeWorkType(NodeWorkType.Work);
		nd.setName("节点" + idx);
		nd.setHisNodePosType(NodePosType.Mid);
		nd.setFK_Flow(this.getNo());
		nd.setFlowName(this.getName());
		nd.setX(x);
		nd.setY(y);
		nd.setStep(idx);
		nd.Insert();

		nd.CreateMap();
		return nd;
	}

	/**
	 * 执行新建
	 * 
	 * @param flowSort
	 *            类别
	 * @param flowName
	 *            流程名称
	 * @param model
	 *            数据存储模式
	 * @param pTable
	 *            数据存储物理表
	 * @param FlowMark
	 *            流程标记
	 * @throws Exception
	 */
	public final void DoNewFlow(String flowSort, String flowName, DataStoreModel model, String pTable, String FlowMark)
			throws Exception {
		try {
			// 检查参数的完整性.
			if (StringHelper.isNullOrEmpty(pTable) == false && pTable.length() >= 1) {
				String c = pTable.substring(0, 1);
				if (DataType.IsNumStr(c) == true) {
					throw new RuntimeException("@非法的流程数据表(" + pTable + "),它会导致ccflow不能创建该表.");
				}
			}

			this.setName(flowName);
			// if (String.IsNullOrWhiteSpace(this.getName())) {
			if (StringHelper.isNullOrEmpty(this.getName())) {
				this.setName("新建流程" + this.getNo()); // 新建流程.
			}

			try {
				this.setNo(this.GenerNewNoByKey(FlowAttr.No));
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.setHisDataStoreModel(model);
			this.setPTable(pTable);
			this.setFK_FlowSort(flowSort);
			this.setFlowMark(FlowMark);

			if (StringHelper.isNullOrEmpty(FlowMark) == false) {
				if (this.IsExit(FlowAttr.FlowMark, FlowMark)) {
					throw new RuntimeException("@该流程标示:" + FlowMark + "已经存在于系统中.");
				}
			}

			// 给初始值
			// this.Paras =
			// "@StartNodeX=10@StartNodeY=15@EndNodeX=40@EndNodeY=10";
			this.setParas("@StartNodeX=200@StartNodeY=50@EndNodeX=200@EndNodeY=350");
			this.Save();

			// /#region 删除有可能存在的历史数据.
			Flow fl = new Flow(this.getNo());
			fl.DoDelData();
			fl.DoDelete();

			this.Save();

			// /#endregion 删除有可能存在的历史数据.

			Node nd = new Node();
			nd.setNodeID(Integer.parseInt(this.getNo() + "01"));
			nd.setName("开始节点"); // "开始节点";
			nd.setStep(1);
			nd.setFK_Flow(this.getNo());
			nd.setFlowName(this.getName());
			nd.setHisNodePosType(NodePosType.Start);
			nd.setHisNodeWorkType(NodeWorkType.StartWork);
			nd.setX(200);
			nd.setY(150);
			nd.setICON("前台");
			nd.Insert();

			nd.CreateMap();
			nd.getHisWork().CheckPhysicsTable();

			nd = new Node();
			nd.setNodeID(Integer.parseInt(this.getNo() + "02"));
			nd.setName("节点2"); // "结束节点";
			nd.setStep(2);
			nd.setFK_Flow(this.getNo());
			nd.setFlowName(this.getName());
			nd.setHisNodePosType(NodePosType.Mid);
			nd.setHisNodeWorkType(NodeWorkType.Work);
			nd.setX(200);
			nd.setY(250);
			nd.setICON("审核");
			nd.Insert();
			nd.CreateMap();
			nd.getHisWork().CheckPhysicsTable();

			MapData md = new MapData();
			md.setNo("ND" + Integer.parseInt(this.getNo()) + "Rpt");
			md.setName(this.getName());
			md.Save();

			// 装载模版.
			String file = BP.Sys.SystemConfig.getPathOfDataUser() + "XML" + File.separator
					+ "TempleteSheetOfStartNode.xml";
			// if (System.IO.File.Exists(file)) {
			if (new File(file).exists()) {
				// 如果存在开始节点表单模版
				DataSet ds = new DataSet();
				ds.readXmls(file);

				String nodeID = "ND" + Integer.parseInt(this.getNo() + "01");
				MapData.ImpMapData(nodeID, ds, false);
			} else {

				// Java:
				// /#region 生成CCForm 的装饰.
				FrmImg img = new FrmImg();
				img.setMyPK("Img"
						// + new java.util.Date().ToString("yyMMddhhmmss")
						+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo());
				// img.FK_MapData = "ND" + Integer.parseInt(this.No + "01");
				img.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				// img.X = (float) 577.26;
				// img.Y = (float) 3.45;
				//
				// img.W = (float) 137;
				// img.H = (float) 40;
				img.setX((float) 577.26);
				img.setY((float) 3.45);

				img.setW((float) 137);
				img.setH((float) 40);

				// img.ImgURL = "/ccform;component/Img/LogoBig.png";
				// img.LinkURL = "http://ccflow.org";
				// img.LinkTarget = "_blank";
				img.setImgURL("/ccform;component/Img/LogoBig.png");
				img.setLinkURL("http://ccflow.org");
				img.setLinkTarget("_blank");
				img.Insert();

				FrmLab lab = new FrmLab();
				if (BP.WF.Glo.getIsEnablePRI() == true) {
					lab = new FrmLab();
					lab.setMyPK("Lab"
							// + new java.util.Date().ToString("yyMMddhhmmss")
							+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo() + 1);
					lab.setText("优先级");
					lab.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
					// lab.X = (float) 109.05;
					// lab.Y = (float) 58.1;
					// lab.FontSize = 11;
					// lab.FontColor = "black";
					// lab.FontName = "Portable User Interface";
					// lab.FontStyle = "Normal";
					// lab.FontWeight = "normal";
					lab.setX((float) 109.05);
					lab.setY((float) 58.1);
					lab.setFontSize(11);
					lab.setFontColor("black");
					lab.setFontName("Portable User Interface");
					lab.setFontStyle("Normal");
					lab.setFontWeight("normal");
					lab.Insert();
				}

				lab = new FrmLab();
				lab.setMyPK("Lab"
						// + new java.util.Date().ToString("yyMMddhhmmss")
						+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo() + 2);
				lab.setText("发起人");
				lab.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				lab.setX((float) 106.48);
				lab.setY((float) 96.08);
				lab.setFontSize(11);
				lab.setFontColor("black");
				lab.setFontName("Portable User Interface");
				lab.setFontStyle("Normal");
				lab.setFontWeight("normal");
				lab.Insert();

				lab = new FrmLab();
				lab.setMyPK("Lab"
						// + new java.util.Date().ToString("yyMMddhhmmss")
						+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo() + 3);
				lab.setText("发起时间");
				lab.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				lab.setX((float) 307.64);
				lab.setY((float) 95.17);

				lab.setFontSize(11);
				lab.setFontColor("black");
				lab.setFontName("Portable User Interface");
				lab.setFontStyle("Normal");
				lab.setFontWeight("normal");
				lab.Insert();

				lab = new FrmLab();
				lab.setMyPK("Lab"
						// + new java.util.Date().ToString("yyMMddhhmmss")
						+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo() + 4);
				lab.setText("新建节点(请修改标题)");
				lab.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));

				lab.setX((float) 294.67);
				lab.setY((float) 8.27);

				lab.setFontSize(23);
				lab.setFontColor("Blue");
				lab.setFontName("Portable User Interface");
				lab.setFontStyle("Normal");
				lab.setFontWeight("normal");
				lab.Insert();

				lab = new FrmLab();
				lab.setMyPK("Lab"
						// + new java.util.Date().ToString("yyMMddhhmmss")
						+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo() + 5);
				lab.setText("说明:以上内容是ccflow自动产生的，您可以修改/删除它。@为了更方便您的设计您可以到http://ccflow.org官网下载表单模板.");
				lab.setText(lab.getText() + "@因为当前技术问题与silverlight开发工具使用特别说明如下:@");
				lab.setText(lab.getText() + "@1,改变控件位置: ");
				lab.setText(lab.getText() + "@  所有的控件都支持 wasd, 做为方向键用来移动控件的位置， 部分控件支持方向键. ");
				lab.setText(
						lab.getText() + "@2, 增加textbox, 从表, dropdownlistbox, 的宽度 shift+ -> 方向键增加宽度 shift + <- 减小宽度.");
				lab.setText(lab.getText() + "@3, 保存 windows键 + s.  删除 delete.  复制 ctrl+c   粘帖: ctrl+v.");
				lab.setText(lab.getText() + "@4, 支持全选，批量移动， 批量放大缩小字体., 批量改变线的宽度.");
				lab.setText(lab.getText() + "@5, 改变线的长度： 选择线，点绿色的圆点，拖拉它。.");
				lab.setText(lab.getText() + "@6, 放大或者缩小　label 的字体 , 选择一个多个label , 按 A+ 或者　A－　按钮.");
				lab.setText(lab.getText() + "@7, 改变线或者标签的颜色， 选择操作对象，点工具栏上的调色板.");

				lab.setX((float) 168.24);
				lab.setY((float) 163.7);
				lab.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				lab.setFontSize(11);
				lab.setFontColor("Red");
				lab.setFontName("Portable User Interface");
				lab.setFontStyle("Normal");
				lab.setFontWeight("normal");
				lab.Insert();

				String key = "L"
						// + new java.util.Date().ToString("yyMMddhhmmss")
						+ DataType.dateToStr(new Date(), "yyMMddhhmmss") + WebUser.getNo();
				FrmLine line = new FrmLine();
				line.setMyPK(key + "_1");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 281.82);
				line.setY1((float) 81.82);
				line.setX2((float) 281.82);
				line.setY2((float) 121.82);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_2");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 360);
				line.setY1((float) 80.91);
				line.setX2((float) 360);
				line.setY2((float) 120.91);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_3");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 158.82);
				line.setY1((float) 41.82);
				line.setX2((float) 158.82);
				line.setY2((float) 482.73);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_4");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 81.55);
				line.setY1((float) 80);
				line.setX2((float) 718.82);
				line.setY2((float) 80);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_5");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 81.82);
				line.setY1((float) 40);
				line.setX2((float) 81.82);
				line.setY2((float) 480.91);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_6");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 81.82);
				line.setY1((float) 481.82);
				line.setX2((float) 720);
				line.setY2((float) 481.82);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_7");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 83.36);
				line.setY1((float) 40.91);
				line.setX2((float) 717.91);
				line.setY2((float) 40.91);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_8");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 83.36);
				line.setY1((float) 120.91);
				line.setX2((float) 717.91);
				line.setY2((float) 120.91);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				line.setMyPK(key + "_9");
				line.setFK_MapData("ND" + Integer.parseInt(this.getNo() + "01"));
				line.setX1((float) 719.09);
				line.setY1((float) 40);
				line.setX2((float) 719.09);
				line.setY2((float) 482.73);
				line.setBorderWidth((float) 2);
				line.setBorderColor("Black");
				line.Insert();

				// Java:
				// /#endregion
			}

			// 写入权限.
			WritToGPM(flowSort);

			this.DoCheck_CheckRpt(this.getHisNodes());
			Flow.RepareV_FlowData_View();
		} catch (RuntimeException ex) {
			/**
			 * 删除垃圾数据.
			 */
			this.DoDelete();

			// 提示错误.
			throw new RuntimeException("创建流程错误:" + ex.getMessage());
		}

	}

	/**
	 * 写入权限
	 * 
	 * @param flowSort
	 */
	public final void WritToGPM(String flowSort) {

		return;
		// /#region 写入权限管理
		// if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
		// String sql = "";
		//
		// try {
		// sql = "DELETE FROM GPM_Menu WHERE FK_App='"
		// + SystemConfig.getSysNo() + "' AND Flag='Flow"
		// + this.getNo() + "'";
		// BP.DA.DBAccess.RunSQL(sql);
		// } catch (java.lang.Exception e) {
		// }
		//
		// // 开始组织发起流程的数据.
		// // 取得该流程的目录编号.
		// sql = "SELECT No FROM GPM_Menu WHERE Flag='FlowSort" + flowSort
		// + "' AND FK_App='" + BP.Sys.SystemConfig.getSysNo() + "'";
		// String parentNoOfMenu = DBAccess
		// .RunSQLReturnStringIsNull(sql, null);
		// if (parentNoOfMenu == null) {
		// throw new RuntimeException("@没有找到该流程的("
		// + BP.Sys.SystemConfig.getSysNo()
		// + ")目录在GPM系统中,请重新新建此目录。");
		// }
		//
		// // 取得该功能的主键编号.
		// // String treeNo = DBAccess.GenerOID("BP.GPM.Menu").toString();
		// String treeNo = String.valueOf(DBAccess.GenerOID("BP.GPM.Menu"));
		//
		// // 插入流程名称.
		// String url = "/WF/MyFlow.aspx?FK_Flow=" + this.getNo()
		// + "&FK_Node=" + Integer.parseInt(this.getNo()) + "01";
		//
		// sql = "INSERT INTO
		// GPM_Menu(No,Name,ParentNo,IsDir,MenuType,FK_App,IsEnable,Flag,Url)";
		// sql += " VALUES('{0}','{1}','{2}',{3},{4},'{5}',{6},'{7}','{8}')";
		// sql = String.format(sql, treeNo, this.getName(), parentNoOfMenu, 0,
		// 4, SystemConfig.getSysNo(), 1, "Flow" + this.getNo(), url);
		// DBAccess.RunSQL(sql);
		// }

		// /#endregion
	}

	/**
	 * 检查报表
	 */
	public final void CheckRpt() {
		this.DoCheck_CheckRpt(this.getHisNodes());
	}

	/**
	 * 更新之前做检查
	 * 
	 * @return
	 */
	@Override
	protected boolean beforeUpdate() {
		this.setVer(BP.DA.DataType.getCurrentDataTimess());
		Node.CheckFlow(this);
		return super.beforeUpdate();
	}

	/**
	 * 更新版本号
	 */
	public static void UpdateVer(String flowNo) {
		String sql = "UPDATE WF_Flow SET VER='" + BP.DA.DataType.getCurrentDataTimess() + "' WHERE No='" + flowNo + "'";
		BP.DA.DBAccess.RunSQL(sql);
	}

	public final void DoDelete() {
		// 删除流程数据.
		this.DoDelData();

		String sql = "";
		// sql = " DELETE FROM WF_chofflow WHERE FK_Flow='" + this.No + "'";
		sql += "@ DELETE  FROM WF_GenerWorkerlist WHERE FK_Flow='" + this.getNo() + "'";
		sql += "@ DELETE FROM  WF_GenerWorkFlow WHERE FK_Flow='" + this.getNo() + "'";
		sql += "@ DELETE FROM  WF_Cond WHERE FK_Flow='" + this.getNo() + "'";

		// 删除岗位节点。
		sql += "@ DELETE  FROM  WF_NodeStation WHERE FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";

		// 删除方向。
		sql += "@ DELETE FROM  WF_Direction WHERE FK_Flow='" + this.getNo() + "'";

		// 删除节点绑定信息.
		sql += "@ DELETE FROM WF_FrmNode  WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";

		sql += "@ DELETE FROM WF_NodeEmp  WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";
		sql += "@ DELETE FROM WF_CCEmp WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";
		sql += "@ DELETE FROM WF_CCDept WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";
		sql += "@ DELETE FROM WF_CCStation WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";

		sql += "@ DELETE FROM WF_NodeFlow WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";
		sql += "@ DELETE FROM WF_NodeReturn WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";

		sql += "@ DELETE FROM WF_NodeDept WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";
		sql += "@ DELETE FROM WF_NodeStation WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";
		sql += "@ DELETE FROM WF_NodeEmp WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";

		sql += "@ DELETE FROM WF_NodeToolbar WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";
		sql += "@ DELETE FROM WF_SelectAccper WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";
		sql += "@ DELETE FROM WF_TurnTo WHERE   FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";

		// 删除侦听.
		sql += "@ DELETE FROM WF_Listen WHERE FK_Node IN (SELECT NodeID FROM WF_Node WHERE FK_Flow='" + this.getNo()
				+ "')";

		// 删除d2d数据.
		// sql +=
		// "@GO DELETE WF_M2M WHERE FK_Node IN (SELECT NodeID FROM WF_Node WHERE
		// FK_Flow='"
		// + this.No + "')";
		// // 删除配置.
		// sql +=
		// "@ DELETE FROM WF_FAppSet WHERE NodeID IN (SELECT NodeID FROM WF_Node
		// WHERE FK_Flow='"
		// + this.No + "')";

		// 删除配置.
		sql += "@ DELETE FROM WF_FlowEmp WHERE FK_Flow='" + this.getNo() + "' ";

		// // 外部程序设置
		// sql +=
		// "@ DELETE FROM WF_FAppSet WHERE NodeID in (SELECT NodeID FROM WF_Node
		// WHERE FK_Flow='"
		// + this.No + "')";

		// 删除单据.
		sql += "@ DELETE FROM WF_BillTemplate WHERE  NodeID in (SELECT NodeID FROM WF_Node WHERE FK_Flow='"
				+ this.getNo() + "')";

		// 删除权限控制.
		sql += "@ DELETE FROM Sys_FrmSln WHERE FK_Flow='" + this.getNo() + "'";

		Nodes nds = new Nodes(this.getNo());
		for (Node nd : Nodes.convertNodes(nds)) {
			// 删除节点所有相关的东西.
			sql += "@ DELETE  FROM Sys_MapM2M WHERE FK_MapData='ND" + nd.getNodeID() + "'";
			nd.Delete();
		}

		sql += "@ DELETE  FROM WF_Node WHERE FK_Flow='" + this.getNo() + "'";
		sql += "@ DELETE  FROM  WF_LabNote WHERE FK_Flow='" + this.getNo() + "'";

		// 删除分组信息
		sql += "@ DELETE FROM Sys_GroupField WHERE EnName NOT IN(SELECT NO FROM Sys_MapData)";

		// /#region 删除流程报表,删除轨迹
		MapData md = new MapData();
		md.setNo("ND" + Integer.parseInt(this.getNo()) + "Rpt");
		md.Delete();

		// 删除视图.
		try {
			BP.DA.DBAccess.RunSQL("DROP VIEW V_" + this.getNo());
		} catch (java.lang.Exception e) {
		}

		// 删除轨迹.
		try {
			BP.DA.DBAccess.RunSQL("DROP TABLE ND" + Integer.parseInt(this.getNo()) + "Track ");
		} catch (java.lang.Exception e2) {
		}

		// /#endregion 删除流程报表,删除轨迹.

		// 执行录制的sql scripts.
		BP.DA.DBAccess.RunSQLs(sql);
		this.Delete(); // 删除需要移除缓存.

		Flow.RepareV_FlowData_View();

		// 删除权限管理
		if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
			try {
				DBAccess.RunSQL("DELETE FROM GPM_Menu WHERE Flag='Flow" + this.getNo() + "' AND FK_App='"
						+ SystemConfig.getSysNo() + "'");
			} catch (java.lang.Exception e3) {
			}
		}
	}

	// /#endregion
}