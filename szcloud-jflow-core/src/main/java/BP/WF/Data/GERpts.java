package BP.WF.Data;

import BP.En.*;
import BP.WF.Template.*;
import BP.Sys.*;

/** 
 报表集合
 
*/
public class GERpts extends BP.En.EntitiesOID
{
	/** 
	 报表集合
	 
	*/
	public GERpts()
	{
	}

	@Override
	public Entity getGetNewEntity()
	{
		return new GERpt();
	}
}