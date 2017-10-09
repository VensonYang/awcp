package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.Port.*;

/** 
 属性
 
*/
public class FlowNodes extends EntitiesMM
{
	/** 
	 他的工作节点
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (FlowNode ns : convertFlowNodes(this))
		{
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;
	}
	/** 
	 流程抄送节点
	 
	*/
	public FlowNodes()
	{
	}
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<FlowNode> convertFlowNodes(Object obj) {
		return (ArrayList<FlowNode>) obj;
	}
	
	/** 
	 流程抄送节点
	 
	 @param NodeID 节点ID
	*/
	public FlowNodes(int NodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FlowNodeAttr.FK_Flow, NodeID);
		qo.DoQuery();
	}
	/** 
	 流程抄送节点
	 
	 @param NodeNo NodeNo 
	*/
	public FlowNodes(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FlowNodeAttr.FK_Node, NodeNo);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FlowNode();
	}
	/** 
	 流程抄送节点s
	 
	 @param sts 流程抄送节点
	 @return 
	*/
	public final Nodes GetHisNodes(Nodes sts)
	{
		Nodes nds = new Nodes();
		Nodes tmp = new Nodes();
		for (Node st :Nodes.convertNodes(sts))
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
	 流程抄送节点
	 
	 @param NodeNo 工作节点编号
	 @return 节点s
	*/
	public final Nodes GetHisNodes(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FlowNodeAttr.FK_Node, NodeNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (FlowNode en : convertFlowNodes(this))
		{
			ens.AddEntity(new Node(en.getFK_Flow()));
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
		qo.AddWhere(FlowNodeAttr.FK_Flow, nodeID);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (FlowNode en : convertFlowNodes(this))
		{
			ens.AddEntity(new Node(en.getFK_Node()));
		}
		return ens;
	}
}