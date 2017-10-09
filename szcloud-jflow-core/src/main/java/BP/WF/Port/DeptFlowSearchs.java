package BP.WF.Port;

import BP.DA.*;
import BP.En.*;

/** 
 流程部门数据查询权限 
 
*/
public class DeptFlowSearchs extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 流程部门数据查询权限
	 
	*/
	public DeptFlowSearchs()
	{
	}
	/** 
	 流程部门数据查询权限
	 
	 @param FK_Emp FK_Emp
	*/
	public DeptFlowSearchs(String FK_Emp)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(DeptFlowSearchAttr.FK_Emp, FK_Emp);
		qo.DoQuery();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new DeptFlowSearch();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}