package BP.WF.Template;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.WF.Port.*;

/** 
 Btn
 
*/
public class BtnLabs extends Entities
{
	/** 
	 Btn
	 
	*/
	public BtnLabs()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new BtnLab();
	}
}