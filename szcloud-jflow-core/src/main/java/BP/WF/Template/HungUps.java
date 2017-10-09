package BP.WF.Template;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.WF.Template.*;
import BP.Port.*;

/** 
 挂起
 
*/
public class HungUps extends EntitiesMyPK
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new HungUp();
	}
	/** 
	 挂起
	 
	*/
	public HungUps()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}