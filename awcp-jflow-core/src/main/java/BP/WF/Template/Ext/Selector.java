package BP.WF.Template.Ext;

import BP.DA.Depositary;
import BP.En.Entity;
import BP.En.Map;
import BP.En.UAC;
import BP.Port.WebUser;
import BP.WF.Port.DeptAttr;
import BP.WF.Port.EmpDeptAttr;
import BP.WF.Template.NodeStationAttr;
import BP.WF.Template.NodeStations;
import BP.WF.Template.SelectorDBShowWay;
import BP.WF.Template.AccepterRole.NodeDeptAttr;
import BP.WF.Template.AccepterRole.NodeDepts;
import BP.WF.Template.AccepterRole.NodeEmpAttr;
import BP.WF.Template.AccepterRole.NodeEmps;

/**
 * 选择器
 */
public class Selector extends Entity {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 基本属性
	@Override
	public String getPK() {
		return "NodeID";
	}

	/**
	 * 显示方式
	 */
	public final SelectorDBShowWay getSelectorDBShowWay() {
		// return
		// (SelectorDBShowWay)this.GetValIntByKey(SelectorAttr.SelectorDBShowWay);
		return SelectorDBShowWay.forValue(this
				.GetValIntByKey(SelectorAttr.SelectorDBShowWay));
	}

	public final void setSelectorDBShowWay(SelectorDBShowWay value) {
		this.SetValByKey(SelectorAttr.SelectorDBShowWay, value.getValue());
	}

	/**
	 * 选择模式
	 */
	public final SelectorModel getSelectorModel() {
		// return (SelectorModel)
		// this.GetValIntByKey(SelectorAttr.SelectorModel);
		return SelectorModel.forValue(this
				.GetValIntByKey(SelectorAttr.SelectorModel));
	}

	public final void setSelectorModel(SelectorModel value) {
		this.SetValByKey(SelectorAttr.SelectorModel, value.getValue());
	}

	public final String getSelectorP1() {
		String s = this.GetValStringByKey(SelectorAttr.SelectorP1);
		s = s.replace("~", "'");
		return s;
	}

	public final void setSelectorP1(String value) {
		this.SetValByKey(SelectorAttr.SelectorP1, value);
	}

	public final String getSelectorP2() {
		String s = this.GetValStringByKey(SelectorAttr.SelectorP2);
		s = s.replace("~", "'");
		return s;
		// return this.GetValStringByKey(SelectorAttr.SelectorP2);
	}

	public final void setSelectorP2(String value) {
		this.SetValByKey(SelectorAttr.SelectorP2, value);
	}

	public final int getNodeID() {
		return this.GetValIntByKey(SelectorAttr.NodeID);
	}

	public final void setNodeID(int value) {
		this.SetValByKey(SelectorAttr.NodeID, value);
	}

	/**
	 * UI界面上的访问控制
	 */
	@Override
	public UAC getHisUAC() {
		UAC uac = new UAC();
		uac.IsDelete = false;
		uac.IsInsert = false;
		uac.IsView = false;
		if (WebUser.getNo().equals("admin")) {
			uac.IsUpdate = true;
			uac.IsView = true;
		}
		return uac;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造方法
	/**
	 * Accpter
	 */
	public Selector() {
	}

	/**
	 * @param nodeid
	 */
	public Selector(int nodeid) {
		this.setNodeID(nodeid);
		this.Retrieve();
	}

	/**
	 * 重写基类方法
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map("WF_Node");
		map.setEnDesc("选择器");

		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);

		map.AddTBIntPK(SelectorAttr.NodeID, 0, "NodeID", true, true);
		map.AddTBString(SelectorAttr.Name, null, "节点名称", true, true, 0, 100,
				100);

		map.AddDDLSysEnum(SelectorAttr.SelectorDBShowWay, 0, "数据显示方式", true,
				true, SelectorAttr.SelectorDBShowWay, "@0=表格显示@1=树形显示");
		map.AddDDLSysEnum(SelectorAttr.SelectorModel, 0, "窗口模式", true, true,
				SelectorAttr.SelectorModel,
				"@0=按岗位@1=按部门@2=按人员@3=按SQL@4=自定义Url");

		// map.AddTBString(SelectorAttr.SelectorP1, null, "参数1", true, false, 0,
		// 500, 10, true);
		// map.AddTBString(SelectorAttr.SelectorP2, null, "参数2", true, false, 0,
		// 500, 10, true);

		map.AddTBStringDoc(SelectorAttr.SelectorP1, null, "参数1", true, false,
				true);
		map.AddTBStringDoc(SelectorAttr.SelectorP2, null, "参数2", true, false,
				true);

		// 相关功能。
		map.getAttrsOfOneVSM().Add(new NodeStations(),
				new BP.WF.Port.Stations(), NodeStationAttr.FK_Node,
				NodeStationAttr.FK_Station, DeptAttr.Name, DeptAttr.No, "节点岗位");

		map.getAttrsOfOneVSM().Add(new NodeDepts(), new BP.WF.Port.Depts(),
				NodeDeptAttr.FK_Node, NodeDeptAttr.FK_Dept, DeptAttr.Name,
				DeptAttr.No, "节点部门");

		map.getAttrsOfOneVSM().Add(new NodeEmps(), new BP.WF.Port.Emps(),
				NodeEmpAttr.FK_Node, EmpDeptAttr.FK_Emp, DeptAttr.Name,
				DeptAttr.No, "接受人员");

		this.set_enMap(map);
		return this.get_enMap();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion
}