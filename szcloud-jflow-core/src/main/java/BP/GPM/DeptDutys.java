package BP.GPM;

import BP.En.Entities;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 部门职务 
 
*/
public class DeptDutys extends Entities
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 部门职务
	 
	*/
	public DeptDutys()
	{
	}
	/** 
	 工作人员与职务集合
	 
	*/
	public DeptDutys(String DutyNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(DeptDutyAttr.FK_Duty, DutyNo);
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
		return new DeptDuty();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}