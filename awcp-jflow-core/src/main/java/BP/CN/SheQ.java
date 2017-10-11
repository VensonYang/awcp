package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.Depositary;
import BP.En.AdjunctType;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

public class SheQ extends EntityNoName{

	public final String getFK_QX(){
		return this.GetValStrByKey(SheQAttr.FK_QX);
	}
	public final void setFK_QX(String val){
		this.SetValByKey(SheQAttr.FK_QX, val);
	}
	
	public final String getFK_JD(){
		return this.GetValStrByKey(SheQAttr.FK_JD);
	}
	public final void setFK_JD(String val){
		this.SetValByKey(SheQAttr.FK_JD, val);
	}
	
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	
	public SheQ()
	{
	}
	public SheQ(String no)
	{
		super(no);
	}
	
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("TX_SQ");
		map.setAdjunctType(AdjunctType.AllType);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity(Depositary.None);
		map.setIsCheckNoLength(false);
		map.setEnDesc("社区");
		map.setEnType(EnType.App);
		map.setCodeStruct("2");
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 字段
		map.AddTBStringPK(SheQAttr.No, null, "编号", true, true, 0, 50, 50);
		map.AddTBString(SheQAttr.Name, null, "名称", true, false, 0, 50, 200);

		map.AddDDLEntities(SheQAttr.FK_JD, null, "街道", new JieDs(), true);
		map.AddDDLEntities(SheQAttr.FK_QX, null, "区域", new QuYs(), true);
		map.AddSearchAttr(SheQAttr.FK_QX);
		map.AddSearchAttr(SheQAttr.FK_JD);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

		this.set_enMap(map);
		return this.get_enMap();
	}

}
