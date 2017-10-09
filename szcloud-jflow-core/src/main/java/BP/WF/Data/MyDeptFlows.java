package BP.WF.Data;

import BP.En.Entities;
import BP.En.Entity;

/** 
我部门的流程s

*/
public class MyDeptFlows extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MyDeptFlow();
	}
	/** 
	 我部门的流程集合
	 
	*/
	public MyDeptFlows()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}