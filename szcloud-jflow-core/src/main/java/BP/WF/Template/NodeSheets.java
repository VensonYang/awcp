package BP.WF.Template;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;

/** 
 节点集合
 
*/
public class NodeSheets extends Entities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 节点集合
	 
	*/
	public NodeSheets()
	{
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<NodeSheet> convertNodeSheets(Object obj) {
		return (ArrayList<NodeSheet>) obj;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	public Entity getGetNewEntity()
	{
		return new NodeSheet();
	}
}