package BP.WF.Template;

import java.io.IOException;
import java.net.URLEncoder;

import BP.DA.DataType;
import BP.DA.Depositary;
import BP.En.Entity;
import BP.En.Map;
import BP.En.RefMethod;
import BP.En.RefMethodType;
import BP.En.UAC;
import BP.Port.DeptAttr;
import BP.Port.Depts;
import BP.Port.EmpDeptAttr;
import BP.Port.Stations;
import BP.Port.WebUser;
import BP.Sys.PubClass;
import BP.Sys.SystemConfig;
import BP.Sys.ToolbarExcel;
import BP.Tools.StringHelper;
import BP.WF.Glo;
import BP.WF.OSModel;
import BP.WF.Entity.FWCAth;
import BP.WF.Entity.FWCType;
import BP.WF.Entity.FrmWorkCheckAttr;
import BP.WF.Entity.FrmWorkCheckSta;
import BP.WF.Entity.FrmWorkShowModel;
import BP.WF.Template.AccepterRole.NodeDeptAttr;
import BP.WF.Template.AccepterRole.NodeDepts;
import BP.WF.Template.AccepterRole.NodeEmpAttr;
import BP.WF.Template.AccepterRole.NodeEmps;
import BP.WF.Template.Condition.CondType;
import BP.WF.Template.Ext.NodeToolbarAttr;
import BP.WF.Template.Ext.NodeToolbars;
import BP.WF.Template.Ext.OutTimeDeal;
import BP.WF.Template.PubLib.BatchRole;
import BP.WF.Template.PubLib.BlockModel;
import BP.WF.Template.PubLib.BtnAttr;
import BP.WF.Template.PubLib.CancelRole;
import BP.WF.Template.PubLib.DeliveryWay;
import BP.WF.Template.PubLib.NodeAttr;
import BP.WF.Template.PubLib.NodeFormType;
import BP.WF.Template.PubLib.ReturnRole;
import BP.WF.Template.PubLib.RunModel;
import BP.WF.Template.PubLib.SubFlowStartWay;
import BP.WF.Template.PubLib.ThreadKillRole;
import BP.WF.Template.PubLib.TodolistModel;
import TL.ContextHolderUtils;

/**
 * 这里存放每个节点的信息.
 */
