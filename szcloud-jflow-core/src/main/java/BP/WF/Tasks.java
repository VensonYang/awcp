package BP.WF;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.Port.*;

/** 
 任务
 
*/
public class Tasks extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Task();
	}
	/** 
	 任务
	 
	*/
	public Tasks()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}