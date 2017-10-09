package BP.WF;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import BP.DA.AtPara;
import BP.DA.DBAccess;
import BP.DA.DBType;
import BP.DA.DataColumn;
import BP.DA.DataRow;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Log;
import BP.DA.Paras;
import BP.En.Attr;
import BP.En.Attrs;
import BP.En.Entity;
import BP.Port.Emp;
import BP.Port.Emps;
import BP.Port.WebUser;
import BP.Sys.DefVal;
import BP.Sys.GEDtl;
import BP.Sys.GEDtlAttr;
import BP.Sys.GEDtls;
import BP.Sys.SFDBSrc;
import BP.Sys.SystemConfig;
import BP.Sys.ToolbarExcel;
import BP.Sys.Frm.FrmAttachment;
import BP.Sys.Frm.FrmImg;
import BP.Sys.Frm.MapAttr;
import BP.Sys.Frm.MapAttrs;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDatas;
import BP.Sys.Frm.MapDtl;
import BP.Sys.Frm.MapDtls;
import BP.Sys.Frm.MapExt;
import BP.Sys.XML.EnumInfoXml;
import BP.Sys.XML.EnumInfoXmls;
import BP.Tools.StringHelper;
import BP.WF.Data.GERpt;
import BP.WF.Data.GERptAttr;
import BP.WF.Entity.FrmWorkCheck;
import BP.WF.Entity.GenerWorkFlow;
import BP.WF.Entity.GenerWorkerList;
import BP.WF.Entity.Track;
import BP.WF.EventBase.FlowEventBase;
import BP.WF.Port.SMS;
import BP.WF.Port.WFEmp;
import BP.WF.Template.Direction;
import BP.WF.Template.Flow;
import BP.WF.Template.FlowSort;
import BP.WF.Template.Flows;
import BP.WF.Template.Node;
import BP.WF.Template.NodeSheets;
import BP.WF.Template.TransferCustom;
import BP.WF.Template.AccepterRole.SelectAccper;
import BP.WF.Template.CC.CCList;
import BP.WF.Template.Ext.NodeToolbar;
import BP.WF.Template.Form.FrmNode;
import BP.WF.Template.Form.Flow.FlowFormTree;
import BP.WF.Template.Form.Sys.SysForm;
import BP.WF.Template.Form.Sys.SysFormTree;
import BP.WF.Template.Form.Sys.SysFormTrees;
import BP.WF.Template.Form.Sys.Sln.FrmField;
import BP.WF.Template.PubLib.ActionType;
import BP.WF.Template.PubLib.FlowAttr;
import BP.WF.Template.PubLib.StartLimitRole;
import BP.WF.Template.Rpt.MapRpts;
import BP.WF.Template.WorkBase.StartWorkAttr;
import BP.WF.Template.WorkBase.Work;
import BP.WF.Template.WorkBase.WorkAttr;
import TL.ContextHolderUtils;

//using Word = Microsoft.Office.Interop.Word;

/**
 * 全局(方法处理)
 * 
 */
public class Glo {
	public static String UserNo = null;

	/// #region 执行安装/升级.
	/**
	 * 执行升级
	 * 
	 * @return
	 */
	public static String UpdataCCFlowVer() {
		/// #region 检查是否需要升级，并更新升级的业务逻辑.
		String val = "201506131";
		String updataNote = "";

		updataNote += "20150613.升级CCRole.";
		updataNote += "201505302.升级FWCType.";
		updataNote += "2015051673.升级表单属性.";
		updataNote += "20150516. 为流程引擎增加了数据同步功能.";
		updataNote += "20150508. 增加删除bpm模式的一个视图 by:DaiGuoQiang.";
		updataNote += "20150506. 增加了bpm模式的一个视图.";
		updataNote += "20150505. 处理了岗位枚举值的问题 by:zhoupeng.";
		updataNote += "20150424. 优化发起列表的效率, by:zhoupeng.";

		//
		// * 升级版本记录:
		// * 20150330: 优化发起列表的效率, by:zhoupeng.
		// * 2, 升级表单树,支持动态表单树.
		// * 1, 执行一次Sender发送人的升级，原来由GenerWorkerList 转入WF_GenerWorkFlow.
		// * 0, 静默升级启用日期.2014-12
		//
		String sql = "SELECT IntVal FROM Sys_Serial WHERE CfgKey='Ver'";
		String currVer = DBAccess.RunSQLReturnStringIsNull(sql, "");
		if (val.equals(currVer)) {
			return null; // 不需要升级.
		}
		/// #endregion 检查是否需要升级，并更新升级的业务逻辑.

		String msg = "";
		try {
			// 更新 PassRate.
			String mySQL = "UPDATE WF_Node SET PassRate=100 WHERE PassRate IS NULL";
			BP.DA.DBAccess.RunSQL(mySQL);

			// 处理岗位枚举值的问题.
			if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
				try {
					mySQL = "UPDATE PORT_STATION A SET A.FK_STATIONTYPE=A.STAGRADE WHERE A.FK_STATIONTYPE IS NULL";
					BP.DA.DBAccess.RunSQL(mySQL);
				} catch (java.lang.Exception e) {
				}
			}

			/// #region 更新CA签名(2015-03-03)
			// BP.Tools.WFSealData sealData = new Tools.WFSealData();
			// sealData.CheckPhysicsTable();
			// sealData.UpdateColumn();
			/// #endregion 更新CA签名

			TransferCustom tc = new TransferCustom();
			tc.CheckPhysicsTable();

			// 增加部门字段。
			CCList cc = new CCList();
			cc.CheckPhysicsTable();

			/// #region 基础数据更新.
			// 删除枚举值,让其自动生成.
			String enumKey = "";
			BP.DA.DBAccess
					.RunSQL("DELETE FROM Sys_Enum WHERE EnumKey IN ('CCRole','FWCType','SelectAccepterEnable','NodeFormType','StartGuideWay','"
							+ FlowAttr.StartLimitRole
							+ "','BillFileType','EventDoType','FormType','BatchRole','StartGuideWay','NodeFormType')");

			Node wf_Node = new Node();
			wf_Node.CheckPhysicsTable();
			// 设置节点ICON.
			sql = "UPDATE WF_Node SET ICON='/WF/Data/NodeIcon/审核.png' WHERE ICON IS NULL";
			BP.DA.DBAccess.RunSQL(sql);

			BP.WF.Template.NodeSheet nodeSheet = new BP.WF.Template.NodeSheet();
			nodeSheet.CheckPhysicsTable();
			// 升级手机应用. 2014-08-02.
			sql = "UPDATE WF_Node SET MPhone_WorkModel=0,MPhone_SrcModel=0,MPad_WorkModel=0,MPad_SrcModel=0 WHERE MPhone_WorkModel IS NULL";
			BP.DA.DBAccess.RunSQL(sql);
			/// #endregion 基础数据更新.

			/// #region 标签
			sql = "DELETE FROM Sys_EnCfg WHERE No='BP.WF.Template.Ext.NodeSheet'";
			BP.DA.DBAccess.RunSQL(sql);
			sql = "INSERT INTO Sys_EnCfg(No,GroupTitle) VALUES ('BP.WF.Template.Ext.NodeSheet','";
			sql += "@NodeID=基本配置";
			sql += "@FormType=表单";
			sql += "@FWCSta=审核组件,适用于sdk表单审核组件与ccform上的审核组件属性设置.";
			sql += "@SendLab=按钮权限,控制工作节点可操作按钮.";
			sql += "@RunModel=运行模式,分合流,父子流程";
			sql += "@AutoJumpRole0=跳转,自动跳转规则当遇到该节点时如何让其自动的执行下一步.";
			sql += "@MPhone_WorkModel=移动,与手机平板电脑相关的应用设置.";
			sql += "@WarningDays=考核,时效考核,质量考核.";
			// sql += "@MsgCtrl=消息,流程消息信息.";
			sql += "@OfficeOpenLab=公文按钮,只有当该节点是公文流程时候有效";
			sql += "')";
			BP.DA.DBAccess.RunSQL(sql);

			sql = "DELETE FROM Sys_EnCfg WHERE No='BP.WF.Template.Ext.FlowSheet'";
			BP.DA.DBAccess.RunSQL(sql);
			sql = "INSERT INTO Sys_EnCfg(No,GroupTitle) VALUES ('BP.WF.Template.Ext.FlowSheet','";
			sql += "@No=基本配置";
			sql += "@FlowRunWay=启动方式,配置工作流程如何自动发起，该选项要与流程服务一起工作才有效.";
			sql += "@StartLimitRole=启动限制规则";
			sql += "@StartGuideWay=发起前置导航";
			sql += "@CFlowWay=延续流程";
			sql += "@DTSWay=流程数据与业务数据同步";
			sql += "')";
			BP.DA.DBAccess.RunSQL(sql);

			sql = "DELETE FROM Sys_EnCfg WHERE No='BP.Sys.MapDataExt'";
			BP.DA.DBAccess.RunSQL(sql);
			sql = "INSERT INTO Sys_EnCfg(No,GroupTitle) VALUES ('BP.Sys.MapDataExt','";
			sql += "@No=基本属性";
			sql += "@Designer=设计者信息";
			sql += "')";
			BP.DA.DBAccess.RunSQL(sql);

			/// #endregion

			/// #region 把节点的toolbarExcel, word 信息放入mapdata
			NodeSheets nss = new NodeSheets();
			nss.RetrieveAll();
			for (BP.WF.Template.NodeSheet ns : NodeSheets.convertNodeSheets(nss)) {
				ToolbarExcel te = new ToolbarExcel();
				te.setNo("ND" + ns.getNodeID());
				te.RetrieveFromDBSources();

				// te.Copy(ns);
				// te.Update();
			}
			/// #endregion

			/// #region 升级SelectAccper
			Direction dir = new Direction();
			dir.CheckPhysicsTable();

			SelectAccper selectAccper = new SelectAccper();
			selectAccper.CheckPhysicsTable();
			/// #endregion

			/// #region 升级wf-generworkerlist 2014-05-09
			GenerWorkerList gwl = new GenerWorkerList();
			gwl.CheckPhysicsTable();
			/// #endregion 升级wf-generworkerlist 2014-05-09

			/// #region 升级 NodeToolbar
			FrmField ff = new FrmField();
			ff.CheckPhysicsTable();

			MapAttr attr = new MapAttr();
			attr.CheckPhysicsTable();

			NodeToolbar bar = new NodeToolbar();
			bar.CheckPhysicsTable();

			FlowFormTree tree = new FlowFormTree();
			tree.CheckPhysicsTable();

			FrmNode nff = new FrmNode();
			nff.CheckPhysicsTable();

			SysForm ssf = new SysForm();
			ssf.CheckPhysicsTable();

			SysFormTree ssft = new SysFormTree();
			ssft.CheckPhysicsTable();

			FrmAttachment ath = new FrmAttachment();
			ath.CheckPhysicsTable();

			FrmField ffs = new FrmField();
			ffs.CheckPhysicsTable();
			/// #endregion

			/// #region 执行sql．
			BP.DA.DBAccess.RunSQL(
					"delete  from Sys_Enum WHERE EnumKey in ('BillFileType','EventDoType','FormType','BatchRole','StartGuideWay','NodeFormType')");
			DBAccess.RunSQL(
					"UPDATE Sys_FrmSln SET FK_Flow =(SELECT FK_FLOW FROM WF_Node WHERE NODEID=Sys_FrmSln.FK_Node) WHERE FK_Flow IS NULL");
			try {
				DBAccess.RunSQL("UPDATE WF_Flow SET StartGuidePara1=StartGuidePara WHERE  "
						+ BP.Sys.SystemConfig.getAppCenterDBLengthStr() + "(StartGuidePara) >=1 ");
			} catch (java.lang.Exception e2) {
			}

			try {
				DBAccess.RunSQL("UPDATE WF_FrmNode SET MyPK=FK_Frm+'_'+convert(varchar,FK_Node )+'_'+FK_Flow");
			} catch (java.lang.Exception e3) {
			}
			/// #endregion

			/// #region 检查必要的升级。
			// 部门
			BP.Port.Dept d = new BP.Port.Dept();
			d.CheckPhysicsTable();

			FrmWorkCheck fwc = new FrmWorkCheck();
			fwc.CheckPhysicsTable();

			GenerWorkFlow gwf = new GenerWorkFlow();
			gwf.CheckPhysicsTable();

			Flow myfl = new Flow();
			myfl.CheckPhysicsTable();

			Node nd = new Node();
			nd.CheckPhysicsTable();
			// Sys_SFDBSrc
			SFDBSrc sfDBSrc = new SFDBSrc();
			sfDBSrc.CheckPhysicsTable();
			/// #endregion 检查必要的升级。

			/// #region 执行更新.wf_node
			sql = "UPDATE WF_Node SET FWCType=0 WHERE FWCType IS NULL";
			sql += "@UPDATE WF_Node SET FWC_X=0 WHERE FWC_X IS NULL";
			sql += "@UPDATE WF_Node SET FWC_Y=0 WHERE FWC_Y IS NULL";
			sql += "@UPDATE WF_Node SET FWC_W=0 WHERE FWC_W IS NULL";
			sql += "@UPDATE WF_Node SET FWC_H=0 WHERE FWC_H IS NULL";
			BP.DA.DBAccess.RunSQLs(sql);
			/// #endregion 执行更新.

			/// #region 执行报表设计。
			Flows fls = new Flows();
			fls.RetrieveAll();
			for (Flow fl : fls.convertFlows(fls)) {
				try {
					MapRpts rpts = new MapRpts(fl.getNo());
				} catch (java.lang.Exception e4) {
					fl.DoCheck();
				}
			}
			/// #endregion

			/// #region 升级站内消息表 2013-10-20
			SMS sms = new SMS();
			sms.CheckPhysicsTable();
			/// #endregion

			/// #region 更新表单的边界.2014-10-18
			MapDatas mds = new MapDatas();
			mds.RetrieveAll();

			for (MapData md : mds.convertMapDatas(mds)) {
				md.ResetMaxMinXY(); // 更新边界.
			}
			/// #endregion 更新表单的边界.

			/// #region 重新生成view WF_EmpWorks, 2013-08-06.
			try {
				BP.DA.DBAccess.RunSQL("DROP VIEW WF_EmpWorks");
				BP.DA.DBAccess.RunSQL("DROP VIEW V_FlowStarter");
				BP.DA.DBAccess.RunSQL("DROP VIEW V_FlowStarterBPM");
			} catch (java.lang.Exception e5) {
			}

			try {
				BP.DA.DBAccess.RunSQL("DROP VIEW WF_NodeExt");
			} catch (java.lang.Exception e6) {
			}

			String sqlscript = "";
			// 执行必须的sql.
			if (BP.Sys.SystemConfig.getAppCenterDBType() == DBType.Oracle) {
				sqlscript = BP.Sys.SystemConfig.getCCFlowAppPath() + File.separator + "WF" + File.separator + "Data"
						+ File.separator + "Install" + File.separator + "SQLScript" + File.separator
						+ "InitCCFlowData_Ora.sql";
			} else {
				sqlscript = BP.Sys.SystemConfig.getCCFlowAppPath() + File.separator + "WF" + File.separator + "Data"
						+ File.separator + "Install" + File.separator + "SQLScript" + File.separator
						+ "InitCCFlowData.sql";
			}

			BP.DA.DBAccess.RunSQLScript(sqlscript);
			/// #endregion

			/// #region 升级Img
			FrmImg img = new FrmImg();
			img.CheckPhysicsTable();
			/// #endregion

			/// #region 修复 mapattr UIHeight, UIWidth 类型错误.
			switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
			case Oracle:
				msg = "@Sys_MapAttr 修改字段";
				break;
			case MSSQL:
				msg = "@修改sql server控件高度和宽度字段。";
				DBAccess.RunSQL("ALTER TABLE Sys_MapAttr ALTER COLUMN UIWidth float");
				DBAccess.RunSQL("ALTER TABLE Sys_MapAttr ALTER COLUMN UIHeight float");
				break;
			default:
				break;
			}
			/// #endregion

