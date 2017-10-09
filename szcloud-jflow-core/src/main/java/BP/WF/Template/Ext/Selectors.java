package BP.WF.Template.Ext;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

/** 
 Accpter
 
*/
public class Selectors extends Entities
{
	/** 
	 Accpter
	 
	*/
	public Selectors()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Selector();
	}
}