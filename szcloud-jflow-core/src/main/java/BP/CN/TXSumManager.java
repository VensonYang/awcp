package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.En.Entity;
import BP.En.Map;
import BP.En.UAC;

public class TXSumManager extends Entity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final String getHuShu()
	{
		return this.GetValStrByKey(TXSumManagerAttr.HuShu);
	}
	
	public final String getJinE()
	{
		return this.GetValStrByKey(TXSumManagerAttr.JinE);
	}
	public final String getDuiXiangLeiXing()
	{
		return this.GetValStrByKey(TXSumManagerAttr.DuiXiangLeiXing);
	}
	public final String getFK_QX1()
	{
		return this.GetValStrByKey(TXSumManagerAttr.FK_QX1);
	}

	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}
	
	public Map getEnMap() {
		
		if (this.get_enMap()!=null)
		{
			return this.get_enMap();
		}
		Map map = new Map();
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("view_tx_sum");
		
		this.set_enMap(map);

		map.AddDDLEntities(TXSumManagerAttr.FK_QX1, null, "区县",new QuYs(), true);
		map.AddTBStringPK(TXSumManagerAttr.DuiXiangLeiXing, null, "对象类型", true, true, 0, 50, 50);
		map.AddTBString(TXSumManagerAttr.HuShu, null, "户数", true, false, 0, 50, 200);
		map.AddTBString(TXSumManagerAttr.JinE, null, "金额", true, false, 0, 50, 200);
	
		return this.get_enMap();
	}

}
