package BP.WF.Template.Form;

import java.util.ArrayList;

import BP.En.EntitiesMM;
import BP.En.Entity;
import BP.En.QueryObject;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;

/** 
 节点表单
 
*/
public class FrmNodes extends EntitiesMM
{
	/** 
	 他的工作节点
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (FrmNode ns : convertFrmNodes(ens))
		{
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;
	}
	/** 
	 节点表单
	 
	*/
	public FrmNodes()
	{
	}
	/** 
	 节点表单
	 
	 @param NodeID 节点ID
	*/
	public FrmNodes(String fk_flow, int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FrmNodeAttr.FK_Flow, fk_flow);
		qo.addAnd();
		qo.AddWhere(FrmNodeAttr.FK_Node, nodeID);

		qo.addOrderBy(FrmNodeAttr.Idx);
		qo.DoQuery();
	}
	/** 
	 节点表单
	 
	 @param NodeNo NodeNo 
	*/
	public FrmNodes(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FrmNodeAttr.FK_Node, NodeNo);
		qo.addOrderBy(FrmNodeAttr.Idx);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmNode();
	}
	/** 
	 节点表单s
	 
	 @param sts 节点表单
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
	 节点表单
	 
	 @param NodeNo 工作节点编号
	 @return 节点s
	*/
	public final Nodes GetHisNodes(String NodeNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(FrmNodeAttr.FK_Node, NodeNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (FrmNode en : convertFrmNodes(this))
		{
			ens.AddEntity(new Node(en.getFK_Frm()));
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
		qo.AddWhere(FrmNodeAttr.FK_Frm, nodeID);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (FrmNode en : convertFrmNodes(this))
		{
			ens.AddEntity(new Node(en.getFK_Node()));
		}
		return ens;
	}
	public static ArrayList<FrmNode> convertFrmNodes(Object fns) {
		return (ArrayList<FrmNode>) fns;
	}
}