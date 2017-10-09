package BP.GPM;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

/**
 * 岗位
 */
public class Station extends EntityNoName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 属性
	public final String getFK_StationType() {
		return this.GetValStrByKey("FK_StationType");
	}
	
	public final void setFK_StationType(String value){
		this.SetValByKey(StationAttr.FK_StationType, value);
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 实现基本的方方法
	@Override
	public UAC getHisUAC() {
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造方法
	/**
	 * 岗位
	 */
	public Station() {
	}

	/**
	 * 岗位
	 * 
	 * @param _No
	 */
	public Station(String _No) {
		super(_No);
	}

	/**
	 * EnMap
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map("Port_Station");
		map.setEnDesc("岗位"); // "岗位";
		map.setEnType(EnType.Admin);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity(Depositary.Application);
		map.setCodeStruct("2222222"); // 最大级别是 7.

		map.AddTBStringPK(EmpAttr.No, null, "编号", true, false, 1, 20, 100);
		map.AddTBString(EmpAttr.Name, null, "名称", true, false, 0, 100, 100);

		map.AddTBStringDoc(StationAttr.DutyReq, null, "职责要求", true, false);
		map.AddTBStringDoc(StationAttr.Makings, null, "素质要求", true, false);

		map.AddDDLEntities(StationAttr.FK_StationType, null, "类型",
				new StationTypes(), true);

		map.AddSearchAttr(StationAttr.FK_StationType);
		this.set_enMap(map);
		return this.get_enMap();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion
}
