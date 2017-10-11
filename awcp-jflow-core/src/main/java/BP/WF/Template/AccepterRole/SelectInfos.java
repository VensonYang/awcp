package BP.WF.Template.AccepterRole;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/** 
 接受人信息
 
*/
public class SelectInfos extends EntitiesMyPK
{
	/** 
	 接受人信息
	 
	*/
	public SelectInfos()
	{
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SelectInfo();
	}
}