package BP.WF.Data;

import BP.En.Entities;
import BP.En.Entity;

/** 
流程监控s

*/
public class Monitors extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Monitor();
	}
	/** 
	 流程监控集合
	 
	*/
	public Monitors()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
