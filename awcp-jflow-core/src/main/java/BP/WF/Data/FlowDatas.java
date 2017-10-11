package BP.WF.Data;

import BP.En.*;
import BP.Sys.*;

/** 
 报表集合
 
*/
public class FlowDatas extends BP.En.EntitiesOID
{
	/** 
	 报表集合
	 
	*/
	public FlowDatas()
	{
	}

	@Override
	public Entity getGetNewEntity()
	{
		return new FlowData();
	}
}