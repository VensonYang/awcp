package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;

/**
 * 节点工作岗位
 */
public class NodeStations extends EntitiesMM {
	
	public static ArrayList<NodeStation> convertNodeStations(Object obj) {
		return (ArrayList<NodeStation>) obj;
	}

	/**
	 * 他的工作岗位
	 */
	public final Stations getHisStations() {
		Stations ens = new Stations();
		for (NodeStation ns : convertNodeStations(this)) {
			ens.AddEntity(new Station(ns.getFK_Station()));
		}
		return ens;
	}

	/**
	 * 他的工作节点
	 */
	public final Nodes getHisNodes() {
		Nodes ens = new Nodes();
		for (NodeStation ns : convertNodeStations(this)) {
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;

	}

	/**
	 * 节点工作岗位
	 */
	public NodeStations() {
	}

	/**
	 * 节点工作岗位
	 * 
	 * @param nodeID
	 *            节点ID
	 */
	public NodeStations(int nodeID) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeStationAttr.FK_Node, nodeID);
		qo.DoQuery();
	}

	/**
	 * 节点工作岗位
	 * 
	 * @param StationNo
	 *            StationNo
	 */
	public NodeStations(String StationNo) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeStationAttr.FK_Station, StationNo);
		qo.DoQuery();
	}

	/**
	 * 得到它的 Entity
	 */
	@Override
	public Entity getGetNewEntity() {
		return new NodeStation();
	}

	/**
	 * 取到一个工作岗位集合能够访问到的节点s
	 * 
	 * @param sts
	 *            工作岗位集合
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
	 * 工作岗位对应的节点
	 * 
	 * @param stationNo
	 *            工作岗位编号
	 * @return 节点s
	 */
	public final Nodes GetHisNodes(String stationNo) {
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(NodeStationAttr.FK_Station, stationNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (NodeStation en : convertNodeStations(this)) {
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
		qo.AddWhere(NodeStationAttr.FK_Node, nodeID);
		qo.DoQuery();

		Stations ens = new Stations();
		for (NodeStation en : convertNodeStations(this)) {
			ens.AddEntity(new Station(en.getFK_Station()));
		}
		return ens;
	}
}