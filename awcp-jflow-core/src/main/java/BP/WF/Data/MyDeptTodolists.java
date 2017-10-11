package BP.WF.Data;

import BP.En.Entities;
import BP.En.Entity;

/** 
我部门的待办s

*/
public class MyDeptTodolists extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new MyDeptTodolist();
	}
	/** 
	 我部门的待办集合
	 
	*/
	public MyDeptTodolists()
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}