public class NodeSheet extends Entity {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Index
	/**
	 * 获取节点的帮助信息url
	 * <p>
	 * </p>
	 * <p>
	 * added by liuxc,2014-8-19
	 * </p>
	 * 
	 * @param sysNo
	 *            帮助网站中所属系统No
	 * @param searchTitle
	 *            帮助主题标题
	 * @return
	 */
	private String getItem(String sysNo, String searchTitle) {
		if (StringHelper.isNullOrEmpty(sysNo)
				|| StringHelper.isNullOrEmpty(searchTitle)) {
			return "javascript:alert('此处还没有帮助信息！')";
		}
		try {
			return String.format(
					"http://online.ccflow.org/KM/Tree.jsp?no=%1$s&st=%2$s",
					sysNo, URLEncoder.encode(searchTitle, "UTF-8"));

		} catch (Exception e) {
			return "";
		}
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Const
	/**
	 * CCFlow流程引擎
	 */
	private static final String SYS_CCFLOW = "001";
	/**
	 * CCForm表单引擎
	 */
	private static final String SYS_CCFORM = "002";

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 属性.
	// /// <summary>
	// /// 节点标记
	// /// </summary>
	// public string NodeMark
	// {
	// get
	// {
	// return this.GetValStrByKey(NodeAttr.NodeMark);
	// }
	// }

	/**
	 * 超时处理方式
	 */
	public final OutTimeDeal getHisOutTimeDeal() {
		// return (OutTimeDeal) this.GetValIntByKey(NodeAttr.OutTimeDeal);
		return OutTimeDeal.forValue(this.GetValIntByKey(NodeAttr.OutTimeDeal));
	}

	public final void setHisOutTimeDeal(OutTimeDeal value) {
		this.SetValByKey(NodeAttr.OutTimeDeal, value.getValue());
	}

	/**
	 * 访问规则
	 */
	public final ReturnRole getHisReturnRole() {
		// return (ReturnRole) this.GetValIntByKey(NodeAttr.ReturnRole);
		return ReturnRole.forValue(this.GetValIntByKey(NodeAttr.ReturnRole));
	}

	public final void setHisReturnRole(ReturnRole value) {
		this.SetValByKey(NodeAttr.ReturnRole, value.getValue());
	}

	/**
	 * 访问规则
	 */
	public final DeliveryWay getHisDeliveryWay() {
		return DeliveryWay.forValue(this.GetValIntByKey(NodeAttr.DeliveryWay));
	}

	public final void setHisDeliveryWay(DeliveryWay value) {
		this.SetValByKey(NodeAttr.DeliveryWay, value.getValue());
	}

	public final int getStep() {
		return this.GetValIntByKey(NodeAttr.Step);
	}

	public final void setStep(int value) {
		this.SetValByKey(NodeAttr.Step, value);
	}

	public final int getNodeID() {
		return this.GetValIntByKey(NodeAttr.NodeID);
	}

	public final void setNodeID(int value) {
		this.SetValByKey(NodeAttr.NodeID, value);
	}

	/**
	 * 超时处理内容
	 */
	public final String getDoOutTime() {
		return this.GetValStringByKey(NodeAttr.DoOutTime);
	}

	public final void setDoOutTime(String value) {
		this.SetValByKey(NodeAttr.DoOutTime, value);
	}

	/**
	 * 超时处理条件
	 */
	public final String getDoOutTimeCond() {
		return this.GetValStringByKey(NodeAttr.DoOutTimeCond);
	}

	public final void setDoOutTimeCond(String value) {
		this.SetValByKey(NodeAttr.DoOutTimeCond, value);
	}

	public final String getName() {
		return this.GetValStringByKey(NodeAttr.Name);
	}

	public final void setName(String value) {
		this.SetValByKey(NodeAttr.Name, value);
	}

	public final String getFK_Flow() {
		return this.GetValStringByKey(NodeAttr.FK_Flow);
	}

	public final void setFK_Flow(String value) {
		this.SetValByKey(NodeAttr.FK_Flow, value);
	}

	public final String getFlowName() {
		return this.GetValStringByKey(NodeAttr.FlowName);
	}

	public final void setFlowName(String value) {
		this.SetValByKey(NodeAttr.FlowName, value);
	}

	/**
	 * 接受人sql
	 */
	public final String getDeliveryParas() {
		return this.GetValStringByKey(NodeAttr.DeliveryParas);
	}

	public final void setDeliveryParas(String value) {
		this.SetValByKey(NodeAttr.DeliveryParas, value);
	}

	/**
	 * 是否可以退回
	 */
	public final boolean getReturnEnable() {
		return this.GetValBooleanByKey(BtnAttr.ReturnRole);
	}

	@Override
	public String getPK() {
		return "NodeID";
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion 属性.

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 初试化全局的 Node
	@Override
	public UAC getHisUAC() {
		UAC uac = new UAC();
		Flow fl = new Flow(this.getFK_Flow());
		if (WebUser.getNo().equals(SystemConfig.getAppSettings().get("Admin").toString())) {
			uac.IsUpdate = true;
		}
		return uac;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造函数
	/**
	 * 节点
	 */
	public NodeSheet() {
	}

	/**
	 * 重写基类方法
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map();
		// map 的基础信息.
		map.setPhysicsTable("WF_Node");
		map.setEnDesc("节点");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 基础属性
		map.AddTBIntPK(NodeAttr.NodeID, 0, "节点ID", true, true);
		map.AddTBInt(NodeAttr.Step, 0, "步骤(无计算意义)", true, false);
		map.SetHelperAlert(NodeAttr.Step, "它用于节点的排序，正确的设置步骤可以让流程容易读写."); // 使用alert的方式显示帮助信息.
		map.AddTBString(NodeAttr.FK_Flow, null, "流程编号", false, false, 3, 3, 10,
				false);

		map.AddTBString(NodeAttr.Name, null, "名称", true, true, 0, 100, 10,
				false);
		map.AddTBString(NodeAttr.Tip, null, "操作提示", true, false, 0, 100, 10,
				false);

		String str = "";
		str += "@0=01.按当前操作员所属组织结构逐级查找岗位";
		str += "@1=02.按节点绑定的部门计算";
		str += "@2=03.按设置的SQL获取接受人计算";
		str += "@3=04.按节点绑定的人员计算";
		str += "@4=05.由上一节点发送人通过“人员选择器”选择接受人";
		str += "@5=06.按上一节点表单指定的字段值作为本步骤的接受人";
		str += "@6=07.与上一节点处理人员相同";
		str += "@7=08.与开始节点处理人相同";
		str += "@8=09.与指定节点处理人相同";
		str += "@9=10.按绑定的岗位与部门交集计算";
		str += "@10=11.按绑定的岗位计算并且以绑定的部门集合为纬度";
		str += "@11=12.按指定节点的人员岗位计算";
		str += "@12=13.按SQL确定子线程接受人与数据源";
		str += "@13=14.由上一节点的明细表来决定子线程的接受人";
		str += "@14=15.仅按绑定的岗位计算";
		str += "@15=16.由FEE来决定";
		str += "@100=17.按ccflow的BPM模式处理";
		map.AddDDLSysEnum(NodeAttr.DeliveryWay, 0, "节点访问规则", true, true,
				NodeAttr.DeliveryWay, str);

		map.AddTBString(NodeAttr.DeliveryParas, null, "访问规则设置内容", true, false,
				0, 500, 10, true);
		map.AddDDLSysEnum(NodeAttr.WhoExeIt, 0, "谁执行它", true, true,
				NodeAttr.WhoExeIt, "@0=操作员执行@1=机器执行@2=混合执行");
		map.AddDDLSysEnum(NodeAttr.TurnToDeal, 0, "发送后转向", true, true,
				NodeAttr.TurnToDeal,
				"@0=提示ccflow默认信息@1=提示指定信息@2=转向指定的url@3=按照条件转向");
		map.AddTBString(NodeAttr.TurnToDealDoc, null, "转向处理内容", true, false, 0,
				1000, 10, true);
		map.AddDDLSysEnum(NodeAttr.ReadReceipts, 0, "已读回执", true, true,
				NodeAttr.ReadReceipts,
				"@0=不回执@1=自动回执@2=由上一节点表单字段决定@3=由SDK开发者参数决定");
		map.SetHelperUrl(NodeAttr.ReadReceipts,
				this.getItem(SYS_CCFLOW, "已读回执"));

		map.AddDDLSysEnum(NodeAttr.CondModel, 0, "方向条件控制规则", true, true,
				NodeAttr.CondModel, "@0=由连接线条件控制@1=让用户手工选择");
		map.SetHelperUrl(NodeAttr.CondModel,
				this.getItem(SYS_CCFLOW, "方向条件控制规则")); // 增加帮助

		// 撤销规则.
		map.AddDDLSysEnum(NodeAttr.CancelRole,
				CancelRole.OnlyNextStep.getValue(), "撤销规则", true, true,
				NodeAttr.CancelRole,
				"@0=上一步可以撤销@1=不能撤销@2=上一步与开始节点可以撤销@3=指定的节点可以撤销");

		// 节点工作批处理. edit by peng, 2014-01-24.
		map.AddDDLSysEnum(NodeAttr.BatchRole, BatchRole.None.getValue(),
				"工作批处理", true, true, NodeAttr.BatchRole,
				"@0=不可以批处理@1=批量审核@2=分组批量审核");
		map.AddTBInt(NodeAttr.BatchListCount, 12, "批处理数量", true, false);
		map.SetHelperUrl(NodeAttr.BatchRole,
				this.getItem(SYS_CCFLOW, "节点工作批处理")); // 增加帮助

		map.AddTBString(NodeAttr.BatchParas, null, "批处理参数", true, false, 0,
				300, 10, true);
		map.AddBoolean(NodeAttr.IsTask, true, "允许分配工作否?", true, true, false);
		map.AddBoolean(NodeAttr.IsRM, true, "是否启用投递路径自动记忆功能?", true, true,
				false);

		map.AddTBDateTime("DTFrom", "生命周期从", true, true);
		map.AddTBDateTime("DTTo", "生命周期到", true, true);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 基础属性

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 表单.
		map.AddDDLSysEnum(
				NodeAttr.FormType,
				NodeFormType.FreeForm.getValue(),
				"节点表单方案",
				true,
				true,
				"NodeFormType",
				"@0=傻瓜表单(ccflow6取消支持)@1=自由表单@2=自定义表单@3=SDK表单@4=SL表单(ccflow6取消支持)@5=表单树@6=动态表单树@7=公文表单(WebOffice)@8=Excel表单(测试中)@9=Word表单(测试中)@100=禁用(对多表单流程有效)");
		map.AddTBString(NodeAttr.NodeFrmID, null, "节点表单ID", true, false, 0, 50,
				10);

		map.AddTBString(NodeAttr.FormUrl, null, "表单URL", true, false, 0, 200,
				10, true);
		map.AddTBString(NodeAttr.FocusField, null, "焦点字段", true, false, 0, 50,
				10, true);

		map.AddDDLSysEnum(NodeAttr.SaveModel, 0, "保存方式", true, true);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 表单.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 分合流子线程属性
		map.AddDDLSysEnum(NodeAttr.RunModel, 0, "运行模式", true, true,
				NodeAttr.RunModel, "@0=普通@1=合流@2=分流@3=分合流@4=子线程");

		map.SetHelperUrl(NodeAttr.RunModel, this.getItem(SYS_CCFLOW, "运行模式")); // 增加帮助.

		// 子线程类型.
		map.AddDDLSysEnum(NodeAttr.SubThreadType, 0, "子线程类型", true, true,
				NodeAttr.SubThreadType, "@0=同表单@1=异表单");
		map.SetHelperUrl(NodeAttr.SubThreadType,
				this.getItem(SYS_CCFLOW, "子线程类型")); // 增加帮助

		map.AddTBDecimal(NodeAttr.PassRate, 100, "完成通过率", true, false);
		map.SetHelperUrl(NodeAttr.PassRate, this.getItem(SYS_CCFLOW, "完成通过率")); // 增加帮助.

		// 启动子线程参数 2013-01-04
		map.AddDDLSysEnum(NodeAttr.SubFlowStartWay,
				SubFlowStartWay.None.getValue(), "子线程启动方式", true, true,
				NodeAttr.SubFlowStartWay, "@0=不启动@1=指定的字段启动@2=按明细表启动");
		map.AddTBString(NodeAttr.SubFlowStartParas, null, "启动参数", true, false,
				0, 100, 10, true);
		map.SetHelperUrl(NodeAttr.SubFlowStartWay,
				this.getItem(SYS_CCFLOW, "子线程启动方式")); // 增加帮助

		// 待办处理模式.
		map.AddDDLSysEnum(NodeAttr.TodolistModel,
				TodolistModel.QiangBan.getValue(), "待办处理模式", true, true,
				NodeAttr.TodolistModel, "@0=抢办模式@1=协作模式@2=队列模式@3=共享模式");
		map.SetHelperUrl(NodeAttr.TodolistModel,
				this.getItem(SYS_CCFLOW, "待办处理模式")); // 增加帮助.

		// 发送阻塞模式.
		map.AddDDLSysEnum(NodeAttr.BlockModel, BlockModel.None.getValue(),
				"发送阻塞模式", true, true, NodeAttr.BlockModel,
				"@0=不阻塞@1=当前节点的所有未完成的子流程@2=按约定格式阻塞未完成子流程@3=按照SQL阻塞@4=按照表达式阻塞");
		map.SetHelperUrl(NodeAttr.BlockModel,
				this.getItem(SYS_CCFLOW, "待办处理模式")); // 增加帮助.

		map.AddTBString(NodeAttr.BlockExp, null, "阻塞表达式", true, false, 0, 700,
				10, true);
		map.SetHelperAlert(NodeAttr.BlockExp, "该属性配合发送阻塞模式属性起作用");

		map.AddTBString(NodeAttr.BlockAlert, null, "被阻塞时提示信息", true, false, 0,
				700, 10, true);
		map.SetHelperAlert(NodeAttr.BlockAlert, "该属性配合发送阻塞模式属性起作用,编写支持cc表达式.");

		// map.AddBoolean(NodeAttr.IsCheckSubFlowOver, false,
		// "(当前节点启动子流程时)是否检查所有子流程结束后,该节点才能向下发送?",
		// true, true, true);

		// // add 2013-09-14
		// map.AddBoolean(NodeAttr.IsEnableTaskPool, true,
		// "是否启用共享任务池(与web.config中的IsEnableTaskPool配置启用才有效,与子线程无关)？", true,
		// true, true);
		// map.SetHelperBaidu(NodeAttr.IsEnableTaskPool); //增加帮助.

		map.AddBoolean(NodeAttr.IsAllowRepeatEmps, false,
				"是否允许子线程接受人员重复(仅当分流点向子线程发送时有效)?", true, true, true);
		map.AddBoolean(NodeAttr.IsGuestNode, false,
				"是否是客户执行节点(非组织结构人员参与处理工作的节点)?", true, true, true);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 分合流子线程属性

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 自动跳转规则
		map.AddBoolean(NodeAttr.AutoJumpRole0, false, "处理人就是发起人", true, true,
				false);
		map.SetHelperUrl(NodeAttr.AutoJumpRole0,
				this.getItem(SYS_CCFLOW, "自动跳转规则")); // 增加帮助

		map.AddBoolean(NodeAttr.AutoJumpRole1, false, "处理人已经出现过", true, true,
				false);
		map.AddBoolean(NodeAttr.AutoJumpRole2, false, "处理人与上一步相同", true, true,
				false);
		map.AddDDLSysEnum(NodeAttr.WhenNoWorker, 0, "找不到处理人处理规则", true, true,
				NodeAttr.WhenNoWorker, "@0=提示错误@1=自动转到下一步");
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 功能按钮状态
		map.AddTBString(BtnAttr.SendLab, "发送", "发送按钮标签", true, false, 0, 50, 10);
		map.AddTBString(BtnAttr.SendJS, "", "按钮JS函数", true, false, 0, 50, 10);
		// map.SetHelperBaidu(BtnAttr.SendJS, "ccflow 发送前数据完整性判断"); //增加帮助.
		map.SetHelperUrl(BtnAttr.SendJS, this.getItem(SYS_CCFLOW, "按钮JS函数"));

		map.AddTBString(BtnAttr.SaveLab, "保存", "保存按钮标签", true, false, 0, 50, 10);
		map.AddBoolean(BtnAttr.SaveEnable, true, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.SaveLab, this.getItem(SYS_CCFLOW, "保存")); // 增加帮助

		map.AddTBString(BtnAttr.ThreadLab, "子线程", "子线程按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.ThreadEnable, false, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.ThreadLab, this.getItem(SYS_CCFLOW, "子线程按钮标签")); // 增加帮助

		map.AddDDLSysEnum(NodeAttr.ThreadKillRole,
				ThreadKillRole.None.getValue(), "子线程删除方式", true, true,
				NodeAttr.ThreadKillRole, "@0=不能删除@1=手工删除@2=自动删除", true);
		map.SetHelperUrl(NodeAttr.ThreadKillRole,
				this.getItem(SYS_CCFLOW, "子线程删除方式")); // 增加帮助

		map.AddTBString(BtnAttr.SubFlowLab, "子流程", "子流程按钮标签", true, false, 0,
				50, 10);
		map.AddDDLSysEnum(BtnAttr.SubFlowCtrlRole, 0, "控制规则", true, true,
				BtnAttr.SubFlowCtrlRole, "@0=无@1=不可以删除子流程@2=可以删除子流程");

		map.AddTBString(BtnAttr.JumpWayLab, "跳转", "跳转按钮标签", true, false, 0, 50,
				10);
		map.AddDDLSysEnum(NodeAttr.JumpWay, 0, "跳转规则", true, true,
				NodeAttr.JumpWay);
		map.AddTBString(NodeAttr.JumpToNodes, null, "可跳转的节点", true, false, 0,
				200, 10, true);
		map.SetHelperUrl(NodeAttr.JumpWay, this.getItem(SYS_CCFLOW, "跳转规则")); // 增加帮助.

		map.AddTBString(BtnAttr.ReturnLab, "退回", "退回按钮标签", true, false, 0, 50,
				10);
		map.AddDDLSysEnum(NodeAttr.ReturnRole, 0, "退回规则", true, true,
				NodeAttr.ReturnRole);
		// map.AddTBString(NodeAttr.ReturnToNodes, null, "可退回节点", true, false,
		// 0, 200, 10, true);
		map.SetHelperUrl(NodeAttr.ReturnRole, this.getItem(SYS_CCFLOW, "222")); // 增加帮助.

		map.AddBoolean(NodeAttr.IsBackTracking, false, "是否可以原路返回(启用退回功能才有效)",
				true, true, false);
		map.AddTBString(BtnAttr.ReturnField, "", "退回信息填写字段", true, false, 0,
				50, 10);
		map.SetHelperUrl(NodeAttr.IsBackTracking,
				this.getItem(SYS_CCFLOW, "是否可以原路返回")); // 增加帮助.

		map.AddTBString(BtnAttr.CCLab, "抄送", "抄送按钮标签", true, false, 0, 50, 10);
		map.AddDDLSysEnum(NodeAttr.CCRole, 0, "抄送规则", true, true,
				NodeAttr.CCRole,
				"@@0=不能抄送@1=手工抄送@2=自动抄送@3=手工与自动@4=按表单SysCCEmps字段计算@5=在发送前打开抄送窗口");
		map.SetHelperUrl(NodeAttr.CCRole, this.getItem(SYS_CCFLOW, "抄送规则")); // 增加帮助.

		// add 2014-04-05.
		map.AddDDLSysEnum(NodeAttr.CCWriteTo, 0, "抄送写入规则", true, true,
				NodeAttr.CCWriteTo, "@0=写入抄送列表@1=写入待办@2=写入待办与抄送列表", true);
		map.SetHelperUrl(NodeAttr.CCWriteTo, this.getItem(SYS_CCFLOW, "抄送写入规则")); // 增加帮助

		map.AddTBString(BtnAttr.ShiftLab, "移交", "移交按钮标签", true, false, 0, 50,
				10);
		map.AddBoolean(BtnAttr.ShiftEnable, false, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.ShiftLab, this.getItem(SYS_CCFLOW, "移交")); // 增加帮助.note:none

		map.AddTBString(BtnAttr.DelLab, "删除", "删除按钮标签", true, false, 0, 50, 10);
		map.AddDDLSysEnum(BtnAttr.DelEnable, 0, "删除规则", true, true,
				BtnAttr.DelEnable);
		map.SetHelperUrl(BtnAttr.DelLab, this.getItem(SYS_CCFLOW, "删除")); // 增加帮助.

		map.AddTBString(BtnAttr.EndFlowLab, "结束流程", "结束流程按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.EndFlowEnable, false, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.EndFlowLab, this.getItem(SYS_CCFLOW, "结束流程")); // 增加帮助

		map.AddTBString(BtnAttr.PrintDocLab, "打印单据", "打印单据按钮标签", true, false,
				0, 50, 10);
		map.AddDDLSysEnum(BtnAttr.PrintDocEnable, 0, "打印方式", true, true,
				BtnAttr.PrintDocEnable, "@0=不打印@1=打印网页@2=打印RTF模板@3=打印Word模版");
		map.SetHelperUrl(BtnAttr.PrintDocEnable,
				this.getItem(SYS_CCFLOW, "表单打印方式")); // 增加帮助

		// map.AddBoolean(BtnAttr.PrintDocEnable, false, "是否启用", true, true);
		// map.AddTBString(BtnAttr.AthLab, "附件", "附件按钮标签", true, false, 0, 50,
		// 10);
		// map.AddDDLSysEnum(NodeAttr.FJOpen, 0, this.ToE("FJOpen", "附件权限"),
		// true, true,
		// NodeAttr.FJOpen, "@0=关闭附件@1=操作员@2=工作ID@3=流程ID");

		map.AddTBString(BtnAttr.TrackLab, "轨迹", "轨迹按钮标签", true, false, 0, 50,
				10);
		map.AddBoolean(BtnAttr.TrackEnable, true, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.TrackLab, this.getItem(SYS_CCFLOW, "轨迹")); // 增加帮助

		map.AddTBString(BtnAttr.HungLab, "挂起", "挂起按钮标签", true, false, 0, 50, 10);
		map.AddBoolean(BtnAttr.HungEnable, false, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.HungLab, this.getItem(SYS_CCFLOW, "挂起")); // 增加帮助.

		map.AddTBString(BtnAttr.SelectAccepterLab, "接受人", "接受人按钮标签", true,
				false, 0, 50, 10);
		map.AddDDLSysEnum(BtnAttr.SelectAccepterEnable, 0, "工作方式", true, true,
				BtnAttr.SelectAccepterEnable);
		map.SetHelperUrl(BtnAttr.SelectAccepterLab,
				this.getItem(SYS_CCFLOW, "接受人")); // 增加帮助

		map.AddTBString(BtnAttr.SearchLab, "查询", "查询按钮标签", true, false, 0, 50,
				10);
		map.AddBoolean(BtnAttr.SearchEnable, false, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.SearchLab, this.getItem(SYS_CCFLOW, "查询")); // 增加帮助

		map.AddTBString(BtnAttr.WorkCheckLab, "审核", "审核按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.WorkCheckEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.BatchLab, "批处理", "批处理按钮标签", true, false, 0, 50,
				10);
		map.AddBoolean(BtnAttr.BatchEnable, false, "是否启用", true, true);
		map.SetHelperUrl(BtnAttr.BatchLab, this.getItem(SYS_CCFLOW, "批处理")); // 增加帮助

		map.AddTBString(BtnAttr.AskforLab, "加签", "加签按钮标签", true, false, 0, 50,
				10);
		map.AddBoolean(BtnAttr.AskforEnable, false, "是否启用", true, true);

		// add by 周朋 2014-11-21. 让用户可以自己定义流转.
		map.AddTBString(BtnAttr.TCLab, "流转自定义", "流转自定义", true, false, 0, 50, 10);
		map.AddBoolean(BtnAttr.TCEnable, false, "是否启用", true, true);

		// map.AddTBString(BtnAttr.AskforLabRe, "执行", "加签按钮标签", true, false, 0,
		// 50, 10);
		// map.AddBoolean(BtnAttr.AskforEnable, false, "是否启用", true, true);

		map.SetHelperUrl(BtnAttr.AskforLab, this.getItem(SYS_CCFLOW, "加签")); // 增加帮助
		map.AddTBString(BtnAttr.WebOfficeLab, "公文", "文档按钮标签", true, false, 0,
				50, 10);
		// map.AddBoolean(BtnAttr.WebOfficeEnable, false, "是否启用", true, true);
		map.AddDDLSysEnum(BtnAttr.WebOfficeEnable, 0, "文档启用方式", true, true,
				BtnAttr.WebOfficeEnable, "@0=不启用@1=按钮方式@2=标签页方式");
		map.SetHelperUrl(BtnAttr.WebOfficeLab, this.getItem(SYS_CCFLOW, "公文"));

		// map.AddBoolean(BtnAttr.SelectAccepterEnable, false, "是否启用", true,
		// true);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 功能按钮状态

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 考核属性
		// 考核属性
		map.AddTBFloat(NodeAttr.WarningDays, 0, "警告期限(0不警告)", true, false); // "警告期限(0不警告)"
		map.AddTBFloat(NodeAttr.DeductDays, 1, "限期(天)", true, false); // "限期(天)"
		map.AddTBFloat(NodeAttr.DeductCent, 2, "扣分(每延期1天扣)", true, false); // "扣分(每延期1天扣)"

		map.AddTBFloat(NodeAttr.MaxDeductCent, 0, "最高扣分", true, false); // "最高扣分"
		map.AddTBFloat(NodeAttr.SwinkCent, Float.parseFloat("0.1"), "工作得分",
				true, false); // "工作得分"
		map.AddDDLSysEnum(NodeAttr.OutTimeDeal, 0, "超时处理", true, true,
				NodeAttr.OutTimeDeal,
				"@0=不处理@1=自动向下运动@2=自动跳转指定的节点@3=自动移交给指定的人员@4=向指定的人员发消息@5=删除流程@6=执行SQL");

		map.AddTBString(NodeAttr.DoOutTime, null, "处理内容", true, false, 0, 300,
				10, true);
		map.AddTBString(NodeAttr.DoOutTimeCond, null, "执行超时条件", true, false, 0,
				100, 10, true);

		// map.AddTBString(NodeAttr.FK_Flows, null, "flow", false, false, 0,
		// 100, 10);

		map.AddDDLSysEnum(NodeAttr.CHWay, 0, "考核方式", true, true,
				NodeAttr.CHWay, "@0=不考核@1=按时效@2=按工作量");
		map.AddTBFloat(NodeAttr.Workload, 0, "工作量(单位:分钟)", true, false);

		// 是否质量考核点？
		map.AddBoolean(NodeAttr.IsEval, false, "是否质量考核点", true, true, true);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 考核属性

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 审核组件属性, 此处变更了BP.Sys.FrmWorkCheck 也要变更.
		// BP.Sys.FrmWorkCheck
		map.AddDDLSysEnum(FrmWorkCheckAttr.FWCSta,
				FrmWorkCheckSta.Disable.getValue(), "审核组件状态", true, true,
				FrmWorkCheckAttr.FWCSta, "@0=禁用@1=启用@2=只读");

		map.AddDDLSysEnum(FrmWorkCheckAttr.FWCShowModel,
				FrmWorkShowModel.Free.getValue(), "显示方式", true, true,
				FrmWorkCheckAttr.FWCShowModel, "@0=表格方式@1=自由模式@2=签章模式"); // 此属性暂时没有用.

		map.AddDDLSysEnum(FrmWorkCheckAttr.FWCType, FWCType.Check.getValue(),
				"工作方式", true, true, FrmWorkCheckAttr.FWCType,
				"@0=审核组件@1=日志组件@2=周报组件@3=月报组件");

		// add by stone 2015-03-19. 如果为空，就去节点名称显示到步骤里.
		map.AddTBString(FrmWorkCheckAttr.FWCNodeName, null, "节点意见名称", true,
				false, 0, 100, 10);

		map.AddDDLSysEnum(FrmWorkCheckAttr.FWCAth, FWCAth.None.getValue(),
				"附件上传", true, true, FrmWorkCheckAttr.FWCAth,
				"@0=不启用@1=多附件@2=单附件(暂不支持)@3=图片附件(暂不支持)");
		map.SetHelperAlert(FrmWorkCheckAttr.FWCAth,
				"在审核期间，是否启用上传附件？启用什么样的附件？注意：附件的属性在节点表单里配置。"); // 使用alert的方式显示帮助信息.

		map.AddBoolean(FrmWorkCheckAttr.FWCTrackEnable, true, "轨迹图是否显示？", true,
				true, false);
		map.AddBoolean(FrmWorkCheckAttr.FWCListEnable, true,
				"历史审核信息是否显示？(否,历史信息仅出现意见框)", true, true, true);

		map.AddBoolean(FrmWorkCheckAttr.FWCIsShowAllStep, false,
				"在轨迹表里是否显示所有的步骤？", true, true);

		map.AddTBString(FrmWorkCheckAttr.FWCOpLabel, "审核", "操作名词(审核/审阅/批示)",
				true, false, 0, 50, 10);
		map.AddTBString(FrmWorkCheckAttr.FWCDefInfo, "同意", "默认审核信息", true,
				false, 0, 50, 10);
		map.AddBoolean(FrmWorkCheckAttr.SigantureEnabel, false,
				"操作人是否显示为图片签名？", true, true);
		map.AddBoolean(FrmWorkCheckAttr.FWCIsFullInfo, true,
				"如果用户未审核是否按照默认意见填充？", true, true, true);

		// map.AddTBFloat(FrmWorkCheckAttr.FWC_X, 5, "位置X", true, false);
		// map.AddTBFloat(FrmWorkCheckAttr.FWC_Y, 5, "位置Y", true, false);

		// 高度与宽度, 如果是自由表单就不要变化该属性.
		map.AddTBFloat(FrmWorkCheckAttr.FWC_H, 300, "高度", true, false);
		map.SetHelperAlert(FrmWorkCheckAttr.FWC_H,
				"如果是自由表单就不要变化该属性,为0，则标识为100%,应用的组件模式."); // 增加帮助
		map.AddTBFloat(FrmWorkCheckAttr.FWC_W, 400, "宽度", true, false);
		map.SetHelperAlert(FrmWorkCheckAttr.FWC_W,
				"如果是自由表单就不要变化该属性,为0，则标识为100%,应用的组件模式."); // 增加帮助
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 审核组件属性.

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 公文按钮
		map.AddTBString(BtnAttr.OfficeOpenLab, "打开本地", "打开本地标签", true, false,
				0, 50, 10);
		map.AddBoolean(BtnAttr.OfficeOpenEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeOpenTemplateLab, "打开模板", "打开模板标签", true,
				false, 0, 50, 10);
		map.AddBoolean(BtnAttr.OfficeOpenTemplateEnable, false, "是否启用", true,
				true);

		map.AddTBString(BtnAttr.OfficeSaveLab, "保存", "保存标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.OfficeSaveEnable, true, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeAcceptLab, "接受修订", "接受修订标签", true, false,
				0, 50, 10);
		map.AddBoolean(BtnAttr.OfficeAcceptEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeRefuseLab, "拒绝修订", "拒绝修订标签", true, false,
				0, 50, 10);
		map.AddBoolean(BtnAttr.OfficeRefuseEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeOverLab, "套红", "套红按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.OfficeOverEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficePrintLab, "打印", "打印按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.OfficePrintEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeSealLab, "签章", "签章按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.OfficeSealEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeDownLab, "下载", "下载按钮标签", true, false, 0,
				50, 10);
		map.AddBoolean(BtnAttr.OfficeDownEnable, false, "是否启用", true, true);

		map.AddTBString(BtnAttr.OfficeInsertFlowLab, "插入流程", "插入流程标签", true,
				false, 0, 50, 10);
		map.AddBoolean(BtnAttr.OfficeInsertFlowEnable, false, "是否启用", true,
				true);

		map.AddBoolean(BtnAttr.OfficeNodeInfo, false, "是否记录节点信息", true, true);
		map.AddBoolean(BtnAttr.OfficeReSavePDF, false, "是否该自动保存为PDF", true,
				true);

		map.AddBoolean(BtnAttr.OfficeIsMarks, true, "是否进入留痕模式", true, true);
		map.AddTBString(BtnAttr.OfficeTemplate, "", "指定文档模板", true, false, 0,
				100, 10);

		map.AddBoolean(BtnAttr.OfficeMarksEnable, true, "是否查看用户留痕", true, true,
				true);

		map.AddBoolean(BtnAttr.OfficeIsParent, true, "是否使用父流程的文档", true, true);

		map.AddBoolean(BtnAttr.OfficeTHEnable, false, "是否自动套红", true, true);
		map.AddTBString(BtnAttr.OfficeTHTemplate, "", "自动套红模板", true, false, 0,
				200, 10);
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 移动设置.
		map.AddDDLSysEnum(NodeAttr.MPhone_WorkModel, 0, "手机工作模式", true, true,
				NodeAttr.MPhone_WorkModel, "@0=原生态@1=浏览器@2=禁用");
		map.AddDDLSysEnum(NodeAttr.MPhone_SrcModel, 0, "手机屏幕模式", true, true,
				NodeAttr.MPhone_SrcModel, "@0=强制横屏@1=强制竖屏@2=由重力感应决定");

		map.AddDDLSysEnum(NodeAttr.MPad_WorkModel, 0, "平板工作模式", true, true,
				NodeAttr.MPad_WorkModel, "@0=原生态@1=浏览器@2=禁用");
		map.AddDDLSysEnum(NodeAttr.MPad_SrcModel, 0, "平板屏幕模式", true, true,
				NodeAttr.MPad_SrcModel, "@0=强制横屏@1=强制竖屏@2=由重力感应决定");
		map.SetHelperUrl(NodeAttr.MPhone_WorkModel,
				"http://bbs.ccflow.org/showtopic-2866.jsp");
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 移动设置.

		// 节点工具栏
		map.AddDtl(new NodeToolbars(), NodeToolbarAttr.FK_Node);

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 对应关系
		// 相关功能。
		if (Glo.getOSModel() == OSModel.WorkFlow) {
			map.getAttrsOfOneVSM().Add(new NodeStations(),
					new BP.WF.Port.Stations(), NodeStationAttr.FK_Node,
					NodeStationAttr.FK_Station, DeptAttr.Name, DeptAttr.No,
					"节点绑定岗位");

			// 判断是否为集团使用，集团时打开新页面以树形展示
			if (Glo.getIsUnit() == true) {
				RefMethod rmDept = new RefMethod();
				rmDept.Title = "节点绑定部门";
				rmDept.ClassMethodName = this.toString() + ".DoDepts";
				rmDept.Icon = BP.WF.Glo.getCCFlowAppPath()
						+ "WF/Img/Btn/DTS.gif";
				map.AddRefMethod(rmDept);
			} else {
				map.getAttrsOfOneVSM().Add(new NodeDepts(),
						new BP.WF.Port.Depts(), NodeDeptAttr.FK_Node,
						NodeDeptAttr.FK_Dept, DeptAttr.Name, DeptAttr.No,
						"节点绑定部门");
			}
		} else {
			// 节点岗位.
			map.getAttrsOfOneVSM().Add(new NodeStations(), new Stations(),
					NodeStationAttr.FK_Node, NodeStationAttr.FK_Station,
					DeptAttr.Name, DeptAttr.No, "节点绑定岗位");
			// 判断是否为集团使用，集团时打开新页面以树形展示
			if (Glo.getIsUnit() == true) {
				RefMethod rmDept = new RefMethod();
				rmDept.Title = "节点绑定部门";
				rmDept.ClassMethodName = this.toString() + ".DoDepts";
				rmDept.Icon = BP.WF.Glo.getCCFlowAppPath()
						+ "WF/Img/Btn/DTS.gif";
				map.AddRefMethod(rmDept);
			} else {
				// 节点部门.
				map.getAttrsOfOneVSM().Add(new NodeDepts(), new Depts(),
						NodeDeptAttr.FK_Node, NodeDeptAttr.FK_Dept,
						DeptAttr.Name, DeptAttr.No, "节点绑定部门");
			}
		}

		map.getAttrsOfOneVSM().Add(new NodeEmps(), new BP.WF.Port.Emps(),
				NodeEmpAttr.FK_Node, EmpDeptAttr.FK_Emp, DeptAttr.Name,
				DeptAttr.No, "节点绑定接受人");

		// 傻瓜表单可以调用的子流程. 2014.10.19 去掉.
		// map.AttrsOfOneVSM.Add(new BP.WF.NodeFlows(), new Flows(),
		// NodeFlowAttr.FK_Node, NodeFlowAttr.FK_Flow, DeptAttr.Name,
		// DeptAttr.No,
		// "傻瓜表单可调用的子流程");
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion

		RefMethod rm = new RefMethod();
		rm.Title = "可退回的节点(当退回规则设置可退回指定的节点时,该设置有效.)"; // "设计表单";
		rm.ClassMethodName = this.toString() + ".DoCanReturnNodes";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		rm.Visable = true;
		rm.refMethodType = RefMethodType.LinkModel;
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.ReturnRole;
		rm.RefAttrLinkLabel = "设置可退回的节点";
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		// rm.Title = "可撤销发送节点(只有撤销规则是指定的节点可以撤销时,该设置有效.)"; // "可撤销发送的节点";
		rm.Title = "可撤销的节点"; // "可撤销发送的节点";
		rm.ClassMethodName = this.toString() + ".DoCanCancelNodes";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		rm.Visable = true;
		rm.refMethodType = RefMethodType.LinkeWinOpen;
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.CancelRole;
		rm.RefAttrLinkLabel = "";
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "设置自动抄送规则(当节点为自动抄送时,该设置有效.)"; // "抄送规则";
		rm.ClassMethodName = this.toString() + ".DoCCRole";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.CCRole;
		rm.RefAttrLinkLabel = "自动抄送设置";
		rm.refMethodType = RefMethodType.LinkeWinOpen;
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "设计傻瓜表单(当节点表单类型设置为傻瓜表单时,该设置有效.)"; // "设计表单";
		rm.ClassMethodName = this.toString() + ".DoFormCol4";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.SaveModel;
		rm.refMethodType = RefMethodType.LinkeWinOpen;
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "设计自由表单(当节点表单类型设置为自由表单时,该设置有效.)"; // "设计表单";
		rm.ClassMethodName = this.toString() + ".DoFormFree";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.SaveModel;
		rm.refMethodType = RefMethodType.LinkeWinOpen;
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "绑定流程表单"; // "设计表单"; (当节点表单类型设置为树形表单时,该设置有效.)
		rm.ClassMethodName = this.toString() + ".DoFormTree";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.SaveModel;
		rm.refMethodType = RefMethodType.LinkeWinOpen;
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "绑定rtf打印格式模版(当打印方式为打印RTF格式模版时,该设置有效)"; // "单据&单据";
		rm.ClassMethodName = this.toString() + ".DoBill";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/FileType/doc.gif";
		rm.refMethodType = RefMethodType.LinkeWinOpen;

		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.PrintDocEnable;
		rm.RefAttrLinkLabel = "";
		rm.Target = "_blank";
		map.AddRefMethod(rm);
		if (BP.Sys.SystemConfig.getCustomerNo().equals("HCBD")) {
			// 为海成邦达设置的个性化需求.
			rm = new RefMethod();
			rm.Title = "DXReport设置";
			rm.ClassMethodName = this.toString() + ".DXReport";
			rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/FileType/doc.gif";
			map.AddRefMethod(rm);
		}

		rm = new RefMethod();
		rm.Title = "设置事件"; // "调用事件接口";
		rm.ClassMethodName = this.toString() + ".DoAction";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Event.png";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "向当事人推送消息"; // "调用事件接口";
		rm.ClassMethodName = this.toString() + ".DoPush2Current";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Message24.png";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		// map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "向指定人推送消息"; // "调用事件接口";
		rm.ClassMethodName = this.toString() + ".DoPush2Spec";
		// rm.Icon = BP.WF.Glo.CCFlowAppPath + "WF/Img/Btn/DTS.gif";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Message32.png";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		// map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "消息收听"; // "调用事件接口";
		rm.ClassMethodName = this.toString() + ".DoListen";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "流程完成条件"; // "流程完成条件";
		rm.ClassMethodName = this.toString() + ".DoCond";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "发送成功转向条件"; // "转向条件";
		rm.ClassMethodName = this.toString() + ".DoTurn";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.TurnToDealDoc;
		rm.RefAttrLinkLabel = "";
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "设置“接受人选择器”的人员选择范围。"; // "个性化接受人窗口"; //(访问规则设置为第05项有效)
		rm.ClassMethodName = this.toString() + ".DoAccepter";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		// 设置相关字段.
		rm.RefAttrKey = NodeAttr.DeliveryWay;
		rm.refMethodType = RefMethodType.LinkeWinOpen;
		rm.RefAttrLinkLabel = "";
		rm.Target = "_blank";
		map.AddRefMethod(rm);

		if (Glo.getOSModel() == OSModel.BPM) {
			rm = new RefMethod();
			rm.Title = "BPM模式接受人设置规则";
			rm.ClassMethodName = this.toString() + ".DoAccepterRole";
			rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";

			// 设置相关字段.
			// rm.RefAttrKey = NodeAttr.WhoExeIt;
			rm.refMethodType = RefMethodType.RightFrameOpen;
			rm.RefAttrLinkLabel = "";
			rm.Target = "_blank";
			map.AddRefMethod(rm);
		}

		rm = new RefMethod();
		rm.Title = "设置流程表单树权限";
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
		rm.ClassMethodName = this.toString() + ".DoNodeFormTree";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		map.AddRefMethod(rm);

		if (Glo.getIsEnableZhiDu()) {
			rm = new RefMethod();
			rm.Title = "对应制度章节"; // "个性化接受人窗口";
			rm.ClassMethodName = this.toString() + ".DoZhiDu";
			rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
			map.AddRefMethod(rm);

			rm = new RefMethod();
			rm.Title = "风险点"; // "个性化接受人窗口";
			rm.ClassMethodName = this.toString() + ".DoFengXianDian";
			rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
			map.AddRefMethod(rm);

			rm = new RefMethod();
			rm.Title = "岗位职责"; // "个性化接受人窗口";
			rm.ClassMethodName = this.toString() + ".DoGangWeiZhiZe";
			rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/DTS.gif";
			map.AddRefMethod(rm);
		}

		this.set_enMap(map);
		return this.get_enMap();
	}

	/**
	 * 集团部门树
	 * 
	 * @return
	 */
	public final String DoDepts() {
		
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					Glo.getCCFlowAppPath()
							+ "WF/Comm/Port/DeptTree.jsp?s=d34&FK_Flow="
							+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
							+ "&RefNo=" + DataType.getCurrentDataTime(),500, 550);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 设置流程表单树权限
	 * 
	 * @return
	 */
	public final String DoNodeFormTree() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/FlowFormTree.jsp?s=d34&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&RefNo=" + DataType.getCurrentDataTime();
	}

	/**
	 * 制度
	 * 
	 * @return
	 */
	public final String DoZhiDu() {
		try {
			PubClass.WinOpen(ContextHolderUtils.getResponse(),Glo.getCCFlowAppPath()
					+ "ZhiDu/NodeZhiDuDtl.jsp?FK_Node=" + this.getNodeID()
					+ "&FK_Flow=" + this.getFK_Flow(), "制度", "Bill", 700, 400, 200,
					300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 风险点
	 * 
	 * @return
	 */
	public final String DoFengXianDian() {
		// PubClass.WinOpen(Glo.CCFlowAppPath +
		// "ZhiDu/NodeFengXianDian.jsp?FK_Node=" + this.NodeID + "&FK_Flow=" +
		// this.FK_Flow, "制度", "Bill", 700, 400, 200, 300);
		return null;
	}

	/**
	 * 找人规则
	 * 
	 * @return
	 */
	public final String DoAccepterRole() {
		Node nd = new Node(this.getNodeID());

		if (nd.getHisDeliveryWay() != DeliveryWay.ByCCFlowBPM) {
			return Glo.getCCFlowAppPath()
					+ "WF/Admin/FindWorker/List.jsp?FK_Node="
					+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow();
		}
		// return
		// "节点访问规则您没有设置按照bpm模式，所以您能执行该操作。要想执行该操作请选择节点属性中节点规则访问然后选择按照bpm模式计算，点保存按钮。";

		return Glo.getCCFlowAppPath()
				+ "WF/Admin/FindWorker/List.jsp?FK_Node=" + this.getNodeID()
				+ "&FK_Flow=" + this.getFK_Flow();
		// return null;
	}

	public final String DoTurn() {
		return Glo.getCCFlowAppPath() + "WF/Admin/TurnTo.jsp?FK_Node="
				+ this.getNodeID();
		// , "节点完成转向处理", "FrmTurn", 800, 500, 200, 300);
		// BP.WF.Node nd = new BP.WF.Node(this.NodeID);
		// return nd.DoTurn();
	}

	/**
	 * 抄送规则
	 * 
	 * @return
	 */
	public final String DoCCRole() {
		return Glo.getCCFlowAppPath()
				+ "WF/Comm/RefFunc/UIEn.jsp?EnName=BP.WF.Template.CC.CC&PK="
				+ this.getNodeID();
		// PubClass.WinOpen("./RefFunc/UIEn.jsp?EnName=BP.WF.CC&PK=" +
		// this.NodeID, "抄送规则", "Bill", 800, 500, 200, 300);
		// return null;
	}

	/**
	 * 个性化接受人窗口
	 * 
	 * @return
	 */
	public final String DoAccepter() {
		return Glo.getCCFlowAppPath()
				+ "WF/Comm/RefFunc/UIEn.jsp?EnName=BP.WF.Template.Ext.Selector&PK="
				+ this.getNodeID();
		// return null;
	}

	/**
	 * 退回节点
	 * 
	 * @return
	 */
	public final String DoCanReturnNodes() {
		return Glo.getCCFlowAppPath() + "WF/Admin/CanReturnNodes.jsp?FK_Node="
				+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow();
	}

	/**
	 * 撤销发送的节点
	 * 
	 * @return
	 */
	public final String DoCanCancelNodes() {
		return Glo.getCCFlowAppPath() + "WF/Admin/CanCancelNodes.jsp?FK_Node="
				+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow();
	}

	/**
	 * DXReport
	 * 
	 * @return
	 */
	public final String DXReport() {
		return Glo.getCCFlowAppPath() + "WF/Admin/DXReport.jsp?FK_Node="
				+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow();
	}

	public final String DoPush2Current() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/Listen.jsp?CondType=0&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&FK_Attr=&DirType=&ToNodeID=";
	}

	public final String DoPush2Spec() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/Listen.jsp?CondType=0&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&FK_Attr=&DirType=&ToNodeID=";
	}

	/**
	 * 执行消息收听
	 * 
	 * @return
	 */
	public final String DoListen() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/Listen.jsp?CondType=0&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&FK_Attr=&DirType=&ToNodeID=";
	}

	public final String DoFeatureSet() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/FeatureSetUI.jsp?CondType=0&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&FK_Attr=&DirType=&ToNodeID=";
	}

	public final String DoShowSheets() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/ShowSheets.jsp?CondType=0&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&FK_Attr=&DirType=&ToNodeID=";
	}

