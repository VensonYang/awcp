package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;

/** 
 可撤销的节点
 
*/
public class NodeCancels extends EntitiesMM
{
	/** 
	 他的撤销到
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (NodeCancel ns : convertNodeCancels(this))
		{
			ens.AddEntity(new Node(ns.getCancelTo()));
		}
		return ens;
	}
	/** 
	 可撤销的节点
	 
	*/
	public NodeCancels()
	{
	}
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<NodeCancel> convertNodeCancels(Object obj) {
		return (ArrayList<NodeCancel>) obj;
	}
	
	/** 
	 可撤销的节点
	 
	 @param NodeID 节点ID
	*/
	public NodeCancels(int NodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeCancelAttr.FK_Node, NodeID);
		qo.DoQuery();
	}
	/** 
	 可撤销的节点
	 
	 @param NodeNo NodeNo 
	*/
	public NodeCancels(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeCancelAttr.CancelTo, NodeNo);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new NodeCancel();
	}
	/** 
	 可撤销的节点s
	 
	 @param sts 可撤销的节点
	 <Cancels></Cancels>
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
	 可撤销的节点
	 
	 @param NodeNo 撤销到编号
	 <Cancels>节点s</Cancels>
	*/
	public final Nodes GetHisNodes(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeCancelAttr.CancelTo, NodeNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (NodeCancel en : convertNodeCancels(this))
		{
			ens.AddEntity(new Node(en.getFK_Node()));
		}
		return ens;
	}
	/** 
	 转向此节点的集合的Nodes
	 
	 @param nodeID 此节点的ID
	 <Cancels>转向此节点的集合的Nodes (FromNodes)</Cancels> 
	*/
	public final Nodes GetHisNodes(int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeCancelAttr.FK_Node, nodeID);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (NodeCancel en : convertNodeCancels(this))
		{
			ens.AddEntity(new Node(en.getCancelTo()));
		}
		return ens;
	}
}