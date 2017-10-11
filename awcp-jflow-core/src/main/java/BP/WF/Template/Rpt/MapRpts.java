package BP.WF.Template.Rpt;

import BP.DA.*;
import BP.Port.*;
import BP.En.*;
import BP.WF.*;
import BP.Sys.*;
import BP.Sys.Frm.MapData;

/** 
 报表设计s
 
*/
public class MapRpts extends EntitiesMyPK
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 报表设计s
	 
	*/
	public MapRpts()
	{
	}
	/** 
	 报表设计s
	 
	 @param fk_flow 流程编号
	*/
	public MapRpts(String fk_flow)
	{
		String fk_Mapdata = "ND" + Integer.parseInt(fk_flow) + "Rpt";
		int i = this.Retrieve(MapRptAttr.ParentMapData, fk_Mapdata);
		if (i == 0)
		{
			MapData mapData = new MapData(fk_Mapdata);
			mapData.setNo(  "ND" + Integer.parseInt(fk_flow) + "MyRpt");
			mapData.setName("我的流程");
			mapData.setNote ( "系统自动生成.");
			mapData.Insert();

			MapRpt rpt = new MapRpt(mapData.getNo());
			rpt.setParentMapData(fk_Mapdata);
			rpt.ResetIt();


			rpt.Update();
		}
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MapRpt();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}