	public final String DoCond() {
		return Glo.getCCFlowAppPath() + "WF/Admin/Condition.jsp?CondType="
				+ CondType.Flow.getValue() + "&FK_Flow=" + this.getFK_Flow()
				+ "&FK_MainNode=" + this.getNodeID() + "&FK_Node="
				+ this.getNodeID() + "&FK_Attr=&DirType=&ToNodeID="
				+ this.getNodeID();
	}

	/**
	 * 设计傻瓜表单
	 * 
	 * @return
	 */
	public final String DoFormCol4() {
		return Glo.getCCFlowAppPath() + "WF/MapDef/MapDef.jsp?PK=ND"
				+ this.getNodeID();
	}

	/**
	 * 设计自由表单
	 * 
	 * @return
	 */
	public final String DoFormFree() {
		return Glo.getCCFlowAppPath()
				+ "WF/MapDef/CCForm/Frm.jsp?FK_MapData=ND" + this.getNodeID()
				+ "&FK_Flow=" + this.getFK_Flow();
	}

	/**
	 * 绑定流程表单
	 * 
	 * @return
	 */
	public final String DoFormTree() {
		return Glo.getCCFlowAppPath()
				+ "WF/Admin/BindFrms.jsp?ShowType=FlowFrms&FK_Flow="
				+ this.getFK_Flow() + "&FK_Node=" + this.getNodeID()
				+ "&Lang=CH";
	}

