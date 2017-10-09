package BP.Sys;

import BP.En.EntitiesOID;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 实体集合
 
*/
public class GroupEnsTemplates extends EntitiesOID
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	public GroupEnsTemplates()
	{
	}
	/** 
	 
	 
	 @param emp
	*/
	public GroupEnsTemplates(String emp)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(GroupEnsTemplateAttr.Rec, emp);
		qo.addOr();
		qo.AddWhere(GroupEnsTemplateAttr.Rec, "admin");
		qo.DoQuery();

	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new GroupEnsTemplate();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}