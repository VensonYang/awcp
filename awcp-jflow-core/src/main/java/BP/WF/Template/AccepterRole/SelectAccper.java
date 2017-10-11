package BP.WF.Template.AccepterRole;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/**
 * 选择接受人 节点的到人员有两部分组成. 记录了从一个节点到其他的多个节点. 也记录了到这个节点的其他的节点.
 */
public class SelectAccper extends EntityMyPK {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 基本属性
	/**
	 * 工作ID
	 */
	public final long getWorkID() {
		return this.GetValInt64ByKey(SelectAccperAttr.WorkID);
	}

	public final void setWorkID(long value) {
		this.SetValByKey(SelectAccperAttr.WorkID, value);
	}

	/**
	 * 节点
	 */
	public final int getFK_Node() {
		return this.GetValIntByKey(SelectAccperAttr.FK_Node);
	}

	public final void setFK_Node(int value) {
		this.SetValByKey(SelectAccperAttr.FK_Node, value);
	}

	/**
	 * 到人员
	 */
	public final String getFK_Emp() {
		return this.GetValStringByKey(SelectAccperAttr.FK_Emp);
	}

	public final void setFK_Emp(String value) {
		this.SetValByKey(SelectAccperAttr.FK_Emp, value);
	}

	/**
	 * 标记
	 */
	public final String getTag() {
		return this.GetValStringByKey(SelectAccperAttr.Tag);
	}

	public final void setTag(String value) {
		this.SetValByKey(SelectAccperAttr.Tag, value);
	}

	/**
	 * 人员名称
	 */
	public final String getEmpName() {
		String s = this.GetValStringByKey(SelectAccperAttr.EmpName);
		if (s.equals("") || s == null) {
			s = this.getFK_Emp();
		}
		return s;
	}

	public final void setEmpName(String value) {
		this.SetValByKey(SelectAccperAttr.EmpName, value);
	}

	/**
	 * 接收人
	 */
	public final String getRec() {
		return this.GetValStringByKey(SelectAccperAttr.Rec);
	}

	public final void setRec(String value) {
		this.SetValByKey(SelectAccperAttr.Rec, value);
	}

	/**
	 * 办理意见 信息
	 */
	public final String getInfo() {
		return this.GetValStringByKey(SelectAccperAttr.Info);
	}

	public final void setInfo(String value) {
		this.SetValByKey(SelectAccperAttr.Info, value);
	}

	/**
	 * 是否记忆
	 */
	public final boolean getIsRemember() {
		return this.GetValBooleanByKey(SelectAccperAttr.IsRemember);
	}

	public final void setIsRemember(boolean value) {
		this.SetValByKey(SelectAccperAttr.IsRemember, value);
	}

	/**
	 * 顺序号
	 */
	public final int getIdx() {
		return this.GetValIntByKey(SelectAccperAttr.Idx);
	}

	public final void setIdx(int value) {
		this.SetValByKey(SelectAccperAttr.Idx, value);
	}

	/**
	 * 类型(@0=接受人@1=抄送人)
	 */
	public final int getAccType() {
		return this.GetValIntByKey(SelectAccperAttr.AccType);
	}

	public final void setAccType(int value) {
		this.SetValByKey(SelectAccperAttr.AccType, value);
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造方法
	/**
	 * 选择接受人
	 */
	public SelectAccper() {
	}

	public SelectAccper(String mypk) {
		this.setMyPK(mypk);
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

		Map map = new Map("WF_SelectAccper");
		map.setEnDesc("选择接受/抄送人信息");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.AddMyPK();

		map.AddTBInt(SelectAccperAttr.FK_Node, 0, "接受人节点", true, false);
		map.AddTBInt(SelectAccperAttr.WorkID, 0, "WorkID", true, false);
		map.AddTBString(SelectAccperAttr.FK_Emp, null, "FK_Emp", true, false,
				0, 20, 10);
		map.AddTBString(SelectAccperAttr.EmpName, null, "EmpName", true, false,
				0, 20, 10);

		map.AddTBInt(SelectAccperAttr.AccType, 0, "类型(@0=接受人@1=抄送人)", true,
				false);
		map.AddTBString(SelectAccperAttr.Rec, null, "记录人", true, false, 0, 20,
				10);
		map.AddTBString(SelectAccperAttr.Info, null, "办理意见信息", true, false, 0,
				200, 10);

		map.AddTBInt(SelectAccperAttr.IsRemember, 0, "以后发送是否按本次计算", true, false);
		map.AddTBInt(SelectAccperAttr.Idx, 0, "顺序号(可以用于流程队列审核模式)", true, false);
		//
		// * add 2015-1-12.
		// * 为了解决多维度的人员问题.
		// * 在分流点向下发送时, 一个人可以分配两次任务，但是这个任务需要一个维度来区分。
		// * 这个维度，有可能是一个类别，批次。
		//
		map.AddTBString(SelectAccperAttr.Tag, null, "维度信息Tag", true, false, 0,
				200, 10);

		this.set_enMap(map);
		return this.get_enMap();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	@Override
	protected boolean beforeInsert() {
		this.ResetPK();
		return super.beforeInsert();
	}

	public final void ResetPK() {
		this.setMyPK(this.getFK_Node() + "_" + this.getWorkID() + "_"
				+ this.getFK_Emp());
	}

	@Override
	protected boolean beforeUpdateInsertAction() {
		this.ResetPK();
		this.setRec(WebUser.getNo());
		return super.beforeUpdateInsertAction();
	}
	// protected override bool beforeUpdateInsertAction()
	// {
	// this.Rec = BP.Web.WebUser.No;
	// return base.beforeUpdateInsertAction();
	// }
}