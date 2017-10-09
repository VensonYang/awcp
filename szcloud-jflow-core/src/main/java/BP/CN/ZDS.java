package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.Depositary;
import BP.En.AdjunctType;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

/** 
洲地市

*/
public class ZDS extends EntityNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 基本属性
	public final String getNameS()
	{
		return this.GetValStrByKey(ZDSAttr.NameS);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 构造函数
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	/** 
	 洲地市
	 		
	*/
	public ZDS()
	{
	}
	public ZDS(String no)
	{
		super(no);
	}
	/** 
	 Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("CN_ZDS");
		map.setAdjunctType(AdjunctType.AllType);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity(Depositary.None);
		map.setIsCheckNoLength(false);
		map.setEnDesc("洲地市");
		map.setEnType(EnType.App);
		map.setCodeStruct("4");
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 字段
		map.AddTBStringPK(ZDSAttr.No, null, "编号", true, false, 0, 50, 50);
		map.AddTBString(ZDSAttr.Name, null, "名称", true, false, 0, 50, 200);
		map.AddTBString(ZDSAttr.NameS, null, "名称s", true, false, 0, 50, 200);


		map.AddTBString(ZDSAttr.FK_SF, null, "FK_SF", true, false, 0, 50, 200);

		map.AddDDLEntities(ZDSAttr.FK_PQ, null, "片区", new PQs(), true);
		  //  map.AddDDLEntities(ZDSAttr.FK_ZDS, null, "省份", new SFs(), true);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

}

