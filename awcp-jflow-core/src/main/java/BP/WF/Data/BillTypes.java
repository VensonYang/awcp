package BP.WF.Data;

import BP.DA.*;
import BP.En.*;

/** 
 单据类型
 
*/
public class BillTypes extends SimpleNoNames
{
	/** 
	 单据类型s
	 
	*/
	public BillTypes()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new BillType();
	}
}