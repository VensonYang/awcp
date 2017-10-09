package BP.WF;

import java.util.ArrayList;

import BP.En.*;
import BP.DA.*;
import BP.Port.*;
import BP.Web.*;
import BP.Sys.*;
import BP.WF.Template.*;
import BP.WF.Template.PubLib.ActionType;
import BP.WF.Template.PubLib.DataStoreModel;
import BP.WF.Template.PubLib.RunModel;
import BP.WF.Template.WorkBase.Work;
import BP.WF.Template.WorkBase.Works;
import BP.WF.Data.*;

/** 
 工作节点集合.
 
*/
//public class WorkNodes extends CollectionBase
public class WorkNodes extends ArrayList<WorkNode>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	
	@SuppressWarnings("unchecked")
	public static ArrayList<WorkNode> convertWorkNodes(Object obj) {
		return (ArrayList<WorkNode>) obj;
	}
	/** 
	 他的工作s
	  
	*/
	public final Works getGetWorks()
	{
		if (this.size() == 0)
		{
			throw new RuntimeException("@初始化失败，没有找到任何节点。");
		}

		Works ens = this.getItem(0).getHisNode().getHisWorks();
		ens.clear();

		for (WorkNode wn : this)
		{
			ens.AddEntity(wn.getHisWork());
		}
		return ens;
	}
	/** 
	 工作节点集合
	 
	*/
	public WorkNodes()
	{
	}

	public final int GenerByFID(Flow flow, long fid)
	{
		this.clear();

		Nodes nds = flow.getHisNodes();
		for (Node nd :Nodes.convertNodes( nds))
		{
			if (nd.getHisRunModel() == RunModel.SubThread)
			{
				continue;
			}

			Work wk = nd.GetWork(fid);
			if (wk == null)
			{
				continue;
			}


			this.Add(new WorkNode(wk, nd));
		}
		return this.size();
	}
	/** 
	 这个方法有问题的
	 
	 @param flow
	 @param oid
	 @return 
	*/
	public final int GenerByWorkID2014_01_06(Flow flow, long oid)
	{
		Nodes nds = flow.getHisNodes();
		for (Node nd :Nodes.convertNodes(nds))
		{
			Work wk = nd.GetWork(oid);
			if (wk == null)
			{
				continue;
			}
			String table = "ND" + Integer.parseInt(flow.getNo()) + "Track";
			String actionSQL = "SELECT EmpFrom,EmpFromT,RDT FROM " + table + " WHERE WorkID=" + oid + " AND NDFrom=" + nd.getNodeID() + " AND ActionType=" + ActionType.Forward.getValue();
			DataTable dt = DBAccess.RunSQLReturnTable(actionSQL);
			if (dt.Rows.size() == 0)
			{
				continue;
			}

			wk.setRec(dt.getValue(0, "EmpFrom").toString());
			wk.setRecText(dt.getValue(0, "EmpFromT").toString());
			wk.SetValByKey("RDT", dt.getValue(0, "RDT").toString());
			this.Add(new WorkNode(wk, nd));
		}
		return this.size();
	}
	public final int GenerByWorkID(Flow flow, long oid)
	{
		String table = "ND" + Integer.parseInt(flow.getNo()) + "Track";
		String actionSQL = "SELECT EmpFrom,EmpFromT,RDT,NDFrom FROM " + table + " WHERE WorkID=" + oid + " AND (ActionType=" + ActionType.Forward.getValue() + " OR ActionType=" + ActionType.ForwardFL.getValue() + " OR ActionType=" + ActionType.ForwardHL.getValue() + " OR ActionType=" + ActionType.SubFlowForward.getValue() + " ) ORDER BY RDT";
		DataTable dt = DBAccess.RunSQLReturnTable(actionSQL);

		String nds = "";
		for (DataRow dr : dt.Rows)
		{
			Node nd = new Node(Integer.parseInt( dr.getValue("NDFrom").toString()));
			Work wk = nd.GetWork(oid);
			if (wk == null)
			{
				wk = nd.getHisWork();
			}

			// 处理重复的问题.
			if (nds.contains((new Integer(nd.getNodeID())).toString() + ",") == true)
			{
				continue;
			}
			nds += (new Integer(nd.getNodeID())).toString() + ",";

			wk.setRec(dt.getValue(0, "EmpFrom").toString());
			wk.setRecText(dt.getValue(0, "EmpFromT").toString());
			wk.SetValByKey("RDT", dt.getValue(0, "RDT").toString());
			this.Add(new WorkNode(wk, nd));
		}
		return this.size();
	}
	/** 
	 删除工作流程
	 
	*/
	public final void DeleteWorks()
	{
		for (WorkNode wn : this)
		{
			if (wn.getHisFlow().getHisDataStoreModel() != DataStoreModel.ByCCFlow)
			{
				return;
			}
			wn.getHisWork().Delete();
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 增加一个WorkNode
	 
	 @param wn 工作 节点
	*/
	public final void Add(WorkNode wn)
	{
//		this.InnerList.Add(wn);
		this.add(wn);
	}
	/** 
	 根据位置取得数据
	 
	*/
	public final WorkNode getItem(int index)
	{
//		return (WorkNode)this.InnerList[index];
		return (WorkNode)this.get(index);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}