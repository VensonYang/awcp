package BP.GPM;

import BP.En.Entities;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 部门查询权限 
 
*/
public class DeptSearchScorps extends Entities
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 部门查询权限
	 
	*/
	public DeptSearchScorps()
	{
	}
	/** 
	 部门查询权限
	 
	 @param FK_Emp FK_Emp
	*/
	public DeptSearchScorps(String FK_Emp)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(DeptSearchScorpAttr.FK_Emp, FK_Emp);
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
		return new DeptSearchScorp();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}