package BP.Sys;

import BP.En.Entities;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 实体集合
 
*/
public class RptTemplates extends Entities
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	public RptTemplates()
	{
	}

	/** 
	 查询
	 
	 @param EnsName
	*/
	public RptTemplates(String EnsName)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(RptTemplateAttr.EnsName, EnsName);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new RptTemplate();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}