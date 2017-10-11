package BP.WF.Template.AccepterRole;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;

/**
 * 节点部门
 */
public class NodeDepts extends EntitiesMM {
	/**
	 * 他的工作部门
	 */
	public final Stations getHisStations() {
		Stations ens = new Stations();
		for (NodeDept ns : convertNodeDepts(this)) {
			ens.AddEntity(new Station(ns.getFK_Dept()));
		}
		return ens;
	}

	/**
	 * 他的工作节点
	 */
	public final Nodes getHisNodes() {
		Nodes ens = new Nodes();
		for (NodeDept ns : convertNodeDepts(this)) {
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;

	}

	/**
	 * 节点部门
	 */
	public NodeDepts() {
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<NodeDept> convertNodeDepts(Object obj) {
		return (ArrayList<NodeDept>) obj;
	}

	/**
	 * 节点部门
	 * 
	 * @param NodeID
	 *            节点ID
	 */
	public NodeDepts(int NodeID) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeDeptAttr.FK_Node, NodeID);
		qo.DoQuery();
	}

	/**
	 * 节点部门
	 * 
	 * @param StationNo
	 *            StationNo
	 */
	public NodeDepts(String StationNo) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeDeptAttr.FK_Dept, StationNo);
		qo.DoQuery();
	}

	/**
	 * 得到它的 Entity
	 */
	@Override
	public Entity getGetNewEntity() {
		return new NodeDept();
	}

	/**
	 * 取到一个工作部门集合能够访问到的节点s
	 * 
	 * @param sts
	 *            工作部门集合
	 * @return
	 */
	public final Nodes GetHisNodes(Stations sts) {
		Nodes nds = new Nodes();
		Nodes tmp = new Nodes();
		for (Station st : Stations.convertStations(sts)) {
			tmp = this.GetHisNodes(st.getNo());
			for (Node nd : Nodes.convertNodes(tmp)) {
				if (nds.Contains(nd)) {
					continue;
				}
				nds.AddEntity(nd);
			}
		}
		return nds;
	}

	/**
	 * 取到一个工作人员能够访问到的节点。
	 * 
	 * @param empId
	 *            工作人员ID
	 * @return
	 */
	public final Nodes GetHisNodes_del(String empId) {
		Emp em = new Emp(empId);
		return this.GetHisNodes(em.getHisStations());
	}

	/**
	 * 工作部门对应的节点
	 * 
	 * @param stationNo
	 *            工作部门编号
	 * @return 节点s
	 */
	public final Nodes GetHisNodes(String stationNo) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeDeptAttr.FK_Dept, stationNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (NodeDept en : convertNodeDepts(this)) {
			ens.AddEntity(new Node(en.getFK_Node()));
		}
		return ens;
	}

	/**
	 * 转向此节点的集合的Nodes
	 * 
	 * @param nodeID
	 *            此节点的ID
	 * @return 转向此节点的集合的Nodes (FromNodes)
	 */
	public final Stations GetHisStations(int nodeID) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeDeptAttr.FK_Node, nodeID);
		qo.DoQuery();

		Stations ens = new Stations();
		for (NodeDept en : convertNodeDepts(this)) {
			ens.AddEntity(new Station(en.getFK_Dept()));
		}
		return ens;
	}

}