	public final String DoMapData() throws IOException {
		int i = this.GetValIntByKey(NodeAttr.FormType);

		// 类型.
		NodeFormType type = NodeFormType.forValue(i);
		switch (type) {
		case FreeForm:
			PubClass.WinOpen(ContextHolderUtils.getResponse(),
					Glo.getCCFlowAppPath()
							+ "WF/MapDef/CCForm/Frm.jsp?FK_MapData=ND"
							+ this.getNodeID() + "&FK_Flow="
							+ this.getFK_Flow(), "设计表单", "sheet", 1024, 768, 0,
					0);
			break;
		default:
		case FixForm:
			try {
				PubClass.WinOpen(ContextHolderUtils.getResponse(),Glo.getCCFlowAppPath()
						+ "WF/MapDef/MapDef.jsp?PK=ND" + this.getNodeID(), "设计表单",
						"sheet", 800, 500, 210, 300);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		return null;
	}

	public final String DoAction() {
		return Glo.getCCFlowAppPath() + "WF/Admin/Action.jsp?NodeID="
				+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow() + "&tk="
				+ new java.util.Random().nextDouble();
	}

	/**
	 * 单据打印
	 * 
	 * @return
	 */
	public final String DoBill() {
		return Glo.getCCFlowAppPath() + "WF/Admin/Bill.jsp?NodeID="
				+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow();
	}

	/**
	 * 设置
	 * 
	 * @return
	 */
	public final String DoFAppSet() {
		return Glo.getCCFlowAppPath() + "WF/Admin/FAppSet.jsp?NodeID="
				+ this.getNodeID() + "&FK_Flow=" + this.getFK_Flow();
	}

	@Override
	protected boolean beforeUpdate() {
		// 更新流程版本
		Flow.UpdateVer(this.getFK_Flow());

		// 把工具栏的配置放入 sys_mapdata里.
		ToolbarExcel te = new ToolbarExcel("ND" + this.getNodeID());
		te.Copy(this);
		try {
			te.Update();
		} catch (java.lang.Exception e) {
		}

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 检查考核逾期处理的设置的完整性.
		String doOutTime = this.GetValStrByKey(NodeAttr.DoOutTime);
		switch (this.getHisOutTimeDeal()) {
		case AutoJumpToSpecNode:
			String[] jumps = doOutTime.split("[,]", -1);
			if (jumps.length > 2) {
				String msg = "自动跳转到相应节点,配置的内容不正确,格式应该为: Node,EmpNo , 比如: 101,zhoupeng  现在设置的格式为:"
						+ doOutTime;
				throw new RuntimeException(msg);
			}
			break;
		case AutoShiftToSpecUser:
		case RunSQL:
		case SendMsgToSpecUser:
			if (StringHelper.isNullOrEmpty(doOutTime) == false) {
				throw new RuntimeException("@在考核逾期处理方式上，您选择的是:"
						+ this.getHisOutTimeDeal() + " ,但是您没有为该规则设置内容。");
			}
			break;
		default:
			break;
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 检查考核逾期处理的设置的完整性

		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#region 处理节点数据.
		Node nd = new Node(this.getNodeID());
		if (nd.getIsStartNode() == true) {
			// 处理按钮的问题
			// 不能退回, 加签，移交，退回, 子线程.
			this.SetValByKey(BtnAttr.ReturnRole,
					ReturnRole.CanNotReturn.getValue());
			this.SetValByKey(BtnAttr.HungEnable, false);
			this.SetValByKey(BtnAttr.ThreadEnable, false); // 子线程.
		}

		if (nd.getHisRunModel() == RunModel.HL
				|| nd.getHisRunModel() == RunModel.FHL) {
			// 如果是合流点
		} else {
			this.SetValByKey(BtnAttr.ThreadEnable, false); // 子线程.
		}
		// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		// /#endregion 处理节点数据.

		// /#region 处理消息参数字段.
		// this.SetPara(NodeAttr.MsgCtrl,
		// this.GetValIntByKey(NodeAttr.MsgCtrl));
		// this.SetPara(NodeAttr.MsgIsSend,
		// this.GetValIntByKey(NodeAttr.MsgIsSend));
		// this.SetPara(NodeAttr.MsgIsReturn,
		// this.GetValIntByKey(NodeAttr.MsgIsReturn));
		// this.SetPara(NodeAttr.MsgIsShift,
		// this.GetValIntByKey(NodeAttr.MsgIsShift));
		// this.SetPara(NodeAttr.MsgIsCC,
		// this.GetValIntByKey(NodeAttr.MsgIsCC));

		// this.SetPara(NodeAttr.MsgMailEnable,
		// this.GetValIntByKey(NodeAttr.MsgMailEnable));
		// this.SetPara(NodeAttr.MsgMailTitle,
		// this.GetValStrByKey(NodeAttr.MsgMailTitle));
		// this.SetPara(NodeAttr.MsgMailDoc,
		// this.GetValStrByKey(NodeAttr.MsgMailDoc));

		// this.SetPara(NodeAttr.MsgSMSEnable,
		// this.GetValIntByKey(NodeAttr.MsgSMSEnable));
		// this.SetPara(NodeAttr.MsgSMSDoc,
		// this.GetValStrByKey(NodeAttr.MsgSMSDoc));
		// /#endregion

		// 清除所有的缓存.
		BP.DA.CashEntity.getDCash().clear();

		return super.beforeUpdate();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion
}