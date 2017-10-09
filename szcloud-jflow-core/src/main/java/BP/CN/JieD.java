package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.Depositary;
import BP.En.AdjunctType;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

public class JieD extends EntityNoName{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public final String getFK_QX(){
		return this.GetValStrByKey(JieDAttr.FK_QX);
	}
	
	public final void setFK_QX(String val){
		this.SetValByKey(JieDAttr.FK_QX, val);
	}
	
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	
	public JieD()
	{
	}
	public JieD(String no)
	{
		super(no);
	}
	
	@Override
	public Map getEnMap() {
		if(this.get_enMap()!=null){
			return this.get_enMap();
		}
		
		Map map=new Map();
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("TX_JD");
		map.setAdjunctType(AdjunctType.AllType);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity(Depositary.None);
		map.setIsCheckNoLength(false);
		map.setEnDesc("街道");
		map.setEnType(EnType.App);
		map.setCodeStruct("2");
		

		map.AddTBStringPK(JieDAttr.No, null, "编号", true, true, 2, 2, 2);
		map.AddTBString(JieDAttr.Name, null, "名称", true, false, 0, 50, 200);
		map.AddDDLEntities(JieDAttr.FK_QX, null, "区域", new QuYs(), true);
		

		this.set_enMap(map);
		return this.get_enMap();
	}

}
