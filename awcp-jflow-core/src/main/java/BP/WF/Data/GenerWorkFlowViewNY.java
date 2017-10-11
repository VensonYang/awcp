package BP.WF.Data;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;

/** 
月份

*/
public class GenerWorkFlowViewNY extends EntityNoName
{

		///#region 构造方法
	/** 
	 月份
	 
	*/
	public GenerWorkFlowViewNY()
	{
	}
	/** 
	 月份
	 
	 @param mypk
	*/
	public GenerWorkFlowViewNY(String no)
	{
		this.setNo(no);
		this.Retrieve();
	}
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Pub_NY");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("月份");
		map.setEnType(EnType.View);

		map.AddTBStringPK(GenerWorkFlowViewNYAttr.No, null, "编号", true, false, 2, 30, 20);
		map.AddTBString(GenerWorkFlowViewNYAttr.Name, null, "名称", true, false, 0, 3900, 20);
		this.set_enMap(map);
		return this.get_enMap();
	}

	//#endregion

}