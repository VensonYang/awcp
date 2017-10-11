package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;

/** 
 可退回的节点
 
*/
public class NodeReturns extends EntitiesMM
{
	/** 
	 他的退回到
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (NodeReturn ns : convertNodeReturns(this))
		{
			ens.AddEntity(new Node(ns.getReturnTo()));
		}
		return ens;
	}
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<NodeReturn> convertNodeReturns(Object obj) {
		return (ArrayList<NodeReturn>) obj;
	}
	
	/** 
	 可退回的节点
	 
	*/
	public NodeReturns()
	{
	}
	/** 
	 可退回的节点
	 
	 @param NodeID 节点ID
	*/
	public NodeReturns(int NodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeReturnAttr.FK_Node, NodeID);
		qo.DoQuery();
	}
	/** 
	 可退回的节点
	 
	 @param NodeNo NodeNo 
	*/
	public NodeReturns(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeReturnAttr.ReturnTo, NodeNo);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new NodeReturn();
	}
	/** 
	 可退回的节点s
	 
	 @param sts 可退回的节点
	 @return 
	*/
	public final Nodes GetHisNodes(Nodes sts)
	{
		Nodes nds = new Nodes();
		Nodes tmp = new Nodes();
		for (Node st : Nodes.convertNodes(sts))
		{
			tmp = this.GetHisNodes(st.getNo());
			for (Node nd : Nodes.convertNodes(tmp))
			{
				if (nds.Contains(nd))
				{
					continue;
				}
				nds.AddEntity(nd);
			}
		}
		return nds;
	}
	/** 
	 可退回的节点
	 
	 @param NodeNo 退回到编号
	 @return 节点s
	*/
	public final Nodes GetHisNodes(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeReturnAttr.ReturnTo, NodeNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (NodeReturn en : convertNodeReturns(this))
		{
			ens.AddEntity(new Node(en.getFK_Node()));
		}
		return ens;
	}
	/** 
	 转向此节点的集合的Nodes
	 
	 @param nodeID 此节点的ID
	 @return 转向此节点的集合的Nodes (FromNodes) 
	*/
	public final Nodes GetHisNodes(int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeReturnAttr.FK_Node, nodeID);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (NodeReturn en : convertNodeReturns(this))
		{
			ens.AddEntity(new Node(en.getReturnTo()));
		}
		return ens;
	}
}