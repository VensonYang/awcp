package BP.WF.Template.CC;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;

/** 
 节点到人员
 
*/
public class CCEmps extends EntitiesMM
{
	@SuppressWarnings("unchecked")
	public static ArrayList<CCEmp> convertCCEmps(Object obj) {
		return (ArrayList<CCEmp>) obj;
	}
	/** 
	 他的到人员
	 
	*/
	public final Emps getHisEmps()
	{
		Emps ens = new Emps();
		for (CCEmp ns : convertCCEmps(this))
		{
			ens.AddEntity(new Emp(ns.getFK_Emp()));
		}
		return ens;
	}
	/** 
	 他的工作节点
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (CCEmp ns : convertCCEmps(this))
		{
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;

	}
	/** 
	 节点到人员
	 
	*/
	public CCEmps()
	{
	}
	/** 
	 节点到人员
	 
	 @param NodeID 节点ID
	*/
	public CCEmps(int NodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCEmpAttr.FK_Node, NodeID);
		qo.DoQuery();
	}
	/** 
	 节点到人员
	 
	 @param EmpNo EmpNo 
	*/
	public CCEmps(String EmpNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCEmpAttr.FK_Emp, EmpNo);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new CCEmp();
	}
	/** 
	 取到一个到人员集合能够访问到的节点s
	 
	 @param sts 到人员集合
	 @return 
	*/
	public final Nodes GetHisNodes(Emps sts)
	{
		Nodes nds = new Nodes();
		Nodes tmp = new Nodes();
		for (Emp st : Emps.convertEmps(sts))
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
	 到人员对应的节点
	 
	 @param EmpNo 到人员编号
	 @return 节点s
	*/
	public final Nodes GetHisNodes(String EmpNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCEmpAttr.FK_Emp, EmpNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (CCEmp en :convertCCEmps( this))
		{
			ens.AddEntity(new Node(en.getFK_Node()));
		}
		return ens;
	}
	/** 
	 转向此节点的集合的 Nodes
	 
	 @param nodeID 此节点的ID
	 @return 转向此节点的集合的Nodes (FromNodes) 
	*/
	public final Emps GetHisEmps(int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCEmpAttr.FK_Node, nodeID);
		qo.DoQuery();

		Emps ens = new Emps();
		for (CCEmp en : convertCCEmps(this))
		{
			ens.AddEntity(new Emp(en.getFK_Emp()));
		}
		return ens;
	}
}