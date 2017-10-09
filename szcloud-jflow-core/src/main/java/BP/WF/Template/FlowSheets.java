package BP.WF.Template;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;
import BP.En.QueryObject;
import BP.WF.Template.PubLib.FlowAttr;

/** 
 流程集合
 
*/
public class FlowSheets extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询
	public static final ArrayList<FlowSheet> convertFlowSheets(Object objct){
		return (ArrayList<FlowSheet>) objct;
	}
	/** 
	 查询出来全部的在生存期间内的流程
	 
	 @param FlowSort 流程类别
	 @param IsCountInLifeCycle 是不是计算在生存期间内 true 查询出来全部的 
	*/
	public final int Retrieve(String FlowSort)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FlowAttr.FK_FlowSort, FlowSort);
		qo.addOrderBy(FlowAttr.No);
		return qo.DoQuery();
	}
		///#endregion
		///#region 构造方法
	/** 
	 工作流程
	 
	*/
	public FlowSheets()
	{
	}
	/** 
	 工作流程
	 
	 @param fk_sort
	*/
	public FlowSheets(String fk_sort)
	{
		this.Retrieve(FlowAttr.FK_FlowSort, fk_sort);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 得到实体
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FlowSheet();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}