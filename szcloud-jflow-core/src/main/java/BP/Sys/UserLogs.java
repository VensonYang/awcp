package BP.Sys;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 用户日志s
 
*/
public class UserLogs extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	public UserLogs()
	{
	}
	/** 
	 
	 
	 @param emp
	*/
	public UserLogs(String emp)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(UserLogAttr.FK_Emp, emp);
		qo.DoQuery();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 重写
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new UserLog();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}