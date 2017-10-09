package BP.WF.Port;

import BP.DA.*;
import BP.En.*;
import BP.Web.*;
import BP.Sys.*;
import BP.WF.Port.*;

/** 
 消息s
  
*/
public class SMSs extends Entities
{
	@Override
	public Entity getGetNewEntity()
	{
		return new SMS();
	}
	public SMSs()
	{
	}
}