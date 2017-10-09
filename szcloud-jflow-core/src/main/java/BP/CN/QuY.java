package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.Depositary;
import BP.En.AdjunctType;
import BP.En.EnType;
import BP.En.Entities;
import BP.En.EntitiesNoName;
import BP.En.Entity;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

public class QuY extends EntityNoName{

	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	
	/** 
	 片区	
	*/
	public QuY()
	{
	}
	public QuY(String no)
	{
		super(no);
	}
	


	@Override
	public Map getEnMap() {
		if (this.get_enMap()!=null)
		{
			return this.get_enMap();
		}
		Map map = new Map();

		///#region 基本属性
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("TX_QX");
		map.setAdjunctType(AdjunctType.AllType);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity(Depositary.None);
		map.setIsCheckNoLength(false);
		map.setEnDesc("区域");
		map.setEnType(EnType.App);
		map.setIsAutoGenerNo(true); //是否自动生成编号.
		map.setCodeStruct("2");
		
		///#region 字段
		map.AddTBStringPK(PQAttr.No,null,"编号",true,true,0,50,50);
		map.AddTBString(PQAttr.Name,null,"名称",true,false,0,50,200);
		

		this.set_enMap(map);
		return this.get_enMap();
		
	}
	
	public Entities getGetNewEntities()
	{
		return new QuYs();
	}

}
