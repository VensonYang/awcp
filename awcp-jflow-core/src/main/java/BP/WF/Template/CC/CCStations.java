package BP.WF.Template.CC;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.Node;
import BP.WF.Template.Nodes;


/** 
 抄送到岗位
 
*/
public class CCStations extends EntitiesMM
{
	@SuppressWarnings("unchecked")
	public static ArrayList<CCStation> convertCCStations(Object obj) {
		return (ArrayList<CCStation>) obj;
	}

	/** 
	 他的工作岗位
	 
	*/
	public final Stations getHisStations()
	{
		Stations ens = new Stations();
		for (CCStation ns : convertCCStations(this))
		{
			ens.AddEntity(new Station(ns.getFK_Station()));
		}
		return ens;
	}
	/** 
	 他的工作节点
	 
	*/
	public final Nodes getHisNodes()
	{
		Nodes ens = new Nodes();
		for (CCStation ns : convertCCStations(this))
		{
			ens.AddEntity(new Node(ns.getFK_Node()));
		}
		return ens;

	}
	/** 
	 抄送到岗位
	 
	*/
	public CCStations()
	{
	}
	/** 
	 抄送到岗位
	 
	 @param nodeID 节点ID
	*/
	public CCStations(int nodeID)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCStationAttr.FK_Node, nodeID);
		qo.DoQuery();
	}
	/** 
	 抄送到岗位
	 
	 @param StationNo StationNo 
	*/
	public CCStations(String StationNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCStationAttr.FK_Station, StationNo);
		qo.DoQuery();
	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new CCStation();
	}
	/** 
	 工作岗位对应的节点
	 
	 @param stationNo 工作岗位编号
	 @return 节点s
	*/
	public final Nodes GetHisNodes(String stationNo)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCStationAttr.FK_Station, stationNo);
		qo.DoQuery();

		Nodes ens = new Nodes();
		for (CCStation en : convertCCStations(this))
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
		qo.AddWhere(CCStationAttr.FK_Node, nodeID);
		qo.DoQuery();

		Stations ens = new Stations();
		for (CCStation en : convertCCStations(this))
		{
			ens.AddEntity(new Station(en.getFK_Station()));
		}
		return ens;
	}
}