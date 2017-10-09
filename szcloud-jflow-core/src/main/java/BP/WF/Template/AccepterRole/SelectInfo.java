package BP.WF.Template.AccepterRole;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/**
 * 接受人信息
 */
public class SelectInfo extends EntityMyPK {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 基本属性
	/**
	 * 工作ID
	 */
	public final long getWorkID() {
		return this.GetValInt64ByKey(SelectInfoAttr.WorkID);
	}

	public final void setWorkID(long value) {
		this.SetValByKey(SelectInfoAttr.WorkID, value);
	}

	/**
	 * 选择节点
	 */
	public final int getAcceptNodeID() {
		return this.GetValIntByKey(SelectInfoAttr.AcceptNodeID);
	}

	public final void setAcceptNodeID(int value) {
		this.SetValByKey(SelectInfoAttr.AcceptNodeID, value);
	}

	public final int getAccType() {
		return this.GetValIntByKey(SelectInfoAttr.AccType);
	}

	public final void setAccType(int value) {
		this.SetValByKey(SelectInfoAttr.AccType, value);
	}

	/**
	 * 信息
	 */
	public final String getInfo() {
		return this.GetValStringByKey(SelectInfoAttr.InfoLeft);
	}

	public final void setInfo(String value) {
		this.SetValByKey(SelectInfoAttr.InfoLeft, value);
	}

	public final String getInfoCenter() {
		return this.GetValStringByKey(SelectInfoAttr.InfoCenter);
	}

	public final void setInfoCenter(String value) {
		this.SetValByKey(SelectInfoAttr.InfoCenter, value);
	}

	public final String getInfoRight() {
		return this.GetValStringByKey(SelectInfoAttr.InfoRight);
	}

	public final void setInfoRight(String value) {
		this.SetValByKey(SelectInfoAttr.InfoRight, value);
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造方法
	/**
	 * 接受人信息
	 */
	public SelectInfo() {
	}

	/**
	 * 重写基类方法
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map("WF_SelectInfo");
		map.setEnDesc ( "选择接受/抄送人节点信息");

		map.setDepositaryOfEntity ( Depositary.None);
		map.setDepositaryOfMap ( Depositary.Application);
		map.AddMyPK();
		map.AddTBInt(SelectInfoAttr.AcceptNodeID, 0, "接受节点", true, false);
		map.AddTBInt(SelectInfoAttr.WorkID, 0, "工作ID", true, false);
		map.AddTBString(SelectInfoAttr.InfoLeft, null, "InfoLeft", true, false,
				0, 200, 10);
		map.AddTBString(SelectInfoAttr.InfoCenter, null, "InfoCenter", true,
				false, 0, 200, 10);
		map.AddTBString(SelectInfoAttr.InfoRight, null, "InfoLeft", true,
				false, 0, 200, 10);
		map.AddTBInt(SelectAccperAttr.AccType, 0, "类型(@0=接受人@1=抄送人)", true,
				false);

		this.set_enMap ( map);
		return this.get_enMap();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	@Override
	protected boolean beforeUpdateInsertAction() {
		this.setMyPK ( this.getAcceptNodeID() + "_" + this.getWorkID() + "_"
				+ this.getAccType());
		;
		return super.beforeUpdateInsertAction();
	}
}