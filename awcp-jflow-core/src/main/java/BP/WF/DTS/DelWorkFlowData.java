package BP.WF.DTS;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.Port.*;
import BP.En.*;
import BP.Sys.*;
import BP.WF.Data.*;
import BP.WF.Template.*;
import BP.WF.Template.CC.CCLists;
import BP.WF.Template.WorkBase.Work;
import BP.DTS.*;

public class DelWorkFlowData extends DataIOEn
{
	public DelWorkFlowData()
	{
		this.HisDoType = DoType.UnName;
		this.Title = "<font color=red><b>清除流程数据</b></font>";
		//this.HisRunTimeType = RunTimeType.UnName;
		//this.FromDBUrl = DBUrlType.AppCenterDSN;
		//this.ToDBUrl = DBUrlType.AppCenterDSN;
	}
	@Override
	public void Do() throws Exception
	{
		if ( ! WebUser.getNo().equals("admin"))
		{
			throw new RuntimeException("非法用户。");
		}

	  //  DA.DBAccess.RunSQL("DELETE FROM WF_CHOfFlow");
		DBAccess.RunSQL("DELETE FROM WF_Bill");
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkerlist");
		DBAccess.RunSQL("DELETE FROM WF_GenerWorkFlow");
	  //  DA.DBAccess.RunSQL("DELETE FROM WF_WORKLIST");
		DBAccess.RunSQL("DELETE FROM WF_ReturnWork");
		DBAccess.RunSQL("DELETE FROM WF_GECheckStand");
		DBAccess.RunSQL("DELETE FROM WF_GECheckMul");
	//    DA.DBAccess.RunSQL("DELETE FROM WF_ForwardWork");
		DBAccess.RunSQL("DELETE FROM WF_SelectAccper");

		// 删除.
		CCLists ens = new CCLists();
		ens.ClearTable();

		Nodes nds = new Nodes();
		nds.RetrieveAll();

		String msg = "";
		for (Node nd : Nodes.convertNodes(nds) )
		{

			Work wk = null;
			try
			{
				wk = nd.getHisWork();
				DBAccess.RunSQL("DELETE FROM " + wk.getEnMap().getPhysicsTable());
			}
			catch (RuntimeException ex)
			{
				wk.CheckPhysicsTable();
				msg += "@" + ex.getMessage();
			}
		}

		if (!msg.equals(""))
		{
			throw new RuntimeException(msg);
		}
	}
}