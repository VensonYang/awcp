package BP.WF.Template.CC;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;


/** 
 抄送部门
 
*/
public class CCDepts extends EntitiesMM
{
	@SuppressWarnings("unchecked")
	public static ArrayList<CCDept> convertCCDepts(Object obj) {
		return (ArrayList<CCDept>) obj;
	}
	/** 
	 他的工作部门
	 
	*/
	public final Stations getHisStations()
	{
		Stations ens = new Stations();
		for (CCDept ns : convertCCDepts(this))
		{
			ens.AddEntity(new Station(ns.getFK_Dept()));
		}
		return ens;
	}
	/** 
	 他的工作节点
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (CCDept ns : convertCCDepts(this))
		{
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;
	}
	/** 
	 抄送部门
	 
	*/
	public CCDepts()
	{
	}
	/** 
	 抄送部门
	 
	 @param NodeID 节点ID
	*/
	public CCDepts(int NodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCDeptAttr.FK_Node, NodeID);
		qo.DoQuery();
	}
	/** 
	 抄送部门
	 
	 @param StationNo StationNo 
	*/
	public CCDepts(String StationNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCDeptAttr.FK_Dept, StationNo);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new CCDept();
	}
	/** 
	 取到一个工作部门集合能够访问到的节点s
	 
	 @param sts 工作部门集合
	 @return 
	*/
	public final Nodes GetHisNodes(Stations sts)
	{
		Nodes nds = new Nodes();
		Nodes tmp = new Nodes();
		for (Station st : Stations.convertStations(sts))
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
	 取到一个工作人员能够访问到的节点。
	 
	 @param empId 工作人员ID
	 @return 
	*/
	public final Nodes GetHisNodes_del(String empId)
	{
		Emp em = new Emp(empId);
		return this.GetHisNodes(em.getHisStations());
	}
	/** 
	 工作部门对应的节点
	 
	 @param stationNo 工作部门编号
	 @return 节点s
	*/
	public final Nodes GetHisNodes(String stationNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCDeptAttr.FK_Dept, stationNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (CCDept en :  convertCCDepts(this))
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
	public final Stations GetHisStations(int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCDeptAttr.FK_Node, nodeID);
		qo.DoQuery();

		Stations ens = new Stations();
		for (CCDept en : convertCCDepts(this))
		{
			ens.AddEntity(new Station(en.getFK_Dept()));
		}
		return ens;
	}
}