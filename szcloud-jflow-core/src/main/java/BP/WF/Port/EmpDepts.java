package BP.WF.Port;

import BP.DA.*;
import BP.En.*;

/** 
 操作员与工作部门 
 
*/
public class EmpDepts extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 工作人员与部门集合
	 
	*/
	public EmpDepts()
	{
	}
	/** 
	 工作人员与部门集合
	 
	 @param FK_Emp FK_Emp
	*/
	public EmpDepts(String FK_Emp)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(EmpDeptAttr.FK_Emp, FK_Emp);
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
		return new EmpDept();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}