			/// #region 升级常用词汇
			switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
			case Oracle:
				int i = DBAccess.RunSQLReturnCOUNT(
						"SELECT * FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'SYS_DEFVAL' AND COLUMN_NAME = 'PARENTNO'");
				if (i == 0) {
					DBAccess.RunSQL("drop table Sys_DefVal");
					DefVal dv = new DefVal();
					dv.CheckPhysicsTable();
				}
				msg = "@Sys_DefVal 修改字段";
				break;
			case MSSQL:
				msg = "@修改 Sys_DefVal。";
				i = DBAccess.RunSQLReturnCOUNT(
						"SELECT * FROM SYSCOLUMNS WHERE ID=OBJECT_ID('Sys_DefVal') AND NAME='ParentNo'");
				if (i == 0) {
					DBAccess.RunSQL("drop table Sys_DefVal");
					DefVal dv = new DefVal();
					dv.CheckPhysicsTable();
				}
				break;
			default:
				break;
			}
			/// #endregion

			/// #region 登陆更新错误
			msg = "@登陆时错误。";
			DBAccess.RunSQL(
					"DELETE FROM Sys_Enum WHERE EnumKey IN ('DeliveryWay','RunModel','OutTimeDeal','FlowAppType')");
			try {
				DBAccess.RunSQL("UPDATE Port_Station SET StaGrade=FK_StationType WHERE StaGrade IS null ");
			} catch (java.lang.Exception e7) {

			}
			/// #endregion 登陆更新错误

			/// #region 升级表单树
			// 首先检查是否升级过.
			sql = "SELECT * FROM Sys_FormTree WHERE No = '0'";
			DataTable formTree_dt = DBAccess.RunSQLReturnTable(sql);
			if (formTree_dt.Rows.size() == 0) {
				// 没有升级过.增加根节点
				SysFormTree formTree = new SysFormTree();
				formTree.setNo("0");
				formTree.setName("表单库");
				formTree.setParentNo("");
				formTree.setTreeNo("0");
				formTree.setIdx(0);
				formTree.setIsDir(true);

				try {
					formTree.DirectInsert();
				} catch (java.lang.Exception e8) {
				}
				// 将表单库中的数据转入表单树
				SysFormTrees formSorts = new SysFormTrees();
				formSorts.RetrieveAll();

				for (SysFormTree item : formSorts.convertSysFormTrees(formSorts)) {
					if (item.getNo().equals("0")) {
						continue;
					}

					SysFormTree subFormTree = new SysFormTree();
					subFormTree.setNo(item.getNo());
					subFormTree.setName(item.getName());
					subFormTree.setParentNo("0");
					subFormTree.Save();
				}
				// 表单于表单树进行关联
				sql = "UPDATE Sys_MapData SET FK_FormTree=FK_FrmSort WHERE FK_FrmSort <> '' AND FK_FrmSort is not null";
				DBAccess.RunSQL(sql);
			}
			/// #endregion

			/// #region 执行admin登陆. 2012-12-25 新版本.
			Emp emp = new Emp();
			emp.setNo("admin");
			if (emp.RetrieveFromDBSources() == 1) {
				WebUser.SignInOfGener(emp, true);
			} else {
				emp.setNo("admin");
				emp.setName("admin");
				emp.setFK_Dept("01");
				emp.setPass("123");
				emp.Insert();
				WebUser.SignInOfGener(emp, true);
				// throw new Exception("admin 用户丢失，请注意大小写。");
			}
			/// #endregion 执行admin登陆.

			/// #region 修复 Sys_FrmImg 表字段 ImgAppType Tag0
			switch (BP.Sys.SystemConfig.getAppCenterDBType()) {
			case Oracle:
				int i = DBAccess.RunSQLReturnCOUNT(
						"SELECT * FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'SYS_FRMIMG' AND COLUMN_NAME = 'TAG0'");
				if (i == 0) {
					DBAccess.RunSQL("ALTER TABLE SYS_FRMIMG ADD (ImgAppType number,TAG0 nvarchar(500))");
				}
				msg = "@Sys_FrmImg 修改字段";
				break;
			case MSSQL:
				msg = "@修改 Sys_FrmImg。";
				i = DBAccess
						.RunSQLReturnCOUNT("SELECT * FROM SYSCOLUMNS WHERE ID=OBJECT_ID('Sys_FrmImg') AND NAME='Tag0'");
				if (i == 0) {
					DBAccess.RunSQL("ALTER TABLE Sys_FrmImg ADD ImgAppType int");
					DBAccess.RunSQL("ALTER TABLE Sys_FrmImg ADD Tag0 nvarchar(500)");
				}
				break;
			default:
				break;
			}
			/// #endregion

			// 最后更新版本号，然后返回.
			sql = "UPDATE Sys_Serial SET IntVal=" + val + " WHERE CfgKey='Ver'";
			if (DBAccess.RunSQL(sql) == 0) {
				sql = "INSERT INTO Sys_Serial (CfgKey,IntVal) VALUES('Ver'," + val + ") ";
				DBAccess.RunSQL(sql);
			}
			// 返回版本号.
			return val; // +"\t\n解决问题:" + updataNote;
		} catch (RuntimeException ex) {
			String err = "问题出处:" + ex.getMessage() + "<hr>" + msg + "<br>详细信息:@" + ex.getStackTrace()
					+ "<br>@<a href='../DBInstall.jsp' >点这里到系统升级界面。</a>";
			BP.DA.Log.DebugWriteError("系统升级错误:" + err);
			return "0";
			// return "升级失败,详细请查看日志.\\DataUser\\Log\\";
		}
	}

	/**
	 * CCFlowAppPath
	 * 
	 */
	/**
	 * CCFlowAppPath
	 */
	public static String getCCFlowAppPath() {
		String basePath = ContextHolderUtils.getRequest().getScheme() + "://"
				+ ContextHolderUtils.getRequest().getServerName() + ":"
				+ ContextHolderUtils.getRequest().getServerPort() + ContextHolderUtils.getRequest().getContextPath()
				+ "/";
		return basePath;
		// return BP.Sys.SystemConfig.GetValByKey("CCFlowAppPath", "/");
	}

	/**
	 * 安装包
	 * 
	 */
	public static void DoInstallDataBase(String lang, boolean isInstallFlowDemo) {
		java.util.ArrayList al = null;
		String info = "BP.En.Entity";
		al = BP.En.ClassFactory.GetObjects(info);

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 1, 创建or修复表
		for (Object obj : al) {
			Entity en = null;
			en = (Entity) ((obj instanceof Entity) ? obj : null);
			if (en == null) {
				continue;
			}

			if (isInstallFlowDemo == false) {
				// 如果不安装demo.
				String clsName = en.toString();
				if (clsName != null) {
					if (clsName.contains("BP.CN") || clsName.contains("BP.Demo")) {
						continue;
					}
				}
			}
			if (Glo.getOSModel() == OSModel.WorkFlow) {
				// 如果不安装gpm 就把bp.gpm 命名空间排除了.
				String clsName = en.toString();
				if (clsName != null) {
					if (clsName.contains("BP.GMP")) {
						continue;
					}
				}
			}

			String table = null;
			try {
				table = en.getEnMap().getPhysicsTable();
				if (table == null) {
					continue;
				}
			} catch (java.lang.Exception e) {
				continue;
			}

			// switch (table)
			if (table.equals("WF_EmpWorks") || table.equals("WF_GenerEmpWorkDtls") || table.equals("WF_GenerEmpWorks")
					|| table.equals("WF_NodeExt") || table.equals("V_FlowData")) {
				continue;
			} else if (table.equals("Sys_Enum")) {
				en.CheckPhysicsTable();
			} else {
				en.CheckPhysicsTable();
			}
			en.setPKVal("123");
			try {
				en.RetrieveFromDBSources();
			} catch (RuntimeException ex) {
				Log.DebugWriteWarning(ex.getMessage());
				en.CheckPhysicsTable();
			}
		}
		/// #endregion 修复

		/// #region 2, 注册枚举类型 SQL
		// 2, 注册枚举类型。
		EnumInfoXmls xmls = new EnumInfoXmls();
		xmls.RetrieveAll();
		for (EnumInfoXml xml : EnumInfoXmls.convertEnumInfoXmls(xmls)) {
			BP.Sys.SysEnums ses = new BP.Sys.SysEnums();
			ses.RegIt(xml.getKey(), xml.getVals());
		}
		/// #endregion 注册枚举类型

		/// #region 3, 执行基本的 sql
		if (isInstallFlowDemo == false) {
			SysFormTree frmSort = new SysFormTree();
			frmSort.setNo("01");
			frmSort.setName("表单类别1");
			frmSort.setParentNo("0");
			frmSort.Insert();
		}

		String sqlscript = "";
		if (Glo.getOSModel() == BP.WF.OSModel.WorkFlow) {
			// 如果是WorkFlow模式
			sqlscript = BP.Sys.SystemConfig.getCCFlowAppPath() + File.separator + "WF" + File.separator + "Data"
					+ File.separator + "Install" + File.separator + "SQLScript" + File.separator
					+ "Port_Inc_CH_WorkFlow.sql";
			BP.DA.DBAccess.RunSQLScript(sqlscript);
		}

		if (Glo.getOSModel() == BP.WF.OSModel.BPM) {
			// 如果是BPM模式
			sqlscript = BP.Sys.SystemConfig.getCCFlowAppPath() + File.separator + "GPM" + File.separator + "SQLScript"
					+ File.separator + "Port_Inc_CH_BPM.sql";
			BP.DA.DBAccess.RunSQLScript(sqlscript);
		}
		/// #endregion 修复

		/// #region 4, 创建视图与数据.
		// 执行必须的sql.
		if (BP.Sys.SystemConfig.getAppCenterDBType() == DBType.Oracle) {
			sqlscript = BP.Sys.SystemConfig.getCCFlowAppPath() + File.separator + "WF" + File.separator + "Data"
					+ File.separator + "Install" + File.separator + "SQLScript" + File.separator
					+ "InitCCFlowData_Ora.sql";
		} else {
			sqlscript = BP.Sys.SystemConfig.getCCFlowAppPath() + File.separator + "WF" + File.separator + "Data"
					+ File.separator + "Install" + File.separator + "SQLScript" + File.separator + "InitCCFlowData.sql";
		}

		BP.DA.DBAccess.RunSQLScript(sqlscript);
		/// #endregion 创建视图与数据

		/// #region 5, 初始化数据.
		if (isInstallFlowDemo) {
			sqlscript = SystemConfig.getPathOfData() + File.separator + "Install" + File.separator + "SQLScript"
					+ File.separator + "InitPublicData.sql";
			BP.DA.DBAccess.RunSQLScript(sqlscript);
		} else {
			FlowSort fs = new FlowSort();
			fs.setNo("02");
			fs.setParentNo("99");
			fs.setName("其他类");
			fs.DirectInsert();
		}
		/// #endregion 初始化数据

		/// #region 6, 生成临时的wf数据。
		if (isInstallFlowDemo) {
			BP.Port.Emps emps = new BP.Port.Emps();
			emps.RetrieveAllFromDBSource();
			int i = 0;
			for (BP.Port.Emp emp : Emps.convertEmps(emps)) {
				i++;
				BP.WF.Port.WFEmp wfEmp = new BP.WF.Port.WFEmp();
				wfEmp.Copy(emp);
				wfEmp.setNo(emp.getNo());

				if (wfEmp.getEmail().length() == 0) {
					wfEmp.setEmail(emp.getNo() + "@ccflow.org");
				}

				if (wfEmp.getTel().length() == 0) {
					wfEmp.setTel("82374939-6" + StringUtils.leftPad((new Integer(i)).toString(), 2, '0'));
				}

				if (wfEmp.getIsExits()) {
					wfEmp.Update();
				} else {
					wfEmp.Insert();
				}
			}

			// 生成简历数据.
			int oid = 1000;
			for (BP.Port.Emp emp : Emps.convertEmps(emps)) {
				// for (int myIdx = 0; myIdx < 6; myIdx++)
				// {
				// BP.WF.Demo.Resume re = new Demo.Resume();
				// re.NianYue = "200" + myIdx + "年01月";
				// re.FK_Emp = emp.No;
				// re.GongZuoDanWei = "工作部门-" + myIdx;
				// re.ZhengMingRen = "张" + myIdx;
				// re.BeiZhu = emp.Name + "同志工作认真.";
				// oid++;
				// re.InsertAsOID(oid);
				// }
			}
			// 生成年度月份数据.
			String sql = "";
			java.util.Date dtNow = new java.util.Date();
			for (int num = 0; num < 12; num++) {
				sql = "INSERT INTO Pub_NY (No,Name) VALUES ('" + DataType.dateToStr(dtNow, "yyyy-MM") + "','"
						+ DataType.dateToStr(dtNow, "yyyy-MM") + "')";
				// dtNow = dtNow.AddMonths(1);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dtNow);
				calendar.add(Calendar.MONTH, 1);
				dtNow = calendar.getTime();
			}
		}
		/// #endregion 初始化数据

		/// #region 执行补充的sql, 让外键的字段长度都设置成100.
		DBAccess.RunSQL("UPDATE Sys_MapAttr SET maxlen=100 WHERE LGType=2 AND MaxLen<100");
		DBAccess.RunSQL("UPDATE Sys_MapAttr SET maxlen=100 WHERE KeyOfEn='FK_Dept'");

		// Nodes nds = new Nodes();
		// nds.RetrieveAll();
		// foreach (Node nd in nds)
		// nd.HisWork.CheckPhysicsTable();

		/// #endregion 执行补充的sql, 让外键的字段长度都设置成100.

		// 删除空白的字段分组.
		BP.WF.DTS.DeleteBlankGroupField dts = new BP.WF.DTS.DeleteBlankGroupField();
		dts.Do();
	}

	/**
	 * 安装CCIM
	 * 
	 * @param lang
	 * @param yunXingHuanjing
	 * @param isDemo
	 */
	public static void DoInstallCCIM() throws IOException, Exception {
		String sqlscript = SystemConfig.getPathOfData() + "Install" + File.separator + "SQLScript" + File.separator
				+ "CCIM.sql";
		BP.DA.DBAccess.RunSQLScriptGo(sqlscript);
	}

	public static void KillProcess(String processName) // 杀掉进程的方法
	{
		// System.Diagnostics.Process[] processes =
		// System.Diagnostics.Process.GetProcesses();
		// for (System.Diagnostics.Process pro : processes)
		// {
		// String name = pro.ProcessName + ".exe";
		// if (name.toLowerCase().equals(processName.toLowerCase()))
		// {
		// pro.Kill();
		// }
		// }
		try {
			Process process = Runtime.getRuntime().exec("taskList");
			Scanner in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				String temp = in.nextLine();
				if (temp.toLowerCase().contains(processName.toLowerCase())) {
					String pid = temp.substring(9, temp.indexOf("Console"));
					Runtime.getRuntime().exec("tskill " + pid);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 产生新的编号
	 * 
	 * @param rptKey
	 * @return
	 */
	public static String GenerFlowNo(String rptKey) {
		rptKey = rptKey.replace("ND", "");
		rptKey = rptKey.replace("Rpt", "");
		switch (rptKey.length()) {
		case 0:
			return "001";
		case 1:
			return "00" + rptKey;
		case 2:
			return "0" + rptKey;
		case 3:
			return rptKey;
		default:
			return "001";
		}
		// return rptKey;
	}

	/** 
	 
	 
	*/
	public static boolean getIsShowFlowNum() {
		// switch (SystemConfig.AppSettings["IsShowFlowNum"])
		// ORIGINAL LINE: case "1":
		if (SystemConfig.GetValByKey("IsShowFlowNum", null).equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 产生word文档.
	 * 
	 * @param wk
	 */
	public static void GenerWord(Object filename, Work wk) {
	}
	// {
	// BP.WF.Glo.KillProcess("WINWORD.EXE");
	// String enName = wk.getEnMap().getPhysicsTable();
	// try
	// {
	// RegistryKey delKey =
	// Registry.getLocalMachine().OpenSubKey("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Shared
	// Tools\\Text Converters\\Import\\", true);
	// delKey.DeleteValue("MSWord6.wpc");
	// delKey.Close();
	// }
	// catch (java.lang.Exception e)
	// {
	// }
	//
	// GroupField currGF = new GroupField();
	// MapAttrs mattrs = new MapAttrs(enName);
	// GroupFields gfs = new GroupFields(enName);
	// MapDtls dtls = new MapDtls(enName);
	// for (MapDtl dtl : MapDtls.convertMapDtls(dtls) )
	// {
	// dtl.IsUse = false;
	// }
	//
	// // 计算出来单元格的行数。
	// int rowNum = 0;
	// for (GroupField gf :GroupFields.convertGroupFields(gfs) )
	// {
	// rowNum++;
	// boolean isLeft = true;
	// for (MapAttr attr :MapAttrs.convertMapAttrs(mattrs) )
	// {
	// if (attr.getUIVisible() == false)
	// {
	// continue;
	// }
	//
	// if (attr.getGroupID() != gf.getOID())
	// {
	// continue;
	// }
	//
	// if (attr.getUIIsLine())
	// {
	// rowNum++;
	// isLeft = true;
	// continue;
	// }
	//
	// if (isLeft == false)
	// {
	// rowNum++;
	// }
	// isLeft = !isLeft;
	// }
	// }
	//
	// rowNum = rowNum + 2 + dtls.size();
	//
	// // 创建Word文档
	// String CheckedInfo = "";
	// String message = "";
	// Object Nothing = System.Reflection.Missing.getValue();
	//
	// ///#region 没用代码
	// // object filename = fileName;
	//
	// //Word.Application WordApp = new Word.ApplicationClass();
	// //Word.Document WordDoc = WordApp.Documents.Add(ref Nothing, ref Nothing,
	// ref Nothing, ref Nothing);
	// //try
	// //{
	// // WordApp.ActiveWindow.View.Type = Word.WdViewType.wdOutlineView;
	// // WordApp.ActiveWindow.View.SeekView =
	// Word.WdSeekView.wdSeekPrimaryHeader;
	//
	// // #region 增加页眉
	// // // 添加页眉 插入图片
	// // string pict = SystemConfig.PathOfDataUser + "log.jpg"; // 图片所在路径
	// // if (System.IO.File.Exists(pict))
	// // {
	// // System.Drawing.Image img = System.Drawing.Image.FromFile(pict);
	// // object LinkToFile = false;
	// // object SaveWithDocument = true;
	// // object Anchor = WordDoc.Application.Selection.Range;
	// // WordDoc.Application.ActiveDocument.InlineShapes.AddPicture(pict, ref
	// LinkToFile,
	// // ref SaveWithDocument, ref Anchor);
	// // // WordDoc.Application.ActiveDocument.InlineShapes[1].Width =
	// img.Width; // 图片宽度
	// // // WordDoc.Application.ActiveDocument.InlineShapes[1].Height =
	// img.Height; // 图片高度
	// // }
	// // WordApp.ActiveWindow.ActivePane.Selection.InsertAfter("[驰骋业务流程管理系统
	// http://ccFlow.org]");
	// // WordApp.Selection.ParagraphFormat.Alignment =
	// Word.WdParagraphAlignment.wdAlignParagraphLeft; // 设置右对齐
	// // WordApp.ActiveWindow.View.SeekView =
	// Word.WdSeekView.wdSeekMainDocument; // 跳出页眉设置
	// // WordApp.Selection.ParagraphFormat.LineSpacing = 15f; // 设置文档的行间距
	// // #endregion
	//
	// // // 移动焦点并换行
	// // object count = 14;
	// // object WdLine = Word.WdUnits.wdLine; // 换一行;
	// // WordApp.Selection.MoveDown(ref WdLine, ref count, ref Nothing); //
	// 移动焦点
	// // WordApp.Selection.TypeParagraph(); // 插入段落
	//
	// // // 文档中创建表格
	// // Word.Table newTable = WordDoc.Tables.Add(WordApp.Selection.Range,
	// rowNum, 4, ref Nothing, ref Nothing);
	//
	// // // 设置表格样式
	// // newTable.Borders.OutsideLineStyle =
	// Word.WdLineStyle.wdLineStyleThickThinLargeGap;
	// // newTable.Borders.InsideLineStyle = Word.WdLineStyle.wdLineStyleSingle;
	//
	// // newTable.Columns[1].Width = 100f;
	// // newTable.Columns[2].Width = 100f;
	// // newTable.Columns[3].Width = 100f;
	// // newTable.Columns[4].Width = 100f;
	//
	// // // 填充表格内容
	// // newTable.Cell(1, 1).Range.Text = wk.EnDesc;
	// // newTable.Cell(1, 1).Range.Bold = 2; // 设置单元格中字体为粗体
	//
	// // // 合并单元格
	// // newTable.Cell(1, 1).Merge(newTable.Cell(1, 4));
	// // WordApp.Selection.Cells.VerticalAlignment =
	// Word.WdCellVerticalAlignment.wdCellAlignVerticalCenter; // 垂直居中
	// // WordApp.Selection.ParagraphFormat.Alignment =
	// Word.WdParagraphAlignment.wdAlignParagraphCenter; // 水平居中
	//
	// // int groupIdx = 1;
	// // foreach (GroupField gf in gfs)
	// // {
	// // groupIdx++;
	// // // 填充表格内容
	// // newTable.Cell(groupIdx, 1).Range.Text = gf.Lab;
	// // newTable.Cell(groupIdx, 1).Range.Font.Color =
	// Word.WdColor.wdColorDarkBlue; // 设置单元格内字体颜色
	// // newTable.Cell(groupIdx, 1).Shading.BackgroundPatternColor =
	// Word.WdColor.wdColorGray25;
	// // // 合并单元格
	// // newTable.Cell(groupIdx, 1).Merge(newTable.Cell(groupIdx, 4));
	// // WordApp.Selection.Cells.VerticalAlignment =
	// Word.WdCellVerticalAlignment.wdCellAlignVerticalCenter;
	//
	// // groupIdx++;
	//
	// // bool isLeft = true;
	// // bool isColumns2 = false;
	// // int currColumnIndex = 0;
	// // foreach (MapAttr attr in mattrs)
	// // {
	// // if (attr.UIVisible == false)
	// // continue;
	//
	// // if (attr.GroupID != gf.OID)
	// // continue;
	//
	// // if (newTable.Rows.Count < groupIdx)
	// // continue;
	//
	// // #region 增加从表
	// // foreach (MapDtl dtl in dtls)
	// // {
	// // if (dtl.IsUse)
	// // continue;
	//
	// // if (dtl.RowIdx != groupIdx - 3)
	// // continue;
	//
	// // if (gf.OID != dtl.GroupID)
	// // continue;
	//
	// // GEDtls dtlsDB = new GEDtls(dtl.No);
	// // QueryObject qo = new QueryObject(dtlsDB);
	// // switch (dtl.DtlOpenType)
	// // {
	// // case DtlOpenType.ForEmp:
	// // qo.AddWhere(GEDtlAttr.RefPK, wk.OID);
	// // break;
	// // case DtlOpenType.ForWorkID:
	// // qo.AddWhere(GEDtlAttr.RefPK, wk.OID);
	// // break;
	// // case DtlOpenType.ForFID:
	// // qo.AddWhere(GEDtlAttr.FID, wk.OID);
	// // break;
	// // }
	// // qo.DoQuery();
	//
	// // newTable.Rows[groupIdx].SetHeight(100f,
	// Word.WdRowHeightRule.wdRowHeightAtLeast);
	// // newTable.Cell(groupIdx, 1).Merge(newTable.Cell(groupIdx, 4));
	//
	// // Attrs dtlAttrs = dtl.GenerMap().Attrs;
	// // int colNum = 0;
	// // foreach (Attr attrDtl in dtlAttrs)
	// // {
	// // if (attrDtl.UIVisible == false)
	// // continue;
	// // colNum++;
	// // }
	//
	// // newTable.Cell(groupIdx, 1).Select();
	// // WordApp.Selection.Delete(ref Nothing, ref Nothing);
	// // Word.Table newTableDtl = WordDoc.Tables.Add(WordApp.Selection.Range,
	// dtlsDB.Count + 1, colNum,
	// // ref Nothing, ref Nothing);
	//
	// // newTableDtl.Borders.OutsideLineStyle =
	// Word.WdLineStyle.wdLineStyleSingle;
	// // newTableDtl.Borders.InsideLineStyle =
	// Word.WdLineStyle.wdLineStyleSingle;
	//
	// // int colIdx = 1;
	// // foreach (Attr attrDtl in dtlAttrs)
	// // {
	// // if (attrDtl.UIVisible == false)
	// // continue;
	// // newTableDtl.Cell(1, colIdx).Range.Text = attrDtl.Desc;
	// // colIdx++;
	// // }
	//
	// // int idxRow = 1;
	// // foreach (GEDtl item in dtlsDB)
	// // {
	// // idxRow++;
	// // int columIdx = 0;
	// // foreach (Attr attrDtl in dtlAttrs)
	// // {
	// // if (attrDtl.UIVisible == false)
	// // continue;
	// // columIdx++;
	//
	// // if (attrDtl.IsFKorEnum)
	// // newTableDtl.Cell(idxRow, columIdx).Range.Text =
	// item.GetValRefTextByKey(attrDtl.Key);
	// // else
	// // {
	// // if (attrDtl.MyDataType == DataType.AppMoney)
	// // newTableDtl.Cell(idxRow, columIdx).Range.Text =
	// item.GetValMoneyByKey(attrDtl.Key).ToString("0.00");
	// // else
	// // newTableDtl.Cell(idxRow, columIdx).Range.Text =
	// item.GetValStrByKey(attrDtl.Key);
	//
	// // if (attrDtl.IsNum)
	// // newTableDtl.Cell(idxRow, columIdx).Range.ParagraphFormat.Alignment =
	// Microsoft.Office.Interop.Word.WdParagraphAlignment.wdAlignParagraphRight;
	// // }
	// // }
	// // }
	//
	// // groupIdx++;
	// // isLeft = true;
	// // }
	// // #endregion 增加从表
	//
	// // if (attr.UIIsLine)
	// // {
	// // currColumnIndex = 0;
	// // isLeft = true;
	// // if (attr.IsBigDoc)
	// // {
	// // newTable.Rows[groupIdx].SetHeight(100f,
	// Word.WdRowHeightRule.wdRowHeightAtLeast);
	// // newTable.Cell(groupIdx, 1).Merge(newTable.Cell(groupIdx, 4));
	// // newTable.Cell(groupIdx, 1).Range.Text = attr.Name + ":\r\n" +
	// wk.GetValStrByKey(attr.KeyOfEn);
	// // }
	// // else
	// // {
	// // newTable.Cell(groupIdx, 2).Merge(newTable.Cell(groupIdx, 4));
	// // newTable.Cell(groupIdx, 1).Range.Text = attr.Name;
	// // newTable.Cell(groupIdx, 2).Range.Text =
	// wk.GetValStrByKey(attr.KeyOfEn);
	// // }
	// // groupIdx++;
	// // continue;
	// // }
	// // else
	// // {
	// // if (attr.IsBigDoc)
	// // {
	// // if (currColumnIndex == 2)
	// // {
	// // currColumnIndex = 0;
	// // }
	//
	// // newTable.Rows[groupIdx].SetHeight(100f,
	// Word.WdRowHeightRule.wdRowHeightAtLeast);
	// // if (currColumnIndex == 0)
	// // {
	// // newTable.Cell(groupIdx, 1).Merge(newTable.Cell(groupIdx, 2));
	// // newTable.Cell(groupIdx, 1).Range.Text = attr.Name + ":\r\n" +
	// wk.GetValStrByKey(attr.KeyOfEn);
	// // currColumnIndex = 3;
	// // continue;
	// // }
	// // else if (currColumnIndex == 3)
	// // {
	// // newTable.Cell(groupIdx, 2).Merge(newTable.Cell(groupIdx, 3));
	// // newTable.Cell(groupIdx, 2).Range.Text = attr.Name + ":\r\n" +
	// wk.GetValStrByKey(attr.KeyOfEn);
	// // currColumnIndex = 0;
	// // groupIdx++;
	// // continue;
	// // }
	// // else
	// // {
	// // continue;
	// // }
	// // }
	// // else
	// // {
	// // string s = "";
	// // if (attr.LGType == FieldTypeS.Normal)
	// // {
	// // if (attr.MyDataType == DataType.AppMoney)
	// // s = wk.GetValDecimalByKey(attr.KeyOfEn).ToString("0.00");
	// // else
	// // s = wk.GetValStrByKey(attr.KeyOfEn);
	// // }
	// // else
	// // {
	// // s = wk.GetValRefTextByKey(attr.KeyOfEn);
	// // }
	//
	// // switch (currColumnIndex)
	// // {
	// // case 0:
	// // newTable.Cell(groupIdx, 1).Range.Text = attr.Name;
	// // if (attr.IsSigan)
	// // {
	// // string path = BP.Sys.SystemConfig.PathOfDataUser + "\\Siganture\\" + s
	// + ".jpg";
	// // if (System.IO.File.Exists(path))
	// // {
	// // System.Drawing.Image img = System.Drawing.Image.FromFile(path);
	// // object LinkToFile = false;
	// // object SaveWithDocument = true;
	// // //object Anchor = WordDoc.Application.Selection.Range;
	// // object Anchor = newTable.Cell(groupIdx, 2).Range;
	//
	// // WordDoc.Application.ActiveDocument.InlineShapes.AddPicture(path, ref
	// LinkToFile,
	// // ref SaveWithDocument, ref Anchor);
	// // // WordDoc.Application.ActiveDocument.InlineShapes[1].Width =
	// img.Width; // 图片宽度
	// // // WordDoc.Application.ActiveDocument.InlineShapes[1].Height =
	// img.Height; // 图片高度
	// // }
	// // else
	// // {
	// // newTable.Cell(groupIdx, 2).Range.Text = s;
	// // }
	// // }
	// // else
	// // {
	// // if (attr.IsNum)
	// // {
	// // newTable.Cell(groupIdx, 2).Range.Text = s;
	// // newTable.Cell(groupIdx, 2).Range.ParagraphFormat.Alignment =
	// Microsoft.Office.Interop.Word.WdParagraphAlignment.wdAlignParagraphRight;
	// // }
	// // else
	// // {
	// // newTable.Cell(groupIdx, 2).Range.Text = s;
	// // }
	// // }
	// // currColumnIndex = 1;
	// // continue;
	// // break;
	// // case 1:
	// // newTable.Cell(groupIdx, 3).Range.Text = attr.Name;
	// // if (attr.IsSigan)
	// // {
	// // string path = BP.Sys.SystemConfig.PathOfDataUser + "\\Siganture\\" + s
	// + ".jpg";
	// // if (System.IO.File.Exists(path))
	// // {
	// // System.Drawing.Image img = System.Drawing.Image.FromFile(path);
	// // object LinkToFile = false;
	// // object SaveWithDocument = true;
	// // object Anchor = newTable.Cell(groupIdx, 4).Range;
	// // WordDoc.Application.ActiveDocument.InlineShapes.AddPicture(path, ref
	// LinkToFile,
	// // ref SaveWithDocument, ref Anchor);
	// // }
	// // else
	// // {
	// // newTable.Cell(groupIdx, 4).Range.Text = s;
	// // }
	// // }
	// // else
	// // {
	// // if (attr.IsNum)
	// // {
	// // newTable.Cell(groupIdx, 4).Range.Text = s;
	// // newTable.Cell(groupIdx, 4).Range.ParagraphFormat.Alignment =
	// Microsoft.Office.Interop.Word.WdParagraphAlignment.wdAlignParagraphRight;
	// // }
	// // else
	// // {
	// // newTable.Cell(groupIdx, 4).Range.Text = s;
	// // }
	// // }
	// // currColumnIndex = 0;
	// // groupIdx++;
	// // continue;
	// // break;
	// // default:
	// // break;
	// // }
	// // }
	// // }
	// // }
	// // } //结束循环
	//
	// // #region 添加页脚
	// // WordApp.ActiveWindow.View.SeekView =
	// Word.WdSeekView.wdSeekPrimaryFooter;
	// //
	// WordApp.ActiveWindow.ActivePane.Selection.InsertAfter("模板由ccflow自动生成，严谨转载。此流程的详细内容请访问
	// http://doc.ccFlow.org。 建造流程管理系统请致电: 0531-82374939 ");
	// // WordApp.Selection.ParagraphFormat.Alignment =
	// Word.WdParagraphAlignment.wdAlignParagraphRight;
	// // #endregion
	//
	// // // 文件保存
	// // WordDoc.SaveAs(ref filename, ref Nothing, ref Nothing, ref Nothing,
	// // ref Nothing, ref Nothing, ref Nothing, ref Nothing,
	// // ref Nothing, ref Nothing, ref Nothing, ref Nothing, ref Nothing,
	// // ref Nothing, ref Nothing, ref Nothing);
	//
	// // WordDoc.Close(ref Nothing, ref Nothing, ref Nothing);
	// // WordApp.Quit(ref Nothing, ref Nothing, ref Nothing);
	// // try
	// // {
	// // string docFile = filename.ToString();
	// // string pdfFile = docFile.Replace(".doc", ".pdf");
	// // Glo.Rtf2PDF(docFile, pdfFile);
	// // }
	// // catch (Exception ex)
	// // {
	// // BP.DA.Log.DebugWriteInfo("@生成pdf失败." + ex.Message);
	// // }
	// //}
	// //catch (Exception ex)
	// //{
	// // throw ex;
	// // // WordApp.Quit(ref Nothing, ref Nothing, ref Nothing);
	// // WordDoc.Close(ref Nothing, ref Nothing, ref Nothing);
	// // WordApp.Quit(ref Nothing, ref Nothing, ref Nothing);
	// //}
	// ///#endregion
	// }
	/// #endregion 执行安装.

	/// #region 全局的方法处理
	public static java.util.ArrayList<String> getFlowFields() {
		// C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous
		// methods are not converted by C# to Java Converter:
		// return GERptAttr.class.GetFields().Select(o => o.getName()).ToList();

		ArrayList<String> list = new ArrayList<String>();
		list.add(GERptAttr.AtPara);
		list.add(GERptAttr.BillNo);
		list.add(GERptAttr.CFlowNo);
		list.add(GERptAttr.CWorkID);
		list.add(GERptAttr.FID);
		list.add(GERptAttr.FK_Dept);
		list.add(GERptAttr.FK_NY);
		list.add(GERptAttr.FlowDaySpan);
		list.add(GERptAttr.FlowEmps);
		list.add(GERptAttr.FlowEnder);
		list.add(GERptAttr.FlowEnderRDT);
		list.add(GERptAttr.FlowEndNode);
		list.add(GERptAttr.FlowNote);
		list.add(GERptAttr.FlowStarter);
		list.add(GERptAttr.FlowStartRDT);
		list.add(GERptAttr.GuestName);
		list.add(GERptAttr.GuestNo);
		list.add(GERptAttr.GUID);
		list.add(GERptAttr.MyNum);
		list.add(GERptAttr.OID);
		list.add(GERptAttr.PEmp);
		list.add(GERptAttr.PFlowNo);
		list.add(GERptAttr.PNodeID);
		// list.add(GERptAttr.ProjNo);
		list.add(GERptAttr.PWorkID);
		list.add(GERptAttr.Title);
		list.add(GERptAttr.WFSta);
		list.add(GERptAttr.WFState);
		return list;

	}

	/**
	 * 根据文字处理抄送，与发送人
	 * 
	 * @param note
	 * @param emps
	 */
	public static void DealNote(String note, BP.Port.Emps emps) {
		note = "请综合处阅知。李江龙核示。请王薇、田晓红批示。";
		note = note.replace("阅知", "阅知@");

		note = note.replace("请", "@");
		note = note.replace("呈", "@");
		note = note.replace("报", "@");
		String[] strs = note.split("[@]", -1);

		String ccTo = "";
		String sendTo = "";
		for (String str : strs) {
			if (StringHelper.isNullOrEmpty(str)) {
				continue;
			}

			if (str.contains("阅知") == true || str.contains("阅度") == true) {
				// 抄送的.
				for (BP.Port.Emp emp : Emps.convertEmps(emps)) {
					if (str.contains(emp.getNo()) == false) {
						continue;
					}
					ccTo += emp.getNo() + ",";
				}
				continue;
			}

			if (str.contains("阅处") == true || str.contains("阅办") == true) {
				// 发送送的.
				for (BP.Port.Emp emp : Emps.convertEmps(emps)) {
					if (str.contains(emp.getNo()) == false) {
						continue;
					}
					sendTo += emp.getNo() + ",";
				}
				continue;
			}
		}
	}

	/// #region 与流程事件实体相关.
	private static java.util.Hashtable<String, Object> Htable_FlowFEE = null;

	/**
	 * 获得节点事件实体
	 * 
	 * @param enName
	 *            实例名称
	 * @return 获得节点事件实体,如果没有就返回为空.
	 */
	public static FlowEventBase GetFlowEventEntityByEnName(String enName) {
		if (Htable_FlowFEE == null || Htable_FlowFEE.isEmpty()) {
			Htable_FlowFEE = new java.util.Hashtable<String, Object>();
			java.util.ArrayList<FlowEventBase> al = BP.En.ClassFactory.GetObjects("BP.WF.EventBase.FlowEventBase");
			for (FlowEventBase en : al) {
				Htable_FlowFEE.put(en.getClass().getName(), en);
			}
		}
		FlowEventBase myen = (FlowEventBase) ((Htable_FlowFEE.get(enName) instanceof FlowEventBase)
				? Htable_FlowFEE.get(enName) : null);
		if (myen == null) {
			throw new RuntimeException("@根据类名称获取流程事件实体实例出现错误:" + enName + ",没有找到该类的实体.");
		}
		return myen;
	}

	/**
	 * 获得节点事件实体根据节点编码.
	 * 
	 * @param NodeMark
	 *            节点编码
	 * @return 返回实体，或者null
	 */
	public static FlowEventBase GetFlowEventEntityByFlowMark(String flowMark) {
		if (Htable_FlowFEE == null || Htable_FlowFEE.isEmpty()) {
			Htable_FlowFEE = new java.util.Hashtable<String, Object>();
			java.util.ArrayList<FlowEventBase> al = BP.En.ClassFactory.GetObjects("BP.WF.EventBase.FlowEventBase");
			Htable_FlowFEE.clear();
			for (FlowEventBase en : al) {
				Htable_FlowFEE.put(en.toString(), en);
			}
		}

		for (String key : Htable_FlowFEE.keySet()) {
			FlowEventBase fee = (FlowEventBase) ((Htable_FlowFEE.get(key) instanceof FlowEventBase)
					? Htable_FlowFEE.get(key) : null);
			if (fee.getFlowMark().equals(flowMark)) {
				return fee;
			}
		}

		// for (int i = 0; i < Htable_FlowFEE.Count; i++)
		// {
		// FlowEventBase fee = Htable_FlowFEE[i] as FlowEventBase;
		// }
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 与流程事件实体相关.

	/**
	 * 执行发送工作后处理的业务逻辑 用于流程发送后事件调用. 如果处理失败，就会抛出异常.
	 * 
	 */
	public static void DealBuinessAfterSendWork(String fk_flow, long workid, String doFunc, String WorkIDs,
			String cFlowNo, int cNodeID, String cEmp) {
		if (doFunc.equals("SetParentFlow")) {
			// 如果需要设置子父流程信息.
			// * 应用于合并审批,当多个子流程合并审批,审批后发起一个父流程.
			//
			String[] workids = WorkIDs.split("[,]", -1);
			String okworkids = ""; // 成功发送后的workids.
			GenerWorkFlow gwf = new GenerWorkFlow();
			for (String id : workids) {
				if (StringHelper.isNullOrEmpty(id)) {
					continue;
				}

				// 把数据copy到里面,让子流程也可以得到父流程的数据。
				long workidC = Long.parseLong(id);

				// 设置当前流程的ID
				BP.WF.Dev2Interface.SetParentInfo(cFlowNo, workidC, fk_flow, workid, cNodeID, cEmp);

				// 判断是否可以执行，不能执行也要发送下去.
				gwf.setWorkID(workidC);
				if (gwf.RetrieveFromDBSources() == 0) {
					continue;
				}

				// 是否可以执行？
				if (BP.WF.Dev2Interface.Flow_IsCanDoCurrentWork(gwf.getFK_Flow(), gwf.getFK_Node(), workidC,
						WebUser.getNo()) == false) {
					continue;
				}

				// 执行向下发送.
				try {
					BP.WF.Dev2Interface.Node_SendWork(cFlowNo, workidC);
					okworkids += workidC;
				} catch (RuntimeException ex) {
					// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor
					// in Java:
					/// #region 如果有一个发送失败，就撤销子流程与父流程.
					// 首先把主流程撤销发送.
					BP.WF.Dev2Interface.Flow_DoUnSend(fk_flow, workid);

					// 把已经发送成功的子流程撤销发送.
					String[] myokwokid = okworkids.split("[,]", -1);
					for (String okwokid : myokwokid) {
						if (StringHelper.isNullOrEmpty(id)) {
							continue;
						}

						// 把数据copy到里面,让子流程也可以得到父流程的数据。
						workidC = Long.parseLong(id);
						BP.WF.Dev2Interface.Flow_DoUnSend(cFlowNo, workidC);
					}
					// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor
					// in Java:
					/// #endregion 如果有一个发送失败，就撤销子流程与父流程.
					throw new RuntimeException("@在执行子流程(" + gwf.getTitle() + ")发送时出现如下错误:" + ex.getMessage());
				}
			}
		}

	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 全局的方法处理

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region web.config 属性.
	/**
	 * 根据配置的信息不同，从不同的表里获取人员岗位信息。
	 * 
	 */
	public static String getEmpStation() {
		if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
			return "Port_DeptEmpStation";
		} else {
			return "Port_EmpStation";
		}
	}

	public static String getEmpDept() {
		if (BP.WF.Glo.getOSModel() == OSModel.BPM) {
			return "Port_DeptEmp";
		} else {
			return "Port_EmpDept";
		}
	}

	/**
	 * 是否admin
	 * 
	 */
	public static boolean getIsAdmin() {
		String s = BP.Sys.SystemConfig.GetValByKey("adminers", null);

		if (StringHelper.isNullOrEmpty(s)) {
			s = "admin,";
		}
		return s.contains(WebUser.getNo());
	}

	/**
	 * 获取mapdata字段查询Like。
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param colName
	 *            列编号
	 */
	public static String MapDataLikeKey(String flowNo, String colName) {
		flowNo = String.valueOf(Integer.parseInt(flowNo));
		String len = BP.Sys.SystemConfig.getAppCenterDBLengthStr();
		if (flowNo.length() == 1) {
			return " " + colName + " LIKE 'ND" + flowNo + "%' AND " + len + "(" + colName + ")=5";
		}
		if (flowNo.length() == 2) {
			return " " + colName + " LIKE 'ND" + flowNo + "%' AND " + len + "(" + colName + ")=6";
		}
		if (flowNo.length() == 3) {
			return " " + colName + " LIKE 'ND" + flowNo + "%' AND " + len + "(" + colName + ")=7";
		}

		return " " + colName + " LIKE 'ND" + flowNo + "%' AND " + len + "(" + colName + ")=8";
	}

	/**
	 * 短信时间发送从 默认从 8 点开始.
	 * 
	 */
	public static int getSMSSendTimeFromHour() {
		try {
			return Integer.parseInt(BP.Sys.SystemConfig.GetValByKey("SMSSendTimeFromHour", null));
			// return
			// Integer.parseInt(BP.Sys.SystemConfig.AppSettings["SMSSendTimeFromHour"]);
		} catch (java.lang.Exception e) {
			return 8;
		}
	}

	/**
	 * 短信时间发送到 默认到 20 点结束.
	 * 
	 */
	public static int getSMSSendTimeToHour() {
		try {
			return Integer.parseInt(BP.Sys.SystemConfig.GetValByKey("SMSSendTimeToHour", null));
			// return
			// Integer.parseInt(BP.Sys.SystemConfig.AppSettings["SMSSendTimeToHour"]);
		} catch (java.lang.Exception e) {
			return 8;
		}
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion webconfig属性.

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 常用方法
	private static String html = "";
	private static java.util.ArrayList htmlArr = new java.util.ArrayList();
	private static String backHtml = "";
	private static long workid = 0;

	/**
	 * 模拟运行
	 * 
	 * @param flowNo
	 *            流程编号
	 * @param empNo
	 *            要执行的人员.
	 * @return 执行信息.
	 */
	public static String Simulation_RunOne(String flowNo, String empNo, String paras) {
		backHtml = ""; // 需要重新赋空值
		java.util.Hashtable ht = null;
		if (StringHelper.isNullOrEmpty(paras) == false) {
			AtPara ap = new AtPara(paras);
			ht = ap.getHisHT();
		}

		Emp emp = new Emp(empNo);
		backHtml += " **** 开始使用:" + Glo.GenerUserImgSmallerHtml(emp.getNo(), emp.getName()) + "登录模拟执行工作流程";
		BP.WF.Dev2Interface.Port_Login(empNo);

		workid = BP.WF.Dev2Interface.Node_CreateBlankWork(flowNo, ht, null, emp.getNo(), null);
		SendReturnObjs objs = BP.WF.Dev2Interface.Node_SendWork(flowNo, workid, ht);
		backHtml += objs.ToMsgOfHtml().replace("@", "<br>@"); // 记录消息.

		String[] accepters = objs.getVarAcceptersID().split("[,]", -1);

		for (String acce : accepters) {
			if (StringHelper.isNullOrEmpty(acce) == true) {
				continue;
			}

			// 执行发送.
			Simulation_Run_S1(flowNo, workid, acce, ht, empNo);
			break;
		}
		// return html;
		// return htmlArr;
		return backHtml;
	}

	private static boolean isAdd = true;

	private static void Simulation_Run_S1(String flowNo, long workid, String empNo, java.util.Hashtable ht,
			String beginEmp) {
		// htmlArr.Add(html);
		Emp emp = new Emp(empNo);
		// html = "";
		backHtml += "empNo" + beginEmp;
		backHtml += "<br> **** 让:" + Glo.GenerUserImgSmallerHtml(emp.getNo(), emp.getName()) + "执行模拟登录. ";
		// 让其登录.
		BP.WF.Dev2Interface.Port_Login(empNo);

		// 执行发送.
		SendReturnObjs objs = BP.WF.Dev2Interface.Node_SendWork(flowNo, workid, ht);
		backHtml += "<br>" + objs.ToMsgOfHtml().replace("@", "<br>@");

		if (objs.getVarAcceptersID() == null) {
			isAdd = false;
			backHtml += " <br> **** 流程结束,查看<a href='/WF/WFRpt.jsp?WorkID=" + workid + "&FK_Flow=" + flowNo
					+ "' target=_blank >流程轨迹</a> ====";
			// htmlArr.Add(html);
			// backHtml += "nextEmpNo";
			return;
		}

		if (StringHelper.isNullOrEmpty(objs.getVarAcceptersID())) // 此处添加为空判断，跳过下面方法的执行，否则出错。
		{
			return;
		}
		String[] accepters = objs.getVarAcceptersID().split("[,]", -1);

		for (String acce : accepters) {
			if (StringHelper.isNullOrEmpty(acce) == true) {
				continue;
			}

			// 执行发送.
			Simulation_Run_S1(flowNo, workid, acce, ht, beginEmp);
			break; // 就不让其执行了.
		}
	}

	/**
	 * 是否手机访问?
	 * 
	 * @return
	 */
	public static boolean IsMobile() {
		if (!SystemConfig.getIsBSsystem()) {
			return false;
		}

		String agent = (ContextHolderUtils.getRequest().getHeader("User-Agent") + "").toLowerCase().trim();
		if (agent.equals("") || agent.indexOf("mozilla") != -1 || agent.indexOf("opera") != -1) {
			return false;
		}
		return true;
	}

	/**
	 * 是否启用草稿
	 */
	public static boolean getIsEnableDraft() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsEnableDraft", false);
	}

	/**
	 * 产生单据编号
	 * 
	 * @param billFormat
	 * @param en
	 * @return
	 */
	public static String GenerBillNo(String billNo, long workid, Entity en, String flowPTable) {
		if (StringHelper.isNullOrEmpty(billNo)) {
			return "";
		}
		if (billNo.contains("@")) {
			billNo = BP.WF.Glo.DealExp(billNo, en, null);
		}

		// 如果，Bill 有规则
		billNo = billNo.replace("{YYYY}", DataType.dateToStr(new Date(), "yyyy"));
		billNo = billNo.replace("{yyyy}", DataType.dateToStr(new Date(), "yyyy"));

		billNo = billNo.replace("{yy}", DataType.dateToStr(new Date(), "yy"));
		billNo = billNo.replace("{YY}", DataType.dateToStr(new Date(), "yy"));

		billNo = billNo.replace("{MM}", DataType.dateToStr(new Date(), "MM"));
		billNo = billNo.replace("{DD}", String.format("%td", new Date()));
		billNo = billNo.replace("{dd}", String.format("%td", new Date()));
		billNo = billNo.replace("{HH}", DataType.dateToStr(new Date(), "MM"));
		billNo = billNo.replace("{mm}", DataType.dateToStr(new Date(), "mm"));
		billNo = billNo.replace("{LSH}", (new Long(workid)).toString());
		billNo = billNo.replace("{WorkID}", (new Long(workid)).toString());
		billNo = billNo.replace("{OID}", (new Long(workid)).toString());

		if (billNo.contains("@WebUser.DeptZi")) {
			String val = DBAccess
					.RunSQLReturnStringIsNull("SELECT Zi FROM Port_Dept where no='" + WebUser.getFK_Dept() + "'", "");
			billNo = billNo.replace("@WebUser.DeptZi", val.toString());
		}

		if (billNo.contains("{ParentBillNo}")) {
			String pWorkID = DBAccess
					.RunSQLReturnStringIsNull("SELECT PWorkID FROM " + flowPTable + " WHERE OID=" + workid, "0");
			String parentBillNo = DBAccess
					.RunSQLReturnStringIsNull("SELECT BillNo FROM WF_GenerWorkFlow WHERE WorkID=" + pWorkID, "");
			billNo = billNo.replace("{ParentBillNo}", parentBillNo);

			String sql = "";
			int num = 0;
			for (int i = 2; i < 7; i++) {
				if (billNo.contains("{LSH" + i + "}") == false) {
					continue;
				}

				sql = "SELECT COUNT(OID) FROM " + flowPTable + " WHERE PWorkID =" + pWorkID;
				num = BP.DA.DBAccess.RunSQLReturnValInt(sql, 0);
				billNo = billNo + StringUtils.leftPad((new Integer(num)).toString(), i, '0');
				// billNo = billNo + (new Integer(num)).toString().PadLeft(i,
				// '0');
				billNo = billNo.replace("{LSH" + i + "}", "");
				break;
			}
		} else {
			String sql = "";
			int num = 0;
			for (int i = 2; i < 7; i++) {
				if (billNo.contains("{LSH" + i + "}") == false) {
					continue;
				}

				billNo = billNo.replace("{LSH" + i + "}", "");
				sql = "SELECT COUNT(*) FROM " + flowPTable + " WHERE BillNo LIKE '" + billNo + "%'";
				num = BP.DA.DBAccess.RunSQLReturnValInt(sql, 0) + 1;
				// billNo = billNo + (new Integer(num)).toString().PadLeft(i,
				// '0');
				billNo = billNo + StringUtils.leftPad((new Integer(num)).toString(), i, '0');
			}
		}
		return billNo;
	}

	/**
	 * 加入track
	 * 
	 * @param at
	 *            事件类型
	 * @param flowNo
	 *            流程编号
	 * @param workID
	 *            工作ID
	 * @param fid
	 *            流程ID
	 * @param fromNodeID
	 *            从节点编号
	 * @param fromNodeName
	 *            从节点名称
	 * @param fromEmpID
	 *            从人员ID
	 * @param fromEmpName
	 *            从人员名称
	 * @param toNodeID
	 *            到节点编号
	 * @param toNodeName
	 *            到节点名称
	 * @param toEmpID
	 *            到人员ID
	 * @param toEmpName
	 *            到人员名称
	 * @param note
	 *            消息
	 * @param tag
	 *            参数用@分开
	 */
	public static void AddToTrack(ActionType at, String flowNo, long workID, long fid, int fromNodeID,
			String fromNodeName, String fromEmpID, String fromEmpName, int toNodeID, String toNodeName, String toEmpID,
			String toEmpName, String note, String tag) {
		if (toNodeID == 0) {
			toNodeID = fromNodeID;
			toNodeName = fromNodeName;
		}

		Track t = new Track();
		t.setWorkID(workID);
		t.setFID(fid);
		t.setRDT(DataType.getCurrentDataTimess());

		t.setHisActionType(at);
		t.setNDFrom(fromNodeID);
		t.setNDFromT(fromNodeName);

		t.setEmpFrom(fromEmpID);
		t.setEmpFromT(fromEmpName);
		t.FK_Flow = flowNo;

		t.setNDTo(toNodeID);
		t.setNDToT(toNodeName);

		t.setEmpTo(toEmpID);
		t.setEmpToT(toEmpName);
		t.setMsg(note);

		// 参数.
		if (tag != null) {
			t.setTag(tag);
		}

		try {
			t.Insert();
		} catch (java.lang.Exception e) {
			t.CheckPhysicsTable();
			t.Insert();
		}
	}

	/**
	 * 计算表达式是否通过(或者是否正确.)
	 * 
	 * @param exp
	 *            表达式
	 * @param en
	 *            实体
	 * @return true/false
	 */
	public static boolean ExeExp(String exp, Entity en) {
		exp = exp.replace("@WebUser.No", WebUser.getNo());
		exp = exp.replace("@WebUser.Name", WebUser.getName());
		exp = exp.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());
		exp = exp.replace("@WebUser.FK_DeptName", WebUser.getFK_DeptName());

		String[] strs = exp.split("[ ]", -1);
		boolean isPass = false;

		String key = strs[0].trim();
		String oper = strs[1].trim();
		String val = strs[2].trim();
		val = val.replace("'", "");
		val = val.replace("%", "");
		val = val.replace("~", "");
		BP.En.Row row = en.getRow();
		for (String item : row.keySet()) {
			if (!item.trim().equals(key)) {
				continue;
			}

			String valPara = row.GetValByKey(key).toString();
			if (oper.equals("=")) {
				if (val.equals(valPara)) {
					return true;
				}
			}

			if (oper.toUpperCase().equals("LIKE")) {
				if (valPara.contains(val)) {
					return true;
				}
			}

			if (oper.equals(">")) {
				if (Float.parseFloat(valPara) > Float.parseFloat(val)) {
					return true;
				}
			}
			if (oper.equals(">=")) {
				if (Float.parseFloat(valPara) >= Float.parseFloat(val)) {
					return true;
				}
			}
			if (oper.equals("<")) {
				if (Float.parseFloat(valPara) < Float.parseFloat(val)) {
					return true;
				}
			}
			if (oper.equals("<=")) {
				if (Float.parseFloat(valPara) <= Float.parseFloat(val)) {
					return true;
				}
			}

			if (oper.equals("!=")) {
				if (Float.parseFloat(valPara) != Float.parseFloat(val)) {
					return true;
				}
			}

			throw new RuntimeException("@参数格式错误:" + exp + " Key=" + key + " oper=" + oper + " Val=" + val);
		}

		return false;
	}

	/**
	 * 执行PageLoad装载数据
	 * 
	 * @param item
	 * @param en
	 * @param mattrs
	 * @param dtls
	 * @return
	 */
	public static Entity DealPageLoadFull(Entity en, MapExt item, MapAttrs mattrs, MapDtls dtls) {
		if (item == null) {
			return en;
		}

		DataTable dt = null;
		String sql = item.getTag();
		if (StringHelper.isNullOrEmpty(sql) == false) {
			// 如果有填充主表的sql
			sql = Glo.DealExp(sql, en, null);

			if (StringHelper.isNullOrEmpty(sql) == false) {
				if (sql.contains("@")) {
					throw new RuntimeException("设置的sql有错误可能有没有替换的变量:" + sql);
				}
				dt = DBAccess.RunSQLReturnTable(sql);
				if (dt.Rows.size() == 1) {
					DataRow dr = dt.Rows.get(0);
					for (DataColumn dc : dt.Columns) {
						if (StringHelper.isNullOrEmpty(en.GetValStringByKey(dc.ColumnName))
								|| en.GetValStringByKey(dc.ColumnName).equals("0")) {
							en.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName).toString());
						}
					}
				}
			}
		}

		if (StringHelper.isNullOrEmpty(item.getTag1()) || item.getTag1().length() < 15) {
			return en;
		}

		// 填充从表.
		for (MapDtl dtl : MapDtls.convertMapDtls(dtls)) {
			String[] sqls = item.getTag1().split("[*]", -1);
			for (String mysql : sqls) {
				if (StringHelper.isNullOrEmpty(mysql)) {
					continue;
				}
				if (mysql.contains(dtl.getNo() + "=") == false) {
					continue;
				}
				if (mysql.equals(dtl.getNo() + "=") == true) {
					continue;
				}

				// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in
				// Java:
				/// #region 处理sql.
				sql = Glo.DealExp(mysql, en, null);
				// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in
				// Java:
				/// #endregion 处理sql.

				if (StringHelper.isNullOrEmpty(sql)) {
					continue;
				}

				if (sql.contains("@")) {
					throw new RuntimeException("设置的sql有错误可能有没有替换的变量:" + sql);
				}

				GEDtls gedtls = null;

				try {
					gedtls = new GEDtls(dtl.getNo());
					gedtls.Delete(GEDtlAttr.RefPK, en.getPKVal());
				} catch (RuntimeException ex) {
					if (gedtls.getGetNewEntity() instanceof GEDtl) {
						gedtls.getGetNewEntity().CheckPhysicsTable();
					}
				}

				dt = DBAccess.RunSQLReturnTable(
						sql.startsWith(dtl.getNo() + "=") ? sql.substring((dtl.getNo() + "=").length()) : sql);
				for (DataRow dr : dt.Rows) {
					GEDtl gedtl = (GEDtl) ((gedtls.getGetNewEntity() instanceof GEDtl) ? gedtls.getGetNewEntity()
							: null);
					for (DataColumn dc : dt.Columns) {
						gedtl.SetValByKey(dc.ColumnName, dr.getValue(dc.ColumnName).toString());
					}

					gedtl.setRefPK(en.getPKVal().toString());
					gedtl.setRDT(DataType.getCurrentDataTime());
					gedtl.setRec(WebUser.getNo());
					gedtl.Insert();
				}
			}
		}
		return en;
	}

	/**
	 * 处理表达式
	 * 
	 * @param exp
	 *            表达式
	 * @param en
	 *            数据源
	 * @param errInfo
	 *            错误
	 * @return
	 */
	public static String DealExp(String exp, Entity en, String errInfo) {
		exp = exp.replace("~", "'");

		// 首先替换加; 的。
		exp = exp.replace("@WebUser.No;", WebUser.getNo());
		exp = exp.replace("@WebUser.Name;", WebUser.getName());
		exp = exp.replace("@WebUser.FK_Dept;", WebUser.getFK_Dept() == null ? "" : WebUser.getFK_Dept());
		exp = exp.replace("@WebUser.FK_DeptName;", WebUser.getFK_DeptName() == null ? "" : WebUser.getFK_DeptName());

		// 替换没有 ; 的 .
		exp = exp.replace("@WebUser.No", WebUser.getNo());
		exp = exp.replace("@WebUser.Name", WebUser.getName());
		exp = exp.replace("@WebUser.FK_Dept", WebUser.getFK_Dept() == null ? "" : WebUser.getFK_Dept());
		exp = exp.replace("@WebUser.FK_DeptName", WebUser.getFK_DeptName() == null ? "" : WebUser.getFK_DeptName());

		if (exp.contains("@") == false) {
			exp = exp.replace("~", "'");
			return exp;
		}

		// 增加对新规则的支持. @MyField; 格式.
		Attrs attrs = en.getEnMap().getAttrs();
		for (Attr item : attrs) {
			if (exp.contains("@" + item.getKey() + ";")) {
				exp = exp.replace("@" + item.getKey() + ";", en.GetValStrByKey(item.getKey()));
			}
		}
		if (exp.contains("@") == false) {
			return exp;
		}

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 解决排序问题.
		// Attrs attrs = en.EnMap.Attrs;
		String mystrs = "";
		for (Attr attr : attrs) {
			if (attr.getMyDataType() == DataType.AppString) {
				mystrs += "@" + attr.getKey() + ",";
			} else {
				mystrs += "@" + attr.getKey();
			}
		}
		String[] strs = mystrs.split("[@]", -1);
		DataTable dt = new DataTable();
		dt.Columns.Add(new DataColumn("No", String.class));
		for (String str : strs) {
			if (StringHelper.isNullOrEmpty(str)) {
				continue;
			}

			DataRow dr = dt.NewRow();
			dr.setValue(0, str);

			dt.Rows.add(dr);
		}
		// DataView dv = dt.DefaultView;
		// dv.Sort = "No DESC";
		// DataTable dtNew = dv.Table;
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 解决排序问题.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 替换变量.
		for (DataRow dr : dt.Rows) {
			Object keyObj = dr.getValue(0);
			if (keyObj == null)
				continue;
			String key = (String) keyObj;
			// String key = dr[0].toString();
			boolean isStr = key.contains(",");
			if (isStr == true) {
				key = key.replace(",", "");
				exp = exp.replace("@" + key, en.GetValStrByKey(key));
			} else {
				exp = exp.replace("@" + key, en.GetValStrByKey(key));
			}
		}

		// 处理Para的替换.
		if (exp.contains("@") && Glo.SendHTOfTemp != null) {
			for (String key : Glo.SendHTOfTemp.keySet()) {
				exp = exp.replace("@" + key, Glo.SendHTOfTemp.get(key).toString());
			}
		}

		//
		// if (exp.contains("@") && SystemConfig.getIsBSsystem() == true)
		// {
		// //如果是bs
		// for (String key :
		// System.Web.HttpContext.Current.Request.QueryString.keySet())
		// {
		// exp = exp.replace("@" + key,
		// System.Web.HttpContext.Current.Request.QueryString[key]);
		// }
		// }
		if (exp.contains("@") && SystemConfig.getIsBSsystem()) {
			Log.DefaultLogWriteLineError(exp);
			// throw new Exception("@ccflow的(" + errInfo +
			// ")表达式错误，一些字段没有替换下来，请确认这些字段是否被删除:" + exp);
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion

		exp = exp.replace("~", "'");
		// exp = exp.Replace("''", "'");
		// exp = exp.Replace("''", "'");
		// exp = exp.Replace("=' ", "=''");
		// exp = exp.Replace("= ' ", "=''");
		return exp;
	}

	/**
	 * 加密MD5
	 * 
	 * @param s
	 * @return
	 */
	public static String GenerMD5(Work wk) {
		String s = null;
		for (Attr attr : wk.getEnMap().getAttrs()) {
			// switch (attr.getKey())
			// {
			// case WorkAttr.MD5:
			// case WorkAttr.RDT:
			// case WorkAttr.CDT:
			// case WorkAttr.Rec:
			// case StartWorkAttr.Title:
			// case StartWorkAttr.Emps:
			// case StartWorkAttr.FK_Dept:
			// case StartWorkAttr.PRI:
			// case StartWorkAttr.FID:
			// continue;
			// default:
			// break;
			// }
			if (attr.getKey() == WorkAttr.MD5) {

			} else if (attr.getKey() == WorkAttr.RDT) {

			} else if (attr.getKey() == WorkAttr.CDT) {

			} else if (attr.getKey() == WorkAttr.Rec) {

			} else if (attr.getKey() == StartWorkAttr.Title) {

			} else if (attr.getKey() == StartWorkAttr.Emps) {

			} else if (attr.getKey() == StartWorkAttr.FK_Dept) {

			} else if (attr.getKey() == StartWorkAttr.PRI) {

			} else if (attr.getKey() == StartWorkAttr.FID) {
				continue;
			}

			String obj = (String) ((attr.getDefaultVal() instanceof String) ? attr.getDefaultVal() : null);
			// if (obj == null)
			// continue;
			if (obj != null && obj.contains("@")) {
				continue;
			}

			s += wk.GetValStrByKey(attr.getKey());
		}
		s += "ccflow";
		return DigestUtils.md5Hex(s).toLowerCase();
		// return
		// System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(s,
		// "MD5").toLowerCase();
	}

	/**
	 * 装载流程数据
	 * 
	 * @param xlsFile
	 */
	public static String LoadFlowDataWithToSpecNode(String xlsFile) {
		DataTable dt = BP.DA.DBLoad.GetTableByExt(xlsFile);
		String err = "";
		String info = "";

		for (DataRow dr : dt.Rows) {
			String flowPK = dr.getValue("FlowPK").toString();
			String starter = dr.getValue("Starter").toString();
			String executer = dr.getValue("Executer").toString();
			int toNode = Integer.parseInt(dr.getValue("ToNodeID").toString().replace("ND", ""));
			Node nd = new Node();
			nd.setNodeID(toNode);
			if (nd.RetrieveFromDBSources() == 0) {
				err += "节点ID错误:" + toNode;
				continue;
			}
			String sql = "SELECT count(*) as Num FROM ND" + Integer.parseInt(nd.getFK_Flow()) + "01 WHERE FlowPK='"
					+ flowPK + "'";
			int i = DBAccess.RunSQLReturnValInt(sql);
			if (i == 1) {
				continue; // 此数据已经调度了。
			}

			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #region 检查数据是否完整。
			BP.Port.Emp emp = new BP.Port.Emp();
			emp.setNo(executer);
			if (emp.RetrieveFromDBSources() == 0) {
				err += "@账号:" + starter + ",不存在。";
				continue;
			}
			if (StringHelper.isNullOrEmpty(emp.getFK_Dept())) {
				err += "@账号:" + starter + ",没有部门。";
				continue;
			}

			emp.setNo(starter);
			if (emp.RetrieveFromDBSources() == 0) {
				err += "@账号:" + executer + ",不存在。";
				continue;
			}
			if (StringHelper.isNullOrEmpty(emp.getFK_Dept())) {
				err += "@账号:" + executer + ",没有部门。";
				continue;
			}
			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #endregion 检查数据是否完整。

			WebUser.SignInOfGener(emp);
			Flow fl = nd.getHisFlow();
			Work wk = fl.NewWork();

			Attrs attrs = wk.getEnMap().getAttrs();
			// foreach (Attr attr in wk.EnMap.Attrs)
			// {
			// }

			for (DataColumn dc : dt.Columns) {
				Attr attr = attrs.GetAttrByKey(dc.ColumnName.trim());
				if (attr == null) {
					continue;
				}

				String val = dr.getValue(dc.ColumnName).toString().trim();
				switch (attr.getMyDataType()) {
				case DataType.AppString:
				case DataType.AppDate:
				case DataType.AppDateTime:
					wk.SetValByKey(attr.getKey(), val);
					break;
				case DataType.AppInt:
				case DataType.AppBoolean:
					wk.SetValByKey(attr.getKey(), Integer.parseInt(val));
					break;
				case DataType.AppMoney:
				case DataType.AppDouble:
				case DataType.AppRate:
				case DataType.AppFloat:
					wk.SetValByKey(attr.getKey(), Float.parseFloat(val));
					break;
				default:
					wk.SetValByKey(attr.getKey(), val);
					break;
				}
			}

			wk.SetValByKey(WorkAttr.Rec, WebUser.getNo());
			wk.SetValByKey(StartWorkAttr.FK_Dept, WebUser.getFK_Dept());
			wk.SetValByKey("FK_NY", DataType.getCurrentYearMonth());
			wk.SetValByKey(WorkAttr.MyNum, 1);
			wk.Update();

			Node ndStart = nd.getHisFlow().getHisStartNode();
			WorkNode wn = new WorkNode(wk, ndStart);
			try {
				info += "<hr>" + wn.NodeSend(nd, executer).ToMsgOfHtml();
			} catch (RuntimeException ex) {
				err += "<hr>" + ex.getMessage();
				WorkFlow wf = new WorkFlow(fl, wk.getOID());
				wf.DoDeleteWorkFlowByReal(true);
				continue;
			}

			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #region 更新 下一个节点数据。
			Work wkNext = nd.getHisWork();
			wkNext.setOID(wk.getOID());
			wkNext.RetrieveFromDBSources();
			attrs = wkNext.getEnMap().getAttrs();
			for (DataColumn dc : dt.Columns) {
				Attr attr = attrs.GetAttrByKey(dc.ColumnName.trim());
				if (attr == null) {
					continue;
				}

				String val = dr.getValue(dc.ColumnName).toString().trim();
				switch (attr.getMyDataType()) {
				case DataType.AppString:
				case DataType.AppDate:
				case DataType.AppDateTime:
					wkNext.SetValByKey(attr.getKey(), val);
					break;
				case DataType.AppInt:
				case DataType.AppBoolean:
					wkNext.SetValByKey(attr.getKey(), Integer.parseInt(val));
					break;
				case DataType.AppMoney:
				case DataType.AppDouble:
				case DataType.AppRate:
				case DataType.AppFloat:
					wkNext.SetValByKey(attr.getKey(), Float.parseFloat(val));
					break;
				default:
					wkNext.SetValByKey(attr.getKey(), val);
					break;
				}
			}

			wkNext.DirectUpdate();

			GERpt rtp = fl.getHisGERpt();
			rtp.SetValByKey("OID", wkNext.getOID());
			rtp.RetrieveFromDBSources();
			rtp.Copy(wkNext);
			rtp.DirectUpdate();

			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #endregion 更新 下一个节点数据。
		}
		return info + err;
	}

	public static String LoadFlowDataWithToSpecEndNode(String xlsFile) {
		DataTable dt = BP.DA.DBLoad.GetTableByExt(xlsFile);
		DataSet ds = new DataSet();
		ds.Tables.add(dt);
		ds.WriteXml("C:" + File.separator + "已完成.xml");

		String err = "";
		String info = "";
		int idx = 0;
		for (DataRow dr : dt.Rows) {
			String flowPK = dr.getValue("FlowPK").toString().trim();
			if (StringHelper.isNullOrEmpty(flowPK)) {
				continue;
			}

			String starter = dr.getValue("Starter").toString();
			String executer = dr.getValue("Executer").toString();
			int toNode = Integer.parseInt(dr.getValue("ToNodeID").toString().replace("ND", ""));
			Node ndOfEnd = new Node();
			ndOfEnd.setNodeID(toNode);
			if (ndOfEnd.RetrieveFromDBSources() == 0) {
				err += "节点ID错误:" + toNode;
				continue;
			}

			if (ndOfEnd.getIsEndNode() == false) {
				err += "节点ID错误:" + toNode + ", 非结束节点。";
				continue;
			}

			String sql = "SELECT count(*) as Num FROM ND" + Integer.parseInt(ndOfEnd.getFK_Flow()) + "01 WHERE FlowPK='"
					+ flowPK + "'";
			int i = DBAccess.RunSQLReturnValInt(sql);
			if (i == 1) {
				continue; // 此数据已经调度了。
			}

			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #region 检查数据是否完整。
			// 发起人发起。
			BP.Port.Emp emp = new BP.Port.Emp();
			emp.setNo(executer);
			if (emp.RetrieveFromDBSources() == 0) {
				err += "@账号:" + starter + ",不存在。";
				continue;
			}

			if (StringHelper.isNullOrEmpty(emp.getFK_Dept())) {
				err += "@账号:" + starter + ",没有设置部门。";
				continue;
			}

			emp = new BP.Port.Emp();
			emp.setNo(starter);
			if (emp.RetrieveFromDBSources() == 0) {
				err += "@账号:" + starter + ",不存在。";
				continue;
			} else {
				emp.RetrieveFromDBSources();
				if (StringHelper.isNullOrEmpty(emp.getFK_Dept())) {
					err += "@账号:" + starter + ",没有设置部门。";
					continue;
				}
			}
			// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			/// #endregion 检查数据是否完整。

			WebUser.SignInOfGener(emp);
			Flow fl = ndOfEnd.getHisFlow();
			Work wk = fl.NewWork();
			for (DataColumn dc : dt.Columns) {
				wk.SetValByKey(dc.ColumnName.trim(), dr.getValue(dc.ColumnName).toString().trim());
			}

			wk.SetValByKey(WorkAttr.Rec, WebUser.getNo());
			wk.SetValByKey(StartWorkAttr.FK_Dept, WebUser.getFK_Dept());
			wk.SetValByKey("FK_NY", DataType.getCurrentYearMonth());
			wk.SetValByKey(WorkAttr.MyNum, 1);
			wk.Update();

			Node ndStart = fl.getHisStartNode();
			WorkNode wn = new WorkNode(wk, ndStart);
			try {
				info += "<hr>" + wn.NodeSend(ndOfEnd, executer).ToMsgOfHtml();
			} catch (RuntimeException ex) {
				err += "<hr>启动错误:" + ex.getMessage();
				DBAccess.RunSQL(
						"DELETE FROM ND" + Integer.parseInt(ndOfEnd.getFK_Flow()) + "01 WHERE FlowPK='" + flowPK + "'");
				WorkFlow wf = new WorkFlow(fl, wk.getOID());
				wf.DoDeleteWorkFlowByReal(true);
				continue;
			}

			// 结束点结束。
			emp = new BP.Port.Emp(executer);
			WebUser.SignInOfGener(emp);

			Work wkEnd = ndOfEnd.GetWork(wk.getOID());
			for (DataColumn dc : dt.Columns) {
				wkEnd.SetValByKey(dc.ColumnName.trim(), dr.getValue(dc.ColumnName).toString().trim());
			}

			wkEnd.SetValByKey(WorkAttr.Rec, WebUser.getNo());
			wkEnd.SetValByKey(StartWorkAttr.FK_Dept, WebUser.getFK_Dept());
			wkEnd.SetValByKey("FK_NY", DataType.getCurrentYearMonth());
			wkEnd.SetValByKey(WorkAttr.MyNum, 1);
			wkEnd.Update();

			try {
				WorkNode wnEnd = new WorkNode(wkEnd, ndOfEnd);
				// wnEnd.AfterNodeSave();
				info += "<hr>" + wnEnd.NodeSend().ToMsgOfHtml();
			} catch (RuntimeException ex) {
				err += "<hr>结束错误(系统直接删除它):" + ex.getMessage();
				WorkFlow wf = new WorkFlow(fl, wk.getOID());
				wf.DoDeleteWorkFlowByReal(true);
				continue;
			}
		}
		return info + err;
	}

	/**
	 * 判断是否登陆当前UserNo
	 * 
	 * @param userNo
	 */
	public static void IsSingleUser(String userNo) {
		if (StringHelper.isNullOrEmpty(WebUser.getNo()) || !userNo.equals(WebUser.getNo())) {
			if (!StringHelper.isNullOrEmpty(userNo)) {
				BP.WF.Dev2Interface.Port_Login(userNo);
			}
		}
	}
	// public static void ResetFlowView()
	// {
	// string sql = "DROP VIEW V_WF_Data ";
	// try
	// {
	// BP.DA.DBAccess.RunSQL(sql);
	// }
	// catch
	// {
	// }

	// Flows fls = new Flows();
	// fls.RetrieveAll();
	// sql = "CREATE VIEW V_WF_Data AS ";
	// foreach (Flow fl in fls)
	// {
	// fl.CheckRpt();
	// sql += "\t\n SELECT '" + fl.No + "' as FK_Flow, '" + fl.Name + "' AS
	// FlowName, '" + fl.FK_FlowSort + "' as
	// FK_FlowSort,CDT,Emps,FID,FK_Dept,FK_NY,";
	// sql += "MyNum,OID,RDT,Rec,Title,WFState,FlowEmps,";
	// sql += "FlowStarter,FlowStartRDT,FlowEnder,FlowEnderRDT,FlowDaySpan FROM
	// ND" + int.Parse(fl.No) + "Rpt";
	// sql += "\t\n UNION";
	// }
	// sql = sql.Substring(0, sql.Length - 6);
	// sql += "\t\n GO";
	// BP.DA.DBAccess.RunSQL(sql);
	// }
	public static void Rtf2PDF(Object pathOfRtf, Object pathOfPDF) {
		// Object Nothing = System.Reflection.Missing.Value;
		// //创建一个名为WordApp的组件对象
		// Microsoft.Office.Interop.Word.Application wordApp =
		// new Microsoft.Office.Interop.Word.ApplicationClass();
		// //创建一个名为WordDoc的文档对象并打开
		// Microsoft.Office.Interop.Word.Document doc =
		// wordApp.Documents.Open(ref pathOfRtf, ref Nothing, ref Nothing, ref
		// Nothing, ref Nothing,
		// ref Nothing, ref Nothing, ref Nothing, ref Nothing, ref Nothing,
		// ref Nothing, ref Nothing, ref Nothing, ref Nothing, ref Nothing, ref
		// Nothing);

		// //设置保存的格式
		// object filefarmat =
		// Microsoft.Office.Interop.Word.WdSaveFormat.wdFormatPDF;

		// //保存为PDF
		// doc.SaveAs(ref pathOfPDF, ref filefarmat, ref Nothing, ref Nothing,
		// ref Nothing, ref Nothing,
		// ref Nothing, ref Nothing, ref Nothing, ref Nothing, ref Nothing, ref
		// Nothing, ref Nothing,
		// ref Nothing, ref Nothing, ref Nothing);
		// //关闭文档对象
		// doc.Close(ref Nothing, ref Nothing, ref Nothing);
		// //推出组建
		// wordApp.Quit(ref Nothing, ref Nothing, ref Nothing);
		// GC.Collect();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 常用方法

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 属性
	/**
	 * 消息
	 * 
	 */
	public static String getSessionMsg() {
		Paras p = new Paras();
		p.SQL = "SELECT Msg FROM WF_Emp where No=" + SystemConfig.getAppCenterDBVarStr() + "FK_Emp";
		p.AddFK_Emp();
		return DBAccess.RunSQLReturnString(p);

		// string SQL = "SELECT Msg FROM WF_Emp where
		// No='"+BP.Web.WebUser.No+"'";
		// return DBAccess.RunSQLReturnString(SQL);
	}

	public static void setSessionMsg(String value) {
		if (StringHelper.isNullOrEmpty(value) == true) {
			return;
		}

		Paras p = new Paras();
		p.SQL = "UPDATE WF_Emp SET Msg=" + SystemConfig.getAppCenterDBVarStr() + "v WHERE No="
				+ SystemConfig.getAppCenterDBVarStr() + "FK_Emp";
		p.Add("v", value);
		p.AddFK_Emp();

		int i = DBAccess.RunSQL(p);
		if (i == 0) {
			// 如果没有更新到.
			BP.WF.Port.WFEmp emp = new WFEmp();
			emp.setNo(WebUser.getNo());
			emp.setName(WebUser.getName());
			emp.setFK_Dept(WebUser.getFK_Dept());
			emp.Insert();
			DBAccess.RunSQL(p);
		}

		// string SQL = "UPDATE WF_Emp SET Msg='" + value + "' WHERE No='" +
		// BP.Web.WebUser.No + "'";
		// DBAccess.RunSQL(SQL);
	}

	private static String _FromPageType = null;

	public static String getFromPageType() {
		_FromPageType = null;
		if (_FromPageType == null) {
			try {
				String url = BP.Sys.Glo.getRequest().getRequestURL().toString();
				int i = url.lastIndexOf("/") + 1;
				int i2 = url.indexOf(".jsp") - 6;

				url = url.substring(i);
				url = url.substring(0, url.indexOf(".jsp"));
				_FromPageType = url;
				if (_FromPageType.contains("SmallSingle")) {
					_FromPageType = "SmallSingle";
				} else if (_FromPageType.contains("Small")) {
					_FromPageType = "Small";
				} else {
					_FromPageType = "";
				}
			} catch (RuntimeException ex) {
				_FromPageType = "";
				// throw new Exception(ex.Message + url + " i=" + i + " i2=" +
				// i2);
			}
		}
		return _FromPageType;
	}

	/**
	 * 临时的发送传输变量.
	 * 
	 */
	public static java.util.Hashtable<String, Object> SendHTOfTemp = null;
	/**
	 * 报表属性集合
	 * 
	 */
	private static Attrs _AttrsOfRpt = null;

	/**
	 * 报表属性集合
	 * 
	 */
	public static Attrs getAttrsOfRpt() {
		if (_AttrsOfRpt == null) {
			_AttrsOfRpt = new Attrs();
			_AttrsOfRpt.AddTBInt(GERptAttr.OID, 0, "WorkID", true, true);
			_AttrsOfRpt.AddTBInt(GERptAttr.FID, 0, "FlowID", false, false);

			_AttrsOfRpt.AddTBString(GERptAttr.Title, null, "标题", true, false, 0, 10, 10);
			_AttrsOfRpt.AddTBString(GERptAttr.FlowStarter, null, "发起人", true, false, 0, 10, 10);
			_AttrsOfRpt.AddTBString(GERptAttr.FlowStartRDT, null, "发起时间", true, false, 0, 10, 10);
			_AttrsOfRpt.AddTBString(GERptAttr.WFState, null, "状态", true, false, 0, 10, 10);

			// Attr attr = new Attr();
			// attr.Desc = "流程状态";
			// attr.Key = "WFState";
			// attr.MyFieldType = FieldType.Enum;
			// attr.UIBindKey = "WFState";
			// attr.UITag = "@0=进行中@1=已经完成";

			_AttrsOfRpt.AddDDLSysEnum(GERptAttr.WFState, 0, "流程状态", true, true, GERptAttr.WFState);
			_AttrsOfRpt.AddTBString(GERptAttr.FlowEmps, null, "参与人", true, false, 0, 10, 10);
			_AttrsOfRpt.AddTBString(GERptAttr.FlowEnder, null, "结束人", true, false, 0, 10, 10);
			_AttrsOfRpt.AddTBString(GERptAttr.FlowEnderRDT, null, "结束时间", true, false, 0, 10, 10);
			// _AttrsOfRpt.AddTBDecimal(GERptAttr.FlowEndNode, 0, "结束节点", true,
			// false);
			// _AttrsOfRpt.AddTBDecimal(GERptAttr.FlowDaySpan, 0, "跨度(天)", true,
			// false);
			_AttrsOfRpt.AddTBDecimal(GERptAttr.FlowEndNode, new java.math.BigDecimal(0), "结束节点", true, false);
			_AttrsOfRpt.AddTBDecimal(GERptAttr.FlowDaySpan, new java.math.BigDecimal(0), "跨度(天)", true, false);
			// _AttrsOfRpt.AddTBString(GERptAttr.FK_NY, null, "隶属月份", true,
			// false, 0, 10, 10);
		}
		return _AttrsOfRpt;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 属性

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 其他配置.
	public static String GenerHelp(String helpId) {
		return "";
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (helpId)
		// ORIGINAL LINE: case "Bill":
		// if (helpId.equals("Bill"))
		// {
		// return "<a href=\"http://ccFlow.org\" target=_blank><img
		// src='../../WF/Img/FileType/rm.gif' border=0/>操作录像</a>";
		// }
		// ORIGINAL LINE: case "FAppSet":
		// else if (helpId.equals("FAppSet"))
		// {
		// return "<a href=\"http://ccFlow.org\" target=_blank><img
		// src='../../WF/Img/FileType/rm.gif' border=0/>操作录像</a>";
		// }
		// else
		// {
		// return "<a href=\"http://ccFlow.org\" target=_blank><img
		// src='../../WF/Img/FileType/rm.gif' border=0/>操作录像</a>";
		// }
	}

	public static String getNodeImagePath() {
		return Glo.getIntallPath() + File.separator + "Data" + File.separator + "Node" + File.separator;
	}

	public static void ClearDBData() {
		String sql = "DELETE FROM WF_GenerWorkFlow WHERE fk_flow not in (select no from wf_flow )";
		BP.DA.DBAccess.RunSQL(sql);

		sql = "DELETE FROM WF_GenerWorkerlist WHERE fk_flow not in (select no from wf_flow )";
		BP.DA.DBAccess.RunSQL(sql);
	}

	public static String OEM_Flag = "CCS";

	public static String getFlowFileBill() {
		return Glo.getIntallPath() + File.separator + "DataUser" + File.separator + "Bill" + File.separator;
	}

	public static String getFlowFileTemp() {
		return Glo.getIntallPath() + File.separator + "Temp" + File.separator;
	}

	private static String _IntallPath = null;

	public static String getIntallPath() {
		if (_IntallPath == null) {
			// xiaozhoupeng 20150119 update 获取项目绝对路径 Start
			_IntallPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
			// _IntallPath = BP.WF.Glo.getCCFlowAppPath();
			// xiaozhoupeng 20150119 update 获取项目绝对路径 End

			// throw new Exception("@您没有 _IntallPath 赋值.");
		}
		if (_IntallPath == null) {
			throw new RuntimeException("@没有实现如何获得 cs 下的根目录.");
		}
		return _IntallPath;
	}

	public static void setIntallPath(String value) {
		_IntallPath = value;
	}

	private static String _ServerIP = null;

	public static String getServerIP() {
		if (_ServerIP == null) {
			String ip = "127.0.0.1";
			InetAddress[] addressList = null;
			try {
				addressList = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
			/*
			 * warning System.Net.IPAddress[] addressList = System.Net.Dns
			 * .GetHostByName(System.Net.Dns.GetHostName()).AddressList;
			 */
			if (addressList.length > 1) {
				_ServerIP = addressList[1].toString();
			} else {
				_ServerIP = addressList[0].toString();
			}
		}
		return _ServerIP;
	}

	public static void setServerIP(String value) {
		_ServerIP = value;
	}

	/**
	 * 流程控制器按钮
	 * 
	 */
	public static String getFlowCtrlBtnPos() {
		String s = (String) ((BP.Sys.SystemConfig.getAppSettings().get("FlowCtrlBtnPos") instanceof String)
				? BP.Sys.SystemConfig.getAppSettings().get("FlowCtrlBtnPos") : null);
		if (s == null || s.equals("Top")) {
			return "Top";
		}
		return "Bottom";
	}

	/**
	 * 全局的安全验证码
	 * 
	 */
	public static String getGloSID() {
		String s = (String) ((BP.Sys.SystemConfig.getAppSettings().get("GloSID") instanceof String)
				? BP.Sys.SystemConfig.getAppSettings().get("GloSID") : null);
		if (s == null || s.equals("")) {
			s = "sdfq2erre-2342-234sdf23423-323";
		}
		return s;
	}

	/**
	 * 是否启用检查用户的状态? 如果启用了:在MyFlow.jsp中每次都会检查当前的用户状态是否被禁
	 * 用，如果禁用了就不能执行任何操作了。启用后，就意味着每次都要 访问数据库。
	 * 
	 */
	public static boolean getIsEnableCheckUseSta() {
		String s = (String) ((BP.Sys.SystemConfig.getAppSettings().get("IsEnableCheckUseSta") instanceof String)
				? BP.Sys.SystemConfig.getAppSettings().get("IsEnableCheckUseSta") : null);
		if (s == null || s.equals("0")) {
			return false;
		}
		return true;
	}

	/**
	 * 是否启用显示节点名称
	 * 
	 */
	public static boolean getIsEnableMyNodeName() {
		String s = (String) ((BP.Sys.SystemConfig.getAppSettings().get("IsEnableMyNodeName") instanceof String)
				? BP.Sys.SystemConfig.getAppSettings().get("IsEnableMyNodeName") : null);
		if (s == null || s.equals("0")) {
			return false;
		}
		return true;
	}

	/**
	 * 检查一下当前的用户是否仍旧有效使用？
	 * 
	 * @return
	 */
	public static boolean CheckIsEnableWFEmp() throws Exception {
		Paras ps = new Paras();
		ps.SQL = "SELECT UseSta FROM WF_Emp WHERE No=" + SystemConfig.getAppCenterDBVarStr() + "FK_Emp";
		ps.AddFK_Emp();

		String s = DBAccess.RunSQLReturnStringIsNull(ps, "1");
		if (s.equals("1") || s == null) {
			return true;
		}
		return false;
	}

	/**
	 * 语言
	 * 
	 */
	public static String Language = "CH";

	public static boolean getIsQL() {

		String s = BP.Sys.SystemConfig.getAppSettings().get("IsQL").toString();
		if (s == null || s.equals("0")) {
			return false;
		}
		return true;
	}

	/**
	 * 是否启用共享任务池？
	 * 
	 */
	public static boolean getIsEnableTaskPool() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsEnableTaskPool", false);
	}

	/**
	 * 是否显示标题
	 * 
	 */
	public static boolean getIsShowTitle() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsShowTitle", false);
	}

	/**
	 * 是否为工作增加一个优先级
	 * 
	 */
	public static boolean getIsEnablePRI() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsEnablePRI", false);
	}

	/**
	 * 用户信息显示格式
	 * 
	 */
	public static UserInfoShowModel getUserInfoShowModel() {
		return UserInfoShowModel.forValue(BP.Sys.SystemConfig.GetValByKeyInt("UserInfoShowModel", 0));
	}

	/**
	 * 产生用户数字签名
	 * 
	 * @return
	 */
	public static String GenerUserSigantureHtml(String serverPath, String userNo, String userName) {
		String siganturePath = serverPath + "/" + getCCFlowAppPath() + "DataUser/Siganture/" + userNo + ".jpg";
		/*
		 * warning if (System.IO.File.Exists(siganturePath)) {
		 */
		if (new File(siganturePath).exists()) {
			return "<img src='" + getCCFlowAppPath() + "DataUser/Siganture/" + userNo
					+ ".jpg' width='90' height='30' title='" + userName + "' border=0 onerror=\"src='"
					+ getCCFlowAppPath() + "DataUser/UserIcon/DefaultSmaller.png'\" />";
		}
		return "<img src='" + getCCFlowAppPath() + "DataUser/UserIcon/" + userNo
				+ "Smaller.png' border=0 width='24px' onerror=\"src='" + getCCFlowAppPath()
				+ "DataUser/UserIcon/DefaultSmaller.png'\" />" + userName;
	}

	/**
	 * 产生用户小图片
	 * 
	 * @return
	 */
	public static String GenerUserImgSmallerHtml(String userNo, String userName) {
		return "<img src='" + getCCFlowAppPath() + "DataUser/UserIcon/" + userNo
				+ "Smaller.png' border=0 width='24px' height='24px' style='padding-right:5px;' align='middle' onerror=\"src='"
				+ getCCFlowAppPath() + "DataUser/UserIcon/DefaultSmaller.png'\" />" + userName;
	}

	/**
	 * 更新主表的SQL
	 * 
	 */
	public static String getUpdataMainDeptSQL() {
		return BP.Sys.SystemConfig.GetValByKey("UpdataMainDeptSQL",
				"UPDATE Port_Emp SET FK_Dept=" + BP.Sys.SystemConfig.getAppCenterDBVarStr() + "FK_Dept WHERE No="
						+ BP.Sys.SystemConfig.getAppCenterDBVarStr() + "No");
	}

	/**
	 * 更新SID的SQL
	 * 
	 */
	public static String getUpdataSID() {
		return BP.Sys.SystemConfig.GetValByKey("UpdataSID",
				"UPDATE Port_Emp SET SID=" + BP.Sys.SystemConfig.getAppCenterDBVarStr() + "SID WHERE No="
						+ BP.Sys.SystemConfig.getAppCenterDBVarStr() + "No");
	}

	/**
	 * 下载sl的地址
	 * 
	 */
	public static String getSilverlightDownloadUrl() {
		return BP.Sys.SystemConfig.GetValByKey("SilverlightDownloadUrl",
				"http://go.microsoft.com/fwlink/?LinkID=124807");
	}

	/**
	 * 处理显示格式
	 * 
	 * @param no
	 * @param name
	 * @return 现实格式
	 */
	public static String DealUserInfoShowModel(String no, String name) {
		switch (BP.WF.Glo.getUserInfoShowModel()) {
		case UserIDOnly:
			return "(" + no + ")";
		case UserIDUserName:
			return "(" + no + "," + name + ")";
		case UserNameOnly:
			return "(" + name + ")";
		default:
			throw new RuntimeException("@没有判断的格式类型.");

		}
	}

	/**
	 * 运行模式
	 * 
	 */
	public static OSModel getOSModel() {
		OSModel os = OSModel.forValue(BP.Sys.SystemConfig.GetValByKeyInt("OSModel", 0));
		// OSModel os = (OSModel)BP.Sys.SystemConfig.GetValByKeyInt("OSModel",
		// 0);
		return os;
	}

	/**
	 * 是否是集团使用
	 * 
	 */
	public static boolean getIsUnit() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsUnit", false);
	}

	/**
	 * 是否启用制度
	 * 
	 */
	public static boolean getIsEnableZhiDu() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsEnableZhiDu", false);
	}

	/**
	 * 是否删除流程注册表数据？
	 * 
	 */
	public static boolean getIsDeleteGenerWorkFlow() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsDeleteGenerWorkFlow", false);
	}

	/**
	 * 是否检查表单树字段填写是否为空
	 * 
	 */
	public static boolean getIsEnableCheckFrmTreeIsNull() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsEnableCheckFrmTreeIsNull", true);
	}

	/**
	 * 是否启动工作时打开新窗口
	 * 
	 */
	public static int getIsWinOpenStartWork() {
		return BP.Sys.SystemConfig.GetValByKeyInt("IsWinOpenStartWork", 1);
	}

	/**
	 * 是否打开待办工作时打开窗口
	 * 
	 */
	public static boolean getIsWinOpenEmpWorks() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsWinOpenEmpWorks", true);
	}

	/**
	 * 是否启用消息系统消息。
	 * 
	 */
	public static boolean getIsEnableSysMessage() {
		return BP.Sys.SystemConfig.GetValByKeyBoolen("IsEnableSysMessage", true);
	}

	/**
	 * 与ccflow流程服务相关的配置: 执行自动任务节点，间隔的时间，以分钟计算，默认为2分钟。
	 * 
	 */
	public static int getAutoNodeDTSTimeSpanMinutes() {
		return BP.Sys.SystemConfig.GetValByKeyInt("AutoNodeDTSTimeSpanMinutes", 2);
	}

	/**
	 * ccim集成的数据库. 是为了向ccim写入消息.
	 * 
	 */
	public static String getCCIMDBName() {
		String baseUrl = BP.Sys.SystemConfig.GetValByKey("CCIMDBName", null);
		/*
		 * warning String baseUrl =
		 * BP.Sys.SystemConfig.AppSettings["CCIMDBName"];
		 */
		if (StringHelper.isNullOrEmpty(baseUrl)) {
			baseUrl = "ccPort.dbo";
		}
		return baseUrl;
	}

	/**
	 * 主机
	 * 
	 */
	public static String getHostURL() {
		String baseUrl = BP.Sys.SystemConfig.GetValByKey("HostURL", null);
		/*
		 * warning String baseUrl = BP.Sys.SystemConfig.AppSettings["HostURL"];
		 */
		if (StringHelper.isNullOrEmpty(baseUrl)) {
			baseUrl = BP.Sys.SystemConfig.GetValByKey("BaseURL", null);
			/*
			 * warning baseUrl = BP.Sys.SystemConfig.AppSettings["BaseURL"];
			 */
		}

		if (StringHelper.isNullOrEmpty(baseUrl)) {
			baseUrl = "http://127.0.0.1/";
		}

		if (!baseUrl.substring(baseUrl.length() - 1).equals("/")) {
			baseUrl = baseUrl + "/";
		}
		return baseUrl;
	}

	public static String getCurrPageID() {
		try {
			String url = ContextHolderUtils.getRequest().getRequestURI();
			/*
			 * warning String url =
			 * System.Web.HttpContext.Current.Request.RawUrl;
			 */

			int i = url.lastIndexOf("/") + 1;
			int i2 = url.indexOf(".jsp") - 5;
			try {
				url = url.substring(i);
				return url.substring(0, url.indexOf(".jsp"));

			} catch (RuntimeException ex) {
				throw new RuntimeException(ex.getMessage() + url + " i=" + i + " i2=" + i2);
			}
		} catch (RuntimeException ex) {
			throw new RuntimeException("获取当前PageID错误:" + ex.getMessage());
		}
	}

	/**
	 * 转到消息显示界面.
	 * 
	 * @param info
	 */
	public static void ToMsg(String info, HttpServletResponse response) {
		// string rowUrl = System.Web.HttpContext.Current.Request.RawUrl;
		// if (rowUrl.Contains("&IsClient=1"))
		// {
		// /*说明这是vsto调用的.*/
		// return;
		// }

		ContextHolderUtils.getSession().setAttribute("info", info);
		try {
			response.sendRedirect(Glo.getCCFlowAppPath() + "WF/MyFlowInfo.jsp?Msg=" + info);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * warning
		 * System.Web.HttpContext.Current.Response.Redirect(Glo.getCCFlowAppPath
		 * () + "WF/MyFlowInfo.jsp?Msg=" + info, false);
		 */
	}

	// 用户表单风格控制
	public static String getGetUserStyle() {
		// BP.WF.Port.WFEmp emp = new Port.WFEmp(WebUser.No);
		// if(string.IsNullOrEmpty(emp.Style) || emp.Style=="0")
		// {
		String userStyle = BP.Sys.SystemConfig.GetValByKey("UserStyle", null);
		if (StringHelper.isNullOrEmpty(userStyle)) {
			return "ccflow默认";
		} else {
			return userStyle;
		}
		// }
		// else
		// return emp.Style;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 其他配置.

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 其他方法。
	public static void ToMsgErr(String info) {
		info = "<font color=red>" + info + "</font>";
		ContextHolderUtils.getSession().setAttribute("info", info);
		try {
			ContextHolderUtils.getResponse().sendRedirect(Glo.getCCFlowAppPath() + "WF/MyFlowInfo.jsp?Msg=" + info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查流程发起限制
	 * 
	 * @param flow
	 *            流程
	 * @param wk
	 *            开始节点工作
	 * @return
	 */
	public static boolean CheckIsCanStartFlow_InitStartFlow(Flow flow, Work wk) throws Exception {
		StartLimitRole role = flow.getStartLimitRole();
		if (role == StartLimitRole.None) {
			return true;
		}

		String sql = "";
		String ptable = flow.getPTable();

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #region 按照时间的必须是，在表单加载后判断, 不管用户设置是否正确.
		java.util.Date dtNow = new java.util.Date();
		if (role == StartLimitRole.Day) {
			// 仅允许一天发起一次
			sql = "SELECT COUNT(*) as Num FROM " + ptable + " WHERE RDT LIKE '" + DataType.getCurrentData()
					+ "%' AND WFState NOT IN(0,1) AND FlowStarter='" + WebUser.getNo() + "'";
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0) {
				if (flow.getStartLimitPara().equals("")) {
					return true;
				}

				// 判断时间是否在设置的发起范围内. 配置的格式为 @11:00-12:00@15:00-13:45
				String[] strs = flow.getStartLimitPara().split("[@]", -1);
				for (String str : strs) {
					if (StringHelper.isNullOrEmpty(str)) {
						continue;
					}
					String[] timeStrs = str.split("[-]", -1);
					String tFrom = DataType.dateToStr(new Date(), "yyyy-MM-dd") + " " + timeStrs[0].trim();
					String tTo = DataType.dateToStr(new Date(), "yyyy-MM-dd") + " " + timeStrs[1].trim();
					if (DataType.ParseSysDateTime2DateTime(tFrom).before(dtNow)
							&& dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo)) >= 0) {
						return true;
					}
					// String tFrom = new
					// java.util.Date().ToString("yyyy-MM-dd") + " " +
					// timeStrs[0].trim();
					// String tTo = new java.util.Date().ToString("yyyy-MM-dd")
					// + " " + timeStrs[1].trim();
					// if (DataType.ParseSysDateTime2DateTime(tFrom) <= dtNow &&
					// dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo))
					// >= 0)
					// {
					// return true;
					// }
				}
				return false;
			} else {
				return false;
			}
		}

		if (role == StartLimitRole.Week) {
			//
			// * 1, 找出周1 与周日分别是第几日.
			// * 2, 按照这个范围去查询,如果查询到结果，就说明已经启动了。
			//
			sql = "SELECT COUNT(*) as Num FROM " + ptable + " WHERE RDT >= '" + DataType.WeekOfMonday(dtNow)
					+ "' AND WFState NOT IN(0,1) AND FlowStarter='" + WebUser.getNo() + "'";
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0) {
				if (flow.getStartLimitPara().equals("")) {
					return true; // 如果没有时间的限制.
				}

				// 判断时间是否在设置的发起范围内.
				// 配置的格式为 @Sunday,11:00-12:00@Monday,15:00-13:45,
				// 意思是.周日，周一的指定的时间点范围内可以启动流程.

				String[] strs = flow.getStartLimitPara().split("[@]", -1);
				for (String str : strs) {
					if (StringHelper.isNullOrEmpty(str)) {
						continue;
					}

					String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
							"Saturday" };
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
					if (w < 0)
						w = 0;
					String weekStr = weekDays[w].toLowerCase();
					if (!str.toLowerCase().contains(weekStr)) {
						continue; // 判断是否当前的周.
					}

					String[] timeStrs = str.split("[,]", -1);
					String tFrom = DataType.dateToStr(new Date(), "yyyy-MM-dd") + " " + timeStrs[0].trim();
					String tTo = DataType.dateToStr(new Date(), "yyyy-MM-dd") + " " + timeStrs[1].trim();
					if (DataType.ParseSysDateTime2DateTime(tFrom).before(dtNow)
							&& dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo)) >= 0) {
						return true;
					}
					// String tFrom = new
					// java.util.Date().ToString("yyyy-MM-dd") + " " +
					// timeStrs[0].trim();
					// String tTo = new java.util.Date().ToString("yyyy-MM-dd")
					// + " " + timeStrs[1].trim();
					// if (DataType.ParseSysDateTime2DateTime(tFrom) <= dtNow &&
					// dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo))
					// >= 0)
					// {
					// return true;
					// }
				}
				return false;
			} else {
				return false;
			}
		}

		// #warning 没有考虑到周的如何处理.

		if (role == StartLimitRole.Month) {
			sql = "SELECT COUNT(*) as Num FROM " + ptable + " WHERE FK_NY = '" + DataType.getCurrentYearMonth()
					+ "' AND WFState NOT IN(0,1) AND FlowStarter='" + WebUser.getNo() + "'";
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0) {
				if (flow.getStartLimitPara().equals("")) {
					return true;
				}

				// 判断时间是否在设置的发起范围内. 配置格式: @-01 12:00-13:11@-15 12:00-13:11 ,
				// 意思是：在每月的1号,15号 12:00-13:11可以启动流程.
				String[] strs = flow.getStartLimitPara().split("[@]", -1);
				for (String str : strs) {
					if (StringHelper.isNullOrEmpty(str)) {
						continue;
					}
					String[] timeStrs = str.split("[-]", -1);
					String tFrom = DataType.dateToStr(new Date(), "yyyy-MM-") + " " + timeStrs[0].trim();
					String tTo = DataType.dateToStr(new Date(), "yyyy-MM-") + " " + timeStrs[1].trim();
					if (DataType.ParseSysDateTime2DateTime(tFrom).before(dtNow)
							&& dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo)) >= 0) {
						return true;
					}
					// String tFrom = new java.util.Date().ToString("yyyy-MM-")
					// + " " + timeStrs[0].trim();
					// String tTo = new java.util.Date().ToString("yyyy-MM-") +
					// " " + timeStrs[1].trim();
					// if (DataType.ParseSysDateTime2DateTime(tFrom) <= dtNow &&
					// dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo))
					// >= 0)
					// {
					// return true;
					// }
				}
				return false;
			} else {
				return false;
			}
		}

		if (role == StartLimitRole.JD) {
			sql = "SELECT COUNT(*) as Num FROM " + ptable + " WHERE FK_NY = '" + DataType.getCurrentAPOfJD()
					+ "' AND WFState NOT IN(0,1) AND FlowStarter='" + WebUser.getNo() + "'";
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0) {
				if (flow.getStartLimitPara().equals("")) {
					return true;
				}

				// 判断时间是否在设置的发起范围内.
				String[] strs = flow.getStartLimitPara().split("[@]", -1);
				for (String str : strs) {
					if (StringHelper.isNullOrEmpty(str)) {
						continue;
					}
					String[] timeStrs = str.split("[-]", -1);
					String tFrom = DataType.dateToStr(new Date(), "yyyy-") + " " + timeStrs[0].trim();
					String tTo = DataType.dateToStr(new Date(), "yyyy-") + " " + timeStrs[1].trim();
					// String tFrom = new java.util.Date().ToString("yyyy-") + "
					// " + timeStrs[0].trim();
					// String tTo = new java.util.Date().ToString("yyyy-") + " "
					// + timeStrs[1].trim();
					if (DataType.ParseSysDateTime2DateTime(tFrom).before(dtNow)
							&& dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo)) >= 0) {
						return true;
					}
					// if (DataType.ParseSysDateTime2DateTime(tFrom) <= dtNow &&
					// dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo))
					// >= 0)
					// {
					// return true;
					// }
				}
				return false;
			} else {
				return false;
			}
		}

		if (role == StartLimitRole.Year) {
			sql = "SELECT COUNT(*) as Num FROM " + ptable + " WHERE FK_NY LIKE '" + DataType.getCurrentYear()
					+ "%' AND WFState NOT IN(0,1) AND FlowStarter='" + WebUser.getNo() + "'";
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0) {
				if (flow.getStartLimitPara().equals("")) {
					return true;
				}

				// 判断时间是否在设置的发起范围内.
				String[] strs = flow.getStartLimitPara().split("[@]", -1);
				for (String str : strs) {
					if (StringHelper.isNullOrEmpty(str)) {
						continue;
					}
					String[] timeStrs = str.split("[-]", -1);
					String tFrom = DataType.dateToStr(new Date(), "yyyy-") + " " + timeStrs[0].trim();
					String tTo = DataType.dateToStr(new Date(), "yyyy-") + " " + timeStrs[1].trim();
					if (DataType.ParseSysDateTime2DateTime(tFrom).before(dtNow)
							&& dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo)) >= 0) {
						return true;
					}
					// String tFrom = new java.util.Date().ToString("yyyy-") + "
					// " + timeStrs[0].trim();
					// String tTo = new java.util.Date().ToString("yyyy-") + " "
					// + timeStrs[1].trim();
					// if (DataType.ParseSysDateTime2DateTime(tFrom) <= dtNow &&
					// dtNow.compareTo(DataType.ParseSysDateTime2DateTime(tTo))
					// >= 0)
					// {
					// return true;
					// }
				}
				return false;
			} else {
				return false;
			}
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		/// #endregion 按照时间的必须是，在表单加载后判断, 不管用户设置是否正确.

		return true;
	}

	/**
	 * 当要发送是检查流程是否可以允许发起.
	 * 
	 * @param flow
	 *            流程
	 * @param wk
	 *            开始节点工作
	 * @return
	 */
	public static boolean CheckIsCanStartFlow_SendStartFlow(Flow flow, Work wk) throws Exception {
		StartLimitRole role = flow.getStartLimitRole();
		if (role == StartLimitRole.None) {
			return true;
		}

		String sql = "";
		String ptable = flow.getPTable();

		if (role == StartLimitRole.ColNotExit) {
			// 指定的列名集合不存在，才可以发起流程。

			// 求出原来的值.
			String[] strs = flow.getStartLimitPara().split("[,]", -1);
			String val = "";
			for (String str : strs) {
				if (StringHelper.isNullOrEmpty(str) == true) {
					continue;
				}
				try {
					val += wk.GetValStringByKey(str);
				} catch (RuntimeException ex) {
					throw new RuntimeException(
							"@流程设计错误,您配置的检查参数(" + flow.getStartLimitPara() + "),中的列(" + str + ")已经不存在表单里.");
				}
			}

			// 找出已经发起的全部流程.
			sql = "SELECT " + flow.getStartLimitPara() + " FROM " + ptable
					+ " WHERE  WFState NOT IN(0,1) AND FlowStarter='" + WebUser.getNo() + "'";
			DataTable dt = DBAccess.RunSQLReturnTable(sql);
			for (DataRow dr : dt.Rows) {
				String v = dr.get(0) + "" + dr.get(1) + "" + dr.get(2);
				if (val.equals(v)) {
					return false;
				}
			}
			return true;
		}

		// 配置的sql,执行后,返回结果是 0 .
		if (role == StartLimitRole.ResultIsZero) {
			sql = BP.WF.Glo.DealExp(flow.getStartLimitPara(), wk, null);
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0) {
				return true;
			} else {
				return false;
			}
		}

		// 配置的sql,执行后,返回结果是 <> 0 .
		if (role == StartLimitRole.ResultIsNotZero) {
			sql = BP.WF.Glo.DealExp(flow.getStartLimitPara(), wk, null);
			if (DBAccess.RunSQLReturnValInt(sql, 0) != 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion 其他方法。

}