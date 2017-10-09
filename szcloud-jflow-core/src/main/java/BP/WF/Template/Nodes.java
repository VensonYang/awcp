package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.Sys.*;
import BP.En.*;
import BP.Port.*;
import BP.WF.Data.*;
import BP.WF.Template.*;
import BP.WF.Template.PubLib.NodeAttr;
import BP.WF.Template.PubLib.NodePosType;

/** 
 节点集合
 
*/
public class Nodes extends EntitiesOID
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	public static ArrayList<Node> convertNodes(Object nodes){
		return (ArrayList<Node>)nodes;
	}
	
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Node();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 节点集合
	 
	*/
	public Nodes()
	{
	}
	/** 
	 节点集合.
	 
	 @param FlowNo
	*/
	public Nodes(String fk_flow)
	{
		//   Nodes nds = new Nodes();
		this.Retrieve(NodeAttr.FK_Flow, fk_flow, NodeAttr.Step);
		//this.AddEntities(NodesCash.GetNodes(fk_flow));
		return;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 查询方法
	/** 
	 RetrieveAll
	 
	 @return 
	*/
	@Override
	public int RetrieveAll()
	{
		Object tempVar = Cash.GetObj(this.toString(), Depositary.Application);
		Nodes nds = (Nodes)((tempVar instanceof Nodes) ? tempVar : null);
		if (nds == null)
		{
			nds = new Nodes();
			QueryObject qo = new QueryObject(nds);
			qo.AddWhereInSQL(NodeAttr.NodeID, " SELECT Node FROM WF_Direction ");
			qo.addOr();
			qo.AddWhereInSQL(NodeAttr.NodeID, " SELECT ToNode FROM WF_Direction ");
			qo.DoQuery();

			Cash.AddObj(this.toString(), Depositary.Application, nds);
			Cash.AddObj(this.getGetNewEntity().toString(), Depositary.Application, nds);
		}

		this.clear();
		this.AddEntities(nds);
		return this.size();
	}
	/** 
	 开始节点
	 
	*/
	public final void RetrieveStartNode()
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeAttr.NodePosType, NodePosType.Start.getValue());
		qo.addAnd();
		qo.AddWhereInSQL(NodeAttr.NodeID, "SELECT FK_Node FROM WF_NodeStation WHERE FK_STATION IN (SELECT FK_STATION FROM Port_EmpSTATION WHERE FK_Emp='" + WebUser.getNo() + "')");

		qo.addOrderBy(NodeAttr.FK_Flow);
		qo.DoQuery();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}