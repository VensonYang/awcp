package BP.WF.Template;

import java.util.ArrayList;

import BP.En.Entities;
import BP.En.Entity;
import BP.En.QueryObject;
import BP.WF.Template.PubLib.NodeAttr;

 /** 
  节点方向
  
 */
public class Directions extends Entities
{
	/** 
	 节点方向
	 
	*/
	public Directions()
	{
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<Direction> convertDirections(Object obj) {
		return (ArrayList<Direction>) obj;
	}
	/** 
	 方向
	 
	 @param flowNo
	*/
	public Directions(String flowNo)
	{
		this.Retrieve(DirectionAttr.FK_Flow, flowNo);
	}
	/** 
	 节点方向
	 
	 @param NodeID 节点ID
	 @param dirType 类型
	*/
	public Directions(int NodeID, int dirType)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(DirectionAttr.Node,NodeID);
		qo.addAnd();
		qo.AddWhere(DirectionAttr.DirType, dirType);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new Direction();
	}
	/** 
	 此节点的转向方向集合
	 
	 @param nodeID 此节点的ID
	 @param isLifecyle 是不是判断在节点的生存期内		 
	 @return 转向方向集合(ToNodes) 
	*/
	public final Nodes GetHisToNodes(int nodeID, boolean isLifecyle)
	{
		Nodes nds = new Nodes();
		QueryObject qo = new QueryObject(nds);
		qo.AddWhereInSQL(NodeAttr.NodeID,"SELECT ToNode FROM WF_Direction WHERE Node="+nodeID);
		qo.DoQuery();
		return nds;
	}
	/** 
	 转向此节点的集合的Nodes
	 
	 @param nodeID 此节点的ID
	 @return 转向此节点的集合的Nodes (FromNodes) 
	*/
	public final Nodes GetHisFromNodes(int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(DirectionAttr.ToNode,nodeID);
		qo.DoQuery();
		Nodes ens = new Nodes();
		for(Direction en : convertDirections(this))
		{
			ens.AddEntity(new Node(en.getNode()));
		}
		return ens;
	}

}