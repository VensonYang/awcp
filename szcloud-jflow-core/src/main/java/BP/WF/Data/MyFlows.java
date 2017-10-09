package BP.WF.Data;

import BP.En.Entities;
import BP.En.Entity;

/** 
我参与的流程s

*/
public class MyFlows extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MyFlow();
	}
	/** 
	 我参与的流程集合
	 
	*/
	public MyFlows()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}