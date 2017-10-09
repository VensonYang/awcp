package BP.WF.Data;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.WF.Template.Direction;
import BP.WF.Template.Node;

/** 
 单据模板s
 
*/
public class BillTemplates extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new BillTemplate();
	}
	/** 
	 单据模板
	 
	*/
	public BillTemplates()
	{
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<BillTemplate> convertBillTemplates(Object obj) {
		return (ArrayList<BillTemplate>) obj;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询与构造
	/** 
	 按节点查询
	 
	 @param nd
	*/
	public BillTemplates(Node nd)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(BillTemplateAttr.NodeID, nd.getNodeID());
		if (nd.getIsStartNode())
		{
			qo.addOr();
			qo.AddWhere("No", "SLHZ");
		}
		qo.DoQuery();
	}
	/** 
	 按流程查询
	 
	 @param fk_flow 流程编号
	*/
	public BillTemplates(String fk_flow)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhereInSQL(BillTemplateAttr.NodeID, "SELECT NodeID FROM WF_Node WHERE fk_flow='" + fk_flow + "'");
		qo.DoQuery();
	}
	/** 
	 按节点查询
	 
	 @param fk_node 节点ID
	*/
	public BillTemplates(int fk_node)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(BillTemplateAttr.NodeID, fk_node);
		qo.DoQuery();
	}
		///#endregion 查询与构造

}