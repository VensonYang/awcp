package BP.WF.Template.Form.Flow;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;

/** 
 流程表单树
 
*/
public class FlowFormTrees extends EntitiesMultiTree
{
	/** 
	 流程表单树s
	 
	*/
	public FlowFormTrees()
	{
	}
	/** 
	 流程表单树
	 
	*/
	public FlowFormTrees(String flowNo)
	{
	   int i= this.Retrieve(FlowFormTreeAttr.FK_Flow, flowNo);
	   if (i == 0)
	   {
		   FlowFormTree tree = new FlowFormTree();
		   tree.setNo ( "100");
		   tree.setFK_Flow(flowNo);
		   tree.setName("根目录");
		   tree.setIsDir ( false);
		   tree.setParentNo( "0");
		   tree.Insert();

		   //创建一个节点.
		   tree.DoCreateSubNode();
	   }
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FlowFormTree();
	}

}