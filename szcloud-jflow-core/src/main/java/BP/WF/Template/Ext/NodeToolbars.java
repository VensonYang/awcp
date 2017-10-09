package BP.WF.Template.Ext;

import java.util.ArrayList;

import BP.En.EntitiesOID;
import BP.En.Entity;

/** 
 工具栏集合
 
*/
public class NodeToolbars extends EntitiesOID
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	
	public static ArrayList<NodeToolbar> convertNodeToolbars(Object obj) {
		return (ArrayList<NodeToolbar>) obj;
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new NodeToolbar();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 工具栏集合
	 
	*/
	public NodeToolbars()
	{
	}
	/** 
	 工具栏集合.
	 
	 @param fk_node
	*/
	public NodeToolbars(String fk_node)
	{
		this.Retrieve(NodeToolbarAttr.FK_Node, fk_